package models

import org.specs2.mutable.Specification
import java.net.URL
import play.api.libs.json.Json
import models.TwitterTrendArea.TwitterTrendAreaFormat


class TwitterTrendAreaSpec extends Specification {
  "TwitterTrendArea model" should {

    val twitterTrendAreaModel = TwitterTrendArea("サンクトペテルブルク", Some("RU"), 2123260, "Russia", new URL("http://where.yahooapis.com/v1/place/2123260"), Some(23424936), "Town")

    "ModelからJsonに変換" in {
      val twitterTrendAreaJson = Json.parse("{\"name\":\"サンクトペテルブルク\",\"countryCode\":\"RU\",\"woeid\":2123260,\"country\":\"Russia\",\"url\":\"http://where.yahooapis.com/v1/place/2123260\",\"parentid\":23424936,\"placeType\":\"Town\"}")
      TwitterTrendAreaFormat.writes(twitterTrendAreaModel) must_== (twitterTrendAreaJson)
    }

    "JsonからModelに変換" in {
      val twitterTrendAreaJson = Json.parse("{\"url\":\"http://where.yahooapis.com/v1/place/2123260\",\"countryCode\":\"RU\",\"parentid\":23424936,\"name\":\"サンクトペテルブルク\",\"country\":\"Russia\",\"woeid\":2123260,\"placeType\":{\"name\":\"Town\",\"code\":7}}")
      TwitterTrendAreaFormat.reads(twitterTrendAreaJson) must_== (twitterTrendAreaModel)
    }
  }
}
