import java.util.Stack;
import java.util.ArrayList;
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
    private ArrayList<Item> inventario;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room inicio)
    {
        currentRoom = inicio;
        rooms = new Stack<>();
        inventario = new ArrayList<>();
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
    
        public void take(Command comando) {
        if(!comando.hasSecondWord()) {
            System.out.println("coger que?");
        }
        else {
            if (currentRoom.getItems().size() != 0) {
                boolean itemCogido = false;
                int contador = 0;
                ArrayList<Item> itemsRoom = currentRoom.getItems();
                while (contador < itemsRoom.size() && !itemCogido) {
                    if (itemsRoom.get(contador).getItemId().equals(comando.getSecondWord()) 
                        && itemsRoom.get(contador).getItemPickUp()) {
                        inventario.add(itemsRoom.get(contador));
                        itemCogido = true;
                        System.out.println(itemsRoom.get(contador).getItemId() + " ha sido"
                            + " añadido a tu inventario");
                        currentRoom.deleteItems(comando.getSecondWord());
                    }
                    else if (itemsRoom.get(contador).getItemId().equals(comando.getSecondWord()) 
                        && !itemsRoom.get(contador).getItemPickUp()) {
                        System.out.println("No puedes recoger ese objeto");
                        itemCogido = true;
                    }
                    contador++;
                }
                if (!itemCogido) {
                    System.out.println("No hay ningun objeto con ese nombre");
                }
            }
            else {
                System.out.println("Esta sala no contiene objetos");
            }
        }
    }
}
