window.onload = () => {
    // 添加模拟信息数据
    addData('<div class="msg">信息...</div>'.repeat(10))
    // 默认将滚动条置为最下端
    setScrollToEnd()
}

// 设置滚动条的颜色
function setColor(color) {
    document.documentElement.style.setProperty('--scroll-color', `${color}`)
}

function sendMsg() {
    const node = document.querySelector('.messageText')
    if (!node.value) {
        node.placeholder = '信息不可为空！'
        return
    }
    addData(`<div class="msg-right" style="text-align: right;">${node.value}</div>`)
    setScrollToEnd()
    node.value = node.placeholder = ''
}

function addData(msg) {
    document.getElementsByClassName('middle')[0].innerHTML += msg
}

function setScrollToEnd() {
    const mid = document.querySelector('.middle')
    mid.scrollTop = mid.scrollHeight
}

function send(code) {
    if (code === 'Enter'){
       sendMsg()
    }
}
