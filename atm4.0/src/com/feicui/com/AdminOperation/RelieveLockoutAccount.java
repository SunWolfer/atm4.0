package com.feicui.com.AdminOperation;

import com.feicui.com.UserLogic.AbstractVirtual;

public class RelieveLockoutAccount extends AbstractVirtual{
	/*
	 * 解除锁定账户
	 * (non-Javadoc)
	 * @see com.feicui.com.user.Virtual#show()
	 */
	@Override
	public AbstractVirtual show() {
		
		System.out.println("请输入要解除锁定的账户卡号");
        
        String num = scanner.nextLine();
		
		
		return inputSql("SELECT * FROM user_ where judge = 1 and account = ?",num);
	}
	
}
