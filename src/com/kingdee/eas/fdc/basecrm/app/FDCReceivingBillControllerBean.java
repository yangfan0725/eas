package com.kingdee.eas.fdc.basecrm.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActGroupDetailCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASAppException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum;
import com.kingdee.eas.fdc.sellhouse.RecordTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fi.cas.AssItemsForCashPayInfo;
import com.kingdee.eas.fi.cas.AssItemsForCashRecInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.CasRecPayBillTypeEnum;
import com.kingdee.eas.fi.cas.IPaymentBillType;
import com.kingdee.eas.fi.cas.IReceivingBillType;
import com.kingdee.eas.fi.cas.PaymentBillEntryCollection;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillTypeCollection;
import com.kingdee.eas.fi.cas.PaymentBillTypeFactory;
import com.kingdee.eas.fi.cas.PaymentBillTypeInfo;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillEntryCollection;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.ReceivingBillTypeCollection;
import com.kingdee.eas.fi.cas.ReceivingBillTypeFactory;
import com.kingdee.eas.fi.cas.ReceivingBillTypeInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.fi.cas.app.ArApRecPayServerHelper;
import com.kingdee.eas.fm.nt.NTNumberFormat;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

public class FDCReceivingBillControllerBean extends AbstractFDCReceivingBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basecrm.app.FDCReceivingBillControllerBean");
    
    
    ArApRecPayServerHelper arapHelper = new ArApRecPayServerHelper();
	
	/**
	 * �տ�ύ��ͨ�ò���,�����տ������������Ӧ����ϸ���з�д
	 * ��Ҫ���� Ӧ���տ�;ֱ���տ�;Ԥ���տ�;�˿�;Ӧ��ת��;ֱ��ת��;Ԥ��ת��;����;
	 * �տ�ģʽ��
	 * > Ӧ���տһ�������տ���ϸ,�����տ���ϸ��ʵ�ս��
	 * > ֱ���տ����û���տ���ϸ,���û���տ���ϸ,������һ��������ϸ,���������տ���ϸ��ʵ�ս��
	 * > Ԥ���տ����û���տ���ϸ,���û���տ���ϸ,������һ��������ϸ,���������տ���ϸ��ʵ�ս��
	 * �˿һ�������տ���ϸ,�����տ���ϸ�����˽��
	 * > Ӧ���˿��Ӧ����ϸ���˿�
	 * > Ӧ���˿û��Ӧ����ϸ���˿�,�縺�Ĳ����
	 * ת��ģʽ��ת����һ�����տ���ϸ,����ת���տ���ϸ����ת���
	 * > Ӧ��ת�ת����տ���ϸ���տ�ģʽ�ķ�д��ͬ
	 * > ֱ��ת�ת����տ���ϸ���տ�ģʽ�ķ�д��ͬ
	 * > Ԥ��ת�ת����տ���ϸ���տ�ģʽ�ķ�д��ͬ
	 * ������һ�������տ���ϸ,�����տ���ϸ���ѵ����
	 * 
	 * ע�⣺
	 * 1.(�տ��ת��)�����ύǰû���տ���ϸ,��Ҫ�ύʱͬ�������տ���ϸ��,���뽫��װ���տ���ϸ����set���տ��¼��,�Ҹ��տ���ϸ��idҪʹ��createBosID����һ��
	 * 2.�տ���ϸ����Ҫ���ύ�տǰ��װ����,��Ҫ�ر�ע����ֱ�ʶ�ֶ��Ƿ���ֵ
	 * 
	 * TODO ���տ�޸�,ɾ����֧��
	 * �տ�޸ģ�
	 * �жϴ�����տ�Ƿ�IDΪ����ȷ���Ƿ�Ϊ�޸�;ɾ������дһ������
	 * @param oldFdcRev 
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	private String doRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldRev, IRevHandle handle) throws EASBizException, BOSException {
		RevBillTypeEnum revBillType = rev.getRevBillType();
		
		if(oldRev == null){
			doRevOfAddNew(ctx, handle, revBillType, rev);
		}else{
			doRevOfEdit(ctx, handle, revBillType, rev, oldRev);
		}
		
		return _submit(ctx, rev).toString();
	}
	
	/**
	 * ����������״̬
	 */
    public void setAudittingStatus(Context ctx, BOSUuid billId)
		throws BOSException, EASBizException {
	    super.setAudittingStatus(ctx, billId);
	    FDCReceivingBillInfo rev = new FDCReceivingBillInfo();
		rev.setId(billId);
		rev.setBillStatus(RevBillStatusEnum.AUDITING);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("billStatus");
		this.updatePartial(ctx, rev, sels);
    }
    
    /**
	 * �����ύ״̬
	 */
    public void setSubmitStatus(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	super.setSubmitStatus(ctx, billId);
    	FDCReceivingBillInfo rev = new FDCReceivingBillInfo();
		rev.setId(billId);
		rev.setBillStatus(RevBillStatusEnum.SUBMIT);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("billStatus");
		this.updatePartial(ctx, rev, sels);
    }
    
	//���������տ���ύ
	//�·�ʽ,�Է�¼����Ԥ����,���޸� TODO
	private void doRevOfAddNew1(Context ctx, IRevHandle handle, RevBillTypeEnum revBillType, FDCReceivingBillInfo rev) throws BOSException, EASBizException {
		Map date = parseRevEntrys(rev);
		
		FDCReceivingBillEntryCollection revEntrys = rev.getEntries();
		if(RevBillTypeEnum.gathering.equals(revBillType)){
			Set revListDeses = date.keySet();
			for(Iterator itor = revListDeses.iterator(); itor.hasNext(); ){
				RevListDes revListDes = (RevListDes) itor.next();
				
				RevListTypeEnum revListType = revListDes.revListType;
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revListDes.revListId, revListDes.revListInfo, revListType, true);
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revListDes.amount);
				
				revListInfo.setActRevAmount(srcActRevAmount.add(curRevAmount));
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));//����ֶ���ҲҪ�ýӿڱ�¶��ȥ��������ʹ�տ���ϸ���ϱ�,����տ���ϸ��ʵ�����Ҳ�Ǻ��б�Ҫ�İ�,�����ýӿ�ȥ�����е��鷳
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}
		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal hasRefundmentAmount = getBigDecimal(revListInfo.getHasRefundmentAmount());
				BigDecimal curRefundmentAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setHasRefundmentAmount(hasRefundmentAmount.add(curRefundmentAmount.abs()));
				
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getHasRefundmentFieldName(revListType));
				
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}
		}else if(RevBillTypeEnum.transfer.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setActRevAmount(srcActRevAmount.add(curRevAmount));
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
				
				FeeMoneyTypeEnum feeMoneyType = revListInfo.getFeeMoneyType();
				
				//ת��,��Ҫ���Ǳ���岿�ֵļ�¼
				TransferSourceEntryCollection srcEntrys = revEntry.getSourceEntries();
				for(int j=0; j<srcEntrys.size(); j++){
					TransferSourceEntryInfo srcEntry = srcEntrys.get(j);
					String tmpRevListId = srcEntry.getFromRevListId();
					RevListTypeEnum tmpRevListType = srcEntry.getFromRevListType();
					
					SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
					
					IRevListInfo tmpRevListInfo = handle.getRevListInfoObj(ctx, tmpRevListId, tmpRevListType);
					BigDecimal curTransferredAmount = getBigDecimal(srcEntry.getAmount());
					
					//������Ҫ������ͨת���������ת��,��ͨת�д��ת���,������ת�д�ѻ������
					if(FeeMoneyTypeEnum.Fee.equals(feeMoneyType)){
						BigDecimal hasToFeeAmount = getBigDecimal(tmpRevListInfo.getHasToFeeAmount());
						tmpRevListInfo.setHasToFeeAmount(hasToFeeAmount.add(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasToFeeFieldName(revListType));
					}else{
						BigDecimal hasTransferredAmount = getBigDecimal(tmpRevListInfo.getHasTransferredAmount());
						tmpRevListInfo.setHasTransferredAmount(hasTransferredAmount.add(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasTransferredFieldName(revListType));
					}
					
					ICoreBase tmpBizInterface = handle.getRevListBizInterface(ctx, tmpRevListType);
					tmpBizInterface.updatePartial((CoreBaseInfo) tmpRevListInfo, tmpUpdateSels);
				}				
			}
		}else if(RevBillTypeEnum.adjust.equals(revBillType)){
			//�������˿�����
			//TODO
		}
	}
	
	//���������տ���ύ
	private void doRevOfAddNew(Context ctx, IRevHandle handle, RevBillTypeEnum revBillType, FDCReceivingBillInfo rev) throws BOSException, EASBizException {
		FDCReceivingBillEntryCollection revEntrys = rev.getEntries();
		if(RevBillTypeEnum.gathering.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				//���յ��տ���ϸ�������з�д
				if(revEntry.getOrgUnit() != null){
					continue;
				}
				
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setActRevAmount(srcActRevAmount.add(curRevAmount));
				
				revListInfo.setActRevDate(rev.getBizDate());
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));//����ֶ���ҲҪ�ýӿڱ�¶��ȥ��������ʹ�տ���ϸ���ϱ�,����տ���ϸ��ʵ�����Ҳ�Ǻ��б�Ҫ�İ�,�����ýӿ�ȥ�����е��鷳
				updateSels.add(handle.getActRevDateFieldName(revListType));
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}
		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
			//TODO Ӧ���տ��Ӧ���˿�,��д�������������
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal hasRefundmentAmount = getBigDecimal(revListInfo.getHasRefundmentAmount());
				BigDecimal curRefundmentAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setHasRefundmentAmount(hasRefundmentAmount.add(curRefundmentAmount.abs()));
				
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getHasRefundmentFieldName(revListType));
				
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}
		}else if(RevBillTypeEnum.transfer.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setActRevAmount(srcActRevAmount.add(curRevAmount));
				revListInfo.setActRevDate(rev.getBizDate());
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));
				updateSels.add(handle.getActRevDateFieldName(revListType));
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
				
				FeeMoneyTypeEnum feeMoneyType = revListInfo.getFeeMoneyType();
				
				//ת��,��Ҫ���Ǳ���岿�ֵļ�¼
				TransferSourceEntryCollection srcEntrys = revEntry.getSourceEntries();
				for(int j=0; j<srcEntrys.size(); j++){
					TransferSourceEntryInfo srcEntry = srcEntrys.get(j);
					String tmpRevListId = srcEntry.getFromRevListId();
					RevListTypeEnum tmpRevListType = srcEntry.getFromRevListType();
					
					SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
					
					IRevListInfo tmpRevListInfo = handle.getRevListInfoObj(ctx, tmpRevListId, tmpRevListType);
					BigDecimal curTransferredAmount = getBigDecimal(srcEntry.getAmount());
					
					//������Ҫ������ͨת���������ת��,��ͨת�д��ת���,������ת�д�ѻ������
					if(FeeMoneyTypeEnum.Fee.equals(feeMoneyType)){
						BigDecimal hasToFeeAmount = getBigDecimal(tmpRevListInfo.getHasToFeeAmount());
						tmpRevListInfo.setHasToFeeAmount(hasToFeeAmount.add(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasToFeeFieldName(revListType));
					}else{
						BigDecimal hasTransferredAmount = getBigDecimal(tmpRevListInfo.getHasTransferredAmount());
						tmpRevListInfo.setHasTransferredAmount(hasTransferredAmount.add(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasTransferredFieldName(revListType));
					}
					
					ICoreBase tmpBizInterface = handle.getRevListBizInterface(ctx, tmpRevListType);
					tmpBizInterface.updatePartial((CoreBaseInfo) tmpRevListInfo, tmpUpdateSels);
				}				
			}
		}else if(RevBillTypeEnum.adjust.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				SelectorItemCollection updateSels = new SelectorItemCollection();
				if(curRevAmount.compareTo(new BigDecimal(0))<0) {	//�տ� �� ת�� �ĵ���
					revListInfo.setActRevAmount(srcActRevAmount.add(curRevAmount));
					revListInfo.setActRevDate(rev.getBizDate());				
					
					updateSels.add(handle.getActRevFieldName(revListType));
					updateSels.add(handle.getActRevDateFieldName(revListType));
				}else if(curRevAmount.compareTo(new BigDecimal(0))>0) {	//�˿� �ĵ���
					BigDecimal hasRefundmentAmount = getBigDecimal(revListInfo.getHasRefundmentAmount());
					BigDecimal curRefundmentAmount = getBigDecimal(revEntry.getRevAmount().negate());
					
					revListInfo.setHasRefundmentAmount(hasRefundmentAmount.add(curRefundmentAmount));
					updateSels.add(handle.getHasRefundmentFieldName(revListType));
				}
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
				
				FeeMoneyTypeEnum feeMoneyType = revListInfo.getFeeMoneyType();
				
				//ת��,��Ҫ���Ǳ���岿�ֵļ�¼
				TransferSourceEntryCollection srcEntrys = revEntry.getSourceEntries();
				for(int j=0; j<srcEntrys.size(); j++){
					TransferSourceEntryInfo srcEntry = srcEntrys.get(j);
					String tmpRevListId = srcEntry.getFromRevListId();
					RevListTypeEnum tmpRevListType = srcEntry.getFromRevListType();
					
					SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
					
					IRevListInfo tmpRevListInfo = handle.getRevListInfoObj(ctx, tmpRevListId, tmpRevListType);
					BigDecimal curTransferredAmount = getBigDecimal(srcEntry.getAmount());
					
					//������Ҫ������ͨת���������ת��,��ͨת�д��ת���,������ת�д�ѻ������
					if(FeeMoneyTypeEnum.Fee.equals(feeMoneyType)){
						BigDecimal hasToFeeAmount = getBigDecimal(tmpRevListInfo.getHasToFeeAmount());
						tmpRevListInfo.setHasToFeeAmount(hasToFeeAmount.add(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasToFeeFieldName(revListType));
					}else{
						BigDecimal hasTransferredAmount = getBigDecimal(tmpRevListInfo.getHasTransferredAmount());
						tmpRevListInfo.setHasTransferredAmount(hasTransferredAmount.add(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasTransferredFieldName(revListType));
					}
					
					ICoreBase tmpBizInterface = handle.getRevListBizInterface(ctx, tmpRevListType);
					tmpBizInterface.updatePartial((CoreBaseInfo) tmpRevListInfo, tmpUpdateSels);
				}				
			}
		
		}
	}
	
	/**
	 * �տ�޸�ʱ���տ���ϸ��д
	 * */
	private void doRevOfEdit(Context ctx, IRevHandle handle, RevBillTypeEnum revBillType, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldRev) throws BOSException, EASBizException {
		Map revDate = parseRevEntrys(rev);
		Map oldRevDate = parseRevEntrys(oldRev);
		
		Set revKeys = revDate.keySet();
		Set oldRevKeys = oldRevDate.keySet();
		for(Iterator itor = revKeys.iterator(); itor.hasNext(); ){
			RevListDes revListDes = (RevListDes) itor.next();
			
			RevListDes oldRevListDes = null;
			for(Iterator oldItor = oldRevKeys.iterator(); oldItor.hasNext(); ){
				RevListDes tmpDes = (RevListDes) oldItor.next();
				if(revListDes.equals(tmpDes)){
					oldRevListDes = tmpDes;
					break;
				}
			}
			if(oldRevListDes == null){
				throw new EASBizException(new NumericExceptionSubItem("100","�޸Ļ���֧����ɾ�տ���ϸ!"));
			}
			BigDecimal newAmount = getBigDecimal(revListDes.amount);
			BigDecimal oldAmount = getBigDecimal(oldRevListDes.amount);
			
			BigDecimal subAmount = newAmount.subtract(oldAmount);
			if(subAmount.compareTo(FDCHelper.ZERO) == 0){
				continue;
			}
			
			RevListTypeEnum revListType = revListDes.revListType;
			
			ICoreBase bizI = handle.getRevListBizInterface(ctx, revListType);
			IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revListDes.revListId, revListDes.revListInfo, revListType, false);
			//�޸�ʱ��Ҫ���տ�ϵ�ҵ�����ڷ�д����ϸ�ϵ�ʵ��������ȥ
			revListInfo.setActRevDate(rev.getBizDate());
			if(RevBillTypeEnum.gathering.equals(revBillType)){
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				
				revListInfo.setActRevAmount(srcActRevAmount.add(subAmount));
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));
				updateSels.add(handle.getActRevDateFieldName(revListType));
				
				
				//�����ֱ�յ��޸�,��Ҫ�޸Ķ�Ӧ��Ӧ�ս��
				if(RevMoneyTypeEnum.DirectRev.equals(revListInfo.getRevMoneyType())){
					revListInfo.setAppAmount(revListInfo.getActRevAmount());
					updateSels.add(handle.getAppRevFieldName(revListType));
					updateSels.add(handle.getActRevDateFieldName(revListType));
				}
				
				bizI.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}else if(RevBillTypeEnum.refundment.equals(revBillType)){
				BigDecimal hasRefundmentAmount = getBigDecimal(revListInfo.getHasRefundmentAmount());
				
				revListInfo.setHasRefundmentAmount(hasRefundmentAmount.subtract(subAmount)); //����Ӧ�ü�����Ϊ�˿��Ǹ���
				
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getHasRefundmentFieldName(revListType));
				updateSels.add(handle.getActRevDateFieldName(revListType));
				
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}else if(RevBillTypeEnum.transfer.equals(revBillType)){
				throw new EASBizException(new NumericExceptionSubItem("101","ת���ݲ�֧���޸�!"));
			}else if(RevBillTypeEnum.adjust.equals(revBillType)){
				throw new EASBizException(new NumericExceptionSubItem("101","��������֧���޸�!"));
			}
		}
	}
	
	/**
	 * ɾ��ʱ����ϸ�ķ�д
	 * */
	private void doRevOfDelete(Context ctx, IRevHandle handle, RevBillTypeEnum revBillType, FDCReceivingBillEntryCollection revEntrys) throws BOSException, EASBizException {
		if(RevBillTypeEnum.gathering.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				//����Ǵ��յ��տ���ϸ����������ϸ�ķ�д
				if(revEntry.getOrgUnit() != null){
					continue;
				}
				
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), null, revListType, false);
				
				if(revListInfo == null){
//					throw new EASBizException(new NumericExceptionSubItem("102","������!"));
					logger.error("�����ݣ�:" + revEntry.getRevListId());
					continue;
				}
				
				//��ϸ�ϵ�ʣ�����Ѳ����Ի�����
				if(revListInfo.getAllRemainAmount().compareTo(revEntry.getRevAmount())<0){
					String exceptionDes = "��Ӧ��"+revListType.getAlias()+ "������ת����˿����֧��ԭ�տ��ɾ����";
					throw new EASBizException(new NumericExceptionSubItem("100", exceptionDes ));
				}
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setActRevAmount(srcActRevAmount.subtract(curRevAmount));
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));//����ֶ���ҲҪ�ýӿڱ�¶��ȥ��������ʹ�տ���ϸ���ϱ�,����տ���ϸ��ʵ�����Ҳ�Ǻ��б�Ҫ�İ�,�����ýӿ�ȥ�����е��鷳
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}
		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), null, revListType, false);
				
				if(revListInfo == null){
//					throw new EASBizException(new NumericExceptionSubItem("102","������!"));
					logger.error("�����ݣ�:" + revEntry.getRevListId());
					continue;
				}
				
				BigDecimal hasRefundmentAmount = getBigDecimal(revListInfo.getHasRefundmentAmount());
				BigDecimal curRefundmentAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setHasRefundmentAmount(hasRefundmentAmount.subtract(curRefundmentAmount.abs()));
				
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getHasRefundmentFieldName(revListType));
				
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}
		}else if(RevBillTypeEnum.transfer.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				//��ϸ�ϵ�ʣ�����Ѳ����Ի�����
				if(revListInfo.getAllRemainAmount().compareTo(revEntry.getRevAmount())<0){
					String exceptionDes = "��Ӧ��"+revListType.getAlias()+ "������ת����˿����֧��ԭ�տ��ɾ����";
					throw new EASBizException(new NumericExceptionSubItem("100", exceptionDes ));
				}
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setActRevAmount(srcActRevAmount.subtract(curRevAmount));
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
				
				FeeMoneyTypeEnum feeMoneyType = revListInfo.getFeeMoneyType();
				
				//ת��,��Ҫ���Ǳ���岿�ֵļ�¼
				TransferSourceEntryCollection srcEntrys = revEntry.getSourceEntries();
				for(int j=0; j<srcEntrys.size(); j++){
					TransferSourceEntryInfo srcEntry = srcEntrys.get(j);
					String tmpRevListId = srcEntry.getFromRevListId();
					RevListTypeEnum tmpRevListType = srcEntry.getFromRevListType();
					
					SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
					
					IRevListInfo tmpRevListInfo = handle.getRevListInfoObj(ctx, tmpRevListId, tmpRevListType);
					BigDecimal curTransferredAmount = getBigDecimal(srcEntry.getAmount());
					
					//������Ҫ������ͨת���������ת��,��ͨת�д��ת���,������ת�д�ѻ������
					if(FeeMoneyTypeEnum.Fee.equals(feeMoneyType)){
						BigDecimal hasToFeeAmount = getBigDecimal(tmpRevListInfo.getHasToFeeAmount());
						tmpRevListInfo.setHasToFeeAmount(hasToFeeAmount.subtract(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasToFeeFieldName(revListType));
					}else{
						BigDecimal hasTransferredAmount = getBigDecimal(tmpRevListInfo.getHasTransferredAmount());
						tmpRevListInfo.setHasTransferredAmount(hasTransferredAmount.subtract(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasTransferredFieldName(revListType));
					}
					
					ICoreBase tmpBizInterface = handle.getRevListBizInterface(ctx, tmpRevListType);
					tmpBizInterface.updatePartial((CoreBaseInfo) tmpRevListInfo, tmpUpdateSels);
				}				
			}
		}else if(RevBillTypeEnum.adjust.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				SelectorItemCollection updateSels = new SelectorItemCollection();
				if(curRevAmount.compareTo(new BigDecimal(0))<0) {	//�տ� �� ת�� �ĵ���
					revListInfo.setActRevAmount(srcActRevAmount.subtract(curRevAmount));					
					updateSels.add(handle.getActRevFieldName(revListType));
				}else if(curRevAmount.compareTo(new BigDecimal(0))>0) {	//�˿� �ĵ���
					BigDecimal hasRefundmentAmount = getBigDecimal(revListInfo.getHasRefundmentAmount());
					BigDecimal curRefundmentAmount = getBigDecimal(revEntry.getRevAmount().negate());
					
					revListInfo.setHasRefundmentAmount(hasRefundmentAmount.subtract(curRefundmentAmount));
					updateSels.add(handle.getHasRefundmentFieldName(revListType));
				}
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
				
				FeeMoneyTypeEnum feeMoneyType = revListInfo.getFeeMoneyType();
				
				//ת��,��Ҫ���Ǳ���岿�ֵļ�¼
				TransferSourceEntryCollection srcEntrys = revEntry.getSourceEntries();
				for(int j=0; j<srcEntrys.size(); j++){
					TransferSourceEntryInfo srcEntry = srcEntrys.get(j);
					String tmpRevListId = srcEntry.getFromRevListId();
					RevListTypeEnum tmpRevListType = srcEntry.getFromRevListType();
					
					SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
					
					IRevListInfo tmpRevListInfo = handle.getRevListInfoObj(ctx, tmpRevListId, tmpRevListType);
					BigDecimal curTransferredAmount = getBigDecimal(srcEntry.getAmount());
					
					//������Ҫ������ͨת���������ת��,��ͨת�д��ת���,������ת�д�ѻ������
					if(FeeMoneyTypeEnum.Fee.equals(feeMoneyType)){
						BigDecimal hasToFeeAmount = getBigDecimal(tmpRevListInfo.getHasToFeeAmount());
						tmpRevListInfo.setHasToFeeAmount(hasToFeeAmount.subtract(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasToFeeFieldName(revListType));
					}else{
						BigDecimal hasTransferredAmount = getBigDecimal(tmpRevListInfo.getHasTransferredAmount());
						tmpRevListInfo.setHasTransferredAmount(hasTransferredAmount.subtract(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasTransferredFieldName(revListType));
					}
					
					ICoreBase tmpBizInterface = handle.getRevListBizInterface(ctx, tmpRevListType);
					tmpBizInterface.updatePartial((CoreBaseInfo) tmpRevListInfo, tmpUpdateSels);
				}				
			}
		
		
			
			
		}
	}
	
	private Map parseRevEntrys(FDCReceivingBillInfo rev) {
		if(rev == null){
			return null;
		}
		Map map = new LinkedHashMap();
		FDCReceivingBillEntryCollection revEntrys = rev.getEntries();
		for(int i=0; i<revEntrys.size(); i++){
			FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
			//���յ��տ���ϸ�������з�д
			if(revEntry.getOrgUnit() != null){
				continue;
			}
			
			String revListId = revEntry.getRevListId();
			RevListTypeEnum revListType = revEntry.getRevListType();
			BigDecimal amount = revEntry.getRevAmount();
			
			List list = (List) map.get(new RevListDes(revListId));
			
			if(list == null){
				list = new ArrayList();
				list.add(revEntry);
				
				RevListDes revListDes = new RevListDes();
				revListDes.revListId = revListId;
				revListDes.revListType = revListType;
				revListDes.revListInfo = revEntry.getRevListInfo();
				revListDes.amount = revEntry.getRevAmount();
				
				map.put(revListDes, list);
			}else{
				Set keys = map.keySet();
				
				for(Iterator itor = keys.iterator(); itor.hasNext(); ){
					RevListDes des = (RevListDes) itor.next();
					if(revListId.equals(des.revListId)){
						des.amount = des.amount.add(amount);
						break;
					}
				}
				list.add(revEntry);
			}
		}
		
		return map;
	}

	/**
	 * ���»�ȡһ���տ���ϸ��ֵ,��Ϊ����տ���ϸ�ǿͻ��˴�������,��Ҫ�Կ��е�����Ϊ׼
	 * ���ڿ���û�������տ���ϸ��¼��,��������ϸ����������
	 * */
	private IRevListInfo regetRevListInfo(Context ctx, IRevHandle handle, String revListId, IRevListInfo revListInfo, RevListTypeEnum revListType, boolean isNeedAddNew) throws EASBizException, BOSException {
		IRevListInfo tmp = handle.getRevListInfoObj(ctx, revListId, revListType);
		//TODO ����������Ҫ���Ӹ�����жϣ���ֹ���ڵ������ݻᵼ����������صĴ���
		if(isNeedAddNew  &&  tmp == null  &&  revListInfo != null){
			addnewRevListInfo(revListInfo, revListInfo.getRevMoneyType(), handle.getRevListBizInterface(ctx, revListType));
			return revListInfo;
		}
		return tmp;
	}
	
	/**
	 * ���»�ȡһ���տ���ϸ��ֵ,��Ϊ����տ���ϸ�ǿͻ��˴�������,��Ҫ�Կ��е�����Ϊ׼
	 * ���ڿ���û�������տ���ϸ��¼��,��������ϸ����������
	 * @deprecated
	 * */
//	private IRevListInfo regetRevListInfo(Context ctx, IRevHandle handle, IRevListInfo revListInfo, RevListTypeEnum revListType) throws EASBizException, BOSException {
//		String revListId = null;
//		if(revListInfo.getId() != null){
//			revListId = revListInfo.getId().toString();
//		}else{
//			logger.error("�߼�����,����ֱ���տ�ʱ�������տ���ϸ��,Ҳ��Ҫcreateһ��ID");
//		}
//		
//		return this.regetRevListInfo(ctx, handle, revListId, revListInfo, revListType);
//	}
	
	/**
	 * �����Ҫͬ�������տ���ϸ��,���տ���ϸ�������
	 * @param revListInfo �տ���ϸ����
	 * @param revMoneyType �����ʾ,����Ԥ��,ֱ��,Ӧ��
	 * @param bizInterface ��Ӧ�տ���ϸ��ҵ��ӿ�
	 * */
	private void addnewRevListInfo(IRevListInfo revListInfo, RevMoneyTypeEnum revMoneyType, ICoreBase bizInterface) throws BOSException, EASBizException {
		//��Ҫ�տ��ʱ��������տ���ϸ��,ֻ�п�����Ԥ���տ��ֱ���տ�,�������˳��У��һ��
/*		if(!RevMoneyTypeEnum.PreRev.equals(revMoneyType)  &&  
				!RevMoneyTypeEnum.DirectRev.equals(revMoneyType)){
			throw new BOSException("ϵͳ�߼������.");
		}*/		
		bizInterface.addnew((CoreBaseInfo) revListInfo);
		
		//��ID�ڿͻ��˾�Ӧ���Ѿ�������
//			revEntry.setRevListId(resId.toString());
//			revListInfo.setId(BOSUuid.read(resId.toString()));
	}

	private BigDecimal getBigDecimal(BigDecimal big){
		return CRMHelper.getBigDecimal(big);		
	}
	
	protected String _submitRev(Context ctx, IObjectValue rev, String handleClazzName) throws BOSException, EASBizException {
		FDCReceivingBillInfo fdcRev = (FDCReceivingBillInfo) rev;
		IRevHandle handle = null;
		
		if(fdcRev.getReceipt()!=null ||!StringUtils.isEmpty(fdcRev.getReceiptNumber())){
			fdcRev.setReceiptState(ReceiptStatusEnum.HasMake);
		}
		if(!StringUtils.isEmpty(handleClazzName)){
			try {
				handle = (IRevHandle) Class.forName(handleClazzName).newInstance();
			} catch (InstantiationException e) {
				throw new EASBizException(new NumericExceptionSubItem("101","ϵͳ����"));
			} catch (IllegalAccessException e) {
				throw new EASBizException(new NumericExceptionSubItem("101","ϵͳ����"));
			} catch (ClassNotFoundException e) {
				throw new EASBizException(new NumericExceptionSubItem("101","ϵͳ����"));
			}
		}
		
		if(handle == null){
			throw new EASBizException(new NumericExceptionSubItem("101","ϵͳ����"));
		}
		
		FDCReceivingBillInfo oldFdcRev = null;
		if(fdcRev != null  &&  fdcRev.getId() != null){
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("entries.*");
			oldFdcRev = getFDCReceivingBillInfo(ctx, new ObjectUuidPK(fdcRev.getId().toString()), sels);
		}
//		//����վݺ���F7 ��Ҫ��д�����վ����ڵ�Ʊ�ݹ�����������վ�״̬  xin_wang 2010.09.22
//		if(fdcRev.getReceipt()!=null){
//			SelectorItemCollection sels = new SelectorItemCollection();
//			sels.add("status");
//			ChequeInfo ci =fdcRev.getReceipt();
//			ci.setStatus(ChequeStatusEnum.WrittenOff);
//			ChequeFactory.getLocalInstance(ctx).updatePartial(ci, sels);
//		}
		/* 
		 * fdcRev��Ϊ�գ�oldFdcRevΪ�գ���ʾ������
		   fdcRev��Ϊ�գ�oldFdcRev��Ϊ�գ���ʾ�޸ģ�
		 */
		handle.doBeforeRev(ctx, fdcRev, oldFdcRev);
		String id = doRev(ctx, fdcRev, oldFdcRev, handle);
		handle.doAfterRev(ctx, fdcRev, oldFdcRev);
	    
		return id;
	}
	
	public DAPTransformResult generateVoucher(Context ctx, IObjectPK[] sourceBillPkList, IObjectPK botMappingPK, SelectorItemCollection botpSelectors) throws BOSException, EASBizException {
		botpSelectors = getBOTPSelectors();
		
		return super.generateVoucher(ctx, sourceBillPkList, botMappingPK, botpSelectors);
	}
	
	
	protected SelectorItemCollection getBOTPSelectors() {
        SelectorItemCollection sels = new SelectorItemCollection();
		
		sels.add("entries.settlementType.*");
		sels.add("entries.revAccount.*");
		sels.add("entries.oppAccount.*");
		sels.add("entries.moneyDefine.*");
		sels.add("entries.revAccountBank.*");
		sels.add("entries.room.*");
		sels.add("entries.room.building.*");
		sels.add("entries.sourceEntries.*");

		sels.add("purchaseObj.*");
		sels.add("sincerityObj.*");
		sels.add("obligateObj.*");
		sels.add("tenancyObj.*");
		sels.add("fdcCustomers.*");
		sels.add("customer.*");
		sels.add("room.*");
		sels.add("sellProject.*");
		sels.add("currency.*");
		sels.add("company.*");
		sels.add("*");		
        
		return sels;
	}
	
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo) throws BOSException, EASBizException {
	}	
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
//		FDCReceivingBillInfo rev = new FDCReceivingBillInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("room.*");
		selector.add("entries.*");
		selector.add("customer.*");
		selector.add("fdcCustomers.*");
		selector.add("receipt.*");
		selector.add("*");
		FDCReceivingBillInfo rev =FDCReceivingBillFactory.getLocalInstance(ctx).getFDCReceivingBillInfo(new ObjectUuidPK(billId), selector);
