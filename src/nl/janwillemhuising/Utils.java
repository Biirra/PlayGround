package nl.janwillemhuising;

/**
 * Created by JanWillem Huising on 10-7-2017.
 */
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import nl.janwillemhuising.model.PVector;

import java.util.Random;


public class Utils {

    public static float map(float value, float currentRangeStart, float currentRangeStop, float targetRangeStart, float targetRangeStop) {
        return targetRangeStart + (targetRangeStop - targetRangeStart) * ((value - currentRangeStart) / (currentRangeStop - currentRangeStart));
    }

    /**
     * Create an imageview of a right facing arrow.
     * @param size The width. The height is calculated as width / 2.0.
     * @return
     */
    public static ImageView createArrowImageView( double size) {

        return createArrowImageView(size, size / 2.0, Color.BLUE, Color.BLUE.deriveColor(1, 1, 1, 0.3), 1);

    }

    /**
     * Create an imageview of a right facing arrow.
     * @param width
     * @param height
     * @return
     */
    public static ImageView createArrowImageView( double width, double height, Paint stroke, Paint fill, double strokeWidth) {

        return new ImageView( createArrowImage(width, height, stroke, fill, strokeWidth));

    }

    /**
     * Create an image of a right facing arrow.
     * @param width
     * @param height
     * @return
     */
    public static Image createArrowImage( double width, double height, Paint stroke, Paint fill, double strokeWidth) {

        WritableImage wi;

        double arrowWidth = width - strokeWidth * 2;
        double arrowHeight = height - strokeWidth * 2;

        Polygon arrow = new Polygon( 0, 0, arrowWidth, arrowHeight / 2, 0, arrowHeight); // left/right lines of the arrow
        arrow.setStrokeLineJoin(StrokeLineJoin.MITER);
        arrow.setStrokeLineCap(StrokeLineCap.SQUARE);
        arrow.setStroke(stroke);
        arrow.setFill(fill);
        arrow.setStrokeWidth(strokeWidth);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        int imageWidth = (int) width;
        int imageHeight = (int) height;

        wi = new WritableImage( imageWidth, imageHeight);
        arrow.snapshot(parameters, wi);

        return wi;

    }
    public static Line drawLine(PVector start, PVector end){
        return new Line(start.getX(),start.getY(), end.getX(), end.getY());
    }

    public static float randomFloat(float min, float max) {
        Random rand = new Random();
        return rand.nextFloat() * (max - min) + min;
    }

}