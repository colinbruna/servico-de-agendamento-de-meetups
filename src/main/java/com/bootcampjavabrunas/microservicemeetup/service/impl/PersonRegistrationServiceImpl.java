package com.bootcampjavabrunas.microservicemeetup.service.impl;

import com.bootcampjavabrunas.microservicemeetup.exception.BusinessException;
import com.bootcampjavabrunas.microservicemeetup.model.entity.PersonRegistration;
import com.bootcampjavabrunas.microservicemeetup.repository.PersonRegistrationRepository;
import com.bootcampjavabrunas.microservicemeetup.service.PersonRegistrationService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonRegistrationServiceImpl implements PersonRegistrationService {

    PersonRegistrationRepository repository;

    public PersonRegistrationServiceImpl(PersonRegistrationRepository repository) {
        this.repository = repository;
    }

    public PersonRegistration save(PersonRegistration registration) {

        if (repository.existsByRegistration(registration.getRegistration())) {
            throw new BusinessException("Registration already created");
        }

        return repository.save(registration);
    }

    @Override
    public Optional<PersonRegistration> getRegistrationById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(PersonRegistration registration) {
        if (registration == null || registration.getId() == null) {
            throw new IllegalArgumentException("Registration id cannot be null");
        }
        this.repository.delete(registration);
    }

    @Override
    public PersonRegistration update(PersonRegistration registration) {
        if (registration == null || registration.getId() == null) {
            throw new IllegalArgumentException("Registration id cannot be null");
        }
        return this.repository.save(registration);
    }

    @Override
    public Page<PersonRegistration> find(PersonRegistration filter, Pageable pageRequest) {
        Example<PersonRegistration> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example, pageRequest);
    }

    @Override
    public Optional<PersonRegistration> getRegistrationByRegistrationAttribute(String registrationAttribute) {
        return repository.findByRegistration(registrationAttribute);
    }
}