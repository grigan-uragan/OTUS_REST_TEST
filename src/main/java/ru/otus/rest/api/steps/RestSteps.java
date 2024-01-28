package ru.otus.rest.api.steps;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class RestSteps {

  public RequestSpecification setup(String uri) {
    return given().baseUri(uri).contentType(ContentType.JSON).log().all();
  }

  public ValidatableResponse doPostRequest(RequestSpecification specification, String path, Object body) {
    return specification
          .basePath(path)
          .body(body)
          .when()
          .post().then().log().all();
  }

  public ValidatableResponse doGetWithPathParam(RequestSpecification specification, String path, String pathParam) {
    return specification
          .basePath(path)
          .when()
          .get(pathParam)
          .then()
          .log()
          .all();
  }
}
