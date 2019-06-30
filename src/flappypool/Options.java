package flappypool;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * A beállítások kezeléséért felelős osztály.
 */
public class Options {
    public GridPane options;

    private boolean done = false;

    public Options(){
        options = new GridPane();
        options.setAlignment(Pos.CENTER);
        options.setHgap(50);
        options.setVgap(35);
        options.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        setDifficulty();
        setDebug();
        setRotation();
        setLevelSpeed();
        setGravity();
        setJumpStrength();
        setPositionX();

        Button b = new Button("Return");
        b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                done = true;
            }
        });
        GridPane.setConstraints(b, 0, 0);
        options.getChildren().add(b);
    }

    /**
     * A játék nehézségét állítja be.
     */
    private void setDifficulty(){
        Label l = new Label("Difficulty: ");
        l.setTextFill(Color.WHITE);
        GridPane.setConstraints(l, 0,1);
        ChoiceBox choiceBox = new ChoiceBox();
        GridPane.setConstraints(choiceBox, 1, 1);
        choiceBox.setItems(FXCollections.observableArrayList(
                "EASY", "MEDIUM", "HARD", "INSANE"));
        choiceBox.setValue(Settings.getDifficulty());
        choiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Settings.setDifficulty(choiceBox.getSelectionModel().getSelectedItem().toString());
            }
        });
        options.getChildren().addAll(l, choiceBox);
    }

    /**
     * A hibakereső módot állítja be.
     */
    private void setDebug(){
        Label l = new Label("Debug: ");
        l.setTextFill(Color.WHITE);
        GridPane.setConstraints(l, 0, 2);
        CheckBox checkBox = new CheckBox();
        GridPane.setConstraints(checkBox, 1, 2);
        checkBox.setSelected(Settings.isDebug());
        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Settings.setDebug(checkBox.isSelected());
            }
        });
        options.getChildren().addAll(l, checkBox);
    }

    /**
     * A játékos forgását állítja be.
     */
    private void setRotation(){
        Label l = new Label("Rotation: ");
        l.setTextFill(Color.WHITE);
        GridPane.setConstraints(l, 0, 3);
        CheckBox checkBox = new CheckBox();
        GridPane.setConstraints(checkBox, 1, 3);
        checkBox.setSelected(Settings.isRotation());
        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Settings.setRotation(checkBox.isSelected());
            }
        });
        options.getChildren().addAll(l, checkBox);
    }

    /**
     * A játék gyorsaságát állítja be.
     */
    private void setLevelSpeed(){
        Label l = new Label("Level speed: ");
        l.setTextFill(Color.WHITE);
        GridPane.setConstraints(l, 0, 4);
        Label l2 = new Label(Double.toString(Settings.getLevelSpeed()).substring(0,3));
        l2.setTextFill(Color.WHITE);
        GridPane.setConstraints(l2, 2, 4);
        Slider slider = new Slider();
        GridPane.setConstraints(slider, 1, 4);
        slider.setMax(6.0);
        slider.setMin(2.0);
        slider.setValue(Settings.getLevelSpeed());
        slider.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Settings.setLevelSpeed(slider.getValue());
                l2.setText(Double.toString(Settings.getLevelSpeed()).substring(0,3));
            }
        });
        options.getChildren().addAll(l, l2, slider);
    }

    /**
     * A játék gravitációját állítja be.
     */
    private void setGravity(){
        Label l = new Label("Gravity: ");
        l.setTextFill(Color.WHITE);
        GridPane.setConstraints(l, 0, 5);
        Label l2 = new Label(Double.toString(Settings.getGravity()).substring(0,3));
        l2.setTextFill(Color.WHITE);
        GridPane.setConstraints(l2, 2, 5);
        Slider slider = new Slider();
        GridPane.setConstraints(slider, 1, 5);
        slider.setMax(1.0);
        slider.setMin(0.2);
        slider.setValue(Settings.getGravity());
        slider.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Settings.setGravity(slider.getValue());
                l2.setText(Double.toString(Settings.getGravity()).substring(0,3));
            }
        });
        options.getChildren().addAll(l, l2, slider);
    }

    /**
     * Az ugrás kezdőerejét állítja be.
     */
    private void setJumpStrength(){
        Label l = new Label("Jump strength: ");
        l.setTextFill(Color.WHITE);
        GridPane.setConstraints(l, 0, 6);
        Label l2 = new Label(Double.toString((-1)*Settings.getJumpStrength()).substring(0,3));
        l2.setTextFill(Color.WHITE);
        GridPane.setConstraints(l2, 2, 6);
        Slider slider = new Slider();
        GridPane.setConstraints(slider, 1, 6);
        slider.setMax(13.0);
        slider.setMin(6.0);
        slider.setValue(Settings.getJumpStrength()*(-1));
        slider.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Settings.setJumpStrength((-1)*slider.getValue());
                l2.setText(Double.toString((-1)*Settings.getJumpStrength()).substring(0,3));
            }
        });
        options.getChildren().addAll(l, l2, slider);
    }

    /**
     * A játékos vízszintes helyzetét állítja be.
     */
    private void setPositionX(){
        Label l = new Label("X position: ");
        l.setTextFill(Color.WHITE);
        GridPane.setConstraints(l, 0, 7);
        Label l2 = new Label(Integer.toString(Settings.getPositionX()));
        l2.setTextFill(Color.WHITE);
        GridPane.setConstraints(l2, 2, 7);
        Slider slider = new Slider();
        GridPane.setConstraints(slider, 1, 7);
        slider.setMax(Settings.getSCREENWIDTH()-100);
        slider.setMin(0.0);
        slider.setValue(Settings.getPositionX());
        slider.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Settings.setPositionX((int)slider.getValue());
                l2.setText(Integer.toString(Settings.getPositionX()));
            }
        });
        options.getChildren().addAll(l, l2, slider);
    }

    public boolean isDone() {
        return done;
    }

    public void restoreDone(){
        done = false;
    }
}
