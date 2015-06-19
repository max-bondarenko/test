package org.max.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by add on 19.06.2015.
 */
@Embeddable
public class ListItem {
    @Column
    private String type;

    public ListItem() {
    }

    public ListItem(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
