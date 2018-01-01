package com.aditcrey.todoList;

import com.aditcrey.todoList.datamodel.ToDoItem;
import com.aditcrey.todoList.datamodel.TodoData;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

/**
 * Created by aditc on 30-12-2017.
 */
public class DialogController {
    @FXML
    private TextField shortDescriptionField;

    @FXML
    private TextArea detailsArea;

    @FXML
    private DatePicker deadlinePicker;

    public ToDoItem processResults(){ //when OK processed
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate deadlineValue = deadlinePicker.getValue();

        ToDoItem newItem = new ToDoItem(shortDescription,details,deadlineValue);
        TodoData.getInstance().addTodoItem(newItem);

        return newItem;

    }






}
