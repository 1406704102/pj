package com.pangjie.wx.qrCode;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.DatatypeConverter;

@RestController
@RequiredArgsConstructor
public class QRCodeController {

    private final QCodeService qCodeService;



    //获取小程序商品码
    @GetMapping("/getWxAppQR/{storeId}")
    public ResponseEntity<String> getWxAppQR(@PathVariable(name = "storeId") String storeId) {
        byte[] goodShareImage = qCodeService.createGoodShareImage(storeId, "pages/order/qrOrder/alone");
        String base64 = DatatypeConverter.printBase64Binary(goodShareImage);
        return new ResponseEntity<>(base64, HttpStatus.OK);
    }

}
