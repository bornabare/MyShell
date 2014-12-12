package hr.fer.oop.lab3.topic1.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;
import hr.fer.oop.lab3.topic1.shell.Terminal;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by borna on 09/12/14.
 */
public class FilterCommand extends AbstractCommand {

    public FilterCommand() {
        super("filter", "Command that filters");
    }

    @Override
    public CommandStatus execute(Environment environment, String argument) {
        Terminal terminal = environment.getActiveTerminal();
        Path path = terminal.getCurrentPath();
        File file = new File(path.toString());


        FileVisitor visitor = new FileVisitor() {
            @Override
            public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
                return null;
            }

            @Override
            public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {

                return null;
            }

            @Override
            public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
                return null;
            }

            @Override
            public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
                return null;
            }
        };
//        visitor.visitFile(file);


        return CommandStatus.CONTINUE;
    }
}
