package com.frame.beetl.format;

import java.math.BigDecimal;

import org.beetl.core.Format;

/**   
* @Title: MoneyFormat.java 
* @Package org.whale.beetl.format 
* @Description: 金额格式
* @author lwyx
* @version V1.0   
*/
public class MoneyFormat implements Format{

	/* 
	 * money 金额
	 * num 小数点后几位
	 */
	@Override
	public Object format(Object money, String num) {
		String reMoney = "";
		if(money != null && num !=null){
			boolean tag = true;
			int iNum = Integer.parseInt(num);
			//�?��只能设置小数点后20�?
			iNum = iNum > 0 && iNum <= 20 ? iNum : 2; 
			
			String sMoney = money.toString();
			Double dMoney = Double.parseDouble(sMoney);
			BigDecimal bdMoney = new BigDecimal(dMoney);
			
			//stripTrailingZeros 小数点后�?.100变成 1.1
			//toPlainString 转成字符串时不使用科学计数法
			sMoney = bdMoney.setScale(iNum, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
			//是否存在小数�?
			if(sMoney.indexOf(".")<0){
				tag = false;
			}
			if(tag){
				String[] tmp = sMoney.split("\\.");
				reMoney += this.rePreMoneyFT(tmp[0]);
				reMoney += "."+tmp[1];
			}else{
				reMoney += this.rePreMoneyFT(sMoney);
			}
		}
		return reMoney;
	}
	/**
	 * 返回格式化的金额
	 * @param money
	 * @return
	 */
	private String rePreMoneyFT(String money){
		BigDecimal bd = new BigDecimal(money);
		char[] moneyList = bd.toString().toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = moneyList.length; i > 0; i--) {
			sb.append(moneyList[i-1]+"");
			if(i%3 == 0){
				sb.append(",");
			}
		}
		sb.reverse();
		return sb.toString();
	}
	
	public static void main(String[] args) {
		MoneyFormat mf = new MoneyFormat();
		System.out.println("13123242342:"+mf.format("13123242342","2"));
		System.out.println("13123242.3420000000:"+mf.format("13123242.342","2"));
		System.out.println("0.342:"+mf.format("0.342","2"));
		System.out.println("000.342:"+mf.format("000.342","2"));
		System.out.println("12120.0:"+mf.format("12120.0","2"));
		System.out.println("12120.000:"+mf.format("12120.000","2"));
		System.out.println("12120.001:"+mf.format("12120.001","2"));
	}
}
