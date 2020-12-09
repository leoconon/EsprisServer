package com.iotaplicada.espris;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {

    @EqualsAndHashCode.Include
    private String username;
    private int totalScore = 0;
    private int actualScore = 0;

    @Getter
    private boolean gaming = false;

    @JsonIgnore
    private LocalDateTime lastMessage;

    public Player(String username) {
        this.username = username;
    }

    public boolean getGaming() {
        return lastMessage.isAfter(LocalDateTime.now().minusSeconds(30));
    }

}
