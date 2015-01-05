package hr.fer.oop.lab3.topic1.shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by borna on 07/12/14.
 *
 * @version 1.0
 * @author borna
 */
public class Terminal {

    private int id;
    private Path currentPath;

    public Terminal (int id) {
        this.id = id;
        try {
            currentPath = Paths.get(new File(".").getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Terminal(){
        id = 1;
    }

    public int getId(){
        return this.id;
    }

    public Path getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath (Path path) {
        currentPath = path;

    }
}
