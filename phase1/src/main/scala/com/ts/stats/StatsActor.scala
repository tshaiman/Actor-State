package com.ts.stats

import akka.actor.{Actor, ActorRef}

class StatsActor extends Actor {

  private def wordsToMapCount(line: String): Map[String, Int] = {
    line.split("\\W+")
      .foldLeft(Map.empty[String, Int])((map: Map[String, Int], next: String) => map + (next -> (map.getOrElse(next, 0) + 1)))
  }

  var statsMap:Map[String,Int] = Map.empty.withDefaultValue(0)

  override def receive: Receive = {
    case Message(line: String) =>
      val tmpMap = wordsToMapCount(line)
      statsMap ++= tmpMap.map { case (k, v) => k -> (v + statsMap(k)) }

    case GetStats =>
      sender ! StatsResult(statsMap)
  }
}
