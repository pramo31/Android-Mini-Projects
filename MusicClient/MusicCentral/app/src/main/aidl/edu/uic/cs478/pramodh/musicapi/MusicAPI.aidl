// MusicAPI.aidl
package edu.uic.cs478.pramodh.musicapi;

import edu.uic.cs478.pramodh.musicapi.Song;
// Declare any non-default types here with import statements

interface MusicAPI {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    List<Song> getAllSongs();
    Song getSong(int index);
    String getSongUrl(int index);
}
