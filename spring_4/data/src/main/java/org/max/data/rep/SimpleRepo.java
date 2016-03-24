package org.max.data.rep;


import org.max.spring.data.config.annotations.DruidQuery;
import org.max.spring.data.config.repository.GetRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */

public interface SimpleRepo extends GetRepository<Map, String> {
    @DruidQuery("query1")
    List<Map> findInWikipediaWithDate(Date weq);
}
