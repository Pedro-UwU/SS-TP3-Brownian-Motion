package ar.edu.itba;

public class SimChainVels {
    public static void main(String[] args) {
        int total = 1;
        Config.init_config();
        String original_name = Config.SIM_NAME;
        double[] max_vels = {1 , 1.5 };
        for (int j = 0; j < max_vels.length; j++) {
            for (int i = 0; i < total; i++) {
                System.out.println(original_name + "_" + (int) (max_vels[j]*10) + "_" + i);
                Config.SIM_NAME = original_name + "_" +(int) (max_vels[j]*10) + "_" + i;
                Config.SMALL_P_MAX_VEL = max_vels[j];
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
