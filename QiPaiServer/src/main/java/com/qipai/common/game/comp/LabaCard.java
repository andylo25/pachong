package com.qipai.common.game.comp;

public enum LabaCard {
	// One-10
	One(1),Two(2),Three(3),Four(4),Five(5),Six(6),Seven(7),Eight(8),Nine(9),Ten(10),Ele(11),Any(12),Bar(13);
//	private String name;
	private int id;
	
	private LabaCard(int id) {
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public static LabaCard getCardById(int id){
		return LabaCard.values()[id-1];
	}
	
}
