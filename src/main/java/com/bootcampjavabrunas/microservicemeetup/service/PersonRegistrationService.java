package com.bootcampjavabrunas.microservicemeetup.service;

import com.bootcampjavabrunas.microservicemeetup.model.entity.PersonRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PersonRegistrationService {

    PersonRegistration save(PersonRegistration any);

    Optional<PersonRegistration> getRegistrationById(Integer id);

    void delete(PersonRegistration registration);

    PersonRegistration update(PersonRegistration registration);

    Page<PersonRegistration> find(PersonRegistration filter, Pageable pageRequest);

    Optional<PersonRegistration> getRegistrationByRegistrationAttribute(String registrationAttribute);
}
