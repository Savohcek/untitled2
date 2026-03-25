// GameState.java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameState {
    private Knight player;
    private Location currentLocation;
    private List<Location> locations;
    private int steps;
    private Random random;
    private int enemiesDefeated;
    private Scanner scanner;

    public GameState(Knight player) {
        this.player = player;
        this.steps = 0;
        this.enemiesDefeated = 0;
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.locations = new ArrayList<>();
        initializeLocations();
        this.currentLocation = locations.get(0);
    }

    private void initializeLocations() {
        // Лес
        Location forest = new Location("Лес", "Тёмный и загадочный лес", 40);
        forest.addEnemy(new Enemy("Волк", "easy"));
        forest.addEnemy(new Enemy("Гоблин", "easy"));
        forest.addEnemy(new Enemy("Лесной тролль", "normal"));

        // Горная тропа
        Location mountains = new Location("Горная тропа", "Опасный горный перевал", 35);
        mountains.addEnemy(new Enemy("Горный орк", "normal"));
        mountains.addEnemy(new Enemy("Каменный голем", "normal"));
        mountains.addEnemy(new Enemy("Грифон", "hard"));

        // Заброшенный замок
        Location castle = new Location("Заброшенный замок", "Мрачные руины древней крепости", 45);
        castle.addEnemy(new Enemy("Скелет-воин", "normal"));
        castle.addEnemy(new Enemy("Призрак", "normal"));
        castle.addEnemy(new Enemy("Рыцарь-мертвец", "hard"));

        // Подземелье
        Location dungeon = new Location("Подземелье", "Тёмные и зловонные катакомбы", 50);
        dungeon.addEnemy(new Enemy("Крысолак", "easy"));
        dungeon.addEnemy(new Enemy("Паук-гигант", "normal"));
        dungeon.addEnemy(new Enemy("Драконий отродье", "hard"));

        // Магический лес
        Location magicForest = new Location("Магический лес", "Лес, наполненный древней магией", 30);
        magicForest.addEnemy(new Enemy("Лесной дух", "normal"));
        magicForest.addEnemy(new Enemy("Древесный страж", "hard"));
        magicForest.addEnemy(new Enemy("Единорог", "hard"));

        locations.add(forest);
        locations.add(mountains);
        locations.add(castle);
        locations.add(dungeon);
        locations.add(magicForest);
    }

    public void travelToRandomLocation() {
        List<Location> availableLocations = new ArrayList<>(locations);
        availableLocations.remove(currentLocation);
        if (!availableLocations.isEmpty()) {
            currentLocation = availableLocations.get(random.nextInt(availableLocations.size()));
        }
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Вы отправились в " + currentLocation);
        System.out.println("=".repeat(50));
    }

    public void travelToSpecificLocation(int index) {
        if (index >= 0 && index < locations.size()) {
            currentLocation = locations.get(index);
            System.out.println("\n" + "=".repeat(50));
            System.out.println("Вы отправились в " + currentLocation);
            System.out.println("=".repeat(50));
        }
    }

    public void explore() {
        steps++;
        System.out.println("\nВы исследуете " + currentLocation.getName() + "...");

        if (currentLocation.checkEncounter()) {
            Enemy enemy = currentLocation.getRandomEnemy();
            System.out.println("\n ВНЕЗАПНАЯ ВСТРЕЧА! ");
            System.out.println("На вас нападает " + enemy.getName() + " (Сложность: " +
                    enemy.getDifficulty() + ", Опыт: " + enemy.getExpReward() + ")!");

            BattleResult result = battleSimulation(enemy);

            if (result.isVictory()) {
                enemiesDefeated++;
                player.addExperience(result.getExperience());

                if (random.nextInt(100) < 30) {
                    findRandomItem();
                }
            } else {
                player.setHealth(0);
            }
        } else {
            System.out.println("Вокруг тихо... Вам удаётся спокойно исследовать местность.");
            int healAmount = 5 + random.nextInt(10);
            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + healAmount));
            System.out.println("Вы нашли лечебные травы и восстановили " + healAmount + " здоровья.");
        }
    }

    private void findRandomItem() {
        int itemType = random.nextInt(4);

        if (itemType == 2) {
            int healAmount = 20 + random.nextInt(30);
            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + healAmount));
            System.out.println(" Вы нашли лечебное зелье! Восстановлено " + healAmount + " здоровья!");
        } else if (itemType == 1) {
            findRandomWeapon();
        } else if (itemType == 3) {
            int bonusExp = 10 + random.nextInt(20);
            player.addExperience(bonusExp);
            System.out.println(" Вы нашли древний свиток! Получено " + bonusExp + " дополнительного опыта!");
        } else {
            System.out.println(" Вы нашли сундук с сокровищами! Но он оказался пуст...");
        }
    }

    private void findRandomWeapon() {
        Weapon newWeapon = new Weapon();
        System.out.println("\n ВЫ НАШЛИ НОВОЕ ОРУЖИЕ! ");
        System.out.println(newWeapon);

        System.out.print("Хотите экипировать это оружие? (y/n): ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("y")) {
            player.equipWeapon(newWeapon);
            System.out.println("Вы экипировали " + newWeapon.getName() + "!");
        } else {
            System.out.println("Вы оставили оружие в инвентаре.");
        }
    }

    private BattleResult battleSimulation(Enemy enemy) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(" БОЙ НАЧИНАЕТСЯ! ");
        System.out.println("=".repeat(50));

        Knight playerCopy = new Knight(player.getName());
        playerCopy.setHealth(player.getHealth());
        playerCopy.setSpecialAbilityReady(player.isSpecialAbilityReady());

        if (player.getWeapon() != null) {
            playerCopy.equipWeapon(player.getWeapon());
        }
        if (player.getArmor() != null) {
            playerCopy.equipArmor(player.getArmor());
        }

        // Копируем уровень и опыт через добавление опыта
        for (int i = 1; i < player.getLevel(); i++) {
            playerCopy.addExperience(ExperienceSystem.getExpNeededForLevel(i));
        }

        int turn = 1;
        boolean victory = false;

        while (playerCopy.isAlive() && enemy.isAlive()) {
            System.out.println("\n--- Ход " + turn + " ---");
            System.out.println("Рыцарь:  " + playerCopy.getHealth() + "/" + playerCopy.getMaxHealth() +
                    " (Ур. " + playerCopy.getLevel() + ")");
            System.out.println(enemy.getName() + ":  " + enemy.getHealth() + "/" + enemy.getMaxHealth());

            System.out.println("\nВаш ход:");
            System.out.println("1. Обычная атака");
            System.out.println("2. Рыцарский удар (особая способность)");
            System.out.println("3. Попытаться сбежать");

            System.out.print("Выберите действие: ");
            int choice = getIntInput();

            if (choice == 3) {
                if (tryEscape()) {
                    System.out.println("Вы успешно сбежали из боя!");
                    player.setHealth(playerCopy.getHealth());
                    player.setSpecialAbilityReady(playerCopy.isSpecialAbilityReady());
                    break;
                } else {
                    System.out.println("Не удалось сбежать! Враг не даёт вам уйти!");
                }
            }

            if (choice == 1 || (choice == 3 && !tryEscape())) {
                playerCopy.attack(enemy);
                if (!enemy.isAlive()) {
                    victory = true;
                    enemy.setHealth(enemy.maxHealth);
                    break;
                }
            } else if (choice == 2) {
                playerCopy.specialAttack(enemy);
                if (!enemy.isAlive()) {
                    victory = true;
                    enemy.setHealth(enemy.maxHealth);
                    break;
                }
            }

            // Ход врага
            System.out.println("\nХод врага:");
            enemy.enemyAction(playerCopy);

            if (!playerCopy.isAlive()) {
                victory = false;
                break;
            }

            playerCopy.regenerate();
            turn++;
        }

        if (victory) {
            System.out.println("\n ПОБЕДА! ");
            System.out.println("Вы победили " + enemy.getName() + "!");
            System.out.println("Получено опыта: " + enemy.getExpReward());

            player.setHealth(playerCopy.getHealth());
            player.setSpecialAbilityReady(playerCopy.isSpecialAbilityReady());

            return new BattleResult(true, enemy.getExpReward());
        } else {
            System.out.println("\n ПОРАЖЕНИЕ! ");
            return new BattleResult(false, 0);
        }
    }

    private boolean tryEscape() {
        return random.nextInt(100) < 40;
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return 1;
        }
    }

    public Knight getPlayer() { return player; }
    public Location getCurrentLocation() { return currentLocation; }
    public List<Location> getLocations() { return locations; }
    public int getSteps() { return steps; }
    public int getEnemiesDefeated() { return enemiesDefeated; }
}