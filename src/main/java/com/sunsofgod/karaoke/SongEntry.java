package com.sunsofgod.karaoke;

public class SongEntry {
    private String code;
    private String title;
    private String artist;
    private String youtubeId;
    private String genre;

    public SongEntry(String code, String title, String artist, String youtubeId) {
        this.code = code;
        this.title = title;
        this.artist = artist;
        this.youtubeId = youtubeId;
    }

    // Getters
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getYoutubeId() { return youtubeId; }
    public String getGenre() { return genre; }

    // Setters
    public void setGenre(String genre) { this.genre = genre; }
}