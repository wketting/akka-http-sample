package com.spronq

import scala.concurrent.Future

class UserService {
  import HttpJsonProtocol._
  import scala.concurrent.ExecutionContext.Implicits.global


  case class InternalUser(name: String, password: String)

  val users = List(
    InternalUser("test1", "test1"),
    InternalUser("test2", "test2"),
    InternalUser("test3", "test3")
  )

  def getUsers() : Future[List[User]] = Future {
    users.map(internalUser => User(internalUser.name))
  }

  def loginUser(loginRequest: UserLoginRequestPayload) : Future[Option[User]] = Future {
    users.filter(internalUser => internalUser.name.equals(loginRequest.name) && internalUser.password.equals(loginRequest.password)).map(iu => User(iu.name)).headOption
  }

  def getUser(name: String): Future[Option[User]] = Future {
    users.filter(internalUser => internalUser.name.equals(name)).map(iu => User(iu.name)).headOption
  }

  def updateName(name: String): Unit = Future {

  }
}
