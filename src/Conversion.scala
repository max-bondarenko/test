/**
 * Created by mbondarenko on 31.01.14.
 */

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Map.Entry


object Conversion extends App {
  import scala.collection.JavaConversions.mapAsJavaMap



  val map = Map[String, AnyRef]("a" -> new Integer(12), "b" -> Seq(1, 2), "c" ->null, "d" ->  new SimpleDateFormat( "d/MM/yyyy hh:mm:ss a").format(new Date())  )

  val mp  = new MapPrinter


  mp.pri(map)


}
