package com.pangjie.dy;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;

public class DYUtil {

//    private static final String client_key = "awm83gocdgadsu20";
//    private static final String client_secret = "5ec190aa1688f9895edcde6ee1168313";
    private static final String client_key = "awklst4j1hg8w33i";
    private static final String client_secret = "2e880528ab0aa6ff8f553765ab28b0ad";



//    private static String dy_access_token;
//    private static Long dy_access_token_updateTime;
    private static String dy_client_token;
    private static Long dy_client_token_updateTime;
    private static String dy_jsapi_ticket;
    private static Long dy_jsapi_ticket_updateTime;

    /*
     * @Author pangjie
     * @Description //TODO 生成client_token
     * @Date 16:20 21.7.14
     * @Param 
     * @return 
     */

    public static String getClient_token() {
        RestTemplate restTemplate = new RestTemplate();
        if (dy_client_token != null && (dy_client_token_updateTime + 5400000) > new Date().getTime())
            return dy_client_token;
        String client_token = restTemplate.getForObject(String.format("https://open.douyin.com/oauth/client_token?client_key=%s&client_secret=%s&grant_type=client_credential", client_key, client_secret), String.class);
        JSONObject jso = JSONObject.parseObject(client_token);
        JSONObject data = JSONObject.parseObject(String.valueOf(jso.get("data")));
        String access_token = (String) data.get("access_token");
        int error_code = (int) data.get("error_code");
        if (error_code == 0) {
            dy_client_token_updateTime = new Date().getTime();
            dy_client_token = access_token;
        } else System.out.println("error:" + data);
        return access_token;
    }

    /*
     * @Author pangjie
     * @Description //TODO 生成jsapi_ticket
     * @Date 16:20 21.7.14
     * @Param
     * @return
     */

    public static String getJsapi_ticket() {
        RestTemplate restTemplate = new RestTemplate();
        if (dy_jsapi_ticket != null && (dy_jsapi_ticket_updateTime + 5400000) > new Date().getTime())
            return dy_jsapi_ticket;
        String client_token = restTemplate.getForObject(String.format("https://open.douyin.com/js/getticket/?access_token=%s", DYUtil.getClient_token()), String.class);
        JSONObject jsapi_ticket = JSONObject.parseObject(client_token);
        JSONObject data = JSONObject.parseObject(String.valueOf(jsapi_ticket.get("data")));
        String ticket = (String) data.get("ticket");
        int error_code = (int) data.get("error_code");
        if (error_code == 0) {
            dy_jsapi_ticket_updateTime = new Date().getTime();
            dy_jsapi_ticket = ticket;
        } else System.out.println("error:" + data);
        return ticket;
    }


    /*
     * @Author pangjie
     * @Description //TODO 生成client_token
                https://open.douyin.com/platform/oauth/connect/?client_key=awm83gocdgadsu20&response_type=code&scope=user_info,video.create,video.list&redirect_uri=http://pangjie.w3.luyouxia.net/api/DY/login

     * @Date 16:20 21.7.14
     * @Param
     * @return
     */

    public static JSONObject getAccess_token(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String s  = restTemplate.getForObject(String.format("https://open.douyin.com/oauth/access_token?client_key=%s&client_secret=%s&code=%s&grant_type=authorization_code", client_key, client_secret, code), String.class);
        JSONObject jso = JSONObject.parseObject(s);
        JSONObject data = JSONObject.parseObject(String.valueOf(jso.get("data")));
        return data;
    }

    public static void getDYUserInfo(JSONObject data, String userId){
        String access_token = (String) data.get("access_token");
        String open_id = (String) data.get("open_id");

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String s = restTemplate.getForObject("https://open.douyin.com/oauth/userinfo/?access_token=" + access_token + "&open_id=" + open_id, String.class);
        JSONObject jso = JSONObject.parseObject(s);
        JSONObject user_data = JSONObject.parseObject(String.valueOf(jso.get("data")));


    }


    public static String getCode() {
        return new RestTemplate().getForObject("https://open.douyin.com/platform/oauth/connect/?client_key=awm83gocdgadsu20&response_type=code&scope=user_info&redirect_uri=http://pangjie.w3.luyouxia.net/api/DY/login", String.class);
    }


}
