package sample;

import sample.Ports.ComposantLogique;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by AMOKRANE Abdennour on 22/04/2017.
 */
public class composantSauv implements Serializable {

    private Class aClass;
    private double positionX;
    private double positionY;
    private double rotation=0;

    public composantSauv(ComposantLogique composant) {
        this.aClass = composant.getClass();
        this.positionX = composant.getPosXImageView();
        this.positionY = composant.getPosYImageView();
        this.rotation = composant.getImageView().getRotate();
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
