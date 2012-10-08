package models

import java.net.URL
import play.api.libs.json._
import play.api.libs.json.JsObject
import play.api.libs.json.JsString

/*
 * twitterのトレンド情報を格納するcase class
 * 例 TwitterTrend("abc",new URL("http://google.com"))
 */
case class TwitterTrend(
                         name: String,
                         url: URL
                         )

object TwitterTrend {

  implicit object TwitterTrendFormat extends Format[TwitterTrend] {
    /*
     * JsonからModelに変換
     */
    def reads(json: JsValue): TwitterTrend = TwitterTrend(
      (json \ "name").as[String],
      new URL((json \ "url").as[String])
    )

    /*
     * ModelからJsonに変換
     */
    def writes(t: TwitterTrend): JsValue = JsObject(
      List(
        "name" -> JsString(t.name),
        "url" -> JsString(t.url.toString)
      )
    )

  }

}