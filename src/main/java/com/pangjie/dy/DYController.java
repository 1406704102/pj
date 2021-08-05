package com.pangjie.dy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pangjie.util.FileUtil;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping("/api/DY")
public class DYController {

    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;

    /*
     * @Author pangjie
     * @Description //TODO 加密
     * @Date 14:43 21.8.5
     * @Param 
     * @return 
     */
    @GetMapping("/signature")
    public ResponseEntity<Object> getSignature(Long timestamp, String nonceStr, String url) {
        String jsapi_ticket = DYUtil.getJsapi_ticket();
        System.out.println(jsapi_ticket);
        String s = "jsapi_ticket=" + jsapi_ticket + "&nonce_str=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
        String md5 = FileUtil.getMd5(s.getBytes());
        return new ResponseEntity<>(md5, HttpStatus.OK);
    }

    /*
     * @Author pangjie
     * @Description //TODO 登录
     * @Date 14:43 21.8.5
     * @Param 
     * @return 
     */
    @GetMapping("/login")
    public ResponseEntity<Object> login(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String userId = request.getParameter("userId");
        // 判断
        if (code == null) {
            throw new RuntimeException("用户禁止授权...");
        }
        try {
            JSONObject jsonObject = DYUtil.getAccess_token(code);
            String open_id = (String) jsonObject.get("open_id");
            DYUtil.getDYUserInfo(jsonObject, userId);

            strRedisTemplate.opsForValue().getAndSet(open_id, (String) jsonObject.get("access_token"));

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("抖音扫码登录异常...");
        }
        return new ResponseEntity<>("dyUserInfo", HttpStatus.OK);
    }

    /*
     * @Author pangjie
     * @Description //TODO 上传视频到抖音
     * @Date 14:42 21.8.5
     * @Param 
     * @return 
     */
//    @GetMapping("/getVideo")
//    public ResponseEntity<Object> getVideo(String id, String dyUserId) throws MalformedURLException {
//        VideoInfoDto byId = videoInfoService.findById(id);
//        DyUserInfoDto dyUserInfoDto = dyUserInfoService.findById(dyUserId);
//        RestTemplate restTemplate = new RestTemplate();
////        ResponseEntity<byte[]> video = restTemplate.getForEntity(byId.getVideoUrl(), byte[].class);
//        File fileByUrl = getFileByUrl(byId.getVideoUrl());
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType("multipart/form-data"));
//
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("Content-Type", "video/mp4");
//        body.add("video",new FileSystemResource(fileByUrl));
//        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);
//        String token = strRedisTemplate.opsForValue().get(dyUserInfoDto.getOpenId());
//        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(
//                String.format("https://open.douyin.com/video/upload/?open_id=%s&access_token=%s", dyUserInfoDto.getOpenId(), token),
//                httpEntity, String.class);
////        删除临时文件
//        fileByUrl.deleteOnExit();
//        JSONObject jso = JSONObject.parseObject(stringResponseEntity.getBody());
//        JSONObject data = JSONObject.parseObject(String.valueOf(jso.get("data")));
//        JSONObject data2 = JSONObject.parseObject(String.valueOf(data.get("video")));
//
//        restTemplate = new RestTemplate();
//        headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType("application/json"));
//        HttpEntity<String> request = new HttpEntity<>("{\"video_id\":\""+data2.get("video_id")+"\"}", headers);
//        ResponseEntity<String> stringResponseEntity2 = restTemplate.postForEntity(String.format("https://open.douyin.com/video/create?open_id=%s&access_token=%s", dyUserInfoDto.getOpenId(), token), request, String.class);
//
//
//
//        System.out.println(stringResponseEntity2);
//
//        restTemplate = new RestTemplate();
//        ResponseEntity<String> forEntity = restTemplate.getForEntity(String.format("https://open.douyin.com/video/list?open_id=%s&access_token=%s&cursor=0&count=10", dyUserInfoDto.getOpenId(), token), String.class);
//        JSONObject videoItem = JSONObject.parseObject(forEntity.getBody());
//        JSONObject data1 = JSONObject.parseObject(String.valueOf(videoItem.get("data")));
//        JSONArray list = JSONArray.parseArray(String.valueOf(data1.get("list")));
//        JSONObject o = (JSONObject) list.get(0);
//        String itemId = (String) o.get("item_id");
//        DyShareVideo dyShareVideo = new DyShareVideo();
//        dyShareVideo.setCmtvVideoId(byId.getId());
//        dyShareVideo.setCmtvVideoName(byId.getVideoName());
//        dyShareVideo.setCmtvVideoPic(byId.getVideoPic());
//        dyShareVideo.setCmtvVideoUrl(byId.getVideoUrl());
//        dyShareVideo.setCreateTime(new Timestamp(new Date().getTime()));
//        dyShareVideo.setUpdateTime(dyShareVideo.getCreateTime());
//        dyShareVideo.setCommentCount(0);
//        dyShareVideo.setDiggCount(0);
//        dyShareVideo.setDownloadCount(0);
//        dyShareVideo.setForwardCount(0);
//        dyShareVideo.setShareCount(0);
//        dyShareVideo.setPlayCount(0);
//        dyShareVideo.setDyUserId(dyUserId);
//        dyShareVideo.setDyUserName(dyUserInfoDto.getNickname());
//        dyShareVideo.setDyUserAvatar(dyUserInfoDto.getAvatar());
//        dyShareVideo.setCmtvUserId(dyUserInfoDto.getCmtvUserId());
//        dyShareVideo.setItemId(itemId);
//        DyShareVideoDto dyShareVideoDto = dyShareVideoService.create(dyShareVideo);
//
//        return new ResponseEntity<>(dyShareVideoDto, HttpStatus.OK);
//    }

    public static byte[] urlToByte(String url) throws MalformedURLException {
        URL ur = null;
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            ur = new URL(url);
            in = new BufferedInputStream(ur.openStream());
            out = new ByteArrayOutputStream(1024);
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] content = out.toByteArray();
        return content;
    }

    private File getFileByUrl(String fileUrl) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        BufferedOutputStream stream = null;
        InputStream inputStream = null;
        File file = null;
        try {
            URL imageUrl = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            file = File.createTempFile("file", fileUrl.substring(fileUrl.lastIndexOf("."), fileUrl.length()));
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fileOutputStream);
            stream.write(outStream.toByteArray());
        } catch (Exception ignored) {
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (stream != null) {
                    stream.close();
                }
                outStream.close();
            } catch (Exception ignored) {
            }
        }
        return file;
    }
}
