<template>
    <div>

    </div>
</template>

<script>
    export default {
        name: "home",
        created(){
            this.wxShare({
                title: '自定义的标题',
                content: '自定义的内容',
                logo:'自定义的logo 网络地址',
                url: window.location.href //当前网页的地址
            },this);
        }
    }
</script>

<style scoped>

</style>