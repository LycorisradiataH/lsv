import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: (resolve) => require(['../views/home/Home.vue'], resolve),
    meta: {
      title: 'lsv'
    }
  },
  {
    path: '/search',
    component: (resolve) =>
      require(['../views/video/VideoSearch.vue'], resolve),
    meta: {
      title: '的搜索结果'
    }
  },
  {
    path: '/videos/:videoId',
    component: (resolve) => require(['../views/video/Video.vue'], resolve)
  },
  {
    path: '/categories',
    component: (resolve) =>
      require(['../views/category/Category.vue'], resolve),
    meta: {
      title: '分类'
    }
  },
  {
    path: '/categories/:categoryId',
    component: (resolve) => require(['../views/video/VideoList.vue'], resolve)
  },
  {
    path: '/user',
    component: (resolve) => require(['../views/user/User.vue'], resolve),
    meta: {
      title: '个人中心'
    }
  },
  {
    path: '/publish/videos',
    component: (resolve) =>
      require(['../views/video/PublishVideo.vue'], resolve),
    meta: {
      title: '发布视频'
    }
  },
  {
    path: '/user/videos/:userId',
    component: (resolve) => require(['../views/user/UserVideo.vue'], resolve),
    meta: {
      title: '发布的视频'
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
