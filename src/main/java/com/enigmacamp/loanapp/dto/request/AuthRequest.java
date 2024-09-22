package com.enigmacamp.loanapp.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {
    @Email
    @NotBlank(message = "Email cant be null")
    private String email;

    @NotBlank
    @Size(min=8, message = "Password minimum 8")
    private String password;
}
