package com.example.springhorarios.Config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

public abstract class GrantedAuthorityMixin {
    @JsonCreator
    public GrantedAuthorityMixin(@JsonProperty("authority") String authority) {
    }
}
