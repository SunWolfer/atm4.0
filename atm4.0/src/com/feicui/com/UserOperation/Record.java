﻿package com.feicui.com.UserOperation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.feicui.com.AdminOperation.Transaction;
import com.feicui.com.UserLogic.AbstractVirtual;
import com.feicui.com.UserLogic.MainMenu;
import com.feicui.com.util.DatabaseUtils;

/*
 * 交易记录查询
 * 
 */

public class Record extends AbstractVirtual {
	
	public AbstractVirtual show() {
		
		 DatabaseUtils utils;
		try {
			
			
			utils = new DatabaseUtils();
					
			// 执行sql语句,返回结果 -- 查询
			List<Transaction>tran = utils.queryData(Transaction.class, "select * from "
					+ "transaction where account = ?",user.getAccount());
			
			System.out.println(tran);
			
			System.out.println("1.返回主菜单");
			
			int num = scanner.nextInt();
			
			if (num == 1) {
				
				return new MainMenu();
				
			} else {
				
				return this;
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReflectiveOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
				
	}
	
}