package com.team.angular.interactiondesignapi.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Email {

    String[] to;

    String from;

    String subject;

    String template;

    boolean reply;

    @ApiModelProperty(notes = "For Email-content", name = "", required = true, value = "{\"content\" : \"Mail-Content\"}")
    Map<String, Object> properties;
}
