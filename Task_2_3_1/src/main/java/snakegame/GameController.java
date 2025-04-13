package snakegame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

public class GameController {
    @FXML private VBox startScreen;
    @FXML private TextField scoreInput;
    @FXML private Canvas gameCanvas;
    @FXML private Label scoreLabel;
    @FXML private Button restartButton;

    private Image appleImage = new Image(getClass().getResourceAsStream("/apple.png"));

    private static final int CELL_SIZE = 20;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;

    private GameField gameField;
    private Snake snake;
    private boolean running;
    private Thread gameLoop;
    private boolean gameOverDisplayed = false;
    private int scoreToWin;

    @FXML
    private void startGame() {
        try {
            scoreToWin = Integer.parseInt(scoreInput.getText());
            if (scoreToWin <= 0 || scoreToWin >= 600) {
                throw new NumberFormatException();
            }

            startScreen.setVisible(false);

            gameCanvas.setVisible(true);
            scoreLabel.setVisible(true);
            restartButton.setVisible(true);

            restartGame();
        } catch (NumberFormatException e) {
            scoreInput.setText("Неверный ввод!");
        }
    }

    @FXML
    public void initialize() {
        showStartScreen();
        restartButton.setOnAction(e -> showStartScreen());
        restartButton.setFocusTraversable(false);
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPress);
    }

    private void restartGame() {
        gameField = new GameField(WIDTH, HEIGHT);
        snake = new Snake(WIDTH / 2, HEIGHT / 2, gameField);
        running = true;

        if (gameLoop != null) {
            gameLoopStop();
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
                        Platform.runLater(() -> fullDraw());
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
            CellType collision = snake.checkCollision(WIDTH, HEIGHT);
            if (collision == CellType.WALL || collision == CellType.SNAKE) {
                running = false;
                gameLoopStop();
                drawGameOver();
                return;
            }

            Point clearTail = null;
            Point newFood = null;

            if (collision == CellType.FOOD) {
                snake.grow();
                if (snake.getBody().size() >= scoreToWin) {
                    running = false;
                    gameLoopStop();
                    drawVictory();
                    return;
                }
                newFood = gameField.generateFood(snake.getBody(), snake.getNextHeadPosition());
            } else {
                clearTail = snake.move();
            }

            draw(clearTail, newFood);
        }
    }

    private void fullDraw() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.ALICEBLUE);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        for (var food : gameField.getFoodPositions()) {
            gc.drawImage(appleImage, food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        gc.setFill(Color.GREEN);
        for (var segment : snake.getBody()) {
            gc.fillRect(segment.x * CELL_SIZE, segment.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        scoreLabel.setText("Score: " + snake.getBody().size());
    }

    private void draw(Point clearTail, Point newFood) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        if(clearTail != null) {
            gc.setFill(Color.ALICEBLUE);
            gc.fillRect(clearTail.x * CELL_SIZE, clearTail.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
        if (newFood != null) {
            gc.drawImage(appleImage, newFood.x * CELL_SIZE, newFood.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
        gc.setFill(Color.GREEN);
        gc.fillRect(snake.getBody().getFirst().x * CELL_SIZE, snake.getBody().getFirst().y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

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

    private void showStartScreen() {
        Platform.runLater(() -> {
            startScreen.setVisible(true);
            scoreInput.clear();
            gameCanvas.setVisible(false);
            scoreLabel.setVisible(false);
            restartButton.setVisible(false);
        });
    }

    private void gameLoopStop() {
        gameLoop.interrupt();
        gameLoop = null;
    }

    private void drawVictory() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.GREENYELLOW);
        gc.setFont(new Font(30));
        gc.fillText("Victory", gameCanvas.getWidth() / 3, gameCanvas.getHeight() / 2);
    }

}
