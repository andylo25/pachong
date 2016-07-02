Ext.Loader.setConfig({enabled: true,disableCaching: false});

Ext.Loader.setPath('Bly', appPath);
Ext.Loader.setPath('Ext.ux', extjs+"/ux");

Ext.require([
    'Ext.tip.QuickTipManager',
    'Ext.container.Viewport',
    'Ext.layout.*',
    'Ext.form.Panel',
    'Ext.form.Label',
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.tree.*',
    'Ext.selection.*',
    'Ext.ux.grid.FiltersFeature',
    'Ext.tab.Panel'
]);

//
// This is the main layout definition.
//
Ext.onReady(function(){
 
    Ext.tip.QuickTipManager.init();

    // Gets all layouts examples
    var layoutContents = [];
    layoutContents.push({
            id: 'start-panel',
            title: '管理开始面板',
//            layout: 'fit',
            bodyStyle: 'padding:25px',
            contentEl: 'start-div'  // pull existing content from the page
        });
    
    // This is the main content center region that will contain each example layout panel.
    // It will be implemented as a CardLayout since it will contain multiple panels with
    // only one being visible at any given time.

    var contentPanel = {
         id: 'content-panel',
         region: 'center', // this is what makes this panel into a region within the containing layout
         xtype: 'tabpanel',
         margins: '2 5 5 0',
         activeTab: 0,
         border: false,
         items: layoutContents
    };
     
    var store = Ext.create('Ext.data.TreeStore', {
        root: {
            expanded: true
        },
        proxy: {
            type: 'ajax',
            url: '../resources/data/tree-data.json'
        }
    });
    
    // Go ahead and create the TreePanel now so that we can use it below
     var treePanel = Ext.create('Ext.tree.Panel', {
        id: 'tree-panel',
        title: '管理菜单',
        region:'center',
        split: true,
//        height: 360,
        minSize: 150,
        rootVisible: false,
        autoScroll: true,
        store: store
    });
    
    // Assign the changeLayout function to be called on tree node click.
    treePanel.getSelectionModel().on('select', function(selModel, record) {
        if (record.get('leaf')) {
        	var id = record.get('id')+"_main_tab_";
        	var tabs = Ext.getCmp('content-panel');
        	var tab = Ext.getCmp(id);
        	if(!tab){
	        	var tab = tabs.add({
	        	 	id : id,
		            title: record.get('text'),
		            closable: true
		        });
		        tab.on("afterrender",loaderJs,this,record);
        	}

        	tabs.setActiveTab(tab);
        }
    });
    
    function loaderJs(tab,record){
    	var url,varMod;
    	if(record.raw.url){
        	url = appPath+"/"+record.raw.url+"Page.js";
        	varMod = record.raw.url+"Page";
    	}else{
        	url = appPath+"/"+record.get('id')+"Page.js";
        	varMod = record.get('id')+"Page";
    	}
    	if(!window[varMod]){
	    	Ext.Loader.loadScript({url:url,onLoad:function(){
	    		window[varMod]();
	    	}});
    	}else{
    		window[varMod]();
    	}
    }
    
    
    // Finally, build the main layout once all the pieces are ready.  This is also a good
    // example of putting together a full-screen BorderLayout within a Viewport.
    Ext.create('Ext.Viewport', {
        layout: 'border',
        title: '后台管理系统',
        items: [{
            xtype: 'box',
            id: 'header',
            region: 'north',
            html: '<h1><strong> 后台管理系统 </strong> 当前登录用户：'+gl_login_userName+'</h1>',
            height: 30
        },{
            layout: 'border',
            id: 'layout-browser',
            region:'west',
            border: false,
            collapsible:true,
            split:true,
            margins: '2 0 5 5',
            width: 290,
            minSize: 100,
            maxSize: 500,
            tools:[{
			    type:'next',
			    tooltip: '退出',
			    // hidden:true,
			    handler: function(event, toolEl, panelHeader) {
			    	window.location.href = basePath+"/user/logout";
			    }
			},{
			    type:'help',
			    tooltip: 'Get Help',
			    callback: function(panel, tool, event) {
			        // show help here
			    }
			}],
            items: [treePanel]
        }, 
            contentPanel
        ],
        renderTo: Ext.getBody()
    });
});
