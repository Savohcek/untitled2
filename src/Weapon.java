// Weapon.java
class Weapon extends Item {
    private int damage;

    public Weapon(String name, int damage, int weight) {
        super(name, weight);
        this.damage = damage;
    }

    @Override
    public int use() {
        return damage;
    }

    public int getDamage() { return damage; }

    @Override
    public String toString() {
        return name + " (урон: " + damage + ", вес: " + weight + ")";
    }
}
