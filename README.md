# Command Framework README

This README provides an overview of the `BaseCommand` and `CommandArgument` classes, which together form the foundation of a flexible and extensible command framework.

## BaseCommand Class

### Features
- **Command Execution:** Handles the execution of commands and their associated arguments.
- **Command Argument Registration:** Allows the registration of various command arguments associated with the command.
- **Tab Completion:** Provides tab-completion support for command arguments.

### Usage Example
```java
public class MyCommand extends BaseCommand {

    public MyCommand() {
        super("mycommand", "A sample command", "alias1", "alias2");
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Custom logic for command execution

        return true;
    }
}
```

## CommandArgument Class

### Features
- **Command Argument Definition:** Defines the properties and behavior of a command argument.
- **Permission Handling:** Supports specifying permissions required to execute the argument.
- **Player-Only Constraint:** Allows marking an argument as executable only by players.
- **Tab-Completion:** Provides tab-completion support for the argument.

### Usage Example
```java
public class MyCommandArgument extends CommandArgument {

    public MyCommandArgument() {
        super("myarg", "Description of my argument", "my.permission", "alias1", "alias2", true);
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " myarg <player>";
    }

    @Override
    public void onExecute(CommandSender sender, String label, String[] args) {
        // Custom logic for argument execution
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        // Custom tab-completion logic
        return Arrays.asList("player1", "player2");
    }
}
```

## Getting Started
1. **Command Registration:** Create an instance of `BaseCommand` and register it in your plugin or application.
2. **Argument Registration:** Create subclasses of `CommandArgument` for each distinct argument and register them within your `BaseCommand` instance.
3. **Custom Logic:** Implement the custom logic for command execution and argument handling.

This framework provides a solid foundation for creating complex and organized command structures in your Bukkit/Spigot plugins or other Java applications. Feel free to extend and customize as needed for your specific requirements.
