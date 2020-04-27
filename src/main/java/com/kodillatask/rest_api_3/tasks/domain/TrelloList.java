package com.kodillatask.rest_api_3.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrelloList {

    private String id;
    private String name;
    private boolean isClosed;
}
