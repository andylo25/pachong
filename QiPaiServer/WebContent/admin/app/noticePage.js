if(!noticePage){
var noticePage = function() {
	var tabs = Ext.getCmp('content-panel');
	var tab = tabs.getActiveTab();

	var store = Ext.create('Bly.store.Notices');
	var grid = Ext.create('Bly.view.AppGrid',{
		store : store,
		stateId : 'stateNoticeGrid',
//		height : 350,
		columns : [{
					text : 'id',
					sortable : false,
//					hidden: true,
					dataIndex : 'id'
				}, {
					text : '公告',
					flex : 1,
//					width : 75,
					dataIndex : 'notice'
				}, {
					text : '状态',
					flex : 1,
//					width : 80,
					dataIndex : 'flag'
				}, {
					text : '操作',
					menuDisabled : true,
					sortable : false,
					xtype : 'actioncolumn',
					width : 50,
					items : [
					{
						icon: imgPath+'/user.png',
						tooltip : '修改公告',
						handler : function(grid, rowIndex, colIndex) {
							var rec = grid.getStore().getAt(rowIndex);
							Ext.Msg.prompt('修改公告', '输入公告:', function(btn,text){
								if(btn == 'cancel')return;
								send('updateNotice',
								    {
								        id: rec.get('id'),
								        notice: text
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
	tab.add(grid);
}
}
