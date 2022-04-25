package ar.edu.itba;

import com.sun.xml.internal.ws.wsdl.writer.document.Part;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.Config.init_config;

public class Simulation {
    public static void main(String[] args) {
        init_config();
        Particle big_particle = new Particle(Config.SPACE_WIDTH/2, Config.SPACE_WIDTH/2, 0, 0, Config.BIG_P_RADIUS, Config.BIG_P_MASS);
        List<Particle> particles = new ArrayList<>();
        particles.add(big_particle);
        particles = Generator.generate(Config.TOTAL_PARTICLES, Config.SPACE_WIDTH, Config.SMALL_P_MIN_RADIUS, Config.SMALL_P_MAX_RADIUS, Config.SMALL_P_MIN_VEL, Config.SMALL_P_MAX_VEL, Config.SMALL_P_MIN_MASS, Config.SMALL_P_MAX_MASS, particles);
        //List<Particle> particles = new ArrayList<>();
        for (Particle p : particles) {
            System.out.println(p);
        }
        String folder = OutputGenerator.createStaticInfo(particles , Config.SIM_NAME);
        double t = 0;
        double max_t = Config.MAX_T;
        JSONArray snapshots = OutputGenerator.saveSnapshot( particles , t , CollisionType.NONE , null);
        while (t < max_t) {
            SimEvent event = SimEvent.next_event(Config.SPACE_WIDTH , particles);
            if( event.t + t >= max_t){
                for (Particle p : particles) {
                    p.update(max_t - t);
                }
                snapshots = OutputGenerator.saveSnapshot( particles , max_t , CollisionType.NONE , snapshots);
                break;
            }

            for (Particle p : particles) {
                p.update(event.t);
            }
            collisionOperator(event);
            t+= event.t;
            snapshots = OutputGenerator.saveSnapshot( particles , t , event.type, snapshots);
            System.out.println("Current T: " + t);
        }
        OutputGenerator.generateDynamic(snapshots , folder);
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
