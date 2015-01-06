package hr.fer.oop.lab3.topic1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;


/**
 * Created by borna on 07/12/14.
 */

public class SimpleHashTable<K,V> implements Iterable<SimpleHashTable.TableEntry<K,V>> {

//    private TableEntry[] table;
    private int size;
    private ArrayList<TableEntry<K,V>> table;

    /**
     * first constructor with no arguments; table wi
     * th 16 slots
     */
    public SimpleHashTable() {
//        table[16] = new TableEntry(null, null, n=ull);
//    }

        this.table = new ArrayList<SimpleHashTable.TableEntry<K,V>>(16);

        this.size = 0;
    }

    /**
     * 2nd constructor with capacity argument; table with number of slots as the first bigger exponent of 2
     *
     * @param capacity
     * @exception java.lang.IllegalArgumentException for capacity less than 0
     *
     */
    public SimpleHashTable(int capacity) {
        int i = capacity;

        if (capacity < 0 ) {
            throw new IllegalArgumentException("I need positive capacity!");
        }

        while (isNotExpenentOf2(i)) {
            i++;
        }

        System.out.println("Final capacity equals: "+i);
        table = new ArrayList<SimpleHashTable.TableEntry<K,V>>(i);

        // now i is the next number that is exponent of 2
//        for (int j = 0; j < i; j++){
//            table[j] = new TableEntry(null, null, null);
//            table = new TableEntry[j];
//        }
    }

    /**
     *
     * @param i
     * @return boolean value if given number i is exponent of number 2
     *
     * for example i=32 returns true, i=128 true, i=333 returns false
     */
    private boolean isNotExpenentOf2(int i) {
        for (; i != 2; i=i/2) {
            if (i % 2 != 0)
                return true;
        }
        if (i % 2 == 0) return false;
        return true;
    }

    /**
     *
     * @param key
     * @param newValue
     * @exception java.lang.IllegalArgumentException for trying to put new table entry with key null
     */
    public void put(K key, V newValue) {
//        String keyString;
//        try {
//            keyString = key.toString();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            System.err.println("Not able to put new table entry with null key value!\n");
//
//        }
        if (key == null) {
            throw new IllegalArgumentException("Not able to put null key value");
        }
        if (key == "") {
            throw new IllegalArgumentException("Not able to put new table entry with NO key value!\n");
        }

        if (newValue == null){
            throw new IllegalArgumentException("Not able to create key with NO grade value!");
        }

        int slot = slotNumber(key);

        if (containsKey(key)){          //case No2 when entry is already in slot
            TableEntry entry = table.get(slot);
            for (; entry != null; entry = entry.next){
                if ( keyEqual(key, entry) ) {
                    entry.setValue(newValue);
                    break;
                }
            }
            //now entry is the one that has to be changed
        }

        else {                          //case No3 when new entry with no prior existence in slot

            TableEntry newEntry = new TableEntry(key, newValue, null);


            if (table.get(slot) == null) {
                table.set(slot, newEntry);

            } else {
                TableEntry currentEntry = table.get(slot);

                while (currentEntry.next != null) {
                    currentEntry = currentEntry.next;
                }
                currentEntry.next = newEntry;
            }
            size += 1;
        }


    }

    /**
     *
     * @param keyGiven user input value
     * @param entryFound current entry from the iteration
     * @return boolean value true if given key and a key from a current entry is equal
     */
    public boolean keyEqual(K keyGiven, TableEntry entryFound){
        if (keyGiven.equals( entryFound.getKey() ) )
            return true;
        return false;
    }

    /**
     *
     * @param valueGiven user input value
     * @param entryFound current entry from the iteration
     * @return boolean true if equal
     */
    public boolean valueEqual(V valueGiven, TableEntry entryFound){
        if (valueGiven.equals(entryFound.value))
            return true;
        return false;
    }

