package com.bootcampjavabrunas.microservicemeetup.infraestructure.repository.personRegistration;

import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PersonRegistrationRepository extends MongoRepository<PersonRegistration, ObjectId> {

//    boolean existsByRegistration(String registration);
//
//    Optional<PersonRegistration> findByRegistration(String registrationAtrb);
}
