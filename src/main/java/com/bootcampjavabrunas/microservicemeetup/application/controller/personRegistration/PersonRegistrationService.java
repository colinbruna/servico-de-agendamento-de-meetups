package com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration;

import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;

import java.util.List;

public interface PersonRegistrationService {

    PersonRegistration save(PersonRegistration personRegistration);

    PersonRegistration update(String id, PersonRegistration personRegistration);

    PersonRegistration find(String id);

    void delete(String id);

    List<PersonRegistration> findAll();

    List<PersonRegistration> findByMeetup(String idMeetup);
}
