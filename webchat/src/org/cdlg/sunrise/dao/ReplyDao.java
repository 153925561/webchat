package org.cdlg.sunrise.dao;

import java.util.List;

import org.cdlg.sunrise.entity.Reply;

public interface ReplyDao {

	/*
	 * 根据空间ID 查询评论列表
	 * @param sid
	 * 
	 * */
	public List<Reply> findReplyById(Integer sid);
}
