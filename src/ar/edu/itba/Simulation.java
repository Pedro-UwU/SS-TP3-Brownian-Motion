package ar.edu.itba;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.Config.init_config;

public class Simulation {
    public static void main(String[] args) {
        init_config();
        //List<Particle> particles = Generator.generate(Config.TOTAL_PARTICLES, Config.SPACE_WIDTH, Config.SMALL_P_MIN_RADIUS, Config.SMALL_P_MAX_RADIUS, Config.SMALL_P_MIN_VEL, Config.SMALL_P_MAX_VEL, Config.SMALL_P_MIN_MASS, Config.SMALL_P_MAX_MASS);
        List<Particle> particles = new ArrayList<>();
        particles.add(new Particle( 10 , 10.2 , 1.5 , 0 , 1 , 1 ));
        particles.add(new Particle( 20 , 10 , -1.5 , 0 , 1 , 1 ));
        for (Particle p : particles) {
            System.out.println(p);
        }
        String folder = OutputGenerator.createStaticInfo(particles , "testChoque");
        double t = 0;
        double max_t = Config.MAX_T;
        JSONArray snapshots = OutputGenerator.saveSnapshot( particles , t , null);
        while (t < max_t) {
            SimEvent event = SimEvent.next_event(Config.SPACE_WIDTH , particles);
            for (Particle p : particles) {
                p.update(event.t);
            }
            collisionOperator(event);
            t+= event.t;
            snapshots = OutputGenerator.saveSnapshot( particles , t , snapshots);
        }
        OutputGenerator.generateDynamic(snapshots , folder);
    }

    private static void collisionOperator( SimEvent e){
        Vector2D p1Vel = e.p1.vel;
        switch (e.type){
            case HORIZONTAL_WALL:
                System.out.println("wall");
                e.p1.vel = new Vector2D(p1Vel.x , -p1Vel.y);
                break;
            case VERTICAL_WALL:
                System.out.println("wall");
                e.p1.vel = new Vector2D(-p1Vel.x  , p1Vel.y);
                break;
            case PARTICLE:
                System.out.println("collision");
                System.out.println(e.p1 + " " + e.p2);
                collisionBetweenParticles(e.p1 , e.p2);
                System.out.println(e.p1 + " " + e.p2);

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
