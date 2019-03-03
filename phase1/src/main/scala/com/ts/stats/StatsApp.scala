package com.ts.stats

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object StatsApp extends App{

  implicit val system: ActorSystem = ActorSystem("Panda-Stats-Counter")
  implicit val executionContext = system.dispatcher
  val statsActor = system.actorOf(Props[StatsActor])
  val generator = system.actorOf(GeneratorActor.props(statsActor))

  generator ! StartProducer

}
