/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
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
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ActivityInstInfo;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.metas.WfState;
import com.kingdee.bos.workflow.monitor.client.WorkFlowMonitorPanel;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.bos.workflow.service.ormrpc.IWfFacade;
import com.kingdee.bos.workflow.service.ormrpc.WfFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.cssp.ISupplier;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierLinkManInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierPurchaseInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierPurchaseInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierPurchaseInfoInfo;
import com.kingdee.eas.basedata.master.cssp.client.SupplierEditUI;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.InquiryFileEntryInfo;
import com.kingdee.eas.fdc.invite.InquiryFileFactory;
import com.kingdee.eas.fdc.invite.InquiryFileInfo;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteTypeCollection;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.client.offline.util.AttachmentPermissionUtil;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.framework.client.workflow.EASWfServiceClient;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.scm.common.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ������ѯ���ļ���������
 * 
 * @author guangyue_liu
 * 
 */
public class InquiryFileEditUI extends AbstractInquiryFileEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(InquiryFileEditUI.class);

	CostCenterOrgUnitInfo currentOrg = null;
	
	CtrlUnitInfo cu = null;
	
	private boolean isEvent = false;
	
	ItemAction actionAddSupplier = null;
	ItemAction actionRemoveSupplier = null;
	ItemAction actionViewSupplier = null;
	
	private Map supplierIDs = new HashMap();
	
	public InquiryFileEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		//��ӷ�¼��尴ť
		addButtonOnEntryPanel();
		
		initMyData();
		
		initF7();
		 
		super.onLoad();
		
		setActionVisible();
		
		setTableStyle();
		
		isEvent = true;
		
		storeFields();
		initOldData(editData);
	}

	/**
	 * ���ñ�����ʽ
	 */
	private void setTableStyle() {
		//�����ѡ��ģʽ
		kdtEntrys.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		//���õȼ��п�����ʾ
		kdtEntrys.getColumn("supplier.grade").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		//���ȫ�������������Ա༭
		kdtEntrys.getStyleAttributes().setLocked(true);
		
	}

	
	protected void initWorkButton() {
		super.initWorkButton();
		
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.menuView.setVisible(false);
		this.menuSubmitOption.setVisible(false);
	}
	
	/**
	 * ���ò����¼����ɼ�
	 */
	private void setActionVisible() {
		actionPre.setVisible(false);
		actionNext.setVisible(false);
		actionLast.setVisible(false);
		actionFirst.setVisible(false);
		actionCopy.setVisible(false);
		actionCopyFrom.setVisible(false);
		
		actionCreateFrom.setVisible(false);
		actionCreateTo.setVisible(false);
		
	}

	
	protected void initDataStatus() {
		super.initDataStatus();
		
	}
	
	/**
	 * ��������ʼ����
	 */
	private void initMyData() {
		
		currentOrg = SysContext.getSysContext().getCurrentCostUnit();		
		
		cu = SysContext.getSysContext().getCurrentCtrlUnit();
	}

	/**
	 * ��������ʼF7
	 */
	private void initF7() throws Exception {

		String cuId = cu.getId().toString();
		
		initF7InviteProject();
		// ��ʼ�����Ʋ���
		FDCClientUtils.setRespDeptF7(prmtRespDept, this);

		// ��F7Ϊ���ؿؼ�����Ҫ���ڷ�¼������¼�����
		FDCClientUtils.initSupplierF7(this, prmtSupplier, cuId);
		
		((GeneralKDPromptSelectorAdaptor)prmtSupplier.getSelector()).setIsMultiSelect(true);
	
		//����������������������ʾ
		this.prmtCreator.setDisplayFormat("$name$");
		this.prmtAuditor.setDisplayFormat("$name$");
	}

	/**
	 * ��������ʼ����F7
	 * @throws Exception
	 */
	private void initF7InviteProject() throws Exception{
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("project.*");
		sic.add("inviteType.*");
		
		
		Map map = getUIContext();
		
		EntityViewInfo evi = new  EntityViewInfo();
		
		FilterInfo filter = new FilterInfo();
		//�����
		filter.getFilterItems().add(new FilterItemInfo("inviteProjectState",FDCBillStateEnum.AUDITTED_VALUE));
		//��ѯ�۵�����
		if(!getInviteProjectIsInquiry().isEmpty()){
			filter.getFilterItems().add(new FilterItemInfo("id",getInviteProjectIsInquiry(),CompareType.NOTINCLUDE));
		}
		//�ɹ�����
		if(map.containsKey("type")&&map.get("type") instanceof InviteTypeInfo){
			InviteTypeInfo inviteType = (InviteTypeInfo)map.get("type");
			BOSUuid id = inviteType.getId();

			filter.getFilterItems().add(new FilterItemInfo("inviteType.id", getInviteTypeIdSet(id),CompareType.INCLUDE));
		}
		//��ǰ��֯�µ�����
		if (currentOrg != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", currentOrg.getId()
							.toString()));
		}
		evi.setFilter(filter);
		
		prmtinviteProject.setSelectorCollection(sic);
		prmtinviteProject.setEntityViewInfo(evi);
		
		
	}

	/**
	 * ��������ȡ�ɹ����͵ļ���
	 * @param id
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private Set getInviteTypeIdSet(BOSUuid id) throws EASBizException,
			BOSException {
		
		Set idSet = new HashSet();
		IObjectPK pk = new ObjectUuidPK(id);
		InviteTypeInfo parentTypeInfo = InviteTypeFactory.getRemoteInstance()
				.getInviteTypeInfo(pk);

		String longNumber = parentTypeInfo.getLongNumber();
		
		EntityViewInfo view = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber + "!%",	CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber));
		filter.setMaskString("#0 or #1");
		
		view.setFilter(filter);

		InviteTypeCollection typeCols = InviteTypeFactory.getRemoteInstance().getInviteTypeCollection(view);

		Iterator iter = typeCols.iterator();
		while (iter.hasNext()) {
			InviteTypeInfo tmp = (InviteTypeInfo) iter.next();
			if(tmp!=null){
				idSet.add(tmp.getId().toString());
			};
		}

		return idSet;
	}
	
	/**
	 * ��������ȡ��ѯ�۵�����
	 * @return
	 * @throws BOSException
	 * @throws Exception
	 */
	private Set getInviteProjectIsInquiry() throws Exception{
		
		Set ips = new HashSet();
		Iterator it = getBizInterface().getCollection().iterator();
		while(it.hasNext()){
			InquiryFileInfo info = (InquiryFileInfo)it.next();
			if(info!= null && info.getInviteProject() != null){
				ips.add(info.getInviteProject().getId().toString());
			}
		}
		
		return ips;
	}
	
	public void loadFields() {
		
		super.loadFields();
		
		kdtEntrys.removeRows();
		supplierIDs.clear();
		
		Object [] suppliers = new Object[editData.getEntrys().size()];
		for(int i = 0,n = editData.getEntrys().size();i<n;i++){
			InquiryFileEntryInfo entry = editData.getEntrys().get(i);
			suppliers[i] = entry.getSupplier();
		}
		prmtSupplier.setData(suppliers);
		
		//�Ѹ����ӵ������б���
		try {
			getAttachmentNamesToShow();
		} catch (Exception e) {
			this.handUIException(e);
		}
		cboAttachment.setEnabled(true);
		
	}
	
	
	/**
	 * �������Ѹ�����Ϣ��ʾ�������б���
	 */
	private boolean getAttachmentNamesToShow() throws Exception {

		isEvent = false;
		this.cboAttachment.removeAllItems();
		isEvent = true;
		String boID = getSelectBOID();
		boolean hasAttachment = false;
		if (boID == null) {
			return hasAttachment;
		}
		
		EntityViewInfo view = new EntityViewInfo();
		
		SelectorItemCollection itemColl = new SelectorItemCollection();
		itemColl.add(new SelectorItemInfo("id"));
		itemColl.add(new SelectorItemInfo("attachment.name"));
		itemColl.add(new SelectorItemInfo("attachment.id"));
		
		view.getSelector().addObjectCollection(itemColl);

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", boID));
		
		view.setFilter(filter);

		BoAttchAssoCollection boAttchColl = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		
		if (boAttchColl != null && boAttchColl.size() > 0) {
			hasAttachment = true;
			isEvent = false;
			for (int i = 0; i < boAttchColl.size(); i++) {
				AttachmentInfo attachment = (AttachmentInfo) boAttchColl.get(i).getAttachment();
				this.cboAttachment.addItem(attachment);
				this.cboAttachment.setUserObject(attachment);
			}
			if(boAttchColl.size() == 1){
				this.cboAttachment.addItem("    ");
				this.cboAttachment.setUserObject(null);
			}
			
			isEvent = true;
		}
		return hasAttachment;
	}
	
	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
        sic.add(new SelectorItemInfo("inviteProject.project.name"));
        sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("inviteProject.inviteType.name"));
		sic.add(new SelectorItemInfo("entrys.supplier.*"));
        return sic;
	}

	/**
	 * ��������ӷ�¼��尴ť
	 */
	protected void addButtonOnEntryPanel() {

		// ��ӹ�Ӧ��
		actionAddSupplier = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddSupplier_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};

		// ɾ����Ӧ��
		actionRemoveSupplier = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveSupplier_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};

		// �鿴��Ӧ����Ϣ
		actionViewSupplier = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionViewSupplier_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};

		initWorkBtn(actionAddSupplier,"imgTbtn_addline", contSupplierEntry, "���");
		initWorkBtn(actionRemoveSupplier,"imgTbtn_deleteline", contSupplierEntry, "ɾ��");
		initWorkBtn(actionViewSupplier,"imgTbtn_sortstandard", contSupplierEntry, "�鿴������ϸ��Ϣ");

	}

	/**
	 * ������������¼��ť
	 * 
	 * @param action
	 * @param iconName
	 * @param parentContainer
	 * @param text
	 * @return
	 */
	private KDWorkButton initWorkBtn(Action action, String iconName,
			KDContainer parentContainer, String text) {
		action.putValue(Action.SMALL_ICON, EASResource.getIcon(iconName));
		KDWorkButton btn = new KDWorkButton();
		btn = (KDWorkButton) parentContainer.add(action);
		btn.setToolTipText(text);
		btn.setText(text);
		return btn;
	}

	
	
	/**
	 * �����������ϴ� 
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		String boID = getSelectBOID();
		if (boID == null) {
			return;
		}
		boolean isEdit = false;
		
		if (STATUS_ADDNEW.equals(getOprtState())
				|| STATUS_EDIT.equals(getOprtState())) {
			isEdit = true;
		}
		
		isEdit = AttachmentPermissionUtil.checkAuditingAttachmentEdit(editData.getState(), boID,isEdit);
		
		AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		info.setBoID(boID);
		MultiApproveUtil.showAttachmentManager(info,this,editData,String.valueOf("1"),isEdit);

		getAttachmentNamesToShow(); // ��Ӹ������Զ���ʾ�������п���
	}
	
	/**
	 * ��������¼������Ӧ�̣��򿪹�Ӧ�̽���
	 * 
	 * @param e
	 */
	protected void actionAddSupplier_actionPerformed(ActionEvent e) throws Exception {
		//������Ӧ��F7
		prmtSupplier.setDataBySelector();
	}

	/**
	 * ��������¼ɾ����Ӧ��
	 * 
	 * @param e
	 */
	protected void actionRemoveSupplier_actionPerformed(ActionEvent e) throws Exception {
		if(OprtState.VIEW.endsWith(getOprtState())){
			return ;
		}
		checkSelected();
		
		IRow row = kdtEntrys.getRow(KDTableUtil.getSelectedRow(kdtEntrys));
		//�Ƴ���MAP�еĹ�Ӧ��
		SupplierInfo supplier = (SupplierInfo)row.getUserObject();
		if(supplier!=null){
			supplierIDs.remove(supplier.getId().toString());
		}
		
		kdtEntrys.removeRow(row.getRowIndex());
	}

	/**
	 * ��������¼��Ӧ��ѡ��鿴
	 * 
	 * @param e
	 */
	protected void actionViewSupplier_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		// �ڲ鿴������֮ǰ��������Ƿ񻹴���
		checkObjectExists();
		
		UIContext uiContext = new UIContext(this);
        uiContext.put(UIContext.ID, getSelectedKeyValue());
        IUIWindow uiWindow = null;
        if (SwingUtilities.getWindowAncestor(this) != null && SwingUtilities.getWindowAncestor(this) instanceof JDialog){
            uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(getSupplierEditUIName(), uiContext, null,
                    OprtState.VIEW);

        }
        else{
            // ����UI������ʾ
            uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(getSupplierEditUIName(), uiContext, null,
                    OprtState.VIEW);
        }

        uiWindow.show();
	}
	
	/**
	 * ��������Ӧ��ѡ���޸ķ�¼
	 */
	protected void prmtSupplier_dataChanged(DataChangeEvent e) throws Exception {
		Object obj = e.getNewValue();
		if(obj instanceof Object []){
			Object [] suppliers = (Object [])obj;
			for (int i = 0, n = suppliers.length; i < n ; i++) {
				if(suppliers[i]==null){
					continue;
				} 
				addSupplierInEntry((SupplierInfo) suppliers[i]);
			}
		}else if(obj instanceof SupplierInfo){
			addSupplierInEntry((SupplierInfo) obj);
		}else{
			
		}
	}
	
	/**
	 * ��������������ѡ��ʱ�����ļ�
	 */
	protected void cboAttachment_itemStateChanged(ItemEvent e) throws Exception {
		if(StringUtils.isEmpty(e.getItem().toString()))return;
		if(isEvent && e.getStateChange()==ItemEvent.SELECTED){
			// �õ������б����ѡ�еĸ�����ID
			FileGetter fileGetter = getFileGetter();
			Object selectObj = this.cboAttachment.getSelectedItem();
			if (selectObj != null) {
				String attachId = ((AttachmentInfo) selectObj).getId().toString();
				fileGetter.viewAttachment(attachId);
			}
		}
	}
	
	/**
	 *  �õ�����FilterGetter��"���ظ���"��"�򿪸���"��ʵ��zouwen
	 *  ���������ε��ã���������
	 * @return FileGetter���ʵ��
	 * @throws Exception
	 */

	private FileGetter getFileGetter() throws Exception {
		FileGetter fileGetter = null;

		if (getUIContext().get("fileGetter") == null) {
			fileGetter = new FileGetter((IAttachment) AttachmentFactory
					.getRemoteInstance(), AttachmentFtpFacadeFactory
					.getRemoteInstance());
			getUIContext().put("fileGetter", fileGetter);
		}else{
			fileGetter = (FileGetter) getUIContext().get("fileGetter");
		}
		return fileGetter;
	}
	
	/**
	 * �������ѹ�Ӧ����Ϣ��ӵ���¼��
	 * @param supplier
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected void addSupplierInEntry(SupplierInfo supplier) throws EASBizException, BOSException{
		
		if(supplierIDs.containsKey(supplier.getId().toString())){
			throw new FDCInviteException(FDCInviteException.INQUIRY_SUPPLIER,new Object[]{"["+supplier.getNumber() + "  " +supplier.getName()+"]"});
		}
		
		//��Ӧ�̲ɹ���Ϣ
		SupplierPurchaseInfoInfo supplierPur = getSupplierPurchaseInfo(supplier);
		
		if(supplierPur != null){
			supplier = supplierPur.getSupplier();
		}
		
		IRow row = kdtEntrys.addRow();
		
		row.getCell("supplier.id").setValue(supplier.getId().toString());
		row.getCell("supplier.number").setValue(supplier.getNumber());
		row.getCell("supplier.name").setValue(supplier.getName());
		row.getCell("supplier.address").setValue(supplier.getAddress());
		
		if(supplier.getIndustry()!=null){
			row.getCell("supplier.industry").setValue(supplier.getIndustry().getName());
		}
		
		
		if(supplierPur != null){
			if(supplierPur.getGrade()!=null){
				row.getCell("supplier.grade").setValue(supplierPur.getGrade());
			}
				
			//ȡ��Ӧ�̲ɹ���Ϣ����ϵ�˵ĵ�һ��
			if(supplierPur.getSupplierLinkMan()!=null && supplierPur.getSupplierLinkMan().get(0) != null){
				SupplierLinkManInfo linkMan = supplierPur.getSupplierLinkMan().get(0);
				row.getCell("supplier.linkman").setValue(linkMan.getContactPerson());
				row.getCell("supplier.phone").setValue(linkMan.getPhone());
			}
		}
		
		row.setUserObject(supplier);
		
		supplierIDs.put(supplier.getId().toString(),supplier);
	}

	/**
	 * �������б���Ŀ�ı�ʱ���޸�������룬������Ŀ���ɹ�����
	 */
	protected void prmtinviteProject_dataChanged(DataChangeEvent e)
			throws Exception {

		txtInviteNumber.setText(null);
		txtCurProject.setText(null);
		txtInviteType.setText(null);
		
		InviteProjectInfo inviteProject = (InviteProjectInfo) e.getNewValue();
		if (inviteProject != null) {
			// �������
			this.txtInviteNumber.setText(inviteProject.getNumber());
			// ������Ŀ
		/*	if(inviteProject.getProject() != null){
				this.txtCurProject.setText(inviteProject.getProject().getName());
			}*/
			// �ɹ�����
			if(inviteProject.getInviteType() != null){
				this.txtInviteType.setText(inviteProject.getInviteType().getName());
			}
		} 
	}

	/**
	 * Ϊ�����������ˢ�¹��� haiti_yang 2007-03-19
	 */
	public void checkSelected() {
		if (kdtEntrys.getRowCount() == 0
				|| kdtEntrys.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource	+ "Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	/**
	 * �ڲ���ѡ�е��б���֮ǰ���������Ƿ���ڡ����������쳣֪ͨ��������
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws Exception
	 */
    private void checkObjectExists() throws BOSException, EASBizException, Exception
    {
    	ISupplier iSupplier = SupplierFactory.getRemoteInstance();
        if (getSelectedKeyValue() == null)
        {
            return;
        }
        if (!iSupplier.exists(new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue()))))
        {
        	//TODO ���¼��ع�Ӧ������
            throw new EASBizException(EASBizException.CHECKEXIST);
        }
    }
    
    private int selectIndex = -1;
    /**
     * ��ȡ��ǰѡ���е�����
     *
     * @return ���ص�ǰѡ���е�����������ǰѡ����Ϊ�գ����ߵ�ǰѡ���е�������Ϊ�գ��򷵻�null
     */
    protected String getSelectedKeyValue()
    {
    	String keyFiledName= "supplier.id";
        int[] selectRows = KDTableUtil.getSelectedRows(this.kdtEntrys);
        selectIndex=-1;
        if (selectRows.length > 0)
        {
        	selectIndex=selectRows[0];
        }
        return ListUiHelper.getSelectedKeyValue(selectRows,this.kdtEntrys,keyFiledName);
    }
    
    /**
     * �������򿪲鿴��Ӧ�̽����ģʽ
     * @return
     */
    protected String getEditUIModal()
    {
        String openModel = UIConfigUtility.getOpenModel();
        if (openModel != null)
        {
            return openModel;
        }
        else
        {
            return UIFactoryName.NEWWIN;
        }
    }
    
    protected SupplierPurchaseInfoInfo getSupplierPurchaseInfo(SupplierInfo supplier) throws EASBizException, BOSException{
    	
    	//��ѯʵ��
    	EntityViewInfo evi = new EntityViewInfo();
    	
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("grade");
    	sic.add("supplier.*");
    	sic.add("supplier.industry.name");
    	sic.add("supplierLinkMan.*");
    	evi.setSelector(sic);
    	
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("supplier.id",supplier.getId().toString()));
    	
    	evi.setFilter(filter);
    	
    	SupplierPurchaseInfoCollection supplierPur = SupplierPurchaseInfoFactory.getRemoteInstance().getSupplierPurchaseInfoCollection(evi);
    	if(supplierPur!=null && supplierPur.get(0)!=null){
    		return supplierPur.get(0);
    	}
    	
    	return null;
    }
    
    /**
     * �������鿴��Ӧ�̵���Ϣ����
     * @return
     */
    private String getSupplierEditUIName() {
    	return SupplierEditUI.class.getName();
	}
    
   
    
	public void storeFields() {
		
		super.storeFields();
		
		storeEntrys();
	}

	/**
	 * �����������¼�еĹ�Ӧ�̵���Ӧ�ֶ�
	 */
	protected void storeEntrys(){
		
		editData.getEntrys().clear();
		
		for(int i = 0 ;i< kdtEntrys.getRowCount();i++){
			//�ѹ�Ӧ����Ϣ���浽��¼��
			InquiryFileEntryInfo entry = new InquiryFileEntryInfo();
			entry.setParent(editData);
			entry.setSupplier((SupplierInfo) kdtEntrys.getRow(i).getUserObject());
			editData.getEntrys().add(entry);
		}
		
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return InquiryFileFactory.getRemoteInstance();
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		InquiryFileEntryInfo entry = new InquiryFileEntryInfo();
		return entry;
	}

	protected IObjectValue createNewData() {
		InquiryFileInfo objectValue = new InquiryFileInfo();
		objectValue.setCreator((UserInfo) (SysContext.getSysContext()
				.getCurrentUser()));
		return objectValue;
	}


}