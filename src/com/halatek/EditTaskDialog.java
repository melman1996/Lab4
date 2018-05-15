package com.halatek;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditTaskDialog {

    private Main main;
    private Task task;

    @FXML
    private TextField title;
    @FXML
    private ChoiceBox<Priority> priority;
    @FXML
    private DatePicker date;
    @FXML
    private TextArea description;

    public void handleEditAction(ActionEvent actionEvent) {
        task.setTitle(title.getText());
        task.setPriority(priority.getValue());
        task.setExpiration(date.getValue());
        task.setDescription(description.getText());
        ((Stage) title.getScene().getWindow()).close();
    }

    public void setMainApp(Main main) {
        this.main = main;
        if (main == null) {
            System.out.println("NULL");
        }
    }

    public void setTaskToEdit(Task task) {
        this.task = task;
        title.setText(task.getTitle());
        priority.setValue(task.getPriority());
        date.setValue(task.getExpiration());
        description.setText(task.getDescription());
    }

    @FXML
    private void initialize() {
        priority.setItems(FXCollections.observableArrayList(Priority.values()));
    }
}
