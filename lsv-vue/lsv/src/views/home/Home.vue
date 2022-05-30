<template>
  <div>
    <!-- banner -->
    <div class="home-banner">
      <div class="banner-container"></div>
    </div>
    <!-- 主页 -->
    <v-row class="home-container">
      <!-- 轮播图 -->
      <v-col md="9" cols="12">
        <v-card
          class="animated zoomIn mt-5"
          style="line-height: 2; border-radius: 12px"
        >
          <v-carousel
            cycle
            interval="3000"
            style="border-radius: 12px"
            height="434.65"
            hide-delimiter-background
            show-arrows-on-hover
          >
            <v-carousel-item
              v-for="(item, i) in videoInfo.photoList"
              :key="i"
              :src="item"
            ></v-carousel-item>
          </v-carousel>
        </v-card>
      </v-col>
      <!-- 点击量前7视频 -->
      <v-col md="3" cols="12" class="d-md-block d-none">
        <div class="lsv-wrapper">
          <v-card class="animated zoomIn lsv-card mt-5">
            <v-list style="padding: 0">
              <v-list-item style="padding-left: 1px">
                <v-list-item-icon style="margin-right: 10px">
                  <v-icon color="#FF3D00">mdi-play-box</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title class="text-h6">
                    点击量排行榜
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <v-divider></v-divider>
            <v-list nav dense shaped>
              <v-list-item-group>
                <v-list-item
                  v-for="(item, i) in videoInfo.videoRankDTOList"
                  :key="i"
                  :to="`/videos/${item.id}`"
                >
                  <font
                    :color="
                      i === 0
                        ? 'red'
                        : i === 1
                        ? '#FF6D00'
                        : i === 2
                        ? '#FFEA00'
                        : '#757575'
                    "
                    v-text="i + 1"
                    style="padding-right: 10px"
                  ></font>
                  <v-img
                    :class="i === 0 ? '' : 'd-none'"
                    style="margin-right: 5px"
                    :src="item.videoCover"
                    max-height="63.9"
                    max-width="102.5"
                  ></v-img>
                  <v-list-item-content>
                    <v-list-item-title
                      v-text="item.videoName"
                      text-truncate
                    ></v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-card>
        </div>
      </v-col>
      <!-- 视频列表 -->
      <v-col
        v-for="(video, i) in videoList"
        :key="i"
        cols="3"
        style="margin-top: 10px"
      >
        <v-card :to="`/videos/${video.id}`">
          <v-img
            :src="video.videoCover"
            class="white--text align-end"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="200px"
          >
            <v-card-title v-text="video.videoName"></v-card-title>
          </v-img>
          <v-card-actions>
            <v-btn disabled icon>
              <v-icon>mdi-play-circle-outline</v-icon>
            </v-btn>
            <span
              class="subheading mr-2"
              v-text="video.viewCount === null ? '0' : video.viewCount"
            ></span>
            <v-btn disabled icon>
              <v-icon>mdi-thumb-up-outline</v-icon>
            </v-btn>
            <span
              class="subheading mr-2"
              v-text="video.likeCount === null ? '0' : video.likeCount"
            ></span>
            <v-spacer></v-spacer>
            <v-btn disabled icon>
              <v-icon>mdi-clock-time-three-outline</v-icon>
            </v-btn>
            <span class="subheading mr-2" v-text="video.duration"></span>
          </v-card-actions>
        </v-card>
      </v-col>
      <!-- 无限加载 -->
      <infinite-loading @infinite="infiniteHandler">
        <div slot="no-more" />
      </infinite-loading>
    </v-row>
  </div>
</template>

<script>
import dayjs from 'dayjs'

export default {
  data () {
    return {
      categoryList: [],
      videoList: [],
      current: 1
    }
  },
  created () {
    this.init()
    this.listCategories()
  },
  computed: {
    videoInfo () {
      return this.$store.state.videoInfo
    },
    isShowSocial () {
      return function (social) {
        return this.videoInfo.websiteConfig.socialUrlList.indexOf(social) !== -1
      }
    }
  },
  methods: {
    // 初始化
    init () {
      document.title = this.blogInfo.websiteConfig.websiteName
        ? this.blogInfo.websiteConfig.websiteName
        : 'lsv'
    },
    listCategories () {
      this.axios.get('/api/categories').then(({ data }) => {
        if (data.status) {
          this.categoryList = data.data
        }
      })
    },
    infiniteHandler ($state) {
      this.axios
        .get('/api/videos', {
          params: {
            current: this.current
          }
        })
        .then(({ data }) => {
          if (data.data.length) {
            // 处理点击量和点赞量
            data.data.forEach((item) => {
              if (item.viewCount >= 10000) {
                item.viewCount = (item.viewCount / 10000).toFixed(1) + '万'
              }
              if (item.likeCount >= 10000) {
                item.likeCount = (item.likeCount / 10000).toFixed(1) + '万'
              }
              let second = item.duration
              let minute = (second / 60) | 0
              second %= 60
              const hour = (minute / 60) | 0
              minute %= 60
              item.duration = dayjs()
                .hour(hour)
                .minute(minute)
                .second(second)
                .format('HH:mm:ss')
            })
            this.videoList.push(...data.data)
            this.current++
            $state.loaded()
          } else {
            $state.complete()
          }
        })
    }
  }
}
</script>

<style lang="stylus">
.typed-cursor
  opacity: 1
  animation: blink 0.7s infinite
@keyframes blink
  0%
    opacity: 1
  50%
    opacity: 0
  100%
    opacity: 1
</style>

<style scoped>
.home-banner {
  position: absolute;
  top: -60px;
  left: 0;
  right: 0;
  height: 33vh;
  background-attachment: fixed;
  background: url('http://www.static.hdiata.com/home.jpg') center center / cover
    no-repeat;
  text-align: center;
  color: #fff !important;
  animation: header-effect 1s;
}
.banner-container {
  margin-top: 43vh;
  line-height: 1.5;
  color: #eee;
}
.card-info-social {
  line-height: 40px;
  text-align: center;
  margin: 6px 0 -6px;
}
.card-info-social a {
  font-size: 1.5rem;
}
@media (min-width: 760px) {
  .home-container {
    max-width: 1200px;
    margin: calc(33vh - 48px) auto 28px auto;
    padding: 0 5px;
  }
}
@media (max-width: 759px) {
  .home-container {
    width: 100%;
    margin: calc(33vh - 66px) auto 0 auto;
  }
}
.lsv-wrapper {
  position: sticky;
  top: 10px;
}
.lsv-card {
  line-height: 2;
  padding: 1.25rem 1.5rem;
}
</style>
