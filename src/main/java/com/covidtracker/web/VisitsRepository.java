package com.covidtracker.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitsRepository extends JpaRepository <VisitModel, Long> {
}
