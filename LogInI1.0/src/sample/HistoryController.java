package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by asus on 28/02/2017.
 */
public class HistoryController {

    @FXML
    private Pane historyVbox;

    @FXML
    private AnchorPane viderPane;

    @FXML
    private Label date;

    @FXML
    private TableColumn Action;

    @FXML
    private TableView<Processus> Table=new TableView<Processus>();

    @FXML
    private TableColumn Temps;

    @FXML
    private TableColumn Statut;

    @FXML
    private Button Vider;

    public static class Processus {

        private final SimpleStringProperty temps;
        private final SimpleStringProperty action;
        private final SimpleStringProperty statut;

        public Processus(String temps, String action, String statut) {
            this.temps = new SimpleStringProperty(temps);
            this.action = new SimpleStringProperty(action);
            this.statut = new SimpleStringProperty(statut);
        }

        public String getTemps() {
            return temps.get();
        }

        public SimpleStringProperty tempsProperty() {
            return temps;
        }

        public String getAction() {
            return action.get();
        }

        public SimpleStringProperty actionProperty() {
            return action;
        }

        public String getStatut() {
            return statut.get();
        }

        public SimpleStringProperty statutProperty() {
            return statut;
        }

        public void setTemps(String temps) {
            this.temps.set(temps);
        }

        public void setAction(String action) {
            this.action.set(action);
        }

        public void setStatut(String statut) {
            this.statut.set(statut);
        }
    }

    public static final ObservableList<Processus> data =
            FXCollections.observableArrayList();

    void setDate() {
        Timeline timeline=new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), actionEvent -> {
            date.setText("Date : ");
            date.setText(date.getText() + new SimpleDateFormat("dd/MM/YYYY  HH:mm:ss").format(Calendar.getInstance().getTime()));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void initialize() {
        setDate();
        Action.setCellValueFactory(new PropertyValueFactory<Processus, String>("action"));
        Temps.setCellValueFactory(new PropertyValueFactory<Processus, String>("temps"));
        Statut.setCellValueFactory(new PropertyValueFactory<Processus, String>("statut"));
        Table.setItems(data);
        Table.setEditable(true);

        Vider.setOnAction((e) -> {
            data.clear();
        });
    }
}
