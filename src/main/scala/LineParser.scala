package finalproject

import java.time._
import java.time.format._
import java.time.temporal.ChronoUnit._
import finalproject._

class LineParser(precision: Double = 0.1, timestep: Int = 3) extends java.io.Serializable {

    val LAT_OFFSET = 40.5000000
    val LONG_OFFSET = -74.25000000
    val DATETIME_OFFSET = LocalDateTime.of(2015,1,1,0,0,0)
    val DATE_COL = 2
    val LAT_COL = 10
    val LONG_COL = 9
    //this value literally doesn't matter, just using a const to get the desired form
    val numPassenger = 1

    def parseLine(line: Array[String]): Map[SpaceTimeCoordinate, Int] = {
        return Map(parseCoord(line) -> numPassenger)
    }

    def parseCoord(line: Array[String]): SpaceTimeCoordinate = {
        val date = parseDate(line(DATE_COL).toString, timestep)
        val lat = parseLat(line(LAT_COL).toDouble, precision)
        val lon = parseLong(line(LONG_COL).toDouble, precision)

        return new SpaceTimeCoordinate(lat,lon,date)
    }

    def parseLat(lat: Double, precision: Double): Int = {
        return ((lat - LAT_OFFSET) / precision).toInt
    }

    def parseLong(long: Double, precision: Double): Int = {
        return ((long - LONG_OFFSET) / precision).toInt
    }

    def parseDate(date: String, timestep: Int): Int = {
        // 2015-01-12 23:36:49
        // 2015-02-01 00:13:14
        val formatter = DateTimeFormatter.ofPattern("uuuu-MM-DD HH:mm:ss")
        return (DAYS.between(DATETIME_OFFSET, LocalDateTime.parse(date, formatter)) / timestep).toInt
    }

}
