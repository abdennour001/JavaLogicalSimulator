package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Ports.ComposantLogique;

import java.net.URISyntaxException;

/**
 * Created by asus on 04/03/2017.
 */
public class ExitController {

    @FXML
    ImageView imageView;

    @FXML
    Button quitter;

    @FXML
    Button annuler;

    @FXML
    public void initialize() {
        try {
            imageView.setImage(new Image(getClass().getResource("icons/question.png").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        quitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.stage.close();
            }
        });
        annuler.setOnAction((event -> {

            Controller.stage.close();
        }));
    }
}
