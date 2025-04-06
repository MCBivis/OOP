package snakegame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.LinkedList;

public class GameFieldTest {

    @Test
    public void testGenerateFood() {
        GameField gameField = new GameField(30, 20);
        LinkedList<Point> snakeBody = new LinkedList<>();
        snakeBody.add(new Point(5, 5));
        gameField.generateFood(snakeBody, null);
        assertEquals(3, gameField.getFoodPositions().size());
    }

    @Test
    public void testFoodNotCollided() {
        for(int i = 0; i < 1000; i++) {
            GameField gameField = new GameField(30, 20);
            LinkedList<Point> snakeBody = new LinkedList<>();
            snakeBody.add(new Point(5, 5));
            gameField.generateFood(snakeBody, null);
            for (Point foodPosition : gameField.getFoodPositions()){
                assertNotSame(foodPosition, snakeBody.getFirst());
            }
        }
    }
}
