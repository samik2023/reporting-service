package com.inventory.management.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Event {

    private Long eventId;
    private String eventType;
    private String commandObjStr;
    private Long orderId;
    private LocalDateTime timeStamp;

}