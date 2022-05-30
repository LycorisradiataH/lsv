const compressionPlugin = require("compression-webpack-plugin");
module.exports = {
  configureWebpack: {
    plugins: [
      new compressionPlugin({
        test: /\.(js|css)(\?.*)?$/i,
        threshold: 10240,
        deleteOriginalAssets: false,
      }),
    ],
  },
  productionSourceMap: false,
  devServer: {
    port: 81,
    open: true,
    proxy: {
      "/api": {
        target: "http://localhost:8001",
        changeOrigin: true,
        pathRewrite: {
          "^/api": "",
        },
      },
    },
    disableHostCheck: true,
  },
  chainWebpack: (config) => {
    config.resolve.alias.set("@", resolve("src"));
  },
};

const path = require("path");
function resolve(dir) {
  return path.join(__dirname, dir);
}
