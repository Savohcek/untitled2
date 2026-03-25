// Main.java (или RPGame.java)
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class RPGame {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("⚔️  ДОБРО ПОЖАЛОВАТЬ В RPG ПРИКЛЮЧЕНИЕ!  ⚔️");
        System.out.println("=".repeat(50));

        System.out.print("Введите имя вашего рыцаря: ");
        String playerName = scanner.nextLine();
        if (playerName.isEmpty()) {
            playerName = "Артур";
        }

        Knight player = new Knight(playerName);
        System.out.println("\nПриветствую, сэр " + player.getName() + "!");

        initializeEquipment(player);

        GameState gameState = new GameState(player);

        boolean playing = true;
        while (playing && player.isAlive()) {
            displayGameStatus(gameState);
            displayGameMenu();

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    gameState.explore();
                    break;
                case 2:
                    gameState.travelToRandomLocation();
                    break;
                case 3:
                    showLocationsMenu(gameState);
                    break;
                case 4:
                    showCharacterInfo(player);
                    break;
                case 5:
                    showStats(gameState);
                    break;
                case 6:
                    System.out.println("\nСпасибо за игру, " + player.getName() + "!");
                    System.out.println("Ваши достижения:");
                    System.out.println("  • Уровень: " + player.getLevel());
                    System.out.println("  • Повержено врагов: " + gameState.getEnemiesDefeated());
                    System.out.println("  • Совершено шагов: " + gameState.getSteps());
                    playing = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }

            if (!player.isAlive()) {
                System.out.println("\n💀 " + player.getName() + " пал в бою... 💀");
                System.out.println("Ваше приключение закончилось!");
                System.out.println("Достижения:");
                System.out.println("  • Достигнутый уровень: " + player.getLevel());
                System.out.println("  • Повержено врагов: " + gameState.getEnemiesDefeated());
                System.out.println("  • Совершено шагов: " + gameState.getSteps());
                System.out.print("\nХотите начать заново? (y/n): ");
                String restart = scanner.nextLine();
                if (restart.equalsIgnoreCase("y")) {
                    player = new Knight(playerName);
                    initializeEquipment(player);
                    gameState = new GameState(player);
                } else {
                    playing = false;
                }
            }
        }

        System.out.println("\nДо новых приключений!");
        scanner.close();
    }

    private static void initializeEquipment(Knight player) {
        System.out.println("\nВЫБЕРИТЕ ОРУЖИЕ ДЛЯ НАЧАЛА ПУТИ:");
        System.out.println("=".repeat(50));

        Weapon[] starterWeapons = {
                new Weapon(WeaponType.SWORD, WeaponRarity.COMMON, WeaponEffect.NONE),
                new Weapon(WeaponType.AXE, WeaponRarity.COMMON, WeaponEffect.BLEEDING),
                new Weapon(WeaponType.DAGGER, WeaponRarity.UNCOMMON, WeaponEffect.CRITICAL),
                new Weapon(WeaponType.SPEAR, WeaponRarity.COMMON, WeaponEffect.NONE),
                new Weapon(WeaponType.MACE, WeaponRarity.UNCOMMON, WeaponEffect.ARMOR_PIERCE)
        };

        for (int i = 0; i < starterWeapons.length; i++) {
            System.out.println((i + 1) + ". " + starterWeapons[i].getName());
            System.out.println("   " + starterWeapons[i].getType().getDisplayName() +
                    " | " + starterWeapons[i].getRarity().getDisplayName() +
                    " | Урон: " + starterWeapons[i].getBaseDamage() +
                    " | Крит: " + starterWeapons[i].getCritChance() + "%");
            if (starterWeapons[i].getEffect() != WeaponEffect.NONE) {
                System.out.println("   ✨ Эффект: " + starterWeapons[i].getEffect().getName());
            }
            System.out.println();
        }

        System.out.print("Выберите оружие (1-" + starterWeapons.length + "): ");
        int weaponChoice = getIntInput() - 1;

        if (weaponChoice >= 0 && weaponChoice < starterWeapons.length) {
            player.equipWeapon(starterWeapons[weaponChoice]);
        } else {
            player.equipWeapon(starterWeapons[0]);
        }

        System.out.println("\nВыберите броню для защиты:");
        String[] armors = {"Кожаная броня", "Кольчуга", "Латы", "Щит"};
        int[] armorDefense = {5, 8, 12, 4};
        int[] armorWeight = {4, 8, 12, 3};

        for (int i = 0; i < armors.length; i++) {
            System.out.println((i + 1) + ". " + armors[i] + " (защита: " + armorDefense[i] + ", вес: " + armorWeight[i] + ")");
        }

        int armorChoice = getIntInput() - 1;
        if (armorChoice >= 0 && armorChoice < armors.length) {
            player.equipArmor(new Armor(armors[armorChoice], armorDefense[armorChoice], armorWeight[armorChoice]));
        }
    }

    private static void displayGameStatus(GameState gameState) {
        Knight player = gameState.getPlayer();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("📜 СТАТУС ПРИКЛЮЧЕНИЯ 📜");
        System.out.println("=".repeat(50));
        System.out.println("Локация: " + gameState.getCurrentLocation());
        System.out.println("Уровень: " + player.getLevel() + " | Опыт: " + player.getExperience() + "/" + player.getExpNeeded());
        System.out.println("Здоровье: ❤️ " + player.getHealth() + "/" + player.getMaxHealth());
        System.out.println("Сила: ⚔️ " + player.getStrength());
        System.out.println("Повержено врагов: " + gameState.getEnemiesDefeated());
        System.out.println("Шагов сделано: " + gameState.getSteps());

        if (player.getWeapon() != null) {
            System.out.println("Оружие: ⚔️ " + player.getWeapon().getName());
        }
        if (player.getArmor() != null) {
            System.out.println("Броня: 🛡️ " + player.getArmor().getName());
        }
    }

    private static void displayGameMenu() {
        System.out.println("\n📋 ЧТО БУДЕМ ДЕЛАТЬ? 📋");
        System.out.println("=".repeat(50));
        System.out.println("1. 🔍 Исследовать текущую локацию");
        System.out.println("2. 🚶 Отправиться в случайное путешествие");
        System.out.println("3. 🗺️ Выбрать конкретную локацию");
        System.out.println("4. 📊 Показать информацию о персонаже");
        System.out.println("5. 🏆 Показать статистику");
        System.out.println("6. 🏰 Завершить приключение");
        System.out.print("\nВаш выбор: ");
    }

    private static void showLocationsMenu(GameState gameState) {
        System.out.println("\n🗺️ ДОСТУПНЫЕ ЛОКАЦИИ 🗺️");
        System.out.println("=".repeat(50));

        List<Location> locations = gameState.getLocations();
        for (int i = 0; i < locations.size(); i++) {
            System.out.println((i + 1) + ". " + locations.get(i));
        }

        System.out.print("\nВыберите локацию (1-" + locations.size() + "): ");
        int locationChoice = getIntInput() - 1;

        if (locationChoice >= 0 && locationChoice < locations.size()) {
            gameState.travelToSpecificLocation(locationChoice);
        } else {
            System.out.println("Неверный выбор!");
        }
    }

    private static void showCharacterInfo(Knight player) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("👑 " + player.getName().toUpperCase() + " 👑");
        System.out.println("=".repeat(50));
        System.out.println(player);
        System.out.println("\n✨ Особые способности:");
        System.out.println("  • Рыцарский удар: мощная атака, наносящая удвоенный урон оружия + 30 + сила персонажа");
        System.out.println("  • Восстановление: 30% шанс восстановить способность после каждого хода");
        System.out.println("\n📈 Система уровней:");
        System.out.println("  • Каждый уровень даёт +5 к здоровью и силе");
        System.out.println("  • Повышение уровня полностью восстанавливает здоровье");
        System.out.println("  • Требуется опыта для уровня: " + ExperienceSystem.getExpNeededForLevel(player.getLevel() + 1));
    }

    private static void showStats(GameState gameState) {
        Knight player = gameState.getPlayer();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🏆 СТАТИСТИКА ПРИКЛЮЧЕНИЙ 🏆");
        System.out.println("=".repeat(50));
        System.out.println("Имя героя: " + player.getName());
        System.out.println("Уровень: " + player.getLevel());
        System.out.println("Всего опыта: " + player.getExperience());
        System.out.println("Повержено врагов: " + gameState.getEnemiesDefeated());
        System.out.println("Совершено шагов: " + gameState.getSteps());
        System.out.println("Текущая локация: " + gameState.getCurrentLocation().getName());
        System.out.println("\nХарактеристики:");
        System.out.println("  • Максимальное здоровье: " + player.getMaxHealth());
        System.out.println("  • Сила: " + player.getStrength());
        if (player.getWeapon() != null) {
            System.out.println("  • Урон с оружием: " + (player.getStrength() + player.getWeapon().getBaseDamage()));
        }
        if (player.getArmor() != null) {
            System.out.println("  • Защита: " + player.getArmor().getDefense());
        }
    }

    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return 1;
        }
    }
}