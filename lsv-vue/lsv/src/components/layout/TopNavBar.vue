<template>
  <v-app-bar app :class="navClass" hide-on-scroll flat height="60">
    <!-- 电脑导航栏 -->
    <div class="d-md-block d-none nav-container">
      <div class="float-left video-title">
        <router-link to="/">
          {{ videoInfo.websiteConfig.websiteAuthor }}
        </router-link>
      </div>
      <div class="float-right nav-title">
        <div v-if="this.$route.path !== '/search'" class="menus-item">
          <v-text-field
            v-model="searchContent"
            append-outer-icon="mdi-magnify"
            dense
            :dark="isDark"
            :color="searchColor"
            outlined
            clear-icon="mdi-close-circle"
            clearable
            label="Search"
            placeholder="搜索视频名称"
            @click:append-outer="sendContent"
            @click:clear="clearContent"
            @keydown.enter="sendContent"
          ></v-text-field>
        </div>
        <div class="menus-item">
          <router-link class="menu-btn" to="/">
            <v-icon :color="iconColor">mdi-home</v-icon>
            <span>首页</span>
          </router-link>
        </div>
        <!-- <div class="menus-item">
          <router-link class="menu-btn" to="/message">
            <v-icon :color="iconColor">mdi-chat</v-icon>
            <span>私信</span>
          </router-link>
        </div> -->
        <div class="menus-item">
          <router-link class="menu-btn" to="/categories">
            <v-icon :color="iconColor">mdi-sort-variant</v-icon>
            <span>分类</span>
          </router-link>
        </div>
        <!-- <div class="menus-item">
          <router-link class="menu-btn" to="/message">
            <v-icon :color="iconColor">mdi-information-variant</v-icon>
            <span>关于作者</span>
          </router-link>
        </div> -->
        <!-- <div class="menus-item">
          <router-link class="menu-btn" to="/message">
            <v-icon :color="iconColor">mdi-forum</v-icon>
            <span>讨论区</span>
          </router-link>
        </div> -->
        <div class="menus-item">
          <a
            class="menu-btn"
            v-if="!this.$store.state.avatar"
            @click="openLogin"
          >
            <v-icon :color="iconColor">mdi-account-key</v-icon>
            <span>登录</span>
          </a>
          <template v-else>
            <img
              class="user-avatar"
              :src="this.$store.state.avatar"
              height="30"
              width="30"
            />
            <ul class="menus-submenu">
              <li>
                <router-link to="/user" class="name text-center">
                  <div class="text-truncate">
                    {{ this.$store.state.nickname }}
                  </div>
                </router-link>
              </li>
              <li>
                <router-link to="/user">
                  <v-icon>mdi-account-cog</v-icon>
                  <span>个人中心</span>
                  <v-icon class="right">mdi-arrow-right-bottom</v-icon>
                </router-link>
              </li>
              <li>
                <router-link to="/publish/videos">
                  <v-icon>mdi-upload-outline</v-icon>
                  <span>发布视频</span>
                  <v-icon class="right">mdi-arrow-right-bottom</v-icon>
                </router-link>
              </li>
              <li>
                <router-link :to="`/user/videos/${this.$store.state.userId}`">
                  <v-icon>mdi-video-account</v-icon>
                  <span>查看我发布的视频</span>
                  <v-icon class="right">mdi-arrow-right-bottom</v-icon>
                </router-link>
              </li>
              <v-divider class="divider"></v-divider>
              <li>
                <a @click="logout" class="exit">
                  <v-icon>mdi-export</v-icon>
                  <span>退出登录</span>
                </a>
              </li>
            </ul>
          </template>
        </div>
      </div>
    </div>
  </v-app-bar>
</template>

<script>
export default {
  mounted () {
    window.addEventListener('scroll', this.scroll)
  },
  data: function () {
    return {
      navClass: '',
      iconColor: 'white',
      searchContent: '',
      isDark: true,
      searchColor: '#E0E0E0'
    }
  },
  created () {},
  methods: {
    scroll () {
      const that = this
      const scrollTop =
        window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop
      that.scrollTop = scrollTop
      if (that.scrollTop > 60) {
        that.navClass = 'nav-fixed'
        that.iconColor = 'black'
        that.isDark = false
        that.searchColor = '#EF6C00'
      } else {
        that.navClass = 'nav'
        that.iconColor = 'white'
        that.isDark = true
        that.searchColor = '#E0E0E0'
      }
    },
    sendContent () {
      this.$router.push({
        path: '/search',
        query: {
          keywords: this.searchContent
        }
      })
    },
    clearContent () {
      this.searchContent = ''
    },
    openLogin () {
      this.$store.state.loginDialog = true
    },
    logout () {
      // 如果在个人中心则跳回上一页
      if (
        this.$route.path === '/user' ||
        this.$route.path === '/videos/publish'
      ) {
        this.$router.push('/')
      }
      this.axios.get('/api/logout').then(({ data }) => {
        if (data.status) {
          this.$store.commit('logout')
          this.$toast({ type: 'success', message: '注销成功' })
        } else {
          this.$toast({ type: 'error', message: data.message })
        }
      })
    }
  },
  computed: {
    avatar () {
      return this.$store.state.avatar
    },
    videoInfo () {
      return this.$store.state.videoInfo
    }
  }
}
</script>

