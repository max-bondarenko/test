package org.max.data.rep;

import org.max.data.config.GetRepository;

import java.util.List;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public interface NotSimpleRepo extends GetRepository<Map, String> {

    List<Map> byName(String name);
}
