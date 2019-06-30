package flappypool;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Flappy Deadpool");
        primaryStage.setResizable(false);
        Image icon = new Image("icon.png");
        primaryStage.getIcons().add(icon);

        Game game = new Game(primaryStage);

        new AnimationTimer() {
            long last;
            double t=0;

            @Override
            public void handle(long now) {
//                t=(now-last)/100000L;
                t=(now-last)/200000L;
                game.update(t);

                last = now;
            }
        }.start();

        primaryStage.show();
    }
}
