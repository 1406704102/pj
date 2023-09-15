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


//    @GetMapping("/getJ2SR")
//    public ResponseEntity<Object> getJ2SR(String code, UserInfo userInfo, String encryptedData, String iv, HttpServletRequest request) {
//        String j2SR = WXUtil.getJ2SR(code);
//        JSONObject json = JSONObject.parseObject(j2SR);
//        String openid = json.getString("openid");
//        return new ResponseEntity<>(openid, HttpStatus.OK);
//    }
}