package org.cdlg.sunrise.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Alert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.cdlg.sunrise.entity.Message;
import org.cdlg.sunrise.entity.Reply;
import org.cdlg.sunrise.entity.Space;
import org.cdlg.sunrise.entity.User;
import org.cdlg.sunrise.service.MessageService;
import org.cdlg.sunrise.service.SpaceService;
import org.cdlg.sunrise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sun.org.apache.xerces.internal.impl.xs.util.StringListImpl;


@Controller
public class UserController {

	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private MessageService messageservice;
	
	@Autowired
	private SpaceService spaceservice;
	

	/**
	 * 
	 * 登录
	 */
	
	@RequestMapping("/dologin")
	public String login(String userid,String password,HttpSession session){
		
		Boolean lo = this.userservice.login(userid, password);
		
		if (lo)
		{
			session.setAttribute("username", userid);
			User findOneUser = this.userservice.findOneUser(userid);
			//System.out.println(findOneUser.getFirsttime());
			session.setAttribute("user", findOneUser);
			return ("redirect:/doreadmessage");
			
		}
		return ("redirect:/login.html");		
	}
	
	

	/**
	 * 
	 * 信息显示
	 */
	
	@RequestMapping("/doShow")
	public String ShowMessage(HttpSession session){
		String userid=(String) session.getAttribute("username");
		User findOneUser = this.userservice.findOneUser(userid);
		//System.out.println(findOneUser.getFirsttime());
		session.setAttribute("user", findOneUser);
		return "information";
		
	}

	/**
	 * 
	 * 信息更新
	 */
	@RequestMapping("/doUpdateMessage")
	
	public String UpdateMessage(String nickname,String profile,int age,HttpSession session){
		
		 String userid = (String) session.getAttribute("username");
		 this.userservice.UpdateMessage(userid, nickname, profile, age);
		return "redirect:/doShow";	
	}
	
	
	/**
	 * 
	 * 文件上传(头像上传)
	 */
	 
