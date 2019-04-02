package org.cdlg.sunrise.service;

import java.util.List;

import org.cdlg.sunrise.entity.Message;

public interface MessageService {
	
	
 /* 读取消息列表 */	
public List<Message> readMessages(Message message);


/*处理验证消息*/
public int UpdateMessageStatus(int friendid,int mineid,int isagree);

/*读取聊天内容*/

public List<Message> ReadChatMessage(Message message);

/*添加聊天消息*/
 
public void AddChatMessage(Message message);
}
