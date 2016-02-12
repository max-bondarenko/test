package org.max.data.rep;

import org.max.data.config.GetRepository;
import org.max.data.config.annotations.DruidQuery;

import java.util.Date;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */

public interface SimpleRepo extends GetRepository<Map, String> {
    @DruidQuery(dataSource = "wikipedia")
    Map findQuery(Date weq);
}
