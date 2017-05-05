package sample.Ports;

import javafx.scene.control.Label;
import sample.LogicalEtat;
import sample.Noeud;

import java.io.Serializable;

/**
 * Created by AMOKRANE Abdennour on 06/03/2017.
 */
public class Branche{
    /*******          BRANCHE C'EST LA CLASSE QUI CONTIENT LE NOEUD LE NOM LE NUMERO ET LA VALEUR DU NOEUD ********/
    private static int id=0;
    private Label IDE;
    private Label IDS;
    private boolean estInverser=false;
    private LogicalEtat valeur=LogicalEtat.ZERO;
    private Noeud noeud;
    private int numeroDeBranche;
    /*************************                     LE CONSTRUCTEUR                    ***************************/
    public Branche() {
        id++;
        IDE=new Label("i "+this.id);
        IDS=new Label("s "+this.id);
        noeud = new Noeud();
    }
        /*************          SETTERS ET GETTTERS                  *****************/
    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Branche.id = id;
    }

    public LogicalEtat isValeur() {
        return valeur;
    }

    public void setValeur(LogicalEtat valeur) {
        this.valeur = valeur;
    }

    public LogicalEtat getValeur() {
        /*if (!this.estInverser) return this.valeur;
        else return this.valeur == LogicalEtat.ONE ? LogicalEtat.ZERO : LogicalEtat.ONE;*/
        if (!this.estInverser) {
            return this.valeur;
        } else {
            if(this.valeur == LogicalEtat.ONE) {
                return LogicalEtat.ZERO;
            } else {
                return LogicalEtat.ONE;
            }
        }
    }

    public Noeud getNoeud() {
        return noeud;
    }

    public void setNoeud(Noeud noeud) {
        this.noeud = noeud;
    }

    public int getNumeroDeBranche() {
        return numeroDeBranche;
    }

    public void setNumeroDeBranche(int numeroDeBranche) {
        this.numeroDeBranche = numeroDeBranche;
    }

    public boolean isEstInverser() {
        return estInverser;
    }

    public void setEstInverser(boolean estInverser) {
        this.estInverser = estInverser;
    }

    public Label getIDE() {
        return IDE;
    }

    public void setIDE(Label IDE) {
        this.IDE = IDE;
    }

    public Label getIDS() {
        return IDS;
    }

    public void setIDS(Label IDS) {
        this.IDS = IDS;
    }
    /************************************************************************************/
}
