package ar.edu.itba;

import com.sun.xml.internal.ws.wsdl.writer.document.Part;

import java.util.List;

public class SimEvent {
    public Particle p1;
    public Particle p2;
    double t;

    public SimEvent(double t, Particle p1, Particle p2) {
        this.t = t;
        this.p1 = p1;
        this.p2 = p2;
    }

    static SimEvent next_event(double space_width, List<Particle> particles) {
        double min_t = Double.POSITIVE_INFINITY;
        Particle p1 = null;
        Particle p2 = null;
        // Check Walls
        for (Particle p : particles) {
            //Horizontal
            if (p.vel.x > 0) {
                double new_t = (space_width - p.radius - p.pos.x)/p.vel.x;
                if (new_t < min_t) {
                    min_t = new_t;
                    p1 = p;
                }
            } else if (p.vel.x < 0) {
                double new_t = (space_width + p.radius - p.pos.x)/p.vel.x;
                if (new_t < min_t) {
                    min_t = new_t;
                    p1 = p;
                }
            }

            //Vertical
            if (p.vel.y > 0) {
                double new_t = (space_width - p.radius - p.pos.y)/p.vel.y;
                if (new_t < min_t) {
                    min_t = new_t;
                    p1 = p;
                }
            } else if (p.vel.y < 0) {
                double new_t = (space_width + p.radius - p.pos.y)/p.vel.y;
                if (new_t < min_t) {
                    min_t = new_t;
                    p1 = p;
                }
            }
        }

        for (int i = 0; i < particles.size(); i++) {
            for (int j =  i + 1; j < particles.size(); j++) {
                Particle pi = particles.get(i);
                Particle pj = particles.get(j);

                if (pi.pos.x < (pj.pos.x + pi.radius + pj.radius)) {
                    if (pi.vel.x <= pj.vel.x) continue;
                }

                if (pi.pos.y < (pj.pos.y + pi.radius + pj.radius)) {
                    if (pi)
                }
            }
        }

        return new SimEvent(min_t, p1, p2);
    }
}
