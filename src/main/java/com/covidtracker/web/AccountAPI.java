package com.covidtracker.web;

import com.covidtracker.CovidTrackerApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private ChecklistRepository checklistRepository;

    Logger log = Logger.getLogger(AccountAPI.class.getName());

    @GetMapping("/check")
    public boolean CheckIfUserExists(@PathParam("email") String email) {
        log.info("Checking that email exists: " + email);
        return accountRepository.findById(email).isPresent();
    }

    @GetMapping("/{email}")
    public AccountModel getAccount(@PathVariable("email") String email) {
        return accountRepository.findById(email).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody AccountModel accountModel) {
        accountRepository.save(accountModel);
        log.info("New account saved with email " + accountModel.email);
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginData loginData) {
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

    @PostMapping(path = "/{email}/checklists", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void sendChecklist(@PathVariable("email") String email,
            @PathParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody ChecklistModel checklist) {
        Optional<AccountModel> one = accountRepository.findById(email);
        one.ifPresent(accountModel -> {
            checklist.accountModel = accountModel;
            checklist.date = Date.valueOf(date);
            checklistRepository.save(checklist);
        });
    }

    @PostMapping(path = "/{email}/checklists/submit")
    @ResponseStatus(HttpStatus.OK)
    public void submitChecklist(@PathVariable("email") String email,
            @PathParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ChecklistModel checklistModel = checklistRepository
                .findFirstByAccountModel_EmailAndDate(email, Date.valueOf(date));
        checklistModel.submitted = true;
        checklistRepository.save(checklistModel);
    }

    @GetMapping(path = "/{email}/checklists")
    public ChecklistModel getChecklist(@PathVariable("email") String email,
            @PathParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ChecklistModel checklistModel = checklistRepository
                .findFirstByAccountModel_EmailAndDate(email, Date.valueOf(date));
        return checklistModel;
    }

    @PostMapping("/{email}/visits")
    @ResponseStatus(HttpStatus.CREATED)
    public void setVisiting(@PathVariable("email") String email,
            @PathParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Optional<AccountModel> one = accountRepository.findById(email);
        one.ifPresent(accountModel -> {
            VisitModel visitModel = VisitModel.ValueOf(Date.valueOf(date), accountModel);
            visitRepository.save(visitModel);
        });
    }

    @GetMapping("/{email}/visits")
    public ResponseEntity<Boolean> getVisitForDate(@PathVariable("email") String email,
            @PathParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Optional<AccountModel> one = accountRepository.findById(email);
        if (one.isPresent()) {
            VisitModel visitModel = VisitModel.ValueOf(Date.valueOf(date), one.get());
            boolean exists = visitRepository.exists(Example.of(visitModel));
            return ResponseEntity.ok(exists);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{email}/visits")
    public ResponseEntity<Void> deleteVisit(@PathVariable("email") String email,
            @PathParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Optional<AccountModel> one = accountRepository.findById(email);
        if (one.isPresent()) {
            VisitModel visitModel = VisitModel.ValueOf(Date.valueOf(date), one.get());
            Optional<VisitModel> visitToDelete = visitRepository.findOne(Example.of(visitModel));
            if (!visitToDelete.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            visitRepository.delete(visitToDelete.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
