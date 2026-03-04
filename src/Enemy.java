import java.util.Random;

class Enemy extends Character {
    private String difficulty;
    private Random random = new Random();

    public Enemy(String name, String difficulty) {
        super(name, getHealthByDifficulty(difficulty),
                getStrengthByDifficulty(difficulty));
        this.difficulty = difficulty;
    }

    private static int getHealthByDifficulty(String difficulty) {
        switch(difficulty) {
            case "easy": return 50;
            case "hard": return 150;
            default: return 80; // normal
        }
    }

    private static int getStrengthByDifficulty(String difficulty) {
        switch(difficulty) {
            case "easy": return 8;
            case "hard": return 20;
            default: return 12; // normal
        }
    }

    public void enemyAction(Character target) {
        int action = random.nextInt(3); // 0, 1, или 2

        if (action == 0) {
            attack(target);
        } else if (action == 1) {
            int damage = strength * 2;
            System.out.println(name + " готовит тяжёлую атаку!");
            target.takeDamage(damage);
        } else {
            System.out.println(name + " защищается и восстанавливает 15 здоровья");
            health = Math.min(maxHealth, health + 15);
        }
    }
}
