package org.max.boot;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.max.boot.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.*;

/**
 * Created by Maksym_Bondarenko on 6/4/2016.
 */
@SpringBootApplication
@Import({
        Configuration.class
}

)
@EnableJSONDoc
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
