﻿package com.feicui.com.AdminOperation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import com.feicui.com.UserLogic.AbstractVirtual;
import com.feicui.com.storage.AdminMenu;
import com.feicui.com.util.DatabaseUtils;

/*
 * 开户
 */
public class OpenAccount extends AbstractVirtual {

	@Override
	public AbstractVirtual show() {

		try {
			DatabaseUtils utils = new DatabaseUtils();
			//生成主键
			user.setId(UUID.randomUUID().toString());
			// 正常用户默认为1
			user.setJudge("1");

			utils.updateData(
					"insert into user_(id,account, name,sex,card,"
					+ "education,passwd,address,amount,status,judge)"
					+ "value(?,?,?,?,?,?,?,?,?,?,?)",
					user.getId(), user.getAccount(), user.getName(), 
					user.getSex(), user.getCard(), user.getEducation(),
					user.getPasswd(), user.getAddress(), "0", 
					user.getStatus(), user.getJudge());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new AdminMenu();

	}
}
