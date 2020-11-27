package com.covidtracker.web;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "visits")
public class VisitModel {
    @Id
    @GeneratedValue
    public Long id;

    public Date date;

    @ManyToOne
    @JoinColumn(name = "email")
    public AccountModel accountModel;

    public static VisitModel ValueOf(Date date, String email){
        AccountModel accountModel = new AccountModel();
        accountModel.email = email;

        VisitModel visitModel = new VisitModel();
        visitModel.date = date;
        visitModel.accountModel = accountModel;

        return visitModel;
    }

}
