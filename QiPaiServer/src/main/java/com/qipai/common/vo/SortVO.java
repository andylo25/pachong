package com.qipai.common.vo;

import java.io.Serializable;

public class SortVO implements Serializable{
	private String property;
	private String direction;
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
}
