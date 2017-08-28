package com.spronq

import spray.json.DefaultJsonProtocol

object HttpJsonProtocol extends DefaultJsonProtocol {

  case class User(name: String)
  case class UserLoginRequestPayload(name: String, password: String)
  case class ErrorResponse(msg: String)

  implicit val userFmt = jsonFormat1(User)
  implicit val errorResponseFmt = jsonFormat1(ErrorResponse)
  implicit val userLoginRequestPayloadFmt = jsonFormat2(UserLoginRequestPayload)
}
