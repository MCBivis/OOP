package snakegame;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class SnakeGame extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Snake Game");
        InputStream iconStream = getClass().getResourceAsStream("/icon.jpg");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}