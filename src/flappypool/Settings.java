package flappypool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

/**
 * A beállításokat tároló statikus osztály.
 */
public class Settings {

    private static Preferences prefs;
    private static int SCREENWIDTH, SCREENHEIGHT;
    private static boolean debug, rotation;
    private static double levelSpeed, gravity, jumpStrength;
    private static int positionX, staringHeight;
    private static String playerModel, difficulty;

    public Settings(){
        this("settings.xml");
    }

    public Settings(String path){
        try {
            prefs = Preferences.userNodeForPackage(Settings.class);
            prefs.importPreferences(new FileInputStream(path));
        } catch (IOException e) {
            /*TODO*/
            prefs = Preferences.userNodeForPackage(Settings.class);
            setDefault();
        } catch (InvalidPreferencesFormatException e) {
            prefs = Preferences.userNodeForPackage(Settings.class);
            setDefault();
        }
        finally {
            loadSettings();
        }
    }

    /**
     * Mindent visszaállít default értékre.
     */
    public void setDefault(){
        prefs.putInt("Screen_Width", 480);
        prefs.putInt("Screen_Height", 640);
        prefs.putBoolean("Debug_mode", false);
        prefs.put("Difficulty", "EASY");
        prefs.putDouble("Level_speed", 3.0);
        prefs.putDouble("Gravity", 0.4);
        prefs.putDouble("Jump_strength", -8.0);
        prefs.putInt("PositionX", 125);
        prefs.putInt("Starting_height", 150);
        prefs.put("Player_model", "deadpool.png");
        prefs.putBoolean("Rotation", false);
    }

    /**
     * Kimenti a Preferences tartalmát egy XML fájlba.
     */
    public void save(){
        save("settings.xml");
    }

    /**
     * Kimenti a Preferences tartalmát egy XML fájlba.
     * @param path A fájl neve.
     */
    public void save(String path){
        try {
            prefs.exportNode(new FileOutputStream(path, false));
        } catch (IOException | BackingStoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Betölti a beállításokat a Preferences-ből.
     */
    public void loadSettings(){
        SCREENWIDTH = prefs.getInt("Screen_Width", 480);
        SCREENHEIGHT = prefs.getInt("Screen_Height", 640);
        debug = prefs.getBoolean("Debug_mode", false);
        difficulty = prefs.get("Difficulty", "EASY");
        levelSpeed = prefs.getDouble("Level_speed", 3.0);
        gravity = prefs.getDouble("Gravity", 0.4);
        jumpStrength = prefs.getDouble("Jump_strength", -8.0);
        positionX = prefs.getInt("PositionX", 125);
        staringHeight = prefs.getInt("Starting_height", 150);
        playerModel = prefs.get("Player_model", "deadpool.png");
        rotation = prefs.getBoolean("Rotation", false);
    }

    /**
     * Frissíti a beállításokat.
     */
    public void refreshSettings(){
        prefs.put("Difficulty", difficulty);
        prefs.putBoolean("Debug_mode", debug);
        prefs.putDouble("Level_speed", levelSpeed);
        prefs.putDouble("Gravity", gravity);
        prefs.putDouble("Jump_strength", jumpStrength);
        prefs.putInt("PositionX", positionX);
        prefs.putBoolean("Rotation", rotation);
    }

    public static void setSCREENWIDTH(int SCREENWIDTH) {
        Settings.SCREENWIDTH = SCREENWIDTH;
    }

    public static void setSCREENHEIGHT(int SCREENHEIGHT) {
        Settings.SCREENHEIGHT = SCREENHEIGHT;
    }

    public static void setDebug(boolean debug) {
        Settings.debug = debug;
    }

    public static void setRotation(boolean rotation) {
        Settings.rotation = rotation;
    }

    public static void setLevelSpeed(double levelSpeed) {
        Settings.levelSpeed = levelSpeed;
    }

    public static void setGravity(double gravity) {
        Settings.gravity = gravity;
    }

    public static void setJumpStrength(double jumpStrength) {
        Settings.jumpStrength = jumpStrength;
    }

    public static void setPositionX(int positionX) {
        Settings.positionX = positionX;
    }

    public static void setStaringHeight(int staringHeight) {
        Settings.staringHeight = staringHeight;
    }

    public static void setPlayerModel(String playerModel) {
        Settings.playerModel = playerModel;
    }

    public static void setDifficulty(String difficulty) {
        Settings.difficulty = difficulty;
    }

    public static int getSCREENWIDTH() {
        return SCREENWIDTH;
    }

    public static int getSCREENHEIGHT() {
        return SCREENHEIGHT;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static boolean isRotation() {
        return rotation;
    }

    public static double getLevelSpeed() {
        return levelSpeed;
    }

    public static double getGravity() {
        return gravity;
    }

    public static double getJumpStrength() {
        return jumpStrength;
    }

    public static int getPositionX() {
        return positionX;
    }

    public static int getStaringHeight() {
        return staringHeight;
    }

    public static String getPlayerModel() {
        return playerModel;
    }

    public static String getDifficulty() {
        return difficulty;
    }

    public Preferences getPrefs() {
        return prefs;
    }
}
