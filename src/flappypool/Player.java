package flappypool;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Az ugráló karaktert reprezentáló osztály.
 */
public class Player{

    private String imagePath;
    public Image image;
    public ImageView imageView;

    public double x, y;
    public double velocityY;
    private double jumpStrength;
    public double gravity;

    public int score = 0;
    public int scoreIt = 0;
    public boolean gameOver = false;

    public Rectangle hitBox;
    public Animation animation;

    private static final int COLUMNS  =  4;
    private static final int COUNT    =  4;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 78;
    private static final int HEIGHT   = 66;

    public Player(){
        imagePath = Settings.getPlayerModel();
        jumpStrength = Settings.getJumpStrength();
        gravity = Settings.getGravity();
        image = new Image(imagePath);

        x=Settings.getPositionX();
        y=Settings.getStaringHeight();

        hitBox = new Rectangle(x+30,y+5,32,HEIGHT-10);
        hitBox.setFill(Color.TRANSPARENT);
        hitBox.setStroke(Color.GREEN);

        imageView = new ImageView(image);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        animation = new SpriteAnimation(
                imageView,
                Duration.millis(450),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        imageView.setX(x);
    }

    /**
     * Hibakereső mód
     * @param x
     * @param y
     */
    public void debugMove(double x, double y){
        this.y = y;
    }

    /**
     * Frissíti a játékos pozícióját, és vizsgálja volt-e ütközés.
     * @param level
     */
    public void update(Level level){
        velocityY+=gravity;
        if(!Settings.isDebug())
            y += velocityY;
        checkCollision(level);
        checkScore(level);
        if(Settings.isRotation())
            imageView.setRotate(Math.atan(velocityY/45)*180/3.14);

        imageView.setY(y);
    }

    /**
     * Ugrás megkezdése.
     */
    public void startJump(){
        velocityY = jumpStrength;
        animation.play();
    }

    /**
     * Ugrás befejezése.
     */
    public void endJump(){
        if(velocityY < -6.0){
            velocityY = -6.0;
        }
    }

    /**
     * Megvizsgálja hogy volt-e ütközés a pálya elemeivel.
     * @param level
     */
    public void checkCollision(Level level){
        hitBox.setX(x+30);
        hitBox.setY(y+5);

        for (Pipe p : level.getPipes()){
            if(p.getBottom().intersects(x+30,y+5,32,HEIGHT-10) || p.getTop().intersects(x+30,y+5,32,HEIGHT-10) || y < 0 || y > 640){
                gameOver = true;
            }
        }
    }

    /**
     * Számolja az eredményt.
     * @param level
     */
    public void checkScore(Level level){
        if(level.getPipes().get(scoreIt).getTop().getX()+level.getPipes().get(scoreIt).getTop().getWidth() < x){
            score++;
            level.getPipes().get(scoreIt).Counted();
            ++scoreIt;
        }
    }
}
