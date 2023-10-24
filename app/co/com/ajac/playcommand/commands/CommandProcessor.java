package co.com.ajac.playcommand.commands;

import co.com.ajac.messaging.publishers.PublisherProvider;
import co.com.ajac.infrastructure.api.commands.CommandProvider;
import io.vavr.collection.List;
import play.mvc.Controller;

import javax.inject.Inject;


public class CommandProcessor extends Controller implements PlayProcessor {

    private final ProviderManager providerManager;
    private final PublisherProvider publisher;

    @Inject
    public CommandProcessor(ProviderManager providerManager, PublisherProvider publisher) {
        this.providerManager = providerManager;
        this.publisher = publisher;
    }

    @Override
    public List<CommandProvider> commandProviders() {
        return providerManager.getCommandProviders();
    }

    @Override
    public PublisherProvider publisher() {
        return publisher;
    }
}
