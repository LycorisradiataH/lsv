<template>
  <div>
    <!-- 封面图 -->
    <div class="banner" :style="videoCover">
      <div class="video-info-container">
        <!-- 文章标题 -->
        <div class="video-title">{{ video.videoName }}</div>
        <div class="video-info">
          <div class="first-line">
            <!-- 发表时间 -->
            <span>
              <v-icon dark>mdi-clock-time-two-outline</v-icon>
              发表于 {{ video.gmtCreate | date }}
            </span>
            <span class="separator">|</span>
            <!-- 更新时间 -->
            <span>
              <v-icon dark>mdi-clock-time-five-outline</v-icon>
              更新于
              <template v-if="video.gmtModified">
                {{ video.gmtModified | date }}
              </template>
              <template v-else>
                {{ video.gmtCreate | date }}
              </template>
            </span>
          </div>
          <div class="second-line">
            <!-- 视频分类 -->
            <span class="video-category">
              <v-icon dark>mdi-view-grid</v-icon>
              <router-link :to="'/categories/' + video.categoryId">
                {{ video.categoryName }}
              </router-link>
            </span>
            <span class="separator">|</span>
            <!-- 点击量 -->
            <span>
              <v-icon dark>mdi-eye-plus</v-icon> 点击量: {{ video.viewsCount }}
            </span>
            <span class="separator">|</span>
            <!-- 评论量 -->
            <span>
              <v-icon dark>mdi-chat-plus</v-icon> 评论数: {{ commentCount }}
            </span>
          </div>
        </div>
      </div>
    </div>
    <!-- 内容 -->
    <v-row class="video-container">
      <v-col md="9" cols="12">
        <div style="margin-bottom: 10px">
          <vue-core-video-player
            :src="video.url"
            :autoplay="autoplay"
            :cover="video.videoCover"
          ></vue-core-video-player>
        </div>
        <v-card class="video-wrapper">
          <!-- 版权声明 -->
          <div class="aritcle-copyright">
            <div>
              <span>视频作者：</span>
              <router-link to="/">
                {{ video.nickname }}
              </router-link>
            </div>
            <div>
              <span>视频链接：</span>
              <a :href="videoHref" target="_blank">{{ videoHref }} </a>
            </div>
            <div>
              <span>版权声明：</span>本网站所有视频除特别声明外，均采用
              <a
                href="https://creativecommons.org/licenses/by-nc-sa/4.0/"
                target="_blank"
              >
                CC BY-NC-SA 4.0
              </a>
              许可协议。转载请注明视频出处。
            </div>
          </div>
          <!-- 转发 -->
          <div class="video-operation">
            <div class="tag-container">
              <router-link
                v-for="item of video.tagDTOList"
                :key="item.id"
                to=""
              >
                {{ item.tagName }}
              </router-link>
            </div>
            <share style="margin-left: auto" :config="config" />
          </div>
          <!-- 点赞 -->
          <div class="article-reward">
            <!-- 点赞按钮 -->
            <a :class="isLike" @click="like">
              <v-icon size="14" color="#fff">mdi-thumb-up</v-icon> 点赞
              <span v-show="video.likeCount > 0">{{ video.likeCount }}</span>
            </a>
            <v-dialog v-model="deleteDialog" width="500">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  style="margin-left: 5px"
                  color="red lighten-2"
                  dark
                  v-if="loginUserId === authorId"
                  v-bind="attrs"
                  v-on="on"
                >
                  删除视频
                </v-btn>
              </template>
              <v-card>
                <v-card-title class="text-h5 lighten-2">
                  删除视频
                </v-card-title>
                <v-card-text>
                  是否确认删除视频，一旦删除无法回退！
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="primary" text @click="removeVideo">
                    确定
                  </v-btn>
                  <v-btn color="error" text @click="deleteDialog = false">
                    取消
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </div>
          <!-- 推荐视频 -->
          <div
            class="recommend-container"
            v-if="video.recommendVideoList.length"
          >
            <div class="recommend-title">
              <v-icon size="20" color="#4c4948">mdi-thumb-up</v-icon> 相关推荐
            </div>
            <div class="recommend-list">
              <div
                class="recommend-item"
                v-for="item of video.recommendVideoList"
                :key="item.id"
              >
                <router-link :to="'/videos/' + item.id">
                  <img class="recommend-cover" :src="item.videoCover" />
                  <div class="recommend-info">
                    <div class="recommend-date">
                      <v-icon dark>mdi-clock-time-two-outline</v-icon>
                      {{ item.gmtCreate | date }}
                    </div>
                    <div>{{ item.videoName }}</div>
                  </div>
                </router-link>
              </div>
            </div>
          </div>
          <!-- 分割线 -->
          <hr />
          <!-- 评论 -->
          <comment
            :type="commentType"
            :authorId="authorId"
            @getCommentCount="getCommentCount"
          />
        </v-card>
      </v-col>
      <!-- 侧边功能 -->
      <v-col md="3" cols="12" class="d-md-block d-none">
        <div style="position: sticky; top: 20px">
          <!-- 作者 -->
          <router-link :to="`/user/videos/${video.authorId}`">
            <v-card class="right-container">
              <div class="right-title">
                <v-avatar size="60">
                  <img alt="Avatar" :src="video.avatar" />
                </v-avatar>
                <v-list-item color="rgba(0, 0, 0, .4)">
                  <v-list-item-content>
                    <v-list-item-title style="color: #ef5350" class="text-h6">
                      {{ video.nickname }}
                    </v-list-item-title>
                    <v-list-item-subtitle
                      class="text-truncate"
                      style="max-width: 160px"
                    >
                      {{ video.intro }}
                    </v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
                <span style="margin-left: 10px; color: "> </span>
              </div>
            </v-card>
          </router-link>
          <!-- 最新视频 -->
          <v-card class="right-container" style="margin-top: 20px">
            <div class="right-title">
              <v-icon>mdi-new-box</v-icon>
              <span style="margin-left: 10px">最新视频</span>
            </div>
            <div class="video-list">
              <div
                class="video-item"
                v-for="item of video.newestVideoList"
                :key="item.id"
              >
                <router-link :to="'/videos/' + item.id" class="content-cover">
                  <img :src="item.videoCover" />
                </router-link>
                <div class="content">
                  <div class="content-title">
                    <router-link :to="'/videos/' + item.id">
                      {{ item.videoName }}
                    </router-link>
                  </div>
                  <div class="content-time">{{ item.gmtCreate | date }}</div>
                </div>
              </div>
            </div>
          </v-card>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import Comment from '../../components/Comment'

