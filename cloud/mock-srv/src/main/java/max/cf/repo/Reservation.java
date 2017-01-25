package max.cf.repo;

import javax.persistence.*;

/**
 * Created by Maksym_Bondarenko on 1/25/2017.
 */
@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String  name;

    public Reservation() {
    }

    public Reservation(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
