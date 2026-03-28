package me.deepsea.julius.itadClient.prices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Deal(
        Shop shop,
        Price price,
        String url
) {}

