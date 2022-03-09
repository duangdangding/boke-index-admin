package com.pearadmin.boke.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.BokesMapper;
import com.lsh.mapper.boke.DateArchiveMapper;
import com.pearadmin.boke.entry.Bokes;
import com.pearadmin.boke.entry.CategoryCount;
import com.pearadmin.boke.entry.CreateDates;
import com.pearadmin.boke.entry.DateArchive;
import com.pearadmin.boke.entry.Lookups;
import com.pearadmin.boke.service.BokesService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.MyStringUtil;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.BokeListEntry;
import com.pearadmin.boke.vo.query.QueryBokeVo;

import cn.hutool.json.JSONUtil;

@Service
public class BokesServiceImpl extends ServiceImpl<BokesMapper, Bokes> implements BokesService {

    @Autowired
    private BokesMapper bokesMapper;
    
    @Autowired
    private DateArchiveMapper dateArchiveMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean addOrUpdateAndSetCache(Bokes bokes) {

        String dateStr = Constants.DateFormat.S_DATE.format(new Date());
        bokes.setCreateDate(dateStr);

        QueryWrapper<DateArchive> wrapper = new QueryWrapper<>();
        wrapper.eq("date_str",dateStr);
        DateArchive one = dateArchiveMapper.selectOne(wrapper);
        if (one == null) {
            dateArchiveMapper.insert(new DateArchive(dateStr));
            String key = Constants.RedisKey.DATEARCHIVE;
            redisUtil.del(key);
        }

        boolean b = saveOrUpdate(bokes);
        if (b) {
            Integer bokeId = bokes.getBokeId();
            BokeListEntry bokesById = bokesMapper.getBokesById(bokeId, bokes.getUserId());
            // 设置缓存1个月
            redisUtil.setToDay(Constants.RedisKey.BOKEXQ + bokeId, JSONUtil.toJsonStr(bokesById), 30);
        }
        return b;
    }

    @Override
    public IPage<BokeListEntry> getBokes(int page, int size, Bokes bokes) {
        Page<BokeListEntry> pageData = new Page<>(page, size);
        pageData.setOptimizeCountSql(false);
        return bokesMapper.getBokes(pageData, bokes.getTitle(), bokes.getLId(),bokes.getCateId(), bokes.getCreateDate(), bokes.getUserId());
    }

    @Override
    public IPage<BokeListEntry> getBokesList(QueryBokeVo vo) {
        Page pageData = new Page<>(vo.getPage(), vo.getLimit());
        // 设置false 不对count进行优化
        pageData.setOptimizeCountSql(false);
        return bokesMapper.getBokesList(pageData,vo);
    }

    @Override
    public BokeListEntry getBokesById(Integer bokeId, Long userId) {
        String key = Constants.RedisKey.BOKEXQ + bokeId;
        BokeListEntry boke;
        if (redisUtil.hasKey(key)) {
            boke = JSONUtil.toBean(redisUtil.get(key).toString(), BokeListEntry.class);
        } else {
            boke = bokesMapper.getBokesById(bokeId, userId);
            // 设置缓存1个月
            redisUtil.setToDay(key, JSONUtil.toJsonStr(boke), 30);
        }
        if (boke != null && boke.getBokeZip() == 1) {
            boke.setBokeCont(MyStringUtil.uncompress(boke.getBokeCont()));
            boke.setMdContent(MyStringUtil.uncompress(boke.getMdContent()));
        }
        return boke;
    }

    @Override
    public BokeListEntry getBokesByEmId(Integer bokeId, Long userId) {
        return bokesMapper.getBokesByEmId(bokeId, userId);
    }

    @Override
    public List<CategoryCount> getCateCount(Integer userId) {
        String key = Constants.RedisKey.CATERANK;
        if (redisUtil.hasKey(key)) {
            return JSONUtil.toList(redisUtil.get(key).toString(),CategoryCount.class);
        }
        List<CategoryCount> cateCount = bokesMapper.getCateCount(userId);
        redisUtil.set(key,JSONUtil.toJsonStr(cateCount));
        return cateCount;
    }

    @Override
    public List<CreateDates> getCreates(Integer userId) {
        String key = Constants.RedisKey.GUIDANG + userId;
        List<CreateDates> createDates;
        if (redisUtil.hasKey(key)) {
            createDates = JSONUtil.toList(redisUtil.get(key).toString(), CreateDates.class);
        } else {
            createDates = bokesMapper.getCreates(userId);
            redisUtil.set(key, JSONUtil.toJsonStr(createDates));
        }
        return createDates;
    }

    @Override
    public List<Lookups> getLookups(Integer userId) {
        
        String key = Constants.RedisKey.LOOKRANK;
        if (redisUtil.hasKey(key)) {
            return JSONUtil.toList(redisUtil.get(key).toString(),Lookups.class);
        }
        List<Lookups> lookups = bokesMapper.getLookups(userId);
        redisUtil.set(key,JSONUtil.toJsonStr(lookups),Constants.TimeStr.DAY5);
        return lookups;
    }

    @Override
    public int setLookUp(Integer bokeId) {
        int i = bokesMapper.setLookUp(bokeId);
        if (i > 0) {
            String key = Constants.RedisKey.LOOKNUM + bokeId;
            redisUtil.incr(key,1);
        }
        return i;
    }

    @Override
    public int setLikeNum(Integer bokeId) {
        int i = bokesMapper.setLikeNum(bokeId);
        String key = Constants.RedisKey.LIKENUM + bokeId;
        if (i > 0) {
            redisUtil.incr(key,1);
        }
        return i;
    }

    @Override
    public int setShareNum(Integer bokeId) {
        int i = bokesMapper.setShareNum(bokeId);
        String key = Constants.RedisKey.SHARENUM + bokeId;
        if (i > 0) {
            redisUtil.incr(key,1);
        }
        return i;
    }

    @Override
    public int setCommentNum(Integer bokeId) {
        return bokesMapper.setCommentNum(bokeId);
    }

    @Override
    public boolean checkTitle(String title) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("title", title);
        Long integer = bokesMapper.selectCount(wrapper);
        return integer > 0;
    }

    @Override
    public int setDeleteState(List<Long> ids, Integer deleteState) {
        QueryWrapper<Bokes> queryWrapper = new QueryWrapper();
        // string数组转list<Integer>
        queryWrapper.in("boke_id",ids);
        Bokes bokes = new Bokes();
        bokes.setDeleteState(deleteState);
        return bokesMapper.update(bokes,queryWrapper);
    }

    @Override
    public int setUnPass(List<Long> ids,Integer unpass) {
        QueryWrapper queryWrapper = new QueryWrapper();
        // string数组转list<Integer>
        queryWrapper.in("boke_id",ids);
        Bokes bokes = new Bokes();
        bokes.setBokeExamine(unpass);
        return bokesMapper.update(bokes,queryWrapper);
    }

    @Override
    public Map<String, Object> getSLLNumById(Integer bokeId) {
        return bokesMapper.getSLLNumById(bokeId);
    }

    @Override
    public Bokes selectByUidAndBid(Long userId, Long bokeId) {
        QueryWrapper<Bokes> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("boke_id",bokeId);
        return bokesMapper.selectOne(wrapper);
    }
}
