package sample;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URISyntaxException;

import sample.Ports.Branche;
import sample.Ports.ComposantLogique;

/**
 * Created by BERARMA Oussama on 24/02/2017.
 */
public class Bascule {
    // La classe Bascule contient les bascules : JK,RS,RST,D,DLATCH,JKasyn,T.
    /*******************************************************************************************************************/
    // boolean up indique / fonctionne en front-montont si up est vraie ou front-decendent si  up est faux
    //boolean active /  indique l'etat du simulation
                              /**                    BASCULE JK                    **/
    public static  class JK extends ComposantLogique {
        private boolean active ;
        private boolean up;
        /*****                       Constructeur               **********/
        public JK(boolean up) {
            super (4 , 2);
            super.isSeq = true;
            this.up = up ;
            try {
                image = new Image(getClass().getResource("JK.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(160);
                this.setHeight(93);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortiejk() {
            for (int i = 0; i < 2; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 160);
                        n.setCoorY(this.getPosYImageView() + 24);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 160);
                        n.setCoorY(this.getPosYImageView() +62 );

                        break;
                }
                n.setCircle();
                n.setEmessionSortie(i);
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }
        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        public void run() throws InterruptedException {
            this.active = true;
            Branche m =new Branche();
            while (this.active) {
                do {
                    if (super.Entrees [3].getValeur() == LogicalEtat.NONE)
                    {
                        super.Sortie [0].setValeur(LogicalEtat.NONE);
                        super.Sortie[1].setValeur(LogicalEtat.NONE);
                    }
                    if (super.Entrees [3].getValeur() == LogicalEtat.ZERO){
                        super.Sortie [0].setValeur(LogicalEtat.ZERO);
                        super.Sortie[1].setValeur(LogicalEtat.ONE);
                    }
                    m.setValeur(super.Entrees[2].getValeur());
                    Thread.sleep((long) 10);
                }
                while (!(super.Entrees [3].getValeur() == LogicalEtat.ONE) ||((!((up) && (m.getValeur() == LogicalEtat.ZERO) && (super.Entrees[2].getValeur() == LogicalEtat.ONE))) && (!((!(up)) && (m.getValeur() == LogicalEtat.ONE) && (super.Entrees[2].getValeur() == LogicalEtat.ZERO)))));
                if ((super.Entrees [0].getValeur() == LogicalEtat.ONE) && (super.Entrees[1].getValeur() == LogicalEtat.ZERO)) {
                    super.Sortie [0].setValeur(LogicalEtat.ONE);
                    super.Sortie[1].setValeur(LogicalEtat.ZERO);
                }
                if ((super.Entrees [0].getValeur() == LogicalEtat.ONE) && (super.Entrees[1].getValeur() == LogicalEtat.ONE)) {
                    if (super.Sortie [0].getValeur() == LogicalEtat.ONE) {
                        super.Sortie [0].setValeur(LogicalEtat.ZERO);
                        super.Sortie[1].setValeur(LogicalEtat.ONE);
                    } else {
                        super.Sortie [0].setValeur(LogicalEtat.ONE);
                        super.Sortie[1].setValeur(LogicalEtat.ZERO);
                    }
                }
                if ((super.Entrees [0].getValeur() == LogicalEtat.ZERO) && (super.Entrees[1].getValeur() == LogicalEtat.ONE)) {
                    super.Sortie [0].setValeur(LogicalEtat.ZERO);
                    super.Sortie[1].setValeur(LogicalEtat.ONE);
                }
                if ((super.Entrees [0].getValeur() == LogicalEtat.NONE) || (super.Entrees[1].getValeur() == LogicalEtat.NONE)) {
                    super.Sortie [0].setValeur(LogicalEtat.NONE);
                    super.Sortie[1].setValeur(LogicalEtat.NONE);
                }
            }
        }
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesJK() {
            for (int i = 0; i < 4; i++) {
                Noeud n=new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 12);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 57);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 36);
                        break;
                    case 3:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 76);
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        public void descative() {
            this.active = false ;
        }

        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void setNoeudEntrees() {

        }

