/**
* <p>Title: Person.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2017</p>
* <p>Company: UOB</p>
* @author RuJia
* @date 2017年1月31日
* @version 1.0
*/
package com.bham.sso.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
// Added some comments Here
/**
* <p>Title: Person.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2017</p>
* <p>Company: UOB</p>
* @author RuJia
* @date 2017年1月31日
* @version 1.0
*/
//Added some code ----- here----
This are the error lines
@Entity
public class Person {
    @Id
//    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    @Override
    public String toString() {
        return "Person [firstName=" + this.firstName + ", lastName=" + this.lastName
                + "]";
    }
    
    public String getName() {
        return this.firstName + " " + this.lastName;
    }
}
