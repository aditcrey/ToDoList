package com.aditcrey.todoList.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;  //info about nio : https://docs.oracle.com/javase/7/docs/api/java/nio/package-summary.html
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by aditc on 29-12-2017.
 */
//This is a singleton class
public class TodoData {

    private static TodoData instance = new TodoData();
    private static String filename = "TodoListItems.txt";


    /**
     * why we are using observable list?
     * packages:
     * list -> java.util.Collections
     * observaleList ->javafx.collections
     *
     * we do it for performance reasons...observable list will raise events and because methods withing the list classes may
     * call each other when a list changed, it's impossible for more than 1 event to be raised for a single change...
     * for eg. if method a, b and c all raise events and when an item is added, method a calls b which calls c then 3 events
     * will be raised whenever an item is added and UI operations can be expensive and that's because the UI control has to paint
     * the screen...so we don't want a control like a listView to run its handler multiple times when an item is added or deleted....
     * ideally what we want is to run its handler once
     * Now, FXCollections package contains a copy of all the classes and static methods in java.util.collections package but the code has
     * been optimised to reduce a number of events or notifications raised when collections are changed
     * All methods are optimised in a way that only yield a limited numbers of notifications on the other hand, java.util.collections might call
     * modification methods or an observable list multiple times resulting in number of notifications
     * Documentation: https://docs.oracle.com/javase/8/javafx/api/javafx/collections/FXCollections.html
     */
    private ObservableList<ToDoItem> toDoItems;
//    private List<ToDoItem> toDoItems;  //before data binding
    private DateTimeFormatter formatter;

    public static TodoData getInstance(){ //public static mehtod to return the instance of this class
        return instance;
    }

    public void setToDoItems(ObservableList<ToDoItem> toDoItems) {
        this.toDoItems = toDoItems;
    }

    private TodoData(){
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<ToDoItem> getToDoItems() {
        return toDoItems;
    }


    public void addTodoItem(ToDoItem item){
        toDoItems.add(item);
    }



    //now we write the method to load the items from the file...we will write this using try finally syntax
    public void loadTodoItems() throws IOException{
        //we will buildup an arrayList of items

        toDoItems = FXCollections.observableArrayList(); //we are using an observableArrayList here because in the Controller class we've used setAll
        //method which is an ObservableArrayList method
        Path path = Paths.get(filename);  //specifying path to the file
        BufferedReader br = Files.newBufferedReader(path);

        String input;


        try{
            while((input = br.readLine()) != null){
                String[] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];


                LocalDate date = LocalDate.parse(dateString, formatter);
                ToDoItem toDoItem = new ToDoItem(shortDescription, details, date);
                toDoItems.add(toDoItem);

            }

        }finally{
            if(br != null){
                br.close();
            }
        }
    }



    public void storeTodoItems() throws IOException{
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);


        try{

            Iterator<ToDoItem> iter = toDoItems.iterator();
            while(iter.hasNext()){
                ToDoItem item = iter.next();
                bw.write(String.format("%s\t%s\t%s",
                        item.getShortDescription(),
                        item.getDetails(),
                        item.getDeadline().format(formatter)));
                bw.newLine();
            }


        }finally{
            if(bw!=null){
                bw.close();
            }
        }





    }



}
