package co.com.ajac.playcommand.commands;

import co.com.ajac.base.events.Publisher;
import co.com.ajac.infrastructure.api.commands.CommandProvider;
import io.vavr.collection.List;
import play.mvc.Controller;

import javax.inject.Inject;


public class CommandProcessor extends Controller implements PlayProcessor {

    private final ProviderManager providerManager;
    private final Publisher publisher;

    @Inject
    public CommandProcessor(ProviderManager providerManager, Publisher publisher) {
        this.providerManager = providerManager;
        this.publisher = publisher;
    }

    @Override
    public List<CommandProvider> commandProviders() {
        return providerManager.getCommandProviders();
    }

    @Override
    public Publisher publisher() {
        return publisher;
    }
}
