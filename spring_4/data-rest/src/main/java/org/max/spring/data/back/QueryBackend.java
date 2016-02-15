package org.max.spring.data.back;

import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */

public interface QueryBackend<T> {

    String getTemplate(String templateName);

    T execute(String template);

    T execute(String templateName, Map<String, ?> placeholders);


}
