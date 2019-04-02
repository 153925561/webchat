package org.cdlg.sunrise.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.cdlg.sunrise.dao.MessageDao;
import org.cdlg.sunrise.dao.UserDao;
import org.cdlg.sunrise.entity.Message;
import org.cdlg.sunrise.entity.User;
import org.cdlg.sunrise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl  implements UserService{

	@Autowired
	private UserDao userdao;

    @Autowired
   private MessageDao messagedao;
	
	public UserDao getUserdao() {
		return userdao;
	}


	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}


	
/*
	 * 注册函数
	 * 
	 * */
	@Override
	@Transactional
	public void register(User user) {
		this.userdao.addUser(user);
		
	}


/*
	 * 登录函数
	 * 
	 * */
	@Override
	public Boolean login(String userid,String password) {
		// TODO Auto-generated method stub
		
	    Map map=new HashMap();
		map.put("username", userid);
		map.put("password", password);
		List<User> list = this.userdao.findUserByUsernameAndPassword(map);
		
		Boolean bo =false;
		if(list!=null&&list.size()>0){
			bo=true;
			return bo;
		}
	
		return bo;
	}
	
/*
	 * 个人信息查询函数
	 * 
	 * */

	@Override
	public User findOneUser(String userid) {
		// TODO Auto-generated method stub
		User user =	this.userdao.findOneUser(userid);
		
		return user;
	
	}

/**
	   * 
	   * 查询好友信息
	   * */
		@Override
     public User findfriend(int friendid) {
			// TODO Auto-generated method stub
			User friend = this.userdao.findfriend(friendid);
			return friend;
		}
	
	
	
/*
	 * 个人信息更新函数
	 * 
	 * */
	@Override
	@Transactional
	public void UpdateMessage(String userid, String nickname, String profile,
			int age) {
		// TODO Auto-generated method stub
		User user=new User();
		user.setUserid(userid);
		user.setNickname(nickname);
		user.setProfile(profile);
		user.setAge(age);
		this.userdao.UpdateMessage(user);					
	}
/*
	 * 头像更新函数
	 * 
	 * */
	@Override
	@Transactional
	public void UpdateHead( String userid,String filename) {
		// TODO Auto-generated method stub
		User user=new User();
		user.setUserid(userid);
		user.setProfilehead(filename);
		this.userdao.UpdateHead(user);
	}
/*
	 * 密码修改函数
	 * 
	 * */
	@Override
	@Transactional
	public void UpdatePassword(String userid, String newpass) {
		// TODO Auto-generated method stub
		User user=new User();
		user.setUserid(userid);
		user.setPassword(newpass);
		this.userdao.UpdatePassword(user);
		
		
	}

/*
     * 查询可添加好友列表
     * 
     * */
	 
	@Override
	public List<User> FindFriend(Map map) {

       List<User> friendlist = this.userdao.FindFriend(map);
		return friendlist;
	}

/*
	* 查询可添加好友总数
	* 
    * */
	@Override
	public List<User> FindFriends(Map<String, Object> map) {

		List<User> friendlists = this.userdao.FindFriends(map);
		return friendlists;
	}
	
/*
	 * 
	 * 
	 * 添加好友
	 * 
	 * */
	@Override
	@Transactional
	public int AddFriend(int friendid, int mineid) {
		// TODO Auto-generated method stub
		User findfriend = userdao.findfriend(friendid);
		if(findfriend.getChecktype()==1) {
		
		 userdao.AddFriend(friendid, mineid);
		 userdao.AddFriend( mineid , friendid);
		}
		 else  if(findfriend.getChecktype()==2){
			 User findmine = userdao.findfriend(mineid);
			 Message message = new Message();
			 message.setFromid(mineid);
			 message.setToid(friendid);
			 message.setType(1);//好友验证消息
			 message.setContent(findmine.getNickname()+",想要添加你为好友");
			 messagedao.addMessage(message); 
			 return 0;
		}
		return 1;
	}


	
	
/*
	 * 
	 * 显示好友列表和未读信息
	 * 
	 * */
	@Override
	public List<User> FindMyFriend(int id) {
		// TODO Auto-generated method stub
		
		List<User> findMyFriend = userdao.FindMyFriend(id);
		
		//读取每个好友的消息
		for (User user : findMyFriend) {
			Message message =new Message();
			message.setToid(id);
			message.setFromid(user.getId());
			int MesCount = messagedao.FindMessageCount(message);
			user.setMessagecount(MesCount);
		}
		
		return findMyFriend;
	}

	
/*
	 * 
	 * 根据ID查询个人信息
	 * 
	 * */
	@Override
	public User findOneUserById(Integer id) {
		// TODO Auto-generated method stub
		
		
		return userdao.findOneUserById(id);
	}


	/*
	 * 删除好友
	 * */
	@Override
	@Transactional
	public void deleteFriend(Integer userid,Integer friendid) {
		// TODO Auto-generated method stub
		userdao.deleteFriend(userid,friendid);
		
	}

 


	
	


	



}
