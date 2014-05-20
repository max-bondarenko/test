import akka.actor.ActorSystem
import com.max.entity.repo.SimpleRepo
import com.max.entity.Simple

import java.util
import spray.http._
import spray.http.HttpResponse
import spray.routing.SimpleRoutingApp

/**
 * Created by mbondarenko on 19.05.14.
 */
object Main extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")

  import scala.collection.JavaConversions._

  implicit def toHttpResp(s: String): HttpResponse = {
    HttpResponse(entity = HttpEntity(ContentType(MediaTypes.`text/html`, HttpCharsets.`UTF-8`), s))
  }

  startServer(interface = "localhost", port = 8080) {


    get {
      path("list") {
        complete {
          SimpleRepo.sf.openSession()
          val all: util.List[Simple] = SimpleRepo.getAll
          SimpleRepo.sf.getCurrentSession.close
          val s: HttpResponse = all.toList.foldLeft("<html><body><ul>\n ") {
            _ + "\t<li>" + _.toString + "</li>\n"
          } + "\n</ul></body></html>"
          s
        }
      } ~
        path("list" / IntNumber) {
          id =>
            complete {
              SimpleRepo.sf.openSession()
              val s: HttpResponse = s"<h1>list ${SimpleRepo.get(id).name}</h1>"
              SimpleRepo.sf.getCurrentSession.close
              s
            }
        }
    }
  }
}

object MainHiber extends App {

  import com.max.entity.repo.SimpleRepo

  SimpleRepo.sf.openSession()

  println(SimpleRepo.get(1))

  SimpleRepo.sf.getCurrentSession.close()

}


