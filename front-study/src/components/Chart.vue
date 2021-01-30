<template>
  <div class="app">
    <button @click="displayChart" >显示/隐藏图表</button>
    <div v-show="display" id="main" style="width: 600px;height:400px;"></div>
  </div>
</template>

<script>

import * as echarts from 'echarts'
import {
  PieChart
} from 'echarts/charts'

import {
  CanvasRenderer
} from 'echarts/renderers'

echarts.use(
  [PieChart, CanvasRenderer]
)

export default {
  name: 'Chart',
  data() {
    return {
      display: false
    }
  },
  methods: {
    displayChart() {
      this.display = !this.display
      const myChart = echarts.init(document.getElementById('main'))
      myChart.setOption({
        series : [
          {
            name: '访问来源',
            type: 'pie',
            radius: '45%',
            data:[
              {value:235, name:'视频广告'},
              {value:274, name:'联盟广告'},
              {value:310, name:'邮件营销'},
              {value:335, name:'直接访问'},
              {value:400, name:'搜索引擎'}
            ]
          }
        ]
      })
    }
  }
}
</script>

<style scoped>

</style>
