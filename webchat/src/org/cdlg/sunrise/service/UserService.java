package org.cdlg.sunrise.service;
import java.util.List;
import java.util.Map;

import org.cdlg.sunrise.entity.User;
public interface UserService {

	/**
	 * 注册业务
	 * */
	public void register (User user);
	/**
	 * 登录业务
	 * */
	public Boolean login(String userid,String password);
	/**
	 * 个人信息查询业务
	 * */
	public User findOneUser(String userid) ;
	/**
	 * 好友信息查询业务
	 * */
	public User findfriend(int friendid);
	
	/**
	 * 个人信息更新业务
	 * */
	public void UpdateMessage(String userid,String nickname,String profile,int age);
	/**
	 * 头像信息更新业务
	 * */
	public void UpdateHead(String userid,String filename);
	/**
	 * 密码修改业务
	 * */
	public void UpdatePassword(String userid, String newpass);
	/*
	 * 查询可添加好友列表业务
	 * */
	public List<User> FindFriend(Map map);
	public List<User> FindFriends(Map<String, Object> map);
	/**
	 * 添加好友
	 * */
    public int AddFriend(int friendid,int mineid);
  
	/*
	 * 查询好友列表
	 * */
	public List<User> FindMyFriend(int id);
	
	/*
	 *根据ID查询个人信息
	 * */
	public User findOneUserById(Integer id);
	
	/*
	 *删除好友 
	 * */
	public void deleteFriend(Integer userid, Integer friendid);
	
}
