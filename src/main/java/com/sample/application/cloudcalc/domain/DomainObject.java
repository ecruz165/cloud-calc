package com.sample.application.cloudcalc.domain;

import java.time.LocalDateTime;

public interface DomainObject {
 
    Long getId();
 
    LocalDateTime getCreated();
}
