package org.max.domain;

import javax.persistence.*;

/**
 * Created by add on 17.06.2015.
 */
@Entity
public class Core {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private One one;


    public Core() {
    }

    public Core(One one) {
        this.one = one;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public One getOne() {
        return one;
    }

    public void setOne(One one) {
        this.one = one;
    }
}
