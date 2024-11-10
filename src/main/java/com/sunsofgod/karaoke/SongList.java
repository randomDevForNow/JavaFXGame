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
        // Classic Hits - Using official YouTube music videos or lyric videos
        addSong("001", "My Way", "Frank Sinatra", "_OnqmlQvWqs");
        addSong("002", "Yesterday", "The Beatles", "4YfL8RpCO6M");
        addSong("003", "Bohemian Rhapsody", "Queen", "axAtWjn3MfI");
        
        // Pop Hits - Official music videos with confirmed embedding
        addSong("101", "Shape of You", "Ed Sheeran", "_dK2tDK9grQ");
        addSong("102", "Rolling in the Deep", "Adele", "0LiTw9qWevw");
        addSong("103", "Uptown Funk", "Mark Ronson ft. Bruno Mars", "7Ya2U8XN_Zw");
        addSong("104", "Dance Monkey", "Tones and I", "q0hyYWKXF0Q");
        addSong("105", "Shake It Off", "Taylor Swift", "7yeFzwmzogg");
        
        // Rock Classics - Verified embeddable versions
        addSong("201", "Sweet Child O' Mine", "Guns N' Roses", "oK_XtfXPkqw");
        addSong("202", "Nothing Else Matters", "Metallica", "s6TtwR2Dbjg");
        addSong("203", "Hotel California", "Eagles", "09839DpTctU");
        addSong("204", "Sweet Dreams", "Eurythmics", "5GunXy_obuY");
        addSong("205", "Smells Like Teen Spirit", "Nirvana", "zYxkezUr8MQ");
        
        // Filipino Hits - Quality uploads with embedding enabled
        addSong("301", "Ang Huling El Bimbo", "Eraserheads", "HSlsCqrxGwo");
        addSong("302", "Harana", "Parokya Ni Edgar", "GM5fmj6XZtE");
        addSong("303", "214", "Rivermaya", "y0TIERXbGY4");
        addSong("304", "With A Smile", "Eraserheads", "8vbvGXNUJUg");
        addSong("305", "Perfect", "True Faith", "khrnpZY5FbQ");
        
        // Modern Pop - Verified working videos
        addSong("401", "Cruel Summer", "Taylor Swift", "6YEhcPcsqHw");
        addSong("402", "As It Was", "Harry Styles", "V_rS4UAzj9I");
        addSong("403", "good 4 u", "Olivia Rodrigo", "pi-GA0dZ6LQ");
        addSong("404", "Blinding Lights", "The Weeknd", "kh7cUiYodiI");
        addSong("405", "Stay With Me", "Sam Smith", "SQFqw-_5nXA");
        
        // Ballads - Confirmed embeddable versions
        addSong("501", "All of Me", "John Legend", "73_DOquGBD8");
        addSong("502", "Perfect", "Ed Sheeran", "iKzRIweSBLA");
        addSong("503", "Just the Way You Are", "Bruno Mars", "ZuJWQnaavTI");
        addSong("504", "Someone Like You", "Adele", "jD9dr2ZRm9A");
        addSong("505", "Say You Won't Let Go", "James Arthur", "PHqQRql0SLU");
        
        // Party Songs - Working embeds
        addSong("601", "I Gotta Feeling", "The Black Eyed Peas", "CwdrtwZiQ9E");
        addSong("602", "Don't Stop Believin'", "Journey", "9GDV_bKUxnY");
        addSong("603", "Sweet Caroline", "Neil Diamond", "p_LxnR5_ezc");
        addSong("604", "Y.M.C.A.", "Village People", "fY2iZcrTk6s");
        addSong("605", "Dancing Queen", "ABBA", "yhqV49us4mE");
        
        // Recent Hits - Confirmed working
        addSong("701", "Butter", "BTS", "YAXTn0E-Zgo");
        addSong("702", "Stay", "The Kid LAROI & Justin Bieber", "Ec7TN_11az8");
        addSong("703", "Easy On Me", "Adele", "X-yIEMduRXk");
        addSong("704", "Levitating", "Dua Lipa", "WHuBW3qLqj0");
        addSong("705", "Heat Waves", "Glass Animals", "KT7m9hb9_5I");
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