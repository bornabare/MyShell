package hr.fer.oop.lab3.topic1.commands;

import com.sun.corba.se.spi.extension.CopyObjectPolicy;
import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;
import hr.fer.oop.lab3.topic1.shell.ShellCommand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by borna on 09/12/14.
 */
public class XCopyCommand extends AbstractCommand {

    public XCopyCommand() {
        super("xcopy", "Command that copies recursively");
    }

    /**
     *
     * @param environment
     * @param argument
     * @return
     */
    @Override
    public CommandStatus execute(Environment environment, String argument) throws IOException {


        String[] staza = argument.split(" ");

        if (staza == null || staza.length != 2){
            environment.writeln("Wrong number of arguments");
            return CommandStatus.CONTINUE;
        }

        Path srcFilePath = Paths.get(staza[0]);
        Path destFilePath = Paths.get(staza[1]);

        if (! Files.isDirectory(srcFilePath) || Files.isRegularFile(destFilePath)) {
            environment.writeln("1st and 2nd argument are not appropriate!");
            return CommandStatus.CONTINUE;
        }

        // dest directory is directory
        if ( Files.isDirectory(destFilePath)) {
            String newPath = destFilePath.toString()+"/"+srcFilePath.getFileName();
            destFilePath = Paths.get(newPath);

            new File(destFilePath.toString()).mkdir();
            recursiveWalk(srcFilePath.toString(), destFilePath.toString(), environment);
        }

        // dest parent directory is directory
        else {
            //create newDirectory with name of file!
            new File(destFilePath.toString()).mkdir();
            recursiveWalk(srcFilePath.toString(), destFilePath.toString(), environment);
        }
        return CommandStatus.CONTINUE;
    }


    public void recursiveWalk ( String srcPathString , String destPathString, Environment environment) {

        File root = new File ( srcPathString );
        File end = new File ( destPathString );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            if (f.getName().equals(".DS_Store")) {
                continue;
            }
            if ( f.isDirectory() ) {
                String[] srcArray = f.toString().split(root.getName());         //split whole root to get trimmed srcPath (just last directory)
                String trimSrc = srcArray[srcArray.length-1];
                new File(destPathString+trimSrc).mkdir();
                recursiveWalk(f.getAbsolutePath(), destPathString+trimSrc, environment);
            }
            else if (f.isFile()){              //f.isFile() so COPY it!!
                String argument = f.toString()+" "+destPathString;
                CopyCommand copyCommand = new CopyCommand();

                try {
                    copyCommand.execute(environment, argument);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException a) {
                    a.printStackTrace();
                }
            }
        }
    }
}