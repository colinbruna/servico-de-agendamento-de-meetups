package com.bootcampjavabrunas.microservicemeetup.repository;

import com.bootcampjavabrunas.microservicemeetup.model.entity.Meetup;
import com.bootcampjavabrunas.microservicemeetup.model.entity.PersonRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MeetupRepository extends JpaRepository<Meetup, Integer> {

    @Query(value = " select l from Meetup as l join l.registration as b where b.registration = :registration or l.event = :event ")
    Page<Meetup> findByRegistrationOnMeetup(
            @Param("registration") String registration,
            @Param("event") String event,
            Pageable pageable
    );

    Page<Meetup> findByRegistration(PersonRegistration registration, Pageable pageable);

    boolean existsByEvent(String event);

    Optional<Meetup> findByRegistration(String meetupAttribute);
}