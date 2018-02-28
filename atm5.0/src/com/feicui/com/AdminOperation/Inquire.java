package com.feicui.com.AdminOperation;

import com.feicui.com.UserLogic.AbstractVirtual;

/*
 * 查询正常用户信息
 */
public class Inquire extends AbstractVirtual {

	@Override
	public AbstractVirtual show(){

		return inputSql("SELECT * FROM user_ where judge = 1");
	}
}
