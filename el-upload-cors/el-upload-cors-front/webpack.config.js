const VueLoaderPlugin = require('vue-loader/lib/plugin')

module.exports = {
  mode: 'development',
  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader'
      }
    ]
  },
  resolve: {
    extensions: ['', '.js', '.json', '.vue', '.scss', '.css']
  },
  plugins: [
    new VueLoaderPlugin()
  ]
}
