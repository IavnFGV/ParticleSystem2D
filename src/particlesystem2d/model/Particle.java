package particlesystem2d.model;

import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import particlesystem2d.Utils;

/**
 * Created by GFH on 06.09.2015.
 */
public class Particle {

    private static Image[] images = Utils.preCreateImages();
    private double x;
    private double y;

    private Point2D velocity;

    private double radius;

    private double life = 1.;
    private Paint color;
    private BlendMode blendMode;
    private double decay;
    private int lifeSpan = 255;

    public Particle(double x,
                    double y,
                    Point2D velocity,
                    double radius,
                    double expireTime,
                    Paint color,
                    BlendMode blendMode) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.radius = radius;
        this.decay = 0.016 / expireTime;
        this.color = color;
        this.blendMode = blendMode;
    }

    public void update() {
        x += velocity.getX();
        y += velocity.getY();
        life -= decay;
        lifeSpan--;
    }

    public boolean isAlive() {
        //return life > 0;
        return lifeSpan > 0;
    }

    public void render(GraphicsContext g) {

           g.setGlobalAlpha(life);
       //   setGlobalBlendMode(blendMode);
          g.setFill(color);
         g.fillOval(x, y, radius, radius);
        g.drawImage(images[lifeSpan], x, y);
    }
}
