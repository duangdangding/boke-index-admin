package com.pearadmin.boke.vo.query;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * 视频源查询VO
 */
@Data
public class SourceSearchVo {

//   1、正常 2、不正常 3、m3u8
   private Integer createType;
   private Integer startId;
   private Integer endId;
//   1手机端2PC端
   private Integer exporType;
   private String startTime;
   private String endTime;
//  是否需要解析0不解析需要解析
   private Integer needParse;
   private Integer shoudong = 0;
   
   @Getter(AccessLevel.NONE)
   private Integer sourceType;
   
   public Integer getSourceType() {
       return sourceType == null ? createType : sourceType;
   }
}
