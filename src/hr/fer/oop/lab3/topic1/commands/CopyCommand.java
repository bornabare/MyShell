package hr.fer.oop.lab3.topic1.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This is command that copies file given by first command line argument to destination given either
 * by the name of still unexistent destination folder that will be created (so parent has to exist),
 * or by the existing directory - in that case name of the file remains exact like in src.
 *
 * Created by borna on 09/12/14.
 *
 * @version 1.0
 * @author borna
 */

public class CopyCommand extends AbstractCommand{

    public CopyCommand() {
        super("copy", "Command that copies first file to second arg directory");
    }

    /**
     *
     * @param environment
     * @param argument
     * @return CommandStatus.CONTINUE
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


        //srcFile is not file! It has to be file in order to be copied
        if (Files.isDirectory(srcFilePath)) {
            environment.writeln("Wrong type of arguments");
            return CommandStatus.CONTINUE;
        };

        File file = new File(staza[0]);
        File directory = new File(staza[1]);

        if (! file.isFile()){
            throw new IllegalArgumentException("Please provide file and directory!");
        }

        BufferedReader br = new BufferedReader (new FileReader (file) );
        List <String>list = new ArrayList<String>();

        try {
            String line = br.readLine();

            while (line != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(line);
                sb.append(System.lineSeparator());
                list.add(sb.toString());
                line = br.readLine();
            }

        } catch (IOException e) {
            e.getMessage();
        } finally {
            br.close();
        }

        // exploiting list to another file
        OutputStream outputStream = null;

        if (directory.isDirectory()) {          // file name will be the same!!
            String[] fileRegex = file.toString().split("/");
            String fileName = fileRegex[fileRegex.length-1];

            outputStream = new FileOutputStream (directory+"/"+fileName);     //creates file in directory with the name of original file
        }
        else {      //directory is file
        try {
            outputStream = new FileOutputStream(directory);              //creates file with the name of given argument for destination since it is file name
        }   catch (FileNotFoundException e) {
                e.getMessage();
            }
        }

        for (int i=0 ; i < list.size(); i++){
            outputStream.write(list.get(i).getBytes());
        }
        environment.writeln("Copying successful!");
        return CommandStatus.CONTINUE;
    }
}