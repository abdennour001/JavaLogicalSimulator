package sample;

import com.sun.webkit.dom.HTMLLabelElementImpl;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.VLineTo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by AMOKRANE Abdennour on 23/04/2017.
 */
public class filSauv implements Serializable{

    private int debutId;
    private int finId;
    private double debutCoorX;
    private double debutCoorY;
    private double finCoorX;
    private double finCoorY;
    private ArrayList<Class> pointType=new ArrayList<Class>();
    private ArrayList<Double> pointCoordinate=new ArrayList<Double>();

    public filSauv(Fil fil) {
        this.debutCoorX=fil.getNoeudDebut().getCircle().getCenterX();
        this.debutCoorY=fil.getNoeudDebut().getCircle().getCenterY();
        this.finCoorX=fil.getNoeudFin().getCircle().getCenterX();
        this.finCoorY=fil.getNoeudFin().getCircle().getCenterY();

        for (PathElement p:
             fil.getFil().getElements()) {
            if(p.getClass() != MoveTo.class) {
                pointType.add(p.getClass());
                if(p.getClass() == HLineTo.class) {
                    pointCoordinate.add(((HLineTo) p).getX());
                } else {
                    pointCoordinate.add(((VLineTo) p).getY());
                }
            }
        }
    }

    public double getDebutCoorX() {
        return debutCoorX;
    }

    public void setDebutCoorX(double debutCoorX) {
        this.debutCoorX = debutCoorX;
    }

    public ArrayList<Class> getPointType() {
        return pointType;
    }

    public void setPointType(ArrayList<Class> pointType) {
        this.pointType = pointType;
    }

    public ArrayList<Double> getPointCoordinate() {
        return pointCoordinate;
    }

    public void setPointCoordinate(ArrayList<Double> pointCoordinate) {
        this.pointCoordinate = pointCoordinate;
    }

    public double getDebutCoorY() {
        return debutCoorY;
    }

    public void setDebutCoorY(double debutCoorY) {
        this.debutCoorY = debutCoorY;
    }

    public double getFinCoorX() {
        return finCoorX;
    }

    public void setFinCoorX(double finCoorX) {
        this.finCoorX = finCoorX;
    }

    public double getFinCoorY() {
        return finCoorY;
    }

    public void setFinCoorY(double finCoorY) {
        this.finCoorY = finCoorY;
    }

    public int getDebutId() {
        return debutId;
    }

    public void setDebutId(int debutId) {
        this.debutId = debutId;
    }

    public int getFinId() {
        return finId;
    }

    public void setFinId(int finId) {
        this.finId = finId;
    }
}
