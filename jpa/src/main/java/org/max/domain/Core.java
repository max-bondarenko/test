package org.max.domain;

import javax.persistence.*;
import java.util.List;

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


    @ElementCollection
    @CollectionTable(name = "items")
    private List<ListItem> list;


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

    public List<ListItem> getList() {
        return list;
    }

    public void setList(List<ListItem> list) {
        this.list = list;
    }
}
