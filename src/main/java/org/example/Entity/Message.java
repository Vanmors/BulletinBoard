package org.example.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authorName;
    private String text;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}