# nyc_hotspot_analysis
CSC543 Final Project, re-implement(ish) SVG Magalhaes and WR Franklin's solution to ACM-Sig Spatial Cup for Spatio-Temporal Analysis of New York City Yellow Cab Data for 2015

To use this app you'll need to install sbt, scala 2.11 and spark 2.1.1

To run it, navigate to the top directory of the project: 
```
sbt package
```
Then:
```
spark-submit target/scala-2.11/main_2.11-0.1.0.jar
```
