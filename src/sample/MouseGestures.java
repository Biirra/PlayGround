package sample;

/**
 * Created by JanWillem Huising on 11-7-2017.
 */
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sample.sprite.Sprite;


public class MouseGestures {

    final DragContext dragContext = new DragContext();

    public void makeDraggable(final Sprite sprite) {
        sprite.setOnMousePressed(onMousePressedEventHandler);
        sprite.setOnMouseDragged(onMouseDraggedEventHandler);
        sprite.setOnMouseReleased(onMouseReleasedEventHandler);
    }
    public void makeUnDraggable(final Sprite sprite) {
        sprite.setOnMousePressed(null);
        sprite.setOnMouseDragged(null);
        sprite.setOnMouseReleased(null);
    }

    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            Sprite sprite = (Sprite) event.getSource();

            dragContext.x = event.getSceneX();
            dragContext.y = event.getSceneY();
            sprite.removeEventHandler(event.MOUSE_PRESSED, this);
        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            Sprite sprite = (Sprite) event.getSource();

            float offsetX = (float)event.getSceneX() - (float)dragContext.x;
            float offsetY = (float)event.getSceneY() - (float)dragContext.y;

            sprite.setLocationOffset(offsetX, offsetY);

            dragContext.x = event.getSceneX();
            dragContext.y = event.getSceneY();

            sprite.removeEventHandler(event.MOUSE_DRAGGED, this);
        }
    };

    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            Sprite sprite = (Sprite) event.getSource();

            sprite.removeEventHandler(event.MOUSE_RELEASED, this);
        }
    };

    class DragContext {

        double x;
        double y;

    }

}