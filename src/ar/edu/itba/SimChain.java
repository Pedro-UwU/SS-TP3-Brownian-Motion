package ar.edu.itba;

public class SimChain {
    public static void main(String[] args) {
        int total = 5;
        Config.init_config();
        String original_name = Config.SIM_NAME;
        int[] sizes = {105, 120, 135};
        for (int j = 0; j < sizes.length; j++) {
            for (int i = 0; i < total; i++) {
                Config.SIM_NAME = original_name + "_" + sizes[j] + "_" + i;
            }
        }
    }
}
