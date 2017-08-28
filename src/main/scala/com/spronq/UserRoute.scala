package com.spronq

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route


import scala.util.{Failure, Success}

class UserRoute(userService: UserService)(implicit system: ActorSystem) extends SprayJsonSupport {

  import HttpJsonProtocol._

  val usersRoute: Route = pathPrefix("users") {
    users ~
      login
  }

  def users = get {
    onComplete(userService.getUsers()) {
      case Success(user) => complete(StatusCodes.OK -> user)
      case Failure(msg: Throwable) => complete(StatusCodes.InternalServerError -> ErrorResponse(msg + Option(msg.getCause).map(t => s" due to ${t.getMessage}").getOrElse("")))
    }
  }

  def login = post {
    path("login") {
      entity(as[UserLoginRequestPayload]) { userRequest =>
        onComplete(userService.loginUser(userRequest)) {
          case Success(user) => user match {
            case Some(u) => complete(StatusCodes.OK -> u)
            case _ => complete(StatusCodes.Unauthorized)
          }
          case Failure(msg: Throwable) => complete(StatusCodes.InternalServerError -> ErrorResponse(msg + Option(msg.getCause).map(t => s" due to ${t.getMessage}").getOrElse("")))
        }
      }
    }
  }

}