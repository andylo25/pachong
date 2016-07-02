package com.qipai.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.qipai.common.game.comp.Card;
import com.qipai.common.game.comp.Deck;
import com.qipai.game.reward.ThsReward;

public class Game {

	static Deck deck = new Deck();
	static Card[] cards = null;
	public static void main(String[] args) {
//		test1();
		test2();
	}
	
	public static void test1() {
		long start = System.currentTimeMillis();
		for(int i=0;i<10000;i++){
			open();
//			swap(RandomUtils.nextInt()%5,RandomUtils.nextInt()%5);
//			System.out.println(cards);
			int rwd = checkReward();
			if(rwd > 0){
				Arrays.sort(cards);
				System.out.println(Arrays.toString(cards));
				System.out.println(rwd);
			}
		}
		System.out.println(System.currentTimeMillis()-start);
	}
	
	private static void test2(){
		cards = new Card[]{Card.ThreeS,Card.TwoD,Card.NineC,Card.FiveS,Card.RedJoker};
		int rwd = checkReward();
		if(rwd > 0){
			Arrays.sort(cards);
			System.out.println(Arrays.toString(cards));
			System.out.println(rwd);
		}
	}

	private static int checkReward() {
		List<Integer> list = new ThsReward(cards).getRwdIds();
		return list.isEmpty()?0:list.get(0);
	}

	private static void swap(int... pos) {
		for(int i=0;i<cards.length;i++){
			if(ArrayUtils.contains(pos, i))continue;
			cards[i]=deck.next();
		}
	}

	private static void open() {
		deck = new Deck();
		cards = deck.next(5);
	}
	
	

}
