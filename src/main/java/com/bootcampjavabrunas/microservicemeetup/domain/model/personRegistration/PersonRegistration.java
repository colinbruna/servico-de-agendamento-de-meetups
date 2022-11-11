package com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "personRegistration")
public class PersonRegistration {

    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String dateRegistration;
}