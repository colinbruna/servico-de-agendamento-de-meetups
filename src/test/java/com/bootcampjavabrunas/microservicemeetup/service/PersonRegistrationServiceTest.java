package com.bootcampjavabrunas.microservicemeetup.service;

import com.bootcampjavabrunas.microservicemeetup.application.controller.personRegistration.PersonRegistrationService;
import com.bootcampjavabrunas.microservicemeetup.domain.service.exception.BusinessException;
import com.bootcampjavabrunas.microservicemeetup.domain.model.personRegistration.PersonRegistration;
import com.bootcampjavabrunas.microservicemeetup.infraestructure.repository.personRegistration.PersonRegistrationRepository;
import com.bootcampjavabrunas.microservicemeetup.domain.service.impl.personRegistration.PersonRegistrationServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PersonRegistrationServiceTest {

    PersonRegistrationService registrationService;

    @MockBean
    PersonRegistrationRepository repository;

    @BeforeEach
    public void setUp() {
        this.registrationService = new PersonRegistrationServiceImpl(repository, meetupService);
    }

    @Test
    @DisplayName("Should save an registration")
    public void saveRegistrationTest() {

        // cenario
        PersonRegistration registration = createValidRegistration();

        // execucao
        Mockito.when(repository.existsByRegistration(Mockito.anyString())).thenReturn(false);
        Mockito.when(repository.save(registration)).thenReturn(createValidRegistration());

        PersonRegistration savedRegistration = registrationService.save(registration);

        // assert
        assertThat(savedRegistration.getId()).isEqualTo(101);
        assertThat(savedRegistration.getName()).isEqualTo("Bruna Silva");
        assertThat(savedRegistration.getDateOfRegistration()).isEqualTo("01/04/2022");
        assertThat(savedRegistration.getRegistration()).isEqualTo("001");
    }

    @Test
    @DisplayName("Should throw business error when try " +
            "to save a new registration with a registration duplicated")
    public void notSaveAsRegistrationDuplicatedTest() {

        PersonRegistration registration = createValidRegistration();
        Mockito.when(repository.existsByRegistration(Mockito.any())).thenReturn(true);

        Throwable exception = Assertions.catchThrowable( () -> registrationService.save(registration));
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Registration already created");

        Mockito.verify(repository, Mockito.never()).save(registration);
    }

    @Test
    @DisplayName("Should get an registration by Id")
    public void getRegistrationByIdTest() {

        // cenario
        Integer id = 11;
        PersonRegistration registration = createValidRegistration();
        registration.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(registration));

        // execucao
        Optional<PersonRegistration> foundRegistration = registrationService.getRegistrationById(id);

        // assert
        assertThat(foundRegistration.isPresent()).isTrue();
        assertThat(foundRegistration.get().getId()).isEqualTo(id);
        assertThat(foundRegistration.get().getName()).isEqualTo(registration.getName());
        assertThat(foundRegistration.get().getDateOfRegistration()).isEqualTo(registration.getDateOfRegistration());
        assertThat(foundRegistration.get().getRegistration()).isEqualTo(registration.getRegistration());
    }

    @Test
    @DisplayName("Should return empty when get an registration by id when doesn't exists")
    public void registrationNotFoundByIdTest() {

        Integer id = 11;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<PersonRegistration> registration = registrationService.getRegistrationById(id);

        assertThat(registration.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should delete an registration")
    public void deleteRegistrationTest() {

        PersonRegistration registration = PersonRegistration.builder().id(11).build();

        assertDoesNotThrow(() -> registrationService.delete(registration));

        Mockito.verify(repository, Mockito.times(1)).delete(registration);
    }

    @Test
    @DisplayName("Should update an registration")
    public void updateRegistrationTest(){

        // cenario
        Integer id =11;
        PersonRegistration updatingRegistration = PersonRegistration.builder().id(11).build();

        // execucao
        PersonRegistration updatedRegistration = createValidRegistration();
        updatedRegistration.setId(id);

        Mockito.when(repository.save(updatingRegistration)).thenReturn(updatedRegistration);
        PersonRegistration registration = registrationService.update(updatingRegistration);

        // assert
        assertThat(registration.getId()).isEqualTo(updatedRegistration.getId());
        assertThat(registration.getName()).isEqualTo(updatedRegistration.getName());
        assertThat(registration.getDateOfRegistration()).isEqualTo(updatedRegistration.getDateOfRegistration());
        assertThat(registration.getRegistration()).isEqualTo(updatedRegistration.getRegistration());
    }

    @Test
    @DisplayName("Should filter registrations must by properties")
    public void findRegistrationByPropertiesTest(){

        // cenario
        PersonRegistration registration = createValidRegistration();
        PageRequest pageRequest = PageRequest.of(0,10);

        List<PersonRegistration> listRegistrations = Arrays.asList(registration);
        Page<PersonRegistration> page = new PageImpl<PersonRegistration>(Arrays.asList(registration),
                PageRequest.of(0,10),1);

        // execucao
        Mockito.when(repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);

        Page<PersonRegistration> result = registrationService.find(registration, pageRequest);

        // assert
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(listRegistrations);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
    }

    @Test
    @DisplayName("Should get an registration model by registration attribute")
    public void getRegistrationByRegistrationAtrbTest() {

        String registrationAttribute = "1234";

        Mockito.when(repository.findByRegistration(registrationAttribute)).
                thenReturn(Optional.of(PersonRegistration.builder().id(11).registration(registrationAttribute).build()));

        Optional<PersonRegistration> registration = registrationService.getRegistrationByRegistrationAttribute(registrationAttribute);

        assertThat(registration.isPresent()).isTrue();
        assertThat(registration.get().getId()).isEqualTo(11);
        assertThat(registration.get().getRegistration()).isEqualTo(registrationAttribute);

        Mockito.verify(repository, Mockito.times(1)).findByRegistration(registrationAttribute);
    }

    private PersonRegistration createValidRegistration() {

        return PersonRegistration.builder()
                .id(101)
                .name("Bruna Silva")
                .dateOfRegistration("01/04/2022")
                .registration("001")
                .build();
    }
}