package org.max.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by mbondarenko on 01.04.14.
 */
@Entity
public class Line {
    private Integer id;
    private Character letter;
    private Set<Stack> stacks;

    @Id
    public Integer getId() {
        return id;
    }

    @Basic
    public Character getLetter() {
        return letter;
    }

    @OneToMany
    public Set<Stack> getStacks() {
        return stacks;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public void setStacks(Set<Stack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (getId() != null ? !getId().equals(line.getId()) : line.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
