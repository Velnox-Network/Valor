package dev.wacho.valor;

import dev.wacho.valor.command.ExampleCommand;
import dev.wacho.valor.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandHandler {

    private JavaPlugin javaPlugin;
    private CommandMap commandMap;

    /**
     * Instantiates a new Command handler and registers default commands.
     *
     * @param javaPlugin the JavaPlugin instance
     */
    public CommandHandler(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;

        // Register default commands upon instantiation
        registerCommand(new ExampleCommand());  // Command without required permission
        registerCommand(new ExampleCommand(), "example.command");  // Command with "example.command" permission
    }

    /**
     * Registers a BaseCommand with optional permission.
     *
     * @param baseCommand the BaseCommand instance to register
     */
    private void registerCommand(BaseCommand baseCommand) {
        registerCommand(baseCommand, null);
    }

    /**
     * Registers a BaseCommand with an optional permission.
     *
     * @param baseCommand the BaseCommand instance to register
     * @param permission  the optional permission required to execute the command
     */
    private void registerCommand(BaseCommand baseCommand, String permission) {
        PluginCommand command = getCommand(baseCommand.getName(), javaPlugin);

        // Set the default permission message for unauthorized users
        command.setPermissionMessage(CC.translate("&cYou do not have permissions to use this command."));

        // Set the optional permission for the command
        if (permission != null) {
            command.setPermission(permission.toLowerCase());
        }

        // Set the command description if available
        if (baseCommand.getDescription() != null) {
            command.setDescription(baseCommand.getDescription());
        }

        // Set command aliases
        command.setAliases(Arrays.asList(baseCommand.getAliases()));

        // Set command executor and tab completer
        command.setExecutor(baseCommand);
        command.setTabCompleter(baseCommand);

        // Register the command with Bukkit's command map
        if (!getCommandMap().register(baseCommand.getName(), command)) {
            // Re-register if the command is already registered
            command.unregister(getCommandMap());
            getCommandMap().register(baseCommand.getName(), command);
        }
    }

    /**
     * Retrieves the command map from the Bukkit PluginManager.
     *
     * @return the command map
     */
    private CommandMap getCommandMap() {
        if (commandMap != null) {
            return commandMap;
        }

        try {
            // Access the private field 'commandMap' in SimplePluginManager
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);

            // Get the command map instance
            commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
        } catch (Exception ignored) {
            // Handle exceptions by ignoring them
        }

        return commandMap;
    }

    /**
     * Creates a new PluginCommand instance with the specified name and owner.
     *
     * @param name  the name of the command
     * @param owner the owning plugin
     * @return the PluginCommand instance
     */
    private PluginCommand getCommand(String name, Plugin owner) {
        PluginCommand command = null;

        try {
            // Access the private constructor of PluginCommand
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            // Create a new PluginCommand instance
            command = constructor.newInstance(name, owner);
        } catch (Exception ignored) {
            // Handle exceptions by ignoring them
        }

        return command;
    }
}
