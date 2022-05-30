<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 视频状态 -->
    <div class="video-status-menu">
      <span>状态</span>
      <span @click="changeStatus('all')" :class="isActive('all')">全部</span>
      <span @click="changeStatus('delete')" :class="isActive('delete')">
        回收站
      </span>
    </div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
        v-if="isDelete == 0"
        type="danger"
        size="small"
        icon="el-icon-delete"
        :disabled="videoIdList.length == 0"
        @click="updateIsDelete = true"
      >
        批量删除
      </el-button>
      <el-button
        v-else
        type="danger"
        size="small"
        icon="el-icon-delete"
        :disabled="videoIdList.length == 0"
        @click="remove = true"
      >
        批量删除
      </el-button>
      <!-- 条件筛选 -->
      <div style="margin-left: auto">
        <!-- 分类 -->
        <el-select
          clearable
          size="small"
          v-model="categoryId"
          filterable
          placeholder="请选择分类"
          style="margin-right: 1rem"
        >
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.categoryName"
            :value="item.id"
          />
        </el-select>
        <!-- 标签 -->
        <el-select
          clearable
          size="small"
          v-model="tagId"
          filterable
          placeholder="请选择标签"
          style="margin-right: 1rem"
        >
          <el-option
            v-for="item in tagList"
            :key="item.id"
            :label="item.tagName"
            :value="item.id"
          />
        </el-select>
        <!-- 视频名 -->
        <el-input
          clearable
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入视频名"
          style="width: 200px"
          @keyup.enter.native="searchVideos"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="searchVideos"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      :data="videoList"
      @selection-change="selectionChange"
      v-loading="loading"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <!-- 文章修改时间 -->
      <el-table-column
        prop="videoCover"
        label="视频封面"
        width="180"
        align="center"
      >
        <template slot-scope="scope">
          <el-image
            class="video-cover"
            :src="
              scope.row.videoCover
                ? scope.row.videoCover
                : 'https://static.talkxj.com/articles/c5cc2b2561bd0e3060a500198a4ad37d.png'
            "
          />
          <i
            v-if="scope.row.status == 1"
            class="iconfont el-icon-mygongkai video-status-icon"
          />
          <i
            v-if="scope.row.status == 2"
            class="iconfont el-icon-mymima video-status-icon"
          />
          <i
            v-if="scope.row.status == 3"
            class="iconfont el-icon-mycaogaoxiang video-status-icon"
          />
        </template>
      </el-table-column>
      <!-- 视频标题 -->
      <el-table-column
        prop="videoName"
        label="标题"
        width="200"
        align="center"
      />
      <!-- 视频分类 -->
      <el-table-column
        prop="categoryName"
        label="分类"
        width="100"
        align="center"
      />
      <!-- 视频标签 -->
      <el-table-column
        prop="tagDTOList"
        label="标签"
        width="150"
        align="center"
      >
        <template slot-scope="scope">
          <el-tag
            v-for="item of scope.row.tagDTOList"
            :key="item.tagId"
            style="margin-right: 0.2rem; margin-top: 0.2rem"
          >
            {{ item.tagName }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 视频浏览量 -->
      <el-table-column
        prop="viewCount"
        label="浏览量"
        width="70"
        align="center"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.viewCount">
            {{ scope.row.viewCount }}
          </span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <!-- 视频点赞量 -->
      <el-table-column
        prop="likeCount"
        label="点赞量"
        width="70"
        align="center"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.likeCount">
            {{ scope.row.likeCount }}
          </span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <!-- 视频作者 -->
      <el-table-column prop="type" label="作者" width="80" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.author">
            {{ scope.row.author }}
          </span>
          <span v-else>未知</span>
        </template>
      </el-table-column>
      <!-- 视频发表时间 -->
      <el-table-column
        prop="gmtCreate"
        label="发表时间"
        width="128"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right: 5px" />
          {{ scope.row.gmtCreate | date }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="190">
        <template slot-scope="scope">
          <!-- <el-button
            type="success"
            size="mini"
            @click="editVideo(scope.row.id)"
            v-if="scope.row.isDelete == 0"
          >
            查看
          </el-button> -->
          <el-button
            type="primary"
            size="mini"
            @click="openModel(scope.row)"
            v-if="scope.row.isDelete == 0"
          >
            编辑
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left: 10px"
            @confirm="updateVideoDelete(scope.row.id)"
            v-if="scope.row.isDelete == 0"
          >
            <el-button size="mini" type="danger" slot="reference">
              删除
            </el-button>
          </el-popconfirm>
          <el-popconfirm
            title="确定恢复吗？"
            v-if="scope.row.isDelete == 1"
            @confirm="updateVideoDelete(scope.row.id)"
          >
            <el-button size="mini" type="success" slot="reference">
              恢复
            </el-button>
          </el-popconfirm>
          <el-popconfirm
            style="margin-left: 10px"
            v-if="scope.row.isDelete == 1"
            title="确定彻底删除吗？"
            @confirm="deleteVideos(scope.row.id)"
          >
            <el-button size="mini" type="danger" slot="reference">
              删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      class="pagination-container"
      background
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="current"
      :page-size="size"
      :total="count"
      :page-sizes="[10, 20]"
      layout="total, sizes, prev, pager, next, jumper"
    />
    <!-- 添加编辑对话框 -->
    <el-dialog :visible.sync="edit" width="30%">
      <div class="dialog-title-container" slot="title" ref="videoTitle" />
      <el-form label-width="80px" size="medium" :model="videoForm">
        <!-- 视频名字 -->
        <el-form-item label="视频名">
          <el-input v-model="videoForm.videoName" style="width: 220px" />
        </el-form-item>
        <!-- 视频分类 -->
        <el-form-item label="视频分类">
          <el-tag
            type="success"
            v-show="videoForm.categoryName"
            style="margin: 0 1rem 0 0"
            :closable="true"
            @close="removeCategory"
          >
            {{ videoForm.categoryName }}
          </el-tag>
          <!-- 分类选项 -->
          <el-popover
            placement="bottom-start"
            width="460"
            trigger="click"
            v-if="!videoForm.categoryName"
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
        </el-form-item>
        <!-- 视频标签 -->
        <el-form-item label="视频标签">
          <el-tag
            v-for="(item, index) of videoForm.tagNameList"
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
            v-if="videoForm.tagNameList.length < 3"
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
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="closeModel">取 消</el-button>
        <el-button type="primary" @click="updateVideo"> 确 定 </el-button>
      </div>
    </el-dialog>
    <!-- 批量逻辑删除对话框 -->
    <el-dialog :visible.sync="updateIsDelete" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color: #ff9900" />提示
      </div>
      <div style="font-size: 1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="updateIsDelete = false">取 消</el-button>
        <el-button type="primary" @click="updateVideoDelete(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 批量彻底删除对话框 -->
    <el-dialog :visible.sync="remove" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color: #ff9900" />提示
      </div>
      <div style="font-size: 1rem">是否彻底删除选中项？</div>
      <div slot="footer">
        <el-button @click="remove = false">取 消</el-button>
        <el-button type="primary" @click="deleteVideos(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
export default {
  created() {
    this.listVideos();
    this.listCategories();
    this.listTags();
  },
  data: function () {
    return {
      edit: false,
      videoForm: {
        id: null,
        videoName: "",
        categoryName: "",
        tagNameList: [],
      },
      autoSave: true,
      categoryName: "",
      tagName: "",
      loading: true,
      updateIsDelete: false,
      remove: false,
      activeStatus: "all",
      videoList: [],
      videoIdList: [],
      categoryList: [],
      tagList: [],
      keywords: null,
      categoryId: null,
      tagId: null,
      isDelete: 0,
      status: null,
      current: 1,
      size: 10,
      count: 0,
    };
  },
  methods: {
    selectionChange(videoList) {
      this.videoIdList = [];
      videoList.forEach((item) => {
        this.videoIdList.push(item.id);
      });
    },
    searchVideos() {
      this.current = 1;
      this.listVideos();
    },
    editVideo(id) {
      this.$router.push({ path: "/videos/" + id });
    },
    updateVideoDelete(id) {
      let param = {};
      if (id != null) {
        param.idList = [id];
      } else {
        param.idList = this.videoIdList;
      }
      param.isDelete = this.isDelete == 0 ? 1 : 0;
      this.axios.put("/api/admin/videos", param).then(({ data }) => {
        if (data.status) {
          this.$notify.success({
            title: "成功",
            message: data.message,
          });
          this.listVideos();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message,
          });
        }
        this.updateIsDelete = false;
      });
    },
    deleteVideos(id) {
      var param = {};
      if (id == null) {
        param = { data: this.videoIdList };
      } else {
        param = { data: [id] };
      }
      this.axios.delete("/api/admin/videos", param).then(({ data }) => {
        if (data.status) {
          this.$notify.success({
            title: "成功",
            message: data.message,
          });
          this.listVideos();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message,
          });
        }
        this.remove = false;
      });
    },
    sizeChange(size) {
      this.size = size;
      this.listVideos();
    },
    currentChange(current) {
      this.current = current;
      this.listVideos();
    },
    changeStatus(status) {
      switch (status) {
        case "all":
          this.isDelete = 0;
          this.status = null;
          break;
        case "delete":
          this.isDelete = 1;
          this.status = null;
          break;
      }
      this.current = 1;
      this.activeStatus = status;
    },
    listVideos() {
      this.axios
        .get("/api/admin/videos", {
          params: {
            current: this.current,
            size: this.size,
            keywords: this.keywords,
            categoryId: this.categoryId,
            status: this.status,
            tagId: this.tagId,
            isDelete: this.isDelete,
          },
        })
        .then(({ data }) => {
          this.videoList = data.data.recordList;
          this.count = data.data.count;
          this.loading = false;
        });
    },
    listCategories() {
      this.axios.get("/api/admin/categories/search").then(({ data }) => {
        this.categoryList = data.data;
      });
    },
    listTags() {
      this.axios.get("/api/admin/tags/search").then(({ data }) => {
        this.tagList = data.data;
      });
    },
    openModel(video) {
      let i = 0,
        arr = [];
      video.tagDTOList.forEach((item) => {
        arr[i++] = item.tagName;
      });
      video.tagNameList = arr;
      this.videoForm = JSON.parse(JSON.stringify(video));
      this.$refs.videoTitle.innerHTML = "修改视频";
      this.edit = true;
    },
    updateVideo() {
      if (this.videoForm.videoName.trim() == "") {
        this.$message.error("视频标题不能为空");
        return false;
      }
      if (this.videoForm.categoryName == null) {
        this.$message.error("视频分类不能为空");
        return false;
      }
      if (this.videoForm.tagNameList.length == 0) {
        this.$message.error("视频标签不能为空");
        return false;
      }
      if (this.videoForm.videoCover.trim() == "") {
        this.$message.error("视频封面不能为空");
        return false;
      }
      this.axios.post("/api/admin/videos", this.videoForm).then(({ data }) => {
        if (data.status) {
          this.$notify.success({
            title: "成功",
            message: data.message,
          });
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message,
          });
        }
        this.edit = false;
      });
      //关闭自动保存功能
      this.autoSave = false;
    },
    autoSaveVideo() {
      // 自动上传文章
      if (
        this.autoSave &&
        this.videoForm.videoName.trim() != "" &&
        this.videoForm.id != null
      ) {
        this.axios
          .post("/api/admin/videos", this.videoForm)
          .then(({ data }) => {
            if (data.status) {
              this.$notify.success({
                title: "成功",
                message: "自动保存成功",
              });
            } else {
              this.$notify.error({
                title: "失败",
                message: "自动保存失败",
              });
            }
          });
      }
      // 保存本地文章记录
      if (this.autoSave && this.videoForm.id == null) {
        sessionStorage.setItem("article", JSON.stringify(this.videoForm));
      }
    },
    searchCategories(keywords, cb) {
      this.axios
        .get("/api/admin/categories/search", {
          params: {
            keywords: keywords,
          },
        })
        .then(({ data }) => {
          cb(data.data);
        });
    },
    handleSelectCategories(item) {
      this.addCategory({
        categoryName: item.categoryName,
      });
    },
    saveCategory() {
      if (this.categoryName.trim() != "") {
        this.addCategory({
          categoryName: this.categoryName,
        });
        this.categoryName = "";
      }
    },
    addCategory(item) {
      this.videoForm.categoryName = item.categoryName;
    },
    removeCategory() {
      this.videoForm.categoryName = null;
    },
    searchTags(keywords, cb) {
      this.axios
        .get("/api/admin/tags/search", {
          params: {
            keywords: keywords,
          },
        })
        .then(({ data }) => {
          cb(data.data);
        });
    },
    handleSelectTag(item) {
      this.addTag({
        tagName: item.tagName,
      });
    },
    saveTag() {
      if (this.tagName.trim() != "") {
        this.addTag({
          tagName: this.tagName,
        });
        this.tagName = "";
      }
    },
    addTag(item) {
      if (this.videoForm.tagNameList.indexOf(item.tagName) == -1) {
        this.videoForm.tagNameList.push(item.tagName);
      }
    },
    removeTag(item) {
      const index = this.videoForm.tagNameList.indexOf(item);
      this.videoForm.tagNameList.splice(index, 1);
    },
    closeModel() {
      this.edit = false;
    },
  },
  watch: {
    type() {
      this.current = 1;
      this.listVideos();
    },
    categoryId() {
      this.current = 1;
      this.listVideos();
    },
    tagId() {
      this.current = 1;
      this.listVideos();
    },
    status() {
      this.current = 1;
      this.listVideos();
    },
    isDelete() {
      this.current = 1;
      this.listVideos();
    },
  },
  computed: {
    isActive() {
      return function (status) {
        return this.activeStatus == status ? "active-status" : "status";
      };
    },
    tagClass() {
      return function (item) {
        const index = this.videoForm.tagNameList.indexOf(item.tagName);
        return index != -1 ? "tag-item-select" : "tag-item";
      };
    },
  },
};
</script>

<style scoped>
.operation-container {
  margin-top: 1.5rem;
}
.video-status-menu {
  font-size: 14px;
  margin-top: 40px;
  color: #999;
}
.video-status-menu span {
  margin-right: 24px;
}
.status {
  cursor: pointer;
}
.active-status {
  cursor: pointer;
  color: #333;
  font-weight: bold;
}
.video-cover {
  position: relative;
  width: 100%;
  height: 90px;
  border-radius: 4px;
}
.video-cover::after {
  content: "";
  background: rgba(0, 0, 0, 0.3);
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
.video-status-icon {
  color: #fff;
  font-size: 1.5rem;
  position: absolute;
  right: 1rem;
  bottom: 1.4rem;
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
.category-item {
  cursor: pointer;
  padding: 0.6rem 0.5rem;
}
.category-item:hover {
  background-color: #f0f9eb;
  color: #67c23a;
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
