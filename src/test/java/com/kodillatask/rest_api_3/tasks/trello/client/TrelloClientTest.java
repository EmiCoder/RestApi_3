package com.kodillatask.rest_api_3.tasks.trello.client;

import com.kodillatask.rest_api_3.tasks.domain.BadgesDto;
import com.kodillatask.rest_api_3.tasks.domain.CreatedTrelloCardDto;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20description&pos=top&idList=test_id");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com",
                new BadgesDto()
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);

        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        Assert.assertEquals("1", newCard.getId());
        Assert.assertEquals("Test task", newCard.getName());
        Assert.assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        URI uri = new URI("http://test.com" +
                "/members" +
                "/emicello" +
                "/boards" +
                "?key=test" +
                "&token=test" +
                "&fields=name,id" +
                "&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        assertEquals(0, fetchedTrelloBoards.size());
    }
}