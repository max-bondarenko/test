package com.max.entities;



import javax.persistence.*;
import java.util.List;

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

    @OneToMany
    @JoinTable(
            name ="RECORD_LIKE",
            joinColumns = {@JoinColumn(name = "LIKE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "RECORD__ID")}
    )
    private List<Like> likes;

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

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                '}';
    }
}
