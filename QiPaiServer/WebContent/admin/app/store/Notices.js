Ext.define('Bly.store.Notices', Ext.merge(cloneObj(store_config),{
    model: 'Bly.model.Notice',
    proxy: {
        api: {
        	read : 'noticeList',
            create : 'noticeCreate',
            update: 'noticeUpdate',
            destroy: 'noticeDelete'
        }
    }
}));