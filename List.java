// Alinase Phiri
// Lab 5

import java.util.Comparator;

/** A collection of T objects listed in no particular order. */
public class List<T extends Comparable<T>> {

    /** Primary structure for storing the collection of Items*/
    protected T[] items;

    // ____________________________________________________
    //              VARIABLES
    // ____________________________________________________

    /** Default for maximum number of items that can be stored */
    protected static final int DEFAULT_CAPACITY = 20;

    /** The number of items stored in the array. */
    private Integer length = 0;

    /** Comparator for ordering array. Uses compareTo of T by default */
    private Comparator<T> orderBy = new Comparator<T>() {
        @Override
        public int compare(T object1, T object2) {
            return object1.compareTo(object2);
        }
    };


    // ____________________________________________________
    //              CONSTRUCTORS
    // ____________________________________________________

    /** Constructor creates List with user-specified capacity
     * @param capacity The capacity of the list (i.e. max number of elements)
     */
    public List(int size) {
        // suppress warning to convince Java to create a generic array
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Comparable[size];
        items = temp;
    }

    /** Default Constructor */
    public List() {
        this(DEFAULT_CAPACITY);
    }

    /** Constructor creates List with 2 times other arrays length
     * @param toAdd array that is being passed in
     */
    public List(T[] toAdd) {
        // set length to 2* toAdd's length if it is larger then the default capacity
        if((2 * toAdd.length) > DEFAULT_CAPACITY){
            // suppress warning to convince Java to create a generic array
            @SuppressWarnings("unchecked")
            T[] temp = (T[]) new Comparable[2 * toAdd.length];
            items = temp;
        }
        else{
            // suppress warning to convince Java to create a generic array
            @SuppressWarnings("unchecked")
            T[] temp = (T[]) new Comparable[DEFAULT_CAPACITY];
            items = temp;
        }
        // add all the element into this list
        this.addAll(toAdd);
    }

    /** Constructor creates List that is a copy of the toCopy list
     * @param toCopy array that is being passed in
     */
    public List(List<T> toCopy) { 
        // suppress warning to convince Java to create a generic array
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Comparable[toCopy.length];
        items = temp; 
        // add each element from toCopy to this list by iterating through the list
        for(int i=0; i < toCopy.length; i++){
            this.add(toCopy.get(i)) ;  
        }
    }

    /** Constructor that sets orderBy to the incoming comparator
     * @param order comparator being passed into the list class
     */
    public List(Comparator<T> order){
        orderBy = order;
    }


    // ____________________________________________________
    //              HELPER METHODS
    // ____________________________________________________

    /** Method that determines if a list is full
     * @return true if the array is full
     * @return false if the array has at least 1 spot
     */
    private boolean full() {
        if(length.equals(this.length)){ return true; } return false;
    }
    
    /** Method that determines if a list is empty
     * @return true if the array is empty
     * @return false if the array has at least 1 taken
     */
    public boolean empty() {
        if(length.equals(0)){ return true; } return false;
    }

    /** Method that determines if the index given is valid
     * @param index the index being tested
     * @return true if the index is valid
     * @return false if the index is invalid
     */
    public boolean valid(int index) {
        // return true if the index is between 0 and length
        if(index >=0 && index < length){ 
            return true;
        }
        return false;
    }

    /** Method that prints the list in a readable format
     * @return The list being printed
     */
    @Override
    public String toString() {
        // Create numbered list of Items in the collection
        String printedList = "";
        for (int i = 0; i < length; i++) {
            printedList += (i + 1) + ". " + items[i].toString() + "\n";
        }
        return printedList;
    }

    /** Method that increases the capacity of a list
     * @param size the size you want your list to increase by
     */
    private void increaseCapacity(int size){ 
        // suppress warning to convince Java to create a generic array
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Comparable[this.capacity() + size];
        for(int i=0; i < length; i++){
            temp[i] = items[i];
        }
        items = temp;
    }


    // ____________________________________________________
    //                   SEARCH METHODS
    // ____________________________________________________

    /** Method that locates the index of the item
     * @param item the item being located
     * @return the index of the item being located
     * @return return -1 if item is not in the list
     */
    public int locate(T item) {
        // find the item by iterating through the list
        for(int i=0; i < length; i++){
            if(item.equals(items[i])){
                return i;
            }
        }
        return -1;
    }

