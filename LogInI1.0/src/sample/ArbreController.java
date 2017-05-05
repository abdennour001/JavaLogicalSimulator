/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author younes chellaf
 */
public class ArbreController implements Initializable {

    @FXML
    private AnchorPane anch_left;

    @FXML
    private ImageView image;

    @FXML
    private TreeView<String> tree;

    @FXML
    private AnchorPane anch_centre;

    @FXML
    private ImageView image2;

    @FXML
    private TextArea text;
    /*******************************************************************/
    Image ou = new Image(getClass().getResourceAsStream("Ports/OU.png"));
    Image nor = new Image(getClass().getResourceAsStream("Ports/NOR.png"));
    Image et = new Image(getClass().getResourceAsStream("Ports/ET2.png"));
    Image nand= new Image(getClass().getResourceAsStream("Ports/NAND2.png"));
    Image inv = new Image(getClass().getResourceAsStream("Ports/INV.png"));
    Image oux= new Image(getClass().getResourceAsStream("Ports/XOR.png"));
    Image led = new Image(getClass().getResourceAsStream("Ports/LED.png"));
    Image interep = new Image(getClass().getResourceAsStream("Ports/switch.png"));
    Image decod = new Image(getClass().getResourceAsStream("Ports/dec3_8.png"));
    Image encod = new Image(getClass().getResourceAsStream("Ports/Encod8_3.png"));
    Image seq = new Image(getClass().getResourceAsStream("Ports/afficheur.png"));
    Image mux= new Image(getClass().getResourceAsStream("Ports/mux8.png"));
    Image transcod = new Image(getClass().getResourceAsStream("Ports/mux4.png"));
    Image demux = new Image(getClass().getResourceAsStream("Ports/demux8.png"));
    Image cmp = new Image(getClass().getResourceAsStream("Ports/Cmp4.png"));
    Image add = new Image(getClass().getResourceAsStream("Ports/ADDC4.png"));
    Image comb= new Image(getClass().getResourceAsStream("Ports/ADDC2.png"));
    Image jk= new Image(getClass().getResourceAsStream("JK.png"));
    Image rs= new Image(getClass().getResourceAsStream("RS.png"));
    Image d= new Image(getClass().getResourceAsStream("d.png"));
    Image t= new Image(getClass().getResourceAsStream("T.png"));
    Image horloge= new Image(getClass().getResourceAsStream("HORLOGE.png"));

    Image icon = new Image(getClass().getResourceAsStream("icon22.png"));

