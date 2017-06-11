# nyc_hotspot_analysis
CSC543 Final Project, re-implement(ish) SVG Magalhaes and WR Franklin's solution to ACM-Sig Spatial Cup for Spatio-Temporal Analysis of New York City Yellow Cab Data for 2015

To use this app you'll need to install sbt, scala 2.11 and spark 2.1.1

You will have to change the following values to appropriate values for your run in `Main.scala`. Future versions will take commandline arguments:

```scala
val DEGREES_PRECISION = .01
val TIMESTEP_SIZE = 3
val FILE = "/Absolute/Path/To/A/CSV/File/or/Files/*.csv"
val MAKRAI_OPT = false
```
Setting `MAKRAI_OPT` governs at what point the values are extracted. If `false` we first apply the G\* statistic to all the values and then extract the highest. If `true` we first extract the highest *xj* value and then apply G\* to each of those highest 50 values. 

To run it, navigate to the top directory of the project: 
```bash
sbt package
```
Then:
```bash
spark-submit target/scala-2.11/main_2.11-0.1.0.jar
```
