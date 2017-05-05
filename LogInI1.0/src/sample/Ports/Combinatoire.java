package sample.Ports;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import sample.Controller;
import sample.LogicalEtat;
import sample.Noeud;

import java.net.URISyntaxException;

/**
 * Created by younes chellaf on 21/03/2017.
 */
public class Combinatoire {



    /**************************************************************/
    public static int EtatToInt(LogicalEtat b) {
        if (b == LogicalEtat.ONE)
            return 1;
        return 0;
    }

    public static LogicalEtat intToEtat(int b) {
        if (b == 0)
            return LogicalEtat.ZERO;
        return LogicalEtat.ONE;
    }

    public static LogicalEtat[] extraireBits(String s, int tai) {
        LogicalEtat[] tab = new LogicalEtat[tai];
        int i = 0, j = s.length();
        for (i = 0; i < s.length(); i++) {
            tab[i] = intToEtat(Integer.parseInt("" + s.charAt(j - 1)));
            j--;
        }
        i = s.length();
        while (i < tai) {
            tab[i] = LogicalEtat.ZERO;
            i++;
        }
        return tab;
    }

    public static LogicalEtat nonLogicalEtat(LogicalEtat etat) {
        if (etat == LogicalEtat.ONE)
            return LogicalEtat.ZERO;
        return LogicalEtat.ONE;
    }
    /**************************************************************/


