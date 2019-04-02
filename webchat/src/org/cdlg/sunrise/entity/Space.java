package org.cdlg.sunrise.entity;

import java.util.ArrayList;
import java.util.List;

public class Space {
	
	
	 private Integer id;
	 private Integer userid;
	 private String nickname;
	 private String content;
	 private String pics; //图片之间使用，分开  例如1.jpg，2.jpg
	 private String creatdate;
	 private Integer replynum;
	 private List<String> picList =new ArrayList<String>();//使用集合保存单个图片路径
	 private List<Reply> replyList=new ArrayList<Reply>();
	 private String headimg;

	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public List<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}
	public List<String> getPicList() {
		//如果存在多张图片	
		if(pics!=null && pics.indexOf(",")!=-1)
		{
	    //利用,将pics分割
		String[] array = pics.split(",");
		 for (int i = 0; i < array.length; i++) {
			 if (array[i]!=null && !array[i].equals("") )
			 {
				 picList.add(array[i]);				 
			 }	
		   }
		 }else {
			picList.add(pics);
		 }
		return picList;
	}
	public void setPicList(List<String> picList) {
		this.picList = picList;
	}
	 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
	}
	public String getCreatdate() {
		return creatdate;
	}
	public void setCreatdate(String creatdate) {
		this.creatdate = creatdate;
	}
	public Integer getReplynum() {
		return replynum;
	}
	public void setReplynum(Integer replynum) {
		this.replynum = replynum;
	}
	
}	
	 
	 
	 
	 
	 
	
	
	
	
	
	


