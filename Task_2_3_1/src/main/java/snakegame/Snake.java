package snakegame;

import java.awt.Point;
import java.util.LinkedList;

public class Snake {
    private LinkedList<Point> body;
    private int dx, dy;
    private final GameField gameField;

    public Snake(int startX, int startY, GameField gameField) {
        body = new LinkedList<>();
        body.add(new Point(startX, startY));
        dx = 1;
        dy = 0;
        this.gameField = gameField;
    }

    public Point move() {
        Point newHead = getNextHeadPosition();
        body.addFirst(newHead);
        return body.removeLast();
    }

    public void grow() {
        body.addFirst(getNextHeadPosition());
    }

    public void changeDirection(int newDx, int newDy) {
        if (dx != -newDx || dy != -newDy) {
            dx = newDx;
            dy = newDy;
        }
    }

    public Point getNextHeadPosition() {
        Point head = body.getFirst();
        return new Point(head.x + dx, head.y + dy);
    }

    public CellType checkCollision(int width, int height) {
        Point head = body.getFirst();
        if (head.x < 0 || head.x >= width || head.y < 0 || head.y >= height) {
            return CellType.WALL;
        }

        if (body.stream().skip(1).anyMatch(segment -> segment.equals(head))){
            return CellType.SNAKE;
        }
        if (gameField.isFood(head)){
            return CellType.FOOD;
        }
        return CellType.EMPTY;
    }

    public LinkedList<Point> getBody() {
        return body;
    }
}
