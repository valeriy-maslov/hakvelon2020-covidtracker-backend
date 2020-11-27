package com.covidtracker;
import com.covidtracker.web.AccountModel;
import com.covidtracker.web.ChecklistModel;
import com.covidtracker.web.EmployeeModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class CovidTrackerApp {
    public static void main(String[] args) {
        SpringApplication.run(CovidTrackerApp.class, args);
    }

    public static void CreateUser(AccountModel userData){
        System.out.println("user created");
    }

    public static void Login(String email, String password){
        System.out.println("you logged in");
    }

    public static void UpdateUserData(AccountModel userData){
        System.out.println("account info updated");
    }

    public static void SendChecklist(){
        System.out.println("checklist was sent");
    }

    public static void SubmitChecklist(ChecklistModel checklist){
        System.out.println("checklist was submitted");
    }

    public static void RestorePassword(String email, String submitCode, String newPassword){
        System.out.println("password was reset");
    }

    public static void EnableNotifications(boolean notificationsEnabled){
        System.out.println("notifications were enabled");
    }

    public static void UpdateEmployeeInfo(EmployeeModel employeeModel){
        System.out.println("info was updated");
    }
}
