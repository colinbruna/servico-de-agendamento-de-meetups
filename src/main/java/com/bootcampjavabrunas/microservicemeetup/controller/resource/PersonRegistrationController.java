package com.bootcampjavabrunas.microservicemeetup.controller.resource;

import com.bootcampjavabrunas.microservicemeetup.controller.dto.PersonRegistrationDTO;
import com.bootcampjavabrunas.microservicemeetup.model.entity.PersonRegistration;
import com.bootcampjavabrunas.microservicemeetup.service.PersonRegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registration")
public class PersonRegistrationController {

    private PersonRegistrationService registrationService;

    private ModelMapper modelMapper;

    public PersonRegistrationController(PersonRegistrationService registrationService, ModelMapper modelMapper) {
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonRegistrationDTO create(@RequestBody @Valid PersonRegistrationDTO registrationDTO) {

        PersonRegistration entity = modelMapper.map(registrationDTO, PersonRegistration.class);
        entity = registrationService.save(entity);

        return modelMapper.map(entity, PersonRegistrationDTO.class);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonRegistrationDTO get (@PathVariable Integer id) {

        return registrationService
                .getRegistrationById(id)
                .map(registration -> modelMapper.map(registration, PersonRegistrationDTO.class))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByRegistrationId(@PathVariable Integer id) {
        PersonRegistration registration = registrationService.getRegistrationById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        registrationService.delete(registration);
    }

    @PutMapping("{id}")
    public PersonRegistrationDTO update(@PathVariable Integer id, @RequestBody @Valid PersonRegistrationDTO registrationDTO) {
        return registrationService.getRegistrationById(id).map(registration -> {
            registration.setName(registrationDTO.getName());
            registration.setDateOfRegistration(registrationDTO.getDateOfRegistration());
            registration.setRegistration(registrationDTO.getRegistration());
            registration = registrationService.update(registration);

            return modelMapper.map(registration, PersonRegistrationDTO.class);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public Page<PersonRegistrationDTO> find(PersonRegistrationDTO dto, Pageable pageRequest) {
        PersonRegistration filter = modelMapper.map(dto, PersonRegistration.class);
        Page<PersonRegistration> result = registrationService.find(filter, pageRequest);

        List<PersonRegistrationDTO> list = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, PersonRegistrationDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<PersonRegistrationDTO>(list, pageRequest, result.getTotalElements());
    }
}