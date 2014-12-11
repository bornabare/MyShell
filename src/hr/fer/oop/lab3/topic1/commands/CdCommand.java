package hr.fer.oop.lab3.topic1.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by borna on 07/12/14.
 */
public class CdCommand extends AbstractCommand {
    public CdCommand() {
        super("cd", "is CD Command");
    }

    /**
     *  1st check if it is relative path then absolute path!!! for example in MyShell write src can cause problems otherwise!!
     *
     * @param environment
     * @param arguments
     * @return CommandStatus.CONTINUE
     *
     *
     */
    @Override
    public CommandStatus execute(Environment environment, String arguments) throws IOException {

        if (arguments.equals("..")) {

            Path path = environment.getActiveTerminal().getCurrentPath().getParent();
            environment.getActiveTerminal().setCurrentPath(path);
            environment.writeln("Current directory is now set to" + path.toString());
        }

        else {
            //for relative path
            Path currentPath = environment.getActiveTerminal().getCurrentPath();
            String newPathString = currentPath.toString().concat("/"+arguments);
            Path newRelativePath = Paths.get(newPathString);

            //for absolute path
            Path newAbsolutePath = Paths.get(arguments);


            if (Files.exists(newRelativePath)) {
                environment.getActiveTerminal().setCurrentPath(newRelativePath);
                environment.writeln("(RP)Current directory is now set to " + newRelativePath.toString());

            }

            else if (Files.exists(newAbsolutePath)) {
                    environment.getActiveTerminal().setCurrentPath(newAbsolutePath);
                    environment.writeln("(AP)Current directory is now set to " + newAbsolutePath.toString());
            }

            else {
                environment.writeln("Invalid path provided! Try another!");
            }
        }
        return CommandStatus.CONTINUE;
    }
}
