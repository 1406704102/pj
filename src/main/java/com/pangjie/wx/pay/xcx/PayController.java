package com.pangjie.wx.pay.xcx;

import com.pangjie.util.JacksonUtil;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public class PayController {
    public Object prepay(Integer userId, @RequestBody String body, HttpServletRequest request) {

        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        //查询订单
//        LitemallOrder order = orderService.findById(userId, orderId);

        //查询用户
//        LitemallUser user = userService.findById(userId);
//        String openid = user.getWeixinOpenid();

//        WxPayMpOrderResult result = null;
//        try {
//            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
//            orderRequest.setOutTradeNo(order.getOrderSn());
//            orderRequest.setOpenid(openid);
//            orderRequest.setBody("订单：" + order.getOrderSn());
//            // 元转成分
//            int fee = 0;
//            BigDecimal actualPrice = order.getActualPrice();
//            fee = actualPrice.multiply(new BigDecimal(100)).intValue();
//            orderRequest.setTotalFee(fee);
//            orderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));
//
//            result = wxPayService.createOrder(orderRequest);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseUtil.fail(ORDER_PAY_FAIL, "订单不能支付");
//        }
//
//        if (orderService.updateWithOptimisticLocker(order) == 0) {
//            return ResponseUtil.updatedDateExpired();
//        }
//        return ResponseUtil.ok(result);
        return "";
    }
}
