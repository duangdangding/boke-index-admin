package com.pearadmin.boke.conf.thread;

import com.pearadmin.boke.service.VideoSourceService;
import com.pearadmin.boke.entry.VideoSource;
import com.pearadmin.boke.utils.ApiUtil;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.ResultDtoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @Author lushao
 * @Description 异步线程代理类
 * @Date 2021/7/2 19:06
 * @Version 1.0
 */
@Component
public class MyAsync {
    
    @Autowired
    private ApiUtil apiUtil;
    
    @Async
    public CompletableFuture<ResultDto<Integer>> checkUrl(VideoSourceService videoSourceService, String url, Integer sourceId) {
        int type = apiUtil.isConnect(url);
        VideoSource source = new VideoSource();
        if (type > 0) {
            source.setSourceId(sourceId);
            if (type == 1) {
                source.setSourceSee(false);
            } else {
                source.setSourceSee(true);
                if (type == 2) {
                    source.setSourceType(2);
                } else if (type == 3) {
                    source.setSourceType(1);
                }
            }
            videoSourceService.updateById(source);
        }
        return CompletableFuture.completedFuture(ResultDtoManager.success(type));
    }
}
