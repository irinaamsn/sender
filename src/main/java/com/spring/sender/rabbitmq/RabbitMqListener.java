package com.spring.sender.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.sender.dto.EmailDto;
import com.spring.sender.exceptions.EmailSendException;
import com.spring.sender.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@EnableRabbit
public class RabbitMqListener /*implements MessageListener*/ {
    private final EmailSenderService emailSenderService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "SEND_SUMMARY_EMAIL")
    public void listen(Message message) throws IOException {
        EmailDto emailDto = objectMapper.readValue(message.getBody(), EmailDto.class);
        emailSenderService.sendMessage(emailDto);
    }

}
