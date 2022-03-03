package com.lsh.birthday;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class Honey implements Serializable {

    @TableId
    private Integer hId;
    private String hName;
    
    public Honey(String hName) {
        this.hName = hName;
    }
    public Honey(int hId,String hName) {
        this.hId = hId;
        this.hName = hName;
    }

    public Integer gethId() {
        return hId;
    }

    public void sethId(Integer hId) {
        this.hId = hId;
    }

    public String gethName() {
        return hName;
    }

    public void sethName(String hName) {
        this.hName = hName;
    }
}
