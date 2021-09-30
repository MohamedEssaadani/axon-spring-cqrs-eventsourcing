package com.essaadani.bankingapp.coreapi.events;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseEvent <T>{
    @TargetAggregateIdentifier
    @Getter private T id;
}
