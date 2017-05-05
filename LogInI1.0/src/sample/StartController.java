package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by asus on 16/02/2017.
 */
public class StartController {

    @FXML
    private ProgressBar bar;

    void loadStart() {
        Main.isFull = true;

        Timeline timeline=new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2100), actionEvent -> {
            if (bar.getProgress() <= 1.5) bar.setProgress(bar.getProgress() + 0.4);
            if(bar.getProgress() > 1.5) {
                timeline.stop();
                Controller.stage.close();
                Main.stage.show();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void initialize() {
        System.out.println("StartController lunched ...");
        if (!Main.isFull) loadStart();
    }
}
