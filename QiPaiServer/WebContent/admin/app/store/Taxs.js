Ext.define('Bly.store.Taxs', Ext.merge(cloneObj(store_config),{
    model: 'Bly.model.Tax',
    proxy: {
        api: {
        	read : 'taxList',
            create : 'taxCreate',
            update: 'taxUpdate',
            destroy: 'taxDelete'
        }
    }
}));