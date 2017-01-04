package com.xiyoukeji.controller;

import com.xiyoukeji.entity.Video;
import com.xiyoukeji.service.FileService;
import com.xiyoukeji.service.SettingService;
import com.xiyoukeji.tools.State;
import com.xiyoukeji.tools.UploadType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Matilda on 2016/12/23.
 */

@Controller
public class FileController {

    @Resource
    FileService fileService;
    @Resource
    SettingService settingService;
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
            dir = "/uploads/video/";
        else if(type == UploadType.IMAGE.ordinal())
            dir = "/uploads/img/";
        else
            dir = "/uploads/pdf/";

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
                video.setImg("");
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

    @RequestMapping(value = "/setHomeVideo", method = RequestMethod.POST)
    @ResponseBody
    public Map setHomeVideo(Integer id) {
        Map<String, Object> map = new HashMap<>();
        settingService.deleteHomeVideo();
        settingService.setHomeVideo(id);

        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/setHomeImg", method = RequestMethod.POST)
    @ResponseBody
    public Map setHomeImg(String url) {
        List<String> list = settingService.getHomeImg();
        Map<String, Object> map = new HashMap<>();
        if(list.size()==9) {
            map.put("state", State.SET_EXCEED.value());
            map.put("detail", State.SET_EXCEED.desc()+": 9");
            return map;
        }
        settingService.setHomeImg(url);
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/setHomeImgs", method = RequestMethod.POST)
    @ResponseBody
    public Map setHomeImgs(@RequestParam(value = "urls") String[] urls) {
        Map<String, Object> map = new HashMap<>();
        settingService.deleteHomeImgs();  // 清除原先设置信息
        for (String url: urls) {
            settingService.setHomeImg(url);
        }
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

    @RequestMapping(value = "/editHomeImg", method = RequestMethod.POST)
    @ResponseBody
    public Map editHomeImg(String pre_url, String cur_url) {
        settingService.editHomeImg(pre_url, cur_url);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping("/deleteFile")
    @ResponseBody
    public Map deleteFile(String url, Integer type) {  //url为相对路径
        Map<String, Object> map = new HashMap<>();
        if((url.startsWith("/uploads/img") && type!= UploadType.IMAGE.ordinal()) ||
                url.startsWith("/uploads/video") && type != UploadType.VIDEO.ordinal() ||
                url.startsWith("/uploads/pdf") && type != UploadType.PDF.ordinal()) {
            map.put("state", State.FAIL.value());
            map.put("detail", "文件类型不匹配");
            return map;
        }
        File file = new File(request.getSession().getServletContext().getRealPath(url));
        if(!file.exists()) {
            map.put("state", State.FAIL.value());
            map.put("detail", "该文件不存在");
            return map;
        }
        boolean res = file.delete();
        if (!res) {
            map.put("state", State.FAIL.value());
            map.put("detail", "文件删除失败");
            return map;
        }
        if(type == UploadType.VIDEO.ordinal()) {
            fileService.deleteVideoByUrl(url);
            settingService.deleteHomeVideo();
        }
//        else if(type == UploadType.IMAGE.ordinal()) {
//            settingService.deleteHomeImg();
//        }
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping("/getImgList")
    @ResponseBody
    public Map getImgList(HttpServletRequest request) { // 不能直接用HttpSession
        return fileService.getFileList(request.getSession(), 1);
    }

    @RequestMapping("/getPdfList")
    @ResponseBody
    public Map getPdfList(HttpServletRequest request) {
        return fileService.getFileList(request.getSession(), 2);
    }


    @RequestMapping(value = "/searchFile", method = RequestMethod.POST)
    @ResponseBody
    public Map searchFile(String keyWord, int type, HttpServletRequest request) {
        return fileService.searchFile(keyWord, request, type);
    }



}
