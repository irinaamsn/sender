package com.spring.sender.services;

import com.spring.sender.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;
    @Value("${sender.name}")
    private String senderName;

    public void sendMessage(EmailDto emailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderName + " <" + senderEmail + ">");
        message.setTo(emailDto.getReceiverEmail());
        message.setSubject(emailDto.getHeader());
        message.setText(emailDto.getBody());
        emailSender.send(message);
    }
}
