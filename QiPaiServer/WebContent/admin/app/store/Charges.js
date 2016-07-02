Ext.define('Bly.store.Charges', Ext.merge(cloneObj(store_config),{
    model: 'Bly.model.Charge',
    
    proxy: {
        api: {
        	read : 'chargeList',
            create : 'chargeCreate',
            update: 'chargeUpdate',
            destroy: 'chargeDelete'
        }
    }
}));