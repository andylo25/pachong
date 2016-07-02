Ext.define('Bly.store.Users', Ext.merge(cloneObj(store_config),{
    model: 'Bly.model.User',
    proxy: {
        api: {
        	read : 'userList',
            create : 'userCreate',
            update: 'userUpdate',
            destroy: 'userDelete'
        }
    }
}));