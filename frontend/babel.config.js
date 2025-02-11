const { plugins } = require("@babel/preset-env/lib/plugins-compat-data");
module.exports = {
  presets: ["@vue/cli-plugin-babel/preset"],
  plugins: [
    "@babel/plugin-transform-class-static-block",
    "@babel/plugin-transform-class-properties",
  ],
};