        @Override
        public void setNoeudSortie() {

        }
    }
    /*****************************************                             **************************************/
                             /**                    BASCULE RS                   **/
    public static  class RS extends ComposantLogique {
        private boolean active ;
                                 /*****                       Constructeur               **********/
        public RS() {
            super (2 , 2);
            this.isSeq = true ;
            try {
                super.image = new Image(getClass().getResource("RS.png").toURI().toString());
                super.imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(160);
                this.setHeight(93);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortieRS() {
            for (int i = 0; i < 2; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 162);
                        n.setCoorY(this.getPosYImageView() + 23);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 162);
                        n.setCoorY(this.getPosYImageView() + 64);

                        break;
                }
                n.setCircle();
                n.setEmessionSortie(i);
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }
        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        public void run() throws InterruptedException {
            this.active = true;
            while (this.active) {
                Thread.sleep((long) 10);
                if ((this.Entrees[0].getValeur() == LogicalEtat.ONE) && (this.Entrees[1].getValeur() == LogicalEtat.ONE) ){
                    super.Sortie [0].setValeur( LogicalEtat.ZERO) ;
                    super.Sortie[1].setValeur( LogicalEtat.ZERO) ;
                }
                if ((this.Entrees[0].getValeur() == LogicalEtat.ONE) && (this.Entrees[1].getValeur() == LogicalEtat.ZERO) ){
                    super.Sortie [0].setValeur( LogicalEtat.ONE) ;
                    super.Sortie[1].setValeur( LogicalEtat.ZERO) ;
                }
                if ((this.Entrees[0].getValeur() == LogicalEtat.ZERO) && (this.Entrees[1].getValeur() == LogicalEtat.ONE) ){
                    super.Sortie [0].setValeur( LogicalEtat.ZERO) ;
                    super.Sortie[1].setValeur( LogicalEtat.ONE) ;
                }
                if ((this.Entrees[0].getValeur() == LogicalEtat.NONE) && (this.Entrees[1].getValeur() == LogicalEtat.NONE) ){
                    super.Sortie [0].setValeur( LogicalEtat.NONE) ;
                    super.Sortie[1].setValeur( LogicalEtat.NONE) ;
                }
            }
        }
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesRS() {
            for (int i = 0; i < 2; i++) {
                Noeud n=new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 23);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 64);
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        public void descative()
        {
            this.active = false ;
        }


        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void setNoeudEntrees() {

        }

