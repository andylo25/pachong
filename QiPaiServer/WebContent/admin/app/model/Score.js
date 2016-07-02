Ext.define('Bly.model.Score', {
    extend: 'Ext.data.Model',
    fields: [
    	{name: 'id', type: 'int'},
	    {name: 'userId',type: 'int'},
	    {name: 'gameId',type: 'int'},
	    {name: 'gameTime',type: 'date',dateFormat:gl_dateFormat},
	    {name: 'payMoney',type: 'int'},
	    {name: 'awardMoney',type: 'int'}
	]
});