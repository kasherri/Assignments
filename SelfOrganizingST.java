/******************************************************************************
 *  Compilation:  javac -cp SelfOrganizingST.java
 *  Execution:    java SelfOrganizingST
 *  
 *  Symbol table implementation with sequential search in an
 *  unordered linked list of key-value pairs.
 *
 * java SelfOrganizingST < tiny.txt 
 * java SelfOrganizingST < got1.txt 

 * Num unique words = 10
 *  l 1
 *  p 1
 *  m 1
 *  x 1
 *  h 1
 *  c 1
 *  r 1
 *  a 2
 *  e 3
 *  s 1
 *******************************************************************************/

/**
 *  The {@code SeqSearchSTsent} class represents an (unordered)
 *  symbol table of generic key-value pairs.  It supports the usual
 *  <em>put</em>, <em>get</em>, <em>contains</em>, <em>delete</em>,
 *  <em>size</em>, and <em>is-empty</em> methods.  It also provides a
 *  <em>keys</em> method for iterating over all of the keys.  A symbol
 *  table implements the <em>associative array</em> abstraction: when
 *  associating a value with a key that is already in the symbol
 *  table, the convention is to replace the old value with the new
 *  value.  The class also uses the convention that values cannot be
 *  {@code null}. Setting the value associated with a key to {@code
 *  null} is equivalent to deleting the key from the symbol table.

 *  <p> This implementation uses a singly-linked list with a sentinel
 *  node and sequential search.  It relies on the {@code equals()}
 *  method to test whether two keys are equal. It does not call either
 *  the {@code compareTo()} or {@code hashCode()} method.  The
 *  <em>put</em> and <em>delete</em> operations take linear time; the
 *  <em>get</em> and <em>contains</em> operations takes linear time in
 *  the worst case.  The <em>size</em>, and <em>is-empty</em>
 *  operations take constant time.  Construction takes constant time.
 *
 *  <p> For additional documentation, see <a
 *  href="https://algs4.cs.princeton.edu/31elementary">Section 3.1</a>
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin
 *  Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author S. Anderson (2022 revisions)
 *  @author Kasherri Njoroge
 *  
 */
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class SelfOrganizingST<Key, Value> {
    private int n;           // number of key-value pairs
    private Node sentinel;      // the linked list of key-value pairs

    // a helper linked list data type
    private class Node {
        private Key key;
        private Value val;
        private Node next;

        public Node(Key key, Value val, Node next)  {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public SelfOrganizingST() {
	// uses circular linked list with sentinel node.
	sentinel = new Node(null,null,null);
	sentinel.next = sentinel;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and {@code
     * false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *     {@code false} otherwise
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is
     *     in the symbol table and {@code null} if the key is not in
     *     the symbol table
     */
    public Value get(Key key) {
    Node prev=sentinel;

    Node curr = sentinel.next;

    sentinel.key = key; // guarntees we stop!
	
	while ( !curr.key.equals(key) ) { 
        prev=curr;
        curr = curr.next;
}
	sentinel.key = null; // reset the sentinal node's key, no loitering!
	if (curr == sentinel) return null; // not found
    //placing recently accessed node at the beginning of the list
    prev.next=curr.next; //the node before current will now point to the node after current
    curr.next=sentinel.next; //the item that sentinel was previously following sentinel is now following curr
    sentinel.next=curr; //curr is now following sentinel. New order: Sentinel->curr->sentinel next
	return sentinel.next.val;
    
    }



    /**
     * Inserts the key-value pair into the symbol table, overwriting
     * the old value with the new value if the key is already in the
     * symbol table.  If the value is {@code null}, this effectively
     * deletes the key from the symbol table.
     * @param key the key
     * @param val the value
     */
    public void put(Key key, Value val) {
        if (val == null) {
            delete(key);
            return;
        }
    Node prev=sentinel;
	Node curr = sentinel.next;
	sentinel.key = key; // so the loop terminates

	while ( !curr.key.equals(key) ) {
        prev=curr;
        curr = curr.next;
    }
	sentinel.key = null; // no loitering

	if (curr == sentinel) { // new key in table is first node
	    sentinel.next = new Node(key, val, sentinel.next);
	    n++;
	} else {
        curr.val = val;  // reset this value for key
        prev.next=curr.next; //the node before current will now point to the node after current
        curr.next=sentinel.next; //node after current will now be the node that previously followed sentinel
        sentinel.next=curr; //new order: sentinel->curr->sentinel next
	}
    

    }

    /**
     * Removes the key and associated value from the symbol table
     * (if the key is in the symbol table).
     * @param key the key
     */
    public void delete(Key key) {
	if (size() == 0) return;
	
        Node curr = sentinel;
	sentinel.key = key;

	while ( !curr.next.key.equals(key) ) curr = curr.next;
	// ASSERT: curr is prior to node containing key
	sentinel.key = null;

	if (curr.next == sentinel) { 
	    return;  // key not found
	} else {  // skip over next node
	    curr.next = curr.next.next;
	    n--;
	}

	
    }


    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named
     * {@code st}, use the foreach notation: {@code for (Key key :
     * st.keys())}.
     * @return all keys in the symbol table as an {@code Iterable}
     */
    public Iterable<Key> keys()  {
        LinkedList<Key> list = new LinkedList<Key>();
	Node curr = sentinel.next;
	while (curr != sentinel) {
	    list.add(curr.key);
	    curr = curr.next;
	}
        return list;
    }

    // returns string in lowercase w/o punctuation.
    public static String normalize(String source) {
	String s = source.replaceAll("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]", "");
	return s.toLowerCase();
    }



    /**
     * Get frequency counts for input text.
     *
     * @param args the command-line arguments
     */
        public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Pattern word = Pattern.compile(".+"); // any white-space delim chars.
        SelfOrganizingST<String, Integer> st =
        new SelfOrganizingST<String, Integer>();
        // Read words from file, keep frequency count in symbol table.
        long startTime = System.nanoTime();
        while (sc.hasNext(word)) {
        String key = normalize(sc.next(word));
        if (st.get(key) == null) {
            st.put(key, 1);
        } else {
            st.put(key, 1 + st.get(key));
        }
        }

        System.out.println("Num unique words = " + st.size());
        for (String k: st.keys()) {
        System.out.println(k + " " + st.get(k));
        }
        System.out.println("Num unique words = " + st.size());


        
 
        /* … The code being measured starts … */
 
      
 
        /* … The code being measured ends … */
 
        long endTime = System.nanoTime();
 
        // get the difference between the two nano time valuess
        long timeElapsed = endTime - startTime;
 
        System.out.println("Execution time in nanoseconds: " + timeElapsed);
        System.out.println("Execution time in seconds: " + timeElapsed / 1000000000);
    }

}
