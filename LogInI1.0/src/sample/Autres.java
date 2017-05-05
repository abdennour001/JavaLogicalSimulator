package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Ports.Branche;
import sample.Ports.ComposantLogique;

import java.net.URISyntaxException;

import static java.lang.Math.pow;

/**
 * Created by BERARMA OUSSAMA on 31/03/2017.
 */
public class Autres extends Sequentielle {
    // La classe Autres contient : Registre 4bits,8bits , Compteur 4,8 bits Registres à décalage 4,8 bits .
    /*******************************************************************************************************************/
    // boolean up indique / fonctionne en front-montont si up est vraie ou front-decendent si  up est faux
    //boolean active /  indique l'etat du simulation
    /**                    REG 4 bits                    **/
    public static class Registre4bit extends ComposantLogique {
        private boolean active;
        private boolean up;
        /*****                       Constructeur               **********/
        public Registre4bit(boolean up) {
            super(6, 4);
            super.isSeq = true;
            this.up = up;
            try {
                super.image = new Image(getClass().getResource("REG4.png").toURI().toString());
                super.imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(110);
                this.setHeight(220);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortieREG4() {
            for (int i = 0; i < 4; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 110);
                        n.setCoorY(this.getPosYImageView() + 105);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 110);
                        n.setCoorY(this.getPosYImageView() + 129);

                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView() + 110);
                        n.setCoorY(this.getPosYImageView() + 152);

                        break;
                    case 3:
                        n.setCoorX(this.getPosXImageView() + 110);
                        n.setCoorY(this.getPosYImageView() + 176);

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
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesREG4() {
            for (int i = 0; i < 6; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 105);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 129);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 152);
                        break;
                    case 3:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 176);
                        break;
                    case 4:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 26);
                        break;
                    case 5:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 48);
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }

        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        @Override
        public void run() throws InterruptedException {
            this.active = true;
            Branche m = new Branche();
            while (this.active) {
                do {
                    if (super.Entrees[4].getValeur() != LogicalEtat.ONE) {
                        for (int i = 0; i < 4; i++) {
                            super.Sortie[i].setValeur(LogicalEtat.ZERO);
                        }
                    }
                    m.setValeur(super.Entrees[5].getValeur());
                    Thread.sleep((long) 10);
                }
                while (!(super.Entrees[4].getValeur() == LogicalEtat.ONE) || ((!((up) && (m.getValeur() == LogicalEtat.ZERO) && (super.Entrees[5].getValeur() == LogicalEtat.ONE))) && (!((!(up)) && (m.getValeur() == LogicalEtat.ONE) && (super.Entrees[5].getValeur() == LogicalEtat.ZERO)))));
                for (int i = 0; i < 4; i++) {
                    super.Sortie[i].setValeur(this.Entrees[i].getValeur());
                }
            }
        }

        public void desactiver() {
            this.active = false;
        }

        @Override
        public void setNoeudEntrees() {
        }

        @Override
        public void setNoeudSortie() {
        }
    }
    /**************************************              ********************************************/
    /***********              REG 8 bits                ***************************/
    public static class Registre8bit extends ComposantLogique {
        private boolean active;
        private boolean up;
        /*****                       Constructeur               **********/
        public Registre8bit(boolean up) {
            super(10, 8);
            super.isSeq = true;
            this.up = up;
            try {
                System.out.print("AAA");
                super.image = new Image(getClass().getResource("REG8.png").toURI().toString());
                super.imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(110);
                this.setHeight(220);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortieREG8() {
            for (int i = 0; i < 8; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 110);
                        n.setCoorY(this.getPosYImageView() + 93);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 110);
                        n.setCoorY(this.getPosYImageView() + 111);

                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView() + 110);
                        n.setCoorY(this.getPosYImageView() + 129);

                        break;
                    case 3:
                        n.setCoorX(this.getPosXImageView() + 110);
                        n.setCoorY(this.getPosYImageView() + 145);

                        break;
                    case 4:
                        n.setCoorX(this.getPosXImageView() + 110);
                        n.setCoorY(this.getPosYImageView() + 160);

                        break;
                    case 5:
                        n.setCoorX(this.getPosXImageView() + 110);
                        n.setCoorY(this.getPosYImageView() + 175);

                        break;
                    case 6:
                        n.setCoorX(this.getPosXImageView() + 110);
                        n.setCoorY(this.getPosYImageView() + 192);

                        break;
                    case 7:
                        n.setCoorX(this.getPosXImageView() + 110);
                        n.setCoorY(this.getPosYImageView() + 204);

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
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesREG8() {
            for (int i = 0; i < 10; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 92);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 111);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 129);
                        break;
                    case 3:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 145);
                        break;
                    case 4:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 161);
                        break;
                    case 5:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 175);
                        break;
                    case 6:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 190);
                        break;
                    case 7:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 205);
                        break;
                    case 8:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 26);
                        break;
                    case 9:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 48);
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }


        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        @Override
        public void run() throws InterruptedException {
            this.active = true;
            Branche m = new Branche();
            while (this.active) {
                do {
                    if (super.Entrees[8].getValeur() != LogicalEtat.ONE) {
                        for (int i = 0; i < 8; i++) {
                            super.Sortie[i].setValeur(LogicalEtat.ZERO);
                        }
                    }
                    m.setValeur(super.Entrees[9].getValeur());
                    Thread.sleep((long) 10);
                }
                while (!(super.Entrees[8].getValeur() == LogicalEtat.ONE) || ((!((up) && (m.getValeur() == LogicalEtat.ZERO) && (super.Entrees[9].getValeur() == LogicalEtat.ONE))) && (!((!(up)) && (m.getValeur() == LogicalEtat.ONE) && (super.Entrees[9].getValeur() == LogicalEtat.ZERO)))));
                for (int i = 0; i < 8; i++) {
                    super.Sortie[i].setValeur(this.Entrees[i].getValeur());
                }
            }
        }

        public void desactiver() {
            this.active = false;
        }

        @Override
        public void setNoeudEntrees() {
        }

        @Override
        public void setNoeudSortie() {
        }
    }
    /**************************************              ********************************************/
    /***********              COP 4 bits                ***************************/
    public static class Compteur4bit extends ComposantLogique {
        private boolean active;
        private boolean up;
        /*****                       Constructeur               **********/
        public Compteur4bit(boolean up) {
            super(2, 4);
            super.isSeq = true;
            this.up = up;
            try {
                super.image = new Image(getClass().getResource("COP4.png").toURI().toString());
                super.imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(160);
                this.setHeight(97);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortieCOP4() {
            for (int i = 0; i < 4; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 3:
                        n.setCoorX(this.getPosXImageView() + 56);
                        n.setCoorY(this.getPosYImageView() + 97);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView() + 84);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 108);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 132);
                        n.setCoorY(this.getPosYImageView() + 97);

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
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesCOP4() {
            for (int i = 0; i < 2; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 56);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 25);
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        public LogicalEtat dectobin (int k)
        {
            if ((k%2) == 1) {
                System.out.print("1");
                return LogicalEtat.ONE;
            }else{
                System.out.print("O");
                return LogicalEtat.ZERO;

            }
        }
        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }
        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        @Override
        public void run() throws InterruptedException {
            this.active = true;
            Branche m = new Branche();
            int k = 0;
            int s;
            while (this.active) {
                do {
                    if (super.Entrees[0].getValeur() == LogicalEtat.ONE) {
                        for (int i = 0; i < 4; i++) {
                            super.Sortie[i].setValeur(LogicalEtat.ZERO);
                            k=0;
                        }
                    }
                    else{
                        if (k == 0) {
                            for (int i = 0; i < 4; i++) {
                                super.Sortie[i].setValeur(LogicalEtat.ZERO);
                            }
                            super.Sortie[0].setValeur(LogicalEtat.ONE);
                            k++;
                        }
                    }
                    m.setValeur(super.Entrees[1].getValeur());
                    Thread.sleep((long) 10);
                }
                while (!(super.Entrees[0].getValeur() != LogicalEtat.ONE) || ((!((up) && (m.getValeur() == LogicalEtat.ZERO) && (super.Entrees[1].getValeur() == LogicalEtat.ONE))) && (!((!(up)) && (m.getValeur() == LogicalEtat.ONE) && (super.Entrees[1].getValeur() == LogicalEtat.ZERO)))));
                if (k == 16){
                    k=0;
                }
                for (int i=0;i<4;i++){
                    s = (int) (k / pow(2,i));
                    super.Sortie[i].setValeur(dectobin(s));
                }
                k += 1;
            }
        }

        public void desactiver() {
            this.active = false;
        }

        @Override
        public void setNoeudEntrees() {
        }

        @Override
        public void setNoeudSortie() {
        }
    }
    /**************************************              ********************************************/
    /***********              COP 8 bits                ***************************/
    public static class Compteur8bit extends ComposantLogique {
        private boolean active;
        private boolean up;
        /*****                       Constructeur               **********/
        public Compteur8bit(boolean up) {
            super(2, 8);
            super.isSeq = true;
            this.up = up;
            try {
                super.image = new Image(getClass().getResource("COP8.png").toURI().toString());
                super.imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(160);
                this.setHeight(97);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortieCOP8() {
            for (int i = 0; i < 8; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 143);
                        n.setCoorY(this.getPosYImageView() + 97);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 129);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView() + 114);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 3:
                        n.setCoorX(this.getPosXImageView() + 99);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 4:
                        n.setCoorX(this.getPosXImageView() + 85);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 5:
                        n.setCoorX(this.getPosXImageView() + 71);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 6:
                        n.setCoorX(this.getPosXImageView() + 57);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 7:
                        n.setCoorX(this.getPosXImageView() + 43);
                        n.setCoorY(this.getPosYImageView() + 97);

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
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesCOP8() {
            for (int i = 0; i < 2; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 56);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 25);
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        public LogicalEtat dectobin (int k)
        {
            if ((k%2) == 1) {
                System.out.print("1");
                return LogicalEtat.ONE;
            }else{
                System.out.print("O");
                return LogicalEtat.ZERO;

            }
        }
        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }
        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        @Override
        public void run() throws InterruptedException {
            this.active = true;
            Branche m = new Branche();
            int k = 0;
            int s;
            while (this.active) {
                do {
                    if (super.Entrees[0].getValeur() == LogicalEtat.ONE) {
                        for (int i = 0; i < 8; i++) {
                            super.Sortie[i].setValeur(LogicalEtat.ZERO);
                            k=0;
                        }
                    }
                    else{
                        if (k == 0) {
                            for (int i = 0; i < 8; i++) {
                                super.Sortie[i].setValeur(LogicalEtat.ZERO);
                            }
                            super.Sortie[0].setValeur(LogicalEtat.ONE);
                            k++;
                        }
                    }
                    m.setValeur(super.Entrees[1].getValeur());
                    Thread.sleep((long) 10);
                }
                while (!(super.Entrees[0].getValeur() != LogicalEtat.ONE) || ((!((up) && (m.getValeur() == LogicalEtat.ZERO) && (super.Entrees[1].getValeur() == LogicalEtat.ONE))) && (!((!(up)) && (m.getValeur() == LogicalEtat.ONE) && (super.Entrees[1].getValeur() == LogicalEtat.ZERO)))));
                if (k == 256){
                    k=0;
                }
                for (int i=0;i<8;i++){
                    s = (int) (k / pow(2,i));
                    super.Sortie[i].setValeur(dectobin(s));
                }
                k += 1;
            }
        }

        public void desactiver() {
            this.active = false;
        }

        @Override
        public void setNoeudEntrees() {
        }

        @Override
        public void setNoeudSortie() {
        }
    }
    /**************************************              ********************************************/
    /***********              REG à DEC 4 bits                ***************************/
    public static class Regadec4bit extends ComposantLogique {
        private boolean active;
        private boolean up;
        private boolean gd = false ; // à Gauche
        /*****                       Constructeur               **********/
        public Regadec4bit(boolean up) {
            super(3, 4);
            super.isSeq = true;
            this.up = up;
            try {
                super.image = new Image(getClass().getResource("REGD4.png").toURI().toString());
                super.imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(160);
                this.setHeight(97);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortieREGADEC4() {
            for (int i = 0; i < 4; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 3:
                        n.setCoorX(this.getPosXImageView() + 57);
                        n.setCoorY(this.getPosYImageView() + 97);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView() + 80);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 105);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 129);
                        n.setCoorY(this.getPosYImageView() + 97);

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
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesREGADEC4() {
            for (int i = 0; i < 3; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 59);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 32);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView() + 91);
                        n.setCoorY(this.getPosYImageView());
                        break;

                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }
        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        @Override
        public void run() throws InterruptedException {
            this.active = true;
            Branche m = new Branche();
            int k;
            while (this.active) {
                do {
                    if (super.Entrees[0].getValeur() == LogicalEtat.ONE) {
                        for (int i = 0; i < 4; i++) {
                            super.Sortie[i].setValeur(LogicalEtat.ZERO);
                        }
                    }
                    m.setValeur(super.Entrees[1].getValeur());
                    Thread.sleep((long) 10);
                }
                while (!(super.Entrees[0].getValeur() != LogicalEtat.ONE) || ((!((up) && (m.getValeur() == LogicalEtat.ZERO) && (super.Entrees[1].getValeur() == LogicalEtat.ONE))) && (!((!(up)) && (m.getValeur() == LogicalEtat.ONE) && (super.Entrees[1].getValeur() == LogicalEtat.ZERO)))));
                if (gd) {
                    for (int i = 3; i > 0; i--) {
                        this.Sortie[i].setValeur(this.Sortie[i - 1].getValeur());
                    }
                    this.Sortie[0].setValeur(this.Entrees[2].getValeur());
                } else{
                    for (int i = 0; i < 3; i++) {
                        this.Sortie[i].setValeur(this.Sortie[i + 1].getValeur());
                    }
                    this.Sortie[3].setValeur(this.Entrees[2].getValeur());
                }
            }
        }

        public void desactiver() {
            this.active = false;
        }

        @Override
        public void setNoeudEntrees() {
        }

        @Override
        public void setNoeudSortie() {
        }
        /**************************************              ********************************************/
        /***********              REG à DEC 8 bits                ***************************/
    }
    public static class Regadec8bit extends ComposantLogique {
        private boolean active;
        private boolean up;
        private boolean gd = false ;// à gauche
        /*****                       Constructeur               **********/
        public Regadec8bit(boolean up) {
            super(3, 8);
            super.isSeq = true;
            this.up = up;
            try {
                super.image = new Image(getClass().getResource("REGD8.png").toURI().toString());
                super.imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(160);
                this.setHeight(97);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        /***********                   Positionner les noeuds des Sorties ****************/
        public void setNoeudSortieREGADEC8() {
            for (int i = 0; i < 8; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView() + 143);
                        n.setCoorY(this.getPosYImageView() + 97);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 129);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView() + 114);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 3:
                        n.setCoorX(this.getPosXImageView() + 99);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 4:
                        n.setCoorX(this.getPosXImageView() + 85);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 5:
                        n.setCoorX(this.getPosXImageView() + 71);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 6:
                        n.setCoorX(this.getPosXImageView() + 57);
                        n.setCoorY(this.getPosYImageView() + 97);

                        break;
                    case 7:
                        n.setCoorX(this.getPosXImageView() + 43);
                        n.setCoorY(this.getPosYImageView() + 97);

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
        /***********                   Positionner les noeuds d'entres ****************/
        public void setNoeudEntreesREGADEC8() {
            for (int i = 0; i < 3; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 59);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 32);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView() + 91);
                        n.setCoorY(this.getPosYImageView());
                        break;

                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        @Override
        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[numeroDeSortie].getValeur();
        }
        /*****************                 La procedure qui donnent Les etats loguiques ds sorties **********/
        @Override
        public void run() throws InterruptedException {
            this.active = true;
            Branche m = new Branche();
            while (this.active) {
                do {
                    if (super.Entrees[0].getValeur() == LogicalEtat.ONE) {
                        for (int i = 0; i < 8; i++) {
                            super.Sortie[i].setValeur(LogicalEtat.ZERO);
                        }
                    }
                    m.setValeur(super.Entrees[1].getValeur());
                    Thread.sleep((long) 10);
                }
                while (!(super.Entrees[0].getValeur() != LogicalEtat.ONE) || ((!((up) && (m.getValeur() == LogicalEtat.ZERO) && (super.Entrees[1].getValeur() == LogicalEtat.ONE))) && (!((!(up)) && (m.getValeur() == LogicalEtat.ONE) && (super.Entrees[1].getValeur() == LogicalEtat.ZERO)))));
                if (gd) {
                    for (int i = 7; i > 0; i--) {
                        this.Sortie[i].setValeur(this.Sortie[i - 1].getValeur());
                    }
                    this.Sortie[0].setValeur(this.Entrees[2].getValeur());
                } else{
                    for (int i = 0; i < 7; i++) {
                        this.Sortie[i].setValeur(this.Sortie[i + 1].getValeur());
                    }
                    this.Sortie[7].setValeur(this.Entrees[2].getValeur());
                }
            }
        }

        public void desactiver() {
            this.active = false;
        }

        @Override
        public void setNoeudEntrees() {
        }

        @Override
        public void setNoeudSortie() {
        }
    }
}