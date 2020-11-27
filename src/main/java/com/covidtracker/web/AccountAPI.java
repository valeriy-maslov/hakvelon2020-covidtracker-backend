package com.covidtracker.web;

import com.covidtracker.CovidTrackerApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

/**
 *
 */
@RestController
@RequestMapping("/accounts")
public class AccountAPI {

    static class LoginData {
        public String email;
        public String password;

        public LoginData(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private VisitsRepository visitRepository;

    Logger log = Logger.getLogger(AccountAPI.class.getName());

    @GetMapping("/check")
    public boolean CheckIfUserExists(@PathParam("email") String email){
        log.info("Checking that email exists: " + email);
        return accountRepository.findById(email).isPresent();
    }

    @GetMapping("/{email}")
    public AccountModel getAccount(@PathVariable("email") String email){
        return accountRepository.findById(email).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser (@RequestBody AccountModel accountModel){
        accountRepository.save(accountModel);
        log.info("New account saved with email " + accountModel.email);
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void login (@RequestBody LoginData loginData){
        Optional<AccountModel> account = accountRepository.findById(loginData.email);
        account.ifPresent(accountModel -> {
            Assert.isTrue(accountModel.password.equals(loginData.password), "Invalid password");
            log.info("User " + loginData.email + " logged in");
        });
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUserData(@RequestBody AccountModel accountModel) {
        CovidTrackerApp.UpdateUserData(accountModel);
    }

    @PostMapping("/checklists")
    @ResponseStatus(HttpStatus.OK)
    public void sendChecklist(ChecklistModel checklist){
        CovidTrackerApp.SubmitChecklist(checklist);
        CovidTrackerApp.SendChecklist();
    }

    @PostMapping("/checklists/submit")
    @ResponseStatus(HttpStatus.OK)
    public void submitChecklist(ChecklistModel checklist){
        CovidTrackerApp.SubmitChecklist(checklist);
    }

    @PostMapping("/{email}/visits")
    public void setVisiting(@PathVariable("email") String email, @RequestBody Date date) {
        VisitModel visitModel = VisitModel.ValueOf(date, email);
        visitRepository.save(visitModel);
    }

    /*
        set or update days for office visiting
     */
}
