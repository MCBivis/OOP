package snakegame;

import java.awt.Point;
import java.util.*;

public class GameField {
    private final int width, height;
    private final Set<Point> food;
    private final Random random;

    public GameField(int width, int height) {
        this.width = width;
        this.height = height;
        this.food = new HashSet<>();
        this.random = new Random();
        generateFood(new LinkedList<>());
    }

    public void generateFood(LinkedList<Point> occupied) {
        food.clear();

        List<Point> availableCells = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Point point = new Point(x, y);

                if (!occupied.contains(point)) {
                    availableCells.add(point);
                }
            }
        }

        while (food.size() < 3 && !availableCells.isEmpty()) {
            int randomIndex = random.nextInt(availableCells.size());
            Point newFood = availableCells.get(randomIndex);

            food.add(newFood);

            availableCells.remove(randomIndex);
        }
    }

    public boolean isFood(Point p) {
        return food.remove(p);
    }

    public Set<Point> getFoodPositions() {
        return food;
    }
}
