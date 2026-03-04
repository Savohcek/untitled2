// Knight.java
import java.util.Random;

class Knight extends Character {
    private boolean specialAbilityReady;
    private Random random = new Random();

    public Knight(String name) {
        super(name, 120, 15);
        this.specialAbilityReady = true;
    }

    public boolean specialAttack(Character target) {
        if (specialAbilityReady) {
            int damage = 30 + (weapon != null ? weapon.getDamage() * 2 : 0);
            System.out.println(name + " использует РЫЦАРСКИЙ УДАР!");
            target.takeDamage(damage);
            specialAbilityReady = false;
            return true;
        } else {
            System.out.println("Способность ещё не восстановлена!");
            return false;
        }
    }

    public void regenerate() {
        if (random.nextDouble() < 0.3) { // 30% шанс
            specialAbilityReady = true;
            System.out.println(name + " восстановил рыцарский удар!");
        }
    }

    public boolean isSpecialAbilityReady() {
        return specialAbilityReady;
    }

    public void setSpecialAbilityReady(boolean ready) {
        this.specialAbilityReady = ready;
    }
}