package com.team.angular.interactiondesignapi.models;

import java.util.Map;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Email {

    String [] to;

    String from;

    String subject;

    String template;
    
    boolean reply;

    // set properties for the templates (messages and another elements)
    Map<String, Object> properties;
}