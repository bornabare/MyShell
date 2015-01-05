package hr.fer.oop.lab3.topic1.shell;

import hr.fer.oop.lab3.topic1.SimpleHashTable;
import hr.fer.oop.lab3.topic1.commands.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * Main class for running
 *
 * Created by borna on 07/12/14.
 *
 * @version 1.0
 * @author borna
 *
 */
public class MyShell {

    private static SimpleHashTable commands;
    private static Environment environment = new EnvironmentImpl();

    static {
        commands = new SimpleHashTable();
        ShellCommand[] cc = {
            new HelpCommand(),
            new QuitCommand(),
            new CdCommand(),
            new TerminalCommand(),
            new FilterCommand(),
            new TypeCommand(),
            new CopyCommand(),
            new XCopyCommand()
        };

        for (ShellCommand c : cc) {
            commands.put(c.getCommandName(), c);
        }

    }

    /**
     * This is class that is implementing the environment created in order to produce Shell
     * That includes needed terminals, operation with them, as well as commands.
     *
     * Terminals and commands are structured in SimpleHashTable, which gives them nice feature
     * to be get next elements easily
     */

    public static class EnvironmentImpl implements Environment {

        private SimpleHashTable terminals = new SimpleHashTable();
        private Terminal activeTerminal;
        private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));

        public EnvironmentImpl() {
            this.activeTerminal = getOrCreateTerminal(1);
        }

        public String readLine() throws IOException {
            return reader.readLine();
        }

        public void write(String string) throws IOException {
            writer.write(string);
            writer.flush();
        }

        public void writeln(String string) throws IOException {
//            writer.write(string+"\n");
//            writer.flush();
            this.write(string + "\n");

        }

        public Terminal getActiveTerminal() {
            return activeTerminal;
        }

        @Override
        public void setActiveTerminal(Terminal terminal) {
            this.activeTerminal = terminal;
        }

        /**
         * @param number if Terminal exist, return value from SimplehashTable.TableEntry which is reference to itself
         *               else if terminal does not exist, create new Terminal, put given number as key, and reference to itself as value
         * @return Terminal
         */

        public Terminal getOrCreateTerminal(int number) {

            if (terminals.containsKey(number)) {
                return (Terminal) terminals.get(number);
            } else {
                Terminal createdTerminal = new Terminal(number);
                terminals.put(number, createdTerminal);
                return createdTerminal;
            }
        }

        public Terminal[] listTerminals() {

            Terminal[] array = new Terminal[terminals.size()];
            int i = 0;

            for (Object terminalObject : terminals) {

                SimpleHashTable.TableEntry terminalTableEntry = (SimpleHashTable.TableEntry) terminalObject;
                array[i] = (Terminal) terminalTableEntry.getValue();
                i++;
            }
            return array;
        }

        /**
         *
         * @return
         */

        public Iterable<ShellCommand> commands() {
//            return commands;      // Iterable commands originalno

            return new Iterable<ShellCommand>() {

                @Override
                public Iterator<ShellCommand> iterator() {

                    return new Iterator<ShellCommand>() {

                        Iterator<SimpleHashTable.TableEntry> i = commands.iterator();

                        @Override
                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        @Override
                        public ShellCommand next() {
                            return (ShellCommand) i.next().getValue();
                        }
                    };
                }
            };
        }
    }

    public static void main(String[] args) throws IOException{

        environment.writeln("Welcome to MyShell! You may enter commands.");
        CommandStatus status = CommandStatus.CONTINUE;
        ShellCommand shellCommand;

        //this is loop that doesn't end until current operation returns CommandStatus.EXIT (only quit for now)
        while (status != CommandStatus.EXIT) {

            environment.write(getPromt()); //implement writing Term No., $ and current path
            String line = environment.readLine();
            String[] lineSplit = line.split(" ");
            String cmd = lineSplit[0];
            int argsLen = lineSplit.length;
            String arg = "";

            for (int i = 1; i < argsLen; i++){
                arg = arg.concat(lineSplit[i]).concat(" ");
            }
            try {
                arg = arg.substring(0,arg.length()-1);
            } catch (StringIndexOutOfBoundsException e) {
                e.getMessage();
            }

            try {
                shellCommand = (ShellCommand) commands.get(cmd);
            } catch (IllegalArgumentException e){
                environment.writeln(e.getMessage());
                continue;
            }


            if (shellCommand == null) {
                environment.writeln("Unknown command");
                continue;
            }
            else {
                status = shellCommand.execute(environment, arg);        //executing the given command
            }
        }

        environment.writeln("Thank you for using this shell. Goodbye!");
    }

    /**
     *
     * @return string that will currently be written on prompt (screen)
     */
    public static String getPromt(){
        String string = Integer.toString(environment.getActiveTerminal().getId())+"$"+ environment.getActiveTerminal().getCurrentPath().toString()+" ";
        return string;
    }
}