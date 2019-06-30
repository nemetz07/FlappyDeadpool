package flappypool;

/**
 * Egy eredményt reprezentáló osztály.
 */
public class Score {
    private int value;
    private String name;
    private String difficulty;

    public Score(int value, String name, String difficulty) {
        this.value = value;
        this.name = name;
        this.difficulty = difficulty;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
