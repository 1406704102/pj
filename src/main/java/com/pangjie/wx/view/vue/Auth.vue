<template>
    <div>

    </div>
</template>

<script>
    export default {
        name: "auth",
        async created(){
            // 如果连接中有微信返回的 code，则用此 code 调用后端接口，向微信服务器请求用户信息
            // 如果不是从微信重定向过来的，没有带着微信的 code，则直接进入首页
            if (this.$route.query.code) {
                let res = await this.$axios.get('https://dowell.xmay.cc/jhcl/WX/login?code=' + this.$route.query.code);
                console.log("auth-data", res.data);
                localStorage.setItem('wxUserInfo2', JSON.stringify(res.data));
                localStorage.setItem('id', res.data.id);

                let redirectUrl = sessionStorage.getItem('wxRedirectUrl')
                this.$router.replace(redirectUrl)
            } else {
                this.$router.replace('/')
            }
        }
    }
</script>

<style scoped>

</style>
