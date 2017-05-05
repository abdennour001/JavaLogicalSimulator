package sample;

/**
 * Created by asus on 13/03/2017.
 */
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Controller;
import sample.Noeud;
import sample.Ports.Branche;
import sample.Ports.ComposantLogique;

import java.net.URISyntaxException;

/**
 * @Author BERARMA Oussama
 * Created by DELL on 06/03/2017.
 */
     /******************************************            *****************************/
     /**************          L'horloge            ********************************/
     //  active indique l'etat du simulation
     //  s     indisue le temps d'une top d'horloge
public class Horloge extends ComposantLogique {
    private boolean active;
    public double s ;
         /***************              LE CONSTRUCTEUR              **********************/
    public Horloge(double tmp) {
        super(0,1);
        super.isSeq = true ;
        try {
            super.image = new Image(getClass().getResource("HORLOGE.png").toURI().toString());
            super.imageView = new ImageView();
            imageView.setImage(image);
            this.setWidth(90);
            this.setHeight(53);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.s = tmp;
    }

    @Override
    public LogicalEtat getSortie(int numeroDeSortie) {
        return super.Sortie[numeroDeSortie].getValeur();
    }

    /******                            LA PROCEDURE DU QUI DONNE LA VALEUR LOGUIQUE A LA SORTIE             ****/
    @Override
    public void run() {
        this.active=true;
        while(this.active)
        {
            if (this.Sortie[0].getValeur() == LogicalEtat.ZERO)
            {
                this.Sortie[0].setValeur( LogicalEtat.ONE);
            }
            else
            {
                this.Sortie[0].setValeur(LogicalEtat.ZERO);
            }
            try {
                Thread.sleep(((long)this.s)*500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNoeudEntrees() {
    }
    /**************                            FAIRE POSITIONNER LE NOEUD DU SORTIE      ******************/
    public void setNoeudSortie() {
        Noeud n = new Noeud();
        n.setColor("CYAN");
        n.setRadius(4);
        n.setCoorX(this.getPosXImageView() + 90);
        n.setCoorY(this.getPosYImageView() + 26);
        n.setCircle();
        n.setEmessionSortie(0);
        this.Sortie[0].setNoeud(n);
        this.Sortie[0].setNumeroDeBranche(0);
        this.noeudSortie.add(n);
        Controller.noeudsList.add(n);
    }
    public void descativate()
    {
        this.active = false;
        this.Sortie[0].setValeur( LogicalEtat.ZERO);
    }
}