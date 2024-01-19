package ru.otus.rest.api.tests;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import ru.otus.rest.api.dto.UserDTO;
import ru.otus.rest.api.helper.PropertyReader;
import ru.otus.rest.api.steps.RestSteps;

public class RestTest {
  private final PropertyReader propertyReader = ConfigFactory.create(PropertyReader.class);

  @Test
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
  void shouldFindUser() {
    RestSteps steps = new RestSteps();
    RequestSpecification specification = steps.setup(propertyReader.petSoreBaseURI());

  }
}