    @FXML
    public void mouseClick() {

        TreeItem<String> item = tree.getSelectionModel().getSelectedItem();
        switch (item.getValue()){
            case "portes logiques":{text.setText("Comportement\n" +
                    "Les portes ET, NAND, OU, NOR et XOR calculent chacune la fonction respective des entrées et émettent le résultat sur la sortie.\n" +
                    "Par défaut, toutes les entrées qui ne sont pas connectées sont ignorées, c'est-à-dire si l'entrée n'a vraiment rien d'attaché, pas même un fil. De cette façon, vous pouvez insérer une grille à 4 entrées, mais seulement attacher trois entrées, et il fonctionnera comme une porte à 3 entrées.\n" +
                    "La table de vérité à deux entrées pour les portes est la suivante. (La lettre X représente la valeur d'erreur et la lettre Z représente la valeur flottante.)");

            }
            break;
            case "circuits combinatoires":{text.setText("Circuit combinatoire\n" +
                    "Un circuit combinatoire est un circuit numérique à base de portes logiques dont les sorties dépendent uniquement des entrées\n" +
                    "Parmi les principaux circuits combinatoires, on distingue les circuits d’opérations arithmétiques (addition, soustraction) et \n" +
                    "logiques (décodage, multiplexage, comparaison)\n" +
                    "On peut utiliser les circuits combinatoires pour réaliser d’autres circuits plus complexes");
                image2.setImage(comb);
            }
            break;
            case "circuits sequentiels": {text.setText("Chaque bascule (ou flip-flop) stocke un seul bit de données, qui est émis par la sortie Q. Normalement, la valeur peut être contrôlée via les entrées du côté ouest. En particulier, la valeur change lorsque l'entrée d'horloge passe de 0 à 1 (ou autrement configuré), la valeur change selon le tableau ci-dessous.");
                image2.setImage(seq);
            } break;
            case "ET":{text.setText("la porte logique ET :\n\n"+
                    "E0  E1   S\n" +
                    "0   0    0\n" +
                    "0   1    0\n" +
                    "1   0    0\n" +
                    "1   1    1\n" +
                    "\n" +
                    "X AND 1 = X\n" +
                    "X AND 0 = 0");
                image2.setImage(et);
            }
            break;
            case "OU":{text.setText("la porte logique OU :\n\n" +
                    "E0  E1   S\n" +
                    "0   0    0\n" +
                    "0   1    1\n" +
                    "1   0    1\n" +
                    "1   1    1\n" +
                    "\n" +
                    "X OU 1 = 1\n" +
                    "X OU 0 = X");
                image2.setImage(ou);


            }
            break;
            case "NON OU":{text.setText("la porte logique NON OU  :\n\n" +
                    "Le NON OU (NOR) est l'equivalent d'une porte OU suivie d'un inverseur (NON)\n" +
                    "E0  E1   S\n" +
                    "0   0    1\n" +
                    "0   1    0\n" +
                    "1   0    0\n" +
                    "1   1    0\n" +
                    "\n" +
                    "X NOR 1 = 0\n" +
                    "X NOR 0 = non X");
                image2.setImage(nor);
            }
            break;
            case "NON ET":{
                text.setText("la porte loogique NON ET   : \n" +
                        "Le NON ET (NAND) est l'equivalent d'une porte ET suivie d'un inverseur (NON)\n" +
                        "E0  E1   S\n" +
                        "0   0    1\n" +
                        "0   1    1\n" +
                        "1   0    1\n" +
                        "1   1    0\n" +
                        "\n" +
                        "X NAND 1 = non X\n" +
                        "X NAND 0 = 1");
                image2.setImage(nand);
            }
            break;
            case "OUX":{
                text.setText("la porte loqique OUX :\n" +
                        "OUX (XOR) est le OU exculsif c'est à dire que 1 XOR 1 = 0\n" +
                        "E0  E1   S\n" +
                        "0   0    0\n" +
                        "0   1    1\n" +
                        "1   0    1\n" +
                        "1   1    0");
                image2.setImage(oux);
            }

            break;
            case "INVERSEUR":{
                text.setText("La porte NON émet le complément de l'entrée qu'elle reçoit. La table de vérité pour une porte NOT est la suivante:\n" +
                        "E    S\n" +
                        "0    1\n" +
                        "1    0");
                image2.setImage(inv);
            }
            break;
            case "LED":{
                text.setText("LED\n" +
                        "Composant electronique que l'on branche à la sortie et qui indique si la sortie est à 0 ou à 1");
                image2.setImage(led);
            }
            break;
            case "SWITCH":{
                text.setText("Switch\n" +
                        "Composant que l'on branche à l'entrée d'un circuit pour indiquer si c'est un 0 ou un 1");
                image2.setImage(interep);
            }
            break;
            case "ADDITIONEUR":{
                text.setText("ADDITIONNEUR\n" +
                        "Ce composant additonne deux valeurs entrantes et sort la somme.\n" +
                        "Sont disponibles: le demi additionneur (sans retenue entrante), l'additionneur complet de 1 bits et l'additionneur 4 bits\n" +
                        "Pour additionner 3 bits par exemple, on prend un additionneur 4 bits sachant que ce qui est en plus ne sera pas considéré \n" +
                        "Le composant est conçu de telle sorte qu'on peut en mettre plusieurs en cascade pour additionner plus de bits que ce qui est possible avec un seul additionneur \n" +
                        "");
                image2.setImage(add);
            }
            break;
            case "SOUSTRACTEUR":{
                text.setText("SOUSTRACTEUR\n" +
                        "Ce composant soustrait les valeurs entrantes (la supérieure moins l'inferieur) et sort donc la différence.\n" +
                        "(on va essayer d'associer plusieurs soustracteurs par la suite)");
            }
            break;
            case "MULTIPLIXEUR":{text.setText("MULTIPLEXEUR\n" +
                    "Ce composant met en relation 1 entrée parmi n, avec la sortie, d’où la nécessité de commande pour sa sélection.\n" +
                    "Sont disponibles: Mux 2_1, Mux 4_1 et Mux 8_1");
                image2.setImage(mux);
            }
            break;
            case "COMPARATEUR":{
                text.setText("COMPARATEUR\n" +
                        "Compare deux valeurs sur maximums n bits chacune (c'est à dire pas forcement n bits exactement)\n" +
                        "Sur les sorties, on aura: soit egale, soit superieur, soit inferieur");
                image2.setImage(cmp);
            }
            break;
            case "DEMULTIPLIXEUR":{
                text.setText("Demux\n" +
                        "Ce composant met en relation 1 entrée avec 1 parmi 2 puissance n sorties d’où la nécessité de n fils de commande pour sa sélection\n" +
                        "Sont disponibles: Demux 1_2, Demux 1_4 et Demux 1_8\n" +
                        "");
                image2.setImage(demux);
            }
            break;
            case "DECODEUR":{
                text.setText("DECODEUR\n" +
                        "Ce composant consiste à faire correspondre à un code présent en entrée sur n lignes d'un bit chacune, une seule sortie active parmi les 2n sorties possibles selon la valeur decimale de l'entrée\n" +
                        "Sont disponibles: Dec 2_4 et Dec 3_8");
                image2.setImage(decod);
            }
            break;
            case "ENCODEUR":{
                text.setText("Encodeur\n" +
                        "C'est l'nverse d’un décodeur avec 2 puissance n entrées et n sorties tel que pour chaque combinaison en entrée on va avoir son numéro (en binaire) à la sortie\n" +
                        "Sont disponibles: Encodeur 4_2 et Encodeur 8_3\n" +
                        "");
                image2.setImage(encod);
            }
            break;
            case "TRANSCODEUR":
            {
                text.setText("Transcodeur\n" +
                        "C’est un circuit combinatoire transformant une information présente en entrée sous une forme donnée (code 1) sur n bits en la même information \n" +
                        "en sortie mais sous une autre forme (code 2) sur m bits\n" +
                        "");
            }
            break;
            case "bascule JK":{
                text.setText("Bascule J-K : Lorsque l'horloge déclenche, la valeur rappelée par la bascule bascule si les entrées J et K sont à la fois 1 et la valeur reste la même si les deux sont 0; Si elles sont différentes, la valeur devient 1 si l'entrée J (saut) est 1 et 0 si l'entrée K (Kill) est 1.\n" +
                        "J    K     Q \n" +
                        "0    0     Q\n" +
                        "0    1     1\n" +
                        "1    0     0 \n" +
                        "1    1     nonQ");
                image2.setImage(jk);
            }
            break;
            case "bascule RS":{
                text.setText("Bascule RS : Lorsque l'horloge déclenche, la valeur mémorisée par la bascule reste inchangée si R et S sont tous les deux 0, devient 0 si l'entrée R (Reset) est 1 et devient 1 si l'entrée S (Set) Est 1.\n" +
                        "R    S     Q\n" +
                        "0    0     Interdit\n" +
                        "0    1     1 \n" +
                        "1    0     0\n" +
                        "1    1     Memoire ");
                image2.setImage(rs);
            }
            break;
            case "bascule RST":{text.setText("Bascules RST\n" +
                    "Bascule RS à portes NOR dans laquelle les entrées R et S ne sont prises en compte que si elles sont en coïncidence avec un signal\n" +
                    "de commande T à 1, et bloquée s'il est à 0.\n" +
                    "On parle de bascule synchrone si le signal de commande est fourni par une horloge \n" +
                    "D'ou la table de vérité suivante:\n" +
                    "T    R    S     Q+\n" +
                    "0    X    X     Q- (mémoire) \n" +
                    "1    0    0     Q- (mémoire)\n" +
                    "1    0    1     1 \n" +
                    "1    1    0     0\n" +
                    "1    1    1     Interdit   ");

            }
            break;
            case "bascule D":{
                text.setText("Bascule D: Lorsque l'horloge se déclenche, la valeur mémorisée par la bascule devient la valeur de l'entrée D (Data) à cet instant.\n" +
                        "D   Q\n" +
                        "0   0\n" +
                        "1   1");
                image2.setImage(d);
            }
            break;
            case "bascule T": {
                text.setText("Bascule T (Toggle) : Lorsque l'horloge se déclenche, la valeur rappelée par la bascule bascule ou reste la même selon que l'entrée T (Toggle) est 1 ou 0.\n" +
                        "T   Q\n" +
                        "0   Q\n" +
                        "1   nonQ");
                image2.setImage(t);
            }
            break;
            case "Horloge":{
                text.setText("Horloge\n" +
                        "Composant(ou signal) passant indéfiniment et de façon régulière  d’un niveau haut à un niveau bas (succession de 1 et de 0), \n" +
                        "chaque transition s’appelle un top \n" +
                        "On parle alors de fréquence de l'horloge qui est le nombre de changement d'état par seconde en hertz (Hz)");
                image2.setImage(horloge);
            }
            break;

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TreeItem<String> composons = new TreeItem<>("LES COMPOSANTS", new ImageView(icon));
        composons.setExpanded(true);
        TreeItem<String> comp1 = new TreeItem<>("portes logiques", new ImageView(icon));
        comp1.setExpanded(false);
        TreeItem<String> comp2 = new TreeItem<>("circuits combinatoires", new ImageView(icon));
        comp2.setExpanded(false);
        TreeItem<String> comp3 = new TreeItem<>("circuits sequentiels", new ImageView(icon));
        comp3.setExpanded(false);
        /*************************************************************************/
        TreeItem<String> ET = new TreeItem<>("ET", new ImageView(icon));
        TreeItem<String> OU = new TreeItem<>("OU", new ImageView(icon));
        TreeItem<String> NOR = new TreeItem<>("NON OU", new ImageView(icon));
        TreeItem<String> NAND = new TreeItem<>("NON ET", new ImageView(icon));
        TreeItem<String> XOR = new TreeItem<>("OUX", new ImageView(icon));
        TreeItem<String> INV = new TreeItem<>("INVERSEUR", new ImageView(icon));
        TreeItem<String> LED= new TreeItem<>("LED", new ImageView(icon));
        TreeItem<String> SWITCH = new TreeItem<>("SWITCH", new ImageView(icon));

        comp1.getChildren().addAll(ET,OU,NOR,NAND,XOR,INV,LED,SWITCH);
        /************************************************************************/
        TreeItem<String>  add= new TreeItem<>("ADDITIONEUR", new ImageView(icon));
        TreeItem<String>  soust= new TreeItem<>("SOUSTRACTEUR", new ImageView(icon));
        TreeItem<String>  mux= new TreeItem<>("MULTIPLIXEUR", new ImageView(icon));
        TreeItem<String>  cmp= new TreeItem<>("COMPARATEUR", new ImageView(icon));
        TreeItem<String>  demux= new TreeItem<>("DEMULTIPLIXEUR", new ImageView(icon));
        TreeItem<String>  decod= new TreeItem<>("DECODEUR", new ImageView(icon));
        TreeItem<String>  encod= new TreeItem<>("ENCODEUR", new ImageView(icon));
        TreeItem<String>  trans= new TreeItem<>("TRANSCODEUR", new ImageView(icon));

        comp2.getChildren().addAll(add,soust,mux,cmp,demux,decod,encod,trans);
        /*************************************************************************/
        TreeItem<String>  jk= new TreeItem<>("bascule JK", new ImageView(icon));
        TreeItem<String>  rs= new TreeItem<>("bascule RS", new ImageView(icon));
        TreeItem<String>  rst= new TreeItem<>("bascule RST", new ImageView(icon));
        TreeItem<String>  d= new TreeItem<>("bascule D", new ImageView(icon));
        TreeItem<String>  horloge= new TreeItem<>("Horloge", new ImageView(icon));
        TreeItem<String>  t= new TreeItem<>("bascule T", new ImageView(icon));
        comp3.getChildren().addAll(jk,rs,rst,d,t,horloge);
        /*************************************************************************/
        composons.getChildren().addAll(comp1,comp2,comp3);
        tree.setRoot(composons);
// TODO
    }

}
