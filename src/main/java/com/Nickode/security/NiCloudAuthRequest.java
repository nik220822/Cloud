package com.Nickode.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class NiCloudAuthRequest {
    @NotNull
    @NotBlank
    @JsonProperty("login")
    private  String username;
    @NotNull
    @NotBlank
    private  String password;
}
