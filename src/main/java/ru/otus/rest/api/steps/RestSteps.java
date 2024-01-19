package ru.otus.rest.api.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestSteps {

  public RequestSpecification setup(String URI) {
    return given().baseUri(URI).contentType(ContentType.JSON).log().all();
  }

  public ValidatableResponse doPostRequest(RequestSpecification specification, String path, Object body) {
    return specification
          .basePath(path)
          .body(body)
          .when()
          .post().then().log().all();
  }

  public ValidatableResponse doGetWithPathParam(RequestSpecification specification, String path, String pathParam) {
    return null;
  }
}