    /**
     *
     * @param key user input, name
     * @return Object value with the key parameter
     * @exception java.lang.IllegalArgumentException if wanted key does not exist
     */
    public V get(K key){ //return value
        int slot = slotNumber(key);
        TableEntry<K,V> currentEntry = table.get(slot);

        for (; currentEntry != null; currentEntry = currentEntry.next) {
            if (keyEqual(key, currentEntry)) {
                return currentEntry.getValue();
            }
        }
        throw new IllegalArgumentException("Given key does not exist!");
    }

    /**
     *
     * @param key String name
     * @return slot number of a given key by using hashCode method
     * @exception throw java.lang.IllegalArgumentException if key is empty String because hashCode() can't be performed
     */
    public int slotNumber (K key){
        if (key == null){
            throw new IllegalArgumentException("Can't create slot number with null key\n");
        }
        if (key == ""){
            throw new IllegalArgumentException("Can't create slot number without appropriate key\n");
        }
        return Math.abs( key.hashCode() ) % table.size();
    }

    /**
     *
     * @return size class variable that is incrementing in put method
     */
    public int size() {
        return this.size;
    }

    /**
     *
     * @param key user input String name
     * @return boolean true if key exists in calculated slot number
     * @exception throw java.lang.IllegalArgumentException if key empty string, NullPointerException is called on its own.
     */
    public boolean containsKey(K key){ //go through only one slot!!!! and return true if key exists in the right slot

        if (key == null){
            throw new IllegalArgumentException("Not able to search for null key");
        }

        if (key == ""){
            throw new IllegalArgumentException("Not able to search for no key!\n");
        }

        int slot = slotNumber(key);
        TableEntry currentEntry = table.get(slot);

        while (currentEntry != null){
            if ( keyEqual(key, currentEntry) )
                return true;
            currentEntry = currentEntry.next;
        }
        return false;
    }

    /**
     *
     * @param value
     * @return boolean value saying whether value given in parameter exists anywhere in the SimpleHashtable instance
     */
    public boolean containsValue(V value){ //go through all slots and return true if value exists
        TableEntry currentEntry;
        int tableLength = table.size();

        for (int i = 0; i < tableLength; i++){

            currentEntry = table.get(i);

            while (currentEntry != null){
                if (valueEqual(value, currentEntry))
                    return true;
                currentEntry = currentEntry.next;
            }
        }
        return false;
    }

    /**
     *
     * @param key
     * Remove object with the given key value
     * @exception throw java.lang.IllegalArgumentException if non-existing key value wants to be removed
     */
    public void remove(K key){
        int slot = slotNumber(key);

        TableEntry beforeEntry = table.get(slot);
        TableEntry currentEntry = beforeEntry.next;
        size--;

        while (currentEntry != null){

            if ( keyEqual(key, currentEntry) ) {

                if (currentEntry.next == null)      // case No.1 when last TableEntry object in slot
                    beforeEntry.next = null;
                else {                                  // case when there's object before and after removed object
                    beforeEntry.next = currentEntry.next;
                    currentEntry.next = null;
                    return;
                }
            }
            beforeEntry = currentEntry;
            currentEntry = currentEntry.next;
        }
        //error, key not found
        size++;
        throw new IllegalArgumentException("Non-existing key can't be removed!\n");
    }

    public boolean isEmpty(){
        if (size > 0)
            return false;
        return true;
    }

    /**
     *
     * @return String how you want SimpleHashTable to appear on screen
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("SimpleHashtable:\n").append("Size: "+size()+"\n");
        int sizeTableEntryArray = table.size();

        for (int i = 0; i < sizeTableEntryArray; i++) {
            TableEntry currentEntry = table.get(i);

            for (; currentEntry != null; currentEntry = currentEntry.next) {
                sb.append("Slot["+i+"]\n").append(currentEntry.toString());
            }
        }
        return sb.toString();
    }




    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */

