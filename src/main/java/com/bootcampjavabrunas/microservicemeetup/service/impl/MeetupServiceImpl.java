package com.bootcampjavabrunas.microservicemeetup.service.impl;

import com.bootcampjavabrunas.microservicemeetup.controller.dto.MeetupFilterDTO;
import com.bootcampjavabrunas.microservicemeetup.exception.BusinessException;
import com.bootcampjavabrunas.microservicemeetup.model.entity.Meetup;
import com.bootcampjavabrunas.microservicemeetup.model.entity.Registration;
import com.bootcampjavabrunas.microservicemeetup.repository.MeetupRepository;
import com.bootcampjavabrunas.microservicemeetup.service.MeetupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeetupServiceImpl implements MeetupService {

    private MeetupRepository repository;

    public MeetupServiceImpl(MeetupRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meetup save(Meetup meetup) {

        if (repository.existsByEvent(meetup.getEvent())) {
            throw new BusinessException("Meetup already created");
        }
        return repository.save(meetup);
    }

    @Override
    public Optional<Meetup> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Meetup update(Meetup loan) {
        return repository.save(loan);
    }

    @Override
    public Page<Meetup> find(MeetupFilterDTO filterDTO, Pageable pageable) {
        return repository.findByRegistrationOnMeetup( filterDTO.getRegistration(), filterDTO.getEvent(), pageable );
    }

    @Override
    public Page<Meetup> getRegistrationsByMeetup(Registration registration, Pageable pageable) {
        return repository.findByRegistration(registration, pageable);
    }
}
