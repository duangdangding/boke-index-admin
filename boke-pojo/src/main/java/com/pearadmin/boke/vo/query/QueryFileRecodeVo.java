package com.pearadmin.boke.vo.query;

import lombok.Data;

@Data
public class QueryFileRecodeVo extends PageVo {
    
    private Integer upType;
    private Integer upBelong;
    private String fileName;
    private String urlPath;
}
