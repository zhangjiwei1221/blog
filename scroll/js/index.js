// 默认将滚动条置为最下端
const mid = document.querySelector('.middle')
mid.scrollTop = mid.scrollHeight

// 设置滚动条的颜色
const setColor = color => document.documentElement.style.setProperty('--scroll-color', `${color}`)

// 添加模拟信息数据
document.getElementsByClassName('middle')[0].innerHTML = ' <div class="msg">信息...</div>'.repeat(10)
