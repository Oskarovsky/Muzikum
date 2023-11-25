package com.oskarro.muzikum.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseEvent<T> {

    private String message;
    private EventStatus status;
    private T item;

    public enum EventStatus {
        IN_PROGRESS, COMPLETED, FAILED
    }
}
