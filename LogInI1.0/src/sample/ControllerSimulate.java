package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TouchEvent;

/**
 * Created by AMOKRANE Abdennour on 19/04/2017.
 */
public class ControllerSimulate {

    @FXML
    private Slider sliderSimulator;

    public void  initialize() {
        sliderSimulator.setMin(50);
        sliderSimulator.setMax(500);
        sliderSimulator.setValue(40);
        sliderSimulator.setShowTickLabels(true);
        sliderSimulator.setShowTickMarks(true);
        sliderSimulator.setMajorTickUnit(50);
        sliderSimulator.setMinorTickCount(5);
        sliderSimulator.setBlockIncrement(10);
        sliderSimulator.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                //System.out.println(sliderSimulator.getValue());
                loader.vitesse = (long) (sliderSimulator.getValue());
            }
        });
    }
}