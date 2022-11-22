package com.bootcampjavabrunas.microservicemeetup.infraestructure.repository.personRegistration;

import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRegistrationRepository extends MongoRepository<PersonRegistration, ObjectId> {

    List<PersonRegistration> findByIdMeetup(String idMeetup);

}
