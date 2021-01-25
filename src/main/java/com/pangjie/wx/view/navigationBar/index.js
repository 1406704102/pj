// pages/slowlists/index.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    isFixed: false,
    idTop: 0,

    typeArr: [{
      name: '树洞',
      english: '· secret ·',
    }, {
      name: '慢邮件',
      english: '· Snail Mail ·'
    }],
    typeIndex: 0,
    isTypeNew: true,

    tagArr: ['时间', '点赞'],
    tagIndex: 0,

    isPopup: false,
    isHideLoadMore: true,
    lists: [{
      id: '1',
      time: '已抵达，可阅读',
      name: '我的收信',
      isAccept: 1,
      isSend: 0,
      isNew: 1,
      type: 1,
      content: '关于人际交往中的小烦恼我们只是认识时间不算太久，虽然对我更关于人际交往中的小烦恼我们只是认识时间不算太久，虽然对我更',
      author: '一二三四',
      avatar: 'https://ossweb-img.qq.com/images/lol/web201310/skin/big21001.jpg'
    }, {
      id: '2',
      time: '已送达',
      name: '发出的信件',
      isAccept: 1,
      isSend: 1,
      isNew: 0,
      type: 0,
      content: '关于人际交往中的小烦恼我们只是认识时间不算太久，虽然对我更关于人际交往中的小烦恼我们只是认识时间不算太久，虽然对我更',
      author: '拜伦维斯',
      avatar: 'https://ossweb-img.qq.com/images/lol/web201310/skin/big21001.jpg'
    }, {
      id: '3',
      time: '已抵达，可阅读',
      name: '我的收信',
      isAccept: 1,
      isSend: 0,
      isnew: 1,
      type: 1,
      content: '关于人际交往中的小烦恼我们只是认识时间不算太久，虽然对我更关于人际交往中的小烦恼我们只是认识时间不算太久，虽然对我更',
      author: '一二三四',
      avatar: 'https://ossweb-img.qq.com/images/lol/web201310/skin/big21001.jpg'
    }, {
      id: '4',
      time: '19:22 后送达',
      name: '我的收信',
      isAccept: 0,
      isSend: 0,
      isnew: 1,
      type: 1,
      content: '关于人际交往中的小烦恼我们只是认识时间不算太久，虽然对我更关于人际交往中的小烦恼我们只是认识时间不算太久，虽然对我更',
      author: '一二三四',
      avatar: 'https://ossweb-img.qq.com/images/lol/web201310/skin/big21001.jpg'
    }]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getIdTop()
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
    this.onReachBottom()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  // 监听屏幕滚动 判断上下滚动 bindscroll
  onPageScroll: function (ev) {
    if (ev.scrollTop >= this.data.idTop - this.data.CustomBar) {
      if (!this.data.isFixed) {
        this.setData({
          isFixed: true
        })
        wx.setNavigationBarColor({
          frontColor: '#000000',
          backgroundColor: ''
        })
      }
    } else {
      if (this.data.isFixed) {
        this.setData({
          isFixed: false
        })
        wx.setNavigationBarColor({
          frontColor: '#ffffff',
          backgroundColor: ''
        })
      }
    }
  },

  //加载更多
  onReachBottom: function () {
    this.setData({
      isHideLoadMore: false
    })
    let lists = this.data.lists
    this.data.lists.map(item => {
      lists.push(item)
    })
    setTimeout(() => {
      this.setData({
        isHideLoadMore: true,
        lists: lists
      })
    }, 1000)
  },

  closePopup: function () {
    this.setData({
      isPopup: false
    })
  },

  /**
   * 设置
   * @param {*} e 
   */
  changType: function (e) {
    this.setData({
      typeIndex: e.currentTarget.dataset.index
    })
  },

  /**
   * 设置
   * @param {*} e 
   */
  changTag: function (e) {
    this.setData({
      tagIndex: e.currentTarget.dataset.index
    })
  },

  goBack: function () {
    wx.navigateBack({
      delta: 1,
    })
  },

  /**
   * 获取滚动高度
   */
  getIdTop: function () {
    const query = wx.createSelectorQuery()
    query.select('#zmui-id').boundingClientRect()
    query.selectViewport().scrollOffset()
    query.exec(res => {
      let miss = res[1].scrollTop + res[0].top
      this.setData({
        idTop: miss
      })
    })
  },

  /**
   * 跳转页面
   * @param {*} e 
   */
  goreadmail: function (e) {
    if (!e.currentTarget.dataset.accept) {
      this.setData({
        isPopup: true
      })
    } else {
      if (this.data.typeIndex) {
        wx.navigateTo({
          url: '/pages/slowread/slowread?id=' + e.currentTarget.dataset.id
        })
      } else {
        if (e.currentTarget.dataset.type) {
          wx.navigateTo({
            url: '/pages/slowto/slowto?id=' + e.currentTarget.dataset.id
          })
        } else {
          wx.navigateTo({
            url: '/pages/slowfrom/slowfrom?id=' + e.currentTarget.dataset.id
          })
        }
      }
    } 
  },

  /**
   * 跳转写邮件
   */
  goclowwrite: function () {
    wx.navigateTo({
      url: '/pages/slowwrite/slowwrite'
    })
  }
})