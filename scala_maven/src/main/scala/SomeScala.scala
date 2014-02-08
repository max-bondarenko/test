import akka.actor.{Props, PoisonPill, Actor, ActorSystem}
import akka.routing.RoundRobinRouter


object SomeScala extends App {

  val sys = ActorSystem(name = "Sys")

  val simple = sys.actorOf(Props[SimpleActor], "name")

  simple ! "live"
  simple ! "stop"
  simple ! "live"

  sys.dispatchers


}

class SimpleActor extends Actor {
  val ch = context.system.actorOf(Props[Simple2Actor].withRouter(RoundRobinRouter(nrOfInstances = 5)),"sr" )

  override def postStop() = {
    println("die a")
    ch ! PoisonPill
  }

  def receive
  = {
    case "stop" => println("have a poison"); self ! PoisonPill
    case "live" => println("live"); ch ! "live";
  }
}

class Simple2Actor extends Actor {

  override def postStop() = {
    println("die c")

  }

  def receive  = {
    case "live" => println("ch live " + self)
  }
}


