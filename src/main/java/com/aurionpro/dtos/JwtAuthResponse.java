package com.aurionpro.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtAuthResponse {
    private final String token;
}
