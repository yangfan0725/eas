package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.ChequeCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillRecordCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillRecordFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillRecordInfo;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum;
import com.kingdee.eas.fdc.sellhouse.RecordTypeEnum;
import com.kingdee.eas.fm.nt.NTNumberFormat;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ReceiptInvoiceFacadeControllerBean extends
		AbstractReceiptInvoiceFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.ReceiptInvoiceFacadeControllerBean");

	/**
	 * �����տ��ƱIDȡ�ò�����¼
	 */
	protected IObjectCollection _getRecord(Context ctx, BOSUuid id)
			throws BOSException {
		FDCReceiveBillRecordCollection col = null;

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("operatingUser.id");
		sic.add("operatingUser.name");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head", id));
		view.setFilter(filter);
		SorterItemCollection sort = new SorterItemCollection();
		SorterItemInfo sii = new SorterItemInfo("operatingDate");
		sii.setSortType(SortType.ASCEND);
		sort.add(sii);
		view.setSorter(sort);

		col = FDCReceiveBillRecordFactory.getLocalInstance(ctx)
				.getFDCReceiveBillRecordCollection(view);

		return col;
	}

	/**
	 * �����վݲ����²�����¼
	 * fdcPK ������
	 * �޸ļ�¼�������Ѿ����л�Ʊ��������Ҫ����ǰ��Ʊ�ݵ�һЩ��Ϣ�����޸�
	 * 
	 */
	protected void _updateReceipt(Context ctx, String pk, String fdcPK,
			String receiptID, String receiptNum, String oldReceiptNum,
			String description) throws BOSException {

		// �����տ
		if (pk == null) { 
			return;
		}

		FDCReceivingBillInfo updateFDCRevInfo = new FDCReceivingBillInfo();
		updateFDCRevInfo.setId(BOSUuid.read(pk));
		if(receiptID!=null && receiptID.trim().length()>0) {
			ChequeInfo receptInfo = new ChequeInfo();
			receptInfo.setId(BOSUuid.read(receiptID));
			updateFDCRevInfo.setReceipt(receptInfo);
		}
		updateFDCRevInfo.setReceiptNumber(receiptNum);
		if ((receiptNum != null && !receiptNum.equals("")) || receiptID != null) 
			updateFDCRevInfo.setReceiptState(ReceiptStatusEnum.HasMake);
		else
			updateFDCRevInfo.setReceiptState(ReceiptStatusEnum.UnMake);	
		SelectorItemCollection updateFDCRevSels = new SelectorItemCollection();
		updateFDCRevSels.add("receipt");
		updateFDCRevSels.add("receiptNumber");
		updateFDCRevSels.add("receiptState");
		try{
			FDCReceivingBillFactory.getLocalInstance(ctx).updatePartial(updateFDCRevInfo, updateFDCRevSels);
			updateFDCRevInfo = FDCReceivingBillFactory.getLocalInstance(ctx)
						.getFDCReceivingBillInfo("select amount,Customer.name,bizDate,room.name,revBillType,revBizType " +
								"where id = '"+updateFDCRevInfo.getId()+"'");
		}catch(Exception e){e.printStackTrace();}
		
			
		// ��վ�
		if (receiptID != null) {
			ChequeInfo cheque = new ChequeInfo();
			cheque.setId(BOSUuid.read(receiptID));
			// ״̬
			cheque.setStatus(ChequeStatusEnum.WrittenOff);
			// ���
			cheque.setWrittenOffer(ContextUtil.getCurrentUserInfo(ctx));
			// �����
			cheque.setWrittenOffTime(new Timestamp(new Date().getTime()));
			// ������
			if(updateFDCRevInfo.getCustomer()!=null)
				cheque.setPayer(updateFDCRevInfo.getCustomer().getName());

			// ��������
			if(updateFDCRevInfo.getBizDate()!=null)
				cheque.setPayTime(new Timestamp(updateFDCRevInfo.getBizDate().getTime()));
			
			if(updateFDCRevInfo.getRoom()!=null)
				cheque.setResume(updateFDCRevInfo.getRoom().getName()+","
							+ updateFDCRevInfo.getRevBillType().getAlias()+","+updateFDCRevInfo.getRevBizType().getAlias());
			
			BigDecimal invoiceAmount = updateFDCRevInfo.getAmount();
			if(invoiceAmount == null)	invoiceAmount = new BigDecimal("0");			
			Format u = NTNumberFormat.getInstance("rmb");
			cheque.setCapitalization(u.format(invoiceAmount));    		
			cheque.setAmount(invoiceAmount);
			cheque.setDescription(description);

			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("status");
			sels.add("writtenOffer");
			sels.add("writtenOffTime");
			sels.add("capitalization");
			sels.add("amount");
			
			sels.add("payer");
			sels.add("payTime");
			sels.add("resume");
			sels.add("Description");
			try {
				ChequeFactory.getLocalInstance(ctx).updatePartial(cheque, sels);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}

		// ���Ӳ�����¼
		FDCReceiveBillRecordInfo record = new FDCReceiveBillRecordInfo();
		// ����ͷ && ����
		record.setHead(pk);
		record.setHeadType(0);
		// �������� && ����
		if (oldReceiptNum == null || oldReceiptNum.equals("")) {
			record.setRecordType(RecordTypeEnum.MakeOutReceipt);
			record.setContent("���վ�" + receiptNum);
		} else {
			record.setRecordType(RecordTypeEnum.ChangeReceipt);
			record.setContent("�վ�" + oldReceiptNum + "��Ϊ�վ�" + receiptNum);
		}
		// ������
		record.setOperatingUser(ContextUtil.getCurrentUserInfo(ctx));
		// ��������
		record.setOperatingDate(new Date());
		// ��ע
		record.setDescription(description);

		// ������¼
		try {
			FDCReceiveBillRecordFactory.getLocalInstance(ctx).addnew(record);
		} catch (EASBizException e) {
			e.printStackTrace();
		}

	}

	/**
	 * �����վݲ����²�����¼
	 * fdcPK ������ 
	 */
	protected void _retackReceipt(Context ctx, String pk, String fdcPK,
			String receiptNum, String description,Map paramMap) throws BOSException {
		// �����տ
		if (pk == null) {
			return;
		}
		
		
		
		
		
		
		if (paramMap!=null && paramMap.get("retakeReceivingBillidSet")!=null){
			Set idSet = (Set)paramMap.get("retakeReceivingBillidSet");
			if(idSet.size()==1){//������һ����ʱ�� ����ȥ�޸�Ʊ�ݹ����е�״̬����Ϊһ����ʱ����Ҫ�޸ģ���Ϊ����һ������ʹ������Ʊ��
				//���շ�ͳһƱ�ݹ����Ʊ��
				if(paramMap.get("receivingBillInfo")!=null && paramMap.get("receivingBillInfo") instanceof FDCReceivingBillInfo){
					FDCReceivingBillInfo receivInfo = (FDCReceivingBillInfo)paramMap.get("receivingBillInfo");
					if(receivInfo.getReceiptNumber()!=null && receivInfo.getRoom().getId()!=null ){
						
						// ����Ʊ���վ�
						StringBuffer sql = new StringBuffer();
						sql.append(" update T_SHE_Cheque set FWrittenOfferID = null,FWrittenOffTime = null,FStatus = 2 ");
						sql.append(" where FID in (select FReceiptID from T_BDC_FDCReceivingBill ");
						sql.append(" where FID = '"+pk+"' and FReceiptID is not null ) ");
						DbUtil.execute(ctx, sql.toString());
						
						
						//����Ʊ�ݹ���
						StringBuffer sqll = new StringBuffer();
						sqll.append(" update T_SHE_Invoice set FDate = null,FUserID = null ");
						sqll.append(" where FChequeID in (select FReceiptID from T_BDC_FDCReceivingBill ");
						sqll.append(" where FID = '"+pk+"' and FReceiptID is not null ) ");
						DbUtil.execute(ctx, sqll.toString());
						
						StringBuffer sqlByFNumber = new StringBuffer();
						sqlByFNumber.append(" update T_SHE_Invoice set FDate = null ");
						sqlByFNumber.append(" where fnumber = '" +receivInfo.getReceiptNumber() +"' ");
						sqlByFNumber.append(" and froomid ='"+String.valueOf(receivInfo.getRoom().getId())+"' ");
						DbUtil.execute(ctx, sqlByFNumber.toString());
						
						
						
					}
				}
			}
		} 
		
		
		
		
		FDCReceivingBillInfo updateFDCRevInfo = new FDCReceivingBillInfo();
		updateFDCRevInfo.setId(BOSUuid.read(pk));
		updateFDCRevInfo.setReceipt(null);
		updateFDCRevInfo.setReceiptNumber(null);
		updateFDCRevInfo.setReceiptState(ReceiptStatusEnum.HasRecover);	
		SelectorItemCollection updateFDCRevSels = new SelectorItemCollection();
		updateFDCRevSels.add("receipt");
		updateFDCRevSels.add("receiptNumber");
		updateFDCRevSels.add("receiptState");
		try{
			FDCReceivingBillFactory.getLocalInstance(ctx).updatePartial(updateFDCRevInfo, updateFDCRevSels);
		}catch(Exception e){e.printStackTrace();}
		
		

		// ���Ӳ�����¼
		FDCReceiveBillRecordInfo record = new FDCReceiveBillRecordInfo();
		// ����ͷ && ����
		record.setHead(pk);
		record.setHeadType(0);
		// �������� && ����
		record.setRecordType(RecordTypeEnum.RetakeReceipt);
		record.setContent("�����վ�" + receiptNum);
		// ������
		record.setOperatingUser(ContextUtil.getCurrentUserInfo(ctx));
		// ��������
		record.setOperatingDate(new Date());
		// ��ע
		record.setDescription(description);

		// ������¼
		try {
			FDCReceiveBillRecordFactory.getLocalInstance(ctx).addnew(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���²�����¼
	 */
	protected void _updateRecord(Context ctx, int billType, String pk,
			RecordTypeEnum recordType, String content, String description)
			throws BOSException {
		// ���Ӳ�����¼
		FDCReceiveBillRecordInfo record = new FDCReceiveBillRecordInfo();
		// ����ͷ && ����
		record.setHead(pk);
		record.setHeadType(billType);
		// �������� && ����
		record.setRecordType(recordType);
		record.setContent(content);
		// ������
		record.setOperatingUser(ContextUtil.getCurrentUserInfo(ctx));
		// ��������
		record.setOperatingDate(new Date());
		// ��ע
		record.setDescription(description);

		// ������¼
		try {
			FDCReceiveBillRecordFactory.getLocalInstance(ctx).addnew(record);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���·�Ʊ�����²�����¼
	 * ���۷�Ʊ -- ��Ʊ  ����
	 */
	protected void _updateInvoice(Context ctx, String pk, String oldChequeNum,
			String newChequePK, String newChequeNum, String description)
			throws BOSException {
		// ���·�Ʊ
		if (pk == null) {
			return;
		}
		InvoiceInfo invoice = null;
		try {
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("room.*");
			sels.add("cheque.*");
			sels.add("customer.*");
			sels.add("user.*");
			invoice = InvoiceFactory.getLocalInstance(ctx).getInvoiceInfo(new ObjectUuidPK(pk),sels);
		} catch (EASBizException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuffer sql = new StringBuffer();
		Object[] params = new Object[] { newChequeNum, newChequePK, pk };
		sql.append(" update T_SHE_Invoice set ");
		sql.append(" FNumber = ? , FChequeID = ? ");
		sql.append(" where fid = ? ");
//		DbUtil.execute(ctx, sql.toString(), params);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx,sql.toString());
		builder.addParams(params);
		builder.executeUpdate(ctx);

		// ���Ʊ
		if (newChequePK != null) {
			//chequePk��Ϊ�յ�����£�Ӧ��������ͬ��Ʊ�ݹ�����Ҫ�Ϳ�ͨ��oldChequeNum��ѯ֮ǰ��Ʊ�ݵ���Ϣ
			ChequeInfo cheque = new ChequeInfo();
			ChequeCollection oldChequeColl = ChequeFactory.getLocalInstance(ctx).getChequeCollection("where number = '"+oldChequeNum+"'");
			ChequeInfo oldCheQueInfo=null;
			if(oldChequeColl.size()==1) {
				oldCheQueInfo = oldChequeColl.get(0); 
				cheque = (ChequeInfo)oldCheQueInfo.clone();
				
				//����ɵ�Ʊ�ݵ���Ϣ
				try{
					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("resume");
					sels.add("status");
					sels.add("payer");
					sels.add("amount");
					sels.add("description");
					sels.add("capitalization");    		
					sels.add("writtenOffer");
					sels.add("writtenOffTime");
					/**
					 * �����������
					 * update by renliang
					 * at 2010-10-7
					 */
					sels.add("payTime");
					oldCheQueInfo.setCapitalization(null);
					oldCheQueInfo.setPayer(null);
					oldCheQueInfo.setResume("");
					oldCheQueInfo.setStatus(ChequeStatusEnum.Booked);
					oldCheQueInfo.setAmount(null);
					oldCheQueInfo.setDescription("");
					oldCheQueInfo.setWrittenOffer(null);
					oldCheQueInfo.setWrittenOffTime(null);
					/**
					 * �����������
					 * update by renliang
					 * at 2010-10-7
					 */
					oldCheQueInfo.setPayTime(null);
					ChequeFactory.getLocalInstance(ctx).updatePartial(oldCheQueInfo, sels);
					
				}catch(Exception e){e.printStackTrace();}
			}
			
			cheque.setId(BOSUuid.read(newChequePK));
			// ״̬
			cheque.setStatus(ChequeStatusEnum.WrittenOff);
			// ���
			cheque.setWrittenOffer(ContextUtil.getCurrentUserInfo(ctx));
			// �����
			cheque.setWrittenOffTime(new Timestamp(new Date().getTime()));
			cheque.setAmount(invoice.getAmount());
			if(invoice.getCustomer() != null){
				cheque.setPayer(invoice.getCustomer().getName());
			}
			cheque.setPayTime(invoice.getCreateTime());
			cheque.setCU(invoice.getCU());
			cheque.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			cheque.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
			//ժҪ
//			if(oldCheQueInfo!=null){
//				cheque.setResume(oldCheQueInfo.getResume());
//			}
			Format u = NTNumberFormat.getInstance("rmb");
			cheque.setCapitalization(u.format(invoice.getAmount()));  
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("status");
			sels.add("writtenOffer");
			sels.add("writtenOffTime");
			sels.add("CU");
			sels.add("payer");
			sels.add("payTime");
			sels.add("amount");
			sels.add("capitalization");
			sels.add("resume");
			
			try {
				ChequeFactory.getLocalInstance(ctx).updatePartial(cheque, sels);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}

		// ���Ӳ�����¼
		FDCReceiveBillRecordInfo record = new FDCReceiveBillRecordInfo();
		// ����ͷ && ����
		record.setHead(pk);
		record.setHeadType(1);
		// �������� && ����
		if (oldChequeNum == null || oldChequeNum.equals("")) {
			record.setRecordType(RecordTypeEnum.MakeOutInvoice);
			record.setContent("��Ʊ��" + newChequeNum);
		} else {
			record.setRecordType(RecordTypeEnum.ChangeInvoice);
			record.setContent("Ʊ��" + oldChequeNum + "��ΪƱ��" + newChequeNum);
		}
		// ������
		record.setOperatingUser(ContextUtil.getCurrentUserInfo(ctx));
		// ��������
		record.setOperatingDate(new Date());
		// ��ע
		record.setDescription(description);

		// ������¼
		try {
			FDCReceiveBillRecordFactory.getLocalInstance(ctx).addnew(record);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		//���վݺ� �տ����Ҫ�滻��
		try {
			FDCReceivingBillCollection revcoll = null;
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection coll = new SelectorItemCollection();
			FilterInfo filter = new FilterInfo();
//			coll.add("*");
//			coll.add("");
			view.setSelector(coll);
			view.setFilter(filter);
			
			if(ChequeTypeEnum.receipt.equals(invoice.getChequeType())&&invoice!=null){//ֻ�����վݣ���Ʊ���ô���
				//�þɵ��վ�ȥ���տ
			 if (newChequePK!=null&&invoice.getCheque()!=null){//����ͳһƱ�ݹ���
					ChequeInfo cheque = ChequeFactory.getLocalInstance(ctx).getChequeInfo(new ObjectUuidPK(newChequePK));
					filter.getFilterItems().add(new FilterItemInfo("receipt.id",invoice.getCheque().getId()));
					revcoll = FDCReceivingBillFactory.getLocalInstance(ctx).getFDCReceivingBillCollection(view);
					SelectorItemCollection coll1 = new SelectorItemCollection();
					coll1.add("receipt.id");
					for(int i = 0;i<revcoll.size();i++){
						FDCReceivingBillInfo revinfo =revcoll.get(i);
						revinfo.setReceipt(cheque);
						FDCReceivingBillFactory.getLocalInstance(ctx).updatePartial(revinfo, coll1);
					}
			}else {//û������ͳһƱ�ݹ���
						filter.getFilterItems().add(new FilterItemInfo("receiptNumber",invoice.getNumber()));
						revcoll = FDCReceivingBillFactory.getLocalInstance(ctx).getFDCReceivingBillCollection(view);
						SelectorItemCollection coll1 = new SelectorItemCollection();
						coll1.add("receiptNumber");
						for(int i = 0;i<revcoll.size();i++){
							FDCReceivingBillInfo revinfo =revcoll.get(i);
							revinfo.setReceiptNumber(newChequeNum);
							FDCReceivingBillFactory.getLocalInstance(ctx).updatePartial(revinfo, coll1);
						}
					}
					
					
				}
				
			
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ͨ��������ȡ��Ʊ����Ϣ
	 */
	protected List _getChequebyChgID(Context ctx, String chgID)
			throws BOSException {
		List lst = new ArrayList();
		Map data;

		// ȡ�վ�����
		StringBuffer sql = new StringBuffer();
		sql.append(" select DISTINCT fr.Fid as id , '�վ�' as type , ");
		sql.append(" fr.FReceiptNumber as oldNumber , fr.FAmount as amount ");
		sql.append(" from T_SHE_PChCustomer as pcc ");
		sql.append(" left join T_SHE_Purchase as pc on pc.FID = pcc.FPurchaseID ");
		sql.append(" left join T_BDC_FDCReceivingBill as fr on fr.FPurchaseObjID = pc.FID ");
		sql.append(" where pcc.FID = '").append(chgID).append("' ");
		sql.append(" and fr.FReceiptState = '"+ReceiptStatusEnum.HASMAKE_VALUE + "'");
		
		// ȡ��Ʊ����
		sql.append(" union all ");
		sql.append(" select DISTINCT iv.Fid as id , '��Ʊ' as type , ");
		sql.append(" iv.FNumber as oldNumber , iv.FAmount as amount ");
		sql.append(" from T_SHE_PChCustomer as pcc ");
		sql.append(" left join T_SHE_Purchase as pc on pc.FID = pcc.FPurchaseID ");
		sql.append(" left join T_BDC_FDCReceivingBill as fr on fr.FPurchaseObjID = pc.FID ");
		sql.append(" left join T_SHE_Invoice as iv on iv.FID = fr.FInvoiceID ");
		sql.append(" where iv.Fid is not null ");
		sql.append(" and pcc.FID = '").append(chgID).append("'");

		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		try {
			String id;
			String type;
			String number;
			BigDecimal amount;
			while (rs.next()) {
				id = rs.getString("id");
				type = rs.getString("type");
				number = rs.getString("oldNumber");
				amount = rs.getBigDecimal("amount");

				data = new HashMap();
				data.put("id", id);
				data.put("type", type);
				data.put("number", number);
				data.put("amount", amount);
				lst.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}

	/**
	 * ����������Ʊ��
	 */
	protected void _updateChequeForChg(Context ctx, List param)
			throws BOSException {
		if (param != null && param.size() > 0) {
			Map row;
			String chgPK;
			String id;
			String type;
			String oldNum;
			Object newNum;
			String description;
			for (int i = 0; i < param.size(); i++) {
				row = (Map) param.get(i);
				chgPK = (String) row.get("chgPK");
				id = (String) row.get("id");
				type = (String) row.get("type");
				oldNum = (String) row.get("oldNum");
				newNum = row.get("newNum");
				description = (String) row.get("description");
				if (type.equals("�վ�")) {
					if (newNum instanceof String) {
						_updateReceipt(ctx, id, null, null, (String) newNum, oldNum, description);
					} else if (newNum instanceof ChequeInfo) {
						ChequeInfo info = (ChequeInfo) newNum;
						_updateReceipt(ctx, id, null, info.getId().toString(),	info.getNumber(), oldNum, description);
						writtenOffCheque(ctx, info.getId().toString());
					}
				} else if (type.equals("��Ʊ")) {
					if (newNum instanceof String) {
						_updateInvoice(ctx, id, oldNum, null, (String) newNum,	description);
					} else if (newNum instanceof ChequeInfo) {
						ChequeInfo info = (ChequeInfo) newNum;
						_updateInvoice(ctx, id, oldNum,	info.getId().toString(), info.getNumber(),
								description);
						writtenOffCheque(ctx, info.getId().toString());
					}
					changeInvoiceCustomer(ctx, chgPK, id);
				}
			}
		}
	}

	/**
	 * ͨ���˷����򻻷�ȡ��Ʊ����Ϣ
	 */
	protected List _getChequeForRoom(Context ctx, String sourceID,
			String sourceType) throws BOSException {
		List lst = new ArrayList();
		Map data;

		// ȡ�վ�����
		StringBuffer sql = new StringBuffer();
		sql.append(" select DISTINCT fr.Fid as id , '�վ�' as type , ");
		sql.append(" fr.FReceiptNumber as number , fr.FAmount as amount ");
		if (sourceType.equals("�˷�")) {
			sql.append(" from T_SHE_QuitRoom as cr ");
			sql.append(" left join T_SHE_Purchase as pc on pc.FID = cr.FPurchaseID ");
		} else if (sourceType.equals("����")) {
			sql.append(" from T_SHE_ChangeRoom as cr ");
			sql.append(" left join T_SHE_Purchase as pc on pc.FID = cr.FOldPurchaseID ");
		}
		sql.append(" left join T_BDC_FDCReceivingBill as fr on fr.FPurchaseObjID = pc.FID ");
		sql.append(" where cr.FID = '").append(sourceID).append("' ");
		sql.append(" and fr.FReceiptState = '1'");
		sql.append(" and fr.Fid is not null ");
		// ȡ��Ʊ����
		sql.append(" union all ");
		sql.append(" select DISTINCT iv.Fid as id , '��Ʊ' as type , ");
		sql.append(" iv.FNumber as number , iv.FAmount as amount ");
		if (sourceType.equals("�˷�")) {
			sql.append(" from T_SHE_QuitRoom as cr ");
			sql.append(" left join T_SHE_Purchase as pc on pc.FID = cr.FPurchaseID ");
		} else if (sourceType.equals("����")) {
			sql.append(" from T_SHE_ChangeRoom as cr ");
			sql.append(" left join T_SHE_Purchase as pc on pc.FID = cr.FOldPurchaseID ");
		}
		sql.append(" left join T_BDC_FDCReceivingBill as fr on fr.FPurchaseObjID = pc.FID ");
		sql.append(" left join T_SHE_Invoice as iv on iv.FID = fr.FInvoiceID ");
		sql.append(" where iv.Fid is not null ");
		sql.append(" and iv.FNumber is not null ");
		sql.append(" and cr.FID = '").append(sourceID).append("' ");

		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		try {
			String id;
			String type;
			String number;
			BigDecimal amount;
			while (rs.next()) {
				id = rs.getString("id");
				type = rs.getString("type");
				number = rs.getString("number");
				amount = rs.getBigDecimal("amount");

				// ���ڷ�Ʊû��״̬���ƣ��ж�������²�����¼�ǻ��շ�Ʊ���ͣ����ӹ��ü�¼
				if ("��Ʊ".equals(type)) {
					EntityViewInfo view = new EntityViewInfo();
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("id");
					sic.add("head");
					sic.add("recordType");
					view.setSelector(sic);
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("head", id));
					view.setFilter(filter);
					SorterItemInfo sort = new SorterItemInfo("operatingDate");
					sort.setSortType(SortType.DESCEND);
					view.getSorter().add(sort);
					FDCReceiveBillRecordCollection rec = FDCReceiveBillRecordFactory
							.getLocalInstance(ctx)
							.getFDCReceiveBillRecordCollection(view);
					if (rec != null && rec.size() > 0) {
						FDCReceiveBillRecordInfo info = rec.get(0);
						if (info != null
								&& info.getRecordType().getValue().equals(
										RecordTypeEnum.RETAKEINVOICE_VALUE)) {
							continue;
						}
					}
				}

				data = new HashMap();
				data.put("id", id);
				data.put("type", type);
				data.put("number", number);
				data.put("amount", amount);
				lst.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}

	/**
	 * �˷��򻻷�ʱ����Ʊ��
	 */
	protected void _retakeChequeForRoom(Context ctx, List param)
			throws BOSException {
		if (param != null && param.size() > 0) {
			Map row;
			String sourceID;
			String id;
			String type;
			String number;
			String description;
			for (int i = 0; i < param.size(); i++) {
				row = (Map) param.get(i);
				sourceID = (String) row.get("sourceID");
				id = (String) row.get("id");
				type = (String) row.get("type");
				number = (String) row.get("number");
				description = (String) row.get("description");
				if (type.equals("�վ�")) {
					_retackReceipt(ctx, id, null, number, description,null);
				} else if (type.equals("��Ʊ")) {
					_retackInvoice(ctx, id, number, description);
				}
			}
		}
	}

	/**
	 * ���շ�Ʊ ��ʱ�Ȳ��Է�Ʊ��������ֻ���Ӳ�����¼�����·�Ʊ���¸��汾������ �����Ϊ���շ�Ʊ�������տ���վ�״̬
	 */
	protected void _retackInvoice(Context ctx, String pk, String number,
			String description) throws BOSException {
		// ���·�Ʊ
		if (pk == null) {
			return;
		}
		StringBuffer sql = new StringBuffer();
		// Object[] params = new Object[] { pk };
		// sql.append(" update T_SHE_Invoice set ");
		// sql.append(" FNumber = null ");
		// sql.append(" where fid = ? ");
		// DbUtil.execute(ctx, sql.toString(), params);

		// �����տ��ɾ�����������˴˷�Ʊ�ĵط�
		sql = new StringBuffer();
		sql.append(" update T_BDC_FDCReceivingBill set FInvoiceID = null");
		// sql
		// .append(" ,FReceiptState = (CASE FReceiptNumber WHEN null THEN 0 ELSE 1 END) ");
		sql.append(" where FInvoiceID = '").append(pk).append("' ");
		DbUtil.execute(ctx, sql.toString());

		// ����Ʊ�ݷ�Ʊ
		sql = new StringBuffer();
		sql
				.append(" update T_SHE_Cheque set FWrittenOfferID = null,FWrittenOffTime = null,FStatus = 2 ");
		sql.append(" where FID in (select FChequeID from T_SHE_Invoice ");
		sql.append(" where FID = '").append(pk).append(
				"' and FChequeID is not null ) ");
		DbUtil.execute(ctx, sql.toString());

		// ���Ӳ�����¼
		FDCReceiveBillRecordInfo record = new FDCReceiveBillRecordInfo();
		// ����ͷ && ����
		record.setHead(pk);
		record.setHeadType(1);
		// �������� && ����
		record.setRecordType(RecordTypeEnum.RetakeInvoice);
		record.setContent("���շ�Ʊ" + number);
		// ������
		record.setOperatingUser(ContextUtil.getCurrentUserInfo(ctx));
		// ��������
		record.setOperatingDate(new Date());
		// ��ע
		record.setDescription(description);

		// ������¼
		try {
			FDCReceiveBillRecordFactory.getLocalInstance(ctx).addnew(record);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����տ��Ʊ 
	 */
	protected void _clearInvoice(Context ctx, String pk, boolean isClearAll)
			throws BOSException {
		if (pk == null) {
			return;
		}
		StringBuffer sql = new StringBuffer();
		if (isClearAll) {
			sql.append(" select iv.FNumber as number , fr.FID as pk ");
			sql.append(" from T_BDC_FDCReceivingBill as fr ");
			sql.append(" left join T_SHE_Invoice as iv on iv.FID = fr.FInvoiceID ");
			sql.append(" where fr.FInvoiceID = '").append(pk).append("' ");
			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
			try {
				String number;
				String id;
				while (rs.next()) {
					number = rs.getString("number");
					id = rs.getString("pk");
					_updateRecord(ctx, 0, id, RecordTypeEnum.ClearInvoice,"�����Ʊ" + number, null);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			sql.append(" select iv.FNumber as number , fr.FID as pk ");
			sql.append(" from T_BDC_FDCReceivingBill as fr ");
			sql.append(" left join T_SHE_Invoice as iv on iv.FID = fr.FInvoiceID ");
			sql.append(" where fr.FID = '").append(pk).append("' ");
			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
			try {
				String number;
				String id;
				while (rs.next()) {
					number = rs.getString("number");
					id = rs.getString("pk");
					_updateRecord(ctx, 0, id, RecordTypeEnum.ClearInvoice,	"�����Ʊ" + number, null);
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * ���ݷ���IDȡ�������Ϲ��������ͻ�
	 */
	protected IObjectValue _getCustomerByRoom(Context ctx, String roomID)
			throws BOSException {
		if (roomID == null) {
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select Cust.fid id,Cust.FNumber number,Cust.FName_l2 name ");
		sql.append(" from t_bdc_FDCReceivingBill fdcBill ");
		sql.append(" inner join t_bdc_RevFDCCustomerEntry fdcCust on fdcbill.fid = fdcCust.fheadId ");
		sql.append(" inner join T_SHE_FDCCustomer as Cust on fdcCust.FFdcCustomerID = Cust.Fid ");
		sql.append(" where fdcbill.froomId = '"+roomID+"' ");
		sql.append(" and fdcbill.fbillStatus != '"+RevBillStatusEnum.SAVE_VALUE+"' and fdcbill.frevBillType = '"+RevBillTypeEnum.GATHERING_VALUE+"' ");
		sql.append(" order by fdcBill.fcreateTime desc,fdcCust.fseq ");		
		
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		try {
			if(rs.next()) {
				String id = rs.getString("id");
				String number = rs.getString("number");
				String name = rs.getString("name");
				FDCCustomerInfo customer = new FDCCustomerInfo();
				customer.setId(BOSUuid.read(id));
				customer.setNumber(number);
				customer.setName(name);
				return customer;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���ݸ��������·�Ʊ�ͻ�Ϊ�¿ͻ���Ϣ�е����ͻ�
	 * 
	 * @param chgID
	 */
	private void changeInvoiceCustomer(Context ctx, String chgID, String invID) {
		if (chgID != null) {
			StringBuffer sql = new StringBuffer();
			sql.append(" select fc.fid as customerID ");
			sql.append(" from T_SHE_pchCustomer as pc ");
			sql
					.append(" left join T_SHE_PChCusomerEntry as pe on pe.FHeadID = pc.FID ");
			sql
					.append(" left join T_SHE_FDCCustomer as fc on fc.FID = pe.FCustomerID ");
			sql.append(" where pc.FID = '").append(chgID).append("' ");
			sql.append(" and pe.FSeq = 0 ");
			IRowSet rs;
			try {
				rs = DbUtil.executeQuery(ctx, sql.toString());
				while (rs.next()) {
					String customerID = rs.getString("customerID");
					sql = new StringBuffer();
					sql.append(" update T_SHE_Invoice set FCustomerID = '")
							.append(customerID).append("' where Fid = '")
							.append(invID).append("' ");
					DbUtil.execute(ctx, sql.toString());
				}
			} catch (BOSException e1) {
				e1.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �Ʊ��
	 * 
	 * @param ctx
	 * @param chequeID
	 */
	private void writtenOffCheque(Context ctx, String chequeID) {
		if (chequeID != null) {
			ChequeInfo cheque = new ChequeInfo();
			cheque.setId(BOSUuid.read(chequeID));
			// ״̬
			cheque.setStatus(ChequeStatusEnum.WrittenOff);
			// ���
			cheque.setWrittenOffer(ContextUtil.getCurrentUserInfo(ctx));
			// �����
			cheque.setWrittenOffTime(new Timestamp(new Date().getTime()));
			// ������

			// ��������

			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("status");
			sels.add("writtenOffer");
			sels.add("writtenOffTime");
			try {
				ChequeFactory.getLocalInstance(ctx).updatePartial(cheque, sels);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * ���������������վ�
	 * @author pu_zhang  2010-11-15
	 * @param paramMap<key,value>  key��retakeReceivingBillidSet, receivingBillInfo   value��Set��FDCReceivingBillInfo
	 */
	protected void _retackReceiptBatch(Context ctx, Map paramMap, String fdcPK, String receiptNum, String description) throws BOSException {
		
		// �����տ
		if (paramMap==null) {
			return;
		}
		if(paramMap.get("retakeReceivingBillidSet")==null){
			return;
		}
		Set idSet = (Set)paramMap.get("retakeReceivingBillidSet");
		Object[]  idArray = idSet.toArray();
		String inStr = getInString(idArray);
		// ����Ʊ���վ�
		StringBuffer sql = new StringBuffer();
		sql.append(" update T_SHE_Cheque set FWrittenOfferID = null,FWrittenOffTime = null,FStatus = 2 ");
		sql.append(" where FID in (select FReceiptID from T_BDC_FDCReceivingBill ");
		sql.append(" where FReceiptID is not null ");
		sql.append(" and FID in ( ");
		if("".equals(inStr)){
			sql.append("'null'");
		}else{
			sql.append(inStr);
		}
		sql.append(" ) ");
		sql.append(" ) ");
		DbUtil.execute(ctx, sql.toString());
		
		//����Ʊ�ݹ���
		StringBuffer sqll = new StringBuffer();
		sqll.append(" update T_SHE_Invoice set FDate = null,FUserID = null ");
		sqll.append(" where FChequeID in (select FReceiptID from T_BDC_FDCReceivingBill ");
		sqll.append(" where FReceiptID is not null ");
		sqll.append(" and FID in ( ");
		if("".equals(inStr)){
			sqll.append("'null'");
		}else{
			sqll.append(inStr);
		}
		sqll.append(" ) ");
		sqll.append(" ) ");
		DbUtil.execute(ctx, sqll.toString());
		
		//���շ�ͳһƱ�ݹ����Ʊ��
		if(paramMap.get("receivingBillInfo")!=null && paramMap.get("receivingBillInfo") instanceof FDCReceivingBillInfo){
			FDCReceivingBillInfo receivInfo = (FDCReceivingBillInfo)paramMap.get("receivingBillInfo");
			if(receivInfo.getReceiptNumber()!=null && receivInfo.getRoom().getId()!=null ){
				StringBuffer sqlByFNumber = new StringBuffer();
				sqlByFNumber.append(" update T_SHE_Invoice set FDate = null ");
				sqlByFNumber.append(" where fnumber = '" +receivInfo.getReceiptNumber() +"' ");
				sqlByFNumber.append(" and froomid ='"+String.valueOf(receivInfo.getRoom().getId())+"' ");
				DbUtil.execute(ctx, sqlByFNumber.toString());
			}
		}
		
		//����Ʊ�ݹ���
		StringBuffer sqlRevInfo = new StringBuffer();
		sqlRevInfo.append(" update t_bdc_FDCReceivingBill set freceiptid = null,freceiptNumber=null, freceiptState='2' ");
		sqlRevInfo.append(" where 1=1 ");
		sqlRevInfo.append(" and FID in ( ");
		if("".equals(inStr)){
			sqlRevInfo.append("'null'");
		}else{
			sqlRevInfo.append(inStr);
		}
		sqlRevInfo.append(" ) ");
		DbUtil.execute(ctx,sqlRevInfo.toString());
		
		try {
			// ���Ӳ�����¼
			FDCReceiveBillRecordInfo record = new FDCReceiveBillRecordInfo();
			// ����ͷ && ����
			record.setHeadType(0);
			// �������� && ����
			record.setRecordType(RecordTypeEnum.RetakeReceipt);
			record.setContent("�����վ�" + receiptNum);
			// ������
			record.setOperatingUser(ContextUtil.getCurrentUserInfo(ctx));
			// ��������
			record.setOperatingDate(new Date());
			// ��ע
			record.setDescription(description);
			// ������¼
			for(int i = 0; i < idArray.length; i++){
				record.setHead(idArray[i].toString());
				FDCReceiveBillRecordFactory.getLocalInstance(ctx).addnew(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getInString(Object[] array) {
		StringBuffer sb = new StringBuffer("");
		
		for(int i = 0; i < array.length; i++){
			sb.append("'"+array[i]+"',");
		}
		String  reStr= sb.toString();
		if(!"".equals(reStr)){
			reStr = reStr.substring(0,reStr.lastIndexOf(","));
		}
		return reStr;
	}
}