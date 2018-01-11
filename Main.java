package sample;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainController.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        final Task task = new Task<Void>(){
            @Override
            protected Void call() throws Exception
            {
                for(int i = 1; i < 100000000; i++)
                {
                    updateProgress(i, 100000000);
                }


                return null;
            }
        };


        task.setOnSucceeded((Event event) -> {
            FXMLLoader innerLoader = new FXMLLoader(getClass().getResource("SceneOne.fxml"));
            try
            {
                Stage stage = new Stage();
                Parent innerRoot = innerLoader.load();
                stage.setTitle("Scene One");
                stage.setScene(new Scene(innerRoot, 500, 500));
                stage.show();
                primaryStage.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        });


        mainController.getPBSplashValue().progressProperty().bind(task.progressProperty());
        mainController.getPISplash().progressProperty().bind(task.progressProperty());

        new Thread(task).start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
