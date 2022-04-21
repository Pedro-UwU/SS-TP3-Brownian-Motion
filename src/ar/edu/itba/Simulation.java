package ar.edu.itba;

import java.util.List;

import static ar.edu.itba.Config.init_config;

public class Simulation {
    public static void main(String[] args) {
        init_config();
        List<Particle> particles = Generator.generate(Config.TOTAL_PARTICLES, Config.SPACE_WIDTH, Config.SMALL_P_MIN_RADIUS, Config.SMALL_P_MAX_RADIUS, Config.SMALL_P_MIN_VEL, Config.SMALL_P_MAX_VEL, Config.SMALL_P_MIN_MASS, Config.SMALL_P_MAX_MASS);
        for (Particle p : particles) {
            System.out.println(p);
        }
        double t = 0;
        double max_t = Config.MAX_T;

        while (t < max_t) {

        }
    }
}
