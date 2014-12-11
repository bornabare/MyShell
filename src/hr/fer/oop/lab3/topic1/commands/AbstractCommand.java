package hr.fer.oop.lab3.topic1.commands;

import hr.fer.oop.lab3.topic1.shell.ShellCommand;

/**
 * Created by borna on 07/12/14.
 */
public abstract class AbstractCommand implements ShellCommand {

    private String commandName;
    private String commandDescription;

    public AbstractCommand(String commandName, String commandDescription) {
        this.commandName = commandName;
        this.commandDescription = commandDescription;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public String getCommandDescription() {
        return commandDescription;
    }
}
