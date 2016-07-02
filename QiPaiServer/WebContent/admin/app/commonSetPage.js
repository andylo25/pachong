if(!commonSetPage){
var commonSetPage = function() {
	var tabs = Ext.getCmp('content-panel');
	var tab = tabs.getActiveTab();
	
	var form = Ext.create('Ext.panel.Panel', {
	//  layout: 'absolute',
	    defaults: {
	        bodyStyle: 'padding:15px;',
	        width: 200,
	        height: 100,
	        frame: true
	    },
	    items:[{
	        title: '开发中...',
	        html: '开发中...'
	    }]
	});
	
	tab.add(form);
	
}
}
