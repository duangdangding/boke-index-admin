package com.pearadmin.boke.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Data
@Configuration
public class MailUtil {
    
    @Value("${question.host}")
    private String host;

    @Value("${question.port}")
    private int port;

    @Value("${question.username}")
    private String username;

    @Value("${question.password}")
    private String password;

    @Value("${question.protocol}")
    private String protocol;

    @Value("${question.pretiesAuth}")
    private String pretiesAuth;

    @Value("${question.pretiesTimeout}")
    private String pretiesTimeout;

    @Value("${question.pretiesSsl}")
    private String pretiesSsl;

    public JavaMailSenderImpl createMailSender(){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setDefaultEncoding("Utf-8");
        sender.setProtocol(protocol);
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout",pretiesTimeout);
        p.setProperty("mail.smtp.auth",pretiesAuth);
        p.setProperty("mail.smtp.ssl.enabled",pretiesSsl);
        sender.setJavaMailProperties(p);
        return sender;
    }
}

