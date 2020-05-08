package com.startng.newsapp;

public class RecyclerItems {
    private int deleteImage;
    private  String noteTitle, content,noteId;


    public RecyclerItems()
    {

    }

    public RecyclerItems(String noteId,int deleteImage , String noteTitle, String content)
    {
        this.deleteImage = deleteImage;
        this.noteTitle = noteTitle;
        this.noteId= noteId;
        this.content = content;
    }

    public int getDeleteImage() {
        return deleteImage;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteId() {
        return noteId;
    }

    public String getContent() {
        return content;
    }
}
