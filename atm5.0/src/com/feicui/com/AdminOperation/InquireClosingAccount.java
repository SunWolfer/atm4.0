package com.feicui.com.AdminOperation;

import com.feicui.com.UserLogic.AbstractVirtual;

public class InquireClosingAccount extends AbstractVirtual{
	
	
	/*
	 * 查询销户账户
	 * (non-Javadoc)
	 * @see com.feicui.com.user.Virtual#show()
	 */
	@Override
	public AbstractVirtual show() {
		
		return inputSql("SELECT * FROM user_ where judge = -1");
	}
	
}