<style scoped>
.v-input {
  height: 40px;
  width: 500px;
  margin-right: 200px;
  border-radius: 10px;
}
.mdi-home::before,
.mdi-information-variant::before,
.mdi-account-key::before {
  margin-top: -7px !important;
}
.mdi-chat::before {
  margin-top: -3px !important;
}
i {
  margin-right: 4px;
}
ul {
  list-style: none;
  border-radius: 10px;
}
.nav {
  background: rgba(0, 0, 0, 0) !important;
}
.nav a {
  color: #eee !important;
}
.nav .menu-btn {
  text-shadow: 0.05rem 0.05rem 0.1rem rgba(0, 0, 0, 0.3);
}
.nav .video-title a {
  text-shadow: 0.1rem 0.1rem 0.2rem rgba(0, 0, 0, 0.15);
}
.theme--light.nav-fixed {
  background: rgba(255, 255, 255, 0.8) !important;
  box-shadow: 0 5px 6px -5px rgba(133, 133, 133, 0.6);
}
.theme--dark.nav-fixed {
  background: rgba(18, 18, 18, 0.8) !important;
}
.theme--dark.nav-fixed a {
  color: rgba(255, 255, 255, 0.8) !important;
}
.theme--light.nav-fixed a {
  color: #4c4948 !important;
}
.nav-fixed .menus-item a,
.nav-fixed .video-title a {
  text-shadow: none;
}
.nav-container {
  font-size: 14px;
  width: 100%;
  height: 100%;
}
.nav-mobile-container {
  width: 100%;
  display: flex;
  align-items: center;
}
.video-title,
.nav-title {
  display: flex;
  align-items: center;
  height: 100%;
}
.video-title a {
  font-size: 18px;
  font-weight: bold;
}
.menus-item {
  position: relative;
  display: inline-block;
  margin: 0 0 0 0.875rem;
}
.menus-item a {
  transition: all 0.2s;
}
.nav-fixed .menu-btn:hover {
  color: #95999b !important;
  border-radius: 5px;
}
.menu-btn:hover:after {
  width: 100%;
  border-radius: 5px;
}
.menus-item a:after {
  position: absolute;
  bottom: -5px;
  left: 0;
  z-index: -1;
  width: 0;
  height: 3px;
  background-color: #80c8f8;
  content: '';
  transition: all 0.3s ease-in-out;
}
.user-avatar {
  cursor: pointer;
  border-radius: 50%;
}
.menus-item:hover .menus-submenu {
  display: block;
  border-radius: 10px;
}
.menus-submenu {
  position: absolute;
  display: none;
  right: 0;
  width: 300px;
  margin-top: 8px;
  box-shadow: 0 5px 20px -4px rgba(0, 0, 0, 0.5);
  background-color: #fff;
  animation: submenu 0.3s 0.1s ease both;
}
.menus-submenu:before {
  position: absolute;
  top: -8px;
  left: 0;
  width: 100%;
  height: 20px;
  content: '';
}
.menus-submenu a {
  line-height: 2;
  color: #4c4948 !important;
  text-shadow: none;
  display: block;
  margin-left: 10px;
  margin-right: 10px;
  padding: 6px 10px;
  border-radius: 10px;
}
.menus-submenu a span {
  padding-left: 7px;
}
.menus-submenu .right {
  float: right;
}
.menus-submenu .name div {
  font-size: 24px;
}
.v-divider {
  margin-left: 10px !important;
  margin-top: 5px;
  margin-bottom: 5px;
  max-width: 90% !important;
}
.menus-submenu .exit {
  margin-bottom: 15px;
}
.menus-submenu a:hover {
  background: #95999b;
  border-radius: 10px;
}
.menus-submenu .name:hover {
  background-color: white !important;
}
@keyframes submenu {
  0% {
    opacity: 0;
    filter: alpha(opacity=0);
    transform: translateY(10px);
  }
  100% {
    opacity: 1;
    filter: none;
    transform: translateY(0);
  }
}
</style>
