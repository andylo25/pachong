Ext.define('Bly.store.Scores', Ext.merge(cloneObj(store_config),{
    model: 'Bly.model.Score',
    
    proxy: {
        api: {
        	read : 'scoreList',
            create : 'scoreCreate',
            update: 'scoreUpdate',
            destroy: 'scoreDelete'
        }
    }
}));