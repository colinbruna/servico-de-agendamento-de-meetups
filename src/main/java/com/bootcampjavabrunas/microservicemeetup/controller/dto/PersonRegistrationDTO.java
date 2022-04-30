package com.bootcampjavabrunas.microservicemeetup.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonRegistrationDTO {

    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String dateOfRegistration;

    @NotEmpty
    private String registration;
}