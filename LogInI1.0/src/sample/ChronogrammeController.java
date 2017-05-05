package sample;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sample.Ports.Branche;
import sample.Ports.ComposantLogique;

import static sample.ChronogrammeController.chrono;

/**
 * Created by pc on 23/03/2017.
 */
class cloader extends Thread {
    public void run() {
        try {
            double x = 35, y = 18;
            int i = 0;
            double ae, b, espace = 8;
            ae = x;
            b = y;
            int boucle = 0;
            LogicalEtat[] prec = new LogicalEtat[100];
            Label label = new Label();

            String nom = new String("e0");
            int to=0;

            System.out.println("Here 2");
            i = 0;
            for(int kh=0;kh<chrono.length;kh++)
            {
                chrono[kh]=null;
            }
            boolean run =true;
            while((Controller.isSimulate) && (run)){
                while(x<550){
                    // Dessiner les noeuds
                    //  run=Controller.stagechr.isShowing();
                    System.out.print(run);
                    for (ComposantLogique composantLogique : Controller.composantLogiquesList) {
                        // le nom de la sortie

                        for (Branche e : composantLogique.getBrancheEntree()) {
                            Line a = new Line(x, y, x + 80, y);

                            if ((boucle != 0) && (((prec[i] == LogicalEtat.ZERO) && (e.getValeur() == LogicalEtat.ONE)) || ((prec[i] == LogicalEtat.ONE) && (e.getValeur() == LogicalEtat.ZERO)))) {
                                a = new Line(x, y, x, y - espace);
                                a.setStroke(Color.BLUE);
                                chrono[to]=a;
                                to++;

                            }
                            if (e.getValeur() == LogicalEtat.ZERO) {
                                a = new Line(x, y, x + 30, y);
                                a.setStroke(Color.RED);
                                chrono[to]=a; to=to+1;
                            }
                            if (e.getValeur() == LogicalEtat.ONE) {
                                a = new Line(x, y, x, y - espace);


                                a.setStroke(Color.GREEN);
                                if (boucle == 0) {
                                    chrono[to]=a; to=to+1;
                                }
                                a = new Line(x, y - espace, x + 30, y - espace);


                                a.setStroke(Color.GREEN);
                                chrono[to]=a; to=to+1;
                            }
                            x = ae;
                            y = y + 20;



                            prec[i] = e.getValeur();

                            i++;
                        }

                        for (Branche e : composantLogique.getBrancheSortie()) {
                            Line a = new Line(x, y, x + 30, y);
                            if ((boucle != 0) && (((prec[i] == LogicalEtat.ZERO) && (e.getValeur() == LogicalEtat.ONE)) || ((prec[i] == LogicalEtat.ONE) && (e.getValeur() == LogicalEtat.ZERO)))) {
                                a = new Line(x, y, x, y - espace);
                                a.setStroke(Color.BLUE);
                                chrono[to]=a;
                                to++;

                            }
                            if (e.getValeur() == LogicalEtat.ZERO) {
                                a = new Line(x, y, x + 30, y);
                                a.setStroke(Color.RED);
                                chrono[to]=a;
                                to=to+1;
                            }
                            if (e.getValeur() == LogicalEtat.ONE) {
                                a = new Line(x, y, x, y - espace);


                                a.setStroke(Color.GREEN);
                                if (boucle == 0) {
                                    chrono[to]=a;
                                    to=to+1;
                                }
                                a = new Line(x, y - espace, x + 30, y - espace);


                                a.setStroke(Color.GREEN);
                                chrono[to]=a;
                                to=to+1;
                            }

                            x = ae;
                            y = y + 20;
                            prec[i] = e.getValeur();

                            i++;


                        }
                    }
                    ae = ae + 30;
                    if(to==0)
                    {
                        ae=35;

                    }
                    x = ae;
                    i = 0;
                    y = b;
                    boucle++;
                    Thread.sleep(500);
                    System.out.print("to= " +to +" valeur ta3o ");
                    System.out.print("x= " +x + " y = " + y);
                }
                ae=35;
                x=ae;
                y=b;
                i=0;
                Thread.sleep(1000);
                to=0;
                for(int kh=0;kh<chrono.length;kh++)
                {
                    chrono[kh]=null;
                }
                boucle=0;


            }
        }



        catch (Exception e ) {
            System.out.println("Mamchach");
        }
    }
}

public class ChronogrammeController {


    public static Line[] chrono=new Line[20000];




    @FXML
    public  Separator separateur;

    @FXML
    public AnchorPane rot;


    public void initialize() {

        cloader t1=new cloader();
        t1.start();
        Timeline timeline=new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500),(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {



                        rot.getChildren().clear();
                        String nom=new String("ola");
                        Label label=new Label();
                        int i=0,y=5;

                        Separator separator=new Separator();
                        for (i = 65; i < 3000; i += 30) {
                            separator = new Separator();
                            separator.setOrientation(Orientation.VERTICAL);
                            separator.setLayoutX(i);
                            separator.setPrefHeight(1000);
                            separator.setLayoutY(0);
                            separator.setOpacity(0.5);
                            rot.getChildren().add(separator);
                        }
                        rot.getChildren().addAll( separateur);

                        int nb=0;
                        for(ComposantLogique composantLogique : Controller.composantLogiquesList )
                        {
                            for(Branche branche : composantLogique.getBrancheEntree())
                            {nb++;}
                            for(Branche branche : composantLogique.getBrancheSortie())
                            {nb++;}
                        }
                        int nombre=nb;
                        while(nb!=0) {
                            nom = "e" + nb;
                            label = new Label(nom);
                            label.setLayoutY(y+2);
                            label.setLayoutX(5);
                            label.setPrefWidth(20);
                            label.setPrefHeight(5);



                            separator = new Separator();
                            separator.setOrientation(Orientation.HORIZONTAL);
                            separator.setLayoutX(0);
                            separator.setPrefWidth(33);
                            separator.setPrefHeight(3);
                            separator.setLayoutY(y);
                            if(nombre==nb){ rot.getChildren().add(label);}
                            else {
                                rot.getChildren().addAll(separator, label);
                            }
                            y = y + 20;
                            nb--;
                        }
                        separator = new Separator();
                        separator.setOrientation(Orientation.HORIZONTAL);
                        separator.setLayoutX(0);
                        separator.setPrefWidth(30);
                        separator.setPrefHeight(3);
                        separator.setLayoutY(y + 4);
                        rot.getChildren().add(separator);

                        if ((Controller.isSimulate) && (chrono[0]!=null) )
                        {
                            for (Line line : chrono)
                            {if (!rot.getChildren().contains(line)) {
                                rot.getChildren().add(line);

                            }
                            }


                        }





                    }
                }
                )
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }


}