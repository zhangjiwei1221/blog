<template>
  <div id="app">
    <el-table :data="fileList" style="width: 60%;margin: 0 auto;">
      <el-table-column prop="isDir" label="类型" align="center">
        <template #default="{ row }">
          <i v-if="row.isDir" class="el-icon-folder"></i>
          <i v-else class="el-icon-document"></i>
        </template>
      </el-table-column>
      <el-table-column prop="fileName" label="文件名" align="center"/>
      <el-table-column prop="fileSize" label="文件大小(byte)" align="center">
        <template #default="{ row }">
          {{ row.isDir ? '-' : row.fileSize }}
        </template>
      </el-table-column>
      <el-table-column prop="lastModified" label="修改时间" align="center">
        <template #default="{ row }">
          {{ new Date(row.lastModified).toLocaleString() }}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { QWebChannel } from '@/assets/qwebchannel'

export default {
  name: 'App',
  data() {
    return {
      // 文件列表
      fileList: []
    }
  },
  mounted() {
    const websocket = new WebSocket('ws://localhost:9999')
    websocket.onopen = () => {
      new QWebChannel(websocket, channel => {
        const webChannelObj = channel.objects.server
        webChannelObj.infoChanged.connect(data => {
          this.fileList = data
        })
        webChannelObj.getInfo(data => {
          this.fileList = data
        })
      })
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
