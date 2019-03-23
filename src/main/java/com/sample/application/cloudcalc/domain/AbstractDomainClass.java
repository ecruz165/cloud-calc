package com.sample.application.cloudcalc.domain;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbstractDomainClass implements DomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    LocalDateTime created;

	@Override
	public Long getId() {
		return id;
	}
	@Override
	public LocalDateTime getCreated() {
		return created;
	}
	
    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        if (created==null) {
            created = LocalDateTime.now();
        }
    }

}