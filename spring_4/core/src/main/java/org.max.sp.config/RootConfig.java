package org.max.sp.config;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * Created by Maksym_Bondarenko on 1/25/2016.
 */
@Configuration
@ImportResource({"classpath:context1.xml"})
@PropertySource(name = "app", value = "classpath:app.properties")
@ComponentScan("org.max.spr.core.bean")
public class RootConfig {

    @Bean
    @Required
    @Profile({"jv"})
    public PropertySourcesPlaceholderConfigurer get(Environment env) {
        PropertySourcesPlaceholderConfigurer cf = new PropertySourcesPlaceholderConfigurer();
        cf.setEnvironment(env);
        return cf;
    }


    @Override
    public String toString() {
        return "Root";
    }
}
