package hr.fer.oop.lab3.topic1.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;
import hr.fer.oop.lab3.topic1.shell.Terminal;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * This class represents a "filter" command. Command searches in all folders and
 * subfolders of working directory for a given pattern. When it finds it, it
 * prints an absolute path of a file. It uses glob to match the pattern
 *
 * Syntax: filter pattern
 *
 * This command is not case sensitive.
 *
 * For example, if provided pattern is "img*.png", command will find
 * "img-split.png" and "ImG-Svemir.Png" also.
 *
 *
 * Created by borna on 09/12/14.
 *
 * @version 1.0
 * @author borna
 */
public class FilterCommand extends AbstractCommand {

    public FilterCommand() {
        super("filter", "Command that filters subfolders with given pattern and prints full path");
    }

    public static class FilterVisitor extends SimpleFileVisitor<Path> {

        private final PathMatcher matcher;


        FilterVisitor (String pattern) {
            matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern.toUpperCase());

        }

        // Compares the glob pattern (not case sensitive!) against
        // the file or directory name.

        void find(Path file) {
            Path name = null;
            if (file.getFileName() != null) {
                name = Paths.get(file.getFileName().toString().toUpperCase());
            }
            if (name != null && matcher.matches(name)) {
                System.out.println(file);
            }
        }


        // Invoke the pattern matching
        // method on each file.
        @Override
        public FileVisitResult visitFile(Path file,
                                         BasicFileAttributes attrs) {
            if (attrs.isRegularFile()) {
                find(file);
            }
            return FileVisitResult.CONTINUE;
        }

        // Invoke the pattern matching
        // method on each directory.
        @Override
        public FileVisitResult preVisitDirectory (Path dir, BasicFileAttributes attrs) {
            if (dir != null) {
                find (dir);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed (Path file, IOException exc) {
            return FileVisitResult.CONTINUE;
        }


    }

    @Override
    public CommandStatus execute (Environment environment, String argument) throws IOException {

        Terminal activeTerminal = environment.getActiveTerminal();

        if (argument.length() == 0) {
            environment.writeln("The syntax of the command is incorrect.");
            return CommandStatus.CONTINUE;
        }

        FilterVisitor finder = new FilterVisitor (argument);

        Files.walkFileTree (activeTerminal.getCurrentPath(), finder);

        return CommandStatus.CONTINUE;
    }
}