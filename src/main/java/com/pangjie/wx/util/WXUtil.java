package com.pangjie.wx.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class WXUtil {
    private static final String APPID = "...";
    private static final String SECRET = "...";

    //分享
    private static String access_token;
    private static Long access_token_updateTime;

    //登录授权
    private static String login_access_token;
    private static String openId;
    private static Long login_access_token_updateTime;

    private static String jsapiTicket;
    private static Long jsapiTicket_updateTime;


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

    /*
     * @Author pangjie
     * @Description //TODO 文字检测
     * @Date 17:17 2020/10/23 0023
     * @Param 
     * @return 
     */
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
     * 功能描述: 获取 AccessToken
     * No such property: code for class: Script1
     *
     * @return:
     * @Author: pangjie
     * @Date: 2020/4/13 0013 10:40
     */
    public static String getAccessToken() {

        if (access_token != null && (access_token_updateTime + 5400000) > new Date().getTime())
            return access_token;
        RestTemplate restTemplate = new RestTemplate();
        AccessTokenResult accessTokenResult = restTemplate.getForObject(String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", APPID, SECRET), AccessTokenResult.class);
        if (accessTokenResult.getErrcode() == null || accessTokenResult.getErrcode().equals("0")) {
            access_token_updateTime = new Date().getTime();
            access_token = accessTokenResult.getAccess_token();
        } else System.out.println("error:" + accessTokenResult);
        return accessTokenResult.getAccess_token();
//        return "";
    }

    /**
     * 功能描述: 根据AccessToken获取 jsapiTicket
     * No such property: code for class: Script1
     *
     * @return:
     * @Author: pangjie
     * @Date: 2020/4/13 0013 10:41
     */
    public static String getJsApiTicket() {
        if (jsapiTicket != null && (jsapiTicket_updateTime + 5400000) > new Date().getTime())
            return jsapiTicket;
        RestTemplate restTemplate = new RestTemplate();
        JsApiTicketResult jsApiTicketResult = restTemplate.getForObject(String.format("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi",
                getAccessToken()), JsApiTicketResult.class);
        if (jsApiTicketResult.getErrcode() == null || jsApiTicketResult.getErrcode().equals("0")) {
            jsapiTicket_updateTime = new Date().getTime();
            jsapiTicket = jsApiTicketResult.getTicket();
        } else System.out.println("error:" + jsApiTicketResult);
        return jsApiTicketResult.getTicket();
    }

    /**
     * 功能描述:
     * No such property: code for class: Script1
     * @return: 获取公众号授权Token
     * @Author: pangjie
     * @Date: 2020/8/4 0004 10:31
     */
    public static String getAuthAccessToken(String code){
        if (login_access_token != null && (login_access_token_updateTime + 5400000) > new Date().getTime())
            return login_access_token;
        RestTemplate restTemplate = new RestTemplate();

        String wxJsonToken = restTemplate.getForObject(String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                APPID,SECRET,code), String.class);
        JSONObject jsonToken = JSONObject.parseObject(wxJsonToken);
        if (StringUtils.isNotBlank(String.valueOf(jsonToken))) {
            login_access_token_updateTime = new Date().getTime();
            openId = jsonToken.getString("openid");
            login_access_token = jsonToken.getString("access_token");
        }
        return jsonToken.getString("access_token");
    }
    
    /**
     * 功能描述: 签名 url(分享的url)
     * No such property: code for class: Script1
     *
     * @return:
     * @Author: pangjie
     * @Date: 2020/4/13 0013 14:05
     */
    public static Map<String, String> getSignature(String url) {
        Map<String, String> ret = new HashMap<>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String jsapi_ticket = getJsApiTicket();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appId", APPID);
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    /**
     * 功能描述: 生成随机字符串
     * No such property: code for class: Script1
     *
     * @return:
     * @Author: pangjie
     * @Date: 2020/4/13 0013 14:09
     */
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    /**
     * 功能描述: 生成时间戳字符串
     * No such property: code for class: Script1
     *
     * @return:
     * @Author: pangjie
     * @Date: 2020/4/13 0013 14:09
     */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 功能描述: 获取直播间
     * No such property: code for class: Script1
     *
     * @return:
     * @Author: pangjie
     * @Date: 2020/3/25 0025 17:29
     */

    public static String getRepalyVideo(String access_token, Integer LiveId) {
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("access_token", access_token);
        jsonObject.put("action", "get_replay");
        jsonObject.put("room_id", LiveId);
        jsonObject.put("start", 0);
        jsonObject.put("limit", 1);

        JSONObject s = new RestTemplate().postForObject("http://api.weixin.qq.com/wxa/business/getliveinfo?access_token=" + access_token, jsonObject, JSONObject.class);
        System.out.println(s);
        return "";
    }


}
