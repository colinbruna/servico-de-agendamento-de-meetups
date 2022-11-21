package com.bootcampjavabrunas.microservicemeetup.domain.service.impl.meetup;

import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.MeetupService;
import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import com.bootcampjavabrunas.microservicemeetup.infraestructure.repository.meetup.MeetupRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetupServiceImpl implements MeetupService {

    private MeetupRepository repository;

    public MeetupServiceImpl(MeetupRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meetup save(Meetup meetup) { return repository.save(meetup); }

    @Override
    public Meetup update(String id, Meetup meetup) {
        Optional<Meetup> optionalMeetup = repository.findById(new ObjectId(id));

        if (optionalMeetup.isPresent()) {
            meetup.setId(optionalMeetup.get().getId());
            return repository.save(meetup);
        }

        return null;
    }

    @Override
    public Meetup find(String id) {
        Optional<Meetup> optionalMeetup = repository.findById(new ObjectId(id));
        return optionalMeetup.orElse(null);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(new ObjectId(id));
    }

    @Override
    public List<Meetup> findAll() {
        return repository.findAll();
    }
}
