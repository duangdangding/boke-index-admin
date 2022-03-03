package com.pearadmin.boke.service;

public interface MailService {

    boolean sendMailFreemark(String to,String object,String templete,String key);
 
    boolean sendMailQuestion(String to,String content,String ip);
    
}
