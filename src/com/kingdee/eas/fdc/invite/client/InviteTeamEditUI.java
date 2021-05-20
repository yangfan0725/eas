package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
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
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteTeamFactory;
import com.kingdee.eas.fdc.invite.InviteTeamInfo;
import com.kingdee.eas.fdc.invite.InviteTypeCollection;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.eas.fdc.invite.client.offline.util.AttachmentPermissionUtil;

/**
 * �б�С��EditUI
 * @author liangliang_ye
 */
public class InviteTeamEditUI extends AbstractInviteTeamEditUI {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = CoreUIObject.getLogger(InviteTeamEditUI.class);
    /**�������Ƿ����ѡ���������ŵ���Ա*/
	private boolean canSelectOtherOrgPerson = false;
	/**��ǰ�ɱ�����*/
	private CostCenterOrgUnitInfo currentOrg =SysContext.getSysContext().getCurrentCostUnit();
	private boolean isEvent=true;
	private final String USER_COL="person";
	/**����*/
	private final String DEPT_COL="dept";
	/**������˾*/
	private final String DEPTPARENT_COL="deptParent";
	/**ְ������*/
	private final String JOB_COL="job";
	/**��ǰcu��id*/
	private String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
    
    /**
     * �޲ι���
     */
    public InviteTeamEditUI() throws Exception
    {
        super();
    }
    
    /**
     * ��ʼ���������֣����÷�¼�еİ�ť����������ɾ��С���Ա�İ�ť��ӵ���¼��,����ʾ����ɫ������
     */
	public void initUIContentLayout() {
		super.initUIContentLayout();
		logger.info("���÷�¼��ť");
		this.remove(btnAddPerson);
		this.remove(btnDeletePerson);
		String t1=btnAddPerson.getText();
		Icon i1=btnAddPerson.getIcon();
		btnAddPerson=(KDWorkButton) kDContainer1.add(actionAddPerson);
		btnAddPerson.setText(t1);
		btnAddPerson.setIcon(i1);
		String t2=btnDeletePerson.getText();
		Icon i2=btnDeletePerson.getIcon();
		btnDeletePerson=(KDWorkButton) kDContainer1.add(actionDeletePerson);
		btnDeletePerson.setText(t2);
		btnDeletePerson.setIcon(i2);
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		return InviteTeamFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtInviteTeamPerson;
	}

	protected void initListener() {
		super.initListener();
		prmtInviteProject.addDataChangeListener(new DataChangeListener(){
			/**
			 * �б�����ֵ�ı��¼�
			 * @author yell
			 */
			public void dataChanged(DataChangeEvent eventObj) {
				//��Ϊ��
				if(prmtInviteProject.getValue()!=null){
					InviteProjectInfo inviteProjectInfo=(InviteProjectInfo)prmtInviteProject.getValue();
					txtInviteProjectNumber.setText(inviteProjectInfo.getNumber());
//					prmtProject.setValue(inviteProjectInfo.getProject());
					prmtInviteType.setValue(inviteProjectInfo.getInviteType());
				}else{
					//Ϊ��
					txtInviteProjectNumber.setText("");
					prmtProject.setValue(null);
					prmtInviteType.setValue(null);
				}
			}
		});
	}

