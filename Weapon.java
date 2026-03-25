// Weapon.java
import java.util.Random;

public class Weapon extends Item {
    private WeaponType type;
    private WeaponRarity rarity;
    private WeaponEffect effect;
    private int baseDamage;
    private int bonusStrength;
    private int bonusDefense;
    private int critChance;
    private static Random random = new Random();

    public Weapon() {
        super("", 0);
        this.type = getRandomWeaponType();
        this.rarity = getRandomRarity();
        this.effect = getRandomEffect();
        this.baseDamage = calculateBaseDamage();
        this.bonusStrength = calculateBonusStrength();
        this.bonusDefense = calculateBonusDefense();
        this.critChance = calculateCritChance();
        this.name = generateWeaponName();
        this.weight = calculateWeight();
    }

    public Weapon(WeaponType type, WeaponRarity rarity, WeaponEffect effect) {
        super("", 0);
        this.type = type;
        this.rarity = rarity;
        this.effect = effect;
        this.baseDamage = calculateBaseDamage();
        this.bonusStrength = calculateBonusStrength();
        this.bonusDefense = calculateBonusDefense();
        this.critChance = calculateCritChance();
        this.name = generateWeaponName();
        this.weight = calculateWeight();
    }

    private WeaponType getRandomWeaponType() {
        WeaponType[] types = WeaponType.values();
        return types[random.nextInt(types.length)];
    }

    private WeaponRarity getRandomRarity() {
        double rand = random.nextDouble();
        if (rand < 0.50) return WeaponRarity.COMMON;
        if (rand < 0.75) return WeaponRarity.UNCOMMON;
        if (rand < 0.90) return WeaponRarity.RARE;
        if (rand < 0.97) return WeaponRarity.EPIC;
        if (rand < 0.995) return WeaponRarity.LEGENDARY;
        return WeaponRarity.MYTHIC;
    }

    private WeaponEffect getRandomEffect() {
        WeaponEffect[] effects = WeaponEffect.values();
        if (random.nextDouble() < 0.7) {
            return effects[random.nextInt(effects.length)];
        }
        return WeaponEffect.NONE;
    }

    private int calculateBaseDamage() {
        int baseDamage = 10;
        switch(type) {
            case DAGGER: baseDamage = 8; break;
            case RAPIER: baseDamage = 9; break;
            case SWORD: baseDamage = 12; break;
            case SPEAR: baseDamage = 13; break;
            case AXE: baseDamage = 15; break;
            case MACE: baseDamage = 14; break;
            case FLAIL: baseDamage = 16; break;
            case BOW: baseDamage = 11; break;
            case HALBERD: baseDamage = 18; break;
            case GREATSWORD: baseDamage = 20; break;
            default: baseDamage = 10;
        }
        return (int)(baseDamage * rarity.getStatMultiplier());
    }

    private int calculateBonusStrength() {
        int bonus = (int)(5 * rarity.getStatMultiplier());
        if (type == WeaponType.AXE || type == WeaponType.GREATSWORD) {
            bonus += 3;
        }
        return bonus;
    }

    private int calculateBonusDefense() {
        int bonus = 0;
        if (type == WeaponType.SPEAR) {
            bonus = (int)(5 * rarity.getStatMultiplier());
        } else if (type == WeaponType.SWORD) {
            bonus = (int)(3 * rarity.getStatMultiplier());
        }
        return bonus;
    }

    private int calculateCritChance() {
        int baseCrit = 5;
        if (type == WeaponType.DAGGER) baseCrit += 10;
        if (type == WeaponType.RAPIER) baseCrit += 15;
        if (effect == WeaponEffect.CRITICAL) baseCrit += 10;
        return (int)(baseCrit * rarity.getStatMultiplier());
    }

    private int calculateWeight() {
        int weight = 5;
        switch(type) {
            case DAGGER: weight = 2; break;
            case RAPIER: weight = 3; break;
            case SWORD: weight = 5; break;
            case SPEAR: weight = 6; break;
            case AXE: weight = 8; break;
            case MACE: weight = 7; break;
            case FLAIL: weight = 9; break;
            case BOW: weight = 4; break;
            case HALBERD: weight = 10; break;
            case GREATSWORD: weight = 12; break;
            default: weight = 5;
        }
        return (int)(weight * (0.8 + 0.4 * rarity.getTier() / 6.0));
    }

    private String generateWeaponName() {
        String[] prefixes = {
                "Старый", "Ржавый", "Острый", "Закаленный", "Древний",
                "Магический", "Проклятый", "Благословенный", "Ледяной",
                "Огненный", "Молниевый", "Кровавый", "Теневой", "Святой"
        };

        String[] suffixes = {
                "Убийца", "Разрушитель", "Каратель", "Защитник",
                "Рассвета", "Заката", "Шторма", "Молний", "Драконов"
        };

        if (rarity == WeaponRarity.LEGENDARY || rarity == WeaponRarity.MYTHIC) {
            String prefix = prefixes[random.nextInt(prefixes.length)];
            String suffix = suffixes[random.nextInt(suffixes.length)];
            return prefix + " " + type.getDisplayName() + " " + suffix;
        } else if (rarity == WeaponRarity.EPIC) {
            return prefixes[random.nextInt(prefixes.length)] + " " + type.getDisplayName();
        } else {
            return type.getDisplayName();
        }
    }

    public int calculateDamage(int strength) {
        int damage = strength + baseDamage + bonusStrength;
        damage = (int)(damage * type.getDamageMultiplier());

        if (random.nextInt(100) < critChance) {
            damage *= 2;
            System.out.println("⚡ КРИТИЧЕСКИЙ УДАР! ⚡");
        }

        return damage;
    }

    // Геттеры
    public WeaponEffect getEffect() { return effect; }
    public WeaponType getType() { return type; }
    public WeaponRarity getRarity() { return rarity; }
    public int getBonusStrength() { return bonusStrength; }
    public int getBonusDefense() { return bonusDefense; }
    public int getCritChance() { return critChance; }
    public int getBaseDamage() { return baseDamage; }

    // Метод для совместимости с Knight.specialAttack()
    public int getDamageBonus() {
        return baseDamage;
    }
    // Добавьте этот метод в класс Weapon.java

    @Override
    public int use() {
        return baseDamage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        sb.append("  Тип: ").append(type.getDisplayName()).append(" (").append(type.getDescription()).append(")\n");
        sb.append("  Редкость: ").append(rarity.getDisplayName()).append("\n");
        sb.append("  Базовый урон: ").append(baseDamage).append("\n");
        sb.append("  Бонус к силе: +").append(bonusStrength).append("\n");
        if (bonusDefense > 0) {
            sb.append("  Бонус к защите: +").append(bonusDefense).append("\n");
        }
        sb.append("  Шанс крита: ").append(critChance).append("%\n");
        sb.append("  Вес: ").append(weight).append("\n");
        if (effect != WeaponEffect.NONE) {
            sb.append("  Эффект: ").append(effect.getName()).append(" - ").append(effect.getDescription()).append("\n");
        }
        return sb.toString();
    }
}