	@RequestMapping("/doUpload")
	public String Upload(@RequestParam("file") MultipartFile file,HttpSession session){
		//将file写入服务器的某个目录下
        //先获取文件名称
		String filename = file.getOriginalFilename();
		InputStream   input =null;
		OutputStream output =null;
		try {
			input=file.getInputStream();
			output= new FileOutputStream("F:\\tomcat\\apache-tomcat-8.0.53\\webapps\\headimgaes\\"+filename);
			IOUtils.copy(input, output);
			
			 
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				input.close();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		//获取userid
		String userid = (String) session.getAttribute("username");
		//调用头像更新函数，将头像信息存入数据库
		this.userservice.UpdateHead(userid,filename);
		//返回个人设置页面
		return "info-setting";	
	}
	
	
	/**
	 * 
	 * 密码修改
	 */
	
	@RequestMapping("/dochangepassword")
	public String ChangePassword(String oldpass , String newpass, String rnewpass,HttpSession session){	
		 String userid = (String) session.getAttribute("username");	 
		 User oneuser = this.userservice.findOneUser(userid);		 
		 if( !oldpass.equals(oneuser.getPassword()))
		 {
			 session.setAttribute("errormsg", "密码错误");
			 return "info-setting";	
		 }
		  else 
			  this.userservice.UpdatePassword(userid , newpass);
		      return "info-setting";	
		
	}
	
	/**
	 * 
	 * 查找可添加好友列表
	 */
	@RequestMapping("/dofindfriend")
	
	public String FindFriend(HttpSession session,String findid,String nickname,HttpServletRequest req,Integer nowpage){		
		//判断页码
		if(nowpage==null||nowpage==0)
		{
			nowpage = 1;	
		}
		//判断登录信息，如失效则重新登录
		User user=(User) session.getAttribute("user");
		if(user==null)
		 return ("redirect:/login.html");	
		//创建查询信息图
		Map<String, Object> map =new HashMap<String, Object>();
		String userid=(String) session.getAttribute("username");
		map.put("userid", userid);
		map.put("mineid", user.getId());
		map.put("searchid",findid );
		map.put("searchnickname", nickname);
		map.put("startindex", (nowpage-1)*3);
		//查询数据
		List<User> listfriend = this.userservice.FindFriend(map);
		List<User> listfriends = this.userservice.FindFriends(map);
		double x=listfriends.size();
		int y= (int) Math.ceil(x/3.0);
		//保存数据
		req.setAttribute("listfriend", listfriend);
		if(nowpage+1<y)
			{req.setAttribute("nextpage", nowpage+1);}
		else 
			{req.setAttribute("nextpage", y);}
		req.setAttribute("lastpage", nowpage-1);
		req.setAttribute("finalpage", y);
		req.setAttribute("firstpage", 1);
		req.setAttribute("findid", findid);
		req.setAttribute("findnickname", nickname);
		return "friend";
	}
	

/*
    * 添加好友
    */
	@RequestMapping("/doaddfirend")
	public String AddFriend(int friendid,HttpSession session){
		//this.userservice.findfriend(friendid);
		//判断登录信息，如失效则重新登录
		User user=(User) session.getAttribute("user");
		if(user==null)
				return ("redirect:/login.html");	
		int mineid = user.getId();
		int res = this.userservice.AddFriend(friendid, mineid);
		//System.out.println(res);
		 if(res==0)
		 {
		 
		     session.setAttribute("msg", "已发送验证信息");	  
		 }
		 else if(res==1)
		 {
			 session.setAttribute("msg", "已添加好友，请刷新页面"); 
			 
		 }
		return "redirect:/dofindfriend";
	}
	
/*
   * 读取消息列表
   * 
   * */
	 @RequestMapping("/doreadmessage")
	 public String ReadMessage(HttpSession session,HttpServletRequest request){
		 User user=(User) session.getAttribute("user");
		 Message mes =new Message();
		 mes.setToid(user.getId());
		 mes.setType(1);//1表示验证消息
		 mes.setStatus(0);//0表示未处理
		 List<Message> meslist = messageservice.readMessages(mes);
		 request.setAttribute("messagelist", meslist);
		 request.setAttribute("messagelistcount", meslist.size());
		 return "index";
	 }
	 
	
	
/**
    * 处理验证消息
    * 更改消息状态
	* 
	*/
@RequestMapping("/doupdatemessage")
public String UpdateMessageStatus(HttpSession session,int friendid,int isagree){
	//System.out.println("111");
	User user=(User) session.getAttribute("user");
	messageservice.UpdateMessageStatus(friendid, user.getId(), isagree);
	return "redirect:/doreadmessage";
}
	
/**
     * 显示好友列表
     * 
     */
@RequestMapping("/dofindmyfriend")
@ResponseBody
public Map<String, Object> FindMyFriend( HttpSession session){
	User user = (User) session.getAttribute("user");
	List<User> findMyFriend = userservice.FindMyFriend(user.getId());
	Map<String, Object> pmap=new HashMap<String, Object>();
	pmap.put("friendlist", findMyFriend);
	pmap.put("friendlistcount", findMyFriend.size());	
	return pmap;
}


/***
	 * 读取聊天内容
	 * 
	 * AJAX请求方式
	 * 
	 * */
@RequestMapping("/doreadchatmessage")
@ResponseBody
public Map<String, Object> ReadChatMessage(HttpSession session,Integer friendid,HttpServletRequest request){
	//System.out.println("1");
	 User user = (User) session.getAttribute("user");
	 Message message =new Message();
	 message.setFromid(friendid);
	 message.setToid(user.getId());
	 message.setType(0);
	 List<Message> ChatMessageList = messageservice.ReadChatMessage(message);
	 User friend = userservice.findOneUserById(friendid);
	// System.out.println(ChatMessageList);
	 Map<String, Object> map=new HashMap<String, Object>();
	 map.put("ChatMessageList", ChatMessageList);
	 map.put("friend", friend);
	 map.put("user", user);
	 return  map;


}


/*
 * 添加聊天消息	
 * 
 * AJAX请求
 * 
 * */

@RequestMapping("/doaddchatmessage")
@ResponseBody
public String AddChatMessage(HttpSession session,Integer friendid,String content){
	User user = (User) session.getAttribute("user");
	Message message =new Message();
	message.setContent(content);
	message.setFromid(user.getId());
	message.setToid(friendid);
	message.setType(0);
	messageservice.AddChatMessage(message);
	return "success";
}



/*
 * 上传图片
 * 
 * 
 * 
 * */
@RequestMapping("/doupimg")
@ResponseBody
public Object upfile(@RequestParam("file")MultipartFile file,HttpServletRequest request){
	Map<String, Object>map =new HashMap<String, Object>();
	String path="F:/tomcat/apache-tomcat-8.0.53/webapps/spaceimages/";
	try {
		file.transferTo(new File(path+file.getOriginalFilename()));
		map.put("imgurl",file.getOriginalFilename());
		map.put("msg","上传成功");
		
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
    return map;
}


/*
 * 添加个人空间动态
 * 
 * 
 * */

@RequestMapping("/doaddspace")
public String addSpace(HttpSession session,Space space,String content,String pics){
	User user =(User) session.getAttribute("user");
	System.out.println(content);
	System.out.print(pics);
	space.setUserid(user.getId());
	space.setNickname(user.getNickname());
	space.setContent(content);
	space.setPics(pics);
	spaceservice.addSpace(space);
	return "redirect:/index.jsp";
}


/*
 * 读取个人空间动态
 * 
 * */

@RequestMapping("/doreadspace")
public String  ReadSpace(HttpSession session,HttpServletRequest request){
	User user=(User) session.getAttribute("user");
    List<Space> readSpaceList = spaceservice.readSpace(user.getId());
    request.setAttribute("spaceList", readSpaceList);  
	return "user_space";
	
}


/*
 * 读取好友空间动态
 * 
 * */
 @RequestMapping("/doreadfriendspace")
 public String ReadFriendSpace(HttpSession session,HttpServletRequest request){
	    User user=(User) session.getAttribute("user");	
	    List<Space> readFriendSpaceList = spaceservice.readFriendSpace(user.getId()); 	  
	    request.setAttribute("friendspaceList", readFriendSpaceList);  
		return "friend";
	   	
 }
 
 
 /*
  * 删除好友
  * 
  * */

 @RequestMapping("/dodeletefriend")
 @ResponseBody
 public String DeleteFriend(HttpSession session,Integer friendid){
	  User user =(User) session.getAttribute("user");
	  userservice.deleteFriend(user.getId(),friendid);
      return "success";
 }
 
 
 
 




}
