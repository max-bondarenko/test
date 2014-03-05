import org.springframework.jdbc.core.{PreparedStatementCallback, JdbcTemplate}
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
    "jdbc:oracle:thin:@ictu2.us.db.com:1633:NYICTU2",
    "cem_owner",
    "cem10wnr", false)

  val t = new JdbcTemplate(ds)

  t.execute("SELECT sysdate from DUAL" )

}
