String CONFIG_LOCATION = "../config_visualization.json";
String SIM_NAME;
Boolean SEE_PATH = true;
int snap_number = 0;

float space_width;
float[] particles_radius;
int total_particles;
int frame_rate;
JSONArray snapshots;

ArrayList<PVector> path;

boolean temp = true;
int seconds = 0;
int frame_in_second = 0;
boolean first = true;
Snap prev, current;

void setup() {
  size(800, 800);
  background(0);

  JSONObject config = loadJSONObject(CONFIG_LOCATION);
  SIM_NAME = config.getString("SIMULATION_NAME");
  frame_rate = config.getInt("FRAME_RATE");
  path = new ArrayList<>();

  JSONObject dynamic_data = loadJSONObject("../results/" + SIM_NAME + "/snapshots.json");
  JSONObject static_data = loadJSONObject("../results/" + SIM_NAME + "/static.json");

  space_width = static_data.getFloat("space_width");
  total_particles = static_data.getInt("total_particles");
  particles_radius = new float[total_particles];
  JSONArray rad_json = static_data.getJSONArray("radius");
  for (int i = 0; i < total_particles; i++) {
    particles_radius[i] = rad_json.getFloat(i);
  }

  snapshots = dynamic_data.getJSONArray("info");


  frameRate(frame_rate);
}

void draw() {
  fill(51, 200);
  noStroke();
  rect(0, 0, width, height);
  println("frame count: " + frameCount);
  
  if (snap_number >= snapshots.size()) {
    noLoop();
    return;
  }
  
  

  JSONObject data = snapshots.getJSONObject(snap_number++);
  current = new Snap(data.getFloat("t"), data.getJSONArray("p"), data.getJSONArray("v"));
  if (first) {
    first = false;
    
  } else {
    float current_t = seconds + (frame_in_second * 1.0/frame_rate);
    while (snap_number <= snapshots.size() && data.getFloat("t") < current_t) {
      data = snapshots.getJSONObject(snap_number++);
      current = new Snap(data.getFloat("t"), data.getJSONArray("p"), data.getJSONArray("v"));
    }
  }
  if (SEE_PATH) {
    path.add(current.pos[0]);
  }
  drawSnap(current);
  
  
  frame_in_second++;
  if (frame_in_second >= frame_rate) {
    seconds++;
    frame_in_second = 0;
  }
  
  if (SEE_PATH) {
    for (int i = 1; i < path.size(); i++) {
      PVector prev = path.get(i-1);
      PVector current = path.get(i);
      stroke(0, 0, 255);
      strokeWeight(5);
      float prevX = map(prev.x, 0, space_width, 0, width);
      float prevY = map(prev.y, 0, space_width, 0, height);
      float currX = map(current.x, 0, space_width, 0, width);
      float currY = map(current.y, 0, space_width, 0, height);
      line(prevX, prevY, currX, currY);
    }
  }
  
  saveFrame("results/"+SIM_NAME+"/data/######.png");

  //background(0);
  //println(snap_number);
  //JSONObject snap = snapshots.getJSONObject(snap_number);
  //float t = snap.getFloat("t");
  //JSONArray pos = snap.getJSONArray("p");
  //JSONArray vel = snap.getJSONArray("v");
  //for (int i = 0; i < pos.size(); i++) {
  //  JSONArray current_pos = pos.getJSONArray(i);
  //  JSONArray current_vel = vel.getJSONArray(i);
  //  drawParticle(current_pos.getFloat(0), current_pos.getFloat(1), 0.2);
  //}
}

void drawParticle(float x, float y, float radius, color col) {
  float new_x = map(x, 0, space_width, 0, width);
  float new_y = map(y, 0, space_width, 0, height);
  float rad = 2 * radius * (width/space_width);
  //println(x + " - " + y + " - " + radius);
  noStroke();
  fill(col);
  ellipse(new_x, new_y, rad, rad);
}


void drawSnap(Snap s) {
  for (int i = 0; i < s.pos.length; i++) {
    drawParticle(s.pos[i].x, s.pos[i].y, particles_radius[i], i==0?color(255, 255, 0) : color(255, 0, 0));
  }
}

void keyPressed() {
  if (snap_number < (snapshots.size() - 1)) {
    snap_number++;
    draw();
  }
}
