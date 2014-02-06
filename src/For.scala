import java.io.File

/**
 * Created by mbondarenko on 14.01.14.
 */
object For {

  def main(args: Array[String]) {
    val files = new File("./src").listFiles()


    for( file <- files){
      val fName = file.getName

      if( fName.endsWith(".scala")) println(fName)

    }

  }

}
