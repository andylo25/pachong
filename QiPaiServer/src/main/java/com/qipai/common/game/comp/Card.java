package com.qipai.common.game.comp;

import java.io.Serializable;


public enum Card implements Serializable{

	AceC(1,1),AceD(1,2),AceH(1,3),AceS(1,4),
	TwoC(2,1),TwoD(2,2),TwoH(2,3),TwoS(2,4),
	ThreeC(3,1),ThreeD(3,2),ThreeH(3,3),ThreeS(3,4),
	FourC(4,1),FourD(4,2),FourH(4,3),FourS(4,4),
	FiveC(5,1),FiveD(5,2),FiveH(5,3),FiveS(5,4),
	SixC(6,1),SixD(6,2),SixH(6,3),SixS(6,4),
	SevenC(7,1),SevenD(7,2),SevenH(7,3),SevenS(7,4),
	EightC(8,1),EightD(8,2),EightH(8,3),EightS(8,4),
	NineC(9,1),NineD(9,2),NineH(9,3),NineS(9,4),
	TenC(10,1),TenD(10,2),TenH(10,3),TenS(10,4),
	JackC(11,1),JackD(11,2),JackH(11,3),JackS(11,4),
	QueenC(12,1),QueenD(12,2),QueenH(12,3),QueenS(12,4),
	KingC(13,1),KingD(13,2),KingH(13,3),KingS(13,4),
	BlackJoker(14,1),RedJoker(14,2); // 大王小王的花色：小王-梅花，大王-方块
	
	private String name;
	private int num;
	private CardFace cf;
	private Card(int num,int cf) {
		this.num = num;
		this.setCf(CardFace.values()[cf-1]);
		initName();
	}
	private Card(int num,CardFace cf) {
		this.num = num;
		this.setCf(cf);
		initName();
	}
	
	public int getId(){
		return (num-1)*4+cf.num;
	}
	
	public Card get(int num,int cf){
		return Card.values()[(num-1)*4+cf-1];
	}
	
	public String toString(){
		return getName()+":"+getId();
	}
	
	private void initName(){
		switch (num) {
		case 1:
			name = cf.name+"A";
			break;
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
			name = cf.name+num;
			break;
		case 11:
			name = cf.name+"J";
			break;
		case 12:
			name = cf.name+"Q";
			break;
		case 13:
			name = cf.name+"K";
			break;
		case 14:
			switch (cf) {
			case club:
				name = "小王";
				break;
			case diamond:
				name = "大王";
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public CardFace getCf() {
		return cf;
	}

	public void setCf(CardFace cf) {
		this.cf = cf;
	}

	public enum CardFace{
		club(1),diamond(2),heart(3),spade(4);
		
		private String name;
		private int num;
		private CardFace(int num) {
			this.num = num;
			initName();
		}
		private void initName() {
			switch (num) {
			case 1:
				name = "梅花";
				break;
			case 2:
				name = "方块";
				break;
			case 3:
				name = "红桃";
				break;
			case 4:
				name = "黑桃";
				break;
			default:
				name = "";
				break;
			}
		}
		
		public CardFace get(int num){
			return CardFace.values()[num-1];
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		
		
	}
	
	
}


