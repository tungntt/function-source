package gcfv2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gcfv2.dto.CardRequest;
import gcfv2.dto.GoogleChatMessage;
import gcfv2.dto.SingleMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class GoogleChatService {

    private static final String URL = "https://chat.googleapis.com/v1/spaces/AAAAX0vK0pU/messages?key=AIzaSyDdI0hCZtE6vySjMm-WEfRq3CPzqKqqsHI&token=RBYhhYYujqNSGcUB_m5hmTyyTk85jH4oN3WiP3RtyK4";
    private final ObjectMapper objectMapper;

    private final WebClient webClient;

    public void sendMessage(GoogleChatMessage value) throws IOException, InterruptedException {
        String payload = objectMapper.writeValueAsString(value);
        webClient.post()
                .uri(URL)
                .header("accept", "application/json; charset=UTF-8")
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(responseBody -> {
                    log.info("Response {}", responseBody);
                });
    }

    public Mono<String> sendMessage(SingleMessage message) throws IOException {
        String payload = objectMapper.writeValueAsString(message);
        return webClient.post()
                .uri(URL)
                .header("accept", "application/json; charset=UTF-8")
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .bodyToMono(String.class);
    }

    public void sendMessage(CardRequest cardRequest) throws JsonProcessingException {
        String payload = objectMapper.writeValueAsString(cardRequest);
        webClient.post()
                //.uri("https://chat.googleapis.com/v1/spaces/Developers/messages")
                .uri(URL)
                .header("accept", "application/json; charset=UTF-8")
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
