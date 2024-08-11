package org.example.cinemaflix.service;

import jakarta.mail.MessagingException;
import org.example.cinemaflix.dao.entity.Customer;
import org.example.cinemaflix.model.dto.request.MailBody;

public interface EmailService {
    void sendEmailAttachment(Customer customer , String fileName) throws MessagingException;

    void sendSimpleMessage(MailBody mailBody);
}
