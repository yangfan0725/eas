package com.kingdee.eas.fdc.finance.client;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.util.client.EASResource;

/**
 * ���ڰ󶨸���༭�����¼�е�cell������
 * @author zhonghui_luo
 *
 */
public class PaymentBillClientUtils {
private static final Logger logger = CoreUIObject.getLogger(AbstractPaymentBillEditUI.class);
	
	
	/*
	 * Description:
	 * 		�󶨵�Ԫ��Ҫ�洢��ֵ,��ֵ���ڵĵ�Ԫ��Ϊ��������Ӧ�ĵ�Ԫ����ұ�һ����
	 * @param table
	 * @param rowIndex
	 * @param colIndex		
	 * @param name			Ҫ��ʾ������
	 * @param key 		ֵ,�����ö���������,��auditor.name
	 * @param bindCellMap
	 */
	public static void bindCell(KDTable table,int rowIndex,int colIndex,String name,String key,HashMap bindCellMap){
		ICell cell=table.getCell(rowIndex, colIndex);
		cell.setValue(name);
		/*
		 * ֵ���
		 */
		cell=table.getCell(rowIndex, colIndex+1);
//		cell.getStyleAttributes().setBackground(Color.LIGHT_GRAY);
		//�󶨶�Ӧ��ϵ
		bindCellMap.put(key, cell);
	}

	/*
	 * Description:
	 *		�󶨶��󵽵�Ԫ��
	 * @param cell
	 * @param valueName
	 * @param valueProperty
	 * @param bindCellMap
	 */
	public static void bindCell(ICell cell,String key,HashMap bindCellMap){
		//�󶨶�Ӧ��ϵ
		bindCellMap.put(key, cell);
	}
	
	public static void bindCell(KDTable table,int rowIndex,int colIndex,String name,String key,HashMap bindCellMap,boolean numberEditor){
		ICell cell=table.getCell(rowIndex, colIndex);
		cell.setValue(name);
		/*
		 * ֵ���
		 */
		cell=table.getCell(rowIndex, colIndex+1);
		bindCell(cell, key, bindCellMap, numberEditor);
	}
	
