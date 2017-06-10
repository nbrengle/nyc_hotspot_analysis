package finalproject;

import java.util.HashMap;
import finalproject.SpaceTimeCoordinate;
//this is a really cheap, unsafe Flyweight Factory

public class CoordFactory {
   private static final HashMap<String, SpaceTimeCoordinate> coordMap = new HashMap();

   public static SpaceTimeCoordinate getCoord(String xyt) {
      SpaceTimeCoordinate coord_out = (SpaceTimeCoordinate)coordMap.get(xyt);

      if(coord_out == null) {
         coord_out = new SpaceTimeCoordinate(xyt);
         coordMap.put(xyt, coord_out);
      }
      return coord_out;
   }
}
