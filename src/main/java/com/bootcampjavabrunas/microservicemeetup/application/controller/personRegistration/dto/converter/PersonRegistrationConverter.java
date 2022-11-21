package com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.dto.converter;

import com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.dto.PersonRegistrationDTO;
import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;

import java.util.List;

public interface PersonRegistrationConverter {

    PersonRegistration convertToPersonRegistration (PersonRegistrationDTO personRegistrationDTO);

    PersonRegistrationDTO convertToDto (PersonRegistration personRegistration);

    List<PersonRegistrationDTO> convertToDtoList(List<PersonRegistration> personRegistrationList);
}
