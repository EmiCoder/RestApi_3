package com.kodillatask.rest_api_3.tasks.trello.client;

import com.kodillatask.rest_api_3.tasks.domain.TrelloBoardDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String trelloUserName;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = build_url();

        try {
            Optional<TrelloBoardDto[]> boardsResponse = Optional.ofNullable(restTemplate.getForObject(url,
                    TrelloBoardDto[].class));
            return Arrays.asList(boardsResponse.orElse(new TrelloBoardDto[0]));
        } catch(RestClientException e){
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private URI build_url() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUserName + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id").build().encode().toUri();
    }
}
