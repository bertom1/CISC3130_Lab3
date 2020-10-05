/* Class: CISC 3130
 * Section: TY9
 * EMPLID: 23908757
 * Name: Roberto Melchor
 */
import java.util.*;
import java.io.File;
import java.io.PrintWriter;

//separated Main and file reading/writing for better readability. 
public class Main {

	public static void main(String[] args) {
		//creates object for class that reads csv file and writes output file
		fileReadWrite test = new fileReadWrite();
		//runs object method to read file
		test.getArtistMap();
		//runs method to create Artist Linked List
		test.getArtistList();
		//prints number of artist
		System.out.println( "Number of artists: " + test.getNumberOfArtists());
		//creates and writes output file
		test.writeArtistListFile();
	}

}

//this class handles reading the input file and writing the output file
class fileReadWrite {
	//class variables that can only be accessed within the class
	private int numberOfArtists;
	//created HashMap as class variable to use within both read/write methods without creating a new HashMap
	private HashMap<String, Integer> hmap = new HashMap<String, Integer>();
	private TopStreamingArtists artistList = new TopStreamingArtists();

	//returns the value of the private variable numberOfArtists
	public int getNumberOfArtists() {
		return this.numberOfArtists;
	}
	
	//method to parse the csv file
	public void getArtistMap() {		
		try {
			//variable for name of csv file
			//uses actual file name since it is stored in the same folder, can also use file path if stored elsewhere 
			String fileName = "./regional-global-weekly-latest.csv";
			//Scanner with generic name to read from specified csv file
			Scanner scan = new Scanner(new File(fileName));
			//used to skip first line of file since it explains the file formatting
			scan.nextLine();
			while (scan.hasNextLine()) {
				//assigns the complete line from the csv file to a string
				String s = scan.nextLine();
				//array that contains the comma seperated values
				//regex is made to handle \" \" in lines to prevent improper formatting
				String[] fileLine = s.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				//assigns third element to variable. With proper formatting, the artist name should fall under the third element
				String artist = fileLine[2];
				//checks if artist is already listed in HashMap
				if (hmap.containsKey(artist)) {
					//increments the Integer value for the artist,
					hmap.replace(artist, hmap.get(artist) + 1);
				}
				//adds new artist to HashMap and increases number of artists
				else {
					hmap.put(artist, 1);
					numberOfArtists += 1;
				}
			}
			scan.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	//Adds artist nodes to list. Uses set of keys from existing Hash Map. Only creates sorted file
	public void getArtistList() {
		Set<String> artistNames = hmap.keySet();
		for (String name: artistNames) {
			artistList.sortedAdd(new Artist(name));
		}
	}

	//Method to create and write file with artist
	public void writeArtistMapFile() {
		try {
            //creates new file
		File file = new File("Artists-WeekOf09032020.txt");
		//PrintWriter object with generic name to write into the text file.
		PrintWriter pw = new PrintWriter(file);
		//obtains all keys in HashMap and stores in a set
		Set<String> keys = hmap.keySet();
		//writes number of artists in file
		pw.println("Number of artists: " + numberOfArtists);
		//used to explain formatting within file
		pw.println("Formatting for file: \rArtist Name: number of apperances");
		//loop to write artists into file along with their number of occurances
		//get method retrieves the element(number of appearances) from the key (artist)
		for (String test: keys) {
			pw.println(test +  ": " + hmap.get(test));
		}
		//used to indicate that file has been written
		System.out.println("The list of artist has been written");
		pw.close(); 
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	//creates file using Artist Linked List. Does not modify list
	public void writeArtistListFile() {
		try {
			//creates new file
			File file = new File("ArtistsSorted-WeekOf09032020.txt");
			//PrintWriter object with generic name to write into the text file.
			PrintWriter pw = new PrintWriter(file);
			pw.println("Sorted in Ascending order of name");
			Artist position = artistList.getFirst();
			while (position != null) {
				//writes artist name in file, then it continues to next node
				pw.println(position.getName());
				position = position.getNext();
			}
			//used to indicate that file has been written
			System.out.println("The list of artist has been written");
			pw.close(); 
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
}


//Artist class, nodes for list
class Artist {
	//stores Artist name and node for next artist in list
    private String name;
	private Artist next;

    public Artist(String artistName) {
        this.name = artistName;
		this.next = null;
    }

	//Assigns new value for next since the variable is private
    public void setNext(Artist artist) {
        this.next = artist;
	}
	
	//getter for next Artist in list since the variable is private
    public Artist getNext() {
        return this.next;
	}

    public String getName() {
        return this.name;
    }

 }

//linked list class for Artist list
class TopStreamingArtists {
    private Artist first;
	private int size;
	
    public TopStreamingArtists(){
      first = null;
    }

	//adds node as list head
    public void addFirst(Artist artist) {
		artist.setNext(first);
		first = artist;
        size += 1;
	}

	public Artist getFirst() {
		return this.first;
	}
	
	/*Adds artists into sorted order, follows bubblesort algorithm.
	Ignores case when comparing, but keeps case when adding into list
	**/
	public void sortedAdd(Artist artist) {
		//Artist variable to mark current node/position in list
		Artist position = first;
		//Stores artist name, sets to lowercase 
		String artistName = artist.getName().toLowerCase();
		//checks for empty list, doesn't update position to skip while loop
		if (this.isEmpty()) {
		    this.addFirst(artist);
		}
		//loops through the entire list till the end, or if it is forced out of the loop
		while (position != null) {
			String posName = position.getName().toLowerCase();
			//checks for insertion at head. If true, new node becomes the head of the list.
			if (artistName.compareTo(posName) <= 0 && position == first) {
				artist.setNext(first);
				first = artist;
				size += 1;
				//force out of loop once insertion is done
				position = null;
			} 
			//checks for insertion at tail. If true, new node becomes last element of list
			else if (artistName.compareTo(posName) >= 0 && position.getNext() == null) {
				position.setNext(artist);
				size += 1;
				position = null;
			}
			//checks for insertion within list. If true, new node is inserted between current node and next node. 
			else if (artistName.compareTo(posName) >= 0 && artistName.compareTo(position.getNext().getName().toLowerCase()) <= 0) {
				Artist temp = position.getNext();
				artist.setNext(temp);
				position.setNext(artist);
				size += 1;
				position = null;
			}
			//continues to next node if no cases are met.
			else {
				position = position.getNext();
			}
		}
	}

	//removes head of list. Second element becomes new head
    public Artist removeFirst() {
        Artist temp = this.first;
		first = temp.getNext();
        size -= 1;
        return temp;
    }

	//prints all elements of list, does not alter order
    public void displayList() {
        Artist currentPos = this.first;
        while (currentPos != null) {
            System.out.println(currentPos.getName());
            currentPos = currentPos.getNext();
        }
    }
	
	//tracks returns private size variable. Used to avoid looping through list if size is needed
    public int size() {
        return size;
	}
	
	//checks if list is empty
    public boolean isEmpty(){
     return (first == null);
    }
 }