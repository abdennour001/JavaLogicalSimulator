package sample.Ports;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.LogicalEtat;
import sample.Noeud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author G21 on 06/02/2017.
 */
public abstract class ComposantLogique{
    //attributes
    protected Label ID;
    protected int nombreDEntrees;
    protected int nombreDSortie;
    protected Branche[] Entrees;
    protected Branche[] Sortie;

    protected ImageView imageView=new ImageView();
    protected Image image;
    protected boolean isSeq ;
    protected double width;
    protected double height;
    protected double posXImageView;
    protected double posYImageView;
    protected List<Noeud> noeudEntrees;
    protected List<Noeud> noeudSortie;

    //methods

    /**
     *
     * @Autor AMOKRANE Abdennour
     *
     * @param n Donner le nombre d'entrées
     * @param m Donner le nombre de sorties
     */

    public ComposantLogique(int n, int m) {
        this.ID=new Label();
        this.setNombreDEntrees(n);
        this.Entrees =new Branche[n];
        this.Sortie =new Branche[m];
        for (int i=0; i<n; i++) this.Entrees[i] = new Branche();
        for (int i=0; i<m; i++) this.Sortie[i] = new Branche();
        this.noeudEntrees = new ArrayList<Noeud>(n);
        this.noeudSortie = new ArrayList<Noeud>(m);
    }

    public void setisSeq (boolean e){
        this.isSeq = e;
    }
    public boolean getisSeq (){
        return this.isSeq ;
    }


    public void setNombreDEntrees(int n) {
        this.nombreDEntrees = n;
    }

    public int getNombreDEntrees() {
        return this.nombreDEntrees;
    }

    public void setEntree(int numeroDEntree, LogicalEtat valeur) {
        this.Entrees[numeroDEntree].setValeur(valeur);
    }

    public LogicalEtat getEntree(int numeroDEntree) {
        return this.Entrees[numeroDEntree].getValeur();
    }

    public void setID(Label Id) {
        this.ID = Id;
    }

    public Label getID() {
        return this.ID;
    }

    public abstract LogicalEtat getSortie(int numeroDeSortie);

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView() {
        this.imageView.setLayoutX(this.posXImageView);
        this.imageView.setLayoutY(this.posYImageView);
        //this.imageView.rotateProperty().set(39.8);
        this.imageView.fitHeightProperty().set(this.height);
        this.imageView.fitWidthProperty().set(this.width);
        this.imageView.pickOnBoundsProperty().set(true);
        this.imageView.preserveRatioProperty().set(true);
        this.imageView.setVisible(true);
    }

    public void removeImageView() {
        this.imageView = null;
    }

    public Image getImage() {
        return image;
    }

    public double getPosXImageView() {
        return posXImageView;
    }

    public void setPosXImageView(double posXImageView) {
        this.posXImageView = posXImageView;
    }

    public double getPosYImageView() {
        return posYImageView;
    }

    public void setPosYImageView(double posYImageView) {
        this.posYImageView = posYImageView;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public List<Noeud> getNoeudEntrees() {
        return noeudEntrees;
    }

    public List<Noeud> getNoeudSortie() {
        return noeudSortie;
    }

    public Branche[] getBrancheEntree() {
        return this.Entrees;
    }

    public Branche[] getBrancheSortie() {
        return this.Sortie;
    }

    public String getObject() {
        String car="";
        car+=getClass().getCanonicalName() + "\r\n";
        car+=getPosXImageView() + "\r\n";
        car+=getPosYImageView() + "\r\n";
        return car;
    }

    public abstract void run() throws InterruptedException;

    public abstract void setNoeudEntrees();

    public abstract void setNoeudSortie();

    public int getNombreDSortie() {
        return this.nombreDSortie;
    }

    public void setNombreDSortie(int nombreDSortie) {
        this.nombreDSortie = nombreDSortie;
    }
}
