package org.max.entity;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: add
 * Date: 30.03.14
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
@Entity
@javax.persistence.Table(name = "Stack")
@org.hibernate.annotations.Table( appliesTo = "Stack",
        indexes = {@Index(name = "naturalId_ind", columnNames = {"name"})},
        foreignKey = @ForeignKey(name = "fk_book_id")
)
public class Stack {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String name;

    @OneToMany
    @OrderBy(clause = "title asc" )
    private Set<Book> books;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "{" + id +", '" + name + '\'' +

                '}';
    }

}
