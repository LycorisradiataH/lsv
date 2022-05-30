<template>
  <div>
    <div class="banner user-cover">
      <h1 class="banner-title">发布视频</h1>
    </div>
    <v-card class="lsv-container">
      <div>
        <span class="info-title">视频信息</span>
      </div>
      <v-row class="info-wrapper">
        <v-col md="10" cols="12">
          <v-text-field
            v-model="videoInfo.videoName"
            label="视频名"
            placeholder="请输入视频名称"
          />
          <div style="margin-bottom: 15px">
            <v-btn style="padding-left: 0" raised text>视频分类</v-btn>
            <el-tag
              type="success"
              v-show="videoInfo.categoryName"
              style="margin: 0 1rem 0 0"
              :closable="true"
              @close="removeCategory"
            >
              {{ videoInfo.categoryName }}
            </el-tag>
            <!-- 分类选项 -->
            <el-popover
              placement="bottom-start"
              width="460"
              trigger="click"
              v-if="!videoInfo.categoryName"
            >
              <div class="popover-title">分类</div>
              <!-- 搜索框 -->
              <el-autocomplete
                style="width: 100%"
                v-model="categoryName"
                :fetch-suggestions="searchCategories"
                placeholder="请输入分类名搜索，enter可添加自定义分类"
                :trigger-on-focus="false"
                @keyup.enter.native="saveCategory"
                @select="handleSelectCategories"
              >
                <template slot-scope="{ item }">
                  <div>{{ item.categoryName }}</div>
                </template>
              </el-autocomplete>
              <!-- 分类 -->
              <div class="popover-container">
                <div
                  v-for="item of categoryList"
                  :key="item.id"
                  class="category-item"
                  @click="addCategory(item)"
                >
                  {{ item.categoryName }}
                </div>
              </div>
              <el-button type="success" plain slot="reference" size="small">
                添加分类
              </el-button>
            </el-popover>
          </div>
          <div style="margin-bottom: 15px">
            <v-btn style="padding-left: 0" raised text>视频标签</v-btn>
            <el-tag
              v-for="(item, index) of videoInfo.tagNameList"
              :key="index"
              style="margin: 0 1rem 0 0"
              :closable="true"
              @close="removeTag(item)"
            >
              {{ item }}
            </el-tag>
            <!-- 标签选项 -->
            <el-popover
              placement="bottom-start"
              width="460"
              trigger="click"
              v-if="videoInfo.tagNameList.length < 3"
            >
              <div class="popover-title">标签</div>
              <!-- 搜索框 -->
              <el-autocomplete
                style="width: 100%"
                v-model="tagName"
                :fetch-suggestions="searchTags"
                placeholder="请输入标签名搜索，enter可添加自定义标签"
                :trigger-on-focus="false"
                @keyup.enter.native="saveTag"
                @select="handleSelectTag"
              >
                <template slot-scope="{ item }">
                  <div>{{ item.tagName }}</div>
                </template>
              </el-autocomplete>
              <!-- 标签 -->
              <div class="popover-container">
                <div style="margin-bottom: 1rem">添加标签</div>
                <el-tag
                  v-for="(item, index) of tagList"
                  :key="index"
                  :class="tagClass(item)"
                  @click="addTag(item)"
                >
                  {{ item.tagName }}
                </el-tag>
              </div>
              <el-button type="primary" plain slot="reference" size="small">
                添加标签
              </el-button>
            </el-popover>
          </div>
          <div style="margin-bottom: 15px">
            <v-btn style="padding-left: 0; float: left" raised text>
              上传封面
            </v-btn>
            <el-upload
              class="upload-cover"
              drag
              action="/api/videos/images"
              :before-upload="beforeUpload"
              :on-success="uploadCover"
            >
              <i class="el-icon-upload" v-if="videoInfo.videoCover == ''" />
              <div class="el-upload__text" v-if="videoInfo.videoCover == ''">
                将文件拖到此处，或<em>点击上传</em>
              </div>
              <img
                v-else
                :src="videoInfo.videoCover"
                width="360px"
                height="180px"
              />
            </el-upload>
          </div>
          <v-file-input
            v-model="file"
            color="deep-purple accent-4"
            label="视频上传"
            placeholder="选择你需要上传的视频"
            prepend-icon="mdi-upload"
            append-outer-icon="mdi-send"
            outlined
            :show-size="1000"
            @click:append-outer="uploadVideo"
            @keydown.enter="uploadVideo"
          >
            <template v-slot:selection="{ index, text }">
              <v-chip
                v-if="index < 2"
                color="deep-purple accent-4"
                dark
                label
                small
              >
                {{ text }}
              </v-chip>
            </template>
          </v-file-input>
          <v-btn
            color="success"
            class="mt-5"
            style="margin-left: 10px; color: white"
            @click="saveVideo"
          >
            发布
          </v-btn>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import * as imageConversion from 'image-conversion'
