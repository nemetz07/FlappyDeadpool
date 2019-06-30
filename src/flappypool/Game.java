package flappypool;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Set;
import java.util.logging.Logger;

/**
Ez az osztály tartalmazza a játékhoz szükséges összes mezőt, metódust.
 */

public class Game {
    private Settings settings;
    private GameState gameState = GameState.MAINMENU;
    private ScoreManager scoreManager;
    private HighScore highScore;
    public final static Logger LOGGER = Logger.getLogger(Game.class.getName());

    private Player player;
    private Level level;
    private Menu mainMenu;
    private Options options;
    private HUD hud;
    private Group root;
    private Stage primaryStage;
    private Scene mainMenuScene;
    private Scene optionsScene;
    private Scene playScene;
    private Scene highScoreScene;

    private Label gameOverText;
    private Label score;
    private Button mainMenuButton;
    private Button saveScoreButton;
    private TextField gameOverField;

    public boolean start = false;
    public boolean first = true;

    public Game(Stage primaryStage){
        LOGGER.setLevel(java.util.logging.Level.INFO);
        settings = new Settings();
        scoreManager = new ScoreManager();
        highScore = new HighScore(scoreManager.getScores());

        this.primaryStage = primaryStage;
        root = new Group();
        player = new Player();
        level = new Level();
        mainMenu = new Menu();
        options = new Options();
        hud = new HUD();

        score = hud.getScore();
        gameOverText = hud.getGameOverText();
        gameOverField = hud.getGameOverField();
        mainMenuButton = hud.getMainMenuButton();
        saveScoreButton = hud.getSaveScoreButton();
        saveScoreButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(gameOverField.getLength() != 0) {
                    gameState = GameState.MAINMENU;
                    scoreManager.addScore(player.score, gameOverField.getText(), level.getDifficulty());
                    scoreManager.save();
                }
            }
        });
        mainMenuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gameState = GameState.MAINMENU;
//                if(gameOverField.getLength() != 0) {
//                    gameState = GameState.MAINMENU;
//                    scoreManager.addScore(player.score, gameOverField.getText());
//                    scoreManager.save();
//                }
            }
        });
        mainMenuButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                gameState = GameState.MAINMENU;
//                if (event.getCode() == KeyCode.ENTER) {
//                    gameState = GameState.MAINMENU;
//                    scoreManager.addScore(player.score, gameOverField.getText());
//                    scoreManager.save();
//                }
            }
        });

        mainMenuScene = new Scene(mainMenu.getMenu(), Settings.getSCREENWIDTH(), Settings.getSCREENHEIGHT());
        playScene = new Scene(root, Settings.getSCREENWIDTH(), Settings.getSCREENHEIGHT());
        optionsScene = new Scene(options.options, Settings.getSCREENWIDTH(), Settings.getSCREENHEIGHT());
        highScoreScene = new Scene(highScore.vBox, Settings.getSCREENWIDTH(), Settings.getSCREENHEIGHT());
        getInputs();

        primaryStage.setScene(mainMenuScene);
    }

    public void startJump(){
        player.startJump();
    }

    public void endJump(){
        player.endJump();
    }

    /**
     * Frissíti a játékot. Beállítja a játék állapotát, a karakter és a pálya helyzetét. Kezeli a menüket.
     * @param t Két frissítés között eltelt idő.
     */
    public void update(double t){
        switch (gameState){
            case MAINMENU:
                if (primaryStage.getScene() != mainMenuScene)
                    primaryStage.setScene(mainMenuScene);
                root.getChildren().remove(mainMenuButton);
                root.getChildren().remove(saveScoreButton);
                root.getChildren().remove(gameOverText);
                root.getChildren().remove(gameOverField);
                switch (mainMenu.gameState){
                    case NEWGAME:
                        LOGGER.info("New Game started!(" + level.getDifficulty() + ")");
                        gameState = GameState.NEWGAME;
                        root.getChildren().removeAll();
                        first = true;
                        Rectangle hatter = new Rectangle(0,0,Settings.getSCREENWIDTH(),Settings.getSCREENHEIGHT());
                        hatter.setFill(Color.DIMGRAY);
                        root.getChildren().add(hatter);

                        player = new Player();
                        root.getChildren().add(player.imageView);
                        if(Settings.isDebug())
                            root.getChildren().add(player.hitBox);
                        level = new Level();
                        mainMenu.gameState = GameState.MAINMENU;
                        break;
                    case OPTIONS:
                        gameState = GameState.OPTIONS;
                        mainMenu.gameState = GameState.MAINMENU;
                        break;
                    case HIGHSCORE:
                        scoreManager.reload();
                        gameState = GameState.HIGHSCORE;
                        mainMenu.gameState = GameState.MAINMENU;
                        break;
                    case EXIT:
                        gameState = GameState.EXIT;
                        break;
                }

                break;
            case OPTIONS:
                if (primaryStage.getScene() != optionsScene)
                    primaryStage.setScene(optionsScene);

                if(options.isDone()) {
                    LOGGER.info("Settings updated!");
                    refreshSettings();
                    gameState = GameState.MAINMENU;
                    options.restoreDone();
                }
                break;
            case HIGHSCORE:
                scoreManager.reload();
                highScore.setHighScore(scoreManager.getScores());
                if (primaryStage.getScene() != highScoreScene)
                    primaryStage.setScene(highScoreScene);
                if(highScore.isDone()){
                    gameState = GameState.MAINMENU;
                    highScore.resetDone();
                }
                break;
            case NEWGAME:
                if(primaryStage.getScene() != playScene)
                    primaryStage.setScene(playScene);
                if(!root.getChildren().contains(score))
                    root.getChildren().add(score);
                if(!start)
                    start = true;
                if(player.gameOver)
                    gameState = GameState.GAMEOVER;
                break;
            case GAMEOVER:
                start = false;
                if(!root.getChildren().contains(gameOverText)) {
                    LOGGER.info("Game Over!");
                    root.getChildren().add(gameOverText);
                }
                if (!root.getChildren().contains(mainMenuButton))
                    root.getChildren().add(mainMenuButton);

                if (!root.getChildren().contains(saveScoreButton))
                    root.getChildren().add(saveScoreButton);

                if(!root.getChildren().contains(gameOverField))
                    root.getChildren().add(gameOverField);
                break;
            case EXIT:
                settings.save();
                LOGGER.info("Save and exit!");
                primaryStage.close();
                break;
        }

        if(start) {
            if(first) {
                level.update(16000, root);
                first = false;
            }

            player.update(level);
            level.update(t, root);
            score.setText("SCORE: " + Integer.toString(player.score));
            root.getChildren().remove(score);
            root.getChildren().add(score);
        }
    }

    /**
     * A beállításokat frissíti.
     */
    private void refreshSettings(){
        settings.refreshSettings();
    }

    /**
     * Beállítja a játékhoz szükséges eseménykezelőket.
     */
    private void getInputs(){
        playScene.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(Settings.isDebug())
                    player.debugMove(event.getX(), event.getY());
            }
        });

        playScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE){
                    startJump();
                }
            }
        });

        playScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE){
                    endJump();
                }
            }
        });
    }
}