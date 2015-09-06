package particlesystem2d.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by GFH on 06.09.2015.
 */
public class FireEmitter extends Emitter {
    @Override
    public List<Particle> emit(double x, double y) {
        List<Particle> particles = new LinkedList<>();
        int numParticles = 7;
        for (int i = 0; i < numParticles; i++) {
            Particle p = new Particle(x, y, new Point2D(Math.random() + getxDist(), Math.random() * getyDist()),
                    getRadius(),
                    getExpireTime(),
                    getColor(),
                    getBlendMode()
            );
            particles.add(p);
        }
        return particles;
    }

    private ObjectProperty<BlendMode> blendMode = new SimpleObjectProperty<>(BlendMode.ADD);

    public BlendMode getBlendMode() {
        return blendMode.get();
    }

    public ObjectProperty<BlendMode> blendModeProperty() {
        return blendMode;
    }

    public void setBlendMode(BlendMode blendMode) {
        this.blendMode.set(blendMode);
    }

    public Paint getColor() {
        return color.get();
    }

    public ObjectProperty<Paint> colorProperty() {
        return color;
    }

    public void setColor(Paint color) {
        this.color.set(color);
    }

    private ObjectProperty<Paint> color = new SimpleObjectProperty<>(Color.rgb(230, 40, 45));

    public double getExpireTime() {
        return expireTime.get();
    }

    public DoubleProperty expireTimeProperty() {
        return expireTime;
    }

    public void setExpireTime(double expireTime) {
        this.expireTime.set(expireTime);
    }

    private DoubleProperty expireTime = new SimpleDoubleProperty(1);

    private DoubleProperty radius = new SimpleDoubleProperty(5);

    public double getRadius() {
        return radius.get();
    }

    public DoubleProperty radiusProperty() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius.set(radius);
    }

    private DoubleProperty yDist = new SimpleDoubleProperty(-4);

    public double getyDist() {
        return yDist.get();
    }

    public DoubleProperty yDistProperty() {
        return yDist;
    }

    public void setyDist(double yDist) {
        this.yDist.set(yDist);
    }

    private DoubleProperty xDist = new SimpleDoubleProperty(-0.5);

    public double getxDist() {
        return xDist.get();
    }

    public DoubleProperty xDistProperty() {
        return xDist;
    }

    public void setxDist(double xDist) {
        this.xDist.set(xDist);
    }

}