export default {
  data: function () {
    return {
      videoInfo: {
        videoName: '',
        categoryName: '',
        tagNameList: [],
        videoCover: '',
        playId: ''
      },
      categoryList: [],
      categoryName: '',
      tagList: [],
      tagName: '',
      file: null
    }
  },
  created () {
    this.listCategories()
    this.listTags()
  },
  methods: {
    listCategories () {
      this.axios.get('/api/admin/categories/search').then(({ data }) => {
        this.categoryList = data.data
      })
    },
    listTags () {
      this.axios.get('/api/admin/tags/search').then(({ data }) => {
        this.tagList = data.data
      })
    },
    searchCategories (keywords, cb) {
      this.axios
        .get('/api/admin/categories/search', {
          params: {
            keywords: keywords
          }
        })
        .then(({ data }) => {
          cb(data.data)
        })
    },
    handleSelectCategories (item) {
      this.addCategory({
        categoryName: item.categoryName
      })
    },
    saveCategory () {
      if (this.categoryName.trim() !== '') {
        this.addCategory({
          categoryName: this.categoryName
        })
        this.categoryName = ''
      }
    },
    addCategory (item) {
      this.videoInfo.categoryName = item.categoryName
    },
    removeCategory () {
      this.videoInfo.categoryName = null
    },
    searchTags (keywords, cb) {
      this.axios
        .get('/api/admin/tags/search', {
          params: {
            keywords: keywords
          }
        })
        .then(({ data }) => {
          cb(data.data)
        })
    },
    handleSelectTag (item) {
      this.addTag({
        tagName: item.tagName
      })
    },
    saveTag () {
      if (this.tagName.trim() !== '') {
        this.addTag({
          tagName: this.tagName
        })
        this.tagName = ''
      }
    },
    addTag (item) {
      if (this.videoInfo.tagNameList.indexOf(item.tagName) === -1) {
        this.videoInfo.tagNameList.push(item.tagName)
      }
    },
    removeTag (item) {
      const index = this.videoInfo.tagNameList.indexOf(item)
      this.videoInfo.tagNameList.splice(index, 1)
    },
    uploadCover (response) {
      this.videoInfo.videoCover = response.data
    },
    beforeUpload (file) {
      return new Promise((resolve) => {
        if (file.size / 1024 < this.config.UPLOAD_SIZE) {
          resolve(file)
        }
        // 压缩到200KB,这里的200就是要压缩的大小,可自定义
        imageConversion
          .compressAccurately(file, this.config.UPLOAD_SIZE)
          .then((res) => {
            resolve(res)
          })
      })
    },
    uploadVideo () {
      console.log(this.file)
      if (this.file.type !== 'video/mp4') {
        this.$toast({ type: 'error', message: '该视频格式暂不支持' })
        this.file = null
      } else {
        var formData = new window.FormData()
        formData.append('file', this.file)
        this.axios
          .post('/api/videos/video', formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
          })
          .then(({ data }) => {
            if (data.status) {
              this.$toast({ type: 'success', message: '上传成功' })
              this.videoInfo.playId = data.data
            } else {
              this.$toast({ type: 'error', message: data.message })
            }
          })
      }
    },
    saveVideo () {
      this.axios.post('/api/videos', this.videoInfo).then(({ data }) => {
        if (data.status) {
          this.$toast({ type: 'success', message: '发布成功' })
          this.$router.push('/user')
        } else {
          this.$toast({ type: 'error', message: data.message })
        }
      })
    }
  },
  computed: {
    tagClass () {
      return function (item) {
        const index = this.videoInfo.tagNameList.indexOf(item.tagName)
        return index !== -1 ? 'tag-item-select' : 'tag-item'
      }
    }
  }
}
</script>

<style scoped>
.user-cover {
  background: url('http://www.static.hdiata.com/userInfo.jpg') center center /
    cover no-repeat;
}
.info-title {
  font-size: 1.25rem;
  font-weight: bold;
}
.info-wrapper {
  margin-top: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
}
#pick-avatar {
  outline: none;
}
.binding {
  display: flex;
  align-items: center;
}
.category-item {
  cursor: pointer;
  padding: 0.6rem 0.5rem;
}
.category-item:hover {
  background-color: #f0f9eb;
  color: #67c23a;
}
.tag-item {
  margin-right: 1rem;
  margin-bottom: 1rem;
  cursor: pointer;
}
.tag-item-select {
  margin-right: 1rem;
  margin-bottom: 1rem;
  cursor: not-allowed;
  color: #ccccd8 !important;
}
.popover-title {
  margin-bottom: 1rem;
  text-align: center;
}
.popover-container {
  margin-top: 1rem;
  height: 260px;
  overflow-y: auto;
}
</style>
