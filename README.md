# CISC3130-lab2
Java program written to read a spotify csv files and create a sorted output file containing only artist names.
Source code for the program can be found within the Main.java file. A sample output file is included which is titled "ArtistsSorted-WeekOf09032020.txt" 
while the csv file used is titled "regional-global-weekly-latest.csv"  
## Implementation
This program uses a Scanner object to read a csv file, then splits the comma seperated values using
the .split() method. A custom regex code is used to handle any extra commas within the CSV entries. These values are stored in a String array so the artist name can be pulled from index
n (in this program n = 2.) The artist name is then stored in a HashMap using a String as the key and an integer as the value for number of occurances. To create the sorted sorted output file, a custom
Linked list class is used (TopStreamingArtists) which uses a node class Artist. Nodes are added in sorted order, i.e. sorting is done with each addition instead of creating the list,
then sorting. This is done with the method sortedAdd(Artist artist) in the TopStreamingArtists class, which is similar to the bubble sort algorithm as it compares the node being 
added with the current node as well as the following node, but inserts between the two nodes. This method also ignores case when comparing, but maintains the original case when inserting,
so the artist names in the output file appear the same as they are in original file. For this implementation, the HashMap key set is used to get all the artist names, although
the code could be refactored to only use the linked list class (a good TODO task).
A PrintWriter object is used to write the output file. 
### Dependencies
* Written using Java 14
* Terminal to run commands for setup, may differ by OS used.
#### Set up
To run this program, you need to have a github account setup with an SSH key. Access to a terminal and git commands
are needed, unless your IDE of choice has git support. If you choose to run the program with this IDE,
the setup process will differ.  
1. Clone to your machine using `git clone git@github.com:bertom1/CISC3130_Lab3.git` 
2. Open the project directory with the command `cd CISC3130_Lab3`
3. To compile the program, use the command `javac Main.java`
4. To run the program, use the command `java Main`  
