package models

import java.net.URL
import play.api.libs.json._
import play.api.libs.json.JsObject
import play.api.libs.json.JsString

/*
 * twitterのトレンド地域情報を格納するcase class
 * parentidとはその地域が属している地域のwoeidを指す。
 * placeTypeにはCountryとTownが入るらしい。
 *
 * TODO これでは不可逆なjsonが生まれる。
 *
 * https://github.com/playframework-ja/Play20/wiki/ScalaJson
 *
 * {{{
 * TwitterTrendArea(サンクトペテルブルク,Some(RU),2123260,Russia,new URL(http://where.yahooapis.com/v1/place/2123260),Some(23424936),Town)
 * }}}
 */
case class TwitterTrendArea(
                             name: String,
                             countryCode: Option[String],
                             woeid: Int,
                             country: String,
                             url: URL,
                             parentid: Option[Int], //parentID==1のとき、その地域はCountryである。
                             placeType: String
                             )

object TwitterTrendArea {

  implicit object TwitterTrendAreaFormat extends Format[TwitterTrendArea] {
    def reads(json: JsValue): TwitterTrendArea = TwitterTrendArea(
      (json \ "name").as[String],
      (json \ "countryCode").asOpt[String],
      (json \ "woeid").as[Int],
      (json \ "country").as[String],
      new URL((json \ "url").as[String]),
      (json \ "parentid").asOpt[Int],
      ((json \ "placeType") \ "name").as[String] //TODO 少々適当な感じがする
    )

    def writes(t: TwitterTrendArea): JsValue = JsObject(
      List(
        "name" -> JsString(t.name),
        "countryCode" -> JsString(t.countryCode.getOrElse("WORLD")),  //TODO getOrElseした場合の値
        "woeid" -> JsNumber(t.woeid),
        "country" -> JsString(t.country),
        "url" -> JsString(t.url.toString),
        "parentid" -> JsNumber(t.parentid.getOrElse(0).toInt), //TODO getOrElseした場合の値
        "placeType" -> JsString(t.placeType)
      )
    )

  }

}