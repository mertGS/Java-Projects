
import data_structures.*;
import java.util.Iterator;

public class ProductLookup {
    
    private DictionaryADT<String, StockItem> data;
    private int maxSize;
    private int size;
    
    // Constructor.  There is no argument-less constructor, or default size.
    // The user must specify the maximum size of the structure.
    public ProductLookup(int maxSize) { 
        data = new Hashtable<>(maxSize);
        this.maxSize = maxSize;
        size = 0;
    }

    
    // Adds a new StockItem to the dictionary
    public void addItem(String SKU, StockItem item) {
        if (size < maxSize &&
                data.insert(SKU, item) == true) {
            size++;
        }
    }

    // Returns the StockItem associated with the given SKU, if it is
    // in the ProductLookup, null if it is not.    
    public StockItem getItem(String SKU) {
        return data.getValue(SKU);
    }
    
    // Returns the retail price associated with the given SKU value.
    // -.01 if the item is not in the dictionary    
    public float getRetail(String SKU) {
        StockItem item = getItem(SKU);
        if (item == null) {
            return (float) (-0.01);
        }
        return item.getRetail();
    }
    
    // Returns the cost price associated with the given SKU value.
    // -.01 if the item is not in the dictionary
    public float getCost(String SKU) {
        StockItem item = getItem(SKU);
        if (item == null) {
            return (float) (-0.01);
        }
        return item.getCost();
    }
    // Returns the description of the item, null if not in the dictionary.
    public String getDescription(String SKU) {
        StockItem item = getItem(SKU);
        if (item == null) {
            return null;
        }
        return item.getDescription();
    }

    // Deletes the StockItem associated with the SKU if it is
    // in the ProductLookup.  Returns true if it was found and
    // deleted, otherwise false.      
    public boolean deleteItem(String SKU) {
        if (data.remove(SKU) == true) {
            size--;
            return true;
        }
        return false;
    }

    // Prints a directory of all StockItems with their associated
    // price, in sorted order (ordered by SKU).    
    public void printAll() {
//        Iterator<StockItem> it = data.values();
        for (Iterator iterator = data.values(); iterator.hasNext();) {
            StockItem next = (StockItem) iterator.next();
            System.out.println(next.toString());
        }
    }
    
    // Prints a directory of all StockItems from the given vendor, 
    // in sorted order (ordered by SKU).
    public void print(String vendor) {
        Iterator<StockItem> it = data.values();
        for (Iterator iterator = data.values(); iterator.hasNext();) {
            StockItem next = (StockItem) iterator.next();
            if (next.getVendor().equals(vendor)) {
                System.out.println(next.toString());
            }            
        }
    }

    // An iterator of the SKU keys.    
    public Iterator<String> keys() {
        return data.keys();
    }

    // An iterator of the StockItem values.    
    public Iterator<StockItem> values(){
        return data.values();
    }
}
