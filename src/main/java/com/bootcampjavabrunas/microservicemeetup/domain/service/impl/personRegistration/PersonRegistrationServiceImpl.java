package com.bootcampjavabrunas.microservicemeetup.domain.service.impl.personRegistration;

import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.MeetupService;
import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;
import com.bootcampjavabrunas.microservicemeetup.domain.service.exception.EntityNotFoundException;
import com.bootcampjavabrunas.microservicemeetup.infraestructure.repository.personRegistration.PersonRegistrationRepository;
import com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.PersonRegistrationService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public PersonRegistration update(String id, PersonRegistration personRegistration) {
        Optional<PersonRegistration> optionalPersonRegistration = repository.findById(new ObjectId(id));

        if (optionalPersonRegistration.isPresent()) {
            personRegistration.setId(optionalPersonRegistration.get().getId());
            return repository.save(personRegistration);
        }

        return null;
    }

    @Override
    public PersonRegistration find(String id) {
        Optional<PersonRegistration> optionalPersonRegistration = repository.findById(new ObjectId(id));
        return optionalPersonRegistration.orElse(null);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(new ObjectId(id));
    }

    @Override
    public List<PersonRegistration> findAll() {
        return repository.findAll();
    }

    @Override
    public List<PersonRegistration> findByMeetup(String idMeetup) {
        List<PersonRegistration> personRegistrationList = repository.findByIdMeetup(idMeetup);
        return personRegistrationList;
    }

    private void validateMeetup(final PersonRegistration personRegistration) {
        Meetup idMeetup = meetupService.find(personRegistration.getIdMeetup());
        if (Objects.isNull(idMeetup)) {
            throw new EntityNotFoundException("Meetup não encontrado");
        } else {
            List<PersonRegistration> personRegistrationList = new ArrayList<>();
            personRegistrationList.add(personRegistration);
        }
    }
}