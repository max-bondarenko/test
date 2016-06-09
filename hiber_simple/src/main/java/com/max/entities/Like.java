package com.max.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by mbondarenko on 13.03.14.
 */
@Entity
@Table(name ="LIKE_")
public class Like {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Integer id;
    @Column(name="val")
    @Type(type = "yes_no")
    private Boolean negative;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getNegative() {
        return negative;
    }

    public void setNegative(Boolean negative) {
        this.negative = negative;
    }

    @Override
    public String toString() {
        return "Like{id=" + id + ", negative=" + negative + '}';
    }
}
