package models

import org.specs2.mutable.Specification
import java.net.URL
import models.TwitterTrend.TwitterTrendFormat
import play.api.libs.json.Json


class TwitterTrendSpec extends Specification {
  "TwitterTrend model" should {

    val twitterTrendModel = TwitterTrend("abc",new URL("http://twitter.com/i/#!/search/abc"))
    val twitterTrendJson = Json.parse("{\"name\":\"abc\",\"url\":\"http://twitter.com/i/#!/search/abc\"}")

    "ModelからJsonに変換" in {
      TwitterTrendFormat.writes(twitterTrendModel) must_==(twitterTrendJson)
    }

    "JsonからModelに変換" in {
      TwitterTrendFormat.reads(twitterTrendJson)  must_==(twitterTrendModel)
    }
  }
}
