package com.lsh.birthday;

import java.io.Serializable;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Comment implements Serializable {
    @TableId
    private Long commentId;
    private String context;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createtime;
    private int userId;
    @TableField(exist = false)
    private UserMsg userMsg;
    
    public Comment() {
    }
    public Comment(String context,int userId) {
        this.context = context;
//        this.createtime = createtime;
        this.userId = userId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", context='" + context + '\'' +
                ", createtime=" + createtime +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserMsg getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(UserMsg userMsg) {
        this.userMsg = userMsg;
    }
}
