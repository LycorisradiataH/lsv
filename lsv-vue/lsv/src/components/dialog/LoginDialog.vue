<template>
  <v-dialog v-model="loginDialog" :fullscreen="isMobile" max-width="460">
    <v-card class="login-container" style="border-radius: 5px">
      <v-btn
        class="mx-2 float-right"
        fab
        x-small
        outlined
        text
        depressed
        @click="loginDialog = false"
      >
        <v-icon> mdi-close </v-icon>
      </v-btn>
      <div class="login-wrapper">
        <v-toolbar dense flat>
          <v-toolbar-items>
            <v-btn
              text
              block
              :color="isLogin ? 'success' : 'black'"
              @click="isLogin = true"
            >
              登录
            </v-btn>
            <v-divider vertical style="margin: auto 7px"></v-divider>
            <v-btn
              text
              block
              :color="!isLogin ? 'success' : 'black'"
              @click="isLogin = false"
            >
              注册
            </v-btn>
          </v-toolbar-items>
        </v-toolbar>
        <template v-if="isLogin">
          <!-- 用户名 -->
          <v-text-field
            v-model="username"
            label="邮箱号"
            placeholder="请输入您的邮箱号"
            clearable
            @keyup.enter="login"
          />
          <div class="mt-7 send-wrapper">
            <!-- 密码 -->
            <v-text-field
              v-model="password"
              label="密码"
              placeholder="请输入您的密码"
              @keyup.enter="login"
              :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
              :type="show ? 'text' : 'password'"
              @click:append="show = !show"
            />
            <v-btn
              style="color: white"
              color="#F50057"
              small
              depressed
              @click="openForget"
            >
              忘记密码？
            </v-btn>
          </div>
          <!-- 按钮 -->
          <v-btn
            class="mt-7"
            block
            color="blue"
            style="color: #fff"
            @click="login"
          >
            登录
          </v-btn>
        </template>
        <template v-else>
          <!-- 用户名 -->
          <v-text-field
            v-model="username"
            class="mt-7"
            label="邮箱号"
            placeholder="请输入您的邮箱号"
            clearable
            @keyup.enter="register"
          />

          <!-- 密码 -->
          <v-text-field
            v-model="password"
            class="mt-7"
            label="密码"
            placeholder="请输入您的密码"
            @keyup.enter="register"
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            @click:append="show = !show"
          />
          <div class="mt-7 send-wrapper">
            <!-- 验证码 -->
            <v-text-field
              v-model="code"
              maxlength="6"
              label="验证码"
              placeholder="请输入6位验证码"
              @keyup.enter="register"
            />
            <v-btn
              @click="sendCode"
              :disabled="flag"
              color="#FFA726"
              depressed
              small
            >
              {{ codeMsg }}
            </v-btn>
          </div>
          <!-- 按钮 -->
          <v-btn
            class="mt-7"
            block
            color="#E64A19"
            style="color: #fff"
            @click="register"
          >
            注册
          </v-btn>
        </template>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  data: function () {
    return {
      isLogin: true,
      username: '',
      password: '',
      code: '',
      codeMsg: '发送验证码',
      time: 60,
      flag: true,
      show: false
    }
  },
  computed: {
    loginDialog: {
      set (value) {
        this.$store.state.loginDialog = value
      },
      get () {
        return this.$store.state.loginDialog
      }
    },
    isMobile () {
      const clientWidth = document.documentElement.clientWidth
      if (clientWidth > 960) {
        return false
      }
      return true
    },
    socialLoginList () {
      return this.$store.state.videoInfo.websiteConfig.socialLoginList
    },
    showLogin () {
      return function (type) {
        return this.socialLoginList.indexOf(type) !== -1
      }
    }
  },
  watch: {
    username (value) {
      var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (reg.test(value)) {
        this.flag = false
      } else {
        this.flag = true
      }
    },
    isLogin: function () {
      this.username = ''
      this.password = ''
      this.code = ''
    }
  },
  methods: {
    openForget () {
      this.$store.state.loginDialog = false
      this.$store.state.forgetDialog = true
    },
    sendCode () {
      const that = this
      // eslint-disable-next-line no-undef
      var captcha = new TencentCaptcha(this.config.TENCENT_CAPTCHA, function (
        res
      ) {
        if (res.ret === 0) {
          // 发送邮件
          that.countDown()
          that.axios
            .get('/api/users/code', {
              params: { username: that.username }
            })
            .then(({ data }) => {
              if (data.status) {
                that.$toast({ type: 'success', message: '发送成功' })
              } else {
                that.$toast({ type: 'error', message: data.message })
              }
            })
        }
      })
      // 显示验证码
      captcha.show()
    },
    countDown () {
      this.flag = true
      this.timer = setInterval(() => {
        this.time--
        this.codeMsg = this.time + 's'
        if (this.time <= 0) {
          clearInterval(this.timer)
          this.codeMsg = '发送'
          this.time = 60
          this.flag = false
        }
      }, 1000)
    },
    register () {
      var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (!reg.test(this.username)) {
        this.$toast({ type: 'error', message: '邮箱格式不正确' })
        return false
      }
      if (this.code.trim().length !== 6) {
        this.$toast({ type: 'error', message: '请输入6位验证码' })
        return false
      }
      if (this.password.trim().length < 6) {
        this.$toast({ type: 'error', message: '密码不能少于6位' })
        return false
      }
      const user = {
        username: this.username,
        password: this.password,
        code: this.code
      }
      this.axios.post('/api/register', user).then(({ data }) => {
        if (data.status) {
          const param = new URLSearchParams()
          param.append('username', user.username)
          param.append('password', user.password)
          this.axios.post('/api/login', param).then(({ data }) => {
            this.username = ''
            this.password = ''
            this.$store.commit('login', data.data)
            this.$store.commit('closeModel')
          })
          this.$toast({ type: 'success', message: '注册成功' })
        } else {
          this.$toast({ type: 'error', message: data.message })
        }
      })
    },
    login () {
      var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (!reg.test(this.username)) {
        this.$toast({ type: 'error', message: '邮箱格式不正确' })
        return false
      }
      if (this.password.trim().length === 0) {
        this.$toast({ type: 'error', message: '密码不能为空' })
        return false
      }
      const that = this
      // eslint-disable-next-line no-undef
      var captcha = new TencentCaptcha(this.config.TENCENT_CAPTCHA, function (
        res
      ) {
        if (res.ret === 0) {
          // 发送登录请求
          const param = new URLSearchParams()
          param.append('username', that.username)
          param.append('password', that.password)
          that.axios.post('/api/login', param).then(({ data }) => {
            if (data.status) {
              that.username = ''
              that.password = ''
              that.$store.commit('login', data.data)
              that.$store.commit('closeModel')
              that.$toast({ type: 'success', message: '登录成功' })
            } else {
              that.$toast({ type: 'error', message: data.message })
            }
          })
        }
      })
      // 显示验证码
      captcha.show()
    }
  }
}
</script>

<style scoped>
.theme--light.v-btn.v-btn--outlined.v-btn--text {
  border-color: rgba(0, 0, 0, 0);
}
.social-login-title {
  margin-top: 1.5rem;
  color: #b5b5b5;
  font-size: 0.75rem;
  text-align: center;
}
.social-login-title::before {
  content: '';
  display: inline-block;
  background-color: #d8d8d8;
  width: 60px;
  height: 1px;
  margin: 0 12px;
  vertical-align: middle;
}
.social-login-title::after {
  content: '';
  display: inline-block;
  background-color: #d8d8d8;
  width: 60px;
  height: 1px;
  margin: 0 12px;
  vertical-align: middle;
}
.social-login-wrapper {
  margin-top: 1rem;
  font-size: 2rem;
  text-align: center;
}
.social-login-wrapper a {
  text-decoration: none;
}
</style>
