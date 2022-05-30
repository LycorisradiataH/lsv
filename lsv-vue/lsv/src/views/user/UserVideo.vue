<template>
  <div>
    <!-- 发布作者 -->
    <div class="banner videoList-cover">
      <h1 class="banner-title animated fadeInDown">{{ title }}</h1>
    </div>
    <div class="video-list-wrapper">
      <v-row>
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
      </v-row>
      <!-- 无限加载 -->
      <infinite-loading @infinite="infiniteHandler">
        <div slot="no-results" />
        <div slot="no-more" />
      </infinite-loading>
    </div>
  </div>
</template>

<script>
import dayjs from 'dayjs'

export default {
  data () {
    return {
      videoList: [],
      title: '发布的视频',
      current: 1
    }
  },

  created () {
    this.getUsername()
  },

  methods: {
    getUsername () {
      this.axios
        .get('/api/users/username/' + this.$route.params.userId)
        .then(({ data }) => {
          if (data.status) {
            document.title = data.data + document.title
            this.title = document.title
          }
        })
    },
    infiniteHandler ($state) {
      this.axios
        .get('/api/videos/condition/userId', {
          params: {
            userId: this.$route.params.userId,
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

<style scoped>
.videoList-cover {
  background: url('http://www.static.hdiata.com/category.jpg') center center /
    cover no-repeat;
}
@media (min-width: 760px) {
  .video-list-wrapper {
    max-width: 1106px;
    margin: 370px auto 1rem auto;
  }
  .video-item-card:hover {
    transition: all 0.3s;
    box-shadow: 0 4px 12px 12px rgba(7, 17, 27, 0.15);
  }
  .video-item-card:not(:hover) {
    transition: all 0.3s;
  }
  .video-item-card:hover .on-hover {
    transition: all 0.6s;
    transform: scale(1.1);
  }
  .video-item-card:not(:hover) .on-hover {
    transition: all 0.6s;
  }
  .video-item-info {
    line-height: 1.7;
    padding: 15px 15px 12px 18px;
    font-size: 15px;
  }
}
@media (max-width: 759px) {
  .video-list-wrapper {
    margin-top: 230px;
    padding: 0 12px;
  }
  .video-item-info {
    line-height: 1.7;
    padding: 15px 15px 12px 18px;
  }
}
.video-item-card {
  border-radius: 8px !important;
  box-shadow: 0 4px 8px 6px rgba(7, 17, 27, 0.06);
}
.video-item-card a {
  transition: all 0.3s;
}
.video-item-cover {
  height: 220px;
  overflow: hidden;
}
.video-item-card a:hover {
  color: #8e8cd8;
}
.tag-wrapper {
  padding: 10px 15px 10px 18px;
}
.tag-wrapper a {
  color: #fff !important;
}
.tag-btn {
  display: inline-block;
  font-size: 0.725rem;
  line-height: 22px;
  height: 22px;
  border-radius: 10px;
  padding: 0 12px !important;
  background: linear-gradient(to right, #bf4643 0%, #6c9d8f 100%);
  opacity: 0.6;
  margin-right: 0.5rem;
}
</style>
