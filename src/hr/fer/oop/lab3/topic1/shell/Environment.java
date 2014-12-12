package hr.fer.oop.lab3.topic1.shell;

import java.io.IOException;

/**
 * Created by borna on 07/12/14.
 */
public interface Environment {
    public String readLine() throws IOException;
    public void write(String s) throws IOException;
    public void writeln(String s) throws IOException;
    public Terminal getActiveTerminal();
    public void setActiveTerminal (Terminal t);
    public Terminal getOrCreateTerminal (int broj);
    public Terminal[] listTerminals();
    public Iterable<ShellCommand> commands();
}
