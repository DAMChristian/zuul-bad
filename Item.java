
/**
 * Esta clase es la encargada de crear los items de las salas
 */
public class Item
{
    private String itemDescription;
    private int itemWeight;
    
    /**
     * Nos crea un item
     */
    public Item( String itemDescription, int itemWeight)
    {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
    }

    /**
     * Nos genera la descripcion del item y su peso
     */
    public String description()
    {
        return "Esta sala contiene: " + itemDescription + " y su peso es " 
                       + itemWeight;
    }

}
