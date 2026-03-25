// ExperienceSystem.java
public class ExperienceSystem {
    private static final int BASE_EXP_FOR_LEVEL = 100;
    private static final double EXP_MULTIPLIER = 1.5;

    public static int getExpNeededForLevel(int level) {
        return (int)(BASE_EXP_FOR_LEVEL * Math.pow(EXP_MULTIPLIER, level - 1));
    }

    public static int getStatIncrease(int level) {
        return level * 5;
    }
}
