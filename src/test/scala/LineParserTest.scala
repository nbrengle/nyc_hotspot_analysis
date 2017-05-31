package finalproject

import org.scalatest._
import finalproject._

class LineParserTest extends FlatSpec with Matchers {

    //val line = List("2","2015-01-12 23:34:05","2015-01-12 23:36:49","1",".67",-73.960807800292969,40.770030975341797,1,N,-73.951972961425781,40.771202087402344,1,4.5,0.5,0.5,1,0,0.3,6.8)

    // "Parsing a line with parseLine" should "return appropriate vals" in {
    //     assert(parseLine(line))
    // }

    val parser: LineParser = new LineParser()

    "ParseLine" should "parse a date correctly" in {
        parser.parseDate("2015-01-12 23:34:05",3) should be (3) //might be an off-by-1 because of 1/1/15
    }

    it should "parse long correctly" in {
        parser.parseLong(-73.960807800292969, 0.01) should be (7168)
    }

    it should "parse lat correctly" in {
        parser.parseLat(40.770030975341797, 0.01) should be (1769472)
    }
}
