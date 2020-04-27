package com.kodillatask.rest_api_3.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TrelloBoard {

    private String id;
    private String name;
    private List<TrelloList> lists;

}
