package com.aditcrey.todoList;

import com.aditcrey.todoList.datamodel.ToDoItem;
import com.aditcrey.todoList.datamodel.TodoData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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


//        ToDoItem item1 = new ToDoItem("Mail birthdaycard", "Buy a birthday card for John", LocalDate.of(2016, Month.APRIL, 25));
//        ToDoItem item2 = new ToDoItem("Doctor's appointment", "See doctor smith at 123, main street, pink pepperwood", LocalDate.of(2016, Month.MAY, 23));
//        ToDoItem item3 = new ToDoItem("Finish design proposal for client", "I promised Mike I'd email website mockups by Friday 22nd April", LocalDate.of(2016, Month.APRIL, 22));
//        ToDoItem item4 = new ToDoItem("Pickup Doug at the train station", "Doug's arriving on March 23 on the 5:00 train", LocalDate.of(2016, Month.MARCH, 23));
//        ToDoItem item5 = new ToDoItem("Pick up dry cleaning", "The clothes should be ready by wednesday", LocalDate.of(2016, Month.APRIL, 20));
//
//        toDoItems = new ArrayList<>();
//        toDoItems.add(item1);
//        toDoItems.add(item2);
//        toDoItems.add(item3);
//        toDoItems.add(item4);
//        toDoItems.add(item5);
//
//
//        TodoData.getInstance().setToDoItems(toDoItems); //this will create the file to be stored in for us //this will be later replaced by loading the items from the file instead of hardcoding
            //the above line was run only once to store the hardcoded items in a file for once...for all later uses, the items will be loaded from the file itself and no hardcoding will be needed

        /**
         * the following code creates a generic event handler which handles any time the value changes
         * matlab ki mouseClick ke alawa agar kisi aur tarike se bhi listItem select hota h to listener work karega...this was necessary since we were selecting the
         * first item in the listView when the application starts and the earlier event handler handled only when the item was selected onmouseClick which doesn't happen
         * when the application starts
         */
        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observable, ToDoItem oldValue, ToDoItem newValue) {
                if(newValue!=null){
                    ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
                    itemDetailTextArea.setText(item.getDetails());
                    //adding a date time formatter so that the date is easy to read //oracle document link: https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    deadlineLabel.setText(df.format(item.getDeadline()));
//                    deadlineLabel.setText(item.getDeadline().toString()); //earlier (before adding date formatter)
                }
            }
        });


//        todoListView.getItems().setAll(toDoItems);
        todoListView.getItems().setAll(TodoData.getInstance().getToDoItems());
        //now we can set the listview to single select or multi select i.e. the user is able to select only a single item or multiple items from the listView...here
        //we'll do the single select as follows
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //now just making the text area display the first item in the listview(otherwise the text area will remain blank initially)
        todoListView.getSelectionModel().selectFirst();


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
 *
 *
 * Now, the user would also need to be able to add items and the items should not get deleted after closing the application and should reappear on reopening the application
 * We need to override the stop method (of Application Class) which our Main class extends from to store the items in the listView...however the controller will need the data to
 * populate the listView and it already has access to listView's contents when we want to store them  so for experience sake and because we are doing this quick and dirty way so that
 * we can get back to UI coding what we'll do is we'll add a singleton class that both our main class and the controller class can access...in an nutshell, we will use a singleton
 * that we want to be there only one instance of class created over the entire run of the application...because of this the singleton class creates the one instance of itself
 *
 *A singleton class has a private constructor to ensure that no other class can create an instance...Usually a singleton class contains a static method that allows us to get the single
 * instance and call its methods
 */
