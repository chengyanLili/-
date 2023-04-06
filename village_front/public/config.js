const Globe = (function(){
        // 更新页面布局配置标题
        // 时间字符转化为 yyyy-MM-dd HH:mm:ss 格式
    const formatDates = function () {
        const array = Array.from(arguments).map(item => {
            const string = new Date(item).toLocaleString()
            return string.replace(/上午|下午/, '').replace(/(\/\d+)/g, (val) => '-' + (val.length > 2 ? '' : '0') + val.substring(1))
        })
        return array
    }

        return Object.freeze({
                formatDates
            })
})()