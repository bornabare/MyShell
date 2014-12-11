package hr.fer.oop.lab3.topic1.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;

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

        if (! Files.isDirectory(srcFilePath)) {
            environment.writeln("1st argument is not directory!");
            return CommandStatus.CONTINUE;
        }

        if (! Files.isDirectory(destFilePath)) {

            if (! Files.isDirectory(destFilePath.getParent())) {
                environment.writeln("2nd argument, or its parent directory is not directory!");
                return CommandStatus.CONTINUE;
            }
            // parent directory is directory
            else {

            }
        }

        //directory is directory
        else {
        
        }

        //copy directory




        return CommandStatus.CONTINUE;
    }

    public void walk ( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
                System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
                System.out.println( "File:" + f.getAbsoluteFile() );
            }
        }
    }
}
