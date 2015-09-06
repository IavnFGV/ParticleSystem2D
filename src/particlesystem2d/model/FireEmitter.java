package particlesystem2d.model;

import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

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
            Particle p =  new Particle(x,y,new Point2D(Math.random()-0.5,Math.random()*-4),
                    5,
                    1.,
                    Color.rgb(230,40,45),
                    BlendMode.ADD);
            particles.add(p);
        }
        return particles;
    }
}
