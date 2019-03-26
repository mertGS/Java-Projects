package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

class Node<E> {

    E data;
    Node<E> next;
    Node<E> prev;

    public Node(E obj) {
        data = obj;
        next = null;
        prev = null;
    }
}

/**
 *
 * @author mert
 * @param <E>
 */
public class LinkedList<E extends Comparable<E>> implements UnorderedListADT<E> {

    protected Node<E> head;
    protected Node<E> tail;
    private int size;

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    //  Adds the Object obj to the list in first position.
    @Override
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
    @Override
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
    @Override
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
    @Override
    public E remove(int position) {
        if (position < 1 || position > size) {
            //System.err.println("size = " + size);
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
    @Override
    public E remove(E obj) {       
        
        Node<E> tmp = head;
        if (head == null) {           
            return null;
        }
        if (head.data.equals(obj)) {
            return removeFirst();
        }         
        while (tmp != null) {            
            if (obj.equals(tmp.data)) {
                E result = tmp.data;
                
                if (tmp == tail) {
                    tail = tmp.prev;
                    tail.next = null;
                } else{
                    tmp.prev.next = tmp.next;
                    tmp.next.prev = tmp.prev;
                }
                size--;
                return result;
            }
            tmp = tmp.next;
        }
        return null;
    }

    //  Removes and returns the first element in the list and null if the it is empty.
    @Override
    public E removeFirst() {
        if (head == null) {           
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
       // tmp = null;
        return data;
    }

    //  Removes and returns the last element in the list and null if the it is empty.
    @Override
    public E removeLast() {
        if (head == null) {
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
    @Override
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
    @Override
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
    @Override
    public int find(E obj) {
        if (head == null) {
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
    @Override
    public boolean contains(E obj) {
        return find(obj) >= 1;
    }

    //  The list is returned to an empty state.
    @Override
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
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    //  Returns true if the list is full, otherwise false.  
    @Override
    public boolean isFull() {
        return false;
    }

    //  Returns the number of Objects currently in the list.
    @Override
    public int size() {
        return size;
    }

    //  Returns an Iterator of the values in the list, presented in
    //  the same order as the list.  The list iterator MUST be
    //  fail-fast.
    @Override
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
