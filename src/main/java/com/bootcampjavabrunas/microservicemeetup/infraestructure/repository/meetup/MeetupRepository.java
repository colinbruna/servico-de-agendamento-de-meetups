package com.bootcampjavabrunas.microservicemeetup.infraestructure.repository.meetup;

import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetupRepository extends MongoRepository<Meetup, ObjectId> {

//    @Query(value = " select l from Meetup as l join l.registration as b where b.registration = :registration or l.event = :event ")
//    Page<Meetup> findByRegistrationOnMeetup(
//            @Param("registration") String registration,
//            @Param("event") String event,
//            Pageable pageable
//    );

//    Page<Meetup> findByRegistration(PersonRegistration registration, Pageable pageable);
//
//    boolean existsByEvent(String event);
//
//    Optional<Meetup> findByRegistration(String meetupAttribute);
}