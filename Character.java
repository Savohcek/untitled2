// Character.java
public abstract class Character {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int strength;
    protected Weapon weapon;
    protected Armor armor;

    public Character(String name, int health, int strength) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.strength = strength;
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
        System.out.println(name + " экипировал " + weapon.getName());
    }

    public void equipArmor(Armor armor) {
        this.armor = armor;
        System.out.println(name + " надел " + armor.getName());
    }

    public void takeDamage(int damage) {
        int actualDamage = damage;
        if (armor != null) {
            actualDamage = Math.max(0, damage - armor.getDefense());
        }
        health -= actualDamage;
        if (actualDamage > 0) {
            System.out.println(name + " получил " + actualDamage + " урона. Осталось здоровья: " + Math.max(0, health));
        }
    }

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

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getStrength() { return strength; }
    public Weapon getWeapon() { return weapon; }
    public Armor getArmor() { return armor; }

    public void setHealth(int health) {
        this.health = Math.min(maxHealth, Math.max(0, health));
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        String status = name + " [Здоровье: " + health + "/" + maxHealth + "]";
        if (weapon != null) {
            status += "\n  Оружие: " + weapon.getName();
        }
        if (armor != null) {
            status += "\n  Броня: " + armor.getName();
        }
        return status;
    }
}