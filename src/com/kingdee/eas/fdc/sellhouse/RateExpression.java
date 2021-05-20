package com.kingdee.eas.fdc.sellhouse;

import java.math.BigDecimal;
import java.util.List;

import com.kingdee.eas.fdc.basedata.FDCHelper;

public class RateExpression {
  
	public RateExpression()
	{	
	}
	
	public static  BigDecimal amountPrincipal = FDCHelper.ZERO;
	public static  BigDecimal accrual = FDCHelper.ZERO;
	
	/**
	 * �ȶ�𰴽Ҽ��㹫ʽ
	 *
	 * @param totalMoney  �����ܶ�
	 * @param requMonth   �������
	 * @param loanInterestRate ������
	 * @param countRequMoney  �ѻ�����
	 * @return BigDecimal
	 */
	public static BigDecimal getAmountPrincipal(BigDecimal totalMoney, int requMonth,
			BigDecimal loanInterestRate, int countRequMoney)
	{
		// �Ѿ��黹�����
		BigDecimal alreadyAmountPrincipal = totalMoney.divide(
				new BigDecimal(requMonth),BigDecimal.ROUND_HALF_UP).multiply(
						new BigDecimal(countRequMoney));
		amountPrincipal = totalMoney.divide(new BigDecimal(requMonth),10,
				BigDecimal.ROUND_HALF_UP).add(
						totalMoney.subtract(alreadyAmountPrincipal).multiply(
								loanInterestRate.divide(FDCHelper.ONE_HUNDRED,10,
										BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_UP)));
		return amountPrincipal;
	}
	
	/**
	 * �ȶϢ���Ҽ��㹫ʽ
	 *
	 * @param totalMoney  �����ܶ�
	 * @param requMonth   �������
	 * @param loanInterestRate ������
	 * @return BigDecimal
	 */
	public static BigDecimal getAmountAccrual(BigDecimal totalMoney,int requMonth,BigDecimal loanInterestRate) {
		double rate = loanInterestRate.divide(FDCHelper.ONE_HUNDRED,10,BigDecimal.ROUND_HALF_UP)
		.divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1)).doubleValue();
		accrual = totalMoney.multiply(loanInterestRate.divide(FDCHelper.ONE_HUNDRED,10,BigDecimal.ROUND_HALF_UP)
				.divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(Math.pow(rate,requMonth))))
				.divide(new BigDecimal(Math.pow(rate,requMonth)-1),10,BigDecimal.ROUND_HALF_UP);
		return accrual ;
	}
	
}
