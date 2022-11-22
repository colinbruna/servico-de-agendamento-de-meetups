package com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class PersonRegistrationDTO {

    @NotBlank(message = "Nome para registro é obrigatório")
    private String name;
    @NotBlank(message = "Email é obrigatório")
    private String email;
    @NotBlank(message = "Data do registro é obrigatório")
    private String dateRegistration;
    @NotBlank(message = "idMeetup para inscrição no evento é obrigatório")
    private String idMeetup;
}