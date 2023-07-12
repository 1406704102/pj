package com.pangjie.wx.uploadFile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Random;

public class SignUtil {
    public ResponseEntity<Object> getSignature() {
        Signature sign = new Signature();
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2);

        try {
            return new ResponseEntity<>(sign.getUploadSignature(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
