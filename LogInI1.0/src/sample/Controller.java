package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.Callback;
import javafx.util.Duration;
import org.omg.CORBA.Any;
import sample.Ports.*;
import sun.reflect.generics.tree.Tree;

import javax.swing.*;
import javax.swing.text.*;
import javax.tools.Tool;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static javafx.scene.paint.Color.*;
import static sample.Main.*;


final class TextFieldTreeCellImpl extends TreeCell<String> {

    private TextField textField;

    public TextFieldTreeCellImpl() {
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText((String) getItem());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(String item, boolean empty) {

        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());
                    Controller.tabPane2.getSelectionModel().getSelectedItem().setText(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}

class  loader2 extends Thread {
    private ComposantLogique c;
    public void run() {
        try {
            this.c.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void start1 (ComposantLogique c)
    {
        this.c = c;
    }
}
class loader extends Thread {

    public static long vitesse=100;

    public void run() {
        try {
            int i=1;
            while (Controller.isSimulate) {

                for (Noeud n :
                        Controller.noeudsList) {
                    n.Simulation();
                }
                for (ComposantLogique c:
                     Controller.composantLogiquesList) {
                    if(c.getClass() == Port.FromModule.class) {
                        System.out.println("1");
                        for (ArrayList<ComposantLogique> compList:
                                Controller.composantLogiqueList2) {
                            for (ComposantLogique comp:
                                    compList) {
                                if (comp.getClass() == Port.ToModule.class) {
                                    System.out.println("2");
                                    if(((Port.ToModule) comp).getId().getText().equals(((Port.FromModule)c).getId().getText()))
                                    {
                                        System.out.println("3");
                                        c.getBrancheSortie()[0].setValeur(comp.getEntree(0));
                                    }
                                }
                            }
                        }
                    }
                }
                for (ComposantLogique c :
                        Controller.composantLogiquesList) {
                    for (Branche e :
                            c.getBrancheEntree()) {
                        if (e.getValeur() == LogicalEtat.ONE) {
                            e.getNoeud().getCircle().setFill(Paint.valueOf("RED"));
                        } else {
                            e.getNoeud().getCircle().setFill(Paint.valueOf("GRAY"));
                        }
                    }
                    for (Branche s :
                            c.getBrancheSortie()) {
                        if (s.getValeur() == LogicalEtat.ONE) {
                            s.getNoeud().getCircle().setFill(Paint.valueOf("RED"));
                        } else {
                            s.getNoeud().getCircle().setFill(Paint.valueOf("GRAY"));
                        }
                    }
                    if ( c.getisSeq() == true) {
                        if (i==1) {
                            loader2 obj = new loader2();
                            obj.start1(c);
                            obj.start();
                        }
                    }
                    else {
                        if (c.getClass() == Port.LED.class) {
                            if (c.getEntree(0) == LogicalEtat.ONE) {
                                int depth = 50;
                                DropShadow borderGlow = new DropShadow();
                                borderGlow.setSpread(0.7);
                                borderGlow.setOffsetY(0f);
                                borderGlow.setOffsetX(0f);
                                borderGlow.setColor(Color.RED);
                                borderGlow.setWidth(depth);
                                borderGlow.setHeight(depth);

                                c.getImageView().setEffect(borderGlow);
                            } else {
                                c.getImageView().setEffect(null);
                            }
                        } else if (c.getClass() == Port.Switch.class) {
                            if (c.getSortie(0) == LogicalEtat.ONE) {
                                int depth = 50;
                                DropShadow borderGlow = new DropShadow();
                                borderGlow.setSpread(0.7);

                                borderGlow.setOffsetY(0f);
                                borderGlow.setOffsetX(0f);
                                borderGlow.setColor(Color.RED);
                                borderGlow.setWidth(depth);
                                borderGlow.setHeight(depth);

                                c.getImageView().setEffect(borderGlow);
                            } else {
                                c.getImageView().setEffect(null);
                            }


                        } else if (c.getClass() == Port.Af_Seg.class) {
                            if ((c.getEntree(0) == LogicalEtat.ZERO) && (c.getEntree(1) == LogicalEtat.ZERO) && (c.getEntree(2) == LogicalEtat.ZERO) && c.getEntree(3) == LogicalEtat.ZERO) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("0.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);
                            } else if ((c.getEntree(0) == LogicalEtat.ONE) && (c.getEntree(1) == LogicalEtat.ZERO) && (c.getEntree(2) == LogicalEtat.ZERO) && c.getEntree(3) == LogicalEtat.ZERO) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("1.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ZERO) && (c.getEntree(1) == LogicalEtat.ONE) && (c.getEntree(2) == LogicalEtat.ZERO) && c.getEntree(3) == LogicalEtat.ZERO) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("2.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ONE) && (c.getEntree(1) == LogicalEtat.ONE) && (c.getEntree(2) == LogicalEtat.ZERO) && c.getEntree(3) == LogicalEtat.ZERO) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("3.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ZERO) && (c.getEntree(1) == LogicalEtat.ZERO) && (c.getEntree(2) == LogicalEtat.ONE) && c.getEntree(3) == LogicalEtat.ZERO) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("4.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ONE) && (c.getEntree(1) == LogicalEtat.ZERO) && (c.getEntree(2) == LogicalEtat.ONE) && c.getEntree(3) == LogicalEtat.ZERO) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("5.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ZERO) && (c.getEntree(1) == LogicalEtat.ONE) && (c.getEntree(2) == LogicalEtat.ONE) && c.getEntree(3) == LogicalEtat.ZERO) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("6.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ONE) && (c.getEntree(1) == LogicalEtat.ONE) && (c.getEntree(2) == LogicalEtat.ONE) && c.getEntree(3) == LogicalEtat.ZERO) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("7.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ZERO) && (c.getEntree(1) == LogicalEtat.ZERO) && (c.getEntree(2) == LogicalEtat.ZERO) && c.getEntree(3) == LogicalEtat.ONE) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("8.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ONE) && (c.getEntree(1) == LogicalEtat.ZERO) && (c.getEntree(2) == LogicalEtat.ZERO) && c.getEntree(3) == LogicalEtat.ONE) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("9.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ZERO) && (c.getEntree(1) == LogicalEtat.ONE) && (c.getEntree(2) == LogicalEtat.ZERO) && c.getEntree(3) == LogicalEtat.ONE) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("a.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ONE) && (c.getEntree(1) == LogicalEtat.ONE) && (c.getEntree(2) == LogicalEtat.ZERO) && c.getEntree(3) == LogicalEtat.ONE) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("b.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ZERO) && (c.getEntree(1) == LogicalEtat.ZERO) && (c.getEntree(2) == LogicalEtat.ONE) && c.getEntree(3) == LogicalEtat.ONE) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("c.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ONE) && (c.getEntree(1) == LogicalEtat.ZERO) && (c.getEntree(2) == LogicalEtat.ONE) && c.getEntree(3) == LogicalEtat.ONE) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("d.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ZERO) && (c.getEntree(1) == LogicalEtat.ONE) && (c.getEntree(2) == LogicalEtat.ONE) && c.getEntree(3) == LogicalEtat.ONE) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("e.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            } else if ((c.getEntree(0) == LogicalEtat.ONE) && (c.getEntree(1) == LogicalEtat.ONE) && (c.getEntree(2) == LogicalEtat.ONE) && c.getEntree(3) == LogicalEtat.ONE) {
                                Image image = null;
                                try {
                                    image = new Image(getClass().getResource("f.png").toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                c.getImageView().setImage(image);

                            }


                        }
                    }


                }

                for (Fil f :
                        Controller.filList) {
                    if (f.getValeur() == LogicalEtat.ONE) {
                        f.getFil().setStroke(Paint.valueOf("RED"));
                    } else {
                        f.getFil().setStroke(Paint.valueOf("GRAY"));
                    }
                }
                i=0;
                Thread.sleep(vitesse);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(!Controller.isSimulate)
        {
            for(ComposantLogique e : Controller.composantLogiquesList)
            {if(e.getClass()==Port.Af_Seg.class){
                try {
                    e.getImageView().setImage(new Image(getClass().getResource("afficheur.png").toURI().toString()));
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
            }
        }
    }
}

public class Controller {


    /************OUSSAMA****************/
    @FXML
    private ImageView et2;
    @FXML
    private ImageView et3;
    @FXML
    private ImageView ou;
    @FXML
    private ImageView ou3;
    @FXML
    private ImageView et4;
    @FXML
    private ImageView ou4;
    @FXML
    private ImageView nand2;
    @FXML
    private ImageView nand3;
    @FXML
    private ImageView nand4;
    @FXML
    private ImageView nor;
    @FXML
    private ImageView nor4;
    @FXML
    private ImageView nor3;
    @FXML
    private ImageView xor;
    @FXML
    private ImageView inv;
    @FXML
    private ImageView tristate;
    @FXML
    private ImageView Switch;
    @FXML
    private ImageView led;
    @FXML
    private ImageView dec2_4;
    @FXML
    private ImageView dec3_8;
    @FXML
    private ImageView demux4;
    @FXML
    private ImageView demux8;
    @FXML
    private ImageView mux4;
    @FXML
    private ImageView mux8;
    @FXML
    private ImageView aff2;
    @FXML
    private ImageView ADDC1;
    @FXML
    private ImageView ADDC2;
    @FXML
    private ImageView ADDC4;
    @FXML
    private ImageView demiADD;
    @FXML
    private ImageView Cmp4;
    @FXML
    private ImageView Encod4_2;
    @FXML
    private ImageView Encod8_3;
    @FXML
    private ImageView demiSOUS;
    @FXML
    private ImageView d;
    @FXML
    private ImageView H;
    @FXML
    private ImageView JK;
    @FXML
    private ImageView JKasyn;
    @FXML
    private ImageView REG4;
    @FXML
    private ImageView REG8;
    @FXML
    private ImageView RS;
    @FXML
    private ImageView RST;
    @FXML
    private ImageView T;
    @FXML
    private ImageView dlatch;
    @FXML
    private ImageView cop4;
    @FXML
    private ImageView cop8;
    @FXML
    private ImageView regd4;
    @FXML
    private ImageView regd8;

    void composantTree() {
        cop4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Compteur4bit.class, "Terminer."));
                Autres.Compteur4bit t = new Autres.Compteur4bit(true);
                t.setPosXImageView(event.getX());
                t.setPosYImageView(event.getY());
                t.setImageView();
                t.setNoeudEntreesCOP4();
                t.setNoeudSortieCOP4();
                workSpace.getChildren().add(t.getImageView());
                for (Noeud n :
                        t.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        t.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(t);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(t);
                MainWorkStack.push(item);
                event.consume();
            }
        });
        cop8.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Compteur8bit.class, "Terminer."));
                Autres.Compteur8bit t = new Autres.Compteur8bit(true);
                t.setPosXImageView(event.getX());
                t.setPosYImageView(event.getY());
                t.setImageView();
                t.setNoeudEntreesCOP8();
                t.setNoeudSortieCOP8();
                workSpace.getChildren().add(t.getImageView());
                for (Noeud n :
                        t.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        t.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(t);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(t);
                MainWorkStack.push(item);
                event.consume();
            }
        });
        regd4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Regadec4bit.class, "Terminer."));
                Autres.Regadec4bit t = new Autres.Regadec4bit(true);
                t.setPosXImageView(event.getX());
                t.setPosYImageView(event.getY());
                t.setImageView();
                t.setNoeudEntreesREGADEC4();
                t.setNoeudSortieREGADEC4();
                workSpace.getChildren().add(t.getImageView());
                for (Noeud n :
                        t.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        t.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(t);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(t);
                MainWorkStack.push(item);
                event.consume();
            }
        });
        regd8.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Regadec8bit.class, "Terminer."));
                Autres.Regadec8bit t = new Autres.Regadec8bit(true);
                t.setPosXImageView(event.getX());
                t.setPosYImageView(event.getY());
                t.setImageView();
                t.setNoeudEntreesREGADEC8();
                t.setNoeudSortieREGADEC8();
                workSpace.getChildren().add(t.getImageView());
                for (Noeud n :
                        t.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        t.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(t);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(t);
                MainWorkStack.push(item);
                event.consume();
            }
        });

        et2.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.ET et = new Port.ET(2);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);

            }
        });
        inv.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.INV.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.INV et = new Port.INV();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Branche b :
                        et.getBrancheSortie()) {
                    workSpace.getChildren().add(b.getNoeud().getCircle());
                }
                //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });

        aff2.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.Af_Seg.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.Af_Seg et = new Port.Af_Seg();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        tristate.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.Tristate.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.Tristate et = new Port.Tristate();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudSortie();
                et.setNoeudEntrees();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        et.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }

                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });

        JK.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.JK.class, "Terminer."));
                Bascule.JK jk = new Bascule.JK(true);
                jk.setPosXImageView(10);
                jk.setPosYImageView(10);
                jk.setImageView();
                jk.setNoeudEntreesJK();
                jk.setNoeudSortiejk();
                workSpace.getChildren().add(jk.getImageView());
                for (Noeud n :
                        jk.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        jk.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(jk);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(jk);
                MainWorkStack.push(item);
            }
        });
        JKasyn.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.JKasy.class, "Terminer."));
                Bascule.JKasy jKasy = new Bascule.JKasy(true);
                jKasy.setPosXImageView(10);
                jKasy.setPosYImageView(10);
                jKasy.setImageView();
                jKasy.setNoeudEntreesJKasy();
                jKasy.setNoeudSortiejkasy();
                workSpace.getChildren().add(jKasy.getImageView());
                for (Noeud n :
                        jKasy.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        jKasy.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(jKasy);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(jKasy);
                MainWorkStack.push(item);
            }
        });
        RS.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.RS.class, "Terminer."));
                Bascule.RS rs = new Bascule.RS();
                rs.setPosXImageView(10);
                rs.setPosYImageView(10);
                rs.setImageView();
                rs.setNoeudEntreesRS();
                rs.setNoeudSortieRS();
                workSpace.getChildren().add(rs.getImageView());
                for (Noeud n :
                        rs.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        rs.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(rs);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(rs);
                MainWorkStack.push(item);
            }
        });
        d.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.D.class, "Terminer."));
                Bascule.D d = new Bascule.D(true);
                d.setPosXImageView(event.getX());
                d.setPosYImageView(event.getY());
                d.setImageView();
                d.setNoeudEntreesD();
                d.setNoeudSortieD();
                workSpace.getChildren().add(d.getImageView());
                for (Noeud n :
                        d.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        d.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(d);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(d);
                MainWorkStack.push(item);
            }
        });
        dlatch.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.Dlatch.class, "Terminer."));
                Bascule.Dlatch dlatch = new Bascule.Dlatch(true);
                dlatch.setPosXImageView(10);
                dlatch.setPosYImageView(10);
                dlatch.setImageView();
                dlatch.setNoeudEntreesDlatch();
                dlatch.setNoeudSortieDlatch();
                workSpace.getChildren().add(dlatch.getImageView());
                for (Noeud n :
                        dlatch.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        dlatch.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(dlatch);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(dlatch);
                MainWorkStack.push(item);
            }
        });
        RST.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.RST.class, "Terminer."));
                Bascule.RST rst = new Bascule.RST();
                rst.setPosXImageView(10);
                rst.setPosYImageView(10);
                rst.setImageView();
                rst.setNoeudEntreesRST();
                rst.setNoeudSortieRST();
                workSpace.getChildren().add(rst.getImageView());
                for (Noeud n :
                        rst.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        rst.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(rst);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(rst);
                MainWorkStack.push(item);
            }
        });
        T.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.T.class, "Terminer."));
                Bascule.T t = new Bascule.T();
                t.setPosXImageView(10);
                t.setPosYImageView(10);
                t.setImageView();
                t.setNoeudEntreesT();
                t.setNoeudSortieT();
                workSpace.getChildren().add(t.getImageView());
                for (Noeud n :
                        t.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        t.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(t);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(t);
                MainWorkStack.push(item);
            }
        });
        REG4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Registre4bit.class, "Terminer."));
                Autres.Registre4bit t = new Autres.Registre4bit(true);
                t.setPosXImageView(10);
                t.setPosYImageView(10);
                t.setImageView();
                t.setNoeudEntreesREG4();
                t.setNoeudSortieREG4();
                workSpace.getChildren().add(t.getImageView());
                for (Noeud n :
                        t.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        t.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(t);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(t);
                MainWorkStack.push(item);
            }
        });
        REG8.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Registre8bit.class, "Terminer."));
                Autres.Registre8bit t = new Autres.Registre8bit(true);
                t.setPosXImageView(10);
                t.setPosYImageView(10);
                t.setImageView();
                t.setNoeudEntreesREG8();
                t.setNoeudSortieREG8();
                workSpace.getChildren().add(t.getImageView());
                for (Noeud n :
                        t.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud b :
                        t.getNoeudSortie()) {
                    workSpace.getChildren().add(b.getCircle());
                }
                composantLogiquesList.add(t);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(t);
                MainWorkStack.push(item);
            }
        });
        H.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Horloge.class, "Terminer."));
                Horloge h = new Horloge(1);
                h.setPosXImageView(10);
                h.setPosYImageView(10);
                h.setImageView();
                h.setNoeudSortie();
                workSpace.getChildren().add(h.getNoeudSortie().get(0).getCircle());
                workSpace.getChildren().add(h.getImageView());
                composantLogiquesList.add(h);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(h);
                MainWorkStack.push(item);
            }
        });
        Switch.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.Switch.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.Switch et = new Port.Switch();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        led.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.LED.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.LED et = new Port.LED();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });


        et3.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.ET et = new Port.ET(3);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        demiADD.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.demiAdditionneur2.class, "Terminer."));
                //System.out.println("NEW_ET");
                Combinatoires.demiAdditionneur2 et = new Combinatoires.demiAdditionneur2();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        ADDC1.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.completAdditionneur3.class, "Terminer."));
                //System.out.println("NEW_ET");
                Combinatoires.completAdditionneur3 et = new Combinatoires.completAdditionneur3();
                et.setPosXImageView(event.getX());
                et.setPosYImageView(event.getY());
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud n:
                     et.getNoeudSortie()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        ADDC2.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.Additionneur2bits.class, "Terminer."));
                //System.out.println("NEW_ET");
                Combinatoires.Additionneur2bits et = new Combinatoires.Additionneur2bits();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        ADDC4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.Additionneur4bits.class, "Terminer."));
                //System.out.println("NEW_ET");
                Combinatoires.Additionneur4bits et = new Combinatoires.Additionneur4bits();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        demiSOUS.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.demiSoustracteur.class, "Terminer."));
                //System.out.println("NEW_ET");
                Combinatoires.demiSoustracteur et = new Combinatoires.demiSoustracteur();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        Cmp4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.CompNbits.class, "Terminer."));
                //System.out.println("NEW_ET");
                Combinatoires.CompNbits et = new Combinatoires.CompNbits();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        Encod4_2.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.Encod4_2.class, "Terminer."));
                //System.out.println("NEW_ET");
                Combinatoires.Encod4_2 et = new Combinatoires.Encod4_2();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        Encod8_3.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.Encod8_3.class, "Terminer."));
                Combinatoires.Encod8_3 et = new Combinatoires.Encod8_3();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        et2.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.ET et = new Port.ET(2);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        et4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.ET et = new Port.ET(4);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        nand3.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.nonET et = new Port.nonET(3);
                et.setPosXImageView(event.getX());
                et.setPosYImageView(event.getY());
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        nand4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.nonET et = new Port.nonET(4);
                et.setPosXImageView(event.getX());
                et.setPosYImageView(event.getY());
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        /******************************************************************************************/
        /******************* younes ajouter /*****************************************************/
        /*********************************************************************************************/
        xor.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                Port.XOR et = new Port.XOR(2);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        /**********************************************************************************************/
        dec3_8.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                Combinatoire.Dec3_8 et = new Combinatoire.Dec3_8();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud n :
                        et.getNoeudSortie()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        /*********************************************************************************************/
        demux4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                Combinatoire.demux1_4 et = new Combinatoire.demux1_4();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud n :
                        et.getNoeudSortie()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        /*********************************************************************************************/
        demux8.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                Combinatoire.demux1_8 et = new Combinatoire.demux1_8();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud n :
                        et.getNoeudSortie()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        /**********************************************************************************************/
        dec2_4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                Combinatoire.Dec2_4 et = new Combinatoire.Dec2_4();
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                for (Noeud n :
                        et.getNoeudSortie()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        /******************************************************************************************/
        mux4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                Combinatoire.Mux4_1 et = new Combinatoire.Mux4_1(6);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });

        /******************************************************************************************/
        mux8.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                Combinatoire.Mux8_1 et = new Combinatoire.Mux8_1(13);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        /******************************************************************************************/
        /****************** FIN DE TRAVAIL DE YOUNES **********************************************/
        /******************************************************************************************/
        nand2.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.nonET.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.nonET et = new Port.nonET(2);
                et.setPosXImageView(event.getX());
                et.setPosYImageView(event.getY());
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        nand3.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.nonET.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.nonET et = new Port.nonET(3);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        nand4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.nonET.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.nonET et = new Port.nonET(4);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });

        ou3.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.OU et = new Port.OU(3);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        ou4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.OU et = new Port.OU(4);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        ou.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.OU et = new Port.OU(2);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        nor.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.nonOU et = new Port.nonOU(2);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        nor4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.nonOU et = new Port.nonOU(4);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });
        nor3.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                //System.out.println("NEW_ET");
                Port.nonOU et = new Port.nonOU(3);
                et.setPosXImageView(10);
                et.setPosYImageView(10);
                et.setImageView();
                et.setNoeudEntrees();
                et.setNoeudSortie();
                workSpace.getChildren().add(et.getImageView());
                for (Noeud n :
                        et.getNoeudEntrees()) {
                    workSpace.getChildren().add(n.getCircle());
                }
                workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                composantLogiquesList.add(et);
                WorkSpaceEvent item = new WorkSpaceEvent();
                item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                item.setComposant(et);
                MainWorkStack.push(item);
            }
        });

    }


    /***********************************/

    /**************************** ajoute */
    public static ObservableList<stat> ErreurList = FXCollections.observableArrayList();


    @FXML
    public static Tab statut;

    @FXML
    private TableView<stat> t_statut = new TableView<stat>();

    @FXML
    private TableColumn<stat, String> type;

    @FXML
    private TableColumn<stat, String> t_erreur;

    @FXML
    private TableColumn<stat, String> solution;

    private static int tabID = 1;


    public static class stat {
        private final SimpleStringProperty type;
        private final SimpleStringProperty erreur;
        private final SimpleStringProperty solution;


        public stat(String type, String erreur, String solution) {

            this.type = new SimpleStringProperty(type);
            this.erreur = new SimpleStringProperty(erreur);
            this.solution = new SimpleStringProperty(solution);
        }

        public String getType() {
            return type.get();
        }

        public SimpleStringProperty typeProperty() {
            return type;
        }

        public void setType(String type) {
            this.type.set(type);
        }

        public String getErreur() {
            return erreur.get();
        }

        public SimpleStringProperty erreurProperty() {
            return erreur;
        }

        public void setErreur(String erreur) {
            this.erreur.set(erreur);
        }

        public String getSolution() {
            return solution.get();
        }

        public SimpleStringProperty solutionProperty() {
            return solution;
        }

        public void setSolution(String solution) {
            this.solution.set(solution);
        }

        public String toString() {
            return ("\ntype " + this.type + " erreur " + this.erreur + " soltution " + this.solution);

        }

    }

    /*************************************************************/


    //--------- NEW --------------
    ObservableList<String> langues = FXCollections.observableArrayList("FR", "EN", "AR");
    public String selection = new String("Sélectionner");
    public String edit = new String("Edition");
    public String sup = new String("Supprimer");
    public String cop = new String("Copier");
    public String coup = new String("Couper");
    public String coll = new String("Coller");
    public String crr = new String("Créer");
    public String mod = new String("Modifier");
    public String ariplan = new String("Mettre au premier plan");
    public String preplan = new String("Mettre en arriére plan");
    public String aff = new String("Afficher ?");
    public String param = new String("Paramétre");
    public static Stage s;
    public static Stage stage2;


    @FXML
    private Menu f1;

    @FXML
    private Menu f2;
    @FXML
    private Menu f3;

    @FXML
    private Menu f5;
    @FXML
    private Menu f4;
    @FXML
    private Menu f6;
    @FXML
    private MenuItem f11;


    @FXML
    private MenuItem f12;

    @FXML
    private MenuItem f13;


    @FXML
    private MenuItem f14;
    @FXML
    private MenuItem f21;
    @FXML
    private MenuItem f22;
    @FXML
    private MenuItem f24;
    @FXML
    private MenuItem f23;

    @FXML
    private MenuItem f25;
    @FXML
    private MenuItem f26;
    @FXML
    private MenuItem f27;
    @FXML
    private MenuItem f28;
    @FXML
    private MenuItem f31;
    @FXML
    private MenuItem f32;
    @FXML
    private MenuItem f33;
    @FXML
    private MenuItem f34;
    @FXML
    private MenuItem configuration;
    @FXML
    private RadioMenuItem f41;
    @FXML
    private MenuItem f42;
    @FXML
    private RadioMenuItem f43;
    @FXML
    private RadioMenuItem f44;

    @FXML
    private Tab statique;

    @FXML
    private Tab historique;

    @FXML
    private ChoiceBox langue;


    public static int nombreEntree = 0;
    public static boolean inv1;
    public static boolean inv2;
    public static boolean inv3;
    public static boolean inv4;
    public MenuItem rotd = new MenuItem("Rotation Droite");
    public MenuItem rotg = new MenuItem("Rotation gauche");
    public MenuItem copier = new MenuItem(cop);
    public MenuItem couper = new MenuItem(coup);
    public MenuItem premierPlan = new MenuItem(preplan);
    public MenuItem arrierPlan = new MenuItem(ariplan);
    public MenuItem modifier = new MenuItem(mod);
    public MenuItem supp = new MenuItem(sup);
    public CheckMenuItem select = new CheckMenuItem(selection);
    public CheckMenuItem afficher = new CheckMenuItem(aff);
    public CheckMenuItem item1 = new CheckMenuItem("Position tip.");
    public MenuItem item2 = new MenuItem(edit);
    public MenuItem item0 = new MenuItem(sup);
    public MenuItem item3 = new MenuItem(cop);
    public MenuItem item33 = new MenuItem(coup);
    public MenuItem item4 = new MenuItem(coll);
    public MenuItem itemMain2 = new MenuItem(param);
    public Menu createMenu = new Menu(crr);
    public static boolean actionEdit = false;
    public static ComposantLogique v = null;
    public static boolean copy = false, cut = false;
    public static String clss;

    @FXML
    private Button chrono;

    @FXML
    private Button arb;

    private boolean emetteurActif = false;
    private boolean recepteurActif = false;

    private void chronogramme(ActionEvent event) throws Exception {
        try {
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Controller cn = new Controller();
            loader.setController(cn);
            root = (Pane) FXMLLoader.load(getClass().getResource("Chronogramme.fxml"));
            Image icon = new Image(getClass().getResource("icon.png").toURI().toString());


            stage.getIcons().add(icon);

            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Chronogramme");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setAlwaysOnTop(true);


            stage.show();


        } catch (Exception e) {
            System.out.print("chronogramme stopped.");
        }
    }

    public void SimulateAction() throws Exception {
        try {
            stage2 = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Controller cn = new Controller();
            loader.setController(cn);
            root = (Pane) FXMLLoader.load(getClass().getResource("simulator.fxml"));
            Image icon = new Image(getClass().getResource("icon.png").toURI().toString());
            stage2.getIcons().add(icon);
            stage2.setScene(new Scene(root));
            stage2.initStyle(StageStyle.DECORATED);
            stage2.setTitle("Simulateur");
            stage2.initModality(Modality.WINDOW_MODAL);
            stage2.setAlwaysOnTop(true);
            stage2.show();
        } catch (Exception var4) {
            System.out.print("Stage 3 stopped.");
        }

    }

    void Arbre(ActionEvent event) throws Exception {
        try {
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Controller cn = new Controller();
            loader.setController(cn);
            root = (Pane) FXMLLoader.load(getClass().getResource("Arbre.fxml"));
            Image icon = new Image(getClass().getResource("icon.png").toURI().toString());
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Arboresence de description");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------

    @FXML
    MenuItem demarrer;
    @FXML
    MenuItem arreter;
    @FXML
    MenuItem pause;
    @FXML
    MenuItem resumer;
    @FXML
    MenuItem redemarrer;

    public static double composantX;
    public static double composantY;

    public static double X;
    public static double Y;

    public static boolean isSimulate = false;
    private static loader Simulate;
    private static boolean estDemarrer = true;
    private static boolean estArreter = false;
    private static boolean estPauser = false;
    private static boolean estResumer = false;
    private static boolean estRedemarrer = false;
    private static boolean creationEnCourFil = false;
    private Fil filGlobale;

    ArrayList<Stack<WorkSpaceEvent>> MainWorkStackList = new ArrayList();
    ArrayList<Stack<WorkSpaceEvent>> SecondaryWorkStackList = new ArrayList();

    Stack<WorkSpaceEvent> MainWorkStack = new Stack<WorkSpaceEvent>();
    Stack<WorkSpaceEvent> SecondaryWorkStack = new Stack<WorkSpaceEvent>();

    @FXML
    private BorderPane root2;

    @FXML
    private TreeView PortsTree;

    @FXML
    private TabPane tabPane;

    public static TabPane tabPane2;

    @FXML
    private AnchorPane workSpace;

    @FXML
    private CheckMenuItem pleinEcran;

    @FXML
    private MenuItem fermer;

    @FXML
    private MenuItem nommerSchema;

    @FXML
    private Text textDescription;

    @FXML
    private CheckMenuItem grilleContinue;

    @FXML
    private CheckMenuItem grillePointilles1;

    @FXML
    private CheckMenuItem grillePointilles2;

    @FXML
    private MenuItem zoomPlus;

    @FXML
    private MenuItem zoomMiness;

    @FXML
    private MenuItem load;

    @FXML
    private MenuItem save;

    @FXML
    private MenuItem undo;

    @FXML
    private MenuItem redo;

    @FXML
    private ImageView portImageView;

    @FXML
    private Label portName;

    @FXML
    private AnchorPane FedAnchor;

    @FXML
    private Label position;

    @FXML
    private MenuItem historyAccelerator;


    public static String pos1 = new String("Position : ");

    public static Stage stage;
    public static boolean isSelect = false;
    public static boolean alreadyDragged = false;
    public static Node whatIsSelected;
    public static Label lSelection = new Label("Cliquez pour arrêter la sélection");
    public static EventHandler<MouseEvent> workSpaceEventHandler3;
    public static EventHandler<MouseEvent> workSpaceEventHandler2;
    public static EventHandler<MouseEvent> workSpaceEventHandler;
    public static EventHandler<MouseEvent> workSpaceEventTipHandler;
    public static EventHandler<MouseEvent> creationDeFilEventHandler;
    public static EventHandler<MouseEvent> composantLogiqueEventHandler;
    private Line l = new Line(20, 40, 270, 40);
    private Circle c = new Circle(15);


    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    //Context Menu
    private ContextMenu context = new ContextMenu();
    //Context Menu for main pane
    private MenuItem TO=new MenuItem("ToModule");
    private MenuItem FROM=new MenuItem("FromModule");
    private Menu passageMenu=new Menu("Outil de passage");
    private ContextMenu contextMainPane = new ContextMenu();
    private MenuItem supprimerFil=new MenuItem("Supprimer le fil");
    private ContextMenu contextFil = new ContextMenu(supprimerFil);
    private List<Line> grilleLines = new ArrayList<Line>();
    private Path helpLines = new Path();
    private Line helpLineCenterH = new Line();
    private Line helpLineCenterV = new Line();
    private Line helpLineH = new Line();
    private Line helpLineV = new Line();
    private Line helpLineFinH = new Line();
    private Line helpLineFinV = new Line();

    /*
    Les listes de commandes Fil, Noeud et Composant
     */

    public ArrayList<ArrayList<Noeud>> noeudsList2 = new ArrayList<ArrayList<Noeud>>();
    public static ArrayList<ArrayList<ComposantLogique>> composantLogiqueList2 = new ArrayList<ArrayList<ComposantLogique>>();
    public ArrayList<ArrayList<Fil>> filList2 = new ArrayList<ArrayList<Fil>>();

    public static List<Noeud> noeudsList = new ArrayList<Noeud>();
    public static List<ComposantLogique> composantLogiquesList = new ArrayList<ComposantLogique>();
    public static List<Fil> filList = new ArrayList<Fil>();

    public static double zoomFactor = 1;
    private double workSpaceHeight;
    private double workSpaceWidth;
    private double mousePosX;
    private double mousePosY;
    private boolean shiftKey;

    void exitAction() {
        try {
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Controller cn = new Controller();
            loader.setController(cn);
            root = (Pane) loader.load(getClass().getResource("exit.fxml"));
            Image icon = new Image(getClass().getResource("icon.png").toURI().toString());
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.DECORATED);
            stage.setX(MAX_X / 2 - root.getPrefWidth() / 2);
            stage.setY(MAX_Y / 2 - root.getPrefHeight() / 2);
            stage.setTitle("Confirmation de quitter");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setAlwaysOnTop(true);
            stage.initOwner(Main.stage);
            stage.show();
        } catch (Exception e) {
            System.out.print("Stage 5 stopped.");
        }
    }

    @FXML
    public void supprimerTout() {
        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Effacer l'écran", "Terminer."));
        workSpace.getChildren().clear();
        filList.clear();
        noeudsList.clear();
        composantLogiquesList.clear();
        gridDrowPointesLongue(workSpace);
        drawRulers(workSpace);
    }

    private void history(ActionEvent event) throws Exception {
        try {
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Controller cn = new Controller();
            loader.setController(cn);
            root = (Pane) FXMLLoader.load(getClass().getResource("History.fxml"));
            Image icon = new Image(getClass().getResource("icon.png").toURI().toString());
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Histoire");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (Exception e) {
            System.out.print("Stage 5 stopped.");
        }
    }

    private void loadScreen() {
        try {
            stage = new Stage();
            Parent load = FXMLLoader.load(getClass().getResource("start.fxml"));
            Image icon = new Image(getClass().getResource("icon.png").toURI().toString());
            stage.getIcons().add(icon);
            FadeTransition fade = new FadeTransition(Duration.seconds(5), load);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();
            Scene s = new Scene(load, 730, 410);
            stage.setScene(s);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void gridDrowPointesCourte() {
        for (int i = 0; i <= workSpace.getPrefWidth(); i += 25 * zoomFactor) {
            Line l1 = new Line();
            l1.toBack();
            l1.setStyle("-fx-stroke-width: 0.5px;");
            l1.setOpacity(0.8);
            l1.setLayoutX(i);
            l1.setLayoutY(0);
            l1.setStartX(0);
            l1.setStartY(0);
            l1.setEndX(0);
            l1.setEndY(workSpace.getPrefHeight());
            l1.getStrokeDashArray().addAll(2.2d);
            grilleLines.add(l1);
            workSpace.getChildren().add(l1);
        }
        for (int j = 0; j <= workSpace.getPrefHeight(); j += 25 * zoomFactor) {
            Line l2 = new Line();
            l2.toBack();
            l2.setStyle("-fx-stroke-width: 0.5px;");
            l2.setOpacity(0.8);
            l2.setLayoutX(0);
            l2.setLayoutY(j);
            l2.setStartX(0);
            l2.setStartY(0);
            l2.setEndX(workSpace.getPrefWidth());
            l2.setEndY(0);
            l2.getStrokeDashArray().addAll(2.2d);
            grilleLines.add(l2);
            workSpace.getChildren().add(l2);
        }
    }

    void drawRulers(AnchorPane w) {

        boolean v = true;
        for (int i = 0; i <= workSpace.getPrefWidth(); i += 25) {

            Line p = new Line();
            p.toFront();
            if (v) {
                v = false;
                p.setStyle("-fx-stroke-width: 1px;");
                p.setStroke(WHITE.brighter());
                p.setOpacity(1);
                p.setLayoutX(i);
                p.setLayoutY(0);
                p.setStartX(0);
                p.setStartY(0);
                p.setEndX(0);
                p.setEndY(13);
            } else {
                v = true;
                p.setStyle("-fx-stroke-width: 1px;");
                p.setStroke(WHITE.brighter());
                p.setOpacity(1);
                p.setLayoutX(i);
                p.setLayoutY(0);
                p.setStartX(0);
                p.setStartY(0);
                p.setEndX(0);
                p.setEndY(6.5);
            }

            p.toFront();
            w.getChildren().add(p);
        }

        v = true;
        for (int i = 0; i <= workSpace.getPrefHeight(); i += 22) {

            Line p = new Line();
            p.toFront();
            if (v) {
                v = false;
                p.setStyle("-fx-stroke-width: 1px;");
                p.setStroke(WHITE.brighter());
                p.setOpacity(1);
                p.setLayoutX(0);
                p.setLayoutY(i);
                p.setStartX(0);
                p.setStartY(0);
                p.setEndX(13);
                p.setEndY(0);
            } else {
                v = true;
                p.setStyle("-fx-stroke-width: 1px;");
                p.setStroke(WHITE.brighter());
                p.setOpacity(1);
                p.setLayoutX(0);
                p.setLayoutY(i);
                p.setStartX(0);
                p.setStartY(0);
                p.setEndX(6.5);
                p.setEndY(0);
            }

            p.toFront();
            w.getChildren().add(p);
        }
    }

    void gridDrowPointesLongue(AnchorPane w) {
        for (int i = 0; i <= w.getPrefWidth(); i += 25 * zoomFactor) {
            Line l1 = new Line();
            l1.toBack();
            l1.setStyle("-fx-stroke-width: 0.8px;");
            l1.setFill(GREEN);
            l1.setOpacity(1);
            l1.setLayoutX(i);
            l1.setLayoutY(0);
            l1.setStartX(0);
            l1.setStartY(0);
            l1.setEndX(0);
            l1.setEndY(w.getPrefHeight());
            l1.getStrokeDashArray().addAll(1d, 21d);
            grilleLines.add(l1);
            w.getChildren().add(l1);
        }
    }

    void gridDrowContinue(AnchorPane w) {
        for (int i = 0; i <= w.getPrefWidth(); i += 25 * zoomFactor) {
            Line l1 = new Line();
            l1.toBack();
            l1.setStyle("-fx-stroke-width: 0.4px;");
            l1.setOpacity(0.5);
            l1.setLayoutX(i);
            l1.setLayoutY(0);
            l1.setStartX(0);
            l1.setStartY(0);
            l1.setEndX(0);
            l1.setEndY(w.getPrefHeight());
            grilleLines.add(l1);
            w.getChildren().add(l1);
        }
        for (int j = 0; j <= w.getPrefHeight(); j += 25 * zoomFactor) {
            Line l2 = new Line();
            l2.toBack();
            l2.setStyle("-fx-stroke-width: 0.4px;");
            l2.setOpacity(0.5);
            l2.setLayoutX(0);
            l2.setLayoutY(j);
            l2.setStartX(0);
            l2.setStartY(0);
            l2.setEndX(w.getPrefWidth());
            l2.setEndY(0);
            grilleLines.add(l2);
            w.getChildren().add(l2);
        }
    }

    void position(double X, double Y) {
        position.setText("Position : ");
        position.setText(position.getText() + X + ", " + Y);
    }

    void zoomComposant() {
        for (ComposantLogique c :
                composantLogiquesList) {
            workSpace.getChildren().remove(c.getImageView());
            c.getImageView().setFitHeight(c.getHeight() * zoomFactor);
            c.getImageView().fitWidthProperty().set(c.getWidth() * zoomFactor);
            workSpace.getChildren().add(c.getImageView());
        }
    }

    private void clearPane(AnchorPane w) {
        for (Line l :
                grilleLines) {
            w.getChildren().remove(l);
        }
        grilleLines.clear();
    }

    private void lineHelpers() {
        helpLineH.setStroke(Paint.valueOf("GRAY"));
        helpLineH.setOpacity(0.8);
        helpLineH.getStrokeDashArray().addAll(18d, 18d, 5d, 18d);
        helpLineH.setLayoutY(0);
        helpLineH.setStartX(0);
        helpLineH.setStartY(0);
        helpLineH.setEndX(0);

        helpLineV.setStroke(Paint.valueOf("GRAY"));
        helpLineV.setOpacity(0.8);
        helpLineV.getStrokeDashArray().addAll(18d, 18d, 5d, 18d);
        helpLineV.setLayoutX(0);
        helpLineV.setStartX(0);
        helpLineV.setStartY(0);
        helpLineV.setEndY(0);

        helpLineFinV.setStroke(Paint.valueOf("GRAY"));
        helpLineFinV.setOpacity(0.8);
        helpLineFinV.getStrokeDashArray().addAll(18d, 18d, 5d, 18d);
        helpLineFinV.setLayoutY(0);
        helpLineFinV.setStartX(0);
        helpLineFinV.setStartY(0);
        helpLineFinV.setEndX(0);

        helpLineFinH.setStroke(Paint.valueOf("GRAY"));
        helpLineFinH.setOpacity(0.8);
        helpLineFinH.getStrokeDashArray().addAll(18d, 18d, 5d, 18d);
        helpLineFinH.setLayoutX(0);
        helpLineFinH.setStartX(0);
        helpLineFinH.setStartY(0);
        helpLineFinH.setEndY(0);

        helpLineCenterH.setStroke(LIGHTGREEN);
        helpLineCenterH.setOpacity(0.8);
        helpLineCenterH.getStrokeDashArray().addAll(15d, 15d, 5d, 15d);
        helpLineCenterH.setLayoutX(0);
        helpLineCenterH.setStartX(0);
        helpLineCenterH.setStartY(0);
        helpLineCenterH.setEndY(0);

        helpLineCenterV.setStroke(LIGHTGREEN);
        helpLineCenterV.setOpacity(0.8);
        helpLineCenterV.getStrokeDashArray().addAll(15d, 15d, 5d, 15d);
        helpLineCenterV.setLayoutY(0);
        helpLineCenterV.setStartX(0);
        helpLineCenterV.setStartY(0);
        helpLineCenterV.setEndX(0);
    }


    private boolean isDisobeyHLine(ComposantLogique c, double debutCoorX, double debutCoorY, double finCoorX) {
        boolean b = false;
        if (debutCoorY > c.getPosYImageView() && debutCoorY < c.getPosYImageView() + c.getImage().getHeight()) {
            for (int i = 0; i < Math.abs(debutCoorX - finCoorX); i += 2) {
                if (debutCoorX < finCoorX) {
                    double x = debutCoorX + i;
                    if (x > c.getPosXImageView() && x < c.getPosXImageView() + c.getImage().getWidth()) {
                        b = true;
                        break;
                    }
                } else {
                    double x = finCoorX + i;
                    if (x > c.getPosXImageView() && x < c.getPosXImageView() + c.getImage().getWidth()) {
                        b = true;
                        break;
                    }
                }
            }
        } else {
            b = false;
        }
        return b;
    }

    private boolean isDisobeyVLine(ComposantLogique c, double debutCoorX, double debutCoorY, double finCoorY) {
        boolean b = false;
        if (debutCoorX > c.getPosXImageView() && debutCoorX < c.getPosXImageView() + c.getImage().getWidth()) {
            for (int i = 0; i < Math.abs(debutCoorY - finCoorY); i += 2) {
                if (debutCoorY < finCoorY) {
                    double x = debutCoorY + i;
                    if (x > c.getPosYImageView() && x < c.getPosYImageView() + c.getImage().getHeight()) {
                        b = true;
                        break;
                    }
                } else {
                    double x = finCoorY + i;
                    if (x > c.getPosYImageView() && x < c.getPosYImageView() + c.getImage().getHeight()) {
                        b = true;
                        break;
                    }
                }
            }
        } else {
            b = false;
        }
        return b;
    }

    private void lineNormalizationAlgorithm() {
        // some code here
        int index;
        for (ComposantLogique c :
                composantLogiquesList) {
            for (Fil f :
                    filList) {
                index = 1;
                while (index < f.getFil().getElements().size()) {
                    if ((index == 1) || (index == 2)) {
                        PathElement element1 = f.getFil().getElements().get(index);
                        MoveTo moveto = (MoveTo) f.getFil().getElements().get(0);
                        double debutX = moveto.getX();
                        double debutY = moveto.getY();
                        if (f.getFil().getElements().get(index).getClass() == HLineTo.class) {
                            System.out.println("1: ");
                            double finX = ((HLineTo) element1).getX();
                            if (isDisobeyHLine(c, debutX, debutY, finX))
                                System.out.println(element1 + " is in forbidden area.");
                        } else if (f.getFil().getElements().get(index).getClass() == VLineTo.class) {
                            double debutX2 = ((HLineTo) f.getFil().getElements().get(index - 1)).getX();
                            System.out.println("2: ");
                            if (isDisobeyVLine(c, debutX2, debutY, ((VLineTo) (element1)).getY()))
                                System.out.println(element1 + " is in forbidden area.");
                        }
                    } else {
                        PathElement element1 = f.getFil().getElements().get(index);
                        PathElement element0 = f.getFil().getElements().get(index - 1);
                        if (f.getFil().getElements().get(index).getClass() == HLineTo.class) {
                            System.out.println("3: ");
                            PathElement element00 = f.getFil().getElements().get(index - 2);
                            if (isDisobeyHLine(c, ((HLineTo) element00).getX(), ((VLineTo) (element0)).getY(), ((HLineTo) (element1)).getX()))
                                System.out.println(element1 + " is in forbidden area.");
                        } else if (f.getFil().getElements().get(index).getClass() == VLineTo.class) {
                            //System.out.println("4: ");
                            //if(isDisobeyVLine(c, (VLineTo) element1, ((VLineTo) element0).getY())) System.out.println(element1 + " is in forbidden area.");
                        }
                    }
                    index++;
                }
            }
        }
    }

    private void threeLineAlgorithm(Fil fil) {
        VLineTo vfil = new VLineTo();
        HLineTo hfil1 = new HLineTo();
        HLineTo hfil2 = new HLineTo();

        fil.getFil().getElements().clear();
        fil.getFilHorisontal().clear();
        fil.getFilVertical().clear();

        fil.getFil().getElements().addAll(fil.getMoveTo().get(0), hfil1, vfil, hfil2);
        fil.addFilVertical(vfil);
        fil.addFilHorisontal(hfil1);
        fil.addFilHorisontal(hfil2);
        double debutX = fil.getNoeudDebut().getCircle().getCenterX();
        double debutY = fil.getNoeudDebut().getCircle().getCenterY();
        double finX = fil.getNoeudFin().getCircle().getCenterX();
        double finY = fil.getNoeudFin().getCircle().getCenterY();


        hfil1.setX(debutX + Math.abs((finX - debutX) / 2));
        vfil.setY(finY);
        hfil2.setX(finX);
    }

    private void redefineFil() {
        for (Fil fil :
                filList) {
            // path control
            fil.getMoveTo().get(0).setX(fil.getNoeudDebut().getCircle().getCenterX());
            fil.getMoveTo().get(0).setY(fil.getNoeudDebut().getCircle().getCenterY());
            if (fil.getFil().getElements().get(fil.getFil().getElements().size() - 1).getClass() == HLineTo.class) {
                fil.getFilHorisontal().get(fil.getFilHorisontal().size() - 1).setX(fil.getNoeudFin().getCircle().getCenterX());
                fil.getFilVertical().get(fil.getFilVertical().size() - 1).setY(fil.getNoeudFin().getCircle().getCenterY());
                HLineTo f;
                f = (HLineTo) fil.getFil().getElements().get(fil.getFil().getElements().size() - 1);
                if (f.getX() < fil.getFilHorisontal().get(fil.getFilHorisontal().size() - 2).getX()) {
                    //fil.getFilHorisontal().get(fil.getFilHorisontal().size()-2).setX(fil.getNoeudDebut().getCircle().getCenterX()+10);
                }
            } else if (fil.getFil().getElements().get(fil.getFil().getElements().size() - 1).getClass() == VLineTo.class) {
                fil.getFilHorisontal().get(fil.getFilHorisontal().size() - 1).setX(fil.getNoeudFin().getCircle().getCenterX());
                fil.getFilVertical().get(fil.getFilVertical().size() - 1).setY(fil.getNoeudFin().getCircle().getCenterY());
            }

            if (fil.getFil().getElements().size() > 6) {
                threeLineAlgorithm(fil);
            }
            // ------------
            //fil.getFil().setLayoutX(fil.getNoeudDebut().getCircle().getCenterX());
            //fil.getFil().setLayoutY(fil.getNoeudDebut().getCircle().getCenterY());
            //fil.getFil().setEndX(fil.getNoeudFin().getCircle().getCenterX() - fil.getFil().getLayoutX());
            //fil.getFil().setEndY(fil.getNoeudFin().getCircle().getCenterY() - fil.getFil().getLayoutY());
            lineNormalizationAlgorithm();
        }
    }

    private ComposantLogique redefineComposantLogique(ComposantLogique c, double x, double y) {

        for (Branche n :
                c.getBrancheEntree()) {
            workSpace.getChildren().remove(n.getNoeud().getCircle());
            n.getNoeud().getCircle().setCenterX(n.getNoeud().getCircle().getCenterX() + x);
            n.getNoeud().getCircle().setCenterY(n.getNoeud().getCircle().getCenterY() + y);
            workSpace.getChildren().add(n.getNoeud().getCircle());
        }
        for (Branche n :
                c.getBrancheSortie()) {
            workSpace.getChildren().remove(n.getNoeud().getCircle());
            n.getNoeud().getCircle().setCenterX(n.getNoeud().getCircle().getCenterX() + x);
            n.getNoeud().getCircle().setCenterY(n.getNoeud().getCircle().getCenterY() + y);
            workSpace.getChildren().add(n.getNoeud().getCircle());
        }
        if (f44.isSelected()) {
            removeLabels();
            setLabels();
        }

        if (f43.isSelected()) {
            removeLabelsComposant();
            setLabelsComposant();
        }
        return null;
    }

    void zoomPlusFunction() {
        zoomFactor += 0.3;
        System.out.print(zoomFactor);
        workSpace.setPrefHeight(workSpaceHeight * zoomFactor);
        workSpace.setPrefWidth(workSpaceWidth * zoomFactor);
        clearPane(workSpace);
        if (grilleContinue.isSelected()) {
            gridDrowContinue(workSpace);
        } else if (grillePointilles1.isSelected()) {
            gridDrowPointesLongue(workSpace);
        } else {
            gridDrowPointesCourte();
        }
        zoomComposant();
    }

    void zoomMinessFunction() {
        zoomFactor -= 0.3;
        System.out.print(zoomFactor);
        workSpace.setPrefHeight(workSpaceHeight * zoomFactor);
        workSpace.setPrefWidth(workSpaceWidth * zoomFactor);
        clearPane(workSpace);
        if (grilleContinue.isSelected()) {
            gridDrowContinue(workSpace);
        } else if (grillePointilles1.isSelected()) {
            gridDrowPointesLongue(workSpace);
        } else {
            gridDrowPointesCourte();
        }
        zoomComposant();
    }

    void supprimerComposant(ComposantLogique composantLogique) {
        workSpace.getChildren().remove(composantLogique.getImageView());
        for (Branche b :
                composantLogique.getBrancheEntree()) {
            for (Fil f : filList) {
                if (f.getNoeudFin() == b.getNoeud()) {
                    supprimerFil(f);
                    break;
                }
            }
            if (f44.isSelected()) workSpace.getChildren().remove(b.getIDE());
            workSpace.getChildren().remove(b.getNoeud().getCircle());
            noeudsList.remove(b.getNoeud());
        }
        for (Branche b :
                composantLogique.getBrancheSortie()) {
            for (Fil f : filList) {
                if (f.getNoeudFin() == b.getNoeud() || f.getNoeudDebut() == b.getNoeud()) {
                    supprimerFil(f);
                    break;
                }
            }
            if (f44.isSelected()) workSpace.getChildren().remove(b.getIDS());
            workSpace.getChildren().remove(b.getNoeud().getCircle());
            noeudsList.remove(b.getNoeud());
        }
        if (f43.isSelected()) workSpace.getChildren().remove(composantLogique.getID());
        composantLogiquesList.remove(composantLogique);
    }

    void creationComposant(ComposantLogique composantLogique) {

    }

    void supprimerFil(Fil fil) {
        filList.remove(fil);
        workSpace.getChildren().remove(fil.getFil());
    }

    private void switchLogique() {
        for (ComposantLogique c :
                composantLogiquesList) {
            if (c.getClass().equals(Port.Switch.class)) {
                c.getImageView().setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (c.getBrancheSortie()[0].getValeur() == LogicalEtat.ONE) {
                            c.getBrancheSortie()[0].setValeur(LogicalEtat.ZERO);
                        } else {
                            c.getBrancheSortie()[0].setValeur(LogicalEtat.ONE);
                        }
                    }
                });
            }
        }
    }

    public void setSimulationMenuLogique() {
        demarrer.setOnAction((e) -> {
            chrono.setDisable(false);
            split1.setDividerPosition(0, 0.70);
            tabHistorique.getSelectionModel().select(0);
            //Demarrer action ici ...
            /*********************************************ajoute par chohra*/
            stat erreur;

            ErreurList.clear();
            /*for (Noeud n :
                    Controller.noeudsList) {
                n.Simulation();
                if ((n.getEmetteurFil() == null) &&(n.getEmetteurComp() == null)) {

                    erreur = new stat("Warning", "Erreur trouvé : non entré trouvé ", "Ajouter des entrées ");
                    n.getCircle().setStroke(Paint.valueOf("RED"));
                    ErreurList.add(erreur);

                }

            }*/

            // AMOKRANE
            for (ComposantLogique c :
                    composantLogiquesList) {
                for (Branche b :
                        c.getBrancheEntree()) {
                    if (b.getNoeud().getEmetteurFil() == null) {
                        erreur = new stat("Warning", "Erreur trouvé : non entré trouvé ", "Ajouter des entrées ");
                        b.getNoeud().getCircle().setStroke(Paint.valueOf("RED"));
                        ErreurList.add(erreur);
                    }
                }
                for (Branche b :
                        c.getBrancheSortie()) {
                    System.out.print(b.getNoeud().getRecepteurFil());
                    if (b.getNoeud().getRecepteurFil().isEmpty()) {
                        erreur = new stat("Warning", "Erreur trouvé : la sortie est à vide ", "Faire lier la sortie ");
                        b.getNoeud().getCircle().setStroke(Paint.valueOf("RED"));
                        ErreurList.add(erreur);
                    }
                }

            }
            // --------

            for (Fil f : Controller.filList) {
                boolean debutEntree = false;
                boolean finEntree = false;
                boolean debutSortie = false;
                boolean finSortie = false;

                for (ComposantLogique c :
                        composantLogiquesList) {
                    for (Branche b :
                            c.getBrancheEntree()) {
                        if (f.getNoeudDebut() == b.getNoeud()) debutEntree = true;
                        if (f.getNoeudFin() == b.getNoeud()) finEntree = true;
                        if (debutEntree && finEntree) {
                            ErreurList.add(new stat("Erreur ", "Entre avec Entre ", "Ajoute un switch"));
                            f.getFil().setStroke(Paint.valueOf("RED"));
                            break;
                        }
                    }

                    for (Branche b :
                            c.getBrancheSortie()) {
                        if (f.getNoeudDebut() == b.getNoeud()) debutSortie = true;
                        if (f.getNoeudFin() == b.getNoeud()) finSortie = true;
                        if (debutSortie && finSortie) {
                            ErreurList.add(new stat("Erreur ", "Sortie avec Sortie ", "Utiliser un tristate"));
                            f.getFil().setStroke(Paint.valueOf("RED"));
                            break;
                        }
                    }
                }
            }
            t_statut.setItems(ErreurList);
            if (!ErreurList.isEmpty()) {
                workSpace.addEventHandler(MouseEvent.ANY, workSpaceEventHandler);
                isSimulate = false;
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Arrêter la simulation", "En cours ..."));
                for (ComposantLogique c :
                        composantLogiquesList) {
                    for (Branche b :
                            c.getBrancheEntree()) {
                        b.getNoeud().getCircle().setFill(Paint.valueOf("CYAN"));
                    }
                    for (Branche b :
                            c.getBrancheSortie()) {
                        b.getNoeud().getCircle().setFill(Paint.valueOf("CYAN"));
                    }
                    c.getImageView().setEffect(null);
                }

                System.out.println("Arreter pressed ...");
                estDemarrer = true;
                estArreter = false;
                estPauser = false;
                estResumer = false;
                estRedemarrer = true;
                setSimulationMenuItemLogique2();
            } else {
                try {
                    for (Tab t:
                            tabPane.getTabs()) {
                        if(!t.isSelected()) t.setDisable(true);
                    }
                    SimulateAction();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                stop.setDisable(false);

                switchLogique();
                //ErreurList.clear();
                isSimulate = true;
                Simulate = new loader();
                Simulate.start();
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Demarrer la simulation", "En cours ..."));
                workSpace.removeEventHandler(MouseEvent.ANY, workSpaceEventHandler);
                estDemarrer = false;
                estPauser = true;
                estArreter = true;
                estResumer = false;
                estRedemarrer = true;
                setSimulationMenuItemLogique2();
                HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Demarrage avec succès", "Terminer."));
            }
        });
        /****************************************************************/
        arreter.setOnAction((e) -> {
            //Arreter action ici ...
            for (Tab t:
                    tabPane.getTabs()) {
                if(!t.isSelected()) t.setDisable(false);
            }
            stage2.close();
            workSpace.addEventHandler(MouseEvent.ANY, workSpaceEventHandler);
            isSimulate = false;
            HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Arrêter la simulation", "En cours ..."));
            for (ComposantLogique c :
                    composantLogiquesList) {
                for (Branche b :
                        c.getBrancheEntree()) {
                    b.getNoeud().getCircle().setFill(Paint.valueOf("CYAN"));
                }
                for (Branche b :
                        c.getBrancheSortie()) {
                    b.getNoeud().getCircle().setFill(Paint.valueOf("CYAN"));
                }
                c.getImageView().setEffect(null);
            }
            for (Fil f :
                    filList) {
                f.getFil().setStroke(Paint.valueOf("CYAN"));
            }
            estDemarrer = true;
            estArreter = false;
            estPauser = false;
            estResumer = false;
            estRedemarrer = true;
            setSimulationMenuItemLogique2();
        });
        pause.setOnAction((e) -> {
            //Pause action ici ...
            HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Pauser la simulation", "En cours ..."));
            estPauser = false;
            estResumer = true;
            estDemarrer = false;
            estArreter = false;
            estRedemarrer = true;
            setSimulationMenuItemLogique2();
        });
        resumer.setOnAction((e) -> {
            //Resumer action ici ...
            HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Resumer la simulation", "En cours ..."));
            estPauser = true;
            estDemarrer = false;
            estRedemarrer = true;
            estArreter = true;
            estResumer = false;
            setSimulationMenuItemLogique2();
            HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Resumer", "Terminer."));

        });
        redemarrer.setOnAction((e) -> {
            //Redemarrer action ici ...
            HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Redémarrer la simulation", "En cours ..."));
            estDemarrer = false;
            estArreter = true;
            estPauser = true;
            estResumer = false;
            estRedemarrer = true;
            setSimulationMenuItemLogique2();
            HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Redémarrage", "Terminer."));
        });
    }

    private void setSimulationMenuItemLogique2() {
        demarrer.setDisable(!estDemarrer);
        arreter.setDisable(!estArreter);
        pause.setDisable(!estPauser);
        resumer.setDisable(!estResumer);
        redemarrer.setDisable(!estRedemarrer);
    }


    void setLabels() {
        for (ComposantLogique c :
                composantLogiquesList) {
            for (Branche b :
                    c.getBrancheEntree()) {
                b.getIDE().setLabelFor(b.getNoeud().getCircle());
                b.getIDE().setLayoutX(b.getNoeud().getCircle().getCenterX() - 5);
                b.getIDE().setLayoutY(b.getNoeud().getCircle().getCenterY());
                workSpace.getChildren().add(b.getIDE());
            }
            for (Branche b :
                    c.getBrancheSortie()) {
                b.getIDS().setLabelFor(b.getNoeud().getCircle());
                b.getIDS().setLayoutX(b.getNoeud().getCircle().getCenterX() + 5);
                b.getIDS().setLayoutY(b.getNoeud().getCircle().getCenterY());
                workSpace.getChildren().add(b.getIDS());
            }
        }
    }

    void removeLabels() {
        for (ComposantLogique c :
                composantLogiquesList) {
            for (Branche b :
                    c.getBrancheEntree()) {

                workSpace.getChildren().remove(b.getIDE());
            }
            for (Branche b :
                    c.getBrancheSortie()) {

                workSpace.getChildren().remove(b.getIDS());
            }
        }
    }

    void setLabelsComposant() {
        for (ComposantLogique c :
                composantLogiquesList) {
            c.getID().setText(c.getClass().getSimpleName());
            c.getID().setLabelFor(c.getImageView());
            c.getID().toFront();
            c.getID().setLayoutX(c.getPosXImageView() + c.getImage().getWidth() / 2 - c.getID().getWidth() / 2);
            c.getID().setLayoutY(c.getPosYImageView() + c.getImage().getHeight() / 2 - c.getID().getHeight() / 2);
            c.getID().setStyle("-fx-text-fill: GRAY; -fx-text-size:3em");
            workSpace.getChildren().add(c.getID());
        }
    }

    void removeLabelsComposant() {
        for (ComposantLogique c :
                composantLogiquesList) {
            workSpace.getChildren().remove(c.getID());
        }
    }

    void setNeonComposant() {
        for (ComposantLogique c :
                composantLogiquesList) {
            int depth = 70;
            DropShadow borderGlow = new DropShadow();
            borderGlow.setSpread(0.25);

            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.GRAY);
            borderGlow.setWidth(depth);
            borderGlow.setHeight(depth);

            c.getImageView().setEffect(borderGlow);
        }
    }

    void removeNeonComposant() {
        for (ComposantLogique c :
                composantLogiquesList) {
            c.getImageView().setEffect(null);
        }
    }

    public void saveFile(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream f = new ObjectOutputStream(fos);
            f.writeByte(composantLogiquesList.size());
            for (ComposantLogique c :
                    composantLogiquesList) {
                f.writeObject(new composantSauv(c));
            }
            f.writeByte(filList.size());
            for (Fil fil :
                    filList) {
                f.writeObject(new filSauv(fil));
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile(File file) {
        supprimerTout();
        try {
            FileInputStream fos = new FileInputStream(file);
            ObjectInputStream f = new ObjectInputStream(fos);
            try {
                composantSauv c;
                int size = f.readByte();
                for (int i = 1; i <= size; i++) {
                    c = (composantSauv) f.readObject();
                    if (c == null) break;
                    switch (c.getaClass().getSimpleName()) {
                        case "ToModule":
                            TO.fire();
                            break;
                        case "FromModule":
                            FROM.fire();
                            break;
                        case "ET":
                            // come herererere
                            Port.ET et = new Port.ET(2);
                            et.setPosXImageView(c.getPositionX());
                            et.setPosYImageView(c.getPositionY());
                            et.setImageView();
                            et.setNoeudEntrees();
                            et.setNoeudSortie();
                            workSpace.getChildren().add(et.getImageView());
                            for (Noeud n :
                                    et.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                            composantLogiquesList.add(et);
                            break;
                        case "OU":
                            HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                            //System.out.println("NEW_ET");
                            Port.OU ou = new Port.OU(2);
                            ou.setPosXImageView(c.getPositionX());
                            ou.setPosYImageView(c.getPositionY());
                            ou.setImageView();
                            ou.setNoeudEntrees();
                            ou.setNoeudSortie();
                            workSpace.getChildren().add(ou.getImageView());
                            for (Noeud n :
                                    ou.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            workSpace.getChildren().add(ou.getNoeudSortie().get(0).getCircle());
                            composantLogiquesList.add(ou);
                            WorkSpaceEvent item = new WorkSpaceEvent();
                            item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                            item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                            item.setComposant(ou);
                            MainWorkStack.push(item);
                            break;
                        case "nonET":
                            Port.nonET nonET = new Port.nonET(2);
                            nonET.setPosXImageView(c.getPositionX());
                            nonET.setPosYImageView(c.getPositionY());
                            nonET.setImageView();
                            nonET.setNoeudEntrees();
                            nonET.setNoeudSortie();
                            workSpace.getChildren().add(nonET.getImageView());
                            for (Noeud n :
                                    nonET.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            workSpace.getChildren().add(nonET.getNoeudSortie().get(0).getCircle());
                            composantLogiquesList.add(nonET);
                            break;
                        case "nonOU":
                            HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                            //System.out.println("NEW_ET");
                            Port.nonOU nonOU = new Port.nonOU(2);
                            nonOU.setPosXImageView(c.getPositionX());
                            nonOU.setPosYImageView(c.getPositionY());
                            nonOU.setImageView();
                            nonOU.setNoeudEntrees();
                            nonOU.setNoeudSortie();
                            workSpace.getChildren().add(nonOU.getImageView());
                            for (Noeud n :
                                    nonOU.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            workSpace.getChildren().add(nonOU.getNoeudSortie().get(0).getCircle());
                            composantLogiquesList.add(nonOU);
                            break;
                        case "XOR":
                            Port.XOR XOR = new Port.XOR(2);
                            XOR.setPosXImageView(c.getPositionX());
                            XOR.setPosYImageView(c.getPositionY());
                            XOR.setImageView();
                            XOR.setNoeudEntrees();
                            XOR.setNoeudSortie();
                            workSpace.getChildren().add(XOR.getImageView());
                            for (Noeud n :
                                    XOR.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            workSpace.getChildren().add(XOR.getNoeudSortie().get(0).getCircle());
                            composantLogiquesList.add(XOR);
                            break;
                        case "Af_Seg":
                            Port.Af_Seg aff = new Port.Af_Seg();
                            aff.setPosXImageView(c.getPositionX());
                            aff.setPosYImageView(c.getPositionY());
                            aff.setImageView();
                            aff.setNoeudEntrees();
                            workSpace.getChildren().add(aff.getImageView());
                            for (Noeud n :
                                    aff.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            composantLogiquesList.add(aff);
                            break;
                        case "INV":
                            Port.INV inv = new Port.INV();
                            inv.setPosXImageView(c.getPositionX());
                            inv.setPosYImageView(c.getPositionY());
                            inv.setImageView();
                            inv.setNoeudEntrees();
                            inv.setNoeudSortie();
                            workSpace.getChildren().add(inv.getImageView());
                            for (Noeud n :
                                    inv.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Branche b :
                                    inv.getBrancheSortie()) {
                                workSpace.getChildren().add(b.getNoeud().getCircle());
                            }
                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                            composantLogiquesList.add(inv);

                            break;
                        case "Switch":
                            Port.Switch aSwitch = new Port.Switch();
                            aSwitch.setPosXImageView(c.getPositionX());
                            aSwitch.setPosYImageView(c.getPositionY());
                            aSwitch.setImageView();
                            aSwitch.setNoeudSortie();
                            workSpace.getChildren().add(aSwitch.getImageView());
                            workSpace.getChildren().add(aSwitch.getNoeudSortie().get(0).getCircle());
                            composantLogiquesList.add(aSwitch);
                            break;
                        case "LED":
                            Port.LED led = new Port.LED();
                            led.setPosXImageView(c.getPositionX());
                            led.setPosYImageView(c.getPositionY());
                            led.setImageView();
                            led.setNoeudEntrees();
                            workSpace.getChildren().add(led.getImageView());
                            for (Noeud n :
                                    led.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            composantLogiquesList.add(led);

                            break;
                        case "Dec2_4":
                            Combinatoire.Dec2_4 dec2_4 = new Combinatoire.Dec2_4();
                            dec2_4.setPosXImageView(c.getPositionX());
                            dec2_4.setPosYImageView(c.getPositionY());
                            dec2_4.setImageView();
                            dec2_4.setNoeudEntrees();
                            dec2_4.setNoeudSortie();
                            workSpace.getChildren().add(dec2_4.getImageView());
                            for (Noeud n :
                                    dec2_4.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud n :
                                    dec2_4.getNoeudSortie()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            composantLogiquesList.add(dec2_4);

                            break;
                        case "Dec3_8":
                            Combinatoire.Dec3_8 dec3_8 = new Combinatoire.Dec3_8();
                            dec3_8.setPosXImageView(c.getPositionX());
                            dec3_8.setPosYImageView(c.getPositionX());
                            dec3_8.setImageView();
                            dec3_8.setNoeudEntrees();
                            dec3_8.setNoeudSortie();
                            workSpace.getChildren().add(dec3_8.getImageView());
                            for (Noeud n :
                                    dec3_8.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud n :
                                    dec3_8.getNoeudSortie()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            composantLogiquesList.add(dec3_8);

                            break;
                        case "demux1_4":
                            Combinatoire.demux1_4 demux1_4 = new Combinatoire.demux1_4();
                            demux1_4.setPosXImageView(c.getPositionX());
                            demux1_4.setPosYImageView(c.getPositionY());
                            demux1_4.setImageView();
                            demux1_4.setNoeudEntrees();
                            demux1_4.setNoeudSortie();
                            workSpace.getChildren().add(demux1_4.getImageView());
                            for (Noeud n :
                                    demux1_4.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud n :
                                    demux1_4.getNoeudSortie()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            composantLogiquesList.add(demux1_4);

                            break;
                        case "demux1_8":
                            Combinatoire.demux1_8 demux1_8 = new Combinatoire.demux1_8();
                            demux1_8.setPosXImageView(c.getPositionX());
                            demux1_8.setPosYImageView(c.getPositionY());
                            demux1_8.setImageView();
                            demux1_8.setNoeudEntrees();
                            demux1_8.setNoeudSortie();
                            workSpace.getChildren().add(demux1_8.getImageView());
                            for (Noeud n :
                                    demux1_8.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud n :
                                    demux1_8.getNoeudSortie()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            composantLogiquesList.add(demux1_8);

                            break;
                        case "Mux4_1":
                            Combinatoire.Mux4_1 mux4_1 = new Combinatoire.Mux4_1(6);
                            mux4_1.setPosXImageView(c.getPositionX());
                            mux4_1.setPosYImageView(c.getPositionY());
                            mux4_1.setImageView();
                            mux4_1.setNoeudEntrees();
                            mux4_1.setNoeudSortie();
                            workSpace.getChildren().add(mux4_1.getImageView());
                            for (Noeud n :
                                    mux4_1.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            workSpace.getChildren().add(mux4_1.getNoeudSortie().get(0).getCircle());
                            composantLogiquesList.add(mux4_1);

                            break;
                        case "Mux8_1":
                            Combinatoire.Mux8_1 mux8_1 = new Combinatoire.Mux8_1(13);
                            mux8_1.setPosXImageView(c.getPositionX());
                            mux8_1.setPosYImageView(c.getPositionY());
                            mux8_1.setImageView();
                            mux8_1.setNoeudEntrees();
                            mux8_1.setNoeudSortie();
                            workSpace.getChildren().add(mux8_1.getImageView());
                            for (Noeud n :
                                    mux8_1.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            workSpace.getChildren().add(mux8_1.getNoeudSortie().get(0).getCircle());
                            composantLogiquesList.add(mux8_1);

                            break;
                        case "demiAdditionneur2":
                            Combinatoires.demiAdditionneur2 demiAdditionneur2 = new Combinatoires.demiAdditionneur2();
                            demiAdditionneur2.setPosXImageView(c.getPositionX());
                            demiAdditionneur2.setPosYImageView(c.getPositionY());
                            demiAdditionneur2.setImageView();
                            demiAdditionneur2.setNoeudEntrees();
                            demiAdditionneur2.setNoeudSortie();
                            workSpace.getChildren().add(demiAdditionneur2.getImageView());
                            for (Noeud n :
                                    demiAdditionneur2.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud n :
                                    demiAdditionneur2.getNoeudSortie()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            composantLogiquesList.add(demiAdditionneur2);

                            break;
                        case "demiSoustracteur":
                            Combinatoires.demiSoustracteur demiSoustracteur = new Combinatoires.demiSoustracteur();
                            demiSoustracteur.setPosXImageView(c.getPositionX());
                            demiSoustracteur.setPosYImageView(c.getPositionY());
                            demiSoustracteur.setImageView();
                            demiSoustracteur.setNoeudEntrees();
                            demiSoustracteur.setNoeudSortie();
                            workSpace.getChildren().add(demiSoustracteur.getImageView());
                            for (Noeud n :
                                    demiSoustracteur.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud n :
                                    demiSoustracteur.getNoeudSortie()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            composantLogiquesList.add(demiSoustracteur);


                            break;
                        case "Additionneur2bits":
                            Combinatoires.Additionneur2bits additionneur2bits = new Combinatoires.Additionneur2bits();
                            additionneur2bits.setPosXImageView(c.getPositionX());
                            additionneur2bits.setPosYImageView(c.getPositionY());
                            additionneur2bits.setImageView();
                            additionneur2bits.setNoeudEntrees();
                            additionneur2bits.setNoeudSortie();
                            workSpace.getChildren().add(additionneur2bits.getImageView());
                            for (Noeud n :
                                    additionneur2bits.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud n :
                                    additionneur2bits.getNoeudSortie()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            composantLogiquesList.add(additionneur2bits);

                            break;
                        case "Additionneur4bits":
                            Combinatoires.Additionneur4bits add = new Combinatoires.Additionneur4bits();
                            add.setPosXImageView(c.getPositionX());
                            add.setPosYImageView(c.getPositionY());
                            add.setImageView();
                            add.setNoeudEntrees();
                            add.setNoeudSortie();
                            workSpace.getChildren().add(add.getImageView());
                            for (Noeud n :
                                    add.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud n :
                                    add.getNoeudSortie()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            composantLogiquesList.add(add);

                            break;
                        case "CompNbits":
                            Combinatoires.CompNbits compNbits = new Combinatoires.CompNbits();
                            compNbits.setPosXImageView(c.getPositionX());
                            compNbits.setPosYImageView(c.getPositionY());
                            compNbits.setImageView();
                            compNbits.setNoeudEntrees();
                            compNbits.setNoeudSortie();
                            workSpace.getChildren().add(compNbits.getImageView());
                            for (Noeud n :
                                    compNbits.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            workSpace.getChildren().add(compNbits.getNoeudSortie().get(0).getCircle());
                            composantLogiquesList.add(compNbits);


                            break;
                        case "Encod4_2":
                            Combinatoires.Encod4_2 encod4_2 = new Combinatoires.Encod4_2();
                            encod4_2.setPosXImageView(c.getPositionX());
                            encod4_2.setPosYImageView(c.getPositionY());
                            encod4_2.setImageView();
                            encod4_2.setNoeudEntrees();
                            encod4_2.setNoeudSortie();
                            workSpace.getChildren().add(encod4_2.getImageView());
                            for (Noeud n :
                                    encod4_2.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud n :
                                    encod4_2.getNoeudSortie()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            composantLogiquesList.add(encod4_2);

                            break;
                        case "Encod8_3":
                            Combinatoires.Encod8_3 encod8_3 = new Combinatoires.Encod8_3();
                            encod8_3.setPosXImageView(c.getPositionX());
                            encod8_3.setPosYImageView(c.getPositionY());
                            encod8_3.setImageView();
                            encod8_3.setNoeudEntrees();
                            encod8_3.setNoeudSortie();
                            workSpace.getChildren().add(encod8_3.getImageView());
                            for (Noeud n :
                                    encod8_3.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud n :
                                    encod8_3.getNoeudSortie()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            composantLogiquesList.add(encod8_3);

                            break;
                        case "Horloge":
                            Horloge h = new Horloge(1);
                            h.setPosXImageView(c.getPositionX());
                            h.setPosYImageView(c.getPositionY());
                            h.setImageView();
                            h.setNoeudSortie();
                            workSpace.getChildren().add(h.getNoeudSortie().get(0).getCircle());
                            workSpace.getChildren().add(h.getImageView());
                            composantLogiquesList.add(h);

                            break;
                        case "JK":
                            Bascule.JK jk = new Bascule.JK(true);
                            jk.setPosXImageView(c.getPositionX());
                            jk.setPosYImageView(c.getPositionY());
                            jk.setImageView();
                            jk.setNoeudEntreesJK();
                            jk.setNoeudSortiejk();
                            workSpace.getChildren().add(jk.getImageView());
                            for (Noeud n :
                                    jk.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud b :
                                    jk.getNoeudSortie()) {
                                workSpace.getChildren().add(b.getCircle());
                            }
                            composantLogiquesList.add(jk);

                            break;
                        case "D":
                            Bascule.D d = new Bascule.D(true);
                            d.setPosXImageView(c.getPositionX());
                            d.setPosYImageView(c.getPositionY());
                            d.setImageView();
                            d.setNoeudEntreesD();
                            d.setNoeudSortieD();
                            workSpace.getChildren().add(d.getImageView());
                            for (Noeud n :
                                    d.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud b :
                                    d.getNoeudSortie()) {
                                workSpace.getChildren().add(b.getCircle());
                            }
                            composantLogiquesList.add(d);

                            break;
                        case "T":
                            Bascule.T t = new Bascule.T();
                            t.setPosXImageView(c.getPositionX());
                            t.setPosYImageView(c.getPositionY());
                            t.setImageView();
                            t.setNoeudEntreesT();
                            t.setNoeudSortieT();
                            workSpace.getChildren().add(t.getImageView());
                            for (Noeud n :
                                    t.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud b :
                                    t.getNoeudSortie()) {
                                workSpace.getChildren().add(b.getCircle());
                            }
                            composantLogiquesList.add(t);

                            break;
                        case "Dlatch":
                            Bascule.Dlatch dlatch = new Bascule.Dlatch(true);
                            dlatch.setPosXImageView(c.getPositionX());
                            dlatch.setPosYImageView(c.getPositionY());
                            dlatch.setImageView();
                            dlatch.setNoeudEntreesDlatch();
                            dlatch.setNoeudSortieDlatch();
                            workSpace.getChildren().add(dlatch.getImageView());
                            for (Noeud n :
                                    dlatch.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud b :
                                    dlatch.getNoeudSortie()) {
                                workSpace.getChildren().add(b.getCircle());
                            }
                            composantLogiquesList.add(dlatch);

                            break;
                        case "RS":
                            Bascule.RS rs = new Bascule.RS();
                            rs.setPosXImageView(c.getPositionX());
                            rs.setPosYImageView(c.getPositionY());
                            rs.setImageView();
                            rs.setNoeudEntreesRS();
                            rs.setNoeudSortieRS();
                            workSpace.getChildren().add(rs.getImageView());
                            for (Noeud n :
                                    rs.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud b :
                                    rs.getNoeudSortie()) {
                                workSpace.getChildren().add(b.getCircle());
                            }
                            composantLogiquesList.add(rs);

                            break;
                        case "JKasy":
                            Bascule.JKasy jKasy = new Bascule.JKasy(true);
                            jKasy.setPosXImageView(c.getPositionX());
                            jKasy.setPosYImageView(c.getPositionY());
                            jKasy.setImageView();
                            jKasy.setNoeudEntreesJKasy();
                            jKasy.setNoeudSortiejkasy();
                            workSpace.getChildren().add(jKasy.getImageView());
                            for (Noeud n :
                                    jKasy.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud b :
                                    jKasy.getNoeudSortie()) {
                                workSpace.getChildren().add(b.getCircle());
                            }
                            composantLogiquesList.add(jKasy);

                            break;
                        case "RST":
                            Bascule.RST rst = new Bascule.RST();
                            rst.setPosXImageView(c.getPositionX());
                            rst.setPosYImageView(c.getPositionY());
                            rst.setImageView();
                            rst.setNoeudEntreesRST();
                            rst.setNoeudSortieRST();
                            workSpace.getChildren().add(rst.getImageView());
                            for (Noeud n :
                                    rst.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud b :
                                    rst.getNoeudSortie()) {
                                workSpace.getChildren().add(b.getCircle());
                            }
                            composantLogiquesList.add(rst);
                            break;
                        case "Registre4bit":
                            Autres.Registre4bit registre4bit = new Autres.Registre4bit(true);
                            registre4bit.setPosXImageView(c.getPositionX());
                            registre4bit.setPosYImageView(c.getPositionY());
                            registre4bit.setImageView();
                            registre4bit.setNoeudEntreesREG4();
                            registre4bit.setNoeudSortieREG4();
                            workSpace.getChildren().add(registre4bit.getImageView());
                            for (Noeud n :
                                    registre4bit.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud b :
                                    registre4bit.getNoeudSortie()) {
                                workSpace.getChildren().add(b.getCircle());
                            }
                            composantLogiquesList.add(registre4bit);
                            break;
                        case "Registre8bit":
                            Autres.Registre8bit registre8bit = new Autres.Registre8bit(true);
                            registre8bit.setPosXImageView(c.getPositionX());
                            registre8bit.setPosYImageView(c.getPositionY());
                            registre8bit.setImageView();
                            registre8bit.setNoeudEntreesREG8();
                            registre8bit.setNoeudSortieREG8();
                            workSpace.getChildren().add(registre8bit.getImageView());
                            for (Noeud n :
                                    registre8bit.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud b :
                                    registre8bit.getNoeudSortie()) {
                                workSpace.getChildren().add(b.getCircle());
                            }
                            composantLogiquesList.add(registre8bit);
                            break;

                        case "Compteur4bit":
                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Compteur4bit.class, "Terminer."));
                        Autres.Compteur4bit t2 = new Autres.Compteur4bit(true);
                        t2.setPosXImageView(c.getPositionX());
                        t2.setPosYImageView(c.getPositionY());
                        t2.setImageView();
                        t2.setNoeudEntreesCOP4();
                        t2.setNoeudSortieCOP4();
                        workSpace.getChildren().add(t2.getImageView());
                        for (Noeud n :
                                t2.getNoeudEntrees()) {
                            workSpace.getChildren().add(n.getCircle());
                        }
                        for (Noeud b :
                                t2.getNoeudSortie()) {
                            workSpace.getChildren().add(b.getCircle());
                        }
                        composantLogiquesList.add(t2);
                            break;
                        case "Compteur8bit":
                            HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Compteur4bit.class, "Terminer."));
                            Autres.Compteur8bit t3 = new Autres.Compteur8bit(true);
                            t3.setPosXImageView(c.getPositionX());
                            t3.setPosYImageView(c.getPositionY());
                            t3.setImageView();
                            t3.setNoeudEntreesCOP8();
                            t3.setNoeudSortieCOP8();
                            workSpace.getChildren().add(t3.getImageView());
                            for (Noeud n :
                                    t3.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud b :
                                    t3.getNoeudSortie()) {
                                workSpace.getChildren().add(b.getCircle());
                            }
                            composantLogiquesList.add(t3);
                            break;
                        case "Regadec4bit":
                            HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Regadec4bit.class, "Terminer."));
                            Autres.Regadec4bit t45 = new Autres.Regadec4bit(true);
                            t45.setPosXImageView(c.getPositionX());
                            t45.setPosYImageView(c.getPositionY());
                            t45.setImageView();
                            t45.setNoeudEntreesREGADEC4();
                            t45.setNoeudSortieREGADEC4();
                            workSpace.getChildren().add(t45.getImageView());
                            for (Noeud n :
                                    t45.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud b :
                                    t45.getNoeudSortie()) {
                                workSpace.getChildren().add(b.getCircle());
                            }
                            composantLogiquesList.add(t45);
                            break;
                        case "Regadec8bit":
                            HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Regadec4bit.class, "Terminer."));
                            Autres.Regadec8bit t123456789 = new Autres.Regadec8bit(true);
                            t123456789.setPosXImageView(c.getPositionX());
                            t123456789.setPosYImageView(c.getPositionY());
                            t123456789.setImageView();
                            t123456789.setNoeudEntreesREGADEC8();
                            t123456789.setNoeudSortieREGADEC8();
                            workSpace.getChildren().add(t123456789.getImageView());
                            for (Noeud n :
                                    t123456789.getNoeudEntrees()) {
                                workSpace.getChildren().add(n.getCircle());
                            }
                            for (Noeud b :
                                    t123456789.getNoeudSortie()) {
                                workSpace.getChildren().add(b.getCircle());
                            }
                            composantLogiquesList.add(t123456789);
                            break;
                    }
                }
                int size2 = f.readByte();
                filSauv fs;
                for (int i = 1; i <= size2; i++) {
                    Fil fil = new Fil();
                    fs = (filSauv) f.readObject();
                    MoveTo debut = new MoveTo();
                    debut.setX(fs.getDebutCoorX());
                    debut.setY(fs.getDebutCoorY());
                    fil.addMoveTo(debut);
                    fil.getFil().getElements().add(debut);
                    for (int j = 0; j < fs.getPointType().size(); j++) {
                        if (fs.getPointType().get(j) == HLineTo.class) {
                            HLineTo hl = new HLineTo();
                            hl.setX(fs.getPointCoordinate().get(j));
                            fil.getFil().getElements().add(hl);
                            fil.addFilHorisontal(hl);
                        } else {
                            VLineTo vl = new VLineTo();
                            vl.setY(fs.getPointCoordinate().get(j));
                            fil.getFil().getElements().add(vl);
                            fil.addFilVertical(vl);
                        }
                    }
                    MoveTo finmoveTo = new MoveTo();
                    finmoveTo.setX(fs.getFinCoorX());
                    finmoveTo.setY(fs.getFinCoorY());
                    fil.addMoveTo(finmoveTo);
                    fil.getFil().setStyle("-fx-stroke-width: 2.2px;");
                    fil.getFil().setStroke(CYAN);
                    filList.add(fil);
                    workSpace.getChildren().add(fil.getFil());
                }

                for (Fil fil :
                        filList) {
                    for (ComposantLogique co :
                            composantLogiquesList) {
                        for (Branche b :
                                co.getBrancheEntree()) {
                            if (((MoveTo) fil.getFil().getElements().get(0)).getX() == b.getNoeud().getCircle().getCenterX() && ((MoveTo) fil.getFil().getElements().get(0)).getY() == b.getNoeud().getCircle().getCenterY()) {
                                fil.setNoeudDebut(b.getNoeud());
                            }
                            if (fil.getMoveTo().get(1).getX() == b.getNoeud().getCircle().getCenterX() && fil.getMoveTo().get(1).getY() == b.getNoeud().getCircle().getCenterY()) {
                                fil.setNoeudFin(b.getNoeud());
                            }
                        }
                        for (Branche b :
                                co.getBrancheSortie()) {
                            if (((MoveTo) fil.getFil().getElements().get(0)).getX() == b.getNoeud().getCircle().getCenterX() && ((MoveTo) fil.getFil().getElements().get(0)).getY() == b.getNoeud().getCircle().getCenterY()) {
                                fil.setNoeudDebut(b.getNoeud());
                            }
                            if (fil.getMoveTo().get(1).getX() == b.getNoeud().getCircle().getCenterX() && fil.getMoveTo().get(1).getY() == b.getNoeud().getCircle().getCenterY()) {
                                fil.setNoeudFin(b.getNoeud());
                            }
                        }
                    }
                }

                for (ComposantLogique compo :
                        composantLogiquesList) {
                    for (Branche b :
                            compo.getBrancheEntree()) {
                        for (Fil fill :
                                filList) {
                            if (fill.getNoeudDebut() == b.getNoeud() || fill.getNoeudFin() == b.getNoeud())
                                b.getNoeud().setEmetteurFil(fill);
                        }
                        b.getNoeud().setReceptionEntree(b.getNumeroDeBranche());
                        b.getNoeud().setRecepteurComp(compo);
                    }

                    for (Branche b :
                            compo.getBrancheSortie()) {
                        for (Fil fill :
                                filList) {
                            if (fill.getNoeudDebut() == b.getNoeud() || fill.getNoeudFin() == b.getNoeud())
                                b.getNoeud().setRecepteurFil(fill);
                        }
                        b.getNoeud().setEmessionSortie(b.getNumeroDeBranche());
                        b.getNoeud().setEmetteurComp(compo);
                    }
                }
                fos.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Move the mouse to the specific screen position
     *
     * @param screenX
     * @param screenY
     */
    public void moveCursor(double screenX, double screenY) {
        Platform.runLater(() -> {
            try {
                Robot robot = new Robot();
                robot.mouseMove((int) screenX, (int) screenY);
            } catch (AWTException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }


    public void EditAction(ActionEvent e) throws Exception {
        try {
            s = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Controller cn = new Controller();
            loader.setController(cn);
            root = (Pane) FXMLLoader.load(getClass().getResource("sampleEdit.fxml"));
            Image icon = new Image(getClass().getResource("icon.png").toURI().toString());
            s.getIcons().add(icon);
            s.setScene(new Scene(root));
            s.initStyle(StageStyle.DECORATED);
            s.setTitle("Edit");
            s.initModality(Modality.WINDOW_MODAL);
            s.setAlwaysOnTop(true);
            s.show();
        } catch (Exception var4) {
            System.out.print("Stage 3 stopped.");
        }

    }

    public void ouvrirNavigateur(ActionEvent e) throws Exception {
        try {
            s = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Controller cn = new Controller();
            loader.setController(cn);
            root = (AnchorPane) FXMLLoader.load(getClass().getResource("navigateur.fxml"));
            Image icon = new Image(getClass().getResource("icon.png").toURI().toString());
            s.getIcons().add(icon);
            s.setScene(new Scene(root));
            s.initStyle(StageStyle.DECORATED);
            s.setTitle("Navigateur");
            s.initModality(Modality.WINDOW_MODAL);
            s.setAlwaysOnTop(true);
            s.show();
        } catch (Exception var4) {
            var4.printStackTrace();
            System.out.print("Stage 3 stopped.");
        }

    }


    @FXML
    ImageView lock;
    private boolean isLocked = false;

    @FXML
    private ChoiceBox<String> utiliser;

    void utiliserLogique() {
        utiliser.getItems().addAll("SWITCH",
                "LED",
                "ET2",
                "ET3",
                "OU2",
                "OU3",
                "HORLOGE",
                "AFFICHEUR 7 SEGMENTS",
                "<!>NONE<!>"
        );
        utiliser.getSelectionModel().select("<!>NONE<!>");
        utiliser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch (utiliser.getSelectionModel().getSelectedItem()) {
                    case "SWITCH":
                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.Switch.class, "Terminer."));
                        //System.out.println("NEW_ET");
                        Port.Switch et = new Port.Switch();
                        et.setPosXImageView(10);
                        et.setPosYImageView(10);
                        et.setImageView();
                        et.setNoeudSortie();
                        workSpace.getChildren().add(et.getImageView());
                        workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                        composantLogiquesList.add(et);
                        WorkSpaceEvent item = new WorkSpaceEvent();
                        item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                        item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                        item.setComposant(et);
                        MainWorkStack.push(item);
                        break;
                    case "LED":
                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.LED.class, "Terminer."));
                        //System.out.println("NEW_ET");
                        Port.LED led = new Port.LED();
                        led.setPosXImageView(10);
                        led.setPosYImageView(10);
                        led.setImageView();
                        led.setNoeudEntrees();
                        workSpace.getChildren().add(led.getImageView());
                        for (Noeud n :
                                led.getNoeudEntrees()) {
                            workSpace.getChildren().add(n.getCircle());
                        }
                        composantLogiquesList.add(led);
                        WorkSpaceEvent item2 = new WorkSpaceEvent();
                        item2.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                        item2.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                        item2.setComposant(led);
                        MainWorkStack.push(item2);
                        break;
                    case "ET2":
                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                        //System.out.println("NEW_ET");
                        Port.ET et456 = new Port.ET(2);
                        et456.setPosXImageView(20);
                        et456.setPosYImageView(20);
                        et456.setImageView();
                        et456.setNoeudEntrees();
                        et456.setNoeudSortie();
                        workSpace.getChildren().add(et456.getImageView());
                        for (Noeud n :
                                et456.getNoeudEntrees()) {
                            workSpace.getChildren().add(n.getCircle());
                        }
                        workSpace.getChildren().add(et456.getNoeudSortie().get(0).getCircle());
                        composantLogiquesList.add(et456);
                        WorkSpaceEvent item456 = new WorkSpaceEvent();
                        item456.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                        item456.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                        item456.setComposant(et456);
                        MainWorkStack.push(item456);
                        break;
                    case "ET3":
                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                        //System.out.println("NEW_ET");
                        Port.ET et4569 = new Port.ET(3);
                        et4569.setPosXImageView(20);
                        et4569.setPosYImageView(20);
                        et4569.setImageView();
                        et4569.setNoeudEntrees();
                        et4569.setNoeudSortie();
                        workSpace.getChildren().add(et4569.getImageView());
                        for (Noeud n :
                                et4569.getNoeudEntrees()) {
                            workSpace.getChildren().add(n.getCircle());
                        }
                        workSpace.getChildren().add(et4569.getNoeudSortie().get(0).getCircle());
                        composantLogiquesList.add(et4569);
                        WorkSpaceEvent item444 = new WorkSpaceEvent();
                        item444.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                        item444.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                        item444.setComposant(et4569);
                        MainWorkStack.push(item444);
                        break;
                    case "OU2":
                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                        //System.out.println("NEW_ET");
                        Port.OU et12 = new Port.OU(2);
                        et12.setPosXImageView(20);
                        et12.setPosYImageView(20);
                        et12.setImageView();
                        et12.setNoeudEntrees();
                        et12.setNoeudSortie();
                        workSpace.getChildren().add(et12.getImageView());
                        for (Noeud n :
                                et12.getNoeudEntrees()) {
                            workSpace.getChildren().add(n.getCircle());
                        }
                        workSpace.getChildren(
                        ).add(et12.getNoeudSortie().get(0).getCircle());
                        composantLogiquesList.add(et12);
                        WorkSpaceEvent item145 = new WorkSpaceEvent();
                        item145.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                        item145.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                        item145.setComposant(et12);
                        MainWorkStack.push(item145);
                        break;
                    case "OU3":
                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                        //System.out.println("NEW_ET");
                        Port.OU et1212123132 = new Port.OU(3);
                        et1212123132.setPosXImageView(20);
                        et1212123132.setPosYImageView(20);
                        et1212123132.setImageView();
                        et1212123132.setNoeudEntrees();
                        et1212123132.setNoeudSortie();
                        workSpace.getChildren().add(et1212123132.getImageView());
                        for (Noeud n :
                                et1212123132.getNoeudEntrees()) {
                            workSpace.getChildren().add(n.getCircle());
                        }
                        workSpace.getChildren().add(et1212123132.getNoeudSortie().get(0).getCircle());
                        composantLogiquesList.add(et1212123132);
                        WorkSpaceEvent item798797 = new WorkSpaceEvent();
                        item798797.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                        item798797.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                        item798797.setComposant(et1212123132);
                        MainWorkStack.push(item798797);
                        break;
                    case "HORLOGE":
                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Horloge.class, "Terminer."));
                        //System.out.println("NEW_ET");
                        Horloge h = new Horloge(1);
                        h.setPosXImageView(10);
                        h.setPosYImageView(10);
                        h.setImageView();
                        h.setNoeudSortie();
                        workSpace.getChildren().add(h.getNoeudSortie().get(0).getCircle());
                        workSpace.getChildren().add(h.getImageView());
                        composantLogiquesList.add(h);
                    /*hLoader h0=new hLoader();
                    h0.setHologe(h);
                    h0.start();*/
                        WorkSpaceEvent item6 = new WorkSpaceEvent();
                        item6.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                        item6.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                        item6.setComposant(h);
                        MainWorkStack.push(item6);
                        break;
                    case "AFFICHEUR 7 SEGMENTS":
                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.Af_Seg.class, "Terminer."));
                        //System.out.println("NEW_ET");
                        Port.Af_Seg et321 = new Port.Af_Seg();
                        et321.setPosXImageView(10);
                        et321.setPosYImageView(10);
                        et321.setImageView();
                        et321.setNoeudEntrees();
                        workSpace.getChildren().add(et321.getImageView());
                        for (Noeud n :
                                et321.getNoeudEntrees()) {
                            workSpace.getChildren().add(n.getCircle());
                        }
                        composantLogiquesList.add(et321);
                        WorkSpaceEvent item10101010 = new WorkSpaceEvent();
                        item10101010.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                        item10101010.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                        item10101010.setComposant(et321);
                        MainWorkStack.push(item10101010);
                        break;
                }
                utiliser.getSelectionModel().select("<!>NONE<!>");
            }
        });
    }


    void lockLogique() {
        lock.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isLocked) {
                    Image i = null;
                    try {
                        i = new Image((getClass().getResource("icons/lock2.png").toURI().toString()));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    lock.setImage(i);
                    isLocked = true;
                    tabPane.removeEventHandler(MouseEvent.ANY, workSpaceEventHandler);
                    workSpace.removeEventHandler(MouseEvent.ANY, workSpaceEventHandler2);
                } else {
                    Image i = null;
                    try {
                        i = new Image((getClass().getResource("icons/lock1.png").toURI().toString()));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    lock.setImage(i);
                    isLocked = false;
                    tabPane.addEventHandler(MouseEvent.ANY, workSpaceEventHandler);
                    workSpace.addEventHandler(MouseEvent.ANY, workSpaceEventHandler2);
                }
            }
        });
    }

    @FXML
    private TabPane tabHistorique;

    @FXML
    private TabPane tabProject;

    @FXML
    private TableColumn Action;

    @FXML
    private TableView<HistoryController.Processus> Table = new TableView<HistoryController.Processus>();

    @FXML
    private TableColumn Temps;

    @FXML
    private TableColumn Statut;

    void TableViewLogique() {
        Action.setCellValueFactory(new PropertyValueFactory<HistoryController.Processus, String>("action"));
        Temps.setCellValueFactory(new PropertyValueFactory<HistoryController.Processus, String>("temps"));
        Statut.setCellValueFactory(new PropertyValueFactory<HistoryController.Processus, String>("statut"));
        Table.setItems(HistoryController.data);
    }


    void tabLogique() {
        tabProject.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            split2.setDividerPosition(0, 0.25);
        });
    }

    void workSpaceTabsLogique() {

    }

    @FXML
    private ImageView back;
    @FXML
    private SplitPane split2;
    @FXML
    private SplitPane split1;

    void backLogique() {

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                split2.setDividerPosition(0, 0);
            }
        });
    }

    @FXML
    private AnchorPane treeAnchor1;
    @FXML
    private AnchorPane treeAnchor2;
    @FXML
    private TreeView<String> projectTree;
    public static String projectName = "Default project";
    public static String projectFullPath = "";

    void setTree() {
        Node rootIcon = new ImageView(
                new Image(getClass().getResourceAsStream("icons/dir1.png"))
        );

        Node leafIcon = new ImageView(
                new Image(getClass().getResourceAsStream("icons/file.png"))
        );

        TreeItem<String> rootItem = new TreeItem<String>(projectName, rootIcon);
        for (Tab t :
                tabPane.getTabs()) {
            TreeItem<String> item = new TreeItem<String>(t.getText(), leafIcon);
            rootItem.getChildren().add(item);
        }
        rootItem.setExpanded(false);
        projectTree.setRoot(rootItem);
    }

    void miseJourProjetFiles(Tab tab) {
        File[] files = new File(projectFullPath).listFiles();
        for (File f :
                files) {
            if (f.getName().split("\\.")[0].equals(tab.getText())) {
                try {
                    Files.delete(f.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void miseJourTree(String action, Tab tab) {
        if (action == "remove") {
            for (TreeItem i :
                    projectTree.getRoot().getChildren()) {
                if (i.getValue() == tab.getText()) {
                    projectTree.getRoot().getChildren().remove(i);
                    break;
                }
            }
        } else if (action == "add") {
            Node leafIcon = new ImageView(
                    new Image(getClass().getResourceAsStream("icons/file.png"))
            );
            TreeItem<String> item = new TreeItem<String>(tab.getText(), leafIcon);
            projectTree.getRoot().getChildren().add(item);
        }
    }

    @FXML
    private AnchorPane buttonBar;

    @FXML
    void about() throws IOException {
        String command = "cmd /c start index.html";
        Process child = Runtime.getRuntime().exec(command);
    }


    void setInverseur() {
        for (ComposantLogique c :
                composantLogiquesList) {
            for (Branche b :
                    c.getBrancheEntree()) {
                if (b.isEstInverser()) {
                    b.getNoeud().getCircle().setRadius(3);
                    b.getNoeud().getCircle().setFill(Paint.valueOf("transparent"));
                    b.getNoeud().getCircle().setStroke(Paint.valueOf("CYAN"));
                } else {
                    b.getNoeud().getCircle().setRadius(3);
                    b.getNoeud().getCircle().setFill(Paint.valueOf("CYAN"));
                }
            }
        }
    }

    void supprimerItemTree(TreeItem i) {
        Tab t = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirme la suppression");
        alert.setContentText("Êtes-vous sûr d'effacer ce Schéma?");

        Optional<ButtonType> result = alert.showAndWait();
        for (Tab tab :
                tabPane.getTabs()) {
            if (tab.getText() == i.getValue()) {
                t = tab;
            }
        }
        if (result.get() == ButtonType.OK) {
            composantLogiqueList2.remove(tabPane.getTabs().indexOf(t));
            filList2.remove(tabPane.getTabs().indexOf(t));
            noeudsList2.remove(tabPane.getTabs().indexOf(t));

            tabPane.getTabs().remove(t);
            miseJourTree("remove", t);
            miseJourProjetFiles(t);
        }
    }

    ContextMenu seeContextTree() {
        ContextMenu c = new ContextMenu();
        MenuItem nom = new MenuItem("Nommer le Schéma");
        MenuItem add = new MenuItem("Ajouter un Schéma");
        MenuItem remove = new MenuItem("Supprimer le Schéma");
        c.getItems().addAll(nom, new SeparatorMenuItem(), add, remove);
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Nouveau(null);
            }
        });
        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem i = new TreeItem();
                i = projectTree.getSelectionModel().getSelectedItems().get(0);
                supprimerItemTree(i);
                if (tabPane.getTabs().isEmpty()) {
                    Text nou = new Text("Aucun Schéma n'apparait dans le projet !");
                    Text creer = new Text("Creer un nouveau schéma de travail (Ctrl + N)");

                    nou.setFill(Color.BLACK);
                    nou.setX(450);
                    nou.setY(200);
                    nou.setFont(javafx.scene.text.Font.font(null, FontWeight.LIGHT, 19));

                    creer.setFill(Color.LIGHTBLUE);
                    creer.setX(450);
                    creer.setY(280);
                    creer.setFont(javafx.scene.text.Font.font(null, FontWeight.MEDIUM, 17));

                    Tab emptyTab = new Tab("Projet vide");
                    tabPane.getTabs().add(emptyTab);
                    AnchorPane a = new AnchorPane(nou, creer);
                    emptyTab.setContent(a);
                    creer.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            creer.setFont(javafx.scene.text.Font.font(null, FontWeight.BOLD, 17));
                        }
                    });
                    creer.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            tabPane.getTabs().clear();
                            f11.fire();
                        }
                    });
                    creer.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            creer.setFont(javafx.scene.text.Font.font(null, FontWeight.MEDIUM, 17));
                        }
                    });
                }
            }
        });
        nom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nommerSchema.fire();
            }
        });
        return c;
    }


    @FXML
    private ImageView hide;
    @FXML
    private Button new2;
    @FXML
    private Button saveP;
    @FXML
    private Button openModule;
    @FXML
    private Button open;
    @FXML
    private Button start;
    @FXML
    private Button stop;

    void setButtonLogique() {
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                demarrer.fire();
            }
        });
        stop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arreter.fire();
                stop.setDisable(true);
            }
        });
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                load.fire();
            }
        });
        openModule.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // open modulas
            }
        });
        new2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Nouveau(null);
            }
        });
    }

    void setButtonTips() {

        setButtonLogique();
        Tooltip newTip = new Tooltip("Nouveau Schéma");
        Tooltip saveTip = new Tooltip("Enregistrer");
        Tooltip openTip = new Tooltip("Ouvrir un projet");
        Tooltip open2Tip = new Tooltip("Ouvrir des modèles");
        Tooltip chronoTip = new Tooltip("Chronograme");
        Tooltip playTip = new Tooltip("Démarrer la simulation");
        Tooltip stopTip = new Tooltip("Arrêter la simulation");
        Tooltip placerTip = new Tooltip("Placer un composant");
        Tooltip lanTip = new Tooltip("Changer la langue");
        Tooltip blockTip = new Tooltip("Bloquer l'éspace de travaille");
        Tooltip hideTip = new Tooltip("Hide");

        utiliser.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Node node = (Node) t.getSource();
                placerTip.show(node, t.getScreenX() + 10, t.getScreenY() + 10);
            }
        });
        utiliser.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                placerTip.hide();
            }
        });

        langue.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Node node = (Node) t.getSource();
                lanTip.show(node, t.getScreenX() + 10, t.getScreenY() + 10);
            }
        });
        langue.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lanTip.hide();
            }
        });

        lock.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Node node = (Node) t.getSource();
                blockTip.show(node, t.getScreenX() - 50, t.getScreenY() - 50);
            }
        });
        lock.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                blockTip.hide();
            }
        });

        hide.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Node node = (Node) t.getSource();
                hideTip.show(node, t.getScreenX() - 10, t.getScreenY() - 10);
            }
        });
        hide.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hideTip.hide();
            }
        });

        new2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Node node = (Node) t.getSource();
                newTip.show(node, t.getScreenX() + 10, t.getScreenY() + 10);
            }
        });
        new2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newTip.hide();
            }
        });
        //----------
        open.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Node node = (Node) t.getSource();
                openTip.show(node, t.getScreenX() + 10, t.getScreenY() + 10);
            }
        });
        open.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                openProject();
            }
        });
        open.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openTip.hide();
            }
        });
        openModule.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Node node = (Node) t.getSource();
                open2Tip.show(node, t.getScreenX() + 10, t.getScreenY() + 10);
            }
        });
        openModule.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                open2Tip.hide();
            }
        });
        saveP.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Node node = (Node) t.getSource();
                saveTip.show(node, t.getScreenX() + 10, t.getScreenY() + 10);
            }
        });
        saveP.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveTip.hide();
            }
        });
        saveP.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                simpleSave();
            }
        });
        chrono.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Node node = (Node) t.getSource();
                chronoTip.show(node, t.getScreenX() + 10, t.getScreenY() + 10);
            }
        });
        chrono.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                chronoTip.hide();
            }
        });
        start.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Node node = (Node) t.getSource();
                playTip.show(node, t.getScreenX() + 10, t.getScreenY() + 10);
            }
        });
        start.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playTip.hide();
            }
        });
        stop.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Node node = (Node) t.getSource();
                stopTip.show(node, t.getScreenX() + 10, t.getScreenY() + 10);
            }
        });
        stop.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stopTip.hide();
            }
        });

    }

    private Tooltip mousePositionToolTip = new Tooltip("");


    void setWorkSpaceTip() {
        workSpace.addEventHandler(MouseEvent.MOUSE_MOVED, workSpaceEventTipHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String msg = "(x: " + event.getX() + ", y: " + event.getY() + ")\n(sceneX: "
                        + event.getSceneX() + ", sceneY: " + event.getSceneY() + ")\n(screenX: "
                        + event.getScreenX() + ", screenY: " + event.getScreenY() + ")";
                mousePositionToolTip.setText(msg);

                Node node = (Node) event.getSource();
                mousePositionToolTip.show(node, event.getScreenX() + 50, event.getScreenY());
            }
        });
    }

    private Tab startTabDesign = new Tab("::Start::");

    void startTab() {
        Text nou = new Text("Creer un nouveau projet");
        Text load = new Text("Charger un projet qui existe déja");
        Text right = new Text("ESI 2017 COPYRIGHT ©");
        nou.setFill(Color.LIGHTBLUE);
        load.setFill(Color.LIGHTBLUE);
        right.setFill(Color.LIGHTBLUE);
        nou.setX(480);
        nou.setY(350);
        load.setX(480);
        load.setY(400);
        right.setX(1000);
        right.setY(580);
        nou.setFont(javafx.scene.text.Font.font(null, FontWeight.MEDIUM, 18));
        load.setFont(javafx.scene.text.Font.font(null, FontWeight.MEDIUM, 18));
        right.setFont(javafx.scene.text.Font.font(null, FontWeight.EXTRA_LIGHT, 10));


        nou.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nou.setFont(javafx.scene.text.Font.font(null, FontWeight.EXTRA_BOLD, 18));
            }
        });
        nou.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nou.setFont(javafx.scene.text.Font.font(null, FontWeight.MEDIUM, 18));
            }
        });

        // action
        nou.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nouveauProjet();
            }
        });

        load.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                load.setFont(javafx.scene.text.Font.font(null, FontWeight.EXTRA_BOLD, 18));
            }
        });
        load.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                load.setFont(javafx.scene.text.Font.font(null, FontWeight.MEDIUM, 18));
            }
        });

        // action
        load.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openProject();
            }
        });

        Text t2 = new Text();
        t2.setCache(true);
        t2.setText("LogInI");
        t2.setFill(Color.GREENYELLOW);
        t2.setFont(javafx.scene.text.Font.font(null, FontWeight.BOLD, 150));
        t2.setEffect(new GaussianBlur());
        AnchorPane p = new AnchorPane();
        t2.setX(450);
        t2.setY(200);
        startTabDesign.setContent(p);
        p.getChildren().addAll(t2, nou, load, right);
        tabPane.getTabs().add(startTabDesign);
    }

    void nouveauProjet() {
        TextInputDialog dialog = new TextInputDialog(tabPane.getSelectionModel().getSelectedItem().getText());
        dialog.setTitle("Nouveau Projet");
        dialog.setHeaderText("Donner le nom de projet");
        dialog.setContentText("Faire entrer le nom ici:");

        dialog.getDialogPane().setStyle("-fx-control-inner-background:  rgb(60, 63, 65);" +
                "fx-accent: black;" +
                "-fx-background: rgb(60, 63, 65);");
        // Get the Stage.
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

// Add a custom icon.
        stage.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            projectName = result.get();
        }
        projectFullPath = getDerectoryPath();
        projectFullPath = projectFullPath + "\\" + projectName;
        createNewDerectory(projectFullPath);
        Main.stage.setTitle(projectName + " - " + "[" + projectFullPath + "]" + " - LogInI 2017.1");
        // ------------- start the work ---------------------
        enableButtons(false);
        stop.setDisable(true);
        tabPane.getTabs().remove(startTabDesign);
        projectTree.setDisable(false);
        split2.setDividerPosition(0, 0.25);
        split1.setDividerPosition(0, 0.75);
        projectTree.getRoot().setExpanded(true);
        Nouveau(null);
    }

    String getDerectoryPath() {
        // Derectory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File choosenDir = directoryChooser.showDialog(Main.stage);
        // return full path
        return choosenDir.toString();
    }

    void createNewDerectory(String path) {
        File theDir = new File(path);
        if (!theDir.exists()) theDir.mkdir();
        projectTree.getRoot().setValue(projectName);
    }

    void startProgramme() {
        split2.setDividerPosition(0, 0);
        split1.setDividerPosition(0, 1);
        projectTree.getRoot().getChildren().clear();
        tabPane.getTabs().clear();
        composantLogiqueList2.clear();
        noeudsList2.clear();
        filList.clear();
        projectTree.setDisable(true);
        enableButtons(true);
        startTab();
    }

    void enableButtons(boolean e) {
        start.setDisable(e);
        stop.setDisable(e);
        utiliser.setDisable(e);
        saveP.setDisable(e);
        new2.setDisable(e);
        open.setDisable(e);
        openModule.setDisable(e);
    }

    public void openProject() {


        projectFullPath = getDerectoryPath();
        projectName = projectFullPath.split("\\\\")[projectFullPath.split("\\\\").length - 1];
        Main.stage.setTitle(projectName + " - " + "[" + projectFullPath + "]" + " - LogInI 2017.1");
        enableButtons(false);
        stop.setDisable(true);
        tabPane.getTabs().remove(startTabDesign);
        projectTree.setDisable(false);
        File[] files = new File(projectFullPath).listFiles();

        composantLogiqueList2.clear();
        noeudsList2.clear();
        filList2.clear();
        tabPane.getTabs().clear();
        projectTree.getRoot().getChildren().clear();
        projectTree.getRoot().setValue(projectName);

        for (File file :
                files) {
            if (file.isFile()) {
                ArrayList<ComposantLogique> composantList = new ArrayList<ComposantLogique>();
                ArrayList<Noeud> nList = new ArrayList<Noeud>();
                ArrayList<Fil> fList = new ArrayList<Fil>();
                composantLogiquesList = composantList;
                noeudsList = nList;
                filList = fList;
                composantLogiqueList2.add(composantList);
                noeudsList2.add(nList);
                filList2.add(fList);
                //-----------tab control ------
                Nouveau(file.getName().split("\\.")[0]);
                //-----------project tree control -----
                //-----------------------------
                loadFile(file);
            }
        }

        split2.setDividerPosition(0, 0.25);
        split1.setDividerPosition(0, 0.75);
        projectTree.getRoot().setExpanded(true);
        projectTree.getRoot().setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("icons/dir2.png"))
        ));

        if (files.length == 0) {
            Text nou = new Text("Aucun Schéma n'apparait dans le projet !");
            Text creer = new Text("Creer un nouveau schéma de travail (Ctrl + N)");

            nou.setFill(Color.BLACK);
            nou.setX(450);
            nou.setY(200);
            nou.setFont(javafx.scene.text.Font.font(null, FontWeight.LIGHT, 19));

            creer.setFill(Color.LIGHTBLUE);
            creer.setX(450);
            creer.setY(280);
            creer.setFont(javafx.scene.text.Font.font(null, FontWeight.MEDIUM, 17));

            Tab emptyTab = new Tab("Projet vide");
            tabPane.getTabs().add(emptyTab);
            AnchorPane a = new AnchorPane(nou, creer);
            emptyTab.setContent(a);
            creer.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    creer.setFont(javafx.scene.text.Font.font(null, FontWeight.BOLD, 17));
                }
            });
            creer.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    tabPane.getTabs().clear();
                    f11.fire();
                }
            });
            creer.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    creer.setFont(javafx.scene.text.Font.font(null, FontWeight.MEDIUM, 17));
                }
            });
        }
    }

    public void simpleSave() {
        for (Tab i :
                tabPane.getTabs()) {
            composantLogiquesList = composantLogiqueList2.get(tabPane.getTabs().indexOf(i));
            noeudsList = noeudsList2.get(tabPane.getTabs().indexOf(i));
            filList = filList2.get(tabPane.getTabs().indexOf(i));
            File file = new File(projectFullPath + "\\" + i.getText() + ".bin");
            try {
                if (!file.exists()) file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveFile(file);
        }
        tabPane.getSelectionModel().select(tabPane.getTabs().size() - 1);
    }

    private Fil filOsay=null;

    void redefineLabelTOFROM() {
        for (ComposantLogique c:
                composantLogiquesList) {
            if(c.getClass() == Port.ToModule.class) {
                ((Port.ToModule)c).getId().setLayoutX(c.getPosXImageView() - 13);
                ((Port.ToModule)c).getId().setLayoutY(c.getPosYImageView() - 13);
            } else if(c.getClass() == Port.FromModule.class) {
                ((Port.FromModule)c).getId().setLayoutX(c.getPosXImageView() - 13);
                ((Port.FromModule)c).getId().setLayoutY(c.getPosYImageView() - 13);
            }
        }
    }

    void renameLabelsTOFROM(ComposantLogique c) {
        TextField t=new TextField();
        t.setLayoutX(buttonBar.getPrefWidth()/2);
        t.setLayoutY(0);
        if(c.getClass() == Port.ToModule.class) {
            t.setText(((Port.ToModule) c).getId().getText());
        } else {
            t.setText(((Port.FromModule) c).getId().getText());
        }
        buttonBar.getChildren().add(t);
        t.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) {
                    buttonBar.getChildren().remove(t);
                    if(c.getClass() == Port.ToModule.class) {
                        ((Port.ToModule) c).getId().setText(t.getText());
                    } else {
                        ((Port.FromModule) c).getId().setText(t.getText());
                    }
                }
            }
        });
    }

    @FXML
    public void initialize() {
        passageMenu.getItems().addAll(TO, FROM);
        supprimerFil.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                supprimerFil(filOsay);
            }
        });
        composantTree();
        item1.setSelected(true);
        split2.setDividerPosition(0, 0.25);
        split1.setDividerPosition(0, 0.75);


        setButtonTips();
        projectTree.setEditable(true);
        projectTree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                tabPane2 = tabPane;
                return new TextFieldTreeCellImpl();
            }
        });
        t_erreur.setCellValueFactory(new PropertyValueFactory<stat, String>("erreur"));
        type.setCellValueFactory(new PropertyValueFactory<stat, String>("type"));
        solution.setCellValueFactory(new PropertyValueFactory<stat, String>("solution"));
        /********************************************************/
        setTree();
        workSpace = (AnchorPane) ((ScrollPane) (tabPane.getTabs().get(0).getContent())).getContent();
        setWorkSpaceTip();
        //----------

        composantLogiqueList2.add(0, new ArrayList<ComposantLogique>());
        noeudsList2.add(0, new ArrayList<Noeud>());
        filList2.add(0, new ArrayList<Fil>());

        // endo redo controle
        MainWorkStackList.add(0, new Stack<WorkSpaceEvent>());
        SecondaryWorkStackList.add(0, new Stack<WorkSpaceEvent>());

        langue.setValue("FR");
        tabLogique();
        startProgramme();
        //- close events ---
        projectTree.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node rootIcon = null;
                if (projectTree.getRoot().isExpanded()) {
                    rootIcon = new ImageView(
                            new Image(getClass().getResourceAsStream("icons/dir2.png"))
                    );
                } else {
                    rootIcon = new ImageView(
                            new Image(getClass().getResourceAsStream("icons/dir1.png"))
                    );
                }
                projectTree.getRoot().setGraphic(rootIcon);
                if (event.getButton() == MouseButton.SECONDARY && event.getTarget() != projectTree.getRoot()) {
                    seeContextTree().show(Main.stage, event.getX(), event.getY());
                }
            }
        });

        projectTree.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    Node rootIcon = null;
                    if (projectTree.getRoot().isExpanded()) {
                        rootIcon = new ImageView(
                                new Image(getClass().getResourceAsStream("icons/dir2.png"))
                        );
                    } else {
                        rootIcon = new ImageView(
                                new Image(getClass().getResourceAsStream("icons/dir1.png"))
                        );
                    }
                    projectTree.getRoot().setGraphic(rootIcon);
                }
            }
        });

        // project Tree logique
        projectTree.getSelectionModel().selectedItemProperty().addListener((ov, oldItem, newItem) -> {
            if (newItem != projectTree.getRoot()) {
                for (Tab t :
                        tabPane.getTabs()) {
                    if (t.getText() == newItem.getValue()) {
                        if (!t.isDisable()) tabPane.getSelectionModel().select(t);
                        else t.setDisable(false);
                        break;
                    }
                }
            }

        });
        //------------------
        // tab pane logique
        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            for (Tab t :
                    tabPane.getTabs()) {
                t.setOnCloseRequest(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        t.setDisable(true);
                        tabPane.getSelectionModel().selectNext();
                        event.consume();
                    }
                });
            }

            for (TreeItem i :
                    projectTree.getRoot().getChildren()) {
                if (i.getValue() == newTab.getText()) {
                    projectTree.getSelectionModel().select(i);
                }
            }
            workSpace.removeEventHandler(MouseEvent.ANY, workSpaceEventHandler2);
            workSpace.removeEventHandler(MouseEvent.ANY, workSpaceEventHandler3);
            workSpace.removeEventHandler(MouseEvent.MOUSE_MOVED, workSpaceEventTipHandler);
            //workSpace.removeEventHandler(MouseEvent.ANY, creationDeFilEventHandler);
            workSpace = (AnchorPane) ((ScrollPane) (newTab.getContent())).getContent();
            workSpace.addEventHandler(MouseEvent.ANY, workSpaceEventHandler2);
            workSpace.addEventHandler(MouseEvent.ANY, workSpaceEventHandler3);
            if(mousePositionToolTip.isShowing()) workSpace.addEventHandler(MouseEvent.MOUSE_MOVED, workSpaceEventTipHandler);
            //workSpace.addEventHandler(MouseEvent.ANY, creationDeFilEventHandler);
            composantLogiquesList = composantLogiqueList2.get(tabPane.getSelectionModel().getSelectedIndex());
            noeudsList = noeudsList2.get(tabPane.getSelectionModel().getSelectedIndex());
            filList = filList2.get(tabPane.getSelectionModel().getSelectedIndex());

            // endo redo controle
            MainWorkStack = MainWorkStackList.get(tabPane.getSelectionModel().getSelectedIndex());
            SecondaryWorkStack = SecondaryWorkStackList.get(tabPane.getSelectionModel().getSelectedIndex());
        });
        utiliserLogique();
        backLogique();
        lockLogique();
        langue.setItems(langues);
        langue.setTooltip(new Tooltip("Selectionner la langue"));
        langue.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if (newValue.intValue() == 1) {
                    f1.setText("_File");
                    f2.setText("_Edition");
                    f3.setText("_Display");
                    f4.setText("Cla_ss");
                    f5.setText("Si_mulator");
                    f6.setText("_Signal");
                    langue.setTooltip(new Tooltip("Select the language"));
                    f11.setText("_New");// Nouveau
                    load.setText("_Open");//Ouvrir
                    f12.setText("Open _URL");//Ouvrir _URL
                    f13.setText("_Save");//Enre_gistrer
                    save.setText("Save __in");//Enregistrer _sous
                    f14.setText("_New window");//_Nouvelle Fenetre
                    fermer.setText("_Close");//fermer

                    f21.setText("C_ancel");//Annuler
                    undo.setText("_Undo");//undo
                    redo.setText("_Redo");//_Redo
                    f22.setText("_Cut");//_Couper
                    f23.setText("_Copy");//_Couper
                    f24.setText("_Paste");//_Coller
                    f25.setText("_Open component browser");//_Ouvrir le navigateur des composants
                    nommerSchema.setText("_Name the _Schema");//_Nommer le _Schéma
                    f26.setText("_Rotation of the object");//_Rotation objet
                    f27.setText("_Delete Object");//_Supprimer objet
                    f28.setText("Delete _All");//Supprimer _Tout
                    zoomPlus.setText("_Zoom in");//_Zoom avant
                    zoomMiness.setText("_Zoom out");//_Zoom arrière
                    f31.setText("_Space of work");//_Espace de travaille
                    pleinEcran.setText("_Full screen mode");//_Mode plein écran
                    f32.setText("Screen Mode (_Theme)");//Mode d'écran (_Theme)
                    f33.setText("_Light (Snow white)");//_Light (Snow white)
                    f34.setText("_Dark (Dracula)");//_Dark (Dracula)
                    f41.setText("_Neon portes");//_Neon portes
                    f42.setText("Classe des _Instants");//Classe des _Instants
                    f43.setText("Classe des _Types");//Classe des _Types
                    f44.setText("Classe des _Portes");//
                    demarrer.setText("_Start...");//_Démarrer...
                    arreter.setText("St_op");//_Arrêter
                    pause.setText("P_ause");//P_ause
                    resumer.setText("Resu_me");//Resu_mer
                    redemarrer.setText("_Restart");//_Redémarrer
                    configuration.setText("Confi_guration");//Confi_guration
                    historyAccelerator.setText("_Open Process Management");//_Ouvrir la gestion des processus
                    pos1 = "Position";//position
                    historique.setText("Historical");//Historique
                    statique.setText("Statistical ");//Statique
                    lSelection.setText("Click to stop selection");

                    selection = "Select";
                    edit = "Edition";
                    sup = "Delete";
                    cop = "Copy";
                    coup = "Cut";
                    coll = "Paste";
                    crr = "Create";
                    mod = "Modify";
                    ariplan = "bring to the foreground";
                    preplan = "Put in the background";
                    aff = "D)isplay?";
                    param = "Parameter";
                    copier.setText(cop);
                    couper.setText(coup);
                    premierPlan.setText(preplan);
                    arrierPlan.setText(ariplan);
                    modifier.setText(mod);
                    supp.setText(sup);
                    afficher.setText(aff);
                    item0.setText(sup);
                    item1.setText("Position tip");
                    item2.setText(edit);
                    item3.setText(cop);
                    item33.setText(coup);
                    item4.setText(coll);
                    itemMain2.setText(param);
                    createMenu.setText(crr);

                } else if (newValue.intValue() == 2) {
                    f1.setText("_الملف");
                    f2.setText("_طبعة");
                    f4.setText("_فئة");
                    f3.setText("ع_رض");
                    f5.setText("_محاكاة");
                    f6.setText("_إشارة");
                    langue.setTooltip(new Tooltip("إختر اللفة    "));
                    f11.setText("_جديد");// Nouveau
                    load.setText("_فتح");//Ouvrir
                    f12.setText("فتح _من");//Ouvrir _URL
                    f13.setText("_حفظ");//Enre_gistrer
                    save.setText("حفظ _في ");//Enregistrer _sous
                    f14.setText("نافذة _جديدة");//_Nouvelle Fenetre
                    fermer.setText("إ_غلاق");//fermer
                    f21.setText("إلفاء");//Annuler
                    undo.setText("إرجا_ع");//undo
                    redo.setText("تق_دم");//_Redo
                    f22.setText("_قص");//_Couper
                    f23.setText("نسخ_");//_Copier
                    f24.setText("_لصق");//_Coller
                    f25.setText("_فتح مكونات متصفح");//_Ouvrir le navigateur des composants
                    nommerSchema.setText("_تسمية مخطط");//_Nommer le _Schéma
                    f26.setText("_إستدارة الجسم");//_Rotation objet
                    f27.setText("_مسح الجسم");//_Supprimer objet
                    f28.setText("مسح _الكل");//Supprimer _Tout
                    zoomPlus.setText("_تكبير");//_Zoom avant
                    zoomMiness.setText("_تصغير");//_Zoom arrière
                    f31.setText("_مساحة العمل");//_Espace de travaille
                    pleinEcran.setText("_وضع ملء الشاشة");//_Mode plein écran
                    f32.setText("وضع الشا_شة");//Mode d'écran (_Theme)
                    f33.setText("_مضيىء");//_Light (Snow white)
                    f34.setText("_مظلم");//_Dark (Dracula)
                    f41.setText("_Neon portes");//_Neon portes
                    f42.setText("Classe des _Instants");//Classe des _Instants
                    f43.setText("Classe des _Types");//Classe des _Types
                    f44.setText("Classe des _Portes");//
                    demarrer.setText("_تشغيل...");//_Démarrer...
                    arreter.setText("_توقف");//_Arrêter
                    pause.setText("وق_فة");//P_ause
                    resumer.setText("تا_بع");//Resu_mer
                    redemarrer.setText("_إعادة تشغيل");//_Redémarrer
                    configuration.setText("توزيع");//Confi_guration
                    historyAccelerator.setText("_فتح إدارة العمليات");//_Ouvrir la gestion des processus
                    pos1 = "وضعية  : ";//position

                    historique.setText("تاريخي");//Historique
                    statique.setText("إحصائيات ");//Statique
                    lSelection.setText("انقر لوقف اختيار");
                    selection = "حدد";
                    edit = "تحرير";
                    sup = "حذف";
                    cop = "نسغ";
                    coup = "قص";
                    coll = "لصق";
                    crr = "إنشاء";
                    mod = "تغيير";
                    ariplan = "إحضار إلى المقدمة";
                    preplan = "إرسال إلى الخلف";
                    aff = "عرض ?";
                    param = "معلمة";

                    copier.setText(cop);
                    couper.setText(coup);
                    premierPlan.setText(preplan);
                    arrierPlan.setText(ariplan);
                    modifier.setText(mod);
                    supp.setText(sup);
                    afficher.setText(aff);
                    item0.setText(sup);
                    item1.setText("Position tip");
                    item2.setText(edit);
                    item3.setText(cop);
                    item33.setText(coup);
                    item4.setText(coll);
                    itemMain2.setText(param);
                    createMenu.setText(crr);
                } else {
                    f1.setText("_Fichier");
                    f2.setText("_Edition");
                    f3.setText("_Affichage");
                    f4.setText("Cla_sse");
                    f5.setText("Si_mulateur");
                    f6.setText("_Signal");
                    langue.setTooltip(new Tooltip("Selectionner la langue"));
                    f11.setText("_Nouveau");// Nouveau
                    load.setText("_Ouvrir");//Ouvrir
                    f12.setText("Ouvrir _URL");//Ouvrir _URL
                    f13.setText("Enre_gistrer");//Enre_gistrer
                    save.setText("Enregistrer _sous");//Enregistrer _sous
                    f14.setText("_Nouvelle fenêtre");//_Nouvelle Fenetre
                    fermer.setText("_Fermer");//fermer
                    f21.setText("_Annuler");//Annuler
                    undo.setText("_Undo");//undo
                    redo.setText("_Redo");//_Redo
                    f22.setText("_Couper");//_Couper
                    f23.setText("_Copier");//_Copier
                    f24.setText("_Coller");//_Coller
                    f25.setText("_Ouvrir le navigateur des composants");//_Ouvrir le navigateur des composants
                    nommerSchema.setText("_Nommer le _Schéma");//_Nommer le _Schéma
                    f26.setText("_Rotation objet");//_Rotation objet
                    f27.setText("_Supprimer objet");//_Supprimer objet
                    f28.setText("Supprimer _Tout");//Supprimer _Tout
                    zoomPlus.setText("_Zoom avant");//_Zoom avant
                    zoomMiness.setText("_Zoom arrière");//_Zoom arrière
                    f31.setText("_Espace de travaille");//_Espace de travaille
                    pleinEcran.setText("_Mode plein écran");//_Mode plein écran
                    f32.setText("Mode d'écran (_Theme)");//Mode d'écran (_Theme)
                    f33.setText("_Light (Snow white)");//_Light (Snow white)
                    f34.setText("_Dark (Dracula)");//_Dark (Dracula)
                    f41.setText("_Neon portes");//_Neon portes
                    f42.setText("Classe des _Instants");//Classe des _Instants
                    f43.setText("Classe des _Types");//Classe des _Types
                    f44.setText("Classe des _Portes");//
                    demarrer.setText("_Démarrer...");//_Démarrer...
                    arreter.setText("_Arrêter");//_Arrêter
                    pause.setText("P_ause");//P_ause
                    resumer.setText("Resu_mer");//Resu_mer
                    redemarrer.setText("_Redémarrer");//_Redémarrer
                    configuration.setText("Confi_guration");//Confi_guration
                    historyAccelerator.setText("_Ouvrir la gestion des processus");//_Ouvrir la gestion des processus
                    pos1 = "Position";//position
                    historique.setText("Historique");//Historique
                    statique.setText("Statistique ");//Statique
                    lSelection.setText("Cliquez pour arrêter la sélection");

                    selection = "Selectionner";
                    edit = "Edition";
                    sup = "Supprimer";
                    cop = "Copier";
                    coup = "Couper";
                    coll = "Coller";
                    crr = "Créer";
                    mod = "Modifier";
                    ariplan = "Mettre au premier plan";
                    preplan = "Mettre en arriére plan";
                    aff = "A)fficher ?";
                    param = "Paramétre";
                    copier.setText(cop);
                    couper.setText(coup);
                    premierPlan.setText(preplan);
                    arrierPlan.setText(ariplan);
                    modifier.setText(mod);
                    supp.setText(sup);
                    afficher.setText(aff);
                    item0.setText(sup);
                    item1.setText("Position tip");
                    item2.setText(edit);
                    item3.setText(cop);
                    item33.setText(coup);
                    item4.setText(coll);
                    itemMain2.setText(param);
                    createMenu.setText(crr);

                }

            }
        });

        chrono.setOnAction((e) -> {
            try {
                chronogramme(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        arb.setOnAction((e) -> {
            try {
                Arbre(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        drawRulers(workSpace);
        lineHelpers();
        new RubberBandSelection(workSpace);
        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Chargement d'éspace de travail", "En cours ..."));
        System.out.println("Controller 1 lunched ...");
        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Chargement d'éspace de travail avec succès", "Terminer."));

        // add line of moving
        //workSpace.getChildren().add(l);
        setSimulationMenuItemLogique2();
        setSimulationMenuLogique();
        workSpace.setFocusTraversable(true);
        // pstpst
        loadScreen();

        historyAccelerator.setOnAction((e) -> {
            try {
                history(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        redo.setOnAction((e) -> {
            if (!SecondaryWorkStack.isEmpty()) {
                WorkSpaceEvent event = new WorkSpaceEvent();
                event = SecondaryWorkStack.pop();
                workSpace.getChildren().add(event.getComposant().getImageView());
            }
        });

        f25.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    arb.fire();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        undo.setOnAction((e) -> {
            if (!MainWorkStack.isEmpty()) {
                WorkSpaceEvent event = new WorkSpaceEvent();
                event = MainWorkStack.pop();
                SecondaryWorkStack.push(event);
                switch (event.getTypeEvent()) {
                    case CREATION:
                        switch (event.getSpecifier()) {
                            case COMPOSANT:
                                supprimerComposant(event.getComposant());
                            case FIL:
                                supprimerFil(event.getFil());
                            case NOEUD:
                                //après...
                        }
                        break;
                    case MODIFICATION:
                        break;
                    case MOUVEMENT:
                        switch (event.getSpecifier()) {
                            case COMPOSANT:
                                event.getComposant().getImageView().setLayoutX(event.getComposant().getImageView().getLayoutX() - event.getDeplacementX());
                                event.getComposant().getImageView().setLayoutY(event.getComposant().getImageView().getLayoutY() - event.getDeplacementY());
                                redefineComposantLogique(event.getComposant(), -event.getDeplacementX(), -event.getDeplacementY());
                                redefineFil();
                                redefineLabelTOFROM();
                                break;
                            case FIL:
                                // a plus tard
                                break;
                            case NOEUD:
                                // a plus tard
                                break;
                        }
                        break;
                    case SUPRESSION:
                        break;
                }
            }
        });

        // come here baby

        //creer un nouveau espace de travaille
        f11.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Nouveau(null);
            }
        });

        f44.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (f44.isSelected()) {
                    setLabels();
                } else {
                    removeLabels();
                }
            }
        });


        f41.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (f41.isSelected()) {
                    setNeonComposant();
                } else {
                    removeNeonComposant();
                }
            }
        });

        f43.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (f43.isSelected()) {
                    setLabelsComposant();
                } else {
                    removeLabelsComposant();
                }
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simpleSave();
            }
        });
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openProject();
            }
        });

        workSpaceHeight = workSpace.getPrefHeight();
        workSpaceWidth = workSpace.getPrefWidth();
        gridDrowPointesLongue(workSpace);

                /*---------------------- Menu Items -----------------------------*/
      /*  MenuItem item1=new MenuItem("sélectionner");
        MenuItem item2=new MenuItem("Edition");
        MenuItem item0=new MenuItem("Supprimer");
        MenuItem item3=new MenuItem("Copier");
        MenuItem item33=new MenuItem("Couper");
        */
        item33.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        item3.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        //MenuItem item4=new MenuItem("Coller");
        try {
            item1.setGraphic(new ImageView(new Image(getClass().getResource("icons/selec3.png").toURI().toString())));
            item2.setGraphic(new ImageView(new Image(getClass().getResource("icons/modifier.png").toURI().toString())));
            item0.setGraphic(new ImageView(new Image(getClass().getResource("icons/supprimer3.png").toURI().toString())));
            item3.setGraphic(new ImageView(new Image(getClass().getResource("icons/copy.png").toURI().toString())));
            item33.setGraphic(new ImageView(new Image(getClass().getResource("icons/cut.png").toURI().toString())));

            item4.setGraphic(new ImageView(new Image(getClass().getResource("icons/paste.png").toURI().toString())));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        item4.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        //MainMenu Items
        item4.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        //    MenuItem itemMain2=new MenuItem("Paramètres");
        //Menu createMenu=new Menu("Créer");
        Menu createMenucomb = new Menu("Combinatoire");
        Menu createMenucomb2 = new Menu("Combinatoire 2");

        Menu createMenuporte = new Menu("porte loqique");
        Menu createMenuseq = new Menu("Sequentiel");
        /*********************************************************/
        /*************** YOUNES AJOUTER ************************/
        MenuItem createMenu7 = new MenuItem("dec2_4");
        MenuItem createMenu10 = new MenuItem("dec3_8");
        MenuItem createMenu8 = new MenuItem("mux4");
        MenuItem createMenu11 = new MenuItem("demux4");
        MenuItem createMenu12 = new MenuItem("demux8");
        MenuItem createMenu9 = new MenuItem("mux8");
        /********************************************************/
        /**********************************************************/
        MenuItem ET2 = new MenuItem("ET 2");
        MenuItem ET3 = new MenuItem("ET 3");
        MenuItem ET4 = new MenuItem("ET 4");
        MenuItem nonET2 = new MenuItem("NON-ET 2");
        MenuItem nonET3 = new MenuItem("NON-ET 3");
        MenuItem nonET4 = new MenuItem("NON-ET 4");
        MenuItem OU2 = new MenuItem("OU 2");
        MenuItem OU3 = new MenuItem("OU 3");
        MenuItem OU4 = new MenuItem("OU 4");
        MenuItem nonOU2 = new MenuItem("NON-OU 2");
        MenuItem nonOU3 = new MenuItem("NON-OU 3");
        MenuItem nonOU4 = new MenuItem("NON-OU 4");
        MenuItem XOR2 = new MenuItem("XOR 2");

        MenuItem inverseur = new MenuItem("Inverseur");
        MenuItem input = new MenuItem("Switch");
        MenuItem output = new MenuItem("LED");
        createMenuporte.getItems().addAll(ET2, ET3, ET4, nonET2,
                nonET3, nonET4, OU2, OU3, OU4, nonOU2, nonOU3, nonOU4, XOR2,
                new SeparatorMenuItem(), inverseur, input, output);


/**************************** AJOUTE PAR KARIM ***********************************************/
        //MenuItem createMenu10=new MenuItem("XOR 2");
        MenuItem createMenuKarim11 = new MenuItem("Demi additionneur");
        MenuItem createMenuKarim12 = new MenuItem("ADDC1");
        MenuItem createMenuKarim13 = new MenuItem("ADDC2");
        MenuItem createMenuKarim14 = new MenuItem("ADDC4");
        MenuItem createMenuKarim15 = new MenuItem("Demi soustracteur");
        MenuItem createMenuKarim16 = new MenuItem("Cmp4");
        MenuItem createMenuKarim17 = new MenuItem("Encod4_2");
        MenuItem createMenuKarim18 = new MenuItem("Encod8_3");

        //MenuItem createMenu15=new MenuItem("Dec2_4");
        /**********************************************************************************************/

        /***********************************************************/
        MenuItem MJK = new MenuItem("JK");
        MenuItem MJKasy = new MenuItem("JKasy");
        MenuItem MRS = new MenuItem("RS");
        MenuItem MT = new MenuItem("T");
        MenuItem MREG4 = new MenuItem("REG 4 bit");
        MenuItem MREG8 = new MenuItem("REG 8 bit");
        MenuItem MDlatch = new MenuItem("D latch");
        MenuItem MD = new MenuItem("D");
        MenuItem MRST = new MenuItem("RST");
        MenuItem tristate = new MenuItem("Tristate");
        MenuItem horloge = new MenuItem("Horloge");
        MenuItem COP4 = new MenuItem("Compteur 4bit");
        MenuItem COP8 = new MenuItem("Compteur 8bit");
        MenuItem REGD4 = new MenuItem("Registre à décalage 4bit");
        MenuItem REGD8 = new MenuItem("Registre à décalage 8bit");


        try {
            inverseur.setGraphic(new ImageView(new Image(getClass().getResource("icons/inv.png").toURI().toString())));
            input.setGraphic(new ImageView(new Image(getClass().getResource("icons/switch.png").toURI().toString())));
            output.setGraphic(new ImageView(new Image(getClass().getResource("icons/led.png").toURI().toString())));
            ET2.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            nonET2.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            nonET3.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            ET3.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            nonOU2.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            nonOU3.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            OU3.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));

            /*********************************************************************************************************************/
            /**************************************  YOUNES AJOUTER          *****************************************************/
            /*********************************************************************************************************************/
            XOR2.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            createMenu10.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            createMenu11.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            createMenu12.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            createMenu7.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            createMenu8.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            createMenu9.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            /*********************************************************************************************************************/
            /*********************************************************************************************************************/
            OU2.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            itemMain2.setGraphic(new ImageView(new Image(getClass().getResource("icons/config2.png").toURI().toString())));
            createMenu.setGraphic(new ImageView(new Image(getClass().getResource("icons/creer2.png").toURI().toString())));
            createMenucomb.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            createMenucomb2.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            createMenuporte.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));
            createMenuseq.setGraphic(new ImageView(new Image(getClass().getResource("icons/et.png").toURI().toString())));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        /****************************************** YOUNES AJOUTER ***************************************************************/
        /****************   JAI ajouter dans cette ligne que qlq parameretre  pas toutes la ligne *********************************/
        MenuItem afficheur = new MenuItem("Afficheur 7 segments ");
        createMenucomb.getItems().addAll(afficheur, createMenu10, createMenu11, createMenu12, createMenu7, createMenu8, createMenu9);
        createMenucomb2.getItems().addAll(createMenuKarim11, createMenuKarim12, createMenuKarim13, createMenuKarim14, createMenuKarim15, createMenuKarim16, createMenuKarim17, createMenuKarim18);
        createMenuseq.getItems().addAll(horloge, MJK, MJKasy, MRS, MRST, MT, MD, MDlatch, MREG8, MREG4, COP4, COP8, REGD4, REGD8);
        createMenu.getItems().addAll(createMenucomb, createMenucomb2, createMenuporte, createMenuseq);
        /*********************************************************************************************************************/
        //Add items to Context Menu
        contextMainPane.getItems().addAll(item1, createMenu, passageMenu, new SeparatorMenuItem(), item4, item0, new SeparatorMenuItem(),
                item2);
        //Add items to main context
        //---------------------------------------ELEMENT Context Menu----------------------------------------------
       /* MenuItem copier=new MenuItem("Copier");
        MenuItem couper=new MenuItem("Couper");
        MenuItem premierPlan=new MenuItem("Mettre au premier plan");
        MenuItem arrierPlan=new MenuItem("Mettre à l'arriére plan");
        MenuItem modifier=new MenuItem("Modifier");
        MenuItem supp=new MenuItem("Supprimer");
        MenuItem select=new MenuItem("Selection");
*/

        //   CheckMenuItem afficher=new CheckMenuItem("A)fficher?");
        try {
            premierPlan.setGraphic(new ImageView(new Image(getClass().getResource("icons/premierPlan.png").toURI().toString())));
            arrierPlan.setGraphic(new ImageView(new Image(getClass().getResource("icons/arrierePlan.png").toURI().toString())));
            modifier.setGraphic(new ImageView(new Image(getClass().getResource("icons/modifier.png").toURI().toString())));
            supp.setGraphic(new ImageView(new Image(getClass().getResource("icons/supprimer3.png").toURI().toString())));
            copier.setGraphic(new ImageView(new Image(getClass().getResource("icons/copy.png").toURI().toString())));
            couper.setGraphic(new ImageView(new Image(getClass().getResource("icons/cut.png").toURI().toString())));
            rotd.setGraphic(new ImageView(new Image(getClass().getResource("icons/rotation+90.png").toURI().toString())));
            rotg.setGraphic(new ImageView(new Image(getClass().getResource("icons/rotation-90.png").toURI().toString())));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        context.getItems().addAll(copier, couper, new SeparatorMenuItem(), premierPlan, arrierPlan, supp, modifier, rotd, rotg);
        //-------------------------------------------------------------------------------------
        //come here
        /*-------------------------------------------------------------------*/


        fermer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exitAction();
            }
        });

        nommerSchema.setOnAction((e) -> {
            // your code here ...
            TextInputDialog dialog = new TextInputDialog(tabPane.getSelectionModel().getSelectedItem().getText());
            dialog.setTitle("Nommer");
            dialog.setHeaderText("Donner un nom pour le Schéma");
            dialog.setContentText("Faire entrer le nom ici:");

            dialog.getDialogPane().setStyle("-fx-control-inner-background:  rgb(60, 63, 65);" +
                    "fx-accent: black;" +
                    "-fx-background: rgb(60, 63, 65);");
            // Get the Stage.
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

// Add a custom icon.
            stage.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                tabPane.getSelectionModel().getSelectedItem().setText(result.get());
                projectTree.getSelectionModel().getSelectedItem().setValue(result.get());
            }
        });

        pleinEcran.setAccelerator(KeyCombination.keyCombination("F11"));
        pleinEcran.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.stage.setFullScreen((pleinEcran.isSelected()) ? true : false);
            }
        });
        grillePointilles2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearPane(workSpace);
                gridDrowPointesCourte();
                drawRulers(workSpace);
                grillePointilles1.setSelected(false);
                grilleContinue.setSelected(false);
            }
        });
        grillePointilles1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearPane(workSpace);
                gridDrowPointesLongue(workSpace);
                drawRulers(workSpace);
                grillePointilles2.setSelected(false);
                grilleContinue.setSelected(false);
            }
        });
        grilleContinue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearPane(workSpace);
                gridDrowContinue(workSpace);
                drawRulers(workSpace);
                grillePointilles2.setSelected(false);
                grillePointilles1.setSelected(false);
            }
        });

        item0.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                workSpace.getChildren().remove(item0.getParentPopup().getOwnerNode());
                for (ComposantLogique c :
                        composantLogiquesList) {
                    if (item0.getParentPopup().getOwnerNode() == c.getImageView()) {
                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Supprimer la Porte " + c, "Terminer."));
                        for (Noeud n :
                                c.getNoeudEntrees()) {
                            workSpace.getChildren().remove(n.getCircle());
                        }
                        for (Branche b :
                                c.getBrancheSortie()) {
                            workSpace.getChildren().remove(b.getNoeud().getCircle());
                        }
                        //workSpace.getChildren().remove(c.getNoeudSortie().getCircle());
                        noeudsList.remove(c.getNoeudSortie());
                        noeudsList.removeAll(c.getNoeudEntrees());
                    }
                }
                composantLogiquesList.remove(item0.getParentPopup().getOwnerNode());
            }
        });

        item1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (!item1.isSelected()) {
                    mousePositionToolTip.hide();
                    workSpace.removeEventHandler(MouseEvent.MOUSE_MOVED, workSpaceEventTipHandler);
                } else {
                    workSpace.addEventHandler(MouseEvent.MOUSE_MOVED, workSpaceEventTipHandler);
                }
            }
        });

        item4.setDisable(true);
        copier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (cut && v != null) {
                    workSpace.getChildren().add(v.getImageView());
                    for (Branche n :
                            v.getBrancheEntree()) {
                        workSpace.getChildren().add(n.getNoeud().getCircle());
                    }
                    for (Branche n :
                            v.getBrancheSortie()) {
                        workSpace.getChildren().add(n.getNoeud().getCircle());
                    }
                    v = null;
                }
                copy = true;
                cut = false;
                for (ComposantLogique c :
                        composantLogiquesList) {
                    if (copier.getParentPopup().getOwnerNode() == c.getImageView()) {
                        clss = c.getClass().getSimpleName();
                        item4.setDisable(false);
                    }
                }
            }
        });
        couper.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cut = true;
                clss = null;
                copy = false;
                if (v != null) {
                    workSpace.getChildren().add(v.getImageView());
                    for (Branche n :
                            v.getBrancheEntree()) {
                        workSpace.getChildren().add(n.getNoeud().getCircle());
                    }
                    for (Branche n :
                            v.getBrancheSortie()) {
                        workSpace.getChildren().add(n.getNoeud().getCircle());
                    }
                    v = null;
                }
                for (ComposantLogique c :
                        composantLogiquesList) {
                    if (c.getImageView() == couper.getParentPopup().getOwnerNode()) {
                        workSpace.getChildren().remove(c.getImageView());
                        for (Branche n :
                                c.getBrancheEntree()) {
                            workSpace.getChildren().remove(n.getNoeud().getCircle());
                        }
                        for (Branche n :
                                c.getBrancheSortie()) {
                            workSpace.getChildren().remove(n.getNoeud().getCircle());
                        }
                        v = c;
                        item4.setDisable(false);
                    }
                }
            }
        });


        //Set Context Menu to a Control
        //Come here ...
        tabPane.addEventHandler(MouseEvent.ANY, workSpaceEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TableViewLogique();
                mousePosX = event.getScreenX();
                mousePosY = event.getScreenY();
                position(event.getX(), event.getY());
                if (isSelect) {
                    workSpace.getChildren().remove(lSelection);
                    lSelection.setLayoutX(event.getX());
                    lSelection.setLayoutY(event.getY());
                    workSpace.getChildren().add(lSelection);
                }

                //------------------------- Node control ------------------------
                for (Noeud n :
                        noeudsList) {
                    if (event.getTarget().equals(n.getCircle())) {
                        n.getCircle().setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (n.getCircle().getRadius() != 5) {
                                    n.getCircle().setRadius(5);
                                }
                                workSpace.setCursor(Cursor.HAND);
                            }
                        });

                        n.getCircle().setOnMouseExited(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (n.getCircle().getRadius() != 3) {
                                    n.getCircle().setRadius(3);
                                }
                                //System.out.println("Mouse out of node");
                                workSpace.setCursor(Cursor.DEFAULT);
                            }
                        });

                        n.getCircle().setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (!creationEnCourFil) {
                                    creationEnCourFil = true;
                                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creer un fil à partir de " + n, "En cours ..."));
                                    //System.out.print("This is a line ...");
                                    Fil line = new Fil();
                                    line.setNoeudDebut(n);
                                    WorkSpaceEvent item = new WorkSpaceEvent();
                                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.FIL);
                                    item.setFil(line);
                                    MainWorkStack.push(item);
                                    filList.add(line);
                                    filGlobale = line;
                                    for (ComposantLogique c :
                                            composantLogiquesList) {
                                        for (Branche b :
                                                c.getBrancheEntree()) {
                                            if (n == b.getNoeud()) {
                                                b.getNoeud().setRecepteurComp(c);
                                                b.getNoeud().setReceptionEntree(b.getNumeroDeBranche());
                                                b.getNoeud().setEmetteurFil(filGlobale);
                                                //Some code here
                                                noeudsList.remove(n);
                                                noeudsList.add(b.getNoeud());
                                                recepteurActif = true;
                                            }
                                        }
                                        for (Branche b :
                                                c.getBrancheSortie()) {
                                            if (n == b.getNoeud()) {
                                                emetteurActif = true;
                                                b.getNoeud().setEmetteurComp(c);
                                                b.getNoeud().setEmessionSortie(b.getNumeroDeBranche());
                                                b.getNoeud().setRecepteurFil(filGlobale);
                                                noeudsList.remove(n);
                                                noeudsList.add(b.getNoeud());
                                            }
                                        }
                                    }
                                    line.getFil().setStyle("-fx-stroke-width: 2.2px;");
                                    MoveTo debut = new MoveTo();
                                    debut.setX(n.getCircle().getCenterX());
                                    debut.setY(n.getCircle().getCenterY());
                                    line.addMoveTo(debut);
                                    //line.getFil().setLayoutX(n.getCircle().getCenterX());
                                    //line.getFil().setLayoutY(n.getCircle().getCenterY());
                                    //line.getFil().setStartX(0.0);
                                    //line.getFil().setStartY(0.0);
                                    line.getFil().setStroke(YELLOW);
                                    // path settings
                                    HLineTo hFile = new HLineTo();
                                    hFile.setX(event.getX() + 10);
                                    line.addFilHorisontal(hFile);
                                    line.getFil().getElements().addAll(debut, hFile);
                                    // -------------
                                    workSpace.getChildren().add(line.getFil());
                                    workSpace.addEventHandler(MouseEvent.ANY, creationDeFilEventHandler = new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent e) {
                                            if (e.getEventType() == MouseEvent.MOUSE_MOVED && line.getFil().getStroke().equals(YELLOW)) {
                                                //line.getFil().setEndX(e.getX() - 5 - line.getFil().getLayoutX());
                                                //line.getFil().setEndY(e.getY() - 5 - line.getFil().getLayoutY());

                                                if (line.getFil().getElements().get(line.getFil().getElements().size() - 1).getClass() == HLineTo.class) {
                                                    line.getFilHorisontal().get(line.getFilHorisontal().size() - 1).setX(e.getX() - 3);
                                                } else {
                                                    line.getFilVertical().get(line.getFilVertical().size() - 1).setY(e.getY() - 3);
                                                }
                                            } else if (e.getEventType() == MouseEvent.MOUSE_PRESSED && e.getButton() == MouseButton.PRIMARY && line.getFil().getStroke().equals(YELLOW) && !(e.getTarget().getClass().equals(Circle.class))) {
                                                if (line.getFil().getElements().get(line.getFil().getElements().size() - 1).getClass() == HLineTo.class) {
                                                    VLineTo vFil = new VLineTo();
                                                    vFil.setY(e.getY() + 10);
                                                    line.getFil().getElements().addAll(vFil);
                                                    line.addFilVertical(vFil);
                                                } else if (line.getFil().getElements().get(line.getFil().getElements().size() - 1).getClass() == VLineTo.class) {
                                                    HLineTo hFil = new HLineTo();
                                                    hFil.setX(e.getX() + 2);
                                                    line.getFil().getElements().addAll(hFil);
                                                    line.addFilHorisontal(hFil);
                                                }
                                            } else if (e.getButton() == MouseButton.SECONDARY && line.getFil().getStroke().equals(YELLOW)) {
                                                supprimerFil(line);
                                                creationEnCourFil = false;
                                                recepteurActif = false;
                                                emetteurActif = false;
                                            }
                                        }
                                    });
                                    //workSpace.removeEventHandler(MouseEvent.ANY, creationDeFilEventHandler);
                                } else {
                                    //fil est en cour de creation
                                    System.out.println("**Fil est en cour de creation.");
                                    filGlobale.setNoeudFin(n);
                                    for (ComposantLogique c :
                                            composantLogiquesList) {

                                        for (Branche b :
                                                c.getBrancheSortie()) {
                                            if (n == b.getNoeud()) {
                                                //c.getBrancheSortie().getNoeud().setEmetteurComp(c);
                                                //c.getBrancheSortie().getNoeud().setRecepteurFil(filGlobale);
                                                System.out.println(b.getNumeroDeBranche());
                                                b.getNoeud().setEmetteurComp(c);
                                                b.getNoeud().setEmessionSortie(b.getNumeroDeBranche());
                                                b.getNoeud().setRecepteurFil(filGlobale);
                                                //Some code here
                                                noeudsList.remove(b.getNoeud());
                                                noeudsList.add(b.getNoeud());
                                                System.out.println("EMETTEUR : " + c);
                                            }
                                        }
                                        for (Branche b :
                                                c.getBrancheEntree()) {
                                            if (n == b.getNoeud()) {
                                                System.out.print("RECEPTEUR : " + c);
                                                System.out.println(b.getNumeroDeBranche());
                                                b.getNoeud().setRecepteurComp(c);
                                                b.getNoeud().setReceptionEntree(b.getNumeroDeBranche());
                                                b.getNoeud().setEmetteurFil(filGlobale);
                                                //Some code here
                                                noeudsList.remove(n);
                                                noeudsList.add(b.getNoeud());
                                            }
                                        }
                                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation complete du fil avec succès", "Terminer."));
                                        workSpace.setCursor(Cursor.DEFAULT);
                                        // path settings
                                        MoveTo fin = new MoveTo();
                                        if (filGlobale.getFil().getElements().get(filGlobale.getFil().getElements().size() - 1).getClass() == HLineTo.class) {
                                            if (filGlobale.getFil().getElements().size() == 2) {
                                                //بعدين ان شاء الله
                                                filGlobale.getFilHorisontal().get(0).setX(filGlobale.getMoveTo().get(0).getX() + (n.getCircle().getCenterX() - filGlobale.getMoveTo().get(0).getX()) / 2);
                                                VLineTo v = new VLineTo();
                                                HLineTo h = new HLineTo();
                                                filGlobale.addFilHorisontal(h);
                                                filGlobale.addFilVertical(v);
                                                filGlobale.getFil().getElements().addAll(v, h);

                                                v.setY(n.getCircle().getCenterY());
                                                h.setX(n.getCircle().getCenterX());
                                            } else {
                                                filGlobale.getFilVertical().get(filGlobale.getFilVertical().size() - 1).setY(n.getCircle().getCenterY());
                                            }
                                        } else if (filGlobale.getFil().getElements().get(filGlobale.getFil().getElements().size() - 1).getClass() == VLineTo.class) {
                                            if (filGlobale.getFil().getElements().size() == 2) {
                                                //بعدين
                                                System.out.println("بعدين");
                                            } else {
                                                HLineTo h = new HLineTo();
                                                filGlobale.addFilHorisontal(h);
                                                filGlobale.getFil().getElements().add(h);
                                                h.setX(n.getCircle().getCenterX());
                                                //filGlobale.getFilHorisontal().get(filGlobale.getFilHorisontal().size() - 1).setX(n.getCircle().getCenterX());
                                            }
                                        }
                                        fin.setX(n.getCircle().getCenterX());
                                        fin.setY(n.getCircle().getCenterY());
                                        filGlobale.addMoveTo(fin);
                                        //filGlobale.getFil().getElements().add(fin);
                                        // -------------
                                        //filGlobale.getFil().setEndX(n.getCircle().getCenterX()-filGlobale.getFil().getLayoutX());
                                        //filGlobale.getFil().setEndY(n.getCircle().getCenterY()-filGlobale.getFil().getLayoutY());
                                        filGlobale.getFil().setStroke(CYAN);
                                        creationEnCourFil = false;
                                    }
                                }
                            }
                        });
                    }
                }
                //---------------------------------------------------------------
                //new task here
                for (Fil fil :
                        filList) {
                    fil.getFil().setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(event.getButton() == MouseButton.SECONDARY) {
                                filOsay = fil;
                                contextFil.show((Path)event.getTarget(), event.getSceneX(), event.getSceneY());
                            }
                        }
                    });
                }
                //---------------------------------------------------------------
                //addNoeuds()
                inverseur.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.INV.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.INV et = new Port.INV();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Branche b :
                            et.getBrancheSortie()) {
                        workSpace.getChildren().add(b.getNoeud().getCircle());
                    }
                    //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    e.consume();
                });

                afficheur.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.Af_Seg.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.Af_Seg et = new Port.Af_Seg();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    e.consume();
                });
                tristate.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.Tristate.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.Tristate et = new Port.Tristate();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudSortie();
                    et.setNoeudEntrees();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            et.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }

                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    e.consume();
                });

                MJK.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.JK.class, "Terminer."));
                    Bascule.JK jk = new Bascule.JK(true);
                    jk.setPosXImageView(event.getX());
                    jk.setPosYImageView(event.getY());
                    jk.setImageView();
                    jk.setNoeudEntreesJK();
                    jk.setNoeudSortiejk();
                    workSpace.getChildren().add(jk.getImageView());
                    for (Noeud n :
                            jk.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            jk.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(jk);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(jk);
                    MainWorkStack.push(item);
                    e.consume();
                });
                item4.setOnAction((e) -> {
                    if (copy) {
                        switch (clss) {
                            case "ToModule":
                                TO.fire();
                                break;
                            case "FromModule":
                                FROM.fire();
                                break;
                            case "ET":
                                ET2.fire();
                                break;
                            case "OU":
                                OU2.fire();
                                break;
                            case "nonET":
                                nonET2.fire();
                                break;
                            case "nonOU":
                                nonOU2.fire();
                                break;
                            case "XOR":
                                XOR2.fire();
                                break;
                            case "Af_Seg":
                                afficheur.fire();
                                break;
                            case "INV":
                                inverseur.fire();
                                break;
                            case "Switch":
                                input.fire();
                                break;
                            case "LED":
                                output.fire();
                                break;
                            case "Dec2_4":
                                createMenu7.fire();
                                break;
                            case "Dec3_8":
                                createMenu10.fire();
                                break;
                            case "demux1_4":
                                createMenu11.fire();
                                break;
                            case "demux1_8":
                                createMenu12.fire();
                                break;
                            case "Mux4_1":
                                createMenu8.fire();
                                break;
                            case "Mux8_1":
                                createMenu9.fire();
                                break;
                            case "demiAdditionneur2":
                                createMenuKarim11.fire();
                                break;
                            case "demiSoustracteur":
                                createMenuKarim15.fire();
                                break;
                            case "Additionneur2bits":
                                createMenuKarim13.fire();
                                break;
                            case "Additionneur4bits":
                                createMenuKarim14.fire();
                                break;
                            case "CompNbits":
                                createMenuKarim16.fire();
                                break;
                            case "completAdditionneur3":
                                createMenuKarim12.fire();
                                break;
                            case "Encod4_2":
                                createMenuKarim17.fire();
                                break;
                            case "Encod8_3":
                                createMenuKarim18.fire();
                                break;
                            case "Horloge":
                                horloge.fire();
                                break;
                            case "JK":
                                MJK.fire();
                                break;
                            case "D":
                                MD.fire();
                                break;
                            case "T":
                                MT.fire();
                                break;
                            case "Dlatch":
                                MDlatch.fire();
                                break;
                            case "RS":
                                MRS.fire();
                                break;
                            case "JKasy":
                                MJKasy.fire();
                                break;
                            case "RST":
                                MRST.fire();
                                break;
                            case "Registre4bit":
                                REGD4.fire();
                                break;
                            case "Registre8bit":
                                REGD8.fire();
                                break;
                            case "Compteur4bit":
                                COP4.fire();
                                break;
                            case "Compteur8bit":
                                COP8.fire();
                                break;
                            case "Regadec4bit":
                                REGD4.fire();
                                break;
                            case "Regadec8bit":
                                REGD8.fire();
                                break;
                            case "Tristate":
                                tristate.fire();
                                break;
                        }

                    }
                    if (cut) {
                        orgSceneX = v.getPosXImageView();
                        orgSceneY = v.getPosYImageView();
                        orgTranslateX = v.getImageView().getTranslateX();
                        orgTranslateY = v.getImageView().getTranslateY();
                        double offsetX = event.getX() - orgSceneX;
                        double offsetY = event.getY() - orgSceneY;
                        double newTranslateX = orgTranslateX + offsetX;
                        double newTranslateY = orgTranslateY + offsetY;
                        v.getImageView().setTranslateX(newTranslateX);
                        v.getImageView().setTranslateY(newTranslateY);
                        v.setPosXImageView(v.getImageView().getLayoutX() + v.getImageView().getTranslateX());
                        v.setPosYImageView(v.getImageView().getLayoutY() + v.getImageView().getTranslateY());
                        redefineComposantLogique(v, offsetX, offsetY);
                        workSpace.getChildren().add(v.getImageView());
                        redefineFil();
                        redefineLabelTOFROM();
                        cut = false;
                        v = null;
                        item4.setDisable(true);
                    }
                    e.consume();
                });
                MJKasy.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.JKasy.class, "Terminer."));
                    Bascule.JKasy jKasy = new Bascule.JKasy(true);
                    jKasy.setPosXImageView(event.getX());
                    jKasy.setPosYImageView(event.getY());
                    jKasy.setImageView();
                    jKasy.setNoeudEntreesJKasy();
                    jKasy.setNoeudSortiejkasy();
                    workSpace.getChildren().add(jKasy.getImageView());
                    for (Noeud n :
                            jKasy.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            jKasy.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(jKasy);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(jKasy);
                    MainWorkStack.push(item);
                    e.consume();
                });
                MRS.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.RS.class, "Terminer."));
                    Bascule.RS rs = new Bascule.RS();
                    rs.setPosXImageView(event.getX());
                    rs.setPosYImageView(event.getY());
                    rs.setImageView();
                    rs.setNoeudEntreesRS();
                    rs.setNoeudSortieRS();
                    workSpace.getChildren().add(rs.getImageView());
                    for (Noeud n :
                            rs.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            rs.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(rs);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(rs);
                    MainWorkStack.push(item);
                    e.consume();
                });
                MD.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.D.class, "Terminer."));
                    Bascule.D d = new Bascule.D(true);
                    d.setPosXImageView(event.getX());
                    d.setPosYImageView(event.getY());
                    d.setImageView();
                    d.setNoeudEntreesD();
                    d.setNoeudSortieD();
                    workSpace.getChildren().add(d.getImageView());
                    for (Noeud n :
                            d.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            d.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(d);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(d);
                    MainWorkStack.push(item);
                    e.consume();
                });
                MDlatch.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.Dlatch.class, "Terminer."));
                    Bascule.Dlatch dlatch = new Bascule.Dlatch(true);
                    dlatch.setPosXImageView(event.getX());
                    dlatch.setPosYImageView(event.getY());
                    dlatch.setImageView();
                    dlatch.setNoeudEntreesDlatch();
                    dlatch.setNoeudSortieDlatch();
                    workSpace.getChildren().add(dlatch.getImageView());
                    for (Noeud n :
                            dlatch.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            dlatch.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(dlatch);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(dlatch);
                    MainWorkStack.push(item);
                    e.consume();
                });
                MRST.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.RST.class, "Terminer."));
                    Bascule.RST rst = new Bascule.RST();
                    rst.setPosXImageView(event.getX());
                    rst.setPosYImageView(event.getY());
                    rst.setImageView();
                    rst.setNoeudEntreesRST();
                    rst.setNoeudSortieRST();
                    workSpace.getChildren().add(rst.getImageView());
                    for (Noeud n :
                            rst.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            rst.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(rst);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(rst);
                    MainWorkStack.push(item);
                    e.consume();
                });
                MT.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Bascule.T.class, "Terminer."));
                    Bascule.T t = new Bascule.T();
                    t.setPosXImageView(event.getX());
                    t.setPosYImageView(event.getY());
                    t.setImageView();
                    t.setNoeudEntreesT();
                    t.setNoeudSortieT();
                    workSpace.getChildren().add(t.getImageView());
                    for (Noeud n :
                            t.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            t.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(t);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(t);
                    MainWorkStack.push(item);
                    e.consume();
                });
                MREG4.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Registre4bit.class, "Terminer."));
                    Autres.Registre4bit t = new Autres.Registre4bit(true);
                    t.setPosXImageView(event.getX());
                    t.setPosYImageView(event.getY());
                    t.setImageView();
                    t.setNoeudEntreesREG4();
                    t.setNoeudSortieREG4();
                    workSpace.getChildren().add(t.getImageView());
                    for (Noeud n :
                            t.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            t.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(t);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(t);
                    MainWorkStack.push(item);
                    e.consume();
                });
                COP4.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Compteur4bit.class, "Terminer."));
                    Autres.Compteur4bit t = new Autres.Compteur4bit(true);
                    t.setPosXImageView(event.getX());
                    t.setPosYImageView(event.getY());
                    t.setImageView();
                    t.setNoeudEntreesCOP4();
                    t.setNoeudSortieCOP4();
                    workSpace.getChildren().add(t.getImageView());
                    for (Noeud n :
                            t.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            t.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(t);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(t);
                    MainWorkStack.push(item);
                    e.consume();
                });
                COP8.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Compteur8bit.class, "Terminer."));
                    Autres.Compteur8bit t = new Autres.Compteur8bit(true);
                    t.setPosXImageView(event.getX());
                    t.setPosYImageView(event.getY());
                    t.setImageView();
                    t.setNoeudEntreesCOP8();
                    t.setNoeudSortieCOP8();
                    workSpace.getChildren().add(t.getImageView());
                    for (Noeud n :
                            t.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            t.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(t);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(t);
                    MainWorkStack.push(item);
                    e.consume();
                });
                REGD4.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Regadec4bit.class, "Terminer."));
                    Autres.Regadec4bit t = new Autres.Regadec4bit(true);
                    t.setPosXImageView(event.getX());
                    t.setPosYImageView(event.getY());
                    t.setImageView();
                    t.setNoeudEntreesREGADEC4();
                    t.setNoeudSortieREGADEC4();
                    workSpace.getChildren().add(t.getImageView());
                    for (Noeud n :
                            t.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            t.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(t);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(t);
                    MainWorkStack.push(item);
                    e.consume();
                });
                REGD8.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Regadec8bit.class, "Terminer."));
                    Autres.Regadec8bit t = new Autres.Regadec8bit(true);
                    t.setPosXImageView(event.getX());
                    t.setPosYImageView(event.getY());
                    t.setImageView();
                    t.setNoeudEntreesREGADEC8();
                    t.setNoeudSortieREGADEC8();
                    workSpace.getChildren().add(t.getImageView());
                    for (Noeud n :
                            t.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            t.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(t);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(t);
                    MainWorkStack.push(item);
                    e.consume();
                });
                MREG8.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Autres.Registre8bit.class, "Terminer."));
                    Autres.Registre8bit t = new Autres.Registre8bit(true);
                    t.setPosXImageView(event.getX());
                    t.setPosYImageView(event.getY());
                    t.setImageView();
                    t.setNoeudEntreesREG8();
                    t.setNoeudSortieREG8();
                    workSpace.getChildren().add(t.getImageView());
                    for (Noeud n :
                            t.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud b :
                            t.getNoeudSortie()) {
                        workSpace.getChildren().add(b.getCircle());
                    }
                    composantLogiquesList.add(t);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(t);
                    MainWorkStack.push(item);
                    e.consume();
                });
                horloge.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Horloge.class, "Terminer."));
                    Horloge h = new Horloge(1);
                    h.setPosXImageView(event.getX());
                    h.setPosYImageView(event.getY());
                    h.setImageView();
                    h.setNoeudSortie();
                    workSpace.getChildren().add(h.getNoeudSortie().get(0).getCircle());
                    workSpace.getChildren().add(h.getImageView());
                    composantLogiquesList.add(h);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(h);
                    MainWorkStack.push(item);
                    e.consume();
                });
                FROM.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.FromModule.class, "Terminer."));
                        //System.out.println("NEW_ET");
                        Port.FromModule et = new Port.FromModule();
                        et.setPosXImageView(event.getX());
                        et.setPosYImageView(event.getY());
                        et.getId().setLayoutX(event.getX());
                        et.getId().setLayoutY(event.getY());
                        et.getImageView().setOnMouseClicked((event)->{
                            renameLabelsTOFROM(et);
                        });
                        workSpace.getChildren().add(et.getId());
                        et.setImageView();
                        et.setNoeudSortie();
                        workSpace.getChildren().add(et.getImageView());
                        workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                        composantLogiquesList.add(et);
                        WorkSpaceEvent item = new WorkSpaceEvent();
                        item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                        item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                        item.setComposant(et);
                        MainWorkStack.push(item);
                        e.consume();
                    }
                });
                TO.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ToModule.class, "Terminer."));
                        //System.out.println("NEW_ET");
                        Port.ToModule et = new Port.ToModule();
                        et.setPosXImageView(event.getX());
                        et.setPosYImageView(event.getY());
                        et.getId().setLayoutX(event.getX());
                        et.getId().setLayoutY(event.getY());
                        workSpace.getChildren().add(et.getId());
                        et.getImageView().setOnMouseClicked((event)->{
                            renameLabelsTOFROM(et);
                        });
                        et.setImageView();
                        et.setNoeudEntrees();
                        workSpace.getChildren().add(et.getImageView());
                        workSpace.getChildren().add(et.getNoeudEntrees().get(0).getCircle());
                        composantLogiquesList.add(et);
                        WorkSpaceEvent item = new WorkSpaceEvent();
                        item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                        item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                        item.setComposant(et);
                        MainWorkStack.push(item);
                        e.consume();
                    }
                });
                input.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.Switch.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.Switch et = new Port.Switch();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    e.consume();
                });
                output.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.LED.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.LED et = new Port.LED();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    e.consume();
                });
                horloge.setOnAction((e) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Horloge.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Horloge h = new Horloge(1);
                    h.setPosXImageView(event.getX());
                    h.setPosYImageView(event.getY());
                    h.setImageView();
                    h.setNoeudSortie();
                    workSpace.getChildren().add(h.getNoeudSortie().get(0).getCircle());
                    workSpace.getChildren().add(h.getImageView());
                    composantLogiquesList.add(h);
                    /*hLoader h0=new hLoader();
                    h0.setHologe(h);
                    h0.start();*/
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(h);
                    MainWorkStack.push(item);
                    e.consume();
                });

                ET3.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.ET et = new Port.ET(3);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                createMenuKarim11.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.demiAdditionneur2.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Combinatoires.demiAdditionneur2 et = new Combinatoires.demiAdditionneur2();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Branche n:
                         et.getBrancheSortie()) {
                        workSpace.getChildren().add(n.getNoeud().getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                createMenuKarim12.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.completAdditionneur3.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Combinatoires.completAdditionneur3 et = new Combinatoires.completAdditionneur3();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Branche n:
                         et.getBrancheSortie()) {
                        workSpace.getChildren().add(n.getNoeud().getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                createMenuKarim13.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.Additionneur2bits.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Combinatoires.Additionneur2bits et = new Combinatoires.Additionneur2bits();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Branche b:
                         et.getBrancheSortie()) {
                        workSpace.getChildren().add(b.getNoeud().getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                createMenuKarim14.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.Additionneur4bits.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Combinatoires.Additionneur4bits et = new Combinatoires.Additionneur4bits();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Branche n:
                         et.getBrancheSortie()) {
                        workSpace.getChildren().add(n.getNoeud().getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                createMenuKarim15.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.demiSoustracteur.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Combinatoires.demiSoustracteur et = new Combinatoires.demiSoustracteur();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud n:
                         et.getNoeudSortie()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                createMenuKarim16.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.CompNbits.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Combinatoires.CompNbits et = new Combinatoires.CompNbits();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Branche b:
                         et.getBrancheSortie()) {
                        workSpace.getChildren().add(b.getNoeud().getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                createMenuKarim17.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.Encod4_2.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Combinatoires.Encod4_2 et = new Combinatoires.Encod4_2();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud n:
                         et.getNoeudSortie()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                createMenuKarim18.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Combinatoires.Encod8_3.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Combinatoires.Encod8_3 et = new Combinatoires.Encod8_3();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud n:
                         et.getNoeudSortie()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                ET2.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.ET et = new Port.ET(2);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                ET4.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.ET et = new Port.ET(4);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                nonET3.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.nonET et = new Port.nonET(3);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                nonET4.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.nonET et = new Port.nonET(4);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                /******************************************************************************************/
                /******************* younes ajouter /*****************************************************/
                /*********************************************************************************************/
                XOR2.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                    Port.XOR et = new Port.XOR(2);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                /**********************************************************************************************/
                createMenu10.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                    Combinatoire.Dec3_8 et = new Combinatoire.Dec3_8();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud n :
                            et.getNoeudSortie()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                /*********************************************************************************************/
                createMenu11.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                    Combinatoire.demux1_4 et = new Combinatoire.demux1_4();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud n :
                            et.getNoeudSortie()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                /*********************************************************************************************/
                createMenu12.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                    Combinatoire.demux1_8 et = new Combinatoire.demux1_8();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud n :
                            et.getNoeudSortie()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                /**********************************************************************************************/
                createMenu7.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                    Combinatoire.Dec2_4 et = new Combinatoire.Dec2_4();
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    for (Noeud n :
                            et.getNoeudSortie()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                /******************************************************************************************/
                createMenu8.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                    Combinatoire.Mux4_1 et = new Combinatoire.Mux4_1(6);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });

                /******************************************************************************************/
                createMenu9.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.ET.class, "Terminer."));
                    Combinatoire.Mux8_1 et = new Combinatoire.Mux8_1(13);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                /******************************************************************************************/
                /****************** FIN DE TRAVAIL DE YOUNES **********************************************/
                /******************************************************************************************/
                nonET2.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.nonET.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.nonET et = new Port.nonET(2);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                nonET3.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.nonET.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.nonET et = new Port.nonET(3);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                nonET4.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.nonET.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.nonET et = new Port.nonET(4);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });

                OU3.setOnAction((t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.OU et = new Port.OU(3);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                OU4.setOnAction((t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.OU et = new Port.OU(4);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                OU2.setOnAction((ActionEvent t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.OU et = new Port.OU(2);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                nonOU2.setOnAction((t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.nonOU et = new Port.nonOU(2);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                nonOU4.setOnAction((t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.nonOU et = new Port.nonOU(4);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                nonOU3.setOnAction((t) -> {
                    HistoryController.data.add(new HistoryController.Processus(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), "Creation de " + Port.OU.class, "Terminer."));
                    //System.out.println("NEW_ET");
                    Port.nonOU et = new Port.nonOU(3);
                    et.setPosXImageView(event.getX());
                    et.setPosYImageView(event.getY());
                    et.setImageView();
                    et.setNoeudEntrees();
                    et.setNoeudSortie();
                    workSpace.getChildren().add(et.getImageView());
                    for (Noeud n :
                            et.getNoeudEntrees()) {
                        workSpace.getChildren().add(n.getCircle());
                    }
                    workSpace.getChildren().add(et.getNoeudSortie().get(0).getCircle());
                    composantLogiquesList.add(et);
                    WorkSpaceEvent item = new WorkSpaceEvent();
                    item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                    item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                    item.setComposant(et);
                    MainWorkStack.push(item);
                    t.consume();
                });
                if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
                    if (event.getTarget().getClass().equals(Circle.class)) {
                        /*for (Noeud n:
                                noeudsList) {
                            if(n.getCircle() == event.getTarget()) {
                                System.out.print("set Fing cursor to hand");
                                workSpace.setCursor((workSpace.getCursor() == Cursor.HAND) ? Cursor.DEFAULT : Cursor.HAND);
                                workSpace.getChildren().remove(n.getCircle());
                                n.setRadius((n.getRadius() == 3) ? 6 : 3);
                                n.setCircle();
                                workSpace.getChildren().add(n.getCircle());
                                break;
                            }
                        }*/
                    } else if ((event.getTarget().getClass().equals(ImageView.class))) {
                        workSpace.setCursor(Cursor.OPEN_HAND);
                    } else {
                        //if you have 'cursor problem' discommente this
                        workSpace.setCursor(Cursor.DEFAULT);
                    }
                    //come here AMOKRANE
                } else if (event.getButton() == MouseButton.PRIMARY && isSelect) {

                    isSelect = false;
                    whatIsSelected.setEffect(null);
                    workSpace.getChildren().remove(lSelection);


                } else if (event.getButton() == MouseButton.SECONDARY && event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                    if (event.getTarget().getClass().equals(ImageView.class)) {
                        for (ComposantLogique c :
                                composantLogiquesList) {
                            if (c.getImageView() == event.getTarget()) {
                                System.out.println(c.getClass().getSimpleName());
                                if (c.getClass().getTypeName() == "sample.Ports.Port$ET" || c.getClass().getTypeName() == "sample.Ports.Port$OU" || c.getClass().getTypeName() == "sample.Ports.Port$nonOU" || c.getClass().getTypeName() == "sample.Ports.Port$nonET" || c.getClass().getSimpleName().equals("Horloge")) {
                                    modifier.setDisable(false);
                                } else {
                                    modifier.setDisable(true);
                                }
                            }
                        }
                        context.show((Node) event.getTarget(), event.getScreenX(), event.getScreenY());
                        workSpace.removeEventHandler(MouseEvent.MOUSE_MOVED, workSpaceEventTipHandler);
                        mousePositionToolTip.hide();
                        contextMainPane.hide();
                    } else {
                        workSpace.removeEventHandler(MouseEvent.MOUSE_MOVED, workSpaceEventTipHandler);
                        mousePositionToolTip.hide();
                        if(!contextFil.isShowing()) contextMainPane.show(workSpace, event.getScreenX(), event.getScreenY());
                    }
                }
            }
        });
        workSpace.addEventHandler(MouseEvent.ANY, workSpaceEventHandler2 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED && event.getButton() == MouseButton.PRIMARY && event.getTarget().getClass().equals(ImageView.class)) {
                    //Inner border
                    ((ImageView) (event.getTarget())).getStyleClass().add("highlight");
                    orgSceneX = event.getSceneX();
                    orgSceneY = event.getSceneY();
                    orgTranslateX = ((ImageView) (event.getTarget())).getTranslateX();
                    orgTranslateY = ((ImageView) (event.getTarget())).getTranslateY();
                } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && event.getButton() == MouseButton.PRIMARY && event.getTarget().getClass().equals(ImageView.class)) {
                    workSpace.setCursor(Cursor.CLOSED_HAND);
                    if (!workSpace.getChildren().contains(l)) {
                        c.setOpacity(0.2);
                        c.toBack();
                        for (ComposantLogique co :
                                composantLogiquesList) {
                            if (co.getImageView() == event.getTarget()) {
                                c.setCenterX(co.getPosXImageView() + co.getImage().getWidth() / 2);
                                c.setCenterY(co.getPosYImageView() + co.getImage().getHeight() / 2);
                            }
                        }
                        l.setLayoutX(c.getCenterX());
                        l.setLayoutY(c.getCenterY());
                        l.toBack();
                        l.setStroke(Paint.valueOf("GRAY"));
                        l.setOpacity(0.5);
                        workSpace.getChildren().add(l);
                        workSpace.getChildren().add(c);
                    }
                    double offsetX = event.getSceneX() - orgSceneX;
                    double offsetY = event.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;
                    //--------Line-------
                    l.getStrokeDashArray().addAll(25d, 20d, 5d, 20d);
                    l.setStartX(0.0);
                    l.setStartY(0.0);
                    l.setEndX(event.getX() - l.getLayoutX());
                    l.setEndY(event.getY() - l.getLayoutY());
                    //-------------------

                    ((ImageView) (event.getTarget())).setTranslateX(newTranslateX);
                    ((ImageView) (event.getTarget())).setTranslateY(newTranslateY);
                    composantX = offsetX;
                    composantY = offsetY;
                    X = newTranslateX;
                    Y = newTranslateY;
                    //System.out.println(newTranslateX + " " + newTranslateY);
                    alreadyDragged = true;
                    double x = 0, y = 0, y2 = 0, x2 = 0, x3 = 0, y3 = 0;
                    for (ComposantLogique c :
                            composantLogiquesList) {
                        if (c.getImageView() == event.getTarget()) {
                            x = c.getImageView().getLayoutX() + ((ImageView) (event.getTarget())).getTranslateX();
                            y = c.getImageView().getLayoutY() + ((ImageView) (event.getTarget())).getTranslateY();
                            y2 = c.getImageView().getLayoutY() + ((ImageView) (event.getTarget())).getTranslateY() + c.getImage().getHeight() / 2;
                            y3 = c.getImageView().getLayoutY() + ((ImageView) (event.getTarget())).getTranslateY() + c.getImage().getHeight();
                            x2 = c.getImageView().getLayoutX() + ((ImageView) (event.getTarget())).getTranslateX() + c.getImage().getWidth() / 2;
                            x3 = c.getImageView().getLayoutX() + ((ImageView) (event.getTarget())).getTranslateX() + c.getImage().getWidth();

                        }
                    }
                    for (ComposantLogique c :
                            composantLogiquesList) {
                        if (c.getImageView() != event.getTarget()) {
                            //System.out.println(((ImageView)(event.getTarget())).getLayoutX());
                            if ((c.getPosXImageView() < x + 3 && c.getPosXImageView() > x - 2) || (c.getPosXImageView() + c.getImage().getWidth() < x + 3 && c.getPosXImageView() + c.getImage().getWidth() > x - 2)) {
                                if (c.getPosXImageView() < x + 3 && c.getPosXImageView() > x - 2) {
                                    helpLineV.setLayoutX(c.getPosXImageView());
                                } else {
                                    helpLineV.setLayoutX(c.getPosXImageView() + c.getImage().getWidth());
                                }
                                helpLineV.setEndY(workSpace.getHeight());
                                if (!workSpace.getChildren().contains(helpLineV))
                                    workSpace.getChildren().add(helpLineV);
                                break;
                            } else {
                                workSpace.getChildren().remove(helpLineV);
                            }
                        }
                    }
                    for (ComposantLogique c :
                            composantLogiquesList) {
                        if (c.getImageView() != event.getTarget()) {
                            //System.out.println(((ImageView)(event.getTarget())).getLayoutX());
                            if ((c.getPosYImageView() < y + 3 && c.getPosYImageView() > y - 3) || (c.getPosYImageView() + c.getImage().getHeight() < y + 3 && c.getPosYImageView() + c.getImage().getHeight() > y - 3)) {
                                if (c.getPosYImageView() < y + 3 && c.getPosYImageView() > y - 3) {
                                    helpLineH.setLayoutY(c.getPosYImageView());
                                } else {
                                    helpLineH.setLayoutY(c.getPosYImageView() + c.getImage().getHeight() + 1);
                                }
                                helpLineH.setEndX(workSpace.getWidth());
                                if (!workSpace.getChildren().contains(helpLineH))
                                    workSpace.getChildren().add(helpLineH);
                                break;
                            } else {
                                workSpace.getChildren().remove(helpLineH);
                            }
                        }
                    }
                    for (ComposantLogique c :
                            composantLogiquesList) {
                        if (c.getImageView() != event.getTarget()) {
                            //System.out.println(((ImageView)(event.getTarget())).getLayoutX());
                            if (c.getPosYImageView() + c.getImage().getHeight() / 2 < y2 + 3 && c.getPosYImageView() + c.getImage().getHeight() / 2 > y2 - 3) {
                                helpLineCenterH.setLayoutY(c.getPosYImageView() + c.getImage().getHeight() / 2);
                                helpLineCenterH.setEndX(workSpace.getWidth());
                                if (!workSpace.getChildren().contains(helpLineCenterH))
                                    workSpace.getChildren().add(helpLineCenterH);
                                break;
                            } else {
                                workSpace.getChildren().remove(helpLineCenterH);
                            }
                        }
                    }

                    for (ComposantLogique c :
                            composantLogiquesList) {
                        if (c.getImageView() != event.getTarget()) {
                            //System.out.println(((ImageView)(event.getTarget())).getLayoutX());
                            if (c.getPosXImageView() + c.getImage().getWidth() / 2 < x2 + 3 && c.getPosXImageView() + c.getImage().getWidth() / 2 > x2 - 3) {
                                helpLineCenterV.setLayoutX(c.getPosXImageView() + c.getImage().getWidth() / 2);
                                helpLineCenterV.setEndY(workSpace.getWidth());
                                if (!workSpace.getChildren().contains(helpLineCenterV))
                                    workSpace.getChildren().add(helpLineCenterV);
                                break;
                            } else {
                                workSpace.getChildren().remove(helpLineCenterV);
                            }
                        }
                    }

                    for (ComposantLogique c :
                            composantLogiquesList) {
                        if (c.getImageView() != event.getTarget()) {
                            //System.out.println(((ImageView)(event.getTarget())).getLayoutX());
                            if (c.getPosXImageView() + c.getImage().getWidth() < x3 + 3 && c.getPosXImageView() + c.getImage().getWidth() > x3 - 3) {
                                helpLineFinV.setLayoutX(c.getPosXImageView() + c.getImage().getWidth());
                                helpLineFinV.setEndY(workSpace.getWidth());
                                if (!workSpace.getChildren().contains(helpLineFinV))
                                    workSpace.getChildren().add(helpLineFinV);
                                break;
                            } else {
                                workSpace.getChildren().remove(helpLineFinV);
                            }
                        }
                    }

                    for (ComposantLogique c :
                            composantLogiquesList) {
                        if (c.getImageView() != event.getTarget()) {
                            //System.out.println(((ImageView)(event.getTarget())).getLayoutX());
                            if (c.getPosYImageView() + c.getImage().getHeight() < y3 + 3 && c.getPosYImageView() + c.getImage().getHeight() > y3 - 3) {
                                helpLineFinH.setLayoutY(c.getPosYImageView() + c.getImage().getHeight());
                                helpLineFinH.setEndX(workSpace.getWidth());
                                if (!workSpace.getChildren().contains(helpLineFinH))
                                    workSpace.getChildren().add(helpLineFinH);
                                break;
                            } else {
                                workSpace.getChildren().remove(helpLineFinH);
                            }
                        }
                    }


                } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED && event.getButton() == MouseButton.PRIMARY && event.getTarget().getClass().equals(ImageView.class) && alreadyDragged) {
                    workSpace.getChildren().remove(helpLineH);
                    workSpace.getChildren().remove(helpLineV);
                    workSpace.getChildren().remove(helpLineCenterH);
                    workSpace.getChildren().remove(helpLineCenterV);
                    workSpace.getChildren().remove(helpLineFinH);
                    workSpace.getChildren().remove(helpLineFinV);
                    alreadyDragged = false;
                    workSpace.getChildren().remove(l);
                    //circle de mouvement
                    workSpace.getChildren().remove(c);

                    for (ComposantLogique c :
                            composantLogiquesList) {
                        if (c.getImageView().equals(event.getTarget())) {
                            WorkSpaceEvent item = new WorkSpaceEvent();
                            item.setTypeEvent(WorkSpaceEvent.TypeEvent.MOUVEMENT);
                            item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                            item.setComposant(c);
                            item.setDeplacementX(composantX);
                            item.setDeplacementY(composantY);
                            MainWorkStack.push(item);
                            c.setPosXImageView(c.getImageView().getLayoutX() + c.getImageView().getTranslateX());
                            c.setPosYImageView(c.getImageView().getLayoutY() + c.getImageView().getTranslateY());
                            //System.out.println(String.format("%f", c.getImageView().getLayoutX()+c.getImageView().getTranslateX()));
                            //c.getImageView().setTranslateX((c.getImageView().getTranslateX());
                            redefineComposantLogique(c, composantX, composantY);
                            redefineFil();
                            redefineLabelTOFROM();
                            break;
                        }
                    }
                    workSpace.setCursor(Cursor.OPEN_HAND);
                }
            }
        });
        tabPane.addEventFilter(MouseEvent.ANY, (e) -> workSpace.requestFocus());
        tabPane.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.CONTEXT_MENU) {
                    contextMainPane.show(workSpace, mousePosX, mousePosY);
                } else if (e.isShiftDown()) {
                    shiftKey = true;
                } else {
                    shiftKey = false;
                }
            }
        });

        workSpace.addEventHandler(MouseEvent.MOUSE_PRESSED, workSpaceEventHandler3 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    // if something wrong come here
                    //workSpace.addEventHandler(MouseEvent.MOUSE_MOVED, workSpaceEventTipHandler);
                    context.hide();
                    contextMainPane.hide();
                    contextFil.hide();
                }
            }
        });
        premierPlan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                premierPlan.getParentPopup().getOwnerNode().toFront();
            }
        });
        arrierPlan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arrierPlan.getParentPopup().getOwnerNode().toBack();
            }
        });
        supp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (ComposantLogique c :
                        composantLogiquesList) {
                    if (c.getImageView() == supp.getParentPopup().getOwnerNode()) {
                        supprimerComposant(c);
                        break;
                    }
                }
            }
        });
        modifier.setOnAction((e) -> {
            try {
                ComposantLogique cmp = null;
                for (ComposantLogique c :
                        composantLogiquesList) {
                    if (c.getImageView() == modifier.getParentPopup().getOwnerNode()) {
                        if(c.getClass().getSimpleName().equals("Horloge")) {
                            TextInputDialog dialog = new TextInputDialog(tabPane.getSelectionModel().getSelectedItem().getText());
                            dialog.setTitle("Horloge");
                            dialog.setHeaderText("Temps d'horloge");
                            dialog.setContentText("Faire enter le temps d'horloge (Hz):");

                            dialog.getDialogPane().setStyle("-fx-control-inner-background:  rgb(60, 63, 65);" +
                                    "fx-accent: black;" +
                                    "-fx-background: rgb(60, 63, 65);");
                            // Get the Stage.
                            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

// Add a custom icon.
                            stage.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));

                            // Traditional way to get the response value.
                            Optional<String> result = dialog.showAndWait();
                            if (result.isPresent()) {
                                ((Horloge) c).s = Double.parseDouble(result.get());
                            }
                        }
                        nombreEntree = c.getNombreDEntrees();
                        this.EditAction(e);
                        this.tabPane.setOnMouseMoved((ex) -> {
                            if (actionEdit) {
                                if (c.getClass().getTypeName() == "sample.Ports.Port$ET") {
                                    System.out.println(nombreEntree);
                                    switch (nombreEntree) {
                                        case 2:
                                            Port.ET et = new Port.ET(2);
                                            et.setPosXImageView(c.getPosXImageView());
                                            et.setPosYImageView(c.getPosYImageView());
                                            et.setImageView();
                                            et.setNoeudEntrees();
                                            et.setNoeudSortie();
                                            workSpace.getChildren().add(et.getImageView());
                                            for (Branche n :
                                                    et.getBrancheEntree()) {
                                                workSpace.getChildren().add(n.getNoeud().getCircle());
                                            }
                                            for (Branche b :
                                                    et.getBrancheSortie()) {
                                                workSpace.getChildren().add(b.getNoeud().getCircle());
                                            }
                                            if (inv1) et.getBrancheEntree()[0].setEstInverser(true);
                                            if (inv2) et.getBrancheEntree()[1].setEstInverser(true);
                                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                                            composantLogiquesList.add(et);
                                            supprimerComposant(c);
                                            WorkSpaceEvent item = new WorkSpaceEvent();
                                            item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                            item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                                            item.setComposant(et);
                                            MainWorkStack.push(item);
                                            actionEdit = false;
                                            break;
                                        case 3:
                                            supprimerComposant(c);
                                            Port.ET et2 = new Port.ET(3);
                                            et2.setPosXImageView(c.getPosXImageView());
                                            et2.setPosYImageView(c.getPosYImageView());
                                            et2.setImageView();
                                            et2.setNoeudEntrees();
                                            et2.setNoeudSortie();
                                            workSpace.getChildren().add(et2.getImageView());
                                            for (Noeud n :
                                                    et2.getNoeudEntrees()) {
                                                workSpace.getChildren().add(n.getCircle());
                                            }
                                            for (Branche b :
                                                    et2.getBrancheSortie()) {
                                                workSpace.getChildren().add(b.getNoeud().getCircle());
                                            }

                                            if (inv1) et2.getBrancheEntree()[0].setEstInverser(true);
                                            if (inv2) et2.getBrancheEntree()[1].setEstInverser(true);
                                            if (inv3) et2.getBrancheEntree()[2].setEstInverser(true);

                                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                                            composantLogiquesList.add(et2);
                                            WorkSpaceEvent item2 = new WorkSpaceEvent();
                                            item2.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                            item2.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                                            item2.setComposant(et2);
                                            MainWorkStack.push(item2);
                                            actionEdit = false;
                                            break;
                                        case 4:
                                            supprimerComposant(c);
                                            Port.ET et3 = new Port.ET(4);
                                            et3.setPosXImageView(c.getPosXImageView());
                                            et3.setPosYImageView(c.getPosYImageView());
                                            et3.setImageView();
                                            et3.setNoeudEntrees();
                                            et3.setNoeudSortie();
                                            workSpace.getChildren().add(et3.getImageView());
                                            for (Noeud n :
                                                    et3.getNoeudEntrees()) {
                                                workSpace.getChildren().add(n.getCircle());
                                            }
                                            for (Branche b :
                                                    et3.getBrancheSortie()) {
                                                workSpace.getChildren().add(b.getNoeud().getCircle());
                                            }

                                            if (inv1) et3.getBrancheEntree()[0].setEstInverser(true);
                                            if (inv2) et3.getBrancheEntree()[1].setEstInverser(true);
                                            if (inv3) et3.getBrancheEntree()[2].setEstInverser(true);
                                            if (inv4) et3.getBrancheEntree()[3].setEstInverser(true);

                                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                                            composantLogiquesList.add(et3);
                                            WorkSpaceEvent item3 = new WorkSpaceEvent();
                                            item3.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                            item3.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                                            item3.setComposant(et3);
                                            MainWorkStack.push(item3);
                                            actionEdit = false;
                                            break;
                                    }
                                } else if (c.getClass().getTypeName() == "sample.Ports.Port$OU") {
                                    System.out.println(nombreEntree);
                                    switch (nombreEntree) {
                                        case 2:
                                            Port.OU et = new Port.OU(2);
                                            et.setPosXImageView(c.getPosXImageView());
                                            et.setPosYImageView(c.getPosYImageView());
                                            et.setImageView();
                                            et.setNoeudEntrees();
                                            et.setNoeudSortie();
                                            workSpace.getChildren().add(et.getImageView());
                                            for (Branche n :
                                                    et.getBrancheEntree()) {
                                                workSpace.getChildren().add(n.getNoeud().getCircle());
                                            }
                                            for (Branche b :
                                                    et.getBrancheSortie()) {
                                                workSpace.getChildren().add(b.getNoeud().getCircle());
                                            }
                                            if (inv1) et.getBrancheEntree()[0].setEstInverser(true);
                                            if (inv2) et.getBrancheEntree()[1].setEstInverser(true);
                                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                                            composantLogiquesList.add(et);
                                            supprimerComposant(c);
                                            WorkSpaceEvent item = new WorkSpaceEvent();
                                            item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                            item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                                            item.setComposant(et);
                                            MainWorkStack.push(item);
                                            actionEdit = false;
                                            break;
                                        case 3:
                                            supprimerComposant(c);
                                            Port.OU et2 = new Port.OU(3);
                                            et2.setPosXImageView(c.getPosXImageView());
                                            et2.setPosYImageView(c.getPosYImageView());
                                            et2.setImageView();
                                            et2.setNoeudEntrees();
                                            et2.setNoeudSortie();
                                            workSpace.getChildren().add(et2.getImageView());
                                            for (Noeud n :
                                                    et2.getNoeudEntrees()) {
                                                workSpace.getChildren().add(n.getCircle());
                                            }
                                            for (Branche b :
                                                    et2.getBrancheSortie()) {
                                                workSpace.getChildren().add(b.getNoeud().getCircle());
                                            }

                                            if (inv1) et2.getBrancheEntree()[0].setEstInverser(true);
                                            if (inv2) et2.getBrancheEntree()[1].setEstInverser(true);
                                            if (inv3) et2.getBrancheEntree()[2].setEstInverser(true);

                                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                                            composantLogiquesList.add(et2);
                                            WorkSpaceEvent item2 = new WorkSpaceEvent();
                                            item2.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                            item2.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                                            item2.setComposant(et2);
                                            MainWorkStack.push(item2);
                                            actionEdit = false;
                                            break;
                                        case 4:
                                            supprimerComposant(c);
                                            Port.OU et3 = new Port.OU(4);
                                            et3.setPosXImageView(c.getPosXImageView());
                                            et3.setPosYImageView(c.getPosYImageView());
                                            et3.setImageView();
                                            et3.setNoeudEntrees();
                                            et3.setNoeudSortie();
                                            workSpace.getChildren().add(et3.getImageView());
                                            for (Noeud n :
                                                    et3.getNoeudEntrees()) {
                                                workSpace.getChildren().add(n.getCircle());
                                            }
                                            for (Branche b :
                                                    et3.getBrancheSortie()) {
                                                workSpace.getChildren().add(b.getNoeud().getCircle());
                                            }

                                            if (inv1) et3.getBrancheEntree()[0].setEstInverser(true);
                                            if (inv2) et3.getBrancheEntree()[1].setEstInverser(true);
                                            if (inv3) et3.getBrancheEntree()[2].setEstInverser(true);
                                            if (inv4) et3.getBrancheEntree()[3].setEstInverser(true);

                                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                                            composantLogiquesList.add(et3);
                                            WorkSpaceEvent item3 = new WorkSpaceEvent();
                                            item3.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                            item3.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                                            item3.setComposant(et3);
                                            MainWorkStack.push(item3);
                                            actionEdit = false;
                                            break;
                                    }
                                } else if (c.getClass().getTypeName() == "sample.Ports.Port$nonOU") {
                                    System.out.println(nombreEntree);
                                    switch (nombreEntree) {
                                        case 2:
                                            Port.nonOU et = new Port.nonOU(2);
                                            et.setPosXImageView(c.getPosXImageView());
                                            et.setPosYImageView(c.getPosYImageView());
                                            et.setImageView();
                                            et.setNoeudEntrees();
                                            et.setNoeudSortie();
                                            workSpace.getChildren().add(et.getImageView());
                                            for (Branche n :
                                                    et.getBrancheEntree()) {
                                                workSpace.getChildren().add(n.getNoeud().getCircle());
                                            }
                                            for (Branche b :
                                                    et.getBrancheSortie()) {
                                                workSpace.getChildren().add(b.getNoeud().getCircle());
                                            }
                                            if (inv1) et.getBrancheEntree()[0].setEstInverser(true);
                                            if (inv2) et.getBrancheEntree()[1].setEstInverser(true);
                                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                                            composantLogiquesList.add(et);
                                            supprimerComposant(c);
                                            WorkSpaceEvent item = new WorkSpaceEvent();
                                            item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                            item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                                            item.setComposant(et);
                                            MainWorkStack.push(item);
                                            actionEdit = false;
                                            break;
                                        case 3:
                                            supprimerComposant(c);
                                            Port.nonOU et2 = new Port.nonOU(3);
                                            et2.setPosXImageView(c.getPosXImageView());
                                            et2.setPosYImageView(c.getPosYImageView());
                                            et2.setImageView();
                                            et2.setNoeudEntrees();
                                            et2.setNoeudSortie();
                                            workSpace.getChildren().add(et2.getImageView());
                                            for (Noeud n :
                                                    et2.getNoeudEntrees()) {
                                                workSpace.getChildren().add(n.getCircle());
                                            }
                                            for (Branche b :
                                                    et2.getBrancheSortie()) {
                                                workSpace.getChildren().add(b.getNoeud().getCircle());
                                            }

                                            if (inv1) et2.getBrancheEntree()[0].setEstInverser(true);
                                            if (inv2) et2.getBrancheEntree()[1].setEstInverser(true);
                                            if (inv3) et2.getBrancheEntree()[2].setEstInverser(true);

                                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                                            composantLogiquesList.add(et2);
                                            WorkSpaceEvent item2 = new WorkSpaceEvent();
                                            item2.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                            item2.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                                            item2.setComposant(et2);
                                            MainWorkStack.push(item2);
                                            actionEdit = false;
                                            break;
                                        case 4:
                                            supprimerComposant(c);
                                            Port.nonOU et3 = new Port.nonOU(4);
                                            et3.setPosXImageView(c.getPosXImageView());
                                            et3.setPosYImageView(c.getPosYImageView());
                                            et3.setImageView();
                                            et3.setNoeudEntrees();
                                            et3.setNoeudSortie();
                                            workSpace.getChildren().add(et3.getImageView());
                                            for (Noeud n :
                                                    et3.getNoeudEntrees()) {
                                                workSpace.getChildren().add(n.getCircle());
                                            }
                                            for (Branche b :
                                                    et3.getBrancheSortie()) {
                                                workSpace.getChildren().add(b.getNoeud().getCircle());
                                            }

                                            if (inv1) et3.getBrancheEntree()[0].setEstInverser(true);
                                            if (inv2) et3.getBrancheEntree()[1].setEstInverser(true);
                                            if (inv3) et3.getBrancheEntree()[2].setEstInverser(true);
                                            if (inv4) et3.getBrancheEntree()[3].setEstInverser(true);

                                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                                            composantLogiquesList.add(et3);
                                            WorkSpaceEvent item3 = new WorkSpaceEvent();
                                            item3.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                            item3.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                                            item3.setComposant(et3);
                                            MainWorkStack.push(item3);
                                            actionEdit = false;
                                            break;
                                    }
                                } else if (c.getClass().getTypeName() == "sample.Ports.Port$nonET") {
                                    System.out.println(nombreEntree);
                                    switch (nombreEntree) {
                                        case 2:
                                            Port.nonET et = new Port.nonET(2);
                                            et.setPosXImageView(c.getPosXImageView());
                                            et.setPosYImageView(c.getPosYImageView());
                                            et.setImageView();
                                            et.setNoeudEntrees();
                                            et.setNoeudSortie();
                                            workSpace.getChildren().add(et.getImageView());
                                            for (Branche n :
                                                    et.getBrancheEntree()) {
                                                workSpace.getChildren().add(n.getNoeud().getCircle());
                                            }
                                            for (Branche b :
                                                    et.getBrancheSortie()) {
                                                workSpace.getChildren().add(b.getNoeud().getCircle());
                                            }
                                            if (inv1) et.getBrancheEntree()[0].setEstInverser(true);
                                            if (inv2) et.getBrancheEntree()[1].setEstInverser(true);
                                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                                            composantLogiquesList.add(et);
                                            supprimerComposant(c);
                                            WorkSpaceEvent item = new WorkSpaceEvent();
                                            item.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                            item.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                                            item.setComposant(et);
                                            MainWorkStack.push(item);
                                            actionEdit = false;
                                            break;
                                        case 3:
                                            supprimerComposant(c);
                                            Port.nonET et2 = new Port.nonET(3);
                                            et2.setPosXImageView(c.getPosXImageView());
                                            et2.setPosYImageView(c.getPosYImageView());
                                            et2.setImageView();
                                            et2.setNoeudEntrees();
                                            et2.setNoeudSortie();
                                            workSpace.getChildren().add(et2.getImageView());
                                            for (Noeud n :
                                                    et2.getNoeudEntrees()) {
                                                workSpace.getChildren().add(n.getCircle());
                                            }
                                            for (Branche b :
                                                    et2.getBrancheSortie()) {
                                                workSpace.getChildren().add(b.getNoeud().getCircle());
                                            }

                                            if (inv1) et2.getBrancheEntree()[0].setEstInverser(true);
                                            if (inv2) et2.getBrancheEntree()[1].setEstInverser(true);
                                            if (inv3) et2.getBrancheEntree()[2].setEstInverser(true);

                                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                                            composantLogiquesList.add(et2);
                                            WorkSpaceEvent item2 = new WorkSpaceEvent();
                                            item2.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                            item2.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                                            item2.setComposant(et2);
                                            MainWorkStack.push(item2);
                                            actionEdit = false;
                                            break;
                                        case 4:
                                            supprimerComposant(c);
                                            Port.nonET et3 = new Port.nonET(4);
                                            et3.setPosXImageView(c.getPosXImageView());
                                            et3.setPosYImageView(c.getPosYImageView());
                                            et3.setImageView();
                                            et3.setNoeudEntrees();
                                            et3.setNoeudSortie();
                                            workSpace.getChildren().add(et3.getImageView());
                                            for (Noeud n :
                                                    et3.getNoeudEntrees()) {
                                                workSpace.getChildren().add(n.getCircle());
                                            }
                                            for (Branche b :
                                                    et3.getBrancheSortie()) {
                                                workSpace.getChildren().add(b.getNoeud().getCircle());
                                            }

                                            if (inv1) et3.getBrancheEntree()[0].setEstInverser(true);
                                            if (inv2) et3.getBrancheEntree()[1].setEstInverser(true);
                                            if (inv3) et3.getBrancheEntree()[2].setEstInverser(true);
                                            if (inv4) et3.getBrancheEntree()[3].setEstInverser(true);

                                            //workSpace.getChildren().add(et.getNoeudSortie().getCircle());
                                            composantLogiquesList.add(et3);
                                            WorkSpaceEvent item3 = new WorkSpaceEvent();
                                            item3.setTypeEvent(WorkSpaceEvent.TypeEvent.CREATION);
                                            item3.setSpecifier(WorkSpaceEvent.EventSpecifier.COMPOSANT);
                                            item3.setComposant(et3);
                                            MainWorkStack.push(item3);
                                            actionEdit = false;
                                            break;
                                    }
                                }
                            }
                            ex.consume();
                            setInverseur();
                        });
                    }
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }
            setInverseur();
        });
        rotd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double IX, IY, S;
                for (ComposantLogique c :
                        composantLogiquesList) {
                    if (rotd.getParentPopup().getOwnerNode() == c.getImageView()) {
                        IX = c.getPosXImageView();
                        IY = c.getPosYImageView();
                        c.getImageView().setRotate(c.getImageView().getRotate() + 90);
                        IX = (IX + (c.getImage().getWidth() / 2) - (c.getImage().getHeight() / 2));
                        IY = (IY + (c.getImage().getHeight() / 2) - (c.getImage().getWidth() / 2));
                        for (Branche b :
                                c.getBrancheEntree()) {
                            S = b.getNoeud().getCircle().getCenterX();
                            b.getNoeud().getCircle().setCenterX(c.getImage().getHeight() + IX - (b.getNoeud().getCircle().getCenterY() - c.getPosYImageView()));
                            b.getNoeud().getCircle().setCenterY(IY + (S - c.getPosXImageView()));
                        }
                        for (Branche b :
                                c.getBrancheSortie()) {
                            S = b.getNoeud().getCircle().getCenterX();
                            b.getNoeud().getCircle().setCenterX(c.getImage().getHeight() + IX - (b.getNoeud().getCircle().getCenterY() - c.getPosYImageView()));
                            b.getNoeud().getCircle().setCenterY(IY + (S - c.getPosXImageView()));
                        }
                        break;
                    }
                }
                redefineFil();
            }
        });
        rotg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double IX, IY, S;
                for (ComposantLogique c :
                        composantLogiquesList) {
                    if (rotg.getParentPopup().getOwnerNode() == c.getImageView()) {
                        c.getImageView().setRotate(c.getImageView().getRotate() - 90);
                        for (int i = 0; i < 3; i++) {
                            IX = c.getPosXImageView();
                            IY = c.getPosYImageView();
                            IX = (IX + (c.getImageView().getFitWidth() / 2) - (c.getImageView().getFitHeight() / 2));
                            IY = (IY + (c.getImageView().getFitHeight() / 2) - (c.getImageView().getFitWidth() / 2));
                            for (Branche b :
                                    c.getBrancheEntree()) {
                                S = b.getNoeud().getCircle().getCenterX();
                                b.getNoeud().getCircle().setCenterX(c.getImageView().getFitHeight() + IX - (b.getNoeud().getCircle().getCenterY() - c.getPosYImageView()));
                                b.getNoeud().getCircle().setCenterY(IY + (S - c.getPosXImageView()));
                            }
                            for (Branche b :
                                    c.getBrancheSortie()) {
                                S = b.getNoeud().getCircle().getCenterX();
                                b.getNoeud().getCircle().setCenterX(c.getImageView().getFitHeight() + IX - (b.getNoeud().getCircle().getCenterY() - c.getPosYImageView()));
                                b.getNoeud().getCircle().setCenterY(IY + (S - c.getPosXImageView()));
                            }
                        }
                        break;
                    }
                }
                redefineFil();
            }
        });

    }

    private void Nouveau(String nom) {
        AnchorPane space = new AnchorPane();
        space.setPrefHeight(968.0);
        space.setPrefWidth(1830.0);
        space.setId("workSpace");
        ScrollPane s = new ScrollPane();
        s.setContent(space);
        Tab tab;
        if (nom == null) tab = new Tab("nouveau Schéma " + tabID);
        else tab = new Tab(nom);
        tabID++;
        tab.setContent(s);
        tabPane.getTabs().add(tab);
        drawRulers(space);
        gridDrowPointesLongue(space);
        new RubberBandSelection(space);
        space.setFocusTraversable(true);

        composantLogiqueList2.add(new ArrayList<ComposantLogique>());
        noeudsList2.add(new ArrayList<Noeud>());
        filList2.add(new ArrayList<Fil>());

        // endo redo controle
        MainWorkStackList.add(new Stack<WorkSpaceEvent>());
        SecondaryWorkStackList.add(new Stack<WorkSpaceEvent>());

        tabPane.getSelectionModel().select(tab);
        miseJourTree("add", tab);
    }
}