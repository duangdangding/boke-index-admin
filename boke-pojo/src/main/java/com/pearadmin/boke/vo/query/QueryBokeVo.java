package com.pearadmin.boke.vo.query;

import lombok.Data;

@Data
public class QueryBokeVo extends PageVo{
    
    private String username;
    private String title;
    
    // 博客类型
    private Integer bokeType;
    
    // 编辑器类型 富文本1 markdown 是2
    private Integer editType;
    
    // 博客标签
    private Integer labelId;
    
    // 删除状态 0已删除1未删除
    private Integer deleteState;
    
    // 置顶 置顶：0否1是
    private Integer top;
    
    // 是否压缩 0未压缩1压缩
    private Integer bokeZip;
    
    // 通过 0未审核1通过2不通过
    private Integer pass;
    
    // 私有 1公开2私有
    private Integer pubPri;
    
}
