// Knight.java
import java.util.Random;

public class Knight extends Character {
    private boolean specialAbilityReady;
    private Random random = new Random();
    private int level;
    private int experience;
    private int baseStrength;
    private int baseHealth;

    public Knight(String name) {
        super(name, 120, 15);
        this.specialAbilityReady = true;
        this.level = 1;
        this.experience = 0;
        this.baseStrength = 15;
        this.baseHealth = 120;
        updateStats();
    }

    private void updateStats() {
        int bonus = ExperienceSystem.getStatIncrease(level - 1);
        this.maxHealth = baseHealth + bonus;
        this.strength = baseStrength + bonus;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }

    public void addExperience(int exp) {
        this.experience += exp;
        System.out.println("✨ Получено опыта: " + exp + "! Всего опыта: " + experience + "/" +
                ExperienceSystem.getExpNeededForLevel(level));

        while (experience >= ExperienceSystem.getExpNeededForLevel(level)) {
            levelUp();
        }
    }

    private void levelUp() {
        experience -= ExperienceSystem.getExpNeededForLevel(level);
        level++;

        System.out.println("\n🎉🎉🎉 УРОВЕНЬ ПОВЫШЕН! 🎉🎉🎉");
        System.out.println("Вы достигли " + level + " уровня!");
        System.out.println("Ваши характеристики улучшены!");

        int oldHealth = maxHealth;
        int oldStrength = strength;

        updateStats();

        System.out.println("❤️ Здоровье: " + oldHealth + " → " + maxHealth + " (+" + (maxHealth - oldHealth) + ")");
        System.out.println("⚔️ Сила: " + oldStrength + " → " + strength + " (+" + (strength - oldStrength) + ")");

        health = maxHealth;
        specialAbilityReady = true;

        System.out.println("Все способности восстановлены!");

        if (experience >= ExperienceSystem.getExpNeededForLevel(level)) {
            levelUp();
        }
    }

    public boolean specialAttack(Character target) {
        if (specialAbilityReady) {
            int damage = 30;
            if (weapon != null) {
                damage += weapon.getDamageBonus() * 2;
            }
            damage += strength;
            System.out.println(name + " использует РЫЦАРСКИЙ УДАР и наносит " + damage + " урона!");
            target.takeDamage(damage);
            specialAbilityReady = false;
            return true;
        } else {
            System.out.println("Способность ещё не восстановлена!");
            return false;
        }
    }

    @Override
    public void attack(Character target) {
        int damage = strength;
        if (weapon != null) {
            damage = weapon.calculateDamage(strength);
            System.out.println(name + " атакует " + target.getName() + " с помощью " + weapon.getName() + "!");
        } else {
            System.out.println(name + " атакует " + target.getName() + " голыми руками!");
        }

        System.out.println("Нанесено " + damage + " урона!");
        target.takeDamage(damage);
    }

    public void regenerate() {
        if (random.nextDouble() < 0.3) {
            specialAbilityReady = true;
            System.out.println(name + " восстановил рыцарский удар!");
        }
    }

    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public int getExpNeeded() { return ExperienceSystem.getExpNeededForLevel(level); }
    public boolean isSpecialAbilityReady() { return specialAbilityReady; }
    public void setSpecialAbilityReady(boolean ready) { this.specialAbilityReady = ready; }

    @Override
    public String toString() {
        String status = name + " [Уровень: " + level + "]";
        status += "\n❤️ Здоровье: " + health + "/" + maxHealth;
        status += "\n⚔️ Сила: " + strength;
        status += "\n✨ Опыт: " + experience + "/" + getExpNeeded();

        if (weapon != null) {
            status += "\n⚔️ Оружие: " + weapon.getName();
        }
        if (armor != null) {
            status += "\n🛡️ Броня: " + armor.getName();
        }
        return status;
    }
}
