package ar.edu.itba.Collisions;

import ar.edu.itba.Particle;
import ar.edu.itba.SimEvent2;
import ar.edu.itba.Vector2D;

import java.util.*;

public class ParticleCollision extends Collision{
    Particle p1, p2;
    public ParticleCollision(double t, Particle p1, Particle p2) {
        super(t, CollisionType.PARTICLE);
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticleCollision other = (ParticleCollision) o;
        return p1.equals(other.p1) && p2.equals(other.p2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p1, p2);
    }

    public void collide() {
        double sigma = p1.radius + p2.radius;
        Vector2D dR = p2.pos.sub(p1.pos);
        Vector2D dV = p2.vel.sub(p1.vel);
        double j = 2*p1.mass*p2.mass*dV.dot(dR)/(sigma*(p1.mass + p2.mass));
        double jx = j * dR.x / sigma;
        double jy = j * dR.y / sigma;

        p1.vel = new Vector2D(p1.vel.x + jx/p1.mass , p1.vel.y + jy/ p1.mass);
        p2.vel = new Vector2D(p2.vel.x - jx/p2.mass , p2.vel.y - jy/ p2.mass);
    }

    public void clear_collisions(TreeSet<Collision> collisions, HashMap<Particle, List<Collision>> collisions_per_particle) {
        for (Collision collision : collisions_per_particle.get(p1)) {
            collisions.remove(collision);
        }
        collisions_per_particle.put(p1, new LinkedList<>());
        for (Collision collision : collisions_per_particle.get(p2)) {
            collisions.remove(collision);
        }
        collisions_per_particle.put(p2, new LinkedList<>());
    }

    public void save_new_collisions(double current_t, double space_width, TreeSet<Collision> collisions, HashMap<Particle,List<Collision>> collisions_per_particle, List<Particle> particles) {
        SimEvent2.calculate_new_collisions(p1, current_t, space_width, collisions, collisions_per_particle, particles);
        SimEvent2.calculate_new_collisions(p2, current_t, space_width, collisions, collisions_per_particle, particles);
    }




}
