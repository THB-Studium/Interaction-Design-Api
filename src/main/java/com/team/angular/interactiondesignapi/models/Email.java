package com.team.angular.interactiondesignapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    private String[] to;
    private String subject;
    private String message;


    private String from;

    private String text;

    private String template;

    private boolean reply;

    private Map<String, Object> properties;

}
