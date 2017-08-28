package com.spronq

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

object Boot extends App {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val config = ConfigFactory.load()
  val logger = Logging(system, getClass)

  val userRoute = new UserRoute(new UserService())

  Http().bindAndHandle(userRoute.usersRoute, config.getString("http.interface"), config.getInt("http.port"))
}