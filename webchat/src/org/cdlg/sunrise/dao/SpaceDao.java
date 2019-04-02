package org.cdlg.sunrise.dao;

import java.util.List;

import org.cdlg.sunrise.entity.Space;

public interface SpaceDao {

	
/*
 * 增加个人空间的方法
 * @param space
 * 
 * */
	public void addSpace(Space space);
	
	
 /*
  * 读取个人空间动态	
  * */	
	public List<Space> readSpace(Integer userid);

/*
 * 读取好友空间动态
 * */
	public List<Space> readFriendSpace(Integer userid);
	
	
	
	
	
}
