package ar.edu.itba;

import ar.edu.itba.Collisions.Collision;
import ar.edu.itba.Collisions.CollisionType;
import ar.edu.itba.Collisions.ParticleCollision;
import ar.edu.itba.Collisions.WallCollision;

import java.util.*;

public class SimEvent2 {
    public Particle p1;
    public Particle p2;
    double t;
    CollisionType type;
    public SimEvent2(double t, Particle p1, Particle p2 , CollisionType type) {
        this.t = t;
        this.p1 = p1;
        this.p2 = p2;
        this.type = type;
    }

    static void fillCollisions(double space_width, List<Particle> particles, TreeSet<Collision> collisions, HashMap<Particle, List<Collision>> collisions_per_particle) {
        for (Particle p : particles) {
            //Pared vertical
            if (p.vel.x > 0) {
                double new_t = (space_width - p.radius - p.pos.x)/p.vel.x;
                Collision col = new WallCollision(new_t, p, WallCollision.WALL.RIGHT);
                put_collision(p, col, collisions_per_particle);
                collisions.add(col);
            } else if (p.vel.x < 0) {
                double new_t = ( p.radius - p.pos.x)/p.vel.x;
                Collision col = new WallCollision(new_t, p, WallCollision.WALL.LEFT);
                put_collision(p, col, collisions_per_particle);
                collisions.add(col);
            }

            //Pared horizontal
            if (p.vel.y > 0) {
                double new_t = (space_width - p.radius - p.pos.y)/p.vel.y;
                Collision col = new WallCollision(new_t, p, WallCollision.WALL.TOP);
                put_collision(p, col, collisions_per_particle);
                collisions.add(col);
            } else if (p.vel.y < 0) {
                double new_t = (p.radius - p.pos.y)/p.vel.y;
                Collision col = new WallCollision(new_t, p, WallCollision.WALL.BOT);
                put_collision(p, col, collisions_per_particle);
                collisions.add(col);
            }
            //check particle collisions
            for (int i = 0; i < particles.size(); i++) {
                for (int j =  i + 1; j < particles.size(); j++) {
                    Particle pi = particles.get(i);
                    Particle pj = particles.get(j);
                    if( checkX(pi , pj) && checkY(pi , pj)){
                        double new_t = calculateCollisionTime(pi , pj);
                        Collision col = new ParticleCollision(new_t, pi, pj);
                        collisions.add(col);
                        put_collision(pi, col, collisions_per_particle);
                        put_collision(pj, col, collisions_per_particle);
                    }
                }
            }
        }
    }

    public static void calculate_new_collisions(Particle p, double current_t, double space_width, TreeSet<Collision> collisions, HashMap<Particle, List<Collision>> collisions_per_particle, List<Particle> particles) {
        List<Collision> collisionList = collisions_per_particle.get(p);
        if (p.vel.x > 0) {
            double new_t = (space_width - p.radius - p.pos.x)/p.vel.x + current_t;
            Collision new_col = new WallCollision(new_t, p, WallCollision.WALL.RIGHT);
            collisions.add(new_col);
            collisionList.add(new_col);
        } else if (p.vel.x < 0) {
            double new_t = ( p.radius - p.pos.x)/p.vel.x + current_t;
            Collision new_col = new WallCollision(new_t, p, WallCollision.WALL.LEFT);
            collisions.add(new_col);
            collisionList.add(new_col);
        }

        //Pared horizontal
        if (p.vel.y > 0) {
            double new_t = (space_width - p.radius - p.pos.y)/p.vel.y + current_t;
            Collision new_col = new WallCollision(new_t, p, WallCollision.WALL.TOP);
            collisions.add(new_col);
            collisionList.add(new_col);
        } else if (p.vel.y < 0) {
            double new_t = (p.radius - p.pos.y)/p.vel.y + current_t;
            Collision new_col = new WallCollision(new_t, p, WallCollision.WALL.BOT);
            collisions.add(new_col);
            collisionList.add(new_col);
        }

        for (int j = 0; j < particles.size(); j++) {
            Particle pj = particles.get(j);
            Collision aux_col = new ParticleCollision(0, p, pj);
            if (collisions.contains(aux_col)) {
                continue;
            }
            if (p == pj) continue;
            if( checkX(p , pj) && checkY(p , pj)){
                double new_t = calculateCollisionTime(p , pj) + current_t;
                Collision col = new ParticleCollision(new_t, p, pj);
                collisions.add(col);
                put_collision(p, col, collisions_per_particle);
                put_collision(pj, col, collisions_per_particle);
            }
        }
    }

