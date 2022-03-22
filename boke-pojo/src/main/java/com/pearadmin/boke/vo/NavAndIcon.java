package com.pearadmin.boke.vo;

import java.util.List;

import lombok.Data;

@Data
public class NavAndIcon {
    
    private List<NavDto> custom;
    
    private boolean isCuteicon;
    
    private String[] cuteicon;
    
    private boolean isGratuity;
    
    private String gratuity;

}
