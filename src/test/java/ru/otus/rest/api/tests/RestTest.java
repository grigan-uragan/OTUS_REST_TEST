package ru.otus.rest.api.tests;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import ru.otus.rest.api.dto.UserDTO;
import ru.otus.rest.api.helper.PropertyReader;
import ru.otus.rest.api.steps.RestSteps;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
  Тесткейсы лежат по пути src/test/resources/testcase
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestTest {
  private final PropertyReader propertyReader = ConfigFactory.create(PropertyReader.class);

  @Test
  @Order(1)
  @DisplayName("Успешное создание пользователя")
  void shouldCreateUser() {
    RestSteps steps = new RestSteps();
    UserDTO userDTO = UserDTO.builder()
          .id(1L)
          .firstName("John")
          .lastName("Doe")
          .username("Johny")
          .phone("8(800)555-35-35")
          .build();
    RequestSpecification specification = steps.setup(propertyReader.petSoreBaseURI());
    ValidatableResponse validatableResponse = steps.doPostRequest(specification, propertyReader.petStoreUserPath(), userDTO);
    validatableResponse.statusCode(HttpStatus.SC_OK);
  }

  @Test
  @Order(2)
  @DisplayName("Успешный поиск только созданного пользователя")
  void shouldFindUser() {
    RestSteps steps = new RestSteps();
    RequestSpecification specification = steps.setup(propertyReader.petSoreBaseURI());
    ValidatableResponse response = steps.doGetWithPathParam(specification, propertyReader.petStoreUserPath(), "/Johny");
    response.statusCode(HttpStatus.SC_OK);
    UserDTO userDTO = response.extract().body().as(UserDTO.class);
    assertThat(userDTO, allOf(
          hasProperty("firstName", equalTo("John")),
          hasProperty("lastName", equalTo("Doe")),
          hasProperty("username", equalTo("Johny")),
          hasProperty("phone", equalTo("8(800)555-35-35"))
    ));
  }

  @Test
  @Order(3)
  @DisplayName("Создание 2 Юзеров")
  void shouldCreate2Users() {
    RestSteps steps = new RestSteps();
    UserDTO firstUser = UserDTO.builder()
          .username("Tommy")
          .firstName("Tom")
          .lastName("Kukuruz")
          .build();
    UserDTO secondUser = UserDTO.builder()
          .username("Bobby")
          .firstName("Bob")
          .lastName("Dilan")
          .build();
    RequestSpecification specification = steps.setup(propertyReader.petSoreBaseURI());
    ValidatableResponse validatableResponse = steps.doPostRequest(specification,
          propertyReader.createUserWithListPath(),
          List.of(firstUser, secondUser));
    validatableResponse.statusCode(HttpStatus.SC_OK);
  }

  @Test
  @Order(4)
  @DisplayName("Успешный поиск только созданых 2 пользователя")
  void shouldFind2NewUser() {
    RestSteps steps = new RestSteps();
    RequestSpecification specification = steps.setup(propertyReader.petSoreBaseURI());
    ValidatableResponse responseTommy = steps.doGetWithPathParam(specification, propertyReader.petStoreUserPath(), "/Tommy");
    responseTommy.statusCode(HttpStatus.SC_OK);
    UserDTO tommy = responseTommy.extract().body().as(UserDTO.class);
    assertThat(tommy, allOf(
          hasProperty("firstName", equalTo("Tom")),
          hasProperty("lastName", equalTo("Kukuruz")),
          hasProperty("username", equalTo("Tommy"))
    ));
    ValidatableResponse responseBobby = steps.doGetWithPathParam(specification, propertyReader.petStoreUserPath(), "/Bobby");
    responseBobby.statusCode(HttpStatus.SC_OK);
    UserDTO bobby = responseBobby.extract().body().as(UserDTO.class);
    assertThat(bobby, allOf(
          hasProperty("firstName", equalTo("Bob")),
          hasProperty("lastName", equalTo("Dilan")),
          hasProperty("username", equalTo("Bobby"))
    ));
  }
}
