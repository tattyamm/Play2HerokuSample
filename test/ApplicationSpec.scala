package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.ws.WS


class ApplicationSpec extends Specification {

  "indexにアクセスがあった場合" should {
    "正常なレスポンスを返す" in {
      val Some(result) = routeAndCall(FakeRequest(GET, "/"))
      status(result) must equalTo(OK)
    }
  }


  "trend API" should {

    running(TestServer(9000)) {
      val response = await(WS.url("http://localhost:9000/twitter/trend/1118370").get)
      "正常なレスポンスを返す" in {
        response.status must equalTo(OK)
      }
      "なんか返す" in {
        response.body must contain("trends")
      }

    }

    "存在しない地域ならエラーを返す" in {
      running(TestServer(9000)) {
        await(WS.url("http://localhost:9000/twitter/trend/111").get).status must equalTo(INTERNAL_SERVER_ERROR)
      }
    }
  }

  "search API" should {

    running(TestServer(9000)) {
      val response = await(WS.url("http://localhost:9000/twitter/search/%e3%83%88%e3%83%ac%e3%83%b3%e3%83%89").get)
      "正常なレスポンスを返す" in {
        response.status must equalTo(OK)
      }
      "なんか返す" in {
        response.body must contain("query")
      }

    }

  }
}