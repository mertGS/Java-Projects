package data_structures;

import java.util.Iterator;

class Data<K extends Comparable<K>, V> implements Comparable<Data<K, V>> {

    K key;
    V value;

    public Data(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(Data o) {
        if (o == null) {
            return 1;
        }
        if (key == null) {
            if (o.key == null) {
                return 0;
            }
            return -1;
        }
        return key.compareTo((K) o.key);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != Data.class) {
            return false;
        }
        Data d = (Data) o;
        if (key == null) {
            if (d.key == null) {
                return true;
            }
            return false;
        }
        return key.equals(d.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + " " + value;
    }

}

class DataList<E extends Comparable<E>> extends LinkedList<E> {

    public boolean addUnique(E data) {
        if (contains(data) == true) {
            return false;
        }

        Node<E> current = this.head;

        if (current == null || current.data.compareTo(data) >= 0) {
            this.addFirst(data);
        } else if (this.tail.data.compareTo(data) <= 0) {
            this.addLast(data);
        } else {
            while (current.next != null) {
                if (current.data.compareTo(data) >= 0) {
                    break;
                }
                current = current.next;
            }
            Node<E> n = new Node(data);
            n.next = current;
            n.prev = current.prev;
            current.prev.next = n;
            current.prev = n;
        }
        return true;
    }

    public Data getKey(E data) {
        Node<E> current = this.head;
        Data source = (Data) data;
        while (current != null) {
            Data tmp = (Data) current.data;
            if (source.value.equals(tmp.value)) {
                return (Data) current.data;
            }
            current = current.next;
        }
        return null;
    }

}

class HashArray<E extends Comparable<E>> {

    private DataList<E>[] array;

    public HashArray(int hashSize) {
        array = new DataList[hashSize];
        for (int i = 0; i < array.length; i++) {
            array[i] = new DataList<>();
        }
    }

    private int getIndex(E obj) {
        return Math.abs(obj.hashCode()) % array.length;
    }

    public boolean contains(E obj) {
        int index = getIndex(obj);
        DataList<E> list = array[index];
        return list.contains(obj);
    }

    public boolean insert(E obj) {
        int index = getIndex(obj);
        DataList<E> list = array[index];
        if (list.addUnique(obj)) {
            return true;
        }

        return false;
    }

    public boolean remove(E obj) {
        int index = getIndex(obj);
        DataList<E> list = array[index];
        return list.remove(obj) != null;
    }

    public E getValue(E obj) {
        int index = getIndex(obj);
        DataList<E> list = array[index];
        return list.get(obj);
    }

    public E getKey(E obj) {
        Data d = null;
        for (int i = 0; i < array.length; i++) {
            DataList<E> dataList = array[i];
            d = dataList.getKey(obj);
            if (d != null) {
                return (E) d;
            }
        }
        return null;
    }

    public void clear() {
        for (int i = 0; i < array.length; i++) {
            DataList<E> dataList = array[i];
            dataList.clear();
        }
    }

