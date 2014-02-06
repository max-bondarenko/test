import akka.actor.{Props, PoisonPill, Actor, ActorSystem}


object SomeScala extends App{

  val sys = ActorSystem(name = "Sys")

  val simple = sys.actorOf( Props[SimpleActor],"name" )



}

class SimpleActor extends Actor {
  def receive: Actor.Receive = {
    case e : PoisonPill =>  //do nothing
    case "live" => println("live")
  }
}
