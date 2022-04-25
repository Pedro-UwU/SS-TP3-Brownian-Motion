package ar.edu.itba.Collisions;

import ar.edu.itba.Particle;

import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public abstract class Collision implements Comparable<Collision> {
    public double t;
    public CollisionType type;

    public Collision(double t, CollisionType type) {
        this.t = t;
        this.type = type;
    }

    public boolean shouldEnd(Particle big_p) {
        return false;
    }

    public void collide() {}
    public void clear_collisions(TreeSet<Collision> collisions, HashMap<Particle, List<Collision>> collisions_per_particle) {}
    public void save_new_collisions(double current_t, double space_width, TreeSet<Collision> collisions, HashMap<Particle,List<Collision>> collisions_per_particle, List<Particle> particles) {}

    @Override
    public int compareTo(Collision other) {
        if (this.equals(other)) {
            return 0;
        }
        if (this.t > other.t) {
            return 1;
        }
        return -1;
    }
}
