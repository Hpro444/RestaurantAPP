package com.example.notification.NotificationService.utils;

import org.junit.jupiter.api.Test;
import javax.mail.MessagingException;

import java.io.IOException;
import java.util.Properties;

public class EmailSenderIntegrationTest {

    @Test
    public void testSendEmail() throws MessagingException, IOException {

        EmailSender emailSender = new EmailSender();
        emailSender.sendEmail("hpro444@gmail.com", "subject", "body");

    }
}