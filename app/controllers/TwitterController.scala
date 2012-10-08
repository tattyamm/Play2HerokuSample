package controllers

import controllersSupport.TwitterSupport
import play.api.mvc.{Action, Controller}
import java.net.URL
import models.{TwitterTrendArea, TwitterTrend}
//import play.libs.WS


object TwitterController extends Controller {

  /*
  * 指定された地域コードのトレンドを返す
  * @param id 地域コード
  * @return jsonの文字列
  */
  def trend(id: Int) = Action {
    val twitterTrendJson = TwitterSupport.getTrend(id)
    Ok(twitterTrendJson)
  }

  /*
  * 指定された単語の検索結果を返す
  * @param query 検索語
  * @return twitter検索結果(json)
  */
  def search(query: String) = Action {
    val twitterSearchJson = TwitterSupport.getSearch(query)
    Ok(twitterSearchJson)
  }

  /*
  * トレンドを取得できる地域一覧を取得する
  * @return jsonの文字列
  */
  def trendAvailable() = Action {
    val twitterTrendAvailableJson = TwitterSupport.getTrendAvailable()
    Ok(twitterTrendAvailableJson)
  }

  /*
  * 指定された地域コードのトレンドを表示する
  * @param id 地域コード
  * @return トレンドを表示する
  */
  def trendHtml(id: Int) = Action {
    val twitterTrendJson = TwitterSupport.getTrend(id)
    //println("jsonの中身 " + twitterTrendJson.toString())

    //地域情報取得
    val locations = (twitterTrendJson \\ "locations")(0) //配列として出てきてしまう。こういう方法で良いのだろうか。
    val trendArea = (locations \\ "name")(0).asOpt[String].getOrElse("地域取得エラー")
    //println("trendArea " + trendArea)

    //テスト。こんな感じだろうか。
    //println("Debug : " + twitterTrendJson.as[TwitterTrend].toString)

    //トレンドキーワード取得 //TODO この方法はどうかと思う
    val trendsJson = (twitterTrendJson \\ "trends")(0)
    val trendName = (trendsJson \\ "name").map(_.as[String]).toList
    val trendUrl = (trendsJson \\ "url").map(_.as[String]).toList.map(u => new URL(u))
    val trends = trendName.zip(trendUrl).map(t => TwitterTrend(t._1, t._2)) //TODO case classのread writeを使う

    //結果
    //println("地域:" + trendArea + " キーワード:" + trendName.toString() + " URL:" + trendUrl.toString)
    //println(trends)

    Ok(views.html.trend(trendArea, trends))
  }

  /*
  * トレンドを取得できる地域一覧を取得する
  * @return トレンドモデル。Array[TwitterTrendArea]
  */
  def trendAvailableHtml() = Action {
    val twitterTrendAvailableJson = TwitterSupport.getTrendAvailable()
    //println("jsonの中身 " + twitterTrendAvailableJson.toString())
    val twitterTrendAvailableList = twitterTrendAvailableJson.toString.drop(1).dropRight(1).replace("},{", "},,,,,{").split(",,,,,") //TODO 強引すぎる変換(ラベルの無いカンマ区切りのjsonはどうすれば良いのか。
    val twitterTrendAvailableModel = twitterTrendAvailableList.map(play.api.libs.json.Json.parse(_).as[TwitterTrendArea])
    //println("<<<" + twitterTrendAvailableModel.map(println(_)) + ">>>")
    Ok(views.html.trendAvailable(twitterTrendAvailableModel))
  }


}
