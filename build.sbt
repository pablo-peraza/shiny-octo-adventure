name := "shiny-octo-adventure"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(	
    "com.typesafe.akka" %% "akka-actor" % "2.1.2",
    "org.scalatest" % "scalatest_2.10" % "1.9.1",
    "org.reactivemongo" %% "reactivemongo" % "0.9"    
)