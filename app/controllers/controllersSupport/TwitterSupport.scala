package controllers.controllersSupport

import play.api.libs.json.{JsValue, Json}
import play.api.cache.Cache
import play.api.Play.current
import play.api.libs.oauth.{RequestToken, OAuthCalculator, ConsumerKey}
import play.api.libs.ws.WS
import play.libs.{WS => WWSS}

object TwitterSupport {

  val consumerKey = ""
  val consumerSecret = ""
  val accessToken = "-"
  val accessTokenSecret = ""
  val oauthCalculator = OAuthCalculator(ConsumerKey(consumerKey, consumerSecret), RequestToken(accessToken, accessTokenSecret))
  initial

  /*
  * 初期化処理
  * ログインとかする予定
  */
  def initial {}


  /*
  * 指定された地域コードのトレンドを返す
  * twitter側の情報は5分毎に更新
  * @param id 地域コード
  * @return twitterトレンド(JsValue)
  */
  def getTrend(id: Int): JsValue = {
    println("Trend [" + id.toString + "]")
    val url = "http://api.twitter.com/1/trends/" + id + ".json" //日本なら1118370
    val responseBody: String = Cache.getOrElse[String]("trend" + id.toString, 60 * 5) {
        val resultPromise = WS.url(url).sign(oauthCalculator).get()
        if (resultPromise.await.get.status != 200) throw new NoSuchFieldException("twitter api error : "+resultPromise.await.get.status.toString)
        resultPromise.await.get.body
      }
    //println(responseBody)
    val responseJson = Json.parse(responseBody)
    responseJson
  }


  /*
  * 指定された検索語に対する検索結果を返す
  * twitter側の制限は180回/15分
  * @param id 地域コード
  * @return twitter検索結果(json)
  */
  def getSearch(query: String): JsValue = {
    println("Query [" + query + "]")
    val url = "http://search.twitter.com/search.json"
    val responseBody: String = Cache.getOrElse[String]("search" + Hash.base64sum(query.toString), 60) {
      val resultPromise = WS.url(url).withQueryString(("q", query)).sign(oauthCalculator).get() //WWSS.url(url).setQueryParameter("q", query).get().get().getBody() みたいな感じ。
      if (resultPromise.await.get.status != 200) throw new NoSuchFieldException("twitter api error : "+resultPromise.await.get.status.toString)
      resultPromise.await.get.body
    }
    //println(responseBody)
    val responseJson = Json.parse(responseBody)
    responseJson
  }

  /*
  * トレンドを取得できる地域一覧を取得する
  * @return 地域一覧(json)
  */
  def getTrendAvailable(): JsValue = {
    println("Trend Area")
    val url = "https://api.twitter.com/1.1/trends/available.json"
    val responseBody: String = Cache.getOrElse[String]("trendAvailable", 60 * 60 * 12) {
      val resultPromise = WS.url(url).sign(oauthCalculator).get()
      if (resultPromise.await.get.status != 200) throw new NoSuchFieldException("twitter api error : "+resultPromise.await.get.status.toString)
      resultPromise.await.get.body
    }
    //println(responseBody)
    val responseJson = Json.parse(responseBody)

    responseJson
  }


}
