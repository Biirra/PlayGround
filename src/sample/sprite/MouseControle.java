package sample.sprite;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sample.MouseGestures;

/**
 * Created by JanWillem Huising on 16-7-2017.
 */
public class MouseControle {
    final DragContext dragContext = new DragContext();

    public void makeDragable(Sprite sprite){
        sprite.setOnMousePressed(onMousePressedEventHandler);
        sprite.setOnMouseDragged(onMouseDraggedEventHandler);
        sprite.setOnMouseReleased(onMouseReleasedEventHandler);
    }

    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            dragContext.x = event.getSceneX();
            dragContext.y = event.getSceneY();
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

        }
    };

    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

        }
    };

    class DragContext {

        double x;
        double y;

    }

}
