// Location.java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Location {
    private String name;
    private String description;
    private List<Enemy> possibleEnemies;
    private List <Location> locations;
    private int encounterRate;
    private Random random;

    public Location(String name, String description, int encounterRate) {
        this.name = name;
        this.description = description;
        this.encounterRate = encounterRate;
        this.possibleEnemies = new ArrayList<>();
        this.random = new Random();
    }

    public void addEnemy(Enemy enemy) {
        possibleEnemies.add(enemy);
    }

    public Enemy getRandomEnemy() {
        if (possibleEnemies.isEmpty()) {
            return new Enemy("Странное существо", "normal");
        }
        return possibleEnemies.get(random.nextInt(possibleEnemies.size()));
    }

    public boolean checkEncounter() {
        return random.nextInt(100) < encounterRate;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Enemy> getPossibleEnemies() { return possibleEnemies; }
    public int getEncounterRate() { return encounterRate; }

    @Override
    public String toString() {
        return name + " - " + description;
    }
}
