// BattleResult.java
public class BattleResult {
    private boolean victory;
    private int experience;

    public BattleResult(boolean victory, int experience) {
        this.victory = victory;
        this.experience = experience;
    }

    public boolean isVictory() { return victory; }
    public int getExperience() { return experience; }
}