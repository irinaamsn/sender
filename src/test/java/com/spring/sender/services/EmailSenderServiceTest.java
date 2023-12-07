package com.spring.sender.services;

import com.spring.sender.dto.EmailDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceTest {
    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailSenderService emailSenderService;

    @Test
    void sendMessageTest() {
        EmailDto emailDto = new EmailDto("receiver@example.com", "Test Subject", "Test Content");

        emailSenderService.sendMessage(emailDto);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(messageCaptor.capture());

        SimpleMailMessage capturedMessage = messageCaptor.getValue();
        assertEquals("Test Subject", capturedMessage.getSubject());
        assertEquals("Test Content", capturedMessage.getText());
        // assertEquals("senderName <senderEmail>", capturedMessage.getFrom());
        assertEquals("receiver@example.com", capturedMessage.getTo()[0]);
    }
}