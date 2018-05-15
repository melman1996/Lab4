package com.halatek;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;

    private ObservableList<Task> todo = FXCollections.observableArrayList();
    private ObservableList<Task> inprogress = FXCollections.observableArrayList();
    private ObservableList<Task> done = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Hello");

        initRootLayout();
    }

    public Main() {
        todo.add(new Task("Task 1", Priority.HIGH, LocalDate.now(), ""));
        todo.add(new Task("Task 2", Priority.HIGH, LocalDate.now(), ""));
        todo.add(new Task("Task 3", Priority.HIGH, LocalDate.now(), ""));

        inprogress.add(new Task("Task 4", Priority.HIGH, LocalDate.now(), ""));
        inprogress.add(new Task("Task 5", Priority.HIGH, LocalDate.now(), ""));
        inprogress.add(new Task("Task 6", Priority.HIGH, LocalDate.now(), ""));

        done.add(new Task("Task 7", Priority.HIGH, LocalDate.now(), ""));
        done.add(new Task("Task 8", Priority.HIGH, LocalDate.now(), ""));
        done.add(new Task("Task 9", Priority.HIGH, LocalDate.now(), ""));
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("mainPane.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            MainPane mainPane = loader.getController();
            mainPane.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTask(String title, Priority priority, LocalDate expiration, String description) {
        todo.add(new Task(title, priority, expiration, description));
    }

    public ObservableList<Task> getTodo() {
        return todo;
    }

    public ObservableList<Task> getInprogress() {
        return inprogress;
    }

    public ObservableList<Task> getDone() {
        return done;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
