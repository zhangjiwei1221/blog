new Vue({
    el: '#app',
    data: {
        username: '',
        password: '',
        toast: {
            isShow: false,
            msg: ''
        }
    },
    methods: {
        login() {
            axios.post('/login', {
                username: this.username,
                password: this.password
            }).then(data => {
                if (!data.data) {
                    Vue.set(this, "toast", { isShow: true, msg: '用户名或密码错误' })
                } else {
                    window.location.href = 'info?username=' + this.username
                }
            }).catch(err => {
                console.log(err);
            })
        },
        hidden() {
            Vue.set(this.toast, "isShow", false)
        }
    }
})
