package me.deepsea.julius.itadClient;

import me.deepsea.julius.itadClient.game.Game;
import me.deepsea.julius.itadClient.prices.DealResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;


@Service
public class DealClient {
    private final String apiKey;
    private final WebClient webClient;


    public DealClient(WebClient.Builder webClientBuilder, @Value("${itad.api-key}") String apiKey) {
        this.apiKey = apiKey;
        this.webClient = webClientBuilder
                .baseUrl("https://api.isthereanydeal.com")
                .defaultHeader("User-Agent", "JuliusBot/1.0")
                .build();
    }


    public Flux<Game> searchGame(String title) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/games/search/v1")
                        .queryParam("key", apiKey)
                        .queryParam("title", title)
                        .queryParam("results", 5)
                        .build())
                .retrieve()
                .bodyToFlux(Game.class);
    }


    public Flux<DealResponse> searchDeals(UUID gameId) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("games/prices/v3")
                        .queryParam("key", apiKey)
                        .queryParam("country", "BR")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(gameId))
                .retrieve()
                .bodyToFlux(DealResponse.class);
    }
}
