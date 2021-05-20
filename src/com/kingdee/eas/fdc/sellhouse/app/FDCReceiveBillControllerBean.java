package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jxl.write.DateFormats;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.propertymgmt.DepositBillCollection;
import com.kingdee.eas.fdc.propertymgmt.DepositBillFactory;
import com.kingdee.eas.fdc.propertymgmt.DepositBillInfo;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillCollection;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillFactory;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
import com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.ReceivingBillEntryFactory;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fm.nt.NTNumberFormat;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.scm.sm.pur.util.DateHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class FDCReceiveBillControllerBean extends AbstractFDCReceiveBillControllerBean
{
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.FDCReceiveBillControllerBean");

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException
	{
		
		return super._submit(ctx, model);
		
	}
	
	protected void _updatePrintCount(Context ctx, String billID, int printCount)
			throws BOSException, EASBizException
	{
		// TODO Auto-generated method stub
		String strSql="";
		StringBuffer strBuffer=new StringBuffer();
		strBuffer.append("update T_SHE_FDCReceiveBill set FPrintCount="+printCount+" where fid in"+"\n");
		strBuffer.append("(select FFdcReceiveBillID from  T_CAS_ReceivingBill where fid='"+billID+"')");
		
		strSql=strBuffer.toString();
		try{
			DbUtil.execute(ctx, strSql);
		}catch(Exception ex){
			throw new BOSException(ex.getMessage());
		}
		
		
	}

	protected int _getPrintCount(Context ctx, String billID)
			throws BOSException, EASBizException
	{
		// TODO Auto-generated method stub
		String strSql="";
		int printCount=0;
		StringBuffer strBuffer=new StringBuffer();
		
		strBuffer.append("select distinct isnull(t1.FPrintCount,0) FPrintCount from T_SHE_FDCReceiveBill t1"+"\n");
		strBuffer.append("inner join T_CAS_ReceivingBill t2 on"+"\n");  
		strBuffer.append("t2.FFdcReceiveBillID =t1.FID"+"\n");
		strBuffer.append("where t2.fid='"+billID+"'");
			
		
		strSql=strBuffer.toString();
		IRowSet rowSet=null;
		try{
			rowSet=DbUtil.executeQuery(ctx, strSql);
			while(rowSet.next()){
				printCount=rowSet.getInt("FPrintCount");
			}
		}catch(Exception ex){
			throw new BOSException(ex.getMessage());
		}
		return printCount;
	}
	/**
	 * @author jetdai
	 * ������ʱӦ�յ�
	 * */
	private void updateTempBillByID(Context ctx,String tempID,BigDecimal arAmount,BigDecimal payAmount) throws Exception{
		EntityViewInfo env=new EntityViewInfo();
		FilterInfo filterInfo=new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", tempID,CompareType.INCLUDE));
		filterInfo.setMaskString("#0");
		env.setFilter(filterInfo);
		
		PPMTemporaryInfo ppmInfo= PPMTemporaryFactory.getLocalInstance(ctx).getPPMTemporaryInfo(new ObjectUuidPK(tempID));
		//����Ӧ�գ��Ѹ���
		ppmInfo.setArAmout(arAmount);
		ppmInfo.setPayedAmount(payAmount);
		PPMTemporaryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(tempID), ppmInfo);
	}

	//jetdai 2009-12-11
	//�ڵ����ϵ��ύʱ��������ʱӦ�յ�
	protected void _addTemporaBill(Context ctx, ArrayList listBills,
			IObjectValue billInfo) throws BOSException, EASBizException
	{
		// TODO Auto-generated method stub
		if(listBills.size()>0){
			//�жϱ��������տ�Ƿ�����������ʱӦ�յ��µ��ݣ�����У�ֻ������Ѹ���������
			
			
			ReceivingBillInfo billInfos = (ReceivingBillInfo) billInfo;
			FDCReceiveBillInfo fdcRecBillInfo= billInfos.getFdcReceiveBill();
			String billNumber="",tempID="";
			Map map=null;
			
			//���տ ���³� ���� �տ�
			
			//�ں������տ�������տ�ҲΪ�տ�����
			//fdcRecBillInfo.setBillTypeEnum(ReceiveBillTypeEnum.);
			//FDCReceiveBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(fdcRecBillInfo.getId().toString()), fdcRecBillInfo);
			
			CustomerEntryCollection customerEntryColl=fdcRecBillInfo.getCustomerEntrys();//�տ�б�׼�Ŀͻ�
			for(int j=0;j<customerEntryColl.size();j++){
				CustomerEntryInfo customerInfo=customerEntryColl.get(j);
				CustomerInfo cusInfo=customerInfo.getCustomer();//ĳ����׼ �ͻ�
				
				//����ĳ����׼�ͻ� ���˳���Ӧ�ķ��ز��ͻ�
				EntityViewInfo env=new EntityViewInfo();
				FilterInfo filterInfo=new FilterInfo();
				filterInfo.getFilterItems().add(new FilterItemInfo("sysCustomer.id", cusInfo.getId().toString(),CompareType.INCLUDE));
				filterInfo.setMaskString("#0");
				env.setFilter(filterInfo);
				FDCCustomerCollection fdcCustomerCollection= FDCCustomerFactory.getLocalInstance(ctx).getFDCCustomerCollection(env);
				for(int t=0;t<fdcCustomerCollection.size();t++){
					FDCCustomerInfo fdcCustomerInfo=fdcCustomerCollection.get(t);
					//ÿ���ͻ�����һ����Ӧ�ĵ���
					for(int i=0;i<listBills.size();i++){
						map=(HashMap)listBills.get(i);
						//�ж� �������տ��Ӧ �Ŀ��������� ��ʱӦ�յ��д治����
						try{
							tempID=GetTempoarBill(ctx, billInfos.getId().toString(), map.get("moneyTypeID").toString());
							if(!tempID.equalsIgnoreCase("")){
								//ֱ�Ӹ���
								updateTempBillByID(ctx,tempID,(BigDecimal)map.get("appAmount"),(BigDecimal)map.get("gatheringAmount"));
								continue;
							}
						}catch(Exception ex){
							throw new BOSException(ex.getMessage());
						}
						//************************ end     
						
						
						PPMTemporaryInfo ppmTempInfo=new PPMTemporaryInfo();
						
						//0.UUID
						//�ں������տ�
						
						//String payListId = BOSUuid.create(new PPMTemporaryInfo().getBOSType()).toString();
						String payListId = String.valueOf(map.get("payListId"));
						ppmTempInfo.setId(BOSUuid.read(payListId));
						//1.���ݱ��
						billNumber=getBillNumber(ppmTempInfo, ctx);
						//ȡ����������
						if(billNumber.equalsIgnoreCase("")){
							Date date=SysUtil.getAppServerTime(ctx);
							DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
							String dateStr = df.format(date);
							billNumber=dateStr;
						}
						
						ppmTempInfo.setNumber(billNumber);
						//2.��ҵ��Ŀ
						//SellProjectInfo selInfo=SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(rowSet.getString("SELLPROJECTID")));
//						ppmTempInfo.setSellproject(fdcRecBillInfo.getSellProject());
						//3.ҵ������
						ppmTempInfo.setBizDate(fdcRecBillInfo.getCreateTime());
						//4.����
						//RoomInfo roomInfo=RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(rowSet.getString("ROOMid")));
//						ppmTempInfo.setRoom(fdcRecBillInfo.getRoom());
						//5.�ͻ�
						//FDCCustomerInfo custInfo=FDCCustomerFactory.getLocalInstance(ctx).getFDCCustomerInfo(new ObjectUuidPK(rowSet.getString("FCustomerID")));
						ppmTempInfo.setCustomer(fdcCustomerInfo);
						//6.�շ���Ŀ
						MoneyDefineInfo moneyDefineInfo=MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo(new ObjectUuidPK(map.get("moneyTypeID").toString()));
//						ppmTempInfo.setChargeItem(moneyDefineInfo);
						//7.Ӧ�ս��
						ppmTempInfo.setArAmout((BigDecimal)map.get("appAmount"));
						//8.�Ѹ����
						ppmTempInfo.setPayedAmount((BigDecimal)map.get("gatheringAmount"));
						//9.Ӧ�ɷ�����
						ppmTempInfo.setArDate(billInfos.getBizDate());
						//10.�Ƶ���
						//UserInfo userInfo=UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(rowSet.getString("FCreatorid")));
						ppmTempInfo.setCreator(fdcRecBillInfo.getCreator());
						
						//11.����״̬
						ppmTempInfo.setState(FDCBillStateEnum.AUDITTED);// 
						
						ppmTempInfo.setDescription("���������տ{"+billInfos.getNumber().toString()+"����!}");
						//12.�Ƶ����ڡ������ˡ���������,CU
						ppmTempInfo.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
						//OrgUnitInfo orgInfo= ContextUtil.getCurrentOrgUnit(ctx);

						/*ppmTempInfo.setCreateTime(null);
						ppmTempInfo.setAuditor(null);
						ppmTempInfo.setAuditTime(null);
						ppmTempInfo.setCU(null);*/
						
						
						//������ʱӦ�յ�
						PPMTemporaryFactory.getLocalInstance(ctx).save(ppmTempInfo);
						
						//�ں������տ����Բ��ø���
						/**
						//���տ ���³� ���� �տ�
						fdcRecBillInfo.setBillTypeEnum(ReceiveBillTypeEnum.partial);
						FDCReceiveBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(fdcRecBillInfo.getId().toString()), fdcRecBillInfo);
						//******************  BEGIN ***************
						//���ֻ��ϣ���տ����ͨ�ͻ���Ӧ���ز��ͻ�ʱ��ֻ��һ����
						//�����Ϳ��԰�Ӧ�ս�д���տ��¼�ϡ�����ж���ͻ����Ͳ������ڴ��߼���
						StringBuffer strBuffer =new StringBuffer();
						String sql="";
						strBuffer.append("update T_SHE_FDCReceiveBillEntry set FPayListID='"+payListId+"'"+"\n");
						strBuffer.append("where  FReceivingBillID='"+billInfos.getId().toString()+"'"+"\n");
						strBuffer.append("and FMoneyDefineID='"+moneyDefineInfo.getId().toString()+"'"+"\n");
						sql=strBuffer.toString();
						DbUtil.execute(ctx, sql);
						//******************  END  ************************
						*/
						
					}
				}
			}
			
		}
		
	}
	
	/**
	 * @author jetdai
	 * date:2009-12-13
	 * �������ж���δ������ĸ�����ϸID����ʱӦ�յ��ϴ治����
	 * */
	private String GetTempoarBill(Context ctx,String billID,String moneyTypeID)throws Exception{
		boolean bol=false;
		String tempBillID="";
		String sql="";
		StringBuffer strBuffer=new StringBuffer();
		//IRowSet rowSet= DbUtil.executeQuery(ctx,sql);
		//IRowSet rowSet=null;
		//FPayedAmount
		//update T_PPM_PPMTemporary set FPayedAmount= where fid= and FChargeItemID=
		strBuffer.append("select fid,FChargeItemID ,count(*) as fcnt from T_PPM_PPMTemporary"+"\n");
		strBuffer.append("where fid in"+"\n");
		strBuffer.append("("+"\n");
		strBuffer.append("select distinct FDCRECEIVEBILLENTRY.fpaylistid as fid"+"\n");
		strBuffer.append("FROM T_CAS_ReceivingBill AS RECEIVINGBILL"+"\n");
		strBuffer.append("INNER JOIN T_SHE_FDCReceiveBill AS FDCRECEIVEBILL"+"\n");
		strBuffer.append("ON RECEIVINGBILL.FFdcReceiveBillID = FDCRECEIVEBILL.FID"+"\n");
		strBuffer.append("LEFT OUTER JOIN T_SHE_FDCReceiveBillEntry AS FDCRECEIVEBILLENTRY"+"\n");
		strBuffer.append("ON RECEIVINGBILL.FID = FDCRECEIVEBILLENTRY.FReceivingBillID"+"\n");
		strBuffer.append("where RECEIVINGBILL.FID in ('"+billID+"')"+"\n");
		strBuffer.append(") and FChargeItemID='"+moneyTypeID+"'"+"\n");
		strBuffer.append("group by fid,FChargeItemID"+"\n");
		
		sql=strBuffer.toString();
		IRowSet rowSet= DbUtil.executeQuery(ctx,sql);
		try{
			while(rowSet.next()){
				tempBillID=rowSet.getString("fid");
			}
		}catch(Exception ex){
			throw new BOSException(ex.getMessage());
		}
		return tempBillID;
	}

	//jetdai 209-12-01
	//ʵ�֣� �����²��͵����ϵ�����ʱ���� ��ʱӦ�յ�
	protected void _addTemporaBill(Context ctx, ArrayList listIds)
			throws BOSException, EASBizException
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
		
		
		
		//1.�õ��տ�ĵ���ID
		String strIDs="";
		for (int i=0;i<listIds.size();i++){
			//ReceivingBillInfo receiveBill = (ReceivingBillInfo) listFDCReceiveID.getObject(i);
			strIDs=strIDs +"'"+listIds.get(i).toString()+"',";
		}
		//2.����SQL�������տID���й��ˡ������SQLһ����¼��Ҫ����һ����ʱӦ�յ�
		String sql="";
		StringBuffer strBuffer=new StringBuffer();
		try{
			if(strIDs.length()>0){
				strIDs=strIDs.substring(0, strIDs.length()-1);
				//����ѡ �е������տ� ����Ӧ����ʱӦ�յ��е�״̬ȫ����Ϊ ���
				strBuffer.append("update t_ppm_PPMTemporary set FState='4AUDITTED' where fid in"+"\n");
				strBuffer.append("("+"\n");
				strBuffer.append("select distinct FDCRECEIVEBILLENTRY.fpaylistid as fid"+"\n");
				strBuffer.append("FROM T_CAS_ReceivingBill AS RECEIVINGBILL"+"\n");
				strBuffer.append("INNER JOIN T_SHE_FDCReceiveBill AS FDCRECEIVEBILL"+"\n");
				strBuffer.append("ON RECEIVINGBILL.FFdcReceiveBillID = FDCRECEIVEBILL.FID"+"\n");
				strBuffer.append("LEFT OUTER JOIN T_SHE_FDCReceiveBillEntry AS FDCRECEIVEBILLENTRY"+"\n");
				strBuffer.append("ON RECEIVINGBILL.FID = FDCRECEIVEBILLENTRY.FReceivingBillID"+"\n");
				strBuffer.append("where RECEIVINGBILL.FID in ("+strIDs+")"+"\n");
				strBuffer.append("and FDCRECEIVEBILL.fbilltypeenum='partial'"+"\n");
				strBuffer.append(")"+"\n");
				
				sql=strBuffer.toString();
				DbUtil.execute(ctx, sql);

			}
		
		
		
		/*
		String strIDs="";
		for (int i=0;i<listIds.size();i++){
			//ReceivingBillInfo receiveBill = (ReceivingBillInfo) listFDCReceiveID.getObject(i);
			strIDs=strIDs +"'"+listIds.get(i).toString()+"',";
		}
		//2.����SQL�������տID���й��ˡ������SQLһ����¼��Ҫ����һ����ʱӦ�յ�
		String sql="";
		if(strIDs.length()>0){
			strIDs="("+strIDs.substring(0, strIDs.length()-1)+")";
			sql=getCreateTempPayBillSql(strIDs);
		}
		if(sql.length()==0)
			return;
		String billNumber="";
		IRowSet rowSet= DbUtil.executeQuery(ctx,sql);
		try{
			while(rowSet.next()){
				
				PPMTemporaryInfo ppmTempInfo=new PPMTemporaryInfo();
				
				//1.���ݱ��
				billNumber=getBillNumber(ppmTempInfo, ctx);
				//ȡ����������
				if(billNumber.equalsIgnoreCase("")){
					Date date=SysUtil.getAppServerTime(ctx);
					DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String dateStr = df.format(date);
					billNumber=dateStr;
					
				}
				
				ppmTempInfo.setNumber(billNumber);
				//2.��ҵ��Ŀ
				SellProjectInfo selInfo=SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(rowSet.getString("SELLPROJECTID")));
				ppmTempInfo.setSellproject(selInfo);
				//3.ҵ������
				ppmTempInfo.setBizDate(rowSet.getDate("CREATETIME"));
				//4.����
				RoomInfo roomInfo=RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(rowSet.getString("ROOMid")));
				ppmTempInfo.setRoom(roomInfo);
				//5.�ͻ�
				FDCCustomerInfo custInfo=FDCCustomerFactory.getLocalInstance(ctx).getFDCCustomerInfo(new ObjectUuidPK(rowSet.getString("FCustomerID")));
				ppmTempInfo.setCustomer(custInfo);
				//6.�շ���Ŀ
				MoneyDefineInfo moneyDefineInfo=MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo(new ObjectUuidPK(rowSet.getString("FMoneydefineID")));
				ppmTempInfo.setChargeItem(moneyDefineInfo);
				//7.Ӧ�ս��
				ppmTempInfo.setArAmout(new BigDecimal(rowSet.getDouble("FArAmout")));
				//8.�Ѹ����
				ppmTempInfo.setPayedAmount(rowSet.getBigDecimal("FDCRECEIVEBILLENTRYAMOUNT"));
				//9.Ӧ�ɷ�����
				ppmTempInfo.setArDate(rowSet.getDate("BIZDATE"));
				//10.�Ƶ���
				UserInfo userInfo=UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(rowSet.getString("FCreatorid")));
				ppmTempInfo.setCreator(userInfo);
				
				//11.����״̬
				ppmTempInfo.setState(FDCBillStateEnum.AUDITTED);
				ppmTempInfo.setDescription("���������տ{"+rowSet.getString("billNUMBER")+"����@!}");
				//12.�Ƶ����ڡ������ˡ���������,CU
				ppmTempInfo.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
				//OrgUnitInfo orgInfo= ContextUtil.getCurrentOrgUnit(ctx);

				ppmTempInfo.setCreateTime(null);
				ppmTempInfo.setAuditor(null);
				ppmTempInfo.setAuditTime(null);
				ppmTempInfo.setCU(null);
				
				
				//������ʱӦ�յ�
				PPMTemporaryFactory.getLocalInstance(ctx).addnew(ppmTempInfo);
				
				
			}*/
		}catch(Exception ex){
			throw new BOSException(ex.getMessage());
		}finally{
			//rowSet=null;
		}
		
		
		
		
		
	}
	
	/**
	 * @author jetdai
	 * date:2009-12-08
	 * ���������ر���
	 * ���� 
	 * */
	private String getBillNumber(CoreBillBaseInfo billInfo,Context ctx){
		OrgUnitInfo orgInfo=null;
		String billNumber="";
		String orgID="";
		orgInfo=ContextUtil.getCurrentOrgUnit(ctx);
		if(orgInfo!=null){
			orgID=orgInfo.getId().toString();
		}
		try
		{
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
			if (iCodingRuleManager.isExist(billInfo, orgID)) {// ���ù��������
				billNumber=iCodingRuleManager.getNumber(billInfo, orgID, "");
				//billInfo.setNumber(iCodingRuleManager.getNumber(saleIssueBillInfo, souID, ""));
			} else { 
				
				// �׳��쳣��ʾδ���ù��������
					/*IMetaDataLoader imeataLoader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
					EntityObjectInfo entityObjectInfo = imeataLoader.getEntity(sou.getBOSType());
					String[] materialNames = new String[1];
					materialNames[0] = entityObjectInfo.getAlias();
					throw new SCMBillException(SCMBillException.NOCORDRULE, materialNames);*/
				//}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}

		return billNumber;
	}
	
	
	/**
	 * @author jetdai
	 * date:2009-12-03
	 * ����������һ��SQL����ѯ�տ
	 * recIDS:ID������ �����ڡ�������'aaa','bbbb'��
	 * */
	private String getCreateTempPayBillSql(String recIDS){
		String strSql="";
		StringBuffer sqlBuffer=new StringBuffer();
		
		sqlBuffer.append("select ta.*,tb.FArAmout from"+"\n");
		sqlBuffer.append("("+"\n");
		sqlBuffer.append("SELECT"+"\n"); 
		sqlBuffer.append("RECEIVINGBILL.FID AS ID, --����ID"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FBillStatus AS BILLSTATUS, --����״̬"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FNumber AS billNUMBER, --���ݱ���"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FBizDate AS BIZDATE, --ҵ������"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FCreateTime AS CREATETIME, --�Ƶ�����"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FCreatorid AS FCreatorid, --�Ƶ���"+"\n");
		sqlBuffer.append("customer.FID as FCustomerID,--�ͻ�ID"+"\n");
		sqlBuffer.append("ROOM.Fid AS ROOMid, --����ID"+"\n");
		sqlBuffer.append("FDCRECEIVEBILL.FSellProjectID AS SELLPROJECTID, --������ĿID"+"\n");
		sqlBuffer.append("MONEYDEFINE.FID AS FMoneydefineID,--��¼ ��������ID"+"\n");
		sqlBuffer.append("FDCRECEIVEBILLENTRY.FPayListID as FListID,"+"\n");
		sqlBuffer.append("sum(isnull(FDCRECEIVEBILLENTRY.FAmount,0)) AS FDCRECEIVEBILLENTRYAMOUNT --�տ���"+"\n");
		sqlBuffer.append("FROM T_CAS_ReceivingBill AS RECEIVINGBILL"+"\n");
		sqlBuffer.append("INNER JOIN T_SHE_FDCReceiveBill AS FDCRECEIVEBILL"+"\n");
		sqlBuffer.append("ON RECEIVINGBILL.FFdcReceiveBillID = FDCRECEIVEBILL.FID"+"\n");
		sqlBuffer.append("LEFT OUTER JOIN T_SHE_FDCReceiveBillEntry AS FDCRECEIVEBILLENTRY"+"\n");
		sqlBuffer.append("ON RECEIVINGBILL.FID = FDCRECEIVEBILLENTRY.FReceivingBillID"+"\n");
		sqlBuffer.append("LEFT OUTER JOIN T_SHE_Room AS ROOM"+"\n");
		sqlBuffer.append("ON FDCRECEIVEBILL.FRoomID = ROOM.FID"+"\n");
		
		sqlBuffer.append("LEFT OUTER JOIN T_SHE_MoneyDefine AS MONEYDEFINE"+"\n");
		sqlBuffer.append("ON FDCRECEIVEBILLENTRY.FMoneyDefineID = MONEYDEFINE.FID"+"\n");
		
		sqlBuffer.append("LEFT OUTER JOIN T_SHE_FDCCustomer AS customer"+"\n");
		sqlBuffer.append("ON RECEIVINGBILL.FPayerID = customer.FSysCustomerID"+"\n");
		
		sqlBuffer.append("where"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FID in"+recIDS+"\n");
		sqlBuffer.append("group by "+"\n");
		sqlBuffer.append("RECEIVINGBILL.FID,"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FBillStatus,"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FNumber,"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FBizDate,"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FCreateTime,"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FCreatorid,"+"\n");
		sqlBuffer.append("customer.FID,"+"\n");
		sqlBuffer.append("ROOM.Fid ,"+"\n");
		sqlBuffer.append("MONEYDEFINE.FID,"+"\n");
		sqlBuffer.append("FDCRECEIVEBILLENTRY.FPayListID,"+"\n");
		sqlBuffer.append("FDCRECEIVEBILL.FSellProjectID"+"\n");
		sqlBuffer.append(") ta left outer join "+"\n");
		sqlBuffer.append("("+"\n");
		sqlBuffer.append("select t.FReceivingBillID as id,t.FPayListID as listID,sum(t.FArAmout) FArAmout from"+"\n"); 
		sqlBuffer.append("("+"\n");
		sqlBuffer.append("select distinct t2.FReceivingBillID,t2.FPayListID,isnull(t1.FArAmout,0) FArAmout from T_PPM_PPMGeneralAR t1"+"\n");
		sqlBuffer.append("inner join T_SHE_FDCReceiveBillEntry t2 on t1.fid=t2.FPayListID"+"\n");
		sqlBuffer.append("where t2.FReceivingBillID in"+recIDS+"\n");
		sqlBuffer.append("union all"+"\n");
		sqlBuffer.append("select distinct t2.FReceivingBillID,t2.FPayListID,isnull(t1.FArAmout,0) FArAmout from T_PPM_PPMMeasureAR t1"+"\n");
		sqlBuffer.append("inner join T_SHE_FDCReceiveBillEntry t2 on t1.fid=t2.FPayListID"+"\n");
		sqlBuffer.append("where t2.FReceivingBillID in"+recIDS+"\n");
		sqlBuffer.append(") t group by t.FReceivingBillID,t.FPayListID"+"\n");
		sqlBuffer.append(") tb "+"\n");
		sqlBuffer.append("on ta.ID=tb.id and ta.FListID=tb.listid"+"\n");

		strSql=sqlBuffer.toString();
		return strSql;
		
	}

	

	/**
	 * ͨ��������Ŀȥ���Զ��������Ŀ������ һ�����ֵ� �Զ��������Ŀ
	 * @param fdcBill
	 * @return
	 * @throws BOSException 
	 */
	private FDCReceiveBillInfo setGeneralAsstActType(Context ctx, FDCReceiveBillInfo fdcBill) throws BOSException
	{
		if(fdcBill == null)
			return fdcBill;
		SellProjectInfo info = fdcBill.getSellProject();
		if(info != null && info.getName() != null && info.getName().trim().length() > 0)
		{
			String name = info.getName();
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name",name,CompareType.EQUALS));
			view.setFilter(filter);
			GeneralAsstActTypeCollection coll = GeneralAsstActTypeFactory.getLocalInstance(ctx).getGeneralAsstActTypeCollection(view);
			
			if(coll != null && coll.size() == 1)
			{
				GeneralAsstActTypeInfo type = coll.get(0);
				fdcBill.setAsstActType(type);
			}
		}
		
		return fdcBill;
	}

	/**
	 * �ύ�տ�����ز����տID
	 */
	protected String _submitByCasRev(Context ctx, IObjectValue casRev)
			throws BOSException, EASBizException
	{
		String receivingBillId = null;
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) casRev;
		if(receivingBillInfo.getExchangeRate()==null)
			receivingBillInfo.setExchangeRate(FDCHelper.ONE);
		if(receivingBillInfo.getLocalAmt()==null)
			receivingBillInfo.setLocalAmt(receivingBillInfo.getAmount());
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		fdcReceiveBillInfo = this.setGeneralAsstActType(ctx, fdcReceiveBillInfo);
		/*
		 * ��������ϵͳ�Ĳ�ͬ���տ�
		 */
		MoneySysTypeEnum moneySysTypeEnum = fdcReceiveBillInfo.getMoneySysType();
		if(MoneySysTypeEnum.SalehouseSys.equals(moneySysTypeEnum))
		{
			receivingBillId = this.receiveMoneyInSHE(ctx,casRev);
		}
		else if(MoneySysTypeEnum.TenancySys.equals(moneySysTypeEnum))
		{
			receivingBillId = this.receiveMoneyInTEN(ctx,casRev);
		}
		else if(MoneySysTypeEnum.ManageSys.equals(moneySysTypeEnum))
		{
			receivingBillId = this.receiveMoneyInWuYe(ctx,casRev);
		}
		else
		{
			logger.error("����˳���û�й���"+ moneySysTypeEnum.getAlias() +" ϵͳ���տ��Ӧ���ڿͻ���������У��...");
		    throw new BOSException("�����߼������Ѽ�¼��־��");
		}
		return receivingBillId;
	}
	/**
	 * ����ҵϵͳ�н��е��տ����
	 * @param ctx
	 * @param casRev
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private String receiveMoneyInWuYe(Context ctx, IObjectValue casRev) throws EASBizException,BOSException
	{

		String receivingBillId = null;
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) casRev;
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		
		FDCReceiveBillEntryCollection fdcRecBillEntryColl = receivingBillInfo.getFdcReceiveBillEntry();
		ReceiveBillTypeEnum recBillType = fdcReceiveBillInfo.getBillTypeEnum();
		
		RoomInfo room = fdcReceiveBillInfo.getRoom();
	
		// ����ʵ���տ��û�б���״̬��ֱ���ύ��
		if (receivingBillInfo.getBillStatus().equals(BillStatusEnum.SAVE))
		{
			this.submit(ctx, fdcReceiveBillInfo);
			return null;
		}
		ReceivingBillInfo oldReceivingBillInfo = null;
		//�ж���ϵͳ�������Ƿ��Ѿ����ڵ��������
		if (receivingBillInfo.getId() != null
				&& ReceivingBillFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(receivingBillInfo.getId())))
		{
			oldReceivingBillInfo = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillInfo(new ObjectUuidPK(receivingBillInfo.getId()));
			if (oldReceivingBillInfo.getBillStatus().equals(BillStatusEnum.SAVE))
			{
				oldReceivingBillInfo = null;
			}
		}
		//������˿� ���߽���
		if (ReceiveBillTypeEnum.refundment.equals(recBillType))
		{

//			һ����ʱ�� payListId �Ƚ�ֵ
			String tempComparison = "";
			
			for (int i = 0; i < fdcRecBillEntryColl.size(); i++)
			{
				FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(i);
				String payListId = entryInfo.getPayListId();
				//����һ���� BOSUuid
				if(!BOSUuid.isValidLength(payListId,true))
				{
					payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
				}
				
				MoneyDefineInfo moneyDefineInfo = entryInfo.getMoneyDefine();

				FilterInfo filter = new FilterInfo();
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
			
				filter.getFilterItems().add(new FilterItemInfo("id",payListId));

				PPMTemporaryCollection temporaryColl = PPMTemporaryFactory.getLocalInstance(ctx).getPPMTemporaryCollection(view);
				
				PPMMeasureARCollection measureColl = PPMMeasureARFactory.getLocalInstance(ctx).getPPMMeasureARCollection(view);
				
				PPMGeneralARCollection generalColl = PPMGeneralARFactory.getLocalInstance(ctx).getPPMGeneralARCollection(view);
				
				DepositBillCollection depositColl = DepositBillFactory.getLocalInstance(ctx).getDepositBillCollection(view);
				
				DepositWithdrawBillCollection withdrawColl = DepositWithdrawBillFactory.getLocalInstance(ctx).getDepositWithdrawBillCollection(view);

				if (temporaryColl.size() == 1)
				{
					PPMTemporaryInfo info = temporaryColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getPayedAmount() != null)
						actAmount = info.getPayedAmount();
					
					if(info.getArAmout() != null)
						appAmount = info.getArAmout();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("payedAmount");
					info.setPayedAmount(actAmount);
					
					PPMTemporaryFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				}
				else if(measureColl.size() == 1)
				{

					PPMMeasureARInfo info = measureColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getPayedAmount() != null)
						actAmount = info.getPayedAmount();
					
					if(info.getArAmout() != null)
						appAmount = info.getArAmout();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("payedAmount");
					info.setPayedAmount(actAmount);
					PPMMeasureARFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				}
				else if(generalColl.size() == 1)
				{

					PPMGeneralARInfo info = generalColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getPayedAmount() != null)
						actAmount = info.getPayedAmount();
					
					if(info.getArAmout() != null)
						appAmount = info.getArAmout();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("payedAmount");
					info.setPayedAmount(actAmount);
					PPMGeneralARFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				}
				else if(depositColl.size() == 1)
				{


					DepositBillInfo info = depositColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getActGatheringAmo() != null)
						actAmount = info.getActGatheringAmo();
					
					if(info.getDepositAmount() != null)
						appAmount = info.getDepositAmount();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("actGatheringAmo");
					info.setActGatheringAmo(actAmount);
					DepositBillFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				
				}
				else if(withdrawColl.size() == 1)
				{



					DepositWithdrawBillInfo info = withdrawColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getActRefundmentAm() != null)
						actAmount = info.getActRefundmentAm().abs();
					
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount.abs());
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount().abs() == null? FDCHelper.ZERO : entryInfo.getAmount().abs());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("actRefundmentAm");
					info.setActRefundmentAm(actAmount);
					DepositWithdrawBillFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				
				
				}
				
				//�滻�Ƚ�ֵ
				if(!tempComparison.equalsIgnoreCase(payListId))
				{
					tempComparison = payListId;
				}
			}
		
		}
		//�տ�
		else
		{
//			һ����ʱ�� payListId �Ƚ�ֵ
			String tempComparison = "";
			
			for (int i = 0; i < fdcRecBillEntryColl.size(); i++)
			{
				FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(i);
				String payListId = entryInfo.getPayListId();
				//����һ���� BOSUuid
				if(!BOSUuid.isValidLength(payListId,true))
				{
					payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
				}
				
				MoneyDefineInfo moneyDefineInfo = entryInfo.getMoneyDefine();

				FilterInfo filter = new FilterInfo();
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
			
				filter.getFilterItems().add(new FilterItemInfo("id",payListId));

				PPMTemporaryCollection temporaryColl = PPMTemporaryFactory.getLocalInstance(ctx).getPPMTemporaryCollection(view);
				
				PPMMeasureARCollection measureColl = PPMMeasureARFactory.getLocalInstance(ctx).getPPMMeasureARCollection(view);
				
				PPMGeneralARCollection generalColl = PPMGeneralARFactory.getLocalInstance(ctx).getPPMGeneralARCollection(view);
				
				DepositBillCollection depositColl = DepositBillFactory.getLocalInstance(ctx).getDepositBillCollection(view);
				
				DepositWithdrawBillCollection withdrawColl = DepositWithdrawBillFactory.getLocalInstance(ctx).getDepositWithdrawBillCollection(view);

				if (temporaryColl.size() == 1)
				{
					PPMTemporaryInfo info = temporaryColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getPayedAmount() != null)
						actAmount = info.getPayedAmount();
					
					if(info.getArAmout() != null)
						appAmount = info.getArAmout();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("payedAmount");
					info.setPayedAmount(actAmount);
					
					PPMTemporaryFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				}
				else if(measureColl.size() == 1)
				{

					PPMMeasureARInfo info = measureColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getPayedAmount() != null)
						actAmount = info.getPayedAmount();
					
					if(info.getArAmout() != null)
						appAmount = info.getArAmout();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("payedAmount");
					info.setPayedAmount(actAmount);
					PPMMeasureARFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				}
				else if(generalColl.size() == 1)
				{

					PPMGeneralARInfo info = generalColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getPayedAmount() != null)
						actAmount = info.getPayedAmount();
					
					if(info.getArAmout() != null)
						appAmount = info.getArAmout();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("payedAmount");
					info.setPayedAmount(actAmount);
					PPMGeneralARFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				}
				else if(depositColl.size() == 1)
				{


					DepositBillInfo info = depositColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getActGatheringAmo() != null)
						actAmount = info.getActGatheringAmo();
					
					if(info.getDepositAmount() != null)
						appAmount = info.getDepositAmount();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("actGatheringAmo");
					info.setActGatheringAmo(actAmount);
					DepositBillFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				
				}
				else if(withdrawColl.size() == 1)
				{



					DepositWithdrawBillInfo info = withdrawColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getActRefundmentAm() != null)
						actAmount = info.getActRefundmentAm();
					
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("actRefundmentAm");
					info.setActRefundmentAm(actAmount);
					DepositWithdrawBillFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				
				
				}
				
				//�滻�Ƚ�ֵ
				if(!tempComparison.equalsIgnoreCase(payListId))
				{
					tempComparison = payListId;
				}
			}
		}
		this.submit(ctx, fdcReceiveBillInfo);
		receivingBillId = ReceivingBillFactory.getLocalInstance(ctx).submit(receivingBillInfo).toString();
		return receivingBillId;
	}
	/**
	 * ������ϵͳ�н����տ�Ĳ���
	 * @param ctx
	 * @param casRev
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private String receiveMoneyInTEN(Context ctx, IObjectValue casRev) throws EASBizException,BOSException
	{
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) casRev;
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		TenancyBillInfo tenancyBillInfo = fdcReceiveBillInfo.getTenancyContract();
		
		FDCReceiveBillEntryCollection fdcRecBillEntryColl = receivingBillInfo.getFdcReceiveBillEntry();
		ReceiveBillTypeEnum recBillType = fdcReceiveBillInfo.getBillTypeEnum();
		
		RoomInfo room = fdcReceiveBillInfo.getRoom();
		
		// ����ʵ���տ��û�б���״̬��ֱ���ύ��
		if (receivingBillInfo.getBillStatus().equals(BillStatusEnum.SAVE))
		{
			this.submit(ctx, fdcReceiveBillInfo);
			return null;
		}
		ReceivingBillInfo oldReceivingBillInfo = null;
		//�ж���ϵͳ�������Ƿ��Ѿ����ڵ��������,��Ҫ�����жϸ��ύ�Ƿ����޸Ĳ���
		if (receivingBillInfo.getId() != null
				&& ReceivingBillFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(receivingBillInfo.getId())))
		{
			oldReceivingBillInfo = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillInfo(new ObjectUuidPK(receivingBillInfo.getId()));
			if (oldReceivingBillInfo.getBillStatus().equals(BillStatusEnum.SAVE))
			{
				oldReceivingBillInfo = null;
			}
		}
		//TODO �������� �������ݲ�ʵ�֡���ҵ�еĺ���տ�����﷽ʽ������Ҳ��Ҫ��ġ�
		if(ReceiveBillTypeEnum.settlement.equals(recBillType)){
			
		}else if (ReceiveBillTypeEnum.refundment.equals(recBillType))//����˿�
		{
			// һ����ʱ�� payListId �Ƚ�ֵ
			String tempComparison = "";

			for (int k = 0; k < fdcRecBillEntryColl.size(); k++)
			{
				FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(k);
				String payListId = entryInfo.getPayListId();
				// ����һ���� BOSUuid ,����nullȥ����
				if (!BOSUuid.isValidLength(payListId, true))
				{
					//����������û��Ԥ�տ���տ��¼һ������� payllistid������Ϊ�߼������������
					logger.error("�߼����󣬻��������ݡ��տ��¼û�и�����ϸID.casRevNum:" + receivingBillInfo.getNumber());
					throw new BOSException("ϵͳ�߼������Ѽ�¼��־��");
//					payListId = BOSUuid.create(new TenancyRoomPayListEntryInfo().getBOSType()).toString();
				}
				
				if(GatheringObjectEnum.room.equals(fdcReceiveBillInfo.getGatheringObject())){
					//����Ҫ�˿�����޺�ͬ�ϵ�����������ϸ
					FilterInfo tempFilter = new FilterInfo();
					EntityViewInfo tempView = new EntityViewInfo();
					tempView.getSelector().add("refundmentAmount");
					tempView.getSelector().add("canRefundmentAmount");
					tempView.getSelector().add("actAmount");
					tempView.getSelector().add("tenRoom.remainDepositAmount");
					tempView.getSelector().add("tenRoom.tenancy.remainDepositAmount");
					tempView.setFilter(tempFilter);

//					tempFilter.getFilterItems().add(new FilterItemInfo("tenRoom.room", room.getId().toString()));
					tempFilter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy", tenancyBillInfo.getId().toString()));
					tempFilter.getFilterItems().add(new FilterItemInfo("id", payListId));

					TenancyRoomPayListEntryCollection payListColl = TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryCollection(tempView);
					if (payListColl.size() != 1)
					{
						logger.error("��ͬ " + tenancyBillInfo.getId() + "�Ҳ�����Ӧ��IDΪ " + payListId + " �ĸ�����ϸ��");
						throw new BOSException("ϵͳ�߼������Ѽ�¼��־��");
					} else
					{
						TenancyRoomPayListEntryInfo payListEntryInfo = payListColl.get(0);
						
						BigDecimal refundmentAmount = payListEntryInfo.getLimitAmount();	//TODO  Temp modify by Jeegan***************
						BigDecimal canRefundmentAmount = payListEntryInfo.getCanRefundmentAmount();
						BigDecimal actAmount = payListEntryInfo.getActAmount();
						
						if(refundmentAmount == null){
							refundmentAmount = FDCHelper.ZERO;
						}
						if(canRefundmentAmount == null){
							canRefundmentAmount = FDCHelper.ZERO;
						}
						
						if(actAmount == null){
							actAmount = FDCHelper.ZERO;
						}
						
						//��ʾ����˿�������
						BigDecimal chgAmount = entryInfo.getAmount();
						
						//������޸�
						if(oldReceivingBillInfo != null)
						{
							BigDecimal oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
							BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
					
							//������ͬ�ĸ�����ϸ������ֻҪ��һ�μ��㼴��
							if(!tempComparison.equalsIgnoreCase(payListId))
							{
								chgAmount = newAmount.subtract(oldAmount);
								refundmentAmount = refundmentAmount.add(newAmount).subtract(oldAmount);
								canRefundmentAmount = canRefundmentAmount.abs().subtract(newAmount.abs()).add(oldAmount.abs());
								actAmount = actAmount.add(oldAmount.abs()).subtract(newAmount.abs());
							}
						}
						else
						{
							BigDecimal newAmount = entryInfo.getAmount();
							//�տɾ��ʱ��������ܻ�Ϊ0
							if(newAmount == null){
								newAmount = FDCHelper.ZERO;
							}
							refundmentAmount = refundmentAmount.add(newAmount);
							canRefundmentAmount = canRefundmentAmount.abs().subtract(newAmount.abs());
							actAmount = actAmount.subtract(newAmount.abs());
						}
						
						//���¸�����ϸ���˿���.���˽���ʵ�ս��
						payListEntryInfo.setRemissionAmount(refundmentAmount);	//TODO  Temp modify by Jeegan***************
						payListEntryInfo.setLimitAmount(canRefundmentAmount);	//TODO  Temp modify by Jeegan***************
						payListEntryInfo.setActRevAmount(actAmount);		//TODO  Temp modify by Jeegan***************
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("refundmentAmount");
						sels.add("canRefundmentAmount");
						sels.add("actAmount");
//						TenancyRoomPayListEntryFactory.getLocalInstance(ctx).updatePartial(payListEntryInfo, sels);
						
						//���·���ʣ��Ѻ��ͺ�ͬʣ��Ѻ��
						
						TenancyRoomEntryInfo tenRoom = payListEntryInfo.getTenRoom();
						TenancyBillInfo tenancy = tenRoom.getTenancy();
						
						BigDecimal residualDepositInRoom = FDCHelper.ZERO;
						BigDecimal residualDepositInTenancy = FDCHelper.ZERO;
						if (tenRoom.getRemainDepositAmount() != null) {
							residualDepositInRoom = tenRoom.getRemainDepositAmount();
						}
						if (tenancy.getRemainDepositAmount() != null) {
							residualDepositInTenancy = tenancy.getRemainDepositAmount();
						}
						
						if(oldReceivingBillInfo != null){
							//�����޸�ʱchgAmount���������иø�����ϸ��Ӧ���˿��¼.��ֻ�ڵ�һ�θ���
							if(!tempComparison.equalsIgnoreCase(payListId)){
								tenRoom.setRemainDepositAmount(residualDepositInRoom.subtract(chgAmount));
								tenancy.setRemainDepositAmount(residualDepositInTenancy.subtract(chgAmount));
							}
						}else{
							tenRoom.setRemainDepositAmount(residualDepositInRoom.subtract(chgAmount));
							tenancy.setRemainDepositAmount(residualDepositInTenancy.subtract(chgAmount));
						}
						
						SelectorItemCollection selColl = new SelectorItemCollection();
						selColl.add("remainDepositAmount");
						TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenRoom, selColl);
						
						SelectorItemCollection billSelColl = new SelectorItemCollection();
						billSelColl.add("remainDepositAmount");
						TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancy, billSelColl);
					}
					//				�滻�Ƚ�ֵ
					if (!tempComparison.equalsIgnoreCase(payListId))
					{
						tempComparison = payListId;
					}
				}else if(GatheringObjectEnum.accessorialResource.equals(fdcReceiveBillInfo.getGatheringObject())){
					//����Ҫ�˿�����޺�ͬ�ϵ�����������ϸ
					FilterInfo tempFilter = new FilterInfo();
					EntityViewInfo tempView = new EntityViewInfo();
					tempView.getSelector().add("refundmentAmount");
					tempView.getSelector().add("canRefundmentAmount");
					tempView.getSelector().add("actAmount");
					tempView.getSelector().add("tenAttachRes.tenancyBill.remainDepositAmount");
					tempView.setFilter(tempFilter);
					
					tempFilter.getFilterItems().add(new FilterItemInfo("id", payListId));
					
					TenAttachResourcePayListEntryCollection payListColl = TenAttachResourcePayListEntryFactory.getLocalInstance(ctx).getTenAttachResourcePayListEntryCollection(tempView);
					if (payListColl.size() != 1)
					{
						logger.error("��ͬ " + tenancyBillInfo.getId() + "�Ҳ�����Ӧ��IDΪ " + payListId + " �ĸ�����ϸ��");
						throw new BOSException("ϵͳ�߼������Ѽ�¼��־��");
					} else
					{
						TenAttachResourcePayListEntryInfo payListEntryInfo = payListColl.get(0);
						
						BigDecimal refundmentAmount = payListEntryInfo.getRefundmentAmount();
						BigDecimal canRefundmentAmount = payListEntryInfo.getCanRefundmentAmount();
						BigDecimal actAmount = payListEntryInfo.getActAmount();
						
						if(refundmentAmount == null){
							refundmentAmount = FDCHelper.ZERO;
						}
						if(canRefundmentAmount == null){
							canRefundmentAmount = FDCHelper.ZERO;
						}
						
						if(actAmount == null){
							actAmount = FDCHelper.ZERO;
						}
						
						//��ʾ����˿�������
						BigDecimal chgAmount = entryInfo.getAmount();
						
						//������޸�
						if(oldReceivingBillInfo != null)
						{
							BigDecimal oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
							BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
					
							//������ͬ�ĸ�����ϸ������ֻҪ��һ�μ��㼴��
							if(!tempComparison.equalsIgnoreCase(payListId))
							{
								chgAmount = newAmount.subtract(oldAmount);
								refundmentAmount = refundmentAmount.add(newAmount).subtract(oldAmount);
								canRefundmentAmount = canRefundmentAmount.abs().subtract(newAmount.abs()).add(oldAmount.abs());
								actAmount = actAmount.add(oldAmount.abs()).subtract(newAmount.abs());
							}
						}
						else
						{
							BigDecimal newAmount = entryInfo.getAmount();
							//�տɾ��ʱ��������ܻ�Ϊ0
							if(newAmount == null){
								newAmount = FDCHelper.ZERO;
							}
							refundmentAmount = refundmentAmount.add(newAmount);
							canRefundmentAmount = canRefundmentAmount.abs().subtract(newAmount.abs());
							actAmount = actAmount.subtract(newAmount.abs());
						}
						
						//���¸�����ϸ���˿���.���˽���ʵ�ս��
						payListEntryInfo.setRefundmentAmount(refundmentAmount);
						payListEntryInfo.setCanRefundmentAmount(canRefundmentAmount);
						payListEntryInfo.setActAmount(actAmount);
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("refundmentAmount");
						sels.add("canRefundmentAmount");
						sels.add("actAmount");
						TenAttachResourcePayListEntryFactory.getLocalInstance(ctx).updatePartial(payListEntryInfo, sels);
						
						//���º�ͬʣ��Ѻ��
						TenAttachResourceEntryInfo tenAttach = payListEntryInfo.getTenAttachRes();
						TenancyBillInfo tenancy = tenAttach.getTenancyBill();
						
						BigDecimal residualDepositInTenancy = FDCHelper.ZERO;

						if (tenancy.getRemainDepositAmount() != null) {
							residualDepositInTenancy = tenancy.getRemainDepositAmount();
						}
						
						if(oldReceivingBillInfo != null){
							//�����޸�ʱchgAmount���������иø�����ϸ��Ӧ���˿��¼.��ֻ�ڵ�һ�θ���
							if(!tempComparison.equalsIgnoreCase(payListId)){
								tenancy.setRemainDepositAmount(residualDepositInTenancy.subtract(chgAmount));
							}
						}else{
							tenancy.setRemainDepositAmount(residualDepositInTenancy.subtract(chgAmount));
						}
						
						SelectorItemCollection billSelColl = new SelectorItemCollection();
						billSelColl.add("remainDepositAmount");
						TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancy, billSelColl);
					}
					//				�滻�Ƚ�ֵ
					if (!tempComparison.equalsIgnoreCase(payListId))
					{
						tempComparison = payListId;
					}
				}
				
				
			}
		}
		//�տ�3
		else
		{
//			һ����ʱ�� payListId �Ƚ�ֵ
			String tempComparison = "";
			for (int i = 0; i < fdcRecBillEntryColl.size(); i++)
			{
				FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(i);
				String payListId = entryInfo.getPayListId();
				//����һ���� BOSUuid
				if(!BOSUuid.isValidLength(payListId,true))
				{
					//��������û��Ԥ�տ���payListIdΪ�գ��϶�������
					logger.error("�����տ��¼û�ж�Ӧ������ϸID�� "+ payListId);
					throw new BOSException("ϵͳ�߼������Ѽ�¼��־��");
//					payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
				}
				
				if(GatheringObjectEnum.room.equals(fdcReceiveBillInfo.getGatheringObject())){
					FilterInfo filter = new FilterInfo();
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("actAmount");
					view.getSelector().add("actPayDate");
					
					view.setFilter(filter);
					filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy", tenancyBillInfo.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("id",payListId));

					TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl = TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryCollection(view);
					if (tenancyRoomPayListEntryColl.size() != 1)
					{
						logger.error("��ͬ "+tenancyBillInfo.getId()+"�Ҳ�����Ӧ��IDΪ "+ payListId+" �ĸ�����ϸ��");
						throw new BOSException("ϵͳ�߼������Ѽ�¼��־��");
					} else
					{
						TenancyRoomPayListEntryInfo tenancyRoomPayListEntryInfo = tenancyRoomPayListEntryColl.get(0);
						BigDecimal actAmount = FDCHelper.ZERO;
						
						// ʣ��Ѻ��
						if (tenancyRoomPayListEntryInfo.getActAmount() != null)
						{
							actAmount = tenancyRoomPayListEntryInfo.getActAmount();
						}
						
						if (oldReceivingBillInfo != null)
						{
							BigDecimal oldAmount = this.getOldAmount(ctx,receivingBillInfo.getId().toString(),payListId);
							BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
							
							//������ͬ�ĸ�����ϸ������ֻҪ��һ�μ��㼴��
							if(!tempComparison.equalsIgnoreCase(payListId) )
							{
								actAmount = actAmount.add(newAmount.subtract(oldAmount));
							}
						} else
						{
							actAmount = actAmount.add(entryInfo.getAmount());
						}
						
						//��д������ϸ�ϵ�ʵ�ս���ʵ������
						tenancyRoomPayListEntryInfo.setActRevAmount(actAmount);	 //TODO  Temp modify by Jeegan***************
						tenancyRoomPayListEntryInfo.setActRevDate(receivingBillInfo.getBizDate());	//TODO  Temp modify by Jeegan***************
						if(actAmount == null || actAmount.compareTo(FDCHelper.ZERO) == 0){
							tenancyRoomPayListEntryInfo.setActRevDate(null);		//TODO  Temp modify by Jeegan***************
						}
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("actAmount");
						sels.add("actPayDate");
//						TenancyRoomPayListEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomPayListEntryInfo, sels);
						
						/*
						// �������Ѻ��Ļ�����д���޺�ͬ�ͷ����¼�ϵ�ʣ��Ѻ��
						if (MoneyTypeEnum.DepositAmount.equals(moneyDefineInfo.getMoneyType())) {
							tenancyRoomEntryInfo.setRemainDepositAmount(residualDespoitInRoomEntry);
							SelectorItemCollection selColl = new SelectorItemCollection();
							selColl.add("remainDepositAmount");
							TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo, selColl);

							tenancy.setRemainDepositAmount(residualDespoitInTenancyBill);
							SelectorItemCollection billSelColl = new SelectorItemCollection();
							billSelColl.add("remainDepositAmount");
							TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancy, billSelColl);
						}
						*/
					}
					
					//�滻�Ƚ�ֵ
					if(!tempComparison.equalsIgnoreCase(payListId))
					{
						tempComparison = payListId;
					}
				}else if(GatheringObjectEnum.accessorialResource.equals(fdcReceiveBillInfo.getGatheringObject())){
					FilterInfo filter = new FilterInfo();
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("actAmount");
					view.getSelector().add("actPayDate");
					
					view.setFilter(filter);
//					filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy", tenancyBillInfo.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("id",payListId));

					TenAttachResourcePayListEntryCollection tenancyAttachPayListEntryColl = TenAttachResourcePayListEntryFactory.getLocalInstance(ctx).getTenAttachResourcePayListEntryCollection(view);
					if (tenancyAttachPayListEntryColl.size() != 1)
					{
						logger.error("��ͬ "+tenancyBillInfo.getId()+"�Ҳ�����Ӧ��IDΪ "+ payListId+" �ĸ�����ϸ��");
						throw new BOSException("ϵͳ�߼������Ѽ�¼��־��");
					} else
					{
						TenAttachResourcePayListEntryInfo tenancyAttachPayListEntryInfo = tenancyAttachPayListEntryColl.get(0);
						BigDecimal actAmount = FDCHelper.ZERO;
						// ʣ��Ѻ��
						if (tenancyAttachPayListEntryInfo.getActAmount() != null)
						{
							actAmount = tenancyAttachPayListEntryInfo.getActAmount();
						}
						
						if (oldReceivingBillInfo != null)
						{
							BigDecimal oldAmount = this.getOldAmount(ctx,receivingBillInfo.getId().toString(),payListId);
							BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
							
							//������ͬ�ĸ�����ϸ������ֻҪ��һ�μ��㼴��
							if(!tempComparison.equalsIgnoreCase(payListId) )
							{
								actAmount = actAmount.add(newAmount.subtract(oldAmount));
							}
						} else
						{
							actAmount = actAmount.add(entryInfo.getAmount());
						}
						
						//��д������ϸ�ϵ�ʵ�ս���ʵ������
						tenancyAttachPayListEntryInfo.setActAmount(actAmount);
						tenancyAttachPayListEntryInfo.setActPayDate(receivingBillInfo.getBizDate());
						if(actAmount == null || actAmount.compareTo(FDCHelper.ZERO) == 0){
							tenancyAttachPayListEntryInfo.setActPayDate(null);
						}
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("actAmount");
						sels.add("actPayDate");
						TenAttachResourcePayListEntryFactory.getLocalInstance(ctx).updatePartial(tenancyAttachPayListEntryInfo, sels);
					}
					
					//�滻�Ƚ�ֵ
					if(!tempComparison.equalsIgnoreCase(payListId))
					{
						tempComparison = payListId;
					}
				}
			}
		}
		this.submit(ctx, fdcReceiveBillInfo);
		String receivingBillId = ReceivingBillFactory.getLocalInstance(ctx).submit(receivingBillInfo).toString();
		return receivingBillId;
	}
	
	
	
	/**
	 * �ж��Ƿ��Ǹ������
	 * @param ctx
	 * @param seq
	 * @param tenancyBillInfo
	 * @param room
	 * @return
	 * @throws BOSException
	 * @author laiquan_luo
	 */
	private boolean isFirstAmountForTEN(Context ctx,int seq,TenancyBillInfo tenancyBillInfo,RoomInfo room) throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		SorterItemCollection sorterItemColl = new SorterItemCollection();
		sorterItemColl.add(new SorterItemInfo("seq"));
		
		view.setSorter(sorterItemColl);
		
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.room",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy",tenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType",MoneyTypeEnum.RentAmount));
		
		TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl = TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryCollection(view);
	    
		if(tenancyRoomPayListEntryColl.get(0) != null)
		{
			TenancyRoomPayListEntryInfo tenancyRoomPayListEntryInfo = tenancyRoomPayListEntryColl.get(0);
			if(seq != tenancyRoomPayListEntryInfo.getSeq())
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			logger.error("û���ҵ���ͬ"+tenancyBillInfo.toString()+" ���� "+room.toString() +" �������ϸ����");
			return false;
		}
	}
	
	/**
	 * �ж��Ƿ��Ǹ������
	 * @param ctx
	 * @param payListId
	 * @param tenancyBillInfo
	 * @param room
	 * @return
	 * @throws BOSException
	 */
	private boolean isFirstAmountForTEN(Context ctx,String payListId,TenancyBillInfo tenancyBillInfo,RoomInfo room) throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		SorterItemCollection sorterItemColl = new SorterItemCollection();
		sorterItemColl.add(new SorterItemInfo("seq"));
		
		view.setSorter(sorterItemColl);
		
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.room",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy",tenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType",MoneyTypeEnum.RentAmount));
		
		TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl = TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryCollection(view);
	    
		if(tenancyRoomPayListEntryColl.get(0) != null)
		{
			TenancyRoomPayListEntryInfo tenancyRoomPayListEntryInfo = tenancyRoomPayListEntryColl.get(0);
			if(payListId.equalsIgnoreCase(tenancyRoomPayListEntryInfo.getId().toString()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			logger.error("û���ҵ���ͬ"+tenancyBillInfo.toString()+" ���� "+room.toString() +" �������ϸ����");
			return false;
		}
	}
	
	
	/**
	 * ���ĺ�ͬ��״̬
	 * @param ctx
	 * @param tenancyBillInfo
	 * @param tenancyBillStateEnum
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void changeTenancyBillState(Context ctx, TenancyBillInfo tenancyBillInfo,TenancyBillStateEnum tenancyBillStateEnum) throws EASBizException, BOSException
	{
		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("tenancyState");
		
		tenancyBillInfo.setTenancyState(tenancyBillStateEnum);
		
		TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancyBillInfo,selColl);
	}
	
	private Set getFdcBillSetForCustomer(Context ctx, CustomerInfo customer) throws BOSException
	{
		Set fdcBillIdSet = new HashSet();
		
		if(customer == null)
			return fdcBillIdSet;
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("head");
		view.getSelector().add("head.id");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("customer",customer.getId().toString()));
		
		CustomerEntryCollection		customerColl = CustomerEntryFactory.getLocalInstance(ctx).getCustomerEntryCollection(view);
		
		for(int i = 0; i < customerColl.size(); i ++)
		{
			if(customerColl.get(i) != null && customerColl.get(i).getHead() != null && customerColl.get(i).getHead().getId() != null)
			{
				fdcBillIdSet.add(customerColl.get(i).getHead().getId().toString());
			}
		}
		
		return fdcBillIdSet;
	}
	
	
	/**
	 * ��¥ϵͳ�н����տ�Ĳ���
	 * @param ctx
	 * @param casRev
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private String receiveMoneyInSHE(Context ctx, IObjectValue casRev) throws EASBizException, BOSException
	{
		String receivingBillId = null;
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) casRev;
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		PurchaseInfo purchase = fdcReceiveBillInfo.getPurchase();
		FDCReceiveBillEntryCollection fdcRecBillEntryColl = receivingBillInfo.getFdcReceiveBillEntry();
		ReceiveBillTypeEnum recBillType = fdcReceiveBillInfo.getBillTypeEnum();
		
		// ����ʵ���տ��û�б���״̬��ֱ���ύ��
		if (receivingBillInfo.getBillStatus().equals(BillStatusEnum.SAVE))
		{
			this.submit(ctx, fdcReceiveBillInfo);
			return null;
		}
		ReceivingBillInfo oldReceivingBillInfo = null;
		
		FDCReceiveBillEntryCollection oldFdcRecEnryColl = null;
		//�ж���ϵͳ�������Ƿ��Ѿ����ڵ��������
		if (receivingBillInfo.getId() != null
				&& ReceivingBillFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(receivingBillInfo.getId())))
		{
			SelectorItemCollection selColl = new SelectorItemCollection();
			selColl.add("*");
			selColl.add("fdcReceiveBillEntry.*");
			
			oldReceivingBillInfo = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillInfo(new ObjectUuidPK(receivingBillInfo.getId()),selColl);
			oldFdcRecEnryColl = oldReceivingBillInfo.getFdcReceiveBillEntry();
			
			if (oldReceivingBillInfo.getBillStatus().equals(BillStatusEnum.SAVE))
			{
				oldReceivingBillInfo = null;
			}
		}
		PurchaseInfo purchaseInfo = fdcReceiveBillInfo.getPurchase();
	
		GatheringEnum gathering = fdcReceiveBillInfo.getGathering();
		//����ǳ����Ϲ��տ�
		if(GatheringEnum.SinPurchase.equals(gathering)){
			//Ŀǰ��ʵ�֣���Ҫ��ʵ��ʵ�����ķ�д
			//��壬�˿���Ȳ��� TODO
			if(ReceiveBillTypeEnum.settlement.equals(recBillType)){
				throw new BOSException("�ݲ�֧�ֵĲ���.");
			} else if (ReceiveBillTypeEnum.refundment.equals(recBillType)) {
				String tempComparison = "";
				for (int i = 0; i < fdcRecBillEntryColl.size(); i++) {
					FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(i);

					String payListId = entryInfo.getPayListId();
					// ����һ���� BOSUuid
					if (!BOSUuid.isValidLength(payListId, true)) {
						payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
					}
					FilterInfo filter = new FilterInfo();
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("*");
					view.getSelector().add("moneyDefine.*");
					view.setFilter(filter);

					filter.getFilterItems().add(new FilterItemInfo("id", payListId));
					SincerReceiveEntryCollection sinRevEntrys = SincerReceiveEntryFactory.getLocalInstance(ctx).getSincerReceiveEntryCollection(view);
					if (sinRevEntrys.isEmpty()) {
						throw new BOSException("�˿��Ҳ��������Ϲ���Ӧ�ĸ�����ϸ��");
					}

					SincerReceiveEntryInfo sinRevEntry = sinRevEntrys.get(0);

					BigDecimal refundmentAmount = FDCHelper.ZERO;
					BigDecimal canRefundmentAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;

					if (sinRevEntry.getRefundmentAmount() != null) {
						refundmentAmount = sinRevEntry.getRefundmentAmount();
					}
					if (sinRevEntry.getCanRefundmentAmount() != null) {
						canRefundmentAmount = sinRevEntry.getCanRefundmentAmount();
					}
					if (sinRevEntry.getActPayAmount() != null) {
						actAmount = sinRevEntry.getActPayAmount();
					}

					BigDecimal balancedCanRefundmentAmount = FDCHelper.ZERO;
					
					// ������޸�
					if (oldReceivingBillInfo != null) {
						BigDecimal oldAmount = this.getOldAmount(ctx, oldReceivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);

						// ������ͬ�ĸ�����ϸ������ֻҪ��һ�μ��㼴��
						if (!tempComparison.equalsIgnoreCase(payListId)) {
							refundmentAmount = refundmentAmount.add(newAmount.abs()).subtract(oldAmount.abs());
							canRefundmentAmount = canRefundmentAmount.subtract(newAmount.abs()).add(oldAmount.abs());
							actAmount = actAmount.add(oldAmount.abs()).subtract(newAmount.abs());
							
							balancedCanRefundmentAmount = newAmount.abs().subtract(oldAmount.abs());
						}
					} else {
						BigDecimal newAmount = entryInfo.getAmount();
						if (newAmount == null)
							newAmount = FDCHelper.ZERO;

						refundmentAmount = refundmentAmount.abs().add(newAmount.abs());
						canRefundmentAmount = canRefundmentAmount.subtract(newAmount.abs());
						actAmount = actAmount.subtract(newAmount.abs());
						
						balancedCanRefundmentAmount = newAmount.abs();
					}
					
//					sinRevEntry.setHasRefundmentAmount(refundmentAmount.abs());
//					sinRevEntry.setLimitAmount(canRefundmentAmount);
					sinRevEntry.setActRevAmount(actAmount);

					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("refundmentAmount");
					sels.add("canRefundmentAmount");
					sels.add("actPayAmount");
					SincerReceiveEntryFactory.getLocalInstance(ctx).updatePartial(sinRevEntry, sels);
					
					//���ڳ������˿ɳ��������,��Ҫ����Ӧ���տ�Ŀ��˽������޸�
					if(balancedCanRefundmentAmount.compareTo(FDCHelper.ZERO) != 0){
						FDCReceiveBillEntryCollection fdcRevEntrys = getFdcRecEntryInfo(ctx, payListId);
						for(int m=0; m<fdcRevEntrys.size(); m++){
							FDCReceiveBillEntryInfo fdcRevEntry = fdcRevEntrys.get(m);
							BigDecimal amount = FDCHelper.toBigDecimal(fdcRevEntry.getAmount());
							BigDecimal canRefundmentAmountOfRevEntry = FDCHelper.toBigDecimal(fdcRevEntry.getCanCounteractAmount());
							BigDecimal counteractAmount = FDCHelper.toBigDecimal(fdcRevEntry.getCounteractAmount());//�����Ľ��
							
							BigDecimal maxCanRefunmentAmount = amount.subtract(counteractAmount);
//							BigDecimal minCanRefunmentAmount = counteractAmount;
							
							if(balancedCanRefundmentAmount.compareTo(FDCHelper.ZERO) > 0){
								if(canRefundmentAmountOfRevEntry.compareTo(balancedCanRefundmentAmount) >= 0){
									fdcRevEntry.setCanCounteractAmount(canRefundmentAmountOfRevEntry.subtract(balancedCanRefundmentAmount));
									
									SelectorItemCollection upFdcEntrySels = new SelectorItemCollection();
									upFdcEntrySels.add("canCounteractAmount");
									FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(fdcRevEntry, upFdcEntrySels);
									break;
								}else{
									balancedCanRefundmentAmount = balancedCanRefundmentAmount.subtract(canRefundmentAmountOfRevEntry);
									
									fdcRevEntry.setCanCounteractAmount(FDCHelper.ZERO);
									SelectorItemCollection upFdcEntrySels = new SelectorItemCollection();
									upFdcEntrySels.add("canCounteractAmount");
									FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(fdcRevEntry, upFdcEntrySels);
									continue;
								}
							}else{//������޸��˿���,ʹ�˿����С��.balancedCanRefundmentAmountΪ����
								if(maxCanRefunmentAmount.subtract(canRefundmentAmountOfRevEntry).compareTo(balancedCanRefundmentAmount.abs()) >= 0){
									fdcRevEntry.setCanCounteractAmount(canRefundmentAmountOfRevEntry.add(balancedCanRefundmentAmount.abs()));
									
									SelectorItemCollection upFdcEntrySels = new SelectorItemCollection();
									upFdcEntrySels.add("canCounteractAmount");
									FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(fdcRevEntry, upFdcEntrySels);
									break;
								}else{
									balancedCanRefundmentAmount = balancedCanRefundmentAmount.add(maxCanRefunmentAmount.subtract(canRefundmentAmountOfRevEntry));
									
									fdcRevEntry.setCanCounteractAmount(maxCanRefunmentAmount);
									SelectorItemCollection upFdcEntrySels = new SelectorItemCollection();
									upFdcEntrySels.add("canCounteractAmount");
									FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(fdcRevEntry, upFdcEntrySels);
									continue;
								}
							}
						}	
					}
					
					//�滻�Ƚ�ֵ
					if(!tempComparison.equalsIgnoreCase(payListId)){
						tempComparison = payListId;
					}
				}
			}else{
				//��������޸Ĳ���
				if(oldReceivingBillInfo == null){
					for(int i=0; i<fdcRecBillEntryColl.size(); i++){
						FDCReceiveBillEntryInfo fdcRecEntry = fdcRecBillEntryColl.get(i);
						String payId = fdcRecEntry.getPayListId();
						if(payId == null){
							throw new BOSException("�Ҳ�����Ӧ�ĸ�����ϸ");
						}
						
						SelectorItemCollection paySels = new SelectorItemCollection();
						SincerReceiveEntryInfo sinEntry = SincerReceiveEntryFactory.getLocalInstance(ctx).getSincerReceiveEntryInfo(new ObjectUuidPK(payId));
						
						BigDecimal srcActAmount = sinEntry.getActRevAmount();
						if(srcActAmount == null){
							srcActAmount = FDCHelper.ZERO;
						}
						BigDecimal thisAmount = fdcRecEntry.getAmount();
						if(thisAmount == null){
							thisAmount = FDCHelper.ZERO;
						}
						
						BigDecimal canRefundmentAmount = sinEntry.getCanRefundmentAmount();
						if(canRefundmentAmount == null){
							canRefundmentAmount = FDCHelper.ZERO;
						}
						
						sinEntry.setActRevAmount(srcActAmount.add(thisAmount));
//						sinEntry.setLimitAmount(canRefundmentAmount.add(thisAmount));
						
						SelectorItemCollection payUpdateSels = new SelectorItemCollection();
						payUpdateSels.add("actAmount");
						payUpdateSels.add("canRefundmentAmount");
						SincerReceiveEntryFactory.getLocalInstance(ctx).updatePartial(sinEntry, payUpdateSels);											
					}
				}else{
					//����paylistID��װ���տ��payListId���տ���
					Map actAmountByPayId = new HashMap();
					for(int i=0; i<fdcRecBillEntryColl.size(); i++){
						FDCReceiveBillEntryInfo fdcRecEntry = fdcRecBillEntryColl.get(i);
						String payId = fdcRecEntry.getPayListId();
						if(payId == null){
							throw new BOSException("�Ҳ�����Ӧ�ĸ�����ϸ");
						}
						
						BigDecimal amount = fdcRecEntry.getAmount();
						if(amount == null) amount = FDCHelper.ZERO;
						
						if(actAmountByPayId.get(payId) == null){
							actAmountByPayId.put(payId, amount);	
						}else{
							BigDecimal src = (BigDecimal) actAmountByPayId.get(payId);
							actAmountByPayId.put(payId, src.add(amount));
						}
					}
					
					//����paylistID��װԭ�տ��payListId���տ���
					Map oldActAmountByPayId = new HashMap();
					for(int i=0; i<oldFdcRecEnryColl.size(); i++){
						FDCReceiveBillEntryInfo fdcRecEntry = oldFdcRecEnryColl.get(i);
						String payId = fdcRecEntry.getPayListId();
						if(payId == null){
							throw new BOSException("�Ҳ�����Ӧ�ĸ�����ϸ");
						}
						
						BigDecimal amount = fdcRecEntry.getAmount();
						if(amount == null) amount = FDCHelper.ZERO;
						
						if(oldActAmountByPayId.get(payId) == null){
							oldActAmountByPayId.put(payId, amount);	
						}else{
							BigDecimal src = (BigDecimal) oldActAmountByPayId.get(payId);
							oldActAmountByPayId.put(payId, src.add(amount));
						}
					}
					
					//�Ƚ��¾��տ�Ľ��������в�𣬽������µ�������ϸ��ʵ����
					for(Iterator itor = actAmountByPayId.keySet().iterator(); itor.hasNext(); ){
						String payId = (String) itor.next();
						BigDecimal amount = (BigDecimal) actAmountByPayId.get(payId);
						
						BigDecimal oldAmount = (BigDecimal) oldActAmountByPayId.get(payId);
						if(oldAmount == null){
							logger.error("�տ�޸Ĳ��ܸķ�¼��������");
							continue;
						}
						
						BigDecimal balance = amount.subtract(oldAmount);
						if(balance.compareTo(FDCHelper.ZERO) == 0){
							continue;
						}
						
						SincerReceiveEntryInfo sinEntry = SincerReceiveEntryFactory.getLocalInstance(ctx).getSincerReceiveEntryInfo(new ObjectUuidPK(payId));
						
						BigDecimal srcActAmount = sinEntry.getActRevAmount();
						if(srcActAmount == null){
							srcActAmount = FDCHelper.ZERO;
						}
						
						BigDecimal canRefundmentAmount = sinEntry.getCanRefundmentAmount();
						
						sinEntry.setActRevAmount(srcActAmount.add(balance));
//						sinEntry.setLimitAmount(canRefundmentAmount.add(balance));
						
						SelectorItemCollection payUpdateSels = new SelectorItemCollection();
						payUpdateSels.add("actAmount");
						payUpdateSels.add("canRefundmentAmount");
						SincerReceiveEntryFactory.getLocalInstance(ctx).updatePartial(sinEntry, payUpdateSels);
					}
				}
			}
		}else{
			if(ReceiveBillTypeEnum.settlement.equals(recBillType))
			{
				//���ں���տ��Ҫ�����ñ������տ��¼�Ŀɺ������Ѻ���� 
				//TODO ��ǰ�Ĵ������ڿͻ��˲����ģ���Ҫ �ᵽ�������˴�������
			}
			//������˿�
			else if (ReceiveBillTypeEnum.refundment.equals(recBillType))
			{
				//һ����ʱ�� payListId �Ƚ�ֵ
				String tempComparison = "";
				String tempMoneyId = "";
				
				for(int i = 0; i < fdcRecBillEntryColl.size(); i ++)
				{
					FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(i);
					
					MoneyDefineInfo money = entryInfo.getMoneyDefine();
					
					String payListId = entryInfo.getPayListId();
					//����һ���� BOSUuid
					if(!BOSUuid.isValidLength(payListId,true))
					{
						payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
					}
					FilterInfo filter = new FilterInfo();
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("*");
					view.getSelector().add("moneyDefine.*");
					view.setFilter(filter);
					
					filter.getFilterItems().add(new FilterItemInfo("head",purchaseInfo == null? null: purchaseInfo.getId().toString()));
					
					filter.getFilterItems().add(new FilterItemInfo("id",payListId));
					
					PurchasePayListEntryCollection purchasePayListEntryColl  = PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryCollection(view);
					
					if(purchasePayListEntryColl.size() > 1)
					{
						logger.error("���Ϲ��� "+ purchaseInfo.toString()+"�ҵ����� id "+ payListId+" �ģ�������ϸ��");
						throw new BOSException("ϵͳ���ݴ����Ѽ�¼��־��");
					}
					//�Ǽƻ��Ը�����޶��Կ���
					else if(purchasePayListEntryColl.size() < 1)
					{
						//�ж��Ƿ��� ����������ϸ
						FilterInfo elseFilter = new FilterInfo();
						EntityViewInfo elseView = new EntityViewInfo();
						
						elseView.setFilter(elseFilter);
						
						elseFilter.getFilterItems().add(new FilterItemInfo("head",purchaseInfo == null ? null : purchaseInfo.getId().toString()));
						elseFilter.getFilterItems().add(new FilterItemInfo("id",payListId));
						
						PurchaseElsePayListEntryCollection elsePayColl = PurchaseElsePayListEntryFactory.getLocalInstance(ctx).getPurchaseElsePayListEntryCollection(elseView);
						
						BigDecimal actAmount = FDCHelper.ZERO;
						
						BigDecimal refundmentAmount = FDCHelper.ZERO;
						
						//������˽��
						BigDecimal canRefundmentAmount = FDCHelper.ZERO;
						
						
						PurchaseElsePayListEntryInfo elsePayInfo = null;
						if(elsePayColl.size() > 1)
						{
							logger.error("�Ϲ���Ϊ"+purchaseInfo.toString()+",�ģ�����Ӧ������ID:"+payListId+" �����ظ�������...");
							throw new BOSException("ϵͳ���ݴ����Ѽ�¼��־��");
						}
						else if(elsePayColl.size() == 1)
						{
							elsePayInfo = elsePayColl.get(0);

							if (elsePayInfo.getRefundmentAmount()!= null)
							{
								refundmentAmount = elsePayInfo.getRefundmentAmount();
							}
							if(elsePayInfo.getCanRefundmentAmount() != null)
							{
								canRefundmentAmount = elsePayInfo.getCanRefundmentAmount();
							}
							
							if(elsePayInfo.getActPayAmount() != null)
							{
								actAmount = elsePayInfo.getActPayAmount();
							}
							
							if (oldReceivingBillInfo != null)
							{
								BigDecimal oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
								BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
						
								//������ͬ�ĸ�����ϸ������ֻҪ��һ�μ��㼴��
								if(!tempComparison.equalsIgnoreCase(payListId) )
								{
									refundmentAmount = refundmentAmount.abs().add(newAmount.abs()).subtract(oldAmount.abs());
									
									canRefundmentAmount = canRefundmentAmount.abs().subtract(newAmount.abs()).add(oldAmount.abs());
									
									actAmount = actAmount.add(oldAmount.abs()).subtract(newAmount.abs());
								}
							}
							else
							{
								BigDecimal newAmount = entryInfo.getAmount();
								if(newAmount == null)
									newAmount = FDCHelper.ZERO;
								
								refundmentAmount = refundmentAmount.abs().add(newAmount.abs());
								
								canRefundmentAmount = canRefundmentAmount.abs().subtract(newAmount.abs());
								
								actAmount = actAmount.subtract(newAmount.abs());
							}
							
							
							elsePayInfo.setActRevAmount(actAmount);
						/*	if(FDCHelper.ZERO.compareTo(actAmount) == 0)
								elsePayInfo.setActPayDate(null);*/
								
							
							elsePayInfo.setLimitAmount(canRefundmentAmount.abs());
							elsePayInfo.setHasRefundmentAmount(refundmentAmount.abs());
							SelectorItemCollection sels = new SelectorItemCollection();
							sels.add("refundmentAmount");
							sels.add("canRefundmentAmount");
							sels.add("actPayAmount");
							//sels.add("actPayDate");
//							PurchaseElsePayListEntryFactory.getLocalInstance(ctx).updatePartial(elsePayInfo, sels);
						}//��Ԥ�տ�һ�������������˿�
						else
						{
							EntityViewInfo specialView = new EntityViewInfo();
							FilterInfo specialFilter = new FilterInfo();
							specialView.setFilter(specialFilter);
							
							specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
						
							//��Կͻ����տ����
							if(GatheringEnum.CustomerRev.equals(gathering))
							{
								CustomerInfo customer = null;
								
								if(fdcReceiveBillInfo.getCustomerEntrys() != null && fdcReceiveBillInfo.getCustomerEntrys().get(0) != null)
									customer = fdcReceiveBillInfo.getCustomerEntrys().get(0).getCustomer();
								
								
								Set fdcBillIdSet = this.getFdcBillSetForCustomer(ctx,customer);
								
								if(fdcBillIdSet != null && fdcBillIdSet.size() > 0)
								{
									specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id", fdcBillIdSet,CompareType.INCLUDE));
								}
								else
								{
									specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id",null));
								}
							}
							else
							{
								specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", purchase.getId().toString()));
							}
							
							
							
							specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
							specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.TRANSFER_VALUE));
							specialFilter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));
							
							if(GatheringEnum.CustomerRev.equals(gathering))
							{
								specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.gathering",GatheringEnum.CUSTOMERREV_VALUE));
	
								specialFilter.setMaskString("#0 and #1 and (#2 or #3) and #4 and #5");
							}
							else
							{				
								specialFilter.setMaskString("#0 and #1 and (#2 or #3) and #4");
							}
							
							FDCReceiveBillEntryCollection recBillEntryColl = FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryCollection(specialView);
							
							BigDecimal amount = entryInfo.getAmount();
							if(amount == null)
								amount = FDCHelper.ZERO;
							
							BigDecimal residualAmount = amount.abs();
							
							BigDecimal oldAmount = FDCHelper.ZERO;
							BigDecimal newAmount = FDCHelper.ZERO;
							String moneyDefineId = money.getId().toString();
							if (oldReceivingBillInfo != null)
							{
								FDCReceiveBillEntryCollection fdcEntrys = oldReceivingBillInfo.getFdcReceiveBillEntry();
								for(int m=0; m<fdcEntrys.size(); m++){
									FDCReceiveBillEntryInfo fdcEntry = fdcEntrys.get(m);
									if(moneyDefineId.equals(fdcEntry.getMoneyDefine().getId().toString())){
										oldAmount = oldAmount.add(fdcEntry.getAmount());
									}
								}
								
								for (int m = 0; m < fdcRecBillEntryColl.size(); m++) {
									FDCReceiveBillEntryInfo fdcEntry = fdcRecBillEntryColl.get(m);
									if(moneyDefineId.equals(fdcEntry.getMoneyDefine().getId().toString())){
										newAmount = newAmount.add(fdcEntry.getAmount());
									}
								}
								
//								oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
//								newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
								
								//����Ԥ�������͵ģ�����ͨ��payListId���ж���
								//������ͬ�ĸ�����ϸ������ֻҪ��һ�μ��㼴��
								if(!tempMoneyId.equalsIgnoreCase(moneyDefineId))
								{
									//����˿����С����residualAmountΪ����
									residualAmount = oldAmount.subtract(newAmount);
								}else{
									continue;
								}
							}
							
							for(int k = 0; k < recBillEntryColl.size(); k ++)
							{
								FDCReceiveBillEntryInfo info = recBillEntryColl.get(k);
								BigDecimal canCounteractAmount = info.getCanCounteractAmount();
								BigDecimal actPaymount = info.getAmount();
								if(actPaymount == null)
									actPaymount = FDCHelper.ZERO;
								
								if(canCounteractAmount == null)
									canCounteractAmount = FDCHelper.ZERO;
								
								if (residualAmount.compareTo(FDCHelper.ZERO) >= 0) {
									if (canCounteractAmount.compareTo(residualAmount) >= 0) {
										BigDecimal temp = canCounteractAmount.subtract(residualAmount);
										info.setCanCounteractAmount(temp);
//										info.setAmount(actPaymount.add(oldAmount).subtract(newAmount));
										SelectorItemCollection selColl = new SelectorItemCollection();
										selColl.add("canCounteractAmount");
//										selColl.add("amount");
										FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(info, selColl);

										break;
									} else {
										info.setCanCounteractAmount(FDCHelper.ZERO);

										SelectorItemCollection selColl = new SelectorItemCollection();
										selColl.add("canCounteractAmount");
//										selColl.add("amount");
										FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(info, selColl);

										residualAmount = residualAmount.subtract(canCounteractAmount);
									}
								}else{//�������������ʵ��Ҫ����Ԥ������Ŀ��˽��
									BigDecimal canAddedAmount = residualAmount.abs();
									
									if(actPaymount.subtract(canCounteractAmount).compareTo(canAddedAmount) >= 0){
										BigDecimal temp = canCounteractAmount.add(canAddedAmount);
										info.setCanCounteractAmount(temp);
										SelectorItemCollection selColl = new SelectorItemCollection();
										selColl.add("canCounteractAmount");
//										selColl.add("amount");
										FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(info, selColl);
										break;
									}else{
										BigDecimal temp = actPaymount.subtract(canCounteractAmount);
										if(temp.compareTo(FDCHelper.ZERO) == 0){
											continue;
										}
										info.setCanCounteractAmount(actPaymount);
										SelectorItemCollection selColl = new SelectorItemCollection();
										selColl.add("canCounteractAmount");
//										selColl.add("amount");
										FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(info, selColl);
										
										residualAmount = residualAmount.add(temp);
									}
								}
							}
							
							if(!tempMoneyId.equals(money.getId().toString())){
								tempMoneyId = money.getId().toString();
							}
							
						}
						//this.updateStateByisCompleteReceiveInSHE(ctx, receivingBillInfo, oldReceivingBillInfo, purchaseInfo, moneyDefineInfo,actAmount,apAmount);
					}//�ƻ��Կ���
					else
					{
						PurchasePayListEntryInfo purchasePayListEntryInfo = purchasePayListEntryColl.get(0);
					
						BigDecimal refundmentAmount = FDCHelper.ZERO;
						BigDecimal canRefundmentAmount = FDCHelper.ZERO;
						BigDecimal actAmount = FDCHelper.ZERO;
						
						if(purchasePayListEntryInfo.getRefundmentAmount() != null)
						{
							refundmentAmount = purchasePayListEntryInfo.getRefundmentAmount();
						}
						if(purchasePayListEntryInfo.getCanRefundmentAmount() != null)
						{
							canRefundmentAmount = purchasePayListEntryInfo.getCanRefundmentAmount();
						}
						if(purchasePayListEntryInfo.getActPayAmount() != null)
						{
							actAmount = purchasePayListEntryInfo.getActPayAmount();
						}
						
						//������޸�
						if(oldReceivingBillInfo != null)
						{
							
							BigDecimal oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
							BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
					
							//������ͬ�ĸ�����ϸ������ֻҪ��һ�μ��㼴��
							if(!tempComparison.equalsIgnoreCase(payListId) )
							{
								refundmentAmount = refundmentAmount.add(newAmount.abs()).subtract(oldAmount.abs());
								
								canRefundmentAmount = canRefundmentAmount.subtract(newAmount.abs()).add(oldAmount.abs());
								
								actAmount = actAmount.add(oldAmount.abs()).subtract(newAmount.abs());
							}
						}
						else
						{
							BigDecimal newAmount = entryInfo.getAmount();
							if(newAmount == null)
								newAmount = FDCHelper.ZERO;
							
							refundmentAmount = refundmentAmount.abs().add(newAmount.abs());
							
							canRefundmentAmount = canRefundmentAmount.subtract(newAmount.abs());
							
							actAmount = actAmount.subtract(newAmount.abs());
						}
						purchasePayListEntryInfo.setHasRefundmentAmount(refundmentAmount.abs());
						
						purchasePayListEntryInfo.setLimitAmount(canRefundmentAmount);
						purchasePayListEntryInfo.setActRevAmount(actAmount);
					/*	if(FDCHelper.ZERO.compareTo(actAmount) == 0)
							purchasePayListEntryInfo.setActPayDate(null);*/
						
						SelectorItemCollection sels = new SelectorItemCollection();
						
						sels.add("refundmentAmount");
						sels.add("canRefundmentAmount");
						sels.add("actPayAmount");
						//sels.add("actPayDate");
//						PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(purchasePayListEntryInfo, sels);
					}
					//�滻�Ƚ�ֵ
					if(!tempComparison.equalsIgnoreCase(payListId))
					{
						tempComparison = payListId;
					}
				}
			} //�����Ǽƻ��Ը���ͷǼƻ��Ը���
			else
			{
				//һ����ʱ�� payListId �Ƚ�ֵ
				String tempComparison = "";
				
				for(int i = 0; i < fdcRecBillEntryColl.size(); i ++)
				{
					FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(i);
					String payListId = entryInfo.getPayListId();
					
					
					//����һ���� BOSUuid
					if(!BOSUuid.isValidLength(payListId,true))
					{
						payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
					}
					
					MoneyDefineInfo moneyDefineInfo = entryInfo.getMoneyDefine();
					
					FilterInfo filter = new FilterInfo();
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("*");
					view.getSelector().add("moneyDefine.*");
					view.setFilter(filter);
					
					filter.getFilterItems().add(new FilterItemInfo("head",purchaseInfo.getId().toString()));
					
					filter.getFilterItems().add(new FilterItemInfo("id",payListId));
					
					PurchasePayListEntryCollection purchasePayListEntryColl  = PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryCollection(view);
					
					if(purchasePayListEntryColl.size() > 1)
					{
						logger.error("���Ϲ��� "+ purchaseInfo.toString()+"�ҵ����� id "+ payListId+" �ģ�������ϸ��");
						throw new BOSException("ϵͳ���ݴ����Ѽ�¼��־��");
					}
					//�Ǽƻ��Ը�����޶��Կ���
					else if(purchasePayListEntryColl.size() < 1)
					{
						//�ж��Ƿ��� ����������ϸ
						FilterInfo elseFilter = new FilterInfo();
						EntityViewInfo elseView = new EntityViewInfo();
						
						elseView.setFilter(elseFilter);
						
						elseFilter.getFilterItems().add(new FilterItemInfo("head",purchaseInfo.getId().toString()));
						elseFilter.getFilterItems().add(new FilterItemInfo("id",payListId));
						
						PurchaseElsePayListEntryCollection elsePayColl = PurchaseElsePayListEntryFactory.getLocalInstance(ctx).getPurchaseElsePayListEntryCollection(elseView);
						
						BigDecimal apAmount = FDCHelper.ZERO;
						BigDecimal actAmount = FDCHelper.ZERO;
						PurchaseElsePayListEntryInfo elsePayInfo = null;
						if(elsePayColl.size() > 1)
						{
							logger.error("�Ϲ���Ϊ"+purchaseInfo.toString()+",�ģ�����Ӧ������ID:"+payListId+" �����ظ�������...");
							throw new BOSException("ϵͳ���ݴ����Ѽ�¼��־��");
						}
						else if(elsePayColl.size() == 1)
						{
							elsePayInfo = elsePayColl.get(0);

							if (elsePayInfo.getActPayAmount() != null)
							{
								actAmount = elsePayInfo.getActPayAmount();
							}
							apAmount = elsePayInfo.getApAmount();
							if (apAmount == null)
							{
								apAmount = FDCHelper.ZERO;
							}
							
							BigDecimal canRefundmentAmount = FDCHelper.ZERO;
							if(elsePayInfo.getCanRefundmentAmount() != null)
								canRefundmentAmount = elsePayInfo.getCanRefundmentAmount();
							
							
							
							if (oldReceivingBillInfo != null)
							{
								BigDecimal oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
								BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
						
								//������ͬ�ĸ�����ϸ������ֻҪ��һ�μ��㼴��
								if(!tempComparison.equalsIgnoreCase(payListId) )
								{
									actAmount = actAmount.add(newAmount).subtract(oldAmount);
									
									canRefundmentAmount = canRefundmentAmount.add(newAmount).subtract(oldAmount);
								}
							}
							else
							{
								BigDecimal newAmount = entryInfo.getAmount();
								actAmount = actAmount.add(newAmount);
								
								canRefundmentAmount = canRefundmentAmount.add(newAmount);
							}
							
							elsePayInfo.setLimitAmount(canRefundmentAmount);
							elsePayInfo.setActRevAmount(actAmount);
							elsePayInfo.setActRevDate(receivingBillInfo.getBizDate());
							elsePayInfo.setLastUpdateTime(new Timestamp((new Date()).getTime()));
							
							
							if(actAmount == null || actAmount.compareTo(FDCHelper.ZERO) == 0)
							{
							elsePayInfo.setActRevDate(null);
							}
							
							SelectorItemCollection sels = new SelectorItemCollection();
							sels.add("actPayAmount");
							sels.add("actPayDate");
							sels.add("canRefundmentAmount");
							sels.add("lastUpdateTime");
//							PurchaseElsePayListEntryFactory.getLocalInstance(ctx).updatePartial(elsePayInfo, sels);
						}
						else
						{
						}
						
						this.updateStateByisCompleteReceiveInSHE(ctx, receivingBillInfo, oldReceivingBillInfo, purchaseInfo, actAmount,apAmount,entryInfo);
					}//�ƻ��Կ���
					else
					{
						PurchasePayListEntryInfo purchasePayListEntryInfo = purchasePayListEntryColl.get(0);
					
						BigDecimal appAmount = FDCHelper.ZERO;
						BigDecimal actAmount = FDCHelper.ZERO;
						
						if(purchasePayListEntryInfo.getApAmount() != null)
						{
							appAmount = purchasePayListEntryInfo.getApAmount();
						}
						if(purchasePayListEntryInfo.getActPayAmount() != null)
						{
							actAmount = purchasePayListEntryInfo.getActPayAmount();
						}
						
						//������޸�
						if(oldReceivingBillInfo != null)
						{
							
							BigDecimal oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
							BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
					
							//������ͬ�ĸ�����ϸ������ֻҪ��һ�μ��㼴��
							if(!tempComparison.equalsIgnoreCase(payListId) )
							{
								actAmount = actAmount.add(newAmount).subtract(oldAmount);
							}
						}
						else
						{
							BigDecimal newAmount = entryInfo.getAmount();
							actAmount = actAmount.add(newAmount);
						}
						purchasePayListEntryInfo.setActRevAmount(actAmount);
						purchasePayListEntryInfo.setActRevDate(receivingBillInfo.getBizDate());
						
						purchasePayListEntryInfo.setLastUpdateTime(new Timestamp((new Date()).getTime()));
						
						if(actAmount == null || actAmount.compareTo(FDCHelper.ZERO) == 0)
						{
							purchasePayListEntryInfo.setActRevDate(null);
						}
						
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("actPayAmount");
						sels.add("actPayDate");
						sels.add("lastUpdateTime");
//						PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(purchasePayListEntryInfo, sels);
						
						this.updateStateByisCompleteReceiveInSHE(ctx, receivingBillInfo, oldReceivingBillInfo, purchaseInfo, actAmount,appAmount,entryInfo);
						
					    //����¥����ڵ�һ�� �ƻ�������жϣ����Ϲ����ĸı�
						if(this.isFirstPlanMoney(ctx,payListId,purchaseInfo))
						{
							this.setPurchaseStateByFirstPlanMoney(ctx,receivingBillInfo,purchaseInfo,actAmount,appAmount);
						}
					}
					//�滻�Ƚ�ֵ
					if(!tempComparison.equalsIgnoreCase(payListId))
					{
						tempComparison = payListId;
					}
				}
			}
		}
		this.submit(ctx, fdcReceiveBillInfo);		
		receivingBillId = ReceivingBillFactory.getLocalInstance(ctx).submit(receivingBillInfo).toString();
		this.rewriteCheque(ctx,fdcReceiveBillInfo,purchaseInfo,receivingBillInfo);
		//PurchaseFactory.getLocalInstance(ctx).receiveBill(purchaseInfo.getId());
