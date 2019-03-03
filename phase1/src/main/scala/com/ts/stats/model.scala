package com.ts.stats

case object StartProducer
case class Message(line:String)
case object GetStats
case class  StatsResult(stats:Map[String,Int])
