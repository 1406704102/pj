util.request(api.OrderPrepay, {
    orderId: orderId
}, 'POST').then(function (res) {
    if (res.errno === 0) {
        const payParam = res.data;
        console.log("支付过程开始");
        wx.requestPayment({
            'timeStamp': payParam.timeStamp,
            'nonceStr': payParam.nonceStr,
            'package': payParam.packageValue,
            'signType': payParam.signType,
            'paySign': payParam.paySign,
            'success': function (res) {
                console.log("支付过程成功");
                    wx.redirectTo({
                        url: '/pages/goods/payResult/payResult?status=1&orderId=' + orderId
                    });

            },
            'fail': function (res) {
                console.log("支付过程失败");
                wx.redirectTo({
                    url: '/pages/goods/payResult/payResult?status=0&orderId=' + orderId
                });
            },
            'complete': function (res) {
                console.log("支付过程结束")
            }
        });

    } else {
        wx.redirectTo({
            url: '/pages/goods/payResult/payResult?status=0&orderId=' + orderId
        });
    }

});