package com.pangjie.wx.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import org.bouncycastle.util.encoders.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.security.Key;
import java.security.Security;
import java.util.*;

public class WXUtil {
    private static final String APPID = "wx476ac149a7edb387";
    private static final String SECRET = "c876b5e95ad5cf3af006ff9a9a156776";

    private static String access_token;
    private static Long access_token_updateTime;


    public static String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        if (access_token != null && (access_token_updateTime + 5400000) > new Date().getTime())
            return access_token;
        AccessTokenResult accessTokenResult = restTemplate.getForObject(String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", APPID, SECRET), AccessTokenResult.class);
        if (accessTokenResult.getErrcode() == null || accessTokenResult.getErrcode().equals("0")) {
            access_token_updateTime = new Date().getTime();
            access_token = accessTokenResult.getAccess_token();
        } else System.out.println("error:" + accessTokenResult);
        return accessTokenResult.getAccess_token();
    }

    /**
     * 图片违规检测
     *
     * @param accessToken
     * @param file
     * @return
     */
    public static Boolean imgFilter(String accessToken, MultipartFile file) {
        String contentType = file.getContentType();
        return checkPic(file, accessToken, contentType);
    }

    /**
     * 文本违规检测
     *
     * @param accessToken
     * @param content
     * @return
     */
    public static Boolean cotentFilter(String accessToken, String content) {
        return checkContent(accessToken, content);
    }

    /**
     * 恶意图片过滤
     *
     * @param multipartFile
     * @return
     */
    private static Boolean checkPic(MultipartFile multipartFile, String accessToken, String contentType) {
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();

            CloseableHttpResponse response = null;

            HttpPost request = new HttpPost("https://api.weixin.qq.com/wxa/img_sec_check?access_token=" + accessToken);
            request.addHeader("Content-Type", "application/octet-stream");

            InputStream inputStream = multipartFile.getInputStream();

            byte[] byt = new byte[inputStream.available()];
            inputStream.read(byt);
            request.setEntity(new ByteArrayEntity(byt, ContentType.create(contentType)));

            response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");// 转成string
            JSONObject jso = JSONObject.parseObject(result);
            return getResult(jso);
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("----------------调用腾讯内容过滤系统出错------------------");
            return true;
        }
    }

    private static Boolean checkContent(String accessToken, String content) {
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();

            CloseableHttpResponse response = null;

            HttpPost request = new HttpPost("https://api.weixin.qq.com/wxa/msg_sec_check?access_token=" + accessToken);
            request.addHeader("Content-Type", "application/json");
            Map<String, String> map = new HashMap<>();
            map.put("content", content);
            String body = JSONObject.toJSONString(map);
            request.setEntity(new StringEntity(body, ContentType.create("text/json", "UTF-8")));
            response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");// 转成string
            JSONObject jso = JSONObject.parseObject(result);
            return getResult(jso);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("----------------调用腾讯内容过滤系统出错------------------");
            return true;
        }
    }

    private static Boolean getResult(JSONObject jso) {
        Object errcode = jso.get("errcode");
        int errCode = (int) errcode;
        if (errCode == 0) {
            return true;
        } else if (errCode == 87014) {
            System.out.println("内容违规-----------");
            return false;
        }
        return true;
    }

    /**
     * 功能描述: 获取当前
     * No such property: code for class: Script1
     * access_token => token
     * liveId => 房间id
     * @return:
     * @Author: pangjie
     * @Date: 2020/3/25 0025 17:29
     */

    public static String getReplayVideo(String access_token, Integer LiveId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "get_replay");//获取回放
        jsonObject.put("room_id", LiveId);//房间id
        jsonObject.put("start", 0);//开始位置 (0表示从第一个视频片段开始拉取)
        jsonObject.put("limit", 1);////只要一个

        JSONObject s = new RestTemplate().postForObject("https://api.weixin.qq.com/wxa/business/getliveinfo?access_token=" + access_token, jsonObject, JSONObject.class);
        ArrayList live_replay = (ArrayList) s.get("live_replay");
        LinkedHashMap<String,String> o = (LinkedHashMap<String, String>) live_replay.get(0);
        System.out.println();
        return o.get("media_url");
    }

    public static String getJ2SR(String code) {
        String s = new RestTemplate().getForObject("https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code", String.class);

        return s;
    }

//    public static String sendRemind(String openId, String templateId, LiveInfoDto liveInfo) {
//
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("thing1");
//        strings.add("thing2");
////        strings.add("thing3");
//        strings.add("thing4");
//        strings.add("thing5");
//        Map<String, Object> param = new HashMap<>();
//        param.put("touser", openId);
//        param.put("template_id", templateId);
//        param.put("page", "/pages/my/home/index");
//        Map<String, Object> data = new HashMap<>();
//        HashMap<String, String> value1 = new HashMap<>();
//        HashMap<String, Object> value2 = new HashMap<>();
//        HashMap<String, String> value4 = new HashMap<>();
//        HashMap<String, String> value5 = new HashMap<>();
//        value1.put("value", liveInfo.getLiveName());
//        value2.put("value", new SimpleDateFormat("MM-dd HH:mm").format(liveInfo.getStartTime()));
//        value4.put("value", "暂无直播权益");
//        value5.put("value", "您订阅的直播已经开始了,快去看看吧!");
//        data.put(strings.get(0), value1);
//        data.put(strings.get(1), value2);
//        data.put(strings.get(2), value4);
//        data.put(strings.get(3), value5);
//
//
//
//
//        param.put("data", data);
//
//
//        String s = new RestTemplate().postForObject("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + getAccessToken(), param, String.class);
//        System.out.println(s);
//        return s;
//    }

//<!--解密获取UnionID-->

    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM_STR = "AES/CBC/PKCS7Padding";
    private static Key key;
    private static Cipher cipher;

    public static String decryptData(String encryptDataB64, String sessionKeyB64, String ivB64) {
        return new String(
                decryptOfDiyIV(
                        Base64.decode(encryptDataB64),
                        Base64.decode(sessionKeyB64),
                        Base64.decode(ivB64)
                )
        );
    }

    public static void init(byte[] keyBytes) {
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(ALGORITHM_STR, "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @param ivs           自定义对称解密算法初始向量 iv
     * @return 解密后的字节数组
     */
    public static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

}
