package com.swp391.koibe.components;

import com.swp391.koibe.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

@Component
@RequiredArgsConstructor
public class LocalizationUtils {

    private final MessageSource messageSource;
    private final LocaleResolver customLocaleResolver;

    public String getLocalizedMessage(String messageKey, Object ...params){
        HttpServletRequest request = WebUtils.getCurrentRequest();
        Locale locale = customLocaleResolver.resolveLocale(request);
        return messageSource.getMessage(messageKey, params, locale);
    }

}
