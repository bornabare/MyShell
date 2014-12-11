package hr.fer.oop.lab3.topic1.shell;

import java.io.IOException;

/**
 * Created by borna on 07/12/14.
 */
public interface ShellCommand {
    public String getCommandName();
    public String getCommandDescription();
    public CommandStatus execute (Environment environment, String argument) throws IOException;
}
