package flappypool;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Az eredmények kezeléséért felelős osztály.
 */
public class HighScore {
    public ArrayList<Score> highScore;
    public VBox vBox;
    private Button retButton;
    private boolean done = false;

    public HighScore(ArrayList<Score> highScore){
        this.highScore = highScore;
        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        retButton = new Button("Return");
        retButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                done = true;
            }
        });
        vBox.getChildren().add(retButton);

        for(Score s : highScore){
            vBox.getChildren().addAll(new Label(s.getName() + " : " + s.getValue() + " (" + s.getDifficulty() + ")"));
        }
    }

    public void setHighScore(ArrayList<Score> highScore) {
        this.highScore = highScore;
    }

    public boolean isDone() {
        return done;
    }

    public void resetDone(){
        done = false;
    }
}
