package com.kingdee.eas.fdc.tenancy.app;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceInfo;
import com.kingdee.eas.fdc.tenancy.BizStateEnum;
import com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryFactory;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum;
import com.kingdee.eas.fdc.tenancy.FlagAtTermEnum;
import com.kingdee.eas.fdc.tenancy.HandleRoomEntrysInfo;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.ITenPriceBaseLine;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyInfo;
import com.kingdee.eas.fdc.tenancy.RentCountTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenBillBaseInfo;
import com.kingdee.eas.fdc.tenancy.TenPriceBaseLineFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyException;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class TenancyBillControllerBean extends AbstractTenancyBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.TenancyBillControllerBean");
    private MoneyDefineInfo rentMoneyName = null;// ����������
	private MoneyDefineInfo depositMoneyName = null;// Ѻ���������
    
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	TenancyBillInfo tenancyBill = (TenancyBillInfo) model;
    	tenancyBill.setTenancyState(TenancyBillStateEnum.Saved);
    	
    	IObjectPK pk = super._save(ctx, model);
    	//ת����ʱ����д����Ԥ������ҵ��״̬
    	if(tenancyBill.getSincerObligate() != null){
    		String sql = "update T_TEN_SincerObligate set FBizState = ? where fid = ?";
    		DbUtil.execute(ctx, sql,new Object []{BizStateEnum.LEASE_VALUE,tenancyBill.getSincerObligate().getId().toString()});
    	}
    	return pk;
    }
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("tenancyType");
    	sels.add("oldTenancyBill.tenancyState");
    	sels.add("sincerObligate.*");
    	TenancyBillInfo tenancy = (TenancyBillInfo) _getValue(ctx, pk, sels);
    	//���ɾ���ĺ�ͬ�Ǹ��⣬������߸������͵ģ���Ҫ��ԭ��ͬ�Ӹ����и���Ϊִ����.
    	//�ò�����ǰ���� ��ֻ��ִ���еĺ�ͬ���ܽ��и��⣬���⣬ת����
    	TenancyContractTypeEnum tenType = tenancy.getTenancyType();
    	if(TenancyContractTypeEnum.ContinueTenancy.equals(tenType)
    			|| TenancyContractTypeEnum.RejiggerTenancy.equals(tenType)
    			|| TenancyContractTypeEnum.ChangeName.equals(tenType)){
    		TenancyBillInfo oldTen = tenancy.getOldTenancyBill();
    		if(oldTen == null){
    			logger.error("����ת����ͬ��ԭ��ͬΪ�գ���ͬID��" + pk.toString());
    		}else{
    			TenancyBillStateEnum oldBillState = oldTen.getTenancyState();    			
    			if((TenancyContractTypeEnum.ContinueTenancy.equals(tenType) && TenancyBillStateEnum.ContinueTenancying.equals(oldBillState))
    					|| (TenancyContractTypeEnum.RejiggerTenancy.equals(tenType) && TenancyBillStateEnum.RejiggerTenancying.equals(oldBillState))
    					|| (TenancyContractTypeEnum.ChangeName.equals(tenType) && TenancyBillStateEnum.ChangeNaming.equals(oldBillState))){
    				oldTen.setTenancyState(TenancyBillStateEnum.Executing);
    				SelectorItemCollection updateOldStateSels = new SelectorItemCollection();
    				updateOldStateSels.add("tenancyState");
    				_updatePartial(ctx, oldTen, updateOldStateSels);
    			}else{
    				logger.error("״̬�߼����������tenType:" + tenType + "  oldTenState:" + oldBillState);
    			}
    		}
    	}
    	//ת����ʱ����д����Ԥ������ҵ��״̬
    	if(tenancy.getSincerObligate() != null){
    		String sql = "update T_TEN_SincerObligate set FBizState = ? where fid = ?";
    		DbUtil.execute(ctx, sql,new Object []{BizStateEnum.SINCEROBLIGATED_VALUE,tenancy.getSincerObligate().getId().toString()});
    	}
    	super._delete(ctx, pk);
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		TenancyBillInfo tenancyBill = (TenancyBillInfo) model;
		TenancyContractTypeEnum tenancyBillType = tenancyBill.getTenancyType();

		// ����,�������� ��Ҫ�ж�ԭ��ͬʣ��Ѻ���Ƿ񹻽���
		if (!TenancyContractTypeEnum.NewTenancy.equals(tenancyBillType)) {
			TenancyBillInfo oldTen = tenancyBill.getOldTenancyBill();
			if (oldTen == null) {
				throw new BOSException("û��ԭ��ͬ!");
			}

			//����ԭ��ͬ��״̬Ϊ ������/������/������
			TenancyBillStateEnum oldBillState = null;
			if(TenancyContractTypeEnum.ContinueTenancy.equals(tenancyBillType)){
				oldBillState = TenancyBillStateEnum.ContinueTenancying;
			}else if(TenancyContractTypeEnum.RejiggerTenancy.equals(tenancyBillType)){
				oldBillState = TenancyBillStateEnum.RejiggerTenancying;
			}else if(TenancyContractTypeEnum.ChangeName.equals(tenancyBillType)){
				oldBillState = TenancyBillStateEnum.ChangeNaming;
			}else if(TenancyContractTypeEnum.PriceChange.equals(tenancyBillType)){
				if(FDCBillStateEnum.AUDITTED.equals(oldTen.getState())){
					oldBillState = TenancyBillStateEnum.Expiration;
				}else{
					oldBillState = TenancyBillStateEnum.PriceChanging;
				}
			}
			setBillState(ctx, oldTen, oldBillState);
			
			oldTen = TenancyHelper.getTenancyBillInfo(ctx, oldTen.getId().toString());
			// ����ֻ������֤,Ϊ��Ӱ���������ݵ������ύ,ʹ�ÿ���������е�����֤
			TenancyBillInfo newBillCopy = (TenancyBillInfo) tenancyBill.clone();
			TenancyBillInfo oldBillCopy = (TenancyBillInfo) oldTen.clone();

			adjustDepositeAndPayList(ctx, newBillCopy, oldBillCopy, false);
			checkRemainDepositAmountEnough(newBillCopy, oldBillCopy);
		}

		TenancyCustomerEntryCollection customerInfos = tenancyBill.getTenCustomerList();
		for (int i = 0; i < customerInfos.size(); i++) {
			TenancyCustomerEntryInfo customerInfoInfo = customerInfos.get(i);
			FDCCustomerInfo customer = customerInfoInfo.getFdcCustomer();
			if (customer != null) {
				//---����ֻ��������ʱ���ܸĶ�����Ϣ������ʹ��update(model)
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("postalcode");
				sels.add("phone");
				sels.add("certificateName");
				sels.add("certificateNumber");
				sels.add("mailAddress");
				sels.add("description");
				FDCCustomerFactory.getLocalInstance(ctx).updatePartial(customer, sels);
//				FDCCustomerFactory.getLocalInstance(ctx).update(new ObjectUuidPK(customer.getId()), customer);
				//-------
				FDCCustomerFactory.getLocalInstance(ctx).addToSysCustomer(customer.getId().toString());
			}
		}

		tenancyBill.setTenancyState(TenancyBillStateEnum.Submitted);
		
		IObjectPK pk = super._submit(ctx, model);
		//���º�ͬID���տ���ϸ
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("tenancyRoomList.roomPayList.*");
		sels.add("tenancyRoomList.roomPayList.currency.name");
		sels.add("tenancyRoomList.roomPayList.currency.number");
		
		sels.add("tenancyRoomList.roomPayList.moneyDefine.name");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.number");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.moneyType");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.sysType");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.isEnabled");
		
		TenancyBillInfo tenancyInfo = TenancyBillFactory.getLocalInstance(ctx).getTenancyBillInfo(pk, sels);
		TenancyRoomEntryCollection  roomPaylistColl = tenancyInfo.getTenancyRoomList();
		for(int i = 0;i <roomPaylistColl.size();i++){
			TenancyRoomEntryInfo roomEntryInfo = (TenancyRoomEntryInfo)roomPaylistColl.get(i);
			TenancyRoomPayListEntryCollection roomPaylist = roomEntryInfo.getRoomPayList();
			for(int j= 0 ; j<roomPaylist.size();j++ ){
				TenancyRoomPayListEntryInfo paylistInfo =  (TenancyRoomPayListEntryInfo)roomPaylist.get(j);
				paylistInfo.setTenBill(tenancyBill);
				TenancyRoomPayListEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(paylistInfo.getId()),paylistInfo);
			}
		}
		
		//ת����ʱ����д����Ԥ������ҵ��״̬
    	if(tenancyBill.getSincerObligate() != null){
    		String sql = "update T_TEN_SincerObligate set FBizState = ? where fid = ?";
    		DbUtil.execute(ctx, sql,new Object []{BizStateEnum.LEASE_VALUE,tenancyBill.getSincerObligate().getId().toString()});
    	}
    	if(model.get("tenPriceIdS") != null){
    		Set<String> tenPriceIds = (Set<String>) model.get("tenPriceIdS");
    		ITenPriceBaseLine tenPriceBaseLine = TenPriceBaseLineFactory.getLocalInstance(ctx);
    		for(Iterator<String> it=tenPriceIds.iterator();it.hasNext();){
    			String id = it.next();
    			tenPriceBaseLine.useTenPrice(BOSUuid.read(id));
    		}
    	}
    	return  pk;
	}
    protected void checkNameDup(Context ctx, TenBillBaseInfo tenBillBaseInfo)
    		throws BOSException, EASBizException {
//    	super.checkNameDup(ctx, tenBillBaseInfo); //���ﲻ�ټ�� �����ظ� 2010.4.9 by jian_wen ������
    }
    private void setBillState(Context ctx, TenancyBillInfo oldTen, TenancyBillStateEnum state) throws EASBizException, BOSException {
    	if(oldTen != null){
    		oldTen.setTenancyState(state);
    		SelectorItemCollection sels = new SelectorItemCollection();
    		sels.add("tenancyState");
    		updatePartial(ctx, oldTen, sels);
    	}
	}

	/**
	 * ת����ʱ�����ʽ��ת TODO ��Щ�߼�ת��������ȥ��
	 */
    private void financingCarryForward(TenancyBillInfo tenBill){
    	TenancyBillInfo oldTenBill = tenBill.getOldTenancyBill();
    	//����ʱ�����ʽ��ת
    	BigDecimal deductAmount = tenBill.getDeductAmount();//��ÿۿ���
    	
    	BigDecimal oldRemainDepositAmount = oldTenBill.getRemainDepositAmount();//���ԭ��ͬʣ��Ѻ��
    	Date rejiggerDate = tenBill.getStartDate();//��������
    	
    	BigDecimal totalMissingRentAmount = FDCHelper.ZERO;//ȱ�ٵķ������
    	TenancyRoomEntryCollection tenRooms = oldTenBill.getTenancyRoomList();
    	for(int i=0; i<tenRooms.size(); i++){
    		TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
    		TenancyRoomPayListEntryCollection roomPayList = tenRoom.getRoomPayList();
    		for(int j=0; j<roomPayList.size(); j++){
    			TenancyRoomPayListEntryInfo roomPay = roomPayList.get(j);
    			Date payStartDate = roomPay.getStartDate();
    			Date payEndDate = roomPay.getEndDate();
    			
    			BigDecimal appAmount = roomPay.getAppAmount();
    			BigDecimal actAmount = roomPay.getActAmount();
    			
    			if(!payStartDate.before(rejiggerDate)){
    				//����Ǹ������ں������,���ټ������
    			}else if(!payEndDate.after(rejiggerDate)){
    				//����Ǹ�������֮ǰ������,������������ڵ����
    				BigDecimal missingAmount = appAmount.subtract(appAmount);
    				totalMissingRentAmount = totalMissingRentAmount.add(missingAmount);
    				//TODO �������ĵ�����Ҫɾ���ø�����ϸ��
    			}else{
    				//������������ڸ�����֮��,������������Ч���ڵ����
    				
    				BigDecimal actAppAmount = FDCHelper.ZERO;//ʵ��Ӧ�����
    				BigDecimal missingAmount = actAppAmount.subtract(appAmount);
    				totalMissingRentAmount = totalMissingRentAmount.add(missingAmount);
    			}
    		}
    	}
    	
    	//
    	BigDecimal remainAmount = oldRemainDepositAmount.subtract(deductAmount).subtract(totalMissingRentAmount);
    	if(remainAmount.compareTo(FDCHelper.ZERO) == 0){
    		//���õ�ƽ,����ԭ��ͬ�е�ʣ��Ѻ���δ��������н�ת
    	}else if(remainAmount.compareTo(FDCHelper.ZERO) > 0){
    		//��ԭ��ͬδ������ת��,����Ҫ���º�ͬ���н�ת
    	}else{
    		//Ǯ����ѽ,�׳��쳣����ʵ����ж�Ӧ���Ǹ����ύ��ʱ����е�,��������ʱ��Ͳ�������Ǯ�����������
    	}
    	
    	
    	TenancyContractTypeEnum tenancyBillType = oldTenBill.getTenancyType();
    	if(TenancyContractTypeEnum.RejiggerTenancy.equals(tenancyBillType)){//����
    		carryForward1(tenBill, oldTenBill);
    	}else if(TenancyContractTypeEnum.ContinueTenancy.equals(tenancyBillType)){
//    		carryForward2();
    	}
    	
    }
    
    protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._setSubmitStatus(ctx, billId);
    	TenancyBillInfo tenancy = (TenancyBillInfo) this.getValue(ctx, new ObjectUuidPK(billId));
    	updateTenancyState(ctx, tenancy, TenancyBillStateEnum.Submitted);
    }

    /**
	 * �������޺�ͬ��״̬
	 * */
	private void updateTenancyState(Context ctx, TenancyBillInfo tenancy, TenancyBillStateEnum state) throws EASBizException, BOSException{
		tenancy.setTenancyState(state);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("tenancyState");
		_updatePartial(ctx, tenancy, sels);
	}
    
    protected void _setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._setAudittingStatus(ctx, billId);
    	TenancyBillInfo tenancy = (TenancyBillInfo) this.getValue(ctx, new ObjectUuidPK(billId));
    	updateTenancyState(ctx, tenancy, TenancyBillStateEnum.Auditing);
    }
    
    /** ���� */
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._audit(ctx, billId);
    	
    	TenancyBillInfo tenBill = TenancyHelper.getTenancyBillInfo(ctx, billId.toString());
    	
    	tenBill.setTenancyState(TenancyBillStateEnum.Audited);
		SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("tenancyState");
		_updatePartial(ctx, tenBill, updateSels);
    	
    	TenancyContractTypeEnum tenContractType = tenBill.getTenancyType();
    	
    	TenancyBillInfo oldTenBill = tenBill.getOldTenancyBill();
    	if(oldTenBill != null){
    		oldTenBill = TenancyHelper.getTenancyBillInfo(ctx, oldTenBill.getId().toString());
    		tenBill.setOldTenancyBill(oldTenBill);
    	}
    	
    	if(TenancyContractTypeEnum.RejiggerTenancy.equals(tenContractType)){//��������
    		setOldTenancyFlagAtTerm(ctx, tenBill, oldTenBill, FlagAtTermEnum.RejiggerNotAtTerm, FlagAtTermEnum.RejiggerNotAtTerm, FlagAtTermEnum.QuitNotAtTerm);    		
    	}else if(TenancyContractTypeEnum.ContinueTenancy.equals(tenContractType)){//��������
    		setOldTenancyFlagAtTerm(ctx, tenBill, oldTenBill, FlagAtTermEnum.ReletAtTerm, FlagAtTermEnum.ReletAtTerm, FlagAtTermEnum.QuitAtTerm);    		    	
    	}else if(TenancyContractTypeEnum.ChangeName.equals(tenContractType)){//ת������
    		setOldTenancyFlagAtTerm(ctx, tenBill, oldTenBill, FlagAtTermEnum.ChangeNameNotAtTerm, FlagAtTermEnum.ChangeNameNotAtTerm, FlagAtTermEnum.ChangeNameNotAtTerm);
    	}else if(TenancyContractTypeEnum.PriceChange.equals(tenContractType)){//�۸���
    		setOldTenancyFlagAtTerm(ctx, tenBill, oldTenBill, FlagAtTermEnum.PriceChangeNotAtTerm, FlagAtTermEnum.PriceChangeNotAtTerm, FlagAtTermEnum.PriceChangeNotAtTerm);
    	}
    	
    	if(!TenancyContractTypeEnum.NewTenancy.equals(tenContractType)){
    		adjustDepositeAndPayList(ctx, tenBill, oldTenBill, true);
        	checkRemainDepositAmountEnough(tenBill, oldTenBill);
        	deductAmountOfRemainDepositAmount(ctx, tenBill, oldTenBill);	
    	}
    }
    
    /**
     * ԭ��ͬʣ��Ѻ���ȥ�ۿ�,����̯�����������ʣ��Ѻ����,���øú���ǰһ��Ҫ�ȼ��ԭ��ͬʣ��Ѻ���Ƿ񹻼������пۿ��δ�����,��checkRemainDepositAmountEnough();
     * @throws BOSException 
     * @throws EASBizException 
     * */
    private void deductAmountOfRemainDepositAmount(Context ctx, TenancyBillInfo tenBill, TenancyBillInfo oldTenBill) throws EASBizException, BOSException {
    	BigDecimal deductAmount = tenBill.getDeductAmount();
    	//���ÿ��ˡ�TODO �������޸�Ϊ��Է���ۿ�
//    	TenancyHelper.deductAmountOfRemainDepositAmount(ctx, deductAmount, oldTenBill);
	}

	/**
     * �ж�ʣ��Ѻ���Ƿ񹻽����ͬӦ�����
     * @param tenBill
     * @param oldTenBill
     * @throws BOSException 
	 * @throws TenancyException 
     * */
    private void checkRemainDepositAmountEnough(TenancyBillInfo tenBill, TenancyBillInfo oldTenBill) throws BOSException, TenancyException {
    	BigDecimal deductAmount = tenBill.getDeductAmount()==null ? FDCHelper.ZERO : tenBill.getDeductAmount();
    	//����û�пۿ���ݲ�����. TODO
    	BigDecimal oldTenToPayAmount = FDCHelper.ZERO;//deductAmount;
    	TenancyRoomEntryCollection oldTenRooms = oldTenBill.getTenancyRoomList();
    	TenAttachResourceEntryCollection oldTenAttachs = oldTenBill.getTenAttachResourceList();
    	BigDecimal oldTenRemainDepositAmount = oldTenBill.getRemainDepositAmount()==null ? FDCHelper.ZERO : oldTenBill.getRemainDepositAmount();
    	
    	boolean isCheck=true;
    	for(int i=0; i<oldTenRooms.size(); i++){
    		ITenancyEntryInfo tenObj = (ITenancyEntryInfo) oldTenRooms.getObject(i);
    		
    		IObjectCollection payList = tenObj.getPayList();
    		
    		for(int j=0; j<payList.size(); j++){
    			TenancyRoomPayListEntryInfo pay = (TenancyRoomPayListEntryInfo) payList.getObject(j);
    			MoneyDefineInfo moneyName = pay.getMoneyDefine();
    			//Ѻ���ü���Ӧ��
    			if(MoneyTypeEnum.DepositAmount.equals(moneyName.getMoneyType())&&pay.isIsUnPay()){
    				isCheck=false;
    			}    			
    		}
    	}
    	oldTenToPayAmount = addToPayAmount(oldTenRooms, oldTenToPayAmount);
    	oldTenToPayAmount = addToPayAmount(oldTenAttachs, oldTenToPayAmount);
    	
    	if(isCheck&&oldTenRemainDepositAmount.compareTo(oldTenToPayAmount) < 0){//ԭ��ͬѺ�𲻹�����δ�����Ϳۿ�,����������/�ύ
    		throw new TenancyException(TenancyException.DEPOSIT_NOT_ENOUGH);
    	}
	}
    
    private BigDecimal addToPayAmount(IObjectCollection tenObjs, BigDecimal srcAmount){
    	for(int i=0; i<tenObjs.size(); i++){
    		ITenancyEntryInfo tenObj = (ITenancyEntryInfo) tenObjs.getObject(i);
    		
    		IObjectCollection payList = tenObj.getPayList();
    		
    		// ����Ӧ����ʵ�����бȽ���ȷ��δ����Ӧ�����
    		BigDecimal totalAppAmount = FDCHelper.ZERO;
    		BigDecimal totalActAmount = FDCHelper.ZERO;
    		for(int j=0; j<payList.size(); j++){
    			ITenancyPayListInfo pay = (ITenancyPayListInfo) payList.getObject(j);
    			MoneyDefineInfo moneyName = pay.getMoneyDefine();
    			//Ѻ���ü���Ӧ��
    			if(moneyName == null  ||  MoneyTypeEnum.DepositAmount.equals(moneyName.getMoneyType())){
    				continue;
    			}    			
    			
    			BigDecimal appAmount = pay.getAppAmount()==null ? FDCHelper.ZERO : pay.getAppAmount();
    			BigDecimal actAmount = pay.getActAmount()==null ? FDCHelper.ZERO : pay.getActAmount();
    			
    			totalAppAmount = totalAppAmount.add(appAmount);
    			totalActAmount = totalActAmount.add(actAmount);
    		}
    		
    		//ֻ��Ӧ����ʵ����ʱ,�ż��� δ����Ӧ�����;(��Ӧ����ʵ��Сʱ,����adjustDepositeAndPayList()�н����յ�ʵ�������뵽ʣ��Ѻ����,���ﲻ�ٴ���)
    		BigDecimal tmpToPay = totalAppAmount.subtract(totalActAmount);
    		if(tmpToPay.compareTo(FDCHelper.ZERO) > 0){
    			srcAmount = srcAmount.add(tmpToPay);
    		}
    	}
    	return srcAmount;
    }
    
    /**
     * ����ʣ��Ѻ��͸�����ϸ��Ӧ�����
     * ���ڸ���͸���,���ݸ��������,��Ҫ����ԭ��ͬ��Ӧ�����,�����ཻ��ʵ�����ӵ�����Ѻ����;���ⲻ����Ӧ�����ڵĸĶ�,����ԭ��ͬӦ������䶯
     * @param ctx
     * @param tenBill
     * @param oldTenBill
     * @param isExcuteToDB
     * 			�������Ƿ���µ����ݿ�.���ǵ�����Ⱥ�ͬ�ύʱ,��Ҫ��ԭ��ͬ�Ƿ��㹻���������֤,�����Ὣ�����Ľ�����(����ʱ��Ż�),�ṩ�ò���
     * */
    private void adjustDepositeAndPayList(Context ctx, TenancyBillInfo tenBill, TenancyBillInfo oldTenBill, boolean isExecuteToDB) throws EASBizException, BOSException{
    	Date bizDate = tenBill.getStartDate();//���������ʵ������,���º�ͬ��ʼ����Ϊ׼
    	
    	TenancyHelper.adjustDepositeAndPayList(ctx, bizDate, oldTenBill, isExecuteToDB);
    	
    	/*
    	TenancyRoomEntryCollection oldTenRooms = oldTenBill.getTenancyRoomList();
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("remainDepositAmount");
    	
		BigDecimal oldTenRemainDepositAmount = FDCHelper.ZERO;
		for(int i=0; i<oldTenRooms.size(); i++){
			TenancyRoomEntryInfo oldTenRoom = oldTenRooms.get(i);
			
			BigDecimal oldRoomRemainDepositAmount = oldTenRoom.getRemainDepositAmount();
			if(oldRoomRemainDepositAmount == null){
				oldRoomRemainDepositAmount = FDCHelper.ZERO;
			}
			
			TenancyRoomPayListEntryCollection tenRoomPayList = oldTenRoom.getRoomPayList();
			
			int interruptSeq = -1;//��ʾ����,ת��ʱ���䶯���ڽضϵ������տ���ϸ�����к�
			BigDecimal reducedAppPayAmount = null;//���ضϵ����ڼ��ٵ�Ӧ�����,��ԭӦ������ȥ�ý����Ϊ�µ�Ӧ�����,���ԭӦ�����С�ڸý��,��ԭ��һ�ڵ�Ӧ������м�,��������
			for(int j=0; j<tenRoomPayList.size(); j++){
				TenancyRoomPayListEntryInfo tenRoomPay = tenRoomPayList.get(j);
				
				BigDecimal appPayAmount = tenRoomPay.getActAmount();
				
				Date payStartDate = tenRoomPay.getStartDate();
				Date payEndDate = tenRoomPay.getEndDate();
				
				BigDecimal actPayAmount = tenRoomPay.getActAmount();
				
				//����Ǳ�����ں�ĸ�����ϸ,Ӧ������Ϊ0,��ʵ�������ȫ���ӵ�Ѻ������
				if(!payStartDate.before(bizDate)){
					if(actPayAmount != null  &&  actPayAmount.compareTo(FDCHelper.ZERO) != 0){
						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount);
					}
					tenRoomPay.setAppAmount(FDCHelper.ZERO);
					if(isExecuteToDB)updateAppAmount(ctx, tenRoomPay);
				}else if(!payEndDate.after(bizDate)){//�ڱ������֮ǰ������������ϸ,���漰�޸�
					
				}else if(payStartDate.before(bizDate) && payEndDate.after(bizDate)){//���ضϵĸ�����ϸ��¼,����Ӧ�۵��Ľ��,�������յ�ʵ�����ӵ�ʣ��Ѻ����
					if(tenRoomPay.getSeq() == 1){//���к�Ϊ1�ı�ʾѺ��
						continue;
					}
					interruptSeq = tenRoomPay.getSeq();
					
					//����Ҫ�۵���Ӧ�����,Ҳ���Ǳ䶯���ڵ������ڽ�������֮��������
					reducedAppPayAmount = TenancyHelper.getRentBetweenDate(bizDate, payEndDate, oldTenRoom.getDealRentType(), oldTenRoom.getDealRoomRent());
					
					if(appPayAmount == null)appPayAmount = FDCHelper.ZERO;
					
					BigDecimal tmp = appPayAmount.subtract(reducedAppPayAmount);//����ԭӦ�������۵��Ľ��Ĳ�
					
					if(tmp.compareTo(FDCHelper.ZERO) >= 0){//����ԭӦ�������ڵ���Ҫ�۵��Ľ��
						tenRoomPay.setAppAmount(tmp);
						reducedAppPayAmount = FDCHelper.ZERO;
					}else{//����ԭӦ�������۵��Ľ��
						tenRoomPay.setAppAmount(FDCHelper.ZERO);
						reducedAppPayAmount = reducedAppPayAmount.subtract(appPayAmount);
					}
					
					if(actPayAmount != null  &&  actPayAmount.compareTo(FDCHelper.ZERO) != 0){
						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount.subtract(tenRoomPay.getAppAmount()));
					}
					
					if(isExecuteToDB)updateAppAmount(ctx, tenRoomPay);
				}else{
					logger.warn("�����ݻ����߼�����.tenRoomPay ID:" + tenRoomPay.getId().toString());
					throw new BOSException("�����ݻ����߼�����.");
				}
			}
			
			//���ض�����ԭӦ�����ʱ�����۳���Ӧ�����ʱ,Ӧ���ڱ��ض�����ǰ��������м����۳�
			for(int j=interruptSeq - 1; j>0 && reducedAppPayAmount != null && reducedAppPayAmount.compareTo(FDCHelper.ZERO) > 0; j--){
				TenancyRoomPayListEntryInfo tenRoomPayOfSeq = getTenRoomPayOfSeq(tenRoomPayList, j);
				if(tenRoomPayOfSeq == null){
					logger.warn("�����ݻ����߼�����.tenRoom ID:" + oldTenRoom.getId().toString());
					continue;
				}
				BigDecimal appAmount = tenRoomPayOfSeq.getAppAmount();
				BigDecimal actAmount = tenRoomPayOfSeq.getActAmount();
				
				if(appAmount == null)appAmount = FDCHelper.ZERO;
				
				BigDecimal tmp = appAmount.subtract(reducedAppPayAmount);//����ԭӦ�������۵��Ľ��Ĳ�
				if(tmp.compareTo(FDCHelper.ZERO) >= 0){
					tenRoomPayOfSeq.setAppAmount(tmp);
					reducedAppPayAmount = FDCHelper.ZERO;
				}else{
					tenRoomPayOfSeq.setAppAmount(FDCHelper.ZERO);
					reducedAppPayAmount = reducedAppPayAmount.subtract(appAmount);
				}
				if(actAmount != null  &&  actAmount.compareTo(FDCHelper.ZERO) != 0){
					oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actAmount.subtract(tenRoomPayOfSeq.getAppAmount()));
				}
				
				if(isExecuteToDB)updateAppAmount(ctx, tenRoomPayOfSeq);
			}
			
			if(reducedAppPayAmount != null  &&  reducedAppPayAmount.compareTo(FDCHelper.ZERO) > 0){
				throw new BOSException("�߼�����������.");
			}
			
			//����ԭ���޷����ʣ��Ѻ��
			if(oldTenRoom.getRemainDepositAmount() == null  ||  oldRoomRemainDepositAmount.compareTo(oldTenRoom.getRemainDepositAmount()) != 0){
				oldTenRoom.setRemainDepositAmount(oldRoomRemainDepositAmount);
				if(isExecuteToDB)TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(oldTenRoom, sels);
			}
			oldTenRemainDepositAmount = oldTenRemainDepositAmount.add(oldRoomRemainDepositAmount);
		}
		
		if(oldTenBill.getRemainDepositAmount() == null  ||  oldTenRemainDepositAmount.compareTo(oldTenBill.getRemainDepositAmount()) != 0){
			oldTenBill.setRemainDepositAmount(oldTenRemainDepositAmount);
			if(isExecuteToDB)updatePartial(ctx, oldTenBill, sels);
		}
		*/
    }
    
	/**
     * ��дԭ��ͬ��ԭ��ͬ���޷���ĵ��ڱ��
     * TODO ������Դ�ķ�д
     * @param ctx
     * @param tenBill
     * @param oldTenBill
     * @param targetOldBillFlag
     * 			ԭ��ͬ��Ŀ�굽�ڱ��
     * @param oldRoomFlag
     * 			ԭ��ͬ�ķ������º�ͬ����ʱ,ԭ��ͬ�����Ŀ�굽�ڱ��
     * @param oldRoomFlag2
     * 			ԭ��ͬ�ķ������º�ͬ������ʱ,ԭ��ͬ�����Ŀ�굽�ڱ��
     * */
    private void setOldTenancyFlagAtTerm(Context ctx, TenancyBillInfo tenBill, TenancyBillInfo oldTenBill, FlagAtTermEnum targetOldBillFlag, FlagAtTermEnum oldRoomFlag, FlagAtTermEnum oldRoomFlag2) throws EASBizException, BOSException{
    	TenancyRoomEntryCollection tenRooms = tenBill.getTenancyRoomList();
    	TenAttachResourceEntryCollection tenAttachs = tenBill.getTenAttachResourceList();
    	oldTenBill.setFlagAtTerm(targetOldBillFlag);
    	SelectorItemCollection updateFlagAtTermSels = new SelectorItemCollection();
    	updateFlagAtTermSels.add("flagAtTerm");
    	updatePartial(ctx, oldTenBill, updateFlagAtTermSels);
		TenancyRoomEntryCollection oldTenRooms = oldTenBill.getTenancyRoomList();
    	TenAttachResourceEntryCollection oldTenAttachs = oldTenBill.getTenAttachResourceList();
		
		for(int i=0; i<oldTenRooms.size(); i++){
			TenancyRoomEntryInfo oldTenRoom = oldTenRooms.get(i);
			String oldRoomId = oldTenRoom.getRoom().getId().toString();
			
			boolean existInNewBill = false;//ԭ��ͬ�ķ������º�ͬ���Ƿ����
			for(int j=0; j<tenRooms.size(); j++){
				TenancyRoomEntryInfo tenRoom = tenRooms.get(j);
				String roomId = tenRoom.getRoom().getId().toString();
    			if(roomId.equals(oldRoomId)){
    				existInNewBill = true;
    				break;
    			}
			}
			if(existInNewBill){
				oldTenRoom.setFlagAtTerm(oldRoomFlag);
			}else{
				oldTenRoom.setFlagAtTerm(oldRoomFlag2);
			}
			TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(oldTenRoom, updateFlagAtTermSels);
		}
		
		for(int i=0; i<oldTenAttachs.size(); i++){
			TenAttachResourceEntryInfo oldTenAttach = oldTenAttachs.get(i);
			String oldAttachId = oldTenAttach.getAttachResource().getId().toString();
			
			boolean existInNewBill = false;//ԭ��ͬ��������Դ���º�ͬ���Ƿ����
			for(int j=0; j<tenAttachs.size(); j++){
				TenAttachResourceEntryInfo tenAttach = tenAttachs.get(j);
				String attachId = tenAttach.getAttachResource().getId().toString();
    			if(attachId.equals(oldAttachId)){
    				existInNewBill = true;
    				break;
    			}
			}
			if(existInNewBill){
				oldTenAttach.setFlagAtTerm(oldRoomFlag);
			}else{
				oldTenAttach.setFlagAtTerm(oldRoomFlag2);
			}
			TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(oldTenAttach, updateFlagAtTermSels);
		}
    }

    /**
     * ����Ӧ������ֶ�
     * */
	private void updateAppAmount(Context ctx, TenancyRoomPayListEntryInfo tenRoomPay) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("appAmount");
		TenancyRoomPayListEntryFactory.getLocalInstance(ctx).updatePartial(tenRoomPay, sels);
	}
    
    /**
     * ������Ż�ø�����ϸ�����ж�Ӧ����ϸ��¼
     * */
    private TenancyRoomPayListEntryInfo getTenRoomPayOfSeq(TenancyRoomPayListEntryCollection tenRoomPayList, int seq) {
    	for(int i=0; i<tenRoomPayList.size(); i++){
			TenancyRoomPayListEntryInfo tenRoomPay = tenRoomPayList.get(i);
			if(tenRoomPay.getSeq() == seq){
				return tenRoomPay;
			}
		}
    	return null;
	}

	/**
     * ��������ʱ�Զ������ʽ��ת
     * TODO �Ȳ�ʵ���Զ���ת,�͸���һ���ڽ����ֶ���ת
     * */
	private void carryForward1(TenancyBillInfo newTenBill, TenancyBillInfo oldTenBill) {
		
		
		
		
		
		
	}
	
	/**
	 * �����ʽ��ת,��ʵ���ǰ��տ�ͺ����տ�����﷽ʽ�ύ
	 * */
	protected void _carryForward(Context ctx, IObjectCollection receivingBills) throws BOSException, EASBizException {
		FDCReceiveBillFactory.getLocalInstance(ctx).submitByCasRevColl((ReceivingBillCollection) receivingBills);
	}
	
	/** ���޺�ͬ���� */
	protected void _blankOut(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		TenancyBillInfo ten = this.getTenancyBillInfo(ctx, pk);
		
		TenancyBillStateEnum srcState = ten.getTenancyState();
		
		if(!TenancyBillStateEnum.Saved.equals(srcState) && !TenancyBillStateEnum.Submitted.equals(srcState) && 
				!TenancyBillStateEnum.Auditing.equals(srcState) && !TenancyBillStateEnum.Audited.equals(srcState) /*&& 
				!TenancyBillStateEnum.DepositReved.equals(srcState)*/ ){
			throw new BOSException("�ѱ���,���ύ,������,������,���������Ѻ��״̬�ĺ�ͬ�ſ�������");//TODO ����ҵ���쳣
		}
		
		updateTenancyState(ctx, ten, TenancyBillStateEnum.BlankOut);
		
		//�������޺�ͬ����ع�����ֹͣ
		ProcessInstInfo instInfo = null;
		ProcessInstInfo[] procInsts = null;
		IEnactmentService service = EnactmentServiceFactory.createEnactService(ctx);
		procInsts = service.getProcessInstanceByHoldedObjectId(ten.getId().toString());
		for (int j = 0; j < procInsts.length; j++) {
			if ("open.running".equals(procInsts[j].getState())) {
				instInfo = procInsts[j];
				service.abortProcessInst(instInfo.getProcInstId());
			}
		}
	}
	

	/**
	 * �ӿڷ���
	 * �����޽��ӽ��洫�����Ĳ����������յĽ��Ӳ���,��Ϊ����Ҫ��ÿ�������¼�������������ڿͻ��˽���ѭ��Զ�̵��ò���
	 * @author laiquan_luo
	 */
	protected void _handleTenancyRoom(Context ctx,IObjectCollection tenAttachEntryColl, IObjectCollection tenancyRoomEntryColl, IObjectValue billInfo,IObjectCollection handleRoomEntryColl) throws BOSException
	{
		
	

		if (tenancyRoomEntryColl == null || billInfo == null)
		{
			logger.warn("�������Ĳ�����Ϊ�յģ�����....");
			return;
		}
		
		TenancyBillInfo tenancyBillInfo = (TenancyBillInfo) billInfo;
		
	
		/*�����ÿ������������Դ��¼���д����߼������Ϻͷ����¼�Ĵ���ʽһ��*/
		for(int i=0;i< tenAttachEntryColl.size();i++)
		{
			TenAttachResourceEntryInfo tenAttachEntryInfo = (TenAttachResourceEntryInfo)tenAttachEntryColl.getObject(i);			
			BigDecimal depositAmount = tenAttachEntryInfo.getDepositAmount();
			depositAmount = depositAmount==null?new BigDecimal(0):depositAmount;
        	BigDecimal firstPayRent = tenAttachEntryInfo.getFirstPayAmount();
        	firstPayRent = firstPayRent==null?new BigDecimal(0):firstPayRent;
        	String attachid = tenAttachEntryInfo.getAttachResource().getId().toString();
        	BigDecimal rent = depositAmount.add(firstPayRent);
        	BigDecimal recRent  = this.getAttachAmount(ctx, attachid, tenancyBillInfo.getId().toString());
        	String handleType = "";
        	Date finishDate = new Date();
        	for(int j=0;j<handleRoomEntryColl.size();j++)
        	{
        		HandleRoomEntrysInfo handleRoomInfo = (HandleRoomEntrysInfo)handleRoomEntryColl.getObject(j);
        		if(handleRoomInfo.getAttach()!=null)
        		{
        			if(tenAttachEntryInfo.getId().toString().equals(handleRoomInfo.getAttach().getId().toString()))
            		{
            			tenAttachEntryInfo.setHandleState(handleRoomInfo.getNewHandleState());
            			handleType = handleRoomInfo.getHandleType();
            			finishDate = handleRoomInfo.getFinishDate();
            		}
        		}        		
        	}
        	// ��ǰ����ĺ�ͬ�� ����Ѻ������ �� ��ִ��״̬ ��˵��������������� ����
//			if ((TenancyBillStateEnum.Audited.equals(tenancyBillInfo.getTenancyState()) && recRent.compareTo(rent)>=0)
//					|| TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState()))
        	if (TenancyBillStateEnum.Audited.equals(tenancyBillInfo.getTenancyState()) 
        			|| TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState()))
			{
				/*
				 * �ֺ�ͬ���ʹ���
				 */
				// ���������ĺ�ͬ,��������ʹ���һ�ĵ�ǰ��ͬ���ɡ�
				if (TenancyContractTypeEnum.NewTenancy.equals(tenancyBillInfo
						.getTenancyType()) || handleType.equals("����"))
				{
					tenAttachEntryInfo.setActDeliveryAttachResDate(finishDate);
				}//����������
				else if(TenancyContractTypeEnum.ContinueTenancy.equals(tenancyBillInfo.getTenancyType()) && handleType.equals("���⽻��"))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("���������͵ĺ�ͬ����Ȼû���ҵ�ԭ��ͬ�����Ȳ鿴�Ƿ������ݣ��ٲ��Һ�ͬ¼���Ǳ��߼����ƣ�");
						throw new BOSException("���ֳ�������Ѽ�¼��־��");
					}
					//�����¼����������Ϊ��ͬ�Ŀ�ʼ����
					tenAttachEntryInfo.setActDeliveryAttachResDate(tenancyBillInfo.getStartDate());
					//ԭ��ͬ�����¼���շ�������Ϊԭ��ͬ�Ľ�������
					this.updateOldTenancyAttachEntryInfo(ctx,oldTenancyBillInfo,tenAttachEntryInfo,oldTenancyBillInfo.getEndDate(),"shoufang");
				}//����Ǹ���
				else if(TenancyContractTypeEnum.RejiggerTenancy.equals(tenancyBillInfo.getTenancyType()) && handleType.equals("���⽻��"))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("���������͵ĺ�ͬ����Ȼû���ҵ�ԭ��ͬ�����Ȳ鿴�Ƿ������ݣ��ٲ��Һ�ͬ¼���Ǳ��߼����ƣ�");
						throw new BOSException("���ֳ�������Ѽ�¼��־��");
					}
					//�����¼����������Ϊ��ǰ����
					tenAttachEntryInfo.setActDeliveryAttachResDate(finishDate);
					//ԭ��ͬ�����¼���շ�������Ϊ��ǰ����
					this.updateOldTenancyAttachEntryInfo(ctx,oldTenancyBillInfo,tenAttachEntryInfo,new Date(),"shoufang");
				}//�����ת�� ������һ���Ĵ���
				else if(TenancyContractTypeEnum.ChangeName.equals(tenancyBillInfo.getTenancyType()))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("���������͵ĺ�ͬ����Ȼû���ҵ�ԭ��ͬ�����Ȳ鿴�Ƿ������ݣ��ٲ��Һ�ͬ¼���Ǳ��߼����ƣ�");
						throw new BOSException("���ֳ�������Ѽ�¼��־��");
					}
					//�����¼����������Ϊ��ǰ����
					tenAttachEntryInfo.setActDeliveryAttachResDate(finishDate);
					//ԭ��ͬ�����¼���շ�������Ϊ��ǰ����
					this.updateOldTenancyAttachEntryInfo(ctx,oldTenancyBillInfo,tenAttachEntryInfo,new Date(),"shoufang");
				}
				else
				{
					logger.warn("û�д�������͵Ľ���...");
					throw new BOSException("û�д�������͵Ľ���");
				}
				//����ʱ��
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryAttachResDate");
				selector.add("handleState");
				try
				{
					TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttachEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
				this.setAttachTenState(ctx,tenAttachEntryInfo.getAttachResource(),tenAttachEntryInfo.getTenAttachState());
			}//����Ǹ�����״̬�϶����˷�����������������º�ͬ����û�а취���н��Ӷ����ġ���������Ҳ����Ҫ��֤��
			else if(TenancyBillStateEnum.RejiggerTenancying.equals(tenancyBillInfo.getTenancyState()))
			{
				//�����¼�շ�������Ϊ��ǰ����
				tenAttachEntryInfo.setActQuitTenDate(finishDate);
				//�����¼�Ľ���������Ϊ��ǰ����
				this.setAttachTenState(ctx,tenAttachEntryInfo.getAttachResource(),TenancyStateEnum.waitTenancy);
				//���·�Դ״̬
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//����ʱ��
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryAttachResDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttachEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}else if(TenancyBillStateEnum.ContinueTenancying.equals(tenancyBillInfo.getTenancyState()))
			{
				//�����¼�շ���������Ϊ��ͬ�Ľ�������
				tenAttachEntryInfo.setActQuitTenDate(finishDate);
				//ԭ��ͬ�����¼���շ�������Ϊԭ��ͬ�Ľ�������
				this.setAttachTenState(ctx,tenAttachEntryInfo.getAttachResource(),TenancyStateEnum.waitTenancy);
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//����ʱ��
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryAttachResDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttachEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
				//�����͸������һ��
			}else if(TenancyBillStateEnum.ChangeNaming.equals(tenancyBillInfo.getTenancyState()))
			{
				//�����¼�շ�������Ϊ��ǰ����
				tenAttachEntryInfo.setActQuitTenDate(finishDate);
				//�����¼�Ľ���������Ϊ��ǰ����
				this.setAttachTenState(ctx,tenAttachEntryInfo.getAttachResource(),TenancyStateEnum.waitTenancy);
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//����ʱ��
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryAttachResDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttachEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}else if(TenancyBillStateEnum.QuitTenancying.equals(tenancyBillInfo.getTenancyState()))
			{
				//�����¼�շ�������Ϊ��ǰ����
				tenAttachEntryInfo.setActQuitTenDate(finishDate);
				//�����¼�Ľ���������Ϊ��ǰ����
				this.setAttachTenState(ctx,tenAttachEntryInfo.getAttachResource(),TenancyStateEnum.waitTenancy);
				//����ʱ��
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttachEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}
		}
		/*
		 * �ֱ�ȥ��ÿ�������¼���д�������������һЩ ����״̬����ʱ���һЩ���ã�
		 * ������Щ�Զ��˷��ģ��ĺ�ͬ״̬�����߰���Ĳ����������ﴦ�����֮���ڿͻ��˽�����֤���ٲ���������...
		 */
		for (int i = 0; i < tenancyRoomEntryColl.size(); i++)
		{
			TenancyRoomEntryInfo tenancyRoomEntryInfo = (TenancyRoomEntryInfo) tenancyRoomEntryColl.getObject(i);
			DealAmountEntryCollection dealColl = tenancyRoomEntryInfo.getDealAmounts();
			BigDecimal depositAmount = new BigDecimal(0);
			for(int j=0;j<dealColl.size();j++)
			{
				DealAmountEntryInfo dealInfo = dealColl.get(j);
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("*");
				sel.add("moneyDefine.*");
				try {
					dealInfo = DealAmountEntryFactory.getLocalInstance(ctx).getDealAmountEntryInfo(new ObjectUuidPK(dealInfo.getId()), sel);
				} catch (EASBizException e) {
					e.printStackTrace();
				}
				if(MoneyTypeEnum.DepositAmount.equals(dealInfo.getMoneyDefine().getMoneyType()))
				{
					BigDecimal amount = dealInfo.getAmount();
					amount = amount==null?new BigDecimal(0):amount;
					depositAmount = depositAmount.add(amount);
				}
			}
//			BigDecimal depositAmount = tenancyRoomEntryInfo.getDepositAmount();
//			depositAmount = depositAmount==null?new BigDecimal(0):depositAmount;
        	BigDecimal firstPayRent = tenancyRoomEntryInfo.getFirstPayAmount();
        	firstPayRent = firstPayRent==null?new BigDecimal(0):firstPayRent;
        	String roomid = tenancyRoomEntryInfo.getRoom().getId().toString();
        	BigDecimal rent = depositAmount.add(firstPayRent);
        	BigDecimal recRent = this.getAmount(ctx,roomid,tenancyBillInfo.getId().toString());
        	String handleType = new String();
        	Date finishDate = new Date();
        	for(int j=0;j<handleRoomEntryColl.size();j++)
        	{
        		HandleRoomEntrysInfo handleRoomInfo = (HandleRoomEntrysInfo)handleRoomEntryColl.getObject(j);
        		if(handleRoomInfo.getTenancyRoom()!=null)
        		{
        			if(tenancyRoomEntryInfo.getId().toString().equals(handleRoomInfo.getTenancyRoom().getId().toString()))
            		{
            			tenancyRoomEntryInfo.setHandleState(handleRoomInfo.getNewHandleState());
            			handleType = handleRoomInfo.getHandleType();
            			finishDate = handleRoomInfo.getFinishDate();
            		}
        		}      		
        	}
			// ��ǰ����ĺ�ͬ�� ����Ѻ������ �� ��ִ��״̬ ��˵��������������� ����
//			if ((TenancyBillStateEnum.Audited.equals(tenancyBillInfo.getTenancyState()) && recRent.compareTo(rent)>=0)
//					|| TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState()))
				if (TenancyBillStateEnum.Audited.equals(tenancyBillInfo.getTenancyState())
						|| TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState()))
			{
				/*
				 * �ֺ�ͬ���ʹ���
				 */
				// ���������ĺ�ͬ,��������ʹ���һ�ĵ�ǰ��ͬ���ɡ�
				if (TenancyContractTypeEnum.NewTenancy.equals(tenancyBillInfo
						.getTenancyType()) || handleType.equals("����"))
				{
					tenancyRoomEntryInfo.setActDeliveryRoomDate(finishDate);
				}//����������
				else if(TenancyContractTypeEnum.ContinueTenancy.equals(tenancyBillInfo.getTenancyType()) && handleType.equals("���⽻��"))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("���������͵ĺ�ͬ����Ȼû���ҵ�ԭ��ͬ�����Ȳ鿴�Ƿ������ݣ��ٲ��Һ�ͬ¼���Ǳ��߼����ƣ�");
						throw new BOSException("���ֳ�������Ѽ�¼��־��");
					}
					//�����¼����������Ϊ��ͬ�Ŀ�ʼ����
					tenancyRoomEntryInfo.setActDeliveryRoomDate(tenancyBillInfo.getStartDate());
					//ԭ��ͬ�����¼���շ�������Ϊԭ��ͬ�Ľ�������
					this.updateOldTenancyRoomEntryInfo(ctx,oldTenancyBillInfo,tenancyRoomEntryInfo,oldTenancyBillInfo.getEndDate(),"shoufang");
				}//����Ǹ���
				else if(TenancyContractTypeEnum.RejiggerTenancy.equals(tenancyBillInfo.getTenancyType()) && handleType.equals("���⽻��"))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("���������͵ĺ�ͬ����Ȼû���ҵ�ԭ��ͬ�����Ȳ鿴�Ƿ������ݣ��ٲ��Һ�ͬ¼���Ǳ��߼����ƣ�");
						throw new BOSException("���ֳ�������Ѽ�¼��־��");
					}
					//�����¼����������Ϊ��ǰ����
					tenancyRoomEntryInfo.setActDeliveryRoomDate(finishDate);
					//ԭ��ͬ�����¼���շ�������Ϊ��ǰ����
					this.updateOldTenancyRoomEntryInfo(ctx,oldTenancyBillInfo,tenancyRoomEntryInfo,new Date(),"shoufang");
				}//�����ת�� ������һ���Ĵ���
				else if(TenancyContractTypeEnum.ChangeName.equals(tenancyBillInfo.getTenancyType()))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("���������͵ĺ�ͬ����Ȼû���ҵ�ԭ��ͬ�����Ȳ鿴�Ƿ������ݣ��ٲ��Һ�ͬ¼���Ǳ��߼����ƣ�");
						throw new BOSException("���ֳ�������Ѽ�¼��־��");
					}
					//�����¼����������Ϊ��ǰ����
					tenancyRoomEntryInfo.setActDeliveryRoomDate(finishDate);
					//ԭ��ͬ�����¼���շ�������Ϊ��ǰ����
					this.updateOldTenancyRoomEntryInfo(ctx,oldTenancyBillInfo,tenancyRoomEntryInfo,new Date(),"shoufang");
				}//����Ǽ۸��� ��ת��һ���Ĵ���
				else if(TenancyContractTypeEnum.PriceChange.equals(tenancyBillInfo.getTenancyType()))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("���������͵ĺ�ͬ����Ȼû���ҵ�ԭ��ͬ�����Ȳ鿴�Ƿ������ݣ��ٲ��Һ�ͬ¼���Ǳ��߼����ƣ�");
						throw new BOSException("���ֳ�������Ѽ�¼��־��");
					}
					//�����¼����������Ϊ��ǰ����
					tenancyRoomEntryInfo.setActDeliveryRoomDate(finishDate);
					//ԭ��ͬ�����¼���շ�������Ϊ��ǰ����
					this.updateOldTenancyRoomEntryInfo(ctx,oldTenancyBillInfo,tenancyRoomEntryInfo,new Date(),"shoufang");
				}
				else
				{
					logger.warn("û�д�������͵Ľ���...");
					throw new BOSException("û�д�������͵Ľ���");
				}
				//����ʱ��
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("handleState");
				try
				{
					TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
				this.setRoomTenancyState(ctx,tenancyRoomEntryInfo.getRoom(),tenancyRoomEntryInfo.getTenRoomState(), tenancyBillInfo);
			}
			//����Ǹ�����״̬�϶����˷�����������������º�ͬ����û�а취���н��Ӷ����ġ���������Ҳ����Ҫ��֤��
			else if(TenancyBillStateEnum.RejiggerTenancying.equals(tenancyBillInfo.getTenancyState()))
			{
				//�����¼�շ�������Ϊ��ǰ����
				tenancyRoomEntryInfo.setActQuitTenDate(finishDate);
				//�����¼�Ľ���������Ϊ��ǰ����
				this.setRoomTenancyState(ctx,tenancyRoomEntryInfo.getRoom(),TenancyStateEnum.waitTenancy, null);
				//���·�Դ״̬
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//����ʱ��
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}else if(TenancyBillStateEnum.ContinueTenancying.equals(tenancyBillInfo.getTenancyState()))
			{
				//�����¼�շ���������Ϊ��ͬ�Ľ�������
				tenancyRoomEntryInfo.setActQuitTenDate(finishDate);
				//ԭ��ͬ�����¼���շ�������Ϊԭ��ͬ�Ľ�������
				this.setRoomTenancyState(ctx,tenancyRoomEntryInfo.getRoom(),TenancyStateEnum.waitTenancy, null);
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//����ʱ��
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
				//�����͸������һ��
			}else if(TenancyBillStateEnum.ChangeNaming.equals(tenancyBillInfo.getTenancyState()))
			{
				//�����¼�շ�������Ϊ��ǰ����
				tenancyRoomEntryInfo.setActQuitTenDate(finishDate);
				//�����¼�Ľ���������Ϊ��ǰ����
				this.setRoomTenancyState(ctx,tenancyRoomEntryInfo.getRoom(),TenancyStateEnum.waitTenancy, null);
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//����ʱ��
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}else if(TenancyBillStateEnum.PriceChanging.equals(tenancyBillInfo.getTenancyState()))
			{
				//�����¼�շ�������Ϊ��ǰ����
				tenancyRoomEntryInfo.setActQuitTenDate(finishDate);
				//�����¼�Ľ���������Ϊ��ǰ����
				this.setRoomTenancyState(ctx,tenancyRoomEntryInfo.getRoom(),TenancyStateEnum.waitTenancy, null);
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//����ʱ��
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}else if(TenancyBillStateEnum.QuitTenancying.equals(tenancyBillInfo.getTenancyState()))
			{
				//�����¼�շ�������Ϊ��ǰ����
				tenancyRoomEntryInfo.setActQuitTenDate(finishDate);
				//�����¼�Ľ���������Ϊ��ǰ����
				this.setRoomTenancyState(ctx,tenancyRoomEntryInfo.getRoom(),TenancyStateEnum.waitTenancy, null);
				//����ʱ��
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}
		}
	}
	
	/**
	 * �޸ķ�Դ���淿���״̬
	 * @param ctx
	 * @param room
	 * @param tenancyState
	 * @param lastTen
	 * @author laiquan_luo
	 * @throws BOSException 
	 * ���ӶԷ���lastTenancy�ķ�д   modified by zhicheng_jin
	 */
	public void setRoomTenancyState(Context ctx,RoomInfo room,TenancyStateEnum tenancyState, TenancyBillInfo lastTen) throws BOSException
	{
		SelectorItemCollection selColl = new SelectorItemCollection();
		
		selColl.add("tenancyState");
		selColl.add("lastTenancy");
		room.setTenancyState(tenancyState);
		room.setLastTenancy(lastTen);
		try
		{
			RoomFactory.getLocalInstance(ctx).updatePartial(room,selColl);
		} catch (EASBizException e)
		{
			throw new BOSException(e);
		} catch (BOSException e)
		{
			throw new BOSException(e);
		}
	}
	
	/**
	 * �޸ķ�Դ����������Դ��״̬
	 * @param ctx
	 * @param room
	 * @param tenancyState
	 * @param lastTen
	 * @author laiquan_luo
	 * @throws BOSException 
	 * ���ӶԷ���lastTenancy�ķ�д   modified by zhicheng_jin
	 */
	public void setAttachTenState(Context ctx,AttachResourceInfo attachInfo,TenancyStateEnum tenancyState) throws BOSException
	{
		SelectorItemCollection selColl = new SelectorItemCollection();
		
		selColl.add("tenancyState");
		attachInfo.setTenancyState(tenancyState);
		try
		{
			AttachResourceFactory.getLocalInstance(ctx).updatePartial(attachInfo,selColl);
		} catch (EASBizException e)
		{
			throw new BOSException(e);
		} catch (BOSException e)
		{
			throw new BOSException(e);
		}
	}
	
	/**
	 * ���¸÷����������Ӧ��ԭ��ͬ�ϵķ����¼�Ľ��������շ����� 
	 * @param oldTenancyBillInfo
	 * @param tenancyRoomEntryInfo
	 * @param date
	 * @param type  �޶�Ϊ�ַ���  jiaofang   shoufang
	 * @throws BOSException 
	 * @author laiquan_luo
	 */
	private void updateOldTenancyAttachEntryInfo(Context ctx,TenancyBillInfo oldTenancyBillInfo,TenAttachResourceEntryInfo tenAttachEntryInfo,Date date,String type) throws BOSException
	{
		if(oldTenancyBillInfo == null || tenAttachEntryInfo == null || date == null || type == null)
		{
			logger.warn("�������Ĳ�����Ϊ�յģ�����....");
			return;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",oldTenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("attachResource.id",tenAttachEntryInfo.getAttachResource().getId().toString()));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		//�������ݴ��
		TenAttachResourceEntryCollection oldTenAttachEntryColl = new TenAttachResourceEntryCollection();
		
		oldTenAttachEntryColl = TenAttachResourceEntryFactory.getLocalInstance(ctx).getTenAttachResourceEntryCollection(view);
		//������ִ���һ�������󣬵��ǻ���Ϊ0������
		if(oldTenAttachEntryColl.size() > 1)
		{
			logger.warn("��������Դ��Դ��ͬ�����ҵ��˶��������Դ��¼��Ϣ...");
			throw new BOSException("�����߼������Ѽ�¼��־��");
		}
		TenAttachResourceEntryInfo oldTenAttachEntryInfo = oldTenAttachEntryColl.get(0);
		SelectorItemCollection selectorItemColl = new SelectorItemCollection();
		//�շ�ʱ��
		if("shoufang".equalsIgnoreCase(type))
		{
			oldTenAttachEntryInfo.setActQuitTenDate(date);
			selectorItemColl.add("actQuitTenDate");
			oldTenAttachEntryInfo.setHandleState(HandleStateEnum.AlreadyCallBack);
			selectorItemColl.add("handleState");
		}//����
		else if("jiaofang".equalsIgnoreCase(type))
		{
			oldTenAttachEntryInfo.setActDeliveryAttachResDate(date);
			selectorItemColl.add("actDeliveryRoomDate");
			oldTenAttachEntryInfo.setHandleState(HandleStateEnum.livingHouse);
			selectorItemColl.add("handleState");
		}
		try
		{
			TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(oldTenAttachEntryInfo,selectorItemColl);
		} catch (EASBizException e)
		{	
			throw new BOSException(e);
		} 
		
	}
	
	/**
	 * ���¸÷����������Ӧ��ԭ��ͬ�ϵķ����¼�Ľ��������շ����� 
	 * @param oldTenancyBillInfo
	 * @param tenancyRoomEntryInfo
	 * @param date
	 * @param type  �޶�Ϊ�ַ���  jiaofang   shoufang
	 * @throws BOSException 
	 * @author laiquan_luo
	 */
	private void updateOldTenancyRoomEntryInfo(Context ctx,TenancyBillInfo oldTenancyBillInfo,TenancyRoomEntryInfo tenancyRoomEntryInfo,Date date,String type) throws BOSException
	{
		if(oldTenancyBillInfo == null || tenancyRoomEntryInfo == null || date == null || type == null)
		{
			logger.warn("�������Ĳ�����Ϊ�յģ�����....");
			return;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",oldTenancyBillInfo.getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("room.id",tenancyRoomEntryInfo.getRoom().getId().toString()));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		//�������ݴ��
		TenancyRoomEntryCollection oldTenancyRoomEntryColl = new TenancyRoomEntryCollection();
		
		oldTenancyRoomEntryColl = TenancyRoomEntryFactory.getLocalInstance(ctx).getTenancyRoomEntryCollection(view);
		//������ִ���һ�������󣬵��ǻ���Ϊ0������
//		if(oldTenancyRoomEntryColl.size() > 1)
//		{
//			logger.warn("�÷�����Դ��ͬ�����ҵ��˶�������¼��Ϣ...");
//			throw new BOSException("�����߼������Ѽ�¼��־��");
//		}
		//���º�ͬ�����佻��ʱ��ǿ���Եİ��Ϻ�ͬ�ķ������շ����� eric_wang 2010.08.31
		for(int i=0;i<oldTenancyRoomEntryColl.size();i++){
			TenancyRoomEntryInfo oldTenancyRoomEntryInfo = oldTenancyRoomEntryColl.get(i);
			SelectorItemCollection selectorItemColl = new SelectorItemCollection();
			//�շ�ʱ��
			if("shoufang".equalsIgnoreCase(type))
			{
				oldTenancyRoomEntryInfo.setActQuitTenDate(date);
				selectorItemColl.add("actQuitTenDate");
				oldTenancyRoomEntryInfo.setHandleState(HandleStateEnum.AlreadyCallBack);
				selectorItemColl.add("handleState");
			}//����
			else if("jiaofang".equalsIgnoreCase(type))
			{
				oldTenancyRoomEntryInfo.setActDeliveryRoomDate(date);
				selectorItemColl.add("actDeliveryRoomDate");
				oldTenancyRoomEntryInfo.setHandleState(HandleStateEnum.livingHouse);
				selectorItemColl.add("handleState");
			}
			try
			{
				TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(oldTenancyRoomEntryInfo,selectorItemColl);
			} catch (EASBizException e)
			{	
				throw new BOSException(e);
			} 
		}
		
		
	}
	
	/**
	 * ���¸÷����������Ӧ���º�ͬ�ϵķ����¼�Ľ��������շ�����
	 * @param ctx
	 * @param newTenancyBillInfo
	 * @param tenancyRoomEntryInfo
	 * @param date
	 * @param type
	 * @throws BOSException
	 * @author laiquan_luo
	 */
	private void updateNewTenancyRoomEntryInfo(Context ctx,TenancyBillInfo newTenancyBillInfo,TenancyRoomEntryInfo tenancyRoomEntryInfo,Date date,String type) throws BOSException
	{
		if(newTenancyBillInfo == null || tenancyRoomEntryInfo == null || date == null || type == null)
		{
			logger.warn("�������Ĳ�����Ϊ�յģ�����....");
			throw new BOSException("����Ϊ�գ�");
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",newTenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("room.id",tenancyRoomEntryInfo.getRoom().getId().toString()));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		TenancyRoomEntryCollection newTenancyRoomEntryColl = null;
		
		newTenancyRoomEntryColl = TenancyRoomEntryFactory.getLocalInstance(ctx).getTenancyRoomEntryCollection(view);
		//������ִ���һ�������󣬵��ǻ���Ϊ0������
		if(newTenancyRoomEntryColl.size() > 1)
		{
			logger.warn("�÷�����Դ��ͬ�����ҵ��˶�������¼��Ϣ...");
			throw new BOSException("�����߼������Ѽ�¼��־��");
		}
		//Ϊ0��ʱ��֤����ԭ��ͬ�е�ʱ�䣬���º�ͬ��һ���У��Ͳ����Բ�������Ĳ����ˡ�
		if(newTenancyRoomEntryColl.get(0) == null)
			return;
		
		TenancyRoomEntryInfo newTenancyRoomEntryInfo = newTenancyRoomEntryColl.get(0);
		SelectorItemCollection selectorItemColl = new SelectorItemCollection();
		//�շ�ʱ��
		if("shoufang".equalsIgnoreCase(type))
		{
			newTenancyRoomEntryInfo.setActQuitTenDate(date);
			selectorItemColl.add("actQuitTenDate");
		}//����
		else if("jiaofang".equalsIgnoreCase(type))
		{
			newTenancyRoomEntryInfo.setActDeliveryRoomDate(date);
			selectorItemColl.add("actDeliveryRoomDate");
		}
		try
		{
			TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(newTenancyRoomEntryInfo,selectorItemColl);
		} catch (EASBizException e)
		{	
			throw new BOSException(e);
		} 
	}
	
	/**
	 * �޸ķ�Դ�Ǳ߷����״̬�����״̬���޸��Ǹ����º�ͬ����������¼��״̬
	 * @param ctx
	 * @param newTenancyBillInfo
	 * @param tenancyRoomEntryInfo
	 * @throws BOSException
	 * @author laiquan_luo
	 */
	private void updateRoomStateByNewTenancyRoomEntryInfo(Context ctx,TenancyBillInfo newTenancyBillInfo,TenancyRoomEntryInfo tenancyRoomEntryInfo) throws BOSException
	{
		if(newTenancyBillInfo == null || tenancyRoomEntryInfo == null)
		{
			logger.warn("�������Ĳ�����Ϊ�յģ�����....");
			throw new BOSException("����Ϊ�գ�");
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",newTenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("room.id",tenancyRoomEntryInfo.getRoom().getId().toString()));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("room.tenancyState");
		view.setFilter(filter);
		//�������ݴ��
		TenancyRoomEntryCollection newTenancyRoomEntryColl = null;
		
		newTenancyRoomEntryColl = TenancyRoomEntryFactory.getLocalInstance(ctx).getTenancyRoomEntryCollection(view);
		//������ִ���һ�������󣬵��ǻ���Ϊ0������
		if(newTenancyRoomEntryColl.size() > 1)
		{
			logger.warn("�÷�����Դ��ͬ�����ҵ��˶�������¼��Ϣ...");
			throw new BOSException("�����߼������Ѽ�¼��־��");
		}
		//�º�ͬ�������û���������
		if(newTenancyRoomEntryColl.get(0) == null)
			return;
		TenancyRoomEntryInfo newTenancyRoomEntryInfo = newTenancyRoomEntryColl.get(0);
		
		this.setRoomTenancyState(ctx,newTenancyRoomEntryInfo.getRoom(),newTenancyRoomEntryInfo.getTenRoomState(), newTenancyBillInfo);
	}
	
	/**
	 * ���ݾɵĺ�ͬȥ����һ���µĺ�ͬ���º�ͬ��״̬ʱ�ѽ�Ѻ�������ִ��״̬��
	 * @param oldTenancyBillInfo
	 * @return
	 * @throws BOSException 
	 * @author laiquan_luo
	 * �����ͬ������,ԭ��ͬ��һ�������ڸ����ͬ�в�����,���Ѿ������˷���.��������Ҳ��ѯ�� ����״̬�ĺ�ͬ,�ٸ��ݺ�ͬ״̬���п���  by zhicheng_jin
	 */
	private TenancyBillInfo getNewTenancyBillByOldTenancyBill(Context ctx,TenancyBillInfo oldTenancyBillInfo) throws BOSException
	{
		if(oldTenancyBillInfo == null)
		{
			logger.warn("����Ϊ��...");
			throw new BOSException("�������Ϊ�գ��Ѽ�¼��־");
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("oldTenancyBill.id",oldTenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.AUDITED_VALUE));
		//filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.DEPOSITREVED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.PARTEXECUTED_VALUE));
		//filter.setMaskString("#0 and (#1 or #2 or #3))");
		filter.setMaskString("#0 and (#1 or #2))");
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("tenancyRoomList.room.id");
		view.setFilter(filter);
		
		TenancyBillCollection tenancyBillColl = TenancyBillFactory.getLocalInstance(ctx).getTenancyBillCollection(view);
		
		if(tenancyBillColl.size() > 1)
		{
			logger.warn("�ҵ����ǰ�ִ��״̬��Ѻ������״̬�Ķ����ͬ...");
			throw new BOSException("�����߼������Ѽ�¼��־��");
		}
		return tenancyBillColl.get(0);
	}
	
	/**
	 * �жϸú�ͬ�Ƿ��������״̬�����ⵥ
	 * @param ctx
	 * @param bill
	 * @return
	 * @throws BOSException
	 * @author laiquan_luo
	 */
	private boolean isExistQuitTenancyBill(Context ctx,TenancyBillInfo bill) throws BOSException
	{
		if(bill == null)
		{
			logger.warn("�������Ϊ��....");
			throw new BOSException("�������Ϊ�գ�");
		}
		boolean debug = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",bill.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		
		try
		{
			if(QuitTenancyFactory.getLocalInstance(ctx).exists(filter))
				debug = true;
			else
				debug = false;
		} catch (EASBizException e)
		{
			throw new BOSException(e);
		} 
		return debug;
	}
	
	private BigDecimal getAmount(Context ctx,String roomid,String tenancyBillID)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select room.fid roomID,sum(fdcEntry.famount) sumRent from t_she_fdcreceivebillentry fdcEntry ");
    	builder.appendSql(" left join t_cas_receivingbill cas on cas.fid=fdcentry.FReceivingBillID ");
    	builder.appendSql(" left join T_SHE_FDCReceiveBill fdc on cas.FFDCReceivebillid=fdc.fid ");
    	builder.appendSql(" left join t_she_room room on fdc.froomid=room.fid ");
    	builder.appendSql(" left join t_ten_tenancybill tenbill on fdc.FTenancyContractID = tenbill.fid ");
    	builder.appendSql(" left join t_she_moneyDefine money on fdcentry.FMoneyDefineId=money.fid ");
    	builder.appendSql(" where  tenbill.fid = '"+tenancyBillID+"' and room.fid ='"+roomid+"'");
    	builder.appendSql(" and (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount') and money.FSysType ='TenancySys'");
    	builder.appendSql(" group by room.fid ");
    	IRowSet set = null;
    	BigDecimal sumRent =  new BigDecimal(0);
		try {
			set = SQLExecutorFactory.getLocalInstance(ctx, builder.getSql())
			.executeSQL();
			while(set.next())
			{
				sumRent = set.getBigDecimal("sumRent");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return sumRent;
	}

	private BigDecimal getAttachAmount(Context ctx,String attachid,String tenancyBillID)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select attach.fid attachid,sum(fdcEntry.famount) sumRent from t_she_fdcreceivebillentry fdcEntry ");
    	builder.appendSql(" left join t_cas_receivingbill cas on cas.fid=fdcentry.FReceivingBillID ");
    	builder.appendSql(" left join T_SHE_FDCReceiveBill fdc on cas.FFDCReceivebillid=fdc.fid ");
    	builder.appendSql(" left join t_ten_TenAttachResourceEntry tenEntry on fdc.ftenAttachID=tenEntry.fid ");
    	builder.appendSql(" left join t_ten_attachResource attach on tenEntry.FAttachResourceID=attach.fid ");
    	builder.appendSql(" left join t_ten_tenancybill tenbill on fdc.FTenancyContractID = tenbill.fid ");
    	builder.appendSql(" left join t_she_moneyDefine money on fdcentry.FMoneyDefineId=money.fid ");
    	builder.appendSql(" where  tenbill.fid = '"+tenancyBillID+"' and attach.fid ='"+attachid+"'");
    	builder.appendSql(" and (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount') and money.FSysType ='TenancySys'");
    	builder.appendSql(" group by attach.fid ");
    	IRowSet set = null;
    	BigDecimal sumRent =  new BigDecimal(0);
		try {
			set = SQLExecutorFactory.getLocalInstance(ctx, builder.getSql())
			.executeSQL();
			while(set.next())
			{
				sumRent = set.getBigDecimal("sumRent");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return sumRent;
	}

	protected void _repairStartDate(Context ctx, IObjectValue tenancyBillInfo,
			Date repairStartDate, FirstLeaseTypeEnum firstLease,
			Date firstLeaseDate) throws BOSException, EASBizException {
		//��д���޿�ʼ���ڽ������ڣ��������ͺ������������
		TenancyBillInfo tenancyBill = (TenancyBillInfo) tenancyBillInfo;
		tenancyBill = TenancyHelper.getTenancyBillInfo(ctx, tenancyBill.getId().toString());
		BigDecimal leaseCount = tenancyBill.getLeaseCount();
		int leaseTime = tenancyBill.getLeaseTime();
		int totalMonthes = leaseTime*leaseCount.intValue();
		Date tmpDate = TenancyHelper.addCalendar(repairStartDate, Calendar.MONTH, totalMonthes);
		Date endDate = TenancyHelper.addCalendar(tmpDate, Calendar.DATE, -1);
		tenancyBill.setStartDate(repairStartDate);
		tenancyBill.setEndDate(endDate);
		tenancyBill.setFirstLeaseType(firstLease);
		tenancyBill.setFirstLeaseEndDate(firstLeaseDate);
		
		//��д����ɽ�����¼
		TenancyRoomEntryCollection tenRooms = tenancyBill.getTenancyRoomList();
		TenAttachResourceEntryCollection tenAttaches = tenancyBill.getTenAttachResourceList();
		for(int i=0;i<tenRooms.size();i++)
		{
			TenancyRoomEntryInfo tenRoomInfo = tenRooms.get(i);
			DealAmountEntryCollection dealColl = tenRoomInfo.getDealAmounts();
			for(int j=0;j<dealColl.size();j++)
			{
				DealAmountEntryInfo dealInfo = dealColl.get(j);
				dealInfo.setStartDate(repairStartDate);
				dealInfo.setEndDate(endDate);
				//TODO Ŀǰ��¼��ʼ����ʱ����Ҫ��������������������á�����������Ҫ�޸�
			}
		}
		
		// ��д������Դ���������ϸ.������Դ��Ŀǰ�ƺ�û��
		for (int i = 0; i < tenAttaches.size(); i++) {
			// �õ�������Դ
			TenAttachResourceEntryInfo tenAttaInfo = (TenAttachResourceEntryInfo) tenAttaches.get(i);
			for (int j = 0; j < tenAttaInfo.getDealAmounts().size(); j++) {
				// �õ�������Դ�������ϸ
				AttachDealAmountEntryInfo attaDealInfo = (AttachDealAmountEntryInfo) tenAttaInfo.getDealAmounts().get(j);
				attaDealInfo.setStartDate(repairStartDate);
				attaDealInfo.setEndDate(endDate);
			}
		}
		
		//��д����ĸ�����ϸ
		FirstLeaseTypeEnum firstLeaseType = tenancyBill.getFirstLeaseType();
		Date firstLeaseEndDate = tenancyBill.getFirstLeaseEndDate();
		
		List leaseList = TenancyHelper.getLeaseList(repairStartDate, endDate, firstLeaseType, firstLeaseEndDate, leaseTime);
		
		ChargeDateTypeEnum chargeDateType = tenancyBill.getChargeDateType();
		int chargeOffsetDays = tenancyBill.getChargeOffsetDays();
		
		RentCountTypeEnum rentCountType = tenancyBill.getRentCountType();
		int daysPerYear = tenancyBill.getDaysPerYear() == 0 ? 360 : tenancyBill.getDaysPerYear();
		boolean isToInteger = tenancyBill.isIsAutoToInteger();
		ToIntegerTypeEnum toIntegerType = tenancyBill.getToIntegerType();
		DigitEnum digit  = tenancyBill.getDigit();
		
		// ����������������Է��� eric_wang 2010.08.25
		boolean isToIntegerFee = tenancyBill.isIsAutoToIntegerFee();
		ToIntegerTypeEnum toIntegerTypeFee = tenancyBill.getToIntegetTypeFee();
		DigitEnum digitFee  = tenancyBill.getDigitFee();
		
		MoneyDefineInfo depositMoney = getMoneyDefine(ctx, MoneyTypeEnum.DepositAmount);
		MoneyDefineInfo rentMoney = getMoneyDefine(ctx, MoneyTypeEnum.RentAmount);
		RentFreeEntryCollection rentFress = tenancyBill.getRentFrees();
		
		try {
			TenancyHelper.fillTenEntryPayList(tenRooms, TenancyRoomPayListEntryCollection.class, TenancyRoomPayListEntryInfo.class, 
					depositMoney, rentMoney, leaseList, rentFress, rentCountType, daysPerYear, 
					isToInteger, toIntegerType, digit,isToIntegerFee, toIntegerTypeFee, digitFee, chargeDateType, chargeOffsetDays,tenancyBill.getFristRevDate(),tenancyBill.getFixedDateFromMonth(),tenancyBill.getLeaseTime());
			
			TenancyHelper.fillTenEntryPayList(tenAttaches, TenAttachResourcePayListEntryCollection.class, TenAttachResourcePayListEntryInfo.class, 
					depositMoney, rentMoney, leaseList, rentFress, rentCountType, daysPerYear, 
					isToInteger, toIntegerType, digit,isToIntegerFee, toIntegerTypeFee, digitFee, chargeDateType, chargeOffsetDays,tenancyBill.getFristRevDate(),tenancyBill.getFixedDateFromMonth(),tenancyBill.getLeaseTime());
		} catch (InstantiationException e) {
			throw new BOSException(e);
		} catch (IllegalAccessException e) {
			throw new BOSException(e);
		}
		
		//ע�⣬����ֻ�ܵ��û�����ύ
		super._submit(ctx, tenancyBill);
	}
	
	private MoneyDefineInfo getMoneyDefine(Context ctx, MoneyTypeEnum moneyType) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("moneyType", moneyType));
		view.setFilter(filter);
		MoneyDefineCollection moneyNames = MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineCollection(view);
		if (!moneyNames.isEmpty()) {
			return moneyNames.get(0);
		} else {
			return null;
		}
	}
	
	/**���޺�ͬ ������*/
	protected void _antiAudit(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		super._unAudit(ctx, billID);
		TenancyBillInfo tenBill = TenancyHelper.getTenancyBillInfo(ctx, billID.toString());
		tenBill.setTenancyState(TenancyBillStateEnum.Submitted);
		SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("tenancyState");
		_updatePartial(ctx, tenBill, updateSels);

	}
}