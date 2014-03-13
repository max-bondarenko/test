package com.max.entities;



import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

/**
 * Created by mbondarenko on 13.03.14.
 */
@Entity
@Table(name = "RECORD")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "VALUE")
    private String value;

//    @ManyToOne
//    @JoinColumn(name = "POST_ID")
//    private Post post;

    public Record() {
    }

    public Record(String value) {
        this.value = value;
    }

    public Record(Integer id, String value) {
        this.id = id;
        this.value = value;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                '}';
    }
}
