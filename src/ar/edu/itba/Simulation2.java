package ar.edu.itba;

import ar.edu.itba.Collisions.Collision;
import ar.edu.itba.Collisions.CollisionType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import static ar.edu.itba.Config.init_config;

public class Simulation2 {
    static List<Particle> particles = new ArrayList<>();
    static TreeSet<Collision> collisions = new TreeSet<>();
    static HashMap<Particle, List<Collision>> collisions_per_particle = new HashMap<>();


    public static void main(String[] args) {
        init_config();
        Particle big_particle = new Particle(Config.SPACE_WIDTH/2, Config.SPACE_WIDTH/2, 0, 0, Config.BIG_P_RADIUS, Config.BIG_P_MASS);
        particles.add(big_particle);
        particles = Generator.generate(Config.TOTAL_PARTICLES, Config.SPACE_WIDTH, Config.SMALL_P_MIN_RADIUS, Config.SMALL_P_MAX_RADIUS, Config.SMALL_P_MIN_VEL, Config.SMALL_P_MAX_VEL, Config.SMALL_P_MIN_MASS, Config.SMALL_P_MAX_MASS, particles);
        //List<Particle> particles = new ArrayList<>();
//        Particle little_p = new Particle(new Vector2D(0.3, 3), new Vector2D(-1, 0), 0.2, 0.9);
//        Particle little_p2 =  new Particle(new Vector2D(5.3, 3), new Vector2D(-0.05, 0), 0.2, 0.9);
//        particles.add(little_p);
//        particles.add(little_p2);
        for (Particle p : particles) {
            System.out.println(p);
        }
        String folder = OutputGenerator.createStaticInfo(particles , Config.SIM_NAME);
        OutputGenerator.initializeDynamicWriter(folder);
        double t = 0;
        double max_t = Config.MAX_T;
        List<JSONObject> snapshots = OutputGenerator.saveSnapshot( particles , t , CollisionType.NONE , null , folder);
        SimEvent2.fillCollisions(Config.SPACE_WIDTH, particles, collisions, collisions_per_particle);
        while (t < max_t) {
            Collision next_event = collisions.first();//SimEvent2.next_event(Config.SPACE_WIDTH , particles, collisions, collisions_per_particle);
            if (next_event.t < t) {
                System.out.println("ERRIRRRRRRRRRRRRRRRRRRRRRRRRR");
                System.out.println(next_event.type);
            }
            if(next_event.t >= max_t){
                for (Particle p : particles) {
                    p.update(max_t - t);
                }
                snapshots = OutputGenerator.saveSnapshot( particles , max_t , CollisionType.NONE , snapshots , folder);
                break;
            }

            for (Particle p : particles) {
                p.update(next_event.t - t);
            }
            //collisionOperator(next_event);
            next_event.collide();
            t = next_event.t;
            next_event.clear_collisions(collisions, collisions_per_particle);
            next_event.save_new_collisions(t, Config.SPACE_WIDTH, collisions, collisions_per_particle, particles);
            snapshots = OutputGenerator.saveSnapshot( particles , t , next_event.type, snapshots , folder);
            System.out.println("Current T: " + t);
            if (next_event.shouldEnd(big_particle)) {
                break;
            }
        }
        OutputGenerator.generateDynamic(snapshots);
        OutputGenerator.closeDynamicWriter();
    }

    private static void collisionOperator( SimEvent e){
        Vector2D p1Vel = e.p1.vel;
        switch (e.type){
            case HORIZONTAL_WALL:
                e.p1.vel = new Vector2D(p1Vel.x , -p1Vel.y);
                break;
            case VERTICAL_WALL:

                e.p1.vel = new Vector2D(-p1Vel.x  , p1Vel.y);
                break;
            case PARTICLE:

                collisionBetweenParticles(e.p1 , e.p2);

                break;
        }
    }

    private static void collisionBetweenParticles(Particle p1 , Particle p2){
        double sigma = p1.radius + p2.radius;
        Vector2D dR = p2.pos.sub(p1.pos);
        Vector2D dV = p2.vel.sub(p1.vel);
        double j = 2*p1.mass*p2.mass*dV.dot(dR)/(sigma*(p1.mass + p2.mass));
        double jx = j * dR.x / sigma;
        double jy = j * dR.y / sigma;

        p1.vel = new Vector2D(p1.vel.x + jx/p1.mass , p1.vel.y + jy/ p1.mass);
        p2.vel = new Vector2D(p2.vel.x - jx/p2.mass , p2.vel.y - jy/ p2.mass);

    }
}
