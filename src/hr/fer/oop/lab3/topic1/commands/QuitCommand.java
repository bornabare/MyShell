package hr.fer.oop.lab3.topic1.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;

import java.io.IOException;

/**
 * Created by borna on 07/12/14.
 */
public class QuitCommand extends AbstractCommand {
    public QuitCommand() {
        super("quit", "This is Quit Command");
    }

    /**
     *
     * @param environment
     * @param argument
     * @return CommandStatus.EXIT
     * @throws IOException
     */
    @Override
    public CommandStatus execute(Environment environment, String argument) throws IOException {
        return CommandStatus.EXIT;
    }
}
