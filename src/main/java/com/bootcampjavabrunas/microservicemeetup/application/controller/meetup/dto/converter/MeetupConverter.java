package com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto.converter;

import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto.MeetupDTO;
import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;

import java.util.List;

public interface MeetupConverter {

    Meetup convertToMeetup (MeetupDTO meetupDTO);

    MeetupDTO convertToDto (Meetup meetup);

    List<MeetupDTO> convertToDtoList(List<Meetup> meetups);
}
