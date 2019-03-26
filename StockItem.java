
import java.util.Iterator;
import data_structures.*;

public class StockItem implements Comparable<StockItem> {

    String SKU;
    String description;
    String vendor;
    float cost;
    float retail;

    // Constructor.  Creates a new StockItem instance.  
    // Follows the specifications of the Comparable Interface.
    // The SKU is always used for comparisons, in dictionary order.  
    public StockItem(String SKU, String description, String vendor, 
            float cost, float retail) {
        this.SKU = SKU;
        this.description = description;
        this.vendor = vendor;
        this.cost = cost;
        this.retail = retail;
    }
    
    // Returns an int representing the hashCode of the SKU.
    public int compareTo(StockItem n) {
        if (n == null) {
            return 1;
        }
        return this.hashCode() - n.hashCode();
    }

    // standard get methods
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public float getCost() {
        return cost;
    }
        
    
    
    public float getRetail(){
        return retail;
        
    }

    // All fields in one line, in order   
    public String toString() {
        return SKU + ", " + description + ", " 
                + vendor + ", " + cost + ", " + retail;
    }
}
