/*  LinkedListTester.java
    This file is designed to help you find errors in your linked list
    class.  Note that this driver doesn't guarantee that your class 
    has no errors, but attempts to find the most common problems.
    
    Please note that all methods in your LinkedList, Stack, and 
    Queue must work correctly, even if those methods aren't needed 
    for your project #2 assignment.
    
    Methods testFive() and testSix() have SIZE variables that you may 
    need to modify on a slower machine.  
    
    Note too, that this tester is designed specifically for your 
    LinkedList implementation, so the messages are geared to that
    purpose, and not really appropriate for the array-based implementation.
    That is, removeLast is O(n) for the linked list, but O(1) for the
    array.     

    Alan Riggins
    CS310 Summer 2017
*/    


import data_structures.*;

public class LinkedListChecker {
    private static final int ARRAY_SIZE = 100;
    private UnorderedListADT<String> list; 
    private String [] array;
    
    public LinkedListChecker() {
///////////////////////////////////////////////////////////////////////    
        //list = new ArrayList<Integer>(100000);
       
        list = new LinkedList<String>();
///////////////////////////////////////////////////////////////////////        
        array = new String[ARRAY_SIZE];
        testOne();
        testTwo();
        testThree();
        testFour();
        testFive();
        testSix();
        }
    // testing addFirst.        
    private void testOne() {
        try {
            list.clear();
            if(list.size() != 0)
                die("Error, wrong size in testOne()");            
            list.addFirst(""+1);
            if(list.size() != 1)
                die("Error, wrong size in testOne()");
            for(String s : list) 
                if(!s.equals("1"))
                    die("Error, iterator problem in testOne()");
            if(!list.removeLast().equals("1"))
                die("Error in testOne, did you forget to update the tail ptr in addFirst?");
            if(!(list.isEmpty() && list.size() == 0))
                die("Error in testOne, wrong size in list");
               
            for(int i=0; i < ARRAY_SIZE; i++) {
                array[i] = ""+i;
                list.addFirst(""+i);
                }
                
            int val = Integer.parseInt(array[ARRAY_SIZE-1]);
            for(String s : list)
                if(!s.equals(""+(val--)))
                    die("Error in testOne(), elements in wrong order");    
            System.out.println("testOne() ---> PASSED");               
            }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
            }
        }
        
    // testing addLast.        
    private void testTwo() {
        try {
            list.clear();
            list.addLast("1");
            if(list.size() != 1)
                die("Error, wrong size in testTwo()");
            for(String s : list) 
                if(!s.equals("1"))
                    die("Error, iterator problem in testTwo()");
            if(!list.removeFirst().equals("1"))
                die("Error in testTwo, did you forget to update the head ptr in addFirst?");
            if(!(list.isEmpty() && list.size() == 0))
                die("Error in testTwo, wrong size in list");
                
            for(int i=0; i < ARRAY_SIZE; i++) {
                array[i] = ""+i;
                list.addLast(""+i);
                }
                
            int val = 0;
            for(String s : list)
                if(!s.equals(""+(val++)))
                    die("Error in testTwo(), elements in wrong order");    
            System.out.println("testTwo() ---> PASSED");                
            }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
            }
        }        
   
    // testing removeFirst        
    private void testThree() {
        try {
            list.clear(); 
            list.addFirst("3");
            list.removeFirst();
            list.addLast("3");
            if(!list.removeFirst().equals("3"))
                die("Error in testThree(), removeFirst failed"); 
            list.addLast("1");
            if(!list.removeFirst().equals("1")) 
                die("Error in testThree(), removeFirst failed"); 
            if(list.removeFirst() != null) 
                die("Error in testThree(), removeFirst failed"); 
            if(list.removeLast() != null) 
                die("Error in testThree(), removeFirst failed");
            if(list.size() != 0)
                die("Error in testThree(), wrong size.");
            for(String s : list)
                die("Error in testThree(), iterator failure");
            System.out.println("testThree() ---> PASSED");                                                              
            }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
            } 
        }  
        
    // testing removeLast        
    private void testFour() {
        try {
            list.clear(); 
            list.addFirst("3");
            list.removeFirst();
            list.addLast("3");
            if(!list.removeLast().equals("3"))
                die("Error in testFour(), removeLast failed"); 
            list.addFirst("1");
            if(!list.removeLast().equals("1")) 
                die("Error in testFour(), removeLast failed"); 
            if(list.removeFirst() != null) 
                die("Error in testFour(), removeLast failed"); 
            if(list.removeLast() != null) 
                die("Error in testFour(), removeLast failed");
            if(list.size() != 0)
                die("Error in testFour(), wrong size.");
            for(String s : list)
                die("Error in testFour(), iterator failure"); 
            System.out.println("testFour() ---> PASSED");                                                             
            }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
            } 
        }  
      
    // testing remove(E obj)      
    private void testFive() {
        int arraySize = 100000; // CHANGE THIS IF IT TAKES TOO LONG
        System.out.println("Running testFive(), this make take some time, " +
                           "please be patient.\nIf the program hangs, you may have " +
                           "an infinite loop in either\nyour remove(E obj) or " +
                           "your contains(E obj) method.\n"); 
        try {
            list.clear();  
            String [] tester = new String[arraySize];
            // create a large array, then scramble the contents
            for(int i=0; i < arraySize; i++) {
                tester[i] = ""+i;      
                }
                
            for(int i=0; i < arraySize; i++) {
                int indexToSwap = (int) (arraySize*Math.random());
                String tmp = tester[i];
                tester[i] = tester[indexToSwap];
                tester[indexToSwap] = tmp;
                }
                
            for(int i=0; i < arraySize>>1; i++)
                list.addFirst(""+tester[i]);
            for(int i=arraySize>>1; i < arraySize; i++)
                list.addLast(tester[i]);
                
            for(int i=0; i < arraySize; i++) {
                if(i%1000 == 0)
                    System.out.print(". ");
                if(list.remove(tester[i]) == null)
                    die("\nError in testFive(), remove(E obj) failed.\n"+
                    "Failed to remove value: " + tester[i] + " at index " +i );
                if(list.contains(tester[i]))
                    die("\nError in testFive(), contains(E obj) failed.\n"+
                    "Found a removed element in your list, value: " + tester[i] + " at index " +i );
                    }
            System.out.println();
            System.out.println("testFive() ---> PASSED");             
            }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
            }             
        }
            
    private void testSix() {
        int SIZE = 100000;  // CHANGE THIS IF IT TAKES TOO LONG
        long start, stop;
        list.clear();
        try {
            list.clear();
            System.out.println("Now doing some timing tests, we expect removeLast\n" + 
                               " to be noticeably slower than the other insert/remove methods.");
            start = System.currentTimeMillis();                               
            for(int i=0; i < SIZE; i++)
                list.addFirst(""+i);
            stop = System.currentTimeMillis(); 
            System.out.println("For " + SIZE + " elements, addFirst time is: " +
                (stop-start) + " milliseconds"); 
                
            list.clear();    
            start = System.currentTimeMillis();                               
            for(int i=0; i < SIZE; i++)
                list.addLast(""+i);
            stop = System.currentTimeMillis(); 
            System.out.println("For " + SIZE + " elements, addLast time is: " +
                (stop-start) + " milliseconds");  
                
            list.clear(); 
            for(int i=0; i < SIZE; i++)
                list.addLast(""+i);               
            start = System.currentTimeMillis();                               
            for(int i=0; i < SIZE; i++)
                if(list.removeFirst() == null)
                    die("Error in timing test, removeFirst failed.");
            stop = System.currentTimeMillis(); 
            System.out.println("For " + SIZE + " elements, removeFirst time is: " +
                (stop-start) + " milliseconds");  
                
            list.clear(); 
            for(int i=0; i < SIZE; i++)
                list.addFirst(""+i);  
            System.out.println("This one should be slow, be patient.");                             
            start = System.currentTimeMillis();                               
            for(int i=0; i < SIZE; i++) {
                if(i%1000 == 0)
                    System.out.print(". ");
                if(list.removeLast() == null)
                    die("Error in timing test, removeFirst failed.");
                }
            stop = System.currentTimeMillis();
            System.out.println(); 
            System.out.println("For " + SIZE + " elements, removeLast time is: " +
                (stop-start) + " milliseconds"); 
            System.out.println("testSix() ---> PASSED");                                                                           
            }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
           } 
         } 
   
                          
                   
    private void TEMPLATE() {
        try {
            list.clear();        
            
            }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
            } 
        } 
        
    private void die(String s) {
        System.out.println(s);
        System.exit(1);
        }                  
  
    public static void main(String [] args) {
        new LinkedListChecker();
        }
        
    }
