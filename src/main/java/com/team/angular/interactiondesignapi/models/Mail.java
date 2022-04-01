package com.team.angular.interactiondesignapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    private String recipient;
    private String subject;
    private String message;
}
