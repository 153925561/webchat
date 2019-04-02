package org.cdlg.sunrise.dao;


import java.util.List;

import org.cdlg.sunrise.entity.Message;

public interface MessageDao {

	
	/*发送添加好友验证信息*/
	public void addMessage(Message message);
	
	/*读取消息列表*/
	public List<Message> readMessage(Message message);
	
	/*处理验证消息*/
	public int UpdateMessageStatus(Message message);
	
	/*获取未读消息列表	*/
	public int FindMessageCount(Message message);
	
	/*读取聊天内容*/
	public List<Message> ReadChatMessage(Message message);
	
}
