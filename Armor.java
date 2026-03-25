// Armor.java
public class Armor extends Item {
    private int defense;

    public Armor(String name, int defense, int weight) {
        super(name, weight);
        this.defense = defense;
    }

    @Override
    public int use() {
        return defense;
    }

    public int getDefense() { return defense; }

    @Override
    public String toString() {
        return name + " (защита: " + defense + ", вес: " + weight + ")";
    }
}