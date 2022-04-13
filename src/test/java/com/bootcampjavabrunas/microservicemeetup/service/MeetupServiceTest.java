package com.bootcampjavabrunas.microservicemeetup.service;

import com.bootcampjavabrunas.microservicemeetup.model.entity.Meetup;
import com.bootcampjavabrunas.microservicemeetup.repository.MeetupRepository;
import com.bootcampjavabrunas.microservicemeetup.service.impl.MeetupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

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
        Mockito.when(repository.existsByEvent(Mockito.anyString())).thenReturn(false);
        Mockito.when(repository.save(meetup)).thenReturn(createValidMeetup());

        Meetup savedMeetup = meetupService.save(meetup);

        // assert
        assertThat(savedMeetup.getId()).isEqualTo(100);
        assertThat(savedMeetup.getEvent()).isEqualTo("Bootcamp");
        assertThat(savedMeetup.getMeetupDate()).isEqualTo("10/10/2022");
    }

    private Meetup createValidMeetup() {

        return Meetup.builder()
                .id(100)
                .event("Bootcamp")
                .meetupDate("10/10/2022")
                .build();
    }
}