    public static class Dec3_8 extends ComposantLogique {
        public Dec3_8 () {
            super(3, 8);
            try {
                image = new Image(getClass().getResource("dec3_8.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }


        public LogicalEtat[] getSorties() {
            LogicalEtat[] tabS = new LogicalEtat[8];
            int x = 0, i = 0;
            String E = "" + EtatToInt(this.getEntree(2)) + EtatToInt(this.getEntree(1)) + EtatToInt(this.getEntree(0));
            x = Integer.parseInt(E, 2);
            tabS[x] = LogicalEtat.ONE;
            for (i=0; i<8; i++) {
                if(i != x)
                    tabS[i] = LogicalEtat.ZERO;
            }
            return tabS;
        }
        public LogicalEtat getSortie(int numeroDeSortie) {
            LogicalEtat[] tabB = this.getSorties();
            this.Sortie[numeroDeSortie].setValeur(tabB[numeroDeSortie]);
            return this.Sortie[numeroDeSortie].getValeur();


        }

        @Override
        public void run() throws InterruptedException {

        }

        public void setNoeudEntrees() {
            double j = 86;
            for (int i = 0; i < 3; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                n.setCoorX(this.getPosXImageView() - 3);
                n.setCoorY(this.getPosYImageView() + j);
                j += 24;
                n.setCircle();
                n.getCircle().setStroke(Paint.valueOf("CYAN"));
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        public void setNoeudSortie() {
            int i = 0, j = 33;
            Noeud n = new Noeud();
            for (i=0; i<8; i++) {
                n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                n.setCoorX(this.getPosXImageView() + 92);
                n.setCoorY(this.getPosYImageView() + j);
                j += 22;
                //if (i == 2) j -= 1;
                n.setCircle();
                n.getCircle().setStroke(Paint.valueOf("CYAN"));
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }

    }

    public static class Dec2_4 extends ComposantLogique {
        public Dec2_4 () {
            super(2, 4);
            try {
                image = new Image(getClass().getResource("dec2_4.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(100);
                this.setHeight(300);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        public LogicalEtat[] getSorties() {
            LogicalEtat[] tabS = new LogicalEtat[4];
            int x = 0, i = 0;
            String E = "" + EtatToInt(this.getEntree(1)) + EtatToInt(this.getEntree(0));
            x = Integer.parseInt(E, 2);
            tabS[x] = LogicalEtat.ONE;
            for (i=0; i<4; i++) {
                if(i != x)
                    tabS[i] = LogicalEtat.ZERO;
            }
            return tabS;
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            LogicalEtat[] tabB = this.getSorties();
            this.Sortie[numeroDeSortie].setValeur(tabB[numeroDeSortie]);
            return this.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }

        public void setNoeudEntrees() {
            double j = 95;
            for (int i = 0; i < 2; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                n.setCoorX(this.getPosXImageView() - 3);
                n.setCoorY(this.getPosYImageView() + j);
                j += 39;
                n.setCircle();
                n.getCircle().setStroke(Paint.valueOf("CYAN"));
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        public void setNoeudSortie() {
            int i = 0, j = 57;
            Noeud n = new Noeud();
            for (i=0; i<4; i++) {
                n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                n.setCoorX(this.getPosXImageView() + 102);
                n.setCoorY(this.getPosYImageView() + j);
                j += 39;
                //if (i == 2) j -= 1;
                n.setCircle();
                n.getCircle().setStroke(Paint.valueOf("CYAN"));
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }

    }

    public static class Mux4_1 extends ComposantLogique {
        public Mux4_1 (int n) {
            super(n, 1);
            try {
                image = new Image(getClass().getResource("mux4.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
        public LogicalEtat getSortie(int numeroDeSortie) {
            LogicalEtat[] tabS = new LogicalEtat[1];
            int i = 0, x = 0;
            // Le tableau d'entrees E0 E1 E2 E3 C0 C1
            for (i=0; i<2; i++) {
                x = x + EtatToInt(getEntree(i+4))*(int)Math.pow(2, i);
            }
            tabS[0] = getEntree(x);

            return tabS[0];
        }

        @Override
        public void run() throws InterruptedException {

        }

        public void setNoeudEntrees() {
            for (int i = 0; i < 6; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 51);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 85);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 120);
                        break;
                    case 3:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 156);
                        break;
                    case 4:
                        n.setCoorX(this.getPosXImageView()+55);
                        n.setCoorY(this.getPosYImageView() + 218);
                        break;
                    case 5:
                        n.setCoorX(this.getPosXImageView()+35);
                        n.setCoorY(this.getPosYImageView() + 218);
                        break;
                }
                n.setCircle();
                n.getCircle().setStroke(Paint.valueOf("CYAN"));
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        public void setNoeudSortie() {
            Noeud n = new Noeud();
            n.setColor("CYAN");
            n.setRadius(3);
            n.setCoorX(this.getPosXImageView() + 92);
            n.setCoorY(this.getPosYImageView() + 112);
            n.setCircle();
            n.getCircle().setStroke(Paint.valueOf("CYAN"));
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);
        }

    }

    public static class Mux8_1 extends ComposantLogique {
        public Mux8_1 (int n) { super(n, 1);
            try {
                image = new Image(getClass().getResource("mux8.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } }
        public LogicalEtat getSortie(int numeroDeSortie) {
            LogicalEtat[] tabS = new LogicalEtat[1];
            int i = 0, x = 0;
            // Le tableau d'entrees E0 E1 E2 E3 E4 E5 E6 E7 C0 C1 C2
            for (i=0; i<3; i++) {
                x = x + EtatToInt(getEntree(i+8))*(int)Math.pow(2, i);
            }
            tabS[0] = getEntree(x);

            return tabS[0];
        }

        @Override
        public void run() throws InterruptedException {

        }

        public void setNoeudEntrees() {
            for (int i = 0; i < 13; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 34);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 54);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 77);
                        break;
                    case 3:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 99);
                        break;
                    case 4:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 120);
                        break;
                    case 5:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 140);
                        break;
                    case 6:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 164);
                        break;
                    case 7:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 184);
                        break;
                    case 8:
                        n.setCoorX(this.getPosXImageView()+63);
                        n.setCoorY(this.getPosYImageView() + 218);
                        break;
                    case 9:
                        n.setCoorX(this.getPosXImageView()+50);
                        n.setCoorY(this.getPosYImageView() + 218);
                        break;
                    case 10:
                        n.setCoorX(this.getPosXImageView()+33);
                        n.setCoorY(this.getPosYImageView() + 218);
                        break;
                }
                n.setCircle();
                n.getCircle().setStroke(Paint.valueOf("CYAN"));
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        public void setNoeudSortie() {
            Noeud n = new Noeud();
            n.setColor("CYAN");
            n.setRadius(3);
            n.setCoorX(this.getPosXImageView() + 92);
            n.setCoorY(this.getPosYImageView() + 112);
            n.setCircle();
            n.getCircle().setStroke(Paint.valueOf("CYAN"));
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);
        }
    }

    public static class demux1_4 extends ComposantLogique {
        public demux1_4 () {
            super(3, 4);
            try {
                image = new Image(getClass().getResource("demux4.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
        public LogicalEtat[] getSorties() {
            LogicalEtat[] tabS = new LogicalEtat[4];
            int x = 0;
            if (this.getEntree(0)==LogicalEtat.ONE){
                String E = "" + EtatToInt(this.getEntree(1)) + EtatToInt(this.getEntree(2));
                x = Integer.parseInt(E, 2);
                tabS[x] = LogicalEtat.ONE;
                for (int i=0; i<4; i++) {
                    if(i != x)
                        tabS[i] = LogicalEtat.ZERO;
                }
            }
            return tabS;
        }
        public LogicalEtat getSortie(int numeroDeSortie) {
            LogicalEtat[] tabB = this.getSorties();
            this.Sortie[numeroDeSortie].setValeur(tabB[numeroDeSortie]);
            return this.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }

        public void setNoeudEntrees() {
            for (int i = 0; i < 3; i++) {
                Noeud
                        n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 112);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView()+54);
                        n.setCoorY(this.getPosYImageView() + 218);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView()+34);
                        n.setCoorY(this.getPosYImageView() + 218);
                        break;
                }
                n.setCircle();
                n.getCircle().setStroke(Paint.valueOf("CYAN"));
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        public void setNoeudSortie() {
            int i = 0, j = 50;
            Noeud n = new Noeud();
            for (i=0; i<4; i++) {
                n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                n.setCoorX(this.getPosXImageView() + 92);
                n.setCoorY(this.getPosYImageView() + j);
                j += 36;
                //if (i == 2) j -= 1;
                n.setCircle();
                n.getCircle().setStroke(Paint.valueOf("CYAN"));
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }
    }

    public static class demux1_8 extends ComposantLogique {
        public demux1_8 () {
            super(4, 8);
            try {
                image = new Image(getClass().getResource("demux8.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
        public LogicalEtat[] getSorties() {
            LogicalEtat[] tabS = new LogicalEtat[8];
            int x = 0;
            if (this.getEntree(0)==LogicalEtat.ONE){
                String E = "" + EtatToInt(this.getEntree(1)) + EtatToInt(this.getEntree(2))+EtatToInt(this.getEntree(3));
                x = Integer.parseInt(E, 2);
                tabS[x] = LogicalEtat.ONE;
                for (int i=0; i<8; i++) {
                    if(i != x)
                        tabS[i] = LogicalEtat.ZERO;
                }
            }
            return tabS;
        }
        public LogicalEtat getSortie(int numeroDeSortie) {
            LogicalEtat[] tabB = this.getSorties();
            this.Sortie[numeroDeSortie].setValeur(tabB[numeroDeSortie]);
            return this.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }

        public void setNoeudEntrees() {
            for (int i = 0; i < 4; i++) {
                Noeud
                        n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView()-3);
                        n.setCoorY(this.getPosYImageView() + 112);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView()+59);
                        n.setCoorY(this.getPosYImageView() + 218);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView()+43);
                        n.setCoorY(this.getPosYImageView() + 218);
                        break;
                    case 3:
                        n.setCoorX(this.getPosXImageView()+27);
                        n.setCoorY(this.getPosYImageView() + 218);
                        break;

                }
                n.setCircle();
                n.getCircle().setStroke(Paint.valueOf("CYAN"));
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }
        public void setNoeudSortie() {
            int i = 0, j = 33;
            Noeud n = new Noeud();
            for (i=0; i<8; i++) {
                n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                n.setCoorX(this.getPosXImageView() + 92);
                n.setCoorY(this.getPosYImageView() + j);
                j += 22;
                //if (i == 2) j -= 1;
                n.setCircle();
                n.getCircle().setStroke(Paint.valueOf("CYAN"));
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }
    }
}