    /** Method that locates the index of the item using iterative binary search
     * @param item the item being located
     * @return the index of the item being located
     * @return return -1 if item is not in the list
     */
    public int locateIterative(T item) {
        // initialize start, end, and middle of list
        int start = 0;
        int end = length - 1;
        int middle = 1 + (end - 1)/ 2;
        while(start <= end){
            // return the middle index if the item is found in the middle
            if(orderBy.compare(items[middle],item) == 0){
                return middle;
            }
            // set the end to middle - 1 if the item is in the first half if the list
            if(orderBy.compare(items[middle],item) > 0){
                end = middle - 1;
                middle = 1 + (end - 1)/ 2;
            }
            // set the start to middle + 1 if the item is in the last half of the list
            else{
                start = middle + 1;
                middle = 1 + (end - 1)/ 2;
            }
        }
        return -1;
    }

    /** Method that locates the index of the item using recursive binary search
     * @param item the item being located
     * @return the index of the item being located
     * @return return -1 if item is not in the list
     */
    public int locateRecursive(T item) {
        int start = 0;
        int end = length - 1;
        if(start <= end && end >=1){
            int middle = 1 + (end - 1)/ 2;
            // return the middle index if the item is found in the middle
            if(orderBy.compare(items[middle],item) == 0){
                return middle;
            }
            // set the end to middle - 1 if the item is in the first half if the list
            if(orderBy.compare(items[middle],item) > 0){
                end = middle - 1;
                // call this function again with new end
                return locateRecursive(item);
            }
            // set the start to middle + 1 if the item is in the last half of the list
            else{
                start = middle + 1;
                // call this function again with new start
                return locateRecursive(item);
            }
        }
        return -1;
    }

    /** Method that gets the item by it's index
     * @param index the index being located
     * @return the item at the given index
     * @return null if a item is not at the index
     */
    public T get(int index) {
        if(valid(index)){
            return items[index];
        }
        return null;
    }

    /** Methed that finds the last occurrence of the item
     * @param item the item being located
     * @return the index of the item being located
     * @return return -1 if item is not in the list
     */
    public int findLast(T item){
        // start from the end of the list and work backward to find the item
        for(int i=length-1; i >= 0; i--){
            if(item.equals(items[i])){
                return i;
            }
        }
        return -1;
    }


    // ____________________________________________________
    //                   ADD METHODS
    // ____________________________________________________

    /** Method that adds an item to the end of the array
     * @param item the item being added to the array
     */
    public void add(T item) {
        // Add only if it is not full
        if(!full()){
            items[length] = item;
            // add to count
            length++;           
        }
        // increase the capacity if the list is full
        else{
            this.increaseCapacity(1);
            items[length] = item;
            length++;
        }
        for(int i=0; i < length; i++){
            if(orderBy.compare(item,items[i]) > 0){
                items[i]=item;
            }
        }
    }

    /** Method that adds all the elements from a given array to this array
     * @param array the T array with the elements being added to items array
     * @return total number of elements added to the list
     */
    public int addAll(T[] array) {
        int count = 0; 
        // iterate over the length or the array being passed in
        for(int i=0; i < array.length; i++){
            // add an element to our list is it is not full.
            add(array[i]);
        }
        return count; 
    }

    /** Method that sets the orderBy member variable
     * @param order comparator being passed in
     */
    public void setOrder(Comparator<T> order){
        orderBy = order;

    }


    // ____________________________________________________
    //                   REMOVE METHODS
    // ____________________________________________________


    /** Method that removes the element at the given index
     * @param index The index the element will be removed from
     * @return the item at the given index
     * @return null is the index is not valid
     */
    public T remove(int index){
        // create a placeholder
        T removed;
        // check to see if the index is valid
        if(valid(index)){
            removed = items[index];
            items[index] = null;
            // every element to the right of the index is shifted left, to fill in the gap
            for(int i=index; i < length-1; i++){
                items[i]= items[i+1];
            }
            length--;
            return removed;
        }
        return null;
    }

    /** Method that removes the first occurence of the item if it is located
     * @param item The item being removed
     * @return true if the item is found and removed
     * @return false if the item was not found
     */
    public Boolean remove(T item) {
        // create a boolean placeholder
        boolean removed;
        int index;
        // check to see if the index is valid
        if(this.locate(item) != -1){
            index = this.locate(item);
            items[index] = null;
            removed = true;
            // every element to the right of the index is shifted left, to fill in the gap
            for(int i= index; i < length-1; i++){
                items[i]= items[i+1];
            }
            length--;
            return removed;
        }
        // return false if it is not found
        return false;
    }

