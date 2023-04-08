package com.example.village_back.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.village_back.entity.Files;
import com.example.village_back.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


/**
 * 文件上传相关接口
 * @throws IOException
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${files.upload.path}")
    public String fileUploadPath;
    @Qualifier("fileMapper")
    @Resource
    private FileMapper fileMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String type = FileUtil.extName(originalFileName);
        long size = file.getSize();

//        定义一个文件的一个唯一标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID =  uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath +fileUUID);

        //获取父目录
        File parentFile = uploadFile.getParentFile();
        //判断配置的文件目录是否存在，若不存在就创建一个新的文件目录
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }

         //当文件存在的时候再去获取文件的md5
        String md5;
        String url;
        //把获取到的文件存储到磁盘目录去
        file.transferTo(uploadFile);
        //储存进去之后就能获取到文件的md5
        md5 = SecureUtil.md5(uploadFile);
        //从数据库查询文件的md5是否存在
        Files dbFile = getFileByMd5(md5);
        if (dbFile!=null){
            url = dbFile.getUrl();
            //由于文件已经存在，所以删除刚才上传的重复文件
            uploadFile.delete();
        } else {
            //若数据库不存在重复文件，则不删除刚才上传的文件
            url = "http://localhost:9090/file/"+ fileUUID;
        }


//        存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFileName);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);
        return url;
    }

    /**
     * 文件下载接口路径 http://localhost:9090/file/{fileUUID}
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
//        获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        ServletOutputStream os = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");
//        读取上传文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 通过文件的md5查询文件
     * @param md5
     * @return
     */
    private Files getFileByMd5(String md5){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() ==0 ? null : filesList.get(0);
    }
}