export default {
  components: {
    Comment
  },
  created () {
    this.getvideo()
  },
  data: function () {
    return {
      config: {
        sites: ['qzone', 'wechat', 'weibo', 'qq']
      },
      imgList: [],
      video: {},
      commentType: 1,
      authorId: '',
      deleteDialog: false,
      videoHref: window.location.href,
      commentCount: 0,
      autoplay: false
    }
  },
  methods: {
    getvideo () {
      // 查询视频
      this.axios.get('/api' + this.$route.path).then(({ data }) => {
        document.title = data.data.videoName
        this.video = data.data
        this.authorId = data.data.authorId
      })
    },
    like () {
      // 判断登录
      if (!this.$store.state.userId) {
        this.$store.state.loginDialog = true
        return false
      }
      // 发送请求
      this.axios
        .post('/api/videos/' + this.video.id + '/like')
        .then(({ data }) => {
          if (data.status) {
            // 判断是否点赞
            if (this.$store.state.videoLikeSet.indexOf(this.video.id) !== -1) {
              this.$set(this.video, 'likeCount', this.video.likeCount - 1)
            } else {
              this.$set(this.video, 'likeCount', this.video.likeCount + 1)
            }
            this.$store.commit('videoLike', this.video.id)
          }
        })
    },
    removeVideo () {
      var param = { data: [this.video.id] }
      this.axios.delete('/api/admin/videos', param).then(({ data }) => {
        if (data.status) {
          this.$router.push('/')
          this.$toast({ type: 'success', message: '删除成功' })
        }
      })
    },
    getCommentCount (count) {
      this.commentCount = count
    }
  },
  computed: {
    videoInfo () {
      return this.$store.state.videoInfo
    },
    videoCover () {
      return (
        'background: url(' +
        this.video.videoCover +
        ') center center / cover no-repeat'
      )
    },
    isLike () {
      var videoLikeSet = this.$store.state.videoLikeSet
      return videoLikeSet.indexOf(this.video.id) !== -1
        ? 'like-btn-active'
        : 'like-btn'
    },
    loginUserId () {
      return this.$store.state.userId
    },
    isFull () {
      return function (id) {
        return id ? 'post full' : 'post'
      }
    }
  }
}
</script>

