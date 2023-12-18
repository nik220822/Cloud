package com.Nickode.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NiCloudAuthRequest {
    @NotBlank
    @JsonProperty("login")
    private String username;
    @NotNull
    private String password;
}
