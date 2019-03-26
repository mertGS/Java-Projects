
package data_structures;

import java.util.Iterator;

class BinaryNode<K extends Comparable<K>, V>{
    K key;
    V value;
    BinaryNode<K, V> left;
    BinaryNode<K, V> right;
    BinaryNode<K, V> parent;

    public BinaryNode(K key, V value, BinaryNode parent) {
        this.key = key;
        this.value = value;
        left = null;
        right = null;
        this.parent = parent;
    }
    
}


public class BinarySearchTree<K extends Comparable<K>, V> 
        implements DictionaryADT<K, V> {
    
    private BinaryNode root;
    private int size;

    private LinkedList<Data<K, V>> datas;
    private LinkedList<K> keys;
    private LinkedListNoComparable<V> values;
    
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;        
        datas = new LinkedList<>();
        keys = new LinkedList<>();
        values = new LinkedListNoComparable<>();
    }   
    
    private BinaryNode findByKey (K key){
        BinaryNode tmp = root;        
        while (tmp != null) {            
            if (tmp.key.compareTo(key) == 0) {
                return tmp;
            } else if (tmp.key.compareTo(key) < 0) {
                tmp = tmp.right;
            } else {
                tmp = tmp.left;
            }
        }
        return null;
    }
    
    // Returns true if the dictionary has an object identified by
    // key in it, otherwise false.
    @Override
    public boolean contains(K key) {
       return findByKey(key) != null;
    }

    // Adds the given key/value pair to the dictionary.  Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    @Override
    public boolean insert(K key, V value) {       
        BinaryNode tmp = root;
        
        if (root == null) {
            root = new BinaryNode(key, value, null);
        } else {
            while (tmp != null) {                
                if (tmp.key.compareTo(key) == 0) {
                    return false;
                }
                if (tmp.key.compareTo(key) < 0) {
                    if (tmp.right == null) {
                        tmp.right = new BinaryNode(key, value, tmp);
                        size++;
                        return true;
                    } else {
                        tmp = tmp.right;
                    }
                } else {
                    if (tmp.left == null) {
                        tmp.left = new BinaryNode(key, value, tmp);
                        size++;
                        return true;
                    } else {
                        tmp = tmp.left;
                    }
                }
            }
        }        
        return true;
    }

    
    private void splice(BinaryNode u){
        BinaryNode s, p;
        if (u.left != null) {
            s = u.left;
        } else {
            s = u.right;
        }
        
        if (u == root) {
            root = s;
            p = null;
        } else{
            p = u.parent;
            if (p.left == u) {
                p.left = s;
            } else {
                p.right = s;
            }
        }
        if (s != null) {
            s.parent = p;
        }
        size--;
    }
    
    private void remove(BinaryNode u){
        if (u.left == null || u.right == null) {
            splice(u);
        } else {
            BinaryNode w = u.right;
            while (w.left != null) {
                w = w.left;                
            }
            u.key = w.key;
            u.value = w.value;
            splice(w);            
        }
    }
    
    // Deletes the key/value pair identified by the key parameter.
    // Returns true if the key/value pair was found and removed,
    // otherwise false.
    @Override
    public boolean remove(K key) {
        BinaryNode tmp = findByKey(key);
        if (tmp == null) {
            return false;
        }
        remove(tmp);        
        return true;
    }

    // Returns the value associated with the parameter key.  Returns
    // null if the key is not found or the dictionary is empty.
    @Override
    public V getValue(K key) {
         BinaryNode tmp = findByKey(key);
         if (tmp == null) {
            return null;
        }
        return (V) tmp.value;
    }

    
    private K getKey(BinaryNode n, V value){
        if (n.value.equals(value)) {
            return (K) n.key;
        }        
        K key = null;
        
        BinaryNode child = n.left;
        if (child != null) {
            key = getKey(child, value);
        }
        if (key != null) {
            return key;
        }
        child = n.right;
        if (child != null) {
            key = getKey(child, value);
        }        
        return key;
    }
    
    
    // Returns the key associated with the parameter value.  Returns
    // null if the value is not found in the dictionary.  If more
    // than one key exists that matches the given value, returns the
    // first one found.
    @Override
    public K getKey(V value) {        
       return getKey(root, value);
    }

    @Override
    public int size() {
       return size;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    private void clear(BinaryNode n){
        if (n != null) {
            clear(n.left);
            n.left = null;
            clear(n.right);
            n.right = null;
        }
    }
    
    
    @Override
    public void clear() {
        clear(root);
        root = null;
        size = 0;        
    }

    private void preOrder(BinaryNode n){
        if (n != null) {
            preOrder(n.left);
            datas.addLast(new Data<>((K)n.key, (V)n.value));            
            preOrder(n.right);
        }
    }
    
    private void generateDataList() {
        datas.clear();
        preOrder(root);        
    }
    
    // Returns an Iterator of the keys in the dictionary, in ascending
    // sorted order.  The iterator must be fail-fast.
    @Override
    public Iterator<K> keys() {
        keys.clear();
        Iterator<Data<K, V> > it = datas.iterator();
        while (it.hasNext()) {
            Data<K, V> next = it.next();
            keys.addLast(next.key);
        }        
        
        return keys.iterator();
    }
    
    // Returns an Iterator of the values in the dictionary.  The
    // order of the values must match the order of the keys.
    // The iterator must be fail-fast.
    @Override
    public Iterator<V> values() {
        generateDataList();
        values.clear();
        Iterator<Data<K, V> > it = datas.iterator();
        while (it.hasNext()) {
            Data<K, V> next = it.next();
            values.addLast(next.value);
        }        
        
        return values.iterator();
    }
    
}
