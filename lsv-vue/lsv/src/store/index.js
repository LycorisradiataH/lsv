import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loginDialog: false,
    forgetDialog: false,
    emailFlag: false,
    drawer: false,
    loginUrl: '',
    userId: null,
    avatar: null,
    nickname: null,
    intro: null,
    loginType: null,
    email: null,
    videoLikeSet: [],
    commentLikeSet: [],
    videoInfo: {}
  },
  mutations: {
    login (state, user) {
      state.userId = user.userInfoId
      state.avatar = user.avatar
      state.nickname = user.nickname
      state.intro = user.intro
      state.videoLikeSet = user.videoLikeSet ? user.videoLikeSet : []
      state.commentLikeSet = user.commentLikeSet ? user.commentLikeSet : []
      state.email = user.email
      state.loginType = user.loginType
    },
    logout (state) {
      state.userId = null
      state.avatar = null
      state.nickname = null
      state.intro = null
      state.videoLikeSet = []
      state.commentLikeSet = []
      state.email = null
      state.loginType = null
    },
    saveLoginUrl (state, url) {
      state.loginUrl = url
    },
    saveEmail (state, email) {
      state.email = email
    },
    updateUserInfo (state, user) {
      state.nickname = user.nickname
      state.intro = user.intro
    },
    savePageInfo (state, pageList) {
      state.pageList = pageList
    },
    updateAvatar (state, avatar) {
      state.avatar = avatar
    },
    checkVideoInfo (state, videoInfo) {
      state.videoInfo = videoInfo
    },
    closeModel (state) {
      state.loginDialog = false
      state.emailFlag = false
    },
    videoLike (state, videoId) {
      var videoLikeSet = state.videoLikeSet
      if (videoLikeSet.indexOf(videoId) !== -1) {
        videoLikeSet.splice(videoLikeSet.indexOf(videoId), 1)
      } else {
        videoLikeSet.push(videoId)
      }
    },
    commentLike (state, commentId) {
      var commentLikeSet = state.commentLikeSet
      if (commentLikeSet.indexOf(commentId) !== -1) {
        commentLikeSet.splice(commentLikeSet.indexOf(commentId), 1)
      } else {
        commentLikeSet.push(commentId)
      }
    }
  },
  actions: {},
  modules: {},
  plugins: [
    createPersistedState({
      storage: window.sessionStorage
    })
  ]
})
