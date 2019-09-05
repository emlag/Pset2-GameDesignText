package View;

import Controller.Parser;
import Model.Commands.Command;
import Model.Commands.CommandWord;
import Model.Rooms.Room;
import acm.program.ConsoleProgram;

/**
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 */

public class GameGUI extends ConsoleProgram
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public GameGUI()
    {
        createRooms();
        parser = new Parser(this);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        /**
         * Enter the main command loop.  Here we repeatedly read commands and
         * execute them until the game is over.
         **/
                
        boolean finished = false;
        while (! finished) {
            println("> ");     // print prompt
            String inputLine = readLine();
            Command command = parser.getCommand(inputLine);
            finished = processCommand(command);
        }
        println("Thank you for playing.  Good bye.");
    }
    
    @Override
    public void run()
    {
        printWelcome();
        play();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        println("");
        println("Welcome to the World of Zuul!");
        println("World of Zuul is a new, incredibly boring adventure game.");
        println("Type '" + CommandWord.HELP + "' if you need help.");
        println("");
        println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        println("You are lost. You are alone. You wander");
        println("around at the university.");
        println();
        println("Your command words are:");
        
        for(String command : parser.getCommands())
        {
            print(command + "  ");
        }
        println("");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null)
        {
            System.out.println("There is no door!");
        }
        else
        {
            currentRoom = nextRoom;
            println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord())
        {
            println("Quit what?");
            return false;
        }
        else
        {
            return true;  // signal that we want to quit
        }
    }
}
