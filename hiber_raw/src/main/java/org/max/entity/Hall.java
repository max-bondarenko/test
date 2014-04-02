package org.max.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by mbondarenko on 01.04.14.
 */
@Entity
public class Hall {
    private Integer id;
    private String name;
    private Set<String> traditionalNames;
    private Set<Line> lines;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    @Basic
    public String getName() {
        return name;
    }

    @ElementCollection
    @ForeignKey(name = "fk_hall_traditional_id")
    public Set<String> getTraditionalNames() {
        return traditionalNames;
    }

    @OneToMany
    @ForeignKey(name = "fk_hall_id", inverseName = "fk_line_id")
    public Set<Line> getLines() {
        return lines;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTraditionalNames(Set<String> traditionalNames) {
        this.traditionalNames = traditionalNames;
    }

    public void setLines(Set<Line> lines) {
        this.lines = lines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hall hall = (Hall) o;

        if (getId() != null ? !getId().equals(hall.getId()) : hall.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
