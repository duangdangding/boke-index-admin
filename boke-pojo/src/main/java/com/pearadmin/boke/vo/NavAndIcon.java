package com.pearadmin.boke.vo;

import java.util.List;

public class NavAndIcon {
    
    private List<NavDto> custom;
    
    private boolean isCuteicon;
    
    private String[] cuteicon;
    
    private boolean isGratuity;
    
    private String gratuity;

    public List<NavDto> getCustom() {
        return custom;
    }

    public void setCustom(List<NavDto> custom) {
        this.custom = custom;
    }

    public boolean isCuteicon() {
        return isCuteicon;
    }

    public void setIsCuteicon(boolean cuteicon) {
        isCuteicon = cuteicon;
    }

    public String[] getCuteicon() {
        return cuteicon;
    }

    public void setCuteicon(String[] cuteicon) {
        this.cuteicon = cuteicon;
    }

    public boolean isGratuity() {
        return isGratuity;
    }

    public void setIsGratuity(boolean gratuity) {
        isGratuity = gratuity;
    }

    public String getGratuity() {
        return gratuity;
    }

    public void setGratuity(String gratuity) {
        this.gratuity = gratuity;
    }
}
