package com.essaadani.bankingapp.query.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplayEventsService {
    private final EventProcessingConfiguration eventProcessingConfiguration;

    public void replay(){
        String name = "com.essaadani.bankingapp.query.service";

        eventProcessingConfiguration.eventProcessor(name, TrackingEventProcessor.class)
                .ifPresent(proc -> {
                    proc.shutDown();
                    proc.resetTokens();
                    proc.start();
                });
    }
}
