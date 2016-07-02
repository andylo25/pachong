Ext.define('Bly.model.User', {
    extend: 'Ext.data.Model',
    fields: [
    	{name: 'id', type: 'int'}, 
        'userName', 
        'pwd', 
        'nickName',
        {name: 'coin', type: 'int'},
        {name: 'loginTime',type: 'date',dateFormat:gl_dateFormat},
        {name: 'logoutTime',type: 'date',dateFormat:gl_dateFormat},
        'isVip',
        'status',
        'online'
        ]
});