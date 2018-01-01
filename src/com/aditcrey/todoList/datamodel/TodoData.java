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

    private ObservableList<ToDoItem> toDoItems;
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
