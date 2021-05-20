package com.kingdee.eas.fdc.sellhouse;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.framework.ICoreBase;

/**
 * �� �ͻ��� �� �������� �������õİ�����
 * */
public class SHEComHelper {
	private static final Logger log = Logger.getLogger(SHEComHelper.class);
	/**
	 * ����ֵ����ȡ�������󷵻�
	 * @param srcAmount ԭʼ���
	 * @param isToInteger �Ƿ�ȡ��
	 * @param toIntegerType ȡ����ʽ
	 * @param digit ȡ��λ��
	 * @return ȡ����Ľ��
	 * */
	public static BigDecimal getAmountAfterToInteger(BigDecimal srcAmount, boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit){
		if(isToInteger){
			BigDecimal maxValue = null;
			int roundingMode = 0;//ȡ�᷽ʽ
			
			if(ToIntegerTypeEnum.AbnegateZero.equals(toIntegerType)){
				roundingMode = BigDecimal.ROUND_DOWN;
			}else if(ToIntegerTypeEnum.Carry.equals(toIntegerType)){
				roundingMode = BigDecimal.ROUND_UP;
			}else if(ToIntegerTypeEnum.Round.equals(toIntegerType)){
				roundingMode = BigDecimal.ROUND_HALF_UP;
			}else{
				log.error("oh my god.error impossible.");
				return srcAmount;
			}
			
			if(DigitEnum.EntryDigit.equals(digit)){
				maxValue = new BigDecimal("1");
			}else if(DigitEnum.TenDigit.equals(digit)){
				maxValue = new BigDecimal("10");
			}else if(DigitEnum.HundredDigit.equals(digit)){
				maxValue = new BigDecimal("100");
			}else if(DigitEnum.ThousandDigit.equals(digit)){
				maxValue = new BigDecimal("1000");
			}else if(DigitEnum.TenThousandDigit.equals(digit)){
				maxValue = new BigDecimal("10000");
			}else if(DigitEnum.LakhDigit.equals(digit)){
				maxValue = new BigDecimal("100000");
			}else if(DigitEnum.TenthDigit.equals(digit)){
				maxValue = new BigDecimal("0.1");
			}else{
				log.error("oh my god.error impossible.");
				return srcAmount;
			}
			
			//���㷽ʽ�£���ͬ�ܽ���ȡ����λ�����٣��򲻽���ȡ������.�����ͬ����ȡ����ͱ��0��
			//��λ��ʽ�£���ͬ�ܽ���ȡ����λ����̫�࣬�򲻽���ȡ������.
			if(srcAmount.compareTo(maxValue) < 0){
				log.info("too small amount.");
				return srcAmount;
			}
			
			srcAmount = srcAmount.divide(maxValue, 0, roundingMode).multiply(maxValue);
		}
		return srcAmount;
	}

	
	
	public static ICoreBase getRevListBizInterface(Context ctx, RevListTypeEnum revListType) throws BOSException {
		if(revListType==null) return null;
		if(revListType.equals(RevListTypeEnum.sincerityPur)) {
			return ctx==null?SincerReceiveEntryFactory.getRemoteInstance():SincerReceiveEntryFactory.getLocalInstance(ctx);
		}else if(revListType.equals(RevListTypeEnum.purchaseRev)) {
			return ctx==null?PurchasePayListEntryFactory.getRemoteInstance():PurchasePayListEntryFactory.getLocalInstance(ctx);
		}else if(revListType.equals(RevListTypeEnum.purElseRev)) {
			return ctx==null?PurchaseElsePayListEntryFactory.getRemoteInstance():PurchaseElsePayListEntryFactory.getLocalInstance(ctx);
		}else if(revListType.equals(RevListTypeEnum.fdcCustomerRev)) {
			return ctx==null?CusRevListFactory.getRemoteInstance():CusRevListFactory.getLocalInstance(ctx);
		}else if(revListType.equals(RevListTypeEnum.areaCompensate)) {
			return ctx==null?AreaCompensateRevListFactory.getRemoteInstance():AreaCompensateRevListFactory.getLocalInstance(ctx);
		}
		
		return null;
	}
	
	
}
