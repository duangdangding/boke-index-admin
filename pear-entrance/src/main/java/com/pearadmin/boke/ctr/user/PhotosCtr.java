package com.pearadmin.boke.ctr.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.entry.Photos;
import com.pearadmin.boke.service.PhotosService;
import com.pearadmin.boke.service.VisitsService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.VisitsUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;

@RestController
public class PhotosCtr extends BaseCtr {
    
    @Autowired
    private PhotosService photosService;
    
    @Autowired
    private VisitsService visitsService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @RequestMapping("/photos")
    public ModelAndView photosList(Integer page, HttpServletRequest request,Integer photoType) {
        if (page == null || page == 0) {
            page = 1;
        }
        Map<String,Object> map = new HashMap<>();
        IPage<Photos> photosList = photosService.getPhotosList(page,photoType);
        map.put("photos",photosList);
        VisitsUtil.setVisitCount(request,visitsService,redisUtil);
        return getView("boke/photos",map);
    }
    
    @RequestMapping("/photos/fy")
    public IPage<Photos> photosList2( Integer page,Integer photoType) {
        if (page == null || page == 0) {
            page = 1;
        }
        IPage<Photos> photosList = photosService.getPhotosList(page,photoType);
        return photosList;
    }
}
