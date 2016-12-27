package com.xiyoukeji.controller;

import com.xiyoukeji.entity.Video;
import com.xiyoukeji.service.FileService;
import com.xiyoukeji.tools.State;
import com.xiyoukeji.tools.UploadType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matilda on 2016/12/23.
 */

@Controller
public class FileController {

    @Resource
    FileService fileService;
    @Resource
    HttpServletRequest request;

    @ExceptionHandler
    @ResponseBody
    public Map processException(RuntimeException e){
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.EXCEPTION.value());
        map.put("detail", "Exception occurred");
        return map;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map uploadFile(MultipartFile file, String name, int type) {
        Map<String, Object> map = new HashMap<>();
        if(file.isEmpty())
        {
            map.put("state", State.FAIL.value());
            map.put("detail", "该文件为空");
            return map;
        }

        // 保存路径
        String fileName = file.getOriginalFilename();
        String dir;
        if(type == UploadType.VIDEO.ordinal())
        {
            dir = "/uploads/video/";
        }
        else
            dir = "/uploads/img/";
        String dirPath = request.getSession().getServletContext().getRealPath(dir);
        File dirFile = new File(dirPath);   // 目录不存在则创建
        if(!dirFile.isDirectory())
        {
            boolean res = dirFile.mkdirs();
            if (!res)
            {
                map.put("state", State.FAIL.value());
                map.put("detail", "上传目录新建失败");
                return map;
            }
        }

        String filePath = dirPath+fileName;

        // 转存文件
        try {
            File f = new File(filePath);
            if(f.exists())
            {
                map.put("state", State.FAIL.value());
                map.put("detail", "文件名已被使用");
                return map;
            }
            file.transferTo(f);
            map.put("state", State.SUCCESS.value());
            map.put("detail", dir+fileName);    // 文件存储的相对路径

            if(type == UploadType.VIDEO.ordinal()) {            // 视频
                Video video = new Video();
                video.setName(name==null?"":name);
                video.setUrl(dir+fileName);
                fileService.addVideo(video);
            }

            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("state", State.UPLOAD_FAIL.value());
            map.put("detail", State.UPLOAD_FAIL.desc());
            return map;
        }
    }

    @RequestMapping("/getVideoList")
    @ResponseBody
    public Map getVideoList() {
        Map<String, List<Video>> map = new HashMap<>();
        map.put("list", fileService.getVideoList());
        return map;
    }

    @RequestMapping(value = "/deleteVideo", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteVideo(Integer id) {
        fileService.deleteVideo(id);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/editVideo", method = RequestMethod.POST)
    @ResponseBody
    public Map editVideo(Video video) {
        fileService.editVideo(video);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping("/deleteImg")
    @ResponseBody
    public Map deleteImg(String url) {  //url为相对路径
        Map<String, Object> map = new HashMap<>();
        File file = new File(request.getSession().getServletContext().getRealPath(url));
        if(!file.exists() || file.isDirectory()) {
            map.put("state", State.FAIL.value());
            map.put("detail", "该图片不存在");
            return map;
        }
        boolean res = file.delete();
        if (!res) {
            map.put("state", State.FAIL.value());
            map.put("detail", "图片删除失败");
            return map;
        }
        map.put("state", State.SUCCESS.value());
        map.put("detail", "图片删除成功");
        return map;
    }

    @RequestMapping("/getImgList")
    @ResponseBody
    public Map getImgList() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> imgs = new ArrayList<>();
        File file = new File(request.getSession().getServletContext().getRealPath("/uploads/img/"));
        if(!file.exists() || !file.isDirectory())
        {
            map.put("list", imgs);
            return map;
        }

        File[] files = file.listFiles();
        if(files == null)
        {
            map.put("list", imgs);
            return map;
        }

        for (File f : files) {
            imgs.add("/uploads/img/" + f.getName());
        }
        map.put("list", imgs);
        return map;
    }
}
