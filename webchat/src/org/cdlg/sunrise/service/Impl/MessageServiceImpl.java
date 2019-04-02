package org.cdlg.sunrise.service.Impl;

import java.util.List;

import org.cdlg.sunrise.dao.MessageDao;
import org.cdlg.sunrise.dao.UserDao;
import org.cdlg.sunrise.entity.Message;
import org.cdlg.sunrise.entity.User;
import org.cdlg.sunrise.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDao messagedao;
	
	@Autowired
	private UserDao userdao;
	
	
	/*
	 * 读取消息
	 * 
	 * */
	@Override
	public List<Message> readMessages(Message message) {
		// TODO Auto-generated method stub
		List<Message> messagelist = messagedao.readMessage(message);
		
		return messagelist;
	}

     /*/
      * 
      * 处理验证消息
      * 更改消息状态
      * isagree=0 拒绝
      * isagree=1 同意
      * 
      * */
	@Override
	@Transactional
	public int UpdateMessageStatus(int friendid,int mineid,int isagree) {
		// TODO Auto-generated method stub
		Message message=new Message(); 
		message.setFromid(friendid);
		message.setToid(mineid);
		message.setType(1);//好友验证消息
	    messagedao.UpdateMessageStatus(message);
		if(isagree==1)
		{
			userdao.AddFriend(friendid, mineid);
			userdao.AddFriend(mineid, friendid);
		}
		return 1;
	}

	/*
	 * 读取未读消息
	 * 更新消息状态
	 * 
	 * */
	@Override
	public List<Message> ReadChatMessage(Message message) {
		// TODO Auto-generated method stub
	    messagedao.UpdateMessageStatus(message);

	    return messagedao.ReadChatMessage(message);
			
		
	
	}

	/*
	 * 添加聊天消息
	 * 
	 * */
	@Override
	public void AddChatMessage(Message message) {
		// TODO Auto-generated method stub
		 messagedao.addMessage(message);
		
	}


	
	
	
	
	
	
	
	
	
	
}
