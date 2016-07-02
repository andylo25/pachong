package com.qipai.service;

import com.qipai.common.game.comp.PayVO;
import com.qipai.common.model.User;

public interface IUserService {
	
	public User login(User user);
	
	public int regist(User user);
	
	public void pay(PayVO payVO);
	
	public void addTransSerial(User user,User dest,int coin);

	public void charge(PayVO payVO);

}
