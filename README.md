# lsv

## 介绍
<div align="center">Web在线视频系统</div>

<div align="center">
    <a href="https://gitee.com/lhMorri/personal-blog" style="text-decoration: none;" onMouseOver="text-decoration: none;">
         <img src="https://img.shields.io/badge/license-Apache--2.0-informational" alt="license" />
         <img src="https://img.shields.io/badge/JDK-1.8%2B-brightgreen" alt="JDK" />
        <img src="https://img.shields.io/badge/SpringCloudAlibaba-2.2.1.RELEASE-brightgreen" alt="SpringCloudAlibaba" />
        <img src="https://img.shields.io/badge/Nacos-1.4.2-brightgreen" alt="Nacos" />
         <img src="https://img.shields.io/badge/SpringBoot-2.2.6.RELEASE-brightgreen" alt="SpringBoot" />
         <img src="https://img.shields.io/badge/Vue-2.6.11-brightgreen" alt="Vue" />
         <img src="https://img.shields.io/badge/MySQL-8.0.26-brightgreen" alt="MySQL" />
         <img src="https://img.shields.io/badge/Mybatis--Plus-3.4.1-brightgreen" alt="Mybatis-Plus" />
         <img src="https://img.shields.io/badge/Redis-6.0.16-brightgreen" alt="Redis" />
         <img src="https://img.shields.io/badge/RabbitMQ-3.8.23-brightgreen" alt="RabbitMQ" />
    </a>
</div>
<div align="center">
    <a href="#技术介绍">技术介绍</a>|
    <a href="#开发环境">开发环境</a>|
    <a href="#目录结构">目录结构</a>|
    <a href="#项目特色">项目特色</a>|
    <a href="#运行环境">运行环境</a>|
    <a href="#注意事项">注意事项</a>|
    <a href="#上线日记">上线日记</a>|
    <a href="#项目总结">项目总结</a>
</div>

## 技术介绍

- **前端：** Vue + vue-router + vuex + axios + vuetify + element + echarts
  - 前台框架：vuetify
  - 后台框架：element

- **后端：** Nacos + SpringBoot + SpringSecurity + MySQL + MybatisPlus + Redis + RabbitMQ + Swagger2 + Docker + Nginx
- **其他：** 阿里云OSS对象存储、阿里云视频点播 

## 开发环境

| 开发工具              | 说明              |
| --------------------- | ----------------- |
| Intellij IDEA         | Java开发工具IDE   |
| VS Code               | Vue开发工具IDE    |
| Navicat               | MySQL远程连接工具 |
| Redis Desktop Manager | Redis远程连接工具 |
| Xshell                | Linux远程连接工具 |
| Xftp                  | Linux文件上传工具 |

| 环境依赖 | 版本   |
| -------- | ------ |
| JDK      | 1.8    |
| Maven    | 3.6.3  |
| MySQL    | 8.0.26 |
| Redis    | 6.0.16 |
| RabbitMQ | 3.8.23 |
| Nacos    | 1.4.2  |
| Vue-cli  | 4.5.13 |

## 目录结构

1.  前端项目位于lsv-vue目录下
    - lsv为前台项目
    - lsv-admin为后台项目
2.  后端项目位于lsv-springboot目录下
3.  SQL文件位于根目录下
    - <span style='color: red'>注</span>：MySQL 版本需要 <span style='color: red'>8.0</span> 以上