//		rev.setId(billId);
		
		if(rev.getReceipt()!=null){//�տ�������վݵ���Ҫ��д xin_wang 2010.9.25
			
			ChequeInfo cheque =  new ChequeInfo();
			cheque.setId(rev.getReceipt().getId());
			StringBuffer sb = new StringBuffer();
			sb.append(rev.getRoom().getNumber());
			sb.append("������");
//			
			cheque.setResume(sb.toString());
			cheque.setStatus(ChequeStatusEnum.WrittenOff);
			BigDecimal invoiceAmount = new BigDecimal("0");
	
			//�տ��� ���տ�ϵĽ��
			invoiceAmount = rev.getAmount();
			
			Format u = NTNumberFormat.getInstance("rmb");
			cheque.setCapitalization(u.format(invoiceAmount));    		
			cheque.setAmount(invoiceAmount);
			
			String payerStrs = "";
			Date lastPayDate = null;

				if(rev.getCustomer()!=null) {
						payerStrs = rev.getCustomer().getName();
				}
				if(rev.getBizDate()!=null) {
					if(lastPayDate==null || rev.getBizDate().after(lastPayDate))
						lastPayDate = rev.getBizDate();	
				}
//			}    		
			//������ݴ��ڱ��ֶ���󳤶�300,���н�ȡ
//			String des = writtenOfCheque.toString();
//			if(des.length() > 300) 	des = des.substring(0, 300);
//			cheque.setDescription(des);
			
			cheque.setWrittenOffer(ContextUtil.getCurrentUserInfo(ctx));
			cheque.setWrittenOffTime(new Timestamp(new Date().getTime()));
			cheque.setPayer(payerStrs.replaceFirst(",", ""));
			if(lastPayDate==null) {
				cheque.setPayTime(FDCCommonServerHelper.getServerTimeStamp());
			}else{
				cheque.setPayTime(new Timestamp(lastPayDate.getTime()));
			}
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("resume");
			sels.add("status");
			sels.add("amount");
			sels.add("description");
			sels.add("capitalization");    		
			sels.add("writtenOffer");
			sels.add("writtenOffTime");
			sels.add("payer");
			sels.add("payTime");
			ChequeFactory.getLocalInstance(ctx).updatePartial(cheque, sels);	//���±���Ӧ���վ���Ϣ  
			//��Ʊ�ݹ���û�д�Ʊ�ݵľ�����һ������invoice�����˾�UPdate  
			InvoiceInfo ii = new InvoiceInfo();
//			ii.setId()
			ii.setCheque(cheque);
			ii.setAmount(invoiceAmount);
			//fdcCustomers ���ѿͻ���customer �տ�ͻ�  ����д�Ǹ��ͻ����վ����� ���ѿͻ�д���վ��ϣ�
			FDCCustomerCollection custColl = FDCCustomerFactory.getLocalInstance(ctx)
			.getFDCCustomerCollection("select name,number where sysCustomer.id='"+rev.getCustomer().getId()+"'");
			if(custColl.size()>0)
				ii.setCustomer(custColl.get(0));
			ii.setDate(new Date());
			ii.setRoom(rev.getRoom());
			ii.setUser(ContextUtil.getCurrentUserInfo(ctx));
			ii.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
			ii.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			ii.setLastUpdateUser(SysContext.getSysContext().getCurrentUserInfo());
			ii.setNumber(rev.getReceipt().getNumber());
			FilterInfo filter = new FilterInfo();
			ii.setChequeType(ChequeTypeEnum.receipt);
			filter.getFilterItems().add(new FilterItemInfo("cheque.id",rev.getReceipt().getId()));
			SelectorItemCollection coll  = new SelectorItemCollection();
//			coll.add("amount");
//			coll.add("date");
			coll.add("user.*");
			coll.add("room.*");
//			coll.add("CU");
			coll.add("cheque.*");
			coll.add("customer.*");
			coll.add("*");
			IReceiptInvoiceFacade facade = null;
			try {
				facade = ReceiptInvoiceFacadeFactory.getLocalInstance(ctx);
			} catch (BOSException e) {
//				ExceptionHandler.handle(e);
			}
			if(InvoiceFactory.getLocalInstance(ctx).exists(filter)){
				InvoiceInfo invoice =InvoiceFactory.getLocalInstance(ctx).getInvoiceInfo("select id where cheque.id ='"+rev.getReceipt().getId()+"'" );
				ii.setId(invoice.getId());
				InvoiceFactory.getLocalInstance(ctx).updatePartial(ii, coll);
				//update Ʊ�ݵĲ�����¼
				
			}else{
				ii.setId(BOSUuid.create(ii.getBOSType()));
				InvoiceFactory.getLocalInstance(ctx).addnew(ii);
				//Ʊ���ϵĲ�����¼
				StringBuffer content = new StringBuffer();
				content.append("���տ:");
				content.append(rev.getNumber());
				content.append(" ���վ�");
				RecordTypeEnum recordType = RecordTypeEnum.MakeOutInvoice;
				if (recordType != null) {
					try {
					facade.updateRecord(1, ii.getId().toString(),	recordType, content.toString(), null);
					} catch (BOSException e) {
//						ExceptionHandler.handle(e);
					}
				}	
			}
		}
		rev.setBillStatus(RevBillStatusEnum.AUDITED);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("billStatus");
		this.updatePartial(ctx, rev, sels);
		
		ArrayList list=new ArrayList();
		list.add(billId.toString());
		this.createCashBill(ctx, list);
	}
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		
		if(ReceivingBillFactory.getLocalInstance(ctx).exists("select id from where sourceBillId='"+billId+"'")
				||PaymentBillFactory.getLocalInstance(ctx).exists("select id from where sourceBillId='"+billId+"'")){
			throw new EASBizException(new NumericExceptionSubItem("101","�����ɳ����ո��������ɾ�������ո�����ٽ��з�����������"));
		}
		FDCReceivingBillInfo rev = new FDCReceivingBillInfo();
		rev.setId(billId);
		rev.setBillStatus(RevBillStatusEnum.SUBMIT);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("billStatus");
		this.updatePartial(ctx, rev, sels);
	}

	protected void _delete(Context ctx, BOSUuid fdcReceivingID,
			String handleClazzName) throws BOSException ,EASBizException{
		//
		IRevHandle handle = null;
		if(!StringUtils.isEmpty(handleClazzName)){
			try {
				handle = (IRevHandle) Class.forName(handleClazzName).newInstance();
			} catch (InstantiationException e) {
				throw new EASBizException(new NumericExceptionSubItem("101","ϵͳ����"));
			} catch (IllegalAccessException e) {
				throw new EASBizException(new NumericExceptionSubItem("101","ϵͳ����"));
			} catch (ClassNotFoundException e) {
				throw new EASBizException(new NumericExceptionSubItem("101","ϵͳ����"));
			}
		}
		
		if(handle == null){
			throw new EASBizException(new NumericExceptionSubItem("101","ϵͳ����"));
		}
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("entries.*");
		sels.add("entries.sourceEntries.*");
		FDCReceivingBillInfo rev = getFDCReceivingBillInfo(ctx, new ObjectUuidPK(fdcReceivingID), sels);
		
		doRevOfDelete(ctx, handle, rev.getRevBillType(), rev.getEntries());
		
		//ɾ����Ҫ���Ƕ�ҵ��ϵͳ�ķ�д,��Ҫʵ��
		handle.doOfDelRev(ctx, rev);
		
		this._delete(ctx, new ObjectUuidPK(fdcReceivingID));
	}

	protected void _receive(Context ctx, ArrayList recidList)
			throws BOSException, EASBizException {
		for(int i=0;i<recidList.size();i++)
		{
			FDCReceivingBillInfo rev = new FDCReceivingBillInfo();
			rev.setId(BOSUuid.read((String)recidList.get(i)));
			rev.setBillStatus(RevBillStatusEnum.RECED);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("billStatus");
			this.updatePartial(ctx, rev, sels);	
			FDCReceivingBillFactory.getLocalInstance(ctx).receive(rev.getId());
		}			
	}
	

	protected void _canceReceive(Context ctx, ArrayList recidList)
			throws BOSException, EASBizException {
		for(int i=0;i<recidList.size();i++)
		{
			FDCReceivingBillInfo rev = new FDCReceivingBillInfo();
			rev.setId(BOSUuid.read((String)recidList.get(i)));
			rev.setBillStatus(RevBillStatusEnum.AUDITED);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("billStatus");
			this.updatePartial(ctx, rev, sels);	
		}
	}

	//���ɵ�����
	protected Map _adjust(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		FDCReceivingBillInfo revInfo = this.getFDCReceivingBillInfo(ctx, "select *,entries.*,fdcCustomers.*,entries.sourceEntries.* " +
				" where id ='"+billId.toString()+"'");
		if(revInfo.getRevBillType().equals(RevBillTypeEnum.adjust))
			throw new EASBizException(new NumericExceptionSubItem("100","������Ե�������������"));
		
		if(!revInfo.getBillStatus().equals(RevBillStatusEnum.AUDITED) && 
				!revInfo.getBillStatus().equals(RevBillStatusEnum.RECED)) {
			throw new EASBizException(new NumericExceptionSubItem("100","ֻ�����������Ѹ���״̬�ĵ�������������"));
		}
		
		//��ѡ����տ�������Ϲ��������ϣ���������е���
		if(revInfo.getRevBizType()!=null && revInfo.getRevBizType().equals(RevBizTypeEnum.purchase) && revInfo.getPurchaseObj()!=null) {
			if(PurchaseFactory.getLocalInstance(ctx).exists("where id='"+ revInfo.getPurchaseObj().getId() +"' " +
					"and (purchaseState = '"+ PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE +"' " +
							"or purchaseState = '"+ PurchaseStateEnum.QUITROOMBLANKOUT_VALUE +"' " +
									"or purchaseState = '"+ PurchaseStateEnum.ADJUSTBLANKOUT_VALUE +"' ) ")) {
				throw new EASBizException(new NumericExceptionSubItem("100","�������Ϲ��������ϣ���������е�����"));
			}
		}
		
		FDCReceivingBillInfo adjustInfo = (FDCReceivingBillInfo)revInfo.clone();
		//���ñ���		
		ICodingRuleManager iCodingRuleManager = null;
		if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		
		OrgUnitInfo orgUnit = null;
		if(ctx!=null) {
			orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		}else {
			orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
			if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		}
		
		String retNumber = iCodingRuleManager.getNumber(adjustInfo, orgUnit.getId().toString());
		if(retNumber==null || retNumber.trim().length()==0 )
			throw new EASBizException(new NumericExceptionSubItem("100","δ���ñ������ �������Զ�����ת���"));
		
		adjustInfo.setId(null);
		adjustInfo.setNumber(retNumber);
		adjustInfo.setRevBillType(RevBillTypeEnum.adjust);
		adjustInfo.setBizDate(new Date());
		adjustInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		adjustInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
		adjustInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		adjustInfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		adjustInfo.setAmount(adjustInfo.getAmount().negate());
		adjustInfo.setOriginalAmount(adjustInfo.getOriginalAmount().negate());
		adjustInfo.setBillStatus(RevBillStatusEnum.SUBMIT);
		adjustInfo.setState(FDCBillStateEnum.SUBMITTED);
		adjustInfo.setFiVouchered(false);	//add by zhiyuan_tang R101103-407   �����ɵĵ���������δ����ƾ֤��
		adjustInfo.setDescription("���"+revInfo.getRevBillType().getAlias()+"-"+revInfo.getRevBizType().getAlias()+"�����"+revInfo.getNumber()+"�ĵ���");
		
		
		FDCReceivingBillEntryCollection adjustEntryColl = adjustInfo.getEntries();;
		for(int i=0;i<adjustEntryColl.size();i++) {
			FDCReceivingBillEntryInfo adjustEntryInfo = adjustEntryColl.get(i);
			adjustEntryInfo.setId(BOSUuid.create(adjustEntryInfo.getBOSType()));
			adjustEntryInfo.setHead(adjustInfo);
			adjustEntryInfo.setRevAmount(adjustEntryInfo.getRevAmount().negate());
			TransferSourceEntryCollection trasfEntryColl = adjustEntryInfo.getSourceEntries();
			for(int j=0;j<trasfEntryColl.size();j++){
				TransferSourceEntryInfo trasfEntryInfo = trasfEntryColl.get(j);
				trasfEntryInfo.setId(null);
				trasfEntryInfo.setHeadEntry(adjustEntryInfo);
				trasfEntryInfo.setAmount(trasfEntryInfo.getAmount().negate());
			}
		}
		
		RevFDCCustomerEntryCollection fdcCustColl =  adjustInfo.getFdcCustomers();
		for(int i=0;i<fdcCustColl.size();i++) {
			RevFDCCustomerEntryInfo fdcCustInfo = fdcCustColl.get(i);
			fdcCustInfo.setId(null);
			fdcCustInfo.setHead(adjustInfo);
		}
		
		String handleClazzName = null;
		handleClazzName  = CRMHelper.getHandleClassNameByRevBizType(adjustInfo.getRevBizType());
		
		//���տ��ת��ĵ���������ʱ��Ҫ��֤���������ϸ��ʣ�����С��0
		if(revInfo.getRevBillType().equals(RevBillTypeEnum.gathering) || revInfo.getRevBillType().equals(RevBillTypeEnum.transfer))	{
			try {
				IRevHandle revHandle = (IRevHandle) Class.forName(handleClazzName).newInstance();
				
				for(int i=0;i<adjustEntryColl.size();i++) {
					FDCReceivingBillEntryInfo adjustEntryInfo = adjustEntryColl.get(i);
					IRevListInfo revListInfo = revHandle.getRevListInfoObj(ctx, adjustEntryInfo.getRevListId(), adjustEntryInfo.getRevListType());
					BigDecimal retLeftAmount = revListInfo.getAllRemainAmount().add(adjustEntryInfo.getRevAmount());
					if(retLeftAmount.compareTo(new BigDecimal(0))<0) {
						throw new EASBizException(new NumericExceptionSubItem("100","��ֹ��������Ϊ������ᵼ��'"+adjustEntryInfo.getRevListType().getAlias()+"'��ʣ����Ϊ��ֵ��" ));
					}
				}
			} catch (InstantiationException e) {
				throw new EASBizException(new NumericExceptionSubItem("100","�쳣����InstantiationException��" ));
			} catch (IllegalAccessException e) {
				throw new EASBizException(new NumericExceptionSubItem("100","�쳣����IllegalAccessException��" ));
			} catch (ClassNotFoundException e) {
				throw new EASBizException(new NumericExceptionSubItem("100","�쳣����ClassNotFoundException��" ));
			}

		}		
		
		
		String id = this._submitRev(ctx, adjustInfo ,handleClazzName);
		Map reMap = new HashMap();
		reMap.put("adjustInfo", adjustInfo);
		return reMap;
	}

	/**
	 * ������¥�տ���ɳ����տ
	 * by renliang
	 */
	protected void _createCashBill(Context ctx, ArrayList idList, boolean isCreate) throws BOSException, EASAppException {
		
		try {
			createCashBill(ctx,idList);
		} catch (EASBizException e) {
			logger.error(e.getMessage()+"���ɳ����ո������");
		}
		
	}		
	
	/**
	 * ���ɳ����տ
	 * @param ctx
	 * @param idList
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void createCashBill(Context ctx, ArrayList idList) throws BOSException, EASBizException{
		
		IFDCReceivingBill receivingBill= FDCReceivingBillFactory.getLocalInstance(ctx);
		Set  idSet = new HashSet(); 
		for (int i = 0; i < idList.size(); i++){
			String id = idList.get(i).toString();
			idSet.add(id);
		}
		
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		evi.setFilter(filterInfo);

		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("*"));
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("company.*"));
		coll.add(new SelectorItemInfo("bizDate"));
		coll.add(new SelectorItemInfo("exchangeRate"));
		coll.add(new SelectorItemInfo("currency.*"));
		coll.add(new SelectorItemInfo("amount"));
		coll.add(new SelectorItemInfo("originalAmount"));
		
		coll.add(new SelectorItemInfo("entries.*"));
//		coll.add(new SelectorItemInfo("entries.settlementType.*"));
//		coll.add(new SelectorItemInfo("entries.revAccount.*"));
		coll.add(new SelectorItemInfo("entries.revAmount"));
		coll.add(new SelectorItemInfo("entries.oppAccount.*"));
		coll.add(new SelectorItemInfo("entries.moneyDefine.*"));
//		coll.add(new SelectorItemInfo("entries.moneyDefine.revBillType.*"));
		coll.add(new SelectorItemInfo("customer.id"));
		coll.add(new SelectorItemInfo("customer.number"));
		coll.add(new SelectorItemInfo("customer.name"));
		
		coll.add(new SelectorItemInfo("revAccount.*"));
		coll.add(new SelectorItemInfo("accountBank.*"));
		coll.add(new SelectorItemInfo("settlementType.*"));
		coll.add(new SelectorItemInfo("settlementNumber"));
		coll.add(new SelectorItemInfo("bank.*"));
		coll.add(new SelectorItemInfo("room.name"));
		evi.setSelector(coll);

		
		FDCReceivingBillCollection collection = receivingBill.getFDCReceivingBillCollection(evi);
		
		
		Set updateIdSet = new HashSet();
		
		
		UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
		CtrlUnitInfo cuInfo = ContextUtil.getCurrentCtrlUnit(ctx);

		IORMappingDAO iReceiving = ORMappingDAO.getInstance(new ReceivingBillInfo().getBOSType(), ctx, getConnection(ctx));
		IORMappingDAO iPay = ORMappingDAO.getInstance(new PaymentBillInfo().getBOSType(), ctx, getConnection(ctx));
		
		ReceivingBillInfo rev=null;
		PaymentBillInfo pay=null;
		RevBillTypeEnum type=null;
		
		SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("asstActGpDt.asstActType.*");
    	sels.add("asstActGpDt.*");
    	
		for (int i = 0; i < collection.size(); i++) {
			FDCReceivingBillCollection billColl = null;
			String id = collection.get(i).getId().toString();
			billColl=collection;
			if(billColl!=null && billColl.size()>0){
				for (int j = 0; j < billColl.size(); j++) {
					FDCReceivingBillInfo fdcReceivingBillInfo = billColl.get(j);
					FDCReceivingBillEntryCollection billEntry = fdcReceivingBillInfo.getEntries();
					type=fdcReceivingBillInfo.getRevBillType();
					if(fdcReceivingBillInfo.getRevBillType().equals(RevBillTypeEnum.refundment)){
						PaymentBillTypeInfo payemtnBillTypeInfo = getPaymentBillType(ctx);
						
						PaymentBillInfo  paymentBillInfo = new PaymentBillInfo();
						//��˾
						paymentBillInfo.setCompany(fdcReceivingBillInfo.getCompany());
						//ҵ������
						paymentBillInfo.setBizDate(FDCDateHelper.getDayBegin(fdcReceivingBillInfo.getBizDate()));
						//����
						paymentBillInfo.setExchangeRate(fdcReceivingBillInfo.getExchangeRate());
						//�ұ�
						paymentBillInfo.setCurrency(fdcReceivingBillInfo.getCurrency());
						
						//�տ���
						paymentBillInfo.setActPayAmt(fdcReceivingBillInfo.getAmount().abs());
						
						paymentBillInfo.setCreator(userInfo);
						paymentBillInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						paymentBillInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
						paymentBillInfo.setLastUpdateUser(userInfo);
						paymentBillInfo.setCU(cuInfo);
						
					
						/**
						 * ���ñ���		
						 */
						ICodingRuleManager iCodingRuleManager = null;
						if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
						else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
						
						OrgUnitInfo orgUnit = null;
						if(ctx!=null) {
							orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
							if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
						}else {
							orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
							if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
						}
						
						String retNumber = iCodingRuleManager.getNumber(paymentBillInfo, orgUnit.getId().toString());
						if(retNumber!=null && !"".equals(retNumber)){
							paymentBillInfo.setNumber(retNumber);
						}
						
						/**
						 * ����տ���
						 */
						CustomerInfo custInfo  = fdcReceivingBillInfo.getCustomer();
						if(custInfo!=null){
							paymentBillInfo.setPayeeID(custInfo.getId().toString());
							paymentBillInfo.setPayeeNumber(custInfo.getNumber());
							paymentBillInfo.setPayeeName(custInfo.getName());
						}
						
						paymentBillInfo.setActPayLocAmt(fdcReceivingBillInfo.getOriginalAmount());
						
						//�տ��Ŀ
						paymentBillInfo.setPayerAccount(fdcReceivingBillInfo.getRevAccount());	
						
						// ����Ŀ�Ϸ���
						try {
							arapHelper.verifyAccountView(ctx, fdcReceivingBillInfo.getRevAccount(), paymentBillInfo
									.getCurrency(), paymentBillInfo.getCompany());
						} catch (EASBizException e) {
							logger.error(e.getMessage()+"��Ŀ����ָ���ұ���㣬��ѡ�������ұ�");
							throw new BOSException("��Ŀ����ָ���ұ���㣬��ѡ�������ұ�");
						}
						paymentBillInfo.setPayerAccountBank(fdcReceivingBillInfo.getAccountBank());	
						
						//���㷽ʽ
						paymentBillInfo.setSettlementType(fdcReceivingBillInfo.getSettlementType());
						//�����
						paymentBillInfo.setSettlementNumber(fdcReceivingBillInfo.getSettlementNumber());
						paymentBillInfo.setPayerBank(fdcReceivingBillInfo.getBank());
						
						paymentBillInfo.setPayBillType(payemtnBillTypeInfo);
						/**
						 * ���÷ǿ��ֶ�
						 */
						paymentBillInfo.setPaymentBillType(CasRecPayBillTypeEnum.RealType);
						paymentBillInfo.setIsExchanged(false);
						paymentBillInfo.setIsInitializeBill(false);
						paymentBillInfo.setIsImport(false);
						paymentBillInfo.setFiVouchered(false);
						paymentBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
						paymentBillInfo.setIsAppointVoucher(false);
						paymentBillInfo.setIsCoopBuild(false);
						paymentBillInfo.setSourceType(SourceTypeEnum.CASH);
						//����״̬
						paymentBillInfo.setBillStatus(BillStatusEnum.SAVE);
						//ԭʼ����id
						paymentBillInfo.setSourceBillId(id);
						
						Set mdName=new HashSet();
						for (int k = 0; k < billEntry.size(); k++) {
							/**
							 * �����Ƿ�¼����
							 */
							PaymentBillEntryCollection payBillEntry = paymentBillInfo.getEntries();
							PaymentBillEntryInfo payBillEntryInfo = new PaymentBillEntryInfo();
							//�տ���
							payBillEntryInfo.setActualAmt(billEntry.get(k).getRevAmount().abs());
							//�Է���Ŀ
							if(billEntry.get(k).getOppAccount()!=null){
								payBillEntryInfo.setOppAccount(billEntry.get(k).getOppAccount());
								
								if(payBillEntryInfo.getOppAccount().getCAA()!=null){
									AsstAccountInfo account=AsstAccountFactory.getLocalInstance(ctx).getAsstAccountInfo(new ObjectUuidPK(payBillEntryInfo.getOppAccount().getCAA().getId()),sels);
									AsstActGroupDetailCollection aacol=account.getAsstActGpDt();
									CRMHelper.sortCollection(aacol, "seq", true);
									for(int l=0;l<aacol.size();l++){
										AsstActTypeInfo asstActType=aacol.get(l).getAsstActType();
										AssItemsForCashPayInfo ass=new AssItemsForCashPayInfo();
										ass.setTableName(asstActType.getRealtionDataObject());
										ass.setMappingFileds(asstActType.getMappingFieldName());
										ass.setAsstActType(asstActType);
										ass.setIsSelected(false);
										ass.setSeq(aacol.get(l).getSeq());
										ass.setEntrySeq(i+1);
										
										if(asstActType.getRealtionDataObject().equals("T_BD_Customer")){
											if(custInfo!=null){
												ass.setFromID(custInfo.getId().toString());
												ass.setFromNumber(custInfo.getNumber());
												ass.setIsSelected(true);
											}
										}
										payBillEntryInfo.getAssItemsEntries().add(ass);
									}
								}
							}
							payBillEntryInfo.setExpenseType(ExpenseTypeFactory.getLocalInstance(ctx).getExpenseTypeCollection("select * from where number='888.01'").get(0));
							EntityViewInfo ev = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							ev.setFilter(filter);
							filter.getFilterItems().add(
									new FilterItemInfo("number", "999",
											CompareType.EQUALS));
							payBillEntryInfo.setCostCenter(CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitInfo(new ObjectUuidPK(fdcReceivingBillInfo.getCompany().getId())));
							payBillEntry.add(payBillEntryInfo);
							if(billEntry.get(k).getMoneyDefine()!=null)
								mdName.add(billEntry.get(k).getMoneyDefine().getName());
						}
						if(fdcReceivingBillInfo.getRoom()!=null){
							paymentBillInfo.setDescription(fdcReceivingBillInfo.getRoom().getName());
							Iterator<String> it = mdName.iterator();  
							while (it.hasNext()) {
								String str = it.next();
								paymentBillInfo.setDescription(paymentBillInfo.getDescription()+";"+str);
							}  
						}
						
						pay=paymentBillInfo;
						iPay.addNewBatch(paymentBillInfo);
					}else{
						ReceivingBillTypeInfo receivingBillTypeInfo = getReceivingBillType(ctx);
						
						ReceivingBillInfo  receivingBillInfo = new ReceivingBillInfo();
						//��˾
						receivingBillInfo.setCompany(fdcReceivingBillInfo.getCompany());
						//ҵ������
						receivingBillInfo.setBizDate(FDCDateHelper.getDayBegin(fdcReceivingBillInfo.getBizDate()));
						//����
						receivingBillInfo.setExchangeRate(fdcReceivingBillInfo.getExchangeRate());
						//�ұ�
						receivingBillInfo.setCurrency(fdcReceivingBillInfo.getCurrency());
						
						//�տ���
						receivingBillInfo.setActRecAmt(fdcReceivingBillInfo.getAmount());
						
						receivingBillInfo.setCreator(userInfo);
						receivingBillInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						receivingBillInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
						receivingBillInfo.setLastUpdateUser(userInfo);
						receivingBillInfo.setCU(cuInfo);
						
					
						/**
						 * ���ñ���		
						 */
						ICodingRuleManager iCodingRuleManager = null;
						if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
						else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
						
						OrgUnitInfo orgUnit = null;
						if(ctx!=null) {
							orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
							if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
						}else {
							orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
							if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
						}
						
						String retNumber = iCodingRuleManager.getNumber(receivingBillInfo, orgUnit.getId().toString());
						
						
						///if(retNumber==null || retNumber.trim().length()==0 )
							//throw new EASBizException(new NumericExceptionSubItem("100","δ���ñ������ �������Զ�����ת���"));
						
						if(retNumber!=null && !"".equals(retNumber)){
							receivingBillInfo.setNumber(retNumber);
						}
						
						/**
						 * ����տ���
						 */
						CustomerInfo custInfo  = fdcReceivingBillInfo.getCustomer();
						if(custInfo!=null){
							receivingBillInfo.setPayerID(custInfo.getId().toString());
							receivingBillInfo.setPayerNumber(custInfo.getNumber());
							receivingBillInfo.setPayerName(custInfo.getName());
						}
//						//�۱�λ��
//						BigDecimal actrecLocAmt = FDCHelper.ZERO;
//						BigDecimal ecchangeRate = FDCHelper.ONE;
						
//						if(fdcReceivingBillInfo.getExchangeRate()==null){
//							ecchangeRate = fdcReceivingBillInfo.getExchangeRate();
//						}
//						
//						actrecLocAmt  = FDCHelper.multiply(billEntry.get(k).getRevAmount(), ecchangeRate);
						
						receivingBillInfo.setActRecLocAmt(fdcReceivingBillInfo.getOriginalAmount());
						
						//�տ��Ŀ
							receivingBillInfo.setPayeeAccount(fdcReceivingBillInfo.getRevAccount());	
						
						// ����Ŀ�Ϸ���
						try {
							arapHelper.verifyAccountView(ctx, fdcReceivingBillInfo.getRevAccount(), receivingBillInfo
									.getCurrency(), receivingBillInfo.getCompany());
						} catch (EASBizException e) {
							logger.error(e.getMessage()+"��Ŀ����ָ���ұ���㣬��ѡ�������ұ�");
							throw new BOSException("��Ŀ����ָ���ұ���㣬��ѡ�������ұ�");
						}
						
						//�տ��˻�
//						if(billEntry.get(i).getRevAccountBank()!=null){
							receivingBillInfo.setPayeeAccountBank(fdcReceivingBillInfo.getAccountBank());	
//						}
						
						//���㷽ʽ
						receivingBillInfo.setSettlementType(fdcReceivingBillInfo.getSettlementType());
						//�����
						receivingBillInfo.setSettlementNumber(fdcReceivingBillInfo.getSettlementNumber());
						receivingBillInfo.setPayeeBank(fdcReceivingBillInfo.getBank());
						
//						MoneyDefineInfo moneyDefineInfo  = billEntry.get(k).getMoneyDefine();
						
//						/**
//						 * �������տ����͵�ת��
//						 */
//						if(moneyDefineInfo!=null){
//							//�տ�����
//							receivingBillInfo.setRecBillType(moneyDefineInfo.getRevBillType());
//						}else{
							//�տ�����
							receivingBillInfo.setRecBillType(receivingBillTypeInfo);
//						}
						/**
						 * ���÷ǿ��ֶ�
						 */
						receivingBillInfo.setReceivingBillType(CasRecPayBillTypeEnum.RealType);
						receivingBillInfo.setIsExchanged(false);
						receivingBillInfo.setIsInitializeBill(false);
						receivingBillInfo.setIsImport(false);
						receivingBillInfo.setFiVouchered(false);
						receivingBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
						receivingBillInfo.setIsAppointVoucher(false);
						receivingBillInfo.setIsCoopBuild(false);
						receivingBillInfo.setSourceType(SourceTypeEnum.CASH);
						//����״̬
						receivingBillInfo.setBillStatus(BillStatusEnum.SAVE);
						//ԭʼ����id
						receivingBillInfo.setSourceBillId(id);
						
						Set mdName=new HashSet();
						for (int k = 0; k < billEntry.size(); k++) {
							/**
							 * �����Ƿ�¼����
							 */
							ReceivingBillEntryCollection receBillEntry = receivingBillInfo.getEntries();
							ReceivingBillEntryInfo receBillEntryInfo = new ReceivingBillEntryInfo();
							//�տ���
							receBillEntryInfo.setActualAmt(billEntry.get(k).getRevAmount());
							//�Է���Ŀ
							if(billEntry.get(k).getOppAccount()!=null){
								receBillEntryInfo.setOppAccount(billEntry.get(k).getOppAccount());	
								
								if(receBillEntryInfo.getOppAccount().getCAA()!=null){
									AsstAccountInfo account=AsstAccountFactory.getLocalInstance(ctx).getAsstAccountInfo(new ObjectUuidPK(receBillEntryInfo.getOppAccount().getCAA().getId()),sels);
									AsstActGroupDetailCollection aacol=account.getAsstActGpDt();
									CRMHelper.sortCollection(aacol, "seq", true);
									for(int l=0;l<aacol.size();l++){
										AsstActTypeInfo asstActType=aacol.get(l).getAsstActType();
										AssItemsForCashRecInfo ass=new AssItemsForCashRecInfo();
										ass.setTableName(asstActType.getRealtionDataObject());
										ass.setMappingFileds(asstActType.getMappingFieldName());
										ass.setAsstActType(asstActType);
										ass.setIsSelected(false);
										ass.setSeq(aacol.get(l).getSeq());
										ass.setEntrySeq(i+1);
										
										if(asstActType.getRealtionDataObject().equals("T_BD_Customer")){
											if(custInfo!=null){
												ass.setFromID(custInfo.getId().toString());
												ass.setFromNumber(custInfo.getNumber());
												ass.setIsSelected(true);
											}
										}
										receBillEntryInfo.getAssItemsEntries().add(ass);
									}
								}
							}
							Map date=getDate(billEntry.get(k).getRevListId(),ctx);
							
							receBillEntry.add(receBillEntryInfo);
							if(billEntry.get(k).getMoneyDefine()!=null)
								mdName.add(billEntry.get(k).getMoneyDefine().getName()+date.get("startDate")+date.get("endDate"));
						}
						if(fdcReceivingBillInfo.getRoom()!=null){
							receivingBillInfo.setDescription(fdcReceivingBillInfo.getRoom().getName());
							Iterator<String> it = mdName.iterator();  
							while (it.hasNext()) {
								String str = it.next();
								receivingBillInfo.setDescription(receivingBillInfo.getDescription()+";"+str);
							}  
						}
						rev=receivingBillInfo;
						iReceiving.addNewBatch(receivingBillInfo);
					}
				}
			}
			updateIdSet.add(id);
		}
		
		/**
		 * �����Ƿ������ɳ����տ�ֶ�
		 */
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_BDC_FDCReceivingBill set FIsCreateBill=1 where ");
		builder.appendParam("fid", updateIdSet.toArray());
		
		iReceiving.executeBatch();
		
		if(type.equals(RevBillTypeEnum.refundment)){
			PaymentBillFactory.getLocalInstance(ctx).submit(pay);
			Set payId=new HashSet();
			payId.add(pay.getId().toString());
			PaymentBillFactory.getLocalInstance(ctx).audit(payId);
			PaymentBillFactory.getLocalInstance(ctx).pay(payId);
		}else{
			ReceivingBillFactory.getLocalInstance(ctx).submit(rev);
			HashMap hmParamIn = new HashMap();
			hmParamIn.put("ISSAVED", ContextUtil.getCurrentOrgUnit(ctx).getId().toString());

			HashMap hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);

			boolean isSaved=true;
			if (hmAllParam.get("ISSAVED") != null) {
				isSaved = Boolean.valueOf(
						hmAllParam.get("ISSAVED").toString())
						.booleanValue();
			}
			if(isSaved){
				Set revId=new HashSet();
				revId.add(rev.getId().toString());
				ReceivingBillFactory.getLocalInstance(ctx).audit(revId);
				ReceivingBillFactory.getLocalInstance(ctx).rec(revId);
			}
		}
		
		
		builder.execute();
	}
	public Map getDate(String id,Context ctx) throws BOSException, EASBizException {
    	Map map=new HashMap();
		if(BOSUuid.read(id).getType().toString().equals("31D11A7E")){
			TenancyRoomPayListEntryInfo info=TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryInfo(new ObjectUuidPK(id));
			map.put("startDate", info.getStartDate());
			map.put("endDate", info.getEndDate());
		}else{
			TenBillOtherPayInfo info=TenBillOtherPayFactory.getLocalInstance(ctx).getTenBillOtherPayInfo(new ObjectUuidPK(id));
			map.put("startDate", info.getStartDate());
			map.put("endDate", info.getEndDate());
		}
		return map;
	}
	/**
	 * �ж��Ƿ����ظ���ֵ������еĻ�
	 * �������ظ���ɾ����Ȼ��ɾ��
	 * @param collection
	 * @return
	 */
	private FDCReceivingBillCollection deleteReduplicateBillCollection(FDCReceivingBillCollection collection){
		
		for (int i = 0; i < collection.size(); i++) {
			FDCReceivingBillEntryCollection billEntryColl = collection.get(i).getEntries();
			List list  = new ArrayList();
			for (int j = 0; j < billEntryColl.size(); j++) {
				FDCReceivingBillEntryInfo firstBillEntryInfo  = billEntryColl.get(j);
				for (int k = billEntryColl.size()-1; k >= j+1; k--) {
					FDCReceivingBillEntryInfo secondBillEntryInfo  = billEntryColl.get(k);
					if(firstBillEntryInfo.getMoneyDefine().getId().equals(secondBillEntryInfo.getMoneyDefine().getId())){
						if(firstBillEntryInfo.getSettlementType().getId().equals(secondBillEntryInfo.getSettlementType().getId())){
							if(firstBillEntryInfo.getRevAccountBank()!=null && secondBillEntryInfo.getRevAccountBank()!=null){
								if(firstBillEntryInfo.getRevAccountBank().getId().equals(secondBillEntryInfo.getRevAccountBank().getId())
										   ){
												if(!list.contains(firstBillEntryInfo)){
													list.add(firstBillEntryInfo);
												}
												if(!list.contains(secondBillEntryInfo)){
												list.add(secondBillEntryInfo);
											}
											billEntryColl.remove(firstBillEntryInfo);
											billEntryColl.remove(secondBillEntryInfo);
										}
							}else if(firstBillEntryInfo.getRevAccountBank()==null && secondBillEntryInfo.getRevAccountBank()==null){
									if(!list.contains(firstBillEntryInfo)){
										list.add(firstBillEntryInfo);
									}
									if(!list.contains(secondBillEntryInfo)){
										list.add(secondBillEntryInfo);
									}
									
									billEntryColl.remove(secondBillEntryInfo);
							}
						}

					}
				}
			}
			if(list.size()>0){
				FDCReceivingBillEntryInfo billInfo = (FDCReceivingBillEntryInfo)list.get(0);
				BigDecimal firstAmount = FDCHelper.ZERO;
				for (int j = 1; j < list.size(); j++) {
					FDCReceivingBillEntryInfo info = (FDCReceivingBillEntryInfo)list.get(j);
					firstAmount = FDCHelper.add(firstAmount, info.getRevAmount());
				}
				billInfo.setRevAmount(billInfo.getRevAmount().add(firstAmount));
				billEntryColl.add(billInfo);
			}
		}
		return collection;
	}
	
	
	/**
	 * �õ�����ϵͳ��Ĭ�ϵ��տ�����
	 * @return
	 */
	private ReceivingBillTypeInfo getReceivingBillType(Context ctx){
		ReceivingBillTypeInfo typeInfo = null;
		ReceivingBillTypeCollection coll =null;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "999",
						CompareType.EQUALS));
		IReceivingBillType iReceivingBillType;
		try {
			iReceivingBillType = ReceivingBillTypeFactory
					.getLocalInstance(ctx);
			
			coll = iReceivingBillType.getReceivingBillTypeCollection(ev);
		} catch (BOSException e) {
			logger.error(e.getMessage()+"�õ�����ϵͳ��Ĭ�ϵ��տ�����!");
		}
		

		if (coll != null && !coll.isEmpty()) {
			typeInfo = coll.get(0);
		}
		
		return typeInfo;
	}
	private PaymentBillTypeInfo getPaymentBillType(Context ctx){
		PaymentBillTypeInfo typeInfo = null;
		PaymentBillTypeCollection coll =null;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "999",
						CompareType.EQUALS));
		IPaymentBillType iPaymentBillType;
		try {
			iPaymentBillType = PaymentBillTypeFactory.getLocalInstance(ctx);
			
			coll = iPaymentBillType.getPaymentBillTypeCollection(ev);
		} catch (BOSException e) {
			logger.error(e.getMessage()+"�õ�����ϵͳ��Ĭ�ϵĸ�������!");
		}
		

		if (coll != null && !coll.isEmpty()) {
			typeInfo = coll.get(0);
		}
		
		return typeInfo;
	}

	protected void _adjustReceiveBill(Context ctx, BOSUuid billId, Map map) throws BOSException, EASBizException {
		//TODO 
		
		Map entryMap = (Map)map.get("entryMap");
		Map mainMap = (Map)map.get("mainMap");
		Date bizDate =  (Date)mainMap.get("bizDate");
		String description = (String)mainMap.get("description");
		FDCReceivingBillInfo revInfo = this.getFDCReceivingBillInfo(ctx, "select *,entries.*,fdcCustomers.*,entries.sourceEntries.* " +
				" where id ='"+billId.toString()+"'");
		if(revInfo.getRevBillType().equals(RevBillTypeEnum.adjust))
			throw new EASBizException(new NumericExceptionSubItem("100","������Ե�������������"));
		
		if(!revInfo.getBillStatus().equals(RevBillStatusEnum.AUDITED) && 
				!revInfo.getBillStatus().equals(RevBillStatusEnum.RECED)) {
			throw new EASBizException(new NumericExceptionSubItem("100","ֻ�����������Ѹ���״̬�ĵ�������������"));
		}
		
		//��ѡ����տ�������Ϲ��������ϣ���������е���
		if(revInfo.getRevBizType()!=null && revInfo.getRevBizType().equals(RevBizTypeEnum.purchase) && revInfo.getPurchaseObj()!=null) {
			if(PurchaseFactory.getLocalInstance(ctx).exists("where id='"+ revInfo.getPurchaseObj().getId() +"' " +
					"and (purchaseState = '"+ PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE +"' " +
							"or purchaseState = '"+ PurchaseStateEnum.QUITROOMBLANKOUT_VALUE +"' " +
									"or purchaseState = '"+ PurchaseStateEnum.ADJUSTBLANKOUT_VALUE +"' ) ")) {
				throw new EASBizException(new NumericExceptionSubItem("100","�������Ϲ��������ϣ���������е�����"));
			}
		}
		
		FDCReceivingBillInfo adjustInfo = (FDCReceivingBillInfo)revInfo.clone();
		//���ñ���		
		ICodingRuleManager iCodingRuleManager = null;
		if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		
		OrgUnitInfo orgUnit = null;
		if(ctx!=null) {
			orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		}else {
			orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
			if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		}
		
		String retNumber = iCodingRuleManager.getNumber(adjustInfo, orgUnit.getId().toString());
		if(retNumber==null || retNumber.trim().length()==0 )
			throw new EASBizException(new NumericExceptionSubItem("100","δ���ñ������ �������Զ�����ת���"));
		
		adjustInfo.setId(null);
		adjustInfo.setNumber(retNumber);
		adjustInfo.setRevBillType(RevBillTypeEnum.adjust);
		adjustInfo.setBizDate(bizDate);
		adjustInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		adjustInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
		adjustInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		adjustInfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		adjustInfo.setAuditTime(null);
		adjustInfo.setIsCollection(false);
		adjustInfo.setIsMonthSettled(false);
		
		Set setId  = entryMap.keySet();
		Iterator iter = setId.iterator();
//		for(int k=0;k<map.size();k++)){
//			
//		}
		BigDecimal amt = FDCHelper.ZERO;
		while(iter.hasNext()){
			String id = (String)iter.next();
			amt =  amt.add((BigDecimal)entryMap.get(id ));
		}
//		adjustInfo.setAmount(adjustInfo.getAmount().negate());
//		adjustInfo.setOriginalAmount(adjustInfo.getOriginalAmount().negate());
		adjustInfo.setAmount(amt);
		adjustInfo.setOriginalAmount(amt);
		adjustInfo.setBillStatus(RevBillStatusEnum.SUBMIT);
		adjustInfo.setState(FDCBillStateEnum.SUBMITTED);
//		adjustInfo.setDescription("���"+revInfo.getRevBillType().getAlias()+"-"+revInfo.getRevBizType().getAlias()+"�����"+revInfo.getNumber()+"�ĵ���");
		adjustInfo.setDescription(description);
		
		FDCReceivingBillEntryCollection adjustEntryColl = adjustInfo.getEntries();;
		BigDecimal revAmount = FDCHelper.ZERO;
		for(int i=0;i<adjustEntryColl.size();i++) {
			FDCReceivingBillEntryInfo adjustEntryInfo = adjustEntryColl.get(i);
			if(adjustEntryInfo.getId()!=null){
				revAmount = (BigDecimal)entryMap.get(adjustEntryInfo.getId().toString());
			}
			adjustEntryInfo.setId(BOSUuid.create(adjustEntryInfo.getBOSType()));
			adjustEntryInfo.setHead(adjustInfo);
			adjustEntryInfo.setRevAmount(revAmount);
			TransferSourceEntryCollection trasfEntryColl = adjustEntryInfo.getSourceEntries();
			for(int j=0;j<trasfEntryColl.size();j++){
				TransferSourceEntryInfo trasfEntryInfo = trasfEntryColl.get(j);
				trasfEntryInfo.setId(null);
				trasfEntryInfo.setHeadEntry(adjustEntryInfo);
				trasfEntryInfo.setAmount(revAmount);
			}
		}
		
		RevFDCCustomerEntryCollection fdcCustColl =  adjustInfo.getFdcCustomers();
		for(int i=0;i<fdcCustColl.size();i++) {
			RevFDCCustomerEntryInfo fdcCustInfo = fdcCustColl.get(i);
			fdcCustInfo.setId(null);
			fdcCustInfo.setHead(adjustInfo);
		}
		
		String handleClazzName = null;
		handleClazzName  = "com.kingdee.eas.fdc.propertymgmt.app.PPMNewReceiveHandle";
		
		//���տ��ת��ĵ���������ʱ��Ҫ��֤���������ϸ��ʣ�����С��0
		if(revInfo.getRevBillType().equals(RevBillTypeEnum.gathering) || revInfo.getRevBillType().equals(RevBillTypeEnum.transfer))	{
			try {
				IRevHandle revHandle = (IRevHandle) Class.forName(handleClazzName).newInstance();
				
				for(int i=0;i<adjustEntryColl.size();i++) {
					FDCReceivingBillEntryInfo adjustEntryInfo = adjustEntryColl.get(i);
					IRevListInfo revListInfo = revHandle.getRevListInfoObj(ctx, adjustEntryInfo.getRevListId(), adjustEntryInfo.getRevListType());
					BigDecimal retLeftAmount = revListInfo.getAllRemainAmount().add(adjustEntryInfo.getRevAmount());
					if(retLeftAmount.compareTo(new BigDecimal(0))<0) {
						throw new EASBizException(new NumericExceptionSubItem("100","��ֹ��������Ϊ������ᵼ��'"+adjustEntryInfo.getRevListType().getAlias()+"'��ʣ����Ϊ��ֵ��" ));
					}
				}
			} catch (InstantiationException e) {
				throw new EASBizException(new NumericExceptionSubItem("100","�쳣����InstantiationException��" ));
			} catch (IllegalAccessException e) {
				throw new EASBizException(new NumericExceptionSubItem("100","�쳣����IllegalAccessException��" ));
			} catch (ClassNotFoundException e) {
				throw new EASBizException(new NumericExceptionSubItem("100","�쳣����ClassNotFoundException��" ));
			}

		}		
		
		
		this._submitRev(ctx, adjustInfo ,handleClazzName);
		
	}

	protected void _receive(Context ctx, BOSUuid BOSUuid) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return;
	}
	
	
	
}




class RevListDes{
	String revListId;
	RevListTypeEnum revListType;
	IRevListInfo revListInfo;
	BigDecimal amount;
	public RevListDes(){
	}
	public RevListDes(String revListId){
		this.revListId = revListId;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RevListDes){
			RevListDes other = (RevListDes) obj;
			return this.revListId.equals(other.revListId);
		}
		return false;
	}
	public int hashCode() {
		return revListId.hashCode();
	}
}
