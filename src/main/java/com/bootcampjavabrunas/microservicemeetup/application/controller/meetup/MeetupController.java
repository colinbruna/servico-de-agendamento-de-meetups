package com.bootcampjavabrunas.microservicemeetup.application.controller.meetup;

import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto.MeetupDTO;
import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.PersonRegistrationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/meetups")
@RequiredArgsConstructor
public class MeetupController {

    private final MeetupService meetupService;

    private final PersonRegistrationService registrationService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<MeetupDTO> create(@RequestBody MeetupDTO meetupDTO) {
        Meetup meetup = modelMapper.map(meetupDTO, Meetup.class);
        Meetup meetupSalvo = meetupService.save(meetup);
        return new ResponseEntity<>(modelMapper.map(meetupSalvo, MeetupDTO.class), HttpStatus.CREATED);
    }

//    @GetMapping
//    public Page<MeetupDTO> find(MeetupFilterDTO dto, Pageable pageRequest) {
//        Page<Meetup> result = meetupService.find(dto, pageRequest);
//        List<MeetupDTO> meetups = result
//                .getContent()
//                .stream()
//                .map(entity -> {
//
//                    PersonRegistration registration = entity.getRegistration();
//                    PersonRegistrationDTO registrationDTO = modelMapper.map(registration, PersonRegistrationDTO.class);
//
//                    MeetupDTO meetupDTO = modelMapper.map(entity, MeetupDTO.class);
//                    //meetupDTO.setIdPerson(registrationDTO);
//                    return meetupDTO;
//
//                }).collect(Collectors.toList());
//        return new PageImpl<MeetupDTO>(meetups, pageRequest, result.getTotalElements());
//    }

//    @GetMapping("{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public MeetupDTO get(@PathVariable Integer id) {
//
//        return meetupService
//                .getMeetupById(id)
//                .map(meetup -> modelMapper.map(meetup, MeetupDTO.class))
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }

//    @PutMapping("{id}")
//    public MeetupDTO update(@PathVariable Integer id, @RequestBody @Valid MeetupDTO meetupDTO) {
//        return meetupService.getMeetupById(id).map(meetup -> {
//            meetup.setRegistrationAttribute(meetupDTO.getRegistrationAttribute());
//            meetup.setEvent(meetupDTO.getEvent());
//            meetup = meetupService.update(meetup);
//            return modelMapper.map(meetup, MeetupDTO.class);
//        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }

//    @DeleteMapping("{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteByMeetupId(@PathVariable ObjectId id) {
//        Meetup meetup = meetupService.getMeetupById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        meetupService.delete(meetup);
//    }
}