package com.feicui.com.AdminOperation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.feicui.com.UserLogic.AbstractVirtual;
import com.feicui.com.storage.AdminMenu;
import com.feicui.com.util.DatabaseUtils;

/*
 *管理员验证登录
 */
public class AdmainRegister extends AbstractVirtual {

	/*
	 * 验证账户
	 */

	@Override
	public AbstractVirtual show() {

		System.out.println("请输入账号：");
		String str1 = scanner.nextLine();

		DatabaseUtils utils = null;;
		List<User> list = null;
		
		try {
			utils = new DatabaseUtils();
			String sql = "select * from user_  where account = ? and judge = 999";
			list = utils.queryData(User.class, sql, str1);
		} catch (IOException | SQLException | ReflectiveOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (list.isEmpty()){

			System.out.println("用户名错误，请重新输入");

			return this;

		}
		
		user = list.get(0);
		
		a:while(true) {
			System.out.println("请输入密码");
			String str2 = scanner.nextLine();
			if (user.getPasswd().equals(str2)) {
				
				return new AdminMenu();
			}
			else {
				
				System.out.println("密码错误，请重新输入");
				
				continue a;
				
			}
		}

		
	}
}
