package com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration;

import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonRegistrationService {

    PersonRegistration save(PersonRegistration personRegistration);


//    Optional<PersonRegistration> getRegistrationById(Integer id);
//
//    void delete(PersonRegistration registration);
//
//    PersonRegistration update(PersonRegistration registration);
//
//    Page<PersonRegistration> find(PersonRegistration filter, Pageable pageRequest);
//
//    Optional<PersonRegistration> getRegistrationByRegistrationAttribute(String registrationAttribute);

    List<PersonRegistration> findByMeetup(String idMeetup);
}
