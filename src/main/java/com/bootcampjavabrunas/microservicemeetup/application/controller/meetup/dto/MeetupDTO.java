package com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MeetupDTO {

    private String event;

    private String meetupDate;
}