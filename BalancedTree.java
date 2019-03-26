
package data_structures;
import java.util.*;

public class BalancedTree<K extends Comparable<K>, V> 
        implements DictionaryADT<K, V> {
    
    private TreeMap tree;

    public BalancedTree() {
        tree = new TreeMap();
    }
    
    
        
   // Returns true if the dictionary has an object identified by
    // key in it, otherwise false.
    @Override
    public boolean contains(K key) {
        return tree.containsKey(key);
    }

    // Adds the given key/value pair to the dictionary.  Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    @Override
    public boolean insert(K key, V value) {
        if (tree.containsKey(key)) {
            return false;
        }
        tree.put(key, value);
        return true;
    }

    // Deletes the key/value pair identified by the key parameter.
    // Returns true if the key/value pair was found and removed,
    // otherwise false.
    @Override
    public boolean remove(K key) {
       return tree.remove(key) != null;
    }

    // Returns the value associated with the parameter key.  Returns
    // null if the key is not found or the dictionary is empty.
    @Override
    public V getValue(K key) {
        Set set = tree.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            if (mentry.getKey().equals(key)) {
                return (V) mentry.getValue();
            }
        }
        return null;
    }

    // Returns the key associated with the parameter value.  Returns
    // null if the value is not found in the dictionary.  If more
    // than one key exists that matches the given value, returns the
    // first one found.
    @Override
    public K getKey(V value) {        
        Set set = tree.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            if (mentry.getValue().equals(value)) {
                return (K) mentry.getKey();
            }
        }
        return null;
    }

    @Override
    public int size() {
       return tree.size();
    }

    @Override
    public boolean isFull() {        
        return false;
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public void clear() {
        tree.clear();
    }

    // Returns an Iterator of the keys in the dictionary, in ascending
    // sorted order.  The iterator must be fail-fast.
    @Override
    public Iterator<K> keys() {
        return tree.keySet().iterator();
    }
    
    // Returns an Iterator of the values in the dictionary.  The
    // order of the values must match the order of the keys.
    // The iterator must be fail-fast.
    @Override
    public Iterator<V> values() {
        return tree.values().iterator();
    }
    
}
