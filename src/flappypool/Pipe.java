package flappypool;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Akakdályt megvalósító osztály.
 */
public class Pipe {
    private int SW = Settings.getSCREENWIDTH();
    private int SH = Settings.getSCREENHEIGHT();

    public static final String EASY = "EASY";
    public static final String MEDIUM = "MEDIUM";
    public static final String HARD = "HARD";
    public static final String INSANE = "INSANE";

    private Rectangle top, bottom;
    public boolean counted = false;

    public Pipe(String diff) {

        double y, gap, width;
        Random random = new Random();

        switch (diff) {
            case "EASY":
                gap = random.nextFloat() * 0.4 * SH + 0.4 * SH; //Random szam 0.4SH es 0.8SH kozott
                y = random.nextFloat() * 0.2 * SH; //Random szam 0.2SH es 0.4SH kozott
                width = random.nextFloat() * 0.1 * SW + 0.15 * SW; //Random szam 0.15SW es 0.15SW kozott
                break;

            case "MEDIUM":
                gap = random.nextFloat() * 0.4 * SH + 0.3 * SH; //Random szam 0.3SH es 0.7SH kozott
                y = random.nextFloat() * 0.3 * SH; //Random szam 0 es 0.4SH kozott
                width = random.nextFloat() * 0.1 * SW + 0.15 * SW; //Random szam 0.15SW es 0.15SW kozott
                break;

            case "HARD":
                gap = random.nextFloat() * 0.15 * SH + 0.25 * SH; //Random szam 0.25SH es 0.4SH kozott
                y = random.nextFloat() * 0.5 * SH + 0.1 * SH; //Random szam 0.1SH es 0.6SH kozott
                width = random.nextFloat() * 0.1 * SW + 0.15 * SW; //Random szam 0.15SW es 0.15SW kozott
                break;

            case "INSANE":
                gap = random.nextFloat() * 0.1 * SH + 0.2 * SH; //Random szam 0.2SH es 0.3SH kozott
                y = random.nextFloat() * 0.7 * SH; //Random szam 0 es 0.7SH kozott
                width = random.nextFloat() * 0.1 * SW + 0.15 * SW; //Random szam 0.15SW es 0.15SW kozott
                break;

            default:
                gap = random.nextFloat()*0.5*SH + 0.3*SH; //Random szam 0.3SH es 0.8SH kozott
                y = random.nextFloat()*0.2*SH+0.2*SH; //Random szam 0.2SH es 0.4SH kozott
                width = random.nextFloat()*0.1*SW + 0.15*SW; //Random szam 0.15SW es 0.15SW kozott
                break;
        }

        top = createTop(y, gap, width);
        decorate(top);

        bottom = createBottom(y, gap, width);
        decorate(bottom);

    }

    private Rectangle createTop(double y, double gap, double width) {
        return new Rectangle(SW, 0, width, y);
    }

    private Rectangle createBottom(double y, double gap, double width) {
        return new Rectangle(SW, y + gap, width, SH);
    }

    private void decorate(Rectangle rec) {
        Stop[] stops = new Stop[]{new Stop(0, Color.BLACK), new Stop(0.6, Color.RED), new Stop(0.9, Color.DARKRED)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REPEAT, stops);
        rec.setFill(lg1);
        rec.setStroke(Color.BLACK);
        rec.setStrokeType(StrokeType.OUTSIDE);
        rec.setStrokeLineJoin(StrokeLineJoin.ROUND);
        rec.setStrokeWidth(5);
    }

    public Rectangle getTop() {
        return top;
    }

    /**
     * Az eredmény nyilvántartásához szükséges.
     */
    public void Counted() {
        counted = true;
    }

    public Rectangle getBottom() {
        return bottom;
    }
}
