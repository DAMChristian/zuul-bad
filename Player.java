import java.util.Stack;
/**
 * Almacena todos los datos del jugador.
 * 
 * @author Me 
 * @version 0.1
 */
public class Player
{

    private Room currentRoom;
    private Stack<Room> rooms;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room inicio)
    {
        currentRoom = inicio;
        rooms = new Stack<>();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            rooms.push(currentRoom);
            currentRoom = nextRoom;
            look();
        }
    }

    /**
     * Imprimir por pantalla donde esta nuestro personaje
     */
    public void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    public void eat() {
        System.out.println("acabas de comer y ya no tienes hambre");
    }

    public void back() {
        if (!rooms.isEmpty()) {
            currentRoom = rooms.pop(); 
            look();
        }
        else {
            System.out.println("No puedes retroceder mas.");
        }

    }
}
