// Character.java
abstract class Character {
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
        System.out.println(name + " получил " + actualDamage +
                " урона. Осталось здоровья: " + health);
    }

    public void attack(Character target) {
        int damage = strength + (weapon != null ? weapon.getDamage() : 0);
        System.out.println(name + " атакует " + target.getName() +
                " нанося " + damage + " урона!");
        target.takeDamage(damage);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }

    public void setHealth(int health) {
        this.health = Math.min(maxHealth, health);
    }

    @Override
    public String toString() {
        String status = name + " [Здоровье: " + health + "/" + maxHealth + "]";
        if (weapon != null) {
            status += "\n  Оружие: " + weapon;
        }
        if (armor != null) {
            status += "\n  Броня: " + armor;
        }
        return status;
    }
}
