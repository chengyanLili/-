const Globe = (function(){
        // 更新页面布局配置标题
        const updateTitle_groups = function (size) {
                this[0].title = this[0].title.replace(/（.*?）|$/, `（${ size }）`)
        }
        const PopulationTable = [
                { prop: 'supId', label: '网格员编号',width:'100px' },
        ]
        for (const groups of [ updateTitle_groups ]) {
                groups.updateTitle = updateTitle
        }
        return Object.freeze({
                PopulationTable,
        
                updateTitle_groups
            })
})()