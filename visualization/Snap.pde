class Snap {
  float t;
  PVector[] pos, vel;
  
  public Snap(float t, JSONArray pos, JSONArray vel) {
    this.t = t;
    if (pos.size() != vel.size()) {
      throw new RuntimeException("Different pos and vel sizes");
    }
    this.pos = new PVector[pos.size()];
    this.vel = new PVector[vel.size()];
    for (int i = 0; i < pos.size(); i++) {
      JSONArray aux = pos.getJSONArray(i);
      this.pos[i] = new PVector(aux.getFloat(0), aux.getFloat(1));
    }
    
    for (int i = 0; i < vel.size(); i++) {
      JSONArray aux = vel.getJSONArray(i);
      this.vel[i] = new PVector(aux.getFloat(0), aux.getFloat(1));
    }
  }
}
