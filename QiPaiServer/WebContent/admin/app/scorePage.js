if(!scorePage){
var scorePage = function() {
	var tabs = Ext.getCmp('content-panel');
	var tab = tabs.getActiveTab();

	var store = Ext.create('Bly.store.Scores');
	var grid = Ext.create('Bly.view.AppGrid',{
		store : store,
		stateId : 'stateScoreGrid',
//		height : 350,
		columns : [{
					text : 'id',
					sortable : false,
					hidden: true,
					dataIndex : 'id'
				}, {
					text : '用户ID',
					flex : 1,
//					width : 75,
					dataIndex : 'userId'
				}, {
					text : '游戏ID',
					flex : 1,
//					width : 80,
					dataIndex : 'gameId'
				}, {
					text : '游戏时间',
					flex : 1,
//					width : 100,
					sortable : true,
					renderer : Ext.util.Format.dateRenderer(gl_dateFormat),
					dataIndex : 'gameTime'
				}, {
					text : '下注金额',
					flex : 1,
//					width : 115,
					dataIndex : 'payMoney'
				}, {
					text : '赢取金额',
					flex : 1,
//					width : 115,
					dataIndex : 'awardMoney'
				}]
	});
	tab.add(grid);
}
}