4.  接口文档地址：
    - 启动后端项目访问：[http://localhost:8001/doc.html](http://localhost:8001/doc.html)
5.  拉取项目的注意事项：
    - 需要修改后端项目的 `bootstrap.yml` 配置文件中的 `spring.cloud.config.server-addr` 为自己的 Nacos 地址
    - OSS对象存储功能需要自行开启
    - 后台可以登录管理员账号 admin@qq.com，密码：123456，进行修改数据。
    - 测试账号 test@qq.com，密码：1234567

```
lsv
|--lsv-springboot
|  |--api
|  |  |--pojo
|  |  |  └--entity				// 实体类模块
|  └--video_server				// 后端应用
|  |  |--java					// 应用
|  |  |  |--common				// 公共模块
|  |  |  |  |--annotation		// 自定义注解
|  |  |  |  |--aspect			// aop模块
|  |  |  |  |--constant			// 常量模块
|  |  |  |  |--consumer			// MQ消费者模块
|  |  |  |  |--enums			// 枚举模块
|  |  |  |  |--exception		// 自定义异常
|  |  |  |  |--handler			// 处理器模块
|  |  |  |  └--strategy			// 策略模式
|  |  |  |--config				// 配置模块
|  |  |  |--controller			// 控制器模块
|  |  |  |--mapper				// 数据访问模块
|  |  |  |--pojo				// POJO模块
|  |  |  |  |--dto				// 数据传输对象
|  |  |  |  └--vo				// 展示对象
|  |  |  |--service				// 业务逻辑模块
|  |  |  └--util				// 工具类模块
|  |  └--resource				// 资源
|  |  |  |--mapper				// 数据访问映射文件
|  |  |  |--application.yml		// 本地配置文件
|  |  |  └--bootstrap.yml		// 云端配置文件
└--lsv-vue						// 前端应用
|  |--lsv
|  |  |--public					// 静态资源，打包时不会被压缩
|  |  └--src					// 应用
|  |  |  |--api					// api接口
|  |  |  |--assets				// 静态资源，打包时会被压缩
|  |  |  |--components			// 组件
|  |  |  |--plugins				// 插件
|  |  |  |--request				// 请求
|  |  |  |--router				// 路由
|  |  |  |--store				// 状态管理
|  |  |  |--views				// 页面
|  |  |  |--App.vue				// 主组件
|  |  |  └--main.js				// 入口js文件
|  |  └--vue.config.js			// 配置文件
|  └--lsv-admin
|  |  |--public					// 静态资源，打包时不会被压缩
|  |  └--src					// 应用
|  |  |  |--assets				// 静态资源，打包时会被压缩
|  |  |  |--components			// 组件
|  |  |  |--layout				// 组件
|  |  |  |--router				// 路由
|  |  |  |--store				// 状态管理
|  |  |  |--views				// 页面
|  |  |  |--App.vue				// 主组件
|  |  |  └--main.js				// 入口js文件
|  |  └--vue.config.js			// 配置文件
```

## 项目特色

1.  后台参考"element-admin"设计，侧边栏，历史标签，面包屑自动生成。
2.  评论支持表情输入回复等，样式参考Valine。
3.  前后端分离部署，适应当前潮流。
4.  搜索视频支持高亮分词，响应速度快。
5.  使用aop注解实现日志管理。
6.  支持动态权限修改，采用RBAC模型，前端菜单和后台权限实时更新。
7.  后台管理支持修改背景图片，博客配置等信息，操作简单。
8.  代码遵循阿里巴巴开发规范，利于开发者学习。
9.  视频采用阿里云视频点播功能，方便存取。

## 运行环境

- **推荐配置：**
  - **服务器：** 阿里云2核4G 带宽8M CentOS 7.6

  - **对象存储：** 阿里云OSS

## 注意事项

- 项目拉取之后，后端配置文件需要修改为自己的本地或服务器的nacos地址，账号密码在nacos中进行配置和修改
- 邮箱配置需要自己申请
- 阿里云对象存储需要自己申请，也可以选择上传到本地

## 上线日记

| 时间       | 内容                       | 更新人员 |
| ---------- | -------------------------- | -------- |
| 2021-12-01 | 正式开发                   | Morri    |
| 2022-04-01 | 首次上线测试               | Morri    |
| 2022-04-20 | 解决权限bug                | Morri    |
| 2022-04-25 | 增加用户个人发布视频页面   | Morri    |
| 2022-04-26 | 增加查看其他用户发布的视频 | Morri    |
| 2022-04-30 | 开始摸鱼                   | Morri    |

## 项目总结

其实还有很大的开发空间，只是作者没时间了，就暂时先摸鱼吧，如果后续有计划完善会更新的~(●ˇ∀ˇ●)

[返回顶部](#lsv)
