package org.max.spring.data.config.query.druid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/4/2016.
 */
public class Interval<T extends HasParent, Parent extends T> implements HasParent<Parent> {

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private StringBuilder b;
    private Parent p;
    private Date from;
    private Date to;

    public Interval(StringBuilder b, Parent p) {
        this.p = p;
        this.b = b;
    }

    public Interval<T, ?> from(Date from) {
        this.from = from;
        if (to != null && this.from != null) {
            b.setLength(0);
            b.trimToSize();
            b.append(this.from).append('/').append(to);
        }
        return this;
    }

    public Interval<T, ?> to(Date to) {
        this.to = to;
        makeString();
        return this;
    }

    private void makeString() {
        if (to != null && from != null) {
            b.setLength(0);
            b.trimToSize();
            b.append(df.format(from)).append('/').append(df.format(to));
        }
    }

    @Override
    public Parent end() {
        return p;
    }

    @Override
    public String build() {
        return p.build();
    }

    public static class Intervals<T extends HasParent, Parent extends T> extends Base<Parent> {
        private StringBuilder sub;

        public Intervals(Map<String, Object> b, Parent p) {
            super(b, p);
        }

        public Interval<Intervals<T, ?>, ?> add() {
            sub = new StringBuilder();
            return new Interval<>(sub, this);
        }

        @Override
        public Parent end() {
            if (sub != null && sub.length() > 0) {
                getFields().add(sub.toString());
            }
            return p;
        }

        private Collection<String> getFields() {
            Collection<String> filters = (Collection<String>) b.get("intervals");
            if (filters == null || filters.isEmpty()) {
                filters = new LinkedList<>();
                b.put("intervals", filters);
            }
            return filters;
        }

    }

}
