package sample;
/**
 *
 * @author AMOKRANE Abdennour
 * Created by asus on 06/02/2017.
 */
import javafx.scene.paint.Paint;
import sample.Ports.*;
import javafx.scene.shape.Circle;

import java.io.Serializable;
import java.util.ArrayList;

public class Noeud{

    private ComposantLogique emetteurComp=null;
    private ComposantLogique recepteurComp=null;
    private int receptionEntree=0;
    private int emessionSortie=0;
    private Fil emetteurFil=null;
    private ArrayList<Fil> recepteurFil=null;

    private Circle circle;
    private String color;
    private double radius;
    private double coorX;
    private double coorY;

    public Noeud(){
        recepteurFil=new ArrayList<Fil>();
    }

    public Circle getCircle() {return circle;}

    public void setCoorX(double coorX) {
        this.coorX = coorX;
    }

    public void setCoorY(double coorY) {
        this.coorY = coorY;
    }

    public void setCircle() {
        this.circle = new Circle(this.coorX, this.coorY, this.radius, Paint.valueOf(this.color));
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void removeCircle() {
        this.circle = null;
    }

    public ComposantLogique getEmetteurComp() {
        return emetteurComp;
    }

    public void setEmetteurComp(ComposantLogique emetteurComp) {
        this.emetteurComp = emetteurComp;
    }

    public ComposantLogique getRecepteurComp() {
        return recepteurComp;
    }

    public void setRecepteurComp(ComposantLogique recepteurComp) {
        this.recepteurComp = recepteurComp;
    }

    public Fil getEmetteurFil() {
        return emetteurFil;
    }

    public void setEmetteurFil(Fil emetteurFil) {
        this.emetteurFil = emetteurFil;
    }

    public ArrayList<Fil> getRecepteurFil() {
        return recepteurFil;
    }

    public void setRecepteurFil(Fil recepteurFil) {
        this.recepteurFil.add(recepteurFil);
    }

    public boolean isReceptorsNull() {
        return this.recepteurFil.isEmpty();
    }

    public void Simulation() {
        if(emetteurComp != null && !isReceptorsNull()) {
            for (Fil f:
                 recepteurFil) {
                f.setValeur(emetteurComp.getSortie(this.emessionSortie));
            }
        }
        if(emetteurComp != null && recepteurComp != null) recepteurComp.setEntree(this.receptionEntree, emetteurComp.getSortie(this.emessionSortie));
        if(emetteurFil != null && !isReceptorsNull()) {
            for (Fil f:
                 recepteurFil) {
                f.setValeur(emetteurFil.getValeur());
            }
        }
        if(emetteurFil != null && recepteurComp != null) recepteurComp.setEntree(this.receptionEntree, emetteurFil.getValeur());
    }

    public int getReceptionEntree() {
        return receptionEntree;
    }

    public void setReceptionEntree(int receptionEntree) {
        this.receptionEntree = receptionEntree;
    }

    public int getEmessionSortie() {
        return emessionSortie;
    }

    public void setEmessionSortie(int emessionSortie) {
        this.emessionSortie = emessionSortie;
    }
}
