package flappypool;

import javafx.scene.Group;
import java.util.ArrayList;
import java.util.Random;

/**
 * A pályát kezelő osztály.
 */
public class Level {

    private ArrayList<Pipe> pipes;
    private double speed;
    private int spawnDelay;

    public String getDifficulty() {
        return difficulty;
    }

    private String difficulty;
    private int SCREENWIDTH, SCREENHEIGHT;

    private double time = 0;

    public Level(){
        pipes = new ArrayList<Pipe>();
        speed = Settings.getLevelSpeed();
        difficulty = Settings.getDifficulty();
        SCREENWIDTH = Settings.getSCREENWIDTH();
        SCREENHEIGHT = Settings.getSCREENHEIGHT();

        spawnDelay = 30000/(int)Settings.getLevelSpeed();

//        switch (difficulty){
//            case "EASY":
//                spawnDelay = 30000/(int)Settings.getLevelSpeed();
//                break;
//            case "MEDIUM":
//                spawnDelay = 29000/(int)Settings.getLevelSpeed();
//                break;
//            case "HARD":
//                spawnDelay = 24000/(int)Settings.getLevelSpeed();
//                break;
//            default:
//            case "INSANE":
//                spawnDelay = 20000/(int)Settings.getLevelSpeed();
//                break;
//        }
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    /**
     * Frissíti a pályaelemket. Balra scrollozza az összes akadályt.
     * @param t Az előző frissítés óta eltelt idő
     * @param root A layout, amihez az új akadályokat hozzá lehet adni.
     */
    public void update(double t, Group root){
        for (Pipe p : pipes) {
            p.getTop().setX(p.getTop().getX()-speed);
            p.getBottom().setX(p.getBottom().getX()-speed);
        }

        time += t;
        if(time > spawnDelay){
            Pipe p = generatePipe();
            pipes.add(p);
            root.getChildren().addAll(p.getBottom(), p.getTop());
            time=0;
        }
    }

    /**
     * Generál egy új akadályt a nehézségi szintnek megfelelően.
     */
    private Pipe generatePipe(){
        Pipe p;
        switch (difficulty) {
            case "EASY":
                p = new Pipe(Pipe.EASY);
                break;
            case "MEDIUM":
                p = new Pipe(Pipe.MEDIUM);
                break;
            case "HARD":
                p = new Pipe(Pipe.HARD);
                break;
            default:
            case "INSANE":
                p = new Pipe(Pipe.INSANE);
                break;
        }

        return p;
    }

}
