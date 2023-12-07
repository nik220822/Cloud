package com.Nickode.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NiCloudAuthRequest {
    @NotNull
    @JsonProperty("login")
    private  String username;
    @NotNull
    private  String password;
}
