package com.team.angular.interactiondesignapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    private String name;
    private String surname;
    private String password;
    @Email
    private String email;
    private String role;

    @PastOrPresent
    private LocalDateTime creationDate;
    @PastOrPresent
    private LocalDateTime updateDate;

    /*@Column(nullable=false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean enabled;*/

}
