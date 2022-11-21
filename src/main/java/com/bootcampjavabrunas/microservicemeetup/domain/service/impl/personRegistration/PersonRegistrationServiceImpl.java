package com.bootcampjavabrunas.microservicemeetup.domain.service.impl.personRegistration;

import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.MeetupService;
import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;
import com.bootcampjavabrunas.microservicemeetup.infraestructure.repository.personRegistration.PersonRegistrationRepository;
import com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.PersonRegistrationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PersonRegistrationServiceImpl implements PersonRegistrationService {

    private final PersonRegistrationRepository repository;
    private final MeetupService meetupService;

    public PersonRegistrationServiceImpl(PersonRegistrationRepository repository, MeetupService meetupService) {
        this.repository = repository;
        this.meetupService = meetupService;
    }

    @Override
    public PersonRegistration save(PersonRegistration registration) {
        validateMeetup(registration);
        return repository.save(registration);
    }

    @Override
    public List<PersonRegistration> findByMeetup(String idMeetup) {
        List<PersonRegistration> personRegistrationList = repository.findByIdMeetup(idMeetup);
        return personRegistrationList;
    }

//    @Override
//    public Optional<PersonRegistration> getRegistrationById(Integer id) {
//        return this.repository.findById(id);
//    }
//
//    @Override
//    public void delete(PersonRegistration registration) {
//        if (registration == null || registration.getId() == null) {
//            throw new IllegalArgumentException("Registration id cannot be null");
//        }
//        this.repository.delete(registration);
//    }
//
//    @Override
//    public PersonRegistration update(PersonRegistration registration) {
//        if (registration == null || registration.getId() == null) {
//            throw new IllegalArgumentException("Registration id cannot be null");
//        }
//        return this.repository.save(registration);
//    }
//
//    @Override
//    public Page<PersonRegistration> find(PersonRegistration filter, Pageable pageRequest) {
//        Example<PersonRegistration> example = Example.of(filter,
//                ExampleMatcher
//                        .matching()
//                        .withIgnoreCase()
//                        .withIgnoreNullValues()
//                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
//
//        return repository.findAll(example, pageRequest);
//    }
//
//    @Override
//    public Optional<PersonRegistration> getRegistrationByRegistrationAttribute(String registrationAttribute) {
//        return repository.findByRegistration(registrationAttribute);
//    }

    private void validateMeetup(final PersonRegistration personRegistration) {
        Meetup idMeetup = meetupService.find(personRegistration.getIdMeetup());
        if (Objects.isNull(idMeetup)) {
            throw new RuntimeException("Meetup não encontrado");
        } else {
            List<PersonRegistration> personRegistrationList = new ArrayList<>();
            personRegistrationList.add(personRegistration);
        }
    }
}