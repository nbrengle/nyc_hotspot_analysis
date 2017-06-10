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

    SpaceTimeCoordinate(String xyt) {
        String[] in_arr = xyt.split(",");
        lat = Integer.parseInt(in_arr[0]);
        lon = Integer.parseInt(in_arr[1]);
        time = Integer.parseInt(in_arr[1]);
    }
}
