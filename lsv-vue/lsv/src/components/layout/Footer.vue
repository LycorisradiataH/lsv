<template>
  <v-footer app padless absolute>
    <div class="footer-wrap">
      <div>
        ©{{ videoInfo.websiteConfig.websiteCreateTime | year }} -
        {{ new Date().getFullYear() }} By
        {{ videoInfo.websiteConfig.websiteAuthor }}
      </div>
      <div>
        <a href="https://beian.miit.gov.cn/" target="_blank">
          {{ videoInfo.websiteConfig.websiteRecordNo }}
        </a>
        |
        <a
          target="_blank"
          href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=44020402000283"
        >
          <img src="https://www.static.hdiata.com/config/beian.png" />
          {{ videoInfo.websiteConfig.websiteBeianNo }}
        </a>
      </div>
    </div>
  </v-footer>
</template>

<script>
export default {
  computed: {
    videoInfo () {
      return this.$store.state.videoInfo
    }
  }
}
</script>

<style scoped>
.footer-wrap {
  width: 100%;
  line-height: 2;
  position: relative;
  padding: 40px 20px;
  color: #eee;
  font-size: 14px;
  text-align: center;
  background: linear-gradient(-45deg, #ee7752, #ce3e75, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: Gradient 10s ease infinite;
}
.footer-wrap a {
  color: #eee !important;
}
@keyframes Gradient {
  0% {
    background-position: 0 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0 50%;
  }
}
</style>
