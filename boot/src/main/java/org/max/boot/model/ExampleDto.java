package org.max.boot.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Maksym_Bondarenko on 6/5/2016.
 */
public class ExampleDto {

    private String val;
    private Date date;

    private Long id;
    private List<ExampleDto> derived;

    public ExampleDto() {
    }

    public ExampleDto(String val, Date date, Long id) {
        this.val = val;
        this.date = date;
        this.id = id;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ExampleDto> getDerived() {
        return derived;
    }

    public void setDerived(List<ExampleDto> derived) {
        this.derived = derived;
    }
}
