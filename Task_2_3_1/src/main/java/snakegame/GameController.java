package snakegame;

import javafx.application.Platform;
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
    private Thread gameLoop;
    private boolean gameOverDisplayed = false;

    @FXML
    public void initialize() {
        restartButton.setOnAction(e -> restartGame());
        restartButton.setFocusTraversable(false);
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPress);
        restartGame();
    }

    private void restartGame() {
        gameField = new GameField(WIDTH, HEIGHT);
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        running = true;

        if (gameLoop != null) {
            gameLoop.interrupt();
            gameLoop = null;
        }

        gameLoop = new Thread(() -> {
            long lastUpdate = 0;
            long MOVE_INTERVAL = 150_000_000;

            while (running) {
                long now = System.nanoTime();
                if (now - lastUpdate > MOVE_INTERVAL) {
                    int computedDelta = (int) ((now - lastUpdate) / MOVE_INTERVAL);

                    if (lastUpdate == 0) {
                        computedDelta = 1;
                    }

                    lastUpdate = now;

                    final int delta = computedDelta;
                    Platform.runLater(() -> updateGame(delta));
                }
            }
        });

        gameLoop.setDaemon(true);
        gameLoop.start();
    }

    private void updateGame(int delta) {
        if (!running) return;

        for (int i = 0; i < delta; i++) {
            if (snake.checkCollision(WIDTH, HEIGHT)) {
                running = false;
                gameLoop.interrupt();
                gameLoop = null;
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
        }

        draw();
    }

    private void draw() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        gc.setFill(Color.RED);
        for (var food : gameField.getFoodPositions()) {
            gc.fillRect(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        gc.setFill(Color.GREEN);
        for (var segment : snake.getBody()) {
            gc.fillRect(segment.x * CELL_SIZE, segment.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        scoreLabel.setText("Score: " + snake.getBody().size());
    }

    private void drawGameOver() {
        gameOverDisplayed = true;
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

    public boolean isGameOverDisplayed() {
        return gameOverDisplayed;
    }
}
