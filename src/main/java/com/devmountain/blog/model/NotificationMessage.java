package com.devmountain.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder(toBuilder = true)
public class NotificationMessage {
    private NotificationMessageType type;
    private String text;
}
