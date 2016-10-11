package com.andy.pc.vo;

import java.io.Serializable;

public class DataVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;
	
	private byte[] torrent;
	
	private String desc;
	
	private String metadata;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getTorrent() {
		return torrent;
	}

	public void setTorrent(byte[] torrent) {
		this.torrent = torrent;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	
	
	
}
