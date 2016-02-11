package org.max.data.back;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
@Component
public class RepoBackRegistry {

    public Map getSomeData(String  id){
        return new HashMap();
    }
}
