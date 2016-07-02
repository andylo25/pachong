package com.qipai.common.game.comp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

	private List<Card> list = Arrays.asList(Card.values());
	private int pos = 0;
	
	public Deck() {
		this(true);
	}
	
	public Deck(boolean isSort) {
		if(isSort){
			Collections.shuffle(list,new Random());
		}
	}
	
	public Card next(){
		return list.get(pos++);
	}
	
	public Card[] next(int size){
		int oldPos = pos;
		pos += size;
		return list.subList(oldPos, pos).toArray(new Card[0]);
	}
	public void refresh(){
		pos = 0;
		Collections.shuffle(list,new Random());
	}
	
	public Card[] refreshRestAndGet(int num){
		Collections.shuffle(list.subList(pos-num,list.size()),new Random());
		return list.subList(pos-num, pos).toArray(new Card[0]);
	}
	
	/**
	 * 选择指定号码的牌,没有则返回null
	 * @param num
	 * @return
	 */
	public Card selectByNum(int num){
		Card result = null;
		for(int i=pos;i<list.size();i++){
			if(list.get(i).getNum() == num){
				result = list.get(i);
				if(i != pos){
					Collections.swap(list, pos, i);
				}
				pos++;
				break;
			}
		}
		return result;
	}
	
}
