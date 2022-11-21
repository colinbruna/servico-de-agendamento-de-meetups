package com.bootcampjavabrunas.microservicemeetup.application.controller.meetup;

import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto.MeetupDTO;
import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto.converter.MeetupConverter;
import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/meetups")
@RequiredArgsConstructor
public class MeetupController {

    private final MeetupService service;
    private final MeetupConverter converter;

    @PostMapping
    public ResponseEntity<MeetupDTO> create(@RequestBody @Valid MeetupDTO meetupDTO) {
        Meetup meetup = converter.convertToMeetup(meetupDTO);
        return new ResponseEntity<>(converter.convertToDto(service.save(meetup)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetupDTO> update(@PathVariable String id,
                                            @RequestBody @Valid MeetupDTO meetupDTO) {
        Meetup meetup = converter.convertToMeetup(meetupDTO);
        Meetup meetupUpdated = service.update(id, meetup);

        return meetupUpdated == null?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(converter.convertToDto(meetupUpdated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetupDTO> find(@PathVariable String id) {
        Meetup meetup = service.find(id);

        return meetup == null?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(converter.convertToDto(meetup));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MeetupDTO> delete(@PathVariable String id) {
        if (Objects.isNull(service.find(id))) {
            return ResponseEntity.notFound().build();
        }

        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<MeetupDTO>> findAll() {
        List<Meetup> meetups = service.findAll();
        return ResponseEntity.ok(converter.convertToDtoList(meetups));
    }
}