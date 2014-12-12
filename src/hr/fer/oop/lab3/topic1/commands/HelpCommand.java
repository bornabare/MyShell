package hr.fer.oop.lab3.topic1.commands;

import hr.fer.oop.lab3.topic1.SimpleHashTable;
import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;
import hr.fer.oop.lab3.topic1.shell.ShellCommand;

import java.io.IOException;

/**
 * Created by borna on 07/12/14.
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help", "is Help Command");
    }

    /**
     *
     * @param environment
     * @param argument
     * @return CommandStatus.CONTINUE
     *
     * lists all commands with their descriptions
     */
    @Override
    public CommandStatus execute(Environment environment, String argument) throws IOException {

        for (ShellCommand command : environment.commands()) {

//            SimpleHashTable.TableEntry commandTableEntry = (SimpleHashTable.TableEntry) commandObject;
//            ShellCommand command = (ShellCommand) commandTableEntry.getValue();
            environment.writeln(command.getCommandName()+" => "+command.getCommandDescription());
        }
        return CommandStatus.CONTINUE;
    }
}
