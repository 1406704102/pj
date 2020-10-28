//是用钩子函数拦截路由
router.beforeEach((to, from, next) => {
    var u = navigator.userAgent;
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if (isiOS && to.path !== location.pathname) {
        // 此处不可使用location.replace
        location.assign(to.fullPath)
    } else {
        next()
    }
    // //不要对 Auth Index 路由进行拦截，不进入 WxAuth 路由就拿不到微信返回的授权 code
    // if (to.name === 'Auth' || to.name === 'Index' || to.name === 'Question1'|| to.name === 'Question2'|| to.name === 'Question3'|| to.name === 'Question4'|| to.name === 'Question5'|| to.name === 'Question6'
    // || to.name ==='Result'||to.name === 'Barrage'||to.name ==='Detail') {
    //   next()
    //   return
    // }

    if (to.name === 'Auth' || to.name === 'table'){
        next()
        return
    }

    let wxUserInfo = localStorage.getItem('wxUserInfo2')
    if (!wxUserInfo) {
        //保存当前路由地址，授权后还会跳到此地址
        sessionStorage.setItem('wxRedirectUrl', to.fullPath)
        //请求微信授权,并跳转到 /Auth 路由
        window.location.href = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx13921510363f0cdd&redirect_uri=https://dowell.xmay.cc/auth&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect`
    } else {
        next()
    }
})