public enum WeaponEffect {
    NONE("Нет эффекта", ""),
    BLEEDING("Кровотечение", "Наносит дополнительный урон в течение 3 ходов"),
    POISON("Яд", "Ослабляет врага, уменьшая его силу"),
    FREEZE("Ледяной удар", "Может заморозить врага, пропуск хода"),
    FIRE("Огненный удар", "Наносит дополнительный магический урон"),
    LIGHTNING("Удар молнии", "Имеет шанс нанести двойной урон"),
    VAMPIRE("Вампиризм", "Восстанавливает здоровье при атаке"),
    CRITICAL("Повышенный крит", "Увеличивает шанс критического удара"),
    ARMOR_PIERCE("Пробивание брони", "Игнорирует часть защиты врага"),
    STUN("Оглушение", "Может оглушить врага");

    private final String name;
    private final String description;

    WeaponEffect(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
}