package ar.edu.itba;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Config {



    public static int TOTAL_PARTICLES;
    public static double SPACE_WIDTH;
    public static double MAX_T;

    public static double SMALL_P_MIN_MASS;
    public static double SMALL_P_MAX_MASS;
    public static double SMALL_P_MIN_RADIUS;
    public static double SMALL_P_MAX_RADIUS;
    public static double SMALL_P_MIN_VEL;
    public static double SMALL_P_MAX_VEL;

    public static double BIG_P_MASS;
    public static double BIG_P_VEL;
    public static double BIG_P_RADIUS;

    private static final String config_path = "./config.json";

    public static void init_config() {
        StringBuilder str_builder = new StringBuilder();
        try {
            Stream<String> stream = Files.lines(Paths.get(Config.config_path));
            stream.forEach(s -> str_builder.append(s).append('\n'));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        String content = str_builder.toString();
        JSONObject root = new JSONObject(content);
        TOTAL_PARTICLES = root.getInt("TOTAL_PARTICLES");
        SPACE_WIDTH = root.getDouble("SPACE_WIDTH");
        MAX_T = root.getDouble("MAX_T");

        JSONObject small = root.getJSONObject("SMALL_PARTICLES");
        SMALL_P_MIN_MASS = small.getDouble("MIN_MASS");
        SMALL_P_MAX_MASS = small.getDouble("MAX_MASS");
        SMALL_P_MIN_RADIUS = small.getDouble("MIN_RADIUS");
        SMALL_P_MAX_RADIUS = small.getDouble("MAX_RADIUS");
        SMALL_P_MIN_VEL = small.getDouble("MIN_VEL");
        SMALL_P_MAX_VEL = small.getDouble("MAX_VEL");

        JSONObject big = root.getJSONObject("BIG_PARTICLE");
        BIG_P_MASS = big.getDouble("MASS");
        BIG_P_RADIUS = big.getDouble("RADIUS");
        BIG_P_VEL = big.getDouble("VELOCITY");
    }

    private Config() {}
}
