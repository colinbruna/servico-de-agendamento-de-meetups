package com.bootcampjavabrunas.microservicemeetup.repository;

import com.bootcampjavabrunas.microservicemeetup.model.entity.PersonRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRegistrationRepository extends JpaRepository<PersonRegistration, Integer> {

    boolean existsByRegistration(String registration);

    Optional<PersonRegistration> findByRegistration(String registrationAtrb);
}
