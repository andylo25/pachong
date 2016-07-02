Ext.define('Bly.model.Tax', {
    extend: 'Ext.data.Model',
    fields: [
	    {name: 'id', type: 'int'},
	    {name: 'tax',type: 'int'},
	    {name: 'gameId',type: 'int'},
	    {name: 'stock',type: 'int'},
	    {name: 'activMoney',type: 'int'},
	    {name: 'coinPoo',type: 'int'}
    ]
});