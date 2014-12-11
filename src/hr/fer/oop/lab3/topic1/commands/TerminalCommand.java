package hr.fer.oop.lab3.topic1.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;
import hr.fer.oop.lab3.topic1.shell.Terminal;

import java.io.IOException;

/**
 * Created by borna on 07/12/14.
 */
public class TerminalCommand extends AbstractCommand {


    public TerminalCommand() {
        super("terminal", "This is Terminal Command");
    }

    /**
     *
     * @param environment
     * @param argument
     *
     * @return CommandStatus
     * @throws IOException
     */
    @Override
    public CommandStatus execute(Environment environment, String argument) throws IOException {

        Terminal wantedTerminal = environment.getOrCreateTerminal(Integer.parseInt(argument));
        environment.writeln("Changed current terminal to "+argument);
        environment.setActiveTerminal(wantedTerminal);

        return CommandStatus.CONTINUE;
    }
}
