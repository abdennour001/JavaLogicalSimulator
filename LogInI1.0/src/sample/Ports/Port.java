package sample.Ports;

import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import sample.Controller;
import sample.LogicalEtat;
import sample.Noeud;

import java.awt.*;
import java.net.URISyntaxException;

/**
 * Created by asus on 10/02/2017.
 *
 * @author AMOKRANE Abdennour
 */
public class Port {

    public static class ET extends ComposantLogique{
        public ET(int n){
            super(n, 1);
            try {
                switch (n) {
                    case 2:
                        image = new Image(getClass().getResource("ET2.png").toURI().toString());
                        break;
                    case 3:
                        image = new Image(getClass().getResource("ET3.png").toURI().toString());
                        break;
                    case 4:
                        image = new Image(getClass().getResource("ET4.png").toURI().toString());
                        break;
                }
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(90);
                this.setHeight(54);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        public LogicalEtat getSortie(int numeroDeSortie) {
            this.Sortie[numeroDeSortie].setValeur(LogicalEtat.ONE);
            for (Branche e:
                    super.Entrees) {
                if(e.getValeur() == LogicalEtat.ZERO) this.Sortie[numeroDeSortie].setValeur(LogicalEtat.ZERO);
            }
            return this.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }

        public void setNoeudEntrees() {
            for (int i = 0; i < this.getNombreDEntrees(); i++) {
                Noeud n=new Noeud();
                n.setRadius(3);
                switch (super.nombreDEntrees) {
                    case 2:
                        switch (i) {
                            case 0:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 15);
                                break;
                            case 1:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 38);
                                break;
                        }
                        break;
                    case 3:
                        switch (i) {
                            case 0:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 12);
                                break;
                            case 1:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 25);
                                break;
                            case 2:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 40);
                                break;
                        }
                        break;
                    case 4:
                        switch (i) {
                            case 0:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 10);
                                break;
                            case 1:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 21);
                                break;
                            case 2:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 34);
                                break;
                            case 3:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 45);
                                break;
                        }
                        break;
                }
                n.setColor("CYAN");
                n.setCircle();
                n.getCircle().setFill(Paint.valueOf("CYAN"));
                //System.out.println(super.Entrees[i]);
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        public void setNoeudSortie() {
            Noeud n=new Noeud();
            n.setRadius(3);
            n.setColor("CYAN");
            switch(this.nombreDEntrees) {
                case 2:
                    n.setCoorX(this.getPosXImageView()+80);
                    break;
                case 3:
                    n.setCoorX(this.getPosXImageView()+80);
                    break;
                case 4:
                    n.setCoorX(this.getPosXImageView()+88);
                    break;

            }
            n.setCoorY(this.getPosYImageView()+25);
            n.setCircle();
            n.getCircle().setFill(Paint.valueOf("CYAN"));
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);
        }
    }

    public static class Tristate extends ComposantLogique{

        public Tristate() {
            super(2, 1);
            try {
                image = new Image(getClass().getResource("tristate.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(90);
                this.setHeight(53);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public void setNoeudEntrees() {
            for (int i = 0; i < 2; i++) {
                Noeud n=new Noeud();
                n.setColor("CYAN");
                n.setRadius(4);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 25);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView() + 46);
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

        public void setNoeudSortie() {
            Noeud n=new Noeud();
            n.setColor("CYAN");
            n.setRadius(4);
            n.setCoorX(this.getPosXImageView()+90);
            n.setCoorY(this.getPosYImageView()+26);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            if (this.Entrees[1].getValeur()  == LogicalEtat.ONE ){
                this.Sortie[0].setValeur(this.Entrees[0].getValeur());
            }
            else{
                this.Sortie[0].setValeur(LogicalEtat.ZERO);
            }
            return (this.Sortie[0].getValeur());
        }

        @Override
        public void run() throws InterruptedException {

        }
    }

    public static class OU extends ComposantLogique {

        /**
         * @param n
         */
        public OU(int n) {
            super(n, 1);
            try {
                switch(n) {
                    case 2:
                        image = new Image(getClass().getResource("OU.png").toURI().toString());
                        break;
                    case 3:
                        image = new Image(getClass().getResource("OU3.png").toURI().toString());
                        break;
                    case 4:
                        image = new Image(getClass().getResource("OU4.png").toURI().toString());
                        break;
                }
                imageView = new ImageView();
                imageView.setImage(image);
                //this.setWidth(90);
                //this.setHeight(54);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            this.Sortie[numeroDeSortie].setValeur(LogicalEtat.ZERO);
            for (Branche e :
                    super.Entrees) {
                if (e.getValeur() == LogicalEtat.ONE) this.Sortie[0].setValeur(LogicalEtat.ONE);
            }
            return this.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }

        public void setNoeudEntrees() {
            for (int i = 0; i < this.getNombreDEntrees(); i++) {
                Noeud n=new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                switch (this.nombreDEntrees) {
                    case 2:
                        switch (i) {
                            case 0:
                                n.setCoorX(this.getPosXImageView());
                                n.setCoorY(this.getPosYImageView() + 12);
                                break;
                            case 1:
                                n.setCoorX(this.getPosXImageView());
                                n.setCoorY(this.getPosYImageView() + 40);
                                break;
                        }
                        break;
                    case 3:
                        switch (i) {
                            case 0:
                                n.setCoorX(this.getPosXImageView());
                                n.setCoorY(this.getPosYImageView() + 12);
                                break;
                            case 1:
                                n.setCoorX(this.getPosXImageView());
                                n.setCoorY(this.getPosYImageView() + 26);
                                break;
                            case 2:
                                n.setCoorX(this.getPosXImageView());
                                n.setCoorY(this.getPosYImageView() + 41);
                                break;
                        }
                        break;
                    case 4:
                        switch (i) {
                            case 0:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 10);
                                break;
                            case 1:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 21);
                                break;
                            case 2:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 34);
                                break;
                            case 3:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 45);
                                break;
                        }
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        public void setNoeudSortie() {
            Noeud n=new Noeud();
            n.setColor("CYAN");
            n.setRadius(3);
            n.setCoorX(this.getPosXImageView()+91);
            n.setCoorY(this.getPosYImageView()+26);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);
        }
    }

    public static class nonET extends ComposantLogique{

        /**
         * @param n
         */
        public nonET(int n) {
            super(n, 1);
            try {
                switch (n) {
                    case 2:
                        image = new Image(getClass().getResource("NAND2.png").toURI().toString());
                        break;
                    case 3:
                        image = new Image(getClass().getResource("NAND3.png").toURI().toString());
                        break;
                    case 4:
                        image = new Image(getClass().getResource("NAND4.png").toURI().toString());
                        break;
                }
                imageView = new ImageView();
                imageView.setImage(image);
                //this.setWidth(90);
                //this.setHeight(54);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            this.Sortie[0].setValeur(LogicalEtat.ZERO);
            for (Branche e:
                    this.Entrees) {
                if(e.getValeur() == LogicalEtat.ZERO) this.Sortie[0].setValeur(LogicalEtat.ONE);
            }
            return this.Sortie[0].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }

        public void setNoeudEntrees() {
            for (int i = 0; i < this.getNombreDEntrees(); i++) {
                Noeud n=new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                switch (super.nombreDEntrees) {
                    case 2:
                        switch (i) {
                            case 0:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 15);
                                break;
                            case 1:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 38);
                                break;
                        }
                        break;
                    case 3:
                        switch (i) {
                            case 0:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 12);
                                break;
                            case 1:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 25);
                                break;
                            case 2:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 40);
                                break;
                        }
                        break;
                    case 4:
                        switch (i) {
                            case 0:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 10);
                                break;
                            case 1:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 21);
                                break;
                            case 2:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 34);
                                break;
                            case 3:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 45);
                                break;
                        }
                        break;
                }
                n.setCircle();
                //System.out.println(super.Entrees[i]);
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        public void setNoeudSortie() {
            Noeud n=new Noeud();
            n.setColor("CYAN");
            n.setRadius(3);
            n.setCoorX(this.getPosXImageView()+90);
            n.setCoorY(this.getPosYImageView()+27);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);
        }

    }

    public static class nonOU extends ComposantLogique {
        /**
         * @param n
         */
        public nonOU(int n) {
            super(n, 1);
            try {
                switch(n) {
                    case 2:
                        image = new Image(getClass().getResource("NOR.png").toURI().toString());
                        break;
                    case 3:
                        image = new Image(getClass().getResource("NOR3.png").toURI().toString());
                        break;
                    case 4:
                        image = new Image(getClass().getResource("NOR4.png").toURI().toString());
                        break;
                }
                imageView = new ImageView();
                imageView.setImage(image);
                //this.setWidth(90);
                //this.setHeight(54);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            this.Sortie[numeroDeSortie].setValeur(LogicalEtat.ZERO);
            for (Branche e :
                    super.Entrees) {
                if (e.getValeur() == LogicalEtat.ONE) this.Sortie[0].setValeur(LogicalEtat.ONE);
            }
            return this.Sortie[numeroDeSortie].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }

        public void setNoeudEntrees() {
            for (int i = 0; i < this.getNombreDEntrees(); i++) {
                Noeud n=new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                switch (super.nombreDEntrees) {
                    case 2:
                        switch (i) {
                            case 0:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 15);
                                break;
                            case 1:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 38);
                                break;
                        }
                        break;
                    case 3:
                        switch (i) {
                            case 0:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 12);
                                break;
                            case 1:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 25);
                                break;
                            case 2:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 40);
                                break;
                        }
                        break;
                    case 4:
                        switch (i) {
                            case 0:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 10);
                                break;
                            case 1:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 21);
                                break;
                            case 2:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 34);
                                break;
                            case 3:
                                n.setCoorX(this.getPosXImageView() -1);
                                n.setCoorY(this.getPosYImageView() + 45);
                                break;
                        }
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        public void setNoeudSortie() {
            Noeud n=new Noeud();
            n.setColor("CYAN");
            n.setRadius(3);
            n.setCoorX(this.getPosXImageView()+92);
            n.setCoorY(this.getPosYImageView()+26);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);
        }
    }

    /***************************** YOUNES AJOUTER ***********************************/
    public static class XOR extends ComposantLogique {
        /**
         * @param n
         */
        public XOR(int n) {

            super(n, 1);
            try {
                image = new Image(getClass().getResource("XOR.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                //this.setWidth(90);
                //this.setHeight(54);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            this.Sortie[numeroDeSortie].setValeur(LogicalEtat.ZERO);
            /*
            for (Branche e :
                    super.Entrees) {
                if (e.getValeur() == true) this.Sortie[0].setValeur(true);
            }*/
            Branche e = new Branche();
            if (this.Entrees[0].getValeur() != this.Entrees[1].getValeur())
                this.Sortie[0].setValeur(LogicalEtat.ONE);
            else this.Sortie[0].setValeur(LogicalEtat.ZERO);
            return this.Sortie[0].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }

        public void setNoeudEntrees() {
            for (int i = 0; i < 2; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView()-1);
                        n.setCoorY(this.getPosYImageView() + 14);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView()-1);
                        n.setCoorY(this.getPosYImageView() + 40);
                        break;
                }
                n.setColor("CYAN");
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
            n.setRadius(4);
            n.setCoorX(this.getPosXImageView() + 92);
            n.setCoorY(this.getPosYImageView() + 27);
            n.setColor("CYAN");
            n.setCircle();
            n.getCircle().setStroke(Paint.valueOf("CYAN"));
            this.Sortie[0].setNoeud(n);
            this.Sortie[0].setNumeroDeBranche(0);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);
        }
    }
    /********************************************************************************/


    public static class INV extends ComposantLogique{

        public INV() {
            super(1, 1);
            try {
                image = new Image(getClass().getResource("INV.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(55);
                this.setHeight(54);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public void setNoeudEntrees() {
            for (int i = 0; i < this.getNombreDEntrees(); i++) {
                Noeud n=new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                n.setCoorX(this.getPosXImageView());
                n.setCoorY(this.getPosYImageView() + 17);
                n.setCircle();
                //System.out.println(super.Entrees[i]);
                this.Entrees[0].setNoeud(n);
                this.Entrees[0].setNumeroDeBranche(0);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        public void setNoeudSortie() {
            Noeud n=new Noeud();
            n.setColor("CYAN");
            n.setRadius(3);
            n.setCoorX(this.getPosXImageView()+55);
            n.setCoorY(this.getPosYImageView()+17);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            return this.Entrees[0].getValeur() == LogicalEtat.ZERO ? LogicalEtat.ONE : LogicalEtat.ZERO;
            //return !this.Entrees[0].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }
    }

    public static class LED extends ComposantLogique {

        public LED() {
            super(1, 0);
            try {
                image = new Image(getClass().getResource("LED2.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                //this.setWidth(55);
                //this.setHeight(54);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public void setNoeudEntrees() {
            Noeud n=new Noeud();
            n.setColor("CYAN");
            n.setRadius(3);
            n.setCoorX(this.getPosXImageView());
            n.setCoorY(this.getPosYImageView() + 15);
            n.setCircle();
            //System.out.println(super.Entrees[i]);
            this.Entrees[0].setNoeud(n);
            this.Entrees[0].setNumeroDeBranche(0);
            this.noeudEntrees.add(n);
            Controller.noeudsList.add(n);
        }

        public void setNoeudSortie() {}

        public LogicalEtat getSortie(int numeroDeSortie) {return LogicalEtat.ZERO;}

        @Override
        public void run() throws InterruptedException {

        }
    }

    public static class Switch extends ComposantLogique {

        public Switch() {
            super(0, 1);
            try {
                image = new Image(getClass().getResource("switch.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                //this.setWidth(55);
                //this.setHeight(54);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public void setNoeudEntrees() {}

        public void setNoeudSortie() {
            Noeud n=new Noeud();
            n.setColor("CYAN");
            n.setRadius(3);
            n.setCoorX(this.getPosXImageView()+58);
            n.setCoorY(this.getPosYImageView()+17);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[0].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }
    }

    public static class FromModule extends ComposantLogique {

        private Label id=new Label("FromModule");

        public FromModule() {
            super(0, 1);
            try {
                image = new Image(getClass().getResource("FromM.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                //this.setWidth(55);
                //this.setHeight(54);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public void setNoeudEntrees() {}

        public void setNoeudSortie() {
            Noeud n=new Noeud();
            n.setColor("CYAN");
            n.setRadius(3);
            n.setCoorX(this.getPosXImageView()+89);
            n.setCoorY(this.getPosYImageView()+26);
            n.setCircle();
            this.Sortie[0].setNoeud(n);
            this.noeudSortie.add(n);
            Controller.noeudsList.add(n);
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            return super.Sortie[0].getValeur();
        }

        @Override
        public void run() throws InterruptedException {

        }

        public Label getId() {
            return id;
        }

        public void setId(Label id) {
            this.id = id;
        }
    }

    public static class ToModule extends ComposantLogique {

        private Label id=new Label("ToModule");

        public ToModule() {
            super(1, 0);
            try {
                image = new Image(getClass().getResource("ToM.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                //this.setWidth(55);
                //this.setHeight(54);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public void setNoeudEntrees() {
            Noeud n=new Noeud();
            n.setColor("CYAN");
            n.setRadius(3);
            n.setCoorX(this.getPosXImageView());
            n.setCoorY(this.getPosYImageView() + 26);
            n.setCircle();
            //System.out.println(super.Entrees[i]);
            this.Entrees[0].setNoeud(n);
            this.Entrees[0].setNumeroDeBranche(0);
            this.noeudEntrees.add(n);
            Controller.noeudsList.add(n);
        }

        public void setNoeudSortie() {}

        public LogicalEtat getSortie(int numeroDeSortie) {return LogicalEtat.ZERO;}

        @Override
        public void run() throws InterruptedException {

        }

        public Label getId() {
            return id;
        }

        public void setId(Label id) {
            this.id = id;
        }
    }

    public static class Af_Seg extends ComposantLogique {

        public Af_Seg() {
            super(4, 0);
            try {
                image = new Image(getClass().getResource("afficheur.png").toURI().toString());
                imageView = new ImageView();
                imageView.setImage(image);
                this.setWidth(55);
                this.setHeight(80);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public void setNoeudEntrees() {
            for (int i = 0; i < 4; i++) {
                Noeud n = new Noeud();
                n.setColor("CYAN");
                n.setRadius(3);
                switch (i) {
                    case 0:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 5);
                        break;
                    case 1:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 22);
                        break;
                    case 2:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 45);
                        break;
                    case 3:
                        n.setCoorX(this.getPosXImageView());
                        n.setCoorY(this.getPosYImageView() + 70);
                        break;
                }
                n.setCircle();
                this.Entrees[i].setNoeud(n);
                this.Entrees[i].setNumeroDeBranche(i);
                this.noeudEntrees.add(n);
                Controller.noeudsList.add(n);
            }
        }

        public void setNoeudSortie() {
        }

        public LogicalEtat getSortie(int numeroDeSortie) {
            return LogicalEtat.ZERO;
        }

        @Override
        public void run() throws InterruptedException {

        }
    }

}

