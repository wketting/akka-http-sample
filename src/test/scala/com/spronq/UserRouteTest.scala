package com.spronq

import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FlatSpec, MustMatchers}

class UserRouteTest extends FlatSpec with MustMatchers with ScalatestRouteTest {
  import HttpJsonProtocol._
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  val logger = Logging(system, getClass)
  val userRoute = new UserRoute(new UserService)

  "The users route" should "return a list of users with a GET" in {
    Get(s"/users") ~> Route.seal(userRoute.usersRoute) ~> check {
      status must be(StatusCodes.OK)
      val theResponse = responseAs[List[User]]
      theResponse.size must be(3)
    }
  }

  "The users login route" should "login successfully" in {
    Post(s"/users/login", UserLoginRequestPayload("test1", "test1")) ~> Route.seal(userRoute.usersRoute) ~> check {
      status must be(StatusCodes.OK)
      val theResponse = responseAs[User]
      theResponse.name must be("test1")
    }
  }

  "The users login route" should "give unauthorized with false credentials" in {
    Post(s"/users/login", UserLoginRequestPayload("effe", "effe")) ~> Route.seal(userRoute.usersRoute) ~> check {
      status must be(StatusCodes.Unauthorized)
    }
  }
}
