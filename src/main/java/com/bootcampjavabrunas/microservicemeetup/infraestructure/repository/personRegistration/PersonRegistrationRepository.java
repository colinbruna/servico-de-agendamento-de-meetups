package com.bootcampjavabrunas.microservicemeetup.infraestructure.repository.personRegistration;

import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRegistrationRepository extends JpaRepository<PersonRegistration, Integer> {

    boolean existsByRegistration(String registration);

    Optional<PersonRegistration> findByRegistration(String registrationAtrb);
}
