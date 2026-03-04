// Item.java
abstract class Item {
    protected String name;
    protected int weight;

    public Item(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public abstract int use();

    public String getName() { return name; }
    public int getWeight() { return weight; }

    @Override
    public String toString() {
        return name + " (вес: " + weight + ")";
    }
}
