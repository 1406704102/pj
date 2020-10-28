import Vue from 'vue'
import wx from 'weixin-js-sdk'

export function wxShare(shareData,that) {
  return new Promise(async function (resolve, reject) {
    try {
      let isWechat = navigator.userAgent.indexOf('MicroMessenger') > -1
      if (!isWechat) {
        return resolve('您目前所处的并不是微信内置浏览器')
      }
      //设置默认的分享标题、描述、网页、图片，以及分享成功后的回调
      let defaultData = {
        title: '默认标题！',
        content: '默认描述',
        url: 'https://www.xxxx.com',
        logo: 'https://www.xxxx.com/xxxxxxx.jpg',
        success: function (res) {
        }
      }
      //shareDate是你重置的分享标题等等
      let data = {...defaultData, ...shareData}
      console.log(data)
      //等待后台返回签名
      let res = await act(that,data.url)
      //后台返回成功后，配置微信的API
      let newRest = Object.assign({
        debug: false,
        jsApiList: ['onMenuShareAppMessage', 'onMenuShareTimeline']
      }, {
        appId: res.data.appId, // 必填，公众号的唯一标识
        timestamp: res.data.timestamp, // 必填，生成签名的时间戳
        nonceStr: res.data.nonceStr, // 必填，生成签名的随机串
        signature: res.data.signature,// 必填，签名
      })
      wx.config(newRest)
      //处理验证成功后的信息
      wx.ready(function () {
        wx.onMenuShareTimeline({ //分享到朋友圈
          title: data.title,
          desc: data.content,
          link: data.url,
          imgUrl: data.logo,
          dataUrl: '',
          success: data.success,
          cancel: function () {
          }
        })
        wx.onMenuShareAppMessage({ //分享给朋友
          title: data.title,
          desc: data.content,
          link: data.url,
          imgUrl: data.logo,
          dataUrl: '',
          success: data.success,
          cancel: function () {
          }
        })
        wx.onMenuShareQQ({ //分享到QQ
          title: data.title,
          desc: data.content,
          link: data.url,
          imgUrl: data.logo,
          dataUrl: '',
          success: data.success,
          cancel: function () {
          }
        })
      })
    } catch (error) {
      reject(error) //处理验证失败后的结果
    }
  })
}

//异步接口获取签名
function act(that,url) {
  return new Promise((resolve, reject) => {
    that.$axios.get('https://dowell.xmay.cc/jhcl/WX/getSignature', {
      params: {
        url: url
      }
    }).then(res => {
      resolve(res)
    }, err => {
      reject(err)
    })
  })
}
