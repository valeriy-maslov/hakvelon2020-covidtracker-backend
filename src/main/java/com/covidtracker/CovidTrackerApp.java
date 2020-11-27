package com.covidtracker;

import com.covidtracker.web.AccountModel;
import com.covidtracker.web.ChecklistModel;
import com.covidtracker.web.EmployeeModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;

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
                registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
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
}