    public DataList<E>[] getArray() {
        return array;
    }
}

public class Hashtable<K extends Comparable<K>, V>
        implements DictionaryADT<K, V> {

    private final int HASH_SIZE = 1024;
    private int size;
    private int maxSize;
    private HashArray<Data<K, V>> table;
    private DataList<Data<K, V>> datas;
    private LinkedList<K> keys;
    private LinkedListNoComparable<V> values;

    public Hashtable(int maxSize) {
        size = 0;
        this.maxSize = maxSize;
        table = new HashArray<>(maxSize);
        datas = new DataList<>();
        keys = new DataList<>();
        values = new LinkedListNoComparable<>();
    }

    // Returns true if the dictionary has an object identified by
    // key in it, otherwise false.
    @Override
    public boolean contains(K key) {
        return table.contains(new Data<>(key, null));
    }

    // Adds the given key/value pair to the dictionary.  Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    @Override
    public boolean insert(K key, V value) {
        if (size < maxSize
                && table.insert(new Data<>(key, value)) == true) {
            size++;
            return true;
        }
        return false;
    }

    // Deletes the key/value pair identified by the key parameter.
    // Returns true if the key/value pair was found and removed,
    // otherwise false.
    @Override
    public boolean remove(K key) {
        if (table.remove(new Data<>(key, null)) == true) {
            size--;
            return true;
        }
        return false;
    }

    // Returns the value associated with the parameter key.  Returns
    // null if the key is not found or the dictionary is empty.
    @Override
    public V getValue(K key) {
        //System.out.println("key " + key);
        Data d = table.getValue(new Data<>(key, null));
        if (d == null) {
            return null;
        }
        // System.out.println(" val " + d.value);
        return (V) d.value;
    }

    // Returns the key associated with the parameter value.  Returns
    // null if the value is not found in the dictionary.  If more
    // than one key exists that matches the given value, returns the
    // first one found.
    @Override
    public K getKey(V value) {
        Data d = table.getKey(new Data<>(null, value));
        if (d == null) {
            return null;
        }
        return (K) d.key;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isFull() {
        return size >= maxSize;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public void clear() {
        table.clear();
        size = 0;
    }

    private void generateDataList() {
        datas.clear();
        DataList<Data<K, V>>[] array = table.getArray();
        for (int i = 0; i < array.length; i++) {
            DataList<Data<K, V>> dataList = array[i];
            Iterator<Data<K, V>> it = dataList.iterator();
            while (it.hasNext()) {
                Data<K, V> next = it.next();
                datas.addUnique(next);
            }
        }

    }

    // Returns an Iterator of the keys in the dictionary, in ascending
    // sorted order.  The iterator must be fail-fast.
    @Override
    public Iterator<K> keys() {
        generateDataList();
        keys.clear();
        Iterator<Data<K, V>> it = datas.iterator();
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
        Iterator<Data<K, V>> it = datas.iterator();
        while (it.hasNext()) {
            Data<K, V> next = it.next();
            values.addLast(next.value);
        }

        return values.iterator();
    }

}

class LinkedListNoComparable<E> {

    protected Node<E> head;
    protected Node<E> tail;
    private int size;

    public LinkedListNoComparable() {
        head = null;
        tail = null;
        size = 0;
    }

    //  Adds the Object obj to the list in first position.    
    public void addFirst(E obj) {
        Node<E> n = new Node<>(obj);
        n.next = head;
        if (head != null) {
            head.prev = n;
        }
        head = n;
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    //  Adds the Object obj to the end of the list.    
    public void addLast(E obj) {
        Node<E> n = new Node<>(obj);
        if (tail == null) {
            tail = n;
            head = n;
        } else {
            n.prev = tail;
            tail.next = n;
            tail = n;
        }
        size++;
    }

    //  Adds the Object obj to the list in the position indicated.  The list is one based, and
    //  the first element is at position #1 (not zero).  If the position is currently occupied
    //  existing elements must be shifted over to make room for the insertion.    
    public void add(E obj, int position) {
        Node<E> n = new Node<>(obj);
        if (position <= 1) {
            addFirst(obj);
        } else if (position > size) {
            addLast(obj);
        } else {
            int counter = 1;
            Node<E> tmp = head;
            while (counter < position) {
                tmp = tmp.next;
                counter++;
            }
            n.next = tmp.next;
            if (tmp.next != null) {
                tmp.next.prev = n;
            }
            tmp.next = n;
            n.prev = tmp;
            size++;
        }
    }

    //  Removes and returns the object located at the parameter position.
    //  Throws a RuntimeException if the position does not map to a valid position within the list.    
    public E remove(int position) {
        if (position < 1 || position > size) {
            throw new RuntimeException("Invalid position in remove method");
        }
        if (position == 1) {
            return removeFirst();
        } else if (position == size) {
            return removeLast();
        }

        int counter = 1;
        Node<E> tmp = head;
        while (counter < position) {
            tmp = tmp.next;
            counter++;
        }
        tmp.prev.next = tmp.next;
        tmp.next.prev = tmp.prev;
        tmp.next = null;
        tmp.prev = null;
        E data = tmp.data;
        tmp = null;
        size--;
        return data;
    }

    //  Removes and returns the parameter object obj from the list if the list contains it, 
    //  null otherwise.  If more than one element matches, the element is lowest position is removed
    //  and returned.
    public E remove(E obj) {
        int position = find(obj);
        if (position < 1) {
            return null;
        }
        return remove(position);
    }

    //  Removes and returns the first element in the list and null if the it is empty.
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<E> tmp = head;
        head = head.next;
        if (head == null) {
            tail = null;
            size = 0;
        } else {
            head.prev = null;
            size--;
        }
        E data = tmp.data;
        tmp = null;
        return data;
    }

    //  Removes and returns the last element in the list and null if the it is empty.
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node<E> tmp = tail;
        tail = tail.prev;
        if (tail == null) {
            size = 0;
            head = null;
        } else {
            size--;
            tail.next = null;
        }
        E data = tmp.data;
        tmp = null;
        return data;
    }

    //  Returns the object located at the parameter position.
    //  Throws a RuntimeException if the position does not map to a valid position within 
    //  the list.
    public E get(int position) {
        if (position < 1 || position > size) {
            throw new RuntimeException("Invalid position in get method");
        }
        int counter = 1;
        Node<E> tmp = head;
        while (counter < position) {
            tmp = tmp.next;
            counter++;
        }
        return tmp.data;
    }

    //  Returns the list object that matches the parameter, and null if the list is empty
    //  or if the element is not in the list.  If obj matches more than one element, 
    //  the element with the lowest position is returned.
    public E get(E obj) {
        Node<E> tmp = head;
        while (tmp != null) {
            if (obj.equals(tmp.data)) {
                return tmp.data;
            }
            tmp = tmp.next;
        }
        return null;
    }

    //  Returns the position of the first element that matches the parameter obj
    //  and -1 if the item is not in the list.
    public int find(E obj) {
        if (isEmpty()) {
            return -1;
        }
        int counter = 1;
        Node<E> tmp = head;
        while (tmp != null) {
            if (obj.equals(tmp.data)) {
                return counter;
            }
            counter++;
            tmp = tmp.next;
        }
        return -1;
    }

    //  Returns true if the parameter object obj is in the list, false otherwise.
    public boolean contains(E obj) {
        return find(obj) >= 1;
    }

    //  The list is returned to an empty state.
    public void clear() {
        size = 0;
        Node<E> tmp;
        while (head != null) {
            tmp = head;
            head = head.next;
            tmp = null;
        }
        tail = null;
    }

    //  Returns true if the list is empty, otherwise false
    public boolean isEmpty() {
        return size == 0;
    }

    //  Returns true if the list is full, otherwise false.  
    public boolean isFull() {
        return false;
    }

    //  Returns the number of Objects currently in the list.
    public int size() {
        return size;
    }

    //  Returns an Iterator of the values in the list, presented in
    //  the same order as the list.  The list iterator MUST be
    //  fail-fast.
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {
            // current index
            private Node<E> n = head;

            // return true when the next object is in the list
            @Override
            public boolean hasNext() {
                return n != null;
            }

            // get the next object in the list
            @Override
            public E next() {
                E data = n.data;
                n = n.next;
                return data;
            }

            @Override
            public void remove() {
                if (n != null) {
                    if (n == head) {
                        removeFirst();
                    } else if (n == tail) {
                        removeLast();
                    } else {
                        n.prev.next = n.next;
                        n.next.prev = n.prev;
                        n.next = null;
                        n.prev = null;
                        n = null;
                        size--;
                    }
                }
            }
        };

        return it;
    }
}
