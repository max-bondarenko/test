
import scala.slick.jdbc.JdbcBackend.Database
import scala.slick.jdbc.StaticQuery


/**
 * Created by mbondarenko on 05.03.14.
 */


object SlickTest extends App {
  val driver = "oracle.jdbc.driver.OracleDriver"

  Database.forURL("",driver = driver, user = "", password = "") withSession (implicit se => {

  val self = StaticQuery[String] + "SELECT sysdate from DUAL"
  val first: String = self().first

  print(first)
  })


}