    public Iterable <K> keys() {
        return new Iterable<K>() {
            @Override
            public Iterator<K> iterator() {
                return new Iterator<K>() {

                    private Iterator<SimpleHashTable.TableEntry<K,V>> keyIterator = new SimpleHashTableIterator();
                    @Override
                    public boolean hasNext() {
                        return keyIterator.hasNext();
                    }

                    @Override
                    public K next() {
                        if (keyIterator.hasNext()) {
                            return keyIterator.next().getKey();
                        }
                        throw new NoSuchElementException();
                    }
                };
            }
        };
    }
    public Iterable<V> values() {
        return new Iterable<V>() {
            @Override
            public Iterator<V> iterator() {
                return new Iterator<V>() {

                    private Iterator<SimpleHashTable.TableEntry<K,V>> valueIterator= new SimpleHashTableIterator();
                    @Override
                    public boolean hasNext() {
                        return valueIterator.hasNext();
                    }

                    @Override
                    public V next() {
                        if (valueIterator.hasNext()){
                            return valueIterator.next().getValue();
                        }

                        throw new NoSuchElementException();
                    }
                };
            }
        };


    }

    @Override
    public Iterator<SimpleHashTable.TableEntry<K,V>> iterator() {
        return new SimpleHashTableIterator();
    }


    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Unless otherwise specified by the implementing class,
     * actions are performed in the order of iteration (if an iteration order
     * is specified).  Exceptions thrown by the action are relayed to the
     * caller.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEach(Consumer action) {
        return;
    }

    /**
     * Creates a {@link java.util.Spliterator} over the elements described by this
     * {@code Iterable}.
     *
     * @return a {@code Spliterator} over the elements described by this
     * {@code Iterable}.
     * @implSpec The default implementation creates an
     * <em><a href="Spliterator.html#binding">early-binding</a></em>
     * spliterator from the iterable's {@code Iterator}.  The spliterator
     * inherits the <em>fail-fast</em> properties of the iterable's iterator.
     * @implNote The default implementation should usually be overridden.  The
     * spliterator returned by the default implementation has poor splitting
     * capabilities, is unsized, and does not report any spliterator
     * characteristics. Implementing classes can nearly always provide a
     * better implementation.
     * @since 1.8
     */
    @Override
    public Spliterator spliterator() {
        return null;
    }

    /**
     * Nested class from SimpleHashTable
     */
    public static class TableEntry<K,V> {
        private K key;
        private V value;
        private TableEntry<K,V> next;

        public TableEntry(K key, V value, TableEntry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        //newly added constructor with no arguments
        public TableEntry() {
            this.key = null;
            this.value = null;
            this.next = null;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            if (key instanceof String && value instanceof Integer)
                return "Key: "+getKey()+"\nValue: "+getValue()+"\n\n";
            return ("Not available arguments\n");
        }
    }

    /**
     * TableEntryIterator is class that is implemented Iterator on TableEntries,
     */
    public class SimpleHashTableIterator implements Iterator<TableEntry<K,V>>{

        TableEntry<K,V> current = null;
        int length = table.size();

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            TableEntry<K,V> saveEntry = current;
            try {
                next();
            } catch (NoSuchElementException ex) {
                ex.getMessage();
                return false;
            }
            current = saveEntry;
            return true;
        }
        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration //it is TableEntry
         * @throws NoSuchElementException if the iteration has no more elements
         */

        @Override
        public TableEntry<K, V> next() {

            if (current == null) {
                return firstAvailableTableEntry();
            }
            else {
                if (current.next != null) {                 //next TableEntry is in the same slot
                    return current = current.next;
                }
                // next TableEntry is in different slot
                else {
                    int slot = slotNumber(this.current.getKey());
                    int i = slot;
                    i++; //start from strictly next slot

                    while (i < length) {

                        if (table.get(i) != null){
                            return current = table.get(i);
                        }

                        i++;
                    }
                    throw new NoSuchElementException("No more TableEntries");
                }
            }
        }

        public TableEntry<K,V> firstAvailableTableEntry() throws NoSuchElementException {

            for (int i=0 ; i < length; i++){
                if (table.get(i) != null) {
                    return current = table.get(i);
                }
            }
            throw new NoSuchElementException("No ANY TableEntries");
        }
    }

}
