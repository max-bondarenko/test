package org.max.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by add on 17.06.2015.
 */
@Entity
public class One {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String type;

    public One() {
    }

    public One(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

