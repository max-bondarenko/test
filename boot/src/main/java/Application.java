import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.max.boot.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Created by Maksym_Bondarenko on 6/4/2016.
 */
@EnableAutoConfiguration
@Import({
        Configuration.class
}

)
@EnableJSONDoc
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
