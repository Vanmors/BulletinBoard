package org.example.controller;

import org.example.DTO.NewTopicWithMessageDTO;
import org.example.Entity.Topic;
import org.example.repository.TopicRepository;
import org.example.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<Page<Topic>> getTopics(Pageable pageable) {
        Page<Topic> topicPage = topicRepository.findAll(pageable);
        if (!topicPage.isEmpty()) {
            return new ResponseEntity<>(topicPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Topic> createTopicWithMessage(@RequestBody NewTopicWithMessageDTO newTopicWithMessageDTO) {
        Topic createdTopic = topicService.createTopicWithMessage(newTopicWithMessageDTO);
        return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
    }

    @DeleteMapping("{topicId}")
    public HttpStatus createTopicWithMessage(@PathVariable Long topicId) throws ChangeSetPersister.NotFoundException {
        topicService.deleteTopicWithMessages(topicId);
        return HttpStatus.OK;
    }
}
