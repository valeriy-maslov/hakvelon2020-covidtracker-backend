package com.covidtracker.web;

import com.covidtracker.CovidTrackerApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = CovidTrackerApp.class)
public class VisitsRepositoryTest {

    @Autowired
    VisitsRepository repository;

    @Test
    public void testRepo() {
        String email = "ffooo";
        Date date = new Date();
//        VisitModel visitModel = VisitModel.ValueOf(date, email);
//        repository.save(visitModel);
        repository.flush();

        List<VisitModel> all = repository.findAll();
        assertThat(all).size().isEqualTo(1);
    }
}