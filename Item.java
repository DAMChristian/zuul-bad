
/**
 * Esta clase es la encargada de crear los items de las salas
 */
public class Item
{
    private String itemDescription;
    private int itemWeight;
    private String itemId;
    
    /**
     * Nos crea un item
     */
    public Item( String itemDescription, int itemWeight, String itemId)
    {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
        this.itemId = itemId;
    }

    /**
     * Nos genera la descripcion del item y su peso
     */
    public String description()
    {
        return itemDescription + " peso " 
                       + itemWeight + " su id es " + itemId;
    }
    
    public String getItemDescription() {
        return itemDescription;
    }
    
    public int getItemWeight() {
        return itemWeight;
    }
    
    public String getItemId() {
        return itemId;
    }
}
