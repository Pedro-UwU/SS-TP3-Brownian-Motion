package ar.edu.itba;

public class SimChain {
    public static void main(String[] args) {
        int total = 5;
        Config.init_config();
        String original_name = Config.SIM_NAME;
        int[] sizes = {135, 120, 105};
        for (int j = 0; j < sizes.length; j++) {
            for (int i = 0; i < total; i++) {
                System.out.println(original_name + "_" + sizes[j] + "_" + i);
                Config.SIM_NAME = original_name + "_" + sizes[j] + "_" + i;
                Config.TOTAL_PARTICLES = sizes[j];
                while(simulationTry()){};
            }
        }
    }

    public static boolean simulationTry(){
        try{
            Simulation.simulate();
            return false;
        }catch (RuntimeException e){
            System.out.println("error");
            return true;
        }
    }
}
