package com.Nickode.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NiCloudAuthRequest {
    @NotBlank
    @JsonProperty("login")
    private String username;
    @NotBlank
    private String password;
}
