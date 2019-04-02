package org.cdlg.sunrise.service.Impl;

import java.util.List;

import org.cdlg.sunrise.dao.ReplyDao;
import org.cdlg.sunrise.dao.SpaceDao;
import org.cdlg.sunrise.dao.UserDao;
import org.cdlg.sunrise.entity.Reply;
import org.cdlg.sunrise.entity.Space;
import org.cdlg.sunrise.entity.User;
import org.cdlg.sunrise.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="spaceService")
public class SpaceServiceImpl implements SpaceService {

	@Autowired
    SpaceDao spacedao;
	
	@Autowired
	ReplyDao replyDao;
	
	@Autowired
	UserDao userDao;
	
	public ReplyDao getReplyDao() {
		return replyDao;
	}

	public void setReplyDao(ReplyDao replyDao) {
		this.replyDao = replyDao;
	}

	public SpaceDao getSpacedao() {
		return spacedao;
	}

	public void setSpacedao(SpaceDao spacedao) {
		this.spacedao = spacedao;
	}

	
	
	/*
	 * 增加个人空间信息
	 * 
	 * */
	@Override
	public void addSpace(Space space) {
		// TODO Auto-generated method stub
		spacedao.addSpace(space);
		
	}
     

	/*
	 * 读取个人空间动态和评论
	 * @param userid
	 * */
	@Override
	public List<Space> readSpace(Integer userid) {
		// TODO Auto-generated method stub
		List<Space> readSpaceList = spacedao.readSpace(userid);
		for (int i = 0; i < readSpaceList.size(); i++) {
			Space space=readSpaceList.get(i);
			List<Reply> replyList = replyDao.findReplyById(space.getId());
			space.setReplyList(replyList);
		}
		return readSpaceList;
	}

	
	/*
	 * 读取好友空间动态
	 * @param userid
	 * */
	@Override
	public List<Space> readFriendSpace(Integer userid) {
		// TODO Auto-generated method stub
		
		//System.out.println(userid);
		List<Space> readFriendSpaceList = spacedao.readFriendSpace(userid);
		//System.out.println(readFriendSpaceList);
		for (int i = 0; i < readFriendSpaceList.size(); i++) {
			Space space=readFriendSpaceList.get(i);
			List<Reply> replyList = replyDao.findReplyById(space.getId());
			space.setHeadimg(userDao.findhead(space.getId()));
			space.setReplyList(replyList);
				}
				return readFriendSpaceList;
	
	}
	
	
	 
}
