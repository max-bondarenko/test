import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

/**
 * Created by mbondarenko on 19.05.14.
 */
object Main extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")

  startServer(interface = "localhost", port = 8080) {


    get {
      path("list") {
        complete(" simply List ")
      } ~
        path("list" / IntNumber) {
          id =>
            complete {
              s"<h1>list $id</h1>"
            }
        }
    }
  }
}


