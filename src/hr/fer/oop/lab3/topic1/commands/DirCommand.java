package hr.fer.oop.lab3.topic1.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by borna on 1/6/15.
 */
public class DirCommand extends AbstractCommand {

    public DirCommand() {
        super("dir", "Listing directories in current path");
    }

    @Override
    public CommandStatus execute (Environment environment, String splitThisArgument){

        String[] arguments = splitThisArgument.split(" ");

        if (splitThisArgument == null || (arguments.length < 2) || (arguments.length>4)) {
            System.out.println("dir command takes at least 1 argument and not more than 4!");
            return CommandStatus.CONTINUE;
        }


        /*
        This means that directory is given in argument!
        Everything we are doing later will be done for this and not the current directory
        */

        if (arguments[0].startsWith("/")) {

            Path dirPath = Paths.get(arguments[0]);
            if (!Files.isDirectory(dirPath)) {
                System.out.println("dirPath is not pointing to directory !");
                return CommandStatus.CONTINUE;
            }

            File[] currentDirFiles = dirPath.toFile().listFiles();
            if (currentDirFiles == null) {
                System.out.println("listFiles() error occured !");
                return CommandStatus.CONTINUE;
            }
            for (File f : currentDirFiles) {
                BasicFileAttributeView faView = Files.getFileAttributeView(f.toPath(), BasicFileAttributeView.class,
                        LinkOption.NOFOLLOW_LINKS);
                BasicFileAttributes fileAttributes;
                try {
                    fileAttributes = faView.readAttributes();
                } catch (Exception e) {
                    throw new IllegalArgumentException("faView.readAttributes() error !", e);
                }

                StringBuilder lsLine = new StringBuilder();
                lsLine.append(f.isDirectory() ? "d" : "-"); // append file attributes
                lsLine.append(f.canRead() ? "r" : "-");
                lsLine.append(f.canWrite() ? "w" : "-");
                lsLine.append(f.canExecute() ? "x" : "-");

                String fileSize = String.format("%10s", fileAttributes.size());
                lsLine.append(" " + fileSize); // append file size

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                FileTime fileTime = fileAttributes.creationTime();
                String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
                lsLine.append(" " + formattedDateTime); // append file creation time

                lsLine.append(" " + f.getName()); // append file name

                System.out.println(lsLine.toString()); // print complete ls line
            }

        }
        return CommandStatus.CONTINUE;
    }
}
