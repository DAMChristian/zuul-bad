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
    private int maxWeight;
    private int itemsPeso;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room inicio, int maxWeight)
    {
        currentRoom = inicio;
        rooms = new Stack<>();
        inventario = new ArrayList<>();
        this.maxWeight = maxWeight;
        itemsPeso = 0;
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
                String itemStatus = "No hay ningun objeto con ese nombre";
                ArrayList<Item> itemsRoom = currentRoom.getItems();
                while (contador < itemsRoom.size() && !itemCogido) {
                    if (itemsRoom.get(contador).getItemId().equals(comando.getSecondWord())) {
                        if (itemsRoom.get(contador).getItemPickUp()) {
                            if (itemsPeso + itemsRoom.get(contador).getItemWeight() <= maxWeight) {
                                inventario.add(itemsRoom.get(contador));
                                itemCogido = true;
                                itemsPeso += itemsRoom.get(contador).getItemWeight();
                                itemStatus = itemsRoom.get(contador).getItemId() + " ha sido"
                                    + " añadido a tu inventario";
                                currentRoom.deleteItems(comando.getSecondWord());
                            }
                            else {
                                itemStatus = "No puedes llevar tanto peso";
                            }
                        }
                        else {
                            itemStatus = "No puedes recoger ese objeto";  
                        }
                    }

                    contador++;
                }
                
                System.out.println(itemStatus);
            }
            else {
                System.out.println("Esta sala no contiene objetos");
            }
        }
    }

    public void items() {
        if (inventario.size() == 0) {
            System.out.println("No llevas ningun objeto");
        }
        else {
            int pesoTotal = 0;
            for (Item item : inventario) {
                System.out.println(item.description());
                pesoTotal += item.getItemWeight();
            }
            System.out.println("El peso total de los items que llevas es de: " + pesoTotal);
        }
    }

    public void drop(Command comando) {
        if(!comando.hasSecondWord()) {
            System.out.println("Tirar el que?");
        }
        else {
            boolean itemTirado = false;
            int contador = 0;
            while (!itemTirado && contador < inventario.size()) {
                if (inventario.get(contador).getItemId().equals(comando.getSecondWord())) {
                    currentRoom.addItem(inventario.get(contador).getItemDescription()
                    , inventario.get(contador).getItemWeight()
                    , inventario.get(contador).getItemId()
                    , inventario.get(contador).getItemPickUp());
                    itemsPeso -= inventario.get(contador).getItemWeight();
                    System.out.println(inventario.get(contador).getItemId() + " ha sido "
                        + "quitado de tu inventario");
                    inventario.remove(contador);
                    itemTirado = true;
                }
                contador++;
            }
            if (!itemTirado) {
                System.out.println("No puedes tirar " + comando.getSecondWord() + " por"
                    + "que no lo tienes en tu inventario");
            }
        }
    }

    public void read(Command comando) {
        if(!comando.hasSecondWord()) {
            System.out.println("leer el que?");
        }
        else {
            boolean itemLeido = false;
            int contador = 0;
            while (!itemLeido && contador < inventario.size()) {
                if (inventario.get(contador).getItemId().equals(comando.getSecondWord())
                && inventario.get(contador).getItemId().equals("hechizo")) {
                    itemLeido = true;
                    itemsPeso -= inventario.get(contador).getItemWeight();
                    inventario.remove(contador);
                    maxWeight += 4;
                    System.out.println("Tu peso se ha aumentado en 4, tu peso total es de: "
                        + maxWeight);
                }
                contador++;
            }
            if (!itemLeido) {
                System.out.println("No puedes leer " + comando.getSecondWord() + " por"
                    + "que no es un libro de hechizos");
            }
        }
    }
}
