package hr.fer.oop.lab3.topic1.shell;

import hr.fer.oop.lab3.topic1.SimpleHashTable;
import hr.fer.oop.lab3.topic1.commands.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by borna on 07/12/14.
 */
public class MyShell {

    private static SimpleHashTable commands;

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

        public void write (String string) throws IOException {
            writer.write(string);
            writer.flush();
        }

        public void writeln(String string) throws IOException {
//            writer.write(string+"\n");
//            writer.flush();
            this.write(string+"\n");

        }

        public Terminal getActiveTerminal () {
            return activeTerminal;
        }

        @Override
        public void setActiveTerminal(Terminal terminal) {
            this.activeTerminal = terminal;
        }

        /**
         *
         * @param number
         * if Terminal exist, return value from SimplehashTable.TableEntry which is reference to itself
         * else if terminal does not exist, create new Terminal, put given number as key, and reference to itself as value
         * @return Terminal
         */

        public Terminal getOrCreateTerminal (int number) {

            if (terminals.containsKey(number)){
                    return (Terminal) terminals.get(number);
            }

            else {
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

        public Iterable commands(){         //moze ovako ali bez genericsa ide. Vraca iterable koji generiraju TableEntryije, a commands mora vratiti Iterable objekt koji vracaju objekte ShellCommand
            return commands;
        }

    }

    private static Environment environment = new EnvironmentImpl();

    public static void main(String[] args) throws IOException{


        environment.writeln("Welcome to MyShell! You may enter commands.");
        CommandStatus status = CommandStatus.CONTINUE;
        ShellCommand shellCommand;

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
            arg = arg.substring(0,arg.length()-1);

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
                status = shellCommand.execute(environment, arg);
            }
        }

        environment.writeln("Thank you for using this shell. Goodbye!");
    }
    public static String getPromt(){
        String string = Integer.toString(environment.getActiveTerminal().getId())+"$"+ environment.getActiveTerminal().getCurrentPath().toString()+" ";
        return string;
    }
}