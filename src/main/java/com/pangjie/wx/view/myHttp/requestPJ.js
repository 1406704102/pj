let isRefreshing = true; // 请求锁
let pendings = []; // 请求列表
const pathArr = ['pages/buy-goods/index']; // 不需要登录的路径
const baseUrl = require("./url");
const url = require("./url");
const ui = require("./hint");

// 基础路径

function request(obj) {
    const token = wx.getStorageSync('pj_Token')
    const pages = getCurrentPages();
    // const router = pages[pages.length - 1]['route']; // 当前路由
    // if (pathArr.includes(router)) options.needLogin = false; // 当前路径是否需要登录
    return new Promise((resolve, reject) => {
        // 需要登录 但是 token不存在 跳转登录
        // if (!token && options.needLogin) {
        //     wx.redirectTo({
        //         url: '/pages/my/login/login?type=0'
        //     })
        //     return
        // }
        wx.showNavigationBarLoading();
        ui.showLoading(obj.loading ? obj.loading : '加载中...');
        // 请求主体
        wx.request({
            url: obj.url,
            header: {
                Authorization: token
            },
            method:obj.method,
            data:obj.data,
            success(res) {
                console.log(res);
                let code = res.statusCode
                if (code === 200) {
                    resolve(res)
                } else if (code === 401) {
                    if (isRefreshing) {
                        updateToken();
                        isRefreshing = false;
                        pendings.push(() => {
                            resolve(request(obj))
                        })
                    }
                } else {
                    resolve(res);
                }
            },
            fail(err) {
                reject(err);
            },
            complete() {
                wx.hideNavigationBarLoading();
                wx.hideLoading();
            }
        })
    })
}

// 刷新token
function updateToken() {
    wx.getStorage({
        key: 'userInfo',
        success(res) {
            wx.request({
                url: url.getToken,
                method: 'POST',
                data: {
                    id: res.data.id,
                    openid: res.data.openid,
                },
                success(res) {
                    let code = res.statusCode
                    console.log(code === 200)
                    if (code === 200) {
                        wx.setStorageSync('pj_Token', res.data.token);
                        pendings.map((callback) => {
                            callback();
                        })
                        isRefreshing = true;
                    } else {
                        toLogin();
                    }
                }
            })

        }, fail(res) {
            toLogin();
        }
    })

}

// 前往登录页面 清空状态
function toLogin() {
    wx.showToast({
        title: '登录失效,请重新登录',
        icon: "none",
        success: () => {
            setTimeout(() => {
                wx.navigateTo({
                    url: '/pages/my/login/login?type=0'
                })
                pendings = [];
                isRefreshing = true;
            }, 1200);
        }
    })
}

module.exports = {
    request
};
