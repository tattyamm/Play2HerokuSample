package controllers.controllersSupport

import org.specs2.mutable.Specification


class HashSpec extends Specification {

  "Hash" should {
    "sha1が計算できる" in {
       Hash.sha1Sum("テスト文字列") must_==("bcfe18c839a4836abdbb1138a270569a5d723f3d")
    }
    "md5が計算できる" in {
      Hash.md5Sum("テスト文字列") must_==("ceb101100688f63bdde16817fbd7733e")
    }
    "base64が計算できる" in {
      Hash.base64sum("テスト文字列") must_==("44OG44K544OI5paH5a2X5YiX")
    }
  }
}