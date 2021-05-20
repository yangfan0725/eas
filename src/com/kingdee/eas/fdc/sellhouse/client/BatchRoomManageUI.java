/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BatchManageFactory;
import com.kingdee.eas.fdc.sellhouse.BatchManageInfo;
import com.kingdee.eas.fdc.sellhouse.BatchManageSourceEnum;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizNewFlowEnum;
import com.kingdee.eas.fdc.sellhouse.BusinessTypeNameEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class BatchRoomManageUI extends AbstractBatchRoomManageUI {
	private static final Logger logger = CoreUIObject.getLogger(BatchRoomManageUI.class);
	
	private String source = "";
	
	private final String RoomPropertySource = "roomProperty";
	
	private final String RoomJoinSource = "roomJoin";

	public BatchRoomManageUI() throws Exception {
		super();
	}
	
	protected IObjectValue createNewData() {
		BatchManageInfo batchInfo = new BatchManageInfo();
		SellProjectInfo sellProject = (SellProjectInfo)getUIContext().get("sellProject");
		batchInfo.setSellProject(sellProject);
		String src = (String)getUIContext().get("source");
		if(src.equals(this.RoomPropertySource)){
			batchInfo.setSource(BatchManageSourceEnum.Property);
		}
		else{
			batchInfo.setSource(BatchManageSourceEnum.Join);
		}
		batchInfo.setTransactor(SysContext.getSysContext().getCurrentUserInfo());
		return batchInfo;
	}
	
	public void onLoad() throws Exception {
		this.source = (String)getUIContext().get("source");
		
		super.onLoad();
		
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew, actionAuditResult,
				actionCopy, actionPre, actionNext, actionFirst, actionLast, actionAudit,
				actionCancel, actionCancelCancel, actionRemove }, false);
		
		if(getOprtState().equals(STATUS_VIEW)){
			this.actionEdit.setEnabled(false);
			this.btnAddRoom.setEnabled(false);
			this.btnDeleteRoom.setEnabled(false);
		}
		
		this.prmtTransactor.setEditable(false);
		
		this.initTable();
		
		this.initF7AddRoom();
	}
	
	public void loadFields() {
		super.loadFields();
		
		this.initBatchRoomEntry();
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void btnAddRoom_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnAddRoom_actionPerformed(e);
		
		this.f7AddRoom.setDataBySelector();
	}

	protected void btnDeleteRoom_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnDeleteRoom_actionPerformed(e);
		
		if (this.kdtBatchRoomEntry.getRowCount() == 0 || this.kdtBatchRoomEntry.getSelectManager().size() == 0) {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
		if(MsgBox.showConfirm2(this,"ȷ��Ҫɾ���÷�����") == MsgBox.CANCEL){
			return;
		}
		
		IRow row = KDTableUtil.getSelectedRow(this.kdtBatchRoomEntry);
		this.kdtBatchRoomEntry.removeRow(row.getRowIndex());
	}
	
	/**
	 * �ж��Ƿ����ñ������
	 * @author liang_ren969
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	private boolean isUseRule() throws BOSException, CodingRuleException,
			EASBizException {
		boolean isUse = false;
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();

		IObjectValue objValue = null;

		if (this.source.equals(this.RoomPropertySource)) {
			objValue = new RoomPropertyBookInfo();
		} else {
			objValue = new RoomJoinInfo();
		}
		currentOrgId = FDCClientHelper.getCurrentOrgId();
		if (iCodingRuleManager.isExist(objValue, currentOrgId)) {
			isUse = true;
		}

		return isUse;

	}
	

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		this.checkInput();
		
		IObjectPK pk = null;
		if(this.editData.getId() != null){
			pk = new ObjectUuidPK(this.editData.getId().toString());
		}
		
		this.editData.setSellProject((SellProjectInfo)this.prmtSellProject.getValue());
		this.editData.setName(this.txtBatchNumber.getText());
		this.editData.setNumber(this.txtBatchNumber.getText());
		this.editData.setTransactor((UserInfo)this.prmtTransactor.getValue());
		this.editData.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		
		//�������η����¼
		BatchRoomEntryCollection batchRoomCol = new BatchRoomEntryCollection();
		for(int i=0; i<this.kdtBatchRoomEntry.getRowCount(); i++){
			BatchRoomEntryInfo batchRoomInfo = new BatchRoomEntryInfo();
			
			if(this.source.equals(this.RoomPropertySource)){
				RoomPropertyBookInfo roomBook = (RoomPropertyBookInfo)this.kdtBatchRoomEntry.getRow(i).getUserObject();
				batchRoomInfo.setRoom(roomBook.getRoom());
			}
			else{
				RoomJoinInfo roomJoin = (RoomJoinInfo)this.kdtBatchRoomEntry.getRow(i).getUserObject();
				batchRoomInfo.setRoom(roomJoin.getRoom());
			}
			batchRoomInfo.setIsValid(true);
			batchRoomCol.add(batchRoomInfo);
		}
		this.editData.getBatchRoomEntry().clear();
		this.editData.getBatchRoomEntry().addCollection(batchRoomCol);
		
		if(pk == null){
			pk = this.getBizInterface().save(this.editData);
		}
		else{
			this.getBizInterface().update(pk, this.editData);
		}
		
		if(pk != null){
			BatchManageInfo batchInfo = BatchManageFactory.getRemoteInstance().getBatchManageInfo(pk);
			this.editData.setId(batchInfo.getId());
			
			//�����Ȩ/��ﵥ��
			for(int i=0; i<this.kdtBatchRoomEntry.getRowCount(); i++){
				IRow row = this.kdtBatchRoomEntry.getRow(i);
				if(this.source.equals(this.RoomPropertySource)){  //��Ȩ
					RoomPropertyBookInfo roomBook = (RoomPropertyBookInfo)row.getUserObject();
					roomBook.setPropState(AFMortgagedStateEnum.UNTRANSACT);  //δ����
					
					IObjectPK bookPk = null;
					if(roomBook.getId() != null){
						bookPk = new ObjectUuidPK(roomBook.getId().toString());
					}
					if(row.getCell("number").getValue()==null){
						String number = this.createNumber(false);
						if(number!=null && !"".equals(number)){
							roomBook.setNumber(number);
						}
					}else{
						roomBook.setNumber((String)row.getCell("number").getValue());
					}
					
					if(row.getCell("propertyCardNo").getValue() != null){
						roomBook.setPropertyNumber((String)row.getCell("propertyCardNo").getValue());
					}
					roomBook.setBatchManage(batchInfo);
					//����
					try {
						if(bookPk == null){
							bookPk = RoomPropertyBookFactory.getRemoteInstance().save(roomBook);
							roomBook.setId(BOSUuid.read(bookPk.toString()));
						}
						else{
							RoomPropertyBookFactory.getRemoteInstance().update(bookPk, roomBook);
						}

						//����ҵ��������Ӧ�ķ���
						SHEManageHelper.updateTransactionOverView(null, roomBook.getRoom(), SHEManageHelper.INTEREST,
								roomBook.getPromiseFinishDate(), roomBook.getActualFinishDate(), false);
						
					} catch (EASBizException ex) {
						logger.error(ex);
						FDCMsgBox.showInfo("��" + ( i + 1 ) + "�еķ������ɲ�Ȩ����ʧ�ܣ������ظ�");
						SysUtil.abort();
					} catch (BOSException ex) {
						logger.error(ex);
						FDCMsgBox.showInfo("��" + ( i + 1 ) + "�еķ������ɲ�Ȩ����ʧ�ܣ������ظ�");
						SysUtil.abort();
					}
				}
				else{  //���
					RoomJoinInfo roomJoin = (RoomJoinInfo)row.getUserObject();
					roomJoin.setJoinState(AFMortgagedStateEnum.UNTRANSACT);
					
					IObjectPK joinPK = null;
					if(roomJoin.getId() != null){
						joinPK = new ObjectUuidPK(roomJoin.getId().toString());
					}
					
					if(row.getCell("number").getValue()==null){
						String number = this.createNumber(false);
						if(number!=null && !"".equals(number)){
							roomJoin.setNumber(number);
						}
					}else{
						roomJoin.setNumber((String)row.getCell("number").getValue());
					}

					roomJoin.setBatchManage(batchInfo);
					//����
					try {
						if(joinPK == null){
							joinPK = RoomJoinFactory.getRemoteInstance().save(roomJoin);
							roomJoin.setId(BOSUuid.read(joinPK.toString()));
						}
						else{
							RoomJoinFactory.getRemoteInstance().update(joinPK, roomJoin);
						}
						
						//����ҵ��������Ӧ�ķ���
						SHEManageHelper.updateTransactionOverView(null, roomJoin.getRoom(), SHEManageHelper.JOIN,
								roomJoin.getPromiseFinishDate(), roomJoin.getActualFinishDate(), false);
						
					} catch (EASBizException ex) {
						logger.error(ex);
						FDCMsgBox.showInfo("��" + ( i + 1 ) + "�еķ������ɵ���ʧ�ܣ������ظ�");
						SysUtil.abort();
					} catch (BOSException ex) {
						logger.error(ex);
						FDCMsgBox.showInfo("��" + ( i + 1 ) + "�еķ������ɵ���ʧ�ܣ������ظ�");
						SysUtil.abort();
					}
				}
			}
		}
		
		FDCMsgBox.showInfo("�������� " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		
		setOprtState(STATUS_VIEW);
		lockUIForViewStatus();
		this.actionEdit.setEnabled(false);
		this.btnAddRoom.setEnabled(false);
		this.btnDeleteRoom.setEnabled(false);
		this.btnAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BatchManageFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtBatchRoomEntry;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtBatchNumber;
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selector =  super.getSelectors();
		selector.add(new SelectorItemInfo("batchRoomEntry.*"));
		return selector;
	}
	
	/**
	 * ���¼�����ݣ��Լ���Ȩ���ݱ����Ƿ��ظ�
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void checkInput() throws EASBizException, BOSException {
		if(this.txtBatchNumber.getText() == null || this.txtBatchNumber.getText().trim().length() <= 0){
			FDCMsgBox.showInfo("�������������");
			SysUtil.abort();
		}
		//���ͬһ��Ŀ�±����Ƿ��ظ�
		boolean isBatchNumDup = false;
		SellProjectInfo sellProject = (SellProjectInfo) getUIContext().get("sellProject");
		
		FilterInfo batchFilter = new FilterInfo();
		batchFilter.getFilterItems().add(new FilterItemInfo("number", this.txtBatchNumber.getText()));
		if(this.source.equals(this.RoomPropertySource)){  //��Ȩ����
			batchFilter.getFilterItems().add(new FilterItemInfo("source", BatchManageSourceEnum.PROPERTY_VALUE));
		}
		else{  //�������
			batchFilter.getFilterItems().add(new FilterItemInfo("source", BatchManageSourceEnum.JOIN_VALUE));
		}
		batchFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
		
		if (this.editData.getId() != null) {
			batchFilter.getFilterItems().add(new FilterItemInfo("id", this.editData.getId().toString(),
					CompareType.NOTEQUALS));
		}
		isBatchNumDup = BatchManageFactory.getRemoteInstance().exists(batchFilter);
		
		if(isBatchNumDup){
			FDCMsgBox.showInfo("�����������Ʋ����ظ���");
			SysUtil.abort();
		}
		
		if(this.prmtTransactor.getValue() == null){
			FDCMsgBox.showInfo("��ѡ�񾭰���");
			SysUtil.abort();
		}
		
		if(this.kdtBatchRoomEntry.getRowCount() <= 0){
			FDCMsgBox.showInfo("��ѡ�����η���");
			SysUtil.abort();
		}
		
		for(int i=0; i<this.kdtBatchRoomEntry.getRowCount(); i++){
			IRow row = this.kdtBatchRoomEntry.getRow(i);
			if(row.getCell("number").getValue() == null || row.getCell("number").getValue().toString().trim().length() == 0){
				FDCMsgBox.showInfo("������� "+ (i+1) + "�е�ҵ�����");
				SysUtil.abort();
			}
			if(row.getCell("number").getValue().toString().length() > 80){
				FDCMsgBox.showInfo("�� "+ (i+1) + "�е�ҵ����볤�Ȳ��ܳ���80���ַ�");
				SysUtil.abort();
			}
			if(row.getCell("propertyCardNo").getValue()!=null
				&& row.getCell("propertyCardNo").getValue().toString().length() > 255){
				FDCMsgBox.showInfo("�� "+ (i+1) + "�еķ���֤�ų��Ȳ��ܳ���255���ַ�");
				SysUtil.abort();
			}
			//���ͬһ��Ŀ�±����Ƿ��ظ�
			
			if(!this.isUseRule()){
				boolean result = false;
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("number", row.getCell("number").getValue().toString()));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
				filter.getFilterItems().add(new FilterItemInfo("room.building.sellproject.id", sellProject.getId().toString()));
				
				if(this.source.equals(this.RoomPropertySource)){  //��Ȩ
					RoomPropertyBookInfo bookInfo = (RoomPropertyBookInfo)row.getUserObject();
					if (bookInfo.getId() != null) {
						filter.getFilterItems().add(new FilterItemInfo("id", bookInfo.getId().toString(),
								CompareType.NOTEQUALS));
					}
					result = RoomPropertyBookFactory.getRemoteInstance().exists(filter);
				}
				else{  //���
					RoomJoinInfo joinInfo = (RoomJoinInfo)row.getUserObject();
					if (joinInfo.getId() != null) {
						filter.getFilterItems().add(new FilterItemInfo("id", joinInfo.getId().toString(),
								CompareType.NOTEQUALS));
					}
					result = RoomJoinFactory.getRemoteInstance().exists(filter);
				}
				
				if(result){
					FDCMsgBox.showInfo("��" + ( i + 1 ) + "�еķ��䵥�ݱ����ظ���");
					SysUtil.abort();
				}
			}
			}
			
		
		//��鵱ǰ�����¼�Ĳ�Ȩ�����Ƿ��ظ�
		for(int i=0; i<this.kdtBatchRoomEntry.getRowCount(); i++){
			IRow row = this.kdtBatchRoomEntry.getRow(i);
			for(int j=i+1; j<this.kdtBatchRoomEntry.getRowCount(); j++){
				IRow compareRow = this.kdtBatchRoomEntry.getRow(j);
				if(row.getCell("number").getValue().toString()
					.equals(compareRow.getCell("number").getValue().toString())){
					FDCMsgBox.showInfo("��" + ( i + 1 ) + "�еķ��䵥�ݱ����ظ���");
					SysUtil.abort();
				}
			}
		}
	}
	
	/**
	 * ��ʼ�����༭״̬
	 */
	private void initTable(){
		//��ʼ��������
		this.kdtBatchRoomEntry.getStyleAttributes().setLocked(true);
		
		KDTextField txtNumber = new KDTextField();
    	txtNumber.setHorizontalAlignment(KDTextField.LEFT);
    	txtNumber.setMaxLength(80);
    	this.kdtBatchRoomEntry.getColumn("number").setEditor(new KDTDefaultCellEditor(txtNumber));
    	this.kdtBatchRoomEntry.getColumn("number").getStyleAttributes().setLocked(false);
    	
    	try{
    		if(this.isUseRule()){
    			this.kdtBatchRoomEntry.getColumn("number").getStyleAttributes().setLocked(true);
        	}else{
        		this.kdtBatchRoomEntry.getColumn("number").getStyleAttributes().setLocked(false);
        		this.kdtBatchRoomEntry.getColumn("number").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
        	}
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    	}
    	
    	KDTextField txtPropertyNumber = new KDTextField();
    	txtPropertyNumber.setMaxLength(255);
    	this.kdtBatchRoomEntry.getColumn("propertyCardNo").setEditor(new KDTDefaultCellEditor(txtPropertyNumber));
    	
    	if(this.source.equals(this.RoomPropertySource)){
    		this.kdtBatchRoomEntry.getColumn("propertyCardNo").getStyleAttributes().setLocked(false);
		}
    	else{
    		this.kdtBatchRoomEntry.getColumn("propertyCardNo").getStyleAttributes().setHided(true);
    	}
	}
	
	/**
	 * ��ʼ���������
	 */
	private void initBatchRoomEntry(){
		if(this.editData.getId() != null){
			String batchId = this.getEditData().getId().toString();
			try {
				if(this.source.equals(this.RoomPropertySource)){  //��Ȩ
					RoomPropertyBookCollection roomBookCol = this.getRoomBookCollection(batchId);
					if(roomBookCol!=null && roomBookCol.size()>0){
						for(int i=0; i<roomBookCol.size(); i++){
							IRow row = this.kdtBatchRoomEntry.addRow();
							
							RoomPropertyBookInfo bookInfo = roomBookCol.get(i);
							SignManageInfo signInfo = bookInfo.getSign();
							row.setUserObject(bookInfo);
							
							row.getCell("room").setValue(bookInfo.getRoom().getNumber());
							row.getCell("roomId").setValue(bookInfo.getRoom().getId());
							row.getCell("number").setValue(bookInfo.getNumber());
							if(signInfo != null){
								if(signInfo.getCustomerNames() != null){
									row.getCell("customer").setValue(signInfo.getCustomerNames());
								}
								if(signInfo.getCustomerPhone() != null){
									row.getCell("customerPhone").setValue(signInfo.getCustomerPhone());
								}
								if(signInfo.getNumber() != null){
									row.getCell("contractNumber").setValue(signInfo.getNumber());
								}
								if(signInfo.getBizNumber() != null){
									row.getCell("contractNo").setValue(signInfo.getBizNumber());
								}
								if(signInfo.getBizDate() != null){
									row.getCell("signDate").setValue(signInfo.getBizDate());
								}
							}
							row.getCell("propertyCardNo").setValue(bookInfo.getPropertyNumber());
						}
					}
				}
				else{
					RoomJoinCollection roomJoinCol = this.getRoomJoinCollection(batchId);
					if(roomJoinCol!=null && roomJoinCol.size()>0){
						for(int i=0; i<roomJoinCol.size(); i++){
							IRow row = this.kdtBatchRoomEntry.addRow();
							
							RoomJoinInfo joinInfo = roomJoinCol.get(i);
							SignManageInfo signInfo = joinInfo.getSign();
							row.setUserObject(joinInfo);
							
							row.getCell("room").setValue(joinInfo.getRoom().getNumber());
							row.getCell("roomId").setValue(joinInfo.getRoom().getId());
							row.getCell("number").setValue(joinInfo.getNumber());
							if(signInfo != null){
								if(signInfo.getCustomerNames() != null){
									row.getCell("customer").setValue(signInfo.getCustomerNames());
								}
								if(signInfo.getCustomerPhone() != null){
									row.getCell("customerPhone").setValue(signInfo.getCustomerPhone());
								}
								if(signInfo.getNumber() != null){
									row.getCell("contractNumber").setValue(signInfo.getNumber());
								}
								if(signInfo.getBizNumber() != null){
									row.getCell("contractNo").setValue(signInfo.getBizNumber());
								}
								if(signInfo.getBizDate() != null){
									row.getCell("signDate").setValue(signInfo.getBizDate());
								}
							}
						}
					}
				}
			} catch (BOSException e) {
				logger.error(e);
			}
		}
	}
	
	protected void f7AddRoom_dataChanged(DataChangeEvent e) throws Exception {
		super.f7AddRoom_dataChanged(e);
		
		Object[] rooms = (Object[])e.getNewValue();
		this.f7AddRoom.setValue(null);
		
		if (rooms == null || rooms.length == 0 || rooms[0] == null) {
			return;
		}
		RoomCollection roomCol = new RoomCollection();
		for (int r = 0; r < rooms.length; r++) {
			RoomInfo selRoom = (RoomInfo)rooms[r];
			//������ѡ��ķ���
			boolean isExist = false;
			for(int i=0; i<this.kdtBatchRoomEntry.getRowCount(); i++){
				String roomId = null;
				if(this.source.equals(this.RoomPropertySource)){
					RoomPropertyBookInfo rowRoomProBook = (RoomPropertyBookInfo)this.kdtBatchRoomEntry.getRow(i).getUserObject();
					roomId = rowRoomProBook.getRoom().getId().toString();
				}
				else{
					RoomJoinInfo rowRoomJoinInfo = (RoomJoinInfo)this.kdtBatchRoomEntry.getRow(i).getUserObject();
					roomId = rowRoomJoinInfo.getRoom().getId().toString();
				}
				if(roomId.equals(selRoom.getId().toString())){
					isExist = true;
				}
			}
			if(isExist){
				continue;
			}
			
			//��鷿���Ƿ��Ѿ������Ȩ/���
			if(this.isRoomBooked(selRoom)){
				FDCMsgBox.showWarning("����" + selRoom.getNumber() + "�Ѱ��������Σ��Զ�����");
	            continue;
			}
			
			roomCol.add(selRoom);
		}
		
		this.addRoomToBatch(roomCol);
		
	}
	
	private void addRoomToBatch(RoomCollection roomCol) throws Exception{
		for(int i=0; i<roomCol.size(); i++){
			RoomInfo selRoom = roomCol.get(i);
			
			//�õ������ǩԼ��Ϣ
			SignManageInfo signInfo = this.getSignInfo(selRoom);
			String number = "";
			IRow newRow = this.kdtBatchRoomEntry.addRow();
			if(this.source.equals(this.RoomPropertySource)){
				//Ϊ��������һ����Ȩ����
				RoomPropertyBookInfo roomPropertyBook = new RoomPropertyBookInfo();
				roomPropertyBook.setRoom(selRoom);
				roomPropertyBook.setSign(signInfo);
				
				//���ó�ŵ�������
				if(signInfo != null){
					roomPropertyBook.setPromiseFinishDate(this.getPromiseDate(signInfo));
				}
				
				number= this.createNumber(true);
				if(number!=null && !"".equals(number)){
					roomPropertyBook.setNumber(number);
					this.setNumberTextEnabled(newRow);
				}
				newRow.setUserObject(roomPropertyBook);
			}
			else{
				//Ϊ��������һ����ﵥ��
				RoomJoinInfo roomJoinInfo = new RoomJoinInfo();
				roomJoinInfo.setRoom(selRoom);
				roomJoinInfo.setSign(signInfo);
				
				//���ó�ŵ�������
				if(signInfo != null){
					roomJoinInfo.setPromiseFinishDate(this.getPromiseDate(signInfo));
					roomJoinInfo.setApptJoinDate(signInfo.getJoinInDate());
				}
				
				number = this.createNumber(true);
				if(number!=null && !"".equals(number)){
					roomJoinInfo.setNumber(number);
					this.setNumberTextEnabled(newRow);
				}
				newRow.setUserObject(roomJoinInfo);
			}
			if(!"".equals(number)){
				newRow.getCell("number").setValue(number);
			}
			
			newRow.getCell("room").setValue(selRoom.getNumber());
			if(signInfo != null){
				if(signInfo.getCustomerNames() != null){
					newRow.getCell("customer").setValue(signInfo.getCustomerNames());
				}
				if(signInfo.getCustomerPhone() != null){
					newRow.getCell("customerPhone").setValue(signInfo.getCustomerPhone());
				}
				if(signInfo.getNumber() != null){
					newRow.getCell("contractNumber").setValue(signInfo.getNumber());
				}
				if(signInfo.getBizNumber() != null){
					newRow.getCell("contractNo").setValue(signInfo.getBizNumber());
				}
				if(signInfo.getBizDate() != null){
					newRow.getCell("signDate").setValue(signInfo.getBizDate());
				}
			}
		}
	}
	
	/**
	 * ����ǩԼ���и������ҵ����ϸ�������ŵ�������
	 * @param sign
	 * @return
	 */
	private Date getPromiseDate(SignManageInfo sign){
		if(sign.getPayType() == null){
			return null;
		}
		Date promiseDate = null;
		//��ȡ�������ҵ����ϸ
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("bizLists.*"));
		
		try {
			SHEPayTypeInfo payType = SHEPayTypeFactory.getRemoteInstance()
				.getSHEPayTypeInfo(new ObjectUuidPK(sign.getPayType().getId().toString()), selector);
			if(payType != null && payType.getBizLists()!=null && !payType.getBizLists().isEmpty()){
				for(int i=0; i<payType.getBizLists().size(); i++){
					BizListEntryInfo bizEntry = payType.getBizLists().get(i);
					if(bizEntry.getPayTypeBizFlow().equals(BizNewFlowEnum.PROPERTY)){
						promiseDate = this.getBizPromiseDate(payType.getBizLists(), bizEntry);
						break;
					}
				}
			}
			
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return promiseDate;
	}
	
	/**
	 * ������ϸ������Ӧ�������
	 * @param bizEntryCol
	 * @param currentBizInfo
	 * @return
	 */
	private Date getBizPromiseDate(BizListEntryCollection bizEntryCol, 
			BizListEntryInfo currentBizInfo){
		if(currentBizInfo.getPayTypeBizTime() == null){
			return null;
		}
		else if(currentBizInfo.getPayTypeBizTime().equals("ָ������")){  //��������Ϊָ������
			return currentBizInfo.getAppDate();
		}
		else{
			for(int i=0; i<bizEntryCol.size(); i++){
				BizListEntryInfo bizInfo = bizEntryCol.get(i);
				if(bizInfo.getPayTypeBizFlow().getAlias().equals(currentBizInfo.getPayTypeBizTime())){
					Date tempDate = getBizPromiseDate(bizEntryCol, bizInfo);
					if(tempDate != null){
						//���ݼ���º����������
						int mon = currentBizInfo.getMonthLimit();
						int day = currentBizInfo.getDayLimit();
						return DateTimeUtils.addDuration(tempDate, 0, mon, day);
					}
				}
			}
			return null;
		}
	}
	
	/**
	 * ��ʼ������ѡ��f7�ؼ�
	 */
	private void initF7AddRoom() {
		SellProjectInfo sellProject = (SellProjectInfo) getUIContext().get("sellProject");
		EntityViewInfo view = new EntityViewInfo();
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", sellProject.getId().toString()));
		
		filter.getFilterItems().add(new FilterItemInfo("building.sellProject.isForSHE", "true"));  // ��¥����
		filter.getFilterItems().add(new FilterItemInfo("sellState", RoomSellStateEnum.Sign.getValue()));  //ǩԼ״̬
		view.setFilter(filter);
		
		this.f7AddRoom.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectByListQuery");
		this.f7AddRoom.setEnabledMultiSelection(true);
		this.f7AddRoom.setEntityViewInfo(view);
	}
	
	/**
	 * ��鷿���Ƿ����ڰ���������
	 * @param room
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private boolean isRoomBooked(RoomInfo room) throws EASBizException, BOSException{
		boolean isBooked = false;
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		
		FilterInfo filter = new FilterInfo();
		if(this.source.equals(this.RoomPropertySource)){
			filter.getFilterItems().add(new FilterItemInfo("batchManage.source", BatchManageSourceEnum.PROPERTY_VALUE));
		}
		else{
			filter.getFilterItems().add(new FilterItemInfo("batchManage.source", BatchManageSourceEnum.JOIN_VALUE));
		}
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isValid", Boolean.TRUE));

		view.setFilter(filter);
		
		BatchRoomEntryCollection batchManageCol = BatchRoomEntryFactory.getRemoteInstance().getBatchRoomEntryCollection(view);
		if(batchManageCol!=null && batchManageCol.size()>0){
			isBooked = true;
		}
		
		return isBooked;
	}
	
	private RoomPropertyBookCollection getRoomBookCollection(String batchId) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("room.id");
		view.getSelector().add("room.number");
		view.getSelector().add("sign.id");
		view.getSelector().add("sign.customerNames");
		view.getSelector().add("sign.customerPhone");
		view.getSelector().add("sign.number");
		view.getSelector().add("sign.bizNumber");
		view.getSelector().add("sign.bizDate");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("batchManage.id", batchId));

		view.setFilter(filter);
		
		RoomPropertyBookCollection roomBookCol = RoomPropertyBookFactory.getRemoteInstance().getRoomPropertyBookCollection(view);
		
		return roomBookCol;
	}
	
	private RoomJoinCollection getRoomJoinCollection(String batchId) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("room.id");
		view.getSelector().add("room.number");
		view.getSelector().add("sign.id");
		view.getSelector().add("sign.customerNames");
		view.getSelector().add("sign.customerPhone");
		view.getSelector().add("sign.number");
		view.getSelector().add("sign.bizNumber");
		view.getSelector().add("sign.bizDate");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("batchManage.id", batchId));

		view.setFilter(filter);
		
		RoomJoinCollection roomBookCol = RoomJoinFactory.getRemoteInstance().getRoomJoinCollection(view);
		
		return roomBookCol;
	}
	
	/**
	 * ���ݷ����ȡǩԼ����Ϣ
	 * @param room
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private SignManageInfo getSignInfo(RoomInfo room) throws EASBizException, BOSException{
		SignManageInfo signInfo = null;
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("billId");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isValid", new Boolean(false)));

		view.setFilter(filter);
		
		TransactionCollection transactionCol = TransactionFactory.getRemoteInstance().getTransactionCollection(view);
		//�ӽ��������е�ǩԼ��Id��ȡǩԼ��
		if(transactionCol!=null && !transactionCol.isEmpty()){
			SelectorItemCollection selCol = new SelectorItemCollection();
			selCol.add("*");
			
			signInfo = SignManageFactory.getRemoteInstance()
				.getSignManageInfo(new ObjectUuidPK(transactionCol.get(0).getBillId().toString()), selCol);
			
			return signInfo;
		}
		return signInfo;
	}

	private String createNumber(boolean isCornette) throws BOSException, CodingRuleException,
			EASBizException {
		String number = "";
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		// if (!"ADDNEW".equals(this.oprtState)) {
		// return number;
		// }
		boolean isExist = true;
		
		IObjectValue objValue = null;
		
		if(this.source.equals(this.RoomPropertySource)){
			objValue = new RoomPropertyBookInfo();
			((RoomPropertyBookInfo)objValue).setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		}else{
			objValue = new RoomJoinInfo();
			((RoomJoinInfo)objValue).setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		}
		
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(objValue, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(objValue,
					currentOrgId);
			if (isAddView) {
				number = getRuleNumber(objValue, currentOrgId);
			} else {
				// String number = null;
				if(isCornette){
					return "";
				}
				if (iCodingRuleManager.isUseIntermitNumber(objValue,
						currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(objValue, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								objValue, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(objValue, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					} else {
						// ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
						number = iCodingRuleManager.getNumber(objValue,
								currentOrgId);
					}
				} else {
					number = iCodingRuleManager.getNumber(objValue,
							currentOrgId);
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
	           //handleCodingRuleError(err);
	        }
	        
	      return number;
	}
	
	protected void setNumberTextEnabled(IRow row) {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if (org != null) {

				boolean isAllowModify = true;
				if (this.source.equals(this.RoomPropertySource)) {
					isAllowModify = FDCClientUtils.isAllowModifyNumber(
							new RoomPropertyBookInfo(), org.getId().toString());
				} else {
					isAllowModify = FDCClientUtils.isAllowModifyNumber(
							new RoomJoinInfo(), org.getId().toString());
				}
				if (isAllowModify) {
					row.getCell("number").getStyleAttributes().setLocked(false);
				} else {
					row.getCell("number").getStyleAttributes().setLocked(true);
				}
			}
	}
}