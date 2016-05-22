package org.max.data.init;


import org.max.spring.data.config.annotations.EnableDruidRepositories;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by Maksym_Bondarenko on 2/9/2016.
 */
@Configuration
@ComponentScan("org.max.data.bean")
@EnableDruidRepositories(basePackages = {"org.max.data.rep"})
public class Init {

    @Bean
    public PropertyPlaceholderConfigurer placeholderHelper() {
        PropertyPlaceholderConfigurer ret = new PropertyPlaceholderConfigurer();
        ret.setLocation(new ClassPathResource("app.properties"));
        return ret;
    }


}
