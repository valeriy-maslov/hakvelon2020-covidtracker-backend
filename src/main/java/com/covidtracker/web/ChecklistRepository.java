package com.covidtracker.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface ChecklistRepository extends JpaRepository<ChecklistModel, Long> {

    ChecklistModel findFirstByAccountModel_EmailAndDate(String email, Date date);
}
