package com.pearadmin.boke.vo.query;

import lombok.Data;

@Data
public class QueryTypeVo extends PageVo{
    
    private String typeName;
    
    private Integer deleteState;
}
