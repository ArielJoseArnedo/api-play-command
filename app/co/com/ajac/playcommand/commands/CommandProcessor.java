package co.com.ajac.playcommand.commands;

import co.com.ajac.infrastructure.api.commands.CommandProvider;
import co.com.ajac.infrastructure.api.events.EventPublisher;
import io.vavr.collection.List;
import play.mvc.Controller;

import javax.inject.Inject;


public class CommandProcessor extends Controller implements PlayProcessor {

    private final ProviderManager providerManager;
    private final EventPublisher eventPublisher;

    @Inject
    public CommandProcessor(ProviderManager providerManager, EventPublisher eventPublisher) {
        this.providerManager = providerManager;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<CommandProvider> commandProviders() {
        return providerManager.getCommandProviders();
    }

    @Override
    public EventPublisher eventPublisher() {
        return eventPublisher;
    }
}
