package com.qipai.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseRoom<M extends BaseRoom<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setRoomName(java.lang.String roomName) {
		set("room_name", roomName);
	}

	public java.lang.String getRoomName() {
		return get("room_name");
	}

}
