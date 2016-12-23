package com.xiyoukeji.controller;

import com.xiyoukeji.entity.Video;
import com.xiyoukeji.service.VideoService;
import com.xiyoukeji.tools.State;
import com.xiyoukeji.tools.UploadType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matilda on 2016/12/23.
 */

@Controller
public class VideoController {

    @Resource
    VideoService videoService;
    @Resource
    HttpServletRequest request;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map uploadFile(MultipartFile file, int type) {
        Map<String, Object> map = new HashMap<>();
        if(file.isEmpty())
        {
            map.put("state", State.FILE_EMPTY.value());
            map.put("detail", "File is empty");
            return map;
        }

        // 保存路径
        String dirPath = request.getSession().getServletContext().getRealPath("/uploads/");
        File dir = new File(dirPath);   // 目录不存在则创建
        if(!dir.isDirectory())
        {
            boolean res = dir.mkdirs();
            if (!res)
            {
                map.put("state", State.FAIL.value());
                map.put("detail", "Upload directory create failed");
                return map;
            }
        }
        String fileName = file.getOriginalFilename();
        String filePath = dirPath+fileName;

        System.out.println("filePath: "+filePath);
        // 转存文件
        try {
            File f = new File(filePath);
            if(f.exists())
            {
                map.put("state", State.FAIL.value());
                map.put("detail", "File name used");
                return map;
            }
            file.transferTo(f);
            String url = "/uploads/"+fileName;
            map.put("state", State.SUCCESS.value());
            map.put("detail", url);    // 文件存储的相对路径

            if(type == UploadType.VIDEO.ordinal()) {            // 视频
                Video video = new Video();
                video.setUrl(url);
                videoService.addVideo(video);
            }

            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("state", State.FAIL.value());
            map.put("detail", "Upload failed");
            return map;
        }
    }

    @RequestMapping("/getVideoList")
    @ResponseBody
    public Map getVideoList() {
        Map<String, List<Video>> map = new HashMap<>();
        map.put("list", videoService.getVideoList());
        return map;
    }
}
