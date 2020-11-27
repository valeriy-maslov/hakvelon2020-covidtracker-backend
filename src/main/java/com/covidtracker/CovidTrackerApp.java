package com.covidtracker;

import com.covidtracker.web.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.websocket.server.PathParam;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication

public class CovidTrackerApp {
    public static void main(String[] args) {
        SpringApplication.run(CovidTrackerApp.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS")
                        .allowedOrigins("http://localhost:8080", "http://localhost:4200");
            }
        };
    }


    public static void CreateUser(AccountModel userData) {
        System.out.println("user created");
    }

    public static void Login(String email, String password) {
        System.out.println("you logged in");
    }

    public static void UpdateUserData(AccountModel userData) {
        System.out.println("account info updated");
    }

    public static void SendChecklist() {
        System.out.println("checklist was sent");
    }

    public static void SubmitChecklist(ChecklistModel checklist) {
        System.out.println("checklist was submitted");
    }

    public static void RestorePassword(String email, String submitCode, String newPassword) {
        System.out.println("password was reset");
    }

    public static void EnableNotifications(boolean notificationsEnabled) {
        System.out.println("notifications were enabled");
    }

    public static void UpdateEmployeeInfo(EmployeeModel employeeModel) {
        System.out.println("info was updated");
    }

    @Autowired
    private VisitsRepository visitRepository;

    public List<String> getVisitorsByDate(@PathParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<VisitModel> visitors = visitRepository.findAllByDate(Date.valueOf(date));
        List<String> visitorEmails = new ArrayList<>();
        if (!visitors.isEmpty()) {
            for (VisitModel el : visitors) {
                visitorEmails.add(el.accountModel.email);
            }
            return visitorEmails;
        } else {
            return null;
        }
    }

}
