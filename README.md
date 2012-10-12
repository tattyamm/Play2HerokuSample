#TwiTrend

##概要
* Twitterのトレンドを表示します。
* Play2をHerokuで動かす時のサンプルとして作成しました。
* テストも書いた。
* Playframework2,Scala
* memcachedをキャッシュに使用
* [詳細説明（ブログ）](http://blog.livedoor.jp/tattyamm/archives/4225379.html)

##動かし方
* 以下のコマンドで環境変数を見る

    $ heroku config --app yourAppName

* この値を`conf/application.conf`の以下の値へ設定する。

    memcached.host
    memcached.user
    memcached.password

* `app/controllers/controllersSupport/TwitterSupport`の以下の値を設定する。

    val consumerKey
    val consumerSecret
    val accessToken
    val accessTokenSecret

##作者
* [@tattyamm](https://twitter.com/tattyamm)