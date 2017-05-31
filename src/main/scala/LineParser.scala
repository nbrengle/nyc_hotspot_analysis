package finalproject

import java.time._
import java.time.format._
import java.time.temporal.ChronoUnit._

class LineParser() {

    val LAT_OFFSET = 40.5000000
    val LONG_OFFSET = -74.25000000
    val DATETIME_OFFSET = LocalDateTime.of(2015,1,1,0,0,0)
    val DATE_COL = 2
    val LAT_COL = 8
    val LONG_COL = 9

    def parseLine(line: List[Any], precision: Double, timestep: Int): Int = {
        var date = parseDate(line(DATE_COL).toString, timestep)
        var lat = parseLat(line(LAT_COL).asInstanceOf[Double], precision)
        var long = parseLong(line(LONG_COL).asInstanceOf[Double], precision)

        var line_out = lat | long | date // I have no sense if this works

        return line_out

    }

    def parseLat(lat: Double, precision: Double): Int = {
        return ((lat - LAT_OFFSET) / precision).toInt << 16
    }

    def parseLong(long: Double, precision: Double): Int = {
        return ((long - LONG_OFFSET) / precision).toInt << 8
    }

    def parseDate(date: String, timestep: Int): Int = {
        // 2015-01-12 23:36:49
        val formatter = DateTimeFormatter.ofPattern("u-M-D H:m:s")
        return (DAYS.between(DATETIME_OFFSET, LocalDateTime.parse(date, formatter)) / timestep).toInt
    }

}
