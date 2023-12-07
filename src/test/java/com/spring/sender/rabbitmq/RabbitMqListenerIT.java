package com.spring.sender.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.sender.dto.EmailDto;
import com.spring.sender.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class RabbitMqListenerIT {
    @InjectMocks
    private RabbitMqListener rabbitMqListener;
    @Mock
    private EmailSenderService emailSenderService;
    @Mock
    private ObjectMapper mapper;
    private static final ObjectMapper objectMapper=new ObjectMapper();

    @Container
    private static final RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.8")
            .withExposedPorts(5672);

    @DynamicPropertySource
    static void configureRabbitMQ(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getContainerIpAddress);
        registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
    }

    @Test
    void listenTest() throws IOException {
        MessageProperties props = new MessageProperties();
        EmailDto emailDto = new EmailDto("test@example.com", "Test Email", "Hello, World!");
        byte[] body = objectMapper.writeValueAsBytes(emailDto);
        Message message = MessageBuilder.withBody(body).andProperties(props).build();

        when(mapper.readValue(message.getBody(), EmailDto.class)).thenReturn(emailDto);

        rabbitMqListener.listen(message);

        verify(mapper,times(1)).readValue(message.getBody(), EmailDto.class);
        verify(emailSenderService, times(1)).sendMessage(emailDto);
    }
}
