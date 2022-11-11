package com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto.converter;

import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto.MeetupDTO;
import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MeetupConverterImpl implements MeetupConverter{

    private final ModelMapper mapper;

    public MeetupConverterImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Meetup convertToMeetup(MeetupDTO meetupDTO) {
        return meetupDTO == null?
                null:
                mapper.map(meetupDTO, Meetup.class);
    }

    @Override
    public MeetupDTO convertToDto(Meetup meetup) {
        return meetup == null?
                null:
                mapper.map(meetup, MeetupDTO.class);
    }

    @Override
    public List<MeetupDTO> convertToDtoList(List<Meetup> meetups) {
        List<MeetupDTO> meetupDTOList = new ArrayList<>();

        for (Meetup meetup : meetups) {
            meetupDTOList.add(convertToDto(meetup));
        }

        return meetupDTOList;
    }
}