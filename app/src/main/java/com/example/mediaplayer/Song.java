package com.example.mediaplayer;

public class Song {
    private String song;//歌曲
    private int length;//长度
    private String path;//路径

    public Song(){
        super();
    }
    public Song(String song,int length,String path){
        this.song=song;
        this.length=length;
        this.path=path;
    }

    public void setSong(String song) {
        this.song = song;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void setLength(int length) {
        this.length = length;
    }

    public String getSong() {
        return song;
    }
    public String getPath() {
        return path;
    }
    public int getLength() {
        return length;
    }
}
