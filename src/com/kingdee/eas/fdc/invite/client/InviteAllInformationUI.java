/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.invite.AcceptanceLetterCollection;
import com.kingdee.eas.fdc.invite.AcceptanceLetterFactory;
import com.kingdee.eas.fdc.invite.AcceptanceLetterInfo;
import com.kingdee.eas.fdc.invite.AppraiseResultCollection;
import com.kingdee.eas.fdc.invite.AppraiseResultFactory;
import com.kingdee.eas.fdc.invite.AppraiseResultInfo;
import com.kingdee.eas.fdc.invite.AppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.ExpertQualifyCollection;
import com.kingdee.eas.fdc.invite.ExpertQualifyFactory;
import com.kingdee.eas.fdc.invite.ExpertQualifyInfo;
import com.kingdee.eas.fdc.invite.InviteAllInformationCollection;
import com.kingdee.eas.fdc.invite.InviteAllInformationFactory;
import com.kingdee.eas.fdc.invite.InviteAllInformationInfo;
import com.kingdee.eas.fdc.invite.InviteDocumentsCollection;
import com.kingdee.eas.fdc.invite.InviteDocumentsFactory;
import com.kingdee.eas.fdc.invite.InviteDocumentsInfo;
import com.kingdee.eas.fdc.invite.InviteFileMergeCollection;
import com.kingdee.eas.fdc.invite.InviteFileMergeFactory;
import com.kingdee.eas.fdc.invite.InviteFileMergeInfo;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * �б�ִ����Ϣ��������� ���õ�UI
 */
