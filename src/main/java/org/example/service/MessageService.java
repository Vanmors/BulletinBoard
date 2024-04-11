package org.example.service;

import jakarta.transaction.Transactional;
import org.example.DTO.MessageDTO;
import org.example.Entity.Message;
import org.example.Entity.Topic;
import org.example.repository.MessageRepository;
import org.example.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    public Message createMessage(MessageDTO messageDTO, Long topicId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authorName = authentication.getName();

        Optional<Topic> optionalTopic = topicRepository.findById(topicId);

        if (optionalTopic.isEmpty()) {
            return null;
        }

        Topic topic = optionalTopic.get();

        Message message = new Message();
        message.setAuthorName(authorName);
        message.setText(messageDTO.getText());
        message.setCreatedAt(LocalDateTime.now());
        message.setTopic(topic);

        return messageRepository.save(message);
    }

    @Transactional
    public void deleteMessage(Long topicId, Long messageId) throws AccessDeniedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authorName = authentication.getName();

        // Проверяем существование топика
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isEmpty()) {
            return;
        }

        Topic topic = optionalTopic.get();

        Optional<Message> optionalMessage = topic.getMessages().stream()
                .filter(message -> message.getId().equals(messageId))
                .findFirst();

        if (optionalMessage.isEmpty()) {
            return;
        }

        Message message = optionalMessage.get();

        if (!message.getAuthorName().equals(authorName) && !getCurrentUserRoles().get(0).equals("ROLE_ADMIN")) {
            throw new AccessDeniedException("Access is denied");
        }

        topic.getMessages().remove(message);

        topicRepository.save(topic);

        messageRepository.delete(message);
    }

    public void editMessage(Long topicId, Long messageId, MessageDTO messageDTO) throws AccessDeniedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authorName = authentication.getName();

        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isEmpty()) {
            return;
        }

        Topic topic = optionalTopic.get();

        Optional<Message> optionalMessage = topic.getMessages().stream()
                .filter(message -> message.getId().equals(messageId))
                .findFirst();

        if (optionalMessage.isEmpty()) {
            return;
        }

        Message message = optionalMessage.get();


        if (!message.getAuthorName().equals(authorName) && !getCurrentUserRoles().get(0).equals("ROLE_ADMIN")) {
            throw new AccessDeniedException("Access is denied");
        }

        message.setText(messageDTO.getText());

        messageRepository.save(message);
    }


    public static List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        return roles;
    }

}
