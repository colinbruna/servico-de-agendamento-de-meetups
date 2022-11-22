package com.bootcampjavabrunas.microservicemeetup.application.controller.meetup;

import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;

import java.util.List;

public interface MeetupService {

    Meetup save(Meetup meetup);

    Meetup update(String id, Meetup meetup);

    Meetup find(String id);

    void delete(String id);

    List<Meetup> findAll();
}