public class InviteAllInformationUI extends AbstractInviteAllInformationUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteAllInformationUI.class);
    
    private boolean isFirstLoad=true;
    private boolean hasRemove=false;
    
    private String fileMergeId = null;
    private String supplierId = null;
    private String expertId = null;
    private String templateId = null;
    
    private String prjId = null ;
    private String prjName = null ;
    
    private String appResultId = null;
    private String letterId = null;
    
    private String documentId = null;
    private Map initData = null;
    /**
     * output class constructor
     */
    public InviteAllInformationUI() throws Exception
    {
        super();
    }
   
    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	String listUI = (String)getUIContext().get("LIST_UI");
    	if(this.getUIContext().containsKey("isFromWorkflow")){
    		super.actionSubmit_actionPerformed(e);
    	}
    	else if(listUI!=null&&listUI.equals("com.kingdee.eas.fdc.invite.client.InviteFileMergeListUI"))
    	{
    		super.actionSubmit_actionPerformed(e);
    		
    		this.actionAudit.setVisible(true);
        	this.actionAudit.setEnabled(true);
        	
        	this.actionUnAudit.setEnabled(false);
        	this.actionUnAudit.setVisible(false);
        	
        	this.actionSubmit.setEnabled(false);
        	this.actionRemove.setEnabled(true);
    	}
    }


    /**
     * output actionAudit_actionPerformed
     */
    
    protected String[] getStateForAudit() {
		return new String[]{FDCBillStateEnum.SUBMITTED_VALUE};
	}
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
		if(!checkBeforeOprate(getStateForAudit()))
    	{
    		StringBuffer buffer = new StringBuffer();
        	buffer.append("�������״̬Ϊ");
        	buffer.append(((InviteAllInformationInfo)editData).getState().getAlias());
        	buffer.append(", ����ִ�д˲���");
        	
        	MsgBox.showWarning(this, buffer.toString());
			SysUtil.abort();
    	}

		List idList = new ArrayList();
		idList.add(editData.getId().toString());
		boolean hasMutex = false;
		boolean isSuccess = false ;
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			BOSUuid pk = BOSUuid.read(idList.get(0).toString());
			InviteAllInformationFactory.getRemoteInstance().audit(pk);
			
			isSuccess = true ;
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
			isSuccess = false; 
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
		
		if(isSuccess)
		{
			this.actionAudit.setVisible(false);
	    	this.actionAudit.setEnabled(false);
	    	
	    	this.actionUnAudit.setEnabled(true);
	    	this.actionUnAudit.setVisible(true);
	    	
	    	this.actionRemove.setEnabled(false);
	    	this.actionSubmit.setEnabled(false);
		}
    }

    protected boolean checkBeforeOprate(String[] states) throws Exception 
	{
		boolean flag = false;
		
		String id = (String)getUIContext().get(UIContext.ID);

		if(id==null){
			return flag ;
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		InviteAllInformationCollection coll = InviteAllInformationFactory.getRemoteInstance().getInviteAllInformationCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			InviteAllInformationInfo element = (InviteAllInformationInfo) iter.next();
			String billState = element.getState().getValue();
			
//			��鵥���Ƿ��ڹ�������
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());

			boolean pass = false;
			for (int i = 0; i < states.length; i++) 
			{
				if(billState.equals(states[i])) 
				{
					pass = true;
				}
			}
			if(!pass)
			{
				flag = false;
				break ;
			}
			else
			{
				flag = pass;
			}
		}

		return flag;
	}
    protected String[] getStateForUnAudit() {
		return new String[]{FDCBillStateEnum.AUDITTED_VALUE};
	}
    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(!checkBeforeOprate(getStateForUnAudit()))
    	{
    		StringBuffer buffer = new StringBuffer();
        	buffer.append("�������״̬Ϊ");
        	buffer.append(((InviteAllInformationInfo)editData).getState().getAlias());
        	buffer.append(", ����ִ�д˲���");
        	
        	MsgBox.showWarning(this, buffer.toString());
			SysUtil.abort();
    	}

		List idList = new ArrayList();
		idList.add(editData.getId().toString());
		boolean hasMutex = false;
		
		boolean isSuccess = false ;
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "UnAudit");
			BOSUuid pk = BOSUuid.read(idList.get(0).toString());
			InviteAllInformationFactory.getRemoteInstance().unAudit(pk);
			isSuccess = true;
			
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
			isSuccess = false ;
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
		}
		
		if(isSuccess)
		{
			this.actionAudit.setVisible(true);
	    	this.actionAudit.setEnabled(true);
	    	
	    	this.actionUnAudit.setEnabled(false);
	    	this.actionUnAudit.setVisible(false);
	    	
	    	this.actionSubmit.setEnabled(true);
	    	this.actionRemove.setEnabled(true);
		}
    }

	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return InviteAllInformationFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		KDTextField txtNumber = new KDTextField();
		return txtNumber;
	}
	
	private String getInviteDocumentId(String prjId) throws BOSException{
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id where inviteProject = '");
		buffer.append(prjId);
		buffer.append("' state = '");
		buffer.append(FDCBillStateEnum.AUDITTED_VALUE);
		buffer.append("'");
		InviteDocumentsCollection cols = InviteDocumentsFactory.getRemoteInstance().getInviteDocumentsCollection(buffer.toString());
		
		if(cols.size() > 0)
		{
			InviteDocumentsInfo info = cols.get(0);
			return info.getId().toString();
		}
		
		return null ;
	}
	
	private String getFileMergeId(String prjId) throws BOSException
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id where inviteProject = '");
		buffer.append(prjId);
		buffer.append("' state = '");
		buffer.append(FDCBillStateEnum.AUDITTED_VALUE);
		buffer.append("'");
		InviteFileMergeCollection cols = InviteFileMergeFactory.getRemoteInstance().getInviteFileMergeCollection(buffer.toString());
		
		if(cols.size() > 0)
		{
			InviteFileMergeInfo info = cols.get(0);
			return info.getId().toString();
		}
		
		return null ;
	}
	
	private String getSupplierQualifyId(String prjId) throws BOSException
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id where inviteProject = '");
		buffer.append(prjId);
		buffer.append("' state = '");
		buffer.append(FDCBillStateEnum.AUDITTED_VALUE);
		buffer.append("'");
		
		SupplierQualifyCollection cols = SupplierQualifyFactory.getRemoteInstance().getSupplierQualifyCollection(buffer.toString());
		
		if(cols.size() > 0)
		{
			SupplierQualifyInfo info = cols.get(0);
			return info.getId().toString();
		}
		
		return null ;
	}
	
	private String getExpertQualifyId(String prjId) throws BOSException
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id where inviteProject = '");
		buffer.append(prjId);
		buffer.append("' state = '");
		buffer.append(FDCBillStateEnum.AUDITTED_VALUE);
		buffer.append("'");
		
		ExpertQualifyCollection cols = ExpertQualifyFactory.getRemoteInstance().getExpertQualifyCollection(buffer.toString());
		
		if(cols.size() > 0)
		{
			ExpertQualifyInfo info = cols.get(0);
			
			return info.getId().toString();
		}
		
		return null;
	}
	
	private String getAppraiseTemplateId(String prjId) throws EASBizException, BOSException
	{
		IObjectPK  pk = new ObjectUuidPK(prjId);
		InviteProjectInfo prjInfo = InviteProjectFactory.getRemoteInstance().getInviteProjectInfo(pk);
		
		if(prjInfo.getAppraiseTemplate() != null)
		{
			return prjInfo.getAppraiseTemplate().getId().toString();
		}
		
		return null;
	}

	private void addPanel(String id, String uiClass, String title)
	throws UIException {
		addPanel(this.plInviteAllInfo,id,uiClass,title);
	}
	
	private void addPanel(KDTabbedPane tabPanel, String id, String uiClass, String title) throws UIException {
		if (id == null) {
			KDScrollPane scrollPane = new KDScrollPane();
			tabPanel.add(scrollPane, title);
			return;
		}
		if (isFirstLoad) {
			if (title.equals("�б�����")) {
				KDScrollPane scrollPane = new KDScrollPane();
				tabPanel.add(scrollPane, title);
				int index = tabPanel.indexOfTab(title);
				if (index == 0) {
					// �򿪽���ʱ���ص�һ��ҳǩ
					UIContext uiContext = new UIContext(ui);
					uiContext.put(UIContext.ID, id);
					CoreUIObject plUI = (CoreUIObject) UIFactoryHelper.initUIObject(uiClass, uiContext, null, "VIEW");
					scrollPane.setViewportView(plUI);
					scrollPane.setKeyBoardControl(true);
				}
				scrollPane.setUserObject(new String[] { id, uiClass });
			} else {
				KDScrollPane scrollPane = new KDScrollPane();
				tabPanel.add(scrollPane, title);
				int index = tabPanel.indexOfTab(title);
				if (index == 0) {
					// �򿪽���ʱ���ص�һ��ҳǩ
					UIContext uiContext = new UIContext(ui);
					uiContext.put(UIContext.ID, id);
					CoreUIObject plUI = (CoreUIObject) UIFactoryHelper.initUIObject(uiClass, uiContext, null, "VIEW");
					scrollPane.setViewportView(plUI);
					scrollPane.setKeyBoardControl(true);
				}
				scrollPane.setUserObject(new String[] { id, uiClass });
			}
		}
	}

	private void lazyLoadPanel()throws UIException {
		if(isFirstLoad) return;
		int index = plInviteAllInfo.getSelectedIndex();
		if(index==0){
			return;
		}
		if(plInviteAllInfo.getComponentAt(index) instanceof KDScrollPane ){
			KDScrollPane scrollPane=(KDScrollPane)plInviteAllInfo.getComponentAt(index);
			Object obj = scrollPane.getUserObject();
			if(obj!=null&&obj.getClass().isArray()){
				String s[]=(String[])obj;
				UIContext uiContext = new UIContext(ui);
				uiContext.put(UIContext.ID, s[0]);
				Component plUI = (CoreUIObject) UIFactoryHelper.initUIObject(s[1], uiContext,
						null, OprtState.VIEW);
				
				scrollPane.setViewportView(plUI);
				scrollPane.setKeyBoardControl(true);
				scrollPane.setUserObject("hasLoad");
			}
		}
		
	}
	private void lazyLoadPanel(ChangeEvent e)throws UIException {
		if(isFirstLoad) return;
		if(e.getSource() instanceof KDTabbedPane){
			KDTabbedPane tabPanel = (KDTabbedPane) e.getSource();
			int index = tabPanel.getSelectedIndex();
			if(index==0){
				return;
			}
			if(tabPanel.getComponentAt(index) instanceof KDScrollPane){
				KDScrollPane scrollPane=(KDScrollPane)tabPanel.getComponentAt(index);
				Object obj = scrollPane.getUserObject();
				if(obj!=null&&obj.getClass().isArray()){
					String s[]=(String[])obj;
					UIContext uiContext = new UIContext(ui);
					uiContext.put(UIContext.ID, s[0]);
					Component plUI = (CoreUIObject) UIFactoryHelper.initUIObject(s[1], uiContext,
							null, OprtState.VIEW);
					
					scrollPane.setViewportView(plUI);
					scrollPane.setKeyBoardControl(true);
					scrollPane.setUserObject("hasLoad");
				}
			}
		}
	}
	
	/**
	 * ����id��ʾ����
	 */
	public static boolean showDialogWindows(IUIObject ui, String id)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, id);
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(InviteAllInformationUI.class.getName(), uiContext, null,
						"VIEW");
		uiWindow.show();
		return true;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
	
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		plInviteAllInfo.removeAll();

		setButtonState();
		//����б�����ҳǩ
