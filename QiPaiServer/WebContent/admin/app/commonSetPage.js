if(!commonSetPage){
var commonSetPage = function() {
	var tabs = Ext.getCmp('content-panel');
	var tab = tabs.getActiveTab();
	
	var form = Ext.create('Ext.panel.Panel', {
	//  layout: 'absolute',
	    defaults: {
	        style: 'margin:15px;padding:10px'
//	        width: 200,
//	        height: 100,
//	        frame: true
	    },
	    items:[{
	        xtype: 'button',
	        text : '刷新翻牌配置缓存',
	        handler: function() {
				Ext.Msg.confirm('刷新翻牌配置缓存', '确定刷新翻牌配置缓存吗？', function(btn,text){
					if(btn != 'cancel'){
						send('refreshCache',{type: 1},
						    function(json){
						    	return true;;
						    }
						);
					}
				});
	        }
	    },{
	        xtype: 'button',
	        text: '刷新拉霸配置缓存',
	        handler: function() {
				Ext.Msg.confirm('刷新拉霸配置缓存', '确定刷新拉霸配置缓存吗？', function(btn,text){
					if(btn != 'cancel'){
						send('refreshCache',{type: 2},
						    function(json){
						    	return true;
						    }
						);
					}
				});
	        }
	    }]
	    
	});
	
	tab.add(form);
	
}
}
