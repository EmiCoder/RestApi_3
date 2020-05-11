package com.kodillatask.rest_api_3.tasks.service;
import com.kodillatask.rest_api_3.tasks.config.AdminConfig;
import com.kodillatask.rest_api_3.tasks.domain.CreatedTrelloCardDto;
import com.kodillatask.rest_api_3.tasks.domain.Mail;
import com.kodillatask.rest_api_3.tasks.domain.TrelloBoardDto;
import com.kodillatask.rest_api_3.tasks.domain.TrelloCardDto;
import com.kodillatask.rest_api_3.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TrelloService {

    private static final String SUBJECT = "Tasks: New Trello card";
    @Autowired
    private TrelloClient trelloClient;
    @Autowired
    private SimpleEmailService emailService;
    @Autowired
    private AdminConfig adminConfig;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCardDto createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        ofNullable(newCard).ifPresent(card -> emailService.send(new Mail(
                                                adminConfig.getAdminMail(),
                                                SUBJECT,
                                                "NewCard: " + card.getName() + " has been created on your Trello account",
                                                "some.mail@some.mail")));


        return newCard;

        //fhgdgfsfda
    }
}
