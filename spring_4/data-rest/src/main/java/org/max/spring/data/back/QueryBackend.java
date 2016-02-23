package org.max.spring.data.back;

import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public interface QueryBackend {
    /**
     * Base url for querying.
     *
     * @return
     */
    String getBaseUrl();

    String getTemplate(String templateName);

    <T> T executeByUrl(String url, HttpMethod method, Class<T> responseType, Object[] parameters);

    <T> T execute(String templateName, Map<String, ?> placeholders, Class<T> responseType);


}
