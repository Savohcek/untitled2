public enum WeaponType {
    SWORD("Меч", 1.0, "Сбалансированное оружие"),
    AXE("Топор", 1.3, "Тяжелое оружие с высоким уроном"),
    DAGGER("Кинжал", 0.7, "Быстрое оружие с шансом крита"),
    SPEAR("Копье", 1.1, "Длинное оружие с дополнительной защитой"),
    BOW("Лук", 0.9, "Дальнобойное оружие"),
    MACE("Булава", 1.2, "Дробящее оружие, игнорирует часть брони"),
    GREATSWORD("Двуручный меч", 1.5, "Массивное оружие с огромным уроном"),
    HALBERD("Алебарда", 1.4, "Комбинированное оружие"),
    FLAIL("Цеп", 1.25, "Оружие с цепью, трудно блокировать"),
    RAPIER("Рапира", 0.85, "Точное оружие с высоким шансом критического удара");

    private final String displayName;
    private final double damageMultiplier;
    private final String description;

    WeaponType(String displayName, double damageMultiplier, String description) {
        this.displayName = displayName;
        this.damageMultiplier = damageMultiplier;
        this.description = description;
    }

    public String getDisplayName() { return displayName; }
    public double getDamageMultiplier() { return damageMultiplier; }
    public String getDescription() { return description; }
}
