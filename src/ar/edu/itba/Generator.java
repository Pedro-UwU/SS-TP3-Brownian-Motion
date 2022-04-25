package ar.edu.itba;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    private static final int MAX_ATTEMPTS = 10000;

    public static List<Particle> generate(int total_particles, double space_width, double min_radius, double max_radius, double min_vel, double max_vel, double min_mass, double max_mass) {
        List<Particle> particles = new ArrayList<>();
        generate(total_particles, space_width, min_radius, max_radius, min_vel, max_vel, min_mass, max_mass, particles);
        return particles;
    }

    public static List<Particle> generate(int total_particles, double space_width, double min_radius, double max_radius, double min_vel, double max_vel, double min_mass, double max_mass, List<Particle> particles) {
        for (int i = 0; i < total_particles; i++) {
            boolean suitable;
            int attempts = 0;
            Vector2D pos;
            Vector2D vel;
            double radius;
            double mass;
            do {
                suitable = true;
                pos = Vector2D.generate_random(0, space_width, 0, space_width);
                radius = (Math.random() * (max_radius - min_radius)) + min_radius;
                for (int j = 0; j < particles.size(); j++) {
                    if (pos.distance(particles.get(j).pos) < (radius + particles.get(j).radius)) {
                        suitable = false;
                    }
                    if (suitable && pos.x < radius || pos.x > (space_width-radius) || pos.y < radius || pos.y > (space_width-radius)) {
                        suitable = false;
                    }
                }
                attempts++;
            } while (!suitable && attempts < MAX_ATTEMPTS);
            if (attempts >= MAX_ATTEMPTS) {
                throw new RuntimeException("ERROR: Can't generate particles, couldn't find suitable space to generate new particle");
            }
            double vel_mag = (Math.random() * (max_vel - min_vel)) + min_vel;
            double angle = Math.random() * 2 * Math.PI;
            vel = new Vector2D(vel_mag, 0).rotate(angle);
            mass = (Math.random() * (max_mass - min_mass)) + min_mass;
            Particle p = new Particle(pos, vel, radius, mass);
            particles.add(p);
        }
        return particles;
    }
}
