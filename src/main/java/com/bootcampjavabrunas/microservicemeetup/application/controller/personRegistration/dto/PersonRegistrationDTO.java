package com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonRegistrationDTO {

    private String name;
    private String email;
    private String dateRegistration;
    private String idMeetup;
}