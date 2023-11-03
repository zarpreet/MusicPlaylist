package music;

import java.util.*;

/**
 * This class represents a library of song playlists.
 *
 * An ArrayList of Playlist objects represents the various playlists 
 * within one's library.
 * 
 * @author Jeremy Hui
 * @author Vian Miranda
 */
public class PlaylistLibrary {

    private ArrayList<Playlist> songLibrary; // contains various playlists

    /**
     * DO NOT EDIT!
     * Constructor for Library.
     * 
     * @param songLibrary passes in ArrayList of playlists
     */
    public PlaylistLibrary(ArrayList<Playlist> songLibrary) {
        this.songLibrary = songLibrary;
    }

    /**
     * DO NOT EDIT!
     * Default constructor for an empty library. 
     */
    public PlaylistLibrary() {
        this(null);
    }

    /**
     * This method reads the songs from an input csv file, and creates a 
     * playlist from it.
     * Each song is on a different line.
     * 
     * 1. Open the file using StdIn.setFile(filename);
     * 
     * 2. Declare a reference that will refer to the last song of the circular linked list.
     * 
     * 3. While there are still lines in the input file:
     *      1. read a song from the file
     *      2. create an object of type Song with the song information
     *      3. Create a SongNode object that holds the Song object from step 3.2.
     *      4. insert the Song object at the END of the circular linked list (use the reference from step 2)
     *      5. increase the count of the number of songs
     * 
     * 4. Create a Playlist object with the reference from step (2) and the number of songs in the playlist
     * 
     * 5. Return the Playlist object
     * 
     * Each line of the input file has the following format:
     *      songName,artist,year,popularity,link
     * 
     * To read a line, use StdIn.readline(), then use .split(",") to extract 
     * fields from the line.
     * 
     * If the playlist is empty, return a Playlist object with null for its last, 
     * and 0 for its size.
     * 
     * The input file has Songs in decreasing popularity order.
     * 
     * DO NOT implement a sorting method, PRACTICE add to end.
     * 
     * @param filename the playlist information input file
     * @return a Playlist object, which contains a reference to the LAST song 
     * in the ciruclar linkedlist playlist and the size of the playlist.
     */
    public Playlist createPlaylist(String filename) {

    // Initialize variables
    SongNode lastSong = null;
    int songCount = 0;

    // Open the file
    StdIn.setFile(filename);

    // Read the file line by line
    while (!StdIn.isEmpty()) {
        // Read a line and split it by ","
        String[] songData = StdIn.readLine().split(",");
        String songName = songData[0];
        String artist = songData[1];
        int year = Integer.parseInt(songData[2]);
        int popularity = Integer.parseInt(songData[3]);
        String link = songData[4];

        // Create a Song object
        Song song = new Song(songName, artist, year, popularity, link);

        // Create a SongNode with the Song object
        SongNode songNode = new SongNode(song, null);

        // Insert the SongNode at the end of the circular linked list
        if (lastSong == null) {
            lastSong = songNode;
            lastSong.setNext(songNode); // Circular reference to itself for the first node
        } else {
            songNode.setNext(lastSong.getNext());
            lastSong.setNext(songNode);
            lastSong = songNode;
        }

        // Increment the song count
        songCount++;
    }

    // Create a Playlist object with the lastSong reference and songCount
    Playlist playlist = new Playlist();
    playlist.setLast(lastSong);
    playlist.setSize(songCount);

    // Return the Playlist object
    return playlist;
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you. 
     * 
     * Adds a new playlist into the song library at a certain index.
     * 
     * 1. Calls createPlayList() with a file containing song information.
     * 2. Adds the new playlist created by createPlayList() into the songLibrary.
     * 
     * Note: initialize the songLibrary if it is null
     * 
     * @param filename the playlist information input file
     * @param playlistIndex the index of the location where the playlist will 
     * be added 
     */
    public void addPlaylist(String filename, int playlistIndex) {
        
        /* DO NOT UPDATE THIS METHOD */

        if ( songLibrary == null ) {
            songLibrary = new ArrayList<Playlist>();
        }
        if ( playlistIndex >= songLibrary.size() ) {
            songLibrary.add(createPlaylist(filename));
        } else {
            songLibrary.add(playlistIndex, createPlaylist(filename));
        }        
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you.
     * 
     * It takes a playlistIndex, and removes the playlist located at that index.
     * 
     * @param playlistIndex the index of the playlist to remove
     * @return true if the playlist has been deleted
     */
    public boolean removePlaylist(int playlistIndex) {
        /* DO NOT UPDATE THIS METHOD */

        if ( songLibrary == null || playlistIndex >= songLibrary.size() ) {
            return false;
        }

        songLibrary.remove(playlistIndex);
            
        return true;
    }
    
    /** 
     * 
     * Adds the playlists from many files into the songLibrary
     * 
     * 1. Initialize the songLibrary
     * 
     * 2. For each of the filenames
     *       add the playlist into songLibrary
     * 
     * The playlist will have the same index in songLibrary as it has in
     * the filenames array. For example if the playlist is being created
     * from the filename[i] it will be added to songLibrary[i]. 
     * Use the addPlaylist() method. 
     * 
     * @param filenames an array of the filenames of playlists that should be 
     * added to the library
     */
    public void addAllPlaylists(String[] filenames) {
        
    // Initialize the songLibrary
    songLibrary = new ArrayList<Playlist>();

    // For each filename, add the playlist to songLibrary
    for (int i = 0; i < filenames.length; i++) {
        addPlaylist(filenames[i], i);
    }
    }

    /**
     * This method adds a song to a specified playlist at a given position.
     * 
     * The first node of the circular linked list is at position 1, the 
     * second node is at position 2 and so forth.
     * 
     * Return true if the song can be added at the given position within the 
     * specified playlist (and thus has been added to the playlist), false 
     * otherwise (and the song will not be added). 
     * 
     * Increment the size of the playlist if the song has been successfully
     * added to the playlist.
     * 
     * @param playlistIndex the index where the playlist will be added
     * @param position the position inthe playlist to which the song 
     * is to be added 
     * @param song the song to add
     * @return true if the song can be added and therefore has been added, 
     * false otherwise. 
     */
    public boolean insertSong(int playlistIndex, int position, Song song) {
        // WRITE YOUR CODE HERE

    if (position < 1) {
        // Cannot insert the song at a position less than 1
        return false;
    }

    Playlist playlist = songLibrary.get(playlistIndex);
    SongNode current = playlist.getLast(); // Start at the last node
    SongNode newNode = new SongNode(song, null);

    if (current == null) {
        // If the playlist is empty, set newNode as the only node
        newNode.setNext(newNode);
        playlist.setLast(newNode);
    } else {
        // Find the node at the target position
        for (int i = 1; i < position; i++) {
            current = current.getNext();
        }
        // Insert the song after the current node
        newNode.setNext(current.getNext());
        current.setNext(newNode);
        // Update last node reference if the position is at the end
        if (position == playlist.getSize()) {
            playlist.setLast(newNode);
        }
    }

    playlist.setSize(playlist.getSize() + 1); // Increment the size

    return true;
    }

    /**
     * This method removes a song at a specified playlist, if the song exists. 
     *
     * Use the .equals() method of the Song class to check if an element of 
     * the circular linkedlist matches the specified song.
     * 
     * Return true if the song is found in the playlist (and thus has been 
     * removed), false otherwise (and thus nothing is removed). 
     * 
     * Decrease the playlist size by one if the song has been successfully
     * removed from the playlist.
     * 
     * @param playlistIndex the playlist index within the songLibrary where 
     * the song is to be added.
     * @param song the song to remove.
     * @return true if the song is present in the playlist and therefore has 
     * been removed, false otherwise.
     */
    public boolean removeSong(int playlistIndex, Song song) {
        // WRITE YOUR CODE HERE
        Playlist playlist = songLibrary.get(playlistIndex);
        SongNode currentNode = playlist.getLast().getNext();
        SongNode lastNode = playlist.getLast(); 
        SongNode firstNode = currentNode;
        boolean removed = false;

        if(playlist.getSize()==0){//if playlist empty

            return false;
        }
        do {
            if (song.equals(currentNode.getSong())) {
                lastNode.setNext(currentNode.getNext());// removes current song from list
                //if current song is last in the list
                if (currentNode == playlist.getLast()){
                    playlist.setLast(lastNode);
                }
                
                removed = true;
                 playlist.setSize(playlist.getSize() - 1);

                break;
            }
            lastNode = currentNode;
            currentNode =currentNode.getNext();
        } while (currentNode != firstNode);//traverses thru the linked list

        return removed; // update the return value
    }



    /**
     * This method reverses the playlist located at playlistIndex
     * 
     * Each node in the circular linked list will point to the element that 
     * came before it.
     * 
     * After the list is reversed, the playlist located at playlistIndex will 
     * reference the first SongNode in the original playlist (new last).
     * 
     * @param playlistIndex the playlist to reverse
     */
    public void reversePlaylist(int playlistIndex) {
        // WRITE YOUR CODE HERE
        
    Playlist currentPlaylist = songLibrary.get(playlistIndex);

    if (currentPlaylist == null || currentPlaylist.getLast() == null || currentPlaylist.getSize() == 0) {
        return; // Nothing to reverse
    }

    SongNode firstNode = currentPlaylist.getLast().getNext(); // Store the first node

    // Traverse the playlist and update the next pointers to reverse the list
    SongNode currentNode = currentPlaylist.getLast().getNext();
    SongNode previousNode = currentPlaylist.getLast(); // Start with last node as previous

    do {
        SongNode nextNode = currentNode.getNext();
        currentNode.setNext(previousNode);
        previousNode = currentNode;
        currentNode = nextNode;
    } while (currentNode != firstNode);

    // Update the last reference to point to the original first node (original last node)
    currentPlaylist.setLast(firstNode);  

    }

    /**
     * This method merges two playlists.
     * 
     * Both playlists have songs in decreasing popularity order. The resulting 
     * playlist will also be in decreasing popularity order.
     * 
     * You may assume both playlists are already in decreasing popularity 
     * order. If the songs have the same popularity, add the song from the 
     * playlist with the lower playlistIndex first.
     * 
     * After the lists have been merged:
     *  - store the merged playlist at the lower playlistIndex
     *  - remove playlist at the higher playlistIndex 
     * 
     * 
     * @param playlistIndex1 the first playlist to merge into one playlist
     * @param playlistIndex2 the second playlist to merge into one playlist
     */ 



     public void mergePlaylists(int playlistIndex1, int playlistIndex2) {
        // [Step 1] Identify lower and higher index playlists
        int lowerIndex = Math.min(playlistIndex1, playlistIndex2);
        int higherIndex = Math.max(playlistIndex1, playlistIndex2);

        Playlist lowerPlaylist = songLibrary.get(lowerIndex);
        Playlist higherPlaylist = songLibrary.get(higherIndex);

        // [Step 2] Declare references for merged playlist
        SongNode mergedLast = null;
        SongNode mergedFirst = null;

        int totalSongs = lowerPlaylist.getSize() + higherPlaylist.getSize();

        // [Step 3 & 4] While both playlists have songs, compare and move the higher popularity song
        while (lowerPlaylist.getSize() > 0 && higherPlaylist.getSize() > 0) {
            SongNode lowerFirst = lowerPlaylist.getLast().getNext();
            SongNode higherFirst = higherPlaylist.getLast().getNext();

            boolean useLower = lowerFirst.getSong().getPopularity() >= higherFirst.getSong().getPopularity();

            SongNode toMove;
            if (useLower) {
                toMove = lowerFirst;
                lowerPlaylist.getLast().setNext(lowerFirst.getNext());
                if (lowerFirst == lowerPlaylist.getLast()) {
                    lowerPlaylist.setLast(null);
                }
                lowerPlaylist.setSize(lowerPlaylist.getSize() - 1);
            } else {
                toMove = higherFirst;
                higherPlaylist.getLast().setNext(higherFirst.getNext());
                if (higherFirst == higherPlaylist.getLast()) {
                    higherPlaylist.setLast(null);
                }
                higherPlaylist.setSize(higherPlaylist.getSize() - 1);
            }

            if (mergedLast == null) {
                mergedLast = toMove;
                mergedFirst = toMove;
                toMove.setNext(toMove);
            } else {
                toMove.setNext(mergedFirst);
                mergedLast.setNext(toMove);
                mergedLast = toMove;
            }
        }

        // [Step 5] Append the non-empty playlist to the merged playlist
        Playlist nonEmptyPlaylist = lowerPlaylist.getSize() > 0 ? lowerPlaylist : higherPlaylist;
        if (mergedLast == null) {
            mergedLast = nonEmptyPlaylist.getLast();
            mergedFirst = nonEmptyPlaylist.getLast().getNext();
        } else if (nonEmptyPlaylist.getLast() != null) {
            mergedLast.setNext(nonEmptyPlaylist.getLast().getNext());
            nonEmptyPlaylist.getLast().setNext(mergedFirst);
        }

        // [Step 6] Assign the merged playlist to the lower index and remove the higher index playlist
        lowerPlaylist.setLast(mergedLast);
        lowerPlaylist.setSize(lowerPlaylist.getSize() + higherPlaylist.getSize());
        removePlaylist(higherIndex);

        // [Additional Step] Ensure the first song is the one with the highest popularity
        if (mergedLast != null) {
            SongNode current = mergedLast.getNext(); // Start from the first song
            SongNode highestPopularityNode = mergedLast;
            int highestPopularity = mergedLast.getSong().getPopularity();

            do {
                if (current.getSong().getPopularity() > highestPopularity) {
                    highestPopularity = current.getSong().getPopularity();
                    highestPopularityNode = current;
                }
                current = current.getNext();
            } while (current != mergedLast.getNext()); // Loop until we've checked all songs

            // Find the node that points to the highest popularity node
            SongNode pointerToHighestPopularityNode = mergedLast;
            while (pointerToHighestPopularityNode.getNext() != highestPopularityNode) {
                pointerToHighestPopularityNode = pointerToHighestPopularityNode.getNext();
            }

            // Set the last node in the playlist to be the one that points to the highest popularity song
            lowerPlaylist.setLast(pointerToHighestPopularityNode);
        }

        lowerPlaylist.setSize(totalSongs);

    }






// public void mergePlaylists(int playlistIndex1, int playlistIndex2) {



//     Playlist playlist1 = songLibrary.get(playlistIndex1);
//     Playlist playlist2 = songLibrary.get(playlistIndex2);
//     SongNode mergeL = new SongNode();
//     SongNode is = new SongNode();
//     SongNode curr = mergeL;
//     SongNode ptr1 = playlist1.getLast().getNext();
//     SongNode ptr2 = playlist2.getLast().getNext();
//     int lowerIndex = Math.min(playlistIndex1,playlistIndex2); 
//     int higherIndex = Math.max(playlistIndex1, playlistIndex2);

//     int counter = 0;

//     while (playlist1.getSize() !=0 || playlist2.getSize() != 0){
//         if (playlist1.getSize() !=0 && playlist2.getSize() !=0 ){
//             if (ptr1.getSong().getPopularity() > ptr2.getSong().getPopularity()){
//                 is = ptr1;
//                 ptr1 = ptr1.getNext();
//                 playlist1.setSize(playlist1.getSize()-1);
//             } else if (ptr1.getSong().getPopularity() < ptr2.getSong().getPopularity()){
//                 is = ptr2;
//                 ptr2 = ptr1.getNext();
//                 playlist2.setSize(playlist1.getSize()-1);
//             } else if (ptr1.getSong().getPopularity() == ptr2.getSong().getPopularity()){
//                 if (lowerIndex == playlistIndex1){
//                     is = ptr1;
//                     ptr1 = ptr1.getNext();
//                     playlist1.setSize(playlist1.getSize()-1);
//                 } else if (lowerIndex == playlistIndex2){
//                     is = ptr2;
//                     ptr2 = ptr1.getNext();
//                     playlist2.setSize(playlist1.getSize()-1);
//                 }
//             }
//         } else if (playlist1.getSize() == 0 && playlist2.getSize() != 0){
//             is = ptr2;
//             ptr2 = ptr1.getNext();
//             playlist2.setSize(playlist1.getSize()-1);
//         } else if (playlist1.getSize() != 0 && playlist2.getSize() == 0){
//             is = ptr1;
//             ptr1 = ptr1.getNext();
//             playlist1.setSize(playlist1.getSize()-1);
//         }

//         if (mergeL.getSong() == null){
//             mergeL =is;
//             curr = mergeL; 
//             counter++;
//         } else {
//             curr.setNext(is);
//             curr = curr.getNext();
//             counter++;
//         }
//     }

//     curr.setNext(mergeL);
//     songLibrary.get(lowerIndex).setSize(counter);
//     songLibrary.get(lowerIndex).setLast(curr);
//     removePlaylist(higherIndex);
// }




//     Playlist playlist1 = songLibrary.get(playlistIndex1);
//     Playlist playlist2 = songLibrary.get(playlistIndex2);

//     if (playlist1 == null || playlist2 == null) {
//         return;
//     }

//     int lowerIndex = Math.min(playlistIndex1, playlistIndex2);
//     int higherIndex = Math.max(playlistIndex1, playlistIndex2);
//     if
//         (playlist1.getSize() == 0) {
//         songLibrary.set(lowerIndex, playlist2);
//         songLibrary. remove(higherIndex);
//     }
//     else if (playlist2.getSize() == 0) {
//         songLibrary.set(lowerIndex, playlist1); songLibrary.remove(higherIndex);
//     } else {

//     SongNode lastNode1 = playlist1.getLast();
//     SongNode lastNode2 = playlist2.getLast();

    // if (lastNode1 == null || lastNode2 == null) {
    //     return; // One of the playlists is empty
    // }

//     SongNode current1 = lastNode1.getNext();
//     SongNode current2 = lastNode2.getNext();

//     int totalSongs = playlist1.getSize() + playlist2.getSize(); // To keep track of the total songs
    
// while (current1 != lastNode1 && current2 != lastNode2) {
//     if (current1.getSong().getPopularity() >= current2.getSong().getPopularity()) {
//         SongNode song1 = current1.getNext();
//         lastNode1.setNext(current1);
//         lastNode1 = current1;
//         current1 = song1;
//     } else {
//         SongNode song2 = current2.getNext();
//         lastNode1.setNext(current2);
//         lastNode1 = current2;
//         current2 = song2;
//     }
// }

//     if (current1.getSong().getPopularity() >= current2.getSong().getPopularity()) {
//         SongNode song1 = current1.getNext();
//         lastNode1.setNext(current1);
//         lastNode1 = current1;
//         current1 = song1;
//     } else {
//         SongNode song2 = current2.getNext();
//         lastNode1.setNext(current2);
//         lastNode1 = current2;
//         current2 = song2;
//     }



// if (current1 != lastNode1) {
//     lastNode1.setNext(current1);
// } else {
//     lastNode1.setNext(current2);
//     lastNode1 = lastNode2; // Update lastNode1 to point to the lastNode2
// }




//     // Update the size of playlist1
//     playlist1.setSize(totalSongs);

//     // Remove playlist2
//     removePlaylist(playlistIndex2);
// }
// }






    


    /**
     * This method shuffles a specified playlist using the following procedure:
     * 
     * 1. Create a new playlist to store the shuffled playlist in.
     * 
     * 2. While the size of the original playlist is not 0, randomly generate a number 
     * using StdRandom.uniformInt(1, size+1). Size contains the current number
     * of items in the original playlist.
     * 
     * 3. Remove the corresponding node from the original playlist and insert 
     * it into the END of the new playlist (1 being the first node, 2 being the 
     * second, etc). 
     * 
     * 4. Update the old playlist with the new shuffled playlist.
     *    
     * @param index the playlist to shuffle in songLibrary
     */



public void shufflePlaylist(int playlistIndex) {
    // Get the playlist at the specified index
    Playlist playlistToShuffle = songLibrary.get(playlistIndex);
    int totalSongs = playlistToShuffle.getSize();

    // Create a new playlist to store the shuffled playlist
    Playlist shuffledPlaylist = new Playlist();

    // Get the size of the original playlist
    int size = playlistToShuffle.getSize();

    // Seed the random number generator
    StdRandom.setSeed(2023);

    // Shuffle the playlist
    while (size > 0) {
        // Generate a random number between 1 and size (inclusive)
        int randomIndex = StdRandom.uniformInt(1, size + 1);

        // Remove the corresponding node from the original playlist
        SongNode current = playlistToShuffle.getLast().getNext(); // Start at the first song
        SongNode prev = playlistToShuffle.getLast(); // Initialize prev to the last song
        SongNode toMove = null; // Node to move to the shuffled playlist

        // If removing the first song
        if (randomIndex == 1) {
            toMove = current;
            playlistToShuffle.getLast().setNext(current.getNext());
            if (current == playlistToShuffle.getLast()) { // If removing the last remaining song
                playlistToShuffle.setLast(null);
            }
        } else {
            // Traverse to the song to remove
            for (int i = 1; i < randomIndex; i++) {
                prev = current;
                current = current.getNext();
            }
            toMove = current;
            if (current == playlistToShuffle.getLast()) { // If removing the last song
                playlistToShuffle.setLast(prev);
            }
            prev.setNext(current.getNext());
        }

        // Insert the removed node into the END of the new playlist
        if (shuffledPlaylist.getLast() == null) {
            shuffledPlaylist.setLast(toMove);
            toMove.setNext(toMove);
        } else {
            toMove.setNext(shuffledPlaylist.getLast().getNext());
            shuffledPlaylist.getLast().setNext(toMove);
        }

        // Update the size
        size--;
    }
    shuffledPlaylist.setSize(totalSongs);
    playlistToShuffle = shuffledPlaylist;

    // Update the old playlist with the new shuffled playlist
    songLibrary.set(playlistIndex, playlistToShuffle);
}



// public void shufflePlaylist(int playlistIndex) {
//     Playlist thislist = songLibrary.get(playlistIndex);
//     //Playlist shuffled = new Playlist(null,0);

//     SongNode prev = thislist.getLast();
//     SongNode cur = null;

//     StdRandom.setSeed(2023);
//     int or = thislist.getSize();
//     while (thislist.getSize() > 0) {
//         prev=thislist.getLast();
//         cur = thislist.getLast().getNext();
//         int rand = StdRandom.uniformInt(1, thislist.getSize() + 1);
//         for (int i = 1; i < rand; i++) {
//             prev = cur;
//             cur = cur.getNext();
//         }
        
//         prev.setNext(cur.getNext());
//         cur.setNext(thislist.getLast().getNext());
//         thislist.getLast().setNext(cur);
//         thislist.setLast(cur);
//         thislist.setSize(thislist.getSize()-1);
//         //SongNode current = new SongNode(cur.getSong(), null);
//         // if (shuffled.getLast() == null) {
//         //     current.setNext(current);
//         //     shuffled.setLast(current);
//         //     prev = current;
//         // } else {
//         //     current.setNext(shuffled.getLast().getNext());
//         //     prev.setNext(current);
//         //     shuffled.setLast(current);
//         //     prev = current;
//         // }

//     }
//     thislist.setSize(or);


// }









    /**
     * This method sorts a specified playlist using linearithmic sort.
     * 
     * Set the playlist located at the corresponding playlistIndex
     * in decreasing popularity index order.
     * 
     * This method should  use a sort that has O(nlogn), such as with merge sort.
     * 
     * @param playlistIndex the playlist to shuffle
     */
    public void sortPlaylist ( int playlistIndex ) {

        // WRITE YOUR CODE HERE
        
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Plays playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     * @param repeats number of times to repeat playlist
     * @throws InterruptedException
     */
    public void playPlaylist(int playlistIndex, int repeats) {
        /* DO NOT UPDATE THIS METHOD */

        final String NO_SONG_MSG = " has no link to a song! Playing next...";
        if (songLibrary.get(playlistIndex).getLast() == null) {
            StdOut.println("Nothing to play.");
            return;
        }

        SongNode ptr = songLibrary.get(playlistIndex).getLast().getNext(), first = ptr;

        do {
            StdOut.print("\r" + ptr.getSong().toString());
            if (ptr.getSong().getLink() != null) {
                StdAudio.play(ptr.getSong().getLink());
                for (int ii = 0; ii < ptr.getSong().toString().length(); ii++)
                    StdOut.print("\b \b");
            }
            else {
                StdOut.print(NO_SONG_MSG);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException ex) {
                    ex.printStackTrace();
                }
                for (int ii = 0; ii < NO_SONG_MSG.length(); ii++)
                    StdOut.print("\b \b");
            }

            ptr = ptr.getNext();
            if (ptr == first) repeats--;
        } while (ptr != first || repeats > 0);
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Prints playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     */
    public void printPlaylist(int playlistIndex) {
        StdOut.printf("%nPlaylist at index %d (%d song(s)):%n", playlistIndex, songLibrary.get(playlistIndex).getSize());
        if (songLibrary.get(playlistIndex).getLast() == null) {
            StdOut.println("EMPTY");
            return;
        }
        SongNode ptr;
        for (ptr = songLibrary.get(playlistIndex).getLast().getNext(); ptr != songLibrary.get(playlistIndex).getLast(); ptr = ptr.getNext() ) {
            StdOut.print(ptr.getSong().toString() + " -> ");
        }
        if (ptr == songLibrary.get(playlistIndex).getLast()) {
            StdOut.print(songLibrary.get(playlistIndex).getLast().getSong().toString() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    public void printLibrary() {
        if (songLibrary.size() == 0) {
            StdOut.println("\nYour library is empty!");
        } else {
                for (int ii = 0; ii < songLibrary.size(); ii++) {
                printPlaylist(ii);
            }
        }
    }

    /*
     * Used to get and set objects.
     * DO NOT edit.
     */
     public ArrayList<Playlist> getPlaylists() { return songLibrary; }
     public void setPlaylists(ArrayList<Playlist> p) { songLibrary = p; }
}
