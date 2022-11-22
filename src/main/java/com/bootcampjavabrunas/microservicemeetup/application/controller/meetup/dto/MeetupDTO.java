package com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class MeetupDTO {

    @NotBlank(message = "Nome do evento é obrigatório")
    private String eventName;
    @NotBlank(message = "Data do evento é obrigatório")
    private String eventDate;
    @NotBlank(message = "Local do evento é obrigatório")
    private String place;
}