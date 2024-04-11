package org.example.service;

import org.example.DTO.NewTopicWithMessageDTO;
import org.example.Entity.Message;
import org.example.Entity.Topic;
import org.example.repository.MessageRepository;
import org.example.repository.TopicRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;

    public TopicService(TopicRepository topicRepository, MessageRepository messageRepository) {
        this.topicRepository = topicRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Topic createTopicWithMessage(NewTopicWithMessageDTO newTopicWithMessageDTO) {
        Topic topic = new Topic();
        topic.setTitle(newTopicWithMessageDTO.getTopicTitle());
        Topic createdTopic = topicRepository.save(topic);

        Message message = new Message();
        message.setAuthorName(newTopicWithMessageDTO.getAuthorName());
        message.setText(newTopicWithMessageDTO.getText());
        message.setCreatedAt(LocalDateTime.now());
        message.setTopic(createdTopic);
        messageRepository.save(message);

        return createdTopic;
    }

    @Transactional
    public void deleteTopicWithMessages(Long topicId) throws ChangeSetPersister.NotFoundException {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();

            List<Message> messages = topic.getMessages();

            messageRepository.deleteAll(messages);

            topicRepository.delete(topic);
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }
}
