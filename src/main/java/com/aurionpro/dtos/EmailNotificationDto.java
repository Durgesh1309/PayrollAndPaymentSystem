package com.aurionpro.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationDto {

    private Long notificationId;

    @Email
    private String recipientEmail;

    private String type;

    private String subject;

    private String content;

    private String status;

    private LocalDateTime sentAt;

    private LocalDateTime readAt;

    private String errorMessage;
}

