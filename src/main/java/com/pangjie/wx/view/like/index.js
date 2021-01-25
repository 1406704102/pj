// pages/quarterly//email/detail/index.js
var util = require('../../../../shopConfig/util.js');
var url = require('../../../../utils/url.js');
var util2 = require('../../../../utils/util.js');
Page({

    /**
     * 页面的初始数据
     */
    data: {
        isLike: false,
        defaultLike: false,
        timers: {},
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
    },
    getLike() {
        let that = this
        wx.getStorage({
            key: 'userInfo',
            success(res) {
                wx.request({
                    url: url.openEmailUserStar,
                    method: 'GET',
                    data: {
                        userId: res.data.id,
                        emailId: that.data.email.id,
                        enable: '1'
                    }, success(res) {
                        var like = res.data.content;
                        console.log(like)
                        if (like.length !== 0) {
                            that.setData({
                                isLike: true,
                                defaultLike: true
                            })
                        }
                    }
                })
            }
        })
    },
    doLike() {
        let that = this;
        var data = that.data;
        clearTimeout(data.timers);
        wx.getStorage({
            key: 'userInfo',
            success(res) {
                console.log('userInfo')
                let l = '0';
                if (data.isLike) {
                    that.setData({
                        'email.likeNum': data.email.likeNum - 1,
                        isLike: !data.isLike
                    });
                } else {
                    that.setData({
                        'email.likeNum': data.email.likeNum + 1,
                        isLike: !data.isLike
                    });
                    l = '1';
                }
                if (data.isLike !== data.defaultLike) {
                    that.setData({
                        'timers': setTimeout(() => {
                            wx.request({
                                url: url.openEmailUserStar,
                                method: 'POST',
                                data: {
                                    emailId: data.email.id,
                                    userId: res.data.id,
                                    enable: l
                                }, success(res) {
                                    console.log(res);
                                }
                            });

                        }, 1000)
                    });
                }
            }
        })


    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})
