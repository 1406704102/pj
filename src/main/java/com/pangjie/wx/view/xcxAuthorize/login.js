bindGetUserInfo: function (e) {
    let that = this
    if (e.detail.userInfo) {
        wx.showLoading({
            title: '登录中...'
        });

        console.log(e.detail.userInfo)
        wx.login({
            success: res => {
                let l = res;
                // 获取用户信息
                wx.getSetting({
                    success: res => {
                        if (res.authSetting['scope.userInfo']) {
                            // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
                            wx.getUserInfo({
                                success: res => {
                                    console.log(res)
                                    // console.log("用户的code:" + res.code);
                                    wx.request({
                                        url: url.getOpenid,// /api/WX/getJ2SR
                                        method: 'GET',
                                        data: {
                                            code: l.code,
                                            nickname: e.detail.userInfo.nickName,
                                            avatar: e.detail.userInfo.avatarUrl,
                                            sex: e.detail.userInfo.gender,
                                            encryptedData: res.encryptedData,
                                            iv: res.iv
                                        },
                                        success: res => {
                                            app.globalData.user = res.data

                                            wx.setStorage({
                                                key: 'userInfo',
                                                data: res.data
                                            })


                                            //商城登录-----------------------------------------------------
                                            if (e.detail.userInfo == undefined) {
                                                app.globalData.hasLogin = false;
                                                util.showErrorToast('微信登录失败');
                                                return;
                                            }

                                            // user.checkLogin().catch(() => {

                                            user.loginByWeixin(e.detail.userInfo).then(res => {
                                                app.globalData.hasLogin = true;
                                                // wx.showToast({
                                                //   title:'微信登录成功'
                                                // })

                                                // wx.navigateBack({
                                                //   delta: 1
                                                // })
                                            }).catch((err) => {
                                                app.globalData.hasLogin = false;
                                                util.showErrorToast('微信登录失败');
                                            });

                                            // });
                                            //-------------------------------------------------------------------------
                                            console.log(that.data.op)
                                            console.log(app.globalData.hasLogin)
                                            // if ()
                                            // wx.switchTab({
                                            //   url:'/pages/live/home/index'
                                            // })
                                            let pages = getCurrentPages();
                                            var prevPage = pages[pages.length - 2];
                                            try {
                                                prevPage.remindLive(res)
                                            } catch (e) {

                                            }
                                            try {
                                                prevPage.getLive(0)
                                            } catch (e) {

                                            }
                                            if (that.data.type !== 1) {
                                                const options = that.data.op;
                                                console.log(options)
                                                prevPage.onLoad(options)
                                                // wx.redirectTo({
                                                //   url: "/pages/active/specialActive/index?id=" + options.id + "&activeId=" + options.activeId + "&attTimes=" + options.attTimes + "&activeName=" + options.activeName + "&logo=" + options.logo
                                                // })
                                            }
                                            that.setData({
                                                modalName: "phone"
                                            })
                                            wx.hideLoading();
                                        }
                                    })
                                }
                            })
                        }
                    }
                })
            }
        });
        // wx.hideLoading()
        //授权成功后,通过改变 isHide 的值，让实现页面显示出来，把授权页面隐藏起来
        that.setData({
            isHide: false
        });
    } else {
        //用户按了拒绝按钮
        wx.showModal({
            title: '警告',
            content: '您点击了拒绝授权，将无法进入小程序，请授权之后再进入!!!',
            showCancel: false,
            confirmText: '返回授权',
            success: function (res) {
                // 用户没有授权成功，不需要改变 isHide 的值
                if (res.confirm) {
                    console.log('用户点击了“返回授权”');
                }
            }
        });
    }
}