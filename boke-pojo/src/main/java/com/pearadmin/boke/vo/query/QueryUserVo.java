package com.pearadmin.boke.vo.query;

import lombok.Data;

@Data
public class QueryUserVo extends PageVo{
    
    private String username;
    
    private Integer deleteState;
    
    private Integer phoneBind;
    
    private Integer emailBind;
    
    private String phone;
    
    private String userEmail;
    
}
