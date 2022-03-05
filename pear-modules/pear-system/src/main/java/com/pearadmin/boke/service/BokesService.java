package com.pearadmin.boke.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.Bokes;
import com.pearadmin.boke.entry.CategoryCount;
import com.pearadmin.boke.entry.CreateDates;
import com.pearadmin.boke.entry.Lookups;
import com.pearadmin.boke.vo.BokeListEntry;
import com.pearadmin.boke.vo.query.QueryBokeVo;

public interface BokesService extends IService<Bokes> {
    
    boolean addOrUpdateAndSetCache(Bokes bokes);

    IPage<BokeListEntry> getBokes(int page, int size, Bokes bokes);
    IPage<BokeListEntry> getBokesList(QueryBokeVo vo);

    BokeListEntry getBokesById(Integer bokeId,Long userId);
    BokeListEntry getBokesByEmId(Integer bokeId,Long userId);

    List<CategoryCount> getCateCount(Integer userId);

    List<CreateDates> getCreates(Integer userId);

    List<Lookups> getLookups(Integer userId);

    int setLookUp(Integer bokeId);
    int setLikeNum(Integer bokeId);
    int setShareNum(Integer bokeId);
    int setCommentNum(Integer bokeId);
    
    boolean checkTitle(String title);
    
    int setDeleteState(List<Long> ids,Integer deleteState);
    int setUnPass(List<Long> ids,Integer unPass);

    Map<String,Object> getSLLNumById(Integer bokeId);
}
