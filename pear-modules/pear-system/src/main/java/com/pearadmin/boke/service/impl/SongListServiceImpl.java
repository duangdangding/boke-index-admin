package com.pearadmin.boke.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.SongListMapper;
import com.pearadmin.boke.entry.SongList;
import com.pearadmin.boke.service.SongListService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.Constants;

import cn.hutool.json.JSONUtil;

/**
* SongList接口实现层
*
* @author lushao
* @version 1.0.0 2021-06-16 09:43:56
*/
@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {

    @Autowired
    private SongListMapper songListMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public SongList getByUserId(Integer userId) {
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        SongList songList;
        String key = Constants.RedisKey.SONGLIST + userId;
        if (redisUtil.hasKey(key)) {
            songList = JSONUtil.toBean(redisUtil.get(key).toString(),SongList.class);
        } else {
            songList = songListMapper.selectOne(queryWrapper);
            redisUtil.set(key,JSONUtil.toJsonStr(songList));
        }
        return songList;
    }

    @Override
    public int updateByUserId(SongList song) {
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",song.getUserId());
        return songListMapper.update(song,wrapper);
    }
}
