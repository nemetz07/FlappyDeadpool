package flappypool;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * A játék közben a képernyőn megjelenő szövegek, gombok, bevitali mezők kezeléséért felelős osztály.
 */
public class HUD {
    private Label score;
    private Label gameOverText;
    private Button mainMenuButton;
    private Button saveScoreButton;
    private TextField gameOverField;

    public HUD(){
        score = new Label("SCORE: ");
        score.setTranslateX(65);
        score.setTranslateY(20);
        score.setTextFill(Color.WHITE);
        score.setScaleX(3);
        score.setScaleY(3);

        score.setBackground(new Background(new BackgroundFill(Color.valueOf("DARKRED"), new CornerRadii(6), Insets.EMPTY)));
        score.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5),new BorderWidths(1))));

        gameOverText = new Label("Game Over!");
        gameOverText.setTranslateX(Settings.getSCREENWIDTH()/2 - 35);
        gameOverText.setTranslateY(Settings.getSCREENHEIGHT()/2);
        gameOverText.setScaleX(4);
        gameOverText.setScaleY(4);
        gameOverText.setTextFill(Color.WHITE);

        gameOverField = new TextField();
        gameOverField.setTranslateX(Settings.getSCREENWIDTH()/2 - 80);
        gameOverField.setTranslateY(Settings.getSCREENHEIGHT()/2 + 65);
        gameOverField.setText("Player");
        gameOverField.setScaleX(1.5);
        gameOverField.setScaleY(1.5);

        saveScoreButton = new Button("Save score");
        saveScoreButton.setTranslateX(Settings.getSCREENWIDTH()/2 - 45);
        saveScoreButton.setTranslateY(Settings.getSCREENHEIGHT()/2 + 120);
        saveScoreButton.setScaleX(2);
        saveScoreButton.setScaleY(2);

        mainMenuButton = new Button("Go to Main Menu");
        mainMenuButton.setTranslateX(Settings.getSCREENWIDTH()/2 - 55);
        mainMenuButton.setTranslateY(Settings.getSCREENHEIGHT()/2 + 200);
        mainMenuButton.setScaleX(2);
        mainMenuButton.setScaleY(2);
    }

    public TextField getGameOverField() {
        return gameOverField;
    }

    public String getName(){
        return gameOverField.getText();
    }

    public Label getScore() {
        return score;
    }

    public Label getGameOverText() {
        return gameOverText;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }

    public Button getSaveScoreButton() {
        return saveScoreButton;
    }
}