//		if (purchaseInfo != null)
//		{
//			if (FDCBillStateEnum.AUDITTED.equals(purchaseInfo.getState()))
//			{
//				// �����Ϲ�������ع�����ֹͣ
//				ProcessInstInfo instInfo = null;
//				ProcessInstInfo[] procInsts = null;
//				IEnactmentService service = EnactmentServiceFactory
//						.createEnactService(ctx);
//				procInsts = service.getProcessInstanceByHoldedObjectId(purchaseInfo.getId().toString());
//				for (int j = 0; j < procInsts.length; j++)
//				{
//					if ("open.running".equals(procInsts[j].getState()))
//					{
//						instInfo = procInsts[j];
//						service.abortProcessInst(instInfo.getProcInstId());
//					}
//				}
//			}
//		}
//		
		return receivingBillId;
	
	}
	
	/**
	 * �õ����տ��ĳһ������ɵ�ֵ
	 * @param ctx
	 * @param recId
	 * @param payListId
	 * @return
	 * @throws BOSException
	 */
	private BigDecimal getOldAmount(Context ctx,String recId,String payListId) throws BOSException
	{
		BigDecimal oldAmount = FDCHelper.ZERO;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.id",recId));
		filter.getFilterItems().add(new FilterItemInfo("payListId",payListId));
		
		FDCReceiveBillEntryCollection 	fdcRecColl = FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryCollection(view);
		
		for(int i = 0; i < fdcRecColl.size(); i ++)
		{
			BigDecimal temp = fdcRecColl.get(i).getAmount();
			
			if(temp == null)
				temp = FDCHelper.ZERO;
			
		    oldAmount = oldAmount.add(temp);
		}
		if(oldAmount == null)
			oldAmount = FDCHelper.ZERO;
		return oldAmount;
	}
	
	/**
	 * ȡ�������տ������տ����ĳ���տ���ϸҪ���ɵ�ֵ
	 * @param ctx
	 * @param fdcEntryColl
	 * @param payListId
	 * @return
	 */
	private BigDecimal getNewAmount(Context ctx,FDCReceiveBillEntryCollection fdcEntryColl,String payListId)
	{
		BigDecimal newAmount = FDCHelper.ZERO;
		
		for(int i = 0; i < fdcEntryColl.size(); i ++)
		{
			if(payListId.equalsIgnoreCase(fdcEntryColl.get(i).getPayListId()))
			{
				BigDecimal temp =  fdcEntryColl.get(i).getAmount();
				
				newAmount = newAmount.add(temp);
			}
		}
		return newAmount;
	}
	
	
	private FDCReceiveBillEntryCollection getFdcRecEntryInfo(Context ctx, String payListId) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
