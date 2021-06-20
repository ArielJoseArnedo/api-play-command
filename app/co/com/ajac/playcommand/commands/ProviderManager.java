package co.com.ajac.playcommand.commands;

import co.com.ajac.infrastructure.api.commands.CommandProvider;
import io.vavr.collection.List;

import javax.inject.Singleton;

@Singleton
public class ProviderManager {

    private final List<CommandProvider> commandProviders;

    public ProviderManager(CommandProvider... commandProviders) {
        this.commandProviders = List.of(commandProviders);
    }

    public List<CommandProvider> getCommandProviders() {
        return commandProviders;
    }
}