//		InviteProjectInfo prjInfo = InviteProjectFactory.getRemoteInstance().getInviteProjectInfo(	new ObjectUuidPK(BOSUuid.read(prjId))				);
		
		ArrayList leafProjectID = (ArrayList)this.initData.get("leafProjectID");
		String fileMergeId = (String)initData.get("InviteFileMergeID");
		if (leafProjectID != null && leafProjectID.size() > 0) { // ����б������Ҷ�ӣ�
																	// �ҳ����¼�����Ҷ���б�����
			KDTabbedPane inviteTabPanel = new KDTabbedPane();
			inviteTabPanel.addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent e) {
					try {
						lazyLoadPanel(e);
					} catch (UIException e1) {
						handUIException(e1);
					}
				}
			});
			this.plInviteAllInfo.add(inviteTabPanel,"�б�����");
			addPanel(inviteTabPanel,prjId, InviteProjectEditUI.class.getName(), "��������Ϣ");
			
			for(int i=0;i<leafProjectID.size();i++){
				KDTabbedPane leafProjectTabPanel = new KDTabbedPane();
				leafProjectTabPanel.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						try {
							lazyLoadPanel(e);
						} catch (UIException e1) {
							handUIException(e1);
						}
					}
				});
				inviteTabPanel.add(leafProjectTabPanel,"���"+(i+1));
				addPanel(leafProjectTabPanel,(String)leafProjectID.get(i), InviteProjectEditUI.class.getName(), "�����Ϣ");
				addPanel(leafProjectTabPanel,(String)initData.get("NewListingID"+(String)leafProjectID.get(i)), NewListingEditUI.class.getName(), "�嵥��Ϣ");
				addPanel(leafProjectTabPanel,(String)initData.get("SupplierQualifyID"+(String)leafProjectID.get(i)), SupplierQualifyEditUI.class.getName(), "��Ӧ���ʸ�Ԥ��");
				addPanel(leafProjectTabPanel,(String)initData.get("ExpertQualifyID"+(String)leafProjectID.get(i)), ExpertQualifyEditUI.class.getName(), "����ר��");
				addPanel(leafProjectTabPanel,(String)initData.get("AppraiseTemplateID"+(String)leafProjectID.get(i)), AppraiseTemplateEditUI.class.getName(), "����ģ��");
				String listUI = (String)getUIContext().get("LIST_UI");
				if(listUI!=null&&!listUI.equals("com.kingdee.eas.fdc.invite.client.InviteFileMergeListUI")){
					addPanel(leafProjectTabPanel,(String)leafProjectID.get(i), SupplierInviteRecordFullInfoUI.class.getName(), "��Ӧ��Ͷ���¼");
					addPanel(leafProjectTabPanel,(String)leafProjectID.get(i), InviteClarifyFullInfoUI.class.getName(), "�б����");
					addPanel(leafProjectTabPanel,(String)leafProjectID.get(i), ViewExpertAppraiseResultUI.class.getName(), "ר��Ͷ��������");
					addPanel(leafProjectTabPanel,(String)initData.get("AppraiseResultID"+(String)leafProjectID.get(i)), AppraiseResultEditUI.class.getName(), "����������");
					addPanel(leafProjectTabPanel,(String)initData.get("AcceptanceLetterID"+(String)leafProjectID.get(i)), AcceptanceLetterEditUI.class.getName(), "�б�֪ͨ��");
				}
				
			}
			addPanel(fileMergeId, InviteFileMergeEditUI.class.getName(), "�����ļ��ϳ�");
			addPanel(documentId, InviteDocumentsEditUI.class.getName(), "�б��ļ�");
		}
		else { // �б�������Ҷ�ӽڵ�
			addPanel(plInviteAllInfo,prjId, InviteProjectEditUI.class.getName(), "�б�����");
			addPanel(plInviteAllInfo,fileMergeId, InviteFileMergeEditUI.class.getName(), "�����ļ��ϳ�");
			addPanel(plInviteAllInfo, documentId, InviteDocumentsEditUI.class.getName(), "�б��ļ�");
			if(initData.get("NewListingID"+prjId) instanceof String)
				addPanel(plInviteAllInfo,(String)initData.get("NewListingID"+prjId), NewListingEditUI.class.getName(), "�嵥��Ϣ");
			addPanel(plInviteAllInfo,(String)initData.get("SupplierQualifyID"+prjId), SupplierQualifyEditUI.class.getName(), "��Ӧ���ʸ�Ԥ��");
			addPanel(plInviteAllInfo,(String)initData.get("ExpertQualifyID"+prjId), ExpertQualifyEditUI.class.getName(), "����ר��");
			addPanel(plInviteAllInfo,(String)initData.get("AppraiseTemplateID"+prjId), AppraiseTemplateEditUI.class.getName(), "����ģ��");
			String listUI = (String)getUIContext().get("LIST_UI");
			if(listUI!=null&&!listUI.equals("com.kingdee.eas.fdc.invite.client.InviteFileMergeListUI")){
				addPanel(plInviteAllInfo,prjId, SupplierInviteRecordFullInfoUI.class.getName(), "��Ӧ��Ͷ���¼");
				addPanel(plInviteAllInfo,prjId, InviteClarifyFullInfoUI.class.getName(), "�б����");
				addPanel(plInviteAllInfo,prjId, ViewExpertAppraiseResultUI.class.getName(), "ר��Ͷ��������");
				addPanel(plInviteAllInfo,(String)initData.get("AppraiseResultID"+prjId), AppraiseResultEditUI.class.getName(), "����������");
				addPanel(plInviteAllInfo, prjId, AcceptanceLetterFullInfoUI.class.getName(), "�б�֪ͨ��");
			}
		}
	
		
