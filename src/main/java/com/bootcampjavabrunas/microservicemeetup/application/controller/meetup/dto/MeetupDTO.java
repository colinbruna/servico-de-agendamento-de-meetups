package com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MeetupDTO {

    private String eventName;
    private String eventDate;
    private String place;
}