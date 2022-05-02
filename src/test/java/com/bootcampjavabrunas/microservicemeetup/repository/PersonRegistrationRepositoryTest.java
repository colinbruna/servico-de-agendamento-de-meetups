package com.bootcampjavabrunas.microservicemeetup.repository;

import com.bootcampjavabrunas.microservicemeetup.model.entity.PersonRegistration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class PersonRegistrationRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PersonRegistrationRepository repository;

    @Test
    @DisplayName("Should return true when exists an registration already created")
    public void returnTrueWhenRegistrationExistsTest() {

        String registration = "123";

        PersonRegistration registration_attribute = createNewRegistration(registration);
        entityManager.persist(registration_attribute);

        boolean exists = repository.existsByRegistration(registration);

        // assert
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when doesn't exists an registration_attribute with a registration already created")
    public void returnFalseWhenRegistrationAttributeDoesntExistsTest() {

        String registration = "123";

        boolean exists = repository.existsByRegistration(registration);

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should get an registration by id")
    public void findByIdTest() {

        PersonRegistration registration_attribute = createNewRegistration("323");
        entityManager.persist(registration_attribute);

        Optional<PersonRegistration> foundRegistration = repository.findById(registration_attribute.getId());

        assertThat(foundRegistration.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should save an registration")
    public void saveRegistrationTest() {

        PersonRegistration registration_attribute = createNewRegistration("323");

        PersonRegistration savedRegistration = repository.save(registration_attribute);

        assertThat(savedRegistration.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should delete a registration from the base")
    public void deleteRegistrationTest() {

        PersonRegistration registration_attribute = createNewRegistration("323");
        entityManager.persist(registration_attribute);

        PersonRegistration foundRegistration = entityManager.find(PersonRegistration.class, registration_attribute.getId());

        repository.delete(foundRegistration);

        PersonRegistration deleteRegistration = entityManager.find(PersonRegistration.class, registration_attribute.getId());

        assertThat(deleteRegistration).isNull();
    }

    private PersonRegistration createNewRegistration(String registration) {
        return PersonRegistration.builder().name("Bruna Silva").dateOfRegistration("10/10/2021").registration(registration).build();
    }
}