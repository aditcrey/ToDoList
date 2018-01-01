package com.aditcrey.todoList;

import com.aditcrey.todoList.datamodel.ToDoItem;
import com.aditcrey.todoList.datamodel.TodoData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    private List<ToDoItem> toDoItems;

    @FXML
    private ListView<ToDoItem> todoListView;

    @FXML
    private TextArea itemDetailTextArea;

    @FXML
    private Label deadlineLabel;

    @FXML
    private BorderPane mainBorderPane;


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
            //The above line was run only once to store the hardcoded items in a file for once...for all later uses, the items will be loaded from the file itself and no hardcoding will be needed

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
        todoListView.getItems().setAll(TodoData.getInstance().getToDoItems()); //this will read from the file
        //now we can set the listview to single select or multi select i.e. the user is able to select only a single item or multiple items from the listView...here
        //we'll do the single select as follows
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //now just making the text area display the first item in the listview(otherwise the text area will remain blank initially)
        todoListView.getSelectionModel().selectFirst();


    }

    @FXML
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        //now we want this dialog to be modul...meaning while the dialog is visible, the user's
        //won't be able to interact with any other part of the application's UI
        //note: dialog is modul by default so we don't need to do anything right now

        //since we have set an id to the borderpane of mainwindow.fxml, we can refer to its parent and we can do it by calling getScene()
        // method which will return the scene from the borderPane and then we'll call the scene's getwindow method and set it as the owner of the dialog
        dialog.initOwner(mainBorderPane.getScene().getWindow()); //setting the owner for the dialog


        //note the main class loads the mainwindow fxml and now we created an instance of dialog class
        //but that doesn't load the UI we defined its fxml and so we have to load the fxml here and we are going to do it the same way we do in the main class...
        //once we've done that, we gotta set the dialog's dialogpane to the dialogpane we defined in the fxml



        //Now, since the controls of the dialog can be accessed only via dialogController so we need a way to do so
        //therefore, we create an instance of FXMLLoader so as to access the instances of controls declared in the DialogController.java and so as to be able to call
        //a method define in the DialogController from this Controller(mainwindow's controller)



        dialog.setTitle("Add New Todo Item");  //this sets the title for the dialog window


        FXMLLoader fxmlLoader = new FXMLLoader();   //new way
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml")); //new way

        try{ //we are adding a try block here since the load method can throw an IO exceoption
            dialog.getDialogPane().setContent(fxmlLoader.load()); //new way after creating the instance of FXMLLoader
//            Parent root = FXMLLoader.load(getClass().getResource("todoItemDialog.fxml"));  //old way
//            dialog.getDialogPane().setContent(root);

        }catch(IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }


        //Dialog class provides method to add buttons...we can use custom buttons but here we're gonna use OK and cancel button only
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);


        Optional<ButtonType> result = dialog.showAndWait(); //simple a show() method disappears on its own whereas show and Wait() method waits for the user itput(via the buttons) and suspends the event handler till the user presses any button(blocking dialog)...the former one is non-blocking dialog

        if(result.isPresent() && result.get() == ButtonType.OK){
            DialogController controller = fxmlLoader.getController(); //this is how we access the dialog's controller
            ToDoItem newItem = controller.processResults(); //since this also returns the new item added
            todoListView.getItems().setAll(TodoData.getInstance().getToDoItems()); //this will reset the listView
            todoListView.getSelectionModel().select(newItem);  //this selects the new item added
            System.out.println("OK pressed");
        }else{
            System.out.println("Cancel pressed");
        }

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
 *
 *
 *
 * for setting the dialog pane:
 *      we need to assign an ID to the borderpane in the mainwindow.fxml so that we can access it
 *      Now, it's a good practice to set the dialog's owner parent which is usually the window the dialog
 *      was open from...we could set the owner to null and the JavaFX runtime would still block input
 *      to other parts of the application
 *      When we set the owner, the owner has to of type window
 *      This is the reason we are assigning id to BorderPane so that we can access the parent window instance
 *
 */
