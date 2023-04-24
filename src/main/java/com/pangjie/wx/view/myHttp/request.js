// import { loginCode } from './requestUtil'
import url from "../url";
import ui from "../hint";

const app = getApp();
let requestArr = [], isRefreshing = false;//请求队列，是否正在刷新token

// 401 刷新token
function refreshToken (obj, method, content_type, resolve) {
    requestArr.push(() => { resolve(httpRequest(obj, method, content_type)) });//缓存请求到队列中
    if (!isRefreshing) {
        isRefreshing = true;
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
                    success(res3) {
                        if (res3.data.token !== undefined) {
                            wx.setStorageSync('pj_Token', res3.data.token);
                            // 重新请求队列
                            requestArr.map(MT => {
                                MT();
                            });
                            requestArr = [];//清空队列
                            // wx.startPullDownRefresh()
                        }
                        //解除正在刷新
                        isRefreshing = false;
                        wx.hideLoading();

                    }
                })
            },

        });
    }
}

function httpRequest (obj, method, content_type = 'application/json') {
    return new Promise((resolve, reject) => {
        if (obj.loading !== null && obj.loading!==undefined) {
            wx.showLoading({
                title: obj.loading !== '' ? obj.loading : '加载中...',
                mask: true,
            });
        }

        let token = wx.getStorageSync('pj_Token');//获取本地的token
        wx.request({
            url: obj.url,
            data: obj.data,
            method: method,
            header: { 'content-Type': content_type, 'Authorization': token, },
            success: (res) => {
                console.log(res);
                switch (res.statusCode) {//（根据实际情况判断）
                    case 200:
                        wx.hideLoading();
                        resolve(res);
                        break;
                    case 201:
                        wx.hideLoading();
                        resolve(res);
                        break;
                    case 204:
                        wx.hideLoading();
                        resolve(res);
                        break;
                    case 400:
                        if (res.data.message === '4000') {
                            wx.showToast({ title: "请稍后再试！", icon: 'error' });
                        } else {
                            wx.showToast({ title: "系统繁忙！", icon: 'error' });
                        }
                        wx.hideLoading();
                        // resolve(res);
                        break;
                    case 401://token过期，刷新token（根据实际情况判断）
                        refreshToken(obj, method, content_type, resolve);
                        break;
                    default:
                        wx.showToast({ title: "系统繁忙！", icon: 'error' });
                        wx.hideLoading();
                        reject();
                }
            },
            fail: function (err) {
                wx.showToast({ title: "系统繁忙！", icon: 'error' });
                wx.hideLoading();
                reject(err);
            }
        });
    })
}

function _get (obj) {
    return httpRequest(obj, 'GET', 'application/json');
}

function _post (obj) {
    return httpRequest(obj, 'POST', 'application/json');
}

function _put (obj) {
    return httpRequest(obj, 'PUT', 'application/json');
}

function _delete (obj) {
    return httpRequest(obj, 'DELETE', 'application/json');
}

module.exports = {
    getAction: _get,
    postAction: _post,
    putAction: _put,
    deleteAction: _delete,
}
