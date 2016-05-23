package org.max.update;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by add on 22.05.2016.
 */
public class DbUpdateExample {
    static final String USER_TABLE_NAME = "User";
    private JdbcTemplate template;

    public void setDataSource(DataSource ds){
        template = new JdbcTemplate(ds);
    }

    public void update(User e) {
        String s = "UPDATE " + USER_TABLE_NAME + " SET ";
        // calc not null
        Map<String, Object> l = new LinkedHashMap<>();
        l.put("comment", e.getComment());
        l.put("name", e.getName());
        l.put("email", e.getEmail());
        l.put("supervisorName", e.getSupervisorName());
        l.put("supervisorEmail", e.getSupervisorEmail());
        l.put("supervisorPhone", e.getSupervisorPhone());
        l.put("belongsToId", e.getBelongsToId());
        l.put("type", e.getType());
        l.put("roleId", e.getRoleId());
        l.put("isActive", e.getActive());

        Map<String, Object> filtered = Maps.filterEntries(l, new Predicate<Map.Entry<String, Object>>() {
            @Override
            public boolean apply(Map.Entry<String, Object> input) {
                return input.getValue() != null;
            }
        });
        Collection<Object> values = filtered.values();
        int collumnsToUpdate = values.size();
        if (collumnsToUpdate == 0) {
//            log.warn("No update for User");

            return;
        }
        // generate update SQL
        s += Joiner.on(',').join(Collections2.transform(filtered.keySet(), new Function<Object, String>() {
            @Override
            public String apply(Object input) {
                return input + " = ? ";
            }
        }));
        Object buf[] = new Object[collumnsToUpdate + 1];
        values.toArray(buf);
        s += " WHERE id = ? ";
        // set last param as id
        buf[collumnsToUpdate] = e.getId();
        if (template.update(s, buf) != 1) {
            //throw if nothing to update
            throw new InvalidResultSetAccessException("NO update", "s", null);
        }
    }
}
