
package ru.otus.rest.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserDTO {

  private String email;

  private String firstName;

  private Long id;

  private String lastName;

  private String password;

  private String phone;

  private Long userStatus;

  private String username;


}