<style scoped>
.banner:before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}
.video-info {
  font-size: 14px;
  line-height: 1.9;
  display: inline-block;
}
.article-reward {
  margin-top: 5rem;
  display: flex;
  justify-content: center;
  align-items: center;
}
@media (min-width: 760px) {
  .banner {
    color: #eee !important;
  }
  .video-info span {
    font-size: 95%;
  }
  .video-info-container {
    position: absolute;
    bottom: 6.25rem;
    padding: 0 8%;
    width: 100%;
    text-align: center;
  }
  .second-line,
  .third-line {
    display: inline;
  }
  .video-title {
    font-size: 35px;
    margin: 20px 0 8px;
  }
  .pagination-post {
    display: flex;
  }
  .post {
    width: 50%;
  }
  .recommend-item {
    position: relative;
    display: inline-block;
    overflow: hidden;
    margin: 3px;
    width: calc(33.333% - 6px);
    height: 200px;
    background: #000;
    vertical-align: bottom;
  }
}
@media (max-width: 759px) {
  .banner {
    color: #eee !important;
    height: 360px;
  }
  .video-info span {
    font-size: 90%;
  }
  .separator:first-child {
    display: none;
  }
  .blog-container {
    margin: 322px 5px 0 5px;
  }
  .video-info-container {
    position: absolute;
    bottom: 1.3rem;
    padding: 0 5%;
    width: 100%;
    color: #eee;
    text-align: left;
  }
  .video-title {
    font-size: 1.5rem;
    margin-bottom: 0.4rem;
  }
  .post {
    width: 100%;
  }
  .pagination-post {
    display: block;
  }
  .recommend-item {
    position: relative;
    display: inline-block;
    overflow: hidden;
    margin: 3px;
    width: calc(100% - 4px);
    height: 150px;
    margin: 2px;
    background: #000;
    vertical-align: bottom;
  }
}
.video-operation {
  display: flex;
  align-items: center;
}
.video-category a {
  color: #fff !important;
}
.tag-container a {
  display: inline-block;
  margin: 0.5rem 0.5rem 0.5rem 0;
  padding: 0 0.75rem;
  width: fit-content;
  border: 1px solid #49b1f5;
  border-radius: 1rem;
  color: #49b1f5 !important;
  font-size: 12px;
  line-height: 2;
}
.tag-container a:hover {
  color: #fff !important;
  background: #49b1f5;
  transition: all 0.5s;
}
.aritcle-copyright {
  position: relative;
  margin-top: 40px;
  margin-bottom: 10px;
  font-size: 0.875rem;
  line-height: 2;
  padding: 0.625rem 1rem;
  border: 1px solid #eee;
}
.aritcle-copyright span {
  color: #49b1f5;
  font-weight: bold;
}
.aritcle-copyright a {
  text-decoration: underline !important;
  color: #99a9bf !important;
}
.aritcle-copyright:before {
  position: absolute;
  top: 0.7rem;
  right: 0.7rem;
  width: 1rem;
  height: 1rem;
  border-radius: 1rem;
  background: #49b1f5;
  content: '';
}
.aritcle-copyright:after {
  position: absolute;
  top: 0.95rem;
  right: 0.95rem;
  width: 0.5rem;
  height: 0.5rem;
  border-radius: 0.5em;
  background: #fff;
  content: '';
}
.video-reward {
  margin-top: 5rem;
  display: flex;
  justify-content: center;
  align-items: center;
}
.reward-btn {
  position: relative;
  display: inline-block;
  width: 100px;
  background: #49b1f5;
  margin: 0 1rem;
  color: #fff !important;
  text-align: center;
  line-height: 36px;
  font-size: 0.875rem;
}
.reward-btn:hover .reward-main {
  display: block;
}
.reward-main {
  display: none;
  position: absolute;
  bottom: 40px;
  left: 0;
  margin: 0;
  padding: 0 0 15px;
  width: 100%;
}
.reward-all {
  display: inline-block;
  margin: 0 0 0 -110px;
  padding: 20px 10px 8px !important;
  width: 320px;
  border-radius: 4px;
  background: #f5f5f5;
}
.reward-all:before {
  position: absolute;
  bottom: -10px;
  left: 0;
  width: 100%;
  height: 20px;
  content: '';
}
.reward-all:after {
  content: '';
  position: absolute;
  right: 0;
  bottom: 2px;
  left: 0;
  margin: 0 auto;
  width: 0;
  height: 0;
  border-top: 13px solid #f5f5f5;
  border-right: 13px solid transparent;
  border-left: 13px solid transparent;
}
.reward-item {
  display: inline-block;
  padding: 0 8px;
  list-style-type: none;
}
.reward-img {
  width: 130px;
  height: 130px;
  display: block;
}
.reward-desc {
  margin: -5px 0;
  color: #858585;
  text-align: center;
}
.like-btn {
  display: inline-block;
  width: 100px;
  background: #969696;
  color: #fff !important;
  text-align: center;
  line-height: 36px;
  font-size: 0.875rem;
}
.like-btn-active {
  display: inline-block;
  width: 100px;
  background: #ec7259;
  color: #fff !important;
  text-align: center;
  line-height: 36px;
  font-size: 0.875rem;
}
.pagination-post {
  margin-top: 40px;
  overflow: hidden;
  width: 100%;
  background: #000;
}
.post {
  position: relative;
  height: 150px;
  overflow: hidden;
}
.post-info {
  position: absolute;
  top: 50%;
  padding: 20px 40px;
  width: 100%;
  transform: translate(0, -50%);
  line-height: 2;
  font-size: 14px;
}
.post-cover {
  position: absolute;
  width: 100%;
  height: 100%;
  opacity: 0.4;
  transition: all 0.6s;
  object-fit: cover;
}
.post a {
  position: relative;
  display: block;
  overflow: hidden;
  height: 150px;
}
.post:hover .post-cover {
  opacity: 0.8;
  transform: scale(1.1);
}
.label {
  font-size: 90%;
  color: #eee;
}
.post-title {
  font-weight: 500;
  color: #fff;
}
hr {
  position: relative;
  margin: 40px auto;
  border: 2px dashed #d2ebfd;
  width: calc(100% - 4px);
}
.full {
  width: 100% !important;
}
.right-container {
  padding: 20px 24px;
  font-size: 14px;
}
.right-title {
  display: flex;
  align-items: center;
  line-height: 2;
  font-size: 16.8px;
  margin-bottom: 6px;
}
.recommend-container {
  margin-top: 40px;
}
.recommend-title {
  font-size: 20px;
  line-height: 2;
  font-weight: bold;
  margin-bottom: 5px;
}
.recommend-cover {
  width: 100%;
  height: 100%;
  opacity: 0.4;
  transition: all 0.6s;
  object-fit: cover;
}
.recommend-info {
  line-height: 2;
  color: #fff;
  position: absolute;
  top: 50%;
  padding: 0 20px;
  width: 100%;
  transform: translate(0, -50%);
  text-align: center;
  font-size: 14px;
}
.recommend-date {
  font-size: 90%;
}
.recommend-item:hover .recommend-cover {
  opacity: 0.8;
  transform: scale(1.1);
}
.video-item {
  display: flex;
  align-items: center;
  padding: 6px 0;
}
.video-item:first-child {
  padding-top: 0;
}
.video-item:last-child {
  padding-bottom: 0;
}
.video-item:not(:last-child) {
  border-bottom: 1px dashed #f5f5f5;
}
.video-item img {
  width: 100%;
  height: 100%;
  transition: all 0.6s;
  object-fit: cover;
}
.video-item img:hover {
  transform: scale(1.1);
}
.content {
  flex: 1;
  padding-left: 10px;
  word-break: break-all;
  display: -webkit-box;
  overflow: hidden;
  -webkit-box-orient: vertical;
}
.content-cover {
  width: 58.8px;
  height: 58.8px;
  overflow: hidden;
}
.content-title a {
  transition: all 0.2s;
  font-size: 95%;
}
.content-title a:hover {
  color: #2ba1d1;
}
.content-time {
  color: #858585;
  font-size: 85%;
  line-height: 2;
}
</style>

