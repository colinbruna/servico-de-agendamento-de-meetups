package com.bootcampjavabrunas.microservicemeetup.application.controller.meetup;

import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import org.bson.types.ObjectId;

import java.util.List;

public interface MeetupService {

    Meetup save(Meetup meetup);

    Meetup update(ObjectId id, Meetup meetup);

    Meetup find(ObjectId id);

    void delete(ObjectId id);

    List<Meetup> findAll();
}