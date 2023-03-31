package com.pangjie.wx.qrCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.DatatypeConverter;

@RestController
public class QRCodeController {

    private final QCodeService qCodeService;

    public QRCodeController(QCodeService qCodeService) {
        this.qCodeService = qCodeService;
    }

    @Autowired


    //获取小程序商品码
    @GetMapping("/getWxAppQR/{storeId}")
    public ResponseEntity<String> getWxAppQR(@PathVariable String storeId) {
        byte[] goodShareImage = qCodeService.createGoodShareImage(storeId, "pages/order/qrOrder/alone");
        String base64 = DatatypeConverter.printBase64Binary(goodShareImage);
        return new ResponseEntity<>(base64, HttpStatus.OK);
    }

}
