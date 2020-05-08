
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player jugador;


    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        jugador = new Player(createRooms());
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room inicio, salaPrincipal, salaRituales, almacen, 
        salaOfrendas, salaDioses, tumba, salaEsclavos,
        salaRiquezas;

        // create the rooms
        inicio = new Room("La entrada a la tumba");
        salaPrincipal = new Room("La sala principal de la tumba");
        salaRituales = new Room("La Zona de rituales de la tumba");
        almacen = new Room("El Almacen donde se guardan bienes para la proxima vida");
        salaOfrendas = new Room("La sala de las ofrendas que otorganban los vasayos a su cargo");
        salaDioses = new Room("La sala del Culto hacia los dioses");
        tumba = new Room("La sala donde descansa el faraon");
        salaEsclavos = new Room("La sala de los esclavos");
        salaRiquezas = new Room("La sala de las riquezas del faraon");

        // initialise room exits
        //arriba, derecha, abajo, izquierda, abajo-derecha        
        inicio.setExit("north", salaPrincipal);
        salaPrincipal.setExit("east", salaRituales);
        salaPrincipal.setExit("south", inicio);
        salaPrincipal.setExit("west", salaOfrendas);
        salaRituales.setExit("north", almacen);
        salaRituales.setExit("west", salaPrincipal);
        almacen.setExit("south", salaRituales);
        almacen.setExit("norEste", salaRiquezas);
        salaOfrendas.setExit("north", salaDioses);
        salaOfrendas.setExit("east", salaPrincipal);
        salaOfrendas.setExit("surOeste", salaEsclavos);
        salaDioses.setExit("north", tumba);
        salaDioses.setExit("south", salaOfrendas);
        salaDioses.setExit("surEste", salaPrincipal);
        tumba.setExit("south", salaDioses);
        salaEsclavos.setExit("norEste", salaOfrendas);
        salaRiquezas.setExit("surOeste", almacen);

        //Añadir objetos
        tumba.addItem("Mascara del faraon", 4, "mascara", true);
        tumba.addItem("Cetro del faraon", 5, "cetro", true);
        salaDioses.addItem("Pergamino", 3, "pergamino", false);
        salaRiquezas.addItem("Lingotes de Oro", 6, "oro", true);
        salaEsclavos.addItem("Anillo esmeralda", 2, "anillo", true);
        almacen.addItem("Llave desconocida", 1, "llave", true);

        return inicio;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        jugador.look();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            jugador.goRoom(command);
        }
        else if (commandWord.equals("look")) {
            jugador.look();
        }
        else if (commandWord.equals("eat")) {
            jugador.eat();
        }
        else if (commandWord.equals("back")) {
            jugador.back();
        }
        else if (commandWord.equals("take")) {
            jugador.take(command);
        }
        else if (commandWord.equals("items")) {
            jugador.items();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
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
        System.out.println("Estas perdido. Estas solo. Estas vagando");
        System.out.println("por la tumba del faraon.");
        System.out.println();
        System.out.println("Tus comandos son:");
        System.out.println(parser.showCommands());
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }


    
}

