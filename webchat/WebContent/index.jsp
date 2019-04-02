<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>WebChat | 聊天</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="static/plugins/amaze/css/amazeui.min.css">
    <link rel="stylesheet" href="static/plugins/amaze/css/admin.css">
    <link rel="stylesheet" href="static/plugins/contextjs/css/context.standalone.css">
    <script src="static/plugins/jquery/jquery-2.1.4.min.js"></script>  
    <script src="static/plugins/amaze/js/amazeui.min.js"></script>
    <script src="static/plugins/amaze/js/app.js"></script>
    <script src="static/plugins/layer/layer.js"></script>
    <script src="static/plugins/laypage/laypage.js"></script>
    <script src="static/plugins/contextjs/js/context.js"></script>    
    <script type="text/javascript">
   function showMessageDiv(){
	  document.getElementById("checkmessageDiv").style.display="block";}                              
    
   function docheckmessage(friendid,isagree){
    	 //隐藏提示框	
    	 document.getElementById("checkmessageDiv").style.display="none";
    	 //跳转页面	
    	 location.href="/webchat/doupdatemessage?friendid="+friendid+"&isagree="+isagree;	 
     }
    //AJAX方法
   function findmyfriend(){
    	
    	$.post("/webchat/dofindmyfriend",null,function(data){
    		document.getElementById("friendlists").innerHTML="好友列表["+data.friendlistcount+"]";
    		var friendList=data.friendlist;
    		var friendul="";
    			for (var i=0;i<friendList.length;i++){    				   
    				   var friend = friendList[i];
    				 if(friend.messagecount==0)  
    				    friendul+="<ul class='am-list am-list-static am-list-striped' id='list'>"+
    	                "<li>"+friend.nickname+
    	                "<button type='button' class='am-btn am-btn-xs am-btn-primary  am-round' onclick='readchatmessage("+friend.id+");'>"+
    	                "<span class='am-icon-phone'><span> 私聊</button><button type='button' class='am-btn am-btn-xs am-btn-primary  am-round' onclick='deletefriend("+friend.id+");'<span class='am-icon-phone'><span> 删除好友</button></li>"+
    	                "</ul>";  
    	             else
    	            	 friendul+="<ul class='am-list am-list-static am-list-striped'>"+
     		             "<li>"+friend.nickname+"<button class='am-btn am-btn-xs am-btn-danger' id='tuling' data-am-button  onclick='readchatmessage("+friend.id+")'>"+friend.messagecount+"</button><button type='button' class='am-btn am-btn-xs am-btn-primary  am-round' onclick='deletefriend("+friend.id+");'    <span class='am-icon-phone'><span> 删除好友</button></li>"+
     		             "</ul>";  
    				  }
    		//显示好友信息
    		$("#friendpage ul").remove();
    		$("#friendpage").append(friendul);
       	    //document.getElementById("friendspan").innerHTML=friendul;    		
    	});   	
    }
    
    
 //读取聊天消息   
   function readchatmessage(friendid){
	   
	   
	   //isNaN是判断字符串是否是一个数字，true不是数字，false是数字
		if(isNaN(friendid)==false){ 
			$("#friendid").val(friendid);
		} 
		var temp_friendid =$("#friendid").val(); 
		if(temp_friendid == '')
		{
			return;
		} 	 
	     $.post("/webchat/doreadchatmessage",{"friendid":temp_friendid},function(data){
	      
	   //设置聊天对象 	 
		 $("#sendto").html(data.friend.nickname);
		 $("#friendid").val(data.friend.id);
  
       //处理聊天内容
		  var messagList=data.ChatMessageList;
          var messageinfo="";
          for(var i =0;i<messagList.length;i++){
  			var message = messagList[i];	  		 
  			if(message.fromid == data.user.id){
  				messageinfo+="<li class='am-comment am-comment-flip am-comment-primary'><a href='#link-to-user-home'><img width='48' height='48' class='am-comment-avatar' alt='' src='/headimgaes/"+data.user.profilehead+"'></a><div class='am-comment-main'>"+
              "<header class='am-comment-hd'><div class='am-comment-meta'>"+   
              "<a class='am-comment-author' href='#link-to-user'>"+data.user.nickname +"</a> 发表于<time>"+message.time +"</time> 发送给:"+data.friend.nickname+" </div></header><div class='am-comment-bd'> <p>"+message.content +"</p></div></div></li>";
  			} 
  			else{	
  				messageinfo+="<li class='am-comment am-comment-primary'>"+
          "<a href='#link-to-user-home'>"+
          	"<img width='48' height='48' class='am-comment-avatar' alt='' src='/headimgaes/"+data.friend.profilehead +"'></a>"+
          "<div class='am-comment-main'>"+
          "<header class='am-comment-hd'>"+ 
          "<div class='am-comment-meta'>"+
          "<a class='am-comment-author' href='#link-to-user'>"+data.friend.nickname +"</a> 发表于"+
          "<time>"+message.time +"</time> 发送给:"+data.user.nickname +"</div>"+
          "</header>"+
          "<div class='am-comment-bd'> <p>"+message.content+"</p></div>"+
          "</div></li>";
  			} 
  		} 
  		//把聊天消息的内容放进去
  		$("#chat").html(messageinfo);
		  
	  });
  
 }  
 
  //发送聊天消息 