	public static void bindCell(ICell cell,String key,HashMap bindCellMap,boolean numberEditor){
		if(numberEditor){
			cell.setEditor(getCellNumberEdit());
		}
		//�󶨶�Ӧ��ϵ
		bindCellMap.put(key, cell);
	}
	public static KDTDefaultCellEditor getCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);;
        kdc.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
        kdc.setHorizontalAlignment(JTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	public static boolean getValueFromCell(IObjectValue info,HashMap map){
		if(info==null||map==null) return false;
		Set keys = map.keySet();
		String key=null;
		Iterator iter=keys.iterator();
		while(iter.hasNext()){
			key=(String)iter.next();
			Object cell=map.get(key);
			if(cell instanceof ICell){
				/*	
				 * ��IObjectValue�ķ�����������ֵΪnull��key�ʲ���ͨ��key���ж�
				 *  info�Ƿ��и�����
				 */
				int index = key.indexOf('.');
				if(index>0) {
					//ObjectValue
					String infoKey=key.substring(0,index);
					String propKey=key.substring(index+1);
					Object subinfo=info.get(infoKey);
					if (subinfo instanceof IObjectValue)
					{
						((IObjectValue) subinfo).put(propKey, ((ICell)cell).getValue());
						
					}
				
				}else{
					//��ObjectValue
					info.put(key,((ICell)cell).getValue());
				}
			}
		}
		
		return true;
	}
	public static boolean setValueToCell(IObjectValue info,HashMap map){
		if(info==null||map==null) return false;
		Set keys = map.keySet();
		String key=null;
		Iterator iter=keys.iterator();
		while(iter.hasNext()){
			key=(String)iter.next();
			if(key.equalsIgnoreCase(PaymentBillContants.PRJALLREQAMT)||
				key.equalsIgnoreCase(PaymentBillContants.ADDPRJALLREQAMT)||
				key.equalsIgnoreCase(PaymentBillContants.PAYPARTAMATLALLREQAMT)||
				key.equalsIgnoreCase(PaymentBillContants.CURPAID)){
				continue;
			}
			
			Object cell=map.get(key);
			int index = key.indexOf('.');
			Object value;
			if(index>0) {
				//objectValue
				String infoKey=key.substring(0,index);
				String propKey=key.substring(index+1);					
				Object subinfo=info.get(infoKey);
				if (subinfo instanceof IObjectValue)
				{
					value=((IObjectValue) subinfo).get(propKey);
					((ICell)cell).setValue(value);
				}
			}else{
				//��ObjectValue
				value=info.get(key);
				((ICell)cell).setValue(value);
			}
		}
		
		return true;
	}

	/**
	 * ��������ȡ��Դ������Դ�ļ�com.kingdee.eas.fdc.contract.client.ContractResource��
	 * @author sxhong  		Date 2006-9-14
	 * @param resName	��Դ������
	 * @return String ��ȡ����Դ
	 */
	public static String getRes(String resName) {
		return EASResource
				.getString(
						"com.kingdee.eas.fdc.finance.client.PaymentBillResource",
						resName);
	}
		
	public static void showOprtOK(Component comp) {
		FDCClientUtils.showOprtOK(comp);
	}
	/**
	 * ����ͬʵ���� =�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼơ�
	 * �ں�ͬ���ս���󣬸���ύ������ʱ,�����ͬʵ����(��������)�ӱ��θ����ͬ�ڹ��̿�ڷ������ں�ͬ���������ʾ��
     * �Ҳ����ύ������ͨ����  by cassiel_peng 2009-12-02
     * @see boolean com.kingdee.eas.fdc.finance.client.PaymentBillClientUtils.checkForSaveSubmitAudit(Set paymenSet) throws Exception
	 */
	public static boolean checkProjectPriceInContract(Set paymentSet) throws EASBizException, BOSException{
		boolean retValue=true;
		PaymentBillInfo paymentBill=null;
		BigDecimal settlePrice=FDCHelper.ZERO;//��ͬ������
		BigDecimal thisTimePayAmt=FDCHelper.ZERO;//���θ����ͬ�ڹ��̿�ڷ���
		BigDecimal prjPriceInCon=FDCHelper.ZERO;//��ͬʵ����=�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼ�
		BigDecimal thisTimePrjPriceInCon=FDCHelper.ZERO;//���εĺ�ͬʵ����(��ͬʵ����(��������)�ӱ��θ����ͬ�ڹ��̿�ڷ������ں�ͬ����۹�ʽ�е�"����"����)
		BigDecimal conPayAmtExceptThisTime=FDCHelper.ZERO;//��ͬʵ����(��������)=prjPriceInCon-thisTimePrjPriceInCon
		BigDecimal totalPrice=FDCHelper.ZERO;//��ͬʵ����(��������)+���θ����ͬ�ڹ��̿�ڷ���=conPayAmtExceptThisTime+thisTimePayAmt
		if(paymentSet!=null&&paymentSet.size()>0){
			paymentBill=(PaymentBillInfo)paymentSet.iterator().next();//�˴��������ж��ٸ��,�����м����������Ӧ�ĺ�ͬ�������Ψһ��
			String contractId=paymentBill.getContractBillId();
			boolean isContractWithTxt=FDCUtils.isContractBill(null, contractId);
			//��������ı���ͬ�Ͳ�Ӧ���������߼�����
			if(isContractWithTxt){
				ContractBillInfo contract=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				if(!contract.isHasSettled()){
					return retValue;
				}
				settlePrice=FDCHelper.toBigDecimal(contract.getSettleAmt(),2);//��ͬ������
				
				prjPriceInCon=ContractClientUtils.getPayAmt(contractId);//��ͬʵ����=�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼ�
				
				if(paymentSet.size()==1){
					//ע�Ȿ�εĺ�ͬʵ����Ӧ�����ȡ��:�ѹرյĸ������뵥Ӧ��ȡ��Ӧ�ĸø����ͬ�ڹ��̿�,δ�رյĸ������뵥Ӧ��ȡ�������뵥��ͬ�ڹ��̿�ϼ�
					SelectorItemCollection selector=new SelectorItemCollection();
					selector.add("hasClosed");
					selector.add("projectPriceInContract");
					PayRequestBillInfo payReqBill=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(paymentBill.getFdcPayReqID())),selector);
					if(payReqBill.isHasClosed()){//�Ѿ��ر�
						thisTimePrjPriceInCon=FDCHelper.toBigDecimal(paymentBill.getProjectPriceInContract(),2);
					}else{
						thisTimePrjPriceInCon=FDCHelper.toBigDecimal(payReqBill.getProjectPriceInContract(),2);
					}
				}else{//�˴�һ���Ƕ��Ÿ����������
					for (Iterator iter=paymentSet.iterator();iter.hasNext();) {
						paymentBill=(PaymentBillInfo)iter.next();
						SelectorItemCollection selector=new SelectorItemCollection();
						selector.add("hasClosed");
						selector.add("projectPriceInContract");
						PayRequestBillInfo payReqBill=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(paymentBill.getFdcPayReqID())),selector);
						if(payReqBill.isHasClosed()){//�Ѿ��ر�
							thisTimePrjPriceInCon=FDCHelper.toBigDecimal(FDCHelper.add(thisTimePrjPriceInCon, paymentBill.getProjectPriceInContract()),2);
						}else{
							thisTimePrjPriceInCon=FDCHelper.toBigDecimal(FDCHelper.add(thisTimePrjPriceInCon, payReqBill.getProjectPriceInContract()),2);
						}
					}
				}
				conPayAmtExceptThisTime=FDCHelper.toBigDecimal(FDCHelper.subtract(prjPriceInCon, thisTimePrjPriceInCon),2);
				totalPrice=FDCHelper.toBigDecimal(FDCHelper.add(conPayAmtExceptThisTime,thisTimePayAmt),2);
				if(settlePrice!=null && settlePrice.compareTo(totalPrice)==-1){
					retValue=false;
				}
			}
		}
		return retValue;
	}
	/**
	 * �ں�ͬ���ս����,����ύ������ʱ������������ĸ����ͬ�ڹ����ۼƽ��ӱ��ڷ������ں�ͬ�����
	 * ����ʾ�������ͬ�ڹ��̿��ۼƽ��ܴ��ں�ͬ����ۡ�  by cassiel_peng  2009-08-12 <p>
	 * ���ڲ����߼������仯���ں�ͬ���ս���󣬸���ύ������ʱ,�����ͬʵ����(��������)�ӱ��θ����ͬ�ڹ��̿�ڷ������ں�ͬ���������ʾ��
	 * �Ҳ����ύ������ͨ�����˷�������ʹ�ã����� by cassiel_peng 2009-12-02
	 * @see boolean com.kingdee.eas.fdc.finance.client.PaymentBillClientUtils.checkProjectPriceInContract(Set paymentSet)<p>
	 */
	public static boolean checkForSaveSubmitAudit(/*PaymentBillInfo paymentBill*/Set paymenSet) throws Exception{
		
		boolean retValue=true;
		PaymentBillInfo paymentBill=null;
		BigDecimal settlePrice=FDCHelper.ZERO;//��ͬ���㵥���
		BigDecimal thisTimeAmt=FDCHelper.ZERO;//���θ���ĺ�ͬ�ڹ��̽��
		BigDecimal beforeThisTime=FDCHelper.ZERO;//���θ��֮ǰ���������ĸ���ĺ�ͬ�ڹ��̺ϼƽ��
		BigDecimal totalPrice=FDCHelper.ZERO;//�������ĸ���ĺ�ͬ�ڹ��̺ϼƽ���뱾�θ�����֮��
		if(paymenSet!=null&&paymenSet.size()>0){
			paymentBill=(PaymentBillInfo)paymenSet.iterator().next();//�˴��������ж��ٸ��,�����м����������Ӧ�ĺ�ͬ�������Ψһ��
			String contractId=paymentBill.getContractBillId();
			boolean isContractWithTxt=FDCUtils.isContractBill(null, contractId);
			//��������ı���ͬ�Ͳ�Ӧ���������߼�����    by Cassiel_peng  2008-8-27
			if(isContractWithTxt){
				ContractBillInfo contract=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				if(!contract.isHasSettled()){
					return retValue;
				}
				settlePrice=FDCHelper.toBigDecimal(contract.getSettleAmt(),2);
				
				EntityViewInfo view=new EntityViewInfo(); 
				FilterInfo filter=new FilterInfo();
				if(paymenSet.size()==1){//���ֻ��һ�Ÿ��
					thisTimeAmt=FDCHelper.toBigDecimal(paymentBill.getProjectPriceInContract(),2);
					view.getSelector().add("id");
					//��ͬ����֮��,��ͬʵ���ϵ�settleAmt(�����)�����ս��㵥�� totalSettlePrice(�ۼƽ�����)����ͬ��
					view.getSelector().add("settleAmt");
				}else{//���Ÿ��һ������
					for (Iterator iter=paymenSet.iterator();iter.hasNext();) {
						paymentBill=(PaymentBillInfo)iter.next();
						thisTimeAmt=FDCHelper.toBigDecimal(FDCHelper.add(thisTimeAmt,FDCHelper.toBigDecimal(paymentBill.getProjectPriceInContract(),2)),2);
					}
				}
				
//				 TypeInfo payType=paymentBill.getFdcPayType().getPayType();��ʱ��ȡ�����������Ի���ֱ��дsql��ɣ�
				 /* FDCSQLBuilder builder=new FDCSQLBuilder();
				 builder.appendSql("select FPayTypeId from T_FDC_Type type \n ");
				 builder.appendSql("inner join T_FDC_PaymentType paytype on paytype.FPayTypeID=type.fid \n ");
				 builder.appendSql("inner join T_CAS_PaymentBill pay on paytype.Fid=pay.FFdcPayTypeID \n ");
				 builder.appendSql("where pay.fid=? \n ");
				 builder.addParam(paymentBill.getId().toString());
				 IRowSet rowSet=builder.executeQuery();
				 if(rowSet.next()){
					 String payTypeId=rowSet.getString("FPayTypeId");
					 if(payTypeId!=null&&!"".equals(payTypeId)){
//						 boolean isProgressAmt= payTypeId.getId().toString().equals(PaymentTypeInfo.progressID);
						 boolean isProgressAmt= payTypeId.equals(PaymentTypeInfo.progressID);
						 if(isProgressAmt){//���Ϊ���ȿ�
							 return retValue;
						 }else{//���Ϊ���޿�����ǽ����
		*/					 	view=new EntityViewInfo();
								view.getSelector().add("id");
								view.getSelector().add("projectPriceInContract");
								view.getSelector().add("billStatus");
								
								filter=new FilterInfo();
								filter.getFilterItems().add(new FilterItemInfo("contractBillId",paymentBill.getContractBillId().toString()));
								filter.getFilterItems().add(new FilterItemInfo("billStatus",new Integer(
										com.kingdee.eas.fi.cas.BillStatusEnum.AUDITED_VALUE),
										CompareType.GREATER_EQUALS));
								filter.getFilterItems().add(new FilterItemInfo("id",paymentBill.getId().toString(),CompareType.NOTINCLUDE));//���θ���ĺ�ͬ�ڹ��̽��
								view.setFilter(filter);
								
								IPaymentBill payment=PaymentBillFactory.getRemoteInstance();
								PaymentBillCollection _coll=payment.getPaymentBillCollection(view);
								PaymentBillInfo info = null;
								for(Iterator it = _coll.iterator();it.hasNext();){
									info = (PaymentBillInfo) it.next();
									beforeThisTime = beforeThisTime.add(FDCHelper.toBigDecimal(info.getProjectPriceInContract(),2));
								}
								totalPrice=FDCHelper.add(beforeThisTime, thisTimeAmt);
								if(settlePrice!=null && settlePrice.compareTo(totalPrice)==-1){
									retValue=false;
								}
//						 }
//					 }
//				 }
			}
		}
	return retValue;
	}
}





























