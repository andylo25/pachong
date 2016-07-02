Ext.define('Bly.controller.Users', {
    extend: 'Ext.app.Controller',

    stores: ['Users'],

    models: ['User'],

    views: ['user.Edit', 'user.List'],

    refs: [{
            ref: 'contactsPanel',
            selector: 'panel'
        },{
            ref: 'userlist',
            selector: 'userlist'
        }
    ],

    init: function() {
        this.control({
            'userlist dataview': {
                itemdblclick: this.editUser
            },
            'userlist button[action=add]': {
            	click: this.editUser
            },
            'userlist button[action=delete]': {
                click: this.deleteUser
            },
            'userlist button[action=save]': {
                click: this.updateUser
            }
        });
    },

    editUser: function(grid, record) {
        var edit = Ext.create('Bly.view.user.Edit').show();
        
        if(record){
        	edit.down('form').loadRecord(record);
        }
    },
    
    updateUser: function(button) {
        var win    = button.up('window'),
            form   = win.down('form'),
            record = form.getRecord(),
            values = form.getValues();
        
        
		if (values.id > 0){
			record.set(values);
		} else{
			record = Ext.create('Bly.model.User');
			record.set(values);
			record.setId(0);
			this.getContactsStore().add(record);
		}
        
		win.close();
        this.getContactsStore().sync();
    },
    
    deleteUser: function(button) {
    	
    	var grid = this.getContactlist(),
    	record = grid.getSelectionModel().getSelection(), 
        store = this.getContactsStore();

	    store.remove(record);
	    this.getContactsStore().sync();
    }
});
