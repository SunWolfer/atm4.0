package com.feicui.com.UserLogic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.feicui.com.AdminOperation.User;
import com.feicui.com.storage.AdminMenu;
import com.feicui.com.storage.ReadText;
import com.feicui.com.util.DatabaseUtils;

public abstract class AbstractVirtual {
	
	protected static Atm atm = new Atm();

	protected User user= atm.getUser();
	
	public static Scanner scanner = new Scanner(System.in);
	
	public static ReadText readText = new ReadText();

	public abstract AbstractVirtual show();
	
	/*
	 * 查询客户信息
	 */
	protected AbstractVirtual inputSql(String condition, String...args) {
		try {
	        DatabaseUtils utils = new DatabaseUtils();
	        
	        
	        List<User> list = utils.queryData(User.class, condition, args);

	        System.out.println(list);
	        
	        System.out.println("1:返回主菜单");
	        String string = scanner.nextLine();
	        
	        if("1".equals(string)) {
	        	
	        	return new AdminMenu();
	        }else {
	        	
	        	return this;
	        }
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (ReflectiveOperationException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
}
