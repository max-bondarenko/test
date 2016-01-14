package org.max.domain;

/*
* SupplyOn CONFIDENTIAL
* __________________
* 
* [2015] - [2020] SupplyOn AG
* All Rights Reserved.
* 
* NOTICE:  All information contained herein is, and remains
* the property of SupplyOn AG.
* The intellectual and technical concepts contained
* herein are proprietary to SupplyOn AG and
* may be covered by European and Foreign Patents,
* patents in process, and are protected by trade secret or copyright law.
* Dissemination of this information or reproduction of this material
* is strictly forbidden unless prior written permission is obtained
* from SupplyOn AG.
* <p/>
* Created by : Maksym_Bondarenko
* Created at : 06-01-2016
*/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SetItem {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;

    public SetItem() {
    }

    public SetItem(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetItem setItem = (SetItem) o;

        if (name != null ? !name.equals(setItem.name) : setItem.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
