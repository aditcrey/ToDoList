package com.aditcrey.todoList.datamodel;

import java.time.LocalDate;

/**
 * Created by aditc on 27-12-2017.
 */
public class ToDoItem {
    private String shortDescription;
    private String details;
    private LocalDate deadline;

    public ToDoItem(String shortDescription, String details, LocalDate deadline) {
        this.shortDescription = shortDescription;
        this.details = details;
        this.deadline = deadline;
    }

    public String getShortDescription() {
        return shortDescription;
    }



    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }


//    @Override                     //no longer needed since we are using cellFactory to set text to the list view
//    public String toString() {
//        return shortDescription;
//    }
}
