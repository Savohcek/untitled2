// WeaponRarity.java
public enum WeaponRarity {
    COMMON("Обычное", 1.0, "Серый", 1),
    UNCOMMON("Необычное", 1.2, "Зеленый", 2),
    RARE("Редкое", 1.5, "Синий", 3),
    EPIC("Эпическое", 2.0, "Фиолетовый", 4),
    LEGENDARY("Легендарное", 3.0, "Оранжевый", 5),
    MYTHIC("Мифическое", 4.0, "Золотой", 6);

    private final String displayName;
    private final double statMultiplier;
    private final String color;
    private final int tier;

    WeaponRarity(String displayName, double statMultiplier, String color, int tier) {
        this.displayName = displayName;
        this.statMultiplier = statMultiplier;
        this.color = color;
        this.tier = tier;
    }

    public String getDisplayName() { return displayName; }
    public double getStatMultiplier() { return statMultiplier; }
    public String getColor() { return color; }
    public int getTier() { return tier; }
}