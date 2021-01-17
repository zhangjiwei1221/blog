axios.interceptors.request.use(config => {
    startLoading()
    return config
}, error => {
    return Promise.reject(error)
})

axios.interceptors.response.use(response => {
    if (response.status === 200) {
        endLoading()
    }
    return response
}, error => {
    return Promise.reject(error)
})



function startLoading() {
    document.getElementById('loading').style.display = ''
}

function endLoading() {
    document.getElementById('loading').style.display = 'none'
}
