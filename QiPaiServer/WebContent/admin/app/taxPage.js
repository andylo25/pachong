if(!taxPage){
var taxPage = function() {
	var tabs = Ext.getCmp('content-panel');
	var tab = tabs.getActiveTab();

	var store = Ext.create('Bly.store.Taxs');
	var grid = Ext.create('Bly.view.AppGrid',{
		store : store,
		stateId : 'stateTaxGrid',
//		height : 350,
		columns : [{
					text : 'id',
					sortable : false,
					hidden: true,
					dataIndex : 'id'
				}, {
					text : '税率',
					flex : 1,
//					width : 75,
					renderer : function(val){
						return val+'%';
					},
					dataIndex : 'tax'
				}, {
					text : '游戏ID',
					flex : 1,
//					width : 80,
					dataIndex : 'gameId'
				}, {
					text : '总库存',
					flex : 1,
//					width : 100,
					sortable : true,
					dataIndex : 'stock'
				}, {
					text : '活动金币',
					flex : 1,
//					width : 115,
					dataIndex : 'activMoney'
				}, {
					text : '奖金池',
					flex : 1,
//					width : 115,
//					renderer : Ext.util.Format.dateRenderer('m/d/Y'),
					dataIndex : 'coinPoo'
				}, {
					text : '操作',
					menuDisabled : true,
					sortable : false,
					xtype : 'actioncolumn',
					width : 50,
					items : [
					{
						icon: imgPath+'/add.png',
						tooltip : '增加库存',
						handler : function(grid, rowIndex, colIndex) {
							var rec = grid.getStore().getAt(rowIndex);
							Ext.Msg.prompt('增加库存', '输入金币:', function(btn,text){
								if(btn == 'cancel')return;
								send('updateStock',
								    {
								        gameId: rec.get('gameId'),
								        flag:'1',
								        coin: text
								    },
								    function(json){
								    	store.reload();
								    	return false;
								    }
								);
							});
						}
					}, {
						icon: imgPath+'/add.png',
						tooltip : '增加活动金币',
						handler : function(grid, rowIndex, colIndex) {
							var rec = grid.getStore().getAt(rowIndex);
							Ext.Msg.prompt('增加活动金币', '输入金币:', function(btn,text){
								send('updateStock',
								    {
								        gameId: rec.get('gameId'),
								        flag:'2',
								        coin: text
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
