function getQueryString(key) {
    const reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)")
    const r = window.location.search.substr(1).match(reg)
    return (r && decodeURI(r[2])) || ''
}

const vue = new Vue({
    el: '#app',
    data: {
        username: '',
        toast: {
            isShow: false,
            msg: ''
        }
    },
    created() {
      this.username = getQueryString('username')
    },
    methods: {
        hidden() {
            Vue.set(this.toast, "isShow", false)
        }
    }
})

let socket = new WebSocket("ws://localhost:8080/websocket")
socket.onerror = err => {
    console.log(err)
}
socket.onopen = event => {
    console.log(event)
}
socket.onmessage = event => {
    vue.$data.toast = {
        isShow: true,
        msg: JSON.parse(event.data).msg
    }
}
socket.onclose = () => {
    console.log("连接关闭")
}