        @Override
        public void setNoeudSortie() {

        }
    }
    /*****************************************                             **************************************/
                              /**                    BASCULE RST                   **/
    public static class RST extends ComposantLogique {
        private boolean active ;
                                  /*****                       Constructeur               **********/
        public RST() {
            super (3 , 2);
            super.isSeq =true ;
            try {
                super.image = new Image(getClass().getResource("RST.png").toURI().toString());
                super.imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(160);
                this.setHeight(93);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        public void run() throws InterruptedException {
            this.active = true;
            while (this.active) {
                Thread.sleep((long) 10);
                if (this.Entrees[2].getValeur() == LogicalEtat.ONE) {
                    if ((this.Entrees[0].getValeur() == LogicalEtat.ONE) && (this.Entrees[1].getValeur() == LogicalEtat.ONE) ){
                        super.Sortie [0].setValeur( LogicalEtat.ZERO) ;
                        super.Sortie[1].setValeur( LogicalEtat.ZERO) ;
                    }
                    if ((this.Entrees[0].getValeur() == LogicalEtat.ONE) && (this.Entrees[1].getValeur() == LogicalEtat.ZERO) ){
                        super.Sortie [0].setValeur( LogicalEtat.ONE) ;
                        super.Sortie[1].setValeur( LogicalEtat.ZERO) ;
                    }
                    if ((this.Entrees[0].getValeur() == LogicalEtat.ZERO) && (this.Entrees[1].getValeur() == LogicalEtat.ONE) ){
                        super.Sortie [0].setValeur( LogicalEtat.ZERO) ;
                        super.Sortie[1].setValeur( LogicalEtat.ONE) ;
                    }
                    if ((this.Entrees[0].getValeur() == LogicalEtat.NONE) && (this.Entrees[1].getValeur() == LogicalEtat.NONE) ){
                        super.Sortie [0].setValeur( LogicalEtat.NONE) ;
                        super.Sortie[1].setValeur( LogicalEtat.NONE) ;
                    }
                }else if (this.Entrees[2].getValeur() == LogicalEtat.NONE ) {
                    super.Sortie[0].setValeur(LogicalEtat.NONE);
                    super.Sortie[1].setValeur(LogicalEtat.NONE);
                }
            }
        }
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesRST() {
            for (int i = 0; i < 3; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 23);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 48);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 72);
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortieRST() {
            for (int i = 0; i < 2; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 162);
                        n.setCoorY(this.getPosYImageView() + 23);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 162);
                        n.setCoorY(this.getPosYImageView() + 64);

                        break;
                }
                n.setCircle();
                n.setEmessionSortie(i);
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }
        public void descative()
        {
            this.active = false ;
        }

        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void setNoeudEntrees() {

        }
        @Override
        public void setNoeudSortie() {

        }
    }
    /*****************************************                             **************************************/
                               /**                    BASCULE T                   **/
    public static  class T extends ComposantLogique {
        private boolean active ;
                                   /*****                       Constructeur               **********/
        public T() {
            super (2 , 2);
            this.isSeq = true ;
            try {
                super.image = new Image(getClass().getResource("T.png").toURI().toString());
                super.imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(160);
                this.setHeight(93);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortieT() {
            for (int i = 0; i < 2; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 162);
                        n.setCoorY(this.getPosYImageView() + 24);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 162);
                        n.setCoorY(this.getPosYImageView() + 64);

                        break;
                }
                n.setCircle();
                n.setEmessionSortie(i);
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }
        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        public void run() throws InterruptedException {
            this.active = true;
            Branche m = new Branche();
            while (this.active) {
                do {
                    m.setValeur(super.Entrees[1].getValeur());
                    Thread.sleep((long) 10);
                }
                while (!((m.getValeur() == LogicalEtat.ZERO) && (super.Entrees[1].getValeur() == LogicalEtat.ONE)));
                if (super.Entrees[0].getValeur() == LogicalEtat.ONE) {
                    if (super.Sortie[0].getValeur() == LogicalEtat.ZERO) {
                        super.Sortie[0].setValeur(LogicalEtat.ONE);
                        super.Sortie[1].setValeur(LogicalEtat.ZERO);
                    } else {
                        super.Sortie[0].setValeur(LogicalEtat.ZERO);
                        super.Sortie[1].setValeur(LogicalEtat.ONE);
                    }
                }
            }
        }
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesT() {
            for (int i = 0; i < 2; i++) {
                Noeud n=new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 23);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 64);
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        public void descative()
        {
            this.active = false ;
        }


        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void setNoeudEntrees() {

        }

        @Override
        public void setNoeudSortie() {

        }
    }
    /*****************************************                             **************************************/
                            /**                    BASCULE D                   **/
    public static  class D extends ComposantLogique {
        private boolean active ;
        private boolean up;
                                /*****                       Constructeur               **********/
        public D(boolean up) {
            super (2 , 2);
            this.isSeq = true ;
            this.up = up;
            try {
                super.image = new Image(getClass().getResource("DA.png").toURI().toString());
                super.imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(160);
                this.setHeight(93);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortieD() {
            for (int i = 0; i < 2; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 160);
                        n.setCoorY(this.getPosYImageView() + 24);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 160);
                        n.setCoorY(this.getPosYImageView() + 64);

                        break;
                }
                n.setCircle();
                n.setEmessionSortie(i);
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }
        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        public void run() throws InterruptedException {
            this.active = true;
            Branche m = new Branche();
            while (this.active) {
                do {
                    m.setValeur(super.Entrees[1].getValeur());
                    Thread.sleep((long) 10);
                }
                while ((!((up) && (m.getValeur() == LogicalEtat.ZERO) && (super.Entrees[1].getValeur() == LogicalEtat.ONE))) || ((!(up)) && (m.getValeur() == LogicalEtat.ONE) && (super.Entrees[1].getValeur() == LogicalEtat.ZERO)));
                if (super.Entrees[0].getValeur() == LogicalEtat.ONE) {
                    super.Sortie[0].setValeur(LogicalEtat.ONE);
                    super.Sortie[1].setValeur(LogicalEtat.ZERO);
                }
                else
                {
                    if (super.Entrees[0].getValeur() == LogicalEtat.ZERO) {
                        super.Sortie[0].setValeur(LogicalEtat.ZERO);
                        super.Sortie[1].setValeur(LogicalEtat.ONE);
                    }else {
                        super.Sortie[0].setValeur(LogicalEtat.NONE);
                        super.Sortie[1].setValeur(LogicalEtat.NONE);
                    }
                }

            }
        }
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesD() {
            for (int i = 0; i < 2; i++) {
                Noeud n=new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 23);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 64);
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        public void descative()
        {
            this.active = false ;
        }


        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void setNoeudEntrees() {

        }

        @Override
        public void setNoeudSortie() {

        }
    }
    /*****************************************                             **************************************/
                                /**                    BASCULE DLATCH                 **/
    public static  class Dlatch extends ComposantLogique {
        private boolean active ;
        private boolean up;
                                    /*****                       Constructeur               **********/
        public Dlatch(boolean up) {
            super (2 , 2);
            this.isSeq = true ;
            this.up = up;
            try {
                super.image = new Image(getClass().getResource("DA.png").toURI().toString());
                super.imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(160);
                this.setHeight(93);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortieDlatch() {
            for (int i = 0; i < 2; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 162);
                        n.setCoorY(this.getPosYImageView() + 24);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 162);
                        n.setCoorY(this.getPosYImageView() + 64);

                        break;
                }
                n.setCircle();
                n.setEmessionSortie(i);
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }
        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        public void run() throws InterruptedException {
            this.active = true;
            while (this.active) {
                do {
                    Thread.sleep((long) 10);
                }
                while ((!((up) && (super.Entrees[1].getValeur() == LogicalEtat.ONE))) && (!((!(up)) && (super.Entrees[1].getValeur() == LogicalEtat.ZERO))));
                if (super.Entrees[0].getValeur() == LogicalEtat.ONE) {
                    super.Sortie[0].setValeur(LogicalEtat.ONE);
                    super.Sortie[1].setValeur(LogicalEtat.ZERO);
                }
                else
                {
                    if (super.Entrees[0].getValeur() == LogicalEtat.ZERO) {
                        super.Sortie[0].setValeur(LogicalEtat.ZERO);
                        super.Sortie[1].setValeur(LogicalEtat.ONE);
                    }else {
                        super.Sortie[0].setValeur(LogicalEtat.NONE);
                        super.Sortie[1].setValeur(LogicalEtat.NONE);
                    }
                }

            }
        }
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesDlatch() {
            for (int i = 0; i < 2; i++) {
                Noeud n=new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 23);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 64);
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        public void descative()
        {
            this.active = false ;
        }


        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void setNoeudEntrees() {

        }

        @Override
        public void setNoeudSortie() {

        }
    }
    /*****************************************                             **************************************/
                                /**                    BASCULE JKasy                   **/
    public static  class JKasy extends ComposantLogique {
        private boolean active ;
        private boolean up;
                                    /*****                       Constructeur               **********/
        public JKasy(boolean up) {
            super (6 , 2);
            super.isSeq = true;
            this.up = up ;
            try {
                image = new Image(getClass().getResource("JKasyn.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(160);
                this.setHeight(93);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortiejkasy() {
            for (int i = 0; i < 2; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 160);
                        n.setCoorY(this.getPosYImageView() + 28);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 160);
                        n.setCoorY(this.getPosYImageView() + 57 );

                        break;
                }
                n.setCircle();
                n.setEmessionSortie(i);
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }
        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        public void run() throws InterruptedException {
            this.active = true;
            Branche m =new Branche();
            while (this.active) {
                if ((this.Entrees[4].getValeur() == LogicalEtat.ONE) &&(this.Entrees[5].getValeur() == LogicalEtat.ONE)) {
                    do {
                        if (super.Entrees[3].getValeur() == LogicalEtat.NONE) {
                            super.Sortie[0].setValeur(LogicalEtat.NONE);
                            super.Sortie[1].setValeur(LogicalEtat.NONE);
                        }
                        if (super.Entrees[3].getValeur() == LogicalEtat.ZERO) {
                            super.Sortie[0].setValeur(LogicalEtat.ZERO);
                            super.Sortie[1].setValeur(LogicalEtat.ONE);
                        }
                        if (!((this.Entrees[4].getValeur() == LogicalEtat.ONE) &&(this.Entrees[5].getValeur() == LogicalEtat.ONE)))
                        {
                            break;
                        }
                        m.setValeur(super.Entrees[2].getValeur());
                        Thread.sleep((long) 10);
                    }
                    while (!(super.Entrees [3].getValeur() == LogicalEtat.ONE) ||((!((up) && (m.getValeur() == LogicalEtat.ZERO) && (super.Entrees[2].getValeur() == LogicalEtat.ONE))) && (!((!(up)) && (m.getValeur() == LogicalEtat.ONE) && (super.Entrees[2].getValeur() == LogicalEtat.ZERO)))));
                    if ((super.Entrees[0].getValeur() == LogicalEtat.ONE) && (super.Entrees[1].getValeur() == LogicalEtat.ZERO)) {
                        super.Sortie[0].setValeur(LogicalEtat.ONE);
                        super.Sortie[1].setValeur(LogicalEtat.ZERO);
                    }
                    if ((super.Entrees[0].getValeur() == LogicalEtat.ONE) && (super.Entrees[1].getValeur() == LogicalEtat.ONE)) {
                        if (super.Sortie[0].getValeur() == LogicalEtat.ONE) {
                            super.Sortie[0].setValeur(LogicalEtat.ZERO);
                            super.Sortie[1].setValeur(LogicalEtat.ONE);
                        } else {
                            super.Sortie[0].setValeur(LogicalEtat.ONE);
                            super.Sortie[1].setValeur(LogicalEtat.ZERO);
                        }
                    }
                    if ((super.Entrees[0].getValeur() == LogicalEtat.ZERO) && (super.Entrees[1].getValeur() == LogicalEtat.ONE)) {
                        super.Sortie[0].setValeur(LogicalEtat.ZERO);
                        super.Sortie[1].setValeur(LogicalEtat.ONE);
                    }
                    if ((super.Entrees[0].getValeur() == LogicalEtat.NONE) || (super.Entrees[1].getValeur() == LogicalEtat.NONE)) {
                        super.Sortie[0].setValeur(LogicalEtat.NONE);
                        super.Sortie[1].setValeur(LogicalEtat.NONE);
                    }
                }else if ((this.Entrees[4].getValeur() == LogicalEtat.ONE)&&(this.Entrees[5].getValeur() == LogicalEtat.ZERO))
                {
                    super.Sortie[0].setValeur(LogicalEtat.ZERO);
                    super.Sortie[1].setValeur(LogicalEtat.ONE);
                    Thread.sleep((long) 10);
                }else if ((this.Entrees[4].getValeur() == LogicalEtat.ZERO)&&(this.Entrees[5].getValeur() == LogicalEtat.ONE)){
                    super.Sortie[0].setValeur(LogicalEtat.ONE);
                    super.Sortie[1].setValeur(LogicalEtat.ZERO);
                    Thread.sleep((long) 10);
                }else if ((this.Entrees[4].getValeur() == LogicalEtat.ZERO)&&(this.Entrees[5].getValeur() == LogicalEtat.ZERO))
                {
                    super.Sortie[0].setValeur(LogicalEtat.ZERO);
                    super.Sortie[1].setValeur(LogicalEtat.ZERO);
                    Thread.sleep((long) 10);
                }
            }
        }
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesJKasy() {
            for (int i = 0; i < 6; i++) {
                Noeud n=new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 20);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 51);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 37);
                        break;
                    case 3:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 67);
                        break;
                    case 4:
                        n.setCoorX(this.getPosXImageView() + 75);
                        n.setCoorY(this.getPosYImageView());
                        break;
                    case 5:
                        n.setCoorX(this.getPosXImageView() + 74);
                        n.setCoorY(this.getPosYImageView() + 93);
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        public void descative() {
            this.active = false ;
        }

        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void setNoeudEntrees() {

        }

        @Override
        public void setNoeudSortie() {

        }
    }
}