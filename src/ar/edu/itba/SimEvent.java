package ar.edu.itba;

import com.sun.xml.internal.ws.wsdl.writer.document.Part;

import java.util.List;

public class SimEvent {
    public Particle p1;
    public Particle p2;
    double t;
    CollisionType type;
    public SimEvent(double t, Particle p1, Particle p2 , CollisionType type) {
        this.t = t;
        this.p1 = p1;
        this.p2 = p2;
        this.type = type;
    }

    static SimEvent next_event(double space_width, List<Particle> particles) {
        CollisionType type = CollisionType.NONE;
        double min_t = Double.POSITIVE_INFINITY;
        Particle p1 = null;
        Particle p2 = null;
        // Check Walls
        for (Particle p : particles) {
            //Pared vertical
            if (p.vel.x > 0) {
                double new_t = (space_width - p.radius - p.pos.x)/p.vel.x;
                if (new_t < min_t) {
                    min_t = new_t;
                    p1 = p;
                    type = CollisionType.VERTICAL_WALL;
                }
            } else if (p.vel.x < 0) {
                double new_t = ( p.radius - p.pos.x)/p.vel.x;
                if (new_t < min_t) {
                    min_t = new_t;
                    p1 = p;
                    type = CollisionType.VERTICAL_WALL;
                }
            }

            //Pared horizontal
            if (p.vel.y > 0) {
                double new_t = (space_width - p.radius - p.pos.y)/p.vel.y;
                if (new_t < min_t) {
                    min_t = new_t;
                    p1 = p;
                    type = CollisionType.HORIZONTAL_WALL;
                }
            } else if (p.vel.y < 0) {
                double new_t = (p.radius - p.pos.y)/p.vel.y;
                if (new_t < min_t) {
                    min_t = new_t;
                    p1 = p;
                    type = CollisionType.HORIZONTAL_WALL;
                }
            }
            //check particle collisions
            for (int i = 0; i < particles.size(); i++) {
                for (int j =  i + 1; j < particles.size(); j++) {
                    Particle pi = particles.get(i);
                    Particle pj = particles.get(j);
                    if( checkX(pi , pj) && checkY(pi , pj)){
                        double new_t = calculateCollisionTime(pi , pj);
                        if(new_t < min_t){
                            min_t = new_t;
                            p1 = pi;
                            p2 = pj;
                            type = CollisionType.PARTICLE;
                        }
                    }
                }
            }
        }


        return new SimEvent(min_t, p1, p2 , type);
    }

    private static boolean checkX( Particle pa , Particle pb){
        if( Math.abs(pa.pos.x - pb.pos.x) < pa.radius + pb.radius )
            return true;
        return (pa.pos.x > pb.pos.x)?(pa.vel.x - pb.vel.x > 0) : (pb.vel.x - pa.vel.x > 0) ;
        /* codigo bien
        Particle p1;
        Particle p2;
        if(pa.pos.x < pb.pos.x){
            p1 = pa;
            p2 = pb;
        }else{
            p1 = pb;
            p2 = pa;
        }
        return p1.vel.x - p2.vel.x > 0;
        */
    }

    private static boolean checkY( Particle pa , Particle pb){
        if( Math.abs(pa.pos.y - pb.pos.y) < pa.radius + pb.radius )
            return true;
        return (pa.pos.y > pb.pos.y)?(pa.vel.y - pb.vel.y > 0) : (pb.vel.y - pa.vel.y > 0) ;
        /* codigo bien
        Particle p1;
        Particle p2;
        if(pa.pos.y < pb.pos.y){
            p1 = pa;
            p2 = pb;
        }else{
            p1 = pb;
            p2 = pa;
        }
        return p1.vel.y - p2.vel.y > 0;
        */
    }
    //Teorica_3 diapo 14
    private static double calculateCollisionTime(Particle p1 , Particle p2){
        Vector2D dR = p1.pos.sub(p2.pos);
        Vector2D dV = p1.vel.sub(p2.vel);
        double sigma = p1.radius + p2.radius;
        double d = Math.pow(dV.dot(dR) , 2)  - dV.dot(dV)*(dR.dot(dR) - Math.pow(sigma , 2));
        if (d < 0 || dV.dot(dR) >= 0)
            return Double.POSITIVE_INFINITY;
        return - (dV.dot(dR) + Math.sqrt(d) )/dV.dot(dV);
    }

}
