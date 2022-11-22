package com.bootcampjavabrunas.microservicemeetup.infraestructure.repository.meetup;

import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetupRepository extends MongoRepository<Meetup, ObjectId> {

}