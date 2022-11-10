package com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto;

import com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.dto.PersonRegistrationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetupDTO {

    private Integer id;

    private String registrationAttribute;

    private String event;

    private PersonRegistrationDTO registration;
}