//		//��ӱ����ļ��ϳ�
//		addPanel(fileMergeId, InviteFileMergeEditUI.class.getName(), "�����ļ��ϳ�");
//		
//		//��ӹ�Ӧ���ʸ�Ԥ��
//		addPanel(supplierId, SupplierQualifyEditUI.class.getName(), "��Ӧ���ʸ�Ԥ��");
//		
//		//ȷ������ר��
//		addPanel(expertId, ExpertQualifyEditUI.class.getName(), "����ר��");
//		
//		//ȷ������ģ��
//		addPanel(templateId, AppraiseTemplateEditUI.class.getName(), "����ģ��");
//		
		String listUI = (String)getUIContext().get("LIST_UI");
		if(listUI!=null&&!listUI.equals("com.kingdee.eas.fdc.invite.client.InviteFileMergeListUI"))
		{
//			//��Ӧ��Ͷ���¼
//			addPanel(prjId, SupplierInviteRecordFullInfoUI.class.getName(), "��Ӧ��Ͷ���¼");
//			
//			//�б����
//			addPanel(prjId, InviteClarifyFullInfoUI.class.getName(), "�б����");
//			
//			//ר������������
//			addPanel(prjId, ViewExpertAppraiseResultUI.class.getName(), "ר��Ͷ��������");
//			
//			//����������
//			addPanel(appResultId, AppraiseResultEditUI.class.getName(), "����������");
//			
//			//�б�֪ͨ��
//			addPanel(letterId, AcceptanceLetterEditUI.class.getName(), "�б�֪ͨ��");
			
			setUITitle("�б�ִ����Ϣ");
			
			this.actionAudit.setVisible(false);
			this.actionUnAudit.setVisible(false);
		}
		else
		{
			setUITitle("�������");

			if(getOprtState().equals(OprtState.ADDNEW))
			{
				this.actionSubmit.setEnabled(true);
				this.actionSubmit.setVisible(true);
				
				this.actionRemove.setEnabled(false);
				this.actionRemove.setVisible(true);
				
				this.actionAudit.setVisible(false);
				this.actionUnAudit.setVisible(false);
			}
			else if(getOprtState().equals(OprtState.VIEW))
			{
				if(((InviteAllInformationInfo)editData).getState().equals(FDCBillStateEnum.AUDITTED))
				{
					this.actionSubmit.setEnabled(false);
					this.actionSubmit.setVisible(true);
					
					this.actionRemove.setEnabled(false);
					this.actionRemove.setVisible(true);
				}
				else if(((InviteAllInformationInfo)editData).getState().equals(FDCBillStateEnum.SUBMITTED))
				{
					this.actionSubmit.setEnabled(true);
					this.actionSubmit.setVisible(true);
					
					this.actionRemove.setEnabled(true);
					this.actionRemove.setVisible(true);
				}
			}
		}
		
		plInviteAllInfo.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				try {
					lazyLoadPanel();
				} catch (UIException e1) {
					handUIException(e1);
				}
			}
		});
		
		if(getUIContext().containsKey("isFromWorkflow")){
			this.actionSubmit.setEnabled(true);
		}
		
		isFirstLoad=false;
	}
	
	private void setButtonState()
	{
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		actionPre.setVisible(false);
		actionFirst.setVisible(false);
		actionLast.setVisible(false);
		actionNext.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionCalculator.setVisible(false);
		this.menuTable1.setVisible(false);
		this.menuItemCopyFrom.setVisible(false);
		
		menuItemCopy.setVisible(false);
		btnCopy.setVisible(false);
		
		
		String listUI = (String)getUIContext().get("LIST_UI");
		if((listUI!=null&&listUI.equals("com.kingdee.eas.fdc.invite.client.InviteFileMergeListUI"))
				|| getUIContext().containsKey("isFromWorkflow"))//���ܻ����Թ���������ť״̬ҲҪ�л�����Ϊ����
		{
			if(getOprtState().equals(OprtState.ADDNEW))
			{
				this.actionRemove.setVisible(true);
				this.actionSubmit.setVisible(true);
				this.actionWorkFlowG.setVisible(true);
				this.actionAuditResult.setVisible(true);
				this.actionMultiapprove.setVisible(true);
				this.actionNextPerson.setVisible(true);
				this.menuWorkflow.setVisible(true);
				this.menuTool.setVisible(true);
				this.actionAudit.setVisible(true);
				this.actionUnAudit.setVisible(true);
				this.actionAudit.setEnabled(true);
				this.actionUnAudit.setEnabled(true);
			}
			else if(getOprtState().equals(OprtState.EDIT))
			{
				this.actionRemove.setVisible(true);
				this.actionSubmit.setVisible(true);
				this.actionWorkFlowG.setVisible(true);
				this.actionAuditResult.setVisible(true);
				this.actionMultiapprove.setVisible(true);
				this.actionNextPerson.setVisible(true);
				this.menuWorkflow.setVisible(true);
				this.menuTool.setVisible(true);
				this.actionAudit.setVisible(true);
				this.actionUnAudit.setVisible(true);
				this.actionAudit.setEnabled(true);
				this.actionUnAudit.setEnabled(true);
			}
		}
		else
		{
			this.actionRemove.setVisible(false);
			this.actionSubmit.setVisible(false);
			this.actionWorkFlowG.setVisible(false);
			this.actionAuditResult.setVisible(false);
			this.actionMultiapprove.setVisible(false);
			this.actionNextPerson.setVisible(false);
			this.menuWorkflow.setVisible(false);
			this.menuTool.setVisible(false);
			this.actionAudit.setVisible(false);
			this.actionUnAudit.setVisible(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
		
	}
	
	protected IObjectValue createNewData() {
		InviteAllInformationInfo info = new InviteAllInformationInfo();
		info.setState(FDCBillStateEnum.SAVED);
		String listUI = (String)getUIContext().get("LIST_UI");
		if(listUI.equals("com.kingdee.eas.fdc.invite.client.InviteFileMergeListUI"))
		{
			if(getOprtState().equals(OprtState.ADDNEW))
			{
				//�����б�����
				InviteProjectInfo prjInfo = new InviteProjectInfo();
				BOSUuid prjInfoId = BOSUuid.read(prjId);
				prjInfo.setId(prjInfoId);
				info.setInviteProject(prjInfo);

				if(fileMergeId != null)
				{
					//���ñ����ļ��ϳ�
					InviteFileMergeInfo mergeInfo = new InviteFileMergeInfo();
					BOSUuid mergeInfoId = BOSUuid.read(fileMergeId);
					mergeInfo.setId(mergeInfoId);
					info.setInviteFileMerge(mergeInfo);
				}

				if(supplierId != null)
				{
					//��Ӧ���ʸ�Ԥ��
					SupplierQualifyInfo supInfo = new SupplierQualifyInfo();
					BOSUuid supInfoId = BOSUuid.read(supplierId);
					supInfo.setId(supInfoId);
					info.setSupplierQualify(supInfo);
				}

				if(expertId != null)
				{
					//����ȷ������ר��
					ExpertQualifyInfo expertInfo = new ExpertQualifyInfo();
					BOSUuid expertInfoId = BOSUuid.read(expertId);
					expertInfo.setId(expertInfoId);
					info.setExpertQualify(expertInfo);
				}

				if(templateId != null)
				{
					//��������ģ��
					AppraiseTemplateInfo tmpInfo = new AppraiseTemplateInfo();
					BOSUuid tmpInfoId = BOSUuid.read(this.templateId);
					tmpInfo.setId(tmpInfoId);
					info.setAppraiseTemplate(tmpInfo);
				}
				info.setNumber(prjName);
				info.setName(prjName);
			}
		}
		
		return info;
	}
	
	protected void fetchInitData() throws Exception {
//		super.fetchInitData();
		Map paramMap = this.getUIContext();
		Map param = new HashMap();
		if(paramMap.containsKey("isFromWorkflow")){
			InviteAllInformationInfo billInfo = null;
			if(paramMap.containsKey("DATAOBJECTS")){
				HashMap objects = (HashMap)paramMap.get("DATAOBJECTS");
				billInfo = (InviteAllInformationInfo)objects.get("billInfo");
			}
			if(billInfo!=null)
			{
				prjId = billInfo.getInviteProject().getId().toString();
				param.put("prjId", prjId);
			}
			else if(paramMap.containsKey("ID")){
				String billId = String.valueOf(paramMap.get("ID"));
//				billInfo = InviteAllInformationFactory.getLocalInstance(ctx).getInviteAllInformationInfo(new ObjectUuidPK(billId));
				param.put("billId", billId);
//				prjId = billInfo.getInviteProject().getId().toString();
			}else
					;
		}else{
			prjId    = (String) paramMap.get("INVITE_PROJECT");
			prjName = (String)paramMap.get("INVITEPROJECT_NAME");
			param.put("prjId", prjId);
			param.put("prjName", prjName);
		}
		
		initData = InviteAllInformationFactory.getRemoteInstance().fetchInitData(param);
		if(initData!=null){
			prjId    = (String)initData.get("prjId");
			prjName = (String)initData.get("prjName");
		}
		
//		if(this.getUIContext().containsKey("isFromWorkflow")){
//			InviteAllInformationInfo billInfo = null;
//			if(this.getUIContext().containsKey("DATAOBJECTS")){
//				HashMap objects = (HashMap)this.getUIContext().get("DATAOBJECTS");
//				billInfo = (InviteAllInformationInfo)objects.get("billInfo");
//			}
//			if(billInfo!=null)
//				prjId = billInfo.getInviteProject().getId().toString();
//			else if(this.getUIContext().containsKey("ID")){
//				String billId = String.valueOf(this.getUIContext().get("ID"));
//				billInfo = InviteAllInformationFactory.getRemoteInstance().getInviteAllInformationInfo(new ObjectUuidPK(billId));
//				prjId = billInfo.getInviteProject().getId().toString();
//			}else
//				return;
//		}else{
//			prjId    = (String) this.getUIContext().get("INVITE_PROJECT");
//			prjName = (String)getUIContext().get("INVITEPROJECT_NAME");
//		}
		
		
		fileMergeId = getFileMergeId(prjId);
		supplierId  = getSupplierQualifyId(prjId);
		expertId    = getExpertQualifyId(prjId);
		templateId  = getAppraiseTemplateId(prjId);
		appResultId = getAppraiseResultId(prjId);
		letterId = getAcceptanceLetterId(prjId);
		documentId = getInviteDocumentId(prjId);
	}

	private String getAcceptanceLetterId(String paramPrjId) throws BOSException
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id where inviteProject = '");
		buffer.append(paramPrjId);
		buffer.append("'");
		
		AcceptanceLetterCollection cols = AcceptanceLetterFactory.getRemoteInstance().getAcceptanceLetterCollection(buffer.toString());
		
		if(cols.size() > 0)
		{
			AcceptanceLetterInfo info = cols.get(0);
			return info.getId().toString();
		}
		
		return null;
	}
	private String getAppraiseResultId(String paramPrjId) throws BOSException, EASBizException
	{
		IObjectPK pk = new ObjectUuidPK(paramPrjId);
		InviteProjectInfo prjInfo = InviteProjectFactory.getRemoteInstance().getInviteProjectInfo(pk);
		
		if(prjInfo.isIsExpertAppraise())
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("select id where inviteProject = '");
			buffer.append(paramPrjId);
			buffer.append("'");
			AppraiseResultCollection cols = AppraiseResultFactory.getRemoteInstance().getAppraiseResultCollection(buffer.toString());

			if(cols.size() > 0)
			{
				AppraiseResultInfo info = cols.get(0);
				return info.getId().toString();
			}
		}
		
		return null ;
	}
	protected void verifyInputForSubmint() throws Exception {
		
		//�����б������Ƿ�ֻ����һ�������ļ��ϳ�
		
		if(((InviteAllInformationInfo)editData).getState().equals(FDCBillStateEnum.SAVED)||
    			((InviteAllInformationInfo)editData).getState().equals(FDCBillStateEnum.SUBMITTED))
    	{
			;
    	}
    	else
    	{
    		StringBuffer buffer = new StringBuffer();
    		buffer.append("״̬Ϊ");
    		buffer.append(((InviteAllInformationInfo)editData).getState().getAlias());
    		buffer.append("������ִ�д˲���");
    		MsgBox.showWarning(this, buffer.toString());
    		
    		abort();
    	}
	}
	protected void verifyInputForSave() throws Exception {

	}
	protected boolean isShowAttachmentAction() {
		return false ;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(((InviteAllInformationInfo)editData).getState().equals(FDCBillStateEnum.SUBMITTED)
				||((InviteAllInformationInfo)editData).getState().equals(FDCBillStateEnum.SAVED))
    	{
			//û�н��빤����
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
			hasRemove=false;
	        if(!confirmRemove()){
	      	  return;
	        }
	        String tempState = this.getOprtState();
	        this.setOprtState("REMOVE");
	        IObjectValue val = (IObjectValue)getUIContext().get("CURRENT.VO") ;
	        getUIContext().put("CURRENT.VO",null) ;
	        setDataObject(val) ;

	        try
	        {
	        	IObjectPK pk = new ObjectUuidPK(this.editData.getId());
	            this.getBizInterface().delete(pk);
	            hasRemove=true;
	        }
	        finally
	        {
	            //�ָ�״̬��
	            this.setOprtState(tempState);
	        }
	        setSave(true);
	        setSaved(true);
    	}
    	else
    	{
    		StringBuffer buffer = new StringBuffer();
    		buffer.append("״̬Ϊ");
    		buffer.append(((InviteAllInformationInfo)editData).getState().getAlias());
    		buffer.append("������ִ�д˲���");
    		MsgBox.showWarning(this, buffer.toString());
    		
    		abort();
    	}
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
		if(((InviteAllInformationInfo)editData).getState().equals(FDCBillStateEnum.SUBMITTED))
    	{
			//û�н��빤����
//			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
			super.actionEdit_actionPerformed(e);
    	}
    	else
    	{
    		StringBuffer buffer = new StringBuffer();
    		buffer.append("״̬Ϊ");
    		buffer.append(((InviteAllInformationInfo)editData).getState().getAlias());
    		buffer.append("������ִ�д˲���");
    		MsgBox.showWarning(this, buffer.toString());
    		
    		abort();
    	}
		
	}
	
	 public void afterActionPerformed(ActionEvent e) {
	    	super.afterActionPerformed(e);
	    	if(e.getSource()==btnRemove||e.getSource()==menuItemRemove){
	    		if(hasRemove){
	        		try {
	        			setOprtState(OprtState.VIEW);
	    				actionExitCurrent_actionPerformed(null);
	    			} catch (Exception e1) {
	    				handUIException(e1);
	    			}
	    		}
	    	}
	    }
}