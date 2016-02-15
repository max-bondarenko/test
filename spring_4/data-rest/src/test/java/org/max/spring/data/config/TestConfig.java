package org.max.spring.data.config;

import org.max.spring.data.back.QueryBackend;
import org.max.spring.data.config.annotations.EnableDruidRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 2/15/2016.
 */
@Configuration
@EnableDruidRepositories("org.max.spring.data.config.test_repos")
public class TestConfig {

    @Bean
    public QueryBackend orderService() {
        QueryBackend r = new QueryBackend() {
            @Override
            public String getTemplate(String templateName) {
                return null;
            }

            @Override
            public Object execute(String template) {
                return null;
            }

            @Override
            public Object execute(String templateName, Map placeholders) {
                return null;
            }
        };
        return r;
    }

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }

}
