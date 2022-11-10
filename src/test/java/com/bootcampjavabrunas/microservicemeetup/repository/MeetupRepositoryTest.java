package com.bootcampjavabrunas.microservicemeetup.repository;

import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import com.bootcampjavabrunas.microservicemeetup.infraestructure.repository.meetup.MeetupRepository;
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
public class MeetupRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    MeetupRepository repository;

    @Test
    @DisplayName("Should return true when exists an registration attribute already created")
    public void returnTrueWhenRegistrationAttributeExistsTest() {

        String registrationAttribute = "200";

        Meetup reg = createNewMeetup(registrationAttribute);
        entityManager.persist(reg);

        boolean exist = repository.existsByEvent(reg.getEvent());

        assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("Should return false when doesn't exists an registration attribute with a registration already created")
    public void returnFalseWhenRegistrationAttributeDoesntExistsTest() {

        String registrationAttribute = "123";

        boolean exist = repository.existsByEvent(registrationAttribute);

        assertThat(exist).isFalse();
    }

    @Test
    @DisplayName("Should get an meetup by id")
    public void findByIdTest() {

        Meetup registration_attribute = createNewMeetup("111");
        entityManager.persist(registration_attribute);

        Optional<Meetup> foundRegistrationAttribute = repository.findById(registration_attribute.getId());

        assertThat(foundRegistrationAttribute.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should save an meetup")
    public void saveMeetupTest() {

        Meetup registrationAttribute = createNewMeetup("115");

        Meetup savedRegistrationAttribute = repository.save(registrationAttribute);

        assertThat(savedRegistrationAttribute.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should delete a meetup from the base")
    public void deleteMeetupTest() {

        Meetup registrationAttribute = createNewMeetup("115");
        entityManager.persist(registrationAttribute);

        Meetup foundMeetup = entityManager.find(Meetup.class, registrationAttribute.getId());

        repository.delete(foundMeetup);

        Meetup deleteRegistrationAttribute = entityManager.find(Meetup.class, registrationAttribute.getId());

        assertThat(deleteRegistrationAttribute).isNull();
    }

    private Meetup createNewMeetup(String registrationAttribute) {
        return Meetup.builder().event("Bootcamp").meetupDate("01/05/2022").registrationAttribute(registrationAttribute).build();
    }

}