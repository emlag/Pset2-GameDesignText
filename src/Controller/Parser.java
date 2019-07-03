package Controller;

import Model.Commands.Command;
import Model.Commands.CommandWords;
import Model.Commands.CommandWord;
import acm.program.ConsoleProgram;
import java.util.Set;

import java.util.Scanner;

/**
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two-word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 *
 */
public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input
    private ConsoleProgram gameWindow;    //the window shown by the GUI

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser(ConsoleProgram prog) 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
        gameWindow = prog;
    }


    public Command getCommand(String inputLine)
    {
        String word1 = null;
        String word2 = null;
   
        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext())
        {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext())
            {
                word2 = tokenizer.next();      // get second word
                // note: we just ignore the rest of the input line.
            }
        }
        
        /**
         * First figure out what the use wants to do, then where.
         * and example would be "go east", the userRequest is to "go" and 
         * the second word is where to "go" to.
         * 
         * fullCommand is a Command object that holds a commandWord and its
         * corresponding direction.
         */
        CommandWord userRequest = commands.getCommandWord(word1);
        Command fullCommand = new Command(userRequest, word2); 
        return fullCommand;
    }

    /**
     * Print out a list of valid command words.
     */
    public Set<String> getCommands()
    {
        return commands.getAll();
    }
}
