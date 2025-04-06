package snakegame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Point;

public class SnakeTest {

    @Test
    public void testSnakeInitialPosition() {
        GameField gameField = new GameField(30, 20);
        Snake snake = new Snake(5, 5, gameField);
        assertEquals(new Point(5, 5), snake.getBody().getFirst());
    }

    @Test
    public void testSnakeMove() {
        GameField gameField = new GameField(30, 20);
        Snake snake = new Snake(5, 5, gameField);
        snake.move();
        assertEquals(new Point(6, 5), snake.getBody().getFirst());
    }

    @Test
    public void testSnakeGrow() {
        GameField gameField = new GameField(30, 20);
        Snake snake = new Snake(5, 5, gameField);
        snake.grow();
        assertEquals(2, snake.getBody().size());
    }

    @Test
    public void testSnakeCollision() {
        GameField gameField = new GameField(30, 20);
        Snake snake = new Snake(5, 5, gameField);
        snake.changeDirection(0, 1);
        for (int i = 0; i < 20; i++) {
            snake.move();
        }
        assertTrue(snake.checkCollision(30, 20) == CellType.WALL);
    }
}
