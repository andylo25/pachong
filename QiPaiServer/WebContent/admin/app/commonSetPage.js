if(!commonSetPage){
var commonSetPage = function() {
	var tabs = Ext.getCmp('content-panel');
	var tab = tabs.getActiveTab();
	
	var store = Ext.create('Bly.store.Sysconfs');
	var grid = Ext.create('Bly.view.AppGrid',{
		store : store,
		stateId : 'stateSysconfGrid',
//		height : 350,
		columns : [{
					text : 'id',
					sortable : false,
					hidden: true,
					dataIndex : 'id'
				}, {
					text : '配置键',
					flex : 1,
//					width : 75,
					dataIndex : 'key'
				}, {
					text : '配置值',
					flex : 1,
//					width : 80,
					dataIndex : 'val'
				}, {
					text : '操作',
					menuDisabled : true,
					sortable : false,
					xtype : 'actioncolumn',
					width : 50,
					items : [
					{
						icon: imgPath+'/user.png',
						tooltip : '修改配置',
						handler : function(grid, rowIndex, colIndex) {
							var rec = grid.getStore().getAt(rowIndex);
							Ext.Msg.prompt('修改配置', '输入配置:', function(btn,text){
								if(btn == 'cancel')return;
								send('updateSysconf',
								    {
								        id: rec.get('id'),
								        val: text
								    },
								    function(json){
								    	store.reload();
								    	return false;
								    }
								);
							});
						}
					}]
				}]
	});
	
	var form = Ext.create('Ext.panel.Panel', {
	//  layout: 'absolute',
	    defaults: {
	        style: 'margin:15px;padding:10px'
//	        width: 200,
//	        height: 100,
//	        frame: true
	    },
	    items:[grid,{
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
