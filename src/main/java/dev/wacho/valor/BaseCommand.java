package dev.wacho.valor;

import dev.wacho.valor.utils.CC;
import dev.wacho.valor.utils.ValorUtils;
import lombok.Getter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseCommand implements CommandExecutor, TabCompleter {

    private String name, description;
    private String[] aliases;

    /**
     * List to store registered CommandArgument instances associated with this command.
     */
    @Getter
    private List<CommandArgument> commandArguments = new ArrayList<>();

    /**
     * Instantiates a new Base command with the specified name.
     *
     * @param name the name of the command
     */
    public BaseCommand(String name) {
        this(name, null);
    }

    /**
     * Instantiates a new Base command with the specified name and description.
     *
     * @param name        the name of the command
     * @param description the description of the command
     */
    public BaseCommand(String name, String description) {
        this(name, description, ArrayUtils.EMPTY_STRING_ARRAY);
    }


    /**
     * Instantiates a new Base command with the specified name, description, and aliases.
     *
     * @param name        the name of the command
     * @param description the description of the command
     * @param aliases     an array of aliases for the command
     */
    public BaseCommand(String name, String description, String... aliases) {
        this.name = name;
        this.description = description;
        this.aliases = Arrays.copyOf(aliases, aliases.length);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(CC.translate("&7&m" + StringUtils.repeat("-", 30)));
            sender.sendMessage(CC.translate("&cAvailable sub-command(s) for '&7" + command.getName() + "&c'."));
            sender.sendMessage("");

            for (CommandArgument commandArgument : commandArguments) {
                if (commandArgument.permission != null && !sender.hasPermission(commandArgument.permission)) {
                    continue;
                }
                sender.sendMessage(CC.translate(" &e" + commandArgument.getUsage(label) + (commandArgument.description != null ? " &7- &f" + commandArgument.description : "")));
            }
            sender.sendMessage(CC.translate("&7&m" + StringUtils.repeat("-", 30)));
        } else {
            CommandArgument commandArgument = getArgument(args[0]);
            if (commandArgument == null || (commandArgument.permission != null && !sender.hasPermission(commandArgument.permission))) {
                sender.sendMessage(CC.translate("&cNo argument found."));
            } else {
                if (commandArgument.onlyplayers && sender instanceof ConsoleCommandSender) {
                    Bukkit.getConsoleSender().sendMessage(CC.translate("&cThis command can only be executed by players."));
                    return false;
                }
                commandArgument.onExecute(sender, label, args);
            }
        }
        return true;
    }

    /**
     * Retrieves a CommandArgument instance based on its name or aliases.
     *
     * @param name the name or alias of the command argument
     * @return the CommandArgument instance, or null if not found
     */
    public CommandArgument getArgument(String name) {
        for (CommandArgument commandArgument : commandArguments) {
            if (commandArgument.name.equalsIgnoreCase(name) || Arrays.asList(commandArgument.aliases).contains(name.toLowerCase())) {
                return commandArgument;
            }
        }
        return null;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> results = new ArrayList<>();
        if (args.length < 2) {

            for (CommandArgument commandArgument : commandArguments) {
                String permission = commandArgument.permission;
                if (permission == null || sender.hasPermission(permission)) {
                    results.add(commandArgument.name);
                }
            }

            if (results.isEmpty()) {
                return null;
            }
        } else {
            CommandArgument commandArgument = getArgument(args[0]);
            if (commandArgument == null) {
                return results;
            }

            String permission = commandArgument.permission;
            if (permission == null || sender.hasPermission(permission)) {
                results = commandArgument.onTabComplete(sender, label, args);

                if (results == null) {
                    return null;
                }
            }
        }

        return ValorUtils.getCompletions(args, results);
    }

    /**
     * Gets the name of the command.
     *
     * @return the name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the command.
     *
     * @return the description of the command
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the aliases for the command.
     *
     * @return an array of aliases for the command
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * Registers a new CommandArgument for the command.
     *
     * @param commandArgument the CommandArgument to be registered
     */
    public void registerArgument(CommandArgument commandArgument) {
        this.commandArguments.add(commandArgument);
    }
}