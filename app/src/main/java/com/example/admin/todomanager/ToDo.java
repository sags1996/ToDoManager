package com.example.admin.todomanager;

/**
 * Created by Admin on 7/3/2017.
 */

class ToDo {
    int id;
    String title;
    String date;
    String category;

    public ToDo(int id, String title, String date, String category) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.category = category;
    }
}
