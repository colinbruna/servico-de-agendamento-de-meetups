package com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.dto.converter;

import com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.dto.PersonRegistrationDTO;
import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonRegistrationConverterImpl implements PersonRegistrationConverter{

    private final ModelMapper mapper;

    public PersonRegistrationConverterImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PersonRegistration convertToPersonRegistration(PersonRegistrationDTO personRegistrationDTO) {
        return personRegistrationDTO == null?
                null:
                mapper.map(personRegistrationDTO, PersonRegistration.class);
    }

    @Override
    public PersonRegistrationDTO convertToDto(PersonRegistration personRegistration) {
        return personRegistration == null?
                null:
                mapper.map(personRegistration, PersonRegistrationDTO.class);
    }

    @Override
    public List<PersonRegistrationDTO> convertToDtoList(List<PersonRegistration> personRegistrationList) {
        List<PersonRegistrationDTO> personRegistrationDTOList = new ArrayList<>();

        for (PersonRegistration personRegistration : personRegistrationList) {
            personRegistrationDTOList.add(convertToDto(personRegistration));
        }

        return personRegistrationDTOList;
    }
}
