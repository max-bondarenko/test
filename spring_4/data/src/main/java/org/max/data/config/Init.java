package org.max.data.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Maksym_Bondarenko on 2/9/2016.
 */
@Configuration
@ComponentScan("org.max.data.back")
@EnableDruidRepositories(basePackages = {"org.max.data.rep"} )
public class Init {

}
