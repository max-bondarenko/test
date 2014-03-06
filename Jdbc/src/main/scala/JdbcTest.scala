import java.sql.{CallableStatement, PreparedStatement}
import org.springframework.jdbc.core.{CallableStatementCallback, PreparedStatementCallback, JdbcTemplate}
import org.springframework.jdbc.datasource.{SingleConnectionDataSource, SimpleDriverDataSource}


/**
 * Created by mbondarenko on 05.03.14.
 */
object JdbcTest extends App {

  val driver = "oracle.jdbc.driver.OracleDriver"

  try {
    getClass.getClassLoader.loadClass(driver)
  }
  catch {
    case e: Exception => print(e); sys.exit(1)
  }


  val ds = new SingleConnectionDataSource(
    "",
    "",
    "", false)

  val t = new JdbcTemplate(ds)

  val s = t.queryForObject[String]("SELECT sysdate from DUAL", classOf[String])
  println(s)

}
