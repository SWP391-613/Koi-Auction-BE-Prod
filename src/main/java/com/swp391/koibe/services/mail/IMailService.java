package com.swp391.koibe.services.mail;

import org.thymeleaf.context.Context;

public interface IMailService {
    void sendMail(String to, String subject, String templateName, Context context);
}
