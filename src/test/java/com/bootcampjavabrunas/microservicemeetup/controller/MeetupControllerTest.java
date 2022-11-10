package com.bootcampjavabrunas.microservicemeetup.controller;

import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.dto.MeetupDTO;
import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.MeetupController;
import com.bootcampjavabrunas.microservicemeetup.domain.service.exception.BusinessException;
import com.bootcampjavabrunas.microservicemeetup.domain.model.meetup.Meetup;
import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;
import com.bootcampjavabrunas.microservicemeetup.application.controller.meetup.MeetupService;
import com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.PersonRegistrationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {MeetupController.class})
@AutoConfigureMockMvc
public class MeetupControllerTest {

    static final String MEETUP_API = "/api/meetups";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PersonRegistrationService registrationService;

    @MockBean
    private MeetupService meetupService;

    @Test
    @DisplayName("Should register on a meetup")
    public void createMeetupTest() throws Exception {

        // quando enviar uma requisicao para esse registration precisa ser encontrado um valor que tem esse usuario
        MeetupDTO dto = MeetupDTO.builder().registrationAttribute("123").event("Womakerscode Dados").build();
        String json = new ObjectMapper().writeValueAsString(dto);

        PersonRegistration registration = createNewPersonRegistration();

        BDDMockito.given(registrationService.getRegistrationByRegistrationAttribute("123")).
                willReturn(Optional.of(registration));

        Meetup meetup = Meetup.builder().id(11).event("Womakerscode Dados").registration(registration).meetupDate("10/10/2021").build();

        BDDMockito.given(meetupService.save(Mockito.any(Meetup.class))).willReturn(meetup);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(MEETUP_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Aqui o que retorna é o id do registro no meetup
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().string("11"));
    }

    @Test
    @DisplayName("Should throw an exception when not have data enough for the test")
    public void createInvalidMeetupTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(new MeetupDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(MEETUP_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return error when try to register an a meetup nonexistent")
    public void invalidRegistrationCreateMeetupTest() throws Exception {

        MeetupDTO dto = MeetupDTO.builder().registrationAttribute("123").event("Womakerscode Dados").build();
        String json = new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(registrationService.getRegistrationByRegistrationAttribute("123")).
                willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(MEETUP_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return error when try to register a registration already register on a meetup")
    public void  meetupRegistrationErrorOnCreateMeetupAlreadyRegisteredTest() throws Exception {

        MeetupDTO dto = MeetupDTO.builder().registrationAttribute("123").event("Womakerscode Dados").build();
        String json = new ObjectMapper().writeValueAsString(dto);


        PersonRegistration registration = createNewPersonRegistration();
        BDDMockito.given(registrationService.getRegistrationByRegistrationAttribute("123"))
                .willReturn(Optional.of(registration));

        // procura na base se ja tem algum registration para esse meetup
        BDDMockito.given(meetupService.save(Mockito.any(Meetup.class))).willThrow(new BusinessException("Meetup already enrolled"));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(MEETUP_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should delete the meetup")
    public void deleteMeetupTest() throws Exception {

        BDDMockito.given(meetupService
                        .getMeetupById(anyInt()))
                .willReturn(Optional.of(Meetup.builder().id(11).build()));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(MEETUP_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should get meetup information")
    public void getMeetupInformationTest() throws Exception {

        Integer id = 101;

        Meetup meetup = Meetup.builder()
                .id(id)
                .event(createNewMeetup().getEvent())
                .registrationAttribute(createNewMeetup().getRegistrationAttribute())
                .build();

        BDDMockito.given(meetupService.getMeetupById(id)).willReturn(Optional.of(meetup));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(MEETUP_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        // assert
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(101))
                .andExpect(jsonPath("event").value(createNewMeetup().getEvent()))
                .andExpect(jsonPath("registrationAttribute").value(createNewMeetup().getRegistrationAttribute()));
    }

    @Test
    @DisplayName("Should update the meetup info")
    public void updateMeetupTest() throws Exception {

        Integer id = 101;
        String json = new ObjectMapper().writeValueAsString(createNewMeetup());

        Meetup updatingMeetup =
                Meetup.builder()
                        .id(id)
                        .event("Bootcamp Java")
                        .registrationAttribute("300")
                        .build();

        BDDMockito.given(meetupService.getMeetupById(anyInt()))
                .willReturn(Optional.of(updatingMeetup));

        // cenario valor atualizado
        Meetup updatedMeetup =
                Meetup.builder()
                        .id(id)
                        .event("Bootcamp")
                        .registrationAttribute("300")
                        .build();

        BDDMockito.given(meetupService
                        .update(updatingMeetup))
                .willReturn(updatedMeetup);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(MEETUP_API.concat("/" + 1))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("event").value(createNewMeetup().getEvent()))
                .andExpect(jsonPath("registrationAttribute").value("300"));
    }

    @Test
    @DisplayName("Should return 404 when try to update an meetup no existent")
    public void updateNoExistentMeetupTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(createNewMeetup());
        BDDMockito.given(meetupService.getMeetupById(anyInt()))
                .willReturn(Optional.empty());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(MEETUP_API.concat("/" + 1))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return NOT FOUND when the meetup doesn't exists")
    public void meetupNotFoundTest() throws Exception {

        BDDMockito.given(meetupService.getMeetupById(anyInt())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(MEETUP_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    private PersonRegistration createNewPersonRegistration() {
        return PersonRegistration.builder().id(11).name("Bruna Silva").registration("123").build();
    }

    private MeetupDTO createNewMeetup() {
        return MeetupDTO.builder().id(1).registrationAttribute("300").event("Bootcamp").build();
    }
}