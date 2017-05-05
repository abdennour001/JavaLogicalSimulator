package sample.Ports;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Controller;
import sample.Noeud;
import sample.Controller;
import sample.LogicalEtat;
import sample.Ports.Port.*;
import java.awt.*;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.net.URISyntaxException;

import static sample.Ports.Combinatoire.EtatToInt;
import static sample.Ports.Combinatoire.extraireBits;
import static sample.Ports.Combinatoire.nonLogicalEtat;

/*************************************** KARIM ***********************************************/

/**
 * Created by Karim on 21/03/2017.
 */
public class Combinatoires {

    public static class demiAdditionneur2 extends ComposantLogique {

        public demiAdditionneur2 () {
            super(2, 2);
            try {
                image = new Image(getClass().getResource("demiADD.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(90);
                this.setHeight(173);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        public LogicalEtat[] getSorties() {
            LogicalEtat[] tabSorties = new LogicalEtat[2];
            XOR x = new XOR(2);
            ET et2 = new ET(2);

            x.setEntree(0, this.getEntree(0));
            x.setEntree(1, this.getEntree(1));

            et2.setEntree(0, this.getEntree(0));
            et2.setEntree(1, this.getEntree(1));

            tabSorties[0] = x.getSortie(0);  // le S
            tabSorties[1] = et2.getSortie(0); // le R

            return tabSorties;
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
            int j = 40;
            for (int i = 0; i < this.getNombreDEntrees(); i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                if(i == 2) {
                    j += 41;
                }
                if(i == 3) {
                    j += 2;
                }
                n.setCoorX(this.getPosXImageView() - 3);
                n.setCoorY(this.getPosYImageView() + j);
                j += 96;
                n.setCircle();
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
            n.setCoorX(this.getPosXImageView() + 90);
            n.setCoorY(this.getPosYImageView() + 73);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);

            Noeud n1 = new Noeud();
            n1.setColor("CYAN");
            n1.setRadius(3);
            n1.setCoorX(this.getPosXImageView() + 90);
            n1.setCoorY(this.getPosYImageView() + 107);
            n1.setCircle();
            this.Sortie[1].setNoeud(n1);
            this.Sortie[1].setNumeroDeBranche(1);
            this.noeudSortie.add(n1);
            Controller.noeudsList.add(n1);
        }
    }

    public static class completAdditionneur3 extends ComposantLogique {
        // additionneur complet a 1 bit (1 bit + 1 bit + la retenue precedente)

        public completAdditionneur3 () {
            super(3, 2);
            try {
                image = new Image(getClass().getResource("ADDC1.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(90);
                this.setHeight(173);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public LogicalEtat[] getSorties() {
            LogicalEtat[] tabSorties = new LogicalEtat[2];
            LogicalEtat a, b, r;
            XOR xor = new XOR(2);
            XOR xor2 = new XOR(2);
            ET et1 = new ET(2);
            ET et2 = new ET(2);
            OU ou = new OU(2);

            a = this.getEntree(0);
            b = this.getEntree(1);
            r = this.getEntree(2);

            // RETENUE

            // a xor b
            xor.setEntree(0, a);
            xor.setEntree(1, b);

            // (a xor b) et r
            et1.setEntree(0, xor.getSortie(0));
            et1.setEntree(1, r);

            // a et b
            et2.setEntree(0, a);
            et2.setEntree(1, b);

            // ((a xor b) and r) ou (a et b) qui est la retenue
            ou.setEntree(0, et1.getSortie(0));
            ou.setEntree(1, et2.getSortie(0));

            // la retenue est dans la case 1
            tabSorties[1] = (ou.getSortie(0)); // le R

            // SORTIE

            // a xor b
            xor.setEntree(0, a);
            xor.setEntree(1, b);

            // (a xor b) xor r qui est S
            xor2.setEntree(0, xor.getSortie(0));
            xor2.setEntree(1, r);

            // la sortie dans la case 0
            tabSorties[0] = xor2.getSortie(0);  // le S

            return tabSorties;
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
            int j = 40;
            Noeud n = new Noeud();
            for (int i = 0; i < this.getNombreDEntrees() - 1; i++) {
                n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                if(i == 2) {
                    j += 41;
                }
                if(i == 3) {
                    j += 2;
                }
                n.setCoorX(this.getPosXImageView() - 3);
                n.setCoorY(this.getPosYImageView() + j);
                j += 96;
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
            n = new Noeud();
            n.setColor("CYAN");
            n.setRadius(3);
            n.setCoorX(this.getPosXImageView() + 49);
            n.setCoorY(this.getPosYImageView() + 173);
            n.setCircle();
            this.Entrees[2].setNoeud(n);
            this.Entrees[2].setNumeroDeBranche(2);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);
        }

        public void setNoeudSortie() {
            Noeud n = new Noeud();
            n.setColor("CYAN");
            n.setRadius(3);
            n.setCoorX(this.getPosXImageView() + 90);
            n.setCoorY(this.getPosYImageView() + 74);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);

            Noeud n1 = new Noeud();
            n1.setColor("CYAN");
            n1.setRadius(3);
            n1.setCoorX(this.getPosXImageView() + 90);
            n1.setCoorY(this.getPosYImageView() + 107);
            n1.setCircle();
            this.Sortie[1].setNoeud(n1);
            this.Sortie[1].setNumeroDeBranche(1);
            this.noeudSortie.add(n1);
            Controller.noeudsList.add(n1);
        }
    }

    public static class Additionneur2bits extends ComposantLogique {

        public Additionneur2bits () {
            super(4, 3);
            try {
                image = new Image(getClass().getResource("ADDC2.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(90);
                this.setHeight(173);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public LogicalEtat[] getSorties() {
            LogicalEtat[] tabSorties;
            int a0, a1, b0, b1, x, y, i = 0;
            // le tableau d'entrees : a0 a1 b0 b1
            a0 = EtatToInt(getEntree(0));
            a1 = EtatToInt(getEntree(1));
            b0 = EtatToInt(getEntree(2));
            b1 = EtatToInt(getEntree(3));
            // on les convertit en entier decimal
            String A = "" + a1 + a0;
            String B = "" + b1 + b0;
            x = Integer.parseInt(A, 2);   // on convertit A en decimal
            y = Integer.parseInt(B, 2);
            x = x + y;   // x est le resultat de l'addition mais en decimal
            // on le convertit donc en binaire
            A = Integer.toBinaryString(x);
            // on extraie chaque bit de A et on le met dans le tableau de Sorties
            tabSorties = extraireBits(A, 3);   // S0 S1 R

            return tabSorties;
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
            int j = 29;
            for (int i = 0; i < this.getNombreDEntrees(); i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                if(i == 2) {
                    j += 37;
                }
                if(i == 3) {
                    j += 3;
                }
                n.setCoorX(this.getPosXImageView() - 3);
                n.setCoorY(this.getPosYImageView() + j);
                j += 25;
                n.setCircle();
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
            n.setCoorX(this.getPosXImageView() + 90);
            n.setCoorY(this.getPosYImageView() + 66);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);

            Noeud n1 = new Noeud();
            n1.setColor("CYAN");
            n1.setRadius(3);
            n1.setCoorX(this.getPosXImageView() + 90);
            n1.setCoorY(this.getPosYImageView() + 89);
            n1.setCircle();
            this.Sortie[1].setNoeud(n1);
            this.Sortie[1].setNumeroDeBranche(1);
            this.noeudSortie.add(n1);
            Controller.noeudsList.add(n1);

            Noeud n2 = new Noeud();
            n2.setColor("CYAN");
            n2.setRadius(3);
            n2.setCoorX(this.getPosXImageView() + 90);
            n2.setCoorY(this.getPosYImageView() + 110);
            n2.setCircle();
            this.Sortie[2].setNoeud(n2);
            this.Sortie[2].setNumeroDeBranche(2);
            this.noeudSortie.add(n2);
            Controller.noeudsList.add(n2);
        }
    }

    public static class Additionneur4bits extends ComposantLogique {

        public Additionneur4bits () {
            super(8, 5);
            try {
                image = new Image(getClass().getResource("ADDC4.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(90);
                this.setHeight(173);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public LogicalEtat[] getSorties() {
            LogicalEtat[] tabSorties;
            double x = 0, y = 0;
            int i = 0;
            // le tableau d'entrees : a0 a1 a2 a3 b0 b1 b2 b3
            // on les convertit en entier decimal
            for (i=0; i<4; i++) {
                x = x + EtatToInt(getEntree(i))*Math.pow(2, i);
                y = y + EtatToInt(getEntree(i+4))*Math.pow(2, i);
            }

            x = x + y;   // x est le resultat de l'addition mais en decimal
            // on le convertit donc en binaire
            String A = Integer.toBinaryString((int)x);

            // on extraie chaque bit de A et on le met dans le tableau de Sorties
            tabSorties = extraireBits(A, 8);   // S0 S1 S2 S3 R
            this.setNombreDSortie(A.length());
            //setNombreSorties(A.length());
            return tabSorties;
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
            double j = 24;
            for (int i = 0; i < this.getNombreDEntrees(); i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                if (i == 2) j -= 2;
                if (i == 3) j -= 2;
                if(i == 4) {
                    j += 32;
                }
                if (i == 5) j -= 1;
                if (i == 6) j -= 1;
                if (i == 7) j -= 1;
                n.setCoorX(this.getPosXImageView() - 3);
                n.setCoorY(this.getPosYImageView() + j);
                j += 14;
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        public void setNoeudSortie() {
            int i = 0, j = 60;
            Noeud n = new Noeud();
            for (i=0; i<5; i++) {
                n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                n.setCoorX(this.getPosXImageView() + 93);
                n.setCoorY(this.getPosYImageView() + j);
                j += 12;
                if (i == 3) j += 1;
                n.setCircle();
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }
    }

    public static class demiSoustracteur extends ComposantLogique {

        public demiSoustracteur() {
            super(2, 2);
            try {
                image = new Image(getClass().getResource("demiADD.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(90);
                this.setHeight(173);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public LogicalEtat[] getSorties() {
            // Soustracteur 1 bit a 1 bit,   a  b
            // a est Entree[0]
            // b est Entree[1]
            LogicalEtat[] tabSorties = new LogicalEtat[2];
            OU ou2 = new OU(2);
            ET et2 = new ET(2);
            INV inv = new INV();

            LogicalEtat a, b;

            a = this.getEntree(0);
            b = this.getEntree(1);


            // Calcul de la sortie S
            et2.setEntree(0, nonLogicalEtat(a));
            et2.setEntree(1, b);
            ou2.setEntree(0, et2.getSortie(0));

            et2.setEntree(0, nonLogicalEtat(b));
            et2.setEntree(1, a);
            ou2.setEntree(1, et2.getSortie(0));

            // Calcul de la retenue R
            et2.setEntree(0, nonLogicalEtat(a));
            et2.setEntree(1, b);

            tabSorties[0] = ou2.getSortie(0);  // le S

            tabSorties[1] = et2.getSortie(0); // le R

            return tabSorties;
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
            int j = 40;
            for (int i = 0; i < this.getNombreDEntrees(); i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                if(i == 2) {
                    j += 41;
                }
                if(i == 3) {
                    j += 2;
                }
                n.setCoorX(this.getPosXImageView() - 3);
                n.setCoorY(this.getPosYImageView() + j);
                j += 96;
                n.setCircle();
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
            n.setCoorX(this.getPosXImageView() + 90);
            n.setCoorY(this.getPosYImageView() + 73);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);

            Noeud n1 = new Noeud();
            n1.setColor("CYAN");
            n1.setRadius(3);
            n1.setCoorX(this.getPosXImageView() + 90);
            n1.setCoorY(this.getPosYImageView() + 107);
            n1.setCircle();
            this.Sortie[1].setNoeud(n1);
            this.Sortie[1].setNumeroDeBranche(1);
            this.noeudSortie.add(n1);
            Controller.noeudsList.add(n1);
        }
    }

    public static class CompNbits extends ComposantLogique {
        public CompNbits() {
            super(8, 3);
            try {
                image = new Image(getClass().getResource("Cmp4.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(90);
                this.setHeight(173);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            LogicalEtat[] tabB = this.getSorties();
            this.Sortie[numeroDeSortie].setValeur(tabB[numeroDeSortie]);
            return this.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }

        public LogicalEtat[] getSorties() {
            LogicalEtat[] tabSorties = new LogicalEtat[3]; // E | S | I
            double x = 0, y = 0;
            int i = 0, j = 0;
            // le tableau d'entrees : a0 a1 a2 a3 b0 b1 b2 b3
            // on les convertit en entier decimal
            j = this.getNombreDEntrees()/2;
            for (i=0; i<j; i++) {
                x = x + EtatToInt(getEntree(i))*Math.pow(2, i);
                y = y + EtatToInt(getEntree(i+j))*Math.pow(2, i);
            }

            if (x == y) {
                tabSorties[0] = LogicalEtat.ONE;
                tabSorties[1] = LogicalEtat.ZERO;
                tabSorties[2] = LogicalEtat.ZERO;
            }
            if (x > y) {
                tabSorties[1] = LogicalEtat.ONE;
                tabSorties[0] = LogicalEtat.ZERO;
                tabSorties[2] = LogicalEtat.ZERO;
            }
            if (x < y) {
                tabSorties[2] = LogicalEtat.ONE;
                tabSorties[0] = LogicalEtat.ZERO;
                tabSorties[1] = LogicalEtat.ZERO;
            }


            return tabSorties;
        }

        public void setNoeudEntrees() {
            double j = 16;
            for (int i = 0; i < this.getNombreDEntrees(); i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                if(i == 4) {
                    j += 23;
                }
                n.setCoorX(this.getPosXImageView() - 3);
                n.setCoorY(this.getPosYImageView() + j);
                j += 17;
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        public void setNoeudSortie() {
            int i = 0, j = 68;
            Noeud n = new Noeud();
            for (i=0; i<3; i++) {
                n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                n.setCoorX(this.getPosXImageView() + 93);
                n.setCoorY(this.getPosYImageView() + j);
                j += 25;
                if (i == 2) j +=7 ;
                n.setCircle();
                this.Sortie[i].setNoeud(n);
                this.Sortie[i].setNumeroDeBranche(i);
                this.noeudSortie.add(n);
                Controller.noeudsList.add(n);
            }
        }
    }

    public static class Encod4_2 extends ComposantLogique {

        public Encod4_2 () {
            super(4, 2);
            try {
                image = new Image(getClass().getResource("Encod4_2.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(90);
                this.setHeight(216);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            LogicalEtat[] tabB = this.getSorties();
            this.Sortie[numeroDeSortie].setValeur(tabB[numeroDeSortie]);
            return this.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }

        public LogicalEtat[] getSorties() {
            LogicalEtat[] tabS = new LogicalEtat[2];
            tabS[0] = LogicalEtat.ZERO;
            tabS[1] = LogicalEtat.ZERO;
            if(uniqueONE(1))
                tabS[0] = LogicalEtat.ONE;
            if(uniqueONE(2))
                tabS[1] = LogicalEtat.ONE;
            if(uniqueONE(3)) {
                tabS[0] = LogicalEtat.ONE;
                tabS[1] = LogicalEtat.ONE;
            }
            return tabS;
        }

        public boolean uniqueONE (int numEntree) {
            int i = 0;
            if(this.getEntree(numEntree) == LogicalEtat.ONE){
                while (i < this.getNombreDEntrees()){
                    if(i != numEntree && this.getEntree(i) == LogicalEtat.ONE)
                        return false;
                    i++;
                }
                return true;
            }
            return false;
        }

        public void setNoeudEntrees() {
            int j = 48;
            for (int i = 0; i < this.getNombreDEntrees(); i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                n.setCoorX(this.getPosXImageView() - 3);
                n.setCoorY(this.getPosYImageView() + j);
                j += 36;
                if (i == 1) j+= 1;
                if (i == 2) j+= 1;
                if (i == 3) j+= 1;
                n.setCircle();
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
            n.setCoorX(this.getPosXImageView() + 90);
            n.setCoorY(this.getPosYImageView() + 84);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);

            Noeud n1 = new Noeud();
            n1.setColor("CYAN");
            n1.setRadius(3);
            n1.setCoorX(this.getPosXImageView() + 90);
            n1.setCoorY(this.getPosYImageView() + 122);
            n1.setCircle();
            this.Sortie[1].setNoeud(n1);
            this.Sortie[1].setNumeroDeBranche(1);
            this.noeudSortie.add(n1);
            Controller.noeudsList.add(n1);
        }
    }

    public static class Encod8_3 extends ComposantLogique {

        public Encod8_3 () {
            super(8, 3);
            try {
                image = new Image(getClass().getResource("Encod8_3.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(90);
                this.setHeight(216);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            LogicalEtat[] tabB = this.getSorties();
            this.Sortie[numeroDeSortie].setValeur(tabB[numeroDeSortie]);
            return this.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }

        public LogicalEtat[] getSorties() {
            LogicalEtat[] tabS = new LogicalEtat[3];
            tabS[0] = LogicalEtat.ZERO;
            tabS[1] = LogicalEtat.ZERO;
            tabS[2] = LogicalEtat.ZERO;
            if(uniqueONE(1))
                tabS[0] = LogicalEtat.ONE;
            if(uniqueONE(2))
                tabS[1] = LogicalEtat.ONE;
            if(uniqueONE(3)) {
                tabS[0] = LogicalEtat.ONE;
                tabS[1] = LogicalEtat.ONE;
            }
            if(uniqueONE(4)) {
                tabS[2] = LogicalEtat.ONE;
            }
            if(uniqueONE(5)) {
                tabS[0] = LogicalEtat.ONE;
                tabS[2] = LogicalEtat.ONE;
            }
            if(uniqueONE(6)) {
                tabS[2] = LogicalEtat.ONE;
                tabS[1] = LogicalEtat.ONE;
            }
            if(uniqueONE(7)) {
                tabS[0] = LogicalEtat.ONE;
                tabS[1] = LogicalEtat.ONE;
                tabS[2] = LogicalEtat.ONE;
            }
            return tabS;
        }

        public boolean uniqueONE (int numEntree) {
            int i = 0;
            if(this.getEntree(numEntree) == LogicalEtat.ONE){
                while (i < this.getNombreDEntrees()){
                    if(i != numEntree && this.getEntree(i) == LogicalEtat.ONE)
                        return false;
                    i++;
                }
                return true;
            }
            return false;
        }

        public void setNoeudEntrees() {
            int j = 32;
            for (int i = 0; i < this.getNombreDEntrees(); i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                n.setCoorX(this.getPosXImageView() - 3);
                n.setCoorY(this.getPosYImageView() + j);
                j += 23;
                if (i == 1) j-=1;
                if (i == 3) j -= 2;
                if (i == 5) j -= 2;
                if (i == 6) j -= 2;
                n.setCircle();
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
            n.setCoorX(this.getPosXImageView() + 90);
            n.setCoorY(this.getPosYImageView() + 86);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);

            Noeud n1 = new Noeud();
            n1.setColor("CYAN");
            n1.setRadius(3);
            n1.setCoorX(this.getPosXImageView() + 90);
            n1.setCoorY(this.getPosYImageView() + 112);
            n1.setCircle();
            this.Sortie[1].setNoeud(n1);
            this.Sortie[1].setNumeroDeBranche(1);
            this.noeudSortie.add(n1);
            Controller.noeudsList.add(n1);
            Noeud n2 = new Noeud();
            n2.setColor("CYAN");
            n2.setRadius(3);
            n2.setCoorX(this.getPosXImageView() + 90);
            n2.setCoorY(this.getPosYImageView() + 134);
            n2.setCircle();
            this.Sortie[2].setNoeud(n2);
            this.Sortie[2].setNumeroDeBranche(2);
            this.noeudSortie.add(n2);
            Controller.noeudsList.add(n2);
        }
    }
}
