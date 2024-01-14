package ua.hillel.jaxrs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserRequest(UUID id, String username, String email) {
}
