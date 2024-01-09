package dev.wacho.valor.command;

import dev.wacho.valor.BaseCommand;
import dev.wacho.valor.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ExampleCommand extends BaseCommand {

    /**
     * Instantiates a new Example command with the name "hello" and a simple description.
     */
    public ExampleCommand() {
        super("hello", "A simple example command", "hi");
    }

    /**
     * Executes the "hello" command. Sends a "Hello, World!" message to the command sender.
     *
     * @param sender  the command sender
     * @param command the command that was executed
     * @param label   the label used to execute the command
     * @param args    the arguments provided with the command
     * @return true if the command was executed successfully
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Sends a formatted "Hello, World!" message to the command sender
        sender.sendMessage(CC.translate("&aHello, World!"));
        return true;
    }
}
