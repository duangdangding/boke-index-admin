package com.pearadmin.boke.vo.query;

import lombok.Data;

@Data
public class QuerySourceVo extends PageVo{
    
    private Integer sourceType;
    private Integer shoudong;
    private Integer active;
    private Integer parse;
    private Integer startId;
    private Integer endId;
    //   1手机端2PC端
    private Integer appType;
    private String startTime;
    private String endTime;
    
}
