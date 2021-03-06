package com.kodillatask.rest_api_3.tasks.controller;

import com.kodillatask.rest_api_3.tasks.domain.CreatedTrelloCardDto;
import com.kodillatask.rest_api_3.tasks.domain.TrelloBoardDto;
import com.kodillatask.rest_api_3.tasks.domain.TrelloCardDto;
import com.kodillatask.rest_api_3.tasks.service.TrelloService;
import com.kodillatask.rest_api_3.tasks.trello.facade.TrelloFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    @Autowired
    private TrelloFacade trelloFacade;

    private final String NAME = "Kodilla";

    @RequestMapping(method = RequestMethod.GET, value = "/boards")
    public List<TrelloBoardDto> getTrelloBoards() {
        List<TrelloBoardDto> trelloBoards = trelloFacade.fetchTrelloBoards();
//        trelloBoards.stream()
//                        .filter(distinctByKeys(TrelloBoardDto::getId, TrelloBoardDto::getName))
//                        .filter(TrelloBoardDto -> TrelloBoardDto.getName().equals(NAME))
//                        .forEach(System.out::println);
        return trelloFacade.fetchTrelloBoards();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cards")
    public CreatedTrelloCardDto createdTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloFacade.createCard(trelloCardDto);
    }





    private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors)
    {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t ->
        {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }
}
