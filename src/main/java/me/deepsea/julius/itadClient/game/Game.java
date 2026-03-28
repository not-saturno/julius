package me.deepsea.julius.itadClient.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Game(
        UUID id,
        String title,
        Boolean mature,
        Asset assets
) {}
