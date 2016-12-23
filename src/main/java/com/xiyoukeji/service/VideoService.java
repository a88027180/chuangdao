package com.xiyoukeji.service;

import com.xiyoukeji.entity.Video;
import com.xiyoukeji.tools.BaseDaoImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Matilda on 2016/12/23.
 */

@Service
public class VideoService {

    @Resource
    BaseDaoImpl<Video> videoBaseDao;

    public void addVideo(Video video) {
        videoBaseDao.save(video);
    }

    public void editVideo(Video video) {
        videoBaseDao.update(video);
    }

    public Video getVideoById(Integer id) {
        return videoBaseDao.get(Video.class, id);
    }

    public void deleteVideo(Integer id) {
        Video video = getVideoById(id);
        videoBaseDao.delete(video);
    }

    public List<Video> getVideoList() {
        return videoBaseDao.find("from Video");
    }

}
