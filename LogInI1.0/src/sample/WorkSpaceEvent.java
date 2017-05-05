package sample;

import sample.Ports.ComposantLogique;

/**
 * Created by asus on 17/03/2017.
 * @Author AMOKRANE Abdennour
 */
public class WorkSpaceEvent {

    public sample.Fil getFil() {
        return Fil;
    }

    public void setFil(sample.Fil fil) {
        Fil = fil;
    }

    public ComposantLogique getComposant() {
        return Composant;
    }

    public void setComposant(ComposantLogique composant) {
        Composant = composant;
    }

    public double getDeplacementX() {
        return deplacementX;
    }

    public void setDeplacementX(double deplacementX) {
        this.deplacementX = deplacementX;
    }

    public double getDeplacementY() {
        return deplacementY;
    }

    public void setDeplacementY(double deplacementY) {
        this.deplacementY = deplacementY;
    }

    enum EventSpecifier {
        COMPOSANT,
        FIL,
        NOEUD,
    }

    enum TypeEvent {
        CREATION,
        SUPRESSION,
        MODIFICATION,
        MOUVEMENT,
    }
    private TypeEvent TypeEvent;
    private EventSpecifier Specifier;
    private ComposantLogique Composant;

    // des attributs pour le mouvement
    private double deplacementX;
    private double deplacementY;
    //--------------------------------
    private Fil Fil=new Fil();

    public TypeEvent getTypeEvent() {
        return TypeEvent;
    }

    public void setTypeEvent(TypeEvent typeEvent) {
        TypeEvent = typeEvent;
    }

    public EventSpecifier getSpecifier() {
        return Specifier;
    }

    public void setSpecifier(EventSpecifier specifier) {
        Specifier = specifier;
    }

}
