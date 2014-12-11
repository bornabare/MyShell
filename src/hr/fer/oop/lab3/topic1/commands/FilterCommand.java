package hr.fer.oop.lab3.topic1.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;

import java.io.File;

/**
 * Created by borna on 09/12/14.
 */
public class FilterCommand extends AbstractCommand {

    public FilterCommand() {
        super("filter", "Command that filters");
    }

    @Override
    public CommandStatus execute(Environment environment, String argument) {





        return CommandStatus.CONTINUE;
    }
}
