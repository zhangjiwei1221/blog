const mid = document.querySelector('.middle')
mid.scrollTop = mid.scrollHeight

const setColor = color => document.documentElement.style.setProperty('--scroll-color', `${color}`)