    /** Method that removes each occurence of the item
     * @param item The item being removed
     * @return the number of elements removed
     */
    public int removeEach(T item){
        int removed = 0;
        for(int i=0 ; i < length; i++){
            // remove the item if it can be located
            if(locate(item) != -1){
                this.remove(item);
                // increase removed (remove(item) already accounts for length)
                removed++;  
            }  
        }
        return removed;
    }

    /**
     * Set the length to 0 so all the elements are removed
     */
    public void removeAll() {
        length = 0;
    }


    // ____________________________________________________
    //                   CONVERT METHODS
    // ____________________________________________________

    /** Method that creates a new array of the same size, and all elements are copied into the array
     * @return return the new array
     * @return null if the list is empty
     */
    public T[] toArray() {
        T[] newArray;
        // suppress warning to convince Java to create a generic array
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Comparable[length];
        newArray = temp;
        if(!empty()){
            for(int i=0; i < length; i++){
                newArray[i] = items[i];
            }
            return newArray;
        }
        else{
            return null;
        }
    }

    /** Method that copies as many elements to the passed array from this
     * @param array array that is adding elements
     * @return number of elements in the array
     * @return 0 if the elements do not fit
     */
    public int toArray(T[] array) {
        int count = 0;
        if(empty()){
            return 0;
        }
        // add to the passed array if the length of this array is smaller
        if(length <= array.length){
            for(int i=0; i< length; i++){
                array[i] = items[i];
                count++;
            }
        }
        return count;
    }

    /** Method that returns an array with the values of List
     * @param order array that is adding elements
     * @return number of elements in the array
     */
    public T[] toArray(Comparator<T> order) {
        T[] newArray;
        // suppress warning to convince Java to create a generic array
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Comparable[length];
        newArray = temp;
        if(!empty()){
            for(int i=0; i < length; i++){
                newArray[i] = items[i];
                if(orderBy.compare(newArray[i],newArray[i-1]) < 0){
                    newArray[i] = newArray[i-1];
                    newArray[i-1] = items[i];
                }
            }
            return newArray;
        }
        else{
            return null;
        }
    }

    /** Method that creates a new sublist that contains a range of elements
     * @param start The first index in the array
     * @param end The last index in the array
     * @return nitems New itemlist that holds 2x the amount of elements
     * @return null the indicies given are not valid
     */
    public List<T> sublist(int start, int end) {
        // return null if the start or end indices are not valid
        if(valid(start) || valid(end)){
            // create the new sublist with double the capacity
            List<T> nitems = new List((end-start+1)*2);
            for(int i=start; i <= end; i++){
                nitems.add(items[i]);
            }
            return nitems; 
        }
        else{
            return null;
        } 
    } 


    // ____________________________________________________
    //                   ORDERING METHODS
    // ____________________________________________________
    
    /** Method that finds the minimum element in the list
     * @return the minumum element
     * @return null if it is empty
     */
    public T min(){
        if(!empty()){
            // return the first element
            return items[0];
        }
        else{ return null; }
    }

    /** Method that finds the minimum element in the list based on the given comparator
     * @param order given comparator being passed in
     * @return the minumum element
     * @return null if it is empty
     */
    public T min(Comparator<T> order){
        if(!empty()){
            // initalize the min to the first element
            T minimum = items[0];
            for(int i=1; i < length; i++){
                // if compare returns a neg number, set the min to item in current index
                if(orderBy.compare(items[i],minimum) < 0){
                    minimum = items[i];
                }
            }
            return minimum;
        }
        else{ return null; }
    }

    /** Method that finds the maximum element in the list
     * @return the maximum element
     * @return null if it is empty
     */
    public T max(){
        if(!empty()){
            // return the last element in the list
            return items[length-1];
        }
        else{ return null; }
    }

    /** Method that finds the maximum element in the list
     * @param order given comparator being passed in
     * @return the maximum element
     * @return null if it is empty
     */
    public T max(Comparator<T> order){
        if(!empty()){
            T maximum = items[0];
            for(int i=1; i < length; i++){
                // if compare returns a positive number, set the max to item in current index
                if(orderBy.compare(items[i],maximum) > 0){
                    maximum = items[i];
                }
            }
            return maximum;
        }
        else{ return null; }
    }
    

    // ____________________________________________________
    //                   SETTERS AND GETTERS
    // ____________________________________________________
    
        public int length() {
            return length;
        }
    
        public int capacity() {
            return items.length;
        }
    
} 