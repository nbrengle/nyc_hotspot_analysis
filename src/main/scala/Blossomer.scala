package finalproject

import finalproject._

object Blossomer {

    def blossom(coord:SpaceTimeCoordinate,count:Int): Map[SpaceTimeCoordinate, Int] = {
        val lat = coord.lat
        val lon = coord.lon
        val time = coord.time
        val lat_range = (lat - 1) to (lat + 1)
        val lon_range = (lon - 1) to (lon + 1)
        val time_range = (time - 1) to (time + 1)
        var map_out:Map[SpaceTimeCoordinate,Int] = Map()
        for (lat_l <- lat_range) {
            for (lon_l <- lon_range) {
                for (time_l <- time_range) {
                    var coord_str = lat_l + "," + lon_l + "," + time_l
                    var out_coord = CoordFactory.getCoord(coord_str)
                    map_out += (out_coord -> count)
                }
            }
        }
        return map_out
    }

}
