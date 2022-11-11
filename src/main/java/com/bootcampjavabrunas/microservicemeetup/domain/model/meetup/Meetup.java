package com.bootcampjavabrunas.microservicemeetup.domain.model.meetup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "meetup")
public class Meetup {

    @Id
    private ObjectId id;

    private String event;

    private String meetupDate;
}