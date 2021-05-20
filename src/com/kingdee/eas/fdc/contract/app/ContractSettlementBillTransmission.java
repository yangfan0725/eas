package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeAuxFactory;
import com.kingdee.eas.basedata.assistant.ExchangeAuxInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

public class ContractSettlementBillTransmission extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.contract.ContractTransResource";
	
	private static UserInfo userInfo1 = null;
	
	private static UserInfo userInfo2 = null;
	
	private static CurProjectInfo curprojectInfo = null;
	
	protected ICoreBase getController(Context ctx)throws TaskExternalException {
		try {
			return ContractSettlementBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		
		ContractSettlementBillInfo info = null;
		
		//��֯����
		String FOrgUnitLongNumber=(String) ((DataToken) hsData.get("FOrgUnit_longNumber")).data;
		//������Ŀ����
		String fCurProjectLongNumber=(String) ((DataToken) hsData.get("FCurProject_longNumber")).data;
		//��ͬ����
		String fContractBillCodingNumber=(String) ((DataToken) hsData.get("FContractBill_codingNumber")).data;
        //����
		String fNumber=(String) ((DataToken) hsData.get("FNumber")).data;
		//����
		String fName=(String) ((DataToken) hsData.get("FName")).data;
		//״̬
		String fState=(String) ((DataToken) hsData.get("FState")).data;
		//�ұ����
		String fCurrencyNumber=(String) ((DataToken) hsData.get("FCurrency_number")).data;
		//ҵ������
		String fBizDate=(String) ((DataToken) hsData.get("FBizDate")).data;
		//ԭ�ҽ��
		String fOriginalAmount=(String) ((DataToken) hsData.get("FOriginalAmount")).data;
		//����
		String fExchangeRate=(String) ((DataToken) hsData.get("FExchangeRate")).data;
		//��λ�ҽ��
		String fAmount=(String) ((DataToken) hsData.get("FAmount")).data;
		//�������
		String fBuildArea=(String) ((DataToken) hsData.get("FBuildArea")).data;
		//ȡ�ѱ�׼
		String fGetFeeCriteria=(String) ((DataToken) hsData.get("FGetFeeCriteria")).data;
		//��λ���
		String fUnitPrice=(String) ((DataToken) hsData.get("FUnitPrice")).data;
		//��Ϣ��
		String fInfoPrice=(String) ((DataToken) hsData.get("FInfoPrice")).data;
		//���޽����
		String fQualityGuaranteRate=(String) ((DataToken) hsData.get("FQualityGuaranteRate")).data;
		//���޽�
		String fGuaranteAmt=(String) ((DataToken) hsData.get("FGuaranteAmt")).data;
		//��������
		String fQualityTime=(String) ((DataToken) hsData.get("FQualityTime")).data;
		//�Ƿ����ս���
		String fIsFinalSettle=(String) ((DataToken) hsData.get("FIsFinalSettle")).data;
		//�Ƶ��˱���
		String fCreatorNumber=(String) ((DataToken) hsData.get("FCreator_number")).data;
		//�Ƶ�ʱ��
		String fCreateTime=(String) ((DataToken) hsData.get("FCreateTime")).data;
		//�����˱���
		String fAuditorNumber=(String) ((DataToken) hsData.get("FAuditor_number")).data;
		//���ʱ��
		String fAuditTime=(String) ((DataToken) hsData.get("FAuditTime")).data;

		/*
		 * �ж��Ƿ�Ϊ��
		 */
		if (StringUtils.isEmpty(fCurProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCurProjectCodingNumberNotNull"));
		}
		if (StringUtils.isEmpty(fContractBillCodingNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fContractCodingNumberNotNull"));
		}
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fNumberNotNull"));
		}
		if (StringUtils.isEmpty(fName)) {
			throw new TaskExternalException(getResource(ctx,"Import_fNameNotNull"));
		}
		if (StringUtils.isEmpty(fOriginalAmount)) {
			throw new TaskExternalException(getResource(ctx,"Import_fOriginalAmountNotNull"));
		}
		if (StringUtils.isEmpty(fBizDate)) {
			throw new TaskExternalException(getResource(ctx,"Import_fBizDateNotNull"));
		}		
		if (StringUtils.isEmpty(fCreatorNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCreatorNameL2NotNull"));
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCreateTime"));
		}
		
		/*
		 * �ж��ַ�����
		 */
		if (FOrgUnitLongNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fCostCenterNumber"));
		}
		if (fCurProjectLongNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fCurProjectCodingNumber"));
		}
		if (fContractBillCodingNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx,"Import_fContractCodingNumber"));
		}
		if (fNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx,"Import_fNumber"));
		}
		if (fName.length() > 80) {
			throw new TaskExternalException(getResource(ctx,"Import_fName"));
		}
		if (fState.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fStateLen40"));
		}
		if (fCurrencyNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fCurrencyNumber"));
		}
		if (fGetFeeCriteria.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fGetFeeCriteriaLen40"));
		}
		if (fInfoPrice.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fInfoPriceLen40"));
		}
		if (fQualityTime.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fQualityTimeLen40"));
		}
		if (fCreatorNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fCreatorNameL2"));
		}
		if (fAuditorNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fAuditorNameL2"));
		}
		//���ݱ����ظ��ж�
		try {
			FilterInfo CSBfilter = new FilterInfo();
			CSBfilter.getFilterItems().add(new FilterItemInfo("number", fNumber));
			EntityViewInfo CSBview = new EntityViewInfo();
			CSBview.setFilter(CSBfilter);
			ContractSettlementBillCollection csbcollection;
			csbcollection = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(CSBview);
			if(csbcollection.size() == 0){
				info = new ContractSettlementBillInfo();
			}else{
				throw new TaskExternalException(getResource(ctx,"ContractBillImport_fCodingNumber"));
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		//���������ظ��ж�
		try {
			FilterInfo CSBfilter = new FilterInfo();
			CSBfilter.getFilterItems().add(new FilterItemInfo("name", fName));
			EntityViewInfo CSBview = new EntityViewInfo();
			CSBview.setFilter(CSBfilter);
			ContractSettlementBillCollection csbcollection;
			csbcollection = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(CSBview);
			if(csbcollection.size() == 0){
				info = new ContractSettlementBillInfo();
			}else{
				throw new TaskExternalException(getResource(ctx,"ContractBillImport_fName"));
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		/*
		 * ö���ж�
		 */
		//״̬
		if(fState.trim().equals(getResource(ctx,"baocun"))){
			info.setState(FDCBillStateEnum.SAVED);
		}else if(fState.trim().equals(getResource(ctx,"yitijiao"))){
			info.setState(FDCBillStateEnum.SUBMITTED);
		}else{
			//Ĭ��Ϊ������״̬
			info.setState(FDCBillStateEnum.AUDITTED);
		}
		
		//��ֵ�ж�
		// ������ʽ ��֤������Ƿ�Ϊ�����Ͳ�����    "^\\d+(\\.\\d+)?$"
		//ԭ�ҽ��  	 	
		if(!fOriginalAmount.matches("^\\d+(\\.\\d+)?$")){
			// "������ 1��19 λ������� 1��4λС�����ɣ�"
			throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfOriginalAmount"));
		}
		//����
		if(!(StringUtils.isEmpty(fExchangeRate))&&(FDCTransmissionHelper.isRangedInHundred(fExchangeRate) == null)){
			throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfExchangeRate"));
	 	}
		//��λ�ҽ��
		if((!StringUtils.isEmpty(fAmount))&&(!fAmount.matches("^\\d+(\\.\\d+)?$")))
				throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfAmount"));
		//�������
		if((!StringUtils.isEmpty(fBuildArea))&&(!fBuildArea.matches("^\\d+(\\.\\d+)?$"))){
			// "������ 1��19 λ������� 1��4λС�����ɣ�"
			throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfBuildArea"));
		}
		//��λ���
		if((!StringUtils.isEmpty(fUnitPrice))&&(!fUnitPrice.matches("^\\d+(\\.\\d+)?$"))){
			// "������ 1��19 λ������� 1��4λС�����ɣ�"
			throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfUnitPrice"));
		}
		//���޽����
		if((!StringUtils.isEmpty(fQualityGuaranteRate))&&(!fQualityGuaranteRate.matches("^\\d+(\\.\\d+)?$"))){
			// "������ 1��19 λ������� 1��4λС�����ɣ�"
			throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfQualityGuaranteRate"));
		}
		//���޽�
		if((!StringUtils.isEmpty(fGuaranteAmt))&&(!fGuaranteAmt.matches("^\\d+(\\.\\d+)?$"))){
			// "������ 1��19 λ������� 1��4λС�����ɣ�"
			throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfGuaranteAmt"));
		}
		
		/*
		 * �����ж�
		 */
		//�Ƿ����ս���
		if(fIsFinalSettle.trim().equals(getResource(ctx,"No"))){
			info.setIsFinalSettle(BooleanEnum.FALSE);
		}else{
			info.setIsFinalSettle(BooleanEnum.TRUE);
		}
		try {
			//���ֱ��� 
			CurrencyInfo currinfo = null;
			currinfo= CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(this.getView("number", fCurrencyNumber)).get(0);
			//����Ϊ�յ�ʱ��ȥ��λ�� 
			if (currinfo == null) {
				currinfo = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where name='�����'").get(0);
				if(currinfo==null){
					FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FCurrency_number")+ fCurrencyNumber + getResource(ctx,"ContractBillImport_FY"));
				}	
			}
			info.setCurrency(currinfo);
			
			//����	
			CurrencyInfo ci = null;//�����ұ�λ��
			EntityViewInfo rateView = new EntityViewInfo();
			FilterInfo rateFilter = new FilterInfo();
			ExchangeRateInfo erinfo = null;//���ʶ���
			BigDecimal bgm = null;//���ʵ�ֵ
			//�������Ϊ�գ�����ݱұ��ȡ��ǰ���ʣ�
			if (fExchangeRate==null || fExchangeRate.trim().equals("")) {
				//��λ�Ҷ���ci        //�û���д�ı���-ԭ�Ҷ��� currinfo
				ci = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where name='�����'").get(0);
				if (ci == null) {//ci�Ǳ�λ�ҹ�   �Ҳ�����λ��
					FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FCanotRMB"));
				}else{//�ҵ���λ�ҵ����
					if(ci.getName().trim().equals(currinfo.getName().trim())){//��������ҵ����
						bgm = new BigDecimal(1.0000);
					} else {
						rateFilter.getFilterItems().add(new FilterItemInfo("targetCurrency",ci.getId(),CompareType.EQUALS));//Ŀ��ұ�λ��
						rateFilter.getFilterItems().add(new FilterItemInfo("SourceCurrency", currinfo.getId(), CompareType.EQUALS));//�û���д��ԭ��
						rateFilter.setMaskString("#0 and #1");
						rateView.setFilter(rateFilter);
						ExchangeAuxInfo xinfo = ExchangeAuxFactory.getLocalInstance(ctx).getExchangeAuxCollection(rateView).get(0);
						if(xinfo==null){
							FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FyuanAndRmb"));
						}else{
							erinfo = ExchangeRateFactory.getLocalInstance(ctx).
							getExchangeRate(new ObjectUuidPK(xinfo.getExchangeTable().getId()),
									new ObjectUuidPK(xinfo.getSourceCurrency().getId()), 
									new ObjectUuidPK(xinfo.getTargetCurrency().getId()), 
									Calendar.getInstance().getTime());
							bgm = erinfo.getConvertRate();
							if(erinfo==null){
								FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FyuanAndRmb"));
							}
						}
					}
				}	
			} else {
				bgm = FDCTransmissionHelper.strToBigDecimal(fExchangeRate);
			}
			info.setExchangeRate(bgm);
			
			//��λ�ҽ��  ���Ϊ0����ô���ݽ��*���ʼ���
			if (fAmount == null || FDCTransmissionHelper.strToDouble(fAmount) == 0) {
				// ��λ�ҽ�� = ԭ�ҽ��*����
				info.setAmount(FDCHelper.multiply(new BigDecimal(fOriginalAmount), bgm));
				// ��ǰ��λ�ҽ����
				info.setCurSettlePrice(FDCHelper.multiply(new BigDecimal(fOriginalAmount), bgm));
			}else{
				// ��λ�ҽ��
				info.setAmount(new BigDecimal(fAmount));
				// ��ǰ��λ�ҽ����
				info.setCurSettlePrice(new BigDecimal(fAmount));
			}		
			
			/*
			 * �Ҳ�����ʾ
			 */
			//������Ŀ����
			FilterInfo curFilter = new FilterInfo();
			curFilter.getFilterItems().add(new FilterItemInfo("LongNumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo curView = new EntityViewInfo();
			curView.setFilter(curFilter);
			CurProjectCollection curcoll = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curView);
			if(curcoll.size()>0){
				curprojectInfo = curcoll.get(0);
				info.setCurProject(curprojectInfo);
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_fCurProjectCodingNumber1")+getResource(ctx, "Import_NOTNULL"));
			}
			//��֯����
			CostCenterOrgUnitInfo costCenterOrgUnit = curprojectInfo.getCostCenter(); // ������Ŀ��Ӧ�ɱ�������֯
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			CostCenterOrgUnitCollection collection = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(view);
			if(collection.size() > 0) {
				costCenterOrgUnit = collection.get(0);
			} 
			if(StringUtils.isEmpty(FOrgUnitLongNumber)){
				info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
//				info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
			}else{
				if(FOrgUnitLongNumber.trim().replace('.', '!').equals(costCenterOrgUnit.getLongNumber().toString())){
					info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
//					info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
				}else{
					throw new TaskExternalException(getResource(ctx,"Import_FOrgUnitLongNumberNotE"));
				}
			}
			//��ͬ����
			ContractBillInfo contractbillInfo = null;
			FilterInfo conbfilter = new FilterInfo ();
			conbfilter.getFilterItems().add(new FilterItemInfo("number",fContractBillCodingNumber));
			EntityViewInfo conbview = new EntityViewInfo();
			conbview.setFilter(conbfilter);
			ContractBillCollection conbcoll = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(conbview);
			if(conbcoll.size()>0){
				contractbillInfo = conbcoll.get(0);
			}
			if(contractbillInfo==null){
				throw new TaskExternalException(getResource(ctx,"Import_fContractCodingNumber1")+getResource(ctx,"Import_NOTNULL"));
			}else{
				info.setContractBill(contractbillInfo);
			}
			//�Ƶ��˱���
			FilterInfo user1filter = new FilterInfo();
			user1filter.getFilterItems().add(new FilterItemInfo("number",fCreatorNumber));
			EntityViewInfo user1view = new EntityViewInfo();
			user1view.setFilter(user1filter);
			UserCollection user1coll = UserFactory.getLocalInstance(ctx).getUserCollection(user1view);
			if(user1coll.size()>0){
				userInfo1 = user1coll.get(0);	
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_fCreatorNumber")+getResource(ctx,"Import_NOTNULL"));
			}
			//�����˱��룬���ʱ��		
			if(!(fState.trim().equals(getResource(ctx,"baocun"))||fState.trim().equals(getResource(ctx,"yitijiao")))){
				if (!StringUtils.isEmpty(fAuditorNumber)){ 
					FilterInfo uesr2filter = new FilterInfo();
					uesr2filter.getFilterItems().add(new FilterItemInfo("number",fAuditorNumber));
					EntityViewInfo user2view = new EntityViewInfo();
					user2view.setFilter(uesr2filter);
					UserCollection user2coll = UserFactory.getLocalInstance(ctx).getUserCollection(user2view);
					if(user2coll.size()>0){
						userInfo2 = user2coll.get(0);	
						info.setAuditor(userInfo2);
					} else {
						throw new TaskExternalException(getResource(ctx,"Import_fAuditorNameL21")+getResource(ctx,"Import_NOTNULL"));
					}
				} else {
					throw new TaskExternalException(getResource(ctx,"Import_fAuditorNameL2NotNull"));
				}
			}	
			if(!(fState.trim().equals(getResource(ctx,"baocun"))||fState.trim().equals(getResource(ctx,"yitijiao")))){
				if (StringUtils.isEmpty(fAuditTime)) 
					throw new TaskExternalException(getResource(ctx,"Import_fAuditTime"));
				info.setAuditTime(checkDate(fAuditTime,ctx));
			}		
			checkDate(fBizDate,ctx);
			checkDate(fCreateTime,ctx);
			checkDate(fAuditTime,ctx);
			
			info.setNumber(fNumber);
			info.setName(fName);
			info.setBizDate(checkDate(fBizDate,ctx));	
			// ԭ�ҽ��
			info.setOriginalAmount(new BigDecimal(fOriginalAmount));
			// ��ǰԭ�ҽ����
			info.setCurOriginalAmount(new BigDecimal(fOriginalAmount));
			if (!StringUtils.isEmpty(fBuildArea)) {
				info.setBuildArea(new BigDecimal(fBuildArea));
			} else {
				info.setBuildArea(new BigDecimal(0.0000));
			}
			info.setGetFeeCriteria(fGetFeeCriteria);
			if (!StringUtils.isEmpty(fUnitPrice)) {
				info.setUnitPrice(new BigDecimal(fUnitPrice));
			} else{
				info.setUnitPrice(new BigDecimal(0.0000));
			}
			info.setInfoPrice(fInfoPrice);
			if (!StringUtils.isEmpty(fQualityGuaranteRate)) {
				info.setQualityGuaranteRate(new BigDecimal(fQualityGuaranteRate));
			} else {
				info.setQualityGuaranteRate(new BigDecimal(0.0000));
			}
			if (!StringUtils.isEmpty(fGuaranteAmt)) {
				info.setGuaranteAmt(new BigDecimal(fGuaranteAmt));
			} else {
				info.setGuaranteAmt(new BigDecimal(0.0000));
			}
			info.setQualityTime(fQualityTime);	
			info.setCreator(userInfo1);
			info.setCreateTime(new Timestamp(checkDate(fCreateTime, ctx).getTime()));
	        
	        
	        
		} catch (BOSException e) {
			FDCTransmissionHelper.isThrow("",e.toString());
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	//�����ж�
	private Date checkDate(String dateStr,Context ctx) throws TaskExternalException{
		try {
			if(StringUtils.isEmpty(dateStr)) return null;
			DateFormat df = null;
			if(dateStr.trim().length() <= "yyyy-MM-dd".length()){ // ���� "yyyy-MM-d"
				df = new SimpleDateFormat("yyyy-MM-dd");
			}else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm".length()){ //���� yyyy-MM-d HH:mm���
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm");	
			}else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm:ss".length()){
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_DateForm"));
			}
			df.setLenient(false);
			return df.parse(dateStr);
		} catch (ParseException e) {
//			e.printStackTrace();
			throw new TaskExternalException(getResource(ctx,"Import_DateForm"));
		}
    }
	
	//������ͼ
	private EntityViewInfo getView(String sqlcolnum,Object item){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	
	/**
	 * �õ���Դ�ļ�
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	
}
