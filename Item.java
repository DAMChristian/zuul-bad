
/**
 * Esta clase es la encargada de crear los items de las salas
 */
public class Item
{
    private String itemDescription;
    private int itemWeight;
    private String id;
    
    /**
     * Nos crea un item
     */
    public Item(String itemDescription, int itemWeight, String id)
    {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
        this.id = id;
    }

    /**
     * Nos genera la descripcion del item y su peso
     */
    public String description()
    {
        return itemDescription + " peso " 
                       + itemWeight + " su id: " + id;
    }
    
    public String getItemDescription()
    {
        return itemDescription;
    }
    
    public int getWeight()
    {
        return itemWeight;
    }
    
    public String getId()
    {
        return id;
    }

}
