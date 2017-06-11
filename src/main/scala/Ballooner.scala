package finalproject

import finalproject._

object Ballooner {

    def balloon(coord:SpaceTimeCoordinate,count:Int): Map[SpaceTimeCoordinate, Int] = {
        val lat = coord.lat
        val lon = coord.lon
        val time = coord.time
        val lat_range = (lat - 1) to (lat + 1)
        val lon_range = (lon - 1) to (lon + 1)
        val time_range = (time - 1) to (time + 1)
        var map_out:Map[SpaceTimeCoordinate,Int] = Map()
        for (lat_l <- lat_range) {
            var lat_coord_str = lat_l + "," + lon + "," + time
            var lat_coord = CoordFactory.getCoord(lat_coord_str)
            map_out += (lat_coord -> count)
        }
        for (lon_l <- lon_range) {
            var lon_coord_str = lat + "," + lon_l + "," + time
            var lon_coord = CoordFactory.getCoord(lon_coord_str)
            map_out += (lon_coord -> count)
        }
        for (time_l <- time_range) {
            var time_coord_str = lat + "," + lon + "," + time_l
            var time_coord = CoordFactory.getCoord(time_coord_str)
            map_out += (time_coord -> count)
        }

        return map_out
    }

}
