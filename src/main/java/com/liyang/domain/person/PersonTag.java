package com.liyang.domain.person;

import com.liyang.domain.base.AbstractTag;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by win7 on 2017-07-25.
 */
@Entity
@Table(name = "person_tag")
//person enterpise user
public class PersonTag extends AbstractTag{
    @ManyToMany(targetEntity = Person.class,mappedBy = "personTags")
    private Set<Person> personSet=new HashSet<Person>();
    //优先级


    public void setPersonSet(Set<Person> personSet) {
        this.personSet = personSet;
    }

    public Set<Person> getPersonSet() {
        return personSet;
    }
}