function sendMessage(){
	//获取聊天对象
	var friendid =$("#friendid").val();
	var content=$("#message").val();
	if(friendid==''|| content=='')
		{
		 return;
		}
	//使用AJAX提交聊天内容
	$.post("/webchat/doaddchatmessage",{"friendid":friendid,"content":content},function(data){
		
		$("#message").val("");
		
	});
	
	
	
}
   
   
    //调用
    findmyfriend();
    setInterval(findmyfriend,3000);
    setInterval(readchatmessage,2000);
    
    
    
    
    //删除好友   AJAX
 function deletefriend(friendid){
    	
	 $.post("/webchat/dodeletefriend",{"friendid":friendid},function(data){
		 
	
		 
	 })
    	
    	
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    </script>
</head>
<body>
<header class="am-topbar admin-header">
    <div class="am-topbar-brand">
        <i class="am-icon-weixin"></i> <strong>WebChat</strong> <small>网页聊天室</small>
    </div>
    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>
    <div class="am-collapse am-topbar-collapse" id="topbar-collapse">
        <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
            <li class="am-dropdown" data-am-dropdown >
                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                   ${username}
                   <c:if test="${not empty messagelistcount and messagelistcount !=0}">
                   (<span onclick = "showMessageDiv()">${messagelistcount} </span>)</c:if> 
                   <span class="am-icon-caret-down"></span>      
                </a>
        <div id="checkmessageDiv" style="
        display:none; 
        width:240px;
        background-color: white;
        boder:solid 1px gray;
        position: absolute;
        z-index: 10;
        top:200px;
        left: -150px;
        padding: 5px;">
        <c:forEach var="message" items="${messagelist}">
        ${message.content} 
        <a href="javascript:docheckmessage(${message.fromid},0)">拒绝</a>|
        <a href="javascript:docheckmessage(${message.fromid},1)">同意</a><br/>
        
        </c:forEach>        
        </div>   
         </li>
        </ul>
    </div>
</header>
<div class="am-cf admin-main">
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
        <ul class="am-list admin-sidebar-list">
            <li><a href="index.jsp"><span class="am-icon-commenting"></span> 聊天</a></li>
            <li><a href="/webchat/doShow" class="am-cf"><span class="am-icon-book"></span> 个人信息<span class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span></a></li>
            <li class="admin-parent">
                <a class="am-cf" data-am-collapse="{target: '#collapse-nav'}"><span class="am-icon-cogs"></span> 设置 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav">
                    <li><a href="info-setting.jsp"><span class="am-icon-group"></span> 个人设置</a></li>
                    <li><a href="/webchat/doreadspace"><span class="am-icon-cog"></span>心情动态</a></li>                 
                </ul>
            </li>
           <li><a href="/webchat/doreadfriendspace"><span class="am-icon-globe"></span>好友动态</a></li>        
            <li><a href="log.jsp"><span class="am-icon-inbox"></span> 系统日志<span class="am-badge am-badge-secondary am-margin-right am-fr">24</span></a></li>
            <li><a href="login.jsp"><span class="am-icon-sign-out"></span> 注销</a></li>
        </ul>
        <div class="am-panel am-panel-default admin-sidebar-panel">
            <div class="am-panel-bd">
                <p><span class="am-icon-tag"></span> Welcome</p>
                <p>欢迎使用WebChat聊天室~</p>
            </div>
        </div>
    </div>
</div>
<!-- sidebar end -->
    <!-- content start -->
    <div class="admin-content"  >
        <div class="" style="width: 80%;float:left;">
           <!-- 聊天区 -->
            <div class="am-scrollable-vertical" id="chat-view" style="height: 310px;">
              <ul class="am-comments-list am-comments-list-flip" id="chat">  
        
          
           </ul>
         </div>
 
            
            
          
            <!-- 输入区 -->
            <div class="am-form-group am-form">
                <textarea class="" id="message" name="message" rows="5"  placeholder="这里输入你想发送的信息..."></textarea> 
                <input type="hidden" name="friendid" id="friendid"/>
            </div>
            <!-- 接收者 -->
            <div class="" style="float: left">
                <p class="am-kai">发送给 : <span id="sendto">赵丽丽</span></p>
            </div>
            <!-- 按钮区 -->
            <div class="am-btn-group am-btn-group-xs" style="float:right;"> 
                <button class="am-btn am-btn-default" type="button" onClick="sendMessage()"><span class="am-icon-commenting"></span> 发送</button>
            </div> 
        </div>
        <!-- 列表区 -->
        <div  id="friendpage"class="am-panel am-panel-default" style="float:right;width: 20%;">
            <div class="am-panel-hd">
                <h3 id="friendlists" class="am-panel-title">好友列表 [23]</h3>
            </div>
            
            <ul class="am-list am-list-static am-list-striped" >
                <li>王晓骏 <button class="am-btn am-btn-xs am-btn-danger" id="tuling" data-am-button>3</button></li>
            </ul>
            <ul class="am-list am-list-static am-list-striped" id="list">
            <li>赵丽丽
            <button type="button" class="am-btn am-btn-xs am-btn-primary am-round" onclick="addChat('赵丽丽');"><span class="am-icon-phone"><span> 私聊</button></li>
            </ul>
           
        </div>
    </div>
    <!-- content end -->
</div>
<a href="#" class="am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}">
    <span class="am-icon-btn am-icon-th-list"></span>
</a>
<footer style="text-align: center">
    <hr>
<p class="am-padding-left">© 2016 <a href="http://www.qianchengzhidao.com">oracle北京实训基地</a>. </p>
</footer>

</body>

<script type="text/javascript">

readchatmessage();

</script>


</html>
