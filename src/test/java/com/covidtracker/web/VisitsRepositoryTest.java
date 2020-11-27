package com.covidtracker.web;

import com.covidtracker.CovidTrackerApp;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = CovidTrackerApp.class)
public class VisitsRepositoryTest {

    @Autowired
    VisitsRepository visitsRepository;

    @Autowired
    AccountRepository accountsRepository;

    @Autowired
    CovidTrackerApp app;

    @Test
    public void testRepo() {
        AccountModel userA = new AccountModel();
        userA.email = "ffooo";

        Date date = new Date(System.currentTimeMillis());
        VisitModel visitModel = VisitModel.ValueOf(date, userA);
        visitsRepository.save(visitModel);
        visitsRepository.flush();

        List<VisitModel> all = visitsRepository.findAll();
        assertThat(all).size().isEqualTo(1);
    }

    @Test
    public void testEmailsList(){
        AccountModel userA = new AccountModel();
        AccountModel userB = new AccountModel();

        Date date = new Date(System.currentTimeMillis());

        userA.email = "visitor@first.com";
        userB.email = "visitor@second.com";

        VisitModel visitModelA = VisitModel.ValueOf(date, userA);
        VisitModel visitModelB = VisitModel.ValueOf(date, userB);

        List<String> expected = new ArrayList<>();
        expected.add("visitor@first.com");
        expected.add("visitor@second.com");

        accountsRepository.saveAll(Lists.newArrayList(userA, userB));
        accountsRepository.flush();

        visitsRepository.saveAll(Lists.newArrayList(visitModelA, visitModelB));
        visitsRepository.flush();

        List<String> actual = app.getVisitorsByDate(date.toLocalDate());
        assertThat(actual).isEqualTo(expected);

    }
}