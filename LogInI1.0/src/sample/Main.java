package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.awt.*;

public class Main extends Application {

    public static boolean isFull=false;
    public static Pane root;
    public static Stage stage;
    public static Dimension resolution= Toolkit.getDefaultToolkit().getScreenSize();
    public static final int MAX_X=(int)resolution.getWidth();
    public static final int MAX_Y=(int)resolution.getHeight() - 45;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        root=FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setTitle("["+Controller.projectName+"] - LogInI 2017.1");
        Image icon=new Image(getClass().getResource("icon.png").toURI().toString());
        stage.setScene(new Scene(root, MAX_X, MAX_Y));
        stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("F11"));
        stage.getIcons().add(icon);
        stage.initStyle(StageStyle.DECORATED);
        stage.setOnHidden(e -> Platform.exit());
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Controller cn=new Controller();
                cn.exitAction();
                try {
                    stage.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
