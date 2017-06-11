# nyc_hotspot_analysis
CSC543 Final Project, re-implement(ish) Makrai's solution to ACM-Sig Spatial Cup for Spatio-Temporal Analysis of New York City Yellow Cab Data for 2015

To use this app you'll need to install sbt, scala and spark

To run it: 
sbt package

Then:
spark-submit target/scala-2.11/main_2.11-0.1.0.jar
