package com.kingdee.eas.fdc.finance;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.util.backport.Collections;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
/**
 * ֮ǰ��������װ�Ķ��Ǵ���ɱ��ึ���ֽ����ϵ�����Զ�ƥ���֡���ťӦ�ô�����߼� by sxhong
 * ����������з�װ�ɱ��ึ���ֺ͹�������ֽ����ϵ������������֡���ťӦ�ô�����㷨�߼�  by cassiel_peng  2010-01-19
 */
public class PaymentAutoSplitHelper implements Comparator {
	
	
	private Collator comparator = Collator.getInstance(Locale.getDefault());
	private static AutoSplitSortor sortComparator=null;
	private static Comparator getSortComparator(){
		if(sortComparator==null){
			sortComparator=new AutoSplitSortor();
		}
		return sortComparator;
	}
	public int compare(Object o1, Object o2) {
		if(o1!=null&&o2!=null&&o1 instanceof Integer&&o2 instanceof Integer){
			return compareAgain((Integer)o1, (Integer)o2);
		}
		return 0;
	}
	private int compareAgain(Integer o1,Integer o2) {
		if(o1.intValue()>o2.intValue())   
			return   1;   
		else   
			return   0;   
	}
	
	
	/**
	 * �Զ�ƥ���깤������
	 * @param entrys
	 * @param totalAmt
	 * @return
	 */
	public static void autoSplit(
			FDCSplitBillEntryCollection entrys, BigDecimal totalAmt) {
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentSplitEntryInfo item = (PaymentSplitEntryInfo) iter.next();
			item.setAmount(FDCHelper.ZERO);
			if (item.getLevel() < 0 || !item.isIsLeaf()) {
				continue;
			}
			BigDecimal amount = FDCHelper.subtract(item.getCostAmt(),item.getSplitedCostAmt());
			item.put("distributeAmt", amount);
			item.setDirectAmt(null);
		}
		
