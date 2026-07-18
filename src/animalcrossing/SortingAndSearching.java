package animalcrossing;
import java.util.ArrayList;

 /**
     * This class handles the algorithms mergesort, quicksort, and recursive binary search.
     * Mergesort will be used on an array of Creature objects to sort them by type and name.
     * Quicksort will be implemented on an ArrayList of Catalog objects to sort them from lowest to highest price.
     * Recursive binary search will take the sorted ArrayList of Catalog items and find the desired element by looking for its price.
     * 
     * @author Rachel Saini
     * @author Joachim Cantrell
     */

public class SortingAndSearching{
    
    private Creature[] creatures;
    private ArrayList<Catalog> catalogItems;


     /**
     * This method is used to call the entire mergesort algorithm.
     * It is referred to as "museumSort".
     */
    public void museumSort(){
        //WRITE YOUR CODE HERE

    }


    /**
     * This is a helper method for museumSort. It handles the recursive calls.
     * 
     * @param creatureArr --> The array to recursively mergesort
     * @param lo --> The low bound of the array
     * @param hi --> The high bound of the array 
     */
    public void mergeSortHelper(Creature[] creatureArr, int lo, int hi){
        //WRITE YOUR CODE HERE

    }


     /**
     * The merge helper method is where the bulk of mergesort occurs.
     * It merges the halves of the array after they get split from mergeSortHelper()
     * and sorts each element.
     * 
     * @param creatureArr --> The array to merge/sort
     * @param lo --> The low bound of the array
     * @param mid --> The middle index of the array
     * @param hi --> The high bound of the array
     */
    public void merge(Creature[] creatureArr, int lo, int mid, int hi){
        //WRITE YOUR CODE HERE

    }

     /**
     * This method is used to call the entire quicksort algorithm.
     * It is referred to as "catalogSort".
     */
    public void catalogSort(){
       //WRITE YOUR CODE HERE

    }


     /**
     * This is a helper method for catalogSort. It handles the recursive calls.
     * 
     * @param catalogArrList --> The array to recursively quicksort
     * @param lo --> The low bound of the ArrayList
     * @param hi --> The high bound of the ArrayList
     */
    public void quickSortHelper(ArrayList<Catalog> catalogArrList, int lo, int hi){
        //WRITE YOUR CODE HERE

    }


     /**
     * This method partitions the Catalog ArrayList around a pivot element (at index lo).
     * It rearranges elements so that all items with a lower price move to the left
     * of the pivot, and all items with a higher price move to the right.
     * 
     * @param catalogArrList --> ArrayList of Catalog items to partition
     * @param lo --> The starting bound index of the subarray
     * @param hi --> The ending bound index of  the subarray
     * @return --> Returns an int representing the final sorted index position of the pivot element
     */
    public int partition(ArrayList<Catalog> catalogArrList, int lo, int hi){
        //WRITE YOUR CODE HERE

        return -1;
    }


    /**
     * This method calls and returns the result of the recursive binary search algorithm.
     * 
     * @param catalogList --> The ArrayList of Catalog items
     * @param priceKey --> The price of the item searched for
     * @return --> Returns a Catalog object representing the result of the recursive binary search algorithm
     */
    public Catalog findItem(int priceKey){
       //WRITE YOUR CODE HERE

       return null; // Update this line, it is provided so that the code compiles.
    }


    /**
     * This method executes recursive binary search to find the item
     * of a provided price in the sorted ArrayList.
     * 
     * It is your task to debug the method.
     * 
     * @param catalogList --> The ArrayList of Catalog items
     * @param priceKey --> The price being searched for
     * @param lo --> The low bound of catalogList
     * @param hi --> The high bound of catalogList
     * @return --> Returns a Catalog object representing the item found
     */
    public Catalog findItemHelper(ArrayList<Catalog> catalogList, int priceKey, int lo, int hi){
        if (catalogList == null || lo > hi){
            return null;
        }

        int mid = lo - (hi - lo) / 2;
        int midPrice = catalogList.get(mid).getPrice();

        if (midPrice > priceKey){
            return findItemHelper(catalogList, priceKey, lo, mid - 1);
        }else if (midPrice >= priceKey){
            return findItemHelper(catalogList, priceKey, mid + 1, hi);
        }else{
            return null;
        }
    }


    /**
      * This method is provided to you.
      * 
      * This method reads in data from the corresponding input file, and populates the correct
      * array or ArrayList depending on the filename.
      * 
      * @param filename --> The file being read in
      */
    public void readData(String filename){
        //DO NOT EDIT
        StdIn.setFile(filename);

        if (filename.toLowerCase().contains("creatures")){
            int size = StdIn.readInt();
            creatures = new Creature[size];
            StdIn.readLine();
            
            for (int i = 0; i < size; i++){
                char type = StdIn.readChar();
                StdIn.readLine();
                String name = StdIn.readLine();
                creatures[i] = new Creature(type, name);
            }
        }
        else if (filename.toLowerCase().contains("catalog")){
            catalogItems = new ArrayList<Catalog>();

            while (!StdIn.isEmpty()){
                String name = StdIn.readLine();
                int price = StdIn.readInt();
                StdIn.readLine();
                catalogItems.add(new Catalog(name, price));
            }
        }
    }

    
     /**
     * This method is provided to you.
     * 
     * This method gets the array of Creature objects.
     * @return --> Returns the array containing the sorted or unsorted Creature objects
     */
    public Creature[] getCreatures(){
        //DO NOT EDIT
        return this.creatures;
    }


     /**
     * This method is provided to you.
     * 
     * This method gets the ArrayList of Catalog items.
     * @return --> Returns an ArrayList containing the sorted or unsorted Catalog objects
     */
    public ArrayList<Catalog> getCatalogItems(){
        //DO NOT EDIT
        return this.catalogItems;
    }

}