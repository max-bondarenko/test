package org.max.spring.data.config.query.old;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public class SearchFilter<T extends HasParent, Parent extends T> extends Base<Parent> {

    SearchFilter(Map<String, Object> b, Parent p) {
        super(b, p);
        b.put("type", "search");
    }

    public SearchFilter<T, ?> dimension(String dim) {
        b.put("dimension", dim);
        return this;
    }

    public SearchFilterQuery query() {
        SearchFilterQuery searchFilterQuery = new SearchFilterQuery();
        searchFilterQuery.sub = new HashMap<>();
        b.put("v2", searchFilterQuery.sub);
        return searchFilterQuery;
    }


    public static enum QueryType {
        insensitive_contains
    }

    public class SearchFilterQuery {
        protected Map<String, Object> sub;

        public SearchFilterQuery type(QueryType type) {
            sub.put("type", type.name());
            return this;
        }

        public SearchFilterQuery value(String value) {
            sub.put("value", value);
            return this;
        }

        public SearchFilter<T, ?> end() {
            return SearchFilter.this;
        }
    }

}
