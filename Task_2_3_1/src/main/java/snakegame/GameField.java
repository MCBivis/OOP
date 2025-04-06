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
        generateFood(new LinkedList<>(), null);
    }

    public Point generateFood(LinkedList<Point> occupied, Point eatenFood) {
        food.remove(eatenFood);

        List<Point> availableCells = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Point point = new Point(x, y);

                if (!occupied.contains(point) && point != eatenFood && !food.contains(point)) {
                    availableCells.add(point);
                }
            }
        }

        Point newFood = null;

        while (food.size() < 3 && !availableCells.isEmpty()) {
            int randomIndex = random.nextInt(availableCells.size());
            newFood = availableCells.get(randomIndex);

            food.add(newFood);

            availableCells.remove(randomIndex);
        }

        return newFood;
    }

    public boolean isFood(Point p) {
        return food.remove(p);
    }

    public Set<Point> getFoodPositions() {
        return food;
    }
}
