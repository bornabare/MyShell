package hr.fer.oop.lab3.topic1.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by borna on 09/12/14.
 */
public class TypeCommand extends AbstractCommand {

    public TypeCommand() {
        super("type", "Command that prints file given as one word parameter");
    }

    /**
     *
     * @param environment
     * @param argument
     * @return CommandStatus.CONTINUE
     *
     *
     */
    @Override
    public CommandStatus execute(Environment environment, String argument) throws IOException {

        Path currentPath = environment.getActiveTerminal().getCurrentPath();

        String newPathString = currentPath.toString().concat("/"+argument+".txt");
        Path newPath = Paths.get(newPathString);

        if (Files.exists(newPath)) {

            BufferedReader br = new BufferedReader(new FileReader(newPathString));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                environment.write(sb.toString());
            } finally {
                br.close();
            }
        }

        return CommandStatus.CONTINUE;
    }
}
