package com.ts.stats

import akka.actor.{Actor, ActorRef, Props}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

import scala.util.Random

object DataSource {
  var rnd:Random = Random

  val lines:List[String] =
    List("the cow jumped over the moon",
    "an apple a day keeps the doctor away",
    "four score and seven years ago",
    "snow white and the seven dwarfs",
    "i am at two with nature")

  def getMessage = {
    lines(rnd.nextInt(lines.length))
  }
}

object GeneratorActor{
  def props(statsActor:ActorRef):Props = Props(new GeneratorActor(statsActor))
}

class GeneratorActor(statsActor:ActorRef) extends Actor{
  import DataSource._
  case object Tick

  override def receive: Receive = {
    case StartProducer =>
      println("Producer started")
      context.system.scheduler.schedule(
        0 milliseconds,
        750 milliseconds,
        self,
        Tick)

      context.system.scheduler.schedule(
        0 milliseconds,
        5 seconds,
        statsActor,
        GetStats)

    case Tick =>
      statsActor !  Message (getMessage)

    case StatsResult(stats) =>
      println(stats)

  }
}
