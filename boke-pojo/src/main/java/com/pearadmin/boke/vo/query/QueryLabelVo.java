package com.pearadmin.boke.vo.query;

import lombok.Data;

@Data
public class QueryLabelVo extends PageVo{
    
    private String labelName;
    
    private Integer deleteState;
}
