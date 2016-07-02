Ext.define('Bly.model.Notice', {
    extend: 'Ext.data.Model',
    fields: [
	    {name: 'id', type: 'int'},
	    {name: 'notice',type: 'string'},
	    {name: 'flag',type: 'int'}
    ]
});