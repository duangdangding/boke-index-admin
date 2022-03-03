package com.lsh.mapper.boke;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.entry.Bokes;
import com.pearadmin.boke.entry.CategoryCount;
import com.pearadmin.boke.entry.CreateDates;
import com.pearadmin.boke.entry.Lookups;
import com.pearadmin.boke.vo.BokeListEntry;
import com.pearadmin.boke.vo.query.QueryBokeVo;

public interface BokesMapper extends BaseMapper<Bokes> {
    IPage<BokeListEntry> getBokes(IPage<BokeListEntry> ipage, String title,Integer labelId,Integer cateId, String createDate, Integer userId);
    IPage<BokeListEntry> getBokesList(IPage ipage,@Param("vo") QueryBokeVo vo);
    
    BokeListEntry getBokesById(Integer bokeId,Integer userId);
    BokeListEntry getBokesByEmId(Integer bokeId,Integer userId);

    List<CategoryCount> getCateCount(Integer userId);
    
    List<CreateDates> getCreates(Integer userId);
    
    List<Lookups> getLookups(Integer userId);
    
    int setLookUp(Integer bokeId);
    int setLikeNum(Integer bokeId);
    @Update("update bokes set share_num = share_num + 1 where boke_id = #{bokeId}")
    int setShareNum(Integer bokeId);
    int setCommentNum(Integer bokeId);
    
    Map<String,Object> getSLLNumById(Integer bokeId);
}
