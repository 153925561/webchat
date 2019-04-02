package org.cdlg.sunrise.service;

import java.util.List;

import org.cdlg.sunrise.entity.Space;

public interface SpaceService {

	//增加个人空间信息
 public void addSpace(Space space);	
 
 
 	//读取个人空间动态
 public List<Space> readSpace(Integer userid);

   //读取好友空间动态
public List<Space> readFriendSpace(Integer id);
 
	
}
