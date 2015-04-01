package com.ace.legend.todo;

/**
 * Created by rohan on 3/30/15.
 */
public class ToDo {

    String title = null;
    String detail = null;
    String time = null;
    String date = null;

    public ToDo(){

    }

    public ToDo(String title){
        super();
        this.title = title;
    }

    public ToDo(String title, String detail) {
        super();
        this.title = title;
        this.detail = detail;
    }

    public ToDo(String title, String detail, String date, String time) {
        super();
        this.title = title;
        this.detail = detail;
        this.date = date;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
