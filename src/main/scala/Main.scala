package runtime

import com.github.tototoshi.csv._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import finalproject.LineParser
import finalproject.SpaceTimeCoordinate
import finalproject.CoordFactory
import finalproject.Ballooner
import scala.math.pow
import scala.math.sqrt


object Main {
    def main(args: Array[String]) {
        //Configure the run
        val DEGREES_PRECISION = .01
        val TIMESTEP_SIZE = 3
        val FILE = "/Absolute/Path/To/A/CSV/File/"

        //initialize Spark
        val conf = new SparkConf().setAppName("finalproject").setMaster("local")
        val sparkContext = new SparkContext(conf)
        /*
        1. Read the input file from HDFS, compute the cell co- ordinates of each
        drop-off, and create a RDD containing (t, x, y) as key and numPassenger
        as value. (t, x, y) represents the cell coordinates of the drop- off
        and numPassenger is the number of passengers dropped off at that event.
        */
        val lineParser = new LineParser(DEGREES_PRECISION,TIMESTEP_SIZE)
        //val cab_data = sparkContext.textFile("hdfs://...")
        val cab_data = sparkContext.textFile(FILE)

        //Question: How do I switch to mapPartitions
        val coords = cab_data.flatMap(line => lineParser.parseLine(line.split(",")))

        /*
        2. Perform a reduction by key operation to create a RDD counts.
        In this RDD, for each key (coordinates (t, x, y)) the value will be
        the total number of events at that co- ordinate (count).
        */
        //assumes values are 1, which they are because we use numPassenger=1 above
        val hist = coords.reduceByKey( _ + _ )
        /*
        3. Compute statistics such as the average and standard deviation of
        the number of events in each cell using the previous RDD.
        */
        // mean is the total value of V / count of K
        val cnt = hist.count()
        val tot = hist.map(_._2).reduce((x, y) => x + y)
        val mean = tot / cnt
        // standardDeviation
            //Then for each number: subtract the Mean and square the result.
            //Then work out the mean of those squared differences.
            //Take the square root of that and we are done!
            //consider rolling this into a real, testable class...
        val s_dev = sqrt(hist.map(_._2.toDouble).reduce((x,y) => x + pow((y - mean),2)) / cnt)
        /*
        4. Create a new RDD by generating 27 versions of each
        cell i = ((t, x, y), count), each with the same count,
        but the following various coordinates
        ((t + dt, x + dx, y + dy), count), for dt = −1..1, dx = −1..1, dy = −1..1.
        */
        val neighs = hist.flatMap(point => Ballooner.balloon(point._1,point._2))
        /*for
        5. Reduce this new RDD by key, creating an RDD sumNeigh that will
        contain, for each cell i, σi, the sum of the number of events in the
        neighbors of i.
        */
        val sumNeigh = neighs.reduceByKey( _ + _ )
        /*
        6. Finally, from the statistics in step 3, compute the G∗i score of
        each cell in the RDD sumNeigh and write the cells with highest scores
        to the output.
        */
        //instead we'll sort it and take the top 50 values
        //looping through to take 50 values is actually slower
        //Possible optimizations here to do better than nlogn
        val top50 = sumNeigh.takeOrdered(50)(Ordering.by[(SpaceTimeCoordinate, Int), Int](_._2).reverse)
        //calc GO
            // sum (weights * values) - mean(sum of the weights)
            //divided by s_dev * sqrt(((count * sum of weights^2) - (sum of the weights)^2)/n-1)
            // (_.2 - mean * 27) / s_dev * sqrt (((cnt * 27) - 27^2)/(cnt-1))
        val g_star_denom = s_dev * sqrt(((cnt * 27) - pow(27,2))/(cnt-1))
        val g_star_sub = mean * 27
        val topGO = sparkContext.parallelize(top50).mapValues(x => (x - g_star_sub)/g_star_denom)
        for (GO <- topGO) print (GO)
        //
        // val G_neighs = sumNeigh.mapValues(x => (x - g_star_sub)/g_star_denom)
        // val g_fiddy = G_neighs.takeOrdered(50)(Ordering.by[(SpaceTimeCoordinate, Double), Double](_._2).reverse)
        // for (g <- g_fiddy) print (g)

        sparkContext.stop()
    }
}
