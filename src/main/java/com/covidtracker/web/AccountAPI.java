package com.covidtracker.web;

import com.covidtracker.CovidTrackerApp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/accounts")
public class AccountAPI {

    @GetMapping
    public boolean CheckIfUserExists(@PathParam("email") String email){
        return false;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser (@RequestBody AccountModel accountModel){
        CovidTrackerApp.CreateUser(accountModel);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login (String email, String password){
        CovidTrackerApp.Login(email, password);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUserData(@RequestBody AccountModel accountModel) {
        CovidTrackerApp.UpdateUserData(accountModel);
    }

    @PostMapping("/sendChecklist")
    @ResponseStatus(HttpStatus.OK)
    public void sendChecklist(ChecklistModel checklist){
        CovidTrackerApp.SubmitChecklist(checklist);
        CovidTrackerApp.SendChecklist();
    }

    @PostMapping("/submitChecklist")
    @ResponseStatus(HttpStatus.OK)
    public void submitChecklist(ChecklistModel checklist){
        CovidTrackerApp.SubmitChecklist(checklist);
    }

    /*
        set or update days for office visiting
     */
}
