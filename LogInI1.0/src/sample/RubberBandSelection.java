package sample;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

/**
 * Created by asus on 14/04/2017.
 */
public class RubberBandSelection {
    final DragContext dragContext = new DragContext();
    Rectangle rect;

    Pane group;

    public RubberBandSelection( Pane group) {

        this.group = group;

        rect = new Rectangle( 0,0,0,0);
        rect.setStroke(Color.LIGHTBLUE.darker());
        rect.setStrokeWidth(1);
        rect.setStrokeLineCap(StrokeLineCap.ROUND);
        rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.4));

        group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

    }

    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY && event.getTarget().getClass() != ImageView.class) {
                dragContext.mouseAnchorX = event.getX();
                dragContext.mouseAnchorY = event.getY();

                rect.setX(dragContext.mouseAnchorX);
                rect.setY(dragContext.mouseAnchorY);
                rect.setWidth(0);
                rect.setHeight(0);

                group.getChildren().add( rect);

                event.consume();

            }

        }
    };

    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            rect.setX(0);
            rect.setY(0);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().remove( rect);

            event.consume();

        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            double offsetX = event.getX() - dragContext.mouseAnchorX;
            double offsetY = event.getY() - dragContext.mouseAnchorY;

            if( offsetX > 0)
                rect.setWidth( offsetX);
            else {
                rect.setX(event.getX());
                rect.setWidth(dragContext.mouseAnchorX - rect.getX());
            }

            if( offsetY > 0) {
                rect.setHeight( offsetY);
            } else {
                rect.setY(event.getY());
                rect.setHeight(dragContext.mouseAnchorY - rect.getY());
            }

            event.consume();

        }
    };

    private final class DragContext {

        public double mouseAnchorX;
        public double mouseAnchorY;


    }
}
