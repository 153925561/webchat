package org.cdlg.sunrise.test;

import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.cdlg.sunrise.dao.UserDao;
import org.cdlg.sunrise.entity.User;
import org.junit.Test;

public class test {

	@Test
	public void test() {
		
		// 1、获取与数据库相连的session
		InputStream in  =test.class.getResourceAsStream("/mybatis-config.xml");
	    SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
	    SqlSession session = sf.openSession();
		//2、获取一个dao的对象
	    UserDao userDao=session.getMapper(UserDao.class);
	    List<User> listuser=userDao.findAll();
	    for (User user : listuser) {
			System.out.println(user.getNickname());
		}
		//3、关闭session
		session.close();	
	}

	@Test
	public void testadd(){
		

		// 1、获取与数据库相连的session
		InputStream in  =test.class.getResourceAsStream("/mybatis-config.xml");
	    SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
	    SqlSession session = sf.openSession();
		//2、获取一个dao的对象
	    UserDao userDao=session.getMapper(UserDao.class);
	   
	    User user=new User();
	    user.setUserid("王五");
	    user.setPassword("ok");
	    userDao.addUser(user);
	    session.commit();
	    
	    
	   
		//3、关闭session
		session.close();
		
		
	}
	
	
}
