package org.max.sp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Maksym_Bondarenko on 1/25/2016.
 */
@Configuration
@ImportResource({"classpath:context1.xml"})
@ComponentScan("org.max.spr.core.bean")
public class RootConfig {

    @Override
    public String toString() {
        return "Root";
    }
}
