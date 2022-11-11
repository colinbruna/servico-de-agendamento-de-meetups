package com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PersonRegistration {


    private Integer id;


    private String name;


    private String dateOfRegistration;


    private String registration;
}