package com.covidtracker.web;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "checklists")
public class ChecklistModel {

    @Id
    @GeneratedValue
    public Long id;

    public Number temperatureMorning;
    public Number temperatureEvening;
    public Boolean soreThroat;
    public Boolean cough;
    public Boolean snuffle;
    public Boolean isolation;
    public Date date;
    public Boolean submitted;

    @ManyToOne
    @JoinColumn(name = "email")
    public AccountModel accountModel;
}
