package com.feicui.com.AdminOperation;

public class Transaction {
	
	private String time;
	
	private String account;
	
	private String tradeType;
	
	private String changeAmount;
	
	private String amount;
	
	
	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public String getTradeType() {
		return tradeType;
	}


	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}


	public String getChangeAmount() {
		return changeAmount;
	}


	public void setChangeAmount(String changeAmount) {
		this.changeAmount = changeAmount;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\n" + "日期:" + getTime() + "\n" + "账号:" + getAccount() 
				+ "\n" + "金额变动类型:" + getTradeType() + "\n" + "变动金额:"
				+ getChangeAmount() + "\n" + "账户余额:" + getAmount() + "\n";
	}
}
