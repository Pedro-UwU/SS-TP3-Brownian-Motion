package ar.edu.itba;

import ar.edu.itba.Collisions.CollisionType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OutputGenerator {

    private final static Integer CHUNK_SIZE = 1000;
    private final static String DIRECTORY = "./results";
    private static FileWriter SNAPSHOT_WRITER;
    private static boolean comma = false;
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

    public static List<JSONObject> saveSnapshot(List<Particle> particles , double time , CollisionType collision, List<JSONObject> pastSnapshots , String folder){
        if(pastSnapshots == null){
            pastSnapshots = new ArrayList<>();
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
        snapshot.put("c" , collision.ordinal());
        pastSnapshots.add(snapshot);
        if(pastSnapshots.size() > CHUNK_SIZE){
            generateDynamic(pastSnapshots);
            pastSnapshots = null;
        }
        return  pastSnapshots;
    }


    public static void initializeDynamicWriter(String folder){

        String filePath = DIRECTORY + "/" + folder + "/snapshots.json";
        File myObj = new File(filePath);
        try {
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                SNAPSHOT_WRITER = new FileWriter(filePath);
                SNAPSHOT_WRITER.write("{\"info\":[");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void closeDynamicWriter(){
        try {
                SNAPSHOT_WRITER.write("]}");
                SNAPSHOT_WRITER.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void generateDynamic(List<JSONObject> snapshots){
        if(snapshots == null || snapshots.size() == 0)
            return;
        try {
            for( JSONObject o : snapshots) {
                if(comma){
                    SNAPSHOT_WRITER.write(",");
                }
                comma = true;
                SNAPSHOT_WRITER.write(o.toString());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
