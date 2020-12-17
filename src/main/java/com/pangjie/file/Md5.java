package com.pangjie.file;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Md5 {

    /*
     * @Author pangjie
     * @Description //TODO 获取网络文件的md5
     * @Date 11:38 2020/12/3 0003
     * @Param DigestUtils.md5Hex(new FileInputStream("D:\\" + video.getVideoName() + ".mp4"));
     * @return 
     */
    public ResponseEntity<Object> urlMd5(String url) throws IOException {
        return new ResponseEntity<>(DigestUtils.md5Hex(new URL(url).openStream()), HttpStatus.OK);
    }

    /*
     * @Author pangjie
     * @Description //TODO 获取删除啊文件得md5
     * @Date 11:42 2020/12/3 0003
     * @Param 
     * @return 
     */
    public ResponseEntity<Object> fileMd5(@RequestParam("file") MultipartFile file) throws IOException {
            return new ResponseEntity<>(DigestUtils.md5Hex(file.getInputStream()), HttpStatus.OK);
    }

    /*
     * @Author pangjie
     * @Description //TODO 根据url 获取文件大小
     * @Date 14:35 2020/12/7 0007
     * @Param 
     * @return 
     */
    public ResponseEntity<Object> getSize(String url) throws IOException {
        return new ResponseEntity<>(new URL(url).openConnection().getContentLength(),HttpStatus.OK);
    }
}
