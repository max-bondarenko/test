package org.max.sp;

import org.max.sp.config.RootConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Maksym_Bondarenko on 1/25/2016.
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.max.beans")
@Import(RootConfig.class)
public class WebConfig {


    public String toString(){
        return "web";
    }
}
