// Enemy.java
import java.util.Random;

public class Enemy extends Character {
    private String difficulty;
    private Random random = new Random();
    private int expReward;

    public Enemy(String name, String difficulty) {
        super(name, getHealthByDifficulty(difficulty),
                getStrengthByDifficulty(difficulty));
        this.difficulty = difficulty;
        this.expReward = calculateExpReward();
    }

    private static int getHealthByDifficulty(String difficulty) {
        switch(difficulty) {
            case "easy": return 50;
            case "hard": return 150;
            default: return 80;
        }
    }

    private static int getStrengthByDifficulty(String difficulty) {
        switch(difficulty) {
            case "easy": return 8;
            case "hard": return 20;
            default: return 12;
        }
    }

    private int calculateExpReward() {
        switch(difficulty) {
            case "easy": return 30 + random.nextInt(20);
            case "hard": return 80 + random.nextInt(40);
            default: return 50 + random.nextInt(30);
        }
    }

    public void enemyAction(Character target) {
        int action = random.nextInt(3);

        if (action == 0) {
            attack(target);
        } else if (action == 1) {
            int damage = strength * 2;
            System.out.println(name + " готовит мощную атаку и наносит " + damage + " урона!");
            target.takeDamage(damage);
        } else {
            System.out.println(name + " защищается и восстанавливает 5 здоровья");
            health = Math.min(maxHealth, health + 5);
        }
    }

    public int getExpReward() { return expReward; }
    public String getDifficulty() { return difficulty; }
}