<style lang="scss">
pre.hljs {
  padding: 12px 2px 12px 40px !important;
  border-radius: 5px !important;
  position: relative;
  font-size: 14px !important;
  line-height: 22px !important;
  overflow: hidden !important;
  &:hover .copy-btn {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  code {
    display: block !important;
    margin: 0 10px !important;
    overflow-x: auto !important;
    &::-webkit-scrollbar {
      z-index: 11;
      width: 6px;
    }
    &::-webkit-scrollbar:horizontal {
      height: 6px;
    }
    &::-webkit-scrollbar-thumb {
      border-radius: 5px;
      width: 6px;
      background: #666;
    }
    &::-webkit-scrollbar-corner,
    &::-webkit-scrollbar-track {
      background: #1e1e1e;
    }
    &::-webkit-scrollbar-track-piece {
      background: #1e1e1e;
      width: 6px;
    }
  }
  .line-numbers-rows {
    position: absolute;
    pointer-events: none;
    top: 12px;
    bottom: 12px;
    left: 0;
    font-size: 100%;
    width: 40px;
    text-align: center;
    letter-spacing: -1px;
    border-right: 1px solid rgba(0, 0, 0, 0.66);
    user-select: none;
    counter-reset: linenumber;
    span {
      pointer-events: none;
      display: block;
      counter-increment: linenumber;
      &:before {
        content: counter(linenumber);
        color: #999;
        display: block;
        text-align: center;
      }
    }
  }
  b.name {
    position: absolute;
    top: 7px;
    right: 45px;
    z-index: 1;
    color: #999;
    pointer-events: none;
  }
  .copy-btn {
    position: absolute;
    top: 6px;
    right: 6px;
    z-index: 1;
    color: #ccc;
    background-color: #525252;
    border-radius: 6px;
    display: none;
    font-size: 14px;
    width: 32px;
    height: 24px;
    outline: none;
  }
}
</style>
