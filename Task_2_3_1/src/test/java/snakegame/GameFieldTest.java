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
        gameField.generateFood(snakeBody);
        assertEquals(3, gameField.getFoodPositions().size()); // Три еды должны быть сгенерированы
    }

    @Test
    public void testFoodNotCollided() {
        GameField gameField = new GameField(30, 20);
        LinkedList<Point> snakeBody = new LinkedList<>();
        snakeBody.add(new Point(5, 5));
        for(int i = 0; i < 1000; i++) {
            gameField.generateFood(snakeBody);
            for (Point foodPosition : gameField.getFoodPositions()){
                assertNotSame(foodPosition, snakeBody.getFirst());
            }
        }
    }
}
