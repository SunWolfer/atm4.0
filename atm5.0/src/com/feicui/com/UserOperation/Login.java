package com.feicui.com.UserOperation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.feicui.com.AdminOperation.User;
import com.feicui.com.UserLogic.AbstractVirtual;
import com.feicui.com.UserLogic.Lockout;
import com.feicui.com.UserLogic.LoginChoose;
import com.feicui.com.UserLogic.MainMenu;
import com.feicui.com.util.DatabaseUtils;

/*
 * 普通用户登录
 */
public class Login extends AbstractVirtual {

	public AbstractVirtual show() {

		System.out.println("请输入账号或身份证号：");
		String str1 = scanner.nextLine();

		DatabaseUtils utils = null;;
		List<User> list = null;
		
		try {
			utils = new DatabaseUtils();
			String sql = "select * from user_  where account = ? or card = ?";
			list = utils.queryData(User.class, sql, str1, str1);
			
			if (list.isEmpty()) {

				System.out.println("没有此用户");
				return new LoginChoose();
			}

			atm.setUser(list.get(0));
			user = atm.getUser();
			if (user.getSex().equals("1")) {

				user.setSex("男");
			}
			if (user.getSex().equals("2")) {

				user.setSex("女");
			}

			/*
			 * 登录用户判断
			 */

			if (user.getJudge().equals("0")) {

				System.out.println("用户已锁定");
				return new LoginChoose();
			}

			if (user.getJudge().equals("-1")) {

				System.out.println("用户已销户");
				return new LoginChoose();
			}

			if (user.getJudge().equals("999")) {

				System.out.println("请重新输入");
				return new LoginChoose();
			}

			for (int count = 0; count < 3;) {
				a: while (true) {

					System.out.println("请输入密码");
					String str2 = scanner.nextLine();
					
					String sql1 = "select * from user_  where (account = ? or card = ?) and passwd = ?";
					list = utils.queryData(User.class, sql1, str1, str1,str2);

					if (user.getPasswd().equals(str2)) {

						return new MainMenu();
					} else {
						System.out.println("密码错误，请重新输入");

						count++;

						if (count >= 3) {

							return new Lockout();

						}
						continue a;

					}
				}
			}
			
		} catch (IOException | SQLException | ReflectiveOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new MainMenu();
	}

}
