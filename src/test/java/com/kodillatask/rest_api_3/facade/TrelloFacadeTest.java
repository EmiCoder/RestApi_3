package com.kodillatask.rest_api_3.facade;

import com.kodillatask.rest_api_3.tasks.domain.*;
import com.kodillatask.rest_api_3.tasks.mapper.TrelloMapper;
import com.kodillatask.rest_api_3.tasks.service.TrelloService;
import com.kodillatask.rest_api_3.tasks.trello.client.TrelloClient;
import com.kodillatask.rest_api_3.tasks.trello.facade.TrelloFacade;
import com.kodillatask.rest_api_3.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;
    @Mock
    private TrelloService trelloService;
    @Mock
    private TrelloValidator trelloValidator;
    @Mock
    private TrelloMapper trelloMapper;

    @Test
    public void shouldFetchEmptyList() {
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1","test_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1","test", trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1","test_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1","test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(new ArrayList<>());
//        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList<>());

        List<TrelloBoardDto> trelloBoardDto = trelloFacade.fetchTrelloBoards();
        assertNotNull(trelloBoardDto);
        assertEquals(0, trelloBoardDto.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1","my_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1","my_task", trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1","my_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1","my_task", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
//        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        List<TrelloBoardDto> trelloBoardDto = trelloFacade.fetchTrelloBoards();
        assertNotNull(trelloBoardDto);
        assertEquals(1, trelloBoardDto.size());

        trelloBoardDto.forEach(dto -> {
            assertEquals("1",dto.getId());
            assertEquals("my_task", dto.getName());

            dto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void shouldCreateCard() {
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_name",
                                                        "test_description",
                                                        "test_pos",
                                                        "test_listId");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                                                        "1",
                                                        "test_name",
                                                        "test",
                                                        null);

        when(trelloService.createTrelloCard(any())).thenReturn(createdTrelloCardDto);

        CreatedTrelloCardDto createdTrelloCardDtos = trelloFacade.createCard(trelloCardDto);

        assertEquals("test_name", createdTrelloCardDtos.getName());
    }
}
