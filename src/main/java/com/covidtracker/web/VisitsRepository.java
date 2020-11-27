package com.covidtracker.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface VisitsRepository extends JpaRepository <VisitModel, Long> {
    List<VisitModel> findAllByDate(Date date);
}
