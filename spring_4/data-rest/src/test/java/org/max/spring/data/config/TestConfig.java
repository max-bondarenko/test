package org.max.spring.data.config;

import org.max.spring.data.back.QueryBackend;
import org.max.spring.data.config.annotations.EnableDruidRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

/**
 * Created by Maksym_Bondarenko on 2/15/2016.
 */
@Configuration
@EnableDruidRepositories("org.max.spring.data.config.test_repos")
public class TestConfig {

    @Bean
    public QueryBackend orderService() {
        QueryBackend r = new QueryBackend() {
            private RestTemplate template = new RestTemplate();

            @Override
            public String getBaseUrl() {
                return "http://localhost:8081/druid/v2/";
            }

            @Override
            public void validateTemplate(String name, Set<String> placeholders) {

            }

            @Override
            public Object executeByUrl(String url, HttpMethod method, Class responseType, Object[] parameters) {
                Object retVal = null;
                switch (method) {
                    case GET:
                        retVal = template.getForObject(url, responseType, parameters);
                        break;
                    case POST:
                        retVal = template.postForObject(url, null, responseType, parameters);
                    default:
                        throw new UnsupportedOperationException("Incorrect HTTP method");
                }
                return retVal;
            }

            @Override
            public Object execute(String templateName, Map placeholders, Class respType) {
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
