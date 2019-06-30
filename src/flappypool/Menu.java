package flappypool;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * A Főmenüt megvalósító osztály.
 */
public class Menu{

    Button newGame, options, exit, highScore;
    VBox root;
    public boolean game = false;
    GameState gameState;

    public Menu(){
        root = new VBox();
        gameState = GameState.MAINMENU;

        newGame = new Button("New Game");
        newGame.setScaleX(2);
        newGame.setScaleY(2);
        newGame.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER)
                    gameState = GameState.NEWGAME;
            }
        });
        newGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gameState = GameState.NEWGAME;
            }
        });

        options = new Button("Options");
        options.setScaleX(2);
        options.setScaleY(2);
        options.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gameState = GameState.OPTIONS;
            }
        });

        exit = new Button("Exit");
        exit.setScaleX(2);
        exit.setScaleY(2);
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gameState = GameState.EXIT;
            }
        });

        highScore = new Button("Highscore");
        highScore.setScaleX(2);
        highScore.setScaleY(2);
        highScore.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gameState = GameState.HIGHSCORE;
            }
        });

        root.setSpacing(50);
        root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(newGame, highScore, options, exit);
    }

    public VBox getMenu(){
        return root;
    }

}
