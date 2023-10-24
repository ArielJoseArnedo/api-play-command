package co.com.ajac.playcommand.commands;

import co.com.ajac.base.errors.AppError;
import co.com.ajac.concurrency.FutureEither;
import co.com.ajac.infrastructure.api.commands.Command;
import co.com.ajac.infrastructure.api.commands.CommandUtil;
import co.com.ajac.infrastructure.api.commands.Processor;
import co.com.ajac.infrastructure.api.commands.Request;
import co.com.ajac.infrastructure.api.commands.Response;
import co.com.ajac.messaging.events.Event;
import com.fasterxml.jackson.databind.JsonNode;
import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import io.vavr.control.Option;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletionStage;

import static co.com.ajac.infrastructure.api.util.StgMonad.getOption;

public interface PlayProcessor extends Processor, CommandUtil {

    default CompletionStage<Result> execute(Http.Request request) {
        return toExecuteCommandAndRequest(findCommandAndRequest(request));
    }

    default FutureEither<AppError, Tuple2<Command, Request>> findCommandAndRequest(Http.Request request) {
        return processRequest((Option<String> pathUrlOpt, Option<String> commandNameOpt, Option<JsonNode> commandBodyOpt) ->
            findProvider(pathUrlOpt, commandProviders())
              .flatMap(commandProvider -> findCommand(commandNameOpt, commandProvider)
                .flatMap(command -> findRequest(commandNameOpt, commandBodyOpt, commandProvider)
                  .map(commandRequest -> Tuple.of(command, commandRequest))
                )
              ),
          request);
    }

    default FutureEither<AppError, Tuple2<Command, Request>> processRequest
      (
        Function3<Option<String>, Option<String>, Option<JsonNode>, FutureEither<AppError, Tuple2<Command, Request>>> function3,
        Http.Request request
      ) {
        final Option<String> pathUrlOpt = getOption(request.path());

        final Option<JsonNode> body = getOption(request.body().asJson());
        final Option<String> commandName = body.map(jsonNode -> jsonNode.path("command").asText());
        final Option<JsonNode> commandBody = body.map(jsonNode -> jsonNode.path("body"));

        return function3.apply(pathUrlOpt, commandName, commandBody);
    }

    @SuppressWarnings("unchecked")
    default CompletionStage<Result> toExecuteCommandAndRequest(FutureEither<AppError, Tuple2<Command, Request>> commandAndRequest) {
        return commandAndRequest
          .flatMap(commandRequestTuple2 -> (FutureEither<AppError, Tuple2<Option<Response>, List<Event>>>) commandRequestTuple2._1().execute(commandRequestTuple2._2()))
          .getValue()
          .map(this::onSuccess)
          .recover(throwable -> Results.internalServerError("Unexpected error has occurred"))
          .toCompletableFuture();
    }

    default Result onSuccess(Either<AppError, Tuple2<Option<Response>, List<Event>>> resultEither) {
        return resultEither.fold(
          this::responseError,
          response -> {
              Future.of(() ->
                publisher().publish(response._2)
              );
              return response._1()
                .map(commandResponse -> Results.ok(Json.toJson(commandResponse)))
                .getOrElse(Results.ok("Command executed successfully"));
          });
    }

    default Result responseError(AppError appError) {
        return Results.badRequest(makeResponseError(appError));
    }
}
