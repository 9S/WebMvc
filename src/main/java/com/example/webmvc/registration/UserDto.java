package com.example.webmvc.registration;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserDto {
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    @Email(regexp = ".+@.+\\..+")
    private String email;

    @NotNull
    @NotEmpty
    @PasswordStrengthCheck
    private String password;
    private String password_2;
}
