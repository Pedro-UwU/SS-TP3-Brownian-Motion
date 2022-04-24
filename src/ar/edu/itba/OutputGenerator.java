package ar.edu.itba;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OutputGenerator {
    private final static String DIRECTORY = "./results";

    //Returns folder

    public static String createStaticInfo(List<Particle> particles , String folder) {
        File dir = new File(DIRECTORY);
        dir.mkdir();
        if(folder == null){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss");
            LocalDateTime time = LocalDateTime.now();
            folder = dtf.format(time);
        }
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("total_particles" , particles.size());
        jsonObject.put("space_width" , Config.SPACE_WIDTH);
        JSONArray radius = new JSONArray();
        for (Particle p : particles) {
            radius.put(p.radius);
        }
        jsonObject.put("radius" , radius);
        String filePath = DIRECTORY + "/" + folder + "/static.json";
        File dir2 = new File(DIRECTORY + "/" + folder);
        dir2.mkdir();
        File myObj = new File(filePath);
        try {
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                FileWriter myWriter = new FileWriter(filePath );
                myWriter.write(jsonObject.toString());
                myWriter.close();
            }else{
                System.out.println("File not created");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return folder;
    }

    public static JSONArray saveSnapshot( List<Particle> particles , double time , JSONArray pastSnapshots){
        if(pastSnapshots == null){
            pastSnapshots = new JSONArray();
        }
        JSONObject snapshot = new JSONObject();
        snapshot.put("t" , time);
        JSONArray positions = new JSONArray();
        JSONArray vel = new JSONArray();
        for (Particle p : particles) {
            double[] p_vel = {p.vel.x , p.vel.y};
            double[] p_pos = {p.pos.x , p.pos.y};
            positions.put(p_pos);
            vel.put(p_vel);
        }
        snapshot.put("p" , positions);
        snapshot.put("v" , vel );
        pastSnapshots.put(snapshot);
        return  pastSnapshots;
    }

    public static void generateDynamic(JSONArray snapshots , String folder){
        JSONObject info = new JSONObject();
        info.put("info" , snapshots );
        String filePath = DIRECTORY + "/" + folder + "/snapshots.json";
        File myObj = new File(filePath);
        try {
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                FileWriter myWriter = new FileWriter(filePath );
                myWriter.write(info.toString());
                myWriter.close();
            }else{
                System.out.println("File not created");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
