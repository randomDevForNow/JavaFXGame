// SongList.java
package com.sunsofgod.karaoke;

import java.util.HashMap;
import java.util.Map;

public class SongList {
    private static SongList instance;
    private Map<String, SongEntry> songDatabase;

    private SongList() {
        songDatabase = new HashMap<>();
        initializeSongDatabase();
    }

    public static SongList getInstance() {
        if (instance == null) {
            instance = new SongList();
        }
        return instance;
    }

    private void initializeSongDatabase() {
        // Classic Hits
        addSong("001", "My Way", "Frank Sinatra", "qQzdAsjWGPg");
        addSong("002", "Yesterday", "The Beatles", "NrgmdOz227I");
        addSong("003", "Bohemian Rhapsody", "Queen", "fJ9rUzIMcZQ");
        addSong("004", "DI KO NA MAPIPIGILAN", "SexBomb Dancers", "HNy41EFCJcs");
        
        // Pop Hits
        addSong("101", "Shape of You", "Ed Sheeran", "Vds8ddYXYZY");
        addSong("102", "Rolling in the Deep", "Adele", "AIYpdjQVidc");
        addSong("103", "Uptown Funk", "Mark Ronson ft. Bruno Mars", "7Ya2U8XN_Zw");
        addSong("104", "Dance Monkey", "Tones and I", "q0hyYWKXF0Q");
        addSong("105", "Shake It Off", "Taylor Swift", "zIOVMHMNfJ4");
        
        // Rock Classics
        addSong("201", "Sweet Child O' Mine", "Guns N' Roses", "qoflJn7zkFM");
        addSong("202", "Nothing Else Matters", "Metallica", "ozXZnwYTMbs");
        addSong("203", "Hotel California", "Eagles", "BciS5krYL80");
        addSong("204", "Sweet Dreams", "Eurythmics", "eRhg7qPLeN8");
        addSong("205", "Smells Like Teen Spirit", "Nirvana", "XJRbagz_-Zc");
        
        // Filipino Hits
        addSong("301", "Ang Huling El Bimbo", "Eraserheads", "ih_1qXYDXO4");
        addSong("302", "Harana", "Parokya Ni Edgar", "BtptZbO1lhk");
        addSong("303", "214", "Rivermaya", "d59MC-PEJK0");
        addSong("304", "With A Smile", "Eraserheads", "mTkK5PXz_hY");
        addSong("305", "Perfect", "True Faith", "WlU0a5sRm_A");
        
        // Modern Pop
        addSong("401", "Cruel Summer", "Taylor Swift", "ic8j13piAhQ");
        addSong("402", "As It Was", "Harry Styles", "Qfm6nfz1QNQ");
        addSong("403", "good 4 u", "Olivia Rodrigo", "o6Tf-xcVB_o");
        addSong("404", "Blinding Lights", "The Weeknd", "fHI8X4OXluQ");
        addSong("405", "Stay With Me", "Sam Smith", "bXDSR4GggUU");
        
        // Ballads
        addSong("501", "All of Me", "John Legend", "ngq5Aw0Q6rQ");
        addSong("502", "Perfect", "Ed Sheeran", "cNGjD0VG4R8");
        addSong("503", "Just the Way You Are", "Bruno Mars", "u7XjPmN-tHw");
        addSong("504", "Someone Like You", "Adele", "22c3_LoIfZQ");
        addSong("505", "Say You Won't Let Go", "James Arthur", "8A7-54UguKg");
        
        // Party Songs
        addSong("601", "I Gotta Feeling", "The Black Eyed Peas", "CwdrtwZiQ9E");
        addSong("602", "Don't Stop Believin'", "Journey", "c8wn2fMYvns");
        addSong("603", "Sweet Caroline", "Neil Diamond", "4F_RCWVoL4s");
        addSong("604", "Y.M.C.A.", "Village People", "O0wBUnAeF6k");
        addSong("605", "Dancing Queen", "ABBA", "xFrGuyw1V8s");
        
        // Recent Hits
        addSong("701", "Butter", "BTS", "WMweEpGlu_U");
        addSong("702", "Stay", "The Kid LAROI & Justin Bieber", "Ec7TN_11az8");
        addSong("703", "Easy On Me", "Adele", "X-yIEMduRXk");
        addSong("704", "Levitating", "Dua Lipa", "yB6MCcaFMEo");
        addSong("705", "Heat Waves", "Glass Animals", "KT7F15T9VBI");
    }
    
    public void addSong(String code, String title, String artist, String youtubeId) {
        SongEntry song = new SongEntry(code, title, artist, youtubeId);
        // Add genre based on code prefix
        switch (code.charAt(0)) {
            case '0': song.setGenre("Classic Hits"); break;
            case '1': song.setGenre("Pop Hits"); break;
            case '2': song.setGenre("Rock Classics"); break;
            case '3': song.setGenre("Filipino Hits"); break;
            case '4': song.setGenre("Modern Pop"); break;
            case '5': song.setGenre("Ballads"); break;
            case '6': song.setGenre("Party Songs"); break;
            case '7': song.setGenre("Recent Hits"); break;
        }
        songDatabase.put(code, song);
    }

    public SongEntry getSong(String code) {
        return songDatabase.get(code);
    }

    public boolean hasSong(String code) {
        return songDatabase.containsKey(code);
    }

    // Helper method to get all songs in a specific genre
    public Map<String, SongEntry> getSongsByGenre(String genre) {
        Map<String, SongEntry> genreSongs = new HashMap<>();
        for (Map.Entry<String, SongEntry> entry : songDatabase.entrySet()) {
            if (entry.getValue().getGenre().equals(genre)) {
                genreSongs.put(entry.getKey(), entry.getValue());
            }
        }
        return genreSongs;
    }

    // Helper method to get all available genres
    public java.util.Set<String> getAvailableGenres() {
        java.util.Set<String> genres = new java.util.HashSet<>();
        for (SongEntry song : songDatabase.values()) {
            genres.add(song.getGenre());
        }
        return genres;
    }
}