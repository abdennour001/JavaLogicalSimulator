package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

/**
 * Created by AMOKRANE Abdennour on 18/04/2017.
 */
public class ControllerEdit {

    @FXML
    private ChoiceBox<Integer> nombreEntrees;

    @FXML
    private Button exit;

    @FXML
    private Button save;

    @FXML
    private CheckBox inv1;

    @FXML
    private CheckBox inv2;

    @FXML
    private CheckBox inv3;

    @FXML
    private CheckBox inv4;


    public static int nombreString = 0;


    @FXML
    public void initialize() {
        nombreEntrees.getItems().add(2);
        nombreEntrees.getItems().add(3);
        nombreEntrees.getItems().add(4);

        switch (Controller.nombreEntree) {
            case 2:
                inv4.setDisable(true);
                inv3.setDisable(true);
                break;
            case 3:
                inv4.setDisable(true);
                break;
        }

        nombreEntrees.setValue(Controller.nombreEntree);
        nombreEntrees.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                inv1.setDisable(false);
                inv2.setDisable(false);
                inv3.setDisable(false);
                inv4.setDisable(false);
                System.out.println(nombreEntrees.getValue());

                switch (nombreEntrees.getValue()) {
                    case 2:
                        inv4.setDisable(true);
                        inv3.setDisable(true);
                        break;
                    case 3:
                        inv4.setDisable(true);
                        break;
                }
            }
        });
        this.save.setOnAction((event) -> {
            Controller.nombreEntree = nombreEntrees.getValue();
            Controller.inv1=inv1.isSelected();
            Controller.inv2=inv2.isSelected();
            Controller.inv3=inv3.isSelected();
            Controller.inv4=inv4.isSelected();
            Controller.actionEdit=true;
            Controller.s.close();
        });
        this.exit.setOnAction((event) -> {
            try {
                Controller.s.close();
            } catch (Exception var2) {
                System.out.print("Edit fail 2");
            }
        });

    }
}
