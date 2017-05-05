package sample;

import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

import java.util.ArrayList;

/**
 * Created by asus on 06/02/2017.
 */
public class Fil {
    private LogicalEtat Valeur;
    // les composants d'un fil
    private Path fil=new Path();
    private ArrayList<MoveTo> moveTo=new ArrayList<MoveTo>();
    private ArrayList<VLineTo> filVertical=new ArrayList<VLineTo>();
    private ArrayList<HLineTo> filHorisontal=new ArrayList<HLineTo>();
    //---------------------------
    private Noeud noeudDebut;
    private Noeud noeudFin;

    public Fil() {
        // ajouter les composants d'un fil
        //this.fil.getElements().addAll(this.moveTo);
    }

    public Fil(LogicalEtat Valeur) {
        this.Valeur = Valeur;
    }

    public void setValeur(LogicalEtat valeur) {
        this.Valeur = valeur;
    }

    public LogicalEtat getValeur() {
        return this.Valeur;
    }

    public Path getFil() {
        return fil;
    }

    public Noeud getNoeudDebut() {
        return noeudDebut;
    }

    public void setNoeudDebut(Noeud noeudDebut) {
        this.noeudDebut = noeudDebut;
    }

    public Noeud getNoeudFin() {
        return noeudFin;
    }

    public void setNoeudFin(Noeud noeudFin) {
        this.noeudFin = noeudFin;
    }

    public ArrayList<VLineTo> getFilVertical() {
        return filVertical;
    }

    public void addFilVertical(VLineTo filVertical) {
        this.filVertical.add(filVertical);
    }

    public void removeFilVertical(VLineTo filVertical) {
        this.filVertical.remove(filVertical);
    }

    public ArrayList<HLineTo> getFilHorisontal() {
        return filHorisontal;
    }

    public void addFilHorisontal(HLineTo filHorisontal) {
        this.filHorisontal.add(filHorisontal);
    }

    public void removeFilHorisontal(HLineTo filHorisontal) {
        this.filHorisontal.remove(filHorisontal);
    }

    public ArrayList<MoveTo> getMoveTo() {
        return moveTo;
    }

    public void addMoveTo(MoveTo moveTo) {
        this.moveTo.add(moveTo);
    }

    public void removeMoveTo(MoveTo moveTo) {
        this.moveTo.remove(moveTo);
    }
}
