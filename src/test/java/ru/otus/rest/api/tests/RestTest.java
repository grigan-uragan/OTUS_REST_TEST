package ru.otus.rest.api.tests;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ru.otus.rest.api.dto.UserDTO;
import ru.otus.rest.api.helper.PropertyReader;
import ru.otus.rest.api.steps.RestSteps;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestTest {
  private final PropertyReader propertyReader = ConfigFactory.create(PropertyReader.class);

  @Test
  @Order(1)
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
  void shouldFindUser() {
    RestSteps steps = new RestSteps();
    RequestSpecification specification = steps.setup(propertyReader.petSoreBaseURI());
    ValidatableResponse response = steps.doGetWithPathParam(specification, propertyReader.petStoreUserPath(), "/Johny");
    response.statusCode(HttpStatus.SC_OK);
  }
}
