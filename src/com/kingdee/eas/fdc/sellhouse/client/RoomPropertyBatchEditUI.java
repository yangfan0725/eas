/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDComboBox;
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
import com.kingdee.bos.ui.UIFocusTraversalPolicy;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BatchManageFactory;
import com.kingdee.eas.fdc.sellhouse.BatchManageInfo;
import com.kingdee.eas.fdc.sellhouse.BatchManageSourceEnum;
import com.kingdee.eas.fdc.sellhouse.BusinessTypeNameEnum;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryTwoCollection;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryTwoInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomPropertyBatchEditUI extends AbstractRoomPropertyBatchEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomPropertyBatchEditUI.class);

	private KDBizPromptBox prmtRoom = new KDBizPromptBox();

	// ��Ȩ����
	private final String book_roomNum = "room";
	private final String book_roomCustomer = "customer";
	private final String book_signDate = "signDate";
	private final String book_contractNum = "contractNumber";
	private final String book_propertyNumber = "propertNum";
	private final String book_ruhuVarchar = "ruhuVarchar";
	private final String book_number = "number";
	private final String book_roomId = "roomId";

	// ��������
	private final String step_seq = "seq";
	private final String step_number = "number";
	private final String step_name = "name";
	private final String step_des = "description";
	private final String step_isFinish = "isFinish";
	private final String step_date = "date";

	// ��������
	private final String mat_name = "name";
	private final String mat_des = "remark";
	private final String mat_isFinish = "isFinish";
	private final String mat_date = "processDate";

	private final String AllFinish = "ȫ�����";

	// ��ӵ�Ѻ���к͵�Ѻ����������
	private final String book_mortgageDate = "mortgageDate";
	private final String book_mortgageBank = "mortgageBank";

	/**
	 * output class constructor
	 */
	public RoomPropertyBatchEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		
		this.checkInput();
		
		Date transactDate = (Date) pkUpdateDate.getValue();
		UserInfo transactor = (UserInfo) prmtTransactor.getValue();

		Map valueMap = new HashMap();
		valueMap.put("transactDate", transactDate);
		valueMap.put("transactor", transactor);

		List roomBookList = new ArrayList();
		for(int i=0; i<this.kdtBook.getRowCount(); i++){
			RoomPropertyBookInfo bookInfo = (RoomPropertyBookInfo)this.kdtBook.getRow(i).getUserObject();
			bookInfo.setTransactor(transactor);
			bookInfo.setTransactDate(transactDate);
			
			PropertyDoSchemeInfo schemeInfo = (PropertyDoSchemeInfo)this.prmtScheme.getValue();
			
			//��Ȩ�����仯�����½���
			boolean isAppChange = false;
			if(bookInfo.getPropertyDoScheme()==null || (bookInfo.getPropertyDoScheme()!=null 
					&& !bookInfo.getPropertyDoScheme().getId().toString().equals(schemeInfo.getId().toString()))){
				isAppChange = true;
				bookInfo.getEntry().clear();
				
				bookInfo.setPropertyDoScheme(schemeInfo);
			}
			//���ò�Ȩ���̣�״̬�͵�ǰ����
			RoomPropertyBookEntryCollection appEntryCol = new RoomPropertyBookEntryCollection();
			for(int eIndex=0; eIndex<schemeInfo.getEntry().size(); eIndex++){
				PropertyDoSchemeEntryInfo appEntryInfo = schemeInfo.getEntry().get(eIndex);
				
				RoomPropertyBookEntryInfo bookEntryInfo = null;
				
				//�ж��������̻����޸Ľ���
				if(isAppChange){  //��������
					bookEntryInfo = new RoomPropertyBookEntryInfo();
					bookEntryInfo.setName(appEntryInfo.getName());
					bookEntryInfo.setIsFinish(Boolean.FALSE.booleanValue());
					bookEntryInfo.setPromiseFinishDate(getApproachPromiseDate(schemeInfo.getEntry(), appEntryInfo));
					bookEntryInfo.setDescription(appEntryInfo.getDescription());
					bookEntryInfo.setIsFinishFlag(appEntryInfo.isIsFinish());
				}
				else{  //�ҳ���Ӧ�Ľ��̽����޸�
					for(int p=0; p<bookInfo.getEntry().size(); p++){
						if(bookInfo.getEntry().get(p).getName().equals(appEntryInfo.getName())){
							bookEntryInfo = bookInfo.getEntry().get(p);
						}
					}
				}
				
				if(bookEntryInfo!=null && this.comboCurStep.getSelectedItem().toString().equals(appEntryInfo.getName())) {
					bookEntryInfo.setIsFinish(true);
					bookEntryInfo.setActualFinishDate(new Date());
					bookEntryInfo.setTransactor(transactor);
					
					if(!isAppChange && bookEntryInfo.isIsFinishFlag()){  //�޸Ľ��̣�ʹ�ý���ԭ�еı�ʶ���ж��Ƿ�������
						bookInfo.setStep("�������");
						bookInfo.setActualFinishDate(new Date());
						if(this.comboPropertyState.getSelectedItem() != null){
							bookInfo.setPropertyState((PropertyStateEnum)this.comboPropertyState.getSelectedItem());
						}
						bookInfo.setPropState(AFMortgagedStateEnum.TRANSACTED);
						
						this.ckIsFinished.setSelected(true);
						
						//����ҵ��������Ӧ�ķ���
						SHEManageHelper.updateTransactionOverView(null, bookInfo.getRoom(), SHEManageHelper.INTEREST,
								bookInfo.getPromiseFinishDate(), bookInfo.getActualFinishDate(), false);
					}
					else if(appEntryInfo.isIsFinish()){  //�������
						bookInfo.setStep("�������");
						bookInfo.setActualFinishDate(new Date());
						bookInfo.setPropState(AFMortgagedStateEnum.TRANSACTED);
						if(this.comboPropertyState.getSelectedItem() != null){
							bookInfo.setPropertyState((PropertyStateEnum)this.comboPropertyState.getSelectedItem());
						}
						
						this.ckIsFinished.setSelected(true);
						
						//����ҵ��������Ӧ�ķ���
						SHEManageHelper.updateTransactionOverView(null, bookInfo.getRoom(), SHEManageHelper.INTEREST,
								bookInfo.getPromiseFinishDate(), bookInfo.getActualFinishDate(), false);
					}
					else{
						bookInfo.setStep(appEntryInfo.getName());
						bookInfo.setPropState(AFMortgagedStateEnum.TRANSACTING);
						
						//����ҵ��������Ӧ�ķ���
						SHEManageHelper.updateTransactionOverView(null, bookInfo.getRoom(), SHEManageHelper.INTEREST,
								bookInfo.getPromiseFinishDate(), null, false);
					}
				}
				
				if(isAppChange){
					appEntryCol.add(bookEntryInfo);
				}
			}
			//��������
			if(isAppChange && appEntryCol.size() > 0){
				bookInfo.getEntry().addCollection(appEntryCol);
			}
			
			//���·���Ĳ�Ȩ��Ϣ
			bookInfo.setNumber((String)this.kdtBook.getRow(i).getCell("number").getValue());
			bookInfo.setPropertyNumber((String)this.kdtBook.getRow(i).getCell("propertNum").getValue());
			
			//������������
			if(this.ckBatchUpdateData.isSelected()){
				for(int p=0; p<bookInfo.getEntryTwo().size(); p++){
					bookInfo.getEntryTwo().clear();
				}
				
				RoomPropertyBookEntryTwoCollection dataEntryCol = new RoomPropertyBookEntryTwoCollection();
				for(int a=0; a<this.tblData.getRowCount(); a++ ){
					RoomPropertyBookEntryTwoInfo dataEntryInfo = new RoomPropertyBookEntryTwoInfo();
					dataEntryInfo.setName((String)this.tblData.getRow(a).getCell("name").getValue());
					dataEntryInfo.setIsFinish(((Boolean)this.tblData.getRow(a).getCell("isFinish").getValue()).booleanValue());
					dataEntryInfo.setProcessDate((Date)this.tblData.getRow(a).getCell("processDate").getValue());
					dataEntryInfo.setDescription((String)this.tblData.getRow(a).getCell("remark").getValue());
					
					dataEntryCol.add(dataEntryInfo);
				}
				bookInfo.getEntryTwo().addCollection(dataEntryCol);
			}
			
			roomBookList.add(bookInfo);
		}
		for(int r=0; r<roomBookList.size(); r++){
			RoomPropertyBookFactory.getRemoteInstance().save((RoomPropertyBookInfo)roomBookList.get(r));
		}
		FDCMsgBox.showInfo("������Ȩ���� " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
//		super.actionEdit_actionPerformed(e);
		this.actionAddRoom.setEnabled(true);
		this.actionRemoveRoom.setEnabled(true);

		// this.ckIsFinished.setEnabled(checkIsStepFinish(kdtStep,
		// kdtMaterial));
		this.contPropertyState.setEnabled(this.ckIsFinished.isSelected());
		this.comboPropertyState.setEnabled(this.ckIsFinished.isSelected());

//		afterCkIsFinished(ckIsFinished.isSelected());

		this.contScheme.setEnabled(false);
		this.prmtScheme.setEnabled(false);
	}

	/**
	 * output actionAddRoom_actionPerformed
	 */
	public void actionAddRoom_actionPerformed(ActionEvent e) throws Exception {
		if (getDetailTable() != null) {
			addLine(getDetailTable());
			appendFootRow(getDetailTable());
		}
	}

	/**
	 * output actionRemoveRoom_actionPerformed
	 */
	public void actionRemoveRoom_actionPerformed(ActionEvent e)
			throws Exception {
		String selectRoomId = null;

		if (getDetailTable() != null) {

			// ������ѡ���и��¶�Ӧ�Ĳ�Ȩ����������Ϊ��
			if (getDetailTable().getSelectManager().getActiveRowIndex() >= 0) {
				IRow tmpRow = getDetailTable()
						.getRow(
								getDetailTable().getSelectManager()
										.getActiveRowIndex());
				if (tmpRow.getCell(book_roomId).getValue() != null) {
					selectRoomId = String.valueOf(tmpRow.getCell(book_roomId)
							.getValue());
				}
			}

			removeLine(getDetailTable());
			appendFootRow(getDetailTable());
			// ʵ��ɾ����Ľ������ 2007-10-09 haiti_yang
			if (getDetailTable().getRowCount() == 0) {
				FocusTraversalPolicy policy = null;
				Container container = null;
				Component initComponent = null;
				if (this.getFocusTraversalPolicy() != null
						&& this.getFocusTraversalPolicy() instanceof UIFocusTraversalPolicy) {
					policy = this.getFocusTraversalPolicy();
					container = this;
					Component[] traverComponent = ((UIFocusTraversalPolicy) policy)
							.getComponents();
					for (int i = 0; i < traverComponent.length; i++) {
						if (traverComponent[i] == this.getDetailTable()) {
							initComponent = traverComponent[i];
							break;
						}
					}
					if (initComponent == null) {
						initComponent = policy.getLastComponent(container);
						initComponent.requestFocusInWindow();
					} else if (initComponent != null) {
						Component component = policy.getComponentBefore(
								container, initComponent);
						while ((component instanceof IKDTextComponent) == false
								|| component.isEnabled() == false) {
							component = policy.getComponentBefore(container,
									component);
						}
						component.requestFocusInWindow();
					}
				} else if (policy == null) {
					if (this.getUIWindow() instanceof Dialog) {
						policy = ((Dialog) uiWindow).getFocusTraversalPolicy();
						container = (Dialog) uiWindow;
					} else if (this.getUIWindow() instanceof Window) {
						policy = ((Window) uiWindow).getFocusTraversalPolicy();
						container = (Window) uiWindow;
					}

					if (policy != null) {
						try {
							Component component = policy.getComponentBefore(
									container, getDetailTable());
							while ((component instanceof IKDTextComponent) == false
									|| component.isEnabled() == false) {
								component = policy.getComponentBefore(
										container, component);
							}
							component.requestFocusInWindow();
						} catch (Exception ex) {
						}
					}
				}
			}
		}
		if (selectRoomId != null) {
			Map paramMap = new HashMap();
			paramMap.put("RoomId", selectRoomId);
			RoomPropertyBookFacadeFactory.getRemoteInstance()
					.updateRoomProperty(paramMap);

//			updateStepAndMaterial();
		}
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomPropertyBatchFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtBook;
	}

	protected KDTextField getNumberCtrl() {
		KDTextField defaultField = new KDTextField();
		defaultField.setEnabled(false);
		return defaultField;
	}

	protected IObjectValue createNewData() {
		RoomPropertyBookInfo info = new RoomPropertyBookInfo();
		/*
		 * info.setId(BOSUuid.create(info.getBOSType())); info.setUpdateDate(new
		 * Date());
		 * 
		 * info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		 * info.setTransactor(SysContext.getSysContext().getCurrentUserInfo());
		 * info.setBookState(RoomBookStateEnum.BOOKING);
		 * 
		 * //������֯��������Ŀ String sellPrjId =
		 * String.valueOf(getUIContext().get("ProjectId")); SellProjectInfo
		 * sellPrj = new SellProjectInfo();
		 * sellPrj.setId(BOSUuid.read(sellPrjId)); info.setSellProject(sellPrj);
		 * 
		 * info.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().
		 * castToFullOrgUnitInfo());
		 * 
		 * if(getUIContext().get("SchemeId") != null) { String schemeId =
		 * String.valueOf(getUIContext().get("SchemeId"));
		 * if(!schemeId.equals("null")) { IObjectPK tmpPk = new
		 * ObjectUuidPK(schemeId); try { PropertyDoSchemeInfo schemeInfo =
		 * PropertyDoSchemeFactory
		 * .getRemoteInstance().getPropertyDoSchemeInfo(tmpPk);
		 * schemeInfo.setId(BOSUuid.read(schemeId)); info.setScheme(schemeInfo);
		 * } catch (EASBizException e) { this.handleException(e); } catch
		 * (BOSException e) { this.handleException(e); } } }
		 */

		return info;
	}

	public void onLoad() throws Exception {
		kdtBook.checkParsed();
		tblData.checkParsed();
		super.onLoad();
		
		initHeadStyle();
		initTblStyle();

		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);

		if (getOprtState().equals(OprtState.ADDNEW)) {
			contScheme.setEnabled(true);
			prmtScheme.setEnabled(true);
		} else {
			contScheme.setEnabled(false);
			prmtScheme.setEnabled(false);
		}
		
		String sellProjectId = (String) getUIContext().get("sellProjectId");
		SellProjectInfo sellproject = this.getSellProject(sellProjectId);
		this.prmtSellProject.setDataNoNotify(sellproject);
		
		List roomBookIdList = (java.util.List) getUIContext().get("roomBookIdList");
		if(roomBookIdList!=null && !roomBookIdList.isEmpty()){
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("*");
			view.getSelector().add("room.id");
			view.getSelector().add("sign.id");
			view.getSelector().add("propertyDoScheme.id");
			view.getSelector().add("entry.*");
			view.getSelector().add("entryTwo.*");
			
			FilterInfo filter = new FilterInfo();
			
			HashSet roomLoanIdSet = new HashSet();
			for(int i=0; i<roomBookIdList.size(); i++){
				roomLoanIdSet.add(roomBookIdList.get(i));
			}
			filter.getFilterItems().add(new FilterItemInfo("id", roomLoanIdSet, CompareType.INCLUDE));

			view.setFilter(filter);
			
			RoomPropertyBookCollection roomBookCol = RoomPropertyBookFactory.getRemoteInstance().getRoomPropertyBookCollection(view);
			if(roomBookCol != null && !roomBookCol.isEmpty()){
				for(int r=0; r<roomBookCol.size(); r++){
					RoomPropertyBookInfo roomBookInfo = roomBookCol.get(r);
					
					this.loanRoomBookInfo(roomBookInfo);
				}
			}
		}
		//��ʼ�����οؼ�
		String batchId = (String)getUIContext().get("batchId");
		BatchManageInfo batchInfo = null;
		if(batchId != null && !batchId.equals("null")){
			batchInfo = this.getBatchManage(batchId);
		}
		this.prmtBatchManage.setDataNoNotify(batchInfo);
		
		//Ĭ�Ϸ���
		PropertyDoSchemeInfo schemeInfo = null;
		String schemeId = (String)getUIContext().get("schemeId");
		if(schemeId != null && !schemeId.equals("null")){
			schemeInfo = this.getScheme(schemeId);
			this.prmtScheme.setDataNoNotify(schemeInfo);
		}
		
		//������ϱ��
		if(schemeInfo!=null && schemeInfo.getEntryTwo() != null && !schemeInfo.getEntryTwo().isEmpty()){
			PropertyDoSchemeEntryTwoCollection dataCol = schemeInfo.getEntryTwo();
			for(int i=0; i<dataCol.size(); i++){
				PropertyDoSchemeEntryTwoInfo dataInfo = dataCol.get(i);
				IRow row = this.tblData.addRow();
				row.getCell("name").setValue(dataInfo.getName());
				row.getCell("isFinish").setValue(Boolean.FALSE);
				row.getCell("remark").setValue(dataInfo.getDescription());
			}
		}
		
		this.comboPropertyState.setSelectedIndex(-1);
		this.ckIsFinished.setEnabled(false);
		
		//��ʼ���������������ѡ��
		if(schemeInfo != null && schemeInfo.getEntry() != null){
			this.comboCurStep.setRequired(true);
			
			KDComboBox combox = (KDComboBox)this.contCurStep.getBoundEditor();
			combox.removeAllItems();
			for(int i=0; i<schemeInfo.getEntry().size(); i++){
				combox.addItem(schemeInfo.getEntry().get(i).getName());
			}
		}
		this.initPrmtScheme();
		
		this.initPrmtBatchManage();
		
		this.initPrmtRoomSelect();
	}

	protected void initHeadStyle() {
		this.contPropertyState.setEnabled(false);
		this.comboPropertyState.setEnabled(false);

		this.actionAddNew.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionSubmitOption.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionCopyLine.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionPre.setVisible(false);

		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionWorkflowList.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);

		this.actionCreateFrom.setVisible(false);

		this.menuBiz.setVisible(false);
		this.menuView.setVisible(false);
		this.menuTable1.setVisible(false);
		this.menuTool.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.MenuService.setVisible(false);
		this.menuSubmitOption.setVisible(false);

		/*
		 * if(getOprtState().equals(OprtState.ADDNEW)) {
		 * this.ckIsFinished.setEnabled(false); } else
		 * if(getOprtState().equals(OprtState.EDIT)) {
		 * if(AllFinish.equals(editData.getCurStep())) {
		 * this.ckIsFinished.setEnabled(true);
		 * this.ckIsFinished.setSelected(true);
		 * this.contPropertyState.setEnabled(true);
		 * this.comboPropertyState.setEnabled(true);
		 * 
		 * afterCkIsFinished(ckIsFinished.isSelected()); } else {
		 * this.ckIsFinished.setEnabled(checkIsStepFinish(kdtStep,
		 * kdtMaterial));
		 * this.contPropertyState.setEnabled(this.ckIsFinished.isSelected());
		 * this.comboPropertyState.setEnabled(this.ckIsFinished.isSelected());
		 * 
		 * afterCkIsFinished(ckIsFinished.isSelected()); } } else {
		 * this.ckIsFinished.setEnabled(false);
		 * if(AllFinish.equals(editData.getCurStep())) {
		 * this.ckIsFinished.setSelected(true); } }
		 */

		// ���÷����Ĺ�������
		String sellPrjId = String.valueOf(getUIContext().get("sellProjectId"));
		EntityViewInfo view = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("project.id", sellPrjId));
		view.setFilter(filter);
		prmtScheme.setEntityViewInfo(view);

		// txtNumber.setMaxLength(40);
	}

	protected void initTblStyle() throws BOSException, SQLException {
		kdtBook.checkParsed();
		tblData.checkParsed();

		initkdtBook();
		initkdtMaterial();
	}

	protected void prmtRoomAfterSelected(Object newObj, Object oldObj)
			throws BOSException, SQLException, EASBizException {
		int rowIndex = kdtBook.getSelectManager().getActiveRowIndex();
		RoomInfo newRoom = (RoomInfo) newObj;
		for (int index = 0; index < kdtBook.getRowCount(); ++index) {
			if (index == rowIndex)
				continue;

			if (kdtBook.getRow(index).getCell(book_roomNum).getValue() != null) {
				RoomInfo tmpRoom = (RoomInfo) kdtBook.getRow(index).getCell(
						book_roomNum).getValue();

				if (tmpRoom.getId().equals(newRoom.getId())) {
					StringBuffer buffer = new StringBuffer();
					buffer.append("��ѡ��ķ������͵�");
					buffer.append(index + 1);
					buffer.append("�еķ�����룺");
					buffer.append(tmpRoom.getNumber());
					buffer.append("�ظ���");
					MsgBox.showWarning(buffer.toString());

					kdtBook.removeRow(rowIndex);
					abort();
				}
			}
		}

		List roomIdList = new ArrayList();
		roomIdList.add(newRoom.getId().toString());

		/*List roomFullInfo = getRoomFullInform(roomIdList);

		if (roomFullInfo.size() == 1) {
			loadRoomFullInform(kdtBook.getRow(rowIndex), (Map) (roomFullInfo
					.get(0)));
		}

		updateStepAndMaterial();*/
	}

	protected void initkdtBook() throws BOSException, SQLException {
		ObjectValueRender ovr = new ObjectValueRender();
		ovr.setFormat(new BizDataFormat("$number$"));
		/*roomColumn.setRenderer(ovr);
		roomColumn.setEditor(new KDTDefaultCellEditor(prmtRoom));*/

		// ���ñ���ʽ
		kdtBook.getColumn(book_roomCustomer).getStyleAttributes().setLocked(true);
		kdtBook.getColumn("customerPhone").getStyleAttributes().setLocked(true);
		kdtBook.getColumn(book_signDate).getStyleAttributes().setLocked(true);
		FDCHelper.formatTableDate(kdtBook, book_signDate);
		kdtBook.getColumn(book_contractNum).getStyleAttributes().setLocked(true);
		kdtBook.getColumn("contractNo").getStyleAttributes().setLocked(true);

		// ���ÿɱ༭�е��ַ�����
		// ���ÿɱ༭�е��ַ�����
		KDTextField numLimit = new KDTextField();
		numLimit.setMaxLength(80);
		kdtBook.getColumn(book_number).setEditor(new KDTDefaultCellEditor(numLimit));
		
		KDTextField limitLengthText = new KDTextField();
		limitLengthText.setMaxLength(255);
		kdtBook.getColumn(book_propertyNumber).setEditor(new KDTDefaultCellEditor(numLimit));

		kdtBook.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
	}

	protected void initkdtMaterial() {
		FDCHelper.formatTableDate(tblData, mat_date);
		tblData.getColumn(mat_name).getStyleAttributes().setLocked(true);

		KDTextField limitLengthText = new KDTextField();
		limitLengthText.setMaxLength(50);

		tblData.getColumn(mat_des).setEditor(
				new KDTDefaultCellEditor(limitLengthText));

		tblData.getSelectManager().setSelectMode(
				KDTSelectManager.CELL_SELECT);
	}

	protected void kdtBook_tableClicked(KDTMouseEvent e) throws Exception {
		super.kdtBook_tableClicked(e);
		
		//���������£������ѡ�еİ��ҵ��ݣ�ˢ�°������ϱ������
		if(e.getClickCount() == 1 && !this.ckBatchUpdateData.isSelected()){
			RoomPropertyBookInfo bookInfo = (RoomPropertyBookInfo)this.kdtBook.getRow(e.getRowIndex()).getUserObject();
			RoomPropertyBookEntryTwoCollection entryCol = bookInfo.getEntryTwo();
			
			this.tblData.removeRows();
			if(entryCol!=null && !entryCol.isEmpty()){
				for(int i=0; i<entryCol.size(); i++){
					RoomPropertyBookEntryTwoInfo entryInfo =  entryCol.get(i);
					
					IRow dataRow = this.tblData.addRow();
					//���淿���Ȩ���ѡ���У���������������
					dataRow.setUserObject(new Integer(e.getRowIndex()));
					dataRow.getCell("id").setValue(entryInfo.getId());
					dataRow.getCell("name").setValue(entryInfo.getName());
					dataRow.getCell("processDate").setValue(entryInfo.getProcessDate());
					dataRow.getCell("isFinish").setValue(new Boolean(entryInfo.isIsFinish()));
					dataRow.getCell("remark").setValue(entryInfo.getDescription());
				}
			}
		}
	}
	
	protected void kdtBook_editStopped(KDTEditEvent e) throws Exception {
		super.kdtBook_editStopped(e);
	}
	
	protected void tblData_editStopped(KDTEditEvent e) throws Exception {
		super.tblData_editStopped(e);
		
		IRow currentRow = this.tblData.getRow(e.getRowIndex());
		
		if(e.getColIndex() == this.tblData.getColumnIndex("isFinish")){
			boolean isFinish = ((Boolean)e.getValue()).booleanValue();
			if(isFinish){
				this.tblData.getRow(e.getRowIndex()).getCell("processDate").setValue(new Date());
			}
			else{
				this.tblData.getRow(e.getRowIndex()).getCell("processDate").setValue(null);
			}
		}
		//��������
		if(currentRow.getUserObject() != null && currentRow.getCell("id").getValue()!=null){
			int roomRowIndex = ((Integer)currentRow.getUserObject()).intValue();
			if(this.kdtBook.getRow(roomRowIndex).getUserObject() != null){
				RoomPropertyBookInfo bookInfo = (RoomPropertyBookInfo)this.kdtBook.getRow(e.getRowIndex()).getUserObject();
				RoomPropertyBookEntryTwoCollection entryCol = bookInfo.getEntryTwo();
				for(int i=0; i<entryCol.size(); i++){
					RoomPropertyBookEntryTwoInfo entryInfo =  entryCol.get(i);
					if(entryInfo.getId().toString().equals(currentRow.getCell("id").getValue().toString())){
						entryInfo.setName((String)currentRow.getCell("name").getValue());
						entryInfo.setIsFinish(((Boolean)currentRow.getCell("isFinish").getValue()).booleanValue());
						entryInfo.setProcessDate((Date)currentRow.getCell("processDate").getValue());
						entryInfo.setDescription((String)currentRow.getCell("remark").getValue());
					}
				}
			}
		}
	}
	
	protected void ckBatchUpdateData_itemStateChanged(ItemEvent e)
			throws Exception {
		super.ckBatchUpdateData_itemStateChanged(e);
		
		if(this.ckBatchUpdateData.isSelected()){
			this.tblData.removeRows();
			
			PropertyDoSchemeInfo schemeInfo = (PropertyDoSchemeInfo)this.prmtScheme.getValue();
			//������ϱ��
			if(schemeInfo!=null && schemeInfo.getEntryTwo() != null && !schemeInfo.getEntryTwo().isEmpty()){
				PropertyDoSchemeEntryTwoCollection entryCol = schemeInfo.getEntryTwo();
				for(int i=0; i<entryCol.size(); i++){
					PropertyDoSchemeEntryTwoInfo dataInfo = entryCol.get(i);
					IRow row = this.tblData.addRow();
					
					row.getCell("name").setValue(dataInfo.getName());
					row.getCell("isFinish").setValue(Boolean.FALSE);
					row.getCell("remark").setValue(dataInfo.getDescription());
				}
			}
		}
		else{
			this.tblData.removeRows();
		}
	}
	
	protected void ckIsFinished_itemStateChanged(ItemEvent e) throws Exception {
		super.ckIsFinished_itemStateChanged(e);
		
		if(this.ckIsFinished.isSelected()){
			this.comboPropertyState.setEnabled(true);		
		}
		else{
			this.comboPropertyState.setSelectedIndex(-1);
			this.comboPropertyState.setEnabled(false);
		}
	}
	
	protected void comboCurStep_itemStateChanged(ItemEvent e) throws Exception {
		super.comboCurStep_itemStateChanged(e);
		
		String curStep = null;
		if(this.comboCurStep.getSelectedItem() != null){
			curStep = this.comboCurStep.getSelectedItem().toString();
		}
		PropertyDoSchemeInfo schemeInfo = (PropertyDoSchemeInfo)this.prmtScheme.getValue();
		if(curStep!=null && schemeInfo!=null && schemeInfo.getEntry()!=null && !schemeInfo.getEntry().isEmpty()){
			for(int i=0; i<schemeInfo.getEntry().size(); i++){
				PropertyDoSchemeEntryInfo entry = schemeInfo.getEntry().get(i);
				if(curStep.equals(entry.getName())){
					if(entry.isIsFinish()){
						this.ckIsFinished.setSelected(true);
					}
					else{
						this.ckIsFinished.setSelected(false);
					}
					break;
				}
			}
		}
	}
	
	protected boolean isShowAttachmentAction() {
		return false;
	}

	public void loadFields() {
		super.loadFields();
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("bookState");
		sic.add("book.*");
		sic.add("curStep");
		sic.add("sellProject.*");

		return sic;
	}

	protected void btnRoomSelect_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnRoomSelect_actionPerformed(e);
		
		this.f7RoomSelect.setDataBySelector();
	}
	
	protected void btnRoomDelete_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnRoomDelete_actionPerformed(e);
		
		if (this.kdtBook.getRowCount() == 0 || this.kdtBook.getSelectManager().size() == 0) {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
		if(MsgBox.showConfirm2(this,"ȷ��Ҫɾ���÷�����") == MsgBox.CANCEL){
			return;
		}
		
		IRow row = KDTableUtil.getSelectedRow(this.kdtBook);
		this.kdtBook.removeRow(row.getRowIndex());
	}
	
	protected void prmtScheme_dataChanged(DataChangeEvent e) throws Exception {
		Object newObj = e.getNewValue();
		Object oldObj = e.getOldValue();

		if (newObj == null && oldObj != null) {
			this.tblData.removeRows();
		} 
		else if (newObj != null && oldObj == null) {
			loadPropertyScheme((PropertyDoSchemeInfo) newObj, this.tblData);
		}
		else if (newObj != null && oldObj != null) {
			// ��ѡ��ͬһ������ʱ��������
			PropertyDoSchemeInfo newScheme = (PropertyDoSchemeInfo) (newObj);
			PropertyDoSchemeInfo oldScheme = (PropertyDoSchemeInfo) (oldObj);
			if (!newScheme.getId().equals(oldScheme.getId())) {
				loadPropertyScheme((PropertyDoSchemeInfo) newObj, this.tblData);
			}
		}
	}
	
	protected void prmtBatchManage_dataChanged(DataChangeEvent e)
		throws Exception {
		if(e.getNewValue() == null && e.getOldValue() != null){
			this.kdtBook.removeRows();
		}
		else if(e.getNewValue() != null && e.getOldValue() == null){
			BatchManageInfo newBatchInfo = (BatchManageInfo)e.getNewValue();
			this.loadBatchRoom(newBatchInfo.getId().toString());
		}
		else if(e.getNewValue() != null && e.getOldValue() != null){
			BatchManageInfo newBatchInfo = (BatchManageInfo)e.getNewValue();
			BatchManageInfo oldBatchInfo = (BatchManageInfo)e.getOldValue();
			if(!newBatchInfo.getId().toString().equals(oldBatchInfo.getId().toString())){
				this.loadBatchRoom(newBatchInfo.getId().toString());
			}
		}
		
		//����ѡ������γ�ʼ����ӷ���f7�ؼ�
		this.initPrmtRoomSelect();
	}

	protected void f7RoomSelect_dataChanged(DataChangeEvent e) throws Exception {
		super.f7RoomSelect_dataChanged(e);
		
		RoomPropertyBookInfo bookInfo = (RoomPropertyBookInfo)e.getNewValue();
		for(int i=0; i<this.kdtBook.getRowCount(); i++){
			RoomPropertyBookInfo rowBookInfo = (RoomPropertyBookInfo)this.kdtBook.getRow(i).getUserObject();
			if(rowBookInfo.getId().toString().equals(bookInfo.getId().toString())){
				FDCMsgBox.showWarning("�����Ѵ��ڣ�������ѡ��");
	            SysUtil.abort();
			}
		}
		//���ط�����Ϣ
		this.loanRoomBookInfo(bookInfo);
	}

	/**
	 * ����ѡ������Σ����ط����Ȩ��Ϣ
	 * @param batchId
	 * @throws BOSException 
	 * @throws UuidException 
	 * @throws EASBizException 
	 */
	private void loadBatchRoom(String batchId) throws BOSException, EASBizException, UuidException{
		this.kdtBook.removeRows();
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("room.id");
		view.getSelector().add("sign.id");
		view.getSelector().add("propertyDoScheme.id");
		view.getSelector().add("entry.*");
		view.getSelector().add("entryTwo.*");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("batchManage.id", batchId));

		view.setFilter(filter);
		
		RoomPropertyBookCollection roomBookCol = RoomPropertyBookFactory.getRemoteInstance().getRoomPropertyBookCollection(view);
		if(roomBookCol != null && !roomBookCol.isEmpty()){
			for(int r=0; r<roomBookCol.size(); r++){
				RoomPropertyBookInfo roomBookInfo = roomBookCol.get(r);
				this.loanRoomBookInfo(roomBookInfo);
			}
		}
	}
	
	private void loanRoomBookInfo(RoomPropertyBookInfo roomBookInfo) throws EASBizException, BOSException, UuidException{
		//��ȡ������Ϣ
		RoomInfo room = this.getRoomInfo(roomBookInfo.getRoom().getId().toString());
		roomBookInfo.setRoom(room);
		
		IRow row = kdtBook.addRow();
		row.setUserObject(roomBookInfo);
		row.getCell("bookId").setValue(roomBookInfo.getId());
		
		row.getCell("room").setValue(roomBookInfo.getRoom().getNumber());
		row.getCell("roomId").setValue(roomBookInfo.getRoom().getId().toString());
		
		row.getCell("number").setValue(roomBookInfo.getNumber());
		
		if(roomBookInfo.getSign() != null){  //ǩԼ
			SignManageInfo signInfo = this.getSignInfo(roomBookInfo.getSign().getId().toString());
			roomBookInfo.setSign(signInfo);
			row.getCell("customer").setValue(roomBookInfo.getSign().getCustomerNames());
			row.getCell("customerPhone").setValue(roomBookInfo.getSign().getCustomerPhone());
			row.getCell("contractNumber").setValue(roomBookInfo.getSign().getNumber());
			row.getCell("contractNo").setValue(roomBookInfo.getSign().getBizNumber());
			row.getCell("signDate").setValue(roomBookInfo.getSign().getBizDate());
		}
		
		if(roomBookInfo.getPropertyDoScheme() != null){
			PropertyDoSchemeInfo schemeInfo = this.getScheme(roomBookInfo.getPropertyDoScheme().getId().toString());
			roomBookInfo.setPropertyDoScheme(schemeInfo);
		}
		row.getCell("propertNum").setValue(roomBookInfo.getPropertyNumber());
	}
	
	protected void loadPropertyScheme(PropertyDoSchemeInfo schemeInfo, KDTable dataTable) throws EASBizException,
			BOSException {
		IObjectPK pk = new ObjectUuidPK(schemeInfo.getId().toString());

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("Entry.*");
		sic.add("EntryTwo.*");
		PropertyDoSchemeInfo schemeFullInfo = PropertyDoSchemeFactory.getRemoteInstance().getPropertyDoSchemeInfo(pk, sic);
		PropertyDoSchemeEntryCollection stepCols = schemeFullInfo.getEntry();
		PropertyDoSchemeEntryTwoCollection matCols = schemeFullInfo.getEntryTwo();

		if(stepCols != null && !stepCols.isEmpty()){
			this.comboCurStep.setRequired(true);
			
			KDComboBox combox = (KDComboBox)this.contCurStep.getBoundEditor();
			combox.removeAllItems();
			for(int i=0; i<schemeInfo.getEntry().size(); i++){
				combox.addItem(schemeInfo.getEntry().get(i).getName());
			}
		}

		if (dataTable.getRowCount() > 0) {
			dataTable.removeRows();
		}

		for (int i = 0; i < matCols.size(); i++) {
			PropertyDoSchemeEntryTwoInfo matInfo = matCols.get(i);
			IRow row = dataTable.addRow(i);
//			row.getCell("id").setValue(matInfo.getName());
			row.getCell(mat_name).setValue(matInfo.getName());
			row.getCell(mat_date).setValue(new Date());
			row.getCell(mat_isFinish).setValue(Boolean.valueOf(false));
			row.getCell(mat_des).setValue(matInfo.getDescription());
		}
	}
	
	private void checkInput(){
		if(this.prmtTransactor.getValue() == null){
			FDCMsgBox.showInfo("�����˲���Ϊ��!");
			SysUtil.abort();
		}
		else if(this.prmtScheme.getValue() == null){
			FDCMsgBox.showInfo("���ҷ�������Ϊ��!");
			SysUtil.abort();
		}
		else if(this.comboCurStep.getSelectedItem() == null){
			FDCMsgBox.showInfo("��ǰ���̲���Ϊ��!");
			SysUtil.abort();
		}
		else if(this.pkUpdateDate.getValue() == null){
			FDCMsgBox.showInfo("�������ڲ���Ϊ��!");
			SysUtil.abort();
		}
		
		if(this.kdtBook.getRowCount() <= 0){
			FDCMsgBox.showInfo("��Ȩ�����䲻��Ϊ��!");
			SysUtil.abort();
		}
	}
	
	/**
	 * ��ʼ����Ȩ����f7�ؼ�
	 */
	private void initPrmtBatchManage(){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		String sellProjectId = getUIContext().get("sellProjectId").toString();

		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
		filter.getFilterItems().add(new FilterItemInfo("source", BatchManageSourceEnum.PROPERTY_VALUE));
		
		view.setFilter(filter);

		this.prmtBatchManage.setEntityViewInfo(view);
	}
	
	/**
	 * ��ʼ������f7�ؼ�
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void initPrmtScheme() throws EASBizException, BOSException{
		String sellProjectId = String.valueOf(getUIContext().get("sellProjectId"));
		EntityViewInfo view = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();
	
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		
		Set id=new HashSet();
		SellProjectInfo info = new SellProjectInfo();
		info.setId(BOSUuid.read(sellProjectId));
		id = SHEManageHelper.getAllParentSellProjectCollection(info,id);
	
		if (id != null && id.size() > 0) {
			filter.getFilterItems().add(
				new FilterItemInfo("project.id",FDCTreeHelper.getStringFromSet(id),
						CompareType.INNER));
		}else{
			filter.getFilterItems().add(
					new FilterItemInfo("project.id", null,
							CompareType.EQUALS));
		}
		
		view.setFilter(filter);
		prmtScheme.setEntityViewInfo(view);
	}
	
	/**
	 * ��ʼ������ѡ��f7�ؼ������ݰ������ι��˷���
	 */
	private void initPrmtRoomSelect() {
		// ��������
		FilterInfo filter = new FilterInfo();
		
		BatchManageInfo batchManageInfo = (BatchManageInfo)this.prmtBatchManage.getValue();
		if(batchManageInfo == null){
			filter.getFilterItems().add(new FilterItemInfo("batchManage.id", "xxx"));
		}
		else{
			filter.getFilterItems().add(new FilterItemInfo("batchManage.id", batchManageInfo.getId().toString()));
		}
		
		EntityViewInfo view = new EntityViewInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("room.roomBookState", RoomBookStateEnum.BOOKED_VALUE,
			CompareType.NOTINCLUDE));
		
		view.setFilter(filter);

		String queryInfo = "com.kingdee.eas.fdc.sellhouse.app.NewRoomPropertyBookQuery";
		SHEHelper.initF7(this.f7RoomSelect, queryInfo, filter);
	}
	
	private SellProjectInfo getSellProject(String sellProjectId) throws EASBizException, BOSException, UuidException{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		
		SellProjectInfo sellProject = SellProjectFactory.getRemoteInstance()
			.getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(sellProjectId)), sels);
		
		return sellProject;
	}
	
	private RoomInfo getRoomInfo(String roomId) throws EASBizException, BOSException, UuidException{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("number");
		sels.add("sellState");
		RoomInfo roomInfo = RoomFactory.getRemoteInstance()
			.getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)), sels);
		
		return roomInfo;
	}
	
	private SignManageInfo getSignInfo(String signId) throws EASBizException, BOSException{
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("number");
		selCol.add("customerNames");
		selCol.add("customerPhone");
		selCol.add("bizNumber");
		selCol.add("bizDate");
		selCol.add("srcId");
		
		SignManageInfo signInfo = SignManageFactory.getRemoteInstance()
			.getSignManageInfo(new ObjectUuidPK(signId), selCol);
		
		return signInfo;
	}
	
	private PropertyDoSchemeInfo getScheme(String schemeId) throws EASBizException, BOSException{
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("*");
		selCol.add(new SelectorItemInfo("Entry.*"));
		selCol.add(new SelectorItemInfo("EntryTwo.*"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", schemeId));

		PropertyDoSchemeInfo schemeInfo = PropertyDoSchemeFactory.getRemoteInstance()
			.getPropertyDoSchemeInfo(new ObjectUuidPK(schemeId), selCol);
		
		return schemeInfo;
	}
	
	private BatchManageInfo getBatchManage(String batchId) throws EASBizException, BOSException{
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", batchId));

		BatchManageInfo batchInfo = BatchManageFactory.getRemoteInstance()
			.getBatchManageInfo(new ObjectUuidPK(batchId), selCol);
		
		return batchInfo;
	}
	
	/**
	 * �������̣���ȡ��ǰ���̵�Ӧ�������
	 * @param appEntryCol
	 * @param currentAppInfo
	 * @return
	 */
	private Date getApproachPromiseDate(PropertyDoSchemeEntryCollection appEntryCol, 
			PropertyDoSchemeEntryInfo currentAppInfo){
		if(currentAppInfo.getReferenceTime() == null){
			return null;
		}
		else if(currentAppInfo.getReferenceTime().equals("ָ������")){  //��������Ϊָ������
			return currentAppInfo.getScheduledDate();
		}
		else if(currentAppInfo.getReferenceTime().equals("ǩԼ����")){  //��������ΪǩԼ����
			if(this.editData.getSign()==null){
				return null;
			}
			else{
				return this.editData.getSign().getBizDate();
			}
		}
		else{
			for(int i=0; i<appEntryCol.size(); i++){
				PropertyDoSchemeEntryInfo appInfo = appEntryCol.get(i);
				if(appInfo.getName().equals(currentAppInfo.getReferenceTime())){
					Date tempDate = getApproachPromiseDate(appEntryCol, appInfo);
					if(tempDate != null){
						//���ݼ���º����������
						int mon = currentAppInfo.getIntervalMonth();
						int day = currentAppInfo.getIntervalDays();
						return DateTimeUtils.addDuration(tempDate, 0, mon, day);
					}
				}
			}
			return null;
		}
	}
}