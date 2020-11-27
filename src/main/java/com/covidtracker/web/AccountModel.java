package com.covidtracker.web;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class AccountModel {

    @Id
    public String email;
    public String password;
    public String lastName;
    public String middleName;
    public String firstName;
    public String title;
}
