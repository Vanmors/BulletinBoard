package org.example.repository;

import org.example.Entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByTopicId(Long topicId, Pageable pageable);
}
