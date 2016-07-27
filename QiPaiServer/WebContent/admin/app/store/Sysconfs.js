Ext.define('Bly.store.Sysconfs', Ext.merge(cloneObj(store_config),{
    model: 'Bly.model.Sysconf',
    
    proxy: {
        api: {
        	read : 'sysconfList',
            create : 'sysconfCreate',
            update: 'sysconfUpdate',
            destroy: 'sysconfDelete'
        }
    }
}));