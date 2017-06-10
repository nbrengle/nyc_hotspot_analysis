package finalproject

import java.time._
import java.time.format._
import java.time.temporal.ChronoUnit._
import finalproject._

class LineParser(precision: Double = 0.1, timestep: Int = 3) extends java.io.Serializable {

    val LAT_OFFSET = 40.5000000
    val LONG_OFFSET = -74.25000000
    val START_DATE = "2015-01-01 00:00:00"
    val DATETIME_OFFSET = setupDateOffset(START_DATE)
    val DATE_COL = 2
    val LAT_COL = 10
    val LONG_COL = 9
    //this value literally doesn't matter, just using a const to get the desired form
    val numPassenger = 1

    def setupDateOffset(date: String): LocalDateTime = {
        val formatter = DateTimeFormatter.ofPattern("uuuu-MM-DD HH:mm:ss")
        return LocalDateTime.parse(START_DATE, formatter)
    }

    def parseLine(line: Array[String]): Map[SpaceTimeCoordinate, Int] = {
        return Map(parseCoord(line) -> numPassenger)
    }

    def parseCoord(line: Array[String]): SpaceTimeCoordinate = {
        //Ugly workaround because Java Date Parsing handles 2015-02-01 00:13:14 incorrectly
        val date = try {
            parseDate(line(DATE_COL).toString, timestep)
        } catch {
            case ioe: DateTimeParseException => -1
        }

        val lat_in = line(LAT_COL).toDouble
        val lon_in = line(LONG_COL).toDouble
        if (date == -1 || lat_in == 0.0 || lon_in == 0.0) {
            Unit
        }

        val lat = parseLat(lat_in, precision)
        val lon = parseLong(lon_in, precision)

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
        val formatter = DateTimeFormatter.ofPattern("uuuu-MM-DD HH:mm:ss")
        //amount = start.until(end, MONTHS);
        val tar = LocalDateTime.parse(date, formatter)
        return (DATETIME_OFFSET.until(tar,DAYS) / timestep).toInt
    }

}
