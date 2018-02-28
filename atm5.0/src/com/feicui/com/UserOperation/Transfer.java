package com.feicui.com.UserOperation;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.feicui.com.AdminOperation.User;
import com.feicui.com.UserLogic.AbstractVirtual;
import com.feicui.com.UserLogic.MainMenu;
import com.feicui.com.util.DatabaseUtils;

public class Transfer extends AbstractVirtual {

	public AbstractVirtual show() {

		// 判断卡号
		System.out.println("请输入对方卡号");
		String Account = scanner.nextLine();

		User otheruser = new User();

		DatabaseUtils utils = null;;
		List<User> list = null;
		
		try {
			utils = new DatabaseUtils();
			String sql = "select * from user_  where account = ? and judge = 1";
			list = utils.queryData(User.class, sql, Account);
		} catch (IOException | SQLException | ReflectiveOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if (list.isEmpty()) {

			System.out.println("没有此用户");
			return this;
		}

		otheruser = list.get(0);

		if (otheruser.getAccount().equals(null)) {

			System.out.println("用户不存在");
			return this;

		}
		if(otheruser.getAccount().equals(user.getAccount())) {
			
			System.out.println("不能给自己转账，请重新输入");
			
			return this;
		}
		
		System.out.println("请输入转账金额");

		String moneyString = scanner.nextLine();

		// 判断金额
		if (!atm.figure(moneyString)) {

			System.out.println("请输入正确的金额");
			return this;

		}

		user.setOthermoney(Double.valueOf(moneyString));

		readText.readText("menu" + File.separator + "choosemenu.txt");

		// 转账业务菜单
		String number2 = scanner.nextLine();

		// 确认
		if (number2.equals("1")) {

			System.out.println(
					"姓名：" + user.getName() + "\n" + "银行卡号" + 
			user.getAccount() + "\n" + "转账金额" + moneyString + "\n");

			readText.readText("menu" + File.separator + "transfer.txt");

			String number3 = scanner.nextLine();

			// 确认转账
			if (number3.equals("1")) {

				if (user.getOthermoney() <= user.getAmount()) {
					
					atm.OtherBusiness(otheruser);

					System.out.println("转账成功!");

					return new MainMenu();

				} else {

					System.out.println("余额不足，请重新输入：");
					return this;

				}
			}

			// 重新输入
			if (number3.equals("2")) {

				return this;

			}

			// 退卡
			if (number3.equals("3")) {

				return new Login();

			}
		}

		// 重新输入
		if (number2.equals("2")) {

			return this;

		}

		// 返回主菜单
		if (number2.equals("3")) {

			return new MainMenu();

		}

		return this;

	}
}
