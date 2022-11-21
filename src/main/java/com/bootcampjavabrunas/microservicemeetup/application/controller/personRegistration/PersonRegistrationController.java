package com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration;

import com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.dto.PersonRegistrationDTO;
import com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.dto.converter.PersonRegistrationConverter;
import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

//    @GetMapping("{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public PersonRegistrationDTO get (@PathVariable Integer id) {
//
//        return registrationService
//                .getRegistrationById(id)
//                .map(registration -> modelMapper.map(registration, PersonRegistrationDTO.class))
//                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }
//
//    @DeleteMapping("{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteByRegistrationId(@PathVariable Integer id) {
//        PersonRegistration registration = registrationService.getRegistrationById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        registrationService.delete(registration);
//    }
//
//    @PutMapping("{id}")
//    public PersonRegistrationDTO update(@PathVariable Integer id, @RequestBody @Valid PersonRegistrationDTO registrationDTO) {
//        return registrationService.getRegistrationById(id).map(registration -> {
//            registration.setName(registrationDTO.getName());
//            registration.setDateOfRegistration(registrationDTO.getDateOfRegistration());
//            registration.setRegistration(registrationDTO.getRegistration());
//            registration = registrationService.update(registration);
//
//            return modelMapper.map(registration, PersonRegistrationDTO.class);
//        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }
//
//    @GetMapping
//    public Page<PersonRegistrationDTO> find(PersonRegistrationDTO dto, Pageable pageRequest) {
//        PersonRegistration filter = modelMapper.map(dto, PersonRegistration.class);
//        Page<PersonRegistration> result = registrationService.find(filter, pageRequest);
//
//        List<PersonRegistrationDTO> list = result.getContent()
//                .stream()
//                .map(entity -> modelMapper.map(entity, PersonRegistrationDTO.class))
//                .collect(Collectors.toList());
//
//        return new PageImpl<PersonRegistrationDTO>(list, pageRequest, result.getTotalElements());
//    }

    @GetMapping("/meetup/{idMeetup}")
    public ResponseEntity<List<PersonRegistrationDTO>> findByMeetup(@PathVariable String idMeetup) {
        List<PersonRegistration> personRegistrationList = service.findByMeetup(idMeetup);
        return ResponseEntity.ok(converter.convertToDtoList(personRegistrationList));
    }
}