    public static double calculateCollisionTime(Particle p1 , Particle p2){
        Vector2D dR = p1.pos.sub(p2.pos);
        Vector2D dV = p1.vel.sub(p2.vel);
        double sigma = p1.radius + p2.radius;
        if (p1.pos.distance(p2.pos) < sigma) {
            System.out.println("ERROR: SUPERPUESTOS #########################################################");
        }
        double d = Math.pow(dV.dot(dR) , 2)  - dV.dot(dV)*(dR.dot(dR) - Math.pow(sigma , 2));
        if (d < 0 || dV.dot(dR) >= 0)
            return Double.POSITIVE_INFINITY;
        double aux = - (dV.dot(dR) + Math.sqrt(d) )/dV.dot(dV);
        return aux;
    }

    public static boolean checkX( Particle pa , Particle pb){
        if( Math.abs(pa.pos.x - pb.pos.x) < pa.radius + pb.radius )
            return true;
        return (pa.pos.x < pb.pos.x)?(pa.vel.x - pb.vel.x > 0) : (pb.vel.x - pa.vel.x > 0) ;
        /* codigo bien
        Particle p1;
        Particle p2;
        if(pa.pos.x < pb.pos.x){
            p1 = pa;
            p2 = pb;
        }else{
            p1 = pb;
            p2 = pa;
        }
        return p1.vel.x - p2.vel.x > 0;
        */
    }

    public static boolean checkY( Particle pa , Particle pb){
        if( Math.abs(pa.pos.y - pb.pos.y) < pa.radius + pb.radius )
            return true;
        return (pa.pos.y < pb.pos.y)?(pa.vel.y - pb.vel.y > 0) : (pb.vel.y - pa.vel.y > 0) ;
        /* codigo bien
        Particle p1;
        Particle p2;
        if(pa.pos.y < pb.pos.y){
            p1 = pa;
            p2 = pb;
        }else{
            p1 = pb;
            p2 = pa;
        }
        return p1.vel.y - p2.vel.y > 0;
        */
    }



//    static SimEvent2 next_event(double space_width, List<Particle> particles, TreeSet<Collision> collisions, HashMap<Particle, List<Collision>> collisions_per_particle) {
//        CollisionType type = CollisionType.NONE;
//        double min_t = Double.POSITIVE_INFINITY;
//        Particle p1 = null;
//        Particle p2 = null;
//        // Check Walls
//        for (Particle p : particles) {
//            //Pared vertical
//            if (p.vel.x > 0) {
//                double new_t = (space_width - p.radius - p.pos.x)/p.vel.x;
//                if (new_t < min_t) {
//                    min_t = new_t;
//                    p1 = p;
//                    type = CollisionType.VERTICAL_WALL;
//                }
//            } else if (p.vel.x < 0) {
//                double new_t = ( p.radius - p.pos.x)/p.vel.x;
//                if (new_t < min_t) {
//                    min_t = new_t;
//                    p1 = p;
//                    type = CollisionType.VERTICAL_WALL;
//                }
//            }
//
//            //Pared horizontal
//            if (p.vel.y > 0) {
//                double new_t = (space_width - p.radius - p.pos.y)/p.vel.y;
//                if (new_t < min_t) {
//                    min_t = new_t;
//                    p1 = p;
//                    type = CollisionType.HORIZONTAL_WALL;
//                }
//            } else if (p.vel.y < 0) {
//                double new_t = (p.radius - p.pos.y)/p.vel.y;
//                if (new_t < min_t) {
//                    min_t = new_t;
//                    p1 = p;
//                    type = CollisionType.HORIZONTAL_WALL;
//                }
//            }
//            //check particle collisions
//            for (int i = 0; i < particles.size(); i++) {
//                for (int j =  i + 1; j < particles.size(); j++) {
//                    Particle pi = particles.get(i);
//                    Particle pj = particles.get(j);
//                    if( checkX(pi , pj) && checkY(pi , pj)){
//                        double new_t = calculateCollisionTime(pi , pj);
//                        if(new_t < min_t){
//                            min_t = new_t;
//                            p1 = pi;
//                            p2 = pj;
//                            type = CollisionType.PARTICLE;
//                        }
//                    }
//                }
//            }
//        }
//
//
//        return new SimEvent2(min_t, p1, p2 , type);
//    }


    //Teorica_3 diapo 14


    public static void put_collision(Particle p, Collision col, HashMap<Particle, List<Collision>> collisions_per_particle) {
        List<Collision> collisions_of_p = collisions_per_particle.computeIfAbsent(p, k -> new LinkedList<>());
        collisions_of_p.add(col);
    }

}
