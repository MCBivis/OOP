package snakegame;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class GameField {
    private int width, height;
    private Set<Point> food;
    private Random random;

    public GameField(int width, int height) {
        this.width = width;
        this.height = height;
        this.food = new HashSet<>();
        this.random = new Random();
        generateFood(new LinkedList<>());
    }

    public void generateFood(LinkedList<Point> occupied) {
        food.clear();
        while (food.size() < 3) {
            Point newFood = new Point(random.nextInt(width), random.nextInt(height));
            if (!occupied.contains(newFood)) {
                food.add(newFood);
            }
        }
    }

    public boolean isFood(Point p) {
        return food.remove(p);
    }

    public Set<Point> getFoodPositions() {
        return food;
    }
}