		innerDistributeAmt(entrys, totalAmt, "amount");
	}

	/**
	 *��������ƾ֤���ɵ�ģʽ��Ҫ�ǵ�һ�ʽ����Ĵ�����ڹ�ϵ 
	 * ���ȿ��������һ�¡������distributeAmt�����Ѳ�ֳɱ������ڳɱ����Ѳ�ָ�����
	 * ����Ӧ�����ȿ�ɱ������޽��
	 * ��һ�ʽ���
	 * 	��壺�����distributeAmt����ΪӦ�����ȿ
	 * 	�����������distributeAmt����Ӧ�����ȿ�Ѳ�ֽ��ȿ������Ȳ�ֱ��޿���ô�죿��
	 * �ڶ��ʽ��������distributeAmt��=Ӧ�����ȿ�Ѳ�ֽ��ȿ�
	 * 
	 * ���޿
	 * 	�����distributeAmt��=���޿��ֽ��ڽ����ִ��𣩣��Ѳ�ֱ��޿�
	 *  by sxhong 2008-10-21 13:47:42
	 * @param entrys
	 * @param totalAmt
	 * @param hasSettleSplit δ֪����
	 * @param hasSettlePayed	�ǵ�һ�ʽ����
	 * @param isSettle	��һ�ʽ����
	 * @param isKeepAmt ���޿�
	 * @param isAdjustModel �Ƿ����ģʽ
	 * @return
	 */
	public static void autoSplitAmt(
			FDCSplitBillEntryCollection entrys, BigDecimal totalAmt,
			boolean hasSettleSplit, boolean hasSettlePayed, boolean isSettle,
			boolean isKeepAmt, boolean isAdjustModel) {
		/*
		 * if (!FDCHelper.isPositiveBigDecimal(totalAmt)) { return null; }
		 */
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentSplitEntryInfo item = (PaymentSplitEntryInfo) iter.next();
			item.setPayedAmt(FDCHelper.ZERO);
			if (item.getLevel() < 0 || !item.isIsLeaf()) {
				continue;
			}
			if (isSettle) {
				BigDecimal amount = FDCHelper.ZERO;
				amount = FDCHelper.toBigDecimal(item.getShouldPayAmt());
				if (isAdjustModel) {
					amount = FDCHelper.subtract(amount, item.getSplitedPayedAmt());
				}
				item.put("distributeAmt", amount);
			} else if (isKeepAmt) {
				BigDecimal amount = FDCHelper.ZERO;
				// if(item.getSplitedPayedAmt().compareTo(item.getShouldPayAmt())==1){
				// amount =
				// item.getCostAmt().subtract(item.getSplitedPayedAmt());
				// }else{
				amount = FDCHelper.toBigDecimal(item.getCostAmt()).subtract(FDCHelper.toBigDecimal(item.getShouldPayAmt())).subtract(FDCHelper.toBigDecimal(item.getSplitQualityAmt()));
				// }
				item.put("distributeAmt", amount);
			} else if (hasSettlePayed) {
				BigDecimal amount = FDCHelper.ZERO;
				amount = item.getShouldPayAmt().subtract(item.getSplitedPayedAmt());
				item.put("distributeAmt", amount);
			} else {
				BigDecimal amount = FDCHelper.ZERO;
				amount = FDCHelper.toBigDecimal(item.getSplitedCostAmt()).add(FDCHelper.toBigDecimal(item.getAmount())).subtract(FDCHelper.toBigDecimal(item.getSplitedPayedAmt()));
				item.put("distributeAmt", amount);
			}

			item.setDirectAmt(null);
		}
		innerDistributeAmt(entrys, totalAmt,"payedAmt");

	}
	
	/**
	 * �Զ�ƥ�䷢Ʊ���
	 * @param entrys
	 * @param totalInvoiceAmt
	 * @return
	 */
	public static void autoSplitInvoiceAmt(FDCSplitBillEntryCollection entrys, BigDecimal totalInvoiceAmt) {
		String prop="invoiceAmt";
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentSplitEntryInfo item = (PaymentSplitEntryInfo) iter.next();
			item.setBigDecimal(prop, FDCHelper.ZERO);
			if (item.getLevel() < 0 || !item.isIsLeaf()) {
				continue;
			}
			BigDecimal amount = FDCHelper.subtract(item.getCostAmt(),item.getSplitedInvoiceAmt());
			//���ô�������
			item.put("distributeAmt", amount);
			item.setDirectAmt(null);
		}
		innerDistributeAmt(entrys, totalInvoiceAmt,prop);

	}

	/**
	 * �򵥷�Ʊģʽ
	 * �Զ�ƥ�丶����
	 * @param entrys
	 * @param totalInvoiceAmt
	 * @return
	 */
	public static void autoSplitSimpleInvoicePayAmt(FDCSplitBillEntryCollection entrys, BigDecimal totalInvoiceAmt) {
		String prop="payedAmt";
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentSplitEntryInfo item = (PaymentSplitEntryInfo) iter.next();
			item.setBigDecimal(prop, FDCHelper.ZERO);
			if (item.getLevel() < 0 || !item.isIsLeaf()) {
				continue;
			}
			BigDecimal amount = FDCHelper.subtract(item.getCostAmt(),item.getSplitedPayedAmt());
			//���ô�������
			item.put("distributeAmt", amount);
			item.setDirectAmt(null);
		}
		innerDistributeAmt(entrys, totalInvoiceAmt,prop);
	}
	
	/**
	 * �Զ�ƥ�䷢Ʊ���
	 * @param entrys
	 * @param totalInvoiceAmt
	 * @return
	 */
	public static void autoSplitSimpleInvoiceInvoiceAmt(FDCSplitBillEntryCollection entrys, BigDecimal totalInvoiceAmt) {
		String prop="invoiceAmt";
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentSplitEntryInfo item = (PaymentSplitEntryInfo) iter.next();
			item.setBigDecimal(prop, FDCHelper.ZERO);
			if (item.getLevel() < 0 || !item.isIsLeaf()) {
				continue;
			}
			BigDecimal amount = FDCHelper.subtract(item.getCostAmt(),item.getSplitedInvoiceAmt());
			//���ô�������
			item.put("distributeAmt", amount);
			item.setDirectAmt(null);
		}
		innerDistributeAmt(entrys, totalInvoiceAmt,prop);

	}
	
	/**
	 * ʹ���Զ������㷨���������䵽������ϸ��
	 * @param entrys	��¼��,��¼���������distributeAmt ���ڱ����ÿһ����¼��Ҫ����Ľ��
	 * @param totalAmt	��������ܽ��
	 * @param prop		Ҫ���з�����������ƣ��磺amount��payedAmt��invoiceAmt��
	 *  by sxhong 2009-07-21 13:43:56
	 */
	private static void innerDistributeAmt(FDCSplitBillEntryCollection entrys, BigDecimal totalAmt,String prop) {
		if(totalAmt==null||totalAmt.signum()==0){
			return;
		}
		//�����Զ������˳��
		final List sortList = AutoSplitSortor.sort(entrys);
		FDCSplitBillEntryInfo item = null;
		for (Iterator iter = sortList.iterator(); iter.hasNext();) {
			if(totalAmt.signum()==0){
				break;
			}
			item = (FDCSplitBillEntryInfo) iter.next();
			boolean debug = FDCHelper.isFDCDebug();
			if (debug) {
				System.out.print(prop + item.getCostAccount().getCurProject().getLongNumber());
				System.out.print("\t��Ŀ���룺"+ item.getCostAccount().getLongNumber());
				System.out.print("\tseq��" + item.getSeq());
				System.out.println("\tTotal:" + totalAmt + "\t distributeAmt:"+ item.get("distributeAmt"));
			}

			BigDecimal distributeAmt = FDCHelper.toBigDecimal(item.get("distributeAmt"));
			if (totalAmt.compareTo(distributeAmt) > 0) {
				item.setBigDecimal(prop, distributeAmt);
				totalAmt = totalAmt.subtract(distributeAmt);
			} else {
				item.setBigDecimal(prop, totalAmt);
				totalAmt = FDCHelper.ZERO;
			}

		}
		// ����ķ��õ����һ��
		if(item!=null){
			if (totalAmt.compareTo(FDCHelper.ZERO) != 0) {
				item.setBigDecimal(prop, FDCHelper.toBigDecimal(item.getBigDecimal(prop)).add(totalAmt));
			}
		}
	}
	/**
	 * @param entrys ��ַ�¼
	 * @param map ��߰�������Ҫ�������ı���ֵ����ͷ�ı��ڳɱ����Ѳ�ֳɱ�������ϸ�кϼơ��ɱ���ֽ�����ϸ�кϼ�
	 * by cassiel_peng 2010-01-19 
	 * �÷����е�ע�Ϳɵ���������������(splitPayedAmtBaseOnProp��splitInvoiceAmtBaseOnProp )�Ĳο�ע��
	 */
	public static void splitCostAmtBaseOnProp(FDCSplitBillEntryCollection entrys, Map map){
		/**
		 * �����ɱ����
		 */
		BigDecimal headCostAmt = FDCHelper.toBigDecimal(map.get("headCostAmt"),2);//��ͷ�ı��ڳɱ����
		BigDecimal hadSplitCostAmtSum = FDCHelper.toBigDecimal(map.get("hadSplitCostAmtSum"),2);//�Ѳ�ֳɱ�������ϸ�кϼ�
		BigDecimal splitCostAmtSum = FDCHelper.toBigDecimal(map.get("splitCostAmtSum"),2);//�ɱ���ֽ�����ϸ�кϼ�
		FDCSplitBillEntryInfo item = null;
		final List sortList = AutoSplitSortor.filterExceptProductSplit(entrys);
		BigDecimal _tempTotalAmt = headCostAmt;
		BigDecimal tempTotalAmt = FDCHelper.ZERO;
		Map tempMap = new HashMap();
		Boolean hasValue = null;
		//���ǲ�Ʒ���ʱ����Ϊ���ʱ�򽫲��ŵ����һ�ʣ�Ϊ���ҵ����һ�ʣ������ҵ�ĳһ��Ŀ���µ����в�Ʒ�����ϸ�ж���
		List judgeLastProductSplit = null;
		for(Iterator iter = sortList.iterator();iter.hasNext();){
			if(headCostAmt.compareTo(FDCHelper.ZERO)==0||headCostAmt.signum()==0){
				break;
			}
			item = (FDCSplitBillEntryInfo) iter.next();
			if(CostSplitTypeEnum.PRODSPLIT.equals(item.getSplitType())){//��Ʒ���
				if(!item.isIsLeaf()){//�Ƿ���ϸ�ڵ�
					hasValue = Boolean.FALSE;
					tempMap.put(item.getCostAccount().getId().toString()+"_"+new Integer(item.getLevel()).toString(), item);//�ɱ���Ŀ+������Ϊ��
					
					judgeLastProductSplit = new ArrayList();
					//��Ϊ��ʱ��ֵ�������δ���棬���Դ����ݿ����ǲ鲻��ĳһ�ɱ���Ŀ���µĲ�Ʒ�����ϸ�ж���ģ���ô�ڴ���Ϊ���ʱ����ͼ�����ŵ����һ��
					//��Ʒ�����ϸ�����ǲ���ʵ�ġ������ڽ����ϵ�kdtEntrys��ÿһ���ǰ��˲�ַ�¼����ģ���Щ��������������Ҫ���������������������Ե�ֵ��
//					for(Iterator _iter = sortList.iterator();_iter.hasNext();){
//						FDCSplitBillEntryInfo splitEntry = (FDCSplitBillEntryInfo)_iter.next();
//						if(item.getCostAccount().getId().toString().equals(splitEntry.getCostAccount().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
//							judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
//						}
//					}
					//R101108-137 ����Ӧ��ֻȡ�÷���ϸ�ڵ��µĲ�Ʒ��֡���Ϊ�б����֣����Կ��ܴ��ڶ����ͬ��Ŀ�Ĳ�ּ�¼
					//�������֮ǰ�Ĵ����߼���������е���ͬ��Ŀ�Ĳ�Ʒ��ֶ�ȡ����  by zhiyuan_tang
					int startIndex = sortList.indexOf(item);
					if (startIndex < sortList.size() - 1) {
						for (int i = startIndex + 1 ; i < sortList.size(); i++) {
							FDCSplitBillEntryInfo splitEntry = (FDCSplitBillEntryInfo)sortList.get(i);
							if(item.getCostAccount().getId().toString().equals(splitEntry.getCostAccount().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
								judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
							} else {
								break;
							}
						}
					}
					Collections.sort(judgeLastProductSplit);
				}else{//��ϸ�ڵ�
					//��ֵ���Ʒ���� ÿ����Ʒ�е�   �ɱ���ֽ��/������ϸ��Ŀ�еĳɱ���ֽ��    ���
					//�ҵ�������ϸ��Ŀ��(�뵱ǰ��¼�ĳɱ���Ŀ��һ�������Ǽ��λ�ȵ�ǰ��¼С1)�ĳɱ���ֽ��
					FDCSplitBillEntryInfo currentProductFloorEntry = (FDCSplitBillEntryInfo)tempMap.get(item.getCostAccount().getId().toString()+"_"+new Integer(item.getLevel()-1).toString());
					if(currentProductFloorEntry!=null){
						BigDecimal tempCostSplitAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("costAmt"));//������ϸ��Ŀ�еĳɱ���ֽ�� 
						BigDecimal distributeAmt  = FDCHelper.divide(FDCHelper.multiply(item.getBigDecimal("costAmt"),currentProductFloorEntry.getBigDecimal("amount")),tempCostSplitAmt );
						if(hasValue!=null&&!hasValue.booleanValue()){
							tempTotalAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("amount"),2);
						}
						hasValue = Boolean.TRUE;
						//�˴�һ��Ҫ������ĳһ���ɱ���Ŀ�µĲ�Ʒ��ֵ�ʱ���β���Ϊ��Ʒ��ֵĿ�Ŀ���Ǹ��ݱ��еĹ����ɱ����=���Ѳ�ֳɱ�������ϸ�кϼ�+��ͷ�ı��ڳɱ���*�����еĳɱ���ֽ��/�ɱ���ֽ�����ϸ�кϼƣ�-���е��Ѳ�ֳɱ����
						//��������ģ�Ȼ�������Ŀ���±ߵĲ�Ʒ����ǰ���ÿ����Ʒ�е�   (�ɱ���ֽ��/������ϸ��Ŀ�еĳɱ���ֽ��)*����������ϸ��Ŀ�еĹ����ɱ����    ������ģ����������β�����ⲻ�����ǰ�������������ĸ��в������֮�Ͳ���������������ϸ��Ŀ�еĹ����ɱ���� 
						//�����������β������û�д������������ϸ��Ŀ�еĹ����ɱ���������������еĲ�Ʒ����е����������л��ܣ��������������ϸ��Ŀ�еĹ����ɱ����Ҳ��������   
						if(distributeAmt==null){
							distributeAmt = FDCHelper.ZERO;
						}
						if (tempTotalAmt.abs().compareTo(distributeAmt.abs()) > 0) {
							item.setBigDecimal("amount", distributeAmt);
							tempTotalAmt = tempTotalAmt.subtract(distributeAmt);
						} else {
							item.setBigDecimal("amount", tempTotalAmt);
							tempTotalAmt = FDCHelper.ZERO;
						}
						//���һ����ʱ
						if(item!=null&&judgeLastProductSplit.get(judgeLastProductSplit.size()-1).equals(new Integer(item.getSeq()))){
							if (tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
								item.setBigDecimal("amount", FDCHelper.toBigDecimal(item.getBigDecimal("amount")).add(tempTotalAmt));
							}
						}
						continue;
					}
				}
			}
			//ÿ����ϸ��Ŀ�У����еĹ����ɱ����=���Ѳ�ֳɱ�������ϸ�кϼ�+��ͷ�ı��ڳɱ���*�����еĳɱ���ֽ��/�ɱ���ֽ�����ϸ�кϼƣ�-���е��Ѳ�ֳɱ����
			//ע�⣺���������������б��ʵļ��㣬�ȳ˺��Ϊ���ϲߣ��������ھ��ȵ�����ᵼ��С��λ��ȡ���������
			BigDecimal distributeAmt = FDCHelper.subtract(FDCHelper.divide(FDCHelper.multiply(FDCHelper.add(hadSplitCostAmtSum,headCostAmt), item.getBigDecimal("costAmt")),splitCostAmtSum), item.getBigDecimal("splitedCostAmt"));
			//β���
			if(distributeAmt==null){
				distributeAmt=FDCHelper.ZERO;
			}
			if (_tempTotalAmt.abs().compareTo(distributeAmt.abs()) > 0) {
				item.setBigDecimal("amount", distributeAmt);
				_tempTotalAmt = _tempTotalAmt.subtract(distributeAmt);
			} else {
				item.setBigDecimal("amount", _tempTotalAmt);
				_tempTotalAmt = FDCHelper.ZERO;
			}
		}
		// ����ķ��õ����һ��
		if(item!=null){
			if (_tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
				item.setBigDecimal("amount", FDCHelper.toBigDecimal(item.getBigDecimal("amount")).add(_tempTotalAmt));
			}
		}
	}
	/**
	 * ���������ɶ���������ɿ�splitCostAmtBaseOnProp������߶�Ӧ��ע�ͣ��������ϸ�Ĵ���˵�������ɸ���ʵ������������ע�ͣ�
	 * by cassiel_peng 2010-01-19 
	 */
	public static void splitPayedAmtBaseOnProp(FDCSplitBillEntryCollection entrys, Map map) throws BOSException{
		/**
		 * ����������
		 */
		BigDecimal headPayedAmt = FDCHelper.toBigDecimal(map.get("headPayedAmt"),2);//��ͷ��ʵ�����
		BigDecimal hadSplitPayedAmtSum = FDCHelper.toBigDecimal(map.get("hadSplitPayedAmtSum"),2);//�Ѳ�ָ��������ϸ�кϼ�
		BigDecimal splitCostAmtSum = FDCHelper.toBigDecimal(map.get("splitCostAmtSum"),2);//�ɱ���ֽ�����ϸ�кϼ�
		FDCSplitBillEntryInfo item = null;
		final List sortList = AutoSplitSortor.filterExceptProductSplit(entrys);
		BigDecimal _tempTotalAmt = headPayedAmt;
		BigDecimal tempTotalAmt = FDCHelper.ZERO;
		Boolean hasValue = null;
		List judgeLastProductSplit = null;
		Map tempMap = new HashMap();
		for(Iterator iter = sortList.iterator();iter.hasNext();){
//		for(Iterator iter = entrys.iterator();iter.hasNext();){
			if(headPayedAmt.compareTo(FDCHelper.ZERO)==0||headPayedAmt.signum()==0){
				break;
			}
			item = (FDCSplitBillEntryInfo) iter.next();
			if(CostSplitTypeEnum.PRODSPLIT.equals(item.getSplitType())){//��Ʒ���
				if(!item.isIsLeaf()){//�Ƿ���ϸ�ڵ�
					hasValue = Boolean.FALSE;
					tempMap.put(item.getCostAccount().getId().toString()+"_"+new Integer(item.getLevel()).toString(), item);//�ɱ���Ŀ+������Ϊ��
					
					judgeLastProductSplit = new ArrayList();
//					for(Iterator _iter = sortList.iterator();_iter.hasNext();){
//						FDCSplitBillEntryInfo splitEntry = (FDCSplitBillEntryInfo)_iter.next();
//						if(item.getCostAccount().getId().toString().equals(splitEntry.getCostAccount().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
//							judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
//						}
//					}
					//R101108-137 ����Ӧ��ֻȡ�÷���ϸ�ڵ��µĲ�Ʒ��֡���Ϊ�б����֣����Կ��ܴ��ڶ����ͬ��Ŀ�Ĳ�ּ�¼
					//�������֮ǰ�Ĵ����߼���������е���ͬ��Ŀ�Ĳ�Ʒ��ֶ�ȡ����  by zhiyuan_tang
					int startIndex = sortList.indexOf(item);
					if (startIndex < sortList.size() - 1) {
						for (int i = startIndex + 1 ; i < sortList.size(); i++) {
							FDCSplitBillEntryInfo splitEntry = (FDCSplitBillEntryInfo)sortList.get(i);
							if(item.getCostAccount().getId().toString().equals(splitEntry.getCostAccount().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
								judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
							} else {
								break;
							}
						}
					}
					Collections.sort(judgeLastProductSplit);
				}else{//��ϸ�ڵ�
					FDCSplitBillEntryInfo currentProductFloorEntry = (FDCSplitBillEntryInfo)tempMap.get(item.getCostAccount().getId().toString()+"_"+new Integer(item.getLevel()-1).toString());
					if(currentProductFloorEntry!=null){
						BigDecimal tempCostSplitAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("costAmt"));
						BigDecimal distributeAmt = FDCHelper.divide(FDCHelper.multiply(item.getBigDecimal("costAmt"), currentProductFloorEntry.getBigDecimal("payedAmt")), tempCostSplitAmt);
						if(hasValue!=null&&!hasValue.booleanValue()){
							tempTotalAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("payedAmt"),2);
						}
						hasValue = Boolean.TRUE;
						if(distributeAmt==null){
							distributeAmt=FDCHelper.ZERO;
						}
						if (tempTotalAmt.abs().compareTo(distributeAmt.abs()) > 0) {
							item.setBigDecimal("payedAmt", distributeAmt);
							tempTotalAmt = tempTotalAmt.subtract(distributeAmt);
						} else {
							item.setBigDecimal("payedAmt", tempTotalAmt);
							tempTotalAmt = FDCHelper.ZERO;
						}
						if(item!=null&&judgeLastProductSplit.get(judgeLastProductSplit.size()-1).equals(new Integer(item.getSeq()))){
								if (tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
									item.setBigDecimal("payedAmt", FDCHelper.toBigDecimal(item.getBigDecimal("payedAmt")).add(tempTotalAmt));
								}
						}
						continue;
					}
				}
			}
			//���еĹ���������=���Ѳ�ָ��������ϸ�кϼ�+��ͷ��ʵ����*�����еĳɱ���ֽ��/�ɱ���ֽ�����ϸ�кϼƣ�-���е��Ѳ�ָ�����
			BigDecimal distributeAmt = FDCHelper.subtract(FDCHelper.divide(FDCHelper.multiply(FDCHelper.add(hadSplitPayedAmtSum,headPayedAmt), item.getBigDecimal("costAmt")),splitCostAmtSum), item.getBigDecimal("splitedPayedAmt"));
			if(distributeAmt==null){
				distributeAmt =FDCHelper.ZERO;
			}
			if (_tempTotalAmt.abs().compareTo(distributeAmt.abs()) > 0) {
				item.setBigDecimal("payedAmt", distributeAmt);
				_tempTotalAmt = _tempTotalAmt.subtract(distributeAmt);
			} else {
				item.setBigDecimal("payedAmt", _tempTotalAmt);
				_tempTotalAmt = FDCHelper.ZERO;
			}
		}
		if(item!=null){
			if (_tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
				item.setBigDecimal("payedAmt", FDCHelper.toBigDecimal(item.getBigDecimal("payedAmt")).add(_tempTotalAmt));
			}
		}
	}
	/**
	 * ���������ɶ���������ɿ�splitCostAmtBaseOnProp������߶�Ӧ��ע�ͣ��������ϸ�Ĵ���˵�������ɸ���ʵ������������ע�ͣ�
	 * by cassiel_peng 2010-01-19 
	 */
	public static void splitInvoiceAmtBaseOnProp(FDCSplitBillEntryCollection entrys, Map map){
		/**
		 * ������Ʊ���
		 */
		BigDecimal headInvoiceAmt = FDCHelper.toBigDecimal(map.get("headInvoiceAmt"),2);//��ͷ�ķ�Ʊ���
		BigDecimal hadSplitInvoiceAmtSum = FDCHelper.toBigDecimal(map.get("hadSplitInvoiceAmtSum"),2);//�Ѳ�ַ�Ʊ������ϸ�кϼ�
		BigDecimal splitInvoiceAmtSum = FDCHelper.toBigDecimal(map.get("splitCostAmtSum"),2);//�ɱ���ֽ�����ϸ�кϼ�
		FDCSplitBillEntryInfo item = null;
		final List sortList = AutoSplitSortor.filterExceptProductSplit(entrys);
		BigDecimal _tempTotalAmt = headInvoiceAmt;
		BigDecimal tempTotalAmt = FDCHelper.ZERO;
		Boolean hasValue = null;
		List judgeLastProductSplit = null;//�����ж��ǲ��ǲ�Ʒ�������ĳһ���ɱ���Ŀ���±ߵ����һ����Ʒ�����
		HashMap tempMap = new HashMap();
		for(Iterator iter = sortList.iterator();iter.hasNext();){
			if(headInvoiceAmt.compareTo(FDCHelper.ZERO)==0||headInvoiceAmt.signum()==0){
				break;
			}
			item = (FDCSplitBillEntryInfo) iter.next();
			if(CostSplitTypeEnum.PRODSPLIT.equals(item.getSplitType())){//��Ʒ���
				if(!item.isIsLeaf()){//�Ƿ���ϸ�ڵ�
					hasValue = Boolean.FALSE;
					tempMap.put(item.getCostAccount().getId().toString()+"_"+new Integer(item.getLevel()).toString(), item);//�ɱ���Ŀ+������Ϊ��
					
					judgeLastProductSplit = new ArrayList();
//					for(Iterator _iter = sortList.iterator();_iter.hasNext();){
//						FDCSplitBillEntryInfo splitEntry = (FDCSplitBillEntryInfo)_iter.next();
//						if(item.getCostAccount().getId().toString().equals(splitEntry.getCostAccount().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
//							judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
//						}
//					}
					//R101108-137 ����Ӧ��ֻȡ�÷���ϸ�ڵ��µĲ�Ʒ��֡���Ϊ�б����֣����Կ��ܴ��ڶ����ͬ��Ŀ�Ĳ�ּ�¼
					//�������֮ǰ�Ĵ����߼���������е���ͬ��Ŀ�Ĳ�Ʒ��ֶ�ȡ����  by zhiyuan_tang
					int startIndex = sortList.indexOf(item);
					if (startIndex < sortList.size() - 1) {
						for (int i = startIndex + 1 ; i < sortList.size(); i++) {
							FDCSplitBillEntryInfo splitEntry = (FDCSplitBillEntryInfo)sortList.get(i);
							if(item.getCostAccount().getId().toString().equals(splitEntry.getCostAccount().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
								judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
							} else {
								break;
							}
						}
					}
					Collections.sort(judgeLastProductSplit);
				}else{//��ϸ�ڵ�
					FDCSplitBillEntryInfo currentProductFloorEntry = (FDCSplitBillEntryInfo)tempMap.get(item.getCostAccount().getId().toString()+"_"+new Integer(item.getLevel()-1).toString());
					if(currentProductFloorEntry!=null){
						BigDecimal tempCostSplitAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("costAmt"));
						BigDecimal distributeAmt  = FDCHelper.divide(FDCHelper.multiply(item.getBigDecimal("costAmt"),currentProductFloorEntry.getBigDecimal("invoiceAmt")),tempCostSplitAmt );
						if(hasValue!=null&&!hasValue.booleanValue()){
							tempTotalAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("invoiceAmt"),2);
						}
						hasValue = Boolean.TRUE;
						if(distributeAmt==null){
							distributeAmt=FDCHelper.ZERO;
						}
						if (tempTotalAmt.abs().compareTo(distributeAmt.abs()) > 0) {
							item.setBigDecimal("invoiceAmt", distributeAmt);
							tempTotalAmt = tempTotalAmt.subtract(distributeAmt);
						} else {
							item.setBigDecimal("invoiceAmt", tempTotalAmt);
							tempTotalAmt = FDCHelper.ZERO;
						}
						if(item!=null&&judgeLastProductSplit.get(judgeLastProductSplit.size()-1).equals(new Integer(item.getSeq()))){
							if (tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
								item.setBigDecimal("invoiceAmt", FDCHelper.toBigDecimal(item.getBigDecimal("invoiceAmt")).add(tempTotalAmt));
							}
						}
						continue;
					}
				}
			}
			// ÿ����ϸ��Ŀ�У����еĹ�����Ʊ���=���Ѳ�ַ�Ʊ������ϸ�кϼ�+��ͷ�ķ�Ʊ��*�����еĳɱ���ֽ��/�ɱ���ֽ�����ϸ�кϼƣ�-���е��Ѳ�ַ�Ʊ���
			BigDecimal distributeAmt = FDCHelper.subtract(FDCHelper.divide(FDCHelper.multiply(FDCHelper.add(hadSplitInvoiceAmtSum,headInvoiceAmt), item.getBigDecimal("costAmt")),splitInvoiceAmtSum), item.getBigDecimal("splitedInvoiceAmt"));
			if(distributeAmt==null){
				distributeAmt=FDCHelper.ZERO;
			}
			//֧�ָ���,�Ծ���ֵ���� by hpw 2010-03-23
			if (_tempTotalAmt.abs().compareTo(distributeAmt.abs()) > 0) {
				item.setBigDecimal("invoiceAmt", distributeAmt);
				_tempTotalAmt = _tempTotalAmt.subtract(distributeAmt);
			} else {
				item.setBigDecimal("invoiceAmt", _tempTotalAmt);
				_tempTotalAmt = FDCHelper.ZERO;
			}
		}
		if(item!=null){
			if (_tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
				item.setBigDecimal("invoiceAmt", FDCHelper.toBigDecimal(item.getBigDecimal("invoiceAmt")).add(_tempTotalAmt));
			}
		}
	}
}
