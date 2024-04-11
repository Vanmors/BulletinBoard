package org.example.DTO;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private String authorName;
    private String text;
    private LocalDateTime createdAt;
}
