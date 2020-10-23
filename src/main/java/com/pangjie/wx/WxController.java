package com.pangjie.wx;


import com.pangjie.wx.util.WXUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/WX")
public class WxController {


    @GetMapping("getToken")
    public ResponseEntity<Object> getToken() {
        String accessToken = WXUtil.getAccessToken();
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    @GetMapping("getJsTicket")
    public ResponseEntity<Object> getJsTicket() {
        String jsApiTicket = WXUtil.getJsApiTicket();
        return new ResponseEntity<>(jsApiTicket, HttpStatus.OK);
    }

    @GetMapping("getSignature")
    public ResponseEntity<Object> getSignature(String url) {
        return new ResponseEntity<>(WXUtil.getSignature(url), HttpStatus.OK);
    }

//    @GetMapping("get_auth_access_token")
//    public ResponseEntity<Object> getAuthAccessToken(String code,String state){
//        WXUserInfo authAccessToken = WXUtil.getUserInfo(code);
//        return new ResponseEntity<>(authAccessToken, HttpStatus.OK);
//    }
}