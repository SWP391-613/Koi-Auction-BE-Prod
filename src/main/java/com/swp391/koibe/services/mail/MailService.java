package com.swp391.koibe.services.mail;

import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Auction;
import com.swp391.koibe.responses.AuctionResponse;
import com.swp391.koibe.responses.UserResponse;
import com.swp391.koibe.services.auction.AuctionService;
import com.swp391.koibe.services.auction.IAuctionService;
import com.swp391.koibe.services.auctionparticipant.IAuctionParticipantService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService implements IMailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final IAuctionParticipantService auctionParticipantService;
    private final IAuctionService auctionService;

    @Override
    @Async
    public void sendMail(String to, String subject, String templateName, Context context) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            String htmlContent = templateEngine.process(templateName, context);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
            log.info("Mail send successfully to {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send mail to {}: {}", to, e.getMessage());
            throw new MessagingException("Failed to send mail to " + to);
        }
    }



}