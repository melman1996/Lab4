package com.halatek;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class NewTaskDialog {
    private Main main;
    @FXML
    private TextField title;
    @FXML
    private ChoiceBox<Priority> priority;
    @FXML
    private DatePicker date;
    @FXML
    private TextArea description;

    public void handleAddAction(ActionEvent actionEvent) {
        main.addTask(title.getText(), priority.getValue(), date.getValue(), description.getText());
        ((Stage) title.getScene().getWindow()).close();
    }

    public void setMainApp(Main main) {
        this.main = main;
        if (main == null) {
            System.out.println("NULL");
        }
    }

    @FXML
    private void initialize() {
        priority.setItems(FXCollections.observableArrayList(Priority.values()));
    }
}
