package com.xiyoukeji.service;

import com.xiyoukeji.entity.Video;
import com.xiyoukeji.tools.BaseDaoImpl;
import com.xiyoukeji.tools.UploadType;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Matilda on 2016/12/23.
 */

@Service
public class FileService {

    @Resource
    BaseDaoImpl<Video> videoBaseDao;
    @Resource
    ServletContext servletContext;

    @PostConstruct
    void initLogger() {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("log4j.properties"));
            String realPath = servletContext.getRealPath("/WEB-INF/logs/");
            properties.setProperty("log4j.appender.out.File", realPath + properties.getProperty("log4j.appender.out.File"));
            properties.setProperty("log4j.appender.err.File", realPath + properties.getProperty("log4j.appender.err.File"));
            PropertyConfigurator.configure(properties);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public Map<String, List<String>> getFileList(HttpSession session, int type) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        String url = "";
        if(type == UploadType.IMAGE.ordinal()) {
            url = "/uploads/img/";
        } else if(type == UploadType.PDF.ordinal()) {
            url = "/uploads/pdf/";
        }
        File file = new File(session.getServletContext().getRealPath(url));
        if(!file.exists() || !file.isDirectory())
        {
            map.put("list", list);
            return map;
        }

        File[] files = file.listFiles();
        if(files == null)
        {
            map.put("list", list);
            return map;
        }

        List<File> fileList = new ArrayList<>();
        Collections.addAll(fileList, files);    // 列表转换

        Collections.sort(fileList, (o1, o2) -> {
            long time1 = o1.lastModified();
            long time2 = o2.lastModified();
            return String.valueOf(time2).compareTo(String.valueOf(time1));
        });

        for (File f : fileList) {
            list.add(url + f.getName());
        }
        map.put("list", list);
        return map;
    }

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

    public void deleteVideoByUrl(String url) {
        List<Video> videos = videoBaseDao.find("from Video v where v.url = '"+url+"'");
        if(videos.size()!=0) {
            for (Video video: videos) {
                videoBaseDao.delete(video);
            }
        }
    }

    public Map<String, List<String>> searchFile(String keyWord, HttpServletRequest request, int type) {
        Map<String, List<String>> map = getFileList(request.getSession(), type);
        List<String> list = map.get("list");
        if(list.size() == 0) {
            return map;
        }

        String url = "";
        if(type == UploadType.IMAGE.ordinal()) {
            url = "/uploads/img/";
        } else if(type == UploadType.PDF.ordinal()) {
            url = "/uploads/pdf/";
        } else if(type == UploadType.VIDEO.ordinal()) {
            url = "/uploads/video/";
        }
        int len = url.length();

        List<String> resList = list.stream().filter(path -> path.substring(len).contains(keyWord)).collect(Collectors.toList());
        map.put("list", resList);
        return map;

    }
}