//		filter.getFilterItems().add(new FilterItemInfo("receivingBill.id",recId));
		filter.getFilterItems().add(new FilterItemInfo("payListId",payListId));
		
		return FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryCollection(view);
	}
	
	
	
	
	
	/**
	 * �жϵ�ǰ�տ���Ƿ��Ǹ���ƻ�����ĵ�һ��
	 * ע�����ø÷����ĵط����жϵ�ǰ���Ǹ����տ��Ǽƻ����տ�
	 * @param receivingBillInfo
	 * @param purchase
	 * @return
	 */
	private boolean isFirstPlanMoney(ReceivingBillInfo receivingBillInfo,PurchaseInfo purchase)
	{
		boolean debug = true;
		FDCReceiveBillInfo fdc = receivingBillInfo.getFdcReceiveBill();
		int seq = fdc.getSeq();
		PurchasePayListEntryCollection payColl = purchase.getPayListEntry();
		for(int i = 0; i < payColl.size(); i ++)
		{
			PurchasePayListEntryInfo info = payColl.get(i);
			int temp = info.getSeq();
			if(seq > temp)
			{
				debug = false;
				break;
			}
		}
		return debug;
	}
	

	/**
	 * �жϵ�ǰ�տ���Ƿ��Ǹ���ƻ�����ĵ�һ��
	 * ע�����ø÷����ĵط����жϵ�ǰ���Ǹ����տ��Ǽƻ����տ�
	 * @param payListId
	 * @param purchase
	 * @return
	 * @throws BOSException 
	 */
	private boolean isFirstPlanMoney(Context ctx, String payListId,PurchaseInfo purchase) throws BOSException
	{
		boolean debug = false;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("head",purchase.getId().toString()));
		view.getSelector().add("id");
		
		SorterItemCollection sorColl = new SorterItemCollection();
		sorColl.add(new SorterItemInfo("seq"));
		
		view.setSorter(sorColl);
		
		PurchasePayListEntryCollection payColl = PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryCollection(view);
		
		if(payColl.get(0) == null)
		{
			logger.error("���Ϲ�����"+purchase.toString()+" �У��Ҳ��� ID Ϊ��"+ payListId+" �ĸ�����ϸ...");
			throw new BOSException("���ִ�����鿴�������־��");
		}
		
		if(payListId.equalsIgnoreCase(payColl.get(0).getId().toString()))
		{
			debug = true;
		}
		else
		{
			debug = false;
		}
		return debug;
	}
	
	/**
	 * �жϸ÷��䣬�Ƿ���� ����״̬�µ��˷���
	 * @param room
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private boolean isExistChangeBill(Context ctx,RoomInfo room) throws EASBizException, BOSException
	{
		boolean debug = false;
		
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("oldRoom",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		
		debug = ChangeRoomFactory.getLocalInstance(ctx).exists(filter);
		
		return debug;
	}
	
	
	/**
	 * �����Ƿ�����˵�һ������ƻ������ı��Ϲ�����״̬
	 * @param ctx
	 * @param receivingBillInfo
	 * @param purchaseInfo
	 * @param actAmount
	 * @param apAmount
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void setPurchaseStateByFirstPlanMoney(Context ctx,ReceivingBillInfo receivingBillInfo,PurchaseInfo purchaseInfo,BigDecimal actAmount,BigDecimal apAmount) throws EASBizException, BOSException
	{
		Date bizDate = receivingBillInfo.getBizDate();
		RoomSellStateEnum state = purchaseInfo.getRoom().getSellState();
		
		//������ڻ���������ȥ�ı�״̬�ˡ�
		/*if(this.isExistChangeBill(ctx, purchaseInfo.getRoom()))
			return;*/
		//����� �Ϲ�����״̬ʱ���ϵģ��Ͳ��ù���
		if(PurchaseStateEnum.ChangeRoomBlankOut.equals(purchaseInfo.getPurchaseState())
				|| PurchaseStateEnum.QuitRoomBlankOut.equals(purchaseInfo.getPurchaseState())
				|| PurchaseStateEnum.NoPayBlankOut.equals(purchaseInfo.getPurchaseState())
				|| PurchaseStateEnum.ManualBlankOut.equals(purchaseInfo.getPurchaseState())
				|| PurchaseStateEnum.AdjustBlankOut.equals(purchaseInfo.getPurchaseState())
				)
			return;
		
		
		if (actAmount.compareTo(apAmount) >= 0)
		{
			if(!RoomSellStateEnum.Sign.equals(state))
			// ���տ�ĵ������ڻ�д��������Ϲ�������ȥ
			RoomStateFactory.setRoomSellState(ctx,RoomSellStateEnum.Purchase, purchaseInfo.getId().toString(), bizDate);
		}//�����ɾ�������һ���տ���Ϊ ���۵�״̬
		else if(actAmount.compareTo(FDCHelper.ZERO) == 0)
		{
//			RoomStateFactory.setRoomSellState(ctx,RoomSellStateEnum.OnShow, purchaseInfo.getId().toString(), bizDate);
			RoomStateFactory.setRoomOnShow(ctx, purchaseInfo.getRoom().getId().toString(), false);
		}
		else
		{
			//-- ����תԤ������ҲҪ���տ�����Ϊ׼  zhicheng_jin 090313
			RoomStateFactory.setRoomSellState(ctx,RoomSellStateEnum.PrePurchase, purchaseInfo.getId().toString(), bizDate);
			//-------- over ----------
		}
	}

	/**
	 * ������Ӧ��ʵ�ս�Ӧ�ս�������һЩ״̬�ı仯
	 * @param ctx
	 * @param receivingBillInfo
	 * @param oldReceivingBillInfo
	 * @param purchaseInfo
	 * @param actAmount
	 * @param apAmount
	 * @param entryInfo TODO
	 * @throws BOSException
	 * @throws EASBizException
	 * @author laiquan_luo
	 */
	private void updateStateByisCompleteReceiveInSHE(Context ctx, ReceivingBillInfo receivingBillInfo, ReceivingBillInfo oldReceivingBillInfo, PurchaseInfo purchaseInfo, BigDecimal actAmount,BigDecimal apAmount,FDCReceiveBillEntryInfo entryInfo) throws BOSException, EASBizException
	{
		MoneyDefineInfo moneyDefineInfo = entryInfo.getMoneyDefine();
		
		RoomInfo room = purchaseInfo.getRoom();
		
		
		//�ﵽԤ�����׼�Ÿı�״̬
		if (moneyDefineInfo.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))
		{
			if (RoomSellStateEnum.PrePurchase.equals(room.getSellState())
					|| RoomSellStateEnum.OnShow.equals(room.getSellState()))
			{
				if (this.isCompleteReceivePreMoney(ctx, entryInfo, purchaseInfo))
				{
					RoomStateFactory.setRoomSellState(ctx,RoomSellStateEnum.PrePurchase, purchaseInfo.getId().toString(),receivingBillInfo.getBizDate());
				}
				// ���ɾ���˸�Ԥ�տ��Ҫ��������Ϊ����״̬
				else
				{
					RoomStateFactory.setRoomOnShow(ctx, purchaseInfo.getRoom().getId().toString(), false);
				}
			}
		}
		else if (moneyDefineInfo.getMoneyType().equals(MoneyTypeEnum.CompensateAmount))
		{
			this.changeAreaCompensate(ctx, receivingBillInfo, entryInfo);
		}
	}
	

	/**
	 * ������������״̬���жϵ����ܵ��������Ŀ������Ӧ����Ľ�����������״̬��Ϊ���տ��״̬
	 * @param ctx
	 * @param receivingBillInfo
	 * @param entryInfo TODO
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void changeAreaCompensate(Context ctx, ReceivingBillInfo receivingBillInfo, FDCReceiveBillEntryInfo entryInfo)throws BOSException, EASBizException
	{
		BigDecimal apAmount = FDCHelper.ZERO;
		BigDecimal actAmount = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if (receivingBillInfo.getId() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.id", receivingBillInfo.getId().toString(),CompareType.NOTEQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", receivingBillInfo.getFdcReceiveBill().getPurchase().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType",MoneyTypeEnum.COMPENSATEAMOUNT_VALUE));
		
		FDCReceiveBillEntryCollection entryColl = FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryCollection(view);
		
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", receivingBillInfo.getFdcReceiveBill().getRoom().getId().toString()));
		view.setFilter(filter);
		RoomAreaCompensateCollection roomAreaCompensateColl = RoomAreaCompensateFactory.getLocalInstance(ctx).getRoomAreaCompensateCollection(view);
		if (roomAreaCompensateColl.size() == 1)
		{
			RoomAreaCompensateInfo roomAreaCompensateInfo = roomAreaCompensateColl.get(0);
			if (roomAreaCompensateInfo.getCompensateAmount() != null)
			{
				apAmount = roomAreaCompensateInfo.getCompensateAmount();
			}
			actAmount = FDCHelper.ZERO;
			for (int i = 0; i < entryColl.size(); i++)
			{
				FDCReceiveBillEntryInfo aRev = entryColl.get(i);
				BigDecimal amount = aRev.getAmount();
				if(amount == null)
					amount = FDCHelper.ZERO;
				actAmount = actAmount.add(amount);
			}
			actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO:entryInfo.getAmount());
			actAmount = actAmount.abs();
			apAmount = apAmount.abs();
			if (actAmount.compareTo(apAmount) >= 0)
			{
				roomAreaCompensateInfo.setCompensateState(RoomCompensateStateEnum.COMRECEIVED);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("compensateState");
				RoomAreaCompensateFactory.getLocalInstance(ctx).updatePartial(roomAreaCompensateInfo, sels);
			}
			else
			{
				roomAreaCompensateInfo.setCompensateState(RoomCompensateStateEnum.COMAUDITTED);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("compensateState");
				RoomAreaCompensateFactory.getLocalInstance(ctx).updatePartial(roomAreaCompensateInfo, sels);
			}
		}
	}
	/**
	 * ��д����
	 * @param ctx
	 * @param fdcReceiveBillInfo
	 * @param purchaseInfo
	 * @param receivingBillInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws UuidException
	 */
	private void rewriteCheque(Context ctx,FDCReceiveBillInfo fdcReceiveBillInfo,PurchaseInfo purchaseInfo,ReceivingBillInfo receivingBillInfo) throws EASBizException, BOSException, UuidException
	{
		// ��д�վ������Ϣ
		ChequeInfo chequeInfo = fdcReceiveBillInfo.getCheque();
		if (chequeInfo != null)
		{
			SelectorItemCollection selectorItemColl = new SelectorItemCollection();
			selectorItemColl.add("name");
			selectorItemColl.add("building.name");
			selectorItemColl.add("building.sellProject.name");
			selectorItemColl.add("building.subarea.name");
			
			//���ӳ�����տ�󣬶��ڳ����Ϲ��տ������ָ�� TODO ������Ҫ��Գ����Ϲ���Ʊ�ݷ�дȷ������
			if(purchaseInfo != null){
				BOSUuid roomId = null;
				if(purchaseInfo.getRoom()!=null)
					roomId = purchaseInfo.getRoom().getId();
				if(roomId==null && fdcReceiveBillInfo.getRoom()!=null)
					roomId = fdcReceiveBillInfo.getRoom().getId();
				
				RoomInfo room = RoomFactory.getLocalInstance(ctx).getRoomInfo(
						new ObjectUuidPK(roomId), selectorItemColl);

				StringBuffer sb = new StringBuffer();
				sb.append(room.getBuilding().getSellProject().getName());
				sb.append("-");

				SubareaInfo subarea = room.getBuilding().getSubarea();
				if (subarea != null)
				{
					sb.append(subarea.getName());
					sb.append("-");
				}

				// sb.append(" ¥����");
				sb.append(room.getBuilding().getName());
				sb.append("-");

				// sb.append(" ���䣺");
				sb.append(room.getNumber());
				sb.append("��");

				// sb.append(" �տ����ͣ�");
				sb.append(receivingBillInfo.getRecBillType().getName());

				chequeInfo.setResume(sb.toString());
			}

			chequeInfo.setAmount(receivingBillInfo.getAmount());
			BigDecimal invoiceAmount = receivingBillInfo.getAmount();
			if (invoiceAmount == null)			{
				invoiceAmount = new BigDecimal("0");
			}else{
				Format u = NTNumberFormat.getInstance("rmb");
				if(invoiceAmount.compareTo(new BigDecimal("0"))>=0) {
					chequeInfo.setCapitalization("��:"+u.format(invoiceAmount));
				}else{					
					chequeInfo.setCapitalization("��:"+u.format(invoiceAmount.negate()));
				}
			}			
			
			chequeInfo.setStatus(ChequeStatusEnum.WrittenOff);
			chequeInfo.setPayer(receivingBillInfo.getPayerName());
			chequeInfo.setPayTime(new Timestamp(new Date().getTime()));
			UserInfo curUser = ContextUtil.getCurrentUserInfo(ctx);
			chequeInfo.setWrittenOffer(curUser);
			chequeInfo.setWrittenOffTime(new Timestamp(new Date().getTime()));

			SelectorItemCollection sles = new SelectorItemCollection();
			sles.add("resume");
			sles.add("amount");
			sles.add("status");
			sles.add("capitalization");

			sles.add("payer");
			sles.add("payTime");
			sles.add("writtenOffer");
			sles.add("writtenOffTime");
			ChequeFactory.getLocalInstance(ctx).updatePartial(chequeInfo, sles);
		}
	}

	/**
	 * ֻҪԤ�տ�������OK��
	 * @author laiquan_luo
	 * @param entryInfo TODO
	 * @param purchase TODO
	 * @return
	 * @throws BOSException 
	 */
	private boolean isCompleteReceivePreMoney(Context ctx,FDCReceiveBillEntryInfo entryInfo, PurchaseInfo purchase) throws BOSException
	{
		boolean debug = false;
		
		BigDecimal amount = entryInfo.getAmount();
		MoneyDefineInfo moneyDefineInfo = entryInfo.getMoneyDefine();
		if(amount == null)
			amount = FDCHelper.ZERO;
		
		if(amount.compareTo(FDCHelper.ZERO) > 0)
		{
			debug = true;
		}
		else
		{
			FilterInfo filter = new FilterInfo();
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id", moneyDefineInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.TRANSFER_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", purchase.getId().toString()));
			
			filter.setMaskString("#0 and #1 and (#2 or #3) and #4");
			FDCReceiveBillEntryCollection billColl = FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryCollection(view);
			
			Set set = new HashSet();
			for(int i = 0; i < billColl.size(); i ++)
			{
				if(billColl.get(i).getReceivingBill() != null && billColl.get(i).getReceivingBill().getId() != null)
				set.add(billColl.get(i).getReceivingBill().getId().toString().trim());
			}
			
			if(set.size() > 1)
			{
				debug = true;
			}
			else
			{
				debug = false;
			}
		}
			
		
		return debug;
		
	
	}
	
	/**
	 *  �жϸú�ͬ�Ƿ�����Ѻ�����⣬����ֻ�ж��� �Ƿ��������⣬��Ϊ���������ˣ���Ѻ��϶������ˡ�
	 * @param ctx
	 * @param tennacyBillInfo
	 * @return
	 * @throws BOSException
	 */
	private boolean isCompleteReceiveDepositAndFirstMoney(Context ctx,TenancyBillInfo tennacyBillInfo) throws BOSException
	{
		boolean debug = false;
		
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",tennacyBillInfo.getId().toString()));
		
		TenancyRoomEntryCollection tenancyRoomEntryColl = TenancyRoomEntryFactory.getLocalInstance(ctx).getTenancyRoomEntryCollection(view);
		for(int i = 0; i < tenancyRoomEntryColl.size(); i ++)
		{
			TenancyRoomEntryInfo tenancyRoomEntryInfo = tenancyRoomEntryColl.get(i);
			//��һ������û�н��룬������false
			if(!this.isCompleteReceiveDepositAndFirstMoney(ctx,tenancyRoomEntryInfo))
			{
				debug = false;
				break;
			}
			{
				debug = true;
			}
		}
		return debug;
	}
	/**
	 * �жϸ÷����¼�Ƿ�����Ѻ�����⣬����ֻ�ж��� �Ƿ��������⣬��Ϊ���������ˣ���Ѻ��϶������ˡ�
	 * @param ctx
	 * @param tenancyRoomEntryInfo
	 * @return
	 * @throws BOSException
	 */
	private boolean isCompleteReceiveDepositAndFirstMoney(Context ctx,TenancyRoomEntryInfo tenancyRoomEntryInfo) throws BOSException
	{	
		TenancyRoomPayListEntryInfo tenancyRoomPayListEntryInfo = this.getTheFirstRentAmount(ctx,tenancyRoomEntryInfo);
		if(tenancyRoomPayListEntryInfo == null)
		{
			logger.warn("�ڷ�����Ϊ" + tenancyRoomEntryInfo.getRoom().getNumber()+ "�ķ������棬û�и�����ϸ��ϵͳֱ�ӱ�ʶ�˸ú�ͬδ��Ѻ�����⣡");
			return false;
		}
		BigDecimal appAmount = FDCHelper.ZERO;
		if(tenancyRoomPayListEntryInfo.getAppAmount() != null)
		{
			appAmount = tenancyRoomPayListEntryInfo.getAppAmount();
		}
		BigDecimal actAmount = FDCHelper.ZERO;
		if(tenancyRoomPayListEntryInfo.getActAmount() != null)
		{
			actAmount = tenancyRoomPayListEntryInfo.getActAmount();
		}
		if(actAmount.compareTo(appAmount) >= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * �ҵ�һ����������������ĵ�����������ϸ�������Ǽ򵥵Ĺ̶�ͨ��  seq Ϊ 1 ���жϣ�������������Ӧ���޸�����ϸ�ı䶯��
	 * @param tenancyBillInfo
	 * @return
	 * @throws BOSException 
	 */
	private TenancyRoomPayListEntryInfo getTheFirstRentAmount(Context ctx,TenancyRoomEntryInfo tenancyRoomEntryInfo) throws BOSException
	{
		TenancyRoomPayListEntryInfo returnValue = null;
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("moneyDefine.*");
		view.setFilter(filter);
		
		SorterItemCollection sorterColl = new SorterItemCollection();
		sorterColl.add(new SorterItemInfo("seq"));
		view.setSorter(sorterColl);
		
		filter.getFilterItems().add(new FilterItemInfo("tenRoom",tenancyRoomEntryInfo.getId().toString()));
		
		TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl = TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryCollection(view); 
		if(tenancyRoomPayListEntryColl == null)
		{
			logger.warn("�ڷ�����Ϊ" + tenancyRoomEntryInfo.getRoom().getNumber()+ "�ķ������棬û�и�����ϸ��");
			return null;
		}
		//��һ�����ֵ� ��� ���� ����
		for(int i = 0; i < tenancyRoomPayListEntryColl.size(); i ++)
		{
			TenancyRoomPayListEntryInfo tenancyRoomPayListEntryInfo = tenancyRoomPayListEntryColl.get(i);
			if(MoneyTypeEnum.RentAmount.equals(tenancyRoomPayListEntryInfo.getMoneyDefine().getMoneyType()))
			{
				returnValue = tenancyRoomPayListEntryInfo;
				break;
			}
		}
		return returnValue;
	}

	/**
	 * �����տ�
	 * 
	 * @author laiquan_luo
	 */
	protected void _submitByCasRevColl(Context ctx, IObjectCollection casRevColl)
			throws BOSException, EASBizException
	{
		for (int i = 0; i < casRevColl.size(); i++)
		{
			this._submitByCasRev(ctx, casRevColl.getObject(i));
		}

	}

	
}