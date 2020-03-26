package com.kodillatask.rest_api_3.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TrelloCardDto {

    private String name;
    private String description;
    private String pos;
    private String listId;
}
