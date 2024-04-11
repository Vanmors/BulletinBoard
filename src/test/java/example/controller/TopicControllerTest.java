package example.controller;

import org.example.DTO.NewTopicWithMessageDTO;
import org.example.Entity.Topic;
import org.example.controller.TopicController;
import org.example.repository.TopicRepository;
import org.example.service.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicControllerTest {

    @Mock
    private TopicService topicService;
    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicController topicController;

    @Test
    void getTopics_ReturnsPageOfTopics_WhenTopicsExist() {
        List<Topic> topicList = new ArrayList<>();
        topicList.add(new Topic());
        topicList.add(new Topic());
        Page<Topic> topicPage = new PageImpl<>(topicList);

        when(topicRepository.findAll((Pageable) any())).thenReturn(topicPage);

        ResponseEntity<Page<Topic>> responseEntity = topicController.getTopics(Pageable.unpaged());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().getTotalElements());
    }

    @Test
    void getTopics_ReturnsNotFound_WhenNoTopicsExist() {
        Page<Topic> emptyPage = Page.empty();

        when(topicRepository.findAll((Pageable) any())).thenReturn(emptyPage);

        ResponseEntity<Page<Topic>> responseEntity = topicController.getTopics(Pageable.unpaged());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void createTopicWithMessage_ReturnsCreatedTopic() {
        NewTopicWithMessageDTO newTopicWithMessageDTO = new NewTopicWithMessageDTO();
        Topic createdTopic = new Topic();

        when(topicService.createTopicWithMessage(any(NewTopicWithMessageDTO.class))).thenReturn(createdTopic);

        ResponseEntity<Topic> responseEntity = topicController.createTopicWithMessage(newTopicWithMessageDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void deleteTopic_ReturnsOkStatus() throws ChangeSetPersister.NotFoundException {
        HttpStatus status = topicController.deleteTopicWithMessage(1L);

        assertEquals(HttpStatus.OK, status);
        verify(topicService, times(1)).deleteTopicWithMessages(1L);
    }
}
