// RPGame.java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Weapon> weapons = new ArrayList<>();
    private static List<Armor> armors = new ArrayList<>();

    static {
        // Создание предметов
        weapons.add(new Weapon("Меч", 12, 5));
        weapons.add(new Weapon("Топор", 15, 8));
        weapons.add(new Weapon("Кинжал", 8, 2));
        weapons.add(new Weapon("Двуручный меч", 20, 12));

        armors.add(new Armor("Кожаная броня", 5, 4));
        armors.add(new Armor("Кольчуга", 8, 8));
        armors.add(new Armor("Латы", 12, 12));
        armors.add(new Armor("Щит", 4, 3));
    }

    public static void main(String[] args) {
        mainMenu();
    }

    private static void mainMenu() {
        System.out.println("=".repeat(50));
        System.out.println("ДОБРО ПОЖАЛОВАТЬ В RPG ИГРУ!");
        System.out.println("=".repeat(50));

        // Создание игрока
        System.out.print("Введите имя вашего рыцаря: ");
        String playerName = scanner.nextLine();
        if (playerName.isEmpty()) {
            playerName = "Артур";
        }

        Knight player = new Knight(playerName);
        System.out.println("\nПривет, " + player.getName() + "!");

        // Выбор оружия
        System.out.println("\nДоступное оружие:");
        for (int i = 0; i < weapons.size(); i++) {
            System.out.println((i + 1) + ". " + weapons.get(i));
        }

        System.out.print("Выберите оружие (1-" + weapons.size() + "): ");
        int weaponChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // очистка буфера
        player.equipWeapon(weapons.get(weaponChoice));

        // Выбор брони
        System.out.println("\nДоступная броня:");
        for (int i = 0; i < armors.size(); i++) {
            System.out.println((i + 1) + ". " + armors.get(i));
        }

        System.out.print("Выберите броню (1-" + armors.size() + "): ");
        int armorChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // очистка буфера
        player.equipArmor(armors.get(armorChoice));

        // Основной цикл меню
        boolean running = true;
        while (running) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("ГЛАВНОЕ МЕНЮ");
            System.out.println("=".repeat(50));
            System.out.println("1. Начать лёгкий бой");
            System.out.println("2. Начать обычный бой");
            System.out.println("3. Начать сложный бой");
            System.out.println("4. Показать статистику игрока");
            System.out.println("5. Выйти из игры");

            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            Enemy enemy = null;

            switch (choice) {
                case 1:
                    enemy = new Enemy("Гоблин", "easy");
                    battleSimulation(player, enemy);
                    break;
                case 2:
                    enemy = new Enemy("Орк", "normal");
                    battleSimulation(player, enemy);
                    break;
                case 3:
                    enemy = new Enemy("Дракон", "hard");
                    battleSimulation(player, enemy);
                    break;
                case 4:
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("СТАТИСТИКА ИГРОКА");
                    System.out.println(player);
                    break;
                case 5:
                    System.out.println("Спасибо за игру! До встречи!");
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }

            // Проверка состояния после боя
            if (choice >= 1 && choice <= 3 && !player.isAlive()) {
                System.out.println("\nИгра окончена! Начать заново? (y/n)");
                String restart = scanner.nextLine();
                if (restart.equalsIgnoreCase("y")) {
                    player = new Knight(playerName);
                    player.equipWeapon(weapons.get(weaponChoice));
                    player.equipArmor(armors.get(armorChoice));
                } else {
                    running = false;
                }
            } else if (choice >= 1 && choice <= 3) {
                // Полное восстановление после боя
                player.setHealth(player.getMaxHealth());
                player.setSpecialAbilityReady(true);
            }
        }

        scanner.close();
    }

    private static void battleSimulation(Knight player, Enemy enemy) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("БОЙ НАЧИНАЕТСЯ!");
        System.out.println("=".repeat(50));

        int turn = 1;

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n--- Ход " + turn + " ---");
            System.out.println(player);
            System.out.println(enemy);

            // Ход игрока
            System.out.println("\nВаш ход:");
            System.out.println("1. Обычная атака");
            System.out.println("2. Рыцарский удар (особая способность)");
            System.out.println("3. Пропустить ход");

            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            switch (choice) {
                case 1:
                    player.attack(enemy);
                    break;
                case 2:
                    player.specialAttack(enemy);
                    break;
                default:
                    System.out.println(player.getName() +
                            " отдыхает и восстанавливает 5 здоровья");
                    player.setHealth(player.getHealth() + 5);
            }

            if (!enemy.isAlive()) {
                System.out.println("\n" + enemy.getName() +
                        " повержен! Победа!");
                break;
            }

            // Ход врага
            System.out.println("\nХод врага:");
            enemy.enemyAction(player);

            // Восстановление способностей
            player.regenerate();

            if (!player.isAlive()) {
                System.out.println("\n" + player.getName() +
                        " пал в бою... Поражение!");
                break;
            }

            turn++;
            System.out.print("\nНажмите Enter для продолжения...");
            scanner.nextLine();
        }
    }
}