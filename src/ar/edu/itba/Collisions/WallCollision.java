package ar.edu.itba.Collisions;

import ar.edu.itba.Particle;
import ar.edu.itba.SimEvent2;
import ar.edu.itba.Vector2D;

import java.util.*;

public class WallCollision extends Collision{
    Particle p;
    WALL w;
    public WallCollision(double t, Particle p, WALL w) {
        super(t, CollisionType.VERTICAL_WALL);
        if (w.ordinal() > WALL.BOT.ordinal()){
            this.type = CollisionType.HORIZONTAL_WALL;
        }
        this.p = p;
        this.w = w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WallCollision other = (WallCollision) o;
        return p.equals(other.p) && w == other.w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(p, w);
    }

    public enum WALL {
        TOP,
        BOT,
        LEFT,
        RIGHT
    }

    public void collide() {
        Vector2D pVel = p.vel;
        if (w == WALL.BOT || w == WALL.TOP) {
            p.vel = new Vector2D(pVel.x , -pVel.y);
        } else {
            p.vel = new Vector2D(-pVel.x , pVel.y);
        }
    }

    public void clear_collisions(TreeSet<Collision> collisions, HashMap<Particle, List<Collision>> collisions_per_particle) {
        for (Collision collision : collisions_per_particle.get(p)) {
            collisions.remove(collision);
        }
        collisions_per_particle.put(p, new LinkedList<>());
    }

    public void save_new_collisions(double current_t, double space_width, TreeSet<Collision> collisions, HashMap<Particle,List<Collision>> collisions_per_particle, List<Particle> particles) {
        SimEvent2.calculate_new_collisions(p, current_t, space_width, collisions, collisions_per_particle, particles);
    }

    @Override
    public boolean shouldEnd(Particle big_p) {
        return p == big_p;
    }

}


