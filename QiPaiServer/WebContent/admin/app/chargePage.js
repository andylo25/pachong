if(!chargePage){
var chargePage = function() {
	var tabs = Ext.getCmp('content-panel');
	var tab = tabs.getActiveTab();

	var store = Ext.create('Bly.store.Charges');
	var grid = Ext.create('Bly.view.AppGrid',{
		store : store,
		stateId : 'stateChargeGrid',
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
					text : '充值金额',
					flex : 1,
//					width : 80,
					dataIndex : 'money'
				}, {
					text : '充值金币',
					flex : 1,
//					width : 100,
					sortable : true,
					dataIndex : 'coin'
				}, {
					text : '交易时间',
					flex : 1,
//					width : 115,
					renderer : Ext.util.Format.dateRenderer(gl_dateFormat),
					dataIndex : 'txnTime'
				}, {
					text : '充值平台',
					flex : 1,
//					width : 115,
					dataIndex : 'from'
				}, {
					text : '订单号',
					flex : 1,
//					width : 115,
					dataIndex : 'orderNo'
				}, {
					text : '订单状态',
					flex : 1,
//					width : 115,
					renderer : function(value){
						if(value == '0'){
							return '待支付';
						}else if(value == '1'){
							return '已支付';
						}else if(value == '2'){
							return '支付异常';
						}
						return '未知';
					},
					dataIndex : 'ordreState'
				},{
					text : '操作',
					menuDisabled : true,
					sortable : false,
					xtype : 'actioncolumn',
					width : 50,
					items : [{
						icon: imgPath+'/user.png',
						tooltip : '修改转账状态',
						handler : function(grid, rowIndex, colIndex) {
							var rec = grid.getStore().getAt(rowIndex);
							Ext.Msg.confirm('修改转账状态','确定要修改转账状态吗？', function(btn,text){
								if(btn != 'cancel'){
									send('upChargeState',
									    {
									        id: rec.get('id')
									    },
									    function(json){
									    	store.reload();
									    	return false;
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
