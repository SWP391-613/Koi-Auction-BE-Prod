package com.swp391.koibe.configs;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "alert.manager.emails")
@Getter
@Setter
public class EmailConfig {
    @Value("${alert.manager.emails}")
    private String managerEmails;

    public List<String> getManagerEmailList() {
        return Arrays.asList(managerEmails.split(","));
    }

}
