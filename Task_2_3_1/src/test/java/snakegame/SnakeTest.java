package snakegame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Point;

public class SnakeTest {

    @Test
    public void testSnakeInitialPosition() {
        Snake snake = new Snake(5, 5);
        assertEquals(new Point(5, 5), snake.getBody().getFirst());
    }

    @Test
    public void testSnakeMove() {
        Snake snake = new Snake(5, 5);
        snake.move();
        assertEquals(new Point(6, 5), snake.getBody().getFirst());
    }

    @Test
    public void testSnakeGrow() {
        Snake snake = new Snake(5, 5);
        snake.grow();
        assertEquals(2, snake.getBody().size());
    }

    @Test
    public void testSnakeCollision() {
        Snake snake = new Snake(5, 5);
        snake.changeDirection(0, 1);
        for (int i = 0; i < 20; i++) {
            snake.move();
        }
        assertTrue(snake.checkCollision(30, 20)); // Проверяем столкновение
    }
}
