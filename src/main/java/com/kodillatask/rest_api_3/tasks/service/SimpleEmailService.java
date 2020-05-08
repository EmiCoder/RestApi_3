package com.kodillatask.rest_api_3.tasks.service;

import com.kodillatask.rest_api_3.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;



@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail) {

        LOGGER.info("Starting email preparation...");
        try {
//            SimpleMailMessage mailMessage = createMailMessage(mail);
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("E-mail has been sent.");
        } catch (MailException e) {
            LOGGER.error("E-mail sending process failed", e.getMessage(), e);
        }
    }


    public MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject((mail.getSubject()));
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    public SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        if (mail.getToCc()!=null) { mailMessage.setCc(mail.getToCc()); } 
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()));
        return mailMessage;
    }
}
