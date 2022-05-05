package com.bootcampjavabrunas.microservicemeetup.service.impl;

import com.bootcampjavabrunas.microservicemeetup.controller.dto.MeetupFilterDTO;
import com.bootcampjavabrunas.microservicemeetup.exception.BusinessException;
import com.bootcampjavabrunas.microservicemeetup.model.entity.Meetup;
import com.bootcampjavabrunas.microservicemeetup.model.entity.PersonRegistration;
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
    public Optional<Meetup> getMeetupById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Meetup meetup) {
        if (meetup == null || meetup.getId() == null) {
            throw new IllegalArgumentException("Meetup id cannot be null");
        }
        this.repository.delete(meetup);
    }

    @Override
    public Meetup update(Meetup meetup) {
        if (meetup == null || meetup.getId() == null) {
            throw new IllegalArgumentException("Meetup id cannot be null");
        }
        return this.repository.save(meetup);
    }

    @Override
    public Page<Meetup> find(MeetupFilterDTO dto, Pageable pageable) {
        if (dto.getRegistration() != null && dto.getEvent() != null) {
            return repository.findByRegistrationOnMeetup(dto.getRegistration(), dto.getEvent(), pageable);
        }

        return repository.findAll(pageable);
    }

    @Override
    public Page<Meetup> getRegistrationsByMeetup(PersonRegistration registration, Pageable pageable) {
        return repository.findByRegistration(registration, pageable);
    }
}
