package com.bootcampjavabrunas.microservicemeetup.application.controller.meetup;

import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto.MeetupFilterDTO;
import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MeetupService {

    Meetup save(Meetup meetup);

//    Optional<Meetup> getMeetupById(ObjectId id);
//
//    void delete(Meetup meetup);
//
//    Meetup update(Meetup loan);
//
//    Page<Meetup> find(MeetupFilterDTO filterDTO, Pageable pageable);
//
//    Page<Meetup> getRegistrationsByMeetup(PersonRegistration registration, Pageable pageable);
}