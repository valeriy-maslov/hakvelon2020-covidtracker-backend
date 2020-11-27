package com.covidtracker.web;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "checklists")
public class ChecklistModel {

    @Id
    @GeneratedValue
    public Long id;

    public Number bodyTemperatureMorning;
    public Number bodyTemperatureEvening;
    public Boolean soreThroat;
    public Boolean cough;
    public Boolean snuffle;
    public Boolean isolation;
    public Date date;

    @ManyToOne
    @JoinColumn(name = "email")
    public AccountModel accountModel;
}
