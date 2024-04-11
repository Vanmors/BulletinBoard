package org.example.DTO;

import lombok.Data;

@Data
public class NewTopicWithMessageDTO {
    private String topicTitle;
    private String authorName;
    private String text;
}
