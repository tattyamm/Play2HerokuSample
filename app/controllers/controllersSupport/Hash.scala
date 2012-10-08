package controllers.controllersSupport

/*
 * ハッシュを生成する
 * （base64はハッシュではなくエンコーダー？）
 */
object Hash {
  /*
   * sha1を生成する
   */
  def sha1Sum(message: String): String = {
    import java.security.MessageDigest
    val md = MessageDigest.getInstance("SHA1")
    md.digest(message.getBytes).map(_ & 0xFF).map(_.toHexString).mkString
  }

  /*
   * md5を生成する
   */
  def md5Sum(message: String): String = {
    import java.security.MessageDigest
    val digestedBytes = MessageDigest.getInstance("MD5").digest(message.getBytes)
    digestedBytes.map("%02x".format(_)).mkString
  }

  /*
  * base64を生成する
  */
  def base64sum(message: String): String = {
    new sun.misc.BASE64Encoder().encode(message.getBytes())
  }

}
