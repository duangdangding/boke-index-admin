package com.pearadmin.boke.entry;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class DateArchive {
    @TableId
    private Integer dateId;
    private Date createTime;
    private String dateStr;

    public DateArchive(String dateStr) {
        this.dateStr = dateStr;
    }

}
