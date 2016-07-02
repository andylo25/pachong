Ext.define('Bly.view.AppGrid', {
    extend: 'Ext.grid.Panel',
    requires : ['Ext.grid.column.Action'],
	stateful : true,
	store : 'Ext.data.Store',
	collapsible : true,
	multiSelect : true,
	features: [{
        ftype: 'filters',
        autoReload : true,
        // encode and local configuration options defined previously for easier reuse
        encode: true // json encode the filter query
    }],
//	height : 350,
	viewConfig : {
		stripeRows : true,
		enableTextSelection : true
	},
    initComponent: function(){
    	var me = this;
    	Ext.Array.each(this.columns, function(col) {
    		col.filterable = true;
	    });
    	this.dockedItems = [{
	        xtype: 'pagingtoolbar',
	        store: me.store,   // same store GridPanel is using
	        dock: 'bottom',
	        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	        emptyMsg: "没有记录",
	        displayInfo: true,
	        items:[{
		    	text: '重置',
	            handler: function () {
	                me.filters.clearFilters();
	            } 
	        }]
	    }],
	    
    	this.callParent();
    }
    
})