package com.covidtracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Profile("dev")
public class AdminApi {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ChecklistRepository checklistRepository;

    /**
     * Used only for the development to reset the app during presentation.
     */
    @PostMapping("/totalreset")
    public void totalReset() {
        accountRepository.deleteAll();
        checklistRepository.deleteAll();
    }
}
