package com.essaadani.bankingapp.query.controllers;

import com.essaadani.bankingapp.query.service.ReplayEventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query/accounts")
@RequiredArgsConstructor
public class ReplayEventsController {
    private final ReplayEventsService replayEventsService;

    @GetMapping("/replayEvents")

    public String replayEvents(){
        replayEventsService.replay();

        return "Success Replaying Events";
    }
}
