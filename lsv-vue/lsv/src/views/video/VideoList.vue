<template>
  <div>
    <!-- 标签或分类名 -->
    <div class="banner videoList-cover">
      <h1 class="banner-title animated fadeInDown">{{ title }} - {{ name }}</h1>
    </div>
    <div class="video-list-wrapper">
      <v-row>
        <v-col md="4" cols="12" v-for="item of videoList" :key="item.id">
          <!-- 视频 -->
          <v-card class="animated zoomIn video-item-card">
            <div class="video-item-cover">
              <router-link :to="'/videos/' + item.id">
                <!-- 缩略图 -->
                <v-img
                  class="on-hover"
                  width="100%"
                  height="100%"
                  :src="item.videoCover"
                />
              </router-link>
            </div>
            <div class="video-item-info">
              <!-- 视频名称 -->
              <div>
                <router-link :to="'/videos/' + item.id">
                  {{ item.videoName }}
                </router-link>
              </div>
              <div style="margin-top: 0.375rem">
                <!-- 发表时间 -->
                <v-icon size="20">mdi-clock-outline</v-icon>
                {{ item.gmtCreate | date }}
                <!-- 视频分类 -->
                <router-link
                  :to="'/categories/' + item.categoryId"
                  class="float-right"
                >
                  <v-icon>mdi-bookmark</v-icon>{{ item.categoryName }}
                </router-link>
              </div>
            </div>
            <!-- 分割线 -->
            <v-divider></v-divider>
            <!-- 视频标签 -->
            <div class="tag-wrapper">
              <router-link
                to=""
                class="tag-btn"
                v-for="tag of item.tagDTOList"
                :key="tag.id"
              >
                {{ tag.tagName }}
              </router-link>
            </div>
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
export default {
  created () {
    const path = this.$route.path
    if (path.indexOf('/categories') !== -1) {
      this.title = '分类'
    } else {
      this.title = '标签'
    }
  },
  data: function () {
    return {
      current: 1,
      size: 10,
      videoList: [],
      name: '',
      title: ''
    }
  },
  methods: {
    infiniteHandler ($state) {
      this.axios
        .get('/api/videos/condition', {
          params: {
            categoryId: this.$route.params.categoryId,
            tagId: this.$route.params.tagId,
            current: this.current
          }
        })
        .then(({ data }) => {
          if (data.data.name) {
            this.name = data.data.name
            document.title = this.title + ' - ' + this.name
          }
          if (data.data.videoPreviewDTOList.length) {
            this.current++
            this.videoList.push(...data.data.videoPreviewDTOList)
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
  background: url('http://www.static.hdiata.com/articleList.jpg') center center /
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
