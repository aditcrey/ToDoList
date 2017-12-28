package com.aditcrey.todoList;

import com.aditcrey.todoList.datamodel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<ToDoItem> toDoItems;
    @FXML
    private ListView<ToDoItem> todoListView;
    @FXML
    private TextArea itemDetailTextArea;
    @FXML
    private Label deadlineLabel;

    public void initialize(){
        ToDoItem item1 = new ToDoItem("Mail birthdaycard", "Buy a birthday card for John", LocalDate.of(2016, Month.APRIL, 25));
        ToDoItem item2 = new ToDoItem("Doctor's appointment", "See doctor smith at 123, main street, pink pepperwood", LocalDate.of(2016, Month.MAY, 23));
        ToDoItem item3 = new ToDoItem("Finish design proposal for client", "I promised Mike I'd email website mockups by Friday 22nd April", LocalDate.of(2016, Month.APRIL, 22));
        ToDoItem item4 = new ToDoItem("Pickup Doug at the train station", "Doug's arriving on March 23 on the 5:00 train", LocalDate.of(2016, Month.MARCH, 23));
        ToDoItem item5 = new ToDoItem("Pick up dry cleaning", "The clothes should be ready by wednesday", LocalDate.of(2016, Month.APRIL, 20));

        toDoItems = new ArrayList<>();
        toDoItems.add(item1);
        toDoItems.add(item2);
        toDoItems.add(item3);
        toDoItems.add(item4);
        toDoItems.add(item5);

        todoListView.getItems().setAll(toDoItems);
        //now we can set the listview to single select or multi select i.e. the user is able to select only a single item or multiple items from the listView...here
        //we'll do the single select as follows
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }


    //SelectionModel class handles which control is selected in the UI
    @FXML
    public void handleClickListView(){
//        ToDoItem item = (ToDoItem) todoListView.getSelectionModel().getSelectedItem(); //we need to cast here since we didn't specify type parameter in the ListView
        ToDoItem item = todoListView.getSelectionModel().getSelectedItem();  //after specifying the type parameter in the listView

        itemDetailTextArea.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());


////        System.out.println("The selected item is " + item);
//        /**
//         * using String builder
//         */
//        StringBuilder sb = new StringBuilder(item.getDetails());
//        sb.append("\n\n\n\n");
//        sb.append("Due: ");
//        sb.append(item.getDeadline().toString());
//        itemDetailTextArea.setText(sb.toString());

//        itemDetailTextArea.setText(item.getDetails()); //earlier without string builder


    }

}

/**
 * now if we want to bold the text then we will have to put it outside the text area since text area only understands plain text so what we can do
 * is instead of the big text area, add a VBox and then inside that add a text area and a hbox
 */
