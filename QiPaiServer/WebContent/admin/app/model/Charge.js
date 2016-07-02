Ext.define('Bly.model.Charge', {
    extend: 'Ext.data.Model',
    fields: [
    	{name: 'id', type: 'int'},
	    {name: 'userId',type: 'int'},
	    {name: 'money',type: 'int'},
	    {name: 'coin',type: 'int'},
	    {name: 'txnTime',type: 'date',dateFormat:gl_dateFormat},
	    {name: 'from'},
	    {name: 'orderNo'},
	    {name: 'ordreState'}
    ]
});