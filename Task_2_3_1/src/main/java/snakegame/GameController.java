package snakegame;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameController {
    @FXML private Canvas gameCanvas;
    @FXML private Label scoreLabel;
    @FXML private Button restartButton;

    private static final int CELL_SIZE = 20;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;

    private GameField gameField;
    private Snake snake;
    private boolean running;
    private AnimationTimer gameLoop;

    @FXML
    public void initialize() {
        restartButton.setOnAction(e -> restartGame());
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPress);
        restartGame();
    }

    private void restartGame() {
        gameField = new GameField(WIDTH, HEIGHT);
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        running = true;

        if (gameLoop != null) {
            gameLoop.stop();
        }

        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            private static final long MOVE_INTERVAL = 150_000_000; // 150ms

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= MOVE_INTERVAL) {
                    lastUpdate = now;
                    updateGame();
                }
            }
        };
        gameLoop.start();
    }

    private void updateGame() {
        if (!running) return;

        if (snake.checkCollision(WIDTH, HEIGHT)) {
            running = false;
            gameLoop.stop();
            drawGameOver();
            return;
        }

        boolean ateFood = gameField.isFood(snake.getNextHeadPosition());
        if (ateFood) {
            snake.grow();
            gameField.generateFood(snake.getBody());
        } else {
            snake.move();
        }

        draw();
    }

    private void draw() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // Рисуем еду
        gc.setFill(Color.RED);
        for (var food : gameField.getFoodPositions()) {
            gc.fillRect(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // Рисуем змейку
        gc.setFill(Color.GREEN);
        for (var segment : snake.getBody()) {
            gc.fillRect(segment.x * CELL_SIZE, segment.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        scoreLabel.setText("Score: " + snake.getBody().size());
    }

    private void drawGameOver() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.setFont(new Font(30));
        gc.fillText("Game Over", gameCanvas.getWidth() / 3, gameCanvas.getHeight() / 2);
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP, W -> snake.changeDirection(0, -1);
            case DOWN, S -> snake.changeDirection(0, 1);
            case LEFT, A -> snake.changeDirection(-1, 0);
            case RIGHT, D -> snake.changeDirection(1, 0);
        }
    }
}
