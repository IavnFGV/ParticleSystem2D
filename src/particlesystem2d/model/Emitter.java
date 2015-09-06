package particlesystem2d.model;

import java.util.List;

/**
 * Created by GFH on 06.09.2015.
 */
public abstract class Emitter {
    public abstract List<Particle> emit(double x,double y);
}
