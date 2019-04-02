package org.cdlg.sunrise.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.cdlg.sunrise.entity.User;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;



public interface UserDao {

	/*查找用户*/
	public List<User> findAll();
	
	/*添加用户*/
	public void addUser(User user);
	
	/*匹配用户个人信息*/
	public List<User> findUserByUsernameAndPassword( Map map);
	
	/*查找用户个人信息*/
	public User findOneUser(String userid);
	
	/*根据ID查找头像*/
	public String findhead(Integer id);
	
	/*查找好友个人信息*/
	public User findfriend(int friendid);
	
	/*更新用户个人信息*/
	public void UpdateMessage(User user);
	
	/*更新用户头像信息*/
	public void UpdateHead(User user);
	
	/*修改密码*/
	public void UpdatePassword(User user);
	
	/*分页查询可添加好友列表  */
	public List<User> FindFriend(Map map);
	
	/*查询可添加好友列表 */
	public List<User> FindFriends(Map<String, Object> map);
	
	/*增加好友
	 * 使用注解标识每个参数
	 *  */
	public int AddFriend(@Param("friendid")int friendid,@Param("mineid")int mineid);
	
	/**
	 * 查看好友列表
	 * */
	public List<User> FindMyFriend(@Param("mineid")int id);
	
	/*根据id查找用户个人信息*/
	public User findOneUserById( Integer id);

	/*删除好友*/
	public void deleteFriend(@Param("userid")Integer userid, @Param("friendid")Integer friendid);
	
	
}