	/**
	 * ��ʼ����ť
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		btnAddLine.setVisible(false);
		btnInsertLine.setVisible(false);
		btnRemoveLine.setVisible(false);
		btnCreateTo.setVisible(false);
		btnCopy.setVisible(false);
		btnTraceUp.setVisible(false);
		btnTraceDown.setVisible(false);
		btnCreateFrom.setVisible(false);
		this.btnAddPerson.setEnabled(true);
		this.btnDeletePerson.setEnabled(true);
	}
	
	/****************************************************************
	 * �б�����F7���� ����
	 ***************************************************************/
	private void initF7InviteProject() throws Exception {

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("project.*");
		sic.add("inviteType.*");
		Map map = getUIContext();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// �����
		filter.getFilterItems().add(
				new FilterItemInfo("inviteProjectState",
						FDCBillStateEnum.AUDITTED_VALUE));
		Set set=getInviteProjectIsInviteTeam();
		if(set!=null&&set.size()>0){
			filter.getFilterItems().add(
				new FilterItemInfo("id",
						getInviteProjectIsInviteTeam(), CompareType.NOTINCLUDE));
		}
		// �ɹ�����
		if (map.containsKey("type")
				&& map.get("type") instanceof InviteTypeInfo) {
			InviteTypeInfo inviteType = (InviteTypeInfo) map.get("type");
			BOSUuid id = inviteType.getId();
			Set set2=getInviteTypeIdSet(id);
			if(set2!=null&&set2.size()>0){
			filter.getFilterItems().add(
					new FilterItemInfo("inviteType.id", getInviteTypeIdSet(id),
							CompareType.INCLUDE));
			}
		}
		// ��ǰ��֯�µ�����
		if (currentOrg != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", currentOrg.getId()
							.toString()));
		}
		evi.setFilter(filter);
		prmtInviteProject.setSelectorCollection(sic);
		prmtInviteProject.setEntityViewInfo(evi);
	}
	
	public void loadFields() {
		super.loadFields();
		try {
			// ��ȡ����
			getAttachmentNamesToShow();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	/**
	 * ��ʼ�����
	 */
	private void initTable(){
		 KDTextField kdtEntrys_job_TextField = new KDTextField();
		kdtEntrys_job_TextField.setName("kdtEntrys_job_TextField");
		kdtEntrys_job_TextField.setVisible(true);
		kdtEntrys_job_TextField.setEditable(true);
		kdtEntrys_job_TextField.setHorizontalAlignment(2);
		kdtEntrys_job_TextField.setMaxLength(20);
		KDTDefaultCellEditor kdtEntrys_job_CellEditor = new KDTDefaultCellEditor(kdtEntrys_job_TextField);
		getDetailTable().getColumn(JOB_COL).setEditor(kdtEntrys_job_CellEditor);
		setF7Column(USER_COL,"com.kingdee.eas.base.permission.app.UserListQuery", true);
		setF7Column(DEPTPARENT_COL,"com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery", true);
		setF7Column(DEPT_COL,"com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery", true);
		com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRespPerson=
			(com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox)getDetailTable().getColumn(USER_COL).getEditor().getComponent();
		FDCClientUtils.setPersonF7(prmtRespPerson, this,canSelectOtherOrgPerson ? null : cuId,false);
		((com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox)getDetailTable().getColumn(USER_COL).getEditor().getComponent())
			.addDataChangeListener(new DataChangeListener(){
				public void dataChanged(DataChangeEvent eventObj) {
					if(eventObj.getNewValue()==null){
						return;
					}
					Object newValue=eventObj.getNewValue();
					if(newValue instanceof Object[]){
						Object[] values=(Object[])newValue;
						for(int i=0,n=values.length;i<n;i++){
							addPerson(values[i]);
						}
					}else{
						addPerson(newValue);
					}
				}
			});
	}
	
	/**
	 * ���ؼ����浽info֮ǰ����У��
	 */
	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
		try {
			checkTableEmptyAndRepeat("С���Ա���е�",getDetailTable(),"person");
		} catch (BOSException e1) {
			MsgBox.showInfo(e1.getMessage());
			SysUtil.abort();
		}
	}

	/**
	 * �����ĳһ���Ƿ��Ϊ�գ�Ϊ���׳��쳣
	 * @param kdtEntry:���
	 * @param colName:����
	 * @throws com.kingdee.bos.BOSException
	 */
	public void checkTableEmpty(String title,com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry,String colName)
		throws com.kingdee.bos.BOSException{
		for(int i=0,n=kdtEntry.getRowCount();i<n;i++){
		      Object  temp=kdtEntry.getRow(i).getCell(colName).getValue();
		      if(temp==null||temp.toString().trim().equals("")){
		    	  throw new com.kingdee.bos.BOSException((title==null? "":title)+"��"
                		  +(i+1)+"�е�"+
                		  kdtEntry.getHeadRow(0).getCell(colName).getValue()+"����Ϊ�ա�");
		      }
		 }
	}
	
	/**
	 * �����ĳЩ���Ƿ���ڿ�ֵ
	 * @param kdtEntry:���
	 * @param colName:����
	 * @throws com.kingdee.bos.BOSException
	 */
	public void checkTableEmpty(String title,com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry,String[] colNames)
		throws com.kingdee.bos.BOSException{
		for(int i=0,n=colNames.length;i<n;i++){
			checkTableEmpty(title,kdtEntry,colNames[i]);
		}
	}
	
	/**
	 * ���ĳ���в���Ϊ�տգ��Ҳ����ظ�
	 * @param kdtEntry
	 * @param colName
	 */
	public  void checkTableEmptyAndRepeat(String title,com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry,String colName)
	throws com.kingdee.bos.BOSException{
		checkTableEmpty(title,kdtEntry,colName);
		checkTableRepeat(title,kdtEntry,colName);
	}
	
	/**
	 * �����ĳЩ���Ƿ�����ظ�
	 * @param kdtEntry:���
	 * @param colName:����
	 * @throws com.kingdee.bos.BOSException
	 */
	public void checkTableRepeat(String title,com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry,String[] colNames)
		throws com.kingdee.bos.BOSException{
		for(int i=0,n=colNames.length;i<n;i++){
			checkTableRepeat(title,kdtEntry,colNames[i]);
		}
	}
	
	/**
	 * �����ĳһ���Ƿ�����ظ�
	 * @param kdtEntry:���
	 * @param colName:����
	 * @throws com.kingdee.bos.BOSException
	 */
	public void checkTableRepeat(String title,com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry,String colName)
		throws com.kingdee.bos.BOSException{
		for(int i=0,n=kdtEntry.getRowCount();i<n;i++){
		      Object  temp=kdtEntry.getRow(i).getCell(colName).getValue();
		      for(int j=i+1;j<n;j++){
		            if(temp.equals(kdtEntry.getRow(j).getCell(colName).getValue())){
		                  throw new com.kingdee.bos.BOSException((title==null? "":title)+"��"
		                		  +(i+1)+"�к͵�"+(j+1)+"�е�"+
		                		  kdtEntry.getHeadRow(0).getCell(colName).getValue()+"�ظ�");
		            }
		      }
		 }
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		try {
			super.actionSave_actionPerformed(e);
		} catch (BOSException e1) {
			e1.printStackTrace();
			MsgBox.showInfo(e1.getMessage());
			SysUtil.abort();
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		try {
			super.actionSubmit_actionPerformed(e);
		}catch (BOSException e1) {
			e1.printStackTrace();
			MsgBox.showInfo(e1.getMessage());
			SysUtil.abort();
		}
	}
	
	protected IObjectValue createNewDetailData(KDTable table) {
		return new com.kingdee.eas.fdc.invite.InviteTeamPersonInfo();
	}
	
	private void addPerson(Object person){
		PersonInfo personInfo=((PersonInfo)person);
		try {
			String adminid=InviteTeamFactory.getRemoteInstance().getPersonAdminOrgUnit(personInfo.getId());
			AdminOrgUnitInfo adminOrgUnitInfo=AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(
					new ObjectUuidPK(adminid), getAdminOrgUnitSIC());
			addLine(getDetailTable());
			int rowCount=getDetailTable().getRowCount();
			if(rowCount<1){
				return;
			}
			IRow row=getDetailTable().getRow(rowCount-1);
			row.getCell(USER_COL).setValue(personInfo);
			row.getCell(DEPT_COL).setValue(adminOrgUnitInfo);
			if(adminOrgUnitInfo.getParent()==null&&adminOrgUnitInfo.getLevel()==1){
				row.getCell(DEPTPARENT_COL).setValue(adminOrgUnitInfo);
			}else{
				row.getCell(DEPTPARENT_COL).setValue(adminOrgUnitInfo.getParent());									
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private SelectorItemCollection getAdminOrgUnitSIC(){
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		sic.add("level");
		sic.add("parent.id");
		sic.add("parent.number");
		sic.add("parent.name");
		return sic;
	}
	
	private void setF7Column(String col,String queryInfo,boolean enableMulti){
		final KDBizPromptBox table_PromptBox = new KDBizPromptBox();
		table_PromptBox.setQueryInfo(queryInfo);
		table_PromptBox.setVisible(true);
		table_PromptBox.setEditable(true);
		table_PromptBox.setDisplayFormat("$name$");
		table_PromptBox.setEditFormat("$number$");
		table_PromptBox.setCommitFormat("$number$");
		table_PromptBox.setEnabledMultiSelection(enableMulti);
        KDTDefaultCellEditor table_CellEditor = new KDTDefaultCellEditor(table_PromptBox);
        getDetailTable().getColumn(col).setEditor(table_CellEditor);
        ObjectValueRender table_OVR = new ObjectValueRender();
        table_OVR.setFormat(new BizDataFormat("$name$"));
        getDetailTable().getColumn(col).setRenderer(table_OVR);
	}
	
	private Set getInviteTypeIdSet(BOSUuid id) throws EASBizException,
			BOSException {
		Set idSet = new HashSet();
		IObjectPK pk = new ObjectUuidPK(id);
		InviteTypeInfo parentTypeInfo = InviteTypeFactory.getRemoteInstance()
				.getInviteTypeInfo(pk);
		String longNumber = parentTypeInfo.getLongNumber();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber));
		filter.setMaskString("#0 or #1");
		view.setFilter(filter);
		InviteTypeCollection typeCols = InviteTypeFactory.getRemoteInstance()
				.getInviteTypeCollection(view);
		Iterator iter = typeCols.iterator();
		while (iter.hasNext()) {
			InviteTypeInfo tmp = (InviteTypeInfo) iter.next();
			idSet.add(tmp.getId().toString());
		}
		return idSet;
	}
	
	/**
	 * ��ȡ�����Ѿ�ȷ��С�������
	 * @return
	 * @throws Exception
	 */
	private Set getInviteProjectIsInviteTeam() throws Exception {
		Set ips = new HashSet();
		Iterator it = getBizInterface().getCollection("select id,inviteProject.id").iterator();
		while (it.hasNext()) {
			InviteTeamInfo info = (InviteTeamInfo) it.next();
			if (info != null && info.getInviteProject() != null)
				ips.add(info.getInviteProject().getId().toString());
		}
		return ips;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionCopy.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		FDCClientUtils.setRespDeptF7(prmtDept, this);//,canSelectOtherOrgPerson ? null : cuId);
		try {
			initF7InviteProject();
			initTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionAddPerson_actionPerformed(ActionEvent e) throws Exception {
		((com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox)getDetailTable().getColumn(USER_COL).getEditor().getComponent()).setDataBySelector();
	}

	public void actionDeletePerson_actionPerformed(ActionEvent e)
			throws Exception {
		removeLine(getDetailTable());
	}

	protected IObjectValue createNewData() {
		InviteTeamInfo object = new InviteTeamInfo();
		object.setState(FDCBillStateEnum.SAVED);
		object.setOrgUnit(orgUnitInfo);
		object.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		Date createDate = new Date();
		object.setCreateTime(new Timestamp(createDate.getTime()));
		return object;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	/**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = super.getSelectors();
        sic.add(new SelectorItemInfo("inviteProject.inviteType.id"));
        sic.add(new SelectorItemInfo("inviteProject.inviteType.number"));
        sic.add(new SelectorItemInfo("inviteProject.inviteType.name"));
        sic.add(new SelectorItemInfo("inviteProject.project.id"));
        sic.add(new SelectorItemInfo("inviteProject.project.number"));
        sic.add(new SelectorItemInfo("inviteProject.project.name"));
        return sic;
    }

	
	/******************************�����Ǵ�������**************************/

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
		
		Object obj = editData.getState();
		if (obj != null
				&& (obj.toString().equals(FDCBillStateEnum.AUDITTING.toString())
					||obj.toString().equals(com.kingdee.eas.fi.cas.BillStatusEnum.AUDITING.toString()))) {
			isEdit = true;
		} 
		isEdit = AttachmentPermissionUtil.checkAuditingAttachmentEdit(editData.getState(), boID,isEdit);
		AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		info.setBoID(boID);
		MultiApproveUtil.showAttachmentManager(info,this,editData,String.valueOf("1"),isEdit);

		getAttachmentNamesToShow(); // ��Ӹ������Զ���ʾ�������п���
	}

	/**
	 * �������Ѹ�����Ϣ��ʾ�������б���
	 */
	private boolean getAttachmentNamesToShow() throws Exception {

		isEvent = false;
		this.cmbAttachments.removeAllItems();
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
				this.cmbAttachments.addItem(attachment);
				this.cmbAttachments.setUserObject(attachment);
			}
			if(boAttchColl.size() == 1){
				this.cmbAttachments.addItem("    ");
				this.cmbAttachments.setUserObject(null);
			}
			isEvent = true;
		}
		return hasAttachment;
	}

	/**
	 * ��������ѡ��ʱ�����ļ�
	 */
	protected void cmbAttachments_itemStateChanged(ItemEvent e)
			throws Exception {
		if(StringUtils.isEmpty(e.getItem().toString()))return;
		if(isEvent && e.getStateChange()==ItemEvent.SELECTED){
			// �õ������б����ѡ�еĸ�����ID
			FileGetter fileGetter = getFileGetter();
			Object selectObj = this.cmbAttachments.getSelectedItem();
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
}