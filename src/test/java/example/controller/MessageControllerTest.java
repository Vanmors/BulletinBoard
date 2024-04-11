package example.controller;
import org.example.DTO.MessageDTO;
import org.example.Entity.Message;
import org.example.controller.MessageController;
import org.example.repository.MessageRepository;
import org.example.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMessage_ReturnsPageOfMessages_WhenMessagesExist() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message());
        messageList.add(new Message());
        Page<Message> messagePage = new PageImpl<>(messageList);

        when(messageRepository.findByTopicId(anyLong(), any(Pageable.class))).thenReturn(messagePage);

        ResponseEntity<Page<Message>> responseEntity = messageController.getMessage(1L, Pageable.unpaged());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().getTotalElements());
    }

    @Test
    void getMessage_ReturnsNotFound_WhenNoMessagesExist() {
        Page<Message> emptyPage = Page.empty();

        when(messageRepository.findByTopicId(anyLong(), any(Pageable.class))).thenReturn(emptyPage);

        ResponseEntity<Page<Message>> responseEntity = messageController.getMessage(1L, Pageable.unpaged());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void createMessage_ReturnsCreatedMessage() {
        MessageDTO messageDTO = new MessageDTO();
        Message createdMessage = new Message();

        when(messageService.createMessage(any(MessageDTO.class), anyLong())).thenReturn(createdMessage);

        ResponseEntity<Message> responseEntity = messageController.createMessage(1L, messageDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void deleteMessage_ReturnsOkStatus() throws AccessDeniedException {
        HttpStatus status = messageController.deleteMessage(1L, 1L);

        assertEquals(HttpStatus.OK, status);
        verify(messageService, times(1)).deleteMessage(1L, 1L);
    }

    @Test
    void editMessage_ReturnsOkStatus() throws AccessDeniedException {
        MessageDTO messageDTO = new MessageDTO();

        HttpStatus status = messageController.editMessage(1L, 1L, messageDTO);

        assertEquals(HttpStatus.OK, status);
        verify(messageService, times(1)).editMessage(1L, 1L, messageDTO);
    }
}
