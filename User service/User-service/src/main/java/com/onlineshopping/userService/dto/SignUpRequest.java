package com.onlineshopping.userService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank
    @Size
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String username;

    @NotBlank(message = "Please enter password")
    @Size(min = 6, message = "Please enter password of length greater than 6")
    @Size(max = 30, message = "Please enter password of length lesser than 30")
    private String password;

    private Set<String> role;

}
