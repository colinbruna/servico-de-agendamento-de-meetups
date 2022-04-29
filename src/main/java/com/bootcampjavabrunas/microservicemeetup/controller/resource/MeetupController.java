package com.bootcampjavabrunas.microservicemeetup.controller.resource;

import com.bootcampjavabrunas.microservicemeetup.controller.dto.MeetupDTO;
import com.bootcampjavabrunas.microservicemeetup.controller.dto.MeetupFilterDTO;
import com.bootcampjavabrunas.microservicemeetup.controller.dto.PersonRegistrationDTO;
import com.bootcampjavabrunas.microservicemeetup.model.entity.Meetup;
import com.bootcampjavabrunas.microservicemeetup.model.entity.PersonRegistration;
import com.bootcampjavabrunas.microservicemeetup.service.MeetupService;
import com.bootcampjavabrunas.microservicemeetup.service.PersonRegistrationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meetups")
@RequiredArgsConstructor
public class MeetupController {

    private final MeetupService meetupService;
    private final PersonRegistrationService registrationService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Integer create(@RequestBody MeetupDTO meetupDTO) {

        PersonRegistration registration = registrationService.getRegistrationByRegistrationAttribute(meetupDTO.getRegistrationAttribute())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Meetup entity = Meetup.builder()
                .registration(registration)
                .event(meetupDTO.getEvent())
                .meetupDate("10/10/2021")
                .build();

        entity = meetupService.save(entity);
        return entity.getId();
    }

    @GetMapping
    public Page<MeetupDTO> find(MeetupFilterDTO dto, Pageable pageRequest) {
        Page<Meetup> result = meetupService.find(dto, pageRequest);
        List<MeetupDTO> meetups = result
                .getContent()
                .stream()
                .map(entity -> {

                    PersonRegistration registration = entity.getRegistration();
                    PersonRegistrationDTO registrationDTO = modelMapper.map(registration, PersonRegistrationDTO.class);

                    MeetupDTO meetupDTO = modelMapper.map(entity, MeetupDTO.class);
                    meetupDTO.setRegistration(registrationDTO);
                    return meetupDTO;

                }).collect(Collectors.toList());
        return new PageImpl<MeetupDTO>(meetups, pageRequest, result.getTotalElements());
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public MeetupDTO get(@PathVariable Integer id) {

        return meetupService
                .getMeetupById(id)
                .map(meetup -> modelMapper.map(meetup, MeetupDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    public MeetupDTO update(@PathVariable Integer id, MeetupDTO meetupDTO) {
        return meetupService.getMeetupById(id).map(meetup -> {
            meetup.setRegistrationAttribute(meetupDTO.getRegistrationAttribute());
            meetup.setEvent(meetupDTO.getEvent());
            meetup = meetupService.update(meetup);
            return modelMapper.map(meetup, MeetupDTO.class);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByMeetupId(@PathVariable Integer id) {
        Meetup meetup = meetupService.getMeetupById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        meetupService.delete(meetup);
    }
}

