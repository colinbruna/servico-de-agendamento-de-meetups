package com.bootcampjavabrunas.microservicemeetup.service;

import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.MeetupService;
import com.bootcampjavabrunas.microservicemeetup.application.controller.exceptions.BusinessException;
import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import com.bootcampjavabrunas.microservicemeetup.infraestructure.repository.meetup.MeetupRepository;
import com.bootcampjavabrunas.microservicemeetup.domain.service.impl.meetup.MeetupServiceImpl;
import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class MeetupServiceTest {

    MeetupService meetupService;

    @MockBean
    MeetupRepository repository;

    @BeforeEach
    public void setUp() {
        this.meetupService = new MeetupServiceImpl(repository);
    }

    @Test
    @DisplayName("Should save an meetup")
    public void saveMeetupTest() {

        // cenario
        Meetup meetup = createValidMeetup();

        // execucao
        Mockito.when(repository.save(meetup)).thenReturn(createValidMeetup());

        Meetup savedMeetup = meetupService.save(meetup);

        // assert

        assertNotNull(savedMeetup);
        assertThat(savedMeetup.getEventName()).isEqualTo("Bootcamp");
        assertThat(savedMeetup.getEventDate()).isEqualTo("22/11/22");
        assertThat(savedMeetup.getPlace()).isEqualTo("Porto Alegre");
    }

//    @Test
//    @DisplayName("Should throw business error when try  " +
//            "to save a new meetup with a event duplicated")
//    public void shouldNotSaveAsEventDuplicatedTest() {
//
//        Meetup meetup = createValidMeetup();
//        Mockito.when(repository.existsByEvent(Mockito.any())).thenReturn(true);
//
//        Throwable exception = Assertions.catchThrowable(() -> meetupService.save(meetup));
//        assertThat(exception)
//                .isInstanceOf(BusinessException.class)
//                .hasMessage("Meetup already created");
//
//        Mockito.verify(repository, Mockito.never()).save(meetup);
//    }
//
//    @Test
//    @DisplayName("Should get an meetup by Id")
//    public void getMeetupByIdTest() {
//
//        // cenario
//        Integer id = 200;
//        Meetup meetup = createValidMeetup();
//        meetup.setId(id);
//        Mockito.when(repository.findById(id)).thenReturn(Optional.of(meetup));
//
//        // execucao
//        Optional<Meetup> foundMeetup = meetupService.getMeetupById(id);
//
//        // assert
//        assertThat(foundMeetup.isPresent()).isTrue();
//        assertThat(foundMeetup.get().getId()).isEqualTo(id);
//        assertThat(foundMeetup.get().getEvent()).isEqualTo(meetup.getEvent());
//        assertThat(foundMeetup.get().getMeetupDate()).isEqualTo(meetup.getMeetupDate());
//    }
//
//    @Test
//    @DisplayName("Should return empty when get an meetup by id when doesn't exists")
//    public void meetupNotFoundByIdTest() {
//
//        Integer id = 100;
//        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
//
//        Optional<Meetup> meetup = meetupService.getMeetupById(id);
//
//        assertThat(meetup.isPresent()).isFalse();
//    }
//
//    @Test
//    @DisplayName("Should delete an meetup")
//    public void deleteMeetupTest() {
//
//        Meetup meetup = Meetup.builder().id(100).build();
//
//        assertDoesNotThrow(() -> meetupService.delete(meetup));
//
//        Mockito.verify(repository, Mockito.times(1)).delete(meetup);
//    }
//
//    @Test
//    @DisplayName("Should update an meetup")
//    public void updateMeetupTest(){
//
//        // cenario
//        Integer id = 101;
//        Meetup updatingMeetup = Meetup.builder().id(101).build();
//
//        // execucao
//        Meetup updatedMeetup = createValidMeetup();
//        updatedMeetup.setId(id);
//
//        Mockito.when(repository.save(updatingMeetup)).thenReturn(updatedMeetup);
//        Meetup meetup = meetupService.update(updatingMeetup);
//
//        // assert
//        assertThat(meetup.getId()).isEqualTo(updatedMeetup.getId());
//        assertThat(meetup.getEvent()).isEqualTo(updatedMeetup.getEvent());
//        assertThat(meetup.getMeetupDate()).isEqualTo(updatedMeetup.getMeetupDate());
//    }

    private Meetup createValidMeetup() {

        Meetup meetup = new Meetup();
        meetup.setEventName("Bootcamp");
        meetup.setEventDate("22/11/22");
        meetup.setPlace("Porto Alegre");

        return meetup;
    }
}
