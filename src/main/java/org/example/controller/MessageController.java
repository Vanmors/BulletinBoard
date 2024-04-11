package org.example.controller;

import org.example.DTO.MessageDTO;
import org.example.Entity.Message;
import org.example.repository.MessageRepository;
import org.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/topic")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageService messageService;

    @GetMapping("{topicId}/message")
    public ResponseEntity<Page<Message>> getMessage(@PathVariable Long topicId, Pageable pageable) {
        Page<Message> messagePage = messageRepository.findByTopicId(topicId, pageable);
        if (!messagePage.isEmpty()) {
            return new ResponseEntity<>(messagePage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{topicId}/message")
    public ResponseEntity<Message> createMessage(@PathVariable Long topicId, @RequestBody MessageDTO message) {
     return new ResponseEntity<>(messageService.createMessage(message, topicId), HttpStatus.CREATED);
    }

    @DeleteMapping("{topicId}/message/{messageId}")
    public HttpStatus deleteMessage(@PathVariable Long topicId, @PathVariable Long messageId) throws AccessDeniedException {
        messageService.deleteMessage(topicId, messageId);
        return HttpStatus.OK;
    }

    @PatchMapping("{topicId}/message/{messageId}")
    public HttpStatus editMessage(@PathVariable Long topicId,
                                  @PathVariable Long messageId,
                                  @RequestBody MessageDTO messageDTO) throws AccessDeniedException {

        messageService.editMessage(topicId, messageId, messageDTO);
        return HttpStatus.OK;
    }
}
