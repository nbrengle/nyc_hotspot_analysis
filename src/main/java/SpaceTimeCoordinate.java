package finalproject;

import java.io.Serializable;

public class SpaceTimeCoordinate implements Serializable {

    public int lat;
    public int lon;
    public int time;

    SpaceTimeCoordinate(int x_in, int y_in, int t_in) {
        lat = x_in;
        lon = y_in;
        time = t_in;
    }
}
