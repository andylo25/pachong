

var gl_dateFormat = 'Y-m-d H:i:s';


    
function send(url,params,suc){
	Ext.Ajax.request({
		url: url,
	    params: params,
	    success: function(response){
	    	var obj = Ext.decode(response.responseText);
	    	if(suc && !suc(obj)){
	    		return;
	    	}
	    	ecd(obj.ecd);
	    },
	    failure: function(response, opts) {
	        console.log('server-side failure with status code ' + response.status);
	    }
	});
}

function ecd(ecd){
	if(ecd == 0){
		Ext.Msg.alert("操作提示","操作成功");
	}else if(ecd == 997){
		Ext.Msg.alert("操作提示","操作异常");
	}else if(ecd == 998){
		Ext.Msg.alert("操作提示","用户会话失效");
	}else{
		Ext.Msg.alert("操作提示","未知异常");
	}
}

var cloneObj = function(obj){
    var str, newobj = obj.constructor === Array ? [] : {};
    if(typeof obj !== 'object'){
        return;
    } else if(window.JSON){
        str = JSON.stringify(obj), //系列化对象
        newobj = JSON.parse(str); //还原
    } else {
        for(var i in obj){
            newobj[i] = typeof obj[i] === 'object' ? 
            cloneObj(obj[i]) : obj[i]; 
        }
    }
    return newobj;
};

var store_config = {
    extend: 'Ext.data.Store',
    model: 'Ext.data.Model',
    autoLoad: true,
    pageSize: 25,
    remoteSort: true,
    sorters: [{
            property: 'id',
            direction: 'ASC'
    }],
        
    proxy: {
        type: 'ajax',
        api: {
        	read : 'list',
            create : 'create',
            update: 'update',
            destroy: 'delete'
        },
        reader: {
            type: 'json',
            root: 'list',
            totalProperty: 'totalRow'
        },
        writer: {
            type: 'json',
            writeAllFields: true,
            encode: false,
            root: 'data'
        },
        listeners: {
            exception: function(proxy, response, operation){
                Ext.MessageBox.show({
                    title: 'REMOTE EXCEPTION',
                    msg: operation.getError(),
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        }
    }
}

