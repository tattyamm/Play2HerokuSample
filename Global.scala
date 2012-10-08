import play.api._
import play.api.mvc._
import play.api.mvc.Results._

object Global extends GlobalSettings {

  //TODO エラー整備

  override def onError(request: RequestHeader, ex: Throwable) = {
    InternalServerError(
      views.html.errorPage(ex.getMessage)
    )
  }


  override def onStart(app: Application) {
    // Logger.info("Application has started.")
  }

  override def onStop(app: Application) {
    // Logger.info("Application shutdown.")
  }

}