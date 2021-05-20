/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.calc.CalculatorDialog;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomAreaCompensateBatchEditUI extends
		AbstractRoomAreaCompensateBatchEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomAreaCompensateBatchEditUI.class);

	String type = null;

	private String number="";
	/**
	 * output class constructor
	 */
	public RoomAreaCompensateBatchEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void loadFields() {
		super.loadFields();
		if (getUIContext().get("type") != null) {
			type = getUIContext().get("type").toString();
			tblRoomInfo.checkParsed();
			if (type.equals("scheme")) {
				tblRoomInfo.getColumn("compensateAmt").getStyleAttributes()
						.setLocked(false);
				prmtCompensateScheme.setRequired(true);
				tblRoomInfo.getColumn("compensateRate").getStyleAttributes().setLocked(true);
			} else {
				tblRoomInfo.getColumn("compensateAmt").getStyleAttributes()
						.setLocked(false);
				prmtCompensateScheme.setEnabled(false);
				actionCalc.setEnabled(false);
			}
		}
	}

	protected IObjectValue createNewData() {
		RoomAreaCompensateInfo objectValue = new RoomAreaCompensateInfo();
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomAreaCompensateFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		java.util.List roomIds = (java.util.List) getUIContext().get("roomIds");
		String rooIDList = null;
		for (int i = 0, size = roomIds.size(); i < size; i++) {
			String id = roomIds.get(i).toString();
			if (rooIDList == null)
				rooIDList = "" + "'" + id + "'";
			else
				rooIDList = rooIDList + ",'" + id + "'";
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.clear();
		builder
				.appendSql("SELECT T_SHE_Room.FID, T_SHE_Room.FNumber, T_SHE_Purchase.FCustomerNames, "
						+ "T_SHE_Room.FBuildingArea, T_SHE_Room.FRoomArea, T_SHE_Room.FIsCalByRoomArea, "
						+ "T_SHE_Room.FDealPrice, T_SHE_Room.FActualBuildingArea, T_SHE_Room.FActualRoomArea, "
						+ "T_SHE_RoomAreaCompensate.FCompensateRate, T_SHE_RoomAreaCompensate.FCompensateAmount, "
						//�Ϲ����ϵĳɽ��������ۺͳɽ����ڵ���
						+ "T_SHE_Purchase.FDealBuildPrice, T_SHE_Purchase.FDealRoomPrice, "
						+ "T_SHE_Room.FRoomCompensateState, T_SHE_Room.FSellState, T_SHE_RoomAreaCompensate.FIsCalcByScheme FROM T_SHE_Room "
						+ "LEFT OUTER JOIN T_SHE_Purchase ON (T_SHE_Room.FID = T_SHE_Purchase.FRoomID AND T_SHE_Purchase.FPurchaseState = 'PurchaseAudit') "			
						+ "LEFT OUTER JOIN T_SHE_RoomAreaCompensate ON T_SHE_Room.FID = T_SHE_RoomAreaCompensate.FRoomID "
						+ "WHERE T_SHE_Room.FID IN ("
						+ rooIDList
						+ ") or T_SHE_RoomAreaCompensate.FID IN ("
						+ rooIDList
						+ ")");

		IRowSet rowSet = builder.executeQuery();
		tblRoomInfo.checkParsed();
		while (rowSet.next()) {
			IRow row = tblRoomInfo.addRow();
			row.getCell("id").setValue(rowSet.getString("FID"));
			row.getCell("roomNumber").setValue(rowSet.getString("FNumber"));
			row.getCell("customerNames").setValue(
					rowSet.getString("FCustomerNames"));
			row.getCell("buildingArea").setValue(
					rowSet.getString("FBuildingArea"));
			row.getCell("roomArea").setValue(rowSet.getString("FRoomArea"));
			if (rowSet.getString("FIsCalByRoomArea") != null
					&& rowSet.getInt("FIsCalByRoomArea") == 1) {
				row.getCell("isCalByRoomArea").setValue(Boolean.TRUE);
			} else {
				row.getCell("isCalByRoomArea").setValue(Boolean.FALSE);
			}
			// row.getCell("isCalByRoomArea").setValue(
			// Boolean.valueOf(rowSet.getString("FIsCalByRoomArea")
			// .toString()));
			//row.getCell("dealPrice").setValue(rowSet.getString("FDealPrice"));
			//�ɽ����������ڵ���
			row.getCell("buildingPrice").setValue(rowSet.getString("FDealBuildPrice"));
			row.getCell("roomPrice").setValue(rowSet.getString("FDealRoomPrice"));
			
			row.getCell("actualBuildingArea").setValue(
					rowSet.getString("FActualBuildingArea"));
			row.getCell("actualRoomArea").setValue(
					rowSet.getString("FActualRoomArea"));
			row.getCell("compensateRate").setValue(
					rowSet.getString("FCompensateRate"));
			if (type.equals("nosheme")) {
				row.getCell("compensateAmt").setValue(FDCHelper.ZERO);
			} else {
				row.getCell("compensateAmt").setValue(
						rowSet.getString("FCompensateAmount"));
			}
			row.getCell("compensateState").setValue(
					RoomCompensateStateEnum.getEnum(rowSet
							.getString("FRoomCompensateState")));
			row.getCell("sellState").setValue(
					RoomSellStateEnum.getEnum(rowSet.getString("FSellState")));
			row.getCell("isCalcByScheme").setValue(
					rowSet.getString("FIsCalcByScheme"));
		}

		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionSave, actionCopy, actionPre, actionNext, actionFirst,
				actionLast, actionCancel, actionCancelCancel, actionPrint,
				actionPrintPreview, actionEdit, actionRemove }, false);
		menuEdit.setVisible(false);
		menuView.setVisible(false);
		menuSubmitOption.setVisible(false);
		f7Transactor.setValue(SysContext.getSysContext().getCurrentUserInfo());
		// menuBiz.setVisible(false);
		tblRoomInfo.getStyleAttributes().setLocked(false);
		
		if(roomIds != null && roomIds.size() > 0)
		{
			String id = roomIds.get(0).toString();
			
			SelectorItemCollection selColl = new SelectorItemCollection();
			selColl.add("building.sellProject");
			
			RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(id)), selColl);
			
			String sellProjectId = room.getBuilding().getSellProject().getId().toString();
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProjectId));
			
			prmtCompensateScheme.setEntityViewInfo(view);
		}
			
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		
		FDCClientVerifyHelper.verifyInput(this, tblRoomInfo, "compensateAmt");
		Date reportDate = (Date) pkJoinDate.getValue();
		CompensateSchemeInfo scheme = (CompensateSchemeInfo) prmtCompensateScheme
				.getValue();
		String description = txtDescription.getText();
		UserInfo transactor = (UserInfo) f7Transactor.getValue();

		Map valueMap = new HashMap();
		valueMap.put("reportDate", reportDate);
		valueMap.put("scheme", scheme);
		valueMap.put("description", description);
		valueMap.put("transactor", transactor);
		
		boolean isCalcByScheme = false;
		
		if(prmtCompensateScheme.getValue()!=null){
			isCalcByScheme = true;
		}

		//�жϱ�������Ƿ�
		isUseRule();
		
		for (int i = 0, size = tblRoomInfo.getRowCount(); i < size; i++) {
			Map value = new HashMap(0);
			IRow row = tblRoomInfo.getRow(i);
			String roomId = row.getCell("id").getValue().toString();
			verifyBalance(roomId);	
			value.put("roomId", roomId);
			value.put("compensateAmt", row.getCell("compensateAmt").getValue());
			value.put("compensateRate", row.getCell("compensateRate")
					.getValue());
			/**
			 * �˴��Ĵ����߼�����֣�isCalcByScheme����Ϊfasle
			 */
			//value.put("isCalcByScheme", row.getCell("isCalcByScheme")
					//.getValue());
			value.put("isCalcByScheme", String.valueOf(isCalcByScheme));
			
			if(STATUS_ADDNEW.equals(getOprtState())){
				
				String number = "";
				number = createRuleNumber();
				if(number!=null && !"".equals(number)){
					value.put("ruleNumber", number);
				}
			}
			
			valueMap.put(roomId, value);
		}
		java.util.List roomIds = (java.util.List) getUIContext().get("roomIds");
		RoomAreaCompensateFactory.getRemoteInstance().batchSave(roomIds,
				valueMap);
		this.setMessageText("����������� "
				+ EASResource.getString(FrameWorkClientUtils.strResource
						+ "Msg_Save_OK"));
		this.showMessage();
	}

	/**
	 * 
	 */
	private void isUseRule()throws BOSException, CodingRuleException,
	EASBizException{
		
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		IObjectValue objValue = this.editData;;
		if (currentOrgId.length() == 0 || !iCodingRuleManager.isExist(objValue, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
				FDCMsgBox.showWarning(this,"�������û�����ã����ܽ����������");
				SysUtil.abort();
			}
		}

	}
	
	private String createRuleNumber()throws BOSException, CodingRuleException,
	EASBizException{
		String number = "";
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return number;
		}
		boolean isExist = true;
		IObjectValue objValue = this.editData;;
		if (currentOrgId.length() == 0 || !iCodingRuleManager.isExist(objValue, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(objValue, currentOrgId);
			if (isAddView) {
				number = getRuleNumber(objValue, currentOrgId);
			} else {
				//String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(objValue, currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(objValue, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(objValue, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(objValue, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}else
                    {
                        // ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
                        number = iCodingRuleManager.getNumber(objValue, currentOrgId);
                    }
				}
			}
		}
		return number;
	}
	
	private String getRuleNumber(IObjectValue caller, String orgId){
		
		 String number = "";
		 
		 try {
	            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
	            if (orgId == null || orgId.trim().length() == 0)
	            {
	            	//��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
	                 orgId = OrgConstants.DEF_CU_ID;
	            }
	            if (iCodingRuleManager.isExist(caller, orgId))
	            {
	               
	                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
	                { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
	                    if (iCodingRuleManager.isUserSelect(caller, orgId))
	                    {
	                        // �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
	                        // KDBizPromptBox pb = new KDBizPromptBox();
	                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
	                                caller, orgId, null, null);
	                        // pb.setSelector(intermilNOF7);
	                        // Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
	                        Object object = null;
	                        if (iCodingRuleManager.isDHExist(caller, orgId))
	                        {
	                            intermilNOF7.show();
	                            object = intermilNOF7.getData();
	                        }
	                        if (object != null)
	                        {
	                            number = object.toString();
	                        }
	                        else
	                        {
	                            // ���û��ʹ���û�ѡ����,ֱ��getNumber���ڱ���,Ϊʲô����read?����Ϊʹ���û�ѡ����Ҳ��get!
	                            number = iCodingRuleManager
	                                    .getNumber(caller, orgId);
	                        }
	                    }
	                    else
	                    {
	                        // ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
	                        number = iCodingRuleManager.getNumber(caller, orgId);
	                    }
	                }
	                else
	                {
	                    // û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
	                    number = iCodingRuleManager.getNumber(caller, orgId);
	                }
	            }
       
	        } catch (Exception err) {
	            //��ȡ�����������ֿ����ֹ�������룡
	            handleCodingRuleError(err);
	        }
	        
	      return number;
	}
	private void verifyBalance(String roomId) {
		Date bizDate = (Date) this.pkJoinDate.getValue();
		SelectorItemCollection selColl = new SelectorItemCollection();
		RoomInfo room = new RoomInfo();
		selColl.add("building.sellProject.*");		
		try {
			room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)),selColl);
		} catch (EASBizException e) {
			handleException(e);
			e.printStackTrace();
		} catch (BOSException e) {
			handleException(e);
			e.printStackTrace();
		} catch (UuidException e) {
			handleException(e);
			e.printStackTrace();
		}	
		SellProjectInfo sellProject= room.getBuilding().getSellProject();		
		if(sellProject != null){
			Date balanceEndDate = sellProject.getBalanceEndDate();
			SHEHelper.verifyBalance(bizDate, balanceEndDate);
		}
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		

		if(this.getUIContext().get("type")!=null){
			if(this.getUIContext().get("type").toString().equals("scheme")){
				FDCClientVerifyHelper.verifyEmpty(this, prmtCompensateScheme);
			}
		}
		
		actionSave_actionPerformed(e);
	}

	public void actionCalc_actionPerformed(ActionEvent e) throws Exception {
		if (prmtCompensateScheme.getValue() == null) {
			MsgBox.showInfo(this, "δѡ������,���ܼ��㲹��!");
			SysUtil.abort();
		}
		java.util.List roomIds = (java.util.List) getUIContext().get("roomIds");
		String schemeId = ((CompensateSchemeInfo) prmtCompensateScheme
				.getValue()).getId().toString();
		Map values = null;
		try{
			values = RoomAreaCompensateFactory.getRemoteInstance().calcAmount(
					roomIds, schemeId);
				
		}catch(Exception ex){
			logger.error(ex.getMessage()+"����ʧ��!");
			MsgBox.showWarning(this, "�÷���δ����ʵ�⸴�ϣ��������");
			SysUtil.abort();
		}
		for (int i = 0, size = tblRoomInfo.getRowCount(); i < size; i++) {
			IRow row = tblRoomInfo.getRow(i);
			String roomId = row.getCell("id").getValue().toString();
			Map value = (Map) values.get(roomId);
			if (value != null) {
				if (value.get("compensateAmt") != null)
					row.getCell("compensateAmt").setValue(
							value.get("compensateAmt"));
				if (value.get("compensateRate") != null)
					row.getCell("compensateRate").setValue(
							value.get("compensateRate"));
				row.getCell("isCalcByScheme").setValue(Boolean.TRUE);
			}
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyUIControlEmpty(this);
		//prmtCompensateScheme
		FDCClientVerifyHelper.verifyEmpty(this, prmtCompensateScheme);
		
		super.verifyInput(e);
	}

	protected void tblRoomInfo_editStopped(KDTEditEvent e) throws Exception {
		super.tblRoomInfo_editStopped(e);
		if (e.getColIndex() == tblRoomInfo.getColumnIndex("compensateAmt")) {
//			tblRoomInfo.getCell(e.getRowIndex(), "compensateRate").setValue(
//					null);
			tblRoomInfo.getCell(e.getRowIndex(), "isCalcByScheme").setValue(
					Boolean.FALSE);
		}
	}

	protected void tblRoomInfo_editValueChanged(KDTEditEvent e)
			throws Exception {
		super.tblRoomInfo_editValueChanged(e);
		if (e.getColIndex() == tblRoomInfo.getColumnIndex("compensateAmt")) {
//			tblRoomInfo.getCell(e.getRowIndex(), "compensateRate").setValue(
//					null);
			tblRoomInfo.getCell(e.getRowIndex(), "isCalcByScheme").setValue(
					Boolean.FALSE);
		}
	}
	
	
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
        CalculatorDialog calc=new CalculatorDialog(this,true);
        calc.showDialog(2,true);        
		//super.actionCalculator_actionPerformed(e);
	}
	
	/**
	 * ����������
	 * 
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException,
	EASBizException {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return;
		}
		boolean isExist = true;
		IObjectValue objValue = this.editData;;
		if (currentOrgId.length() == 0 || !iCodingRuleManager.isExist(objValue, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(objValue, currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(objValue, currentOrgId);
			} else {
				//String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(objValue, currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(objValue, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(objValue, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(objValue, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				//getNumberCtrl().setText(number);
			}
			//setNumberTextEnabled();
		}
		
	}
	
}