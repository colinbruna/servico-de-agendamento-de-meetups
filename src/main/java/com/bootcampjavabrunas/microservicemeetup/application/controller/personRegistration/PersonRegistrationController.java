package com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration;

import com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.dto.PersonRegistrationDTO;
import com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.dto.converter.PersonRegistrationConverter;
import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class PersonRegistrationController {

    private final PersonRegistrationService service;
    private final PersonRegistrationConverter converter;

    @PostMapping
    public ResponseEntity<PersonRegistrationDTO> create(@RequestBody @Valid PersonRegistrationDTO registrationDTO) {
        PersonRegistration personRegistration = converter.convertToPersonRegistration(registrationDTO);
        return new ResponseEntity<>(converter.convertToDto(service.save(personRegistration)), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonRegistrationDTO> update(@PathVariable String id,
                                        @RequestBody @Valid PersonRegistrationDTO registrationDTO) {
        PersonRegistration personRegistration = converter.convertToPersonRegistration(registrationDTO);
        PersonRegistration personRegistrationUpdated = service.update(id, personRegistration);

        return personRegistrationUpdated == null?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(converter.convertToDto(personRegistrationUpdated));
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonRegistrationDTO> find(@PathVariable String id) {
        PersonRegistration personRegistration = service.find(id);

        return personRegistration == null?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(converter.convertToDto(personRegistration));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PersonRegistrationDTO> delete(@PathVariable String id) {
        if (Objects.isNull(service.find(id))) {
            return ResponseEntity.notFound().build();
        }

        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PersonRegistrationDTO>> findAll() {
        List<PersonRegistration> personRegistrationList = service.findAll();
        return ResponseEntity.ok(converter.convertToDtoList(personRegistrationList));
    }

    @GetMapping("/meetup/{idMeetup}")
    public ResponseEntity<List<PersonRegistrationDTO>> findByMeetup(@PathVariable String idMeetup) {
        List<PersonRegistration> personRegistrationList = service.findByMeetup(idMeetup);
        return ResponseEntity.ok(converter.convertToDtoList(personRegistrationList));
    }
}