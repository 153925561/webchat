<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>WebChat | 关于</title>
    <link rel="stylesheet" href="static/plugins/amaze/css/amazeui.min.css">
    <link rel="stylesheet" href="static/plugins/amaze/css/admin.css">
    <link rel="stylesheet" href="static/plugins/contextjs/css/context.standalone.css">
    <script src="static/plugins/jquery/jquery-2.1.4.min.js"></script>
    <script src="static/plugins/amaze/js/amazeui.min.js"></script>
    <script src="static/plugins/amaze/js/app.js"></script>
    <script src="static/plugins/layer/layer.js"></script>
    <script src="static/plugins/laypage/laypage.js"></script>
    <script src="static/plugins/contextjs/js/context.js"></script>
</head>
<body>
<header class="am-topbar admin-header">
    <div class="am-topbar-brand">
        <i class="am-icon-weixin"></i> <strong>WebChat</strong> <small>网页聊天室</small>
    </div>
    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>
    <div class="am-collapse am-topbar-collapse" id="topbar-collapse">
        <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
            <li class="am-dropdown" data-am-dropdown>
                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                 ${username}<span class="am-icon-caret-down"></span>
                </a>
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
                <p>欢迎使用WebChat聊天室~ ${msg}</p>
            </div>
        </div>
    </div>
</div>
<!-- sidebar end -->
    <!-- content start -->
    <div class="admin-content">
        <div class="am-cf am-padding">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">好友动态</strong> / <small>about</small></div>
        </div>
        <div class="am-tabs am-margin" data-am-tabs>
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">查找好友</a></li>
                <li><a href="#tab2">好友动态</a></li>
            </ul>
            <div class="am-tabs-bd">
                <div class="am-tab-panel am-fade am-in am-active" id="tab1">
                    <hr>
                    <blockquote>
                       <form class="am-form am-form-horizontal" id="information-form" action="/webchat/dofindfriend" method="post" data-am-validator>
                        <div class="am-form-group">
                            <label for="userid" class="am-u-sm-2 am-form-label">用户名</label>
                            <div class="am-u-sm-10">
                                <input type="text" id="userid" name="findid" value="${list.userid}" >
                            </div>
                        </div>

                        <div class="am-form-group">
                            <label for="nickname" class="am-u-sm-2 am-form-label">昵称</label>
                            <div class="am-u-sm-10">
                                <input type="text" id="nickname" name="nickname" value="${list.nickname}" required placeholder="这里输入你的昵称...">
                            </div>
                        </div>
                  		
                        <div class="am-form-group">
                            <div class="am-u-sm-10 am-u-sm-offset-2">
                                <button type="submit" class="am-btn am-round am-btn-success"><span class="am-icon-send"></span> 查找好友</button>
                            </div>
                        </div>
                    </form>
					<table class="am-table am-table-striped">
						<thead>
							<tr>
								<th>编号</th>
								<th>昵称</th>
								<th>头像</th>
								<th>性别</th>
								<th>年龄</th> 
								<th>加好友</th>
							</tr>
						</thead>
						<tbody> 
                      <c:forEach var="list" items="${listfriend}">
								<tr>
									<td>${list.userid}</td>								
									<td>${list.nickname}</td>
									<td><img class="am-circle" src="/headimgaes/${list.profilehead}" width="40" height="40" alt="Amayadream"/></td>
									<td>${list.sex}</td>
									<td>${list.age}</td>									                                 
                                    <td> <a href="/webchat/doaddfirend?friendid=${list.id}">添加</a></td>
								</tr>
					</c:forEach>								
						</tbody>
					</table>
                    </blockquote>
					<a href="/webchat/dofindfriend?nowpage=${firstpage}&findid=${findid}&nickname=${findnickname}">首页</a>|<a href="/webchat/dofindfriend?nowpage=${lastpage}&findid=${findid}&nickname=${findnickname}">上一页</a>|<a href="/webchat/dofindfriend?nowpage=${nextpage}&findid=${findid}&nickname=${findnickname}">下一页</a>|<a href="/webchat/dofindfriend?nowpage=${finalpage}&findid=${findid}&nickname=${findnickname}">尾页</a>
                </div>

            <div class="am-tab-panel am-fade am-in am-scrollable-vertical" style="height:350px;" id="tab2">
               <blockquote>
                   <ul class="am-comments-list am-comments-list-flip" id="chat">
                 <c:forEach var="spaces" items="${friendspaceList}">
                <li class="am-comment am-comment-primary">
                <a href="#link-to-user-home">
                	<img width="48" height="48" class="am-comment-avatar" alt="" src="/headimgaes/${spaces.headimg}"></a>
                <div class="am-comment-main">
                <header class="am-comment-hd">
                <div class="am-comment-meta">   
                <a class="am-comment-author" href="#link-to-user">${spaces.nickname }</a> 发表于
                <time>${spaces.creatdate }</time>  </div>
                </header>
                <div class="am-comment-bd"> <p>${spaces.content }</p>
                	<c:forEach var="imgpath" items="${spaces.picList }">
					<img width="48" height="48" style="border:solid 1px #666;"  alt="" src="/spaceimages/${imgpath }"/>
					</c:forEach>
				</div>
					<!--回复列表-->
					<ul class="am-comments-list am-comments-list-flip" id="chat">
				<c:forEach var="reply" items="${spaces.replyList }">
						<li class="am-comment">
						<img width="48" height="48" class="am-comment-avatar" alt="" src="static/source/img/default_head.jpg">
						<div class="am-comment-main">
							<div class="am-comment-meta">   
							<a class="am-comment-author" href="#link-to-user">${reply.nickname }</a> 回复于<time>${reply.creatdate }</time></div>
							${reply.content }</div>
						</li>     
			   </c:forEach> 				
					</ul>
					<!--回复列表-->
                </div>
				</li>    
				</c:forEach>                     
                </ul>
                <a href="">首页</a>|<a href="">上一页</a>|<a href="">下一页</a>|<a href="">尾页</a>
                    </blockquote>
                 </div>
					
                </div>
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
	<!--回复DIV-->
			<div id="replyDiv" style="width:300px;height:180px;background-color:#ffffff;border:solid 1px blue;position:absolute;top:200px;left:400px;display:none;z-index:13000">
				<form class="am-form am-form-horizontal" id="information-form" action="${ctx}/${userid}/update" method="post" data-am-validator>
				<div class="am-form-group am-form">
					<textarea class="" id="message" name="message" rows="5"  placeholder="这里输入你想发送的信息..."></textarea> 
				</div>
				<!-- 接收者 -->
				<div class="" style="float: left">
					<p class="am-kai">发送给 : <span id="sendto">赵丽丽</span></p>
				</div>
				<!-- 按钮区 -->
				<div class="am-btn-group am-btn-group-xs" style="float:right;"> 
					<button class="am-btn am-btn-default" type="button" onClick="sendMessage()"><span class="am-icon-commenting"></span> 发送</button>
					<button class="am-btn am-btn-default" type="button" onClick="closeReplyDiv()">关闭</button>
				</div>
				</form>
			</div>
			<!--回复DIV结束-->
<script>
function showReplyDiv(){ 
$("#replyDiv").css("display","block"); 
}
function closeReplyDiv(){
$("#replyDiv").css("display","none"); 
}
</script>
</body>
</html>
