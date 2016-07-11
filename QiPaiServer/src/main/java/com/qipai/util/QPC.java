package com.qipai.util;

import java.util.Arrays;
import java.util.List;

import com.qipai.common.game.comp.Card;

public class QPC {

	/**
	 * 错误码定义
	 */
	public static final int ECD_0 = 0; //成功
	public static final int ECD_999 = 999; // 未知异常
	public static final int ECD_998 = 998; // 用户会话失效
	public static final int ECD_997 = 997; // 操作异常
	public static final int ECD_100 = 100; // 用户名已存在
	public static final int ECD_101 = 101; // 用户名或密码错误
	
	public static final int ECD_110 = 110; //游戏没有开放
	public static final int ECD_111 = 111; //没有权限进入
	
	public static final int ECD_120 = 120; //金币不足
	
	/**
	 * 游戏Id
	 * @return
	 */
	public static final int GAME_100 = 100; // 百搭玩法
	public static final int GAME_101 = 101; // 拉霸游戏
	
	/**
	 * 中奖
	 * @return
	 */
	public static final int BINGO_1 = 1; // 五张
	public static final int BINGO_2 = 2; // 皇家同花顺
	public static final int BINGO_3 = 3; // 同花顺
	public static final int BINGO_4 = 4; // 四张
	public static final int BINGO_5 = 5; // 葫芦
	public static final int BINGO_6 = 6; // 同花
	public static final int BINGO_7 = 7; // 顺子
	public static final int BINGO_8 = 8; // 三张
	public static final int BINGO_9 = 9; // 两对
	public static final int BINGO_10 = 10; // 8对以上
	
	
	public static final int BINGO_OPS = -1; // 没猜中
	public static int fanpai_doub_times = 8;//翻牌赢分加倍次数
	public static int temp_rank_num = 5;//临时排行返回人数
	public static final int INVALID = -1; 
	public static final int FANPAI_CARD_NUM = 5; //翻牌数
	
	/**
	 * 出现概率控制
	 */
	public static int JOKER_PERCENT = 100; //大小王出现概率
	public static int FIVE_PERCENT = 100; //五张出现概率
	public static int HJTH_PERCENT = 100; //皇家同花顺
	public static int TH_PERCENT = 100; //同花顺
	
	
	/**
	 * 线程执行任务类型
	 * @return
	 */
	public static final byte TASK_RANK = 1; // 排行榜刷新计划
	public static final byte TASK_DAY = 10; // 每天执行计划
	
	public static List<Card> cardsNum(){
		List<Card> cardsNum  = Arrays.asList(Card.values());
		return cardsNum;
	}
	
	public enum Role{
		ZHUANG,XIAN,QITA
	}
	
	public enum ZhuangDian{
		One,Tow,Three
	}
	
	public enum GameState{
		Start,Deal,Doub,End
	}
	
	public static final String USER_SESSION_KEY = "USER_SESSION_KEY_";//存放GameUser
	
	public static final String LOGIN_JSP = "/login.jsp";
	
	public static final String vip_admin_trans = "2";//可以操作转账的用户vip级别
	
	public static final String vip_admin = "1";//可以登录管理后台的用户vip级别
	
	// 微信支付交易请求地址
	public static final String wx_reqUrl = "https://api.dinpay.com/gateway/api/weixin";
	
}
