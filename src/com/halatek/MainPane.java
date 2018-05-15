package com.halatek;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class MainPane {
    private Main main;
    private Task dragged;
    private ListView<Task> source;

    @FXML
    private ListView<Task> todo;
    @FXML
    private ListView<Task> inprogress;
    @FXML
    private ListView<Task> done;

    public void handleAddTask(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("newTaskDialog.fxml"));
            AnchorPane rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, 300, 300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            NewTaskDialog dialog = loader.getController();
            dialog.setMainApp(main);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainApp(Main main) {
        this.main = main;
        todo.setItems(main.getTodo());
        inprogress.setItems(main.getInprogress());
        done.setItems(main.getDone());
    }

    private void setupCellFactory() {
        todo.setCellFactory(param -> new TaskCell(todo));
        inprogress.setCellFactory(param -> new TaskCell(inprogress));
        done.setCellFactory(param -> new TaskCell(done));
    }

    @FXML
    private void initialize() {
        setupCellFactory();
    }

    public void handleClose(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void handleAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Created by Damian Hałatek");

        alert.showAndWait();
    }

    private class TaskCell extends ListCell<Task> {
        private TaskCell cell;
        private ListView<Task> listView;

        public TaskCell(ListView<Task> listView) {
            cell = this;
            this.listView = listView;

            setupContextMenu();
            setupDragAndDrop();
        }

        @Override
        public void updateItem(Task item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                cell.setStyle("");
                setText(null);
                setGraphic(null);
            } else {
                if (item != null) {
                    setText(item.toString());
                    Tooltip tip = new Tooltip();
                    tip.setText(item.getDescription());
                    setTooltip(tip);
                    switch (item.getPriority()) {
                        case LOW:
                            cell.setStyle("-fx-control-inner-background: lightgreen;");
                            break;
                        case MEDIUM:
                            cell.setStyle("-fx-control-inner-background: lightblue;");
                            break;
                        case HIGH:
                            cell.setStyle("-fx-control-inner-background: salmon;");
                            break;
                    }
                    setGraphic(null);
                }
            }
        }

        private void setupContextMenu() {
            ContextMenu menu = new ContextMenu();
            MenuItem editItem = new MenuItem();
            editItem.setText("Edit");
            editItem.setOnAction(event -> {
                Task item = cell.getItem();
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("editTaskDialog.fxml"));
                    AnchorPane rootLayout = loader.load();

                    // Show the scene containing the root layout.
                    Scene scene = new Scene(rootLayout, 300, 300);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setResizable(false);

                    EditTaskDialog dialog = loader.getController();
                    dialog.setMainApp(main);
                    dialog.setTaskToEdit(item);

                    stage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listView.refresh();
            });
            MenuItem deleteItem = new MenuItem();
            deleteItem.setText("Delete");
            deleteItem.setOnAction(event -> listView.getItems().remove(cell.getItem()));
            menu.getItems().addAll(editItem, deleteItem);

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(menu);
                }
            });
        }

        private void setupDragAndDrop() {
            cell.setOnDragDetected(event -> {
                dragged = getItem();
                source = listView;
                Dragboard dragBoard = listView.startDragAndDrop(TransferMode.MOVE);

                ClipboardContent content = new ClipboardContent();

                content.putString(listView.getSelectionModel().getSelectedItem().toString());

                dragBoard.setContent(content);
            });
            cell.setOnDragDone(event -> {

            });
            cell.setOnDragEntered(event -> listView.setBlendMode(BlendMode.DIFFERENCE));
            cell.setOnDragExited(event -> listView.setBlendMode(null));
            cell.setOnDragOver(event -> event.acceptTransferModes(TransferMode.MOVE));
            cell.setOnDragDropped(event -> {
                listView.getItems().addAll(dragged);
                source.getItems().remove(dragged);
            });
        }
    }
}
