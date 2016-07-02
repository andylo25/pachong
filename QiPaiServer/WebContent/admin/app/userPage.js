if(!userPage){
var userPage = function() {
//	Ext.require([
//		'Bly.store.Users',
//		'Bly.view.AppGrid'
//	])
	var tabs = Ext.getCmp('content-panel');
	var tab = tabs.getActiveTab();

	var store = Ext.create('Bly.store.Users');
	var grid = Ext.create('Bly.view.AppGrid',{
		store : store,
		stateId : 'stateUserGrid',
//		height : 350,
		columns : [{
					text : '用户ID',
//					sortable : false,
//					hidden: true,
					filterable: true,
					dataIndex : 'id'
				}, {
					text : '用户名',
					flex : 1,
					filterable: true,
//					width : 75,
					filter : {like:1},
					dataIndex : 'userName'
				}, {
					text : '昵称',
					flex : 1,
					filterable: true,
					filter : {like:1},
//					width : 80,
					dataIndex : 'nickName'
				}, {
					text : '金币',
					flex : 1,
//					width : 100,
					sortable : true,
					dataIndex : 'coin'
				}, {
					text : '登录时间',
					flex : 1,
					filterable: true,
//					width : 115,
					renderer : Ext.util.Format.dateRenderer(gl_dateFormat),
					dataIndex : 'loginTime'
				}, {
					text : '登出时间',
					flex : 1,
					filterable: true,
//					width : 115,
					renderer : Ext.util.Format.dateRenderer(gl_dateFormat),
					dataIndex : 'logoutTime'
				}, {
					text : '用户状态',
					flex : 1,
					filterable: true,
//					width : 115,
					renderer: function(value) {
						if(value == '0'){
							return '正常';
						}else if(value == '1'){
							return '锁定';
						}else if(value == '2'){
							return '禁用';
						}else if(value == '3'){
							return '屏蔽';
						}
						return '正常';
			        },
					dataIndex : 'status'
				}, {
					text : '是否在线',
					flex : 1,
					filterable: true,
//					width : 115,
					renderer: function(value) {
						if(value == '1'){
							return '<span style="color:#73b51e;">在线</span>';
						}
						return '<span style="color:#cf4c35;">离线</span>';
			        },
					dataIndex : 'online'
				}, {
					text : '操作',
					menuDisabled : true,
					sortable : false,
					xtype : 'actioncolumn',
					width : 50,
					items : [{
						icon: imgPath+'/user.png',
						tooltip : '修改金币',
						handler : function(grid, rowIndex, colIndex) {
							var rec = grid.getStore().getAt(rowIndex);
							Ext.Msg.prompt('修改金币', '输入金币:', function(btn,text){
								if(btn != 'cancel'){
									send('updateCoin',
									    {
									        id: rec.get('id'),
									        coin: text
									    },
									    function(json){
									    	store.reload();
									    	return true;
									    }
									);
								}
							});
						}
					}]
				}]
	});
	tab.add(grid);

}
}
