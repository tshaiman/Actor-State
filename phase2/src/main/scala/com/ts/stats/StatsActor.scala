package com.ts.stats

import akka.actor.{Actor, ActorRef, Props}

object StatsActor {

  case class StatsMaps(wordCounts:Map[String,Int] = Map.empty.withDefaultValue(0))

  def props():Props = Props[StatsActor]

  def wordsToMapCount(line: String): Map[String, Int] = {
    line.split("\\W+")
      .foldLeft(Map.empty[String, Int])((map: Map[String, Int], next: String) => map + (next -> (map.getOrElse(next, 0) + 1)))
  }
}

class StatsActor extends Actor {

  import StatsActor._

  override def receive: Receive = updated(StatsMaps())

  private def updated(stats:StatsMaps):Receive = {

    case Message(line: String) =>
      val currentMap = wordsToMapCount(line)
      val wcMap = stats.wordCounts ++ currentMap.map { case (k,v) => k -> (v + stats.wordCounts(k)) }
      context.become(updated(stats.copy(wordCounts = wcMap)))

    case GetStats =>
      sender ! StatsResult(stats.wordCounts)

    case _ => throw new IllegalStateException("Unkown event was sent to StatsAggregatorActor")
  }

}
