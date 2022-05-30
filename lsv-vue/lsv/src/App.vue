<template>
  <v-app id="app">
    <!-- 导航栏 -->
    <TopNavBar></TopNavBar>
    <v-main>
      <router-view :key="$route.fullPath" />
    </v-main>
    <!-- 页脚 -->
    <Footer></Footer>
    <!-- 登录模态框 -->
    <LoginDialog></LoginDialog>
    <!-- 忘记密码模态框 -->
    <ForgetDialog></ForgetDialog>
  </v-app>
</template>

<script>
import TopNavBar from './components/layout/TopNavBar'
import Footer from './components/layout/Footer'
import LoginDialog from './components/dialog/LoginDialog'
import ForgetDialog from './components/dialog/ForgetDialog'

export default {
  created () {
    // 获取博客信息
    this.getVideoInfo()
    // 上传访客信息
    this.axios.post('/api/report')
  },
  components: {
    TopNavBar,
    Footer,
    LoginDialog,
    ForgetDialog
  },
  methods: {
    getVideoInfo () {
      this.axios.get('/api/').then(({ data }) => {
        this.$store.commit('checkVideoInfo', data.data)
      })
    }
  },
  computed: {
    blogInfo () {
      return this.$store.state.videoInfo
    },
    isMobile () {
      const flag = navigator.userAgent.match(
        /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i
      )
      return flag
    }
  }
}
</script>
