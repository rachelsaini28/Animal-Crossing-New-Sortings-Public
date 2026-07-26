# 🍃 Animal Crossing: New Sortings (Recursive Sorting & Searching Assignment)

## ℹ️ Overview
This project is an assignment for the CS121: Introduction to Computer Science course.  

Students must help different characters in the Animal Crossing universe sort their files and shop inventory. The goal of this assignment is for students to understand and implement recursive sorting and searching algorithms such as Merge Sort, Quick Sort, and Recursive Binary Search.
* Merge Sort: Sort an array of ```Creature``` objects (fish and insects) lexicographically by group, with fish preceding insects
* Quick Sort: Sort an ArrayList of ```Catalog``` item prices from lowest to highest
* Recursive Binary Search: Requires students to debug the provided algorithm before running it on the sorted Catalog ArrayList

> **Note:** This project was developed through a university program that creates assignments for introductory computer science courses. Solution code is not included in accordance with the university's academic integrity policy. Students will be writing their code in ```SortingAndSearching.java``` in areas where a ```// WRITE YOUR CODE HERE``` comment is present.

## 🎮 Features
* Reads data from text files using the provided ```readData()``` method
    * Populates the Creature array and Catalog ArrayList
* Sorts Creature array using Merge Sort
* Sorts Catalog ArrayList using Quick Sort
* Provides a partially implemented Recursive Binary Search algorithm for students to debug and complete
    * Searches for the item that matches an inputted value
* Displays Animal-Crossing-inspired GUI written in Swing (```Driver.java```)
    * Home screen allows user to navigate to:
        * Blathers' Museum (for Merge Sort)
        * Nook's Cranny (for Quick Sort + Recursive Binary Search)
    * Both sorting screens provide a dropdown selection to choose a text file to read in
    * The array/ArrayList of objects is illustrated with each element as its own box
        * Creature boxes display the Creature's name
            * Fish are stored in blue boxes
            * Insects are stored in green boxes
        * Catalog item boxes display the Catalog item's price
    * The user enters a price value to search for the name of an item in the ArrayList
  
## 🗃️ Text Files
There are **6** ```txt``` files, 3 for Creature objects and 3 for Catalog objects. They are formatted as such:
* ```creatures#.txt```
    * ```int``` - array size
    * ```char``` - Creature type ('F' = Fish, 'I' = Insect)
    * ```String``` - Creature name
* ```catalog#.txt```
    * ```String``` - Catalog item name
    * ```int``` - Catalog item price

## 💻 Technologies Used
* Java
* Java Swing

## 📸 Snapshots
The following screenshots illustrate each algorithm's interface:
![Merge Sort screen displaying an unsorted Creature array](link)
![Quick Sort screen displaying an unsorted CatalogItem ArrayList](link)
![Recursive Binary Search screen displaying a sorted CatalogItem ArrayList and a value to search](link)
