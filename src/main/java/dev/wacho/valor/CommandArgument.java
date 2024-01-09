package dev.wacho.valor;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class CommandArgument {

    /**
     * The name of the command argument.
     */
    @Getter
    @Setter
    public String name;

    /**
     * The description of the command argument, providing additional details about its purpose.
     */
    @Getter
    @Setter
    public String description;

    /**
     * The permission required to execute the command argument, if applicable.
     */
    @Getter
    @Setter
    public String permission;

    /**
     * An array of aliases that can be used as alternative names for the command argument.
     */
    public String[] aliases;

    /**
     * Indicates whether the command argument can only be executed by players.
     */
    @Getter
    @Setter
    public boolean onlyplayers;

    /**
     * Instantiates a new Command argument with the specified name.
     *
     * @param name the name of the command argument
     */
    public CommandArgument(String name) {
        this(name, null);
    }

    /**
     * Instantiates a new Command argument with the specified name and description.
     *
     * @param name        the name of the command argument
     * @param description the description of the command argument
     */
    public CommandArgument(String name, String description) {
        this(name, description, null);
    }

    /**
     * Instantiates a new Command argument with the specified name, description, and permission.
     *
     * @param name        the name of the command argument
     * @param description the description of the command argument
     * @param permission  the permission required to execute the command argument
     */
    public CommandArgument(String name, String description, String permission) {
        this(name, description, permission, ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Instantiates a new Command argument with the specified name, description, permission, and aliases.
     *
     * @param name        the name of the command argument
     * @param description the description of the command argument
     * @param permission  the permission required to execute the command argument
     * @param aliases     an array of aliases for the command argument
     */
    public CommandArgument(String name, String description, String permission, String... aliases) {
        this(name, description, permission, aliases, false);
    }

    /**
     * Instantiates a new Command argument with the specified name, description, permission, aliases, and onlyplayers flag.
     *
     * @param name        the name of the command argument
     * @param description the description of the command argument
     * @param permission  the permission required to execute the command argument
     * @param aliases     an array of aliases for the command argument
     * @param onlyplayers indicates whether the command argument can only be executed by players
     */
    public CommandArgument(String name, String description, String permission, String[] aliases, boolean onlyplayers) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.aliases = Arrays.copyOf(aliases, aliases.length);
        this.onlyplayers = onlyplayers;
    }

    /**
     * Gets the usage information for the command argument.
     *
     * @param label the label associated with the command
     * @return the usage information as a string
     */
    public abstract String getUsage(String label);

    /**
     * Executes the command argument.
     *
     * @param sender the entity executing the command
     * @param label  the label associated with the command
     * @param args   the arguments provided for the command
     */
    public abstract void onExecute(CommandSender sender, String label, String[] args);

    /**
     * Provides a list of tab-completions for the command argument.
     *
     * @param sender the entity executing the command
     * @param label  the label associated with the command
     * @param args   the arguments provided for the command
     * @return a list of possible tab-completions as strings
     */
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }
}