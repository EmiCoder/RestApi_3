package com.kodillatask.rest_api_3.tasks.trello.client;

import com.kodillatask.rest_api_3.tasks.domain.CreatedTrelloCard;
import com.kodillatask.rest_api_3.tasks.domain.TrelloBoardDto;
import com.kodillatask.rest_api_3.tasks.domain.TrelloCardDto;
import com.kodillatask.rest_api_3.tasks.trello.config.TrelloConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {

        TrelloBoardDto[] t = new TrelloBoardDto[1];
        t[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/emicello/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(t);

        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        Assert.assertEquals(1, fetchedTrelloBoards.size());
        Assert.assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        Assert.assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        Assert.assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
}

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                                            "Test task",
                                            "Test description",
                                            "top",
                                            "test_id"
        );



        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard(
                "1",
                "Test task",
                "http://test.com"
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCard.class)).thenReturn(createdTrelloCard);

        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);

        Assert.assertEquals("1", newCard.getId());
        Assert.assertEquals("Test task", newCard.getName());
        Assert.assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() {
        URI url = UriComponentsBuilder.fromHttpUrl("https://api.trello.com/1/members/emicello/boards")
                .queryParam("key", "a1ddfd8c40176c41b918b64bf08ca45e")
                .queryParam("token", "fb5bfdf5684644d70b826d156c32b83821e8a3b19bc081700fbdf68a1d1a2cb9")
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
        List<TrelloBoardDto> emptyList = new ArrayList<>();
        List<TrelloBoardDto> theList = trelloClient.getTrelloBoards();
        Assert.assertTrue(theList.isEmpty());
        Assert.assertEquals(emptyList.size(), theList.